import "../css/socialbtn.css";

const SocialBtn = ({ text }) => {
  return (
    <div className="socialBtnWrapper">
      <button className="socialBtn socialBtnGoogle">
        <i className="fa-brands fa-google"></i>
        <div className="socialBtnText socialBtnGoogleText">{text} with Google</div>
      </button>
      <button className="socialBtn socialBtnGithub">
        <i className="fa-brands fa-github"></i>
        <div className="socialBtnText socialBtnGithubText">{text} with Github</div>
      </button>
      <button className="socialBtn socialBtnFacebook">
        <i className="fa-brands fa-square-facebook"></i>
        <div className="socialBtnText socialBtnFacebookText">{text} with Facebook</div>
      </button>
    </div>
  );
};

export default SocialBtn;
