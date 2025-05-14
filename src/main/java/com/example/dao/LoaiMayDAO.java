package com.example.dao;

import com.example.model.LoaiMay;
import com.example.util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LoaiMayDAO {
    private static LoaiMayDAO instance;
    private Connection conn;

    private LoaiMayDAO() {
        conn = DatabaseConnection.getInstance();
    }

    public static LoaiMayDAO getInstance() {
        if (instance == null) {
            synchronized (LoaiMayDAO.class) {
                if (instance == null) {
                    instance = new LoaiMayDAO();
                }
            }
        }
        return instance;
    }

    public List<LoaiMay> layDsLoaiMay() {
        List<LoaiMay> list = new ArrayList<>();
        String sql = "SELECT * FROM DanhSachLoaiMay";

        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                LoaiMay lm = new LoaiMay(
                        rs.getInt("MaLoaiMay"),
                        rs.getString("TenLoaiMay"),
                        rs.getInt("SoTienMotGio")
                );
                list.add(lm);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    public int themLoaiMay(String tenLoaiMay, int soTienMotGio) {
        String sql = "EXEC proc_ThemLoaiMay ?, ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, tenLoaiMay);
            ps.setInt(2, soTienMotGio);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return rs.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public boolean suaLoaiMay(int maLoaiMay, String tenLoaiMay, int soTienMotGio) {
        String sql = "EXEC proc_SuaLoaiMay ?, ?, ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, maLoaiMay);
            ps.setString(2, tenLoaiMay);
            ps.setInt(3, soTienMotGio);
            int affectedRows = ps.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean xoaLoaiMay(int maLoaiMay) {
        String sql = "EXEC proc_XoaLoaiMay ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, maLoaiMay);
            int affectedRows = ps.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<LoaiMay> timKiemLoaiMay(String searchText) {
        List<LoaiMay> list = new ArrayList<>();
        String sql = "SELECT * FROM DanhSachLoaiMay WHERE TenLoaiMay LIKE ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, "%" + searchText + "%");
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    LoaiMay lm = new LoaiMay(
                            rs.getInt("MaLoaiMay"),
                            rs.getString("TenLoaiMay"),
                            rs.getInt("SoTienMotGio")
                    );
                    list.add(lm);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    public boolean themLinhKien(int maLoaiMay, String tenLinhKien, String chiTietLK, int soLuong) {
        String sql = "EXEC proc_themLinhKien ?, ?, ?, ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, maLoaiMay);
            ps.setString(2, tenLinhKien);
            ps.setString(3, chiTietLK);
            ps.setInt(4, soLuong);
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean suaLinhKien(int maLoaiMay, String tenLinhKien, String chiTietLK, int soLuong) {
        String sql = "EXEC proc_suaLinhKien ?, ?, ?, ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, maLoaiMay);
            ps.setString(2, tenLinhKien);
            ps.setString(3, chiTietLK);
            ps.setInt(4, soLuong);
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean xoaLinhKien(int maLoaiMay, String tenLinhKien) {
        String sql = "EXEC proc_xoaLinhKien ?, ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, maLoaiMay);
            ps.setString(2, tenLinhKien);
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<String[]> timLinhKienTheoLoaiMay(int maLoaiMay) {
        List<String[]> list = new ArrayList<>();
        String sql = "SELECT * FROM fn_timLkTheoLoaiMay (?)";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, maLoaiMay);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String[] row = new String[]{
                        rs.getString("TenLinhKien"),
                        rs.getString("ChiTietLK"),
                        String.valueOf(rs.getInt("SoLuong"))
                };
                list.add(row);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }
}
