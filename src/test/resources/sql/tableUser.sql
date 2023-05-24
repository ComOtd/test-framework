CREATE TABLE user
(
    name VARCHAR(255) NOT NULL,
    surname VARCHAR(255) NOT NULL,
    doc_id INT(20),
    create_date TIMESTAMP(6) Default '1995-01-01'
);