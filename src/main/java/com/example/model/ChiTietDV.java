package com.example.model;

public class ChiTietDV
{
    /*
            public string MaHD { get; set; }
        public string MaDV { get; set; }
        public int DonGia { get; set; }
        public int SoLuong { get; set; }

        public virtual DICHVU DICHVU { get; set; }
        public virtual HOADON HOADON { get; set; }
     */
    public String MaHD;
    public String MaDV;
    public int DonGia;
    public int SoLuong;
    //public DichVu DichVu;
    public HoaDon HoaDon;

    public ChiTietDV(String maHD, String maDV, int donGia, int soLuong) {
        this.MaHD = maHD;
        this.MaDV = maDV;
        this.DonGia = donGia;
        this.SoLuong = soLuong;
    }
    public String getMaHD() {
        return MaHD;
    }
    public void setMaHD(String maHD) {
        this.MaHD = maHD;
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
    public int getSoLuong() {
        return SoLuong;
    }
    public void setSoLuong(int soLuong) {
        this.SoLuong = soLuong;
    }


}
