import React from "react";

// - 회원 정보 수정
//   (Display Name, 프로필 사진, Location, About me, SNS Link)
// - Delete profile 클릭 -> 확인 문구 출력 -> 회원 탈퇴

const Settings = () => {
  return (
    <div>
      <div>
        <ul>
          <h2>PERSONAL INFORMATION</h2>
          <li>Edit profile</li>
          <li>Delete profile</li>
        </ul>
      </div>
      <div>edit & delete</div>
    </div>
  );
};

export default Settings;
