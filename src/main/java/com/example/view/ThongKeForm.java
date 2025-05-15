package com.example.view;

import com.example.dao.DoanhThuDAO;
import com.example.dao.DichVuDAO;
import com.example.dao.MayTinhDAO;
import com.example.dao.KhuyenMaiDAO;
import com.example.dao.TaiKhoanDAO;
import com.example.dao.DichVuDAO.TopItem;
import com.example.model.MayTinh;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.List;
import java.util.ArrayList;
import java.util.Vector;
import java.util.Map;
import java.util.HashMap;

public class ThongKeForm extends JFrame {
    // DAO instances
    private TaiKhoanDAO dbtk;
    private MayTinhDAO dbmt;
    private KhuyenMaiDAO dbkm;
    private DichVuDAO dbdv;
    private DoanhThuDAO dbdt;
    private int currentYear;
    private int currentMonth;

    // GUI components
    private JPanel mainPanel;
    private JPanel flowLayoutPanel;
    private boolean isEmbedded = false;

    // Revenue panel
    private JPanel revenuePanel;
    private JLabel revenueLabel;
    private JLabel revenueTrendLabel;
    private JLabel revenueTitleLabel;
    private JLabel revenueIconLabel;
    private JLabel revenueTrendIconLabel;

    // Computer panel
    private JPanel computerPanel;
    private JLabel computerCountLabel;
    private JLabel computerTitleLabel;
    private JLabel computerIconLabel;

    // Account panel
    private JPanel accountPanel;
    private JLabel accountCountLabel;
    private JLabel accountTrendLabel;
    private JLabel accountTitleLabel;
    private JLabel accountIconLabel;
    private JLabel accountTrendIconLabel;

    // Promotion panel
    private JPanel promotionPanel;
    private JLabel promotionCountLabel;
    private JLabel promotionExpiringLabel;
    private JLabel promotionTitleLabel;
    private JLabel promotionIconLabel;
    private JLabel promotionTrendIconLabel;

    // Top services panel
    private JPanel topServicesPanel;
    private JComboBox<String> serviceTypeComboBox;
    private JTable topServicesTable;
    private DefaultTableModel topServicesTableModel;
    private JLabel topServicesTitleLabel;
    private JLabel topServicesIconLabel;

    // Bottom services panel
    private JPanel bottomServicesPanel;
    private JComboBox<String> bottomServiceTypeComboBox;
    private JTable bottomServicesTable;
    private DefaultTableModel bottomServicesTableModel;
    private JLabel bottomServicesTitleLabel;
    private JLabel bottomServicesIconLabel;

    // Revenue chart panel
    private JPanel chartPanel;
    private JPanel revenueChartPanel;
    private JLabel chartTitleLabel;
    private JLabel chartIconLabel;

    // Statistics panel
    private JPanel statsPanel;
    private JComboBox<Integer> yearComboBox;
    private JComboBox<Integer> monthComboBox;
    private JLabel totalRevenueLabel;

    // Employee of the month panel
    private JPanel empMonthPanel;
    private JLabel empMonthIdLabel;
    private JLabel empMonthNameLabel;
    private JLabel empMonthRevenueLabel;
    private JLabel empMonthTitleLabel;

    // Employee of the year panel
    private JPanel empYearPanel;
    private JLabel empYearIdLabel;
    private JLabel empYearNameLabel;
    private JLabel empYearRevenueLabel;
    private JLabel empYearTitleLabel;

    // Customer of the month panel
    private JPanel custMonthPanel;
    private JLabel custMonthUsernameLabel;
    private JLabel custMonthSpendingLabel;
    private JLabel custMonthTitleLabel;

    // Customer of the year panel
    private JPanel custYearPanel;
    private JLabel custYearUsernameLabel;
    private JLabel custYearSpendingLabel;
    private JLabel custYearTitleLabel;

    // Employee table
    private JTable employeeTable;
    private DefaultTableModel employeeTableModel;

    // Customer table
    private JTable customerTable;
    private DefaultTableModel customerTableModel;

    public ThongKeForm() {
        this(false);
    }

    public ThongKeForm(boolean isEmbedded) {
        this.isEmbedded = isEmbedded;

        // Initialize DAO instances
        currentYear = Calendar.getInstance().get(Calendar.YEAR);
        currentMonth = Calendar.getInstance().get(Calendar.MONTH) + 1; // Calendar months are 0-based
        try {
            dbdt = DoanhThuDAO.getInstance();
            dbdv = DichVuDAO.getInstance();
            dbmt = MayTinhDAO.getInstance();
            dbkm = KhuyenMaiDAO.getInstance();
            dbtk = TaiKhoanDAO.getInstance();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error initializing data: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }

        initComponents();
        loadData();
        System.out.println(this.getSize());
    }

