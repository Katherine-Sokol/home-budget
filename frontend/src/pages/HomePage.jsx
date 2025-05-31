import React from "react";
import { Element } from "react-scroll";
import FeaturesComponent from "../components/FeaturesComponent";

function HomePage(props) {
  return (
    <>
      {/* <!-- Hero --> */}
      <section className="hero">
        <h1>Керуйте своїми фінансами просто і зручно</h1>
        <p>
          Облік витрат та доходів, аналітика, графіки, безпека і повний контроль
          над домашнім бюджетом.
        </p>
      </section>

      {/* <!-- Features --> */}
      <Element name="features">
        <FeaturesComponent></FeaturesComponent>
      </Element>

      {/* <!-- Call to Action --> */}
      <div className="cta">
        <a href="/register">Зареєструватися</a>
        <a href="/login">Увійти</a>
      </div>
    </>
  );
}

export default HomePage;
