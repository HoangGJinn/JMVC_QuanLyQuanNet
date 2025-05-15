package com.example.view;

import com.example.controller.NhanVienController;
import com.example.model.NhanVien;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.sql.Date;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.List;

public class NhanVienForm extends JFrame {

    private DefaultTableModel model;
    private JTable table;
    private NhanVienController controller;

    // Form nhập liệu
    private JTextField txtHoTen, txtSdt, txtDiaChi;
    private JComboBox<String> cbGioiTinh;
    private JTextField txtNgaySinh;
    
    // Main content panel for embedding
    private JPanel mainContentPanel;
    private boolean isEmbedded = false;

    public NhanVienForm() {
        this(false);
    }
    
    public NhanVienForm(boolean isEmbedded) {
        this.isEmbedded = isEmbedded;
        controller = new NhanVienController();

        setTitle("Danh Sách Nhân Viên");
        if (!isEmbedded) {
            setSize(1200, 600);
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setLocationRelativeTo(null);
        }
        
        // Create main content panel
        mainContentPanel = new JPanel(new BorderLayout(10, 10));
        mainContentPanel.setBackground(Color.WHITE);

        // ============ Top Panel ============
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        topPanel.setBackground(Color.WHITE);
        
        // Add "Thêm Mới" button with rounded corners and purple color
        JButton btnAdd = new JButton("Thêm Mới");
        btnAdd.setBackground(new Color(112, 76, 240));
        btnAdd.setForeground(Color.WHITE);
        btnAdd.setFocusPainted(false);
        btnAdd.setFont(new Font("Arial", Font.BOLD, 14));
        btnAdd.setPreferredSize(new Dimension(120, 40));
        btnAdd.setBorderPainted(false);
        btnAdd.addActionListener(e -> ClearForm());
        
        // Search field with rounded look
        JTextField searchField = new JTextField(15);
        searchField.setText("Tìm kiếm");
        searchField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.LIGHT_GRAY),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)));
        searchField.setPreferredSize(new Dimension(200, 30));
        searchField.addActionListener(e -> timKiem());
        
        // Create a panel for search with icon
        JPanel searchPanel = new JPanel(new BorderLayout());
        searchPanel.setBackground(Color.WHITE);
        searchPanel.add(searchField, BorderLayout.CENTER);
        
        // Add a search icon button
        JButton searchButton = new JButton(new ImageIcon("src/main/resources/search_icon.png"));
        if (!searchButton.getIcon().toString().contains("ImageIcon")) {
            searchButton.setText("🔍");
        }
        searchButton.setBorderPainted(false);
        searchButton.setContentAreaFilled(false);
        searchButton.addActionListener(e -> timKiem());
        searchPanel.add(searchButton, BorderLayout.EAST);
        
        topPanel.add(btnAdd, BorderLayout.WEST);
        topPanel.add(searchPanel, BorderLayout.EAST);
        mainContentPanel.add(topPanel, BorderLayout.NORTH);

        // ============ Table Panel ============
        String[] columnNames = {
                "ID", "Họ Và Tên", "SĐT", "Giới Tính", "Ngày Sinh", "Địa Chỉ", "Số HĐ", "Doanh Thu Tháng"
        };
        model = new DefaultTableModel(columnNames, 0);
        table = new JTable(model) {
            @Override
            public Component prepareRenderer(javax.swing.table.TableCellRenderer renderer, int row, int column) {
                Component comp = super.prepareRenderer(renderer, row, column);
                if (row % 2 == 1) {
                    comp.setBackground(new Color(240, 240, 240));
                } else {
                    comp.setBackground(Color.WHITE);
                }
                if (isCellSelected(row, column)) {
                    comp.setBackground(new Color(204, 204, 255));
                }
                return comp;
            }
        };
        
        table.setFillsViewportHeight(true);
        table.setRowHeight(35);
        table.setFont(new Font("Arial", Font.PLAIN, 13));
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 13));
        table.getTableHeader().setBackground(new Color(0, 204, 204));
        table.getTableHeader().setForeground(Color.WHITE);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setShowGrid(true);
        table.setGridColor(new Color(230, 230, 230));
        
        table.getSelectionModel().addListSelectionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow != -1) {
                MapNhanVienToForm(selectedRow);
            }
        });
        
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        mainContentPanel.add(scrollPane, BorderLayout.CENTER);

        // ============ Bottom Form Panel ============
        JPanel bottomPanel = new JPanel(new BorderLayout(10, 10));
        bottomPanel.setBackground(Color.WHITE);
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // ============ Input Form Panel ============
        JPanel formPanel = new JPanel(new GridLayout(1, 5, 10, 0));
        formPanel.setBackground(Color.WHITE);
        
        txtHoTen = createStyledTextField();
        txtSdt = createStyledTextField();
        txtDiaChi = createStyledTextField();
        txtDiaChi.setHorizontalAlignment(JTextField.LEFT);
        cbGioiTinh = new JComboBox<>(new String[]{"Nam", "Nữ"});
        cbGioiTinh.setBackground(Color.WHITE);
        cbGioiTinh.setFont(new Font("Arial", Font.PLAIN, 14));

        txtNgaySinh = createStyledTextField();
        txtNgaySinh.setToolTipText("dd/MM/yyyy");
        
        // Add calendar icon to the date field
        JPanel ngaySinhPanel = new JPanel(new BorderLayout());
        ngaySinhPanel.setBackground(Color.WHITE);
        ngaySinhPanel.add(createLabeledField("Ngày sinh", txtNgaySinh), BorderLayout.CENTER);
        JButton calendarButton = new JButton("📅");
        calendarButton.setBorderPainted(false);
        calendarButton.setContentAreaFilled(false);
        calendarButton.setFocusPainted(false);
        calendarButton.addActionListener(e -> showDatePicker());
        ngaySinhPanel.add(calendarButton, BorderLayout.EAST);
        
        formPanel.add(createLabeledField("Họ và tên nhân viên", txtHoTen));
        formPanel.add(createLabeledField("Số điện thoại", txtSdt));
        formPanel.add(createLabeledField("Giới tính", cbGioiTinh));
        formPanel.add(ngaySinhPanel);
        formPanel.add(createLabeledField("Địa chỉ", txtDiaChi));

        // ============ Button Panel ============
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        buttonPanel.setBackground(Color.WHITE);
        String[] btnLabels = {"Thêm", "Làm Mới", "Cập Nhật", "Mật Khẩu", "Xóa", "Hủy"};
        
        for (String label : btnLabels) {
            JButton btn = createStyledButton(label);
            buttonPanel.add(btn);

            if (label.equals("Thêm")) {
                btn.addActionListener(e -> themNhanVien());
            } else if (label.equals("Xóa")) {
                btn.addActionListener(e -> xoaNhanVien());
            } else if (label.equals("Làm Mới")) {
                btn.addActionListener(e -> lamMoi());
            } else if (label.equals("Cập Nhật")) {
                btn.addActionListener(e -> suaNhanVien());
            } else if (label.equals("Mật Khẩu")) {
                btn.addActionListener(e -> doiMatKhau());
            } else if (label.equals("Hủy")) {
                btn.addActionListener(e -> ClearForm());
            }
        }
        
        bottomPanel.add(formPanel, BorderLayout.CENTER);
        bottomPanel.add(buttonPanel, BorderLayout.SOUTH);
        mainContentPanel.add(bottomPanel, BorderLayout.SOUTH);
        
        if (!isEmbedded) {
            setContentPane(mainContentPanel);
            setVisible(true);
        }

        // Load dữ liệu bảng
        loadNhanVienData();
    }
    
    private JTextField createStyledTextField() {
        JTextField textField = new JTextField();
        textField.setFont(new Font("Arial", Font.PLAIN, 14));
        textField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 0, 1, 0, Color.LIGHT_GRAY),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)));
        return textField;
    }
    
    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setPreferredSize(new Dimension(100, 35));
        button.setBackground(new Color(112, 76, 240));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setFont(new Font("Arial", Font.BOLD, 12));
        return button;
    }
    
    private void showDatePicker() {
        // Placeholder for date picker functionality
        // In a real implementation, we would show a date picker dialog here
        String date = JOptionPane.showInputDialog(this, "Nhập ngày (dd/MM/yyyy):", txtNgaySinh.getText());
        if (date != null && !date.isEmpty()) {
            txtNgaySinh.setText(date);
        }
    }
    
    public Container getContent() {
        return mainContentPanel;
    }

    private JPanel createLabeledField(String label, JComponent field) {
        JPanel panel = new JPanel(new BorderLayout(5, 5));
        panel.setBackground(Color.WHITE);
        JLabel lbl = new JLabel(label);
        lbl.setForeground(new Color(100, 100, 100));
        lbl.setFont(new Font("Arial", Font.PLAIN, 12));
        panel.add(lbl, BorderLayout.NORTH);
        panel.add(field, BorderLayout.CENTER);
        return panel;
    }
    
    private void MapNhanVienToForm(int row) {
        String maNV = model.getValueAt(row, 0).toString();
        String hoTen = model.getValueAt(row, 1).toString();
        String sdt = model.getValueAt(row, 2).toString();
        String gioiTinh = model.getValueAt(row, 3).toString();
        String diaChi = model.getValueAt(row, 5).toString();
        String ngaySinhStr = model.getValueAt(row, 4).toString();

        txtHoTen.setText(hoTen);
        txtSdt.setText(sdt);
        cbGioiTinh.setSelectedItem(gioiTinh);
        txtDiaChi.setText(diaChi);
        txtDiaChi.setHorizontalAlignment(JTextField.LEFT);
        txtNgaySinh.setText(ngaySinhStr);
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
    
    // Keep all the business logic unchanged
    private void xoaNhanVien() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {
            String maNV = model.getValueAt(selectedRow, 0).toString();
            controller.xoaNhanVien(maNV);
            loadNhanVienData(); // refresh lại bảng
            JOptionPane.showMessageDialog(this, "Xóa nhân viên thành công!");
        } else {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn nhân viên để xóa!");
        }
    }
    
    private void suaNhanVien() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {
            String maNV = model.getValueAt(selectedRow, 0).toString();
            String hoTen = txtHoTen.getText().trim();
            String diaChi = txtDiaChi.getText().trim();
            String gioiTinh = (String) cbGioiTinh.getSelectedItem();
            String ngaySinhStr = txtNgaySinh.getText().trim();

            if (hoTen.isEmpty() || diaChi.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin!");
                return;
            }

            // Chuyển đổi String thành java.time.LocalDate
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
            java.util.Date utilDate;
            try {
                utilDate = formatter.parse(ngaySinhStr);
                java.time.LocalDate localDate = new java.sql.Date(utilDate.getTime()).toLocalDate();

                controller.suaNhanVien(maNV, hoTen, diaChi, gioiTinh, localDate);
                loadNhanVienData(); // refresh lại bảng
                JOptionPane.showMessageDialog(this, "Cập nhật nhân viên thành công!");

            } catch (ParseException e) {
                JOptionPane.showMessageDialog(this, "Lỗi khi cập nhật nhân viên: " + e.getMessage());
                e.printStackTrace();
            }
        } else {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn nhân viên để sửa!");
        }
    }
    
    private void doiMatKhau() {
        String sdt = txtSdt.getText().trim();
        String matKhauMoi = JOptionPane.showInputDialog(this, "Nhập mật khẩu mới:");
        if (matKhauMoi != null && !matKhauMoi.trim().isEmpty()) {
            controller.doiMatKhau(sdt, matKhauMoi);
            JOptionPane.showMessageDialog(this, "Đổi mật khẩu thành công!");
        } else {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập mật khẩu mới!");
        }
    }
    
    private void lamMoi() {
        loadNhanVienData(); // mặc định hôm nay
        ClearForm();
    }
    
    private void ClearForm() {
        txtHoTen.setText("");
        txtSdt.setText("");
        txtDiaChi.setText("");
        cbGioiTinh.setSelectedIndex(0);
        txtNgaySinh.setText(new SimpleDateFormat("dd/MM/yyyy").format(new java.util.Date())); // mặc định hôm nay
    }
    
    private void timKiem() {
        String tuKhoa = JOptionPane.showInputDialog(this, "Nhập từ khóa tìm kiếm:");
        if (tuKhoa != null && !tuKhoa.trim().isEmpty()) {
            List<NhanVien> ds = controller.timNhanVien(tuKhoa);
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
        } else {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập từ khóa tìm kiếm!");
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

            // Chuyển đổi String thành java.time.LocalDate
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
            java.util.Date utilDate = formatter.parse(ngaySinhStr);
            java.time.LocalDate localDate = new java.sql.Date(utilDate.getTime()).toLocalDate();

            System.out.println("Họ tên: " + hoTen);
            System.out.println("SĐT: " + sdt);
            System.out.println("Địa chỉ: " + diaChi);
            System.out.println("Giới tính: " + gioiTinh);
            System.out.println("Ngày sinh: " + localDate);

            // Gọi phương thức themNhanVien từ controller
            controller.themNhanVien(hoTen, sdt, diaChi, gioiTinh, localDate);

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