    public Container getContent() {
        if (isEmbedded) {
            // Create a scroll pane to enable scrolling when embedded
            JScrollPane scrollPane = new JScrollPane();
            scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
            scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
            scrollPane.setBorder(null);
            scrollPane.setViewportView(mainPanel);
            return scrollPane;
        }
        return mainPanel;
    }

    private void initComponents() {
        // Set up the main frame
        if (!isEmbedded) {
            setTitle("Thống Kê");
            setDefaultCloseOperation(EXIT_ON_CLOSE);
            setSize(1300, 700);
        }
        else {
            setTitle("Thống Kê - Nhúng");
            setSize(1300, 700);
        }

        // Create main panel with BorderLayout
        mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        // Set preferred size for the main panel

        if (!isEmbedded) {
            // Create a scroll pane to enable scrolling
            JScrollPane scrollPane = new JScrollPane();
            scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
            scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
            scrollPane.setBorder(null);
            setContentPane(scrollPane);
            scrollPane.setViewportView(mainPanel);
        }

        // Create panels for each section
        JPanel topSection = new JPanel(new BorderLayout(10, 10));
        JPanel centerSection = new JPanel(new BorderLayout(10, 10));
        JPanel bottomSection = new JPanel(new BorderLayout(10, 10));

        // Initialize all panels
        initRevenuePanel();
        initComputerPanel();
        initAccountPanel();
        initPromotionPanel();
        initTopServicesPanel();
        initBottomServicesPanel();
        initChartPanel();
        initStatsPanel();

        // Top Section: Filters and Summary
        JPanel filtersPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        filtersPanel.add(new JLabel("Năm:"));
        filtersPanel.add(yearComboBox);
        filtersPanel.add(new JLabel("Tháng:"));
        filtersPanel.add(monthComboBox);

        JPanel summaryPanel = new JPanel(new GridLayout(1, 4, 10, 0));
        summaryPanel.add(revenuePanel);
        summaryPanel.add(computerPanel);
        summaryPanel.add(accountPanel);
        summaryPanel.add(promotionPanel);

        topSection.add(filtersPanel, BorderLayout.NORTH);
        topSection.add(summaryPanel, BorderLayout.CENTER);

        // Center Section: Top 5 Services (food and drinks)
        JPanel rankingsPanel = new JPanel(new GridLayout(1, 2, 10, 0));
        rankingsPanel.add(topServicesPanel);
        rankingsPanel.add(bottomServicesPanel);

        centerSection.add(rankingsPanel, BorderLayout.CENTER);

        // Bottom Section: Detailed Statistics and Chart
        JPanel employeeCustomerPanel = new JPanel(new GridLayout(2, 2, 10, 10));
        employeeCustomerPanel.add(empMonthPanel);
        employeeCustomerPanel.add(custMonthPanel);
        employeeCustomerPanel.add(empYearPanel);
        employeeCustomerPanel.add(custYearPanel);

        JPanel tablesPanel = new JPanel(new GridLayout(1, 2, 10, 0));
        tablesPanel.add(new JScrollPane(employeeTable));
        tablesPanel.add(new JScrollPane(customerTable));

        JPanel totalRevenuePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        totalRevenuePanel.add(totalRevenueLabel);

        // Create a panel for statistics
        JPanel statsContentPanel = new JPanel(new BorderLayout(10, 10));
        statsContentPanel.add(employeeCustomerPanel, BorderLayout.NORTH);
        statsContentPanel.add(tablesPanel, BorderLayout.CENTER);
        statsContentPanel.add(totalRevenuePanel, BorderLayout.SOUTH);

        bottomSection.add(statsContentPanel, BorderLayout.CENTER);

        // Create a panel to hold center section and chart panel
        JPanel centerChartPanel = new JPanel(new BorderLayout(10, 10));
        centerChartPanel.add(centerSection, BorderLayout.NORTH);
        centerChartPanel.add(chartPanel, BorderLayout.CENTER);

        // Add all sections to the main panel
        JPanel contentPanel = new JPanel(new BorderLayout(10, 10));
        contentPanel.add(topSection, BorderLayout.NORTH);
        contentPanel.add(centerChartPanel, BorderLayout.CENTER);
        contentPanel.add(bottomSection, BorderLayout.SOUTH);

        mainPanel.add(contentPanel, BorderLayout.CENTER);
    }

