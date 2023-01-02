import React from "react";
import MyNav from "../component/mypage/MyNav";
import MyHeader from "../component/mypage/MyHeader";
import "../css/user.css";
import TabSettings from "../component/mypage/TabSettings";
import DeleteProfile from "../component/mypage/DeleteProfile";

const UserSettingDelete = () => {
  return (
    <div className="userContainer">
      <MyHeader />
      <MyNav />
      <div>
        <TabSettings />
      </div>
      <div>{/* <DeleteProfile /> */}</div>
    </div>
  );
};

export default UserSettingDelete;
