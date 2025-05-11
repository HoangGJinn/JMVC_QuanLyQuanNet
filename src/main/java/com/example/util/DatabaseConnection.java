package com.example.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final String SERVER_NAME = "localhost";
    private static final String DATABASE_NAME = "QuanLyTiemNet";

    // Chuỗi kết nối sử dụng Windows Authentication (Integrated Security)
    private static final String CONNECTION_URL = "jdbc:sqlserver://" + SERVER_NAME + ";"
            + "databaseName=" + DATABASE_NAME + ";"
            + "integratedSecurity=true;"
            + "encrypt=true;trustServerCertificate=true";

    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(CONNECTION_URL);
        } catch (SQLException e) {
            System.err.println("❌ Kết nối thất bại:");
            e.printStackTrace();
            return null;
        }
    }
}
