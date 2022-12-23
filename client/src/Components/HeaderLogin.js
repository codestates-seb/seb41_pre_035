import React, { useState } from "react";
import "./style.css";
import { useNavigate } from "react-router-dom";

const HeaderLogin = () => {
  const [seachmodal, setSeachmodal] = useState(false);
  const [logoutmodal, setLogoutmodal] = useState(false);
  let navigate = useNavigate();

  return (
    <>
      <div className="headerContainer">
        <div className="logo">stackoverflow</div>
        <div className="products">products</div>
        <div className="search">
          <input type="search" placeholder="Search...." onFocus={() => setSeachmodal(!seachmodal)} />
        </div>
        <div className="link">
          <div className="userProfile">
            <img src="img/user.png" alt="" />
            <p>1</p>
          </div>
          <div className="userInfo">
            <i className="fa-solid fa-inbox"></i>
            <i className="fa-solid fa-trophy"></i>
            <i className="fa-solid fa-circle-question"></i>
            <i className="fa-solid fa-snowflake"></i>
            <i className="fa-solid fa-right-from-bracket" onClick={() => navigate("/11")}></i>

            {/* <FontAwesomeIcon
              className="icon"
              icon={faRightFromBracket}
              onClick={() => {
                navigate("/11");
              }}
            /> */}
          </div>
        </div>
        {seachmodal ? <div className="seachmodal">SEACH</div> : null}
        {logoutmodal ? <div className="logoutmodal">log out</div> : null}
      </div>
    </>
  );
};

export default HeaderLogin;
