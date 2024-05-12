# USSD-Ambulance Booking System 
This is my masters Dissertation project for staffordshire University Msc.Computer Science (Software Engineering). The goal is to develop an ambulance booking system that allows people to book for ambulance via the USSD.

## Overview
This project is a USSD/web application for ambulance booking. 
Users can input their details through a carefully carfted and visually appealing USSD interface. 
The Java springboot backend processes the request, providing a confirmation response and 
serving as a foundation for expanding ambulance booking logic.

# Folder Strcuture
 - BackEnd  - Contains everyhting relating to the backend of the entire application.
 - FrontEnd - Contains everything relating to the admin frontend of the application.

# Installation Guide 
Clone the repository onto your local machine.
Checkout to the main branch and pull from the main branch.
Cd into the backend folder to work on the backend.
cd into the frontend folder to work on the frontend.
Run both folders separtely.

# Setup the USSD testing sandbox.
- Create an account on africa's talking "https://account.africastalking.com/auth/register?next=%2Fapps%2Fsandbox%2Fussd%2Fcodes"
- Navigate to the USSD SECTION
- Create a USSD Channel
- Replace your Callback URL with the API ussd controller API e.g "https://assuring-doe-noble.ngrok-free.app/ussd/callback" ( replace the "assuring-doe-noble.mgrok-free.app" with your localhost name )
- Create your sanbox API key under settings or comment the current api key in the USSD controller and uncomment the similar api key as the current is pointing to production.
![image](https://github.com/lawalselim/USSD-Ambulance/assets/20151468/9a2990e0-fb2b-4e6a-9001-e199747500d7)

- Dial *384*15845# ( Replace the code with the USSD code generated for you ) on the dev environment by africa's talking.


# Backend Installation and Running the code.
To run the backend of the application ensure the following is setup on your location machine
- XAMPP OR WAMP server for database setup
- Replace the database url in the application.properties folder. sample DB url - " jdbc:mysql://localhost:3306/ussddb" 
- Replace the google cloud API with your own API in the application.properties file.
- navigate into the USSDcontroller under the controller folder and replace the AFRICA's talking API with your own API... Get yours here :"https://account.africastalking.com/apps/sandbox/settings/key"
- Run maven dependcies to install all dependencies in the pom.xml folder.
- Start your database server (XAMPP OR WAMP SERVER)
- Run the backend application.

# Frontend Installation Guide.
- Install yarn globally on your local machine.
- cd in the frontend folder of the application.
- run " yarn install " to the install the frontend dependencies
- run " yarn start " to start the frontend on a localhost.
- navigate to localhost://3000 oon your browser and login to the admin panel.



## Features
- Quick and accurate ambulance bookings during emergencies.
- User-friendly admin web interface for seamless requests.
- Java springboot backend for efficient server-side processing.
