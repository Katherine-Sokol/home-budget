import React from "react";
import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import "./BudgetPage.css";
import LastTransactionsTable from "../components/LastTransactionsTable";

const API_URL = import.meta.env.VITE_API_URL;
const MAX_ROWS = 5;

function BudgetPage() {
  const [incomes, setIncomes] = useState([]);
  const [expenses, setExpenses] = useState([]);
  const [balance, setBalance] = useState(0);
  const navigate = useNavigate();

  useEffect(() => {
    const token = localStorage.getItem("token");

    const fetchData = async () => {
      try {
        const [incomesRes, expensesRes] = await Promise.all([
          fetch(
            `${API_URL}/incomes?sortField=incomeDate&sortDir=desc&page=0&limit=${MAX_ROWS}`,
            {
              headers: {
                Authorization: `Bearer ${token}`,
              },
            }
          ),
          fetch(
            `${API_URL}/expenses?sortField=expenseDate&sortDir=desc&page=0&limit=${MAX_ROWS}`,
            {
              headers: {
                Authorization: `Bearer ${token}`,
              },
            }
          ),
        ]);

        if (!incomesRes.ok || !expensesRes.ok) {
          console.error("Помилка при отриманні даних");
          return;
        }

        const incomesData = await incomesRes.json();
        const expensesData = await expensesRes.json();

        setIncomes(incomesData.content || []);
        setExpenses(expensesData.content || []);

        const totalIncome = incomesData.content.reduce(
          (sum, i) => sum + Number(i.amount),
          0
        );
        const totalExpense = expensesData.content.reduce(
          (sum, e) => sum + Number(e.amount),
          0
        );
        setBalance(totalIncome - totalExpense);
      } catch (error) {
        console.error("Помилка запиту: ", error);
      }
    };

    fetchData();
  }, []);

  const formatDate = (dateStr) => {
    const date = new Date(dateStr);
    return date.toLocaleDateString("uk-UA");
  };

  const recentIncomes = [
    ...[...incomes]
      .sort((a, b) => new Date(b.incomeDate) - new Date(a.incomeDate))
      .slice(0, MAX_ROWS),
    ...Array.from(
      { length: Math.max(0, MAX_ROWS - incomes.length) },
      (_, i) => ({
        id: `empty-income-${i}`,
        incomeDate: "",
        description: "",
        amount: "",
      })
    ),
  ];

  console.log(expenses);

  const recentExpenses = [
    ...[...expenses]
      .sort((a, b) => new Date(b.expenseDate) - new Date(a.expenseDate))
      .slice(0, MAX_ROWS),
    ...Array.from(
      { length: Math.max(0, MAX_ROWS - expenses.length) },
      (_, i) => ({
        id: `empty-expense-${i}`,
        expenseDate: "",
        description: "",
        amount: "",
      })
    ),
  ];

  return (
    <>
      <div className="container text-center p-3">
        <div className="row align-items-center balance-info mx-1">
          <div className="col">Поточний баланс: {balance.toFixed(2)} грн</div>
        </div>
      </div>
      <div className="container">
        <div className="row g-3">
          <div className="col-lg-6">
            <LastTransactionsTable
              title="Останні доходи"
              transactions={recentIncomes}
              type="income"
              onAddClick={() => navigate("/add-income")}
            />
          </div>
          <div className="col-lg-6">
            <LastTransactionsTable
              title="Останні витрати"
              transactions={recentExpenses}
              type="expense"
              onAddClick={() => navigate("/add-expense")}
            />
          </div>
        </div>
      </div>
    </>
  );
}

export default BudgetPage;
