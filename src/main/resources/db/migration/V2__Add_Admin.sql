INSERT INTO users (id, archive, email, name, password, role, activated)
VALUES (1, false, 'mail@gmail.ru', 'admin', '$2a$12$70RZg3LRSF6QikPt0QOOjOB8buxSqKSKIwBh6tR5RxIaiYTLXydb6', 'ADMIN', true);

ALTER SEQUENCE user_seq RESTART WITH 2;