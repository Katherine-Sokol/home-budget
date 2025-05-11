import React from "react";

function SigninPage(props) {
  return (
    <>
      <main className="d-flex align-items-center py-4 body-container">
        <div className="form-signin w-100 m-auto">
          <form>
            <h1 className="h3 mb-3 fw-normal text-center">Форма реєстрації</h1>
            <div className="form-floating">
              <input
                type="text"
                className="form-control"
                id="floatingInput"
                placeholder="Name"
                fdprocessedid="oep0s"
              />
              <label for="floatingInput">Ім'я</label>
            </div>
            <div className="form-floating">
              <input
                type="email"
                className="form-control"
                id="floatingInput"
                placeholder="name@example.com"
                fdprocessedid="oep0s"
              />
              <label for="floatingInput">Пошта</label>
            </div>
            <div className="form-floating">
              <input
                type="password"
                className="form-control"
                id="floatingPassword"
                placeholder="Password"
                fdprocessedid="ud33r"
              />
              <label for="floatingPassword">Пароль</label>
            </div>
            <button
              className="btn w-100 py-2 submit-button"
              type="submit"
              fdprocessedid="at1wc9"
            >
              Зареєструватися
            </button>
          </form>
        </div>
      </main>
    </>
  );
}

export default SigninPage;
