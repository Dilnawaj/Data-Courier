# DataCourierApp

This is a full-stack application for system to retrieve files from any computer in multiple formats, integrating with BombitUp  functionalit like Implemented instant email notifications & sms notifications.

## Prerequisites

Before running this application, ensure you have the following installed:

### Backend
- JAVA
- SpringBoot
- Logger
- MySQL

### Frontend
- React Js
- JavaScript
- HTML
- CSS

## Running the React App

To run the React app, follow these steps:

1. Set up environment variables   `REACT_APP_API_KEY`.
2. Install dependencies:
   ```bash
   npm install --legacy-peer-deps
   npm install
3. npm start

To run the React Backend app, follow these steps:
1. Run MySql server and create database blog_app_apis.
2. setup App Password and run spring boot app using below commands:
   ```bash
   mvn clean install -U(if needed)
   mvn clean install
   mvn spring-boot:run
3. Setup Smtp using Email and AppPassword & clientId(for google)