package com.example.controller;

import com.example.dao.MayTinhDAO;
import com.example.model.MayTinh;

import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MayTinhController {
    private final MayTinhDAO mayTinhDAO;

    public MayTinhController() {
        this.mayTinhDAO = MayTinhDAO.getInstance();
    }

    /**
     * Lấy danh sách máy tính theo mã loại
     * @param maLoaiMay Mã loại máy cần lấy
     * @return Danh sách máy tính thuộc loại đã chọn
     */
    public List<MayTinh> layDsMayTheoMaLoai(int maLoaiMay) {
        try {
            if (maLoaiMay <= 0) {
                throw new IllegalArgumentException("Mã loại máy không hợp lệ");
            }
            return mayTinhDAO.layDsMayTheoMaLoai(maLoaiMay);
        } catch (IllegalArgumentException e) {
            System.err.println("Lỗi tham số: " + e.getMessage());
            return new ArrayList<>();
        } catch (SQLException e) {
            System.err.println("Lỗi SQL khi lấy danh sách máy theo loại: " + e.getMessage());
            e.printStackTrace();
            return new ArrayList<>();
        } catch (Exception e) {
            System.err.println("Lỗi không xác định khi lấy danh sách máy theo loại: " + e.getMessage());
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    /**
     * Lấy toàn bộ danh sách máy tính
     * @return Danh sách tất cả máy tính
     */
    public List<MayTinh> layDsMayTinh() {
        try {
            return mayTinhDAO.layDsMayTinh();
        } catch (SQLException e) {
            System.err.println("Lỗi SQL khi lấy danh sách máy tính: " + e.getMessage());
            e.printStackTrace();
            return new ArrayList<>();
        } catch (Exception e) {
            System.err.println("Lỗi không xác định khi lấy danh sách máy tính: " + e.getMessage());
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    /**
     * Thêm máy tính mới
     * @param ngayLapDat Ngày lắp đặt máy tính
     * @param maLoaiMay Mã loại máy tính
     * @return true nếu thêm thành công, false nếu thất bại
     */
    public boolean themMayTinh(Date ngayLapDat, int maLoaiMay) {
        try {
            if (ngayLapDat == null) {
                throw new IllegalArgumentException("Ngày lắp đặt không được để trống");
            }
            if (maLoaiMay <= 0) {
                throw new IllegalArgumentException("Mã loại máy không hợp lệ");
            }
            return mayTinhDAO.themMayTinh(ngayLapDat, maLoaiMay);
        } catch (IllegalArgumentException e) {
            System.err.println("Lỗi tham số: " + e.getMessage());
            return false;
        } catch (SQLException e) {
            System.err.println("Lỗi SQL khi thêm máy tính: " + e.getMessage());
            e.printStackTrace();
            return false;
        } catch (Exception e) {
            System.err.println("Lỗi không xác định khi thêm máy tính: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Sửa thông tin máy tính
     * @param soMay Số máy cần sửa
     * @param trangThai Trạng thái mới của máy
     * @param maLoaiMay Mã loại máy mới
     * @return true nếu sửa thành công, false nếu thất bại
     */
    public boolean suaMayTinh(String soMay, String trangThai, int maLoaiMay) {
        try {
            if (soMay == null || soMay.trim().isEmpty()) {
                throw new IllegalArgumentException("Số máy không được để trống");
            }
            if (trangThai == null || trangThai.trim().isEmpty()) {
                throw new IllegalArgumentException("Trạng thái không được để trống");
            }
            if (maLoaiMay <= 0) {
                throw new IllegalArgumentException("Mã loại máy không hợp lệ");
            }
            return mayTinhDAO.suaMayTinh(soMay, trangThai, maLoaiMay);
        } catch (IllegalArgumentException e) {
            System.err.println("Lỗi tham số: " + e.getMessage());
            return false;
        } catch (SQLException e) {
            System.err.println("Lỗi SQL khi sửa máy tính: " + e.getMessage());
            e.printStackTrace();
            return false;
        } catch (Exception e) {
            System.err.println("Lỗi không xác định khi sửa máy tính: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Tìm kiếm máy tính theo số máy
     * @param searchText Từ khóa tìm kiếm
     * @return Danh sách máy tính phù hợp với từ khóa
     */
    public List<MayTinh> timKiemMayTinh(String searchText) {
        try {
            if (searchText == null) {
                searchText = "";
            }
            return mayTinhDAO.timKiemMayTinh(searchText);
        } catch (SQLException e) {
            System.err.println("Lỗi SQL khi tìm kiếm máy tính: " + e.getMessage());
            e.printStackTrace();
            return new ArrayList<>();
        } catch (Exception e) {
            System.err.println("Lỗi không xác định khi tìm kiếm máy tính: " + e.getMessage());
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    /**
     * Tính tổng số máy đang hoạt động
     * @return Số lượng máy đang hoạt động
     */
    public int tinhTongMayHD() {
        try {
            return mayTinhDAO.tinhTongMayHD();
        } catch (SQLException e) {
            System.err.println("Lỗi SQL khi tính tổng máy hoạt động: " + e.getMessage());
            e.printStackTrace();
            return 0;
        } catch (Exception e) {
            System.err.println("Lỗi không xác định khi tính tổng máy hoạt động: " + e.getMessage());
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * Lấy giá tiền theo giờ của máy
     * @param soMay Số máy cần lấy giá
     * @return Giá tiền theo giờ
     */
    public int getGiaTienTheoGio(String soMay) {
        try {
            if (soMay == null || soMay.trim().isEmpty()) {
                throw new IllegalArgumentException("Số máy không được để trống");
            }
            return mayTinhDAO.getGiaTienTheoGio(soMay);
        } catch (IllegalArgumentException e) {
            System.err.println("Lỗi tham số: " + e.getMessage());
            return 0;
        } catch (SQLException e) {
            System.err.println("Lỗi SQL khi lấy giá tiền theo giờ: " + e.getMessage());
            e.printStackTrace();
            return 0;
        } catch (Exception e) {
            System.err.println("Lỗi không xác định khi lấy giá tiền theo giờ: " + e.getMessage());
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * Lấy tên tài khoản đang sử dụng máy
     * @param soMay Số máy cần kiểm tra
     * @return Tên tài khoản đang sử dụng máy, null nếu không có
     */
    public String layTKDangSuDungMay(String soMay) {
        try {
            if (soMay == null || soMay.trim().isEmpty()) {
                throw new IllegalArgumentException("Số máy không được để trống");
            }
            return mayTinhDAO.layTKDangSuDungMay(soMay);
        } catch (IllegalArgumentException e) {
            System.err.println("Lỗi tham số: " + e.getMessage());
            return null;
        } catch (SQLException e) {
            System.err.println("Lỗi SQL khi lấy tài khoản đang sử dụng máy: " + e.getMessage());
            e.printStackTrace();
            return null;
        } catch (Exception e) {
            System.err.println("Lỗi không xác định khi lấy tài khoản đang sử dụng máy: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }
}