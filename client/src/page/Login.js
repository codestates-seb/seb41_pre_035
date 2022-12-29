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

const Login = ({ setRefreshToken }) => {
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
      postLoginData();
    }
  };

  // * 서버로 로그인 요청을 보내는 함수
  const postLoginData = () => {
    const loginData = JSON.stringify({
      username: loginEmail,
      password: loginPassword,
    });

    return axios
      .post("/auth/login", loginData, {
        headers: { "Content-Type": "application/json" },
      })
      .then((res) => {
        // 엑세스 토큰은 로컬스토리지에 저장, 리프레시 토큰은 쿠키에 저장
        localStorage.setItem("accessToken", res.headers.authorization);
        setRefreshToken("refreshToken", res.headers.refresh, {
          maxAge: 2592000, //한달
        });
        setUser(res.data);
        setLoginError(false);
        loginEmailReset();
        loginPasswordReset();
        navigate("/");
        console.log("로그인에 성공했습니다.");
        console.log(res);
      })
      .catch((err) => {
        console.log(err.response);
        if (err.response.status === 401) {
          setLoginError(true);
        }
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
