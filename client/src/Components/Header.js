import React, { useState } from 'react'
import './style.css'
import { useNavigate } from 'react-router-dom';

const Header = () => {
  const [seachmodal,setSeachmodal]=useState(false);
  let navigate = useNavigate();
  
  return (
    <>
        <div className='headerContainer'>
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
        </div>
    </>
  )
}

export default Header