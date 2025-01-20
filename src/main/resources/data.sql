INSERT INTO NoticationsType (id, name, description) VALUES (1,'News', "News from the organization");
INSERT INTO NoticationsType (id, name, description) VALUES (1,'Status', "Status From Proyects");


INSERT INTO NoticationsRateLimit (id, notification_type_id, limit, time_unit) VALUES (1,1,3,'day');T