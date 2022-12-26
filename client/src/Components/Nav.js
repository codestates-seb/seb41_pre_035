import React from "react";
import { Link } from "react-router-dom";

const Nav = () => {
  return (
    <>
      <div className="navMargin">
        <div className="navContainer">
          <div className="menu">
            <ul>
              <li className="menuHover">
                <Link to="/">Home　　　　　　　　　</Link>
              </li>
              <li>PUBLIC</li>
              <ul className="subMenu">
                <li className="menuHover">
                  <Link to="/questions">
                    <i className="fa-solid fa-earth-americas"></i> Questions　　　　　
                  </Link>
                </li>
                <li className="menuHover">
                  <Link to="/tags">　 Tags　　　　　　　</Link>
                </li>
                <li className="menuHover">
                  <Link to="/users">　 Users　　　　　　　</Link>
                </li>
                <li className="menuHover">　 Companies</li>
              </ul>
              <li>COLLECTIVES</li>
              <ul className="subMenu">
                <li>Explore Collectives</li>
              </ul>
              <li>TEAMS</li>
              <ul className="subMenu">
                <li>Create free Team</li>
              </ul>
            </ul>
          </div>
        </div>
      </div>
    </>
  );
};

export default Nav;
