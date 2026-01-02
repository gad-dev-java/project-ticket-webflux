\c event_service_db;

CREATE TABLE event_categories
(
    category_id   UUID PRIMARY KEY,
    category_name VARCHAR(100) UNIQUE NOT NULL,
    description   TEXT
);

CREATE TABLE events
(
    event_id    UUID PRIMARY KEY,
    name        VARCHAR(255) NOT NULL,
    description TEXT,
    category_id UUID REFERENCES event_categories (category_id),
    event_date  TIMESTAMPTZ  NOT NULL,
    venue       VARCHAR(255),
    address     TEXT,
    city        VARCHAR(100),
    country     VARCHAR(100),
    status      VARCHAR(20) DEFAULT 'ACTIVE' CHECK (status IN ('ACTIVE', 'CANCELLED', 'COMPLETED', 'DRAFT')),
    image_url   TEXT,
    created_at  TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP,
    updated_at  TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE ticket_types
(
    ticket_type_id     UUID PRIMARY KEY,
    event_id           UUID REFERENCES events (event_id) ON DELETE CASCADE,
    type_name          VARCHAR(100)   NOT NULL,
    price              DECIMAL(10, 2) NOT NULL CHECK (price >= 0),
    total_quantity     INT            NOT NULL CHECK (total_quantity > 0),
    available_quantity INT            NOT NULL CHECK (available_quantity >= 0),
    created_at         TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT chk_ticket_available CHECK (available_quantity <= total_quantity)
);
