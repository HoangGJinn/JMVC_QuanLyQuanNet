package com.example.controller;

import com.example.dao.*;
import com.example.model.*;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

public class DichVuController {
    private final DichVuDAO dichVuDAO;

    public DichVuController() {
        this.dichVuDAO = DichVuDAO.getInstance();
    }

    // lấy danh sách dịch vụ đồ ăn
    public List<DichVu> layDanhSachDVDoAn() {
        return dichVuDAO.LayDSDVDA();
    }
    
    
}
