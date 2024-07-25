import React, { useState } from "react";
import { NavLink as ReactLink } from "react-router-dom";
import {
  Collapse,
  Navbar,
  NavbarToggler,
  NavbarBrand,
  Nav,
  NavItem,
  NavLink,
} from "reactstrap";

function CustomNavbar() {
  const [isOpen, setIsOpen] = useState(false);
  const [isBombitUpHovered, setBombitUpHovered] = useState(false);
  const [isBombitUpSmsHovered, setBombitUpSmsHovered] = useState(false);
  const [isAboutHovered, setIsAboutHovered] = useState(false);

  const toggleNavbar = () => setIsOpen(!isOpen);
  const handleBombitUpMouseEnter = () => setBombitUpHovered(true);
  const handleBombitUpMouseLeave = () => setBombitUpHovered(false);
  const handleAboutMouseEnter = () => setIsAboutHovered(true);
  const handleAboutMouseLeave = () => setIsAboutHovered(false);
const handleBombitUpSmsMouseEnter =()=> setBombitUpSmsHovered(true);
const handleBombitUpSmsMouseLeave =()=>setBombitUpSmsHovered(false);
  return (
    <div>
      <Navbar style={{ backgroundColor: "black" }} light expand="md">
        <NavbarBrand
          style={{
            color: "#e6e6e6",
            fontWeight: "bold",
            fontFamily: "Montserrat, sans-serif",
            textAlign: "center",
            width: "100%",
          }}
        >
          Data Courier
        </NavbarBrand>
        
        <NavbarToggler onClick={toggleNavbar} />
        <Collapse isOpen={isOpen} navbar>
          <Nav className="mr-auto" navbar>
            <NavItem>
              <NavLink
                tag={ReactLink}
                to="/bombitup"
                onMouseEnter={handleBombitUpMouseEnter}
                onMouseLeave={handleBombitUpMouseLeave}
                style={{
                  color: "#e6e6e6",
                  fontWeight: "bold",
                  fontFamily: "Montserrat, sans-serif",
                  border: "1px solid #e6e6e6",
                  borderRadius: "5px",
                  padding: "8px 12px",
                  transition: "all 0.2s ease-in-out",
                  backgroundColor: isBombitUpHovered ? "#e6e6e6" : "transparent",
                  marginRight: "10px",
                }}
              >
                BombitUpEmail
              </NavLink>
            </NavItem>
<NavItem>
<NavLink
tag={ReactLink}
to="/bombitupSms"
onMouseEnter={handleBombitUpSmsMouseEnter}
onMouseLeave={handleBombitUpSmsMouseLeave}
style={
{
  color: "#e6e6e6",
  fontWeight:"bold",
  fontFamily: "Montserrat, sans-serif",
  border: "1px solid #e6e6e6",
  borderRadius: "5px",
  padding: "8px 12px",
  transition: "all 0.2s ease-in-out",


}

}

>
  BombitUpSms
</NavLink>


</NavItem>





            
            <NavItem>
              <NavLink
                tag={ReactLink}
                to="/about"
                onMouseEnter={handleAboutMouseEnter}
                onMouseLeave={handleAboutMouseLeave}
                style={{
                  color: "#e6e6e6",
                  fontWeight: "bold",
                  fontFamily: "Montserrat, sans-serif",
                  border: "1px solid #e6e6e6",
                  borderRadius: "5px",
                  padding: "8px 12px",
                  transition: "all 0.2s ease-in-out",
                  backgroundColor: isAboutHovered ? "#e6e6e6" : "transparent",
                  marginLeft: "10px"
                }}
              >
                About
              </NavLink>
            </NavItem>
       
          </Nav>
        </Collapse>
      </Navbar>
    </div>
  );
}

export default CustomNavbar;
