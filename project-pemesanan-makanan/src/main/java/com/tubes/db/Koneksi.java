package com.tubes.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
 @SuppressWarnings("java:S6437") 
public class Koneksi {
    // GANTI 'tubes_pbo' dengan nama database masing masing
    private static final String URL = "jdbc:postgresql://localhost:5432/PBO_db";
    private static final String USER = "postgres";
    private static final String PASS = "Chi.3126"; // Sesuaikan password

    private static final Logger logger = Logger.getLogger(Koneksi.class.getName());

    private Koneksi() {}

    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(URL, USER, PASS);
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Gagal koneksi ke database", e);
            return null;
        }
    }
}
