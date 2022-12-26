//질문 등록 페이지
import React from "react";
import { useState, useRef } from "react";
import MDEditor from "@uiw/react-md-editor";
import "../css/AskQuestions.css";
import "../css/Tags.css";
import "../css/Editer.css";
import "../css/Btn.css";
import TAGS from "../data/Tags";
const axios = require("axios");

function AskQuestions() {
  const [question, setQuestion] = useState("");
  const [title, setTitle] = useState("");
  const [result, setResult] = useState("");
  const [tag, setTag] = useState("");
  const [tagList, setTagList] = useState([]);
  const [post, setPost] = useState(false);
  const [titleReview, setTitleReview] = useState(false);
  const [questionReview, setQuestionReview] = useState(false);
  const [resultReview, setResultReview] = useState(false);
  const [next, setNext] = useState([true, false, false, false]);
  const [tagMax, setTagMax] = useState(false);

  const titleInput = useRef();
  const tagsInput = useRef();
  const reviewBtn = useRef();

  const handleTitleInput = (e) => {
    setTitle(e.target.value);
  };
  const handleTitleTag = (e) => {
    setTag(e.target.value);
  };
  const handleReviewClick = () => {
    //입력값들을 검증
    if (title.length < 15) {
      titleInput.current.focus();
      setTitleReview(true);
      setNext([true, false, false, false]);
    } else if (question.length < 20) {
      setQuestionReview(true);
      setNext([false, true, false, false]);
    } else if (result.length < 20) {
      setQuestionReview(false);
      setResultReview(true);
      setNext([false, false, true, false]);
    } else {
      setResultReview(false);
      setPost(true);
    }
  };
  const removeTag = (indexToRemove) => {
    setTagList([...tagList.filter((_, idx) => idx !== indexToRemove)]);
    console.log(tagList);
  };
  const filterTags = TAGS.filter((el) => {
    return !tagList.includes(el.title) && el.title.replace(" ", "").toLocaleLowerCase().includes(tag.toLocaleLowerCase());
  });
  const handleTagClick = (tag) => {
    if (tagList.length < 5) {
      setTagList([...tagList, tag]);
      setTag("");
    } else {
      //tag의 개수가 5개를 넘어가는 경우 -> 더 이상 태그가 추가되지 않도록..
      setTagMax(true);
      setTag("");
    }
  };
  const handleClear = () => {
    if (window.confirm("Discard question")) {
      setQuestion("");
      setTitle("");
      setResult("");
      setTagList([]);
      setTag("");
      alert("Discard");
      titleInput.current.focus();
    } else {
      alert("Cancel");
    }
  };
  const handleNextClick = (idx) => {
    if (idx === 0) {
      setNext([false, true, false, false]);
      setQuestionReview(false);
    } else if (idx === 1) {
      setNext([false, false, true, false]);
      setResultReview(false);
    } else if (idx === 2) {
      setNext([false, false, false, true]);
      tagsInput.current.focus();
    } else if (idx === 3) {
      reviewBtn.current.focus();
    }
  };
  const questionBody = JSON.stringify({
    memberId: 0,
    title: title,
    body: `${question}\n\n ${result}`,
    tags: tagList,
  });
  const handleQuestionSubmit = (e) => {
    e.preventDefault();
    axios
      .post("http://34.64.179.131:8080/questions", questionBody, {
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
    <>
      <div className="content">
        <div className="bigTitle">
          <h1>Ask a public question</h1>
        </div>
        <div className="notice dWidth">
          <h2>Writing a good question</h2>
          <p>You’re ready to ask a programming-related question and this form will help guide you through the process.</p>
          <p>Looking to ask a non-programming question? See the topics here to find a relevant site.</p>
          <p>Steps</p>
          <ul className="ub0">
            <li>Summarize your problem in a one-line title.</li>
            <li>Describe your problem in more detail.</li>
            <li>Describe what you tried and what you expected to happen.</li>
            <li>Add “tags” which help surface your question to members of the community.</li>
            <li>Review your question and post it to the site.</li>
          </ul>
        </div>
        <div className="postTitle dWidth flexItem questionBorder">
          <p>Title</p>
          <p>Be specific and imagine you're asking a question to another person.</p>
          <input
            className="titleInput"
            type="text"
            ref={titleInput}
            placeholder="e.g. Is there an R function for finding the index of and element in a vector?"
            value={title}
            onChange={handleTitleInput}
          ></input>
          {titleReview && <p className="warnReview">You should write at least 15 characters.</p>}
          <button className="btn" onClick={() => handleNextClick(0)}>
            Next
          </button>
        </div>
        {next[0] && (
          <div className="psAbsolute flexItem questionBorder">
            <div className="fsBody ">
              <p>Writing a good title</p>
            </div>
            <div className="bgWhite">
              <i class="pencilIcon fa-regular fa-pen-to-square"></i>
              <p>Your title should summarize the problem.</p>
              <p>You might find that you have a better idea of your title after writing out the rest of the question.</p>
            </div>
          </div>
        )}
        <div className="editer dWidth flexItem questionBorder">
          <p>What are the details of your problem?</p>
          <p>Introduce the problem and expand on what you put in the title. Minimum 20 characters.</p>
          <MDEditor value={question} onChange={setQuestion} />
          <MDEditor.Markdown source={question} style={{ whiteSpace: "pre-wrap" }} />
          {questionReview && <p className="warnReview">You should write at least 20 characters.</p>}
          <button className="btn" onClick={() => handleNextClick(1)}>
            Next
          </button>
        </div>
        {next[1] && (
          <div className="psAbsolute flexItem questionBorder">
            <div className="fsBody ">
              <p>Introduce the problem</p>
            </div>
            <div className="bgWhite">
              <i class="pencilIcon fa-regular fa-pen-to-square"></i>
              <p>Explain how you encountered the problem you’re trying to solve, and any difficulties that have prevented you from solving it yourself.</p>
            </div>
          </div>
        )}
        <div className="editer dWidth flexItem questionBorder">
          <p>What did you try and what were you expecting?</p>
          <p>Describe what you tried, what you expected to happen, and what actually resulted. Minimum 20 characters.</p>
          <MDEditor value={result} onChange={setResult} />
          <MDEditor.Markdown source={result} style={{ whiteSpace: "pre-wrap" }} />
          {resultReview && <p className="warnReview">You should write at least 20 characters.</p>}
          <button className="btn" onClick={() => handleNextClick(2)}>
            Next
          </button>
        </div>
        {next[2] && (
          <div className="psAbsolute flexItem questionBorder">
            <div className="fsBody">
              <p>Expand on the problem</p>
            </div>
            <div className="bgWhite">
              <i class="pencilIcon fa-regular fa-pen-to-square"></i>
              <p>Show what you’ve tried, tell us what happened, and why it didn’t meet your needs.</p>
              <p>Not all questions benefit from including code, but if your problem is better understood with code you’ve written, you should include a minimal, reproducible example.</p>
              <p>Please make sure to post code and errors as text directly to the question (and not as images), and format them appropriately.</p>
            </div>
          </div>
        )}
        <div className="tagsOverlay dWidth flexItem questionBorder">
          <p>Tags</p>
          <p>Add up to 5 tags to describe what your question is about. Start typing to see suggestions.</p>
          <div className="flexTagItem questionBorder">
            <ul className="tagList">
              {tagList.map((el, idx) => (
                <li key={idx} className="tag">
                  <span className="tagTitle">
                    {el}
                    <span className="tagClose" onClick={() => removeTag(idx)}>
                      x
                    </span>
                  </span>
                </li>
              ))}
            </ul>
            <input className="tagInput" type="text" value={tag} ref={tagsInput} onChange={handleTitleTag}></input>
          </div>
          {tag && (
            <div className="tagsAutoComplete questionBorder">
              {filterTags.map((el, idx) => (
                <li key={idx} className="tag">
                  <span className="tagTitle" onClick={() => handleTagClick(el.title)}>
                    {el.title}
                  </span>
                  <p>{el.content}</p>
                </li>
              ))}
            </div>
          )}

          {tagMax && <p className="warnReview">The maximum number of tags allowed is 5.</p>}
          <button className="btn" onClick={() => handleNextClick(3)}>
            Next
          </button>
        </div>
        {next[3] && (
          <div className="psAbsolute flexItem questionBorder">
            <div className="fsBody">
              <p>Adding tags</p>
            </div>
            <div className="bgWhite">
              <i class="pencilIcon fa-regular fa-pen-to-square"></i>
              <p>Tags help ensure that your question will get attention from the right people.</p>
              <p>Tag things in more than one way so people can find them more easily. Add tags for product lines, projects, teams, and the specific technologies or languages used.</p>
              <p>Learn more about tagging</p>
            </div>
          </div>
        )}
        <div className="flexItem formSubmit">
          {!post && (
            <button className="btn inlineBtn flexItem" ref={reviewBtn} onClick={handleReviewClick}>
              Review your question
            </button>
          )}

          {post && (
            <button className="btn inlineBtn flexItem" onClick={handleQuestionSubmit}>
              Post your question
            </button>
          )}
          {(title || result || tag || question) && (
            <button className="btn redBtn flexItem" onClick={handleClear}>
              Discard draft
            </button>
          )}
        </div>
      </div>
    </>
  );
}

export default AskQuestions;
