import React from "react";
import "../css/nav.css";
import { Link } from "react-router-dom";

const Nav = () => {
  return (
    <>
      <div className="navMargin">
        <nav>
          <div className="navMenu">
            <ul>
              <li className="navMenuHover">
                <Link to="/">Home　　　　　　　　　</Link>
              </li>
              <li>PUBLIC</li>
              <ul className="navSubMenu">
                <li className="navMenuHover">
                  <Link to="/questions">
                    <i className="fa-solid fa-earth-americas"></i> Questions　　　　　
                  </Link>
                </li>
                <li className="navMenuHover">
                  <Link to="/tags">　　Tags　　　　　　　</Link>
                </li>
                <li className="navMenuHover">
                  <Link to="/users">　　Users　　　　　　　</Link>
                </li>
                <li className="navMenuHover">　　Companies</li>
              </ul>
              <li>COLLECTIVES</li>
              <ul className="navSubMenu">
                <li>Explore Collectives</li>
              </ul>
              <li>TEAMS</li>
              <ul className="navSubMenu">
                <li>Create free Team</li>
              </ul>
            </ul>
          </div>
        </nav>
      </div>
    </>
  );
};

export default Nav;
