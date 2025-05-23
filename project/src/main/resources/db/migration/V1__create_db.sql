-- Таблиця користувачів
create TABLE users (
    id SERIAL PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
--    email VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL
);

-- Універсальна таблиця категорій доходів
create TABLE income_categories (
    id SERIAL PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
    user_id INTEGER REFERENCES users(id) ON delete CASCADE, -- NULL = загальна категорія
    UNIQUE(name, user_id)
);

-- Таблиця доходів
create TABLE incomes (
    id SERIAL PRIMARY KEY,
    user_id INTEGER REFERENCES users(id) ON delete CASCADE,
    category_id INTEGER REFERENCES income_categories(id),
    amount DECIMAL(10, 2) NOT NULL,
    description TEXT,
    income_date DATE NOT NULL
);

-- Універсальна таблиця категорій витрат
create TABLE expense_categories (
    id SERIAL PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
    user_id INTEGER REFERENCES users(id) ON delete CASCADE, -- NULL = загальна категорія
    UNIQUE(name, user_id)
);

-- Таблиця витрат
create TABLE expenses (
    id SERIAL PRIMARY KEY,
    user_id INTEGER REFERENCES users(id) ON delete CASCADE,
    category_id INTEGER REFERENCES expense_categories(id),
    amount DECIMAL(10, 2) NOT NULL,
    description TEXT,
    expense_date DATE NOT NULL
);

-- Таблиця цілей користувача
create TABLE goals (
    id SERIAL PRIMARY KEY,
    user_id INTEGER NOT NULL REFERENCES users(id) ON delete CASCADE,
    name VARCHAR(100) NOT NULL,
    target_amount DECIMAL(10, 2) NOT NULL,         -- Цільова сума
    current_amount DECIMAL(10, 2) DEFAULT 0.00,    -- Поточна накопичена сума
    due_date DATE,                                 -- Дата, до якої бажано досягти цілі
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    completed BOOLEAN DEFAULT FALSE
);

-- Записи про внески до цілей
create TABLE goal_contributions (
    id SERIAL PRIMARY KEY,
    goal_id INTEGER REFERENCES goals(id) ON delete CASCADE,
    amount DECIMAL(10, 2) NOT NULL,
    contribution_date DATE NOT NULL DEFAULT CURRENT_DATE,
    note TEXT
);

-- Таблиця боргів користувачів
create TABLE debts (
    id SERIAL PRIMARY KEY,
    user_id INTEGER NOT NULL REFERENCES users(id) ON delete CASCADE,
    counterparty_name VARCHAR(100) NOT NULL,             -- Ім’я того, кому винен або хто винен
    amount DECIMAL(10, 2) NOT NULL,
    type VARCHAR(10) CHECK (type IN ('given', 'taken')) NOT NULL,
        -- 'given' = користувач позичив гроші (йому винні)
        -- 'taken' = користувач взяв у борг (він винен)
    status VARCHAR(10) CHECK (status IN ('unpaid', 'paid')) DEFAULT 'unpaid',
    issue_date DATE NOT NULL DEFAULT CURRENT_DATE,
    due_date DATE,                                       -- Бажана дата повернення
    note TEXT
);

--Таблиця часткових повернень боргів
create TABLE debt_payments (
    id SERIAL PRIMARY KEY,
    debt_id INTEGER NOT NULL REFERENCES debts(id) ON delete CASCADE,
    amount DECIMAL(10, 2) NOT NULL CHECK (amount > 0),
    payment_date DATE NOT NULL DEFAULT CURRENT_DATE,
    income_id INTEGER REFERENCES incomes(id),    -- Якщо хтось повернув борг користувачу
    expense_id INTEGER REFERENCES expenses(id),  -- Якщо користувач повернув борг комусь
    note TEXT
);
