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

    public HoaDon() {
        this.chiTietDVs = new HashSet<>();
    }

    // Getters and setters for all fields
}