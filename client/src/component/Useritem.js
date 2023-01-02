import React, { useEffect } from "react";
import "../css/useritem.css";

const Useritem = (getUser) => {
  //   console.log(getUser.data);

  return (
    <div className="userItem">
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
    </div>
  );
};

export default Useritem;
