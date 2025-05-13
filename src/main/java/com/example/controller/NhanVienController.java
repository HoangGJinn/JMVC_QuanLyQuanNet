package com.example.controller;

import com.example.dao.NhanVienDAO;
import com.example.model.NhanVien;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class NhanVienController {

    private final NhanVienDAO nhanVienDAO;

    public NhanVienController() {
        this.nhanVienDAO = NhanVienDAO.getInstance();
    }

    public List<NhanVien> layDanhSachNhanVien() {
        try {
            return nhanVienDAO.layDsNhanVien();
        } catch (Exception e) {
            // Log lỗi nếu cần
            e.printStackTrace();
            return new ArrayList<>(); // Hoặc trả về danh sách rỗng
        }
    }

    public void themNhanVien(String hoTen, String sdt, String diaChi, String gioiTinh, LocalDate ngaySinh) {
        try {
            nhanVienDAO.themNhanVien(hoTen, sdt, diaChi, gioiTinh, Date.valueOf(ngaySinh));
        } catch (Exception e) {
            // Log lỗi nếu cần
            e.printStackTrace();
        }
    }

    public void suaNhanVien(String maNV, String hoTen, String diaChi, String gioiTinh, LocalDate ngaySinh) {
        try {
            nhanVienDAO.suaNhanVien(maNV, hoTen, diaChi, gioiTinh, Date.valueOf(ngaySinh));
        } catch (Exception e) {
            // Log lỗi nếu cần
            e.printStackTrace();
        }
    }

    public void xoaNhanVien(String maNV) {
        try {
            nhanVienDAO.xoaNhanVien(maNV);
        } catch (Exception e) {
            // Log lỗi nếu cần
            e.printStackTrace();
        }
    }

    public void doiMatKhau(String sdt, String matKhauMoi) {
        try {
            nhanVienDAO.doiMatKhau(sdt, matKhauMoi);
        } catch (Exception e) {
            // Log lỗi nếu cần
            e.printStackTrace();
        }
    }

    public List<NhanVien> timNhanVien(String tuKhoa) {
        try {
            return nhanVienDAO.timNhanVien(tuKhoa);
        } catch (Exception e) {
            // Log lỗi nếu cần
            e.printStackTrace();
            return null; // Hoặc trả về danh sách rỗng
        }
    }
}
