package com.example.model;

public class DvTheCao {
    public String maDV;
    public int DonGia;
    public String LoaiThe;
    public DichVu DichVu;

    public DvTheCao(String maDV, int donGia, String loaiThe) {
        this.DichVu = new DichVu(maDV, loaiThe, null, donGia, null);
        this.maDV = maDV;
        this.DonGia = donGia;
        this.LoaiThe = loaiThe;
    }

    // Getter methods
    public String getMaDV() {
        return maDV;
    }

    public int getDonGia() {
        return DonGia;
    }

    public String getLoaiThe() {
        return LoaiThe;
    }

    public int getMenhGia() {
        return DonGia;
    }
}
