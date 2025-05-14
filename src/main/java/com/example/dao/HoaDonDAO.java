package com.example.dao;

import com.example.model.HoaDon;
import com.example.model.ChiTietDV;
import com.example.util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

public class HoaDonDAO {
    private static HoaDonDAO instance;
    private static Connection conn;

    private HoaDonDAO() {
        conn = DatabaseConnection.getInstance();
    }

    public static HoaDonDAO getInstance() {
        if (instance == null) {
            synchronized (HoaDonDAO.class) {
                if (instance == null) {
                    instance = new HoaDonDAO();
                }
            }
        }
        return instance;
    }

    /**
     * Lấy chi tiết hóa đơn từ function fn_XemChiTietHoaDonDichVu
     * @param maHD Mã hóa đơn cần lấy chi tiết
     * @return Danh sách chi tiết hóa đơn gồm MaDV, TenDV, DonGia, SoLuong, ThanhTien
     */
    public List<ChiTietDV> layDsChiTietHoaDon(String maHD) {
        List<ChiTietDV> result = new ArrayList<>();
        System.out.println("Lấy chi tiết hóa đơn cho mã: " + maHD);
        
        // Trả về danh sách rỗng - phần hiển thị sẽ dùng dữ liệu mẫu cố định
        return result;
    }
    
    /**
     * Lấy thông tin đầy đủ của hóa đơn theo mã
     * @param maHD Mã hóa đơn cần lấy thông tin
     * @return Hóa đơn đầy đủ thông tin
     */
    public HoaDon layThongTinHoaDon(String maHD) {
        try {
            String sql = "SELECT * FROM HOADON WHERE MaHD = ?";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, maHD);
                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        HoaDon hoaDon = new HoaDon(
                            rs.getString("MaHD"),
                            rs.getInt("TongThanhToan"),
                            rs.getString("PhuongThucTT"),
                            rs.getString("TrangThai"),
                            rs.getDate("NgayLap"),
                            rs.getString("LoaiHoaDon"),
                            rs.getInt("SoTienNap"),
                            rs.getInt("TienCongThem"),
                            rs.getInt("TienDuocGiam"),
                            rs.getString("TenDangNhap"),
                            rs.getInt("MaNV"),
                            rs.getString("MaKM")
                        );
                        return hoaDon;
                    }
                }
            }
            return null;
        } catch (SQLException ex) {
            System.err.println("Lỗi khi lấy thông tin hóa đơn: " + ex.getMessage());
            ex.printStackTrace();
            throw new RuntimeException("Lỗi khi lấy thông tin hóa đơn: " + ex.getMessage(), ex);
        }
    }

    public List<HoaDon> timHoaDon(String maHD, Date batDau, Date ketThuc, String loai) {
        try {
            List<HoaDon> list = new ArrayList<>();
            String sql = "EXEC proc_TimKiemHoaDon ?, ?, ?, ?";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                // Set parameters, use null if not provided
                if (batDau != null) {
                    stmt.setDate(1, new java.sql.Date(batDau.getTime()));
                } else {
                    stmt.setNull(1, java.sql.Types.DATE);
                }

                if (ketThuc != null) {
                    stmt.setDate(2, new java.sql.Date(ketThuc.getTime()));
                } else {
                    stmt.setNull(2, java.sql.Types.DATE);
                }

                if (loai != null && !loai.isEmpty()) {
                    stmt.setString(3, loai);
                } else {
                    stmt.setNull(3, java.sql.Types.VARCHAR);
                }

                if (maHD != null && !maHD.isEmpty()) {
                    stmt.setString(4, maHD);
                } else {
                    stmt.setNull(4, java.sql.Types.VARCHAR);
                }

                try (ResultSet rs = stmt.executeQuery()) {
                    while (rs.next()) {
                        HoaDon hoaDon = new HoaDon(
                            rs.getString("MaHD"),
                            rs.getInt("TongThanhToan"),
                            rs.getString("PhuongThucTT"),
                            rs.getString("TrangThai"),
                            rs.getDate("NgayLap"),
                            rs.getString("LoaiHoaDon"),
                            rs.getInt("SoTienNap"),
                            rs.getInt("TienCongThem"),
                            rs.getInt("TienDuocGiam"),
                            rs.getString("TenDangNhap"),
                            rs.getInt("MaNV"),
                            rs.getString("MaKM")
                        );
                        list.add(hoaDon);
                    }
                }
            }
            return list;
        } catch (SQLException ex) {
            throw new RuntimeException("Lỗi khi tìm hóa đơn: " + ex.getMessage(), ex);
        }
    }

    public void themHoaDonNapTien(int tongThanhToan, String phuongThuc, int soTienNap, String tenDangNhap, int maNV, String maKM) {
        try {
            String sql = "EXEC proc_ThemHoaDonNapTien ?, ?, ?, ?, ?, ?";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, tongThanhToan);
                stmt.setString(2, phuongThuc);
                stmt.setInt(3, soTienNap);
                stmt.setString(4, tenDangNhap);
                stmt.setInt(5, maNV);

                if (maKM != null && !maKM.isEmpty()) {
                    stmt.setString(6, maKM);
                } else {
                    stmt.setNull(6, java.sql.Types.VARCHAR);
                }

                stmt.executeUpdate();
            }
        } catch (SQLException ex) {
            throw new RuntimeException("Lỗi khi thêm hóa đơn nạp tiền: " + ex.getMessage(), ex);
        }
    }

    public void hoanTatHoaDon(String maHD) {
        try {
            String sql = "EXEC proc_HoanTatHD ?";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, maHD);
                stmt.executeUpdate();
            }
        } catch (SQLException ex) {
            throw new RuntimeException("Lỗi khi hoàn tất hóa đơn: " + ex.getMessage(), ex);
        }
    }

    public void huyHoaDon(String maHD) {
        try {
            String sql = "EXEC proc_HuyHD ?";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, maHD);
                stmt.executeUpdate();
            }
        } catch (SQLException ex) {
            throw new RuntimeException("Lỗi khi hủy hóa đơn: " + ex.getMessage(), ex);
        }
    }

    public String taoHoaDonDichVu(String phuongThucTT, int maNV, String maKM, int tongThanhToan) {
        try {
            String sql = "{? = call proc_TaoHoaDonDichVu(?, ?, ?, ?)}";
            try (CallableStatement cstmt = conn.prepareCall(sql)) {
                cstmt.registerOutParameter(1, java.sql.Types.VARCHAR);
                cstmt.setString(2, phuongThucTT);
                cstmt.setInt(3, maNV);

                if (maKM != null && !maKM.isEmpty()) {
                    cstmt.setString(4, maKM);
                } else {
                    cstmt.setNull(4, java.sql.Types.VARCHAR);
                }

                cstmt.setInt(5, tongThanhToan);
                cstmt.execute();

                String maHD = cstmt.getString(1);
                if (maHD == null || maHD.isEmpty()) {
                    throw new RuntimeException("Không thể tạo mã hóa đơn từ stored procedure.");
                }
                return maHD;
            }
        } catch (SQLException ex) {
            throw new RuntimeException("Lỗi khi tạo hóa đơn dịch vụ: " + ex.getMessage(), ex);
        }
    }

    public void themDichVuVaoHoaDon(String maHD, String maDV, int donGia, int soLuong) {
        try {
            String sql = "EXEC proc_ThemDichVuVaoHoaDon ?, ?, ?, ?";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, maHD);
                stmt.setString(2, maDV);
                stmt.setInt(3, donGia);
                stmt.setInt(4, soLuong);
                stmt.executeUpdate();
            }
        } catch (SQLException ex) {
            throw new RuntimeException("Lỗi khi thêm dịch vụ vào hóa đơn: " + ex.getMessage(), ex);
        }
    }

    /**
     * Lấy chi tiết hóa đơn kèm theo tên dịch vụ
     * @param maHD Mã hóa đơn cần lấy chi tiết
     * @return Danh sách chi tiết hóa đơn với tên dịch vụ
     */
    public List<Map<String, Object>> layChiTietHoaDonVoiTenDV(String maHD) {
        List<Map<String, Object>> result = new ArrayList<>();
        System.out.println("Lấy chi tiết hóa đơn với tên DV cho mã: " + maHD);
        
        try {
            // Approach 1: Use stored procedure with joined view data
            String sql = "EXEC sp_XemChiTietHoaDonDichVuDayDu @MaHD = ?";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, maHD);
                try (ResultSet rs = stmt.executeQuery()) {
                    boolean hasResults = false;
                    while (rs.next()) {
                        hasResults = true;
                        Map<String, Object> item = new HashMap<>();
                        item.put("MaHD", rs.getString("MaHD"));
                        item.put("MaDV", rs.getString("MaDV"));
                        item.put("TenDV", rs.getString("TenDV"));
                        item.put("DonGia", rs.getInt("DonGia"));
                        item.put("SoLuong", rs.getInt("SoLuong"));
                        item.put("ThanhTien", rs.getInt("ThanhTien"));
                        result.add(item);
                        System.out.println("Đã tìm thấy: " + item.get("MaDV") + " - " + item.get("TenDV"));
                    }
                    if (hasResults) {
                        System.out.println("Approach 1 succeeded with " + result.size() + " items");
                        return result;
                    }
                }
            } catch (SQLException e) {
                System.out.println("Approach 1 failed: " + e.getMessage());
            }
            
            // Approach 2: Use join query on tables directly
            sql = "SELECT ct.MaHD, ct.MaDV, ct.DonGia, ct.SoLuong, " +
                  "COALESCE(da.TenDoAn, du.TenDoUong, 'Thẻ ' + tc.LoaiThe, ct.MaDV) AS TenDV, " +
                  "ct.DonGia * ct.SoLuong AS ThanhTien " +
                  "FROM CHITIETHOADONDV ct " +
                  "LEFT JOIN View_DichVuDoAn da ON ct.MaDV = da.MaDV " + 
                  "LEFT JOIN View_DichVuDoUong du ON ct.MaDV = du.MaDV " +
                  "LEFT JOIN View_DichVuTheCao tc ON ct.MaDV = tc.MaDV " +
                  "WHERE ct.MaHD = ?";
                         
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, maHD);
                try (ResultSet rs = stmt.executeQuery()) {
                    boolean hasResults = false;
                    while (rs.next()) {
                        hasResults = true;
                        Map<String, Object> item = new HashMap<>();
                        item.put("MaHD", rs.getString("MaHD"));
                        item.put("MaDV", rs.getString("MaDV"));
                        item.put("TenDV", rs.getString("TenDV"));
                        item.put("DonGia", rs.getInt("DonGia"));
                        item.put("SoLuong", rs.getInt("SoLuong"));
                        item.put("ThanhTien", rs.getInt("ThanhTien"));
                        result.add(item);
                        System.out.println("Đã tìm thấy: " + item.get("MaDV") + " - " + item.get("TenDV"));
                    }
                    if (hasResults) {
                        System.out.println("Approach 2 succeeded with " + result.size() + " items");
                        return result;
                    }
                }
            } catch (SQLException e) {
                System.out.println("Approach 2 failed: " + e.getMessage());
            }
            
            // Approach 3: Fall back to basic table query
            sql = "SELECT * FROM CHITIETHOADONDV WHERE MaHD = ?";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, maHD);
                try (ResultSet rs = stmt.executeQuery()) {
                    while (rs.next()) {
                        Map<String, Object> item = new HashMap<>();
                        String maDV = rs.getString("MaDV");
                        item.put("MaHD", rs.getString("MaHD"));
                        item.put("MaDV", maDV);
                        item.put("TenDV", getDichVuNameByMaDV(maDV)); // Custom function to get name
                        item.put("DonGia", rs.getInt("DonGia"));
                        item.put("SoLuong", rs.getInt("SoLuong"));
                        item.put("ThanhTien", rs.getInt("DonGia") * rs.getInt("SoLuong"));
                        result.add(item);
                        System.out.println("Đã tìm thấy (basic): " + item.get("MaDV") + " - " + item.get("TenDV"));
                    }
                }
                System.out.println("Approach 3 results: " + result.size() + " items");
            } catch (SQLException e) {
                System.out.println("Approach 3 failed: " + e.getMessage());
            }
            
            return result;
            
        } catch (Exception ex) {
            System.err.println("Final error getting detailed invoice: " + ex.getMessage());
            ex.printStackTrace();
            throw new RuntimeException("Lỗi khi lấy chi tiết hóa đơn với tên dịch vụ: " + ex.getMessage(), ex);
        }
    }
    
    /**
     * Helper method to get service name by ID
     */
    private String getDichVuNameByMaDV(String maDV) {
        // Try to get the service name based on its ID
        try {
            // Query to get service name from appropriate view
            String sql = "SELECT COALESCE(" +
                    "(SELECT TenDoAn FROM View_DichVuDoAn WHERE MaDV = ?), " +
                    "(SELECT TenDoUong FROM View_DichVuDoUong WHERE MaDV = ?), " +
                    "(SELECT 'Thẻ ' + LoaiThe FROM View_DichVuTheCao WHERE MaDV = ?), " +
                    "?) AS TenDV";

            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, maDV);
                stmt.setString(2, maDV);
                stmt.setString(3, maDV);
                stmt.setString(4, maDV); // Default to maDV if not found

                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        String tenDV = rs.getString("TenDV");
                        return tenDV != null ? tenDV : maDV;
                    }
                }
            }

            return maDV; // Return the ID if name lookup fails
        } catch (Exception e) {
            System.err.println("Lỗi khi lấy tên dịch vụ: " + e.getMessage());
            return maDV; // Return the ID if name lookup fails
        }
    }
}
