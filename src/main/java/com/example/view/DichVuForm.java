package com.example.view;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;

public class DichVuForm extends JFrame {
    public DichVuForm() {
        setTitle("Quản lý dịch vụ");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1200, 600);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(1, 3, 10, 0));
        // Panel Đồ ăn
        JPanel panelDoAn = createServicePanel(
                "Dịch vụ đồ ăn",
                new String[]{"Tên Đồ Ăn", "Đơn Giá", "Trạng Thái", "BS"},
                "Tên đồ ăn:", "Nhập tên đồ ăn", true
        );
        add(panelDoAn);

        // Panel Đồ uống
        JPanel panelDoUong = createServicePanel(
                "Dịch vụ đồ uống",
                new String[]{"Tên Đồ Ăn", "Đơn Giá", "Trạng Thái", "BS"},
                "Tên đồ uống:", "Nhập tên đồ uống", true
        );
        add(panelDoUong);

        // Panel Thẻ cào
        JPanel panelTheCao = createServicePanel(
                "Dịch vụ thẻ cào",
                new String[]{"Loại Thẻ", "Mệnh Giá"},
                "Loại thẻ:", "Nhập loại thẻ", false
        );
        add(panelTheCao);
    }

    // ... existing code ...
    private JPanel createServicePanel(String title, String[] tableHeaders, String label1, String placeholder1, boolean hasBestSeller) {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout(0, 5));
        panel.setBackground(new Color(242, 242, 242)); // Nền panel xám nhạt
        panel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.LIGHT_GRAY), title, TitledBorder.LEFT, TitledBorder.TOP, new Font("Arial", Font.BOLD, 22)));

        // Top: Search
        JPanel searchPanel = new JPanel(new BorderLayout(5, 0));
        searchPanel.setBackground(new Color(242, 242, 242));
        JTextField searchField = new JTextField("Tìm kiếm");
        searchField.setFont(new Font("Arial", Font.PLAIN, 16));
        searchField.setBorder(BorderFactory.createLineBorder(new Color(180, 150, 255), 2)); // Viền tím nhạt
        JButton searchBtn = new JButton();
        searchBtn.setBackground(Color.WHITE);
        searchBtn.setIcon(UIManager.getIcon("FileView.fileIcon")); // Placeholder icon
        searchPanel.add(searchField, BorderLayout.CENTER);
        searchPanel.add(searchBtn, BorderLayout.EAST);
        searchPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 0, 10));
        panel.add(searchPanel, BorderLayout.NORTH);

        // Center: Table
        JTable table = new JTable(new Object[0][tableHeaders.length], tableHeaders);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setPreferredSize(new Dimension(350, 220));
        // Header màu xanh
        table.getTableHeader().setBackground(new Color(51, 221, 202));
        table.getTableHeader().setForeground(Color.BLACK);
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 15));
        table.setFont(new Font("Arial", Font.PLAIN, 14));
        table.setRowHeight(28);
        panel.add(scrollPane, BorderLayout.CENTER);

        // Bottom: Form + Buttons
        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new BoxLayout(bottomPanel, BoxLayout.Y_AXIS));
        bottomPanel.setBackground(new Color(242, 242, 242));
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Form fields
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new GridBagLayout());
        formPanel.setBackground(new Color(242, 242, 242));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(3, 3, 3, 3);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0; gbc.gridy = 0;
        JLabel label1Comp = new JLabel(label1);
        label1Comp.setFont(new Font("Arial", Font.PLAIN, 14));
        formPanel.add(label1Comp, gbc);
        gbc.gridx = 1;
        JTextField tf1 = new JTextField(12);
        tf1.setFont(new Font("Arial", Font.PLAIN, 15));
        tf1.setToolTipText(placeholder1);
        tf1.setBorder(BorderFactory.createLineBorder(new Color(180, 150, 255), 2));
        formPanel.add(tf1, gbc);
        if (hasBestSeller) {
            gbc.gridx = 2;
            JCheckBox bestSeller = new JCheckBox("Best seller");
            bestSeller.setBackground(new Color(242, 242, 242));
            formPanel.add(bestSeller, gbc);
        }
        gbc.gridx = 0; gbc.gridy++;
        JLabel labelDonGia = new JLabel("Đơn giá:");
        labelDonGia.setFont(new Font("Arial", Font.PLAIN, 14));
        formPanel.add(labelDonGia, gbc);
        gbc.gridx = 1;
        JTextField tf2 = new JTextField(12);
        tf2.setFont(new Font("Arial", Font.PLAIN, 15));
        tf2.setToolTipText("Nhập đơn giá");
        tf2.setBorder(BorderFactory.createLineBorder(new Color(180, 150, 255), 2));
        formPanel.add(tf2, gbc);
        if (!hasBestSeller) {
            gbc.gridx = 0; gbc.gridy++;
            JLabel labelMenhGia = new JLabel("Mệnh giá:");
            labelMenhGia.setFont(new Font("Arial", Font.PLAIN, 14));
            formPanel.add(labelMenhGia, gbc);
            gbc.gridx = 1;
            JTextField tf3 = new JTextField(12);
            tf3.setFont(new Font("Arial", Font.PLAIN, 15));
            tf3.setToolTipText("Nhập mệnh giá");
            tf3.setBorder(BorderFactory.createLineBorder(new Color(180, 150, 255), 2));
            formPanel.add(tf3, gbc);
        }
        gbc.gridx = 0; gbc.gridy++;
        JLabel labelHinh = new JLabel("Hình:");
        labelHinh.setFont(new Font("Arial", Font.PLAIN, 14));
        formPanel.add(labelHinh, gbc);
        gbc.gridx = 1;
        JLabel imgLabel = new JLabel();
        imgLabel.setPreferredSize(new Dimension(32, 32));
        imgLabel.setBorder(BorderFactory.createDashedBorder(Color.GRAY));
        formPanel.add(imgLabel, gbc);
        gbc.gridx = 2;
        JButton btnChon = new JButton("Chọn");
        btnChon.setBackground(new Color(140, 120, 255));
        btnChon.setForeground(Color.WHITE);
        formPanel.add(btnChon, gbc);
        gbc.gridx = 0; gbc.gridy++;
        JLabel labelTrangThai = new JLabel("Trạng thái:");
        labelTrangThai.setFont(new Font("Arial", Font.PLAIN, 14));
        formPanel.add(labelTrangThai, gbc);
        gbc.gridx = 1;
        JComboBox<String> cbTrangThai = new JComboBox<>(new String[]{"", "Hoạt động", "Ngừng bán"});
        cbTrangThai.setFont(new Font("Arial", Font.PLAIN, 14));
        cbTrangThai.setBorder(BorderFactory.createLineBorder(new Color(180, 150, 255), 2));
        formPanel.add(cbTrangThai, gbc);
        bottomPanel.add(formPanel);

        // Buttons
        JPanel btnPanel = new JPanel(new GridLayout(2, 2, 10, 10));
        btnPanel.setBackground(new Color(242, 242, 242));
        JButton btnThem = new JButton("Thêm");
        JButton btnCapNhat = new JButton("Cập Nhật");
        JButton btnXoa = new JButton("Xóa");
        JButton btnHuy = new JButton("Hủy");
        Color btnColor = new Color(140, 120, 255);
        btnThem.setBackground(btnColor);
        btnCapNhat.setBackground(btnColor);
        btnXoa.setBackground(btnColor);
        btnHuy.setBackground(btnColor);
        btnThem.setForeground(Color.WHITE);
        btnCapNhat.setForeground(Color.WHITE);
        btnXoa.setForeground(Color.WHITE);
        btnHuy.setForeground(Color.WHITE);
        btnThem.setFont(new Font("Arial", Font.BOLD, 16));
        btnCapNhat.setFont(new Font("Arial", Font.BOLD, 16));
        btnXoa.setFont(new Font("Arial", Font.BOLD, 16));
        btnHuy.setFont(new Font("Arial", Font.BOLD, 16));
        btnPanel.add(btnThem);
        btnPanel.add(btnCapNhat);
        btnPanel.add(btnXoa);
        btnPanel.add(btnHuy);
        btnPanel.setMaximumSize(new Dimension(300, 60));
        bottomPanel.add(Box.createVerticalStrut(10));
        bottomPanel.add(btnPanel);

        panel.add(bottomPanel, BorderLayout.SOUTH);
        return panel;
    }
// ... existing code ...


    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new DichVuForm().setVisible(true);
        });
    }
}
