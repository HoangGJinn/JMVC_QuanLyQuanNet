package com.example.dao;

import com.example.model.TaiKhoan;
import com.example.util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TaiKhoanDAO {
    private static TaiKhoanDAO instance;
    private static Connection conn;

    private TaiKhoanDAO() {
        conn = DatabaseConnection.getInstance();  // Giả sử bạn đã có singleton trong DatabaseConnection
    }

    public static TaiKhoanDAO getInstance() {
        if (instance == null) {
            synchronized (TaiKhoanDAO.class) {
                if (instance == null) {
                    instance = new TaiKhoanDAO();
                }
            }
        }
        return instance;
    }

    // Lấy danh sách tài khoản
    public List<TaiKhoan> layDsTaiKhoan() {
        List<TaiKhoan> list = new ArrayList<>();
        String sql = "SELECT * FROM DanhSachTaiKhoan";
        try (PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                TaiKhoan tk = new TaiKhoan(
                        rs.getString("TenTaiKhoan"),
                        rs.getInt("SoDu")
                );
                list.add(tk);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    // Thêm tài khoản mới
    public void themTaiKhoan(String tenTaiKhoan, int soDu) {
        String sql = "EXEC sp_ThemTaiKhoan ?, ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, tenTaiKhoan);
            stmt.setInt(2, soDu);
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Cập nhật tài khoản
    public void suaTaiKhoan(String tenTaiKhoan, int soDu) {
        String sql = "EXEC sp_SuaTaiKhoan ?, ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, tenTaiKhoan);
            stmt.setInt(2, soDu);
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Xóa tài khoản
    public void xoaTaiKhoan(String tenTaiKhoan) {
        String sql = "EXEC sp_XoaTaiKhoan ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, tenTaiKhoan);
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Tìm tài khoản theo tên
    public List<TaiKhoan> timTaiKhoan(String tenTaiKhoan) {
        List<TaiKhoan> list = new ArrayList<>();
        String sql = "SELECT * FROM fn_TimKiemTaiKhoan(?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, tenTaiKhoan);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                TaiKhoan tk = new TaiKhoan(
                        rs.getString("TenTaiKhoan"),
                        rs.getInt("SoDu")
                );
                list.add(tk);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    // Mở khóa tài khoản
    public void moKhoaTaiKhoan(String tenTaiKhoan) {
        String sql = "EXEC proc_MoKhoaTaiKhoan ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, tenTaiKhoan);
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Khóa tài khoản
    public void khoaTaiKhoan(String tenTaiKhoan) {
        String sql = "EXEC proc_KhoaTaiKhoan ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, tenTaiKhoan);
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
