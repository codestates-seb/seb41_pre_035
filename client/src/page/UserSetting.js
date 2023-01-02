import React from "react";
import MyNav from "../component/mypage/MyNav";
import MyHeader from "../component/mypage/MyHeader";
import "../css/user.css";
import TabSettings from "../component/mypage/TabSettings";

const UserSetting = () => {
  return (
    <div className="userContainer">
      <MyHeader />
      <MyNav />
      <div>
        <TabSettings />
      </div>
    </div>
  );
};

export default UserSetting;
