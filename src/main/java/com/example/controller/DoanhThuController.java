package com.example.controller;

import com.example.dao.DoanhThuDAO;
import com.example.model.MayTinh;

import java.util.ArrayList;
import java.util.List;

public class DoanhThuController {
    private final DoanhThuDAO doanhThuDAO;

    public DoanhThuController() {
        this.doanhThuDAO = DoanhThuDAO.getInstance();
    }

    /**
     * Lấy doanh thu 12 tháng gần nhất
     * @return Danh sách doanh thu 12 tháng gần nhất
     */
    public List<MayTinh> getDoanhThu12ThangGanNhat() {
        try {
            return doanhThuDAO.getDoanhThu12ThangGanNhat();
        } catch (Exception e) {
            System.err.println("Lỗi khi lấy doanh thu 12 tháng gần nhất: " + e.getMessage());
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    /**
     * Tính tổng doanh thu theo tháng và năm
     * @param thang Tháng cần tính doanh thu
     * @param nam Năm cần tính doanh thu
     * @return Tổng doanh thu
     */
    public int getTongDoanhThuThang(int thang, int nam) {
        try {
            if (thang < 1 || thang > 12) {
                throw new IllegalArgumentException("Tháng phải từ 1 đến 12");
            }
            if (nam < 2000 || nam > 2100) {
                throw new IllegalArgumentException("Năm không hợp lệ");
            }
            return doanhThuDAO.getTongDoanhThuThang(thang, nam);
        } catch (IllegalArgumentException e) {
            System.err.println("Lỗi tham số: " + e.getMessage());
            return 0;
        } catch (Exception e) {
            System.err.println("Lỗi khi tính tổng doanh thu tháng: " + e.getMessage());
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * Lấy doanh thu tháng hiện tại
     * @return Doanh thu tháng hiện tại
     */
    public int getDoanhThuThangNay() {
        try {
            return doanhThuDAO.getDoanhThuThangNay();
        } catch (Exception e) {
            System.err.println("Lỗi khi lấy doanh thu tháng này: " + e.getMessage());
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * Lấy doanh thu nhân viên theo tháng và năm
     * @param thang Tháng cần lấy doanh thu
     * @param nam Năm cần lấy doanh thu
     * @return Danh sách doanh thu nhân viên
     */
    public List<MayTinh> getDoanhThuNhanVienTheoThang(int thang, int nam) {
        try {
            if (thang < 1 || thang > 12) {
                throw new IllegalArgumentException("Tháng phải từ 1 đến 12");
            }
            if (nam < 2000 || nam > 2100) {
                throw new IllegalArgumentException("Năm không hợp lệ");
            }
            return doanhThuDAO.getDoanhThuNhanVienTheoThang(thang, nam);
        } catch (IllegalArgumentException e) {
            System.err.println("Lỗi tham số: " + e.getMessage());
            return new ArrayList<>();
        } catch (Exception e) {
            System.err.println("Lỗi khi lấy doanh thu nhân viên theo tháng: " + e.getMessage());
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    /**
     * Lấy tổng thời gian sử dụng và doanh thu khách hàng
     * @param thang Tháng cần lấy dữ liệu
     * @param nam Năm cần lấy dữ liệu
     * @return Danh sách thông tin khách hàng
     */
    public List<MayTinh> getTongTGSDVaDTKH(int thang, int nam) {
        try {
            if (thang < 1 || thang > 12) {
                throw new IllegalArgumentException("Tháng phải từ 1 đến 12");
            }
            if (nam < 2000 || nam > 2100) {
                throw new IllegalArgumentException("Năm không hợp lệ");
            }
            return doanhThuDAO.getTongTGSDVaDTKH(thang, nam);
        } catch (IllegalArgumentException e) {
            System.err.println("Lỗi tham số: " + e.getMessage());
            return new ArrayList<>();
        } catch (Exception e) {
            System.err.println("Lỗi khi lấy tổng thời gian sử dụng và doanh thu khách hàng: " + e.getMessage());
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    /**
     * Lấy nhân viên của tháng
     * @param thang Tháng cần lấy dữ liệu
     * @param nam Năm cần lấy dữ liệu
     * @return Danh sách nhân viên của tháng
     */
    public List<MayTinh> getNhanVienCuaThang(int thang, int nam) {
        try {
            if (thang < 1 || thang > 12) {
                throw new IllegalArgumentException("Tháng phải từ 1 đến 12");
            }
            if (nam < 2000 || nam > 2100) {
                throw new IllegalArgumentException("Năm không hợp lệ");
            }
            return doanhThuDAO.getNhanVienCuaThang(thang, nam);
        } catch (IllegalArgumentException e) {
            System.err.println("Lỗi tham số: " + e.getMessage());
            return new ArrayList<>();
        } catch (Exception e) {
            System.err.println("Lỗi khi lấy nhân viên của tháng: " + e.getMessage());
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    /**
     * Lấy nhân viên của năm
     * @param nam Năm cần lấy dữ liệu
     * @return Danh sách nhân viên của năm
     */
    public List<MayTinh> getNhanVienCuaNam(int nam) {
        try {
            if (nam < 2000 || nam > 2100) {
                throw new IllegalArgumentException("Năm không hợp lệ");
            }
            return doanhThuDAO.getNhanVienCuaNam(nam);
        } catch (IllegalArgumentException e) {
            System.err.println("Lỗi tham số: " + e.getMessage());
            return new ArrayList<>();
        } catch (Exception e) {
            System.err.println("Lỗi khi lấy nhân viên của năm: " + e.getMessage());
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    /**
     * Lấy khách hàng của tháng
     * @param thang Tháng cần lấy dữ liệu
     * @param nam Năm cần lấy dữ liệu
     * @return Danh sách khách hàng của tháng
     */
    public List<MayTinh> getKhachHangCuaThang(int thang, int nam) {
        try {
            if (thang < 1 || thang > 12) {
                throw new IllegalArgumentException("Tháng phải từ 1 đến 12");
            }
            if (nam < 2000 || nam > 2100) {
                throw new IllegalArgumentException("Năm không hợp lệ");
            }
            return doanhThuDAO.getKhachHangCuaThang(thang, nam);
        } catch (IllegalArgumentException e) {
            System.err.println("Lỗi tham số: " + e.getMessage());
            return new ArrayList<>();
        } catch (Exception e) {
            System.err.println("Lỗi khi lấy khách hàng của tháng: " + e.getMessage());
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    /**
     * Lấy khách hàng của năm
     * @param nam Năm cần lấy dữ liệu
     * @return Danh sách khách hàng của năm
     */
    public List<MayTinh> getKhachHangCuaNam(int nam) {
        try {
            if (nam < 2000 || nam > 2100) {
                throw new IllegalArgumentException("Năm không hợp lệ");
            }
            return doanhThuDAO.getKhachHangCuaNam(nam);
        } catch (IllegalArgumentException e) {
            System.err.println("Lỗi tham số: " + e.getMessage());
            return new ArrayList<>();
        } catch (Exception e) {
            System.err.println("Lỗi khi lấy khách hàng của năm: " + e.getMessage());
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
}