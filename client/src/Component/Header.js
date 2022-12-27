import React from "react";
import "../css/header.css";
import { useNavigate } from "react-router-dom";
import { Link } from "react-router-dom";
import Logo from "./Logo";

const Header = () => {
  const navigate = useNavigate();

  return (
    <>
      <header>
        <div className="headerLogo">
          <Link to="/" className="logoWrapper">
            <Logo text={true} size={true} />
          </Link>
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
      </header>
    </>
  );
};

export default Header;
