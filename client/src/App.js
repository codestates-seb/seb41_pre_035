import { Route, Routes, useParams } from "react-router-dom";

import Header from "./component/Header";
import HeaderMenumodal from "./component/HeaderMenumodal";
import Login from "./page/Login";
import Recovery from "./page/Recovery";
import SignUp from "./page/SignUp";
import SignUpNotice from "./page/SignUpNotice";
import Questions from "./page/Questions";
import AskQuestions from "./page/AskQuestions";
import QuestionPage from "./page/QuestionPage";
import Main from "./page/Main";
import TagsBoard from "./page/TagsBoard";
import { useCookies } from "react-cookie";
import { useRecoilValue } from "recoil";
import { userState } from "./recoil";

function App() {
  const [refreshToken, setRefreshToken, removeRefreshToken] = useCookies(["refreshToken"]);
  const user = useRecoilValue(userState);

  return (
    <div className="App">
      <Header removeRefreshToken={removeRefreshToken} />
      {/* <HeaderMenumodal /> */}

      <Routes>
        <Route path="/" element={<Main />} />
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
