package com.example.dao;

import com.example.model.KhuyenMai;
import com.example.model.LoaiKM;
import com.example.util.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class KhuyenMaiDAO {
    private static KhuyenMaiDAO instance;
    private static Connection connection;

    private KhuyenMaiDAO() {
        connection = DatabaseConnection.getInstance();
    }

    public static KhuyenMaiDAO getInstance() {
        if (instance == null) {
            synchronized (KhuyenMaiDAO.class) {
                if (instance == null) {
                    instance = new KhuyenMaiDAO();
                }
            }
        }
        return instance;
    }

    public List<KhuyenMai> LayDsKhuyenMai() {
        List<KhuyenMai> danhSachKM = new ArrayList<>();
        try {
            // Use the DanhSachKhuyenMai view which includes the joined data
            String query = "SELECT * FROM DanhSachKhuyenMai";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                KhuyenMai khuyenMai = new KhuyenMai();
                khuyenMai.setMaKM(resultSet.getString("MaKM"));
                khuyenMai.setTenChuongTrinh(resultSet.getString("TenChTrinh"));
                khuyenMai.setToiThieu(resultSet.getInt("SoTienToiThieuApDung"));
                khuyenMai.setKmToiDa(resultSet.getInt("KMToiDa"));
                khuyenMai.setTyLe(resultSet.getInt("TyLeKM"));
                khuyenMai.setBatDau(resultSet.getDate("ThoiGianBatDau"));
                khuyenMai.setKetThuc(resultSet.getDate("ThoiGianKetThuc"));
                khuyenMai.setLoaiKM(resultSet.getString("TenLoai"));


                danhSachKM.add(khuyenMai);
            }

            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            System.err.println("Error loading KhuyenMai: " + e.getMessage());
            e.printStackTrace();
        }
        return danhSachKM;
    }

    public List<KhuyenMai> TimKhuyenMai(String query, Date batdau, Date ketthuc) {
        List<KhuyenMai> danhSachKM = new ArrayList<>();
        try {
            // Trước tiên, hãy log thông tin để debug
            System.out.println("Search query: [" + query + "], Start date: [" + batdau + "], End date: [" + ketthuc + "]");

            // Dùng query trực tiếp thay vì stored procedure để kiểm soát tốt hơn
            String sql = "SELECT KM.MaKM, KM.TenChTrinh, KM.TyLeKM, KM.SoTienToiThieuApDung, KM.KMToiDa, " +
                    "KM.ThoiGianBatDau, KM.ThoiGianKetThuc, LKM.TenLoai " +
                    "FROM KHUYENMAI KM JOIN LOAIKM LKM ON KM.MaLoaiKM = LKM.MaLoai " +
                    "WHERE (KM.TenChTrinh LIKE ? OR KM.MaKM LIKE ?) " +
                    "AND (KM.ThoiGianBatDau >= ? AND KM.ThoiGianKetThuc <= ?)";

            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, "%" + query + "%");
            statement.setString(2, "%" + query + "%");
            statement.setDate(3, batdau);
            statement.setDate(4, ketthuc);

            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                KhuyenMai khuyenMai = new KhuyenMai();
                khuyenMai.setMaKM(resultSet.getString("MaKM"));
                khuyenMai.setTenChuongTrinh(resultSet.getString("TenChTrinh"));
                khuyenMai.setToiThieu(resultSet.getInt("SoTienToiThieuApDung"));
                khuyenMai.setKmToiDa(resultSet.getInt("KMToiDa"));
                khuyenMai.setTyLe(resultSet.getInt("TyLeKM"));
                khuyenMai.setBatDau(resultSet.getDate("ThoiGianBatDau"));
                khuyenMai.setKetThuc(resultSet.getDate("ThoiGianKetThuc"));
                khuyenMai.setLoaiKM(resultSet.getString("TenLoai"));

                danhSachKM.add(khuyenMai);
            }

            resultSet.close();
            statement.close();

            System.out.println("Found " + danhSachKM.size() + " promotions matching the search criteria");
        } catch (SQLException e) {
            System.err.println("Error searching KhuyenMai: " + e.getMessage());
            e.printStackTrace();
        }
        return danhSachKM;
    }

    public List<LoaiKM> LayDsLoaiKM() {
        List<LoaiKM> danhSachLoaiKM = new ArrayList<>();
        try
        {
            String query = "SELECT * FROM LOAIKM";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                LoaiKM loaiKM = new LoaiKM();
                loaiKM.setMaLoai(resultSet.getInt("MaLoai"));
                loaiKM.setTenLoai(resultSet.getString("TenLoai"));

                danhSachLoaiKM.add(loaiKM);
            }

            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            System.err.println("Error loading LoaiKM: " + e.getMessage());
            e.printStackTrace();
        }
        return danhSachLoaiKM;
    }

    public boolean SuaKhuyenMai(String maKM, String tenChTrinh, int tyLeKM, int soTienToiThieuApDung, int kmToiDa, Date thoiGianBatDau, Date thoiGianKetThuc, int maLoaiKM) throws SQLException {
        String sql = "EXEC sp_UpdateKhuyenMai ?, ?, ?, ?, ?, ?, ?, ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, maKM);
        statement.setString(2, tenChTrinh);
        statement.setInt(3, tyLeKM);
        statement.setInt(4, soTienToiThieuApDung);
        statement.setInt(5, kmToiDa);
        statement.setDate(6, thoiGianBatDau);
        statement.setDate(7, thoiGianKetThuc);
        statement.setInt(8, maLoaiKM);

        int rowsUpdated = statement.executeUpdate();
        statement.close();

        System.out.println("Query result: " + (rowsUpdated > 0 ? "Success" : "Failed"));
        return rowsUpdated > 0;
    }

    public boolean ThemKhuyenMai(String tenChTrinh, int tyLeKM, int soTienToiThieuApDung, int kmToiDa, Date thoiGianBatDau, Date thoiGianKetThuc, int maLoaiKM) throws SQLException {
        String sql = "EXEC sp_InsertKhuyenMai ?, ?, ?, ?, ?, ?, ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, tenChTrinh);
        statement.setInt(2, tyLeKM);
        statement.setInt(3, soTienToiThieuApDung);
        statement.setInt(4, kmToiDa);
        statement.setDate(5, thoiGianBatDau);
        statement.setDate(6, thoiGianKetThuc);
        statement.setInt(7, maLoaiKM);

        int rowsAffected = statement.executeUpdate();
        statement.close();
        return rowsAffected > 0;
    }

    public boolean XoaKhuyenMai(String maKM) throws SQLException {
        String sql = "EXEC sp_DeleteKhuyenMai ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, maKM);
        int rowsAffected = statement.executeUpdate();
        statement.close();

        // Log để debug
        System.out.println("XoaKhuyenMai affected rows: " + rowsAffected);

        // Trả về true chỉ khi có ít nhất 1 dòng bị ảnh hưởng
        return rowsAffected > 0;
    }

    public List<KhuyenMai> LayDsKhuyenMaiTheoTienNap(int tienNap) {
        List<KhuyenMai> danhSachKM = new ArrayList<>();
        String sql = "SELECT * FROM fn_TimKMChoHDNapTien (?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)){
            stmt.setInt(1, tienNap);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                KhuyenMai khuyenMai = new KhuyenMai();
                khuyenMai.setMaKM(rs.getString("MaKM"));
                khuyenMai.setTenChuongTrinh(rs.getString("TenChTrinh"));
                khuyenMai.setToiThieu(rs.getInt("SoTienToiThieuApDung"));
                khuyenMai.setKmToiDa(rs.getInt("KMToiDa"));
                khuyenMai.setTyLe(rs.getInt("TyLeKM"));
                khuyenMai.setBatDau(rs.getDate("ThoiGianBatDau"));
                khuyenMai.setKetThuc(rs.getDate("ThoiGianKetThuc"));
                khuyenMai.setLoaiKM(rs.getString("TenLoai"));


                danhSachKM.add(khuyenMai);
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();

        }
        return danhSachKM;
    }

    public int SoKhuyenMaiSapHetHan(int soNgay) {
        int count = 0;
        // Use direct SQL query instead of function call
        String sql = "SELECT COUNT(*) FROM KHUYENMAI WHERE DATEDIFF(day, GETDATE(), ThoiGianKetThuc) <= ? AND DATEDIFF(day, GETDATE(), ThoiGianKetThuc) >= 0";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, soNgay);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                count = resultSet.getInt(1);
            }

            resultSet.close();
        } catch (SQLException e) {
            System.err.println("Error counting promotions about to expire: " + e.getMessage());
            e.printStackTrace();
        }

        return count;
    }

    public int SoKMKhaDung() {
        int count = 0;
        // Use direct SQL query instead of function call
        String sql = "SELECT COUNT(*) FROM KHUYENMAI WHERE GETDATE() BETWEEN ThoiGianBatDau AND ThoiGianKetThuc";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                count = resultSet.getInt(1);
            }

            resultSet.close();
        } catch (SQLException e) {
            System.err.println("Error counting available promotions: " + e.getMessage());
            e.printStackTrace();
        }
        return count;
    }
}
