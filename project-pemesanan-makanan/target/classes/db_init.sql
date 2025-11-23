DROP TABLE IF EXISTS order_details CASCADE;
DROP TABLE IF EXISTS orders CASCADE;
DROP TABLE IF EXISTS menus CASCADE;
DROP TABLE IF EXISTS users CASCADE;


-- Tabel User (Untuk Login)
CREATE TABLE users (
    id SERIAL PRIMARY KEY,
    username VARCHAR(50) UNIQUE NOT NULL,
    password VARCHAR(100) NOT NULL,
    role VARCHAR(20) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Tabel Menu Makanan
CREATE TABLE menus (
    id SERIAL PRIMARY KEY,
    nama_makanan VARCHAR(100) NOT NULL,
    kategori VARCHAR(50),
    harga DECIMAL(10, 2) NOT NULL,
    is_available BOOLEAN DEFAULT TRUE
);

CREATE TABLE orders (
    id SERIAL PRIMARY KEY,
    user_id INT REFERENCES users(id),
    tanggal_pesan TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    total_harga DECIMAL(10, 2),
    status VARCHAR(20) DEFAULT 'PENDING'
);

CREATE TABLE order_details (
    id SERIAL PRIMARY KEY,
    order_id INT REFERENCES orders(id),
    menu_id INT REFERENCES menus(id),
    jumlah INT NOT NULL,
    subtotal DECIMAL(10, 2) NOT NULL
);


INSERT INTO users (username, password, role) VALUES 
('admin', 'admin123', 'admin'),
('customer1', 'pass123', 'customer');

INSERT INTO menus (nama_makanan, kategori, harga, is_available) VALUES 
('Nasi Goreng Spesial', 'Makanan', 25000, TRUE),
('Ayam Bakar Madu', 'Makanan', 30000, TRUE),
('Bakmi Jowo', 'Makanan', 22000, TRUE),
('Es Teh Manis', 'Minuman', 5000, TRUE),
('Jus Jeruk', 'Minuman', 12000, TRUE),
('Kentang Goreng', 'Snack', 15000, TRUE),
('Pisang Goreng', 'Snack', 10000, FALSE);

INSERT INTO orders (user_id, total_harga, status) VALUES 
(2, 55000, 'PAID');

INSERT INTO order_details (order_id, menu_id, jumlah, subtotal) VALUES 
(1, 1, 1, 25000),
(1, 2, 1, 30000);