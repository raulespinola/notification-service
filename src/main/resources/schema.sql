CREATE TABLE NotificationType (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    description VARCHAR(255) NOT NULL
);

CREATE TABLE PeriodTime (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    description VARCHAR(255) NOT NULL,
    days INT,
    hours INT,
    minutes INT,
    seconds INT,
);

CREATE TABLE NotificationRateLimit (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    notification_type_id INT,
    limit INT,
    period_time_id INT,
    FOREIGN KEY (notification_type_id) REFERENCES NotificationType(id)
    FOREIGN KEY (period_time_id_id) REFERENCES PeriodTime(id)
);

CREATE TABLE NotificationEvent (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    notification_type_id INT,
    sent_at TIMESTAMP NOT NULL,
    description VARCHAR(255) NOT NULL
    FOREIGN KEY (notification_type_id) REFERENCES NotificationType(id)
);

