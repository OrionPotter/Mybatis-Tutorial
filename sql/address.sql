CREATE TABLE Address (
    id INT PRIMARY KEY,
    street VARCHAR(100),
    city VARCHAR(50)
);

-- 插入 Address 数据
INSERT INTO Address (id, street, city) VALUES (1, '123 Main St', 'Springfield');
INSERT INTO Address (id, street, city) VALUES (2, '456 Elm St', 'Shelbyville');
