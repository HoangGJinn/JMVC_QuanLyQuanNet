package com.example.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MenuForm extends JFrame {
    private JPanel contentPane;
    private JPanel sidebarPanel;
    private JPanel mainContentPanel;
    private JLabel lblTitle;
    
    // Menu buttons
    private JPanel btnThongKe;
    private JPanel btnMayTinh;
    private JPanel btnNhanVien;
    private JPanel btnTaiKhoan;
    private JPanel btnDichVu;
    private JPanel btnKhuyenMai;
    private JPanel btnHoaDon;
    
    // Active panel highlighting
    private JPanel activePanel;
    private Color defaultBgColor = new Color(40, 44, 68);
    private Color activeBgColor = new Color(60, 64, 88);
    private Color hoverBgColor = new Color(50, 54, 78);
    
    public MenuForm() {
        setTitle("Quản Lý Quán Net");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(1300, 700);
        getContentPane().setBackground(new Color(40, 44, 68));
        
        initUI();
        
        setLocationRelativeTo(null);
        setVisible(true);
    }
    
    private void initUI() {
        contentPane = new JPanel(new BorderLayout());
        contentPane.setBackground(new Color(40, 44, 68));
        
        // Create header
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(new Color(30, 34, 58));
        headerPanel.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));
        
        lblTitle = new JLabel("Danh Mục");
        lblTitle.setFont(new Font("Sans-serif", Font.BOLD, 24));
        lblTitle.setForeground(Color.WHITE);
        
        headerPanel.add(lblTitle, BorderLayout.WEST);
        
        // Create sidebar
        sidebarPanel = new JPanel();
        sidebarPanel.setLayout(new BoxLayout(sidebarPanel, BoxLayout.Y_AXIS));
        sidebarPanel.setBackground(defaultBgColor);
        sidebarPanel.setPreferredSize(new Dimension(200, getHeight()));
        sidebarPanel.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 1, new Color(60, 64, 88)));
        
        // Create menu items
        btnThongKe = createMenuButton("Thống Kê", "/icons/stats_icon.png");
        btnMayTinh = createMenuButton("Máy Tính", "/icons/computer_icon.png");
        btnNhanVien = createMenuButton("Nhân Viên", "/icons/employee_icon.png");
        btnTaiKhoan = createMenuButton("Tài Khoản", "/icons/account_icon.png");
        btnDichVu = createMenuButton("Dịch Vụ", "/icons/service_icon.png");
        btnKhuyenMai = createMenuButton("Khuyến Mãi", "/icons/promotion_icon.png");
        btnHoaDon = createMenuButton("Hóa Đơn", "/icons/invoice_icon.png");
        
        // Add all menu items to sidebar
        sidebarPanel.add(btnThongKe);
        sidebarPanel.add(btnMayTinh);
        sidebarPanel.add(btnNhanVien);
        sidebarPanel.add(btnTaiKhoan);
        sidebarPanel.add(btnDichVu);
        sidebarPanel.add(btnKhuyenMai);
        sidebarPanel.add(btnHoaDon);
        
        // Add filler to push menu items to the top
        sidebarPanel.add(Box.createVerticalGlue());
        
        // Main content panel (will be replaced with child forms)
        mainContentPanel = new JPanel(new BorderLayout());
        mainContentPanel.setBackground(new Color(240, 240, 240));
        
        // Add welcome message to main content
        JLabel welcomeLabel = new JLabel("Chọn một chức năng từ menu bên trái", JLabel.CENTER);
        welcomeLabel.setFont(new Font("Sans-serif", Font.BOLD, 24));
        welcomeLabel.setForeground(new Color(100, 100, 100));
        mainContentPanel.add(welcomeLabel, BorderLayout.CENTER);
        
        // Add components to content pane
        contentPane.add(headerPanel, BorderLayout.NORTH);
        contentPane.add(sidebarPanel, BorderLayout.WEST);
        contentPane.add(mainContentPanel, BorderLayout.CENTER);
        
        // Set content pane
        setContentPane(contentPane);
        
        // Set default active panel
        setActivePanel(btnThongKe);
        
        // Add action listeners
        addMenuListeners();
    }
    
    private JPanel createMenuButton(String text, String iconPath) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(defaultBgColor);
        panel.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));
        panel.setMaximumSize(new Dimension(200, 60));
        
        JLabel label = new JLabel(text);
        label.setFont(new Font("Sans-serif", Font.BOLD, 16));
        label.setForeground(Color.WHITE);
        
        // Try to load icon if available (for now we're not using icons)
        // if icon loading fails, just show text
        /*
        try {
            ImageIcon icon = new ImageIcon(getClass().getResource(iconPath));
            label.setIcon(icon);
            label.setIconTextGap(10);
        } catch (Exception e) {
            // Icon not found, continue without it
        }
        */
        
        panel.add(label, BorderLayout.CENTER);
        
        // Add hover effect
        panel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                if (panel != activePanel) {
                    panel.setBackground(hoverBgColor);
                }
            }
            
            @Override
            public void mouseExited(MouseEvent e) {
                if (panel != activePanel) {
                    panel.setBackground(defaultBgColor);
                }
            }
        });
        
        return panel;
    }
    
    private void setActivePanel(JPanel panel) {
        // Reset previous active panel
        if (activePanel != null) {
            activePanel.setBackground(defaultBgColor);
        }
        
        // Set new active panel
        activePanel = panel;
        activePanel.setBackground(activeBgColor);
    }
    
    private void addMenuListeners() {
        btnThongKe.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                setActivePanel(btnThongKe);
                lblTitle.setText("Thống Kê");
                // Add ThongKe functionality here when implemented
                showWelcomeMessage("Chức năng Thống Kê đang được phát triển");
            }
        });
        
        btnMayTinh.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                setActivePanel(btnMayTinh);
                lblTitle.setText("Máy Tính");
                openChildForm(new MayTinhForm(true));
            }
        });
        
        btnNhanVien.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                setActivePanel(btnNhanVien);
                lblTitle.setText("Nhân Viên");
                openChildForm(new NhanVienForm(true));
            }
        });
        
        btnTaiKhoan.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                setActivePanel(btnTaiKhoan);
                lblTitle.setText("Tài Khoản");
                openChildForm(new TaiKhoanForm(true));
            }
        });
        
        btnDichVu.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                setActivePanel(btnDichVu);
                lblTitle.setText("Dịch Vụ");
                openChildForm(new DichVuForm());
            }
        });
        
        btnKhuyenMai.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                setActivePanel(btnKhuyenMai);
                lblTitle.setText("Khuyến Mãi");
                //openChildForm(new KhuyenMaiForm());
            }
        });
        
        btnHoaDon.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                setActivePanel(btnHoaDon);
                lblTitle.setText("Hóa Đơn");
                //openChildForm(new HoaDonForm());
            }
        });
    }
    
    private void openChildForm(JFrame childForm) {
        // Clear main content panel
        mainContentPanel.removeAll();
        
        try {
            // Handle different form types for embedding
            if (childForm instanceof MayTinhForm) {
                // Don't create a new instance, just get the content
                childForm.setVisible(false); // Ensure the original form is not visible
                Container content = ((MayTinhForm)childForm).getContent();
                mainContentPanel.add(content, BorderLayout.CENTER);
            }
            else if (childForm instanceof TaiKhoanForm) {
                // Don't create a new instance, just get the content
                childForm.setVisible(false); // Ensure the original form is not visible
                Container content = ((TaiKhoanForm)childForm).getContent();
                mainContentPanel.add(content, BorderLayout.CENTER);
            }
            else if (childForm instanceof NhanVienForm) {
                // Don't create a new instance, just get the content
                childForm.setVisible(false); // Ensure the original form is not visible
                Container content = ((NhanVienForm)childForm).getContent();
                mainContentPanel.add(content, BorderLayout.CENTER);
            }
            else {
                // For other forms, show under construction message
                // No need to create and show the original form at all
                JPanel formContentPanel = new JPanel(new BorderLayout());
                formContentPanel.setBackground(new Color(240, 240, 240));
                
                // Show "under construction" message
                JLabel messageLabel = new JLabel("Chức năng đang được phát triển", JLabel.CENTER);
                messageLabel.setFont(new Font("Sans-serif", Font.BOLD, 18));
                messageLabel.setForeground(new Color(100, 100, 100));
                formContentPanel.add(messageLabel, BorderLayout.CENTER);
                
                mainContentPanel.add(formContentPanel, BorderLayout.CENTER);
            }
            
            // Refresh the display
            mainContentPanel.revalidate();
            mainContentPanel.repaint();
            
        } catch (Exception e) {
            e.printStackTrace();
            showWelcomeMessage("Không thể mở form: " + e.getMessage());
        }
    }
    
    private void showWelcomeMessage(String message) {
        // Clear main content panel
        mainContentPanel.removeAll();
        
        // Add welcome message
        JLabel welcomeLabel = new JLabel(message, JLabel.CENTER);
        welcomeLabel.setFont(new Font("Sans-serif", Font.BOLD, 18));
        welcomeLabel.setForeground(new Color(100, 100, 100));
        mainContentPanel.add(welcomeLabel, BorderLayout.CENTER);
        
        // Refresh the display
        mainContentPanel.revalidate();
        mainContentPanel.repaint();
    }
    
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            // Fix button look and feel issues on Windows
            UIManager.put("Button.background", new Color(116, 103, 239));
            UIManager.put("Button.foreground", Color.WHITE);
            UIManager.put("Button.focus", new Color(116, 103, 239));
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        SwingUtilities.invokeLater(() -> new MenuForm());
    }
}
