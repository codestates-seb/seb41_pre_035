import React from "react";
import "../css/sideblogfeature.css";

const SideBlogFeature = () => {
  return (
    <div className="sideBlogFeature">
      <div className="sideBlog">
        <div className="sideBlogFeatureTitle">The Overflow Blog</div>
        <div className="sideblogFeatureContents">
          <li>
            <i class="fa-solid fa-pen"></i>
            <p>Remote work is killing big offices. Cities must change to survive</p>
          </li>
          <li>
            <i class="fa-solid fa-pen"></i>
            <p>You should be reading academic computer science papers</p>
          </li>
        </div>
      </div>
      <div className="sideFeature">
        <div className="sideBlogFeatureTitle">Featured on Meta</div>
        <div className="sideblogFeatureContents">
          <li>
            <i class="fa-regular fa-message"></i>
            <p>Navigation and UI research starting soon</p>
          </li>
          <li>
            <i class="fa-brands fa-stack-overflow"></i>
            <p>Temporary policy: ChatGPT is banned</p>
          </li>
          <li>
            <i class="fa-brands fa-stack-overflow"></i>
            <p>Should we burninate the [choice] tag?</p>
          </li>
        </div>
      </div>
    </div>
  );
};

export default SideBlogFeature;
