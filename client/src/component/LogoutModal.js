import "../css/logoutModal.css";
import { useSetRecoilState } from "recoil";
import { userState } from "../recoil";

const LogoutModal = ({ removeRefreshToken }) => {
  const setUser = useSetRecoilState(userState);

  const handleLogout = () => {
    setUser("");
    localStorage.removeItem("accessToken");
    removeRefreshToken("refreshToken");
    window.location.replace("/");
  };

  return (
    <div className="logoutModal">
      <button onClick={handleLogout}>log out</button>
    </div>
  );
};

export default LogoutModal;
