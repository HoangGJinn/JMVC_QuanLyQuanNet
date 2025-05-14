package com.example.dao;

import com.example.util.DatabaseConnection;
import com.example.model.MayTinh;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MayTinhDAO {
    private static MayTinhDAO instance;
    private static Connection conn;

    private MayTinhDAO() {
        conn = DatabaseConnection.getInstance();  // Singleton kết nối
    }

    public static MayTinhDAO getInstance() {
        if (instance == null) {
            synchronized (MayTinhDAO.class) {
                if (instance == null) {
                    instance = new MayTinhDAO();
                }
            }
        }
        return instance;
    }

    // Lấy danh sách máy theo mã loại
    public List<MayTinh> layDsMayTheoMaLoai(int maLoaiMay) throws SQLException {
        List<MayTinh> list = new ArrayList<>();
        String sql = "EXEC proc_layMayTheoMaLoai ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, maLoaiMay);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    MayTinh mt = new MayTinh(
                            rs.getString("SoMay"),
                            rs.getString("TrangThai"),
                            rs.getDate("NgayLapDat"),
                            rs.getInt("MaLoaiMay"),
                            rs.getDouble("TongTGSD")
                    );
                    list.add(mt);
                }
            }
        }
        return list;
    }

    // Lấy toàn bộ danh sách máy
    public List<MayTinh> layDsMayTinh() throws SQLException {
        List<MayTinh> list = new ArrayList<>();
        String sql = "SELECT * FROM DanhSachMayTinh ORDER BY SoMay ASC";
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                MayTinh mt = new MayTinh(
                        rs.getString("SoMay"),
                        rs.getString("TrangThai"),
                        rs.getDate("NgayLapDat"),
                        rs.getInt("MaLoaiMay"),
                        rs.getDouble("TongTGSD")
                );
                list.add(mt);
            }
        }
        return list;
    }

    // Thêm máy tính
    public boolean themMayTinh(java.sql.Date ngayLapDat, int maLoaiMay) throws SQLException {
        String sql = "EXEC proc_themMayTinh ?, ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setDate(1, ngayLapDat);
            stmt.setInt(2, maLoaiMay);
            stmt.executeUpdate();
            return true;
        }
    }

    // Sửa máy tính
    public boolean suaMayTinh(String soMay, String trangThai, int maLoaiMay) throws SQLException {
        String sql = "EXEC proc_suaMayTinh ?, ?, ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, soMay);
            stmt.setString(2, trangThai);
            stmt.setInt(3, maLoaiMay);
            stmt.executeUpdate();
            return true;
        }
    }

    // Tìm kiếm máy tính theo mã máy
    public List<MayTinh> timKiemMayTinh(String searchText) throws SQLException {
        List<MayTinh> list = new ArrayList<>();
        String sql = "SELECT * FROM DanhSachMayTinh WHERE SoMay LIKE ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, "%" + searchText + "%");
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    MayTinh mt = new MayTinh(
                            rs.getString("SoMay"),
                            rs.getString("TrangThai"),
                            rs.getDate("NgayLapDat"),
                            rs.getInt("MaLoaiMay"),
                            rs.getDouble("TongTGSD")
                    );
                    list.add(mt);
                }
            }
        }
        return list;
    }

    // Gọi hàm scalar: tổng máy hoạt động
    public int tinhTongMayHD() throws SQLException {
        String sql = "SELECT dbo.fn_TinhTongMayHD()";
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            if (rs.next()) {
                return rs.getInt(1);
            }
        }
        return 0;
    }

    // Lấy giá tiền theo giờ
    public int getGiaTienTheoGio(String soMay) throws SQLException {
        String sql = "SELECT dbo.fn_GetGiaTienTheoGio (?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, soMay);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getObject(1) == null ? 0 : rs.getInt(1);
                }
            }
        }
        return 0;
    }

    // Lấy tên tài khoản đang sử dụng máy
    public String layTKDangSuDungMay(String soMay) throws SQLException {
        String sql = "EXEC proc_TaiKhoanSuDungMay ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, soMay);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getString(1);
                }
            }
        }
        return null;
    }
}
