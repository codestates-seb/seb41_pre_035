import React from "react";
import "../css/header.css";
import { useNavigate } from "react-router-dom";
import { Link } from "react-router-dom";

const logoiconSize = { fontSize: "30px" };
const logotextSize = { fontSize: "18px" };

const Header = () => {
  const navigate = useNavigate();

  return (
    <>
      <div className="headerContainer">
        <div className="headerLogo">
          <Link to="/" className="logoWrapper">
            <i className="fa-brands fa-stack-overflow" style={logoiconSize}></i>
            <div className="logoText">
              <div className="logoText1" style={logotextSize}>
                stack
              </div>
              <div className="logoText2" style={logotextSize}>
                overflow
              </div>
            </div>
          </Link>
          {/* <Logo text={true} /> */}
        </div>
        <div className="headerMenu">
          <p>About</p>
          <p>Products</p>
          <p>For Teams</p>
        </div>
        <div className="search">
          <input className="headerSearch" type="search" placeholder="Search...." />
        </div>
        <div className="headerLoginSignup">
          <button
            className="headerLogin"
            onClick={() => {
              navigate("/login");
            }}
          >
            Log in
          </button>
          <button
            className="headerSignup"
            onClick={() => {
              navigate("/signup");
            }}
          >
            Sign up
          </button>
        </div>
      </div>
    </>
  );
};

export default Header;
