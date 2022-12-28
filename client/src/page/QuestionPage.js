//개별 Question 페이지
import React from "react";
import { useState } from "react";
import { useParams, useNavigate } from "react-router-dom";
import "../css/Btn.css";
import "../css/QuestionPage.css";
import questionList from "../data/Questions";
import MDEditor from "@uiw/react-md-editor";

const axios = require("axios");

function QuestionPage() {
  const { questionId } = useParams();
  const navigate = useNavigate();
  const [answer, setAnswer] = useState("");
  const [pop, setPop] = useState(true);

  const question = questionList.filter((el) => el.id === Number(questionId))[0];
  const handleClick = () => {
    navigate("/questions");
  };
  const AnswerBody = JSON.stringify({
    memberId: 0,
    title: "title",
    body: answer,
    tags: [],
  });

  const handleClear = () => {
    if (window.confirm("Discard question")) {
      setAnswer("");
      alert("Discard");
    } else {
      alert("Cancel");
    }
  };

  const handleAnswerSubmit = (e) => {
    e.preventDefault();
    axios
      .post("http://34.64.179.131:8080/answers", AnswerBody, {
        "Accept-Language": "ko",
        "Content-Type": "application/json",
      })
      .then((res) => {
        if (res.ok) {
          alert("생성이 완료 되었습니다.");
        }
      });
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
        {pop === true && answer && (
          <div className="qPopup">
            <div className="popupText">
              <p>Thanks for contributing an answer to Stack Overflow!</p>
              <li>Please be sure to answer the question. Provide details and share your research!</li>
              <p>But avoid …</p>
              <li>Asking for help, clarification, or responding to other answers.</li>
              <li>Making statements based on opinion; back them up with references or personal experience.</li>
              <p>To learn more, see our tips on writing great answers.</p>
            </div>
            <button id="popupClose" onClick={() => setPop(false)}>
              x
            </button>
          </div>
        )}
        <MDEditor.Markdown source={answer} style={{ whiteSpace: "pre-wrap" }} />
        <button className="btn flexItem" onClick={handleAnswerSubmit}>
          Post Your Answer
        </button>
        {answer && (
          <button className="btn redBtn flexItem" onClick={handleClear}>
            Discard draft
          </button>
        )}
      </div>
    </div>
  );
}

export default QuestionPage;
