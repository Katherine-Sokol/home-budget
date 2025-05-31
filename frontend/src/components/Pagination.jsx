import React from "react";
import "./Pagination.css";

function Pagination({ currentPage, totalPages, onPageChange }) {
  const pages = Array.from({ length: totalPages }, (_, i) => i);

  return (
    <nav>
      <ul className="pagination">
        <li className={`page-item ${currentPage === 0 ? "disabled" : ""}`}>
          <button
            className="page-link"
            onClick={() => onPageChange(currentPage - 1)}
          >
            Назад
          </button>
        </li>
        {pages.map((p) => (
          <li
            key={p}
            className={`page-item ${p === currentPage ? "active" : ""}`}
          >
            <button
              className="page-link"
              onClick={() => onPageChange(p)}
            >
              {p + 1}
            </button>
          </li>
        ))}
        <li
          className={`page-item ${
            currentPage === totalPages - 1 ? "disabled" : ""
          }`}
        >
          <button
            className="page-link"
            onClick={() => onPageChange(currentPage + 1)}
          >
            Вперед
          </button>
        </li>
      </ul>
    </nav>
  );
}

export default Pagination;
