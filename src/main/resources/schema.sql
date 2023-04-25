CREATE TABLE IF NOT EXISTS clients (
  id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
  name VARCHAR(75) NOT NULL,
  lastname VARCHAR(75) NOT NULL,
  docnumber VARCHAR(11) NOT NULL);

  CREATE TABLE IF NOT EXISTS invoice (
    id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    client_id INT,
    created_at DATETIME NOT NULL,
    total DOUBLE,
    CONSTRAINT fk_client_id FOREIGN KEY (client_id) REFERENCES clients (id));

 CREATE TABLE IF NOT EXISTS products (
  id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
  description VARCHAR(150) NOT NULL,
  code VARCHAR(50) NOT NULL,
  stock INT NOT NULL,
  price DOUBLE NOT NULL);

CREATE TABLE IF NOT EXISTS invoice_details (
  invoice_detail_id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
  invoice_id INT,
  amounts INT NOT NULL,
  product_id INT,
  price DOUBLE NOT NULL,
  CONSTRAINT fk_invoice_id FOREIGN KEY (invoice_id) REFERENCES invoice (id),
  CONSTRAINT fk_products_id FOREIGN KEY (product_id) REFERENCES products (id));