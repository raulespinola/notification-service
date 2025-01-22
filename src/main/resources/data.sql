INSERT INTO  notification_type  (id, name, description, created_at)
VALUES
(1, 'News', 'News from the organization', CURRENT_TIMESTAMP),
(2, 'Marketing', 'News from the organization', CURRENT_TIMESTAMP),
(3, 'Status', 'Status from projects', CURRENT_TIMESTAMP);

INSERT INTO users(id, name, created_at) VALUES
(1, 'Raul', CURRENT_TIMESTAMP),
(2, 'Renzo', CURRENT_TIMESTAMP);

INSERT INTO period_time (id, name, description, time_in_minutes, created_at) VALUES
(1,'Daily','Every Day',1440, CURRENT_TIMESTAMP),
(2,'Hourly','Every Hour',60, CURRENT_TIMESTAMP),
(3,'Per-Minute','Per Minute',1, CURRENT_TIMESTAMP),
(4,'Every-30-min','Every 30 Minutes',30, CURRENT_TIMESTAMP),
(5,'Weekly','Every Week',10080, CURRENT_TIMESTAMP);

INSERT INTO notification_rate_limit (id, notification_type_id, limit_to_send, period_time_id, created_at) VALUES
(1,1,1,1, CURRENT_TIMESTAMP), --News: not more than 1 per day for each recipient
(2,2,3,2, CURRENT_TIMESTAMP), --Marketing: not more than 3 per hour for each recipient
(3,3,2,3, CURRENT_TIMESTAMP); --Status: not more than 2 per minute for each recipient

