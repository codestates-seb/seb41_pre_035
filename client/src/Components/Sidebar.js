import React from "react";

const Sidebar = () => {
  return (
    <div className="sidebarContainer">
      <div className="blogFeature">
        <div className="blog">
          <div className="blogFeatureTitle">The Overflow Blog</div>
          <div className="blogFeatureContents">contents</div>
        </div>
        <div className="feature">
          <div className="blogFeatureTitle">Featured on Meta</div>
          <div className="blogFeatureContents">contents</div>
        </div>
      </div>
      <div className="customFilters">
        <div className="filtertagTitle">Custom Filters</div>
        <div className="fiterContents">Create a custom filter</div>
      </div>
      <div className="watchedTags">
        <div className="filtertagTitle">Watched Tags</div>
        <div className="tagContents">Watch tags to curate your list of questions.</div>
      </div>
    </div>
  );
};

export default Sidebar;
