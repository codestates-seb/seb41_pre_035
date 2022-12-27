import { Route, Routes } from "react-router-dom";
import Nav from "./component/Nav";
import Footer from "./component/Footer";
import Sidebar from "./component/Sidebar";

import Header from "./component/Header";
import HeaderLogin from "./component/HeaderLogin";
import HeaderMenumodal from "./component/HeaderMenumodal";

import Login from "./page/Login";
import Recovery from "./page/Recovery";
import SignUp from "./page/SignUp";

import { Routes, Route } from "react-router-dom";
import { useEffect, useState } from "react";
import { useRecoilValue } from "recoil";
import { userState } from "./recoil";

import Questions from "./page/Questions";
import AskQuestions from "./page/AskQuestions";


function App() {
  const user = useRecoilValue(userState);

  return (
    <div className="App">

      {user ? <HeaderLogin /> : <Header />}
      {/* <HeaderMenumodal /> */}

      <Routes>
        <Route path="/" element={<Questions />} />
        <Route path="/questions" element={<AskQuestions />} />
        <Route path="/login" element={<Login />} />
        <Route path="/recovery" element={<Recovery />} />
        <Route path="/signup" element={<SignUp />} />
      </Routes>
      <Nav />
      <Sidebar />
      <Footer />
    </div>
  );
}

export default App;
