package com.example.model;

public class MayTinh {
    private String soMay;
    private String trangThai;
    private java.util.Date ngayLapDat;
    private int maLoaiMay;
    private LoaiMay loaiMay;
    private java.util.Set<ChiTietSuDung> chiTietSuDungs;

    public MayTinh() {
        this.chiTietSuDungs = new java.util.HashSet<>();
    }

    // Getters and setters
    public String getSoMay() {
        return soMay;
    }

    public void setSoMay(String soMay) {
        this.soMay = soMay;
    }

    public String getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }

    public java.util.Date getNgayLapDat() {
        return ngayLapDat;
    }

    public void setNgayLapDat(java.util.Date ngayLapDat) {
        this.ngayLapDat = ngayLapDat;
    }

    public int getMaLoaiMay() {
        return maLoaiMay;
    }

    public void setMaLoaiMay(int maLoaiMay) {
        this.maLoaiMay = maLoaiMay;
    }

    public LoaiMay getLoaiMay() {
        return loaiMay;
    }

    public void setLoaiMay(LoaiMay loaiMay) {
        this.loaiMay = loaiMay;
    }

    public java.util.Set<ChiTietSuDung> getChiTietSuDungs() {
        return chiTietSuDungs;
    }

    public void setChiTietSuDungs(java.util.Set<ChiTietSuDung> chiTietSuDungs) {
        this.chiTietSuDungs = chiTietSuDungs;
    }
}