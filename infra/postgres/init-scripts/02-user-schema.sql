\c user_service_db;

CREATE TABLE users
(
    user_id    UUID PRIMARY KEY,
    username   VARCHAR(100) UNIQUE NOT NULL,
    email      VARCHAR(255) UNIQUE NOT NULL,
    first_name VARCHAR(100),
    last_name  VARCHAR(100),
    phone      VARCHAR(20) UNIQUE  NOT NULL,
    status     VARCHAR(20) DEFAULT 'ACTIVE' CHECK (status IN ('ACTIVE', 'INACTIVE', 'SUSPENDED')),
    created_at TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE addresses
(
    address_id UUID PRIMARY KEY,
    user_id    UUID         NOT NULL,
    street     VARCHAR(255) NOT NULL,
    city       VARCHAR(100) NOT NULL,
    country    VARCHAR(100) NOT NULL,
    zip_code   VARCHAR(20),
    is_default BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP,
    CONSTRAINT fk_user FOREIGN KEY (user_id) REFERENCES users (user_id)
);

CREATE TABLE genres
(
    genre_id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    name     VARCHAR(50) NOT NULL UNIQUE
);

INSERT INTO genres (name) VALUES
                              ('Rock'),
                              ('Pop'),
                              ('Hip Hop'),
                              ('Electronic'),
                              ('Jazz'),
                              ('Classical'),
                              ('Reggae'),
                              ('Salsa'),
                              ('Metal'),
                              ('Country') ON CONFLICT (name) DO NOTHING;

CREATE TABLE user_favorite_genres
(
    user_id    UUID NOT NULL,
    genre_id   UUID NOT NULL,
    created_at TIMESTAMP DEFAULT NOW(),
    PRIMARY KEY (user_id, genre_id),
    CONSTRAINT fk_user_pref FOREIGN KEY (user_id) REFERENCES users (user_id),
    CONSTRAINT fk_genre_pref FOREIGN KEY (genre_id) REFERENCES genres (genre_id)
);

CREATE INDEX idx_users_email ON users (email);
CREATE INDEX idx_users_username ON users (username);