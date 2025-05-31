import "./App.css";
import { BrowserRouter as Router, Route, Routes } from "react-router-dom";
import HomePage from "./pages/HomePage";
import LoginPage from "./pages/LoginPage";
import Header from "./components/Header";
import Footer from "./components/Footer";
import SigninPage from "./pages/SigninPage";
import BudgetPage from "./pages/BudgetPage";
import AddIncomePage from "./pages/AddIncomePage";
import AddExpensePage from "./pages/AddExpensePage";
import IncomesPage from "./pages/IncomesPage";
import ExpensesPage from "./pages/ExpensesPage";

function App() {
  return (
    <Router>
      <Header></Header>
      <Routes>
        <Route
          path="/"
          element={<HomePage></HomePage>}
        ></Route>
        <Route
          path="/login"
          element={<LoginPage></LoginPage>}
        ></Route>
        <Route
          path="/register"
          element={<SigninPage></SigninPage>}
        ></Route>
        <Route
          path="/budget"
          element={<BudgetPage></BudgetPage>}
        ></Route>
        <Route
          path="/add-income"
          element={<AddIncomePage></AddIncomePage>}
        />
        <Route
          path="/add-expense"
          element={<AddExpensePage></AddExpensePage>}
        />
        <Route
          path="/incomes"
          element={<IncomesPage></IncomesPage>}
        />
        <Route
          path="/expenses"
          element={<ExpensesPage></ExpensesPage>}
        />
      </Routes>
      <Footer></Footer>
    </Router>
  );
}

export default App;
