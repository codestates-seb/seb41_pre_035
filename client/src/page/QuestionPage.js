//개별 Question 페이지
import React from "react";
import { useNavigate } from "react-router-dom";

import "../css/Questions.css";

function QuestionPage() {
  const navigate = useNavigate();
  const handleClick = () => {
    navigate("/questions");
  };
  return (
    <>
      <p>title</p>
      <button className="btn" onClick={handleClick}>
        Ask Quesitons
      </button>
    </>
  );
}

export default QuestionPage;
