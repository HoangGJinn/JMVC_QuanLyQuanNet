package com.example.model;

import java.util.Date;

public class KhuyenMai {
    /*
            public string MaKM { get; set; }
        public string TenChTrinh { get; set; }
        public int TyLeKM { get; set; }
        public int SoTienToiThieuApDung { get; set; }
        public int KMToiDa { get; set; }
        public System.DateTime ThoiGianBatDau { get; set; }
        public System.DateTime ThoiGianKetThuc { get; set; }
        public int MaLoaiKM { get; set; }

        [System.Diagnostics.CodeAnalysis.SuppressMessage("Microsoft.Usage", "CA2227:CollectionPropertiesShouldBeReadOnly")]
        public virtual ICollection<HOADON> HOADONs { get; set; }
        public virtual LOAIKM LOAIKM { get; set; }
     */
    private String maKM;
    private String tenChuongTrinh;
    private int tyLe;
    private int toiThieu;
    private int kmToiDa;
    private Date batDau;
    private Date ketThuc;
    private String loaiKM;
    //private java.util.List<HoaDon> hOADONs;
    //private LoaiKm lOAIKM;

    public KhuyenMai() {
    }
    
    public KhuyenMai(String maKM, String tenChuongTrinh, int tyLe, int toiThieu, int kmToiDa, Date batDau, Date ketThuc, String loaiKM) {
        this.maKM = maKM;
        this.tenChuongTrinh = tenChuongTrinh;
        this.tyLe = tyLe;
        this.toiThieu = toiThieu;
        this.kmToiDa = kmToiDa;
        this.batDau = batDau;
        this.ketThuc = ketThuc;
        this.loaiKM = loaiKM;
    }
    
    public KhuyenMai(String maKM, String tenChuongTrinh, int tyLe, int toiThieu, int kmToiDa, Date batDau, Date ketThuc, String loaiKM, String tenLoai) {
        this.maKM = maKM;
        this.tenChuongTrinh = tenChuongTrinh;
        this.tyLe = tyLe;
        this.toiThieu = toiThieu;
        this.kmToiDa = kmToiDa;
        this.batDau = batDau;
        this.ketThuc = ketThuc;
        this.loaiKM = loaiKM;
        //this.tenLoai = tenLoai;
    }

    public String getMaKM() {
        return maKM;
    }

    public void setMaKM(String maKM) {
        this.maKM = maKM;
    }

    public String getTenChuongTrinh() {
        return tenChuongTrinh;
    }

    public void setTenChuongTrinh(String tenChuongTrinh) {
        this.tenChuongTrinh = tenChuongTrinh;
    }

    public int getTyLe() {
        return tyLe;
    }

    public void setTyLe(int tyLe) {
        this.tyLe = tyLe;
    }

    public int getToiThieu() {
        return toiThieu;
    }

    public void setToiThieu(int toiThieu) {
        this.toiThieu = toiThieu;
    }

    public int getKmToiDa() {
        return kmToiDa;
    }

    public void setKmToiDa(int kmToiDa) {
        this.kmToiDa = kmToiDa;
    }

    public Date getBatDau() {
        return batDau;
    }

    public void setBatDau(Date batDau) {
        this.batDau = batDau;
    }

    public Date getKetThuc() {
        return ketThuc;
    }

    public void setKetThuc(Date ketThuc) {
        this.ketThuc = ketThuc;
    }

    public String getLoaiKM() {
        return loaiKM;
    }

    public void setLoaiKM(String loaiKM) {
        this.loaiKM = loaiKM;
    }

    @Override
    public String toString() {
        return tenChuongTrinh;
    }
}
