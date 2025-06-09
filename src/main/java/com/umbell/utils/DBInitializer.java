package com.umbell.utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DBInitializer {
    private static final String DB_PATH = "src/main/resources/database/umbell.db";
    private static final String DB_URL = "jdbc:sqlite:" + DB_PATH;
    private static final String SCHEMA_PATH = "src/main/resources/database/umbell_schema.sql";

    public static void initialize() {
        createDatabaseIfNotExists();
        executeSchema();
    }

    private static void createDatabaseIfNotExists() {
        File dbFile = new File(DB_PATH);
        if (!dbFile.exists()) {
            try {
                dbFile.getParentFile().mkdirs();
                dbFile.createNewFile();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private static void executeSchema() {
        try {
            String schema = Files.readString(Paths.get(SCHEMA_PATH));
            
            try (Connection conn = DriverManager.getConnection(DB_URL);
                 Statement stmt = conn.createStatement()) {
                
                String[] statements = schema.split(";");
                
                for (String statement : statements) {
                    if (!statement.trim().isEmpty()) {
                        stmt.execute(statement);
                    }
                }
                
                System.out.println("Database schema executed successfully.");
            }
        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }
    }
}