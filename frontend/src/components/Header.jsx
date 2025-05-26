import React from "react";
import { useState, useEffect } from "react";
import HeaderNav from "./HeaderNav";
import UserNav from "./UserNav";

function Header() {
  const [isLoggedIn, setIsLoggedIn] = useState(false);

  useEffect(() => {
    // Проверка, есть ли токен
    const token = localStorage.getItem("token");
    setIsLoggedIn(!!token);
  }, []);
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
        {isLoggedIn ? <UserNav /> : <HeaderNav />}
      </div>
    </header>
  );
}

export default Header;
