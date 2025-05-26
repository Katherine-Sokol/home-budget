import React from "react";
import { useEffect, useState } from "react";

function BudgetPage(props) {
  // const [data, setData] = useState(null);

  useEffect(() => {
    const token = localStorage.getItem("token");

    fetch("http://localhost:8080/api/budget", {
      method: "GET",
      headers: {
        Authorization: `Bearer ${token}`,
      },
    }).then(async (res) => {
      if (res.ok) {
        const result = await res.json();
        setData(result);
      } else {
        console.error("Ошибка доступа: ", res.status);
      }
    });
  }, []);
  return (
    <>
      <div className="container">
        <div className="row">Поточний баланс: </div>
        <div className="row">
          <div className="col-sm-6 mb-3 mb-sm-0">
            <div className="card">
              <div className="card-body">
                <h5 className="card-title">Останні доходи</h5>
                <ul class="list-group list-group-horizontal">
                  <li class="list-group-item">An item</li>
                  <li class="list-group-item">A second item</li>
                </ul>
                <a
                  href="#"
                  className="btn btn-primary"
                >
                  Додати транзакцію
                </a>
              </div>
            </div>
          </div>
          <div className="col-sm-6">
            <div className="card">
              <div className="card-body">
                <h5 className="card-title">Останні витрати</h5>
                <ul class="list-group list-group-horizontal">
                  <li class="list-group-item">An item</li>
                  <li class="list-group-item">A second item</li>
                </ul>
                <a
                  href="#"
                  className="btn btn-primary"
                >
                  Додати транзакцію
                </a>
              </div>
            </div>
          </div>
        </div>
      </div>
    </>
  );
}

export default BudgetPage;
