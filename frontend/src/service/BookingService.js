//import axios from 'axios';

//const BASE_URL = 'http://localhost:8005/admins/bookings'; // Adjust this URL based on your actual backend URL

// In BookingService.js
export const getAllBookings = async () => {
    return fetch('http://localhost:8005/admins/bookings')
        .then(response => {
            if (!response.ok) {
                throw new Error('Network response was not ok');
            }
            return response.json();
        })
        .catch(error => {
            console.error('Error fetching bookings:', error);
            return []; // Return an empty array as fallback
        });
};

