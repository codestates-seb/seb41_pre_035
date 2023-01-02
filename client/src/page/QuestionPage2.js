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
import { formatDate } from "./QuestionItem";
import Nav from "../component/Nav";
import Sidebar from "../component/Sidebar";

const BASE_URL = "http://ec2-54-180-55-239.ap-northeast-2.compute.amazonaws.com:8080/";
const LIMIT = 15;

function qformatDate(value) {
  const date = new Date(value);
  return `${date.getFullYear()}. ${date.getMonth() + 1}. ${date.getDate()}`;
} //날짜 형식으로 출력해주려고 만듦

function QuestionPage() {
  const { questionId } = useParams();
  const navigate = useNavigate();
  const [answer, setAnswer] = useState("");
  const [pop, setPop] = useState(true);
  const [question, setQuestion] = useState([]);
  const [comments, setComments] = useState([]);

  const user = useRecoilValue(userState); //로그인 유저 정보
  const token = localStorage.getItem("accessToken");

  const handleClick = () => {
    navigate("/askquestions");
  };

  useEffect(() => {
    handleLoad(questionId);
    handleLoadComments(questionId);
  }, []);

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

  const handleLoadComments = async (questionId) => {
    await axios
      .get(`${BASE_URL}questions/${questionId}?page=1&size=${LIMIT}`, {
        headers: { "Content-Type": "application/json", Authorization: token },
      })
      .then((res) => {
        if (res.status === 200) {
          console.log(res.data);
          setComments(res.data);
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
    <>
      <Nav />
      <div className="qContent">
        <div className="qHeader">
          <h1>{question.title}</h1>
          <button className="btn" onClick={handleClick}>
            Ask Quesitons
          </button>
        </div>
        <div className="qwriter">
          <p>Asked {qformatDate(question.createdAt)}</p>
          <p>Edited {qformatDate(question.lastModifiedAt)}</p>
          <p>Viewed {question.viewCount} times</p>
        </div>
        <div className="postLayout">
          <div className="qSideBar">
            <i className="voteCount fa-solid fa-sort-up"></i>
            <p>{question.voteCount}</p>
            <i className="voteCount fa-solid fa-sort-down"></i>
            <i className="qbookMark fa-regular fa-bookmark"></i>
          </div>
          <div className="postBody">
            <p>{question.content}</p>
          </div>
        </div>

        {/*<div className="qComments">
          {comments.map((el) => (
            <div className="=qComment">
              <p>{el.content}</p>
              <p>
                {el.memberName} {formatDate(el.lastModifiedAt, el.createdAt)} ago
              </p>
            </div>
          ))}
        </div>
        <div className="qanswers">
          {question.answers.map((el) => (
            <div className="qanswer">
              <div className="postLayout">
                <div className="qSideBar">
                  <i className="voteCount fa-solid fa-sort-up"></i>
                  <p>{el.voteCount}</p>
                  <i className="voteCount fa-solid fa-sort-down"></i>
                  <i className="qbookMark fa-regular fa-bookmark"></i>
                </div>
                <div className="postBody">
                  <p>{el.content}</p>
                </div>
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
        </div>*/}
      </div>
      <Sidebar />
    </>
  );
}

export default QuestionPage;
