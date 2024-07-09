INSERT INTO role (name, description, is_basic, created_at, updated_at)
VALUES ('SUPER_ADMIN', 'Super administrator role', TRUE, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       ('ADMIN', 'Administrator role', TRUE, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       ('ORG_OWNER', 'Organization owner role', TRUE, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       ('ORG_ADMIN', 'Organization administrator role', TRUE, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       ('ORG_MEMBER', 'Organization member role', TRUE, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       ('USER', 'Regular user role', TRUE, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO organization (name, description, industry, size, status, created_at, updated_at)
VALUES ('Default Organization', 'This is a default organization for initial setup.', 'Software', 50, 'active',
        CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO user_account (organization_id, username, first_name, middle_name, last_name,
                          password_hash, is_email_verified, is_active, created_at, updated_at)
VALUES ((SELECT id FROM organization WHERE name = 'Default Organization'), 'admin', 'Admin', NULL, 'User',
        'hashed_password', TRUE, TRUE, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       ((SELECT id FROM organization WHERE name = 'Default Organization'), 'jdoe', 'John', 'M.', 'Doe',
        'hashed_password2', TRUE, TRUE, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       ((SELECT id FROM organization WHERE name = 'Default Organization'), 'asmith', 'Alice', 'L.', 'Smith',
        'hashed_password3', TRUE, TRUE, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO user_roles (user_id, role_id)
VALUES ((SELECT id FROM user_account WHERE username = 'admin'), (SELECT id FROM role WHERE name = 'SUPER_ADMIN')),
       ((SELECT id FROM user_account WHERE username = 'jdoe'), (SELECT id FROM role WHERE name = 'ORG_MEMBER')),
       ((SELECT id FROM user_account WHERE username = 'asmith'), (SELECT id FROM role WHERE name = 'ORG_MEMBER'));

INSERT INTO contact (user_account_id, type, phone_number, email, zip_code, location, country, address1, address2,
                     is_primary, created_at, updated_at)
VALUES ((SELECT id FROM user_account WHERE username = 'admin'), 'work', '123-456-7890', 'admin@example.com', '12345',
        ST_GeogFromText('SRID=4326;POINT(-71.0589 42.3601)'), 'Default Country', '123 Default St', 'Suite 100', TRUE,
        CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       ((SELECT id FROM user_account WHERE username = 'jdoe'), 'home', '234-567-8901', 'jdoe@example.com', '23456',
        ST_GeogFromText('SRID=4326;POINT(-71.0589 42.3602)'), 'Example Country', '234 Example St', 'Apt 200', TRUE,
        CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       ((SELECT id FROM user_account WHERE username = 'asmith'), 'mobile', '345-678-9012', 'asmith@example.com',
        '34567', ST_GeogFromText('SRID=4326;POINT(-71.0589 42.3603)'), 'Sample Country', '345 Sample St', 'Unit 300',
        TRUE, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

UPDATE organization SET owner_id = (SELECT id FROM user_account WHERE username = 'admin')
WHERE name = 'Default Organization';

INSERT INTO user_roles (user_id, role_id)
VALUES ((SELECT id FROM user_account WHERE username = 'admin'), (SELECT id FROM role WHERE name = 'ORG_OWNER'));
