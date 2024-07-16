import React from "react";
import { Card, CardBody, Button } from "reactstrap";
import Base from "../components/Base";
import { Link } from "react-router-dom";
const About = () => {
  const baseUrl = process.env.REACT_APP_API_KEY;
  return (
    <Base>
      <div className="about-page" >
        <div
          className="background-container"
          style={{
            display: "flex",
            backgroundImage: `url(${baseUrl}image/background.png)`,
            backgroundSize: "104% auto", // Increase the left side length
            backgroundPosition: "left center", // Align the image to the left side
            backgroundRepeat: "no-repeat",
            height: "100vh",
          }}
        >
        <div className="content-container" >

            <h2 style={{ color: 'WHite' }}>Welcome to Data Courier !</h2>

            <div className="features" >
              <h3 style={{ color: 'WHite', marginleft: '50px' }}>Key Features:</h3>
              <Card >
                <CardBody >
                  <ul>
                    <li>
                      <span className="feature-title">
                        Effortless File Retrieval:
                      </span>{" "}
                      Seamlessly retrieve files or data from any computer,
                      regardless of the location, ensuring hassle-free access.
                    </li>
                    <li>
                      <span className="feature-title">
                        Support for Diverse Formats:
                      </span>{" "}
                      Handle a wide range of file formats, including jar, zip,
                      and many more, providing comprehensive compatibility for
                      all user needs.
                    </li>

                    <li>
                      <span className="feature-title">
                        Instant Email Notifications:
                      </span>{" "}
                      Automatically notify users via email upon successful
                      retrieval of their files, ensuring they are always
                      informed and updated.
                    </li>

                    <li>
                      <span className="feature-title">
                        Enhanced Convenience:
                      </span>{" "}
                      Deliver a seamless and user-friendly experience by
                      combining effortless file retrieval and instant
                      notifications, making data access straightforward and
                      efficient.
                    </li>
                  </ul>
                  <div className="d-flex justify-content-center">
                    <Link to="/">
                      <Button color="info" size="mg" className="mr-2 mx-2">
                        Back
                      </Button>
                    </Link>
                  </div>
                </CardBody>
              </Card>
            </div>
            <div className="image-container mt-3 container text-center">
              <div className="image-wrapper"></div>
            </div>
          </div>
        </div>
      </div>
    </Base>
  );
};

export default About;
