package com.example.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final String SERVER_NAME = "localhost";
    private static final String DATABASE_NAME = "QuanLyTiemNet";
    private static final String CONNECTION_URL = "jdbc:sqlserver://" + SERVER_NAME + ";"
            + "databaseName=" + DATABASE_NAME + ";"
            + "integratedSecurity=true;"
            + "encrypt=true;trustServerCertificate=true";

    private static Connection connection;

    private DatabaseConnection() {
    }

    public static Connection getInstance() {
        try {
            if (connection == null || connection.isClosed()) {  // 🔍 kiểm tra connection còn sống
                synchronized (DatabaseConnection.class) {
                    if (connection == null || connection.isClosed()) {
                        connection = DriverManager.getConnection(CONNECTION_URL);
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("❌ Kết nối thất bại:");
            e.printStackTrace();
        }
        return connection;
    }
}