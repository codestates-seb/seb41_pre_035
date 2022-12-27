import React, { useState } from "react";
import "../css/headerLogin.css";
import { useNavigate } from "react-router-dom";
import Logo from "./Logo";

const HeaderLogin = () => {
  const [logoutmodal] = useState(false);
  const navigate = useNavigate();

  return (
    <>
      <div className="headerContainer">
        {/* 로고 컴포넌트 안쓰신거 같아서 제가 가져왔어요 확인 후 삭제해주세요 */}
        <Logo text={true} size={false} />
        <div className="headerProducts">products</div>
        <div className="search">
          <input className="headerSearch" type="search" placeholder="Search...." />
        </div>
        <div className="headerLink">
          <div className="headerUserProfile">
            <img src="img/user.png" alt="" />
            <p>1</p>
          </div>
          <div className="headerUserInfo">
            <i className="fa-solid fa-inbox"></i>
            <i className="fa-solid fa-trophy"></i>
            <i className="fa-solid fa-circle-question"></i>
            <i className="fa-solid fa-snowflake"></i>
            <i className="fa-solid fa-right-from-bracket" onClick={() => navigate("/11")}></i>
          </div>
        </div>
        {logoutmodal ? <div className="logoutmodal">log out</div> : null}
      </div>
    </>
  );
};

export default HeaderLogin;
