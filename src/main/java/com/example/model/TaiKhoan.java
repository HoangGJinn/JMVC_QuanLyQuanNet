package com.example.model;

import java.util.Date;

public class TaiKhoan {
    private String tenDangNhap;
    private String matKhau;
    private int soDu;
    private Date ngayTao;
    private String trangThai; // đã đổi từ boolean sang String

    // Constructor với các tham số
    public TaiKhoan(String tenDangNhap, int soDu) {
        this.tenDangNhap = tenDangNhap;
        this.soDu = soDu;
    }

    public TaiKhoan(String tenDangNhap, String matKhau, int soDu, Date ngayTao, String trangThai) {
        this.tenDangNhap = tenDangNhap;
        this.matKhau = matKhau;
        this.soDu = soDu;
        this.ngayTao = ngayTao;
        this.trangThai = trangThai;
    }

    public String getTenDangNhap() {
        return tenDangNhap;
    }

    public void setTenDangNhap(String tenDangNhap) {
        this.tenDangNhap = tenDangNhap;
    }

    public String getMatKhau() {
        return matKhau;
    }

    public void setMatKhau(String matKhau) {
        this.matKhau = matKhau;
    }

    public int getSoDu() {
        return soDu;
    }

    public void setSoDu(int soDu) {
        this.soDu = soDu;
    }

    public Date getNgayTao() {
        return ngayTao;
    }

    public void setNgayTao(Date ngayTao) {
        this.ngayTao = ngayTao;
    }

    public String getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }

}
