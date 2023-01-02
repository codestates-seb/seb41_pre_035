import React from "react";
import "../../css/mypage/myNav.css";
import { Link } from "react-router-dom";

// 해당 유저가 아니면 Nav에서 Profile만 보여야함

const MyNav = () => {
  return (
    <div className="myNav">
      <p>
        <Link to="/user/:userId/profile">Profile</Link>
      </p>
      <p>
        <Link to="/user/:userId/saves">Saves</Link>
      </p>
      <p>
        <Link to="/user/:userId/settings">Settings</Link>
      </p>
    </div>
  );
};

export default MyNav;
