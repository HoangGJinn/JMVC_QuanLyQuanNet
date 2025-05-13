
import com.example.view.NhanVienForm;
import com.example.util.DatabaseConnection;
import com.example.view.TaiKhoanForm;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import com.example.view.*;

public class Main {
    public static void main(String[] args) {
        // Thử kết nối cơ sở dữ liệu
        try (Connection conn = DatabaseConnection.getInstance()) {
            if (conn != null) {
                System.out.println("Kết nối thành công!");
            } else {
                System.out.println("Kết nối thất bại!");
            }
        } catch (SQLException e) {
            System.err.println("Lỗi khi kết nối cơ sở dữ liệu: " + e.getMessage());
        }

        // Hiển thị form quản lý nhân viên

//        NhanVienForm nhanVienForm = new NhanVienForm();
//        nhanVienForm.setVisible(true);

        // Hiển thị form quản lý tài khoản
        TaiKhoanForm taiKhoanForm = new TaiKhoanForm();
        taiKhoanForm.setVisible(true);
    }
}
