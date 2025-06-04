import React from "react";
import { useState } from "react";
import HeaderNav from "./HeaderNav";
import UserNav from "./UserNav";
import { useAuth } from "../context/AuthContext";
import "./Header.css";

function Header() {
  const { isLoggedIn, logout } = useAuth();
  const [menuOpen, setMenuOpen] = useState(false);
  const handleToggleMenu = (e) => {
    e.stopPropagation();
    setMenuOpen((prev) => !prev);
  };

  const handleCloseMenu = (e) => {
    setMenuOpen(false);
  };
  return (
    <header>
      <div className="nav-container">
        <a
          href="/"
          className="logo"
        >
          <img
            src="../../public/logo.png"
            alt="logo"
            width="52px"
            height="52px"
          />
          <h2>Домашній Бюджет</h2>
        </a>{" "}
        <button
          className="burger"
          onClick={handleToggleMenu}
        >
          ☰
        </button>
        <nav className={`nav-links ${menuOpen ? "open" : ""}`}>
          {isLoggedIn ? (
            <UserNav
              onLogout={logout}
              onLinkClick={handleCloseMenu}
            />
          ) : (
            <HeaderNav onLinkClick={handleCloseMenu} />
          )}
        </nav>
      </div>
    </header>
  );
}

export default Header;
