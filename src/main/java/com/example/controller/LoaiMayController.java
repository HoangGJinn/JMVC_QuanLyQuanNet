package com.example.controller;

import com.example.dao.LoaiMayDAO;
import com.example.model.LoaiMay;

import java.util.ArrayList;
import java.util.List;

public class LoaiMayController {
    private final LoaiMayDAO loaiMayDAO;

    public LoaiMayController() {
        this.loaiMayDAO = LoaiMayDAO.getInstance();
    }

    /**
     * Lấy danh sách tất cả loại máy
     * @return Danh sách loại máy
     */
    public List<LoaiMay> layDsLoaiMay() {
        try {
            return loaiMayDAO.layDsLoaiMay();
        } catch (Exception e) {
            System.err.println("Lỗi khi lấy danh sách loại máy: " + e.getMessage());
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    /**
     * Thêm loại máy mới
     * @param tenLoaiMay Tên loại máy
     * @param soTienMotGio Số tiền một giờ
     * @return Mã loại máy mới được thêm, -1 nếu thất bại
     */
    public int themLoaiMay(String tenLoaiMay, int soTienMotGio) {
        try {
            if (tenLoaiMay == null || tenLoaiMay.trim().isEmpty()) {
                throw new IllegalArgumentException("Tên loại máy không được để trống");
            }
            if (soTienMotGio < 0) {
                throw new IllegalArgumentException("Số tiền một giờ không được âm");
            }
            return loaiMayDAO.themLoaiMay(tenLoaiMay, soTienMotGio);
        } catch (IllegalArgumentException e) {
            System.err.println("Lỗi tham số: " + e.getMessage());
            return -1;
        } catch (Exception e) {
            System.err.println("Lỗi khi thêm loại máy: " + e.getMessage());
            e.printStackTrace();
            return -1;
        }
    }

    /**
     * Sửa thông tin loại máy
     * @param maLoaiMay Mã loại máy cần sửa
     * @param tenLoaiMay Tên loại máy mới
     * @param soTienMotGio Số tiền một giờ mới
     * @return true nếu sửa thành công, false nếu thất bại
     */
    public boolean suaLoaiMay(int maLoaiMay, String tenLoaiMay, int soTienMotGio) {
        try {
            if (maLoaiMay <= 0) {
                throw new IllegalArgumentException("Mã loại máy không hợp lệ");
            }
            if (tenLoaiMay == null || tenLoaiMay.trim().isEmpty()) {
                throw new IllegalArgumentException("Tên loại máy không được để trống");
            }
            if (soTienMotGio < 0) {
                throw new IllegalArgumentException("Số tiền một giờ không được âm");
            }
            return loaiMayDAO.suaLoaiMay(maLoaiMay, tenLoaiMay, soTienMotGio);
        } catch (IllegalArgumentException e) {
            System.err.println("Lỗi tham số: " + e.getMessage());
            return false;
        } catch (Exception e) {
            System.err.println("Lỗi khi sửa loại máy: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Xóa loại máy
     * @param maLoaiMay Mã loại máy cần xóa
     * @return true nếu xóa thành công, false nếu thất bại
     */
    public boolean xoaLoaiMay(int maLoaiMay) {
        try {
            if (maLoaiMay <= 0) {
                throw new IllegalArgumentException("Mã loại máy không hợp lệ");
            }
            return loaiMayDAO.xoaLoaiMay(maLoaiMay);
        } catch (IllegalArgumentException e) {
            System.err.println("Lỗi tham số: " + e.getMessage());
            return false;
        } catch (Exception e) {
            System.err.println("Lỗi khi xóa loại máy: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Tìm kiếm loại máy theo tên
     * @param searchText Từ khóa tìm kiếm
     * @return Danh sách loại máy phù hợp với từ khóa
     */
    public List<LoaiMay> timKiemLoaiMay(String searchText) {
        try {
            if (searchText == null) {
                searchText = "";
            }
            return loaiMayDAO.timKiemLoaiMay(searchText);
        } catch (Exception e) {
            System.err.println("Lỗi khi tìm kiếm loại máy: " + e.getMessage());
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    /**
     * Thêm linh kiện cho loại máy
     * @param maLoaiMay Mã loại máy
     * @param tenLinhKien Tên linh kiện
     * @param chiTietLK Chi tiết linh kiện
     * @param soLuong Số lượng
     * @return true nếu thêm thành công, false nếu thất bại
     */
    public boolean themLinhKien(int maLoaiMay, String tenLinhKien, String chiTietLK, int soLuong) {
        try {
            if (maLoaiMay <= 0) {
                throw new IllegalArgumentException("Mã loại máy không hợp lệ");
            }
            if (tenLinhKien == null || tenLinhKien.trim().isEmpty()) {
                throw new IllegalArgumentException("Tên linh kiện không được để trống");
            }
            if (chiTietLK == null) {
                chiTietLK = "";
            }
            if (soLuong < 0) {
                throw new IllegalArgumentException("Số lượng không được âm");
            }
            return loaiMayDAO.themLinhKien(maLoaiMay, tenLinhKien, chiTietLK, soLuong);
        } catch (IllegalArgumentException e) {
            System.err.println("Lỗi tham số: " + e.getMessage());
            return false;
        } catch (Exception e) {
            System.err.println("Lỗi khi thêm linh kiện: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Sửa thông tin linh kiện
     * @param maLoaiMay Mã loại máy
     * @param tenLinhKien Tên linh kiện
     * @param chiTietLK Chi tiết linh kiện mới
     * @param soLuong Số lượng mới
     * @return true nếu sửa thành công, false nếu thất bại
     */
    public boolean suaLinhKien(int maLoaiMay, String tenLinhKien, String chiTietLK, int soLuong) {
        try {
            if (maLoaiMay <= 0) {
                throw new IllegalArgumentException("Mã loại máy không hợp lệ");
            }
            if (tenLinhKien == null || tenLinhKien.trim().isEmpty()) {
                throw new IllegalArgumentException("Tên linh kiện không được để trống");
            }
            if (chiTietLK == null) {
                chiTietLK = "";
            }
            if (soLuong < 0) {
                throw new IllegalArgumentException("Số lượng không được âm");
            }
            return loaiMayDAO.suaLinhKien(maLoaiMay, tenLinhKien, chiTietLK, soLuong);
        } catch (IllegalArgumentException e) {
            System.err.println("Lỗi tham số: " + e.getMessage());
            return false;
        } catch (Exception e) {
            System.err.println("Lỗi khi sửa linh kiện: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Xóa linh kiện
     * @param maLoaiMay Mã loại máy
     * @param tenLinhKien Tên linh kiện cần xóa
     * @return true nếu xóa thành công, false nếu thất bại
     */
    public boolean xoaLinhKien(int maLoaiMay, String tenLinhKien) {
        try {
            if (maLoaiMay <= 0) {
                throw new IllegalArgumentException("Mã loại máy không hợp lệ");
            }
            if (tenLinhKien == null || tenLinhKien.trim().isEmpty()) {
                throw new IllegalArgumentException("Tên linh kiện không được để trống");
            }
            return loaiMayDAO.xoaLinhKien(maLoaiMay, tenLinhKien);
        } catch (IllegalArgumentException e) {
            System.err.println("Lỗi tham số: " + e.getMessage());
            return false;
        } catch (Exception e) {
            System.err.println("Lỗi khi xóa linh kiện: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Tìm linh kiện theo loại máy
     * @param maLoaiMay Mã loại máy cần tìm linh kiện
     * @return Danh sách linh kiện của loại máy
     */
    public List<String[]> timLinhKienTheoLoaiMay(int maLoaiMay) {
        try {
            if (maLoaiMay <= 0) {
                throw new IllegalArgumentException("Mã loại máy không hợp lệ");
            }
            return loaiMayDAO.timLinhKienTheoLoaiMay(maLoaiMay);
        } catch (IllegalArgumentException e) {
            System.err.println("Lỗi tham số: " + e.getMessage());
            return new ArrayList<>();
        } catch (Exception e) {
            System.err.println("Lỗi khi tìm linh kiện theo loại máy: " + e.getMessage());
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
}