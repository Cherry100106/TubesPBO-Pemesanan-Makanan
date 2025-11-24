package com.tubes.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Koneksi {
    // GANTI 'tubes_pbo' dengan nama database masing masing
    private static final String URL = "jdbc:postgresql://localhost:5432/PBO_db";
    private static final String USER = "postgres";
    private static final String PASS = "Chi.3126"; // Sesuaikan password

    public static Connection getConnection() {
        try {
            Class.forName("org.postgresql.Driver");
            return DriverManager.getConnection(URL, USER, PASS);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}