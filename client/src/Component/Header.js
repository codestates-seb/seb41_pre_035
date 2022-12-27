import React from "react";
import "../css/header.css";
import { useNavigate } from "react-router-dom";
import { Link } from "react-router-dom";
import Logo from "./Logo";

const Header = () => {
  const navigate = useNavigate();

  return (
    <>
      <div className="headerContainer">
        <div className="headerLogo">
          {/* Logo 컴포넌트에 홈으로 리다이렉션이 되도록 만들어놨습니다. 해당 주석 확인 후 작업하실 때 삭제해주세요 */}
          <Logo text={true} size={false} />
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
