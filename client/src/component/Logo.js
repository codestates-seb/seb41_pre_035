import { Link } from "react-router-dom";
import "../css/logo.css";

const Logo = ({ text }) => {
  return (
    <div>
      <Link to="/" className="logoWrapper">
        <i className="fa-brands fa-stack-overflow"></i>
        {text ? (
          <div className="logoText">
            <div className="logoText1">stack</div>
            <div className="logoText2">overflow</div>
          </div>
        ) : null}
      </Link>
    </div>
  );
};

export default Logo;
