CREATE TABLE IF NOT EXISTS translation_request (
    id SERIAL PRIMARY KEY,
    ip_address VARCHAR(45) NOT NULL,
    source_text TEXT NOT NULL,
    translated_text TEXT NOT NULL
    );
