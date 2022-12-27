//개별 Question 페이지
import React from "react";
import { useParams, useNavigate } from "react-router-dom";
import "../css/Btn.css";
import "../css/QuestionPage.css";
import questionList from "../data/Questions";

function QuestionPage() {
  const { questionId } = useParams();
  const navigate = useNavigate();

  const question = questionList.filter((el) => el.id === Number(questionId))[0];
  const handleClick = () => {
    navigate("/questions");
  };
  return (
    <div className="sqContent">
      <div className="qHeader">
        <h1>{question.title}</h1>
        <button className="btn" onClick={handleClick}>
          Ask Quesitons
        </button>
      </div>
      <p>{question.content}</p>
    </div>
  );
}

export default QuestionPage;
