package com.example.backend.controller;

import com.example.backend.dto.EmergencyBookingCreateDto;
import com.example.backend.dto.EmergencyBookingResponseDto;
import com.example.backend.dto.RegisterUserDto;
import com.example.backend.entities.EmergencyType;
import com.example.backend.entities.EmergencyTypeEnum;
import com.example.backend.entities.USSDSession;
import com.example.backend.exceptions.GeocodingException;
import com.example.backend.exceptions.UserNotFoundException;
import com.example.backend.repository.USSDSessionRepository;
import com.example.backend.service.AuthenticationService;
import com.example.backend.service.EmergencyBookingService;
import com.example.backend.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@RestController
@RequestMapping("/ussd/")
public class USSDController {

    private static final Logger logger = LoggerFactory.getLogger(USSDController.class);

    @Autowired
    private EmergencyBookingService emergencyBookingService;

    @Autowired
    private USSDSessionRepository ussdSessionRepository;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private AuthenticationService authenticationService;

    /*
    * This block of code handles api to book an ambulance
    * by
    *
    * */
    @PostMapping("/callback")
    public void handleUSSDRequest(@RequestBody String requestBody, HttpServletResponse response) throws IOException {
        Map<String, String> body = Arrays.asList(requestBody.split("&"))
                .stream()
                .map(entry -> entry.split("="))
                .collect(Collectors.toMap(entry -> entry[0], entry -> entry.length == 2 ? decodeValue(entry[1]) : ""));

        String sessionId = body.get("sessionId");
        String serviceCode = body.get("serviceCode");
        String phoneNumber = normalizePhoneNumber(body.get("phoneNumber"));
        String text = body.get("text");

        USSDSession session = ussdSessionRepository.findById(sessionId)
                .orElseGet(() -> new USSDSession(sessionId, phoneNumber, 1));

        String responseMessage = processUSSDInput(text, session);

        response.getWriter().write(responseMessage);
        response.setContentType("text/plain");
        response.setStatus(HttpServletResponse.SC_OK);
    }

    private String decodeValue(String value) {
        return URLDecoder.decode(value, StandardCharsets.UTF_8);
    }

    private String normalizePhoneNumber(String rawPhoneNumber) {
        if (rawPhoneNumber.startsWith("0")) {
            return "+234" + rawPhoneNumber.substring(1);
        } else if (!rawPhoneNumber.startsWith("+")) {
            return "+234" + rawPhoneNumber; // Adjust based on the expected format
        }
        return rawPhoneNumber;
    }

    private String processUSSDInput(String userInput, USSDSession session) {
        int step = session.getStep();
        switch (step) {
            case 1:
                // Combine greeting message with the emergency type menu
                String greeting = "CON 911 what's your Emergency?\n";
                String emergencyTypeMenu = buildEmergencyTypeMenu();
                session.setStep(2); // Ensure we move to the next step after displaying the menu
                ussdSessionRepository.save(session);
                return greeting + "\n" + emergencyTypeMenu;

            case 2:
                // Handle emergency type selection and prompt for location input
                return handleEmergencyTypeSelection(userInput, session);

            case 3:
                // Now handle location input correctly, using the dedicated method
                return handleLocationInput(userInput, session);

            case 4:
                // Handle booking confirmation
                return handleConfirmation(userInput, session);

            default:
                // Final response when all steps are completed or if an unexpected step is encountered
                return "END Thank you for using our service.";
        }
    }

    private String extractAddressFromUserInput(String sessionInput) {
        logger.info("Original session input: {}", sessionInput);  // Log the original input for debugging
        String[] parts = sessionInput.split("\\*");

        // Attempt to identify the address part more robustly
        if (parts.length == 3) {  // Expecting exactly three parts
            return parts[1].trim();  // The middle part should be the address
        } else if (parts.length > 1) {
            // Handle unexpected formats by taking the longest part (most likely to be the address)
            return Arrays.stream(parts)
                    .map(String::trim)
                    .reduce((a, b) -> a.length() > b.length() ? a : b)
                    .orElse("");
        }
        return "";  // Return empty if the expected format isn't met or no suitable part is found
    }

    private String handleLocationInput(String userInput, USSDSession session) {
        String extractedAddress = extractAddressFromUserInput(userInput);
        logger.info("Extracted address: {}", extractedAddress);  // This log should now provide more accurate feedback

        if (extractedAddress.isEmpty()) {
            logger.error("Failed to extract a valid address from user input: {}", userInput);
            return "CON Failed to extract address. Please re-enter your address:";
        }

        session.setLocation(extractedAddress);
        session.setStep(4);  // Set the next step to confirmation
        ussdSessionRepository.save(session);
        return "CON Confirm Booking\n1. Confirm\n2. Cancel";
    }

