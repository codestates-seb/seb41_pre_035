import React from "react";
import MyNav from "../component/mypage/MyNav";
import MyHeader from "../component/mypage/MyHeader";
import "../css/user.css";
import TabSettings from "../component/mypage/TabSettings";
import Nav from "../component/Nav";

const UserSetting = () => {
  return (
    <div className="userContainer">
      <Nav />
      <MyHeader />
      <MyNav />
      <div>
        <TabSettings />
      </div>
    </div>
  );
};

export default UserSetting;
