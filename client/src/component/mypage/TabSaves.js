import React from "react";
import "../../css/mypage/tabSaves.css";
import { Routes, Route, Link } from "react-router-dom";

const TabSaves = () => {
  return (
    <div className="tabSavesContainer">
      <div className="tabSavesLeft">
        <div className="tabSavesMenus">
          <ul>
            <li>
              <Link to="">All saves</Link>
            </li>
            <li>For later</li>
          </ul>
        </div>
        <div className="tabSavesMylistAdd">
          <ul>
            <li>MY LISTS</li>
          </ul>
        </div>
      </div>
      <div className="tabSavesRight">
        <div className="tabSavesTitle">all saves</div>
        <div>
          <p className="tabSavesSubTitle">2 saved items</p>
          <div className="tabSavesItems">
            saved items list
            {/* 1. question만 save */}
            <div className="tabSavesItemsQtop">
              {/* <p>1 votes</p>
              <p>1 answers</p>
              <p>1 views</p> */}
            </div>
            <div className="tabSavesItemsQmid"></div>
            <div className="tabSavesItemsQbottom"></div>
            <p></p>
            {/* 2. answer만 save 하거나 question,answer 함께 save */}
          </div>
        </div>
      </div>
    </div>
  );
};

export default TabSaves;
