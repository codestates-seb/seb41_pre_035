//질문 등록 페이지
import React from "react";
import { useState } from "react";
import MDEditor from "@uiw/react-md-editor";
import "../css/AskQuestions.css";
import "../css/Tags.css";
import TAGS from "./Tags";

function AskQuestions() {
  const [question, setQuestion] = useState("");
  const [title, setTitle] = useState("");
  const [result, setResult] = useState("");
  const [tag, setTag] = useState("");
  const [tagList, setTagList] = useState([]);
  const [post, setPost] = useState(false);
  let tagMax = false;

  const handleTitleInput = (e) => {
    setTitle(e.target.value);
  };
  const handleTitleTag = (e) => {
    setTag(e.target.value);
  };
  const handleReviewClick = () => {
    setPost(true);
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
      tagMax = true;
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
    } else {
      alert("Cancel");
    }
  };

  const handleQuestionSubmit = (e) => {
    e.preventDefault();
    if (title && question && result) {
      fetch("http://34.64.179.131:8080/questions", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify({
          memberId: Number,
          title: title,
          body: `${question}\n\n ${result}`,
          tags: tagList,
        }),
      }).then((res) => {
        if (res.ok) {
          alert("생성이 완료 되었습니다.");
        }
      });
    } else {
      //focus 이동 -> 다시 작성하도록..
    }
  };
  return (
    <>
      <div className="content">
        <div className="dFlex">
          <h1>Ask a public question</h1>
        </div>
        <div className="notice">
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
        <div className="psAbsolute">
          <div className="fsBody ">
            <p>Writing a good title</p>
          </div>
          <div className="bgWhite dFlex">
            <p>Your title should summarize the problem.</p>
            <p>You might find that you have a better idea of your title after writing out the rest of the question.</p>
          </div>
        </div>
        <div className="postTitle dFlex">
          <p>Title</p>
          <p>Be specific and imagine you're asking a question to another person.</p>
          <input className="input" type="text" placeholder="e.g. Is there an R function for finding the index of and element in a vector?" value={title} onChange={handleTitleInput}></input>
          <button className="btn" disabled={title.length === 0}>
            Next
          </button>
        </div>
        <div className="editer">
          <p>What are the details of your problem?</p>
          <p>Introduce the problem and expand on what you put in the title. Minimum 20 characters.</p>
          <MDEditor value={question} onChange={setQuestion} />
          <MDEditor.Markdown source={question} style={{ whiteSpace: "pre-wrap" }} />
          <button className="btn" disabled={question.length < 20}>
            Next
          </button>
        </div>
        <div className="editer">
          <p>What did you try and what were you expecting?</p>
          <p>Describe what you tried, what you expected to happen, and what actually resulted. Minimum 20 characters.</p>
          <MDEditor value={result} onChange={setResult} />
          <MDEditor.Markdown source={result} style={{ whiteSpace: "pre-wrap" }} />
          <button className="btn" disabled={result.length < 20}>
            Next
          </button>
        </div>
        <div className="tags">
          <p>Tags</p>
          <p>Add up to 5 tags to describe what your question is about. Start typing to see suggestions.</p>
          <input className="input" type="text" value={tag} onChange={handleTitleTag}></input>
          <div className="tagsAutoComplete">
            {tag &&
              filterTags.map((el, idx) => (
                <li key={idx} className="tag">
                  <span className="tagTitle" onClick={() => handleTagClick(el.title)}>
                    {el.title}
                  </span>
                  <p>{el.content}</p>
                </li>
              ))}
          </div>
          <div className="tagList">
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
          </div>
          {tagMax && <p>The maximum number of tags allowed is 5.</p>}
        </div>
        <button className="btn inlineBtn" onClick={handleReviewClick}>
          Review your question
        </button>
        {(title || result || tag || question) && (
          <button className="btn redBtn" onClick={handleClear}>
            Discard draft
          </button>
        )}
        {post && (
          <button className="btn inlineBtn" onClick={handleQuestionSubmit}>
            Post your question
          </button>
        )}
      </div>
    </>
  );
}

export default AskQuestions;
