import React, { useState, useEffect } from "react";
import "../css/users.css";
import Useritem from "../component/Useritem";
import axios from "axios";
import Nav from "../component/Nav";

const Users = () => {
  const [search, setSearch] = useState("");
  const [usersInfo, setUsersInfo] = useState("");

  const searchUser = (e) => {
    const url = "http://ec2-54-180-55-239.ap-northeast-2.compute.amazonaws.com:8080";
    const token = localStorage.getItem("accessToken");

    // if (e.key === "Enter") {
    //   axios
    //     .get(`${url}/members?page=1&size=15`, {
    //       headers: {
    //         Accept: "application/json",
    //         Authorization: token,
    //       },
    //     })
    //     .then((res) => {
    //       let membersArr = res.data.data;
    //       console.log(membersArr);
    //     });
    // }
  };

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
        console.log(membersArr);
        setUsersInfo(membersArr);
      })
      .catch((err) => {
        console.log(err.response);
      });
  }, []);

  return (
    <div className="usersContainer">
      <Nav />
      <h2 className="usersTitle">Users</h2>
      <div className="usersSearch">
        <input type="search" className="searchBar" placeholder="Filter by user" onChange={(e) => setSearch(e.target.value)} value={search} onKeyDown={searchUser} />
      </div>
      <div className="usersBoard">
        {/* {usersInfo.map((el, i) => {
          return <div>{el[i].name}</div>;
        })} */}
        <Useritem />
        <Useritem />
        <Useritem />
        <Useritem />
        <Useritem />
        {/* <div className="userItem">
          <img src="/img/user.png" alt="" />
          <div className="userItemInfo">
            <div className="userItemName">Jon Skeet</div>
            <div className="userItemLoca">Reading, United Kingdom</div>
            <div className="userItemRepu">
              <p>1.4m</p>
              <p>847</p>
              <p>9025</p>
              <p>9126</p>
            </div>
            <div className="userItemTags">c#, java, .net</div>
          </div>
        </div> */}
      </div>
    </div>
  );
};

export default Users;
