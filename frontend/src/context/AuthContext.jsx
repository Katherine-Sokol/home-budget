import React, { createContext, useContext, useEffect, useState } from "react";

const AuthContext = createContext();

export const AuthProvider = ({ children }) => {
  const [isLoggedIn, setIsLoggedIn] = useState(!!localStorage.getItem("token"));
  const [logoutTimer, setLogoutTimer] = useState(null);

  const parseJwt = (token) => {
    try {
      const base64Url = token.split(".")[1];
      const base64 = base64Url.replace(/-/g, "+").replace(/_/g, "/");
      const jsonPayload = decodeURIComponent(
        atob(base64)
          .split("")
          .map((c) => "%" + ("00" + c.charCodeAt(0).toString(16)).slice(-2))
          .join("")
      );
    //   console.log("JWT decoded payload:", JSON.parse(jsonPayload));
      return JSON.parse(jsonPayload);
    } catch (e) {
      return null;
    }
  };

  const startAutoLogoutTimer = (token) => {
    const decoded = parseJwt(token);
    if (decoded?.exp) {
      const expirationTime = decoded.exp * 1000; // в миллисекундах
      const timeLeft = expirationTime - Date.now();
      if (timeLeft > 0) {
        const timer = setTimeout(() => {
          logout();
        }, timeLeft);
        setLogoutTimer(timer);
      } else {
        logout();
      }
    }
  };

  const login = (token) => {
    localStorage.setItem("token", token);
    setIsLoggedIn(true);
    startAutoLogoutTimer(token);
  };

  const logout = () => {
    localStorage.removeItem("token");
    setIsLoggedIn(false);
    if (logoutTimer) {
      clearTimeout(logoutTimer);
      setLogoutTimer(null);
    }
  };

  useEffect(() => {
    const token = localStorage.getItem("token");
    if (token) {
      const decoded = parseJwt(token);
      if (decoded?.exp && decoded.exp * 1000 > Date.now()) {
        setIsLoggedIn(true);
        startAutoLogoutTimer(token);
      } else {
        logout(); // если истёк — сразу выходим
      }
    }
  }, []);

  return (
    <AuthContext.Provider value={{ isLoggedIn, login, logout }}>
      {children}
    </AuthContext.Provider>
  );
};

// Хук для удобного доступа
export const useAuth = () => useContext(AuthContext);
