import { useState } from "react";
import "../css/signUpModal.css";

const SignUpModal = () => {
  const [isOpen, setIsOpen] = useState(false);

  const openModalHandler = () => {
    setIsOpen(!isOpen);
  };

  return (
    <>
      <div className="signUpmodalContainer">
        <button className="signUpModalBtn" onClick={openModalHandler} type="button">
          <i className="fa-solid fa-circle-question signUpModalIcon"></i>
        </button>
        {isOpen ? (
          <div>
            <div className="signUpModalBackdrop" onClick={openModalHandler}></div>
            <div className="signUpModalView">
              <div className="signUpModaText">
                We know you hate spam, and we do too. That’s why we make it easy for you to update your email preferences or unsubscribe at anytime.
                <br />
                <br />
                We never share your email address with third parties for marketing purposes.
              </div>
            </div>
          </div>
        ) : null}
      </div>
    </>
  );
};

export default SignUpModal;
