package com.example.view;

import com.example.controller.NhanVienController;
import com.example.model.NhanVien;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.sql.Date;
import java.text.ParseException;
import java.util.List;

public class NhanVienForm extends JFrame {

    private DefaultTableModel model;
    private JTable table;
    private NhanVienController controller;

    // Form nhập liệu
    private JTextField txtHoTen, txtSdt, txtDiaChi;
    private JComboBox<String> cbGioiTinh;
    private JTextField txtNgaySinh;

    public NhanVienForm() {
        controller = new NhanVienController();

        setTitle("Danh Sách Nhân Viên");
        setSize(1200, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        // ============ Top Panel ============
        JButton btnAdd = new JButton("Thêm Mới");
        btnAdd.setBackground(new Color(112, 48, 160));
        btnAdd.setForeground(Color.WHITE);
        btnAdd.setFocusPainted(false);
        btnAdd.setFont(new Font("Arial", Font.BOLD, 14));
        btnAdd.setPreferredSize(new Dimension(120, 40));

        JTextField searchField = new JTextField("Tìm kiếm");
        searchField.setPreferredSize(new Dimension(200, 30));

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 0, 10));
        topPanel.add(btnAdd, BorderLayout.WEST);
        topPanel.add(searchField, BorderLayout.EAST);
        add(topPanel, BorderLayout.NORTH);

        // ============ Table Panel ============
        String[] columnNames = {
                "ID", "Họ Và Tên", "SĐT", "Giới Tính", "Ngày Sinh", "Địa Chỉ", "Số HĐ", "Doanh Thu Tháng"
        };
        model = new DefaultTableModel(columnNames, 0);
        table = new JTable(model);
        table.setFillsViewportHeight(true);
        table.setRowHeight(25);
        table.setFont(new Font("Arial", Font.PLAIN, 13));
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 13));

        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        // ============ Input Form Panel ============
        JPanel formPanel = new JPanel(new GridLayout(6, 2, 10, 10)); // Thay đổi số hàng và cột để có đủ chỗ cho tất cả các trường
        formPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        txtHoTen = new JTextField();
        txtSdt = new JTextField();
        txtDiaChi = new JTextField();
        cbGioiTinh = new JComboBox<>(new String[]{"Nam", "Nữ"});

        txtNgaySinh = new JTextField();
        txtNgaySinh.setToolTipText("dd/MM/yyyy");
        txtNgaySinh.setText(new SimpleDateFormat("dd/MM/yyyy").format(new java.util.Date())); // mặc định hôm nay

        // Sử dụng GridLayout với 6 hàng, 2 cột
        formPanel.add(createLabeledField("Họ và tên nhân viên", txtHoTen));
        formPanel.add(createLabeledField("Số điện thoại", txtSdt));
        formPanel.add(createLabeledField("Giới tính", cbGioiTinh));
        formPanel.add(createLabeledField("Ngày sinh", txtNgaySinh));
        formPanel.add(createLabeledField("Địa chỉ", txtDiaChi));
        add(formPanel, BorderLayout.WEST);  // Đưa formPanel vào BorderLayout.WEST để nó hiển thị rõ hơn

        // ============ Button Panel ============
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        String[] btnLabels = {"Thêm", "Làm Mới", "Cập Nhật", "Mật Khẩu", "Xóa", "Hủy"};

        for (String label : btnLabels) {
            JButton btn = new JButton(label);
            btn.setPreferredSize(new Dimension(100, 35));
            btn.setBackground(new Color(147, 112, 219));
            btn.setForeground(Color.WHITE);
            btn.setFocusPainted(false);
            buttonPanel.add(btn);

            // Gắn sự kiện cho nút "Thêm"
            if (label.equals("Thêm")) {
                btn.addActionListener(e -> themNhanVien());
            }
        }

        add(buttonPanel, BorderLayout.PAGE_END);

        // Load dữ liệu bảng
        loadNhanVienData();
    }

    private JPanel createLabeledField(String label, JComponent field) {
        JPanel panel = new JPanel(new BorderLayout(5, 5));
        JLabel lbl = new JLabel(label);
        panel.add(lbl, BorderLayout.NORTH);
        panel.add(field, BorderLayout.CENTER);
        return panel;
    }

    private void loadNhanVienData() {
        List<NhanVien> ds = controller.layDanhSachNhanVien();
        model.setRowCount(0);
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        for (NhanVien nv : ds) {
            model.addRow(new Object[]{
                    nv.getMaNV(),
                    nv.getHoTen(),
                    nv.getSdt(),
                    nv.getGioiTinh(),
                    formatter.format(nv.getNgaySinh()),
                    nv.getDiaChi(),
                    "0", // số HĐ
                    "0 VNĐ" // doanh thu
            });
        }
    }

    private void themNhanVien() {
        try {
            String hoTen = txtHoTen.getText().trim();
            String sdt = txtSdt.getText().trim();
            String diaChi = txtDiaChi.getText().trim();
            String gioiTinh = (String) cbGioiTinh.getSelectedItem();
            String ngaySinhStr = txtNgaySinh.getText().trim();

            if (hoTen.isEmpty() || sdt.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ họ tên và SĐT!");
                return;
            }

            // Chuyển đổi String thành java.sql.Date
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
            java.util.Date utilDate = formatter.parse(ngaySinhStr);
            java.sql.Date ngaySinh = new java.sql.Date(utilDate.getTime());

            System.out.println("Họ tên: " + hoTen);
            System.out.println("SĐT: " + sdt);
            System.out.println("Địa chỉ: " + diaChi);
            System.out.println("Giới tính: " + gioiTinh);
            System.out.println("Ngày sinh: " + ngaySinh);

            // Gọi phương thức themNhanVien từ controller
            controller.themNhanVien(hoTen, sdt, diaChi, gioiTinh, ngaySinh.toLocalDate());

            JOptionPane.showMessageDialog(this, "Thêm nhân viên thành công!");
            loadNhanVienData(); // refresh lại bảng

        } catch (ParseException e) {
            JOptionPane.showMessageDialog(this, "Lỗi khi thêm nhân viên: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new NhanVienForm().setVisible(true));
    }
}
