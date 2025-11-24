DROP TABLE IF EXISTS order_details CASCADE;
DROP TABLE IF EXISTS orders CASCADE;
DROP TABLE IF EXISTS menus CASCADE;


-- Tabel Menu Makanan
CREATE TABLE menus (
    id SERIAL PRIMARY KEY,
    nama_makanan VARCHAR(100) NOT NULL,
    kategori VARCHAR(50),
    harga DECIMAL(10, 2) NOT NULL,
    is_available BOOLEAN DEFAULT TRUE
);

-- Tabel Orders
CREATE TABLE orders (
    id SERIAL PRIMARY KEY,
    nama_pelanggan VARCHAR(100),
    tanggal_pesan TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    total_harga DECIMAL(10, 2) NOT NULL,
    status VARCHAR(20) DEFAULT 'PENDING'
);

-- Tabel Order Details
CREATE TABLE order_details (
    id SERIAL PRIMARY KEY,
    order_id INT REFERENCES orders(id) ON DELETE CASCADE,
    menu_id INT REFERENCES menus(id),
    jumlah INT NOT NULL,
    subtotal DECIMAL(10, 2) NOT NULL
);

-- Data awal: menu
INSERT INTO menus (nama_makanan, kategori, harga, is_available) VALUES 
('Nasi Goreng Spesial', 'Makanan', 25000, TRUE),
('Ayam Bakar Madu', 'Makanan', 30000, TRUE),
('Bakmi Jowo', 'Makanan', 22000, TRUE),
('Es Teh Manis', 'Minuman', 5000, TRUE),
('Jus Jeruk', 'Minuman', 12000, TRUE),
('Kentang Goreng', 'Snack', 15000, TRUE),
('Pisang Goreng', 'Snack', 10000, FALSE);