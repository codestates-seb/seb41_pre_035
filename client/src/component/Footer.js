import React from "react";
import "../css/footer.css";
import Logo from "./Logo";

const Footer = () => {
  return (
    <>
      <footer>
        <div className="footerMenus">
          <div className="footerLogo">
            <Logo text={false} size={true} />
          </div>
          <div className="footerMenu">
            <h2>STACK OVERFLOW</h2>
            <p>Questions</p>
            <p>Help</p>
          </div>
          <div className="footerMenu">
            <h2>PRODUCTS</h2>
            <p>Teams</p>
            <p>Advertising</p>
          </div>
          <div className="footerMenu">
            <h2>COMPANY</h2>
            <p>About</p>
            <p>Press</p>
          </div>
          <div className="footerMenu">
            <h2>STACK EXCHANGE NETWORK</h2>
            <p>Technology</p>
            <p>Culture & recreation</p>
          </div>
        </div>
        <div className="footerLicense">Site design / logo © 2022 Stack Exchange Inc; user contributions licensed under CC BY-SA. rev 2022.12.19.43125</div>
      </footer>
    </>
  );
};

export default Footer;
