package com.example.model;

import java.util.Date;

public class NhanVien {
    private String maNV;
    private String hoTen;
    private String sdt;
    private String diaChi;
    private String gioiTinh;
    private Date ngaySinh;

    // Constructor có tham số
    public NhanVien(String maNV, String hoTen, String sdt, String diaChi, String gioiTinh, Date ngaySinh) {
        this.maNV = maNV;
        this.hoTen = hoTen;
        this.sdt = sdt;
        this.diaChi = diaChi;
        this.gioiTinh = gioiTinh;
        this.ngaySinh = ngaySinh;
    }

    // ✅ Constructor mặc định
    public NhanVien() {
    }

    // Các getter/setter nên có để sử dụng với form
    public String getMaNV() { return maNV; }
    public void setMaNV(String maNV) { this.maNV = maNV; }

    public String getHoTen() { return hoTen; }
    public void setHoTen(String hoTen) { this.hoTen = hoTen; }

    public String getSdt() { return sdt; }
    public void setSdt(String sdt) { this.sdt = sdt; }

    public String getDiaChi() { return diaChi; }
    public void setDiaChi(String diaChi) { this.diaChi = diaChi; }

    public String getGioiTinh() { return gioiTinh; }
    public void setGioiTinh(String gioiTinh) { this.gioiTinh = gioiTinh; }

    public Date getNgaySinh() { return ngaySinh; }
    public void setNgaySinh(Date ngaySinh) { this.ngaySinh = ngaySinh; }
}

