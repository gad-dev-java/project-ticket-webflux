\c notification_service_db;

CREATE TABLE notifications
(
    notification_id UUID PRIMARY KEY,
    booking_id      UUID        NOT NULL,
    user_id         UUID        NOT NULL,
    type            VARCHAR(50) NOT NULL CHECK (type IN ('EMAIL', 'SMS', 'PUSH')),
    recipient       VARCHAR(255),
    subject         VARCHAR(255),
    content         TEXT,
    status          VARCHAR(20) DEFAULT 'PENDING' CHECK (status IN ('PENDING', 'SENT', 'FAILED')),
    sent_at         TIMESTAMPTZ,
    error_message   TEXT,
    created_at      TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_notifications_status ON notifications (status);