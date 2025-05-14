package com.example.controller;

import com.example.dao.DichVuDAO;
import com.example.dao.DichVuDAO.TopItem;
import com.example.model.DvDoAn;
import com.example.model.DvDoUong;
import com.example.model.DvTheCao;

import java.util.ArrayList;
import java.util.List;

public class DichVuController {
    private final DichVuDAO dichVuDAO;

    public DichVuController() {
        this.dichVuDAO = DichVuDAO.getInstance();
    }

    // General service methods
    public void xoaDichVu(String maDV) {
        try {
            dichVuDAO.xoaDichVu(maDV);
        } catch (Exception e) {
            throw new RuntimeException("Lỗi khi xóa dịch vụ: " + e.getMessage(), e);
        }
    }

    // Thẻ Cào (Phone Card) methods
    public List<DvTheCao> layDanhSachTheCao() {
        try {
            return dichVuDAO.layDSDVTC();
        } catch (Exception e) {
            throw new RuntimeException("Lỗi khi lấy danh sách thẻ cào: " + e.getMessage(), e);
        }
    }

    public void themTheCao(String loaiThe, int menhGia) {
        try {
            if (loaiThe == null || loaiThe.trim().isEmpty()) {
                throw new IllegalArgumentException("Loại thẻ không được để trống");
            }
            if (menhGia <= 0) {
                throw new IllegalArgumentException("Mệnh giá phải lớn hơn 0");
            }
            dichVuDAO.themDVTheCao(loaiThe, menhGia);
        } catch (IllegalArgumentException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Lỗi khi thêm thẻ cào: " + e.getMessage(), e);
        }
    }

    public void suaTheCao(String maDV, String loaiThe, int menhGia) {
        try {
            if (maDV == null || maDV.trim().isEmpty()) {
                throw new IllegalArgumentException("Mã thẻ cào không được để trống");
            }
            if (loaiThe == null || loaiThe.trim().isEmpty()) {
                throw new IllegalArgumentException("Loại thẻ không được để trống");
            }
            if (menhGia <= 0) {
                throw new IllegalArgumentException("Mệnh giá phải lớn hơn 0");
            }
            dichVuDAO.suaDVTheCao(maDV, loaiThe, menhGia);
        } catch (IllegalArgumentException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Lỗi khi sửa thẻ cào: " + e.getMessage(), e);
        }
    }

    public List<DvTheCao> timKiemTheCao(String tuKhoa) {
        try {
            return dichVuDAO.timKiemDVTC(tuKhoa);
        } catch (Exception e) {
            throw new RuntimeException("Lỗi khi tìm kiếm thẻ cào: " + e.getMessage(), e);
        }
    }

    // Đồ Ăn (Food) methods
    public List<DvDoAn> layDanhSachDoAn() {
        try {
            return dichVuDAO.layDSDVDA();
        } catch (Exception e) {
            throw new RuntimeException("Lỗi khi lấy danh sách đồ ăn: " + e.getMessage(), e);
        }
    }

    public void themDoAn(String tenDoAn, int donGia, boolean bestSeller) {
        try {
            if (tenDoAn == null || tenDoAn.trim().isEmpty()) {
                throw new IllegalArgumentException("Tên đồ ăn không được để trống");
            }
            if (donGia <= 0) {
                throw new IllegalArgumentException("Đơn giá phải lớn hơn 0");
            }
            dichVuDAO.themDVDoAn(tenDoAn, donGia, bestSeller);
        } catch (IllegalArgumentException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Lỗi khi thêm đồ ăn: " + e.getMessage(), e);
        }
    }

    public void suaDoAn(String maDV, String tenDoAn, int donGia, boolean bestSeller, String trangThai) {
        try {
            // Input Validation
            if (maDV == null || maDV.trim().isEmpty()) {
                throw new IllegalArgumentException("Mã đồ ăn không được để trống");
            }
            if (tenDoAn == null || tenDoAn.trim().isEmpty()) {
                throw new IllegalArgumentException("Tên đồ ăn không được để trống");
            }
            if (donGia <= 0) {
                throw new IllegalArgumentException("Đơn giá phải lớn hơn 0");
            }
            if (trangThai == null || trangThai.trim().isEmpty()) {
                throw new IllegalArgumentException("Trạng thái không được để trống");
            }

            // Log parameters before calling DAO
            System.out.println("[Controller] Attempting to update food:");
            System.out.println("  - MaDV: " + maDV);
            System.out.println("  - TenDoAn: " + tenDoAn);
            System.out.println("  - DonGia: " + donGia);
            System.out.println("  - BestSeller: " + bestSeller);
            System.out.println("  - TrangThai: " + trangThai);

            // Call DAO method
            dichVuDAO.suaDVDoAn(maDV, tenDoAn, donGia, bestSeller, trangThai);

            // Log success after DAO call
            System.out.println("[Controller] DAO update method called successfully for MaDV: " + maDV);

        } catch (IllegalArgumentException e) {
            System.err.println("[Controller] Validation error: " + e.getMessage());
            throw e; // Re-throw validation errors
        } catch (Exception e) {
            System.err.println("[Controller] Error during food update for MaDV " + maDV + ": " + e.getMessage());
            e.printStackTrace(); // Print stack trace for detailed debugging
            // Wrap and re-throw general exceptions
            throw new RuntimeException("Lỗi khi sửa đồ ăn: " + e.getMessage(), e);
        }
    }

