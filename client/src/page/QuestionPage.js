//개별 Question 페이지
import React from "react";
import { useState } from "react";
import { useParams, useNavigate } from "react-router-dom";
import "../css/Btn.css";
import "../css/QuestionPage.css";
import questionList from "../data/Questions";
import MDEditor from "@uiw/react-md-editor";

function QuestionPage() {
  const { questionId } = useParams();
  const navigate = useNavigate();
  const [answer, setAnswer] = useState("");

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
      <div className="postLayout">
        <div className="qSideBar">
          <i className="voteCount fa-solid fa-sort-up"></i>
          <p>vote num</p>
          <i className="voteCount fa-solid fa-sort-down"></i>
          <i className="qbookMark fa-regular fa-bookmark"></i>
        </div>
        <div className="postBody">
          <p>{question.content}</p>
        </div>
      </div>
      <div className="answers">
        <h2>{question.answer.length} Answers</h2>
        {question.answer.map((el) => (
          <div className="postLayout answer">
            <div className="qSideBar">
              <i className="voteCount fa-solid fa-sort-up"></i>
              <p>vote num</p>
              <i className="voteCount fa-solid fa-sort-down"></i>
              <i className="qbookMark fa-regular fa-bookmark"></i>
            </div>
            <div className="postBody">
              <p>{el}</p>
            </div>
          </div>
        ))}
      </div>
      <div className="yourAnswer">
        <h2>Your Answer</h2>
        <MDEditor value={answer} onChange={setAnswer} />
        <MDEditor.Markdown source={answer} style={{ whiteSpace: "pre-wrap" }} />
        <button className="btn" onClick={handleClick}>
          Post Your Answer
        </button>
      </div>
    </div>
  );
}

export default QuestionPage;