    private String buildEmergencyTypeMenu() {
        List<EmergencyType> types = emergencyBookingService.getAllEmergencyTypes();
        String menu = IntStream.range(0, types.size())
                .mapToObj(i -> (i + 1) + ". " + types.get(i).getName())
                .collect(Collectors.joining("\n"));
        return "CON Choose Emergency Type:\n" + menu;
    }

    private String handleEmergencyTypeSelection(String userInput, USSDSession session) {
        // Check for empty or null input immediately
        if (userInput == null || userInput.trim().isEmpty()) {
            logger.info("Received empty input for emergency type selection");
            // Prompt the user to make a selection again
            return "CON Invalid selection. Please select an emergency type:\n" + buildEmergencyTypeMenu();
        }

        List<EmergencyType> types = emergencyBookingService.getAllEmergencyTypes();
        try {
            int selectionIndex = Integer.parseInt(userInput.trim()) - 1; // Convert to 0-based index
            if (selectionIndex >= 0 && selectionIndex < types.size()) {
                EmergencyType selectedType = types.get(selectionIndex);
                session.setEmergencyType(selectedType.getName()); // Directly use the enum value
                session.setStep(3); // Move to the next step
                ussdSessionRepository.save(session);
                return "CON Enter your landmark or address:";
            } else {
                // The user's selection is out of the valid range
                return "CON Invalid selection. Please select a valid emergency type:\n" + buildEmergencyTypeMenu();
            }
        } catch (NumberFormatException e) {
            // Log the error and prompt the user again
            logger.error("Error parsing emergency type selection: '{}'. Exception: ", userInput, e);
            return "CON Invalid selection. Please select a valid emergency type:\n" + buildEmergencyTypeMenu();
        }
    }

    private String handleConfirmation(String userInput, USSDSession session) {
        logger.info("Received user input for confirmation: '{}'", userInput);

        // Process the userInput to ensure it's properly segmented
        String[] inputParts = userInput.split("\\*");
        // Assuming the last part of the input is the actual selection for confirmation
        String actualSelection = inputParts[inputParts.length - 1].trim();
        //System.out.println(actualSelection);
        logger.info("Actual selection for switch case: '{}'", actualSelection);

        switch (actualSelection) {
            case "1": // User confirms the booking
                return executeBooking(session);

            case "2": // User cancels
                ussdSessionRepository.delete(session);
                return "END Booking cancelled.";

            default:
                logger.warn("Invalid selection made: '{}'", actualSelection);
                return "CON Invalid selection. Please choose:\n1. Confirm\n2. Cancel";
        }
    }


    private String executeBooking(USSDSession session) {
        try {
            EmergencyBookingCreateDto dto = new EmergencyBookingCreateDto();
            dto.setPhoneNumber(normalizePhoneNumber(session.getPhoneNumber()));
            // Ensure this uses the location which now should contain the correctly extracted address
            dto.setAddress(session.getLocation());
            dto.setEmergencyType(session.getEmergencyType());

            EmergencyBookingResponseDto responseDto = emergencyBookingService.bookAmbulance(dto);

            String bookingDetailsMessage = String.format(
                    "Booking confirmed, Help is on its way. Location: %s. Latitude: %f, Longitude: %f",
                   responseDto.getAddress(), responseDto.getLatitude(), responseDto.getLongitude());

           ussdSessionRepository.delete(session);
           return "END " + bookingDetailsMessage;
        } catch (Exception e) {
            logger.error("Error processing booking confirmation", e);
            return "END An error occurred while processing your booking. Please try again.";
        }
    }



/*
* This block of code section implements
* Africas Talking USSD API
* Do not tamper with it.
*
* The USSD code to book an ambulance on dev is *384*15845#
* */

    private void sendSMS(String phoneNumber, String message) {
        String apiUrl = "https://api.africastalking.com/version1/messaging";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("apiKey", "3299f7bbbd311be8e9dbf5e6d6c88eb772129c419e4fb852c48cc073bda7eed5");
        //headers.set("apiKey", "84b4b91d84ac4283281c0db2d554f0d68fc263eb04183f3ec6333392f2297f6e");

        Map<String, Object> smsRequestBody = new HashMap<>();
        smsRequestBody.put("username", "USSDAmbulance");
        smsRequestBody.put("to", phoneNumber);
        smsRequestBody.put("message", message);

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(smsRequestBody, headers);

        try {
            ResponseEntity<String> response = restTemplate.postForEntity(apiUrl, request, String.class);
            // Log response or handle it according to your needs
        } catch (Exception e) {
            // Log error
        }
    }
}
