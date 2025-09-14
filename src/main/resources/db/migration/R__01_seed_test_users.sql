INSERT INTO USERS (email, password, createdTimestamp, lastUpdatedTimestamp)
VALUES ('alice@example.com', '$2a$10$wSa3wZrcVnYcQmFu0X6Px.zbLATsqZSdwU12DhhJDaF97kvFPaXXe', NOW(), NOW())
ON CONFLICT (LOWER(email)) DO NOTHING;

INSERT INTO USERS (email, password, createdTimestamp, lastUpdatedTimestamp)
VALUES ('bob@example.com', '$2a$10$wSa3wZrcVnYcQmFu0X6Px.zbLATsqZSdwU12DhhJDaF97kvFPaXXe', NOW(), NOW())
    ON CONFLICT (LOWER(email)) DO NOTHING;