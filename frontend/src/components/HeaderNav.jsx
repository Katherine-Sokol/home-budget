import React from "react";
import { Link } from "react-router-dom";
import { Link as ScrollLink } from "react-scroll";

function HeaderNav(props) {
  return (
    <>
      <Link
        className="text-light text-decoration-none"
        to="/"
      >
        Головна
      </Link>
      <ScrollLink
        className="text-light text-decoration-none"
        to="features"
        smooth={true}
        duration={500}
      >
        Можливості
      </ScrollLink>
      <Link
        className="text-light text-decoration-none"
        to="/login"
      >
        Увійти
      </Link>
      <Link
        className="text-light text-decoration-none"
        to="/register"
      >
        Реєстрація
      </Link>
    </>
  );
}

export default HeaderNav;
