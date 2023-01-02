import React, { useState, useEffect } from "react";
import "../../css/mypage/editProfile.css";
import { useParams } from "react-router-dom";
import MDEditor from "@uiw/react-md-editor";
import { userState } from "../../recoil";
import { useRecoilValue, useRecoilState } from "recoil";
import axios from "axios";

// - 회원 정보 수정
//   (Display Name, 프로필 사진, Location, About me, SNS Link)

// 이미지 관련 useState 작성, onEdit 함수 작성, onSubmit 함수 작성
// Link 에디터 삽입, Link useState 작성

const EditProfile = () => {
  const { id } = useParams();

  const [idData, setIdData] = useState(null);
  const [user, setUser] = useRecoilState(userState);

  const [displayname, setDisplayname] = useState("");
  const [location, setLocation] = useState("");

  const [userData, setUserData] = useState("");

  // useEffect 사용?
  const postLoginData = () => {
    const url = "http://ec2-54-180-55-239.ap-northeast-2.compute.amazonaws.com:8080";
    const token = localStorage.getItem("accessToken");

    return axios
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
        setUserData(userData[0]); // 현재 로그인 유저의 데이터
      })
      .catch((err) => {
        console.log(err.response);
      });
  };

  const userName = idData?.nickname;

  const onChange = async (e) => {
    // setUploadImg(true);
    // setProfileFile(e.target.files[0]);
    // await fetchUploadImage(e.target.files[0]).then((path) => {
    //   setUserProfile(path);
    // });
  };

  const onEdit = () => {
    const formData = new FormData();
    formData.append("memberId", 5);
    formData.append("name", displayname);

    const url = "http://ec2-54-180-55-239.ap-northeast-2.compute.amazonaws.com:8080";

    const token = localStorage.getItem("accessToken");

    // 편집할 데이터를 formData 안에 넣어 patch
    return axios
      .patch(`${url}/members/5`, {
        headers: {
          "Content-Type": "application/json;charset=UTF-8",
          Accept: "application/json",
          Authorization: token,
        },
        body: formData,
      })
      .then((res) => {
        if (!res.ok) {
          if (res.status === 400) alert("빈 칸을 채워주세요.");
          throw Error("해당 리소스에 대한 데이터를 가져올 수 없습니다.");
        }
        if (res.status === 201) alert("프로필이 편집됐습니다.");
        return res;
      })
      .catch((error) => {
        console.log(error.response); // error.message와 어떤 차이?
      });
  };

  const onSubmit = () => {
    // if (!uploadImg) alert("이미지를 업로드해주세요.");
    // if (uploadImg) {
    //   onEdit();
    //   navigate(`/user/${id}`);
    //   return location.reload();
    // }
    // // return ;
    // else {
    //   return false;
    // }
  };

  return (
    <div className="editProfile">
      <div className="editProfile">
        {/* <button onClick={onEdit}>edit</button> */}
        <h2 className="editProfileTitle">Edit your profile</h2>
        <hr></hr>
        <h3 className="editProfileSubTitle">Public information</h3>
        <div>
          <div className="editProfileInfo">
            <div className="editProfileInfoIn">
              <div>
                <p className="editProfileItem">Profile image</p>
                <img src={process.env.PUBLIC_URL + "/img/user.png"} alt="" />
                <div>Change picture</div>
                <input type="file" accept="image/jpeg" onChange={onChange} />
              </div>
              <div>
                <p className="editProfileItem">Display name</p>
                <input type="text" value={displayname} onChange={(e) => setDisplayname(e.target.value)} />
              </div>
              <div>
                <p className="editProfileItem">Location</p>
                <input type="text" value={location} onChange={(e) => setLocation(e.target.value)} />
              </div>
              <div>
                <p className="editProfileItem">About me</p>
                <div>
                  <MDEditor />
                </div>
              </div>
            </div>
          </div>
        </div>
        <div>
          <p className="editProfileSubTitle">Links</p>
          <div className="editProfileLinks">
            <div className="editProfileLinksIn">
              <div>
                <p className="editProfileItem">Website link</p>
                <input type="text"></input>
              </div>
              <div>
                <p className="editProfileItem">Twitter link or username</p>
                <input type="text"></input>
              </div>
              <div>
                <p className="editProfileItem">GitHub link or username</p>
                <input type="text"></input>
              </div>
            </div>
          </div>
        </div>
      </div>
      <div className="editProfileBtns">
        <button className="editProfileSave" onClick={postLoginData}>
          Save profile
        </button>
        <button className="editProfileCancel">Cancel</button>
      </div>
    </div>
  );
};

export default EditProfile;