    private void initRevenuePanel() {
        revenuePanel = new JPanel(new BorderLayout());
        revenuePanel.setPreferredSize(new Dimension(280, 120));
        revenuePanel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        revenuePanel.setBackground(Color.WHITE);

        JPanel contentPanel = new JPanel(new GridLayout(3, 1));
        contentPanel.setBackground(Color.WHITE);

        revenueTitleLabel = new JLabel("Doanh thu tháng này");
        revenueTitleLabel.setFont(new Font("Arial", Font.BOLD, 14));

        revenueLabel = new JLabel("0đ");
        revenueLabel.setName("dtLabel");
        revenueLabel.setFont(new Font("Arial", Font.BOLD, 18));

        revenueTrendLabel = new JLabel("Tăng 0% so với tháng trước");
        revenueTrendLabel.setName("dtTang");

        // Create a panel for the trend icon
        JPanel trendPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        trendPanel.setBackground(Color.WHITE);

        revenueTrendIconLabel = new JLabel(); // This would be set with an up/down icon
        revenueTrendIconLabel.setName("dtPic");
        trendPanel.add(revenueTrendLabel);
        trendPanel.add(revenueTrendIconLabel);

        contentPanel.add(revenueTitleLabel);
        contentPanel.add(revenueLabel);
        contentPanel.add(trendPanel);

        revenuePanel.add(contentPanel, BorderLayout.CENTER);
    }

    private void initComputerPanel() {
        computerPanel = new JPanel(new BorderLayout());
        computerPanel.setPreferredSize(new Dimension(280, 120));
        computerPanel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        computerPanel.setBackground(Color.WHITE);

        JPanel contentPanel = new JPanel(new GridLayout(2, 1));
        contentPanel.setBackground(Color.WHITE);

        computerTitleLabel = new JLabel("Số máy đang hoạt động");
        computerTitleLabel.setFont(new Font("Arial", Font.BOLD, 14));

        computerCountLabel = new JLabel("0/0");
        computerCountLabel.setName("soMayHDLabel");
        computerCountLabel.setFont(new Font("Arial", Font.BOLD, 18));

        contentPanel.add(computerTitleLabel);
        contentPanel.add(computerCountLabel);

        computerPanel.add(contentPanel, BorderLayout.CENTER);
    }

    private void initAccountPanel() {
        accountPanel = new JPanel(new BorderLayout());
        accountPanel.setPreferredSize(new Dimension(280, 120));
        accountPanel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        accountPanel.setBackground(Color.WHITE);

        JPanel contentPanel = new JPanel(new GridLayout(3, 1));
        contentPanel.setBackground(Color.WHITE);

        accountTitleLabel = new JLabel("Tổng số tài khoản");
        accountTitleLabel.setFont(new Font("Arial", Font.BOLD, 14));

        accountCountLabel = new JLabel("0");
        accountCountLabel.setName("tongTkLabel");
        accountCountLabel.setFont(new Font("Arial", Font.BOLD, 18));

        accountTrendLabel = new JLabel("0 tài khoản mới tháng này");
        accountTrendLabel.setName("tkTang");

        contentPanel.add(accountTitleLabel);
        contentPanel.add(accountCountLabel);
        contentPanel.add(accountTrendLabel);

        accountPanel.add(contentPanel, BorderLayout.CENTER);
    }

    private void initPromotionPanel() {
        promotionPanel = new JPanel(new BorderLayout());
        promotionPanel.setPreferredSize(new Dimension(280, 120));
        promotionPanel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        promotionPanel.setBackground(Color.WHITE);

        JPanel contentPanel = new JPanel(new GridLayout(3, 1));
        contentPanel.setBackground(Color.WHITE);

        promotionTitleLabel = new JLabel("Khuyến mãi");
        promotionTitleLabel.setFont(new Font("Arial", Font.BOLD, 14));

        promotionCountLabel = new JLabel("0 khuyến mãi đang diễn ra");
        promotionCountLabel.setName("soKmDangDienRa");
        promotionCountLabel.setFont(new Font("Arial", Font.BOLD, 16));

        promotionExpiringLabel = new JLabel("0 khuyến mãi sắp hết hạn");
        promotionExpiringLabel.setName("kmSapHet");

        contentPanel.add(promotionTitleLabel);
        contentPanel.add(promotionCountLabel);
        contentPanel.add(promotionExpiringLabel);

        promotionPanel.add(contentPanel, BorderLayout.CENTER);
    }

