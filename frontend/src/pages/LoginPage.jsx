import React, { useState } from "react";
import { useNavigate } from "react-router-dom";

function LoginPage(props) {
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const navigate = useNavigate();

  const handleSubmit = async (e) => {
    e.preventDefault();

    const response = await fetch("http://localhost:8080/api/login", {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify({ username, password }),
      credentials: "include",
    });

    const data = await response.json();

    if (response.ok) {
      // можно сохранить токен в localStorage/cookies
      localStorage.setItem("token", data.token);
      navigate("/budget"); // Перенаправляем на защищённую страницу
    } else {
      alert(data.message || "Помилка входу");
    }
  };

  return (
    <>
      <main className="d-flex align-items-center py-4 body-container">
        <div className="form-signin w-100 m-auto">
          <form>
            <h1 className="h3 mb-3 fw-normal text-center">Форма входу</h1>
            <div className="form-floating">
              <input
                type="text"
                className="form-control"
                id="floatingInput"
                placeholder="Name"
                value={username}
                onChange={(e) => setUsername(e.target.value)}
              />
              <label htmlFor="floatingInput">Ім'я</label>
            </div>
            <div className="form-floating">
              <input
                type="password"
                className="form-control"
                id="floatingPassword"
                placeholder="Password"
                value={password}
                onChange={(e) => setPassword(e.target.value)}
              />
              <label htmlFor="floatingPassword">Пароль</label>
            </div>
            <button
              className="btn w-100 py-2 submit-button"
              type="submit"
              onSubmit={handleSubmit}
            >
              Увійти
            </button>
          </form>
        </div>
      </main>
    </>
  );
}

export default LoginPage;
