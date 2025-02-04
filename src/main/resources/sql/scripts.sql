-- Create the customer table
CREATE TABLE customer (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL,
    enabled BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- Create the authorities table
CREATE TABLE authorities (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL,
    authority VARCHAR(50) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (username) REFERENCES customer(username) ON DELETE CASCADE ON UPDATE CASCADE
);

-- Insert test data into the customer table
INSERT INTO customer (username, password, email, enabled)
VALUES
('admin', '$2a$10$e0MYzXyjpJS7Pd0RVvHwHeFX4wRaq/1RZBBQKnVokv1pC.AZLjUWy', 'admin@example.com', TRUE),
('user1', '$2a$10$KjgU/TVg1DPO7oZoN1e8IeHZBUI5.TM.q8SSTmbfhXJcihyFVMTUe', 'user1@example.com', TRUE);

-- Insert test data into the authorities table
INSERT INTO authorities (username, authority)
VALUES
('admin', 'ROLE_ADMIN'),
('user1', 'ROLE_USER'),
('user1', 'ROLE_VIEWER');

-- Query to fetch user roles and information
SELECT
    c.id AS user_id,
    c.username,
    c.enabled,
    a.authority
FROM
    customer c
JOIN
    authorities a ON c.username = a.username
WHERE
    c.username = :username;
