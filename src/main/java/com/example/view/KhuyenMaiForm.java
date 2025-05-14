package com.example.view;

import com.example.controller.KhuyenMaiController;
import com.example.model.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Calendar;
import java.util.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.ParseException;
import java.sql.SQLException;

public class KhuyenMaiForm extends JFrame {
    private JTextField searchField;
    private JTable discountTable;
    private JTextField programNameField;
    private JTextField minAmountField;
    private JTextField maxDiscountField;
    private JTextField discountRateField;
    private JComboBox<LoaiKM> discountTypeComboBox;
    private JSpinner startDateSpinner, endDateSpinner;
    private JSpinner filterStartDateSpinner, filterEndDateSpinner;
    private DefaultTableModel tableModel;

    private KhuyenMaiController khuyenMaiController;
    private boolean isRefreshing = false;
    private SimpleDateFormat displayDateFormat = new SimpleDateFormat("dd/MM/yyyy");

    public KhuyenMaiForm() {
        setTitle("Quản Lý Khuyến Mãi");
        setSize(1000, 653);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(0, 0));
        
        // Khởi tạo controller
        khuyenMaiController = KhuyenMaiController.getInstance();
        
        // TOP PANEL - FILTER AND SEARCH
        JPanel topPanel = new JPanel();
        topPanel.setBackground(Color.WHITE);
        topPanel.setPreferredSize(new Dimension(1000, 70));
        topPanel.setLayout(null);
        
        JLabel filterLabel = new JLabel("Bộ lọc:");
        filterLabel.setFont(new Font("Arial", Font.PLAIN, 13));
        filterLabel.setBounds(20, 25, 70, 20);
        topPanel.add(filterLabel);
        
        JLabel dateRangeLabel = new JLabel("Ngày áp dụng:");
        dateRangeLabel.setFont(new Font("Arial", Font.PLAIN, 13));
        dateRangeLabel.setBounds(100, 25, 130, 20);
        topPanel.add(dateRangeLabel);
        
