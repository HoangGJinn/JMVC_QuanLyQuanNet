package com.example.model;

public class DvTheCao {
    public String maDV;
    public int DonGia;
    public String LoaiThe;
    public DichVu DichVu;

    public DvTheCao(String maDV, int donGia, String loaiThe) {
        this.maDV = maDV;
        this.DonGia = donGia;
        this.LoaiThe = loaiThe;

    }
    public String getMaDV() {
        return maDV;
    }
    public void setMaDV(String maDV) {
        this.maDV = maDV;
    }
    public int getDonGia() {
        return DonGia;
    }

    public void setDonGia(int donGia) {
        this.DonGia = donGia;
    }
    public String getLoaiThe() {
        return LoaiThe;
    }
    public void setLoaiThe(String loaiThe) {
        this.LoaiThe = loaiThe;
    }


}
