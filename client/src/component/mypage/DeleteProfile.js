import React, { useState } from "react";
import "../../css/mypage/deleteProfile.css";
import { useNavigate } from "react-router-dom";
import axios from "axios";

const DeleteProfile = () => {
  const [checkBox, setCheckBox] = useState(false);
  const [disabled, setDisabled] = useState(true);
  const navigate = useNavigate();

  const changeHandler = (e) => {
    setCheckBox(e.target.checked);
    setDisabled(false);
  };

  const logout = () => {
    //     localStorage.clear();
  };

  const onRemove = (e) => {
    const url = "http://ec2-54-180-55-239.ap-northeast-2.compute.amazonaws.com:8080";
    const token = localStorage.getItem("accessToken");

    e.preventDefault();
    if (window.confirm("확인을 누르면 회원 정보가 삭제됩니다.")) {
      axios
        .delete(`${url}/members/20`, {
          headers: {
            Authorization: token,
          },
        })
        .then(() => {
          localStorage.clear();
          alert("그동안 이용해주셔서 감사합니다.");
          navigate("/");
        })
        .catch((err) => alert(err.response.data.message));
    } else {
      return;
    }
  };

  return (
    <div className="deleteProfile">
      <h2 className="deleteProfileTitle">DeleteProfile</h2>
      <hr></hr>
      <p>Before confirming that you would like your profile deleted, we'd like to take a moment to explain the implications of deletion:</p>
      <p>Deletion is cirreversible, and you will have no way to regain any of your original content, should this deletion be carried out and you change your mind later on.</p>
      <p>
        Your questions and answers will remain on the site, but will be disassociated and anonymized (the author will be listed as "user20820051") and will not indicate your authorship even if you
        later return to the sitec.
      </p>
      <p>
        Your questions and answers will remain on the site, but will be disassociated and anonymized (the author will be listed as "user20820051") and will not indicate your authorship even if you
        later return to the site.
      </p>
      <div className="deleteProfileCheck">
        <input type="checkbox" id="check" onChange={changeHandler} checked={checkBox} />
        <div>I have read the information stated above and understand the implications of having my profile deleted. I wish to proceed with the deletion of my profile.</div>
      </div>
      <button className="deleteProfileBtn" disabled={disabled} onClick={(onRemove, logout)}>
        Delete profile
      </button>
    </div>
  );
};

export default DeleteProfile;
