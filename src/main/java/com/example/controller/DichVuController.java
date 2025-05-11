package com.example.controller;

import com.example.dao.DichVuDAO;
import com.example.dao.DichVuDAO.TopItem;
import com.example.model.DvDoAn;
import com.example.model.DvDoUong;
import com.example.model.DvTheCao;

import java.util.List;

public class DichVuController {
    private final DichVuDAO dichVuDAO;

    public DichVuController() {
        this.dichVuDAO = DichVuDAO.getInstance();
    }

    // General service methods
    public void xoaDichVu(String maDV) {
        dichVuDAO.xoaDichVu(maDV);
    }

    // Thẻ Cào (Phone Card) methods
    public List<DvTheCao> layDanhSachTheCao() {
        return dichVuDAO.layDSDVTC();
    }

    public void themTheCao(String loaiThe, int menhGia) {
        if (loaiThe == null || loaiThe.trim().isEmpty()) {
            throw new IllegalArgumentException("Loại thẻ không được để trống");
        }
        if (menhGia <= 0) {
            throw new IllegalArgumentException("Mệnh giá phải lớn hơn 0");
        }
        dichVuDAO.themDVTheCao(loaiThe, menhGia);
    }

    public void suaTheCao(String maDV, String loaiThe, int menhGia) {
        if (maDV == null || maDV.trim().isEmpty()) {
            throw new IllegalArgumentException("Mã thẻ cào không được để trống");
        }
        if (loaiThe == null || loaiThe.trim().isEmpty()) {
            throw new IllegalArgumentException("Loại thẻ không được để trống");
        }
        if (menhGia <= 0) {
            throw new IllegalArgumentException("Mệnh giá phải lớn hơn 0");
        }
        dichVuDAO.suaDVTheCao(maDV, loaiThe, menhGia);
    }

    public List<DvTheCao> timKiemTheCao(String tuKhoa) {
        return dichVuDAO.timKiemDVTC(tuKhoa);
    }

    // Đồ Ăn (Food) methods
    public List<DvDoAn> layDanhSachDoAn() {
        return dichVuDAO.layDSDVDA();
    }

    public void themDoAn(String tenDoAn, int donGia, boolean bestSeller) {
        if (tenDoAn == null || tenDoAn.trim().isEmpty()) {
            throw new IllegalArgumentException("Tên đồ ăn không được để trống");
        }
        if (donGia <= 0) {
            throw new IllegalArgumentException("Đơn giá phải lớn hơn 0");
        }
        dichVuDAO.themDVDoAn(tenDoAn, donGia, bestSeller);
    }

    public void suaDoAn(String maDV, String tenDoAn, int donGia, boolean bestSeller, String trangThai) {
        if (maDV == null || maDV.trim().isEmpty()) {
            throw new IllegalArgumentException("Mã đồ ăn không được để trống");
        }
        if (tenDoAn == null || tenDoAn.trim().isEmpty()) {
            throw new IllegalArgumentException("Tên đồ ăn không được để trống");
        }
        if (donGia <= 0) {
            throw new IllegalArgumentException("Đơn giá phải lớn hơn 0");
        }
        if (trangThai == null || trangThai.trim().isEmpty()) {
            throw new IllegalArgumentException("Trạng thái không được để trống");
        }
        dichVuDAO.suaDVDoAn(maDV, tenDoAn, donGia, bestSeller, trangThai);
    }

    public List<DvDoAn> timKiemDoAn(String tuKhoa) {
        return dichVuDAO.timKiemDVDA(tuKhoa);
    }

    public List<TopItem> layTop5DoAnBanChay() {
        return dichVuDAO.layTop5DA();
    }

    public List<TopItem> layTop5DoAnBanIt() {
        return dichVuDAO.layTop5DAIt();
    }

    // Đồ Uống (Beverage) methods
    public List<DvDoUong> layDanhSachDoUong() {
        return dichVuDAO.layDSDVDU();
    }

    public void themDoUong(String tenDoUong, int donGia, boolean bestSeller) {
        if (tenDoUong == null || tenDoUong.trim().isEmpty()) {
            throw new IllegalArgumentException("Tên đồ uống không được để trống");
        }
        if (donGia <= 0) {
            throw new IllegalArgumentException("Đơn giá phải lớn hơn 0");
        }
        dichVuDAO.themDVDoUong(tenDoUong, donGia, bestSeller);
    }

    public void suaDoUong(String maDV, String tenDoUong, int donGia, boolean bestSeller, String trangThai) {
        if (maDV == null || maDV.trim().isEmpty()) {
            throw new IllegalArgumentException("Mã đồ uống không được để trống");
        }
        if (tenDoUong == null || tenDoUong.trim().isEmpty()) {
            throw new IllegalArgumentException("Tên đồ uống không được để trống");
        }
        if (donGia <= 0) {
            throw new IllegalArgumentException("Đơn giá phải lớn hơn 0");
        }
        if (trangThai == null || trangThai.trim().isEmpty()) {
            throw new IllegalArgumentException("Trạng thái không được để trống");
        }
        dichVuDAO.suaDVDoUong(maDV, tenDoUong, donGia, bestSeller, trangThai);
    }

    public List<DvDoUong> timKiemDoUong(String tuKhoa) {
        return dichVuDAO.timKiemDVDU(tuKhoa);
    }

    public List<TopItem> layTop5DoUongBanChay() {
        return dichVuDAO.layTop5DU();
    }

    public List<TopItem> layTop5DoUongBanIt() {
        return dichVuDAO.layTop5DUIt();
    }

    // Computer usage methods
    public void batDauSuDungMay(String soMay, String tenDangNhap) {
        dichVuDAO.batDauThue(soMay, tenDangNhap);
    }

    public void ketThucSuDungMay(String soMay, String tenDangNhap) {
        dichVuDAO.ketThucThue(soMay, tenDangNhap);
    }
}