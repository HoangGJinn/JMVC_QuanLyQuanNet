package com.example.controller;

import com.example.dao.NhanVienDAO;
import com.example.model.NhanVien;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

public class NhanVienController {

    private final NhanVienDAO nhanVienDAO;

    public NhanVienController() {
        this.nhanVienDAO = NhanVienDAO.getInstance();
    }

    public List<NhanVien> layDanhSachNhanVien() {
        return nhanVienDAO.layDsNhanVien();
    }

    public void themNhanVien(String hoTen, String sdt, String diaChi, String gioiTinh, LocalDate ngaySinh) {
        nhanVienDAO.themNhanVien(hoTen, sdt, diaChi, gioiTinh, Date.valueOf(ngaySinh));
    }

    public void suaNhanVien(String maNV, String hoTen, String diaChi, String gioiTinh, LocalDate ngaySinh) {
        nhanVienDAO.suaNhanVien(maNV, hoTen, diaChi, gioiTinh, Date.valueOf(ngaySinh));
    }

    public void xoaNhanVien(String maNV) {
        nhanVienDAO.xoaNhanVien(maNV);
    }

    public void doiMatKhau(String sdt, String matKhauMoi) {
        nhanVienDAO.doiMatKhau(sdt, matKhauMoi);
    }

    public List<NhanVien> timNhanVien(String tuKhoa) {
        return nhanVienDAO.timNhanVien(tuKhoa);
    }
}
