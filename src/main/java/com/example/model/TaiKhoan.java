import java.util.ArrayList;

public class TaiKhoan
{
    public String tenDangNhap;
    public String matKhau;
    public int soDu;
    public String ngayTao;
    public String trangThai;
    public ArrayList<HoaDon> HoaDons;
    public ArrayList<ChiTietSuDung> ChiTietSuDungs;
    public TaiKhoan(){
        this.HoaDons = new ArrayList<HoaDon>();
        this.ChiTietSuDungs = new ArrayList<ChiTietSuDung>();
    }
}
