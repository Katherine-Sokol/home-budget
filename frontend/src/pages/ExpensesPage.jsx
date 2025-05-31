import TransactionPage from "../components/TransactionPage";

const ExpensesPage = () => (
  <TransactionPage
    type="expenses"
    apiBasePath="expenses"
    categoryPath="expense-categories"
    dateField="expenseDate"
    addButtonPath="/add-expense"
    pageTitle="Усі витрати"
  />
);

export default ExpensesPage;

// import React, { useEffect, useState, useCallback, useMemo } from "react";
// import TransactionTable from "../components/TransactionTable";
// import Pagination from "../components/Pagination";
// import { Spinner } from "reactstrap";
// import { useNavigate } from "react-router-dom";
// import { PieChart, Pie, Cell, Tooltip, Legend } from "recharts";

// function ExpensesPage() {
//   const [expenses, setExpenses] = useState([]);
//   const [categories, setCategories] = useState([]);
//   const [loading, setLoading] = useState(true);
//   const [page, setPage] = useState(0);
//   const [totalPages, setTotalPages] = useState(1);
//   const [sortField, setSortField] = useState("expenseDate");
//   const [sortDir, setSortDir] = useState("desc");
//   const navigate = useNavigate();
//   const [startDate, setStartDate] = useState("");
//   const [endDate, setEndDate] = useState("");
//   const [colors, setColors] = useState([]);

//   const getCategoryName = useCallback(
//     (id) => categories.find((c) => c.id === id)?.name || "—",
//     [categories]
//   );

//   useEffect(() => {
//     const fetchExpensesAndCategories = async () => {
//       const token = localStorage.getItem("token");
//       setLoading(true);

//       const params = new URLSearchParams({
//         sortField,
//         sortDir,
//         page,
//         limit: 10,
//       });

//       const hasDateFilter = startDate && endDate;

//       if (hasDateFilter) {
//         params.append("start", startDate);
//         params.append("end", endDate);
//       }

//       try {
//         const [expensesRes, categoriesRes] = await Promise.all([
//           fetch(
//             `http://localhost:8080/api/expenses${
//               hasDateFilter ? "/between-dates" : ""
//             }?${params.toString()}`,
//             {
//               headers: { Authorization: `Bearer ${token}` },
//             }
//           ),
//           fetch("http://localhost:8080/api/expense-categories", {
//             headers: { Authorization: `Bearer ${token}` },
//           }),
//         ]);

//         const expensesData = await expensesRes.json();
//         const categoriesData = await categoriesRes.json();

//         setExpenses(expensesData.content || []);
//         setTotalPages(expensesData.totalPages);
//         setCategories(categoriesData);
//       } catch (err) {
//         console.error("Помилка при завантаженні даних:", err);
//       } finally {
//         setLoading(false);
//       }
//     };

//     fetchExpensesAndCategories();
//   }, [page, sortField, sortDir, startDate, endDate]);

//   const handleDelete = async (id) => {
//     const token = localStorage.getItem("token");
//     await fetch(`http://localhost:8080/api/expenses/${id}`, {
//       method: "DELETE",
//       headers: { Authorization: `Bearer ${token}` },
//     });
//     setExpenses(expenses.filter((e) => e.id !== id));
//   };

//   const handleSort = (field) => {
//     if (field === sortField) {
//       setSortDir(sortDir === "asc" ? "desc" : "asc");
//     } else {
//       setSortField(field);
//       setSortDir("asc");
//     }
//   };

//   const getRandomColor = () => {
//     const letters = "0123456789ABCDEF";
//     let color = "#";
//     for (let i = 0; i < 6; i++) {
//       color += letters[Math.floor(Math.random() * 16)];
//     }
//     return color;
//   };

//   const breakdownByCategory = useMemo(() => {
//     return categories
//       .map((cat) => {
//         const total = expenses
//           .filter((e) => e.categoryId === cat.id)
//           .reduce((sum, e) => sum + e.amount, 0);
//         return {
//           name: cat.name,
//           value: total,
//         };
//       })
//       .filter((entry) => entry.value > 0);
//   }, [categories, expenses]);

//   useEffect(() => {
//     setColors(breakdownByCategory.map(() => getRandomColor()));
//   }, [breakdownByCategory]);

//   const totalAmount = expenses.reduce((sum, e) => sum + e.amount, 0);

//   return (
//     <div className="container mt-4">
//       <h2 className="mb-4">Усі витрати</h2>
//       <button
//         onClick={() => navigate("/add-expense")}
//         className="btn w-100 py-2 submit-button"
//       >
//         Додати транзакцію
//       </button>

//       <div className="row mb-3">
//         <div className="col">
//           <label>З:</label>
//           <input
//             type="date"
//             value={startDate}
//             onChange={(e) => setStartDate(e.target.value)}
//             className="form-control"
//           />
//         </div>
//         <div className="col">
//           <label>По:</label>
//           <input
//             type="date"
//             value={endDate}
//             onChange={(e) => setEndDate(e.target.value)}
//             className="form-control"
//           />
//         </div>
//         <div className="col d-flex align-items-end">
//           <div className="d-flex w-100 gap-2">
//             <button
//               onClick={() => setPage(0)}
//               className="btn btn-primary w-50"
//             >
//               Застосувати фільтр
//             </button>
//             <button
//               onClick={() => {
//                 setStartDate("");
//                 setEndDate("");
//                 setPage(0);
//               }}
//               className="btn btn-secondary w-50"
//             >
//               Скинути
//             </button>
//           </div>
//         </div>
//       </div>

//       {loading ? (
//         <Spinner />
//       ) : (
//         <>
//           <TransactionTable
//             data={expenses.map((i) => ({
//               ...i,
//               date: i.expenseDate,
//             }))}
//             onDelete={handleDelete}
//             onSort={handleSort}
//             sortField={sortField}
//             sortDir={sortDir}
//             getCategoryName={getCategoryName}
//           />
//           <Pagination
//             currentPage={page}
//             totalPages={totalPages}
//             onPageChange={setPage}
//           />

//           <div className="mt-5">
//             <h4>Загальні витрати: {totalAmount.toFixed(2)} грн</h4>
//             <PieChart
//               width={400}
//               height={300}
//             >
//               <Pie
//                 data={breakdownByCategory}
//                 dataKey="value"
//                 nameKey="name"
//                 cx="50%"
//                 cy="50%"
//                 outerRadius={100}
//                 label
//               >
//                 {breakdownByCategory.map((entry, index) => (
//                   <Cell
//                     key={`cell-${index}`}
//                     fill={colors[index]}
//                   />
//                 ))}
//               </Pie>
//               <Tooltip />
//               <Legend />
//             </PieChart>
//           </div>
//         </>
//       )}
//     </div>
//   );
// }

// export default ExpensesPage;
