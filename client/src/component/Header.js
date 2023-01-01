import React from "react";
import { useNavigate } from "react-router-dom";
import { useState } from "react";
import { useRecoilValue } from "recoil";
import { userState } from "../recoil";
import LogoutModal from "./LogoutModal";
import Logo from "./Logo";
import "../css/header.css";
import { Link } from "react-router-dom";

const Header = ({ refreshToken, removeRefreshToken }) => {
  const navigate = useNavigate();
  const user = useRecoilValue(userState);
  const [on, setOn] = useState(false);

  return (
    <header>
      <div className="headerLogo">
        <Logo text={true} size={false} />
      </div>

      {/* 유저 정보가 있다면 products만 / 유저 정보가 없다면 여러개 있던 것으로 */}
      {user ? (
        <div className="headerMenu headerMenuS">
          <div className="headerMenuText">products</div>
        </div>
      ) : (
        <div className="headerMenu headerMenuM">
          <a href="https://stackoverflow.co/" target="__blank" className="headerMenuText">
            About
          </a>
          <p className="headerMenuText">Products</p>
          <a href="https://stackoverflow.co/teams/" target="__blank" className="headerMenuText">
            For Teams
          </a>
        </div>
      )}

      <form className="headerSearch">
        <input className="headerSearchInput" type="search" placeholder="Search...." />
      </form>

      {/* 유저 정보가 있다면 아이콘 메뉴로 / 유저 정보가 없다면 로그인 회원가입 버튼으로 */}
      {user ? (
        <div className="headerLink">
          <Link to="/user/:userId">
            <div className="headerUserProfile">
              <img src={process.env.PUBLIC_URL + "/img/user.png"} alt="" />
              <p>1</p>
            </div>
          </Link>
          <div className="headerUserInfo">
            <i className="fa-solid fa-inbox"></i>
            <i className="fa-solid fa-trophy"></i>
            <i className="fa-solid fa-circle-question"></i>
            <i className="fa-solid fa-snowflake"></i>
            <i className="fa-solid fa-right-from-bracket" onClick={() => setOn(!on)}></i>
            {on ? <LogoutModal /> : null}
          </div>
        </div>
      ) : (
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
      )}
    </header>
  );
};

export default Header;
