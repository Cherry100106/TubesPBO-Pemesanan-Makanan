package com.tubes.order;

import com.tubes.cart.CartItem;
import com.tubes.db.Koneksi;
import java.sql.*;
import java.util.List;

public class OrderService {

    public int saveOrder(List<CartItem> items, String namaPelanggan) {
        if (items == null || items.isEmpty()) {
            throw new IllegalArgumentException("Keranjang tidak boleh kosong");
        }
        if (namaPelanggan == null || namaPelanggan.trim().isEmpty()) {
            throw new IllegalArgumentException("Nama pelanggan wajib diisi");
        }

        try (Connection conn = Koneksi.getConnection()) {
            conn.setAutoCommit(false);

            String sqlOrder = "INSERT INTO orders (nama_pelanggan, total_harga, status) VALUES (?, ?, 'PAID') RETURNING id";
            try (PreparedStatement ps = conn.prepareStatement(sqlOrder)) {
                ps.setString(1, namaPelanggan.trim());
                ps.setDouble(2, hitungTotal(items));

                ResultSet rs = ps.executeQuery();
                rs.next();
                int orderId = rs.getInt(1);

                
                String sqlDetail = "INSERT INTO order_details (order_id, menu_id, jumlah, subtotal) VALUES (?, ?, ?, ?)";
                try (PreparedStatement psd = conn.prepareStatement(sqlDetail)) {
                    for (CartItem item : items) {
                        psd.setInt(1, orderId);
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

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Gagal menyimpan pesanan: " + e.getMessage());
        }
    }

    private double hitungTotal(List<CartItem> items) {
        return items.stream().mapToDouble(CartItem::getSubtotal).sum();
    }
}