import React from "react";
import MyNav from "../component/mypage/MyNav";
import MyHeader from "../component/mypage/MyHeader";
import "../css/user.css";
import TabSettings from "../component/mypage/TabSettings";
import EditProfile from "../component/mypage/EditProfile";

const UserSettingEdit = () => {
  return (
    <div className="userContainer">
      <MyHeader />
      <MyNav />
      <div>
        <TabSettings />
      </div>
      <div>{/* <EditProfile /> */}</div>
    </div>
  );
};

export default UserSettingEdit;
