import Base from "../components/Base";
import {
  Card,
  CardBody,
  CardHeader,
  Container,
  FormGroup,
  Input,
  Label,
  Form,
  Button,
} from "reactstrap";
import { useState } from "react";
import { toast } from "react-toastify";
import axios from "axios";
import { Link } from "react-router-dom";

const BombitUp = () => {
  const [selectedFiles, setSelectedFiles] = useState([]);
  const [email, setEmail] = useState("");
  const [reqId, setReqId] = useState(0);
  const [count, setCount] = useState("");
  const [reqCount, setReqCount] = useState(0);
  const [polling, setPolling] = useState(false);
  const [submitted, setSubmitted] = useState(false); // New state to track form submission
  const baseUrl = process.env.REACT_APP_API_KEY;

  const resetForm = () => {
    setEmail("");
    setSelectedFiles([]);
    setCount(0)
  };

  const handleChange = (e, tag) => {
    const value = e.target.value;
    if (tag === "email") {
      setEmail(value);
    } else {
      setCount(value);
    }
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      const response = await axios.get(
        `${baseUrl}bombit?email=${email}&count=${count}`
      );
      setReqId(response.data);
      toast.success("Email start sending!");
      resetForm();
      setSubmitted(true); // Set submitted to true
      handleSubmitStatus(response.data);
    } catch (error) {
      console.error("Error uploading files:", error);
      toast.error("Failed to send emails.");
    }
  };

  const handleSubmitStatus = async (id) => {
    try {
      const interval = setInterval(async () => {
        const response = await axios.get(`${baseUrl}bombit/status?id=${id}`);
        setReqCount(response.data);
        if (response.data == count) {
          clearInterval(interval);
          setPolling(false);
          
          toast.success("Emails sent successfully!");
          setSubmitted(false);
        }
      }, 1000);
      setPolling(true);
    } catch (error) {
      console.error("Error checking status:", error);
      toast.error("Failed to check email status.");
    }
  };

  return (
    <Base>
      <div
        style={{
          display: "flex",
          justifyContent: "center",
          alignItems: "center",
          backgroundImage: `url(${baseUrl}image/background.png)`,
          backgroundSize: "104% auto",
          backgroundPosition: "left center",
          backgroundRepeat: "no-repeat",
          height: "100vh",
          paddingLeft: "390px",
        }}
      >
        <Container>
          <Card
            inverse
            style={{
              backgroundColor: "#454545",
              width: "700px",
              marginBottom: "170px",
              marginTop: "0px",
              height: "400px",
              display: "flex",
              justifyContent: "center",
            }}
          >
            <CardHeader style={{ textAlign: "center" }}>
              <h3>Email Blast</h3>
            </CardHeader>
            <CardBody>
              <Form onSubmit={handleSubmit}>
                <FormGroup>
                  <Label for="email">Enter Email</Label>
                  <Input
                    type="email"
                    placeholder="Enter the receiver's email"
                    id="email"
                    value={email}
                    required
                    onChange={(e) => handleChange(e, "email")}
                  />
                </FormGroup>
                <FormGroup>
                  <Label for="number">Enter Count</Label>
                  <Input
                    type="number"
                    placeholder="Please enter the count you wish to send"
                    id="number"
                    value={count}
                    required
                    onChange={(e) => handleChange(e, "count")}
                  />
                </FormGroup>

                <Container className="text-center">
                  <Button color="warning" className="mr-2 mx-2" type="submit">
                    BOMBIT
                  </Button>
                  
                  
                    <Link to="/">
                      <Button color="info" size="mg" className="mr-2 mx-2">
                        Back
                      </Button>
                    </Link>
                 
                </Container>
                <div style={{ marginTop: "20px", textAlign: "center" }}>
                  {!submitted ? (
                    <h4 style={{ color: "#FF6347", fontWeight: "bold" }}>
                      Use it for Fun not for Revenge
                    </h4>
                  ) : (
                    <h4 style={{ color: "#32CD32", fontWeight: "bold" }}>
                      Email Sent: {reqCount}
                    </h4>
                  )}
                </div>
              </Form>
            </CardBody>
          </Card>
        </Container>
      </div>
    </Base>
  );
};

export default BombitUp;
