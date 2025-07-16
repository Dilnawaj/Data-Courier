import React, { useState } from "react";
import Base from "../components/Base";
import {
    Button,
  Card,
  CardBody,
  CardHeader,
  Container,
  Form,
  FormGroup,
  Input,
  Label,
} from "reactstrap";
import { Link } from "react-router-dom";
import axios from "axios";
import { toast } from "react-toastify";
function BombitUpSms() {
  const baseUrl = process.env.REACT_APP_API_KEY;
  const [phoneNumber, setPhoneNumber] = useState("");
  const [reqId, setReqId] = useState(0);
  const [count,setCount]= useState("");
  const [submitted, setSubmitted] = useState(false); 
  const[reqCount,setReqCount] = useState(0);
  const resetForm = () => {
    setPhoneNumber("");
    setCount(0)
  };
  const handleCountChange =(e,tag)=>
  {
    setCount(e.target.value);

  }


  const handleSubmit =  (e) => {


e.preventDefault();
if (phoneNumber.length < 10) {
setPhoneNumber("91" + phoneNumber);
}
axios.get(
  `${baseUrl}bombit/message?number=91${phoneNumber}&count=${count}`).then((response)=>
  {
    setReqId(response.data);
    console.log("Data is:"+response.data)
    handleSubmitStatus(response.data);
    setSubmitted(true);
    resetForm();
    toast.success("Message start sending!");

  }).catch((response) =>{
console.log(response)
    toast.error("Failed to send message.");
  })




const handleSubmitStatus= async(id)=>
{

  try{
  const interval=setInterval(async()=>
  {
    const response = await axios.get(`${baseUrl}bombit/sms/status?id=${id}`);
    setReqCount(response.data);

    if (response.data == count) {
      clearInterval(interval);
      toast.success("Message sent successfully!");
    }

  },1000)

}catch(error)
{
  toast.error("Failed to check message status.");
}
}


  };
  const handlePhoneNumberChange = (e,tag) => {

    setPhoneNumber(e.target.value);
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
              <h3> Message Blast</h3>
            </CardHeader>
            <CardBody>
              <Form onSubmit={handleSubmit}>
                <FormGroup>
                  <Label for="phoneNumber">Enter PhoneNumber</Label>
                  <Input
                    type="text"
                    placeholder="Enter phone number"
                    id="phoneNumber"
                    value={phoneNumber}
                    required
                    onChange={(e) => handlePhoneNumberChange(e, "phoneNumber")}
                  />
                </FormGroup>

                <FormGroup>
                  <Label for="number">Enter Count</Label>

                  <Input
                    type="number"
                    placeholder="Please enter the count you wish to send email"
                    id="count"
                    value={count}
                    required
                    onChange={(e) => handleCountChange(e, "count")}
                  />
                </FormGroup>

<Container className="text-center">
<Button  color="warning" className="mr-2 mx-2" type="sumbit">

BOMBIT Message
</Button>


<Link to="/"
>
<Button color="info" size="mg" className="mr-2 mx-2">
Back

</Button>

    </Link>

</Container>

<div style={{ marginTop: "20px", textAlign: "center" }}>

{!submitted?(

<h4 style={{ color: "#FF6347", fontWeight: "bold" }}>
Use it for Fun not for Revenge
</h4>

):(
    <h4 style={{ color: "#32CD32", fontWeight: "bold" }}>
   Message Sent: {reqCount}
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
}

export default BombitUpSms;
