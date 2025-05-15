package com.example.view;

import com.example.util.UIStyler;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;

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
    private Color defaultBgColor = UIStyler.PRIMARY_DARK;
    private Color activeBgColor = UIStyler.ACCENT_COLOR;
    private Color hoverBgColor = UIStyler.HOVER_COLOR;
    private Color textColor = UIStyler.TEXT_COLOR;
    private Color accentColor = UIStyler.ACCENT_COLOR;
    private Color headerBgColor = UIStyler.HEADER_DARK;

    public MenuForm() {
        setTitle("Quản Lý Quán Net");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(1300, 700);
        setMinimumSize(new Dimension(1000, 600));
        getContentPane().setBackground(defaultBgColor);

        initUI();

        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void initUI() {
        contentPane = new JPanel(new BorderLayout());
        contentPane.setBackground(defaultBgColor);

        // Tạo header
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(headerBgColor);
        headerPanel.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));

        lblTitle = new JLabel("Danh Mục");
        UIStyler.styleTitleLabel(lblTitle);
        lblTitle.setIcon(createColorIcon(accentColor, 15, 15));
        lblTitle.setIconTextGap(10);

        headerPanel.add(lblTitle, BorderLayout.WEST);

        // Tạo sidebar
        sidebarPanel = new JPanel();
        sidebarPanel.setLayout(new BoxLayout(sidebarPanel, BoxLayout.Y_AXIS));
        sidebarPanel.setBackground(defaultBgColor);
        sidebarPanel.setPreferredSize(new Dimension(220, getHeight()));
        sidebarPanel.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 1, new Color(40, 44, 68)));

        // Panel logo ở đầu sidebar
        JPanel logoPanel = new JPanel(new BorderLayout());
        logoPanel.setBackground(defaultBgColor);
        logoPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 30, 20));
        logoPanel.setMaximumSize(new Dimension(220, 80));

        JLabel logoLabel = new JLabel("QUÁN NET");
        // Use custom font for logo but keep consistent with UIStyler
        logoLabel.setFont(new Font(UIStyler.TITLE_FONT.getFamily(), Font.BOLD, 22));
        logoLabel.setForeground(accentColor);
        logoLabel.setHorizontalAlignment(SwingConstants.CENTER);
        logoPanel.add(logoLabel, BorderLayout.CENTER);

        sidebarPanel.add(logoPanel);

        // Tạo các nút menu với icon
        btnThongKe = createMenuButton("Thống Kê", createIconFromUnicode('\uD83D', '\uDCCA')); // 📊
        btnMayTinh = createMenuButton("Máy Tính", createIconFromUnicode('\uD83D', '\uDCBB')); // 💻
        btnNhanVien = createMenuButton("Nhân Viên", createIconFromUnicode('\uD83D', '\uDC64')); // 👤
        btnTaiKhoan = createMenuButton("Tài Khoản", createIconFromUnicode('\uD83D', '\uDCB3')); // 💳
        btnDichVu = createMenuButton("Dịch Vụ", createIconFromUnicode('\uD83C', '\uDF7A')); // 🍺
        btnKhuyenMai = createMenuButton("Khuyến Mãi", createIconFromUnicode('\uD83C', '\uDF81')); // 🎁
        btnHoaDon = createMenuButton("Hóa Đơn", createIconFromUnicode('\uD83D', '\uDCDC')); // 📜

        // Thêm các nút vào sidebar
        sidebarPanel.add(Box.createVerticalStrut(10));
        sidebarPanel.add(btnThongKe);
        sidebarPanel.add(Box.createVerticalStrut(5));
        sidebarPanel.add(btnMayTinh);
        sidebarPanel.add(Box.createVerticalStrut(5));
        sidebarPanel.add(btnNhanVien);
        sidebarPanel.add(Box.createVerticalStrut(5));
        sidebarPanel.add(btnTaiKhoan);
        sidebarPanel.add(Box.createVerticalStrut(5));
        sidebarPanel.add(btnDichVu);
        sidebarPanel.add(Box.createVerticalStrut(5));
        sidebarPanel.add(btnKhuyenMai);
        sidebarPanel.add(Box.createVerticalStrut(5));
        sidebarPanel.add(btnHoaDon);

        // Thêm filler để đẩy các nút menu lên trên
        sidebarPanel.add(Box.createVerticalGlue());

        // Thêm thông tin phiên bản ở dưới sidebar
        JPanel versionPanel = new JPanel(new BorderLayout());
        versionPanel.setBackground(defaultBgColor);
        versionPanel.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));
        versionPanel.setMaximumSize(new Dimension(220, 50));

        JLabel versionLabel = new JLabel("Giáp, Huy, Danh");
        // Use custom font size for version label but keep consistent with UIStyler
        versionLabel.setFont(new Font(UIStyler.LABEL_FONT.getFamily(), Font.PLAIN, 12));
        versionLabel.setForeground(UIStyler.TEXT_COLOR_SECONDARY);
        versionLabel.setHorizontalAlignment(SwingConstants.CENTER);
        versionPanel.add(versionLabel, BorderLayout.CENTER);

        sidebarPanel.add(versionPanel);

        // Tạo panel nội dung chính
        mainContentPanel = new JPanel(new BorderLayout());
        mainContentPanel.setBackground(new Color(245, 246, 250));

        // Thêm thông báo welcome
        JPanel welcomePanel = new JPanel(new BorderLayout());
        welcomePanel.setBackground(new Color(245, 246, 250));
        welcomePanel.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));

        JLabel welcomeLabel = new JLabel("QUẢN LÝ QUÁN NET", JLabel.CENTER);
        // Use custom font size for welcome label but keep consistent with UIStyler
        welcomeLabel.setFont(new Font(UIStyler.TITLE_FONT.getFamily(), Font.BOLD, 32));
        welcomeLabel.setForeground(new Color(80, 80, 100));

        JLabel subLabel = new JLabel("Chọn một chức năng từ menu bên trái", JLabel.CENTER);
        // Use custom font size for sub label but keep consistent with UIStyler
        subLabel.setFont(new Font(UIStyler.LABEL_FONT.getFamily(), Font.PLAIN, 18));
        subLabel.setForeground(new Color(120, 120, 140));

        welcomePanel.add(welcomeLabel, BorderLayout.CENTER);
        welcomePanel.add(subLabel, BorderLayout.SOUTH);

        mainContentPanel.add(welcomePanel, BorderLayout.CENTER);

        // Thêm các thành phần vào contentPane
        contentPane.add(headerPanel, BorderLayout.NORTH);
        contentPane.add(sidebarPanel, BorderLayout.WEST);
        contentPane.add(mainContentPanel, BorderLayout.CENTER);

        setContentPane(contentPane);

        // Thiết lập panel hiện tại
        setActivePanel(btnThongKe);

        // Thêm sự kiện
        addMenuListeners();
    }

    private Icon createIconFromUnicode(char high, char low) {
        String text = new String(new char[]{high, low});
        JLabel label = new JLabel(text);
        label.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 22));
        label.setForeground(textColor);
        label.setSize(30, 30);
        label.setPreferredSize(new Dimension(30, 30));

        BufferedIcon icon = new BufferedIcon(30, 30);
        icon.paintComponent(label);
        return icon;
    }

    private Icon createColorIcon(Color color, int width, int height) {
        BufferedIcon icon = new BufferedIcon(width, height);
        Graphics2D g2 = icon.createGraphics();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(color);
        g2.fillRoundRect(0, 0, width, height, 5, 5);
        g2.dispose();
        return icon;
    }

    private class BufferedIcon implements Icon {
        private final BufferedImage image;

        public BufferedIcon(int width, int height) {
            image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        }

        public Graphics2D createGraphics() {
            return image.createGraphics();
        }

        public void paintComponent(JComponent component) {
            component.paint(createGraphics());
        }

        @Override
        public void paintIcon(Component c, Graphics g, int x, int y) {
            g.drawImage(image, x, y, c);
        }

        @Override
        public int getIconWidth() {
            return image.getWidth();
        }

        @Override
        public int getIconHeight() {
            return image.getHeight();
        }
    }

    private JPanel createMenuButton(String text, Icon icon) {
        // Use UIStyler to create a rounded panel
        JPanel panel = UIStyler.createRoundedPanel(UIStyler.PANEL_RADIUS);
        panel.setLayout(new BorderLayout());
        panel.setBackground(defaultBgColor);
        panel.setBorder(BorderFactory.createEmptyBorder(12, 15, 12, 15));
        panel.setMaximumSize(new Dimension(210, 50));

        JLabel label = new JLabel(text);
        label.setFont(new Font("Arial", Font.BOLD, 16));
        label.setForeground(textColor);

        if (icon != null) {
            label.setIcon(icon);
            label.setIconTextGap(15);
        }

        panel.add(label, BorderLayout.CENTER);

        // Thêm hiệu ứng hover
        panel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                if (panel != activePanel) {
                    panel.setBackground(hoverBgColor);
                }
                setCursor(new Cursor(Cursor.HAND_CURSOR));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                if (panel != activePanel) {
                    panel.setBackground(defaultBgColor);
                }
                setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            }
        });

        return panel;
    }

    private void setActivePanel(JPanel panel) {
        // Reset panel hiện tại
        if (activePanel != null) {
            activePanel.setBackground(defaultBgColor);
        }

        // Thiết lập panel mới
        activePanel = panel;
        activePanel.setBackground(activeBgColor);
    }

    private void addMenuListeners() {
        btnThongKe.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                setActivePanel(btnThongKe);
                lblTitle.setText("Thống Kê");
                //showWelcomeMessage("Chức năng Thống Kê đang được phát triển");
                openChildForm(new ThongKeForm(true));

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
                openChildForm(new KhuyenMaiForm(true));
            }
        });

        btnHoaDon.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                setActivePanel(btnHoaDon);
                lblTitle.setText("Hóa Đơn");
                openChildForm(new HoaDonForm(true));
            }
        });
    }

    private void openChildForm(JFrame childForm) {
        // Xóa nội dung hiện tại
        mainContentPanel.removeAll();

        try {
            if (childForm instanceof MayTinhForm) {
                childForm.setVisible(false);
                Container content = ((MayTinhForm)childForm).getContent();
                mainContentPanel.add(content, BorderLayout.CENTER);
            }
            else if (childForm instanceof TaiKhoanForm) {
                childForm.setVisible(false);
                Container content = ((TaiKhoanForm)childForm).getContent();
                mainContentPanel.add(content, BorderLayout.CENTER);
            }
            else if (childForm instanceof NhanVienForm) {
                childForm.setVisible(false);
                Container content = ((NhanVienForm)childForm).getContent();
                mainContentPanel.add(content, BorderLayout.CENTER);
            }
            else if (childForm instanceof KhuyenMaiForm) {
                childForm.setVisible(false);
                Container content = ((KhuyenMaiForm)childForm).getContent();
                mainContentPanel.add(content, BorderLayout.CENTER);
            }
            else if (childForm instanceof HoaDonForm) {
                childForm.setVisible(false);
                Container content = ((HoaDonForm)childForm).getContent();
                mainContentPanel.add(content, BorderLayout.CENTER);
            }
            else if (childForm instanceof DichVuForm) {
                // Get the content from DichVuForm
                childForm.setVisible(false);
                // Use the getContent method we added to DichVuForm
                Container content = ((DichVuForm)childForm).getContent();
                mainContentPanel.add(content, BorderLayout.CENTER);
            }
            else if (childForm instanceof ThongKeForm) {
                childForm.setVisible(false);
                Container content = ((ThongKeForm)childForm).getContent();
                mainContentPanel.add(content, BorderLayout.CENTER);
            }
            else {
                showUnderConstructionMessage();
            }

            mainContentPanel.revalidate();
            mainContentPanel.repaint();

        } catch (Exception e) {
            e.printStackTrace();
            showWelcomeMessage("Không thể mở form: " + e.getMessage());
        }
    }

    private void showUnderConstructionMessage() {
        JPanel formContentPanel = new JPanel(new BorderLayout());
        formContentPanel.setBackground(UIStyler.PANEL_BACKGROUND);
        formContentPanel.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));

        JPanel centerPanel = new JPanel(new GridBagLayout());
        centerPanel.setBackground(UIStyler.PANEL_BACKGROUND);

        JPanel messagePanel = new JPanel(new BorderLayout(0, 20));
        messagePanel.setBackground(UIStyler.PANEL_BACKGROUND);

        JLabel iconLabel = new JLabel(createIconFromUnicode('\uD83D', '\uDEA7')); // 🚧
        iconLabel.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 64));
        iconLabel.setHorizontalAlignment(SwingConstants.CENTER);

        JLabel messageLabel = new JLabel("Chức năng đang được phát triển", JLabel.CENTER);
        // Use UIStyler font for message label
        messageLabel.setFont(UIStyler.TITLE_FONT);
        messageLabel.setForeground(new Color(80, 80, 100));

        messagePanel.add(iconLabel, BorderLayout.CENTER);
        messagePanel.add(messageLabel, BorderLayout.SOUTH);

        centerPanel.add(messagePanel);
        formContentPanel.add(centerPanel, BorderLayout.CENTER);

        mainContentPanel.add(formContentPanel, BorderLayout.CENTER);
    }

    private void showWelcomeMessage(String message) {
        mainContentPanel.removeAll();

        JPanel welcomePanel = new JPanel(new BorderLayout());
        welcomePanel.setBackground(UIStyler.PANEL_BACKGROUND);
        welcomePanel.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));

        JPanel centerPanel = new JPanel(new GridBagLayout());
        centerPanel.setBackground(UIStyler.PANEL_BACKGROUND);

        JPanel messagePanel = new JPanel(new BorderLayout(0, 20));
        messagePanel.setBackground(UIStyler.PANEL_BACKGROUND);

        JLabel iconLabel = new JLabel(createIconFromUnicode('\u2139', '\uFE0F')); // ℹ️
        iconLabel.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 64));
        iconLabel.setHorizontalAlignment(SwingConstants.CENTER);

        JLabel messageLabel = new JLabel(message, JLabel.CENTER);
        // Use UIStyler font for message label
        messageLabel.setFont(UIStyler.TITLE_FONT);
        messageLabel.setForeground(new Color(80, 80, 100));

        messagePanel.add(iconLabel, BorderLayout.CENTER);
        messagePanel.add(messageLabel, BorderLayout.SOUTH);

        centerPanel.add(messagePanel);
        welcomePanel.add(centerPanel, BorderLayout.CENTER);

        mainContentPanel.add(welcomePanel, BorderLayout.CENTER);
        mainContentPanel.revalidate();
        mainContentPanel.repaint();
    }

    public static void main(String[] args) {
        // Apply global UI styling
        UIStyler.applyGlobalStyles();

        SwingUtilities.invokeLater(() -> new MenuForm());
    }
}
