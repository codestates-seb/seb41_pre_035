import React from "react";
import "../css/sidebar.css";
import SideBlogFeature from "./SideBlogFeature";

const Sidebar = () => {
  return (
    <>
      <div className="sidebarContainer">
        <SideBlogFeature />
        <div className="sideCustomFilters">
          <div className="sideFiltertagTitle">Custom Filters</div>
          <div className="sideFiterContents">Create a custom filter</div>
        </div>
        <div className="sideWatchedTags">
          <div className="sideFiltertagTitle">Watched Tags</div>
          <div className="sideTagContents">
            <i class="fa-solid fa-magnifying-glass"></i>
            <p>Watch tags to curate your list of questions.</p>
            <button>
              <i class="fa-solid fa-eye"></i> Watch a tag
            </button>
          </div>
        </div>
      </div>
    </>
  );
};

export default Sidebar;
