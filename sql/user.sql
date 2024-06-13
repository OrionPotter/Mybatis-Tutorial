CREATE TABLE User (
    id INT PRIMARY KEY,
    name VARCHAR(50),
    address_id INT
);


-- 插入 User 数据
INSERT INTO User (id, name, address_id) VALUES (1, 'John Doe', 1);
INSERT INTO User (id, name, address_id) VALUES (2, 'Jane Smith', 2);