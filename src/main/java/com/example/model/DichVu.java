import java.util.ArrayList;

public class DichVu {
    public String MaDV;
    public String TenDV;
    public ArrayList<ChiTietDV> ChiTietDVs;
    public DVDoAn dvDoAn;
    public DVDoUong dvDoUong;
    public DvTheCao dvTheCao;
    public DichVu() {
        this.ChiTietDVs = new ArrayList<ChiTietDV>();
    }
}
