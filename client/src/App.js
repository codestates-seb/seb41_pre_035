// import Header from "./Components/Header";
import Nav from "./Components/Nav";
import Footer from "./Components/Footer";
import Sidebar from "./Components/Sidebar";

import Header from "./Components/Header";
import HeaderLogin from "./Components/HeaderLogin";
import HeaderMenumodal from "./Components/HeaderMenumodal";

// ! 위 코드 수정해주세요
import Login from "./page/Login";
import Recovery from "./page/Recovery";
import SignUp from "./page/SignUp";
import { Routes, Route } from "react-router-dom";

function App() {
  return (
    <div className="App">
      <Header />
      {/* <HeaderLogin/> */}
      {/* <HeaderMenumodal/> */}
      <Routes>
        <Route path="/login" element={<Login />} />
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
