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
        this.TenDoAn = tenDoAn;
        this.DonGia = donGia;
        this.BestSeller = bestSeller;
        this.TrangThai = trangThai;

    }





}
