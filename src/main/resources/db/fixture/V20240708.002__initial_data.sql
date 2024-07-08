-- Initial Data: Default Organization
INSERT INTO organizations (name, description, industry, size, status, created_at, updated_at)
VALUES ('Default Organization', 'Default organization description', 'Software', 50, 'active', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- Initial Data: Default User
INSERT INTO user_account (organization_id, username, first_name, middle_name, last_name, password_hash, salt, is_email_verified, is_active, created_at, updated_at)
VALUES (1, 'admin', 'Admin', NULL, 'User', 'hashed_password', 'random_salt', TRUE, TRUE, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- Initial Data: Default Role
INSERT INTO role (name, description, created_at, updated_at)
VALUES ('ADMIN', 'Administrator role', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- Initial Data: Assign Default Role to Default User
INSERT INTO user_roles (user_id, role_id)
VALUES (1, 1);

-- Initial Data: Dummy Contact for Default User
INSERT INTO contact (user_account_id, type, phone_number, email, zip_code, location, address1, address2, is_primary, created_at, updated_at)
VALUES (1, 'work', '123-456-7890', 'admin@example.com', '12345', 'Default City', '123 Default St', 'Suite 100', TRUE, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
