package com.example.model;


 public class DvDoUong {
    public String maDV;
    public int DonGia;
    public boolean BestSeller;
    public String TenDoUong;
    public String TrangThai;
    public DichVu DICHVU;

    public DvDoUong(String maDV, int donGia, boolean bestSeller, String tenDoUong, String trangThai) {
        this.maDV = maDV;
        this.DonGia = donGia;
        this.BestSeller = bestSeller;
        this.TenDoUong = tenDoUong;
        this.TrangThai = trangThai;

    }

}
