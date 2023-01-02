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
import questionData from "../data/Question";
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
  const question = questionData.data;
  const navigate = useNavigate();
  const [answer, setAnswer] = useState("");
  const [pop, setPop] = useState(true);
  const [share, setShare] = useState(false);
  const [follow, setFollow] = useState(false);
  const [comment, setComment] = useState(false);
  const user = useRecoilValue(userState); //로그인 유저 정보

  const handleClick = () => {
    if (user === null) {
      //로그인 되지 않은 경우
      alert("로그인을 먼저 진행해주세요");
    } else {
      //로그인 된 경우
      navigate("/askquestions");
    }
  };

  const handleClear = () => {
    if (window.confirm("Discard question")) {
      setAnswer("");
      alert("Discard");
    } else {
      alert("Cancel");
    }
  };
  const handleTagClick = () => {
    navigate(`/tags`);
  };
  const handleEdit = () => {
    navigate(`/questions/${questionId}/edit`);
  };

  return (
    <>
      <Nav />
      <div className="qContent">
        <div className="fsHeadLine">
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
            <div className="tags">
              {question.tags.map((el) => (
                <li key={el.tagId} className="tag">
                  <p onClick={handleTagClick} className="tagTitle sTag">
                    {el.name}
                  </p>
                </li>
              ))}
            </div>
          </div>
        </div>
        <div className="qEdit">
          <p onClick={() => setShare(!share)}>Share</p>
          <p onClick={handleEdit}>Edit</p>
          {follow ? <p onClick={() => setFollow(!follow)}>Following</p> : <p onClick={() => setFollow(!follow)}>Follow</p>}
          <div className="qMember">
            <p>{question.writer.name}</p>
            <p>{question.writer.email}</p>
          </div>
        </div>
        {share && (
          <div className="sharePopup">
            <p>Share a link to this question (Includes your user id)</p>
            <input value={`${BASE_URL}questions/${questionId}`}></input>
            <p>Copy link</p>
          </div>
        )}
        <p className="cadd" onClick={() => setComment(!comment)}>
          Add a comment
        </p>
        {comment && (
          <div className="addcomments questionBorder">
            <input className="titleInput"></input>
          </div>
        )}
        {/*<div className="qComments">
        {comments.map((el) => (
          <div className="=qComment">
            <p>{el.content}</p>
            <p>
              {el.memberName} {formatDate(el.lastModifiedAt, el.createdAt)} ago
            </p>
          </div>
        ))}
        </div>*/}
        <div className="qanswers">
          <h1>{question.answers.length} Answers</h1>
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
        <p>Know someone who can answer? Share a link to this question via email, Twitter, or Facebook.</p>
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
          <button className="btn flexItem">Post Your Answer</button>
          {answer && (
            <button className="btn redBtn flexItem" onClick={handleClear}>
              Discard draft
            </button>
          )}
        </div>
      </div>
      <Sidebar />
    </>
  );
}

export { QuestionPage, qformatDate };
