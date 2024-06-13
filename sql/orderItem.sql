CREATE TABLE OrderItem (
    id INT PRIMARY KEY,
    order_id INT,
    product_name VARCHAR(50),
    quantity INT
);


-- 插入 OrderItem 数据
INSERT INTO OrderItem (id, order_id, product_name, quantity) VALUES (1, 1, 'Item A', 2);
INSERT INTO OrderItem (id, order_id, product_name, quantity) VALUES (2, 1, 'Item B', 1);
INSERT INTO OrderItem (id, order_id, product_name, quantity) VALUES (3, 2, 'Item C', 5);