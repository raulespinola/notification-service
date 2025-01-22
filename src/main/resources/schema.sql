CREATE TABLE notification_type (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    description VARCHAR(255) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE period_time (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    description VARCHAR(255) NOT NULL,
    time_in_minutes INT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE notification_rate_limit (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    notification_type_id INT,
    limit_to_send INT,
    period_time_id INT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (notification_type_id) REFERENCES notification_type(id),
    FOREIGN KEY (period_time_id) REFERENCES period_time(id)
);

CREATE TABLE notification_event (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    notification_type_id INT,
    sent_at TIMESTAMP NOT NULL,
    FOREIGN KEY (notification_type_id) REFERENCES notification_type(id)
);

