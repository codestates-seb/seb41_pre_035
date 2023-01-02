import React from "react";
import MyNav from "../component/mypage/MyNav";
import MyHeader from "../component/mypage/MyHeader";
import "../css/user.css";
import TabProfile from "../component/mypage/TabProfile";
import Nav from "../component/Nav";

const UserProfile = () => {
  return (
    <div className="userContainer">
      <Nav />
      <MyHeader />
      <MyNav />
      <div>
        <TabProfile />
      </div>
    </div>
  );
};

export default UserProfile;
