import React from "react";
import MyNav from "../component/mypage/MyNav";
import MyHeader from "../component/mypage/MyHeader";
import "../css/user.css";
import TabSaves from "../component/mypage/TabSaves";
import Nav from "../component/Nav";

const UserSaves = () => {
  return (
    <div className="userContainer">
      <Nav />
      <MyHeader />
      <MyNav />
      <div>
        <TabSaves />
      </div>
    </div>
  );
};

export default UserSaves;
