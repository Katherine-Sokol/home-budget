import "./App.css";
import { BrowserRouter as Router, Route, Routes } from "react-router-dom";
import HomePage from "./pages/home-page/HomePage";
import LoginPage from "./pages/LoginPage";
import Header from "./components/Header";
import Footer from "./components/Footer";
import SigninPage from "./pages/SigninPage";

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
          path="/registrate"
          element={<SigninPage></SigninPage>}
        ></Route>
      </Routes>
      <Footer></Footer>
    </Router>
  );
}

export default App;
