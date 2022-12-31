//Questions 메인 페이지
import React from "react";
import axios from "axios";
import { useNavigate } from "react-router-dom";
import { useEffect, useState } from "react";
import "../css/Btn.css";
import "../css/Questions.css";
import QuestionItem from "./QuestionItem";
import filterMenu from "../component/Menu";
import Nav from "../component/Nav";
import Sidebar from "../component/Sidebar";

const LIMIT = 15; //페이지네이션 값
const BASE_URL = "http://ec2-54-180-55-239.ap-northeast-2.compute.amazonaws.com:8080/";

function Questions() {
  const navigate = useNavigate();
  const [filterMenu, setFilterMenu] = useState(false);
  const [questions, setQuestions] = useState([]);
  const [order, setOrder] = useState("NEWEST");
  const [page, setPage] = useState(1); //페이지 번호 : 1부터 시작

  const token = localStorage.getItem("accessToken");

  const handleClick = () => {
    navigate("/askquestions");
  };

  const handleLoad = async (page) => {
    await axios
      .get(`${BASE_URL}questions?page=${page}&size=${LIMIT}&sort=${order}`, {
        headers: { "Content-Type": "application/json", Authorization: token },
      })
      .then((res) => {
        if (res.status === 200) {
          setQuestions(res.data.data);
        }
      })
      .catch((err) => {
        console.log(err.response);
      });
  };

  useEffect(() => {
    handleLoad(page);
  }, []);

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
        {filterMenu && <filterMenu />}
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
