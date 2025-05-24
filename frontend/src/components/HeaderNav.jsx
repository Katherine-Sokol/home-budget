import React from "react";
import { Link } from "react-router-dom";
import { Link as ScrollLink } from "react-scroll";

function HeaderNav(props) {
  return (
    <nav>
      <Link
        className="me-3 py-2 text-light text-decoration-none"
        to="/"
      >
        Головна
      </Link>
      <ScrollLink
        className="me-3 py-2 text-light text-decoration-none"
        to="features"
        smooth={true}
        duration={500}
      >
        Можливості
      </ScrollLink>
      <Link
        className="me-3 py-2 text-light text-decoration-none"
        to="/api/login"
      >
        Увійти
      </Link>
      <Link
        className="me-3 py-2 text-light text-decoration-none"
        to="/api/register"
      >
        Реєстрація
      </Link>
    </nav>
  );
}

export default HeaderNav;
