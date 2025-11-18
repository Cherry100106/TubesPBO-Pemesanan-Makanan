package com.tubes.db;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.Statement;
import java.util.stream.Collectors;

public class DatabaseSeeder {
    
    public static void seed(Connection conn) {
        try {
            InputStream inputStream = DatabaseSeeder.class.getClassLoader().getResourceAsStream("db_init.sql");
            if (inputStream == null) {
                System.out.println("File SQL tidak ditemukan!");
                return;
            }
            
            String sql = new BufferedReader(new InputStreamReader(inputStream))
                    .lines().collect(Collectors.joining("\n"));
            
            Statement stmt = conn.createStatement();
            stmt.execute(sql);
            System.out.println("Database berhasil di-seed (Reset & Isi Data)!");
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}