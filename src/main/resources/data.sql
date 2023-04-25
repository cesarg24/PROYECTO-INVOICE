INSERT INTO clients (name, lastname, docnumber) VALUES
  ('Juan', 'Pérez', '12345678'),
  ('María', 'Gómez', '87654321'),
  ('Carlos', 'López', '11111111'),
  ('Ana', 'Gonzales', '99999999');

INSERT INTO products (description, code, stock, price) VALUES
  ('Smartphone Samsung Galaxy S21', 'SAMGALS21', 10, 899.99),
  ('Laptop Dell XPS 13', 'DELLXPS13', 5, 1499.99),
  ('Tablet Apple iPad Pro', 'IPADPRO', 8, 799.99),
  ('Smartwatch Garmin Venu 2S', 'GARVEN2S', 15, 399.99);

INSERT INTO invoice (client_id, created_at, total) VALUES
  (1, '2023-04-11 10:00:00', 2599.97),
  (2, '2023-04-10 15:30:00', 2699.96),
  (3, '2023-04-09 14:20:00', 1799.98),
  (1, '2023-04-08 12:00:00', 399.99);

INSERT INTO invoice_details (invoice_id, amounts, product_id, price) VALUES
  (1, 2, 1, 899.99),
  (1, 1, 3, 799.99),
  (2, 1, 2, 1499.99),
  (2, 3, 4, 399.99),
  (3, 2, 1, 899.99),
  (4, 1, 4, 399.99);