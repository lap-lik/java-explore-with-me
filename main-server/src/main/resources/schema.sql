CREATE TABLE IF NOT EXISTS categories
(
    id   INTEGER GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    name VARCHAR(50) UNIQUE NOT NULL
);

CREATE TABLE IF NOT EXISTS users
(
    id    INTEGER GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    name  VARCHAR(50)         NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    CONSTRAINT email_check CHECK (email ~* '^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,6}$')
);

CREATE TABLE IF NOT EXISTS categories
(
    id   INTEGER GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    name VARCHAR(50) UNIQUE NOT NULL
);

CREATE TABLE IF NOT EXISTS locations
(
    id        INTEGER GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    latitude  FLOAT NOT NULL,
    longitude FLOAT NOT NULL,
    CONSTRAINT unique_location UNIQUE (latitude, longitude)
);

CREATE TABLE IF NOT EXISTS events
(
    id                 INTEGER GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    annotation         VARCHAR(2000)               NOT NULL CHECK (LENGTH(title) >= 20),
    category_id        INTEGER                     NOT NULL,
    created_on         TIMESTAMP WITHOUT TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    description        VARCHAR(7000)               NOT NULL CHECK (LENGTH(title) >= 20),
    event_date         TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    initiator_id       INTEGER                     NOT NULL,
    location_id        INTEGER                     NOT NULL,
    paid               BOOLEAN,
    participant_limit  INTEGER                     NOT NULL,
    published_on       TIMESTAMP WITHOUT TIME ZONE,
    request_moderation BOOLEAN,
    state              VARCHAR(50)                 NOT NULL,
    title              VARCHAR(120)                NOT NULL CHECK (LENGTH(title) >= 3),
    FOREIGN KEY (location_id) REFERENCES locations (id),
    FOREIGN KEY (category_id) REFERENCES categories (id),
    FOREIGN KEY (initiator_id) REFERENCES users (id)
);

CREATE TABLE IF NOT EXISTS requests
(
    id           INTEGER GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    created      TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    event_id     INTEGER                     NOT NULL,
    requester_id INTEGER                     NOT NULL,
    status       VARCHAR(25)                 NOT NULL,
    FOREIGN KEY (event_id) REFERENCES events (id) ON DELETE CASCADE,
    FOREIGN KEY (requester_id) REFERENCES users (id) ON DELETE CASCADE
);