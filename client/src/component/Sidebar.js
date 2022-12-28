import React from "react";
import "../css/sidebar.css";
import SideBlogFeature from "./SideBlogFeature";

const Sidebar = () => {
  return (
    <div className="sidebarContainer">
      <SideBlogFeature />
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
