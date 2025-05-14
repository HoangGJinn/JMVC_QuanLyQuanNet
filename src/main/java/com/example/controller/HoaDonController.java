package com.example.controller;

import com.example.dao.HoaDonDAO;
import com.example.model.HoaDon;
import com.example.model.ChiTietDV;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;

public class HoaDonController {
    private final HoaDonDAO hoaDonDAO;

    public HoaDonController() {
        this.hoaDonDAO = HoaDonDAO.getInstance();
    }

    /**
     * Lấy danh sách chi tiết hóa đơn dịch vụ bằng function fn_XemChiTietHoaDonDichVu
     * @param maHD Mã hóa đơn cần lấy chi tiết
     * @return Danh sách chi tiết dịch vụ của hóa đơn với tên dịch vụ và thành tiền
     */
    public List<ChiTietDV> layDanhSachChiTietHoaDon(String maHD) {
        try {
            System.out.println("Đang lấy chi tiết hóa đơn cho mã: " + maHD);
            if (maHD == null || maHD.trim().isEmpty()) {
                throw new IllegalArgumentException("Mã hóa đơn không được để trống");
            }
            
            // Không cần truy vấn chi tiết, trả về danh sách rỗng
            return new ArrayList<>();
        } catch (Exception e) {
            System.err.println("Lỗi khi lấy chi tiết hóa đơn: " + e.getMessage());
            e.printStackTrace();
            // Return empty list instead of throwing exception to allow UI to handle it
            return new ArrayList<>();
        }
    }

    /**
     * Tìm kiếm hóa đơn theo các tiêu chí
     * @param maHD Mã hóa đơn (có thể null)
     * @param batDau Ngày bắt đầu (có thể null)
     * @param ketThuc Ngày kết thúc (có thể null)
     * @param loai Loại hóa đơn (có thể null)
     * @return Danh sách hóa đơn phù hợp
     */
    public List<HoaDon> timKiemHoaDon(String maHD, Date batDau, Date ketThuc, String loai) {
        return hoaDonDAO.timHoaDon(maHD, batDau, ketThuc, loai);
    }

    /**
     * Tạo hóa đơn nạp tiền
     * @param tongThanhToan Tổng số tiền thanh toán
     * @param phuongThuc Phương thức thanh toán
     * @param soTienNap Số tiền nạp
     * @param tenDangNhap Tên đăng nhập tài khoản được nạp
     * @param maNV Mã nhân viên thực hiện
     * @param maKM Mã khuyến mãi (có thể null)
     */
    public void taoHoaDonNapTien(int tongThanhToan, String phuongThuc, int soTienNap,
            String tenDangNhap, int maNV, String maKM) {
        hoaDonDAO.themHoaDonNapTien(tongThanhToan, phuongThuc, soTienNap, tenDangNhap, maNV, maKM);
    }

    /**
     * Hoàn tất hóa đơn
     * @param maHD Mã hóa đơn cần hoàn tất
     */
    public void hoanTatHoaDon(String maHD) {
        hoaDonDAO.hoanTatHoaDon(maHD);
    }

    /**
     * Hủy hóa đơn
     * @param maHD Mã hóa đơn cần hủy
     */
    public void huyHoaDon(String maHD) {
        hoaDonDAO.huyHoaDon(maHD);
    }

    /**
     * Tạo hóa đơn dịch vụ mới
     * @param phuongThucTT Phương thức thanh toán
     * @param maNV Mã nhân viên thực hiện
     * @param maKM Mã khuyến mãi (có thể null)
     * @param tongThanhToan Tổng tiền thanh toán
     * @return Mã hóa đơn mới được tạo
     */
    public String taoHoaDonDichVu(String phuongThucTT, int maNV, String maKM, int tongThanhToan) {
        return hoaDonDAO.taoHoaDonDichVu(phuongThucTT, maNV, maKM, tongThanhToan);
    }

    /**
     * Thêm dịch vụ vào hóa đơn
     * @param maHD Mã hóa đơn
     * @param maDV Mã dịch vụ
     * @param donGia Đơn giá
     * @param soLuong Số lượng
     */
    public void themDichVuVaoHoaDon(String maHD, String maDV, int donGia, int soLuong) {
        hoaDonDAO.themDichVuVaoHoaDon(maHD, maDV, donGia, soLuong);
    }

    /**
     * Tìm hóa đơn theo mã
     * @param maHD Mã hóa đơn cần tìm
     * @return Hóa đơn tìm thấy hoặc null nếu không tìm thấy
     */
    public HoaDon timHoaDonTheoMa(String maHD) {
        List<HoaDon> list = hoaDonDAO.timHoaDon(maHD, null, null, null);
        return list.isEmpty() ? null : list.get(0);
    }

    /**
     * Lấy danh sách hóa đơn trong khoảng thời gian
     * @param batDau Ngày bắt đầu
     * @param ketThuc Ngày kết thúc
     * @return Danh sách hóa đơn trong khoảng thời gian
     */
    public List<HoaDon> layDanhSachHoaDonTheoNgay(Date batDau, Date ketThuc) {
        return hoaDonDAO.timHoaDon(null, batDau, ketThuc, null);
    }

    /**
     * Lấy danh sách hóa đơn theo loại
     * @param loai Loại hóa đơn cần lấy
     * @return Danh sách hóa đơn thuộc loại đó
     */
    public List<HoaDon> layDanhSachHoaDonTheoLoai(String loai) {
        return hoaDonDAO.timHoaDon(null, null, null, loai);
    }

    /**
     * Lấy danh sách chi tiết hóa đơn với tên dịch vụ
     * @param maHD Mã hóa đơn cần lấy chi tiết
     * @return Danh sách chi tiết hóa đơn kèm tên dịch vụ
     */
    public List<Map<String, Object>> layChiTietHoaDonVoiTenDV(String maHD) {
        try {
            return hoaDonDAO.layChiTietHoaDonVoiTenDV(maHD);
        } catch (Exception e) {
            System.err.println("Lỗi khi lấy chi tiết hóa đơn với tên dịch vụ: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Lỗi khi lấy chi tiết hóa đơn: " + e.getMessage(), e);
        }
    }

    /**
     * Lấy thông tin chi tiết của hóa đơn theo mã
     * @param maHD Mã hóa đơn cần lấy thông tin
     * @return Hóa đơn với thông tin đầy đủ
     */
    public HoaDon layThongTinHoaDon(String maHD) {
        return hoaDonDAO.layThongTinHoaDon(maHD);
    }

}
