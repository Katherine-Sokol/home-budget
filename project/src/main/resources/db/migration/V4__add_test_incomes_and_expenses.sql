--Додаємо доходи

-- Зарплата
INSERT INTO incomes (user_id, category_id, amount, income_date, description)
VALUES (1, (SELECT id FROM income_categories WHERE name = 'Зарплата' AND user_id IS NULL), 25000.00, '2025-05-01', 'Зарплата за квітень');

-- Фриланс
INSERT INTO incomes (user_id, category_id, amount, income_date, description)
VALUES (1, (SELECT id FROM income_categories WHERE name = 'Фриланс' AND user_id IS NULL), 7000.00, '2025-05-03', 'Проєкт з дизайну');

-- Подарунок
INSERT INTO incomes (user_id, category_id, amount, income_date, description)
VALUES (1, (SELECT id FROM income_categories WHERE name = 'Подарунок' AND user_id IS NULL), 1500.00, '2025-05-08', 'День народження');

--Додаємо витрати

-- Продукти (повторюється)
INSERT INTO expenses (user_id, category_id, amount, expense_date, description) VALUES
(1, (SELECT id FROM expense_categories WHERE name = 'Продукти' AND user_id IS NULL), 850.00, '2025-05-02', 'АТБ'),
(1, (SELECT id FROM expense_categories WHERE name = 'Продукти' AND user_id IS NULL), 620.00, '2025-05-07', 'Сільпо');

-- Транспорт (повторюється)
INSERT INTO expenses (user_id, category_id, amount, expense_date, description) VALUES
(1, (SELECT id FROM expense_categories WHERE name = 'Транспорт' AND user_id IS NULL), 300.00, '2025-05-04', 'Метро та автобус'),
(1, (SELECT id FROM expense_categories WHERE name = 'Транспорт' AND user_id IS NULL), 180.00, '2025-05-10', 'Таксі');

-- Комунальні послуги
INSERT INTO expenses (user_id, category_id, amount, expense_date, description)
VALUES (1, (SELECT id FROM expense_categories WHERE name = 'Комунальні послуги' AND user_id IS NULL), 2100.00, '2025-05-05', 'Світло, газ, вода');

-- Оренда житла
INSERT INTO expenses (user_id, category_id, amount, expense_date, description)
VALUES (1, (SELECT id FROM expense_categories WHERE name = 'Оренда житла' AND user_id IS NULL), 8000.00, '2025-05-01', 'Квартира');

-- Звʼязок та інтернет
INSERT INTO expenses (user_id, category_id, amount, expense_date, description)
VALUES (1, (SELECT id FROM expense_categories WHERE name = 'Звʼязок та інтернет' AND user_id IS NULL), 250.00, '2025-05-06', 'Київстар + інтернет');

-- Кафе та ресторани
INSERT INTO expenses (user_id, category_id, amount, expense_date, description)
VALUES (1, (SELECT id FROM expense_categories WHERE name = 'Кафе та ресторани' AND user_id IS NULL), 540.00, '2025-05-09', 'Обід у Puzata Hata');

-- Подарунки
INSERT INTO expenses (user_id, category_id, amount, expense_date, description)
VALUES (1, (SELECT id FROM expense_categories WHERE name = 'Подарунки' AND user_id IS NULL), 1000.00, '2025-05-03', 'Подарунок подрузі');

-- Догляд за собою
INSERT INTO expenses (user_id, category_id, amount, expense_date, description)
VALUES (1, (SELECT id FROM expense_categories WHERE name = 'Догляд за собою' AND user_id IS NULL), 650.00, '2025-05-07', 'Перукарня');