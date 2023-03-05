INSERT INTO products (id, price, title)
VALUES (1, 450.0, 'Cheese'),
       (2, 150.0, 'Greek yogurt'),
       (3, 350.0, 'Wine'),
       (4, 2000.0, 'Beef meat'),
       (5, 120.0, 'Water'),
       (6, 80.0, 'Bread'),
       (7, 140.0, 'Mozzarella sticks'),
       (8, 75.0, 'Coffee'),
       (9, 60.0, 'Apple'),
       (10, 1300.0, 'Oysters');

ALTER SEQUENCE product_seq RESTART WITH 11;