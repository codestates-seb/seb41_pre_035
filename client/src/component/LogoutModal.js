import "../css/logoutModal.css";
import { useSetRecoilState } from "recoil";
import { userState } from "../recoil";
import { useCookies } from "react-cookie";
import axios from "axios";

const LogoutModal = () => {
  const setUser = useSetRecoilState(userState);
  const accessToken = localStorage.getItem("accessToken");
  const [refreshToken, setRefreshToken, removeRefreshToken] = useCookies(["refreshToken"]);

  console.log(accessToken);
  console.log(refreshToken.refreshToken);

  const postLogoutData = () => {
    return axios
      .post(`/auth/logout`, {
        headers: {
          "Content-Type": "application/json",
          Authorization: accessToken,
          refresh: refreshToken.refreshToken,
        },
      })
      .then((res) => {
        if (res.status === 204) {
          setUser("");
          localStorage.removeItem("accessToken");
          removeRefreshToken("refreshToken");
          window.location.replace("/");
          console.log(res);
        }
      })
      .catch((err) => {
        if (err.response.status === 409) {
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
