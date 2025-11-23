package com.tubes.order;

import com.tubes.db.Koneksi;
import com.tubes.factory.OrderFactory;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class OrderRepository {

    public List<Order> getAllOrders() {
        List<Order> orders = new ArrayList<>();
        String sql = """
        SELECT id, nama_pelanggan, total_harga, tanggal_pesan, status
        FROM orders
        ORDER BY tanggal_pesan DESC
        """;

        try (Connection conn = Koneksi.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Order order = OrderFactory.createFromResultSet(rs);
                orders.add(order);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return orders;
    }

    public List<OrderDetail> getOrderDetails(int orderId) {
        List<OrderDetail> details = new ArrayList<>();
        String sql = """
            SELECT od.jumlah, od.subtotal, m.nama_makanan
            FROM order_details od
            JOIN menus m ON od.menu_id = m.id
            WHERE od.order_id = ?
            """;

        try (Connection conn = Koneksi.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, orderId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                OrderDetail d = new OrderDetail();
                d.setJumlah(rs.getInt("jumlah"));
                d.setSubtotal(rs.getDouble("subtotal"));
                d.setNamaMenu(rs.getString("nama_makanan"));
                details.add(d);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return details;
    }

public int getOrderCountToday() {
    String sql = "SELECT COUNT(*) FROM orders WHERE tanggal_pesan::date = CURRENT_DATE";
    try (Connection conn = Koneksi.getConnection();
         PreparedStatement stmt = conn.prepareStatement(sql);
         ResultSet rs = stmt.executeQuery()) {
        rs.next();
        return rs.getInt(1);
    } catch (Exception e) {
        e.printStackTrace();
        return 0;
    }
}

public double getTotalIncomeToday() {
    String sql = "SELECT COALESCE(SUM(total_harga), 0) FROM orders WHERE tanggal_pesan::date = CURRENT_DATE";
    try (Connection conn = Koneksi.getConnection();
         PreparedStatement stmt = conn.prepareStatement(sql);
         ResultSet rs = stmt.executeQuery()) {
        rs.next();
        return rs.getDouble(1);
    } catch (Exception e) {
        e.printStackTrace();
        return 0.0;
    }
}

    public int getOutOfStockItemsSoldToday() {
        String sql = """
            SELECT COUNT(*)
            FROM order_details od
            JOIN orders o ON od.order_id = o.id
            JOIN menus m ON od.menu_id = m.id
            WHERE DATE(o.tanggal_pesan) = CURRENT_DATE
            AND m.is_available = FALSE
            """;
        try (Connection conn = Koneksi.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            rs.next();
            return rs.getInt(1);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }
}