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

    private DichVuDAO() {}

    public static DichVuDAO getInstance() {
        if (instance == null) {
            instance = new DichVuDAO();
        }
        return instance;
    }

    // Common methods for all service types
    public void xoaDichVu(String maDV) {
        String sql = "EXEC proc_XoaDV ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, maDV);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Lỗi khi xóa dịch vụ: " + e.getMessage());
        }
    }

    // Phone Card Service methods (Thẻ Cào)
    public List<DvTheCao> layDSDVTC() {
        List<DvTheCao> result = new ArrayList<>();
        String sql = "SELECT * FROM View_DichVuTheCao";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
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
            throw new RuntimeException("Lỗi khi lấy danh sách thẻ cào: " + e.getMessage());
        }

        return result;
    }

    public void themDVTheCao(String loaiThe, int menhGia) {
        String sql = "EXEC proc_ThemDVTheCao ?, ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, loaiThe);
            stmt.setInt(2, menhGia);
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Lỗi khi thêm thẻ cào: " + e.getMessage());
        }
    }

    public void suaDVTheCao(String maDV, String loaiThe, int menhGia) {
        String sql = "EXEC proc_SuaDVTheCao ?, ?, ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, maDV);
            stmt.setString(2, loaiThe);
            stmt.setInt(3, menhGia);
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Lỗi khi sửa thẻ cào: " + e.getMessage());
        }
    }

    public List<DvTheCao> timKiemDVTC(String tuKhoa) {
        List<DvTheCao> result = new ArrayList<>();
        String sql = "SELECT * FROM fn_TimKiemDVTC(?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

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
            throw new RuntimeException("Lỗi khi tìm kiếm thẻ cào: " + e.getMessage());
        }

        return result;
    }

    // Food Service methods (Đồ Ăn)
    public List<DvDoAn> layDSDVDA() {
        List<DvDoAn> result = new ArrayList<>();
        String sql = "SELECT * FROM View_DichVuDoAn";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
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
            throw new RuntimeException("Lỗi khi lấy danh sách đồ ăn: " + e.getMessage());
        }

        return result;
    }

    public List<DvDoAn> timKiemDVDA(String tuKhoa) {
        List<DvDoAn> result = new ArrayList<>();
        String sql = "SELECT * FROM fn_TimKiemDVDA(?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

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
            throw new RuntimeException("Lỗi khi tìm kiếm đồ ăn: " + e.getMessage());
        }

        return result;
    }

    public void themDVDoAn(String tenDoAn, int donGia, boolean bestSeller) {
        String sql = "EXEC proc_ThemDVDoAn ?, ?, ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, tenDoAn);
            stmt.setInt(2, donGia);
            stmt.setBoolean(3, bestSeller);
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Lỗi khi thêm đồ ăn: " + e.getMessage());
        }
    }

    public void suaDVDoAn(String maDV, String tenDoAn, int donGia, boolean bestSeller, String trangThai) {
        String sql = "EXEC proc_SuaDVDoAn ?, ?, ?, ?, ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, maDV);
            stmt.setString(2, tenDoAn);
            stmt.setInt(3, donGia);
            stmt.setBoolean(4, bestSeller);
            stmt.setString(5, trangThai);
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Lỗi khi sửa đồ ăn: " + e.getMessage());
        }
    }

    public List<TopItem> layTop5DA() {
        List<TopItem> result = new ArrayList<>();
        String sql = "SELECT * FROM Top5DoAn";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                TopItem item = new TopItem(
                        rs.getString("TenDoAn"),
                        rs.getInt("SoLuong")
                );
                result.add(item);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Lỗi khi lấy top 5 đồ ăn: " + e.getMessage());
        }

        return result;
    }

    public List<TopItem> layTop5DAIt() {
        List<TopItem> result = new ArrayList<>();
        String sql = "SELECT * FROM Top5DoAnItNhat";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                TopItem item = new TopItem(
                        rs.getString("TenDoAn"),
                        rs.getInt("SoLuong")
                );
                result.add(item);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Lỗi khi lấy top 5 đồ ăn ít nhất: " + e.getMessage());
        }

        return result;
    }

    // Beverage Service methods (Đồ Uống)
    public List<DvDoUong> layDSDVDU() {
        List<DvDoUong> result = new ArrayList<>();
        String sql = "SELECT * FROM View_DichVuDoUong";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
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
            throw new RuntimeException("Lỗi khi lấy danh sách đồ uống: " + e.getMessage());
        }

        return result;
    }

    public List<DvDoUong> timKiemDVDU(String tuKhoa) {
        List<DvDoUong> result = new ArrayList<>();
        String sql = "SELECT * FROM fn_TimKiemDVDU(?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

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
            throw new RuntimeException("Lỗi khi tìm kiếm đồ uống: " + e.getMessage());
        }

        return result;
    }

    public void themDVDoUong(String tenDoUong, int donGia, boolean bestSeller) {
        String sql = "EXEC proc_ThemDVDouong ?, ?, ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, tenDoUong);
            stmt.setInt(2, donGia);
            stmt.setBoolean(3, bestSeller);
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Lỗi khi thêm đồ uống: " + e.getMessage());
        }
    }

    public void suaDVDoUong(String maDV, String tenDoUong, int donGia, boolean bestSeller, String trangThai) {
        String sql = "EXEC proc_SuaDVDouong ?, ?, ?, ?, ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, maDV);
            stmt.setString(2, tenDoUong);
            stmt.setInt(3, donGia);
            stmt.setBoolean(4, bestSeller);
            stmt.setString(5, trangThai);
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Lỗi khi sửa đồ uống: " + e.getMessage());
        }
    }

    public List<TopItem> layTop5DU() {
        List<TopItem> result = new ArrayList<>();
        String sql = "SELECT * FROM Top5DoUong";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                TopItem item = new TopItem(
                        rs.getString("TenDoUong"),
                        rs.getInt("SoLuong")
                );
                result.add(item);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Lỗi khi lấy top 5 đồ uống: " + e.getMessage());
        }

        return result;
    }

    public List<TopItem> layTop5DUIt() {
        List<TopItem> result = new ArrayList<>();
        String sql = "SELECT * FROM Top5DoUongItNhat";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                TopItem item = new TopItem(
                        rs.getString("TenDoUong"),
                        rs.getInt("SoLuong")
                );
                result.add(item);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Lỗi khi lấy top 5 đồ uống ít nhất: " + e.getMessage());
        }

        return result;
    }

    // Computer usage methods
    public void batDauThue(String soMay, String tenDangNhap) {
        String sql = "EXEC sp_BatDauSuDungMay ?, ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, tenDangNhap.isEmpty() ? null : tenDangNhap);
            stmt.setString(2, soMay.isEmpty() ? null : soMay);
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Lỗi khi bắt đầu thuê máy: " + e.getMessage());
        }
    }

    public void ketThucThue(String soMay, String tenDangNhap) {
        String sql = "EXEC sp_KetThucSuDungMay ?, ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, tenDangNhap.isEmpty() ? null : tenDangNhap);
            stmt.setString(2, soMay.isEmpty() ? null : soMay);
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Lỗi khi kết thúc thuê máy: " + e.getMessage());
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
}