CREATE TABLE app_user (
    username VARCHAR(50) PRIMARY KEY,
    password_hash VARCHAR(100) NOT NULL,
    role VARCHAR(20) NOT NULL,
    enabled BOOLEAN NOT NULL
);

-- Demo users of the course: the password of each user is <name>-demo, stored only as a BCrypt hash.
INSERT INTO app_user (username, password_hash, role, enabled) VALUES
    ('admin', '{bcrypt}$2a$10$13yP8Cq7X5L/xurdiqXYLO7zzPYZrBEorD3KNqhFskgLF0iKniHGG', 'ADMIN', TRUE);
INSERT INTO app_user (username, password_hash, role, enabled) VALUES
    ('customer', '{bcrypt}$2a$10$syP9GNM6wUjkRShBVhQACewBA2eawoix5ebr4ppAJ60BWaNBg3dGa', 'CUSTOMER', TRUE);
