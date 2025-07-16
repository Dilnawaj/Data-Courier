DataCourierApp
DataCourierApp is a full-stack application that allows users to retrieve files from any computer in multiple formats. It integrates BombitUp-like functionality such as instant email notifications and SMS alerts.

Prerequisites
Ensure the following tools and platforms are installed and configured before running the application:

Backend
Java (JDK)

Spring Boot

MySQL

Logger (e.g., SLF4J)

Frontend
React.js

JavaScript

HTML

CSS

Running the Backend (Spring Boot)
Run MySQL server and create a database named blog_app_apis.

Configure SMTP for email services:

Use your email, App Password, and Client ID (if using Google OAuth).

In the backend project directory, run the following commands:

bash
Copy
Edit
mvn clean install -U   # only if needed
mvn clean install
mvn spring-boot:run
Running the Frontend (React)
Create a .env file in the root directory and set the environment variable:

ini
Copy
Edit
REACT_APP_API_KEY=your_api_key_here
Install dependencies:

bash
Copy
Edit
npm install --legacy-peer-deps
Start the React application:

bash
Copy
Edit
npm start
