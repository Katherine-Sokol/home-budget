import React, { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import TransactionForm from "../components/TransactionForm";

function AddIncomePage() {
  const [categories, setCategories] = useState([]);
  const [form, setForm] = useState({
    categoryId: "",
    description: "",
    amount: "",
    incomeDate: new Date().toISOString().split("T")[0],
  });

  const navigate = useNavigate();

  useEffect(() => {
    const token = localStorage.getItem("token");
    console.log("Токен:", token);

    if (!token) {
      console.warn("Токен відсутній — запит не буде виконано");
      return;
    }

    fetch("http://localhost:8080/api/income-categories", {
      headers: {
        Authorization: `Bearer ${token}`,
      },
    })
      .then(async (res) => {
        if (!res.ok) {
          const text = await res.text();
          console.error("Помилка HTTP:", res.status, text);
          return;
        }
        const data = await res.json();
        setCategories(data);
      })
      .catch((err) => console.error("Fetch error:", err));
  }, []);

  const handleChange = (e) => {
    const { name, value } = e.target;
    setForm((prev) => ({ ...prev, [name]: value }));
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    const token = localStorage.getItem("token");

    const response = await fetch("http://localhost:8080/api/incomes", {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
        Authorization: `Bearer ${token}`,
      },
      body: JSON.stringify({
        categoryId: Number(form.categoryId),
        description: form.description,
        amount: parseFloat(form.amount),
        incomeDate: form.incomeDate,
      }),
    });

    if (response.ok) {
      alert("Дохід успішно додано!");
      navigate("/budget");
    } else {
      alert("Помилка при збереженні доходу");
    }
  };

    return (
      <TransactionForm
        type="income"
        form={form}
        handleChange={handleChange}
        handleSubmit={handleSubmit}
        categories={categories}
      />
    );
}

export default AddIncomePage;
