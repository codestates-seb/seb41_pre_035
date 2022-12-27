import "../css/recovery.css";
import { useInput } from "../util/useInput";
import { InputEmail, InputBtn } from "../component/Form";
import { emailValidator } from "../component/validator";
import { useState } from "react";

const Recovery = () => {
  // * 복구 이메일을 받을 상태
  const [reEmail, reEmailBind, reEmailReset] = useInput("");
  const [recoveryIsSuccess, setRecoveryIsSuccess] = useState(false);

  // * 유효성 검사 상태
  const [reEmailError, setReEmailError] = useState(false);
  const [reEmailValidError, setReEmailValidError] = useState(false);

  // * 입력창에 뭐가 입력되었는지 감지
  const checkReEmail = () => {
    if (reEmail === "") {
      setReEmailError(true);
      return false;
    } else {
      setReEmailError(false);
      return true;
    }
  };
  const checkReEmailValid = () => {
    const result = emailValidator(reEmail);

    if (!result) {
      setReEmailValidError(true); // 에러가 있음으로 바꿈
      return false;
    } else {
      setReEmailValidError(false); // 에러가 없음으로 바꿈
      return true;
    }
  };

  // * 제출시 실행하는 이벤트 함수
  // ! 버튼 클릭 시 서버로 요청가도록 해야 함
  const handleReEmailBtn = (e) => {
    e.preventDefault();

    checkReEmail();
    checkReEmailValid();

    if (checkReEmail() && checkReEmailValid()) {
      console.log("서버에 데이터를 보내세요!");
      // ! axios요청보내고 성공하면 요청이 성공했으니 해당 이메일로 가서 확인하라고 띄워야 함
      // ! 작성한 이메일과 서버에 있는 이메일과 일치시에 요청 성공이 되나? 나중에 확인하기
      // ! 일단 페이지 확인해야되서 해놓음
      setRecoveryIsSuccess(true);
    }
  };

  return (
    <div className="recoveryWraapper">
      {recoveryIsSuccess ? (
        <div className="recoverySuccess">
          <i className="fa-solid fa-check fa-2x"></i>
          <div>
            <div className="recoverySuccessTitle">Account recovery email sent to {reEmail}</div>
            <div className="recoverySuccessDescription">
              If you don’t see this email in your inbox within 15 minutes, look for it in your junk mail folder. If you find it there, please mark it as “Not Junk”.
            </div>
          </div>
        </div>
      ) : (
        <form className="recoveryForm">
          <div className="recoveryDescription">Forgot your account’s password or having trouble logging into your Team? Enter your email address and we’ll send you a recovery link.</div>

          <InputEmail value={reEmailBind} classname={reEmailError || reEmailValidError ? "errorInput" : "inputEmailInput"} />
          {reEmailError ? <div className="errorMessage">Email을 입력해주세요.</div> : null}
          {reEmailValidError && !reEmailError ? <div className="errorMessage">Email 형식으로 입력해주세요.</div> : null}

          <InputBtn text={"Send recovery email"} onclick={handleReEmailBtn} className="recoveryBtn" />
        </form>
      )}
    </div>
  );
};

export default Recovery;
