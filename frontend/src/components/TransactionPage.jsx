import React, { useEffect, useState, useCallback, useMemo } from "react";
import TransactionTable from "./TransactionTable";
import Pagination from "./Pagination";
import TransactionPieChart from "./TransactionPieChart";
import DateFilter from "./DateFilter";
import { useNavigate } from "react-router-dom";

const API_URL = import.meta.env.VITE_API_URL;

function TransactionPage({
  apiBasePath,
  categoryPath,
  dateField,
  addButtonPath,
  pageTitle,
}) {
  const [data, setData] = useState([]);
  const [categories, setCategories] = useState([]);
  const [loading, setLoading] = useState(true);
  const [page, setPage] = useState(0);
  const [totalPages, setTotalPages] = useState(1);
  const [sortField, setSortField] = useState(dateField);
  const [sortDir, setSortDir] = useState("desc");
  const [startDate, setStartDate] = useState("");
  const [endDate, setEndDate] = useState("");
  const [colors, setColors] = useState([]);
  const navigate = useNavigate();

  const getCategoryName = useCallback(
    (id) => categories.find((c) => c.id === id)?.name || "—",
    [categories]
  );

  useEffect(() => {
    const fetchData = async () => {
      const token = localStorage.getItem("token");
      setLoading(true);
      const params = new URLSearchParams({
        sortField,
        sortDir,
        page,
        limit: 10,
      });
      const hasDateFilter = startDate && endDate;
      if (hasDateFilter) {
        params.append("start", startDate);
        params.append("end", endDate);
      }

      try {
        const [mainRes, categoriesRes] = await Promise.all([
          fetch(
            `${API_URL}/${apiBasePath}${
              hasDateFilter ? "/between-dates" : ""
            }?${params.toString()}`,
            {
              headers: { Authorization: `Bearer ${token}` },
            }
          ),
          fetch(`${API_URL}/${categoryPath}`, {
            headers: { Authorization: `Bearer ${token}` },
          }),
        ]);

        const mainData = await mainRes.json();
        const catData = await categoriesRes.json();

        setData(mainData.content || []);
        setTotalPages(mainData.totalPages);
        setCategories(catData);
      } catch (err) {
        console.error("Помилка при завантаженні:", err);
      } finally {
        setLoading(false);
      }
    };

    fetchData();
  }, [page, sortField, sortDir, startDate, endDate]);

  const handleDelete = async (id) => {
    const token = localStorage.getItem("token");
    await fetch(`${API_URL}/${apiBasePath}/${id}`, {
      method: "DELETE",
      headers: { Authorization: `Bearer ${token}` },
    });
    setData(data.filter((e) => e.id !== id));
  };

  const handleSort = (field) => {
    if (field === sortField) {
      setSortDir(sortDir === "asc" ? "desc" : "asc");
    } else {
      setSortField(field);
      setSortDir("asc");
    }
  };

  const breakdownByCategory = useMemo(() => {
    return categories
      .map((cat) => {
        const total = data
          .filter((e) => e.categoryId === cat.id)
          .reduce((sum, e) => sum + e.amount, 0);
        return { name: cat.name, value: total };
      })
      .filter((entry) => entry.value > 0);
  }, [categories, data]);

  useEffect(() => {
    setColors(
      breakdownByCategory.map(() => {
        const r = Math.floor(Math.random() * 256);
        const g = Math.floor(Math.random() * 256);
        const b = Math.floor(Math.random() * 256);
        return `rgb(${r},${g},${b})`;
      })
    );
  }, [breakdownByCategory]);

  const totalAmount = data.reduce((sum, e) => sum + e.amount, 0);

  return (
    <div className="container mt-4">
      <h2 className="balance-info title p-3">{pageTitle}</h2>
      <div className="transaction-form p-3">
        <button
          onClick={() => navigate(addButtonPath)}
          className="btn w-100 py-2 submit-button"
        >
          Додати транзакцію
        </button>

        <DateFilter
          startDate={startDate}
          endDate={endDate}
          onChangeStart={setStartDate}
          onChangeEnd={setEndDate}
          onReset={() => {
            setStartDate("");
            setEndDate("");
            setPage(0);
          }}
          onApply={() => setPage(0)}
        />

        <TransactionTable
          data={data.map((i) => ({
            ...i,
            date: i[dateField],
          }))}
          onDelete={handleDelete}
          onSort={handleSort}
          sortField={sortField}
          sortDir={sortDir}
          getCategoryName={getCategoryName}
        />
        <Pagination
          currentPage={page}
          totalPages={totalPages}
          onPageChange={setPage}
        />
      </div>

      <TransactionPieChart
        breakdown={breakdownByCategory}
        colors={colors}
        totalAmount={totalAmount}
      />
    </div>
  );
}

export default TransactionPage;
