package com.example.controller;

import com.example.dao.TaiKhoanDAO;
import com.example.model.TaiKhoan;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TaiKhoanController {

    private final TaiKhoanDAO taiKhoanDAO;

    public TaiKhoanController() {
        this.taiKhoanDAO = TaiKhoanDAO.getInstance();
    }

    // Lấy danh sách tài khoản
    public List<TaiKhoan> getDanhSachTaiKhoan() {
        try {
            return taiKhoanDAO.layDsTaiKhoan();
        } catch (SQLException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    // Thêm tài khoản mới
    public void themTaiKhoan(String tenTaiKhoan, int soDu) {
        try {
            taiKhoanDAO.themTaiKhoan(tenTaiKhoan, soDu);
        } catch (Exception e) {
            throw new RuntimeException("Lỗi khi thêm tài khoản: " + e.getMessage());
        }
    }

    // Cập nhật tài khoản
    public void suaTaiKhoan(String tenTaiKhoan, String matKhau, int soDu) {
        try {
            taiKhoanDAO.suaTaiKhoan(tenTaiKhoan, matKhau, soDu);
        } catch (Exception e) {
            throw new RuntimeException("Lỗi khi cập nhật tài khoản: " + e.getMessage());
        }
    }

    // TinhTkMoi
    public int TinhTkMoi() {
        try {
            return taiKhoanDAO.tinhTkMoi();
        } catch (Exception e) {
            throw new RuntimeException("Lỗi khi tính tài khoản mới: " + e.getMessage(), e);
        }
    }

    // Tìm kiếm tài khoản theo tên
    public List<TaiKhoan> timTaiKhoan(String tenTaiKhoan) {
        try {
            return taiKhoanDAO.timTaiKhoan(tenTaiKhoan);
        } catch (SQLException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    // Mở khóa tài khoản
    public boolean moKhoaTaiKhoan(String tenTaiKhoan) {
        try {
            taiKhoanDAO.moKhoaTaiKhoan(tenTaiKhoan);
            return true;
        } catch (Exception e) {
            throw new RuntimeException("Lỗi khi mở khóa tài khoản: " + e.getMessage());
        }
    }

    // Khóa tài khoản
    public boolean khoaTaiKhoan(String tenTaiKhoan) {
        try {
            taiKhoanDAO.khoaTaiKhoan(tenTaiKhoan);
            return true;
        } catch (Exception e) {
            throw new RuntimeException("Lỗi khi khóa tài khoản: " + e.getMessage());
        }
    }
}