package com.example.model;

public class LoaiMay {
    private int maLoaiMay;
    private String tenLoaiMay;
    private int soTienMotGio;

    public LoaiMay(int maLoaiMay, String tenLoaiMay, int soTienMotGio) {
        this.maLoaiMay = maLoaiMay;
        this.tenLoaiMay = tenLoaiMay;
        this.soTienMotGio = soTienMotGio;
    }

    public int getMaLoaiMay() {
        return maLoaiMay;
    }

    public void setMaLoaiMay(int maLoaiMay) {
        this.maLoaiMay = maLoaiMay;
    }

    public String getTenLoaiMay() {
        return tenLoaiMay;
    }

    public void setTenLoaiMay(String tenLoaiMay) {
        this.tenLoaiMay = tenLoaiMay;
    }

    public int getSoTienMotGio() {
        return soTienMotGio;
    }

    public void setSoTienMotGio(int soTienMotGio) {
        this.soTienMotGio = soTienMotGio;
    }
}
