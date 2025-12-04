package com.tubes.order;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.tubes.db.Koneksi;
import com.tubes.factory.OrderFactory;

public class OrderRepository {
    private static final Logger LOGGER = Logger.getLogger(OrderRepository.class.getName());

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
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error fetching all orders", e);
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
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, e, () -> "Error fetching order details for order ID: " + orderId);
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
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error fetching order count today", e);
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
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error fetching total income today", e);
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
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error fetching out of stock items sold today", e);
            return 0;
        }
    }

    public int getTotalItemsSoldToday() {
        String sql = """
            SELECT COALESCE(SUM(od.jumlah), 0) 
            FROM order_details od 
            JOIN orders o ON od.order_id = o.id 
            WHERE o.tanggal_pesan::date = CURRENT_DATE
        """;
        
        try (Connection conn = Koneksi.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error fetching total items sold today", e);
        }
        return 0;
    }

    public String getBestSellingItemToday() {
        String sql = """
            SELECT m.nama_makanan 
            FROM order_details od 
            JOIN orders o ON od.order_id = o.id 
            JOIN menus m ON od.menu_id = m.id 
            WHERE o.tanggal_pesan::date = CURRENT_DATE 
            GROUP BY m.nama_makanan 
            ORDER BY SUM(od.jumlah) DESC 
            LIMIT 1
        """;

        try (Connection conn = Koneksi.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            if (rs.next()) {
                return rs.getString("nama_makanan");
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error fetching best selling item", e);
        }
        return "-";
    }
}