import java.util.ArrayList;

public class LoaiMay {
    /*
            public LOAIMAY()
        {
            this.LINHKIENs = new HashSet<LINHKIEN>();
            this.MAYTINHs = new HashSet<MAYTINH>();
        }

        public int MaLoaiMay { get; set; }
        public string TenLoaiMay { get; set; }
        public int SoTienMotGio { get; set; }

        [System.Diagnostics.CodeAnalysis.SuppressMessage("Microsoft.Usage", "CA2227:CollectionPropertiesShouldBeReadOnly")]
        public virtual ICollection<LINHKIEN> LINHKIENs { get; set; }
        [System.Diagnostics.CodeAnalysis.SuppressMessage("Microsoft.Usage", "CA2227:CollectionPropertiesShouldBeReadOnly")]
        public virtual ICollection<MAYTINH> MAYTINHs { get; set; }
     */
    public int maLoaiMay;
    public String tenLoaiMay;
    public int soTienMotGio;
    public ArrayList<MayTinh> mayTinhs;
    public ArrayList<LinhKien> linhKiens;



}
