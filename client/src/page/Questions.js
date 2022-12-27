//Questions 메인 페이지
import React from "react";
import axios from "axios";
import { useNavigate } from "react-router-dom";
import { useEffect, useState } from "react";
import "../css/Btn.css";
import "../css/Questions.css";
import QuestionItem from "./QuestionItem";
import questionList from "../data/Questions";

const LIMIT = 15; //페이지네이션 값

function Questions() {
  const navigate = useNavigate();
  const [isLoading, setIsLoading] = useState(false);
  const [questions, setQuestions] = useState([]);
  const [order, setOrder] = useState("active");
  const [page, setPage] = useState(0); //페이지 번호 : 0부터 시작
  //0번 페이지 로딩 -> question id는 0번, 1번 페이지 로딩 -> question id는 15번 ...

  //const sortedQuestions = questions.sort((a, b) => b[order] - a[order]);

  const handleClick = () => {
    navigate("/questions");
  };

  const handleLoad = async (questionId) => {
    setIsLoading(true); //"로딩 중이다"
    const response = await axios.get(`/questions/${questionId}`);
    setQuestions(response.data);
    setIsLoading(false); //"로딩 중이 아니다"
  };

  useEffect(() => {
    handleLoad(page * 15);
  }, [page]);

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
      <div className="pageNums">
        <button className="pageBtn" onClick={() => setPage(0)}>
          1
        </button>
        <button className="pageBtn" onClick={() => setPage(1)}>
          2
        </button>
        <button className="pageBtn" onClick={() => setPage(2)}>
          3
        </button>
        <button className="pageBtn" onClick={() => setPage(3)}>
          4
        </button>
        <button className="pageBtn" onClick={() => setPage(4)}>
          5
        </button>
        <button className="pageBtn" onClick={() => setPage(page + 1)}>
          Next
        </button>
      </div>
    </div>
  );
}

export default Questions;
