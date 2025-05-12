package com.example.dao;

import com.example.model.NhanVien;
import com.example.util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;

public class NhanVienDAO {
    private static NhanVienDAO instance;
    private static Connection conn;

    private NhanVienDAO() {
        conn = DatabaseConnection.getInstance();
    }

    public static NhanVienDAO getInstance() {
        if (instance == null) {
            synchronized (NhanVienDAO.class) {
                if (instance == null) {
                    instance = new NhanVienDAO();
                }
            }
        }
        return instance;
    }

    public List<NhanVien> layDsNhanVien() {
        List<NhanVien> list = new ArrayList<>();
        String sql = "SELECT * FROM DanhSachNhanVien";
        try (PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                NhanVien nv = new NhanVien(
                        rs.getString("MaNV"),
                        rs.getString("HoTen"),
                        rs.getString("SDT"),
                        rs.getString("DiaChi"),
                        rs.getString("GioiTinh"),
                        rs.getDate("NgaySinh")
                );
                list.add(nv);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public void themNhanVien(String hoTen, String sdt, String diaChi, String gioiTinh, Date ngaySinh) {
        String sql = "EXEC sp_ThemNhanVien ?, ?, ?, ?, ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, hoTen);
            stmt.setString(2, sdt);
            stmt.setString(3, diaChi);
            stmt.setString(4, gioiTinh);
            stmt.setDate(5, new java.sql.Date(ngaySinh.getTime()));
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void doiMatKhau(String sdt, String matKhau) {
        String sql = "EXEC proc_doiMkNv ?, ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, sdt);
            stmt.setString(2, matKhau);
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void suaNhanVien(String maNV, String hoTen, String diaChi, String gioiTinh, Date ngaySinh) {
        String sql = "EXEC sp_SuaNhanVien ?, ?, ?, ?, ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, maNV);
            stmt.setString(2, hoTen);
            stmt.setString(3, diaChi);
            stmt.setString(4, gioiTinh);
            stmt.setDate(5, new java.sql.Date(ngaySinh.getTime()));
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void xoaNhanVien(String maNV) {
        String sql = "EXEC sp_XoaNhanVien ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, maNV);
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<NhanVien> timNhanVien(String timKiem) {
        List<NhanVien> list = new ArrayList<>();
        String sql = "SELECT * FROM fn_TimKiemNhanVien(?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, timKiem);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                NhanVien nv = new NhanVien(
                        rs.getString("MaNV"),
                        rs.getString("HoTen"),
                        rs.getString("SDT"),
                        rs.getString("DiaChi"),
                        rs.getString("GioiTinh"),
                        rs.getDate("NgaySinh")
                );
                list.add(nv);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
}
