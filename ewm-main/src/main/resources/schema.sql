CREATE TABLE IF NOT EXISTS categories
(
    id   INTEGER GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    name VARCHAR(250) UNIQUE NOT NULL
);