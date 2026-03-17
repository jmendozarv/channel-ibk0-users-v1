CREATE TABLE users
(
    id         BINARY(16) PRIMARY KEY,
    name       VARCHAR(255) NOT NULL,
    email      VARCHAR(255) NOT NULL UNIQUE,
    password   VARCHAR(255) NOT NULL,
    created    TIMESTAMP    NOT NULL,
    modified   TIMESTAMP    NOT NULL,
    last_login TIMESTAMP    NOT NULL,
    token      VARCHAR(255) NOT NULL,
    isactive   BOOLEAN      NOT NULL
);

CREATE TABLE phones
(
    id         BIGINT AUTO_INCREMENT PRIMARY KEY,
    number     VARCHAR(255) NOT NULL,
    citycode   VARCHAR(255) NOT NULL,
    contrycode VARCHAR(255) NOT NULL,
    user_id    BINARY(16),
    CONSTRAINT fk_phone_user FOREIGN KEY (user_id) REFERENCES users (id)
);