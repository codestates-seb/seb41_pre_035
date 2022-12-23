import React from 'react'
import './style.css'

const Footer = () => {
  return (
    <>
        <div className='footerContainer'>
          <div className='footerMenus'>
            <div className='footerLogo'>logo</div>
            <div className='footerMenu'>
              <p>STACK OVERFLOW</p>
              <p>Questions</p>
              <p>Help</p>
            </div>
            <div className='footerMenu'>
              <p>PRODUCTS</p>
              <p>Teams</p>
              <p>Advertising</p>
            </div>
            <div className='footerMenu'>
              <p>COMPANY</p>
              <p>About</p>
              <p>Press</p>
            </div>
            <div className='footerMenu'>
              <p>STACK EXCHANGE NETWORK</p>
              <p>Technology</p>
              <p>Culture & recreation</p>
            </div>
          </div>
          <div>Site design / logo © 2022 Stack Exchange Inc; user contributions licensed under CC BY-SA. rev 2022.12.19.43125</div>
        </div>
    </>
  )
}

export default Footer