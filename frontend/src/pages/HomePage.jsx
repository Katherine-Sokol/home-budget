import React from "react";
import { Element } from "react-scroll";
import { Link } from "react-router-dom";
import { useAuth } from "../context/AuthContext";
import FeaturesComponent from "../components/FeaturesComponent";

function HomePage() {
  const { isLoggedIn } = useAuth();

  return (
    <>
      <section className="hero">
        <h1>Керуйте своїми фінансами просто і зручно</h1>
        <p>
          Облік витрат та доходів, аналітика, графіки, безпека і повний контроль
          над домашнім бюджетом.
        </p>
      </section>

      <Element name="features">
        <FeaturesComponent></FeaturesComponent>
      </Element>

      {!isLoggedIn && (
        <div className="cta">
          <Link to="/register">Зареєструватися</Link>
          <Link to="/login">Увійти</Link>
        </div>
      )}
    </>
  );
}

export default HomePage;
