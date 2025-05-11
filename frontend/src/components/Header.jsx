import React from "react";
import HeaderNav from "./HeaderNav";

function Header() {
  return (
    <header>
      <div className="nav-container">
        <div className="logo">
          <img
            src="../../public/logo.png"
            alt="logo"
            width="52px"
            height="52px"
          />
          <h2>Домашній Бюджет</h2>
        </div>
        <HeaderNav></HeaderNav>
      </div>
    </header>
  );
}

export default Header;
