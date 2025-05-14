package com.example.model;


 public class DvDoUong {
    public String maDV;
    public int DonGia;
    public boolean BestSeller;
    public String TenDoUong;
    public String TrangThai;
    public DichVu DICHVU;

    public DvDoUong(String maDV, int donGia, boolean bestSeller, String tenDoUong, String trangThai) {
        this.DICHVU = new DichVu(maDV, tenDoUong, bestSeller, donGia, trangThai);
        this.maDV = maDV;
        this.DonGia = donGia;
        this.BestSeller = bestSeller;
        this.TenDoUong = tenDoUong;
        this.TrangThai = trangThai;
    }

    // Getter methods
    public String getMaDV() {
        return maDV;
    }

    public int getDonGia() {
        return DonGia;
    }

    public boolean isBestSeller() {
        return BestSeller;
    }

    public String getTenDoUong() {
        return TenDoUong;
    }

    public String getTrangThai() {
        return TrangThai;
    }
    public DichVu getDICHVU() {
        return DICHVU;
    }
    public boolean getBestSeller() {
        return BestSeller;
    }
}
