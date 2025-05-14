package com.example.view;

import com.example.controller.HoaDonController;
import com.example.controller.NhanVienController;
import com.example.model.HoaDon;
import com.example.model.NhanVien;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;

public class ChiTietHoaDonNap extends JDialog {
    private JPanel contentPane;
    
    private JLabel idValueLabel;
    private JLabel dateValueLabel;
    private JLabel sidValueLabel;
    private JLabel kmValueLabel;
    private JLabel snameValueLabel;
    private JLabel statusValueLabel;
    private JLabel loginValueLabel;
    private JLabel totalValueLabel;
    private JLabel discountValueLabel;
    private JLabel finalPayValueLabel;
    private JLabel ptttValueLabel;
    
    // For moving borderless window
    private Point initialClick;
    
    private String maHD;
    private HoaDonController hoaDonController;
    private NhanVienController nhanVienController;
    
    // Màu sắc
    private static final Color HEADER_COLOR = new Color(38, 41, 66); // Màu xanh tối cho header
    private static final Color BG_COLOR = new Color(240, 240, 240);  // Màu xám nhạt cho nền
    private static final Color INFO_BG_COLOR = new Color(230, 230, 230);  // Màu xám nhẹ cho phần thông tin
    private static final Color TEXT_COLOR = new Color(33, 33, 33);   // Màu chữ
    
    public ChiTietHoaDonNap(Frame parent, String maHD) {
        super(parent, "Chi tiết hóa đơn", true);
        
        // Set invoice ID and initialize controllers
        this.maHD = maHD.trim();
        
        System.out.println("Khởi tạo ChiTietHoaDonNap với mã hóa đơn: [" + this.maHD + "]");
        
        this.hoaDonController = new HoaDonController();
        this.nhanVienController = new NhanVienController();
        
        // Set undecorated (borderless) window
        setUndecorated(true);
        
        // Initialize UI components
        initComponents();
        
        // Add window dragging capability
        addDraggingCapability();
        
        // Load invoice details
        SwingUtilities.invokeLater(() -> {
            loadChiTietHoaDon();
        });
        
        // Set dialog properties
        setSize(450, 350);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setResizable(false);
    }
    
