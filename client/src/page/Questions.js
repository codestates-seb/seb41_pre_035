//Questions 메인 페이지
import React from "react";
import { useNavigate } from "react-router-dom";
import "../css/Btn.css";
import "../css/Questions.css";
import QuestionItem from "./QuestionItem";
import questionList from "../data/Questions";

function Questions() {
  const navigate = useNavigate();

  const handleClick = () => {
    navigate("/questions");
  };

  return (
    <div className="qContent">
      <div className="fsHeadLine">
        <h1>All Questions</h1>
        <button className="btn" onClick={handleClick}>
          Ask Quesitons
        </button>
      </div>
      <div className="qTopBar">
        <p>{questionList.length} questions</p>
        <button className="sBtn">Newest</button>
        <button className="sBtn">Active</button>
        <button className="sBtn">Bountied</button>
        <button className="sBtn">Unanswered</button>
        <button className="sBtn">More</button>
        <button className="sBtn filterBtn">Filter</button>
      </div>
      {questionList.map((el, idx) => (
        <li key={idx} className="qListItem">
          <QuestionItem questionItem={el} />
        </li>
      ))}
    </div>
  );
}

export default Questions;
