package com.umbell.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseUtil {
    private static final String DB_PATH = "src/main/resources/database/umbell.db";
    private static final String DB_URL = "jdbc:sqlite:" + DB_PATH;

    public static Connection connect() throws SQLException {
        return DriverManager.getConnection(DB_URL);
    }
} 