import "../css/login.css";
import SocialBtn from "../component/SocialBtn";
import Logo from "../component/Logo";
import { InputEmail, InputPw, InputBtn } from "../component/Form";
import { useInput } from "../util/useInput";
import { emailValidator } from "../component/validator";
import { useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import axios from "axios";

// recoil
import { useSetRecoilState } from "recoil";
import { userState } from "../recoil";

const Login = () => {
  const navigate = useNavigate();

  // * recoil 아톰 변경
  const setUser = useSetRecoilState(userState);

  // * input 관련 상태
  const [loginEmail, loginEmailBind, loginEmailReset] = useInput("");
  const [loginPassword, loginPasswordBind, loginPasswordReset] = useInput("");

  // * 에러 관련 상태
  const [loginEmailError, setloginEmailError] = useState(false);
  const [loginValidError, setLoginValidError] = useState(false);
  const [loginPasswordError, setloginPasswordError] = useState(false);
  const [loginError, setLoginError] = useState(false);

  // * 입력되었는지 + 유효성 검사하는 함수
  // ! 로그인시에도 비밀번호 유효성 검사가 필요한가?
  const checkEmail = () => {
    if (loginEmail === "") {
      setloginEmailError(true); // 에러가 있음으로 바꿈
      return false;
    } else {
      setloginEmailError(false); // 에러가 없음으로 바꿈
      return true;
    }
  };
  const checkEmailValid = () => {
    const result = emailValidator(loginEmail);

    if (!result) {
      setLoginValidError(true); // 에러가 있음으로 바꿈
      return false;
    } else {
      setLoginValidError(false); // 에러가 없음으로 바꿈
      return true;
    }
  };
  const checkPassword = () => {
    if (loginPassword === "") {
      setloginPasswordError(true); // 에러가 있음으로 바꿈
      return false;
    } else {
      setloginPasswordError(false); // 에러가 없음으로 바꿈
      return true;
    }
  };

  // * 버튼을 클릭하면 실행되는 이벤트 핸들러 (유효성 검사가 통과되면 서버에 요청을 보내는 함수가 실행됨)
  const handleLoginBtn = (e) => {
    e.preventDefault();

    checkEmail();
    checkEmailValid();
    checkPassword();

    if (checkEmail() && checkEmailValid() && checkPassword()) {
      console.log("서버에 데이터를 보내세요!");
      postLoginData();
    }
  };

  // ! 서버로 요청을 보내는 함수 완성해야 됨
  const postLoginData = () => {
    const loginData = JSON.stringify({
      username: loginEmail,
      password: loginPassword,
    });

    return axios
      .post("https://6844-121-66-180-162.jp.ngrok.io/", loginData)
      .then((res) => res)
      .then((res) => {
        // ! 응답 오는 형식 보고 반영할 것
        if (res.headers.authorization) {
          localStorage.setItem("accessToken", res.headers.authorization);
          localStorage.setItem("refreshToken", res.headers.refresh);
        }
      })
      .then((res) => {
        setUser(JSON.parse(res.body));
        setLoginError(false);
        loginEmailReset(); // email 상태를 원래 상태로 비워줌
        loginPasswordReset(); // password 상태를 원래 상태로 비워줌
        navigate("/");
      })
      .catch((err) => {
        setLoginError(true);
        console.log(err);
        console.log("로그인에 실패했습니다.");
      });
  };

  return (
    <div className="loginWrapper">
      <div className="inputReuse">
        <Logo text={false} size={true} />
        <SocialBtn text={"log in"} />
      </div>

      <form className="loginForm">
        <div>
          <InputEmail value={loginEmailBind} classname={loginEmailError || loginValidError || loginError ? "errorInput" : "inputEmailInput"} />
          {loginEmailError ? <div className="errorMessage">Email을 입력해주세요.</div> : null}
          {loginValidError && !loginEmailError ? <div className="errorMessage">Email 형식으로 입력해주세요.</div> : null}
          {loginError ? <div className="errorMessage">email이나 password를 확인해주세요.</div> : null}

          <InputPw value={loginPasswordBind} classname={loginPasswordError ? "errorInput" : "inputPwInput"} type={"need"} />
          {loginPasswordError ? <div className="errorMessage">Password를 입력해주세요.</div> : null}

          <InputBtn text={"Log in"} onclick={handleLoginBtn} />
        </div>
      </form>

      <div className="loginSignup">
        <div>Don't have an account?</div>
        <Link to="/signup" className="loginSingupLink">
          Sign up
        </Link>
      </div>
    </div>
  );
};

export default Login;