        // Filter start date
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MONTH, -1); // Mặc định từ 1 tháng trước
        Date defaultStartDate = cal.getTime();
        
        SpinnerDateModel filterStartDateModel = new SpinnerDateModel(defaultStartDate, null, null, Calendar.DAY_OF_MONTH);
        filterStartDateSpinner = new JSpinner(filterStartDateModel);
        JSpinner.DateEditor filterStartDateEditor = new JSpinner.DateEditor(filterStartDateSpinner, "dd/MM/yyyy");
        filterStartDateSpinner.setEditor(filterStartDateEditor);
        filterStartDateSpinner.setBounds(230, 18, 150, 35);
        filterStartDateSpinner.setBorder(BorderFactory.createLineBorder(new Color(148, 130, 255), 1));
        filterStartDateSpinner.addChangeListener(e -> {
            if (!isRefreshing) {
                loadKhuyenMaiList();
            }
        });
        topPanel.add(filterStartDateSpinner);
        
        JLabel dashLabel = new JLabel("-");
        dashLabel.setFont(new Font("Arial", Font.PLAIN, 13));
        dashLabel.setBounds(390, 25, 10, 20);
        topPanel.add(dashLabel);
        
        // Filter end date
        SpinnerDateModel filterEndDateModel = new SpinnerDateModel(new Date(), null, null, Calendar.DAY_OF_MONTH);
        filterEndDateSpinner = new JSpinner(filterEndDateModel);
        JSpinner.DateEditor filterEndDateEditor = new JSpinner.DateEditor(filterEndDateSpinner, "dd/MM/yyyy");
        filterEndDateSpinner.setEditor(filterEndDateEditor);
        filterEndDateSpinner.setBounds(410, 18, 150, 35);
        filterEndDateSpinner.setBorder(BorderFactory.createLineBorder(new Color(148, 130, 255), 1));
        filterEndDateSpinner.addChangeListener(e -> {
            if (!isRefreshing) {
                loadKhuyenMaiList();
            }
        });
        topPanel.add(filterEndDateSpinner);
        
        // Refresh button
        JButton refreshButton = new JButton("Làm mới");
        refreshButton.setBackground(new Color(130, 120, 235));
        refreshButton.setForeground(Color.WHITE);
        refreshButton.setBounds(585, 18, 115, 35);
        refreshButton.setFocusPainted(false);
        refreshButton.setBorderPainted(false);
        refreshButton.addActionListener(e -> refreshData());
        topPanel.add(refreshButton);
        
        // Search field - moved to the left
        searchField = new JTextField();
        searchField.setBounds(720, 18, 200, 35);
        searchField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(148, 130, 255), 2),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)));
        
        // Xóa document listener để tránh tự động tìm kiếm quá nhiều lần
        // Chỉ tìm kiếm khi nhấn nút tìm kiếm hoặc Enter
        searchField.addActionListener(e -> loadKhuyenMaiList());
        
        topPanel.add(searchField);
        
        // Search button - small button right next to search field
        JButton searchButton = new JButton("\uD83D\uDD0D");
        searchButton.setBounds(925, 18, 40, 35);
        searchButton.setFocusPainted(false);
        searchButton.setBorderPainted(false);
        searchButton.setBackground(new Color(130, 120, 235));
        searchButton.setForeground(Color.WHITE);
        searchButton.addActionListener(e -> loadKhuyenMaiList());
        topPanel.add(searchButton);
        
        // MAIN PANEL
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout(0, 0));
        mainPanel.setBackground(Color.WHITE);
        
        // Table Panel
        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.setBackground(new Color(5, 195, 174));
        tablePanel.setPreferredSize(new Dimension(1000, 320));
        
        // Table setup
        String[] columnNames = {"Mã KM", "Tên Chương Trình", "Tỷ Lệ", "Tối Thiểu", 
                "KM Tối Đa", "Bắt Đầu", "Kết Thúc", "Loại KM"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        discountTable = new JTable(tableModel);
        discountTable.setRowHeight(30);
        discountTable.getTableHeader().setBackground(new Color(5, 195, 174));
        discountTable.getTableHeader().setForeground(Color.BLACK);
        discountTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));
        discountTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        discountTable.setGridColor(Color.LIGHT_GRAY);
        discountTable.setShowGrid(false);
        discountTable.setIntercellSpacing(new Dimension(0, 0));
        discountTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int selectedRow = discountTable.getSelectedRow();
                if (selectedRow >= 0) {
                    displaySelectedKhuyenMai(selectedRow);
                }
            }
        });
        
        JScrollPane scrollPane = new JScrollPane(discountTable);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        tablePanel.add(scrollPane, BorderLayout.CENTER);
        
        // Form Panel
        JPanel formPanel = new JPanel();
        formPanel.setLayout(null);
        formPanel.setBackground(Color.WHITE);
        
        // Program name
        JLabel programLabel = new JLabel("Tên chương trình:");
        programLabel.setFont(new Font("Arial", Font.PLAIN, 13));
        programLabel.setBounds(20, 10, 170, 20);
        formPanel.add(programLabel);
        
        programNameField = new JTextField();
        programNameField.setBounds(20, 35, 700, 35);
        programNameField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(148, 130, 255), 2),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)));
        formPanel.add(programNameField);
        
        // Minimum amount
        JLabel minAmountLabel = new JLabel("Số tiền tối thiểu:");
        minAmountLabel.setFont(new Font("Arial", Font.PLAIN, 13));
        minAmountLabel.setBounds(20, 75, 150, 20);
        formPanel.add(minAmountLabel);
        
        minAmountField = new JTextField();
        minAmountField.setBounds(20, 100, 220, 35);
        minAmountField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(148, 130, 255), 2),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)));
        formPanel.add(minAmountField);
        
        // Maximum discount
        JLabel maxDiscountLabel = new JLabel("Khuyến mãi tối đa:");
        maxDiscountLabel.setFont(new Font("Arial", Font.PLAIN, 13));
        maxDiscountLabel.setBounds(260, 75, 150, 20);
        formPanel.add(maxDiscountLabel);
        
        maxDiscountField = new JTextField();
        maxDiscountField.setBounds(260, 100, 220, 35);
        maxDiscountField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(148, 130, 255), 2),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)));
        formPanel.add(maxDiscountField);
        
        // Discount rate
        JLabel discountRateLabel = new JLabel("Tỷ lệ khuyến mãi:");
        discountRateLabel.setFont(new Font("Arial", Font.PLAIN, 13));
        discountRateLabel.setBounds(500, 75, 150, 20);
        formPanel.add(discountRateLabel);
        
        discountRateField = new JTextField();
        discountRateField.setBounds(500, 100, 220, 35);
        discountRateField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(148, 130, 255), 2),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)));
        formPanel.add(discountRateField);
        
        // Start date
        JLabel startDateLabel = new JLabel("Ngày bắt đầu:");
        startDateLabel.setFont(new Font("Arial", Font.PLAIN, 13));
        startDateLabel.setBounds(20, 140, 150, 20);
        formPanel.add(startDateLabel);
        
        SpinnerDateModel startDateModel = new SpinnerDateModel(new Date(), null, null, Calendar.DAY_OF_MONTH);
        startDateSpinner = new JSpinner(startDateModel);
        JSpinner.DateEditor startDateEditor = new JSpinner.DateEditor(startDateSpinner, "dd/MM/yyyy");
        startDateSpinner.setEditor(startDateEditor);
        startDateSpinner.setBounds(20, 165, 220, 35);
        startDateSpinner.setBorder(BorderFactory.createLineBorder(new Color(148, 130, 255), 1));
        formPanel.add(startDateSpinner);
        
        // End date
        JLabel endDateLabel = new JLabel("Ngày kết thúc:");
        endDateLabel.setFont(new Font("Arial", Font.PLAIN, 13));
        endDateLabel.setBounds(260, 140, 150, 20);
        formPanel.add(endDateLabel);
        
        SpinnerDateModel endDateModel = new SpinnerDateModel(new Date(), null, null, Calendar.DAY_OF_MONTH);
        endDateSpinner = new JSpinner(endDateModel);
        JSpinner.DateEditor endDateEditor = new JSpinner.DateEditor(endDateSpinner, "dd/MM/yyyy");
        endDateSpinner.setEditor(endDateEditor);
        endDateSpinner.setBounds(260, 165, 220, 35);
        endDateSpinner.setBorder(BorderFactory.createLineBorder(new Color(148, 130, 255), 1));
        formPanel.add(endDateSpinner);
        
        // Discount type
        JLabel discountTypeLabel = new JLabel("Loại khuyến mãi:");
        discountTypeLabel.setFont(new Font("Arial", Font.PLAIN, 13));
        discountTypeLabel.setBounds(500, 140, 150, 20);
        formPanel.add(discountTypeLabel);
        
        discountTypeComboBox = new JComboBox<>();
        discountTypeComboBox.setBounds(500, 165, 220, 35);
        discountTypeComboBox.setBorder(BorderFactory.createLineBorder(new Color(148, 130, 255), 1));
        formPanel.add(discountTypeComboBox);
        
        // Buttons - arranged closer together
        JButton addButton = new JButton("Thêm");
        addButton.setBackground(new Color(130, 120, 235));
        addButton.setForeground(Color.WHITE);
        addButton.setBounds(750, 65, 100, 45);
        addButton.setFocusPainted(false);
        addButton.setBorderPainted(false);
        addButton.setBorder(new EmptyBorder(0, 0, 0, 0));
        addButton.addActionListener(e -> addKhuyenMai());
        formPanel.add(addButton);
        
        JButton updateButton = new JButton("Cập Nhật");
        updateButton.setBackground(new Color(130, 120, 235));
        updateButton.setForeground(Color.WHITE);
        updateButton.setBounds(880, 65, 100, 45);
        updateButton.setFocusPainted(false);
        updateButton.setBorderPainted(false);
        updateButton.setBorder(new EmptyBorder(0, 0, 0, 0));
        updateButton.addActionListener(e -> updateKhuyenMai());
        formPanel.add(updateButton);
        
        JButton deleteButton = new JButton("Xóa");
        deleteButton.setBackground(new Color(130, 120, 235));
        deleteButton.setForeground(Color.WHITE);
        deleteButton.setBounds(750, 130, 100, 45);
        deleteButton.setFocusPainted(false);
        deleteButton.setBorderPainted(false);
        deleteButton.setBorder(new EmptyBorder(0, 0, 0, 0));
        deleteButton.addActionListener(e -> deleteKhuyenMai());
        formPanel.add(deleteButton);
        
        JButton cancelButton = new JButton("Hủy");
        cancelButton.setBackground(new Color(130, 120, 235));
        cancelButton.setForeground(Color.WHITE);
        cancelButton.setBounds(880, 130, 100, 45);
        cancelButton.setFocusPainted(false);
        cancelButton.setBorderPainted(false);
        cancelButton.setBorder(new EmptyBorder(0, 0, 0, 0));
        cancelButton.addActionListener(e -> clearForm());
        formPanel.add(cancelButton);
        
        // Add to main panel
        mainPanel.add(tablePanel, BorderLayout.NORTH);
        mainPanel.add(formPanel, BorderLayout.CENTER);
        
        // Add the panels to the frame
        add(topPanel, BorderLayout.NORTH);
        add(mainPanel, BorderLayout.CENTER);
        
        // Khởi tạo dữ liệu cho form
        initializeData();
    }
    
    /**
     * Khởi tạo dữ liệu ban đầu cho form
     */
    private void initializeData() {
        try {
            // Load danh sách loại khuyến mãi
            loadLoaiKhuyenMai();
            
            // Load danh sách khuyến mãi
            loadKhuyenMaiList();
            
            System.out.println("Form initialized successfully");
        } catch (Exception e) {
            System.err.println("Error during initialization: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    /**
     * Load danh sách loại khuyến mãi vào combobox
     */
    private void loadLoaiKhuyenMai() {
        try {
            // Lấy danh sách loại khuyến mãi từ controller
            List<LoaiKM> loaiKMList = khuyenMaiController.layDanhSachLoaiKM();
            
            // Xóa tất cả item hiện tại
            discountTypeComboBox.removeAllItems();
            
            // Thêm các loại khuyến mãi vào combobox
            for (LoaiKM loaiKM : loaiKMList) {
                discountTypeComboBox.addItem(loaiKM);
            }
            
            // Chọn loại đầu tiên
            if (discountTypeComboBox.getItemCount() > 0) {
                discountTypeComboBox.setSelectedIndex(0);
            }
            
            System.out.println("Loaded " + loaiKMList.size() + " discount types");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi khi tải danh sách loại khuyến mãi: " + e.getMessage(), 
                    "Lỗi", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }
    
    /**
     * Load danh sách khuyến mãi vào bảng
     */
    private void loadKhuyenMaiList() {
        try {
            // Lấy thông tin tìm kiếm và lọc
            String searchText = searchField.getText().trim();
            java.util.Date startDateUtil = (java.util.Date) filterStartDateSpinner.getValue();
            java.util.Date endDateUtil = (java.util.Date) filterEndDateSpinner.getValue();
            
            // Debug thông tin ngày
            System.out.println("Start date (util): " + startDateUtil);
            System.out.println("End date (util): " + endDateUtil);
            
            // Chuyển đổi dates thành java.sql.Date
            java.sql.Date startDate = new java.sql.Date(startDateUtil.getTime());
            java.sql.Date endDate = new java.sql.Date(endDateUtil.getTime());
            
            // Debug thông tin ngày sau khi chuyển đổi
            System.out.println("Start date (sql): " + startDate);
            System.out.println("End date (sql): " + endDate);
            
            List<KhuyenMai> khuyenMaiList;
            
            // Nếu không có text tìm kiếm thì lấy tất cả dữ liệu
            if (searchText.isEmpty()) {
                khuyenMaiList = khuyenMaiController.layDanhSachKhuyenMai();
                System.out.println("Loaded all promotions without filters");
            } else {
                // Nếu có text tìm kiếm, sử dụng timKhuyenMai với lọc theo ngày
                khuyenMaiList = khuyenMaiController.timKhuyenMai(searchText, startDate, endDate);
                System.out.println("Loaded filtered promotions with search: [" + searchText + 
                    "] and date range: [" + startDate + " to " + endDate + "]");
            }
            
            // Xóa tất cả dữ liệu hiện tại trong bảng
            tableModel.setRowCount(0);
            
            // Thêm dữ liệu mới vào bảng
            for (KhuyenMai km : khuyenMaiList) {
                tableModel.addRow(new Object[]{
                    km.getMaKM(),
                    km.getTenChuongTrinh(),
                    km.getTyLe(),
                    km.getToiThieu(),
                    km.getKmToiDa(),
                    displayDateFormat.format(km.getBatDau()),
                    displayDateFormat.format(km.getKetThuc()),
                    km.getLoaiKM()
                });
            }
            
            // Log số lượng khuyến mãi đã tải
            System.out.println("Total loaded promotions: " + khuyenMaiList.size());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi khi tải danh sách khuyến mãi: " + e.getMessage(), 
                    "Lỗi", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }
    
    /**
     * Hiển thị thông tin khuyến mãi được chọn lên form
     */
    private void displaySelectedKhuyenMai(int selectedRow) {
        try {
            // Lấy dữ liệu từ hàng được chọn
            String maKM = tableModel.getValueAt(selectedRow, 0).toString();
            String tenChuongTrinh = tableModel.getValueAt(selectedRow, 1).toString();
            int tyLe = Integer.parseInt(tableModel.getValueAt(selectedRow, 2).toString());
            int toiThieu = Integer.parseInt(tableModel.getValueAt(selectedRow, 3).toString());
            int kmToiDa = Integer.parseInt(tableModel.getValueAt(selectedRow, 4).toString());
            
            // Parse date từ String sang Date
            String batDauStr = tableModel.getValueAt(selectedRow, 5).toString();
            String ketThucStr = tableModel.getValueAt(selectedRow, 6).toString();
            Date batDau = displayDateFormat.parse(batDauStr);
            Date ketThuc = displayDateFormat.parse(ketThucStr);
            
            String loaiKMText = tableModel.getValueAt(selectedRow, 7).toString();
            
            // Hiển thị dữ liệu lên form
            programNameField.setText(tenChuongTrinh);
            minAmountField.setText(String.valueOf(toiThieu));
            maxDiscountField.setText(String.valueOf(kmToiDa));
            discountRateField.setText(String.valueOf(tyLe));
            startDateSpinner.setValue(batDau);
            endDateSpinner.setValue(ketThuc);
            
            // Debug thông tin loại khuyến mãi
            System.out.println("Selected discount type from table: " + loaiKMText);
            
            // Chọn loại khuyến mãi phù hợp trong combobox
            boolean found = false;
            for (int i = 0; i < discountTypeComboBox.getItemCount(); i++) {
                LoaiKM item = (LoaiKM) discountTypeComboBox.getItemAt(i);
                System.out.println("Comparing with combobox item [" + i + "]: " + item.toString());
                
                if (item.getTenLoai().equals(loaiKMText) || 
                    loaiKMText.contains(item.getTenLoai()) ||
                    loaiKMText.contains(String.valueOf(item.getMaLoai()))) {
                    
                    discountTypeComboBox.setSelectedIndex(i);
                    System.out.println("Match found! Selected index: " + i);
                    found = true;
                    break;
                }
            }
            
            if (!found) {
                System.out.println("No matching discount type found in combobox!");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi khi hiển thị thông tin khuyến mãi: " + e.getMessage(), 
                    "Lỗi", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    /**
     * Thêm khuyến mãi mới
     */
    private void addKhuyenMai() {
        try {
            // Validate form
            validateForm();
            
            // Lấy dữ liệu từ form
            String tenChuongTrinh = programNameField.getText().trim();
            int tyLe = Integer.parseInt(discountRateField.getText().trim());
            int toiThieu = Integer.parseInt(minAmountField.getText().trim());
            int kmToiDa = Integer.parseInt(maxDiscountField.getText().trim());
            Date batDau = (Date) startDateSpinner.getValue();
            Date ketThuc = (Date) endDateSpinner.getValue();
            LoaiKM loaiKM = (LoaiKM) discountTypeComboBox.getSelectedItem();
            int maLoai = loaiKM.getMaLoai();
            
            // Convert java.util.Date to java.sql.Date
            java.sql.Date sqlBatDau = new java.sql.Date(batDau.getTime());
            java.sql.Date sqlKetThuc = new java.sql.Date(ketThuc.getTime());
            
            // Gọi controller để thêm khuyến mãi
            boolean success = khuyenMaiController.themKhuyenMai(
                 tenChuongTrinh,
                 tyLe,
                 toiThieu,
                 kmToiDa,
                 sqlBatDau,
                 sqlKetThuc,
                 maLoai
            );
            
            System.out.println("addKhuyenMai result: " + success);
            
            if (success) {
                // Nếu thực sự thêm thành công (có dòng bị ảnh hưởng)
                JOptionPane.showMessageDialog(this, "Thêm khuyến mãi thành công",
                        "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                
                // Làm mới danh sách sau khi thêm
                loadKhuyenMaiList();
                clearForm();
            } else {
                JOptionPane.showMessageDialog(this, 
                        "Không thể thêm khuyến mãi. Vui lòng kiểm tra lại thông tin.", 
                        "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
 
        } catch (SQLException e) {
            // Hiển thị lỗi SQL cụ thể
            JOptionPane.showMessageDialog(this,
                    "Lỗi SQL khi thêm khuyến mãi: " + e.getMessage(),
                    "Lỗi Database", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this,
                "Lỗi định dạng số: Vui lòng nhập số nguyên cho các trường tỷ lệ, tối thiểu và tối đa.",
                "Lỗi", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi khi thêm khuyến mãi: " + e.getMessage(),
                    "Lỗi", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    /**
     * Xóa khuyến mãi
     */
    private void deleteKhuyenMai() {
        try {
            // Kiểm tra xem có hàng nào được chọn không
            int selectedRow = discountTable.getSelectedRow();
            if (selectedRow < 0) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn khuyến mãi cần xóa", 
                        "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            
            // Lấy mã khuyến mãi từ hàng đã chọn
            String maKM = tableModel.getValueAt(selectedRow, 0).toString();
            
            // Xác nhận xóa
            int confirm = JOptionPane.showConfirmDialog(this, 
                    "Bạn có chắc chắn muốn xóa khuyến mãi này không?", 
                    "Xác nhận xóa", 
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE);
            
            if (confirm != JOptionPane.YES_OPTION) {
                return;
            }
            
            // Gọi controller để xóa khuyến mãi
            // Sẽ throw SQLException nếu có lỗi từ database trigger
            boolean success = khuyenMaiController.xoaKhuyenMai(maKM);
            
            System.out.println("deleteKhuyenMai result: " + success);
            
            if (success) {
                // Nếu thực sự xóa thành công (có dòng bị ảnh hưởng)
                JOptionPane.showMessageDialog(this, "Xóa khuyến mãi thành công", 
                        "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                
                // Làm mới danh sách sau khi xóa
                loadKhuyenMaiList();
                clearForm();
            } else {
                // Nếu không có dòng nào bị xóa
                JOptionPane.showMessageDialog(this, 
                        "Không thể xóa khuyến mãi. Khuyến mãi có thể đang diễn ra hoặc có liên kết đến hóa đơn.",
                        "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException e) {
            // Hiển thị lỗi SQL (thường từ trigger)
            JOptionPane.showMessageDialog(this, 
                    "Lỗi SQL: " + e.getMessage(), 
                    "Lỗi", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        } catch (Exception e) {
            // Hiển thị thông báo lỗi chi tiết từ exception
            JOptionPane.showMessageDialog(this, "Lỗi khi xóa khuyến mãi: " + e.getMessage(), 
                    "Lỗi", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }
    
    /**
     * Cập nhật khuyến mãi
     */
    private void updateKhuyenMai() {
        try {
            // Kiểm tra xem có hàng nào được chọn không
            int selectedRow = discountTable.getSelectedRow();
            if (selectedRow < 0) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn khuyến mãi cần cập nhật", 
                        "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            
            // Xác nhận cập nhật
            int confirm = JOptionPane.showConfirmDialog(this, 
                    "Bạn có chắc chắn muốn cập nhật khuyến mãi này không?", 
                    "Xác nhận cập nhật", 
                    JOptionPane.YES_NO_OPTION);
            
            if (confirm != JOptionPane.YES_OPTION) {
                return;
            }
            
            // Validate form
            validateForm();
            
            // Lấy mã khuyến mãi từ hàng đã chọn
            String maKM = tableModel.getValueAt(selectedRow, 0).toString();
            
            // Lấy dữ liệu từ form
            String tenChuongTrinh = programNameField.getText().trim();
            int tyLe = Integer.parseInt(discountRateField.getText().trim());
            int toiThieu = Integer.parseInt(minAmountField.getText().trim());
            int kmToiDa = Integer.parseInt(maxDiscountField.getText().trim());
            Date batDau = (Date) startDateSpinner.getValue();
            Date ketThuc = (Date) endDateSpinner.getValue();
            LoaiKM loaiKM = (LoaiKM) discountTypeComboBox.getSelectedItem();
            int maLoai = loaiKM.getMaLoai();
            
            // Convert java.util.Date to java.sql.Date
            java.sql.Date sqlBatDau = new java.sql.Date(batDau.getTime());
            java.sql.Date sqlKetThuc = new java.sql.Date(ketThuc.getTime());
            
            // Gọi controller để cập nhật khuyến mãi
            // Trigger sẽ kiểm tra các điều kiện và ném lỗi nếu không thể cập nhật
            boolean success = khuyenMaiController.suaKhuyenMai(
                maKM, 
                tenChuongTrinh, 
                tyLe, 
                toiThieu, 
                kmToiDa, 
                sqlBatDau, 
                sqlKetThuc, 
                maLoai
            );
            
            System.out.println("updateKhuyenMai result: " + success);
            
            if (success) {
                // Nếu thực sự cập nhật thành công (có dòng bị ảnh hưởng)
                JOptionPane.showMessageDialog(this, "Cập nhật khuyến mãi thành công", 
                        "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                
                // Làm mới danh sách sau khi cập nhật
                loadKhuyenMaiList();
                
                // Chọn lại hàng vừa cập nhật
                for (int i = 0; i < tableModel.getRowCount(); i++) {
                    String rowMaKM = tableModel.getValueAt(i, 0).toString();
                    if (rowMaKM.equals(maKM)) {
                        discountTable.setRowSelectionInterval(i, i);
                        discountTable.scrollRectToVisible(discountTable.getCellRect(i, 0, true));
                        displaySelectedKhuyenMai(i);
                        break;
                    }
                }
            } else {
                // Nếu không có dòng nào bị cập nhật
                JOptionPane.showMessageDialog(this, 
                        "Không thể cập nhật khuyến mãi. Khuyến mãi có thể đang diễn ra.", 
                        "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException e) {
            // Hiển thị lỗi SQL (thường từ trigger)
            JOptionPane.showMessageDialog(this, 
                    "Lỗi SQL: " + e.getMessage(), 
                    "Lỗi", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, 
                "Lỗi định dạng số: Vui lòng nhập số nguyên cho các trường tỷ lệ, tối thiểu và tối đa.", 
                "Lỗi", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi khi cập nhật khuyến mãi: " + e.getMessage(), 
                    "Lỗi", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    /**
     * Làm mới dữ liệu
     */
    private void refreshData() {
        try {
            isRefreshing = true;
            
            // Xóa text tìm kiếm
            searchField.setText("");
            
            // Reset bộ lọc ngày về mặc định
            Calendar cal = Calendar.getInstance();
            cal.add(Calendar.MONTH, -1);
            filterStartDateSpinner.setValue(cal.getTime());
            filterEndDateSpinner.setValue(new Date());
            
            isRefreshing = false;
            
            // Tải lại danh sách khuyến mãi (sẽ dùng layDanhSachKhuyenMai() vì searchText đã bị xóa)
            loadKhuyenMaiList();
            
            // Clear form
            clearForm();
            
            // Thông báo
            System.out.println("Data refreshed successfully");
            JOptionPane.showMessageDialog(this, "Dữ liệu đã được làm mới", 
                    "Thông báo", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi khi làm mới dữ liệu: " + e.getMessage(), 
                    "Lỗi", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }
    
    /**
     * Xóa trống form
     */
    private void clearForm() {
        programNameField.setText("");
        minAmountField.setText("");
        maxDiscountField.setText("");
        discountRateField.setText("");
        startDateSpinner.setValue(new Date());
        endDateSpinner.setValue(new Date());
        if (discountTypeComboBox.getItemCount() > 0) {
            discountTypeComboBox.setSelectedIndex(0);
        }
        discountTable.clearSelection();
    }
    
    /**
     * Validate form trước khi thêm/cập nhật
     */
    private void validateForm() throws Exception {
        // Kiểm tra tên chương trình
        if (programNameField.getText().trim().isEmpty()) {
            throw new Exception("Tên chương trình không được để trống");
        }
        
        // Kiểm tra số tiền tối thiểu
        try {
            int toiThieu = Integer.parseInt(minAmountField.getText().trim());
            if (toiThieu < 0) {
                throw new Exception("Số tiền tối thiểu phải lớn hơn hoặc bằng 0");
            }
        } catch (NumberFormatException e) {
            throw new Exception("Số tiền tối thiểu phải là số nguyên");
        }
        
        // Kiểm tra khuyến mãi tối đa
        try {
            int kmToiDa = Integer.parseInt(maxDiscountField.getText().trim());
            if (kmToiDa < 0) {
                throw new Exception("Khuyến mãi tối đa phải lớn hơn hoặc bằng 0");
            }
        } catch (NumberFormatException e) {
            throw new Exception("Khuyến mãi tối đa phải là số nguyên");
        }
        
        // Kiểm tra tỷ lệ khuyến mãi
        try {
            int tyLe = Integer.parseInt(discountRateField.getText().trim());
            if (tyLe < 0) {
                throw new Exception("Tỷ lệ khuyến mãi phải lớn hơn hoặc bằng 0");
            }
        } catch (NumberFormatException e) {
            throw new Exception("Tỷ lệ khuyến mãi phải là số nguyên");
        }
        
        // Kiểm tra ngày bắt đầu và kết thúc
        Date batDau = (Date) startDateSpinner.getValue();
        Date ketThuc = (Date) endDateSpinner.getValue();
        
        if (batDau.after(ketThuc)) {
            throw new Exception("Ngày bắt đầu phải trước ngày kết thúc");
        }
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                // Set look and feel to system
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                e.printStackTrace();
            }
            
            KhuyenMaiForm form = new KhuyenMaiForm();
            form.setVisible(true);
        });
    }
}
