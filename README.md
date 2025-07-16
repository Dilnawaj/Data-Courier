# DataCourierApp

This is a full-stack application for system to retrieve files from any computer in multiple formats, integrating with BombitUp functionality like implemented instant email notifications & SMS notifications.

**FrontEnd URL:** [https://data-courier-dewlxaxeu-dilnawajs-projects.vercel.app/](https://data-courier-dewlxaxeu-dilnawajs-projects.vercel.app/)  
**Backend URL:** [http://datacourier-env.eu-north-1.elasticbeanstalk.com/](http://datacourier-env.eu-north-1.elasticbeanstalk.com/)

---

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

---

## Running the React App

To run the React app, follow these steps:

1. Set up environment variable `REACT_APP_API_KEY` in a `.env` file.

2. Install dependencies:

   ```bash
   npm install --legacy-peer-deps
   npm install
3.Start the React app:
 ```bash
   npm start


## Running the React Backend App

To run the backend (Spring Boot) application, follow these steps:

1. Run MySQL server and create a database named `blog_app_apis`.

2. Set up your email service credentials (App Password and Client ID for Gmail SMTP).

3. In the backend project directory, run the following commands:

   ```bash
   mvn clean install -U   # only if needed
   mvn clean install
   mvn spring-boot:run

