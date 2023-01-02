// * component
import Header from "./component/Header";
import Nav from "./component/Nav";
import TabProfile from "./component/mypage/TabProfile";
import EditProfile from "./component/mypage/EditProfile";
import DeleteProfile from "./component/mypage/DeleteProfile";
// import HeaderMenumodal from "./component/HeaderMenumodal";

// * page
import Login from "./page/Login";
import Recovery from "./page/Recovery";
import SignUp from "./page/SignUp";
import SignUpNotice from "./page/SignUpNotice";
import Questions from "./page/Questions";
import QuestionEditPage from "./page/QuestionEditPage";
import AskQuestions from "./page/AskQuestions";
import { QuestionPage } from "./page/QuestionPage";
import QuestionPage2 from "./page/QuestionPage2";
import User from "./page/User";
import Users from "./page/Users";
import UserProfile from "./page/UserProfile";
import UserSaves from "./page/UserSaves";
import UserSetting from "./page/UserSetting";
import UserSettingEdit from "./page/UserSettingEdit";
import UserSettingDelete from "./page/UserSettingDelete";
import TagsBoard from "./page/TagsBoard";

// * module
import { Route, Routes } from "react-router-dom";
import { useRecoilValue } from "recoil";
import { userState } from "./recoil";
import { useCookies } from "react-cookie";

function App() {
  const [refreshToken, setRefreshToken, removeRefreshToken] = useCookies(["refreshToken"]);
  const user = useRecoilValue(userState);

  return (
    <div className="App">
      <Header />
      {/* <HeaderMenumodal /> */}
      {user ? <Nav /> : null}
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
