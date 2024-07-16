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

const FileUpload = () => {
  const [selectedFiles, setSelectedFiles] = useState([]);
  const [email, setEmail] = useState("");
  const [fileInputKey, setFileInputKey] = useState(Date.now()); // Key state for file input
  const baseUrl = process.env.REACT_APP_API_KEY;

  const resetForm = () => {
    setEmail("");
    setSelectedFiles([]);
    setFileInputKey(Date.now()); // Update the key to reset the file input
  };

  const handleChange = (e, tag) => {
    const value = e.target.value;
    setEmail(value);
    console.log("handleCHangeChange", value);
  };

  const handleFileChange = (e) => {
    const files = Array.from(e.target.files);
    const newSelectedFiles = [];

    files.forEach((file) => {
      newSelectedFiles.push(file);
    });

    setSelectedFiles((prevFiles) => [...prevFiles, ...newSelectedFiles]);
  };

  const handleSubmit = async (e) => {
    e.preventDefault();

    try {
      const readFileData = (file) => {
        return new Promise((resolve, reject) => {
          const reader = new FileReader();
          reader.onloadend = () => resolve(reader.result);
          reader.onerror = (error) => {
            console.error("FileReader error:", error);
            reject(error);
          };
          reader.readAsDataURL(file);
        });
      };

      if (selectedFiles.length > 0) {
        const fileArray = await Promise.all(
          selectedFiles.map(async (file) => ({
            fileName: file.name,
            fileData: await readFileData(file),
          }))
        );

        console.log("Uploading files:", fileArray);

        try {
          const response = await axios.post(
            `${baseUrl}sendemail?email=${email}`,
            {
              files: fileArray,
            }
          );
          console.log("Response:", response);
          toast.success("Files uploaded successfully!");
          resetForm();
        } catch (error) {
          console.error("Error uploading files:", error);
          toast.error("Failed to upload files.");
        }
      }
    } catch (error) {
      console.error("Error in handleSubmit:", error);
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
              height: "350px",
              display: "flex",
              justifyContent: "center",
            }}
          >
            <CardHeader style={{ textAlign: "center" }}>
              <h3>File Upload</h3>
            </CardHeader>
            <CardBody>
              <Form onSubmit={handleSubmit}>
                <FormGroup>
                  <Label for="email">Enter Email</Label>
                  <Input
                    type="email"
                    placeholder="Enter Email here"
                    id="email"
                    value={email}
                    required
                    onChange={(e) => handleChange(e, "email")}
                  />
                </FormGroup>
                <FormGroup>
                  <Label for="fileInput">Upload Files</Label>
                  <Input
                    type="file"
                    id="fileInput"
                    multiple
                    key={fileInputKey} // Add key attribute
                    onChange={handleFileChange}
                    accept=".doc,.pdf,.docx,.txt,.zip,.rtf"
                  />
                </FormGroup>

                <Container className="text-center">
                  <Button color="warning" className="mr-2 mx-2" type="submit">
                    Upload
                  </Button>
                </Container>
              </Form>
            </CardBody>
          </Card>
        </Container>
      </div>
    </Base>
  );
};

export default FileUpload;