    public List<DvDoAn> timKiemDoAn(String tuKhoa) {
        try {
            return dichVuDAO.timKiemDVDA(tuKhoa);
        } catch (Exception e) {
            throw new RuntimeException("Lỗi khi tìm kiếm đồ ăn: " + e.getMessage(), e);
        }
    }

    public List<TopItem> layTop5DoAnBanChay() {
        try {
            return dichVuDAO.layTop5DA();
        } catch (Exception e) {
            throw new RuntimeException("Lỗi khi lấy top 5 đồ ăn bán chạy: " + e.getMessage(), e);
        }
    }

    public List<TopItem> layTop5DoAnBanIt() {
        try {
            return dichVuDAO.layTop5DAIt();
        } catch (Exception e) {
            throw new RuntimeException("Lỗi khi lấy top 5 đồ ăn bán ít: " + e.getMessage(), e);
        }
    }

    // Đồ Uống (Beverage) methods
    public List<DvDoUong> layDanhSachDoUong() {
        try {
            return dichVuDAO.layDSDVDU();
        } catch (Exception e) {
            throw new RuntimeException("Lỗi khi lấy danh sách đồ uống: " + e.getMessage(), e);
        }
    }

    public void themDoUong(String tenDoUong, int donGia, boolean bestSeller) {
        try {
            if (tenDoUong == null || tenDoUong.trim().isEmpty()) {
                throw new IllegalArgumentException("Tên đồ uống không được để trống");
            }
            if (donGia <= 0) {
                throw new IllegalArgumentException("Đơn giá phải lớn hơn 0");
            }
            dichVuDAO.themDVDoUong(tenDoUong, donGia, bestSeller);
        } catch (IllegalArgumentException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Lỗi khi thêm đồ uống: " + e.getMessage(), e);
        }
    }

    public void suaDoUong(String maDV, String tenDoUong, int donGia, boolean bestSeller, String trangThai) {
        try {
            if (maDV == null || maDV.trim().isEmpty()) {
                throw new IllegalArgumentException("Mã đồ uống không được để trống");
            }
            if (tenDoUong == null || tenDoUong.trim().isEmpty()) {
                throw new IllegalArgumentException("Tên đồ uống không được để trống");
            }
            if (donGia <= 0) {
                throw new IllegalArgumentException("Đơn giá phải lớn hơn 0");
            }
            if (trangThai == null || trangThai.trim().isEmpty()) {
                throw new IllegalArgumentException("Trạng thái không được để trống");
            }
            dichVuDAO.suaDVDoUong(maDV, tenDoUong, donGia, bestSeller, trangThai);
        } catch (IllegalArgumentException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Lỗi khi sửa đồ uống: " + e.getMessage(), e);
        }
    }

    public List<DvDoUong> timKiemDoUong(String tuKhoa) {
        try {
            return dichVuDAO.timKiemDVDU(tuKhoa);
        } catch (Exception e) {
            throw new RuntimeException("Lỗi khi tìm kiếm đồ uống: " + e.getMessage(), e);
        }
    }

    public List<TopItem> layTop5DoUongBanChay() {
        try {
            return dichVuDAO.layTop5DU();
        } catch (Exception e) {
            throw new RuntimeException("Lỗi khi lấy top 5 đồ uống bán chạy: " + e.getMessage(), e);
        }
    }

    public List<TopItem> layTop5DoUongBanIt() {
        try {
            return dichVuDAO.layTop5DUIt();
        } catch (Exception e) {
            throw new RuntimeException("Lỗi khi lấy top 5 đồ uống bán ít: " + e.getMessage(), e);
        }
    }

    // Computer usage methods
    public void batDauSuDungMay(String soMay, String tenDangNhap) {
        try {
            dichVuDAO.batDauThue(soMay, tenDangNhap);
        } catch (Exception e) {
            throw new RuntimeException("Lỗi khi bắt đầu sử dụng máy: " + e.getMessage(), e);
        }
    }

    public void ketThucSuDungMay(String soMay, String tenDangNhap) {
        try {
            dichVuDAO.ketThucThue(soMay, tenDangNhap);
        } catch (Exception e) {
            throw new RuntimeException("Lỗi khi kết thúc sử dụng máy: " + e.getMessage(), e);
        }
    }
}