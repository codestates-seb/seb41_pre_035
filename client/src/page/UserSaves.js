import React from "react";
import MyNav from "../component/mypage/MyNav";
import MyHeader from "../component/mypage/MyHeader";
import "../css/user.css";
import TabSaves from "../component/mypage/TabSaves";

const UserSaves = () => {
  return (
    <div className="userContainer">
      <MyHeader />
      <MyNav />
      <div>
        <TabSaves />
      </div>
    </div>
  );
};

export default UserSaves;