    private void initTopServicesPanel() {
        topServicesPanel = new JPanel(new BorderLayout());
        topServicesPanel.setPreferredSize(new Dimension(280, 250));
        topServicesPanel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        topServicesPanel.setBackground(Color.WHITE);

        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(Color.WHITE);

        topServicesTitleLabel = new JLabel("Top 5 bán chạy nhất");
        topServicesTitleLabel.setFont(new Font("Arial", Font.BOLD, 14));

        serviceTypeComboBox = new JComboBox<>(new String[]{"Đồ ăn", "Đồ uống"});
        serviceTypeComboBox.setName("dvCbBox");
        serviceTypeComboBox.addActionListener(e -> loadTopServices());

        headerPanel.add(topServicesTitleLabel, BorderLayout.WEST);
        headerPanel.add(serviceTypeComboBox, BorderLayout.EAST);

        // Create table for top services
        String[] columnNames = {"Tên dịch vụ", "Số lượng bán"};
        topServicesTableModel = new DefaultTableModel(columnNames, 0);
        topServicesTable = new JTable(topServicesTableModel);
        topServicesTable.setName("dgvTop5DESC");
        topServicesTable.setRowHeight(25);

        JScrollPane scrollPane = new JScrollPane(topServicesTable);

        topServicesPanel.add(headerPanel, BorderLayout.NORTH);
        topServicesPanel.add(scrollPane, BorderLayout.CENTER);
    }

    private void initBottomServicesPanel() {
        bottomServicesPanel = new JPanel(new BorderLayout());
        bottomServicesPanel.setPreferredSize(new Dimension(280, 250));
        bottomServicesPanel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        bottomServicesPanel.setBackground(Color.WHITE);

        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(Color.WHITE);

        bottomServicesTitleLabel = new JLabel("Top 5 bán ít nhất");
        bottomServicesTitleLabel.setFont(new Font("Arial", Font.BOLD, 14));

        bottomServiceTypeComboBox = new JComboBox<>(new String[]{"Đồ ăn", "Đồ uống"});
        bottomServiceTypeComboBox.setName("dvCbBoxAsc");
        bottomServiceTypeComboBox.addActionListener(e -> loadBottomServices());

        headerPanel.add(bottomServicesTitleLabel, BorderLayout.WEST);
        headerPanel.add(bottomServiceTypeComboBox, BorderLayout.EAST);

        // Create table for bottom services
        String[] columnNames = {"Tên dịch vụ", "Số lượng bán"};
        bottomServicesTableModel = new DefaultTableModel(columnNames, 0);
        bottomServicesTable = new JTable(bottomServicesTableModel);
        bottomServicesTable.setName("dgvTop5ASC");
        bottomServicesTable.setRowHeight(25);

        JScrollPane scrollPane = new JScrollPane(bottomServicesTable);

        bottomServicesPanel.add(headerPanel, BorderLayout.NORTH);
        bottomServicesPanel.add(scrollPane, BorderLayout.CENTER);
    }

    private void initChartPanel() {
        chartPanel = new JPanel(new BorderLayout());
        chartPanel.setPreferredSize(new Dimension(580, 300));
        chartPanel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        chartPanel.setBackground(Color.WHITE);

        chartTitleLabel = new JLabel("Biểu đồ doanh thu 12 tháng gần nhất");
        chartTitleLabel.setFont(new Font("Arial", Font.BOLD, 14));

        // Create a custom chart panel
        revenueChartPanel = new RevenueChartPanel();
        revenueChartPanel.setName("chart1");
        revenueChartPanel.setBackground(Color.WHITE);

        chartPanel.add(chartTitleLabel, BorderLayout.NORTH);
        chartPanel.add(revenueChartPanel, BorderLayout.CENTER);

        // Load chart data
        loadChartData();
    }

