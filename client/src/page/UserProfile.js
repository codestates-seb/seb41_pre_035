import React from "react";
import MyNav from "../component/mypage/MyNav";
import MyHeader from "../component/mypage/MyHeader";
import "../css/user.css";
import TabProfile from "../component/mypage/TabProfile";

const UserProfile = () => {
  return (
    <div className="userContainer">
      <MyHeader />
      <MyNav />
      <div>
        <TabProfile />
      </div>
    </div>
  );
};

export default UserProfile;
