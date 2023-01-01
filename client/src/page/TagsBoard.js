import Nav from "../component/Nav";
import Footer from "../component/Footer";
import "../css/tagsBoard.css";
import Tags from "../data/Tags";
import axios from "axios";
import Pagination from "../component/Pagination";
import { useState } from "react";
import { useEffect } from "react";

const TagsBoard = () => {
  const url = "http://ec2-54-180-55-239.ap-northeast-2.compute.amazonaws.com:8080";
  const taglist = Tags;
  const pageInfo = {};

  // * 페이지네이션
  // const [posts, setPosts] = useState([]);
  // const [limit, setLimit] = useState(30);
  // const [page, setPage] = useState(1);
  // const offset = (page - 1) * limit;
  // const total = taglist.list;

  // * popular 버튼 핸들러
  const handlePopular = () => {
    // return axios
    //   .get(`${url}/tags/search?q=tag&page=1&size=20&sort=POPULAR HTTP/1.1`)
    //   .then((res) => {
    //     taglist = res.body.data;
    //     pageInfo = res.body.pageInfo;
    //   })
    //   .catch((err) => console.log(err));
  };

  // * name 버튼 핸들러
  const handleName = () => {
    // return axios
    //   .get(`${url}/tags/search?q=tag&page=1&size=20&sort=POPULAR HTTP/1.1`)
    //   .then((res) => {
    //     taglist = res.body.data;
    //     pageInfo = res.body.pageInfo;
    //   })
    //   .catch((err) => console.log(err));
  };

  // * newest 버튼 핸들러
  const handleNewest = () => {
    // return axios
    //   .get(`${url}/tags/search?q=tag&page=1&size=20&sort=POPULAR HTTP/1.1`)
    //   .then((res) => {
    //     taglist = res.body.data;
    //     pageInfo = res.body.pageInfo;
    //   })
    //   .catch((err) => console.log(err));
  };

  return (
    <div className="container">
      <Nav className="tagsBoardNav" />

      <div className="tagsBoard">
        <div className="ttagsBoardInfo">
          <h2 className="tagsBoardTitle">Tags</h2>
          <div className="tagsBoardText">
            A tag is a keyword or label that categorizes your question with other, similar questions. <br /> Using the right tags makes it easier for others to find and answer your question.
          </div>
        </div>
        <div className="tagsBoardFilter">
          <form>
            <input type="text" placeholder="Filter by tag name" className="tagsBoardSearch" />
          </form>
          <div className="tagsBoardFilterBtns">
            <button onClick={handlePopular}>Popular</button>
            <button onClick={handleName}>Name</button>
            <button onClick={handleNewest}>New</button>
          </div>
        </div>

        {/* ! 나중에 서버로 데이터 받아서 할 것 */}
        <ul className="tagsBoardContainers">
          {taglist.map((tag, idx) => (
            <li key={idx} className="tagsBoardContainer">
              <div className="tagsContainer">
                <p className="tagsName">{tag.title}</p>
                <p className="tagsText">{tag.content}</p>

                <div className="tagsInfo">
                  <div className="tagsInfoNum">2462356 questions</div>
                  <div className="tagsInfoDate">563 asked today, 3129 this week</div>
                </div>
              </div>
            </li>
          ))}
        </ul>

        <div className="tagsBoardPage">{/* <Pagination total={total} page={page} limit={limit} /> */}</div>
      </div>

      <Footer />
    </div>
  );
};

export default TagsBoard;
