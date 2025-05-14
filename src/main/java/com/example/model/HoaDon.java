package com.example.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class HoaDon {
    private String maHD;
    private Integer tongThanhToan;
    private String phuongThucTT;
    private String trangThai;
    private Date ngayLap;
    private String loaiHoaDon;
    private Integer soTienNap;
    private Integer tienCongThem;
    private Integer tienDuocGiam;
    private String tenDangNhap;
    private Integer maNV;
    private String maKM;
    private Set<ChiTietDV> chiTietDVs;
    private KhuyenMai khuyenMai;
    private com.example.model.NhanVien nhanVien;
    private TaiKhoan taiKhoan;


    public HoaDon(String maHD, Integer tongThanhToan, String phuongThucTT, String trangThai, Date ngayLap, String loaiHoaDon, Integer soTienNap, Integer tienCongThem, Integer tienDuocGiam, String tenDangNhap, Integer maNV, String maKM) {
        this.maHD = maHD;
        this.tongThanhToan = tongThanhToan;
        this.phuongThucTT = phuongThucTT;
        this.trangThai = trangThai;
        this.ngayLap = ngayLap;
        this.loaiHoaDon = loaiHoaDon;
        this.soTienNap = soTienNap;
        this.tienCongThem = tienCongThem;
        this.tienDuocGiam = tienDuocGiam;
        this.tenDangNhap = tenDangNhap;
        this.maNV = maNV;
        this.maKM = maKM;
        this.chiTietDVs = new HashSet<>();

    }
    // Getters and setters for all fields

    public String getMaHD() {
        return maHD;
    }
    public void setMaHD(String maHD) {
        this.maHD = maHD;
    }
    public Integer getTongThanhToan() {
        return tongThanhToan;
    }
    public void setTongThanhToan(Integer tongThanhToan) {
        this.tongThanhToan = tongThanhToan;
    }
    public String getPhuongThucTT() {
        return phuongThucTT;
    }
    public void setPhuongThucTT(String phuongThucTT) {
        this.phuongThucTT = phuongThucTT;
    }
    public String getTrangThai() {
        return trangThai;
    }
    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }
    public Date getNgayLap() {
        return ngayLap;
    }
    public void setNgayLap(Date ngayLap) {
        this.ngayLap = ngayLap;
    }
    public String getLoaiHoaDon() {
        return loaiHoaDon;
    }
    public void setLoaiHoaDon(String loaiHoaDon) {
        this.loaiHoaDon = loaiHoaDon;
    }
    public Integer getSoTienNap() {
        return soTienNap;
    }
    public void setSoTienNap(Integer soTienNap) {
        this.soTienNap = soTienNap;
    }
    public Integer getTienCongThem() {
        return tienCongThem;
    }
    public void setTienCongThem(Integer tienCongThem) {
        this.tienCongThem = tienCongThem;
    }
    public Integer getTienDuocGiam() {
        return tienDuocGiam;
    }
    public void setTienDuocGiam(Integer tienDuocGiam) {
        this.tienDuocGiam = tienDuocGiam;
    }

    public Integer getMaNV() {
        return maNV;
    }
    public void setMaNV(Integer maNV) {
        this.maNV = maNV;
    }

    public String getMaKM() {
        return maKM;
    }
    public void setMaKM(String maKM) {
        this.maKM = maKM;
    }

    public String getTenDangNhap() {
        return tenDangNhap;
    }
    public void setTenDangNhap(String tenDangNhap) {
        this.tenDangNhap = tenDangNhap;
    }
}