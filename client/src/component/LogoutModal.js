import "../css/logoutModal.css";
import { useSetRecoilState } from "recoil";
import { userState } from "../recoil";
import { useCookies } from "react-cookie";
import axios from "axios";

const LogoutModal = () => {
  const setUser = useSetRecoilState(userState);
  const accessToken = localStorage.getItem("accessToken");
  const [refreshToken, removeRefreshToken] = useCookies(["refreshToken"]);
  const url = "http://ec2-54-180-55-239.ap-northeast-2.compute.amazonaws.com:8080";

  const postLogoutData = () => {
    return axios
      .post(`${url}/auth/logout`, {
        headers: {
          "Content-Type": "application/json",
          Authorization: accessToken,
          refres: refreshToken.refreshToken,
        },
      })
      .then((res) => {
        if (res.status === 204) {
          setUser("");
          localStorage.removeItem("accessToken");
          removeRefreshToken("refreshToken");
          window.location.replace("/");
          console.log(res);
          console.log("로그아웃에 성공했습니다.");
        }
      })
      .catch((err) => {
        if (err.response.status >= 400) {
          alert("로그아웃에 실패했습니다. 다시 시도해주세요.");
        }
        console.log(err);
        console.log("로그아웃에 실패했습니다.");
      });
  };

  return (
    <div className="logoutModal">
      <button onClick={postLogoutData}>log out</button>
    </div>
  );
};

export default LogoutModal;
