CREATE TABLE organizations
(
    id               SERIAL PRIMARY KEY,
    name             VARCHAR(100) UNIQUE NOT NULL,
    description      VARCHAR(255),
    industry         VARCHAR(100),
    size             INT,
    status           VARCHAR(50) DEFAULT 'active',
    created_at       TIMESTAMP   DEFAULT CURRENT_TIMESTAMP,
    updated_at       TIMESTAMP   DEFAULT CURRENT_TIMESTAMP,
    deactivated_at   TIMESTAMP,
    last_activity_at TIMESTAMP,
    logo_base64      TEXT
);

CREATE TABLE user_account
(
    id                    SERIAL PRIMARY KEY,
    organization_id       INT                 REFERENCES organizations (id) ON DELETE SET NULL,
    username              VARCHAR(100) UNIQUE NOT NULL,
    first_name            VARCHAR(100),
    middle_name           VARCHAR(100),
    last_name             VARCHAR(100),
    password_hash         VARCHAR(255)        NOT NULL,
    salt                  VARCHAR(255)        NOT NULL,
    last_login            TIMESTAMP,
    last_login_ip_address VARCHAR(45),
    login_app             VARCHAR(50),
    failed_login_attempts INT       DEFAULT 0,
    lockout_time          TIMESTAMP,
    is_email_verified     BOOLEAN   DEFAULT FALSE,
    is_phone_verified     BOOLEAN   DEFAULT FALSE,
    is_active             BOOLEAN   DEFAULT TRUE,
    avatar_base64         TEXT,
    created_at            TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at            TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE contact
(
    id              SERIAL PRIMARY KEY,
    user_account_id INT REFERENCES user_account (id) ON DELETE CASCADE,
    type            VARCHAR(50),
    phone_number    VARCHAR(30),
    email           VARCHAR(100) UNIQUE,
    zip_code        VARCHAR(20),
    location        VARCHAR(255),
    address1        VARCHAR(255),
    address2        VARCHAR(255),
    is_primary      BOOLEAN   DEFAULT FALSE,
    created_at      TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at      TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE role
(
    id          SERIAL PRIMARY KEY,
    name        VARCHAR(50) UNIQUE NOT NULL,
    description VARCHAR(255),
    created_at  TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at  TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE permission
(
    id          SERIAL PRIMARY KEY,
    name        VARCHAR(100) UNIQUE NOT NULL,
    description VARCHAR(255),
    created_at  TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at  TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE role_permissions
(
    role_id       INT REFERENCES role (id) ON DELETE CASCADE,
    permission_id INT REFERENCES permission (id) ON DELETE CASCADE,
    PRIMARY KEY (role_id, permission_id)
);

CREATE TABLE user_roles
(
    user_id INT REFERENCES user_account (id) ON DELETE CASCADE,
    role_id INT REFERENCES role (id) ON DELETE CASCADE,
    PRIMARY KEY (user_id, role_id)
);

CREATE TABLE refresh_token
(
    id          SERIAL PRIMARY KEY,
    user_id     INT REFERENCES user_account (id) ON DELETE CASCADE,
    token       VARCHAR(255) UNIQUE NOT NULL,
    issued_at   TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    expiry_date TIMESTAMP           NOT NULL,
    revoked     BOOLEAN   DEFAULT FALSE,
    revoked_at  TIMESTAMP,
    revoked_by  INT REFERENCES user_account (id),
    ip_address  VARCHAR(45)
);

CREATE TABLE mfa
(
    id         SERIAL PRIMARY KEY,
    user_id    INT REFERENCES user_account (id) ON DELETE CASCADE,
    method     VARCHAR(50)  NOT NULL,
    secret     VARCHAR(255) NOT NULL,
    priority   INT       DEFAULT 0,
    enabled    BOOLEAN   DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE audit_log
(
    id                SERIAL PRIMARY KEY,
    user_id           INT         REFERENCES user_account (id) ON DELETE SET NULL,
    level             VARCHAR(50) NOT NULL,
    message           TEXT        NOT NULL,
    created_at        TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    format_created_at VARCHAR(50) GENERATED ALWAYS AS (to_char(created_at, 'YYYY-MM-DD HH24:MI:SS')) STORED,
);