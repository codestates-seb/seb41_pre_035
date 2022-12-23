import React, { useState } from 'react'
import './style.css'
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faBars,faEarthAmericas } from "@fortawesome/free-solid-svg-icons";
import { useNavigate } from 'react-router-dom';

import { Link } from 'react-router-dom';

const HeaderHam = () => {
  const [seachmodal,setSeachmodal]=useState(false);
  const [menumodal,setMenumodal]=useState(false);
  let navigate = useNavigate();
  
  return (
    <>
        <div className='headerContainer'>
          <div>
            <p onClick={()=>{setMenumodal(!menumodal)}}><FontAwesomeIcon className="icon" icon={faBars}/></p>
          </div>
          <div className='logo'>
            stackoverflow
          </div>
          <div className='headerMenu'>
            <p>About</p>
            <p>Products</p>
            <p>For Teams</p>
          </div>
          <div className='search'>
            <input type='search' placeholder='Search....' onFocus={()=>setSeachmodal(!seachmodal)}/>
          </div>
          <div className='loginSignup'>
            <button className="login" onClick={()=>{ navigate('/login') }}>Log in</button>
            <button className="signup" onClick={()=>{ navigate('/signup') }}>Sign up</button>
          </div>
          {seachmodal? <div className="seachmodal">SEACH</div>:null}
          {menumodal?
          
          <div className='navContainer menumodal'>
            <div className='menu'>
                <ul>
                    <li className='menuHover'><Link to="/">Home　　　　　　　　　</Link></li>
                    <li>PUBLIC</li>
                        <ul className='subMenu'>
                        <li className='menuHover'><Link to="/questions"><FontAwesomeIcon icon={faEarthAmericas}/> Questions　　　　　</Link></li>
                            <li className='menuHover'><Link to="/tags">　 Tags　　　　　　　</Link></li>
                            <li className='menuHover'><Link to="/users">　 Users　　　　　　　</Link></li>
                            <li className='menuHover'>　 Companies</li>
                        </ul>
                    <li>COLLECTIVES</li>
                        <ul className='subMenu'>
                            <li>Explore Collectives</li>
                        </ul>
                    <li>TEAMS</li>
                        <ul className='subMenu'>
                            <li>Create free Team</li>
                        </ul>
                </ul>
            </div>
          </div>
          
          :null}
        </div>
    </>
  )
}

export default HeaderHam