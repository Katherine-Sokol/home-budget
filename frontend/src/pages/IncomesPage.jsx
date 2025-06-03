import TransactionPage from "../components/TransactionPage";

const IncomesPage = () => (
  <TransactionPage
    apiBasePath="incomes"
    categoryPath="income-categories"
    dateField="incomeDate"
    addButtonPath="/add-income"
    pageTitle="Усі доходи"
  />
);

export default IncomesPage;
