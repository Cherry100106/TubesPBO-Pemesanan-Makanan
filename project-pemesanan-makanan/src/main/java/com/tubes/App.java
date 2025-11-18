package com.tubes;

import com.tubes.db.Koneksi;
import com.tubes.db.DatabaseSeeder;
import java.sql.Connection;

public class App {
    public static void main(String[] args) {
        System.out.println("Memulai Aplikasi...");

        // 1. Cek Koneksi
        Connection conn = Koneksi.getConnection();
        
        if (conn != null) {
            System.out.println("Koneksi ke PostgreSQL Berhasil!");
            
            // 2. Jalankan Seeder (Otomatis buat tabel & data dummy)
            DatabaseSeeder.seed(conn);
            
            // 3. Lanjut logika aplikasi...
        } else {
            System.out.println("Gagal terhubung ke Database.");
        }
    }
}