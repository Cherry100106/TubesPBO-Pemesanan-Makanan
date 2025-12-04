package com.tubes.menu;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.tubes.db.Koneksi;

public class MenuRepository {

    private static final Logger LOGGER = Logger.getLogger(MenuRepository.class.getName());

    public List<MenuItem> getAllMenus() {
        List<MenuItem> menus = new ArrayList<>();

        String sql = "SELECT id, nama_makanan, kategori, harga, is_available FROM menus";

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
            LOGGER.log(Level.SEVERE, "Failed to fetch menus from database", e);
        }

        return menus;
    }
}
