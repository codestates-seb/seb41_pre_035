import React, { useState } from "react";
import "../css/headerMenumodal.css";
import { useNavigate } from "react-router-dom";
import { Link } from "react-router-dom";
import Logo from "./Logo";

const HeaderMenumodal = () => {
  const [menumodal, setMenumodal] = useState(false);
  const navigate = useNavigate();

  return (
    <>
      <header>
        <div>
          <p
            onClick={() => {
              setMenumodal(!menumodal);
            }}
          >
            <i className="fa-solid fa-bars"></i>
          </p>
        </div>
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
        {menumodal ? (
          <nav className="menumodal">
            <div className="navMenu">
              <ul>
                <li className="navMenuHover">
                  <Link to="/">Home　　　　　　　　　</Link>
                </li>
                <li>PUBLIC</li>
                <ul className="navSubMenu">
                  <li className="navMenuHover">
                    <Link to="/questions">
                      <i className="fa-solid fa-earth-americas"></i> Questions　　　　　
                    </Link>
                  </li>
                  <li className="navMenuHover">
                    <Link to="/tags">　　Tags　　　　　　　</Link>
                  </li>
                  <li className="navMenuHover">
                    <Link to="/users">　　Users　　　　　　　</Link>
                  </li>
                  <li className="navMenuHover">　　Companies</li>
                </ul>
                <li>COLLECTIVES</li>
                <ul className="navSubMenu">
                  <li>Explore Collectives</li>
                </ul>
                <li>TEAMS</li>
                <ul className="navSubMenu">
                  <li>Create free Team</li>
                </ul>
              </ul>
            </div>
          </nav>
        ) : null}
      </header>
    </>
  );
};

export default HeaderMenumodal;
