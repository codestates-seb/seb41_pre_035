import React from "react";
import "../../css/mypage/tabProfile.css";
import { useState, useEffect } from "react";
import { Link, useParams } from "react-router-dom";

const TabProfile = () => {
  return (
    <div className="tabProfileContainer">
      <div className="tabProfileLeft">
        <h2 className="tabProfileTitle">stats</h2>
        <div className="tabProfileStatusBox">
          <div className="tabProfileStatusBoxIn">
            <div>
              <p className="tabProfileStatusNum">1</p>
              <p className="tabProfileStatusItem">reputation</p>
            </div>
            <div>
              <p className="tabProfileStatusNum">1</p>
              <p className="tabProfileStatusItem">reached</p>
            </div>
            <div>
              <p className="tabProfileStatusNum">1</p>
              <p className="tabProfileStatusItem">answers</p>
            </div>
            <div>
              <p className="tabProfileStatusNum">1</p>
              <p className="tabProfileStatusItem">questions</p>
            </div>
          </div>
        </div>
      </div>
      <div className="tabProfileRight">
        <div className="myAnswers">
          <h2 className="tabProfileTitle">answers</h2>
          <div className="tabProfileAnswers">
            <div className="tabProfileQAin">
              <div className="tabProfileQAitem">
                <div className="tabProfileQAvote">13</div>
                <p className="tabProfileQAtitle">Why is subtracting these two times...</p>
                <p className="tabProfileQAdate">Dec 28, 2022</p>
              </div>
            </div>
          </div>
        </div>
        <div className="myQuestions">
          <h2 className="tabProfileTitle">questions</h2>
          <div className="tabProfileQuestions">
            <div className="tabProfileQAin">
              <div className="tabProfileQAitem">
                <div className="tabProfileQAvote">10</div>
                <p className="tabProfileQAtitle">What are the correct version numbers...</p>
                <p className="tabProfileQAdate">Dec 28, 2022</p>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};

export default TabProfile;
