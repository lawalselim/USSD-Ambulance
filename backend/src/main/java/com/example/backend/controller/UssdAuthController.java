package com.example.backend.controller;
import com.example.backend.dto.RegisterUserDto;
import com.example.backend.entities.USSDSession;
import com.example.backend.repository.USSDSessionRepository;
import com.example.backend.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/ussd/")
public class UssdAuthController {

        @Autowired
        private AuthenticationService authenticationService;

        @Autowired
        private USSDSessionRepository ussdSessionRepository;

    @Autowired
    private RestTemplate restTemplate;

        @PostMapping("/register")
        public void handleUSSDRequest(@RequestBody String requestBody, HttpServletResponse response) throws IOException {
            Map<String, String> body = Arrays.asList(requestBody.split("&"))
                    .stream()
                    .map(entry -> entry.split("="))
                    .collect(Collectors.toMap(entry -> entry[0], entry -> entry.length == 2 ? decodeValue(entry[1]) : ""));

            String sessionId = body.get("sessionId");
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
                return "+234" + rawPhoneNumber;
            }
            return rawPhoneNumber;
        }

        private String processUSSDInput(String userInput, USSDSession session) {
            int step = session.getStep();
            switch (step) {
                case 1:
                    session.setStep(2);
                    ussdSessionRepository.save(session);
                    return "CON Please enter your name:";
                case 2:
                    session.setName(userInput);
                    session.setStep(3);
                    ussdSessionRepository.save(session);
                    return "CON Please enter your email:";
                case 3:
                    session.setEmail(userInput);
                    session.setStep(4);
                    ussdSessionRepository.save(session);
                    return "CON Please enter your password:";
                case 4:
                    session.setPassword(userInput);
                    ussdSessionRepository.save(session);
                    RegisterUserDto userDto = new RegisterUserDto();
                    userDto.setName(session.getName());
                    userDto.setEmail(session.getEmail());
                    userDto.setPassword(userInput);
                    userDto.setphoneNumber(session.getPhoneNumber());
                    authenticationService.signup(userDto);
                    return "END Thank you for registering.";
                default:
                    return "END Invalid option. Please start again.";
            }
        }

    /*
     * This block of code section implements
     * Africas Talking USSD API
     * Do not tamper with it.
     *
     * Dev environment dial code to register a user on USSD is *384*36533*
     * */
    private void sendSMS(String phoneNumber, String message) {
        String apiUrl = "https://api.africastalking.com/version1/messaging";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("apiKey", "84b4b91d84ac4283281c0db2d554f0d68fc263eb04183f3ec6333392f2297f6e");

        Map<String, Object> smsRequestBody = new HashMap<>();
        smsRequestBody.put("username", "sandbox");
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

