import React from "react";
import EditProfile from "./EditProfile";
import DeleteProfile from "./DeleteProfile";
import "../../css/mypage/tabSettings.css";
import { Routes, Route, Link, Outlet } from "react-router-dom";

const TabSettings = () => {
  return (
    <div className="tabSettingsContainer">
      <div className="tabSettingsLeft">
        <ul>
          <h2>PERSONAL INFORMATION</h2>
          <li>
            <Link to="/user/:userId/settings/edit">Edit profile　　　　　　　　　　</Link>
          </li>
          <li>
            <Link to="/user/:userId/settings/delete">Delete profile　　　　　　　　　</Link>
          </li>
        </ul>
      </div>
      <div className="tabSettingsRight">
        <EditProfile />
        <DeleteProfile />
      </div>
    </div>
  );
};

export default TabSettings;
