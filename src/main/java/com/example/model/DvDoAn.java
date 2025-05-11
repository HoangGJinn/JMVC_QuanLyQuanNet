package com.example.model;

public class DvDoAn {
    /*
            public string MaDV { get; set; }
        public int DonGia { get; set; }
        public string TenDoAn { get; set; }
        public Nullable<bool> BestSeller { get; set; }
        public string TrangThai { get; set; }

        public virtual DICHVU DICHVU { get; set; }
     */
    public String MaDV;
    public int DonGia;
    public String TenDoAn;
    public Boolean BestSeller;
    public String TrangThai;
    public DichVu DichVu;

    public DvDoAn(String maDV, int donGia, String tenDoAn, Boolean bestSeller, String trangThai) {
        this.MaDV = maDV;
        this.DonGia = donGia;
        this.TenDoAn = tenDoAn;
        this.BestSeller = bestSeller;
        this.TrangThai = trangThai;

    }

    public String getMaDV() {
        return MaDV;
    }



    public void setMaDV(String maDV) {
        this.MaDV = maDV;
    }
    public int getDonGia() {
        return DonGia;
    }
    public void setDonGia(int donGia) {
        this.DonGia = donGia;
    }
    public String getTenDoAn() {
        return TenDoAn;
    }
    public void setTenDoAn(String tenDoAn) {
        this.TenDoAn = tenDoAn;
    }
    public Boolean getBestSeller() {
        return BestSeller;
    }
    public void setBestSeller(Boolean bestSeller) {
        this.BestSeller = bestSeller;
    }
    public String getTrangThai() {
        return TrangThai;
    }
    public void setTrangThai(String trangThai) {
        this.TrangThai = trangThai;
    }



}
