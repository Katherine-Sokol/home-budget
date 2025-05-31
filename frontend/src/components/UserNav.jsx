import React from "react";
import { Link, useNavigate } from "react-router-dom";

function UserNav({ onLogout, onLinkClick }) {
  const navigate = useNavigate();

  const handleLogout = () => {
    localStorage.removeItem("token");
    onLogout(); // Сигнал родителю, что пользователь вышел
    navigate("/");
    onLinkClick?.();
  };
  return (
    <>
      <Link
        className="text-light text-decoration-none"
        to="/"
        onClick={onLinkClick}
      >
        Головна
      </Link>
      <Link
        className="text-light text-decoration-none"
        to="/budget"
        onClick={onLinkClick}
      >
        Мій кабінет
      </Link>
      <Link
        className="text-light text-decoration-none"
        to="/incomes"
        onClick={onLinkClick}
      >
        Мої доходи
      </Link>
      <Link
        className="text-light text-decoration-none"
        to="/expenses"
        onClick={onLinkClick}
      >
        Мої витрати
      </Link>
      <span
        className="text-light text-decoration-none"
        style={{ cursor: "pointer" }}
        onClick={handleLogout}
      >
        Вийти
      </span>
    </>
  );
}

export default UserNav;
