import Nav from "./Component/Nav";
import Footer from "./Component/Footer";
import Sidebar from "./Component/Sidebar";

import Header from "./Component/Header";
import HeaderLogin from "./Component/HeaderLogin";
import HeaderMenumodal from "./Component/HeaderMenumodal";

// ! 위 코드 수정해주세요
// import Login from "./page/Login";
// import Recovery from "./page/Recovery";
// import SignUp from "./page/SignUp";
// import { Routes, Route } from "react-router-dom";

function App() {
  return (
    <div className="App">
      <Header />
      {/* <HeaderLogin /> */}
      {/* <HeaderMenumodal /> */}

      {/* <Routes>
        <Route path="/login" element={<Login />} />
        <Route path="/recovery" element={<Recovery />} />
        <Route path="/signup" element={<SignUp />} />
      </Routes> */}

      {/* <Nav />
      <Sidebar /> */}
      <Footer />
    </div>
  );
}

export default App;
