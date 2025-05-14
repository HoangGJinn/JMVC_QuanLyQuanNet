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
        this.DichVu = new DichVu(maDV, tenDoAn, bestSeller, donGia, trangThai);
        this.MaDV = maDV;
        this.DonGia = donGia;
        this.TenDoAn = tenDoAn;
        this.BestSeller = bestSeller;
        this.TrangThai = trangThai;
    }

    // Getter methods
    public String getMaDV() {
        return MaDV;
    }

    public int getDonGia() {
        return DonGia;
    }

    public String getTenDoAn() {
        return TenDoAn;
    }

    public Boolean getBestSeller() {
        return BestSeller;
    }

    public String getTrangThai() {
        return TrangThai;
    }
}
