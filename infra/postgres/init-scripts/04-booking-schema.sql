\c booking_service_db;

CREATE TABLE bookings
(
    booking_id        UUID PRIMARY KEY,
    event_id          UUID           NOT NULL,
    user_id           UUID           NOT NULL,
    total_price       DECIMAL(10, 2) NOT NULL,
    status            VARCHAR(20) DEFAULT 'PENDING' CHECK (status IN ('PENDING', 'CONFIRMED', 'CANCELLED', 'FAILED')),
    booking_reference VARCHAR(100) UNIQUE,
    created_at        TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP,
    updated_at        TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP,
    confirmed_at      TIMESTAMPTZ
);

CREATE TABLE booking_items
(
    item_id        UUID PRIMARY KEY,
    booking_id     UUID REFERENCES bookings (booking_id) ON DELETE CASCADE,
    ticket_type_id UUID           NOT NULL,
    quantity       INT            NOT NULL CHECK (quantity > 0),
    unit_price     DECIMAL(10, 2) NOT NULL,
    subtotal       DECIMAL(10, 2) NOT NULL
);

CREATE INDEX idx_bookings_user ON bookings (user_id);
CREATE INDEX idx_bookings_status ON bookings (status);