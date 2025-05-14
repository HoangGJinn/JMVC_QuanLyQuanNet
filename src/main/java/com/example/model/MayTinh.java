package com.example.model;

import java.util.Date;

public class MayTinh {
    private String soMay;
    private String trangThai;
    private Date ngayLapDat;
    private int maLoaiMay;
    private double thoiGianSD;

    public MayTinh(String soMay, String trangThai, Date ngayLapDat, int maLoaiMay) {
        this.soMay = soMay;
        this.trangThai = trangThai;
        this.ngayLapDat = ngayLapDat;
        this.maLoaiMay = maLoaiMay;
        this.thoiGianSD = 0;
    }
    
    public MayTinh(String soMay, String trangThai, Date ngayLapDat, int maLoaiMay, double thoiGianSD) {
        this.soMay = soMay;
        this.trangThai = trangThai;
        this.ngayLapDat = ngayLapDat;
        this.maLoaiMay = maLoaiMay;
        this.thoiGianSD = thoiGianSD;
    }

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

    public Date getNgayLapDat() {
        return ngayLapDat;
    }

    public void setNgayLapDat(Date ngayLapDat) {
        this.ngayLapDat = ngayLapDat;
    }

    public int getMaLoaiMay() {
        return maLoaiMay;
    }

    public void setMaLoaiMay(int maLoaiMay) {
        this.maLoaiMay = maLoaiMay;
    }
    
    public double getThoiGianSD() {
        return thoiGianSD;
    }
    
    public void setThoiGianSD(double thoiGianSD) {
        this.thoiGianSD = thoiGianSD;
    }
}
