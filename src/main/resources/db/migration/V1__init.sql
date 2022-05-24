CREATE TABLE IF NOT EXISTS users
(
    id bigserial PRIMARY KEY,
    name varchar(32) NOT NULL,
    surname varchar(32) NOT NULL,
    patronymic varchar(32) NOT NULL,
    age integer NOT NULL CHECK(age >= 0),
    salary integer NOT NULL CHECK (salary > 0),
    email varchar(64) NOT NULL,
    job text NOT NULL
);