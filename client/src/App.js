import { Route, Routes, useParams } from "react-router-dom";
import Header from "./component/Header";
import Nav from "./component/Nav";
import HeaderMenumodal from "./component/HeaderMenumodal";
import Login from "./page/Login";
import Recovery from "./page/Recovery";
import SignUp from "./page/SignUp";
import SignUpNotice from "./page/SignUpNotice";

import { useEffect, useState } from "react";
import { useRecoilValue } from "recoil";
import { userState } from "./recoil";
import Questions from "./page/Questions";
import AskQuestions from "./page/AskQuestions";
import QuestionPage from "./page/QuestionPage";
import TagsBoard from "./page/TagsBoard";
import { useCookies } from "react-cookie";

function App() {
  const [refreshToken, setRefreshToken, removeRefreshToken] = useCookies(["refreshToken"]);

  return (
    <div className="App">
      <Header />
      {/* <HeaderMenumodal /> */}
      <Nav />
      <Routes>
        <Route path="/" element={<Questions />} />
        <Route path="/questions" element={<Questions />} />
        <Route path="/askquestions" element={<AskQuestions />} />
        <Route path="/questions/:questionId" element={<QuestionPage />} />
        <Route path="/login" element={<Login setRefreshToken={setRefreshToken} />} />
        <Route path="/recovery" element={<Recovery />} />
        <Route path="/signup" element={<SignUp />} />
        <Route path="/signupnotice" element={<SignUpNotice />} />
        <Route path="/tags" element={<TagsBoard />} />
      </Routes>
    </div>
  );
}

export default App;
