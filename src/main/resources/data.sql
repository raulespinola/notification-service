INSERT INTO NoticationsType (id, name, description) VALUES
(1,'News', "News from the organization"),
(2,'Status', "Status From Proyects");


INSERT INTO PeriodTime (id, name, description, days, hours, minutes, seconds) VALUES
(1,'Daily','Daily Notification',1,0,0,0),
(2,'Hourly','Hourly Notification',0,1,0,0),
(3,'Per-Minute','Notification Per Minute',0,0,1,0);

INSERT INTO NoticationsRateLimit (id, notification_type_id, limit, period_time_id) VALUES
(1,1,3,1),
(2,1,2,2),
(3,1,1,3);

INSERT INTO NoticationsEvent (id, notification_type_id, sent_at) VALUES
(1,1,'2025-01-21 01:35:00'),
(2,1,'2025-01-21 01:35:00'),
(3,1,'2025-01-21 01:35:00'),
(4,1,'2025-01-21 01:35:00');