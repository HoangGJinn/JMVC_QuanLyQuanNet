package com.example.model;

import java.util.ArrayList;

public class DichVu {
    public String MaDV;
    public String TenDV;
    public ArrayList<ChiTietDV> ChiTietDVs;
    public DvDoAn dvDoAn;
    public DvDoUong dvDoUong;
    public DvTheCao dvTheCao;
    public DichVu() {
        this.ChiTietDVs = new ArrayList<ChiTietDV>();
    }
}
