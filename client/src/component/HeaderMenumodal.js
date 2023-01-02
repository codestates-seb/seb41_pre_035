import React, { useState } from "react";
import "../css/header.css";
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
          <Logo text={true} size={true} />
        </div>

        <div className="headerMenu headerMenuM">
          <a href="https://stackoverflow.co/" target="__blank" className="headerMenuText">
            About
          </a>
          <p className="headerMenuText">Products</p>
          <a href="https://stackoverflow.co/teams/" target="__blank" className="headerMenuText">
            For Teams
          </a>
        </div>

        <form className="headerSearch">
          <input className="headerSearchInput" type="search" placeholder="Search...." />
        </form>

        <div className="headerBtns">
          <button
            className="headerLoginBtn"
            onClick={() => {
              navigate("/login");
            }}
          >
            Log in
          </button>
          <button
            className="headerSignupBtn"
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
