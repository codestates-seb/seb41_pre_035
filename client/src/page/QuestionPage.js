//개별 Question 페이지
import React from "react";
import { useState, useEffect } from "react";
import { useParams, useNavigate } from "react-router-dom";
import "../css/Btn.css";
import "../css/QuestionPage.css";
import MDEditor from "@uiw/react-md-editor";
import { userState } from "../recoil";
import { useRecoilValue } from "recoil";
import axios from "axios";

const BASE_URL = "http://ec2-54-180-55-239.ap-northeast-2.compute.amazonaws.com:8080/";

function QuestionPage() {
  const { questionId } = useParams();
  const navigate = useNavigate();
  const [answer, setAnswer] = useState("");
  const [pop, setPop] = useState(true);
  const [question, setQuestion] = useState({});
  const user = useRecoilValue(userState); //로그인 유저 정보
  const token = localStorage.getItem("accessToken");

  useEffect(() => {
    handleLoad(questionId);
  }, []);

  const handleClick = () => {
    navigate("/askquestions");
  };

  const handleLoad = async (questionId) => {
    await axios
      .get(`${BASE_URL}questions/${questionId}`, {
        headers: { "Content-Type": "application/json", Authorization: token },
      })
      .then((res) => {
        if (res.status === 200) {
          console.log(res.data);
          setQuestion(res.data);
        }
      })
      .catch((err) => {
        console.log(err.response);
      });
  };

  const AnswerBody = JSON.stringify({
    questionId: questionId,
    memberId: user.memberId,
    content: answer,
  });
  const handleClear = () => {
    if (window.confirm("Discard question")) {
      setAnswer("");
      alert("Discard");
    } else {
      alert("Cancel");
    }
  };
  const handleVoteClick = (e) => {
    e.preventDefault();
    axios
      .patch(`${BASE_URL}questions/${questionId}/votes`, {
        headers: { "Content-Type": "application/json", Authorization: token },
      })
      .then((res) => {
        if (res.status === 201) {
          console.log(res);
          alert("투표가 완료 되었습니다.");
        }
      })
      .catch((err) => {
        console.log(err.response);
      });
  };
  const handleAnswerSubmit = (e) => {
    e.preventDefault();
    axios
      .post(`${BASE_URL}answers`, AnswerBody, {
        headers: { "Content-Type": "application/json", Authorization: token },
      })
      .then((res) => {
        if (res.status === 201) {
          console.log(res);
          alert("답변 등록이 완료 되었습니다.");
        }
      })
      .catch((err) => {
        console.log(err.response);
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
        <h2>{Object.keys(question.answers).length} Answers</h2>
        {question.answers.map((el) => (
          <div className="postLayout answer">
            <div className="qSideBar">
              <i className="voteCount fa-solid fa-sort-up" onClick={handleVoteClick}></i>
              <p>vote num</p>
              <i className="voteCount fa-solid fa-sort-down" onClick={handleVoteClick}></i>
              <i className="qbookMark fa-regular fa-bookmark"></i>
            </div>
            <div className="postBody">
              <p>{el.content}</p>
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
