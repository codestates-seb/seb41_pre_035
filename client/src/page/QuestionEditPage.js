//개별 Question edit 페이지
import React from "react";
import { useState } from "react";
import { useParams, useNavigate } from "react-router-dom";
import "../css/Btn.css";
import "../css/QuestionPage.css";
import "../css/QuestionEditPage.css";
import MDEditor from "@uiw/react-md-editor";
import { userState } from "../recoil";
import { useRecoilValue } from "recoil";
import axios from "axios";
import { formatDate } from "./QuestionItem";
import questionData from "../data/Question";
import Nav from "../component/Nav";
import Sidebar from "../component/Sidebar";
import { qformatDate } from "./QuestionPage";
import TAGS from "../data/Tags";

const question = questionData.data;

const BASE_URL = "http://ec2-54-180-55-239.ap-northeast-2.compute.amazonaws.com:8080/";
const LIMIT = 15;

function QuestionEditPage() {
  const [content, setContent] = useState(question.content);
  const [tagList, setTagList] = useState(question.tags.map((el) => el.name));
  const [tag, setTag] = useState("");
  const [tagMax, setTagMax] = useState(false);

  const removeTag = (indexToRemove) => {
    setTagList([...tagList.filter((_, idx) => idx !== indexToRemove)]);
  };
  const handleTitleTag = (e) => {
    setTag(e.target.value);
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

  return (
    <>
      <Nav />
      <div className="qContent">
        <div className="eRev">
          <h3>Rev</h3>
          <input className="titleInput" value={`${qformatDate(question.lastModifiedAt)} - last edited`}></input>
        </div>
        <div className="eTitle">
          <h3>Title</h3>
          <input className="titleInput" value={question.title}></input>
        </div>
        <div className="eBody">
          <h3>Body</h3>
          <MDEditor value={content} onChange={setContent} />
          <MDEditor.Markdown source={content} style={{ whiteSpace: "pre-wrap" }} />
        </div>
        <div className="eTags">
          <h3>Tags</h3>
          <div className="flexTagItem tagBorder">
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
            <input className="tagInput" type="text" value={tag} onChange={handleTitleTag}></input>
          </div>
          {tag && (
            <div className="tagsAutoComplete tagBorder">
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
        </div>
        <div className="eSummary">
          <h3>Edit Summary</h3>
          <input className="titleInput"></input>
        </div>
        <div className="flexItem">
          <button className="btn inlineBtn flexItem">Save edits</button>
          <button className="btn redBtn flexItem">Cancel</button>
        </div>
      </div>
      <Sidebar />
    </>
  );
}

export default QuestionEditPage;
