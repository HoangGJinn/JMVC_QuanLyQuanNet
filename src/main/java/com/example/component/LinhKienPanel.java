package com.example.component;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.image.BufferedImage;


public class LinhKienPanel extends JPanel {
    private JTextField tfTenLinhKien;
    private JTextField tfChiTiet;
    private JTextField tfSoLuong;
    private JButton btnXoa;


    public LinhKienPanel(String tenLinhKien, String chiTiet, int soLuong, boolean isEditable) {
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(new Color(200, 200, 200), 1, true),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
        setBackground(Color.WHITE);
        setMaximumSize(new Dimension(Short.MAX_VALUE, 120));

        // Panel chứa các trường nhập liệu
        JPanel contentPanel = new JPanel(new GridLayout(3, 2, 10, 5));
        contentPanel.setBackground(Color.WHITE);

        // Tên linh kiện
        JLabel lblTenLinhKien = new JLabel("Tên linh kiện:");
        lblTenLinhKien.setFont(new Font("Sans-serif", Font.BOLD, 14));
        tfTenLinhKien = new JTextField(tenLinhKien);
        tfTenLinhKien.setFont(new Font("Sans-serif", Font.PLAIN, 14));
        tfTenLinhKien.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(new Color(200, 200, 200), 1),
                BorderFactory.createEmptyBorder(5, 8, 5, 8)
        ));
        tfTenLinhKien.setEditable(isEditable); // Chỉ cho phép sửa tên linh kiện mới

        // Chi tiết linh kiện
        JLabel lblChiTiet = new JLabel("Chi tiết linh kiện:");
        lblChiTiet.setFont(new Font("Sans-serif", Font.BOLD, 14));
        tfChiTiet = new JTextField(chiTiet);
        tfChiTiet.setFont(new Font("Sans-serif", Font.PLAIN, 14));
        tfChiTiet.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(new Color(200, 200, 200), 1),
                BorderFactory.createEmptyBorder(5, 8, 5, 8)
        ));

        // Số lượng
        JLabel lblSoLuong = new JLabel("Số lượng:");
        lblSoLuong.setFont(new Font("Sans-serif", Font.BOLD, 14));
        tfSoLuong = new JTextField(String.valueOf(soLuong));
        tfSoLuong.setFont(new Font("Sans-serif", Font.PLAIN, 14));
        tfSoLuong.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(new Color(200, 200, 200), 1),
                BorderFactory.createEmptyBorder(5, 8, 5, 8)
        ));

        // Thêm các thành phần vào panel
        contentPanel.add(lblTenLinhKien);
        contentPanel.add(tfTenLinhKien);
        contentPanel.add(lblChiTiet);
        contentPanel.add(tfChiTiet);
        contentPanel.add(lblSoLuong);
        contentPanel.add(tfSoLuong);

        // Nút Xóa
        btnXoa = new JButton(getDeleteIcon());
        btnXoa.setBorderPainted(false);
        btnXoa.setContentAreaFilled(false);
        btnXoa.setFocusPainted(false);
        btnXoa.setToolTipText("Xóa linh kiện");

        JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        rightPanel.setBackground(Color.WHITE);
        rightPanel.add(btnXoa);

        add(contentPanel, BorderLayout.CENTER);
        add(rightPanel, BorderLayout.EAST);
    }


    private ImageIcon getDeleteIcon() {
        // Tạo hình ảnh thùng rác màu cam
        int size = 24;
        BufferedImage image = new BufferedImage(size, size, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = image.createGraphics();

        // Vẽ icon thùng rác đơn giản
        g.setColor(new Color(255, 128, 0));
        g.setStroke(new BasicStroke(1.5f));

        // Thân thùng rác
        g.fillRect(6, 8, 12, 14);
        // Nắp thùng rác
        g.fillRect(4, 5, 16, 3);
        // Tay cầm
        g.fillRect(9, 3, 6, 2);

        // Vẽ các đường kẻ trong thùng rác
        g.setColor(Color.WHITE);
        g.drawLine(9, 11, 9, 18);
        g.drawLine(12, 11, 12, 18);
        g.drawLine(15, 11, 15, 18);

        g.dispose();

        return new ImageIcon(image);
    }

    public String getTenLinhKien() {
        return tfTenLinhKien.getText();
    }

    public String getChiTiet() {
        return tfChiTiet.getText();
    }

    public int getSoLuong() {
        try {
            return Integer.parseInt(tfSoLuong.getText().trim());
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    public JButton getBtnXoa() {
        return btnXoa;
    }
}