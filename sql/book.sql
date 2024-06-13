CREATE TABLE books (
    id INT PRIMARY KEY,
    title VARCHAR(100),
    author VARCHAR(100),
    price DOUBLE
);

INSERT INTO books (id, title, author, price) VALUES (1, '1984', 'George Orwell', 9.99);
INSERT INTO books (id, title, author, price) VALUES (2, 'To Kill a Mockingbird', 'Harper Lee', 7.99);