import React from "react";
import "./TransactionForm.css";

const TransactionForm = ({
  type,
  form,
  handleChange,
  handleSubmit,
  categories,
}) => {
  const isIncome = type === "income";
  const title = isIncome ? "Додати дохід" : "Додати витрату";
  const dateFieldName = isIncome ? "incomeDate" : "expenseDate";

  return (
    <div className="container transaction-container">
      <h1 className="balance-info title text-center p-3">{title}</h1>
      <form
        className="row g-3 transaction-form justify-content-center"
        onSubmit={handleSubmit}
      >
        <div className="mb-3">
          <label className="form-label">Категорія</label>
          <select
            className="form-select"
            name="categoryId"
            value={form.categoryId}
            onChange={handleChange}
            required
          >
            <option value="">Оберіть категорію</option>
            {categories.map((cat) => (
              <option
                key={cat.id}
                value={cat.id}
              >
                {cat.name}
              </option>
            ))}
          </select>
        </div>

        <div className="col-12">
          <label className="form-label">Опис</label>
          <input
            type="text"
            className="form-control"
            name="description"
            value={form.description}
            onChange={handleChange}
          />
        </div>

        <div className="col-sm-6">
          <label className="form-label">Дата</label>
          <input
            type="date"
            className="form-control"
            name={dateFieldName}
            value={form[dateFieldName]}
            onChange={handleChange}
            required
          />
        </div>

        <div className="col-sm-6">
          <label className="form-label">Сума</label>
          <input
            type="number"
            step="0.01"
            className="form-control"
            name="amount"
            value={form.amount}
            onChange={handleChange}
            required
          />
        </div>

        <div className="col-6 p-2">
          <button
            type="submit"
            className="btn submit-button w-100"
          >
            {title}
          </button>
        </div>
      </form>
    </div>
  );
};

export default TransactionForm;
