import React from "react";
import { Routes, Route, Outlet } from "react-router-dom";
import MyNav from "../component/mypage/MyNav";
import MyHeader from "../component/mypage/MyHeader";
import "../css/user.css";
import TabProfile from "../component/mypage/TabProfile";
import Nav from "../component/Nav";
import Footer from "../component/Footer";

const User = () => {
  return (
    <div className="userContainer">
      <Nav />
      <MyHeader />
      <MyNav />
      <TabProfile />
      <Footer />
    </div>
  );
};

export default User;
