import React, { useState, useEffect } from "react";

function TransactionTable({ title, data, getCategoryName, onDelete }) {
  const [sortedData, setSortedData] = useState([]);
  const [sortConfig, setSortConfig] = useState({
    key: "date",
    direction: "desc",
  });

  useEffect(() => {
    sortData(sortConfig.key, sortConfig.direction);
  }, [data, sortConfig]);

  const sortData = (key, direction) => {
    const sorted = [...data].sort((a, b) => {
      const aVal =
        key === "category"
          ? getCategoryName(a.categoryId)
          : key === "date"
          ? new Date(a.date)
          : parseFloat(a[key]);

      const bVal =
        key === "category"
          ? getCategoryName(b.categoryId)
          : key === "date"
          ? new Date(b.date)
          : parseFloat(b[key]);

      if (aVal < bVal) return direction === "asc" ? -1 : 1;
      if (aVal > bVal) return direction === "asc" ? 1 : -1;
      return 0;
    });

    setSortedData(sorted);
  };

  const handleSort = (key) => {
    let direction = "asc";
    if (sortConfig.key === key && sortConfig.direction === "asc") {
      direction = "desc";
    }
    setSortConfig({ key, direction });
  };

  const formatDate = (dateStr) => {
    return new Date(dateStr).toLocaleDateString("uk-UA");
  };

  return (
        <table className="table table-bordered transaction-table text-center">
          <thead>
            <tr>
              <th
                onClick={() => handleSort("date")}
                style={{ cursor: "pointer" }}
              >
                Дата{" "}
                {sortConfig.key === "date"
                  ? sortConfig.direction === "asc"
                    ? "↑"
                    : "↓"
                  : ""}
              </th>
              <th
                onClick={() => handleSort("category")}
                style={{ cursor: "pointer" }}
              >
                Категорія{" "}
                {sortConfig.key === "category"
                  ? sortConfig.direction === "asc"
                    ? "↑"
                    : "↓"
                  : ""}
              </th>
              <th>Опис</th>
              <th
                onClick={() => handleSort("amount")}
                style={{ cursor: "pointer" }}
              >
                Сума{" "}
                {sortConfig.key === "amount"
                  ? sortConfig.direction === "asc"
                    ? "↑"
                    : "↓"
                  : ""}
              </th>
              <th>Дії</th>
            </tr>
          </thead>
          <tbody>
            {sortedData.map((item) => (
              <tr key={item.id}>
                <td>{formatDate(item.date)}</td>
                <td>{getCategoryName(item.categoryId)}</td>
                <td>{item.description}</td>
                <td>{Number(item.amount).toFixed(2)} грн</td>
                <td>
                  <button
                    className="btn btn-sm btn-danger"
                    onClick={() => onDelete(item.id)}
                  >
                    Видалити
                  </button>
                </td>
              </tr>
            ))}
          </tbody>
        </table>
  );
}

export default TransactionTable;
