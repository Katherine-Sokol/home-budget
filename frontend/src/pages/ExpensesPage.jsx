import TransactionPage from "../components/TransactionPage";

const ExpensesPage = () => (
  <TransactionPage
    apiBasePath="expenses"
    categoryPath="expense-categories"
    dateField="expenseDate"
    addButtonPath="/add-expense"
    pageTitle="Усі витрати"
  />
);

export default ExpensesPage;
