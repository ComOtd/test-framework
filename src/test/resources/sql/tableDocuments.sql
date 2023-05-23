CREATE TABLE documents
(
    doc_id INT(20) NOT NULL,
    doc_series SMALLINT(4)NOT NULL,
    doc_num INT(6) NOT NULL,
    original_flg BOOLEAN NOT NULL DEFAULT FALSE,
    employee_id INT(20) NOT NULL
);