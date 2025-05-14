package com.example.controller;

import com.example.dao.KhuyenMaiDAO;
import com.example.model.KhuyenMai;
import com.example.model.LoaiKM;

import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

public class KhuyenMaiController {
    private static KhuyenMaiController instance;
    private KhuyenMaiDAO khuyenMaiDAO;

    public KhuyenMaiController() {
        khuyenMaiDAO = KhuyenMaiDAO.getInstance();
    }
    public KhuyenMaiController(KhuyenMaiDAO khuyenMaiDAO) {
        this.khuyenMaiDAO = khuyenMaiDAO;
    }
    public static KhuyenMaiController getInstance() {
        if (instance == null) {
            instance = new KhuyenMaiController();
        }
        return instance;
    }

    public List<KhuyenMai> layDanhSachKhuyenMai() {
        return khuyenMaiDAO.LayDsKhuyenMai();
    }

    public List<KhuyenMai> timKhuyenMai(String query, Date batdau, Date ketthuc) {
        return khuyenMaiDAO.TimKhuyenMai(query, batdau, ketthuc);
    }

    public List<LoaiKM> layDanhSachLoaiKM() {
        return khuyenMaiDAO.LayDsLoaiKM();
    }

    public boolean themKhuyenMai(String tenChTrinh, int tyLeKM, int soTienToiThieuApDung,
                               int kmToiDa, Date thoiGianBatDau, Date thoiGianKetThuc, int maLoaiKM) throws SQLException {
        try {
            return khuyenMaiDAO.ThemKhuyenMai(tenChTrinh, tyLeKM, soTienToiThieuApDung,
                                        kmToiDa, thoiGianBatDau, thoiGianKetThuc, maLoaiKM);
        } catch (SQLException e) {
            // Re-throw the SQL exception so it can be caught and displayed properly
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Lỗi khi thêm khuyến mãi: " + e.getMessage(), e);
        }
    }

    public boolean suaKhuyenMai(String maKM, String tenChTrinh, int tyLeKM, int soTienToiThieuApDung,
                              int kmToiDa, Date thoiGianBatDau, Date thoiGianKetThuc, int maLoaiKM) throws SQLException {
        try {
            return khuyenMaiDAO.SuaKhuyenMai(maKM, tenChTrinh, tyLeKM, soTienToiThieuApDung,
                                       kmToiDa, thoiGianBatDau, thoiGianKetThuc, maLoaiKM);
        } catch (SQLException e) {
            // Re-throw the SQL exception so it can be caught and displayed properly
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Lỗi khi cập nhật khuyến mãi: " + e.getMessage(), e);
        }
    }

    public boolean xoaKhuyenMai(String maKM) throws SQLException {
        try {
            // Gọi DAO và trả về kết quả
            boolean result = khuyenMaiDAO.XoaKhuyenMai(maKM);
            
            // Log để debug
            System.out.println("Controller xoaKhuyenMai result: " + result);
            
            return result;
        } catch (SQLException e) {
            // Re-throw the SQL exception so it can be caught and displayed properly
            System.err.println("SQL Exception in xoaKhuyenMai: " + e.getMessage());
            throw e;
        } catch (Exception e) {
            System.err.println("Exception in xoaKhuyenMai: " + e.getMessage());
            throw new RuntimeException("Lỗi khi xóa khuyến mãi: " + e.getMessage(), e);
        }
    }

    public List<KhuyenMai> layDanhSachKhuyenMaiTheoTienNap(int tienNap) {
        return khuyenMaiDAO.LayDsKhuyenMaiTheoTienNap(tienNap);
    }

    public int soKhuyenMaiSapHetHan(int soNgay) {
        return khuyenMaiDAO.SoKhuyenMaiSapHetHan(soNgay);
    }

    public int soKhuyenMaiKhaDung() {
        return khuyenMaiDAO.SoKMKhaDung();
    }
}