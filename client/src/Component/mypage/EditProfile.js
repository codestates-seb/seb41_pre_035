import React from "react";

// - 회원 정보 수정
//   (Display Name, 프로필 사진, Location, About me, SNS Link)
// - Delete profile 클릭 -> 확인 문구 출력 -> 회원 탈퇴

const EditProfile = () => {
  return (
    <div>
      <h2>Edit your profile</h2>
      <h3>Public information</h3>
      <div>
        <div>
          <p>Profile image</p>
          <img></img>
          <div>Change picture</div>
        </div>
        <div>
          <p>Display name</p>
          <input></input>
        </div>
        <div>
          <p>Location</p>
          <input></input>
        </div>
        <div>
          <p>About me</p>
          <div>에디터</div>
        </div>
        <div>
          <p>Links</p>
          <div>
            <div>
              <p>Website link</p>
              <input></input>
            </div>
            <div>
              <p>Twitter link or username</p>
              <input></input>
            </div>
            <div>
              <p>GitHub link or username</p>
              <input></input>
            </div>
          </div>
        </div>
        <div>
          <button>Save profile</button>
          <button>Cancel</button>
        </div>
      </div>
    </div>
  );
};

export default EditProfile;
