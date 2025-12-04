package com.tubes.order;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.tubes.cart.CartItem;
import com.tubes.db.Koneksi;

public class OrderService {

    private static final Logger logger = Logger.getLogger(OrderService.class.getName());

    @SuppressWarnings("java:S2139") // Exception is logged and rethrown with context
    public int saveOrder(List<CartItem> items, String namaPelanggan) throws OrderServiceException {
        if (items == null || items.isEmpty()) {
            throw new IllegalArgumentException("Keranjang tidak boleh kosong");
        }
        if (namaPelanggan == null || namaPelanggan.trim().isEmpty()) {
            throw new IllegalArgumentException("Nama pelanggan wajib diisi");
        }

        try (Connection conn = Koneksi.getConnection()) {
            if (conn == null) {
                throw new SQLException("Gagal membuka koneksi database");
            }
            conn.setAutoCommit(false);

            String sqlOrder = "INSERT INTO orders (nama_pelanggan, total_harga, status, tanggal_pesan) VALUES (?, ?, 'PAID', CURRENT_TIMESTAMP) RETURNING id";
            try (PreparedStatement ps = conn.prepareStatement(sqlOrder)) {
                ps.setString(1, namaPelanggan.trim());
                ps.setDouble(2, hitungTotal(items));

                int orderId;
                try (ResultSet rs = ps.executeQuery()) {
                    rs.next();
                    orderId = rs.getInt(1);
                }

                String sqlDetail = "INSERT INTO order_details (order_id, menu_id, jumlah, subtotal) VALUES (?, ?, ?, ?)";
                try (PreparedStatement psd = conn.prepareStatement(sqlDetail)) {
                    psd.setInt(1, orderId);
                    for (CartItem item : items) {
                        psd.setInt(2, item.getMenu().getId());
                        psd.setInt(3, item.getQuantity());
                        psd.setDouble(4, item.getSubtotal());
                        psd.addBatch();
                    }
                    psd.executeBatch();
                }

                conn.commit();
                return orderId;
            }

        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Gagal menyimpan pesanan", e);
            throw new OrderServiceException("Gagal menyimpan pesanan: " + e.getMessage(), e);
        }
    }

    private double hitungTotal(List<CartItem> items) {
        return items.stream().mapToDouble(CartItem::getSubtotal).sum();
    }
}