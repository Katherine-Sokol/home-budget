import React from "react";

function LastTransactionsTable({ title, transactions, type, onAddClick }) {
  const formatDate = (dateStr) => {
    if (!dateStr) return "";
    const date = new Date(dateStr);
    return date.toLocaleDateString("uk-UA");
  };

  return (
    <div className="card transaction-card text-center">
      <div className="card-body">
        <table className="table table-bordered transaction-table">
          <thead>
            <tr>
              <th colSpan="3">{title}</th>
            </tr>
          </thead>
          <tbody>
            {transactions.map((tx) => (
              <tr key={tx.id}>
                <td>{formatDate(tx.incomeDate || tx.expenseDate)}</td>
                <td>{tx.description}</td>
                <td>
                  {tx.amount
                    ? `${type === "income" ? "+" : "-"}${tx.amount} грн`
                    : ""}
                </td>
              </tr>
            ))}
          </tbody>
        </table>

        <button
          onClick={onAddClick}
          className="btn w-100 py-2 submit-button"
        >
          Додати транзакцію
        </button>
      </div>
    </div>
  );
}

export default LastTransactionsTable;
