import "../css/form.css";
import { Link } from "react-router-dom";

export const InputNick = ({ value, classname }) => {
  return (
    <div className="inputWrapper">
      <label htmlFor="inputNick" className="inputLabel">
        Display name
      </label>
      <input type="text" {...value} id="inputNick" className={classname} />
    </div>
  );
};

export const InputEmail = ({ value, classname }) => {
  return (
    <div className="inputWrapper">
      <label htmlFor="inputEmail" className="inputLabel">
        Email
      </label>
      <input type="text" {...value} id="inputEmail" className={classname} />
    </div>
  );
};

export const InputPw = ({ value, classname, type }) => {
  if (type === "need") {
    return (
      <div className="inputWrapper">
        <div>
          <label htmlFor="inputPw" className="inputLabel">
            Password
          </label>
          <Link to="/recovery" className="inputPwLink">
            Forgot Password?
          </Link>
        </div>

        <input type="password" {...value} id="inputPw" className={classname} />
      </div>
    );
  }

  if (type === "noNeed") {
    return (
      <div className="inputWrapper">
        <label htmlFor="inputPw" className="inputLabel">
          Password
        </label>
        <input type="password" {...value} id="inputPw" className={classname} />
      </div>
    );
  }
};

export const InputBtn = ({ value, onclick }) => {
  return (
    <button type="submit" className="InputBtn" onClick={onclick}>
      {value}
    </button>
  );
};
