import React from 'react';
import { Link } from "react-router-dom";
import { Link as ScrollLink } from "react-scroll";

function UserNav(props) {
    return (
      <nav>
        <Link
          className="me-3 py-2 text-light text-decoration-none"
          to="/api/incomes"
        >
          Мої доходи
        </Link>
        <Link
          className="me-3 py-2 text-light text-decoration-none"
          to="/api/expenses"
        >
          Мої витрати
        </Link>
        <Link
          className="me-3 py-2 text-light text-decoration-none"
          to="/api/analytics"
        >
          Аналітика
        </Link>
        <Link
          className="me-3 py-2 text-light text-decoration-none"
          to="/api/logout"
        >
          Вийти
        </Link>
      </nav>
    );
}

export default UserNav;