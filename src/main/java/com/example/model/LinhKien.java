package com.example.model;

public class LinhKien {
    public int maLoaiMay;
    public String tenLinhKien;
    public String chiTietLK;
    public int soLuong;
    public LoaiMay loaiMay;

    public LinhKien(int maLoaiMay, String tenLinhKien, String chiTietLK, int soLuong) {
        this.maLoaiMay = maLoaiMay;
        this.tenLinhKien = tenLinhKien;
        this.chiTietLK = chiTietLK;
        this.soLuong = soLuong;
    }

    public int getMaLoaiMay() {
        return maLoaiMay;
    }

    public void setMaLoaiMay(int maLoaiMay) {
        this.maLoaiMay = maLoaiMay;
    }

    public String getTenLinhKien() {
        return tenLinhKien;
    }

    public void setTenLinhKien(String tenLinhKien) {
        this.tenLinhKien = tenLinhKien;
    }

    public String getChiTietLK() {
        return chiTietLK;
    }

    public void setChiTietLK(String chiTietLK) {
        this.chiTietLK = chiTietLK;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public LoaiMay getLoaiMay() {
        return loaiMay;
    }

    public void setLoaiMay(LoaiMay loaiMay) {
        this.loaiMay = loaiMay;
    }

}
