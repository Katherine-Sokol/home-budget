import React from "react";

function FeaturesComponent() {
  return (
    <section className="features">
      <div className="feature-card">
        <h3>📊 Облік витрат</h3>
        <p>
          Додавайте покупки, розподіляйте за категоріями і бачте, куди йдуть
          гроші.
        </p>
      </div>
      <div className="feature-card">
        <h3>💵 Доходи</h3>
        <p>Відстежуйте джерела доходів та підтримуйте фінансовий баланс.</p>
      </div>
      <div className="feature-card">
        <h3>📅 Аналітика</h3>
        <p>Переглядайте звіти по місяцях, тижнях або днях.</p>
      </div>
      <div className="feature-card">
        <h3>📈 Графіки</h3>
        <p>Зрозуміла візуалізація допоможе швидше приймати рішення.</p>
      </div>
      <div className="feature-card">
        <h3>🗂️ Категорії</h3>
        <p>Створюйте власні категорії для кращого обліку.</p>
      </div>
      <div className="feature-card">
        <h3>🔐 Безпека</h3>
        <p>Ваші дані зберігаються захищено і не передаються третім особам.</p>
      </div>
    </section>
  );
}

export default FeaturesComponent;
