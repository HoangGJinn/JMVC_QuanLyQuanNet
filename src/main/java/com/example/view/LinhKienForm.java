package com.example.view;

import com.example.dao.LoaiMayDAO;
import com.example.component.LinhKienPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LinhKienForm extends JDialog {
    private LoaiMayDAO loaiMayDAO;
    private int maLoaiMay;
    private JPanel contentPane;
    private JButton btnThem;
    private JButton btnLuu;
    private JButton btnHuy;
    private JButton btnThoat;
    private JPanel panelLinhKien;
    private JLabel lblTitle;
    
    private List<LinhKienPanel> oldLinhKienList = new ArrayList<>();
    private List<LinhKienPanel> newLinhKienList = new ArrayList<>();
    
    public LinhKienForm(JFrame parent, int maLoaiMay) {
        super(parent, "Quản Lý Linh Kiện", true);
        this.maLoaiMay = maLoaiMay;
        this.loaiMayDAO = LoaiMayDAO.getInstance();
        
        initUI();
        loadLinhKien(maLoaiMay);
    }
    
    private void initUI() {
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        
        contentPane = new JPanel(new BorderLayout());
        contentPane.setBackground(new Color(40, 44, 68));
        
        // Tạo header
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(new Color(30, 34, 58));
        headerPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        
        lblTitle = new JLabel("Quản Lý Linh Kiện");
        lblTitle.setFont(new Font("Sans-serif", Font.BOLD, 24));
        lblTitle.setForeground(Color.WHITE);
        
        btnThoat = new JButton("X");
        btnThoat.setFont(new Font("Sans-serif", Font.BOLD, 18));
        btnThoat.setForeground(Color.WHITE);
        btnThoat.setBackground(new Color(255, 100, 100));
        btnThoat.setPreferredSize(new Dimension(40, 40));
        btnThoat.setBorderPainted(false);
        btnThoat.setFocusPainted(false);
        btnThoat.addActionListener(e -> dispose());
        
        headerPanel.add(lblTitle, BorderLayout.WEST);
        headerPanel.add(btnThoat, BorderLayout.EAST);
        
        // Tạo phần danh sách linh kiện với FlowLayout
        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.setBackground(new Color(240, 240, 240));
        centerPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        JLabel lblDanhSach = new JLabel("Danh sách linh kiện");
        lblDanhSach.setFont(new Font("Sans-serif", Font.BOLD, 18));
        
        panelLinhKien = new JPanel();
        panelLinhKien.setLayout(new BoxLayout(panelLinhKien, BoxLayout.Y_AXIS));
        panelLinhKien.setBackground(new Color(240, 240, 240));
        
        JScrollPane scrollPane = new JScrollPane(panelLinhKien);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        
        centerPanel.add(lblDanhSach, BorderLayout.NORTH);
        centerPanel.add(scrollPane, BorderLayout.CENTER);
        
        // Tạo nút thêm
        btnThem = new JButton("Thêm Linh Kiện");
        btnThem.setFont(new Font("Sans-serif", Font.BOLD, 14));
        btnThem.setBackground(new Color(116, 103, 239));
        btnThem.setForeground(Color.WHITE);
        btnThem.setFocusPainted(false);
        btnThem.setBorderPainted(false);
        btnThem.addActionListener(e -> themLinhKien());
        
        JPanel addButtonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        addButtonPanel.setBackground(new Color(240, 240, 240));
        addButtonPanel.add(btnThem);
        
        centerPanel.add(addButtonPanel, BorderLayout.SOUTH);
        
        // Tạo footer với các nút
        JPanel footerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        footerPanel.setBackground(new Color(40, 44, 68));
        
        btnLuu = new JButton("Lưu");
        btnLuu.setFont(new Font("Sans-serif", Font.BOLD, 14));
        btnLuu.setBackground(new Color(116, 103, 239));
        btnLuu.setForeground(Color.WHITE);
        btnLuu.setPreferredSize(new Dimension(150, 40));
        btnLuu.setFocusPainted(false);
        btnLuu.setBorderPainted(false);
        btnLuu.addActionListener(e -> luuThayDoi());
        
        btnHuy = new JButton("Thoát");
        btnHuy.setFont(new Font("Sans-serif", Font.BOLD, 14));
        btnHuy.setBackground(new Color(116, 103, 239));
        btnHuy.setForeground(Color.WHITE);
        btnHuy.setPreferredSize(new Dimension(150, 40));
        btnHuy.setFocusPainted(false);
        btnHuy.setBorderPainted(false);
        btnHuy.addActionListener(e -> dispose());
        
        footerPanel.add(btnLuu);
        footerPanel.add(btnHuy);
        
        // Thêm tất cả vào content pane
        contentPane.add(headerPanel, BorderLayout.NORTH);
        contentPane.add(centerPanel, BorderLayout.CENTER);
        contentPane.add(footerPanel, BorderLayout.SOUTH);
        
        setContentPane(contentPane);
    }
    
    /**
     * Tải danh sách linh kiện theo mã loại máy
     */
    private void loadLinhKien(int maLoaiMay) {
        try {
            // Xóa danh sách cũ
            oldLinhKienList.clear();
            newLinhKienList.clear();
            panelLinhKien.removeAll();
            
            // Lấy danh sách linh kiện từ database
            List<String[]> linhKienList = loaiMayDAO.timLinhKienTheoLoaiMay(maLoaiMay);
            
            for (String[] linhKien : linhKienList) {
                String tenLinhKien = linhKien[0];
                String chiTiet = linhKien[1];
                int soLuong = Integer.parseInt(linhKien[2]);
                
                LinhKienPanel panel = new LinhKienPanel(tenLinhKien, chiTiet, soLuong, false);
                
                // Thêm sự kiện xóa
                panel.getBtnXoa().addActionListener(e -> {
                    int result = JOptionPane.showConfirmDialog(
                            this,
                            "Bạn có chắc chắn muốn xóa linh kiện này không?",
                            "Xác nhận",
                            JOptionPane.YES_NO_OPTION,
                            JOptionPane.QUESTION_MESSAGE
                    );
                    
                    if (result == JOptionPane.YES_OPTION) {
                        try {
                            loaiMayDAO.xoaLinhKien(maLoaiMay, panel.getTenLinhKien());
                            JOptionPane.showMessageDialog(this, "Xóa thành công", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                            loadLinhKien(maLoaiMay); // Tải lại danh sách
                        } catch (Exception ex) {
                            JOptionPane.showMessageDialog(this, "Lỗi: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                });
                
                oldLinhKienList.add(panel);
                panelLinhKien.add(panel);
                panelLinhKien.add(Box.createVerticalStrut(10)); // Tạo khoảng cách giữa các panel
            }
            
            panelLinhKien.revalidate();
            panelLinhKien.repaint();
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi khi tải danh sách linh kiện: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }
    

    //Thêm một linh kiện mới vào danh sách
    private void themLinhKien() {
        LinhKienPanel panel = new LinhKienPanel("", "", 1, true);
        
        // Thêm sự kiện xóa
        panel.getBtnXoa().addActionListener(e -> {
            panelLinhKien.remove(panel);
            panelLinhKien.remove(panelLinhKien.getComponent(panelLinhKien.getComponentCount() - 1)); // Xóa khoảng cách
            newLinhKienList.remove(panel);
            panelLinhKien.revalidate();
            panelLinhKien.repaint();
        });
        
        newLinhKienList.add(panel);
        panelLinhKien.add(panel);
        panelLinhKien.add(Box.createVerticalStrut(10)); // Tạo khoảng cách
        panelLinhKien.revalidate();
        panelLinhKien.repaint();
        
        // Cuộn xuống cuối
        SwingUtilities.invokeLater(() -> {
            JScrollPane scrollPane = (JScrollPane) panelLinhKien.getParent().getParent();
            JScrollBar vertical = scrollPane.getVerticalScrollBar();
            vertical.setValue(vertical.getMaximum());
        });
    }
    

    //Lưu tất cả các thay đổi vào database
    private void luuThayDoi() {
        try {
            int result = JOptionPane.showConfirmDialog(
                    this,
                    "Bạn có chắc chắn muốn lưu các thay đổi không?",
                    "Xác nhận",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE
            );
            
            if (result == JOptionPane.YES_OPTION) {
                // Cập nhật các linh kiện đã có
                for (LinhKienPanel panel : oldLinhKienList) {
                    // Kiểm tra dữ liệu
                    if (panel.getTenLinhKien().trim().isEmpty()) {
                        JOptionPane.showMessageDialog(this, "Tên linh kiện không được để trống!", "Lỗi", JOptionPane.WARNING_MESSAGE);
                        return;
                    }
                    
                    if (panel.getSoLuong() <= 0) {
                        JOptionPane.showMessageDialog(this, "Số lượng phải lớn hơn 0!", "Lỗi", JOptionPane.WARNING_MESSAGE);
                        return;
                    }
                    
                    loaiMayDAO.suaLinhKien(
                            maLoaiMay, 
                            panel.getTenLinhKien(), 
                            panel.getChiTiet(), 
                            panel.getSoLuong()
                    );
                }
                
                // Thêm các linh kiện mới
                for (LinhKienPanel panel : newLinhKienList) {
                    // Kiểm tra dữ liệu
                    if (panel.getTenLinhKien().trim().isEmpty()) {
                        JOptionPane.showMessageDialog(this, "Tên linh kiện không được để trống!", "Lỗi", JOptionPane.WARNING_MESSAGE);
                        return;
                    }
                    
                    if (panel.getSoLuong() <= 0) {
                        JOptionPane.showMessageDialog(this, "Số lượng phải lớn hơn 0!", "Lỗi", JOptionPane.WARNING_MESSAGE);
                        return;
                    }
                    
                    loaiMayDAO.themLinhKien(
                            maLoaiMay, 
                            panel.getTenLinhKien(), 
                            panel.getChiTiet(), 
                            panel.getSoLuong()
                    );
                }
                
                JOptionPane.showMessageDialog(this, "Lưu thành công", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                loadLinhKien(maLoaiMay); // Tải lại danh sách
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi khi lưu: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }
}
