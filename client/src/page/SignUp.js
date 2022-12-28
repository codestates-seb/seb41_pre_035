import "../css/signup.css";
import SocialBtn from "../component/SocialBtn";
import SignUpModal from "../component/SignUpModal";
import { InputNick, InputEmail, InputPw, InputBtn } from "../component/Form";
import { nickValidator, passwordValidator, emailValidator } from "../component/validator";
import { useInput } from "../util/useInput";
import { useState } from "react";
import { useNavigate } from "react-router-dom";
import axios from "axios";

// recoil
import { useSetRecoilState } from "recoil";
import { emailState } from "../recoil";

const SignUp = () => {
  const navigate = useNavigate();

  // recoil
  const setEmail = useSetRecoilState(emailState);

  // * input 관련 상태 + 메일 수신 여부
  const [nick, nickBind, nickReset] = useInput("");
  const [email, emailBind, emailReset] = useInput("");
  const [password, passwordBind, passwordReset] = useInput("");
  const [newsletter, setNewsletter] = useState(false);

  // * 에러 관련 상태
  const [nickError, setNickError] = useState(false);
  const [nickValid, setNickValid] = useState(false);
  const [emailError, setEmailError] = useState(false);
  const [emailValid, setEmailValid] = useState(false);
  const [passwordError, setPasswordError] = useState(false);
  const [passwordValid, setPasswordValid] = useState(false);

  // * 입력되었는지 + 유효성 검사하는 함수
  const checkNick = () => {
    if (nick === "") {
      setNickError(true); // 에러가 있음으로 바꿈
      return false;
    } else {
      setNickError(false); // 에러가 없음으로 바꿈
      return true;
    }
  };
  const checkNickValid = () => {
    const result = nickValidator(nick);

    if (!result) {
      setNickValid(true); // 에러가 있음으로 바꿈
      return false;
    } else {
      setNickValid(false); // 에러가 없음으로 바꿈
      return true;
    }
  };
  const checkEmail = () => {
    if (email === "") {
      setEmailError(true); // 에러가 있음으로 바꿈
      return false;
    } else {
      setEmailError(false); // 에러가 없음으로 바꿈
      return true;
    }
  };
  const checkEmailValid = () => {
    const result = emailValidator(email);

    if (!result) {
      setEmailValid(true); // 에러가 있음으로 바꿈
      return false;
    } else {
      setEmailValid(false); // 에러가 없음으로 바꿈
      return true;
    }
  };
  const checkPassword = () => {
    if (password === "") {
      setPasswordError(true); // 에러가 있음으로 바꿈
      return false;
    } else {
      setPasswordError(false); // 에러가 없음으로 바꿈
      return true;
    }
  };
  const checkPasswordValid = () => {
    const result = passwordValidator(password);

    if (!result) {
      setPasswordValid(true); // 에러가 있음으로 바꿈
      return false;
    } else {
      setPasswordValid(false); // 에러가 없음으로 바꿈
      return true;
    }
  };

  // * 버튼을 클릭하면 실행되는 이벤트 핸들러
  // ! 버튼 클릭시 서버로 요청이 가도록 해야 함
  const handleSumbitBtn = (e) => {
    e.preventDefault();

    checkNick();
    checkNickValid();
    checkEmail();
    checkEmailValid();
    checkPassword();
    checkPasswordValid();

    if (checkNick() && checkNickValid() && checkEmail() && checkEmailValid() && checkPassword() && checkPasswordValid()) {
      // console.log("서버에 데이터를 보내세요!");
      postSignupData();
    }
  };

  // ! 서버로 요청을 보내는 함수 완성해야 됨
  const postSignupData = () => {
    const signupData = JSON.stringify({
      email: email,
      password: password,
      name: nick,
    });

    return axios
      .post("/members", signupData, {
        headers: { "Content-Type": "application/json" },
      })
      .then((res) => {
        if (res.status === 201) {
          nickReset();
          emailReset();
          passwordReset();
          setEmail(email);
          navigate("/signupsuccess");
        }
      })
      .catch((err) => {
        if (err.response.status === 409) {
          alert("이미 등록된 계정입니다.");
          nickReset();
          emailReset();
          passwordReset();
        }
        console.log(err);
        console.log("회원가입에 실패했습니다.");
      });
  };

  return (
    <div className="signUpWraapper">
      <div className="signUpLeft">
        <div className="signUpLeftTitle">Join the Stack Overflow community</div>
        <div className="signUpLeftContainer">
          <div className="signUpLeftContainerContent">
            <i className="fa-solid fa-message"></i>
            <div>Get unstuck — ask a question</div>
          </div>
          <div className="signUpLeftContainerContent">
            <i className="fa-solid fa-check-to-slot"></i>
            <div>Unlock new privileges like voting and commenting</div>
          </div>
          <div className="signUpLeftContainerContent">
            <i className="fa-solid fa-tags"></i>
            <div>Save your favorite tags, filters, and jobs</div>
          </div>
          <div className="signUpLeftContainerContent">
            <i className="fa-solid fa-trophy"></i>
            <div>Earn reputation and badges</div>
          </div>
        </div>
        <div className="signUpLeftLink">
          <div>Collaborate and share knowledge with a private group for FREE.</div>
          <a href="https://stackoverflow.co/teams/?utm_source=so-owned&utm_medium=product&utm_campaign=free-50&utm_content=public-sign-up" target="__blank">
            Get Stack Overflow for Teams free for up to 50 users.
          </a>
        </div>
      </div>

      <div className="signUpRight">
        <SocialBtn text={"Sign up"} className="loginsocial" />

        <div className="signUpRightWrapper">
          <form className="signUpForm">
            <InputNick value={nickBind} classname={nickError || nickValid ? "errorInput" : "inputNickInput"} />
            {nickError ? <div className="errorMessage">Display name을 입력해주세요.</div> : null}
            {nickValid && !nickError ? <div className="errorMessage">Display name은 영문과 숫자만 가능합니다.</div> : null}

            <InputEmail value={emailBind} classname={emailError || emailValid ? "errorInput" : "inputEmailInput"} />
            {emailError ? <div className="errorMessage">Email을 입력해주세요.</div> : null}
            {emailValid && !emailError ? <div className="errorMessage">Email 형식으로 입력해주세요.</div> : null}

            <InputPw value={passwordBind} classname={nickError || nickValid ? "errorInput" : "inputPwInput"} type={"noNeed"} />
            {passwordError ? <div className="errorMessage">Password를 입력해주세요.</div> : null}
            {passwordValid && !passwordError ? <div className="errorMessage">Password는 최소 1개의 영문과 숫자를 포함하고 최소 8글자 이상이여야 합니다.</div> : null}
            <div className="signUpRightPwNotice">Passwords must contain at least eight characters, including at least 1 letter and 1 number.</div>

            <div className="signUpRightPromoNotice">
              <input
                type="checkbox"
                onClick={(e) => {
                  setNewsletter(!newsletter);
                }}
              />
              <div className="signUpRightPromoNoticeText">Opt-in to receive occasional product updates, user research invitations, company announcements, and digests.</div>
              <SignUpModal />
            </div>

            <InputBtn text={"Sign up"} onclick={handleSumbitBtn} />
          </form>

          <div className="sigUpRightLink">
            By clicking “Sign up”, you agree to our{" "}
            <a href="https://stackoverflow.com/legal/terms-of-service/public" target="__blank">
              terms of service
            </a>
            ,{" "}
            <a href="https://stackoverflow.com/legal/privacy-policy" target="__blank">
              privacy policy
            </a>
            and
            <a href="https://stackoverflow.com/legal/cookie-policy" target="__blank">
              cookie policy
            </a>
          </div>
        </div>
      </div>
    </div>
  );
};

export default SignUp;
