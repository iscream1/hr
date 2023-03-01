CREATE SCHEMA IF NOT EXISTS hr;

CREATE TYPE position_enum AS ENUM ('MANAGER', 'EMPLOYEE');

CREATE TABLE hr.employees
(
    id bigserial NOT NULL,
    full_name varchar(255) NOT NULL,
    email varchar(100) NOT NULL,
    position position_enum NOT NULL,
    department_id bigint DEFAULT NULL,
    created_at date NOT NULL,
    created_by bigint NOT NULL,
    modified_at date DEFAULT NULL,
    modified_by bigint DEFAULT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE hr.departments
(
    id bigserial NOT NULL,
    name varchar(255) NOT NULL,
    manager_id bigint NOT NULL references hr.employees(id),
    created_at date NOT NULL,
    created_by bigint NOT NULL,
    modified_at date DEFAULT NULL,
    modified_by bigint DEFAULT NULL,
    PRIMARY KEY (id)
);

ALTER TABLE hr.employees ADD CONSTRAINT employees_department_id FOREIGN KEY (department_id) REFERENCES hr.departments(id);

CREATE INDEX idx_employees_name ON employees(full_name);
CREATE INDEX idx_employees_email ON employees(email);
CREATE INDEX idx_employees_department_id ON employees(department_id);

CREATE INDEX idx_department_name ON departments(name);
