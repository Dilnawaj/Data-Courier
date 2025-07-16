import { BrowserRouter, Route, Routes } from "react-router-dom";

import About from "./pages/About";

import "react-toastify/dist/ReactToastify.css";
import { ToastContainer } from "react-toastify";
import "./App.css";
import FileUpload from "./pages/FileUpload";
import Unsubscribe from "./pages/Unsubscribe";
import BombitUp from "./pages/BombitUp";
import BombitUpSms from "./pages/BombitUpSms";

function App() {
  return (
    <BrowserRouter>
      <ToastContainer position="top-center" />
      <Routes>
        <Route path="/about" element={<About />} />
        <Route path="/unsubscribe" element={<Unsubscribe />} />
        <Route path="/" element={<FileUpload />} />
        <Route path="/bombitup" element={<BombitUp />} />
        <Route path="/bombitupSms" element={<BombitUpSms />} />
      </Routes>
    </BrowserRouter>
  );
}

export default App;
