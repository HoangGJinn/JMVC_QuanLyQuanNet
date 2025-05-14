package com.example.model;

public class ChiTietDV {
    private String maDV;
    private String tenDV;
    private Integer donGia;
    private Integer soLuong;
    private Integer thanhTien;
    
    public ChiTietDV() {
    }
    
    public ChiTietDV(String maDV, String tenDV, Integer donGia, Integer soLuong, Integer thanhTien) {
        this.maDV = maDV;
        this.tenDV = tenDV;
        this.donGia = donGia;
        this.soLuong = soLuong;
        this.thanhTien = thanhTien;
    }
    
    public String getMaDV() {
        return maDV;
    }
    
    public void setMaDV(String maDV) {
        this.maDV = maDV;
    }
    
    public String getTenDV() {
        return tenDV;
    }
    
    public void setTenDV(String tenDV) {
        this.tenDV = tenDV;
    }
    
    public Integer getDonGia() {
        return donGia;
    }
    
    public void setDonGia(Integer donGia) {
        this.donGia = donGia;
    }
    
    public Integer getSoLuong() {
        return soLuong;
    }
    
    public void setSoLuong(Integer soLuong) {
        this.soLuong = soLuong;
    }
    
    public Integer getThanhTien() {
        return thanhTien;
    }
    
    public void setThanhTien(Integer thanhTien) {
        this.thanhTien = thanhTien;
    }
}
