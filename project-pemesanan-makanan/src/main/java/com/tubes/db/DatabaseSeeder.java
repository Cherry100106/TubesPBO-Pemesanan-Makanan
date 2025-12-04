
package com.tubes.db;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;


public class DatabaseSeeder {

    private static final Logger logger = Logger.getLogger(DatabaseSeeder.class.getName());

    // Utility class: hide constructor
    private DatabaseSeeder() {}

    public static void seed(Connection conn) {
        try {
            InputStream inputStream = DatabaseSeeder.class.getClassLoader().getResourceAsStream("db_init.sql");
            if (inputStream == null) {
                logger.warning("File SQL tidak ditemukan!");
                return;
            }

            String sqlContent;
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
                sqlContent = reader.lines().collect(Collectors.joining("\n"));
            }

            String[] sqlStatements = sqlContent.split(";");

            try (Statement stmt = conn.createStatement()) {
                for (String sql : sqlStatements) {
                    sql = sql.trim();
                    if (sql.isEmpty()) continue;
                    logger.log(Level.INFO, "Menjalankan: {0};", sql);
                    stmt.addBatch(sql + ";");
                }
                stmt.executeBatch();
            }

            logger.info("Database berhasil di-seed (Reset & Isi Data)!");

        } catch (IOException | SQLException e) {
            logger.log(Level.SEVERE, "Gagal melakukan seeding database", e);
        }
    }
}
