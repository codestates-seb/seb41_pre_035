import React, { useState, useEffect } from "react";
import "../../css/mypage/myHeader.css";
import { Link, useParams } from "react-router-dom";
import { userState } from "../../recoil";
import { useRecoilValue } from "recoil";
import axios from "axios";

const MyHeader = () => {
  const { id } = useParams();
  const user = useRecoilValue(userState);
  const [userData, setUserData] = useState("");

  useEffect(() => {
    const url = "http://ec2-54-180-55-239.ap-northeast-2.compute.amazonaws.com:8080";

    const token = localStorage.getItem("accessToken");

    axios
      .get(`${url}/members?page=1&size=15`, {
        headers: {
          Accept: "application/json",
          Authorization: token,
        },
      })
      .then((res) => {
        let membersArr = res.data.data;
        let userData = membersArr.filter((el) => el.memberId == user.memberId);
        console.log(userData[0]);
        setUserData(userData[0]);
      })
      .catch((err) => {
        console.log(err.response);
      });
  }, []);

  return (
    <div className="myHeader">
      <div className="myHeaderImg">
        <img src={process.env.PUBLIC_URL + "/img/user.png"} alt="" />
      </div>
      <div className="myHeaderInfo">
        <div className="myHeaderInfoName">kimcoding{/* {userData.name} */}</div>
        <div className="myHeaderInfoDate">
          <p>
            <i className="fa-solid fa-cake-candles"></i> Member for 8 days
          </p>
          <p>
            <i className="fa-regular fa-clock"></i> Last seen this week
          </p>
        </div>
      </div>
      <div>
        {/* <button className="myHeaderEditBtn">
          <i className="fa-solid fa-pencil"></i> Edit profile
        </button> */}
        {/* 해당 유저가 아니면 Edit profile 숨겨야함 */}
      </div>
    </div>
  );
};

export default MyHeader;
