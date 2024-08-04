CREATE TABLE IF NOT EXISTS translation_request (
    id SERIAL PRIMARY KEY,
    ip_address VARCHAR NOT NULL,
    source_text TEXT NOT NULL,
    translated_text TEXT NOT NULL
);

CREATE TABLE IF NOT EXISTS languages (
    id SERIAL PRIMARY KEY,
    code VARCHAR NOT NULL UNIQUE,
    name VARCHAR NOT NULL
);
