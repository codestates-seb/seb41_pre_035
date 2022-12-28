import { Route, Routes, useParams } from "react-router-dom";
import Nav from "./component/Nav";
import Footer from "./component/Footer";
import Sidebar from "./component/Sidebar";
import Header from "./component/Header";
import HeaderLogin from "./component/HeaderLogin";
import HeaderMenumodal from "./component/HeaderMenumodal";
import Login from "./page/Login";
import Recovery from "./page/Recovery";
import Signup from "./page/SignUp";
import SignupSuccess from "./page/SignupSuccess";

import { useEffect, useState } from "react";
import { useRecoilValue } from "recoil";
import { userState } from "./recoil";
import Questions from "./page/Questions";
import AskQuestions from "./page/AskQuestions";
import QuestionPage from "./page/QuestionPage";

function App() {
  const user = useRecoilValue(userState);

  return (
    <div className="App">
      {user ? <HeaderLogin /> : <Header />}
      {/* <HeaderMenumodal/> */}

      <Routes>
        <Route path="/" element={<Questions />} />
        <Route path="/questions" element={<AskQuestions />} />
        <Route path="/questions/:questionId" element={<QuestionPage />} />
        <Route path="/login" element={<Login />} />
        <Route path="/recovery" element={<Recovery />} />
        <Route path="/signup" element={<Signup />} />
        <Route path="/signupsuccess" element={<SignupSuccess />} />
      </Routes>

      {/* <Nav />

      <Sidebar />
      <Footer /> */}
    </div>
  );
}

export default App;
