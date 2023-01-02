//Questions 메인 페이지
import React from "react";
import axios from "axios";
import { useNavigate } from "react-router-dom";
import { useEffect, useState } from "react";
import "../css/Btn.css";
import "../css/Questions.css";
import { QuestionItem } from "./QuestionItem";
import FilterMenu from "../component/Menu";
import Nav from "../component/Nav";
import Sidebar from "../component/Sidebar";
import { userState } from "../recoil";
import { useRecoilValue } from "recoil";
import questionList from "../data/QuestionList";

function Questions() {
  const navigate = useNavigate();
  const [filterMenu, setFilterMenu] = useState(false);
  const questions = questionList.data;
  const [order, setOrder] = useState("NEWEST");
  const [page, setPage] = useState(1); //페이지 번호 : 1부터 시작
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

  return (
    <>
      <Nav />
      <div className="qContent">
        <div className="fsHeadLine">
          <h1>All Questions</h1>
          <button className="btn" onClick={handleClick}>
            Ask Quesitons
          </button>
        </div>
        <div className="qTopBar">
          <p>{questions.length} questions</p>
          <div className="filterBtns">
            <button className="sBtn" onClick={() => setOrder("NEWEST")}>
              Newest
            </button>
            <button className="sBtn" onClick={() => setOrder("ACTIVE")}>
              Active
            </button>
            <button className="sBtn" onClick={() => setOrder("BOUNTIED")}>
              Bountied
            </button>
            <button className="sBtn" onClick={() => setOrder("UNANSWERED")}>
              Unanswered
            </button>
            <button className="sBtn">More</button>
          </div>
          <button className="sBtn filterBtn" onClick={() => setFilterMenu(!filterMenu)}>
            Filter
          </button>
        </div>
        {filterMenu && <FilterMenu />}
        {questions.map((el, idx) => (
          <li key={idx} className="qListItem">
            <QuestionItem questionItem={el} />
          </li>
        ))}
        <div className="pageNums">
          <button className="pageBtn" onClick={() => setPage(1)}>
            1
          </button>
          <button className="pageBtn" onClick={() => setPage(2)}>
            2
          </button>
          <button className="pageBtn" onClick={() => setPage(3)}>
            3
          </button>
          <button className="pageBtn" onClick={() => setPage(4)}>
            4
          </button>
          <button className="pageBtn" onClick={() => setPage(5)}>
            5
          </button>
          <button className="pageBtn" onClick={() => setPage(page + 1)}>
            Next
          </button>
        </div>
      </div>
      <Sidebar />
    </>
  );
}

export default Questions;
