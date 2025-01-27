-- V2__create_role_table.sql
CREATE TABLE tb_role (
    id SERIAL PRIMARY KEY,
    role_name VARCHAR(50) NOT NULL
);

INSERT INTO tb_role (id, role_name) VALUES
(1, 'ROLE_OPERATOR'),
(2, 'ROLE_ADMIN');