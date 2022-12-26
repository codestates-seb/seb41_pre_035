//Questions 메인 페이지
import React from "react";
import { useNavigate } from "react-router-dom";

import "../css/Questions.css";
import QuestionItem from "./QuestionItem";

function Questions() {
  const navigate = useNavigate();

  const handleClick = () => {
    navigate("/questions");
  };

  return (
    <>
      <p>All Quesitons</p>
      <button className="btn" onClick={handleClick}>
        Ask Quesitons
      </button>
      <p>0000 questions</p>
      <QuestionItem />
    </>
  );
}

export default Questions;
