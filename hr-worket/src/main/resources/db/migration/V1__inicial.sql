-- src/main/resources/db/migration/V1__inicial.sql

-- Enable the uuid-ossp extension
CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

-- Create the tb_worker table
CREATE TABLE tb_worker (
    id UUID PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    daily_income DECIMAL(10, 2) NOT NULL
);

-- Insert initial data into tb_worker table
INSERT INTO tb_worker (id, name, daily_income) VALUES (uuid_generate_v4(), 'Bob', 200.0);
INSERT INTO tb_worker (id, name, daily_income) VALUES (uuid_generate_v4(), 'Maria', 300.0);
INSERT INTO tb_worker (id, name, daily_income) VALUES (uuid_generate_v4(), 'Alex', 250.0);