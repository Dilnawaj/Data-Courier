🚀 DataCourierApp
DataCourierApp is a full-stack web application that allows users to retrieve files from any computer in multiple formats. It also integrates BombitUp-like functionality, including instant Email and SMS notifications for updates and activities.

🔗 Live Links
Frontend: https://data-courier-dewlxaxeu-dilnawajs-projects.vercel.app/

Backend: http://datacourier-env.eu-north-1.elasticbeanstalk.com/

📦 Tech Stack
🖥️ Backend
Java

Spring Boot

MySQL

SLF4J Logger

🌐 Frontend
React.js

JavaScript

HTML5

CSS3

✅ Prerequisites
Before you begin, make sure the following software is installed on your system:

Java JDK

Node.js & npm

MySQL Server

Maven

⚙️ Getting Started
🔧 Running the Backend (Spring Boot)
Start MySQL server and create a database named blog_app_apis.

Set up SMTP configuration for email services:

Use your Gmail Email, App Password, and Client ID (if using Google OAuth).

Navigate to the backend project directory and run:

bash
Copy
Edit
mvn clean install
mvn spring-boot:run
💻 Running the Frontend (React)
Create a .env file in the root of your React project and add:

ini
Copy
Edit
REACT_APP_API_KEY=your_api_key_here
Install dependencies:

bash
Copy
Edit
npm install --legacy-peer-deps
Start the React app:

bash
Copy
Edit
npm start
📬 Features
📁 Retrieve files from any system in multiple formats

📧 Email notifications

📲 SMS notifications (BombitUp-style)

🌐 Deployed using:

AWS Elastic Beanstalk for Backend

AWS RDS for Database

Vercel for Frontend Hosting

🧑‍💻 Author
Mohammad Dilnawaj Khan
Experienced Java & React Developer | Microservices | AWS Enthusiast
