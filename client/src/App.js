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
import QuestionEditPage from "./page/QuestionEditPage";
import AskQuestions from "./page/AskQuestions";
import { QuestionPage } from "./page/QuestionPage";
import QuestionPage2 from "./page/QuestionPage2";
import TagsBoard from "./page/TagsBoard";
import User from "./page/User";
import UserProfile from "./page/UserProfile";
import UserSaves from "./page/UserSaves";
import UserSetting from "./page/UserSetting";

import { useCookies } from "react-cookie";
import Users from "./page/Users";
import TabProfile from "./component/mypage/TabProfile";
import EditProfile from "./component/mypage/EditProfile";
import DeleteProfile from "./component/mypage/DeleteProfile";
import UserSettingEdit from "./page/UserSettingEdit";
import UserSettingDelete from "./page/UserSettingDelete";

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
        <Route path="/questions/:questionId/edit" element={<QuestionEditPage />} />
        <Route path="/login" element={<Login setRefreshToken={setRefreshToken} />} />
        <Route path="/recovery" element={<Recovery />} />
        <Route path="/signup" element={<SignUp />} />
        <Route path="/signupnotice" element={<SignUpNotice />} />
        <Route path="/tags" element={<TagsBoard />} />
        <Route path="/user/:userId" element={<User />} />
        <Route path="/user/:userId/profile" element={<UserProfile />} />
        <Route path="/user/:userId/saves" element={<UserSaves />} />
        <Route path="/user/:userId/settings" element={<UserSetting />} />
        <Route path="/user/:userId/settings/edit" element={<UserSettingEdit />} />
        <Route path="/user/:userId/settings/delete" element={<UserSettingDelete />} />
        <Route path="/users" element={<Users />} />
      </Routes>
    </div>
  );
}

export default App;