    private void loadChartData() {
        try {
            List<MayTinh> data = dbdt.getDoanhThu12ThangGanNhat();
            if (data != null && !data.isEmpty()) {
                Map<String, Integer> chartData = new HashMap<>();

                for (MayTinh item : data) {
                    // Extract month and year from soMay (month) and trangThai (year)
                    String month = item.getSoMay();
                    String year = item.getTrangThai();
                    int revenue = item.getMaLoaiMay(); // Revenue is stored in maLoaiMay

                    String key = month + "/" + year;
                    chartData.put(key, revenue);
                }

                // Update the chart with the data
                ((RevenueChartPanel) revenueChartPanel).setChartData(chartData);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error loading chart data: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    // Custom chart panel class
    private class RevenueChartPanel extends JPanel {
        private Map<String, Integer> chartData = new HashMap<>();
        private final int PADDING = 30;
        private final int LABEL_PADDING = 10;

        public RevenueChartPanel() {
            setPreferredSize(new Dimension(580, 250));
        }

        public void setChartData(Map<String, Integer> data) {
            this.chartData = data;
            repaint();
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            int width = getWidth();
            int height = getHeight();

            // Draw axes
            g2.setColor(Color.BLACK);
            g2.drawLine(PADDING, height - PADDING, width - PADDING, height - PADDING); // X-axis
            g2.drawLine(PADDING, PADDING, PADDING, height - PADDING); // Y-axis

            // Draw axis labels
            g2.drawString("Tháng", width / 2, height - 5);

            // Rotate and draw Y-axis label
            g2.rotate(-Math.PI / 2);
            g2.drawString("Doanh thu", -height / 2, 15);
            g2.rotate(Math.PI / 2);

            if (chartData.isEmpty()) {
                return;
            }

            // Find max revenue for scaling
            int maxRevenue = 0;
            for (int revenue : chartData.values()) {
                maxRevenue = Math.max(maxRevenue, revenue);
            }

            // Add 10% padding to max revenue
            maxRevenue = (int) (maxRevenue * 1.1);

            // Calculate bar width and spacing
            int barWidth = (width - 2 * PADDING) / (chartData.size() + 1);

            // Draw data points
            int i = 0;
            for (Map.Entry<String, Integer> entry : chartData.entrySet()) {
                String label = entry.getKey();
                int value = entry.getValue();

                int x = PADDING + (i + 1) * barWidth;
                int barHeight = (int) ((double) value / maxRevenue * (height - 2 * PADDING));
                int y = height - PADDING - barHeight;

                // Draw bar
                g2.setColor(new Color(70, 130, 180));
                g2.fillRect(x, y, barWidth / 2, barHeight);

                // Draw value above bar
                g2.setColor(Color.BLACK);
                String valueStr = formatPrice(value);
                FontMetrics metrics = g2.getFontMetrics();
                int labelWidth = metrics.stringWidth(valueStr);
                g2.drawString(valueStr, x - labelWidth / 2 + barWidth / 4, y - 5);

                // Draw x-axis label
                g2.drawString(label, x - metrics.stringWidth(label) / 2 + barWidth / 4, height - PADDING + LABEL_PADDING + 10);

                i++;
            }
        }
    }

    private void initStatsPanel() {
        statsPanel = new JPanel(new BorderLayout());
        statsPanel.setPreferredSize(new Dimension(580, 400));
        statsPanel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        statsPanel.setBackground(Color.WHITE);

        JPanel headerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        headerPanel.setBackground(Color.WHITE);

        JLabel yearLabel = new JLabel("Năm:");
        yearComboBox = new JComboBox<>(new Integer[]{currentYear, currentYear - 1});
        yearComboBox.setName("namCbBox");
        yearComboBox.addActionListener(e -> updateMonthComboBox());

        JLabel monthLabel = new JLabel("Tháng:");
        List<Integer> months = new ArrayList<>();
        for (int i = 1; i <= currentMonth; i++) {
            months.add(i);
        }
        monthComboBox = new JComboBox<>(months.toArray(new Integer[0]));
        monthComboBox.setName("thangCbBox");
        monthComboBox.addActionListener(e -> loadMonthlyStats());

        totalRevenueLabel = new JLabel("Tổng doanh thu: 0đ");
        totalRevenueLabel.setName("tongdt");
        totalRevenueLabel.setFont(new Font("Arial", Font.BOLD, 16));

        headerPanel.add(yearLabel);
        headerPanel.add(yearComboBox);
        headerPanel.add(monthLabel);
        headerPanel.add(monthComboBox);
        headerPanel.add(totalRevenueLabel);

        // Initialize employee and customer panels
        initEmployeeMonthPanel();
        initEmployeeYearPanel();
        initCustomerMonthPanel();
        initCustomerYearPanel();

        // Initialize tables
        initEmployeeTable();
        initCustomerTable();

        // Create panel for tables
        JPanel tablesPanel = new JPanel(new GridLayout(1, 2, 10, 0));
        tablesPanel.add(new JScrollPane(employeeTable));
        tablesPanel.add(new JScrollPane(customerTable));

        // Create panel for employee and customer stats
        JPanel statsGridPanel = new JPanel(new GridLayout(2, 2, 10, 10));
        statsGridPanel.add(empMonthPanel);
        statsGridPanel.add(custMonthPanel);
        statsGridPanel.add(empYearPanel);
        statsGridPanel.add(custYearPanel);

        // Add components to stats panel
        JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.add(statsGridPanel, BorderLayout.NORTH);
        contentPanel.add(tablesPanel, BorderLayout.CENTER);

        statsPanel.add(headerPanel, BorderLayout.NORTH);
        statsPanel.add(contentPanel, BorderLayout.CENTER);
    }

    private void initEmployeeMonthPanel() {
        empMonthPanel = new JPanel(new BorderLayout());
        empMonthPanel.setBorder(BorderFactory.createTitledBorder("Nhân viên của tháng"));
        empMonthPanel.setBackground(Color.WHITE);

        JPanel contentPanel = new JPanel(new GridLayout(3, 1));
        contentPanel.setBackground(Color.WHITE);

        empMonthIdLabel = new JLabel("Mã NV: X");
        empMonthIdLabel.setName("maNvThang");

        empMonthNameLabel = new JLabel("Tên: Không xác định");
        empMonthNameLabel.setName("tenNvThang");

        empMonthRevenueLabel = new JLabel("Tổng doanh thu: 0đ");
        empMonthRevenueLabel.setName("tongdtNvM");

        contentPanel.add(empMonthIdLabel);
        contentPanel.add(empMonthNameLabel);
        contentPanel.add(empMonthRevenueLabel);

        empMonthPanel.add(contentPanel, BorderLayout.CENTER);
    }

    private void initEmployeeYearPanel() {
        empYearPanel = new JPanel(new BorderLayout());
        empYearPanel.setBorder(BorderFactory.createTitledBorder("Nhân viên của năm"));
        empYearPanel.setBackground(Color.WHITE);

        JPanel contentPanel = new JPanel(new GridLayout(3, 1));
        contentPanel.setBackground(Color.WHITE);

        empYearIdLabel = new JLabel("Mã NV: X");
        empYearIdLabel.setName("maNvNam");

        empYearNameLabel = new JLabel("Tên: Không xác định");
        empYearNameLabel.setName("tenNvNam");

        empYearRevenueLabel = new JLabel("Tổng doanh thu: 0đ");
        empYearRevenueLabel.setName("tongdtNvY");

        contentPanel.add(empYearIdLabel);
        contentPanel.add(empYearNameLabel);
        contentPanel.add(empYearRevenueLabel);

        empYearPanel.add(contentPanel, BorderLayout.CENTER);
    }

    private void initCustomerMonthPanel() {
        custMonthPanel = new JPanel(new BorderLayout());
        custMonthPanel.setBorder(BorderFactory.createTitledBorder("Khách hàng của tháng"));
        custMonthPanel.setBackground(Color.WHITE);

        JPanel contentPanel = new JPanel(new GridLayout(2, 1));
        contentPanel.setBackground(Color.WHITE);

        custMonthUsernameLabel = new JLabel("Tên đăng nhập: Không xác định");
        custMonthUsernameLabel.setName("usnThang");

        custMonthSpendingLabel = new JLabel("Tổng chi tiêu: 0đ");
        custMonthSpendingLabel.setName("tongctKhM");

        contentPanel.add(custMonthUsernameLabel);
        contentPanel.add(custMonthSpendingLabel);

        custMonthPanel.add(contentPanel, BorderLayout.CENTER);
    }

    private void initCustomerYearPanel() {
        custYearPanel = new JPanel(new BorderLayout());
        custYearPanel.setBorder(BorderFactory.createTitledBorder("Khách hàng của năm"));
        custYearPanel.setBackground(Color.WHITE);

        JPanel contentPanel = new JPanel(new GridLayout(2, 1));
        contentPanel.setBackground(Color.WHITE);

        custYearUsernameLabel = new JLabel("Tên đăng nhập: Không xác định");
        custYearUsernameLabel.setName("usnNam");

        custYearSpendingLabel = new JLabel("Tổng chi tiêu: 0đ");
        custYearSpendingLabel.setName("tongctKhY");

        contentPanel.add(custYearUsernameLabel);
        contentPanel.add(custYearSpendingLabel);

        custYearPanel.add(contentPanel, BorderLayout.CENTER);
    }

    private void initEmployeeTable() {
        String[] columnNames = {"Mã NV", "Họ tên", "Doanh thu"};
        employeeTableModel = new DefaultTableModel(columnNames, 0);
        employeeTable = new JTable(employeeTableModel);
        employeeTable.setName("dgvNV");
        employeeTable.setRowHeight(25);
    }

    private void initCustomerTable() {
        String[] columnNames = {"Tên đăng nhập", "Tổng thời gian (giờ)", "Tổng tiền nạp"};
        customerTableModel = new DefaultTableModel(columnNames, 0);
        customerTable = new JTable(customerTableModel);
        customerTable.setName("dgvCus");
        customerTable.setRowHeight(25);
    }

    private void loadData() {
        try {
            // Load revenue data
            int currentMonthRevenue = dbdt.getDoanhThuThangNay();
            revenueLabel.setText(formatPrice(currentMonthRevenue));

            // Calculate revenue trend
            int lastMonth = currentMonth == 1 ? 12 : currentMonth - 1;
            int lastYear = currentMonth == 1 ? currentYear - 1 : currentYear;
            int lastMonthRevenue = dbdt.getTongDoanhThuThang(lastMonth, lastYear);
            revenueTrendLabel.setText(calculateRevenueTrend(lastMonthRevenue, currentMonthRevenue));

            // Load computer data
            int activeComputers = dbmt.tinhTongMayHD();
            int totalComputers = 0;
            try {
                totalComputers = dbmt.layDsMayTinh().size();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            computerCountLabel.setText(activeComputers + "/" + totalComputers);

            // Load account data
            int totalAccounts = 0;
            try {
                totalAccounts = dbtk.layDsTaiKhoan().size();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            accountCountLabel.setText(String.valueOf(totalAccounts));

            int newAccounts = 0;
            try {
                newAccounts = dbtk.tinhTkMoi();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            accountTrendLabel.setText(newAccounts + " tài khoản mới tháng này");

            // Load promotion data
            int activePromotions = 0;
            try {
                activePromotions = dbkm.SoKMKhaDung();
            } catch (Exception e) {
                e.printStackTrace();
            }
            promotionCountLabel.setText(activePromotions + " khuyến mãi đang diễn ra");

            int expiringPromotions = 0;
            try {
                expiringPromotions = dbkm.SoKhuyenMaiSapHetHan(3);
            } catch (Exception e) {
                e.printStackTrace();
            }
            promotionExpiringLabel.setText(expiringPromotions + " khuyến mãi sắp hết hạn");

            // Load top and bottom services
            loadTopServices();
            loadBottomServices();

            // Load monthly stats
            loadMonthlyStats();

            // Load chart data
            loadChartData();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error loading data: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    private void loadTopServices() {
        try {
            // Clear the table
            topServicesTableModel.setRowCount(0);

            // Get selected service type
            String serviceType = (String) serviceTypeComboBox.getSelectedItem();

            // Load data based on service type
            List<TopItem> topItems;
            if ("Đồ ăn".equals(serviceType)) {
                topItems = dbdv.layTop5DA();
            } else {
                topItems = dbdv.layTop5DU();
            }

            // Add data to table
            for (TopItem item : topItems) {
                topServicesTableModel.addRow(new Object[]{item.getTen(), item.getSoLuong()});
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error loading top services: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    private void loadBottomServices() {
        try {
            // Clear the table
            bottomServicesTableModel.setRowCount(0);

            // Get selected service type
            String serviceType = (String) bottomServiceTypeComboBox.getSelectedItem();

            // Load data based on service type
            List<TopItem> bottomItems;
            if ("Đồ ăn".equals(serviceType)) {
                bottomItems = dbdv.layTop5DAIt();
            } else {
                bottomItems = dbdv.layTop5DUIt();
            }

            // Add data to table
            for (TopItem item : bottomItems) {
                bottomServicesTableModel.addRow(new Object[]{item.getTen(), item.getSoLuong()});
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error loading bottom services: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    private void updateMonthComboBox() {
        try {
            // Get selected year
            int selectedYear = (int) yearComboBox.getSelectedItem();

            // Determine max month
            int maxMonth = (selectedYear == currentYear) ? currentMonth : 12;

            // Create list of months
            List<Integer> months = new ArrayList<>();
            for (int i = 1; i <= maxMonth; i++) {
                months.add(i);
            }

            // Save current selection if possible
            int currentSelection = -1;
            if (monthComboBox.getSelectedItem() != null) {
                currentSelection = (int) monthComboBox.getSelectedItem();
            }

            // Update combo box
            monthComboBox.removeAllItems();
            for (Integer month : months) {
                monthComboBox.addItem(month);
            }

            // Restore selection if possible
            if (currentSelection > 0 && currentSelection <= maxMonth) {
                monthComboBox.setSelectedItem(currentSelection);
            } else {
                monthComboBox.setSelectedIndex(0);
            }

            // Load stats for selected month
            loadMonthlyStats();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error updating month combo box: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    private void loadMonthlyStats() {
        try {
            // Get selected year and month
            int selectedYear = (int) yearComboBox.getSelectedItem();
            int selectedMonth = (int) monthComboBox.getSelectedItem();

            // Load total revenue
            int totalRevenue = dbdt.getTongDoanhThuThang(selectedMonth, selectedYear);
            totalRevenueLabel.setText("Tổng doanh thu: " + formatPrice(totalRevenue));

            // Load employee of the month
            List<MayTinh> empMonth = dbdt.getNhanVienCuaThang(selectedMonth, selectedYear);
            if (empMonth != null && !empMonth.isEmpty()) {
                MayTinh emp = empMonth.get(0);
                empMonthIdLabel.setText("Mã NV: " + emp.getSoMay());
                empMonthNameLabel.setText("Tên: " + emp.getTrangThai());
                empMonthRevenueLabel.setText("Tổng doanh thu: " + formatPrice(emp.getMaLoaiMay()));
            } else {
                empMonthIdLabel.setText("Mã NV: X");
                empMonthNameLabel.setText("Tên: Không xác định");
                empMonthRevenueLabel.setText("Tổng doanh thu: 0đ");
            }

            // Load employee of the year
            List<MayTinh> empYear = dbdt.getNhanVienCuaNam(selectedYear);
            if (empYear != null && !empYear.isEmpty()) {
                MayTinh emp = empYear.get(0);
                empYearIdLabel.setText("Mã NV: " + emp.getSoMay());
                empYearNameLabel.setText("Tên: " + emp.getTrangThai());
                empYearRevenueLabel.setText("Tổng doanh thu: " + formatPrice(emp.getMaLoaiMay()));
            } else {
                empYearIdLabel.setText("Mã NV: X");
                empYearNameLabel.setText("Tên: Không xác định");
                empYearRevenueLabel.setText("Tổng doanh thu: 0đ");
            }

            // Load customer of the month
            List<MayTinh> custMonth = dbdt.getKhachHangCuaThang(selectedMonth, selectedYear);
            if (custMonth != null && !custMonth.isEmpty()) {
                MayTinh cust = custMonth.get(0);
                custMonthUsernameLabel.setText("Tên đăng nhập: " + cust.getSoMay());
                custMonthSpendingLabel.setText("Tổng chi tiêu: " + formatPrice(cust.getMaLoaiMay()));
            } else {
                custMonthUsernameLabel.setText("Tên đăng nhập: Không xác định");
                custMonthSpendingLabel.setText("Tổng chi tiêu: 0đ");
            }

            // Load customer of the year
            List<MayTinh> custYear = dbdt.getKhachHangCuaNam(selectedYear);
            if (custYear != null && !custYear.isEmpty()) {
                MayTinh cust = custYear.get(0);
                custYearUsernameLabel.setText("Tên đăng nhập: " + cust.getSoMay());
                custYearSpendingLabel.setText("Tổng chi tiêu: " + formatPrice(cust.getMaLoaiMay()));
            } else {
                custYearUsernameLabel.setText("Tên đăng nhập: Không xác định");
                custYearSpendingLabel.setText("Tổng chi tiêu: 0đ");
            }

            // Load employee table
            employeeTableModel.setRowCount(0);
            List<MayTinh> employees = dbdt.getDoanhThuNhanVienTheoThang(selectedMonth, selectedYear);
            if (employees != null) {
                for (MayTinh emp : employees) {
                    employeeTableModel.addRow(new Object[]{
                        emp.getSoMay(),
                        emp.getTrangThai(),
                        formatPrice(emp.getMaLoaiMay())
                    });
                }
            }

            // Load customer table
            customerTableModel.setRowCount(0);
            List<MayTinh> customers = dbdt.getTongTGSDVaDTKH(selectedMonth, selectedYear);
            if (customers != null) {
                for (MayTinh cust : customers) {
                    customerTableModel.addRow(new Object[]{
                        cust.getSoMay(),
                        String.format("%.2f", cust.getThoiGianSD()),
                        formatPrice(cust.getMaLoaiMay())
                    });
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error loading monthly stats: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    private String calculateRevenueTrend(int lastMonth, int currentMonth) {
        if (lastMonth == 0 && currentMonth == 0) {
            return "Tăng 0% so với tháng trước";
        }
        if (lastMonth == 0) {
            return "Tăng 100% so với tháng trước";
        }

        String trend = currentMonth - lastMonth < 0 ? "Giảm " : "Tăng ";
        int rate = (int)(((double)(currentMonth - lastMonth) / lastMonth) * 100);
        return trend + Math.abs(rate) + "% so với tháng trước";
    }

    private String formatPrice(long price) {
        DecimalFormat formatter = new DecimalFormat("#,###");
        return formatter.format(price) + "đ";
    }

    // Main method for testing
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ThongKeForm form = new ThongKeForm();
            form.setVisible(true);
            System.out.println(form.getSize());
        });
    }
}