    private void addDraggingCapability() {
        // Add mouse listeners to the header panel for dragging the window
        contentPane.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                initialClick = e.getPoint();
                getComponentAt(initialClick);
            }
        });

        contentPane.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                // Get current location of the frame
                int thisX = getLocation().x;
                int thisY = getLocation().y;

                // Determine how much the mouse moved since the initial click
                int xMoved = e.getX() - initialClick.x;
                int yMoved = e.getY() - initialClick.y;

                // Move frame to the new location
                int X = thisX + xMoved;
                int Y = thisY + yMoved;
                setLocation(X, Y);
            }
        });
    }
    
    private void initComponents() {
        // Main panel with shadow border
        contentPane = new JPanel();
        contentPane.setLayout(new BorderLayout());
        contentPane.setBackground(BG_COLOR);
        contentPane.setBorder(BorderFactory.createLineBorder(new Color(180, 180, 180), 1));
        setContentPane(contentPane);
        
        // Header Panel - "Thông Tin Hóa Đơn"
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(HEADER_COLOR);
        headerPanel.setPreferredSize(new Dimension(getWidth(), 40));
        
        JLabel titleLabel = new JLabel("Chi tiết hóa đơn", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        titleLabel.setForeground(Color.WHITE);
        headerPanel.add(titleLabel, BorderLayout.CENTER);
        
        // Close Button (dấu X)
        JButton closeButton = new JButton("✕");
        closeButton.setFont(new Font("Arial", Font.PLAIN, 14));
        closeButton.setForeground(Color.WHITE);
        closeButton.setBackground(new Color(0, 184, 148)); // Màu xanh lá nhạt
        closeButton.setBorderPainted(false);
        closeButton.setFocusPainted(false);
        closeButton.setPreferredSize(new Dimension(35, 35));
        closeButton.addActionListener(e -> dispose());
        
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));
        buttonPanel.setBackground(HEADER_COLOR);
        buttonPanel.add(closeButton);
        headerPanel.add(buttonPanel, BorderLayout.EAST);
        
        // Add additional mouse listeners to header for dragging
        headerPanel.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                initialClick = e.getPoint();
                getComponentAt(initialClick);
            }
        });

        headerPanel.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                // Get current location
                int thisX = getLocation().x;
                int thisY = getLocation().y;

                // Determine how much the mouse moved
                int xMoved = e.getX() - initialClick.x;
                int yMoved = e.getY() - initialClick.y;

                // Move frame
                int X = thisX + xMoved;
                int Y = thisY + yMoved;
                setLocation(X, Y);
            }
        });
        
        contentPane.add(headerPanel, BorderLayout.NORTH);
        
        // Content Panel with smaller margins
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.setBackground(Color.WHITE);
        centerPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        
        // Invoice Information Panel - Thông tin hóa đơn (with smaller padding)
        JPanel infoPanel = new JPanel(new GridBagLayout());
        infoPanel.setBackground(INFO_BG_COLOR);
        infoPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(3, 3, 3, 3);
        gbc.weightx = 1.0;
        
        // Row 1: Mã hóa đơn
        addInvoiceInfoRow(infoPanel, "Mã hóa đơn:", 0, gbc);
        idValueLabel = addValueLabel(infoPanel, "", 0, gbc);
        
        // Row 2: Ngày lập
        addInvoiceInfoRow(infoPanel, "Ngày lập:", 1, gbc);
        dateValueLabel = addValueLabel(infoPanel, "", 1, gbc);
        
        // Row 3: Mã NV
        addInvoiceInfoRow(infoPanel, "Mã nhân viên:", 2, gbc);
        sidValueLabel = addValueLabel(infoPanel, "", 2, gbc);
        
        // Row 4: Tên NV
        addInvoiceInfoRow(infoPanel, "Tên nhân viên:", 3, gbc);
        snameValueLabel = addValueLabel(infoPanel, "", 3, gbc);
        
        // Row 5: Trạng thái
        addInvoiceInfoRow(infoPanel, "Trạng thái:", 4, gbc);
        statusValueLabel = addValueLabel(infoPanel, "", 4, gbc);
        
        // Row 6: Tên đăng nhập
        addInvoiceInfoRow(infoPanel, "Tên đăng nhập:", 5, gbc);
        loginValueLabel = addValueLabel(infoPanel, "", 5, gbc);
        
        // Row 7: Khuyến mãi
        addInvoiceInfoRow(infoPanel, "Khuyến mãi:", 6, gbc);
        kmValueLabel = addValueLabel(infoPanel, "", 6, gbc);
        
        centerPanel.add(infoPanel);
        
        // Small gap between panels
        centerPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        
        // Summary Panel - Phần tổng kết ở dưới (with smaller padding)
        JPanel summaryPanel = new JPanel(new GridBagLayout());
        summaryPanel.setBackground(INFO_BG_COLOR);
        summaryPanel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        
        GridBagConstraints gbcSum = new GridBagConstraints();
        gbcSum.fill = GridBagConstraints.HORIZONTAL;
        gbcSum.insets = new Insets(3, 5, 3, 5);
        
        // Số tiền nạp
        addSummaryRow(summaryPanel, "Số tiền nạp:", 0, gbcSum);
        totalValueLabel = addSummaryValue(summaryPanel, "", 0, gbcSum);
        
        // Giá được giảm
        addSummaryRow(summaryPanel, "Tiền được giảm:", 1, gbcSum);
        discountValueLabel = addSummaryValue(summaryPanel, "", 1, gbcSum);
        
        // Tổng thanh toán
        addSummaryRow(summaryPanel, "Tổng thanh toán:", 2, gbcSum);
        finalPayValueLabel = addSummaryValue(summaryPanel, "", 2, gbcSum);
        
        // Phương thức thanh toán
        addSummaryRow(summaryPanel, "PT thanh toán:", 3, gbcSum);
        ptttValueLabel = addSummaryValue(summaryPanel, "", 3, gbcSum);
        
        centerPanel.add(summaryPanel);
        
        contentPane.add(centerPanel, BorderLayout.CENTER);
    }
    
    private void addInvoiceInfoRow(JPanel panel, String labelText, int row, GridBagConstraints gbc) {
        JLabel label = new JLabel(labelText);
        label.setFont(new Font("Arial", Font.BOLD, 13));
        label.setForeground(TEXT_COLOR);
        
        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.weightx = 0.3;
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(label, gbc);
    }
    
    private JLabel addValueLabel(JPanel panel, String value, int row, GridBagConstraints gbc) {
        JLabel label = new JLabel(value);
        label.setFont(new Font("Arial", Font.PLAIN, 13));
        label.setForeground(TEXT_COLOR);
        
        gbc.gridx = 1;
        gbc.gridy = row;
        gbc.weightx = 0.7;
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(label, gbc);
        
        return label;
    }
    
    private void addSummaryRow(JPanel panel, String labelText, int row, GridBagConstraints gbc) {
        JLabel label = new JLabel(labelText);
        label.setFont(new Font("Arial", Font.BOLD, 13));
        label.setForeground(TEXT_COLOR);
        label.setHorizontalAlignment(JLabel.RIGHT);
        
        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.weightx = 0.7;
        gbc.anchor = GridBagConstraints.EAST;
        panel.add(label, gbc);
    }
    
    private JLabel addSummaryValue(JPanel panel, String value, int row, GridBagConstraints gbc) {
        JLabel label = new JLabel(value);
        label.setFont(new Font("Arial", Font.PLAIN, 13));
        label.setForeground(TEXT_COLOR);
        label.setHorizontalAlignment(JLabel.RIGHT);
        
        if (row == 2) { // Tổng thanh toán - in đậm
            label.setFont(new Font("Arial", Font.BOLD, 13));
        }
        
        gbc.gridx = 1;
        gbc.gridy = row;
        gbc.weightx = 0.3;
        gbc.anchor = GridBagConstraints.EAST;
        panel.add(label, gbc);
        
        return label;
    }
    
    private void loadChiTietHoaDon() {
        try {
            System.out.println("Bắt đầu tải chi tiết hóa đơn nạp tiền: " + maHD);
            
            // Lấy thông tin hóa đơn từ controller
            HoaDon hoaDon = hoaDonController.layThongTinHoaDon(maHD);
            if (hoaDon == null) {
                throw new Exception("Không tìm thấy hóa đơn với mã: " + maHD);
            }
            
            NhanVien nv = hoaDon.getMaNV() == null ? null : nhanVienController.timNhanVienTheoMa(String.valueOf(hoaDon.getMaNV()));
            
            // Cập nhật các label với thông tin hóa đơn
            idValueLabel.setText(hoaDon.getMaHD() != null ? hoaDon.getMaHD() : "");
            dateValueLabel.setText(hoaDon.getNgayLap() != null ? 
                    new SimpleDateFormat("dd/MM/yyyy").format(hoaDon.getNgayLap()) : "");
            sidValueLabel.setText(nv != null ? String.valueOf(nv.getMaNV()) : "");
            snameValueLabel.setText(nv != null ? nv.getHoTen() : "");
            statusValueLabel.setText(hoaDon.getTrangThai() != null ? hoaDon.getTrangThai() : "");
            loginValueLabel.setText(hoaDon.getTenDangNhap() != null ? hoaDon.getTenDangNhap() : "");
            kmValueLabel.setText(hoaDon.getMaKM() == null || hoaDon.getMaKM().isEmpty() ? "Không có" : hoaDon.getMaKM());
            
            // Xử lý thông tin tài chính
            int soTienNap = hoaDon.getSoTienNap() != null ? hoaDon.getSoTienNap() : 0;
            int tienGiam = hoaDon.getTienDuocGiam() != null ? hoaDon.getTienDuocGiam() : 0;
            int tongTien = soTienNap;
            int thanhToanCuoi = tongTien - tienGiam;
            
            totalValueLabel.setText(formatPrice(soTienNap));
            discountValueLabel.setText(formatPrice(tienGiam));
            finalPayValueLabel.setText(formatPrice(thanhToanCuoi));
            ptttValueLabel.setText(hoaDon.getPhuongThucTT() == null || hoaDon.getPhuongThucTT().isEmpty() ? 
                        "Không xác định" : hoaDon.getPhuongThucTT());
            
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Lỗi khi tải thông tin hóa đơn nạp tiền: " + e.getMessage());
            
            JOptionPane.showMessageDialog(this, 
                "Lỗi khi tải thông tin hóa đơn: " + e.getMessage(), 
                "Lỗi", 
                JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private String formatPrice(Integer price) {
        DecimalFormat formatter = new DecimalFormat("#,###");
        return formatter.format(price) + " VNĐ";
    }
} 