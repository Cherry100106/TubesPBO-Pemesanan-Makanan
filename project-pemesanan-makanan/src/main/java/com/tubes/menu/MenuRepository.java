package com.tubes.menu;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.tubes.db.Koneksi;

public class MenuRepository {

    public List<MenuItem> getAllMenus() {
        List<MenuItem> menus = new ArrayList<>();

        String sql = "SELECT id, nama_makanan, kategori, harga, is_available FROM menus WHERE is_available = TRUE";

        try (Connection conn = Koneksi.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                menus.add(new MenuItem(
                        rs.getInt("id"),
                        rs.getString("nama_makanan"),
                        rs.getString("kategori"),
                        rs.getInt("harga"),
                        rs.getBoolean("is_available")
                ));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return menus;
    }
}
