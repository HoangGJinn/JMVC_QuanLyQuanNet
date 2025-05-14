package com.example.view;

import com.example.controller.HoaDonController;
import com.example.model.HoaDon;
import com.example.model.ChiTietDV;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.basic.BasicButtonUI;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.RoundRectangle2D;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class HoaDonForm extends JFrame {
    private JPanel contentPane;
    private JPanel filterPanel;
    private JPanel tablePanel;
    private JTable table;
    private DefaultTableModel tableModel;
    private JRadioButton filterAllRadio;
    private JRadioButton filterNapTienRadio;
    private JRadioButton filterDichVuRadio;
    private JTextField searchBox;
    private JButton searchButton;
    private JButton refreshButton;
    private JSpinner beginDatePicker;
    private JSpinner endDatePicker;
    private boolean isRefreshing = false;
    
    // Controller
    private HoaDonController hoaDonController;

    public HoaDonForm() {
        setTitle("Quản Lý Hóa Đơn");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(1000, 653);
        setResizable(false);
        setLocationRelativeTo(null);
        
        // Initialize controller
        hoaDonController = new HoaDonController();

        initComponents();
        setupEventListeners();
        loadHoaDonData();
    }

    private void initComponents() {
        contentPane = new JPanel();
        contentPane.setLayout(new BorderLayout(0, 0));
        contentPane.setBackground(new Color(37, 42, 64));
        setContentPane(contentPane);

        // Create a white panel for filters with rounded corners
        filterPanel = new RoundedPanel(10);
        filterPanel.setBackground(Color.WHITE);
        filterPanel.setLayout(null); // Use absolute positioning like the C# example
        filterPanel.setPreferredSize(new Dimension(1000, 71));
        
        // Add padding around filter panel
        JPanel filterPanelWrapper = new JPanel(new BorderLayout());
        filterPanelWrapper.setBackground(new Color(37, 42, 64));
        filterPanelWrapper.setBorder(BorderFactory.createEmptyBorder(10, 10, 5, 10));
        filterPanelWrapper.add(filterPanel, BorderLayout.CENTER);
        contentPane.add(filterPanelWrapper, BorderLayout.NORTH);

        // Filter components with absolute positioning - adjusted for smaller form
        JLabel lblFilter = new JLabel("Bộ lọc:");
        lblFilter.setFont(new Font("Arial", Font.PLAIN, 14));
        lblFilter.setBounds(20, 25, 55, 20);
        filterPanel.add(lblFilter);

        JLabel lblDate = new JLabel("Ngày đặt:");
        lblDate.setFont(new Font("Arial", Font.PLAIN, 14));
        lblDate.setBounds(80, 25, 75, 20);
        filterPanel.add(lblDate);

        // Date Pickers - adjusted width
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -30); // Default to 30 days ago like the C# form
        Date oneMonthAgo = cal.getTime();

        SpinnerDateModel startModel = new SpinnerDateModel(oneMonthAgo, null, null, Calendar.DAY_OF_MONTH);
        beginDatePicker = new JSpinner(startModel);
        JSpinner.DateEditor dateEditorStart = new JSpinner.DateEditor(beginDatePicker, "dd/MM/yyyy");
        beginDatePicker.setEditor(dateEditorStart);
        beginDatePicker.setBounds(155, 18, 120, 35);
        customizeSpinner(beginDatePicker);
        filterPanel.add(beginDatePicker);

        JLabel lblDash = new JLabel("-");
        lblDash.setFont(new Font("Arial", Font.PLAIN, 14));
        lblDash.setBounds(280, 25, 10, 20);
        filterPanel.add(lblDash);

        SpinnerDateModel endModel = new SpinnerDateModel(new Date(), null, null, Calendar.DAY_OF_MONTH);
        endDatePicker = new JSpinner(endModel);
        JSpinner.DateEditor dateEditorEnd = new JSpinner.DateEditor(endDatePicker, "dd/MM/yyyy");
        endDatePicker.setEditor(dateEditorEnd);
        endDatePicker.setBounds(295, 18, 120, 35);
        customizeSpinner(endDatePicker);
        filterPanel.add(endDatePicker);

        // Radio Buttons - compressed
        filterAllRadio = new JRadioButton("Tất cả");
        filterAllRadio.setFont(new Font("Arial", Font.PLAIN, 14));
        filterAllRadio.setSelected(true);
        filterAllRadio.setBackground(Color.WHITE);
        filterAllRadio.setBounds(430, 25, 70, 27);

        filterNapTienRadio = new JRadioButton("Nạp tiền");
        filterNapTienRadio.setFont(new Font("Arial", Font.PLAIN, 14));
        filterNapTienRadio.setBackground(Color.WHITE);
        filterNapTienRadio.setBounds(500, 25, 85, 27);

        filterDichVuRadio = new JRadioButton("Dịch vụ");
        filterDichVuRadio.setFont(new Font("Arial", Font.PLAIN, 14));
        filterDichVuRadio.setBackground(Color.WHITE);
        filterDichVuRadio.setBounds(585, 25, 80, 27);

        ButtonGroup filterGroup = new ButtonGroup();
        filterGroup.add(filterAllRadio);
        filterGroup.add(filterNapTienRadio);
        filterGroup.add(filterDichVuRadio);

        filterPanel.add(filterAllRadio);
        filterPanel.add(filterNapTienRadio);
        filterPanel.add(filterDichVuRadio);

        // Refresh Button with purple color and rounded corners
        refreshButton = new JButton("Làm mới");
        refreshButton.setFont(new Font("Arial", Font.BOLD, 14));
        refreshButton.setForeground(Color.WHITE);
        refreshButton.setBackground(new Color(123, 104, 238)); // MediumSlateBlue
        refreshButton.setBounds(666, 18, 100, 40);
        refreshButton.setBorderPainted(false);
        refreshButton.setFocusPainted(false);
        refreshButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        // Add rounded corners to the refresh button
        refreshButton.setUI(new BasicButtonUI() {
            @Override
            public void paint(Graphics g, JComponent c) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(c.getBackground());
                g2.fillRoundRect(0, 0, c.getWidth(), c.getHeight(), 20, 20);
                super.paint(g2, c);
                g2.dispose();
            }
        });
        
        filterPanel.add(refreshButton);

        // Search Box with border and place on the right - adjusted position
        searchBox = new JTextField("Tìm kiếm");
        searchBox.setFont(new Font("Arial", Font.PLAIN, 14));
        searchBox.setBounds(780, 20, 150, 35);
        searchBox.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(123, 104, 238), 2, true),
                BorderFactory.createEmptyBorder(0, 10, 0, 35)));
        searchBox.setForeground(Color.GRAY);
        filterPanel.add(searchBox);
        
        // Search Button (magnifying glass) - adjusted position
        searchButton = new JButton("🔍");
        searchButton.setBackground(Color.WHITE);
        searchButton.setBorder(BorderFactory.createEmptyBorder());
        searchButton.setBounds(930, 23, 25, 25);
        searchButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        filterPanel.add(searchButton);

        // Main container for table with no extra padding
        JPanel tableContainer = new JPanel(new BorderLayout(0, 0));
        tableContainer.setBackground(new Color(37, 42, 64));
        tableContainer.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));
        contentPane.add(tableContainer, BorderLayout.CENTER);
        
        // Table Panel - Main data display panel with rounded corners
        tablePanel = new RoundedPanel(10);
        tablePanel.setLayout(new BorderLayout(0, 0));
        tablePanel.setBackground(Color.WHITE);
        tableContainer.add(tablePanel, BorderLayout.CENTER);

        // Create the header panel with turquoise background - no extra space
        JPanel headerPanel = new JPanel(new GridLayout(1, 8, 0, 0));
        headerPanel.setBackground(new Color(64, 224, 208)); // Turquoise
        headerPanel.setPreferredSize(new Dimension(980, 40));
        headerPanel.setBorder(new EmptyBorder(0, 10, 0, 10));
        tablePanel.add(headerPanel, BorderLayout.NORTH);

        // Add header labels
        String[] headers = {"Mã đơn", "Mã NV", "Tổng tiền", "Phương thức TT", "Trạng thái", "Ngày lập", "Loại", "Hành động"};
        for (String header : headers) {
            JLabel lbl = new JLabel(header);
            lbl.setHorizontalAlignment(SwingConstants.CENTER);
            lbl.setFont(new Font("Arial", Font.BOLD, 13));
            headerPanel.add(lbl);
        }

        // Create table model
        tableModel = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        for (String header : headers) {
            tableModel.addColumn(header);
        }

        // Create table with custom cell renderer
        table = new JTable(tableModel);
        table.setShowGrid(false);
        table.setIntercellSpacing(new Dimension(0, 0)); // No spacing between cells
        table.setRowHeight(32); // Reduced row height from 40 to match screenshot
        table.getTableHeader().setVisible(false);
        table.setFillsViewportHeight(true);
        table.setBackground(Color.WHITE);
        table.setDefaultRenderer(Object.class, new InvoiceTableCellRenderer());
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        // Put table in scroll pane
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.getViewport().setBackground(Color.WHITE);
        scrollPane.getViewport().setBorder(null); // Remove viewport border
        tablePanel.add(scrollPane, BorderLayout.CENTER);
        
        // Add placeholder text for the search box
        searchBox.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(java.awt.event.FocusEvent evt) {
                if (searchBox.getText().equals("Tìm kiếm")) {
                    searchBox.setText("");
                    searchBox.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(java.awt.event.FocusEvent evt) {
                if (searchBox.getText().isEmpty()) {
                    searchBox.setForeground(Color.GRAY);
                    searchBox.setText("Tìm kiếm");
                }
            }
        });
    }

    private class InvoiceTableCellRenderer extends DefaultTableCellRenderer {
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            
            setHorizontalAlignment(SwingConstants.CENTER);
            
            // Apply different styles based on the column
            switch (column) {
                case 0: // Mã đơn
                    setFont(new Font("Arial", Font.BOLD, 12));
                    break;
                case 2: // Tổng tiền
                    setForeground(new Color(0, 128, 0)); // Green for money
                    setFont(new Font("Arial", Font.BOLD, 12));
                    break;
                case 4: // Trạng thái
                    // Set color based on status
                    if (value != null) {
                        String status = value.toString().toLowerCase();
                        if (status.contains("hoàn tất") || status.contains("thành công") ||
                            status.contains("đã thanh toán") || status.contains("hoàn thành")) {
                            setForeground(new Color(0, 150, 0)); // Green for successful status
                            setFont(new Font("Arial", Font.BOLD, 12));
                        } else {
                            setForeground(new Color(200, 0, 0)); // Red for all other statuses
                            setFont(new Font("Arial", Font.BOLD, 12));
                        }
                    }
                    break;
                case 7: // Hành động
                    setForeground(new Color(0, 102, 204)); // Blue for button text
                    setFont(new Font("Arial", Font.BOLD, 12));
                    break;
                default:
                    setForeground(Color.BLACK);
                    setFont(new Font("Arial", Font.PLAIN, 12));
            }

            // Add bottom border to separate rows
            if (!isSelected) {
                setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(240, 240, 240)));
            }
            
            return c;
        }
    }

    private class RoundedPanel extends JPanel {
        private int radius;

        public RoundedPanel(int radius) {
            super();
            this.radius = radius;
            setOpaque(false);
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(getBackground());
            g2.fill(new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), radius, radius));
            g2.dispose();
        }
    }

    private void customizeSpinner(JSpinner spinner) {
        JComponent editor = spinner.getEditor();
        if (editor instanceof JSpinner.DefaultEditor) {
            JSpinner.DefaultEditor defaultEditor = (JSpinner.DefaultEditor) editor;
            defaultEditor.getTextField().setBackground(Color.WHITE);
            defaultEditor.getTextField().setFont(new Font("Arial", Font.PLAIN, 14));
            defaultEditor.getTextField().setHorizontalAlignment(JTextField.CENTER);
            defaultEditor.getTextField().setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(new Color(200, 200, 200), 1, true),
                    BorderFactory.createEmptyBorder(5, 5, 5, 5)));
        }
    }
    
    private void setupEventListeners() {
        // Add event listeners for filters
        filterAllRadio.addActionListener(e -> {
            if (!isRefreshing) {
                loadHoaDonData();
            }
        });
        
        filterNapTienRadio.addActionListener(e -> {
            if (!isRefreshing) {
                loadHoaDonData();
            }
        });
        
        filterDichVuRadio.addActionListener(e -> {
            if (!isRefreshing) {
                loadHoaDonData();
            }
        });
        
        // Add date change listeners
        beginDatePicker.addChangeListener(e -> {
            if (!isRefreshing) {
                loadHoaDonData();
            }
        });
        
        endDatePicker.addChangeListener(e -> {
            if (!isRefreshing) {
                loadHoaDonData();
            }
        });
        
        // Add search button listener
        searchButton.addActionListener(e -> loadHoaDonData());
        
        // Add refresh button listener
        refreshButton.addActionListener(e -> resetFilters());
        
        // Add search box enter key listener
        searchBox.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    loadHoaDonData();
                }
            }
        });
        
        // Add table row click listener for viewing details
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = table.getSelectedRow();
                int column = table.getSelectedColumn();
                
                // Check if the click is on the action column (view details button)
                if (row >= 0 && column == 7) {
                    String maHD = (String) table.getValueAt(row, 0);
                    String loaiHD = (String) table.getValueAt(row, 6);
                    
                    // Log thông tin để gỡ lỗi
                    System.out.println("Đang mở chi tiết hóa đơn: " + maHD);
                    System.out.println("Loại hóa đơn: " + loaiHD);
                    
                    showChiTietHoaDon(maHD, loaiHD);
                }
            }
        });
    }
    
    private void loadHoaDonData() {
        try {
            // Clear existing table data
            tableModel.setRowCount(0);
            
            // Get filter values
            String maHD = searchBox.getText();
            if (maHD.equals("Tìm kiếm")) {
                maHD = null;
            }
            
            Date batDau = (Date) beginDatePicker.getValue();
            Date ketThuc = (Date) endDatePicker.getValue();
            
            String loai = null;
            if (filterNapTienRadio.isSelected()) {
                loai = "Nạp tiền";
            } else if (filterDichVuRadio.isSelected()) {
                loai = "Dịch vụ";
            }
            
            // Get data from controller
            List<HoaDon> hoaDonList = hoaDonController.timKiemHoaDon(maHD, batDau, ketThuc, loai);
            
            // Add data to table
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            
            for (HoaDon hoaDon : hoaDonList) {
                Object[] rowData = new Object[8];
                rowData[0] = hoaDon.getMaHD();
                rowData[1] = hoaDon.getMaNV();
                rowData[2] = formatPrice(hoaDon.getTongThanhToan());
                rowData[3] = hoaDon.getPhuongThucTT();
                rowData[4] = hoaDon.getTrangThai();
                rowData[5] = dateFormat.format(hoaDon.getNgayLap());
                rowData[6] = hoaDon.getLoaiHoaDon();
                rowData[7] = "Xem chi tiết";
                
                tableModel.addRow(rowData);
            }
            
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, 
                    "Lỗi khi tải dữ liệu: " + ex.getMessage(), 
                    "Lỗi", 
                    JOptionPane.ERROR_MESSAGE);
            resetFilters();
        }
    }
    
    private void resetFilters() {
        isRefreshing = true;
        
        // Reset search box
        searchBox.setText("Tìm kiếm");
        searchBox.setForeground(Color.GRAY);
        
        // Reset date pickers
        Calendar cal = Calendar.getInstance();
        endDatePicker.setValue(cal.getTime()); // Today
        
        cal.add(Calendar.DATE, -30);
        beginDatePicker.setValue(cal.getTime()); // 30 days ago
        
        // Reset filter radio buttons
        filterAllRadio.setSelected(true);
        
        isRefreshing = false;
        
        // Load data with reset filters
        loadHoaDonData();
    }
    
    private String formatPrice(Integer price) {
        if (price == null) return "0 VNĐ";
        DecimalFormat formatter = new DecimalFormat("#,###");
        return formatter.format(price) + " VNĐ";
    }
    
    private void showChiTietHoaDon(String maHD, String loaiHoaDon) {
        try {
            System.out.println("Mở chi tiết hóa đơn với mã: " + maHD + ", loại: " + loaiHoaDon);
            
            // If this is a service invoice, use the ChiTietHoaDonDV dialog
            if (loaiHoaDon != null && loaiHoaDon.equalsIgnoreCase("Dịch vụ")) {
                System.out.println("Đây là hóa đơn dịch vụ, mở cửa sổ ChiTietHoaDonDV");

                // Create and show the dialog with the invoice ID
                ChiTietHoaDonDV chiTietDialog = new ChiTietHoaDonDV(this, maHD.trim());

                // Log success
                System.out.println("Dialog created successfully for invoice ID: " + maHD);

                // Make it visible
                chiTietDialog.setVisible(true);
                return;
            } 
            // If this is a top-up invoice, use the ChiTietHoaDonNap dialog
            else if (loaiHoaDon != null && loaiHoaDon.equalsIgnoreCase("Nạp tiền")) {
                System.out.println("Đây là hóa đơn nạp tiền, mở cửa sổ ChiTietHoaDonNap");
                
                // Create and show the dialog with the invoice ID
                ChiTietHoaDonNap chiTietDialog = new ChiTietHoaDonNap(this, maHD.trim());
                
                // Log success
                System.out.println("Dialog created successfully for invoice ID: " + maHD);
                
                // Make it visible
                chiTietDialog.setVisible(true);
                return;
            } else {
                System.out.println("Đây là loại hóa đơn không xác định, mở cửa sổ thông tin chung");
            }

            // Otherwise show a generic dialog for other invoice types
            JDialog detailDialog = new JDialog(this, "Chi tiết hóa đơn #" + maHD, true);
            detailDialog.setSize(600, 500);
            detailDialog.setLocationRelativeTo(this);
            detailDialog.setLayout(new BorderLayout());

            JPanel panel = new JPanel(new BorderLayout());
            panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

            // Get invoice details from controller
            HoaDon hoaDon = hoaDonController.timHoaDonTheoMa(maHD);

            if (hoaDon != null) {
                // Create invoice header
                JPanel headerPanel = new JPanel(new GridLayout(6, 2, 10, 10));
                headerPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));

                SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

                addDetailRow(headerPanel, "Mã hóa đơn:", hoaDon.getMaHD());
                addDetailRow(headerPanel, "Ngày lập:", dateFormat.format(hoaDon.getNgayLap()));
                addDetailRow(headerPanel, "Mã nhân viên:", String.valueOf(hoaDon.getMaNV()));
                addDetailRow(headerPanel, "Trạng thái:", hoaDon.getTrangThai());
                addDetailRow(headerPanel, "Phương thức thanh toán:", hoaDon.getPhuongThucTT());
                addDetailRow(headerPanel, "Loại hóa đơn:", hoaDon.getLoaiHoaDon());

                panel.add(headerPanel, BorderLayout.NORTH);

                // Add content based on invoice type
                JPanel contentPanel = new JPanel(new GridLayout(3, 2, 10, 10));
                
                // Generic invoice info
                if (hoaDon.getTenDangNhap() != null) {
                    addDetailRow(contentPanel, "Tên đăng nhập:", hoaDon.getTenDangNhap());
                }
                if (hoaDon.getSoTienNap() != null) {
                    addDetailRow(contentPanel, "Số tiền nạp:", formatPrice(hoaDon.getSoTienNap()));
                }
                if (hoaDon.getTienDuocGiam() != null) {
                    addDetailRow(contentPanel, "Tiền được giảm:", formatPrice(hoaDon.getTienDuocGiam()));
                }

                panel.add(contentPanel, BorderLayout.CENTER);

                // Add total section
                JPanel totalPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
                JLabel totalLabel = new JLabel("Tổng thanh toán: " + formatPrice(hoaDon.getTongThanhToan()));
                totalLabel.setFont(new Font("Arial", Font.BOLD, 16));
                totalLabel.setForeground(new Color(0, 128, 0));
                totalPanel.add(totalLabel);

                panel.add(totalPanel, BorderLayout.SOUTH);
            } else {
                JLabel notFoundLabel = new JLabel("Không tìm thấy thông tin hóa đơn!", JLabel.CENTER);
                notFoundLabel.setFont(new Font("Arial", Font.BOLD, 16));
                panel.add(notFoundLabel, BorderLayout.CENTER);
            }

            detailDialog.add(panel);

            // Add close button
            JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
            JButton closeButton = new JButton("Đóng");
            closeButton.addActionListener(e -> detailDialog.dispose());
            buttonPanel.add(closeButton);

            detailDialog.add(buttonPanel, BorderLayout.SOUTH);

            // Show the dialog
            detailDialog.setVisible(true);

        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this,
                    "Lỗi khi hiển thị chi tiết hóa đơn: " + ex.getMessage(),
                    "Lỗi",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void addDetailRow(JPanel panel, String label, String value) {
        JLabel lblName = new JLabel(label);
        lblName.setFont(new Font("Arial", Font.BOLD, 14));

        JLabel lblValue = new JLabel(value);
        lblValue.setFont(new Font("Arial", Font.PLAIN, 14));

        panel.add(lblName);
        panel.add(lblValue);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                e.printStackTrace();
            }
            new HoaDonForm().setVisible(true);
        });
    }
}