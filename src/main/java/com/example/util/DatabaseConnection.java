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
        if (connection == null) {
            synchronized (DatabaseConnection.class) {
                if (connection == null) {
                    try {
                        connection = DriverManager.getConnection(CONNECTION_URL);
                        System.out.println("✅ Đã kết nối SQL Server thành công.");
                    } catch (SQLException e) {
                        System.err.println("❌ Kết nối thất bại:");
                        e.printStackTrace();
                    }
                }
            }
        }
        return connection;
    }

    public static void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
                connection = null;
                System.out.println("🔌 Đã đóng kết nối cơ sở dữ liệu.");
            } catch (SQLException e) {
                System.err.println("❌ Lỗi khi đóng kết nối:");
                e.printStackTrace();
            }
        }
    }

    public static void resetInstance() {
        closeConnection(); // đóng kết nối cũ
        getInstance();     // khởi tạo lại kết nối
    }
}