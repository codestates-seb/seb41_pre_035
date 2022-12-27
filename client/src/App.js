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
import { useState } from "react";

function App() {
  const [isLogin, setIsLogin] = useState(false);
  const [userInfo, setUserInfo] = useState("");

  return (
    <div className="App">
      {isLogin ? <HeaderLogin /> : <Header />}
      {/* <HeaderMenumodal/> */}
      <Routes>
        <Route path="/login" element={<Login setIsLogin={setIsLogin} setUserInfo={setUserInfo} />} />
        <Route path="/recovery" element={<Recovery />} />
        <Route path="/signup" element={<SignUp />} />
      </Routes>

      {/* <Nav />
      <Sidebar />
      <Footer /> */}
    </div>
  );
}

export default App;
