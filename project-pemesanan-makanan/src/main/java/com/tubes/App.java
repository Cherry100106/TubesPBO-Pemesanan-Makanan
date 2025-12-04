package com.tubes;

import java.sql.Connection;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.tubes.db.DatabaseSeeder;
import com.tubes.db.Koneksi;

public final class App {

    private static final Logger logger = Logger.getLogger(App.class.getName());

    private App() {}

    public static void main(String[] args) {
        logger.info("Memulai Aplikasi...");

        Connection conn = Koneksi.getConnection();
        if (conn == null) {
            logger.severe("Gagal terhubung ke Database.");
            return;
        }

        try (Connection c = conn) {
            logger.info("Koneksi ke PostgreSQL berhasil.");
            // Jalankan seeder (buat tabel & data dummy)
            DatabaseSeeder.seed(c);
            // Lanjut logika aplikasi...
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Terjadi kesalahan saat menjalankan aplikasi", e);
        }
    }
}