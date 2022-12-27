import React from "react";
import "../css/sidebar.css";

const Sidebar = () => {
  return (
    <div className="sidebarContainer">
      <div className="sideBlogFeature">
        <div className="sideBlog">
          <div className="sideBlogFeatureTitle">The Overflow Blog</div>
          <div className="sideblogFeatureContents">contents</div>
        </div>
        <div className="sideFeature">
          <div className="sideBlogFeatureTitle">Featured on Meta</div>
          <div className="sideblogFeatureContents">contents</div>
        </div>
      </div>
      <div className="sideCustomFilters">
        <div className="sideFiltertagTitle">Custom Filters</div>
        <div className="sideFiterContents">Create a custom filter</div>
      </div>
      <div className="sideWatchedTags">
        <div className="sideFiltertagTitle">Watched Tags</div>
        <div className="sideTagContents">Watch tags to curate your list of questions.</div>
      </div>
    </div>
  );
};

export default Sidebar;
