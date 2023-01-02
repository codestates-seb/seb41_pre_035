import "../css/signUpNotice.css";
import { useRecoilValue } from "recoil";
import { emailState } from "../recoil";

const SignUpNotice = () => {
  const email = useRecoilValue(emailState);

  return (
    <div className="signUpNotice">
      <div className="signUpNoticeWrapper">
        <i className="fa-solid fa-check fa-2x"></i>
        <div className="signUpNoticeNotice">
          <div className="recoverySuccessTitle">Registration email sent to {email} Open this email to finish signup.</div>
          <div className="recoverySuccessDescription">
            If you don’t see this email in your inbox within 15 minutes, look for it in your junk mail folder. If you find it there, please mark it as “Not Junk”.
          </div>
        </div>
      </div>
    </div>
  );
};

export default SignUpNotice;
