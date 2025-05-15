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
    public List<TaiKhoan> layDsTaiKhoan() throws SQLException {
        List<TaiKhoan> list = new ArrayList<>();
        String sql = "SELECT * FROM DanhSachTaiKhoan";
        try (PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                TaiKhoan tk = new TaiKhoan(
                        rs.getString("TenDangNhap"),
                        rs.getString("MatKhau"),
                        rs.getInt("SoDu"),
                        rs.getDate("NgayTao"),
                        rs.getString("TrangThai")

                );
                list.add(tk);
            }

        }
        return list;
    }

    // Thêm tài khoản mới
    public void themTaiKhoan(String tenTaiKhoan, int soDu) throws SQLException {
        String sql = "EXEC sp_ThemTaiKhoan ?, ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, tenTaiKhoan);
            stmt.setInt(2, soDu);
            stmt.executeUpdate();
        }
    }

    // Cập nhật tài khoản
    public void suaTaiKhoan(String tenTaiKhoan, String matKhau, int soDu) throws SQLException {
        String sql = "EXEC sp_SuaTaiKhoan ?, ?, ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            // Nếu mật khẩu rỗng, truyền NULL thay vì giá trị rỗng
            if (matKhau == null || matKhau.isEmpty()) {
                stmt.setNull(2, Types.VARCHAR);  // Mật khẩu có thể là NULL nếu không có giá trị
            } else {
                stmt.setString(2, matKhau);  // Nếu có mật khẩu, truyền vào tham số thứ hai
            }

            // Truyền tham số vào câu lệnh
            stmt.setString(1, tenTaiKhoan);  // Tham số đầu tiên là tên tài khoản
            stmt.setInt(3, soDu);  // Tham số thứ ba là số dư

            // Thực thi câu lệnh
            stmt.executeUpdate();
        }
    }


    // Ví dụ thêm phương thức thực thi hàm scalar trong SQL Server
    public int tinhTkMoi() throws SQLException {
        String sql = "SELECT dbo.fn_TKMoiTrongThang()"; // Gọi hàm trong SQL Server
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            if (rs.next()) {
                return rs.getInt(1);  // Lấy kết quả trả về từ hàm scalar
            }

        }
        return 0; // Trả về 0 nếu không có kết quả
    }

    // Tìm tài khoản theo tên
    public List<TaiKhoan> timTaiKhoan(String tenDangNhap) throws SQLException {
        List<TaiKhoan> list = new ArrayList<>();
        String sql = "SELECT * FROM fn_LayTaiKhoanTheoTenDangNhap(?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, tenDangNhap);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                TaiKhoan tk = new TaiKhoan(
                        rs.getString("TenDangNhap"),
                        rs.getString("MatKhau"),
                        rs.getInt("SoDu"),
                        rs.getDate("NgayTao"),
                        rs.getString("TrangThai")
                );
                list.add(tk);
            }
        }
        return list;
    }

    // Mở khóa tài khoản
    public void moKhoaTaiKhoan(String tenTaiKhoan) throws SQLException {
        String sql = "EXEC proc_MoKhoaTaiKhoan ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, tenTaiKhoan);
            stmt.executeUpdate();
        }
    }

    // Khóa tài khoản
    public void khoaTaiKhoan(String tenTaiKhoan) throws SQLException {
        String sql = "EXEC proc_KhoaTaiKhoan ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, tenTaiKhoan);
            stmt.executeUpdate();
        }
    }
}