import React, { useEffect, useState } from "react";
import { useLocation } from "react-router-dom";
import axios from "axios";

function Unsubscribe() {
  const baseUrl = process.env.REACT_APP_API_KEY;
  const [email, setEmail] = useState("");

  const getEmailUsername = (email) => {
    // Split the email address at the '@' symbol
    const parts = email.split("@");
    // The username is the first part of the split array
    const username = parts[0];
    return username;
  };

  const location = useLocation();

  useEffect(() => {
    const searchParams = new URLSearchParams(location.search);
    const email = searchParams.get("email");
    if (email) {
      setEmail(email);
      console.log("Email:", email);

      // Call API using Axios
      axios
        .get(`${baseUrl}unsubscribe?email=${email}`)
        .then((response) => {
          console.log("API Response:", response.data);
          // Handle API response here as needed
        })
        .catch((error) => {
          console.error("API Error:", error);
          // Handle errors here
        });
    }
  }, [location.search]); // Empty dependency array to ensure it runs only once on mount

  return (
    <div
      style={{
        maxWidth: "650px",
        padding: "48px",
        border: "1px solid #dadada",
        backgroundSize: "104% auto",
        backgroundImage: `url(${baseUrl}image/background.png)`,
        borderRadius: "100px",
        fontFamily: "Arial, Helvetica, sans-serif",
        fontSize: "15px",
        color: "#495057",
        margin: "auto",
        textAlign: "center",
      }}
    >
      <h1 style={{  fontWeight: "bold",color: 'WHite' }}>Unsubscribe Page</h1>
      <p style={{ fontSize: "17px", fontWeight: "bold",color: 'WHite' }}>
        Dear {getEmailUsername(email)},
      </p>
      <p style={{ fontSize: "17px", fontWeight: "bold",color: 'WHite' }}>
        We are sorry to see you go, but we have unsubscribed you from our
        mailing list as requested. You will no longer receive emails from us.
      </p>
      <p style={{ fontSize: "17px", fontWeight: "bold",color: 'WHite' }}>
        If you wish to re-subscribe or have any further questions, please feel
        free to contact us.
      </p>
      <p style={{ fontSize: "17px", fontWeight: "bold",color: 'WHite' }}>
        Best regards,
        <br />
        Data Courier
      </p>
    </div>
  );
}

export default Unsubscribe;
