INSERT INTO USERS (email, password, createdTimestamp, lastUpdatedTimestamp)
VALUES ('alice@example.com', '$2a$10$wSa3wZrcVnYcQmFu0X6Px.zbLATsqZSdwU12DhhJDaF97kvFPaXXe', NOW(), NOW())
ON CONFLICT (LOWER(email)) DO NOTHING;

INSERT INTO USERS (email, password, createdTimestamp, lastUpdatedTimestamp)
VALUES ('bob@example.com', '$2a$10$wSa3wZrcVnYcQmFu0X6Px.zbLATsqZSdwU12DhhJDaF97kvFPaXXe', NOW(), NOW())
    ON CONFLICT (LOWER(email)) DO NOTHING;

DO $$
DECLARE
alice_id BIGINT;
    poll_uuid UUID := 'a0eebc99-9c0b-4ef8-bb6d-6bb9bd380a11'; -- Static UUID for testing
BEGIN
    -- Find Alice's ID
SELECT id INTO alice_id FROM USERS WHERE email = 'alice@example.com';

-- Insert a poll for Alice if it doesn't already exist
IF alice_id IS NOT NULL AND NOT EXISTS (SELECT 1 FROM Poll WHERE publicId = poll_uuid) THEN
        INSERT INTO Poll(publicId, title, description, createdTimestamp, owner_id)
        VALUES (poll_uuid, 'Alice''s Test Poll', 'This is a poll for testing the delete endpoint.', NOW(), alice_id);
END IF;
END $$;