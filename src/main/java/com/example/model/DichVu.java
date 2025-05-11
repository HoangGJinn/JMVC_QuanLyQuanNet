package com.example.model;

import java.util.Date;


public class DichVu {
        private String maDV;
        private String tenDV;
        private Boolean bestSeller;
        private int DonGia;
        private String TrangThai;

        public DichVu(String maDV, String tenDV, Boolean bestSeller, int donGia, String trangThai) {
            this.maDV = maDV;
            this.tenDV = tenDV;
            this.bestSeller = bestSeller;
            this.DonGia = donGia;
            this.TrangThai = trangThai;
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
        public Boolean getBestSeller() {
            return bestSeller;
        }
        public boolean setBestSeller(Boolean bestSeller) {
            this.bestSeller = bestSeller;
        }
        public long getDonGia() {
            return DonGia;
        }
        public void setDonGia(int donGia) {
            this.DonGia = donGia;
        }
        public String getTrangThai() {
            return TrangThai;
        }
        public void setTrangThai(String trangThai) {
            this.TrangThai = trangThai;
        }

}