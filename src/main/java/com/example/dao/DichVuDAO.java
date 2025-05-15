package com.example.dao;

import com.example.model.DichVu;
import com.example.model.DvDoAn;
import com.example.model.DvDoUong;
import com.example.model.DvTheCao;
import com.example.util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DichVuDAO {
    private static DichVuDAO instance;
    private static Connection conn;

    private DichVuDAO() {
        conn = DatabaseConnection.getInstance();
    }

    public static DichVuDAO getInstance() {
        if (instance == null) {
            synchronized (DichVuDAO.class) {
                if (instance == null) {
                    instance = new DichVuDAO();
                }
            }
        }
        return instance;
    }

    // Common methods for all service types
    public void xoaDichVu(String maDV) {
        String sql = "EXEC proc_XoaDV ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, maDV);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Phone Card Service methods (Thẻ Cào)
    public List<DvTheCao> layDSDVTC() {
        List<DvTheCao> result = new ArrayList<>();
        String sql = "SELECT * FROM View_DichVuTheCao";

        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                DvTheCao theCao = new DvTheCao(
                        rs.getString("MaDV"),
                        rs.getInt("MenhGia"),
                        rs.getString("LoaiThe")
                );
                result.add(theCao);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }

    public void themDVTheCao(String loaiThe, int menhGia) {
        String sql = "EXEC proc_ThemDVTheCao ?, ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, loaiThe);
            stmt.setInt(2, menhGia);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void suaDVTheCao(String maDV, String loaiThe, int menhGia) {
        String sql = "EXEC proc_SuaDVTheCao ?, ?, ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, maDV);
            stmt.setString(2, loaiThe);
            stmt.setInt(3, menhGia);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<DvTheCao> timKiemDVTC(String tuKhoa) {
        List<DvTheCao> result = new ArrayList<>();
        String sql = "SELECT * FROM fn_TimKiemDVTC(?)";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, tuKhoa);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                DvTheCao theCao = new DvTheCao(
                        rs.getString("MaDV"),
                        rs.getInt("MenhGia"),
                        rs.getString("LoaiThe")
                );
                result.add(theCao);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }

    // Food Service methods (Đồ Ăn)
    public List<DvDoAn> layDSDVDA() {
        List<DvDoAn> result = new ArrayList<>();
        String sql = "SELECT * FROM View_DichVuDoAn";

        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                DvDoAn doAn = new DvDoAn(
                        rs.getString("MaDV"),
                        rs.getInt("DonGia"),
                        rs.getString("TenDoAn"),
                        rs.getBoolean("BestSeller"),
                        rs.getString("TrangThai")
                );
                result.add(doAn);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }

    public List<DvDoAn> timKiemDVDA(String tuKhoa) {
        List<DvDoAn> result = new ArrayList<>();
        String sql = "SELECT * FROM fn_TimKiemDVDA(?)";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, tuKhoa);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                DvDoAn doAn = new DvDoAn(
                        rs.getString("MaDV"),
                        rs.getInt("DonGia"),
                        rs.getString("TenDoAn"),
                        rs.getBoolean("BestSeller"),
                        rs.getString("TrangThai")
                );
                result.add(doAn);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }

    public void themDVDoAn(String tenDoAn, int donGia, boolean bestSeller) {
        String sql = "EXEC proc_ThemDVDoAn ?, ?, ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, tenDoAn);
            stmt.setInt(2, donGia);
            stmt.setBoolean(3, bestSeller);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void suaDVDoAn(String maDV, String tenDoAn, int donGia, boolean bestSeller, String trangThai) {
        String sql = "EXEC proc_SuaDVDoAn ?, ?, ?, ?, ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, maDV);
            stmt.setString(2, tenDoAn);
            stmt.setInt(3, donGia);
            stmt.setBoolean(4, bestSeller);
            stmt.setString(5, trangThai);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<TopItem> layTop5DA() {
        List<TopItem> result = new ArrayList<>();
        String sql = "SELECT * FROM Top5DoAn";

        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                String tenDoAn = rs.getString("TenDoAn");
                int soLuong = 0;

                // Try different possible column names for quantity
                try {
                    soLuong = rs.getInt("SoLuong");
                } catch (SQLException e1) {
                    try {
                        soLuong = rs.getInt("TongSoLuongBan");
                    } catch (SQLException e2) {
                        // If both column names fail, get the second column (assuming it's the quantity)
                        try {
                            soLuong = rs.getInt(2);
                        } catch (SQLException e3) {
                            // If all attempts fail, use 0 as default
                            System.err.println("Could not find quantity column for food item: " + tenDoAn);
                        }
                    }
                }

                TopItem item = new TopItem(tenDoAn, soLuong);
                result.add(item);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }

    public List<TopItem> layTop5DAIt() {
        List<TopItem> result = new ArrayList<>();
        String sql = "SELECT * FROM Top5DoAnItNhat";

        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                String tenDoAn = rs.getString("TenDoAn");
                int soLuong = 0;

                // Try different possible column names for quantity
                try {
                    soLuong = rs.getInt("SoLuong");
                } catch (SQLException e1) {
                    try {
                        soLuong = rs.getInt("TongSoLuongBan");
                    } catch (SQLException e2) {
                        // If both column names fail, get the second column (assuming it's the quantity)
                        try {
                            soLuong = rs.getInt(2);
                        } catch (SQLException e3) {
                            // If all attempts fail, use 0 as default
                            System.err.println("Could not find quantity column for food item: " + tenDoAn);
                        }
                    }
                }

                TopItem item = new TopItem(tenDoAn, soLuong);
                result.add(item);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }

    // Beverage Service methods (Đồ Uống)
    public List<DvDoUong> layDSDVDU() {
        List<DvDoUong> result = new ArrayList<>();
        String sql = "SELECT * FROM View_DichVuDoUong";

        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                DvDoUong doUong = new DvDoUong(
                        rs.getString("MaDV"),
                        rs.getInt("DonGia"),
                        rs.getBoolean("BestSeller"),
                        rs.getString("TenDoUong"),
                        rs.getString("TrangThai")
                );
                result.add(doUong);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }

    public List<DvDoUong> timKiemDVDU(String tuKhoa) {
        List<DvDoUong> result = new ArrayList<>();
        String sql = "SELECT * FROM fn_TimKiemDVDU(?)";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, tuKhoa);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                DvDoUong doUong = new DvDoUong(
                        rs.getString("MaDV"),
                        rs.getInt("DonGia"),
                        rs.getBoolean("BestSeller"),
                        rs.getString("TenDoUong"),
                        rs.getString("TrangThai")
                );
                result.add(doUong);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }

    public void themDVDoUong(String tenDoUong, int donGia, boolean bestSeller) {
        String sql = "EXEC proc_ThemDVDoUong ?, ?, ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, tenDoUong);
            stmt.setInt(2, donGia);
            stmt.setBoolean(3, bestSeller);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void suaDVDoUong(String maDV, String tenDoUong, int donGia, boolean bestSeller, String trangThai) {
        String sql = "EXEC proc_SuaDVDoUong ?, ?, ?, ?, ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, maDV);
            stmt.setString(2, tenDoUong);
            stmt.setInt(3, donGia);
            stmt.setBoolean(4, bestSeller);
            stmt.setString(5, trangThai);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<TopItem> layTop5DU() {
        List<TopItem> result = new ArrayList<>();
        String sql = "SELECT * FROM Top5DoUong";

        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                String tenDoUong = rs.getString("TenDoUong");
                int soLuong = 0;

                // Try different possible column names for quantity
                try {
                    soLuong = rs.getInt("SoLuong");
                } catch (SQLException e1) {
                    try {
                        soLuong = rs.getInt("TongSoLuongBan");
                    } catch (SQLException e2) {
                        // If both column names fail, get the second column (assuming it's the quantity)
                        try {
                            soLuong = rs.getInt(2);
                        } catch (SQLException e3) {
                            // If all attempts fail, use 0 as default
                            System.err.println("Could not find quantity column for beverage item: " + tenDoUong);
                        }
                    }
                }

                TopItem item = new TopItem(tenDoUong, soLuong);
                result.add(item);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }

    public List<TopItem> layTop5DUIt() {
        List<TopItem> result = new ArrayList<>();
        String sql = "SELECT * FROM Top5DoUongItNhat";

        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                String tenDoUong = rs.getString("TenDoUong");
                int soLuong = 0;

                // Try different possible column names for quantity
                try {
                    soLuong = rs.getInt("SoLuong");
                } catch (SQLException e1) {
                    try {
                        soLuong = rs.getInt("TongSoLuongBan");
                    } catch (SQLException e2) {
                        // If both column names fail, get the second column (assuming it's the quantity)
                        try {
                            soLuong = rs.getInt(2);
                        } catch (SQLException e3) {
                            // If all attempts fail, use 0 as default
                            System.err.println("Could not find quantity column for beverage item: " + tenDoUong);
                        }
                    }
                }

                TopItem item = new TopItem(tenDoUong, soLuong);
                result.add(item);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }

    // Computer usage methods
    public void batDauThue(String soMay, String tenDangNhap) {
        String sql = "EXEC sp_BatDauSuDungMay ?, ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, tenDangNhap.isEmpty() ? null : tenDangNhap);
            stmt.setString(2, soMay.isEmpty() ? null : soMay);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void ketThucThue(String soMay, String tenDangNhap) {
        String sql = "EXEC sp_KetThucSuDungMay ?, ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, tenDangNhap.isEmpty() ? null : tenDangNhap);
            stmt.setString(2, soMay.isEmpty() ? null : soMay);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Refresh connection if needed
    public void refreshConnection() {
        if (conn == null) {
            conn = DatabaseConnection.getInstance();
            return;
        }

        try {
            if (conn.isClosed()) {
                conn = DatabaseConnection.getInstance();
                System.out.println("Database connection refreshed successfully");
            }
        } catch (SQLException e) {
            System.err.println("Error checking connection state: " + e.getMessage());
            conn = DatabaseConnection.getInstance();
            System.out.println("Database connection refreshed after error");
        }
    }

    // Helper class for Top5 results
    public static class TopItem {
        private String ten;
        private int soLuong;

        public TopItem(String ten, int soLuong) {
            this.ten = ten;
            this.soLuong = soLuong;
        }

        public String getTen() {
            return ten;
        }

        public int getSoLuong() {
            return soLuong;
        }
    }

    /**
     * Lấy tên dịch vụ dựa vào mã dịch vụ
     * @param maDV Mã dịch vụ cần tìm
     * @return Tên dịch vụ hoặc mã dịch vụ nếu không tìm thấy
     */
    public String layTenDichVu(String maDV) {
        try {
            String sql = "SELECT * FROM View_DichVu WHERE MaDV = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, maDV);
                try (ResultSet rs = pstmt.executeQuery()) {
                    if (rs.next()) {
                        // Kiểm tra các cột tên dịch vụ có thể có
                        String ten = null;
                        try {
                            ten = rs.getString("TenDV");
                            if (ten != null && !ten.isEmpty()) return ten;
                        } catch (SQLException e) {
                            // Cột không tồn tại, thử cột khác
                        }

                        try {
                            ten = rs.getString("TenDoAn");
                            if (ten != null && !ten.isEmpty()) return ten;
                        } catch (SQLException e) {
                            // Cột không tồn tại, thử cột khác
                        }

                        try {
                            ten = rs.getString("TenDoUong");
                            if (ten != null && !ten.isEmpty()) return ten;
                        } catch (SQLException e) {
                            // Cột không tồn tại, thử cột khác
                        }

                        try {
                            ten = rs.getString("LoaiThe");
                            if (ten != null && !ten.isEmpty()) return "Thẻ " + ten;
                        } catch (SQLException e) {
                            // Cột không tồn tại
                        }
                    }
                }
            }

            // Thử tìm trong các bảng cụ thể nếu view không có kết quả
            String[] queries = {
                "SELECT TenDoAn AS Ten FROM View_DichVuDoAn WHERE MaDV = ?",
                "SELECT TenDoUong AS Ten FROM View_DichVuDoUong WHERE MaDV = ?",
                "SELECT LoaiThe AS Ten FROM View_DichVuTheCao WHERE MaDV = ?"
            };

            for (String query : queries) {
                try (PreparedStatement pstmt = conn.prepareStatement(query)) {
                    pstmt.setString(1, maDV);
                    try (ResultSet rs = pstmt.executeQuery()) {
                        if (rs.next()) {
                            String ten = rs.getString("Ten");
                            if (ten != null && !ten.isEmpty()) {
                                if (query.contains("TheCao")) {
                                    return "Thẻ " + ten;
                                }
                                return ten;
                            }
                        }
                    }
                } catch (SQLException e) {
                    // Bỏ qua lỗi và thử query tiếp theo
                }
            }

            // Trả về mã dịch vụ nếu không tìm thấy tên
            return maDV;
        } catch (Exception e) {
            System.err.println("Lỗi khi lấy tên dịch vụ: " + e.getMessage());
            return maDV; // Trả về mã dịch vụ trong trường hợp lỗi
        }
    }
}
