import React, { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import TransactionForm from "../components/TransactionForm";
import Modal from "../components/Modal";

const API_URL = import.meta.env.VITE_API_URL;

function AddExpensePage() {
  const [modalMessage, setModalMessage] = useState(null);
  const [categories, setCategories] = useState([]);
  const [form, setForm] = useState({
    categoryId: "",
    description: "",
    amount: "",
    expenseDate: new Date().toISOString().split("T")[0],
  });

  const navigate = useNavigate();

  useEffect(() => {
    const token = localStorage.getItem("token");
    console.log("Токен:", token);

    if (!token) {
      console.warn("Токен відсутній — запит не буде виконано");
      return;
    }

    fetch(`${API_URL}/expense-categories`, {
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

    const response = await fetch(`${API_URL}/expenses`, {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
        Authorization: `Bearer ${token}`,
      },
      body: JSON.stringify({
        categoryId: Number(form.categoryId),
        description: form.description,
        amount: parseFloat(form.amount),
        expenseDate: form.expenseDate,
      }),
    });

    if (response.ok) {
      console.log("Успішна відповідь, встановлюємо модальне повідомлення");
      setModalMessage("Витрату успішно додано!");
    } else {
      setModalMessage("Помилка при збереженні витрати");
    }
  };

  const handleCloseModal = () => {
    setModalMessage(null);
    if (form.amount && form.categoryId) {
      navigate("/budget");
    }
  };
  console.log("modalMessage:", modalMessage);
  return (
    <>
      <TransactionForm
        type="expense"
        form={form}
        handleChange={handleChange}
        handleSubmit={handleSubmit}
        categories={categories}
      />
      {modalMessage && (
        <Modal
          message={modalMessage}
          onClose={handleCloseModal}
        />
      )}
    </>
  );
}

export default AddExpensePage;
