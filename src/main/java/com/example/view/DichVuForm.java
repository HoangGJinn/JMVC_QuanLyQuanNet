package com.example.view;

import com.example.controller.DichVuController;
import com.example.model.*;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.ListSelectionModel;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

public class DichVuForm extends JFrame {
    private JTable tableDoAn;
    private JTable tableDoUong;
    private JTable tableTheCao;
    private DichVuController dichVuController;
    private DefaultTableModel modelDoAn;
    private DefaultTableModel modelDoUong;
    private DefaultTableModel modelTheCao;

    // Declare UI components as class fields
    private JTextField txtTenDoAn;
    private JTextField txtDonGiaDoAn;
    private JComboBox<String> cboTrangThaiDoAn;
    private JCheckBox chkBestSellerDoAn;

    private JTextField txtTenDoUong;
    private JTextField txtDonGiaDoUong;
    private JComboBox<String> cboTrangThaiDoUong;
    private JCheckBox chkBestSellerDoUong;

    private JTextField txtLoaiThe;
    private JTextField txtMenhGia;

    // Store service IDs separately
    private Map<Integer, String> doAnIDs = new HashMap<>();
    private Map<Integer, String> doUongIDs = new HashMap<>();
    private Map<Integer, String> theoCaoIDs = new HashMap<>();

    // Store currently selected IDs
    private String selectedDoAnID = null;
    private String selectedDoUongID = null;
    private String selectedTheCaoID = null;

    public DichVuForm() {
        setTitle("Quản lý dịch vụ");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1200, 600);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(1, 3, 10, 0));

        // Khởi tạo controller
        dichVuController = new DichVuController();

        // Panel Đồ ăn
        JPanel panelDoAn = createServicePanel(
                "Dịch vụ đồ ăn",
                new String[]{"ID", "Tên Đồ Ăn", "Đơn Giá", "Trạng Thái", "BS"},
                "Tên đồ ăn:", "Nhập tên đồ ăn", true,
                1 // Panel type: 1 = food
        );
        tableDoAn = (JTable) ((JScrollPane) panelDoAn.getComponent(1)).getViewport().getView();
        modelDoAn = (DefaultTableModel) tableDoAn.getModel();
        // Make sure we can select rows
        tableDoAn.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tableDoAn.setRowSelectionAllowed(true);
        add(panelDoAn);

        // Panel Đồ uống
        JPanel panelDoUong = createServicePanel(
                "Dịch vụ đồ uống",
                new String[]{"ID", "Tên Đồ Uống", "Đơn Giá", "Trạng Thái", "BS"},
                "Tên đồ uống:", "Nhập tên đồ uống", true,
                2 // Panel type: 2 = drink
        );
        tableDoUong = (JTable) ((JScrollPane) panelDoUong.getComponent(1)).getViewport().getView();
        modelDoUong = (DefaultTableModel) tableDoUong.getModel();
        // Make sure we can select rows
        tableDoUong.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tableDoUong.setRowSelectionAllowed(true);
        add(panelDoUong);

        // Panel Thẻ cào
        JPanel panelTheCao = createServicePanel(
                "Dịch vụ thẻ cào",
                new String[]{"ID", "Loại Thẻ", "Mệnh Giá"},
                "Loại thẻ:", "Nhập loại thẻ", false,
                3 // Panel type: 3 = card
        );
        tableTheCao = (JTable) ((JScrollPane) panelTheCao.getComponent(1)).getViewport().getView();
        modelTheCao = (DefaultTableModel) tableTheCao.getModel();
        // Make sure we can select rows
        tableTheCao.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tableTheCao.setRowSelectionAllowed(true);
        add(panelTheCao);

        // Load dữ liệu từ database
        loadAllData();
    }

    private JPanel createServicePanel(String title, String[] tableHeaders, String label1, String placeholder1, boolean hasBestSeller, int panelType) {
        final JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout(0, 5));
        panel.setBackground(new Color(242, 242, 242));
        panel.setBorder(BorderFactory.createTitledBorder(
        BorderFactory.createLineBorder(Color.LIGHT_GRAY), title, TitledBorder.LEFT, TitledBorder.TOP, new Font("Arial", Font.BOLD, 22)));

        // Top: Search
        JPanel searchPanel = new JPanel(new BorderLayout(5, 0));
        searchPanel.setBackground(new Color(242, 242, 242));
        final JTextField searchField = new JTextField("Tìm kiếm");
        searchField.setFont(new Font("Arial", Font.PLAIN, 16));
        searchField.setBorder(BorderFactory.createLineBorder(new Color(180, 150, 255), 2));
        final JButton searchBtn = new JButton();
        searchBtn.setBackground(Color.WHITE);
        searchBtn.setIcon(UIManager.getIcon("FileView.fileIcon"));
        searchPanel.add(searchField, BorderLayout.CENTER);
        searchPanel.add(searchBtn, BorderLayout.EAST);
        searchPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 0, 10));
        panel.add(searchPanel, BorderLayout.NORTH);

        // Center: Table
        DefaultTableModel tableModel = new DefaultTableModel(new Object[0][tableHeaders.length], tableHeaders) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        final JTable table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setPreferredSize(new Dimension(350, 220));
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

        // Name field
        gbc.gridx = 0; gbc.gridy = 0;
        JLabel label1Comp = new JLabel(label1);
        label1Comp.setFont(new Font("Arial", Font.PLAIN, 14));
        formPanel.add(label1Comp, gbc);
        gbc.gridx = 1;
        final JTextField txtName = new JTextField(12);
        txtName.setFont(new Font("Arial", Font.PLAIN, 15));
        txtName.setToolTipText(placeholder1);
        txtName.setBorder(BorderFactory.createLineBorder(new Color(180, 150, 255), 2));
        formPanel.add(txtName, gbc);

        // Price field
        gbc.gridx = 0; gbc.gridy = 1;
        JLabel labelDonGia = new JLabel("Đơn giá:");
        labelDonGia.setFont(new Font("Arial", Font.PLAIN, 14));
        formPanel.add(labelDonGia, gbc);
        gbc.gridx = 1;
        final JTextField txtPrice = new JTextField(12);
        txtPrice.setFont(new Font("Arial", Font.PLAIN, 15));
        txtPrice.setBorder(BorderFactory.createLineBorder(new Color(180, 150, 255), 2));
        formPanel.add(txtPrice, gbc);

        // Status field (for food and drinks)
        final JComboBox<String> cboStatus;
        if (panelType == 1 || panelType == 2) {
            gbc.gridx = 0; gbc.gridy = 2;
            JLabel labelStatus = new JLabel("Trạng thái:");
            labelStatus.setFont(new Font("Arial", Font.PLAIN, 14));
            formPanel.add(labelStatus, gbc);
            gbc.gridx = 1;
            cboStatus = new JComboBox<>(new String[]{"Còn hàng", "Hết hàng"});
            cboStatus.setFont(new Font("Arial", Font.PLAIN, 14));
            formPanel.add(cboStatus, gbc);
        } else {
            cboStatus = null;
        }

        // Best seller checkbox
        final JCheckBox chkBestSeller;
        if (hasBestSeller) {
            gbc.gridx = 2; gbc.gridy = 0;
            chkBestSeller = new JCheckBox("Best seller");
            chkBestSeller.setBackground(new Color(242, 242, 242));
            formPanel.add(chkBestSeller, gbc);
        } else {
            chkBestSeller = null;
        }

        bottomPanel.add(formPanel);

        // Buttons
        JPanel btnPanel = new JPanel(new GridLayout(2, 2, 10, 10));
        btnPanel.setBackground(new Color(242, 242, 242));
        final JButton btnThem = new JButton("Thêm");
        final JButton btnSua = new JButton("Sửa");
        final JButton btnXoa = new JButton("Xóa");
        final JButton btnHuy = new JButton("Hủy");

        Color btnColor = new Color(140, 120, 255);
        btnThem.setBackground(btnColor);
        btnSua.setBackground(btnColor);
        btnXoa.setBackground(btnColor);
        btnHuy.setBackground(btnColor);
        btnThem.setForeground(Color.WHITE);
        btnSua.setForeground(Color.WHITE);
        btnXoa.setForeground(Color.WHITE);
        btnHuy.setForeground(Color.WHITE);
        btnThem.setFont(new Font("Arial", Font.BOLD, 16));
        btnSua.setFont(new Font("Arial", Font.BOLD, 16));
        btnXoa.setFont(new Font("Arial", Font.BOLD, 16));
        btnHuy.setFont(new Font("Arial", Font.BOLD, 16));

        btnPanel.add(btnThem);
        btnPanel.add(btnSua);
        btnPanel.add(btnXoa);
        btnPanel.add(btnHuy);
        btnPanel.setMaximumSize(new Dimension(300, 60));
        bottomPanel.add(Box.createVerticalStrut(10));
        bottomPanel.add(btnPanel);

        panel.add(bottomPanel, BorderLayout.SOUTH);

        // Store references to UI components based on panel type
        if (panelType == 1) { // Food panel
            txtTenDoAn = txtName;
            txtDonGiaDoAn = txtPrice;
            cboTrangThaiDoAn = cboStatus;
            chkBestSellerDoAn = chkBestSeller;
        } else if (panelType == 2) { // Drink panel
            txtTenDoUong = txtName;
            txtDonGiaDoUong = txtPrice;
            cboTrangThaiDoUong = cboStatus;
            chkBestSellerDoUong = chkBestSeller;
        } else if (panelType == 3) { // Card panel
            txtLoaiThe = txtName;
            txtMenhGia = txtPrice;
        }

        // Add table selection listener
        table.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow != -1) {
                    // Get the ID from the first column
                    String maDV = table.getValueAt(selectedRow, 0).toString();

                    if (panelType == 1) { // Food
                        selectedDoAnID = maDV;
                        // Populate text fields with data from table
                        txtName.setText(table.getValueAt(selectedRow, 1).toString());
                        txtPrice.setText(table.getValueAt(selectedRow, 2).toString().replaceAll("[^0-9]", ""));

                        if (cboStatus != null) {
                            cboStatus.setSelectedItem(table.getValueAt(selectedRow, 3).toString());
                        }
                        if (chkBestSeller != null) {
                            chkBestSeller.setSelected(table.getValueAt(selectedRow, 4).toString().equals("Có"));
                        }
                    } else if (panelType == 2) { // Drink
                        selectedDoUongID = maDV;
                        // Populate text fields with data from table
                        txtName.setText(table.getValueAt(selectedRow, 1).toString());
                        txtPrice.setText(table.getValueAt(selectedRow, 2).toString().replaceAll("[^0-9]", ""));

                        if (cboStatus != null) {
                            cboStatus.setSelectedItem(table.getValueAt(selectedRow, 3).toString());
                        }
                        if (chkBestSeller != null) {
                            chkBestSeller.setSelected(table.getValueAt(selectedRow, 4).toString().equals("Có"));
                        }
                    } else if (panelType == 3) { // Card
                        selectedTheCaoID = maDV;
                        // Populate text fields with data from table
                        txtName.setText(table.getValueAt(selectedRow, 1).toString());
                        txtPrice.setText(table.getValueAt(selectedRow, 2).toString().replaceAll("[^0-9]", ""));
                    }
                }
            }
        });

        // Add button listeners based on panel type
        if (panelType == 1) { // Food
            handleFoodButtons(btnThem, btnSua, btnXoa, btnHuy);
        } else if (panelType == 2) { // Drink
            handleDrinkButtons(btnThem, btnSua, btnXoa, btnHuy);
        } else if (panelType == 3) { // Card
            handleCardButtons(btnThem, btnSua, btnXoa, btnHuy);
        }

        // Add search functionality
        final int finalPanelType = panelType;
        searchBtn.addActionListener(e -> {
            String searchText = searchField.getText().trim();
            if (finalPanelType == 1) { // Food
                List<DvDoAn> results = dichVuController.timKiemDoAn(searchText);
                updateFoodTable(results);
            } else if (finalPanelType == 2) { // Drink
                List<DvDoUong> results = dichVuController.timKiemDoUong(searchText);
                updateDrinkTable(results);
            } else if (finalPanelType == 3) { // Card
                List<DvTheCao> results = dichVuController.timKiemTheCao(searchText);
                updateCardTable(results);
            }
        });

        return panel;
    }

    private void updateFoodTable(List<DvDoAn> foodList) {
        modelDoAn.setRowCount(0);
        doAnIDs.clear();
        int index = 0;
        System.out.println("Updating food table with " + foodList.size() + " items");
        for (DvDoAn food : foodList) {
            modelDoAn.addRow(new Object[]{
                    food.MaDV,
                    food.TenDoAn,
                    food.DonGia,
                    food.TrangThai,
                    food.BestSeller ? "Có" : "Không"
            });
            doAnIDs.put(index, food.MaDV);
            System.out.println("Added food ID at index " + index + ": " + food.MaDV + " - " + food.TenDoAn);
            index++;
        }
    }

    private void updateDrinkTable(List<DvDoUong> drinkList) {
        modelDoUong.setRowCount(0);
        doUongIDs.clear();
        int index = 0;
        System.out.println("Updating drink table with " + drinkList.size() + " items");
        for (DvDoUong drink : drinkList) {
            modelDoUong.addRow(new Object[]{
                    drink.maDV,
                    drink.TenDoUong,
                    drink.DonGia,
                    drink.TrangThai,
                    drink.BestSeller ? "Có" : "Không"
            });
            doUongIDs.put(index, drink.maDV);
            System.out.println("Added drink ID at index " + index + ": " + drink.maDV + " - " + drink.TenDoUong);
            index++;
        }
    }

    private void updateCardTable(List<DvTheCao> cardList) {
        modelTheCao.setRowCount(0);
        theoCaoIDs.clear();
        int index = 0;
        System.out.println("Updating card table with " + cardList.size() + " items");
        for (DvTheCao card : cardList) {
            modelTheCao.addRow(new Object[]{
                    card.maDV,
                    card.LoaiThe,
                    card.DonGia
            });
            theoCaoIDs.put(index, card.maDV);
            System.out.println("Added card ID at index " + index + ": " + card.maDV + " - " + card.LoaiThe);
            index++;
        }
    }

    // Phương thức để load tất cả dữ liệu
    private void loadAllData() {
        showFoodData();
        showDrinkData();
        showCardData();
    }

    // Hiển thị dữ liệu đồ ăn lên form
    public void showFoodData() {
        try {
            List<DvDoAn> foodList = dichVuController.layDanhSachDoAn();
            updateFoodTable(foodList);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Lỗi khi tải dữ liệu đồ ăn: " + e.getMessage(),
                    "Lỗi", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    // Hiển thị dữ liệu đồ uống lên form
    public void showDrinkData() {
        try {
            List<DvDoUong> drinkList = dichVuController.layDanhSachDoUong();
            updateDrinkTable(drinkList);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Lỗi khi tải dữ liệu đồ uống: " + e.getMessage(),
                    "Lỗi", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    // Hiển thị dữ liệu thẻ cào lên form
    public void showCardData() {
        try {
            List<DvTheCao> cardList = dichVuController.layDanhSachTheCao();
            updateCardTable(cardList);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Lỗi khi tải dữ liệu thẻ cào: " + e.getMessage(),
                    "Lỗi", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    private void handleFoodButtons(final JButton btnThem, final JButton btnSua, final JButton btnXoa, final JButton btnHuy) {
        // Add Button
        btnThem.addActionListener(e -> {
            try {
                String tenDoAn = txtTenDoAn.getText().trim();
                if (tenDoAn.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Vui lòng nhập tên đồ ăn", "Lỗi", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                int donGia = Integer.parseInt(txtDonGiaDoAn.getText().trim());
                boolean bestSeller = chkBestSellerDoAn.isSelected();

                dichVuController.themDoAn(tenDoAn, donGia, bestSeller);
                JOptionPane.showMessageDialog(this, "Thêm đồ ăn thành công", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                showFoodData();
                clearFoodFields();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Đơn giá không hợp lệ", "Lỗi", JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Lỗi: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
            }
        });

        // Edit Button
        btnSua.addActionListener(e -> {
            try {
                int selectedRow = tableDoAn.getSelectedRow();
                if (selectedRow == -1) {
                    JOptionPane.showMessageDialog(this, "Vui lòng chọn đồ ăn cần sửa", "Lỗi", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                // Get service ID directly from the table
                String maDV = doAnIDs.get(selectedRow);
                // Get form data
                String tenDoAn = txtTenDoAn.getText().trim();
                if (tenDoAn.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Vui lòng nhập tên đồ ăn", "Lỗi", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                int donGia;
                try {
                    donGia = Integer.parseInt(txtDonGiaDoAn.getText().trim());
                    if (donGia <= 0) {
                        JOptionPane.showMessageDialog(this, "Đơn giá phải lớn hơn 0", "Lỗi", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(this, "Đơn giá không hợp lệ", "Lỗi", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                String trangThai = cboTrangThaiDoAn.getSelectedItem().toString();
                boolean bestSeller = chkBestSellerDoAn.isSelected();

                // Update the data in the database
                dichVuController.suaDoAn(maDV, tenDoAn, donGia, bestSeller, trangThai);

                // Refresh the table
                showFoodData();

                // Select the updated row again
                for (int i = 0; i < tableDoAn.getRowCount(); i++) {
                    if (tableDoAn.getValueAt(i, 0).toString().equals(maDV)) {
                        tableDoAn.setRowSelectionInterval(i, i);
                        break;
                    }
                }

                JOptionPane.showMessageDialog(this, "Sửa đồ ăn thành công", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Lỗi: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
            }
        });

        // Delete Button
        btnXoa.addActionListener(e -> {
            try {
                int selectedRow = tableDoAn.getSelectedRow();
                if (selectedRow == -1) {
                    JOptionPane.showMessageDialog(this, "Vui lòng chọn đồ ăn cần xóa", "Lỗi", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Get service ID directly from the table
                String maDV = tableDoAn.getValueAt(selectedRow, 0).toString();
                String tenDoAn = tableDoAn.getValueAt(selectedRow, 1).toString();

                int confirm = JOptionPane.showConfirmDialog(this,
                        "Bạn có chắc chắn muốn xóa đồ ăn " + tenDoAn + "?", "Xác nhận", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    dichVuController.xoaDichVu(maDV);
                    JOptionPane.showMessageDialog(this, "Xóa đồ ăn thành công", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                    showFoodData();
                    clearFoodFields();
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Lỗi: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
            }
        });

        // Cancel Button
        btnHuy.addActionListener(e -> clearFoodFields());
    }

    private void clearFoodFields() {
        txtTenDoAn.setText("");
        txtDonGiaDoAn.setText("");
        if (cboTrangThaiDoAn != null) {
            cboTrangThaiDoAn.setSelectedIndex(0);
        }
        if (chkBestSellerDoAn != null) {
            chkBestSellerDoAn.setSelected(false);
        }
        tableDoAn.clearSelection();
        selectedDoAnID = null;
    }

    private void handleDrinkButtons(final JButton btnThem, final JButton btnSua, final JButton btnXoa, final JButton btnHuy) {
        // Add Button
        btnThem.addActionListener(e -> {
            try {
                String tenDoUong = txtTenDoUong.getText().trim();
                if (tenDoUong.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Vui lòng nhập tên đồ uống", "Lỗi", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                int donGia = Integer.parseInt(txtDonGiaDoUong.getText().trim());
                boolean bestSeller = chkBestSellerDoUong.isSelected();

                dichVuController.themDoUong(tenDoUong, donGia, bestSeller);
                JOptionPane.showMessageDialog(this, "Thêm đồ uống thành công", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                showDrinkData();
                clearDrinkFields();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Đơn giá không hợp lệ", "Lỗi", JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Lỗi: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        });

        // Edit Button
        btnSua.addActionListener(e -> {
            try {
                int selectedRow = tableDoUong.getSelectedRow();
                if (selectedRow == -1) {
                    JOptionPane.showMessageDialog(this, "Vui lòng chọn đồ uống cần sửa", "Lỗi", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Lấy mã dịch vụ trực tiếp từ bảng
                String maDV = tableDoUong.getValueAt(selectedRow, 0).toString();

                String tenDoUong = txtTenDoUong.getText().trim();
                if (tenDoUong.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Vui lòng nhập tên đồ uống", "Lỗi", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                int donGia;
                try {
                    donGia = Integer.parseInt(txtDonGiaDoUong.getText().trim());
                    if (donGia <= 0) {
                        JOptionPane.showMessageDialog(this, "Đơn giá phải lớn hơn 0", "Lỗi", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(this, "Đơn giá không hợp lệ", "Lỗi", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                String trangThai = cboTrangThaiDoUong.getSelectedItem().toString();
                boolean bestSeller = chkBestSellerDoUong.isSelected();

                // Update in database
                dichVuController.suaDoUong(maDV, tenDoUong, donGia, bestSeller, trangThai);

                JOptionPane.showMessageDialog(this, "Sửa đồ uống thành công", "Thông báo", JOptionPane.INFORMATION_MESSAGE);

                // Refresh data
                showDrinkData();

                // Tìm dòng có chứa mã dịch vụ vừa sửa và chọn lại
                for (int i = 0; i < tableDoUong.getRowCount(); i++) {
                    if (tableDoUong.getValueAt(i, 0).toString().equals(maDV)) {
                        tableDoUong.setRowSelectionInterval(i, i);
                        break;
                    }
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Lỗi: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
            }
        });

        // Delete Button
        btnXoa.addActionListener(e -> {
            try {
                int selectedRow = tableDoUong.getSelectedRow();
                if (selectedRow == -1) {
                    JOptionPane.showMessageDialog(this, "Vui lòng chọn đồ uống cần xóa", "Lỗi", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Lấy mã dịch vụ trực tiếp từ bảng
                String maDV = tableDoUong.getValueAt(selectedRow, 0).toString();
                String tenDoUong = tableDoUong.getValueAt(selectedRow, 1).toString();

                int confirm = JOptionPane.showConfirmDialog(this,
                        "Bạn có chắc chắn muốn xóa đồ uống " + tenDoUong + "?", "Xác nhận", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    dichVuController.xoaDichVu(maDV);
                    JOptionPane.showMessageDialog(this, "Xóa đồ uống thành công", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                    showDrinkData();
                    clearDrinkFields();
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Lỗi: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        });

        // Cancel Button
        btnHuy.addActionListener(e -> clearDrinkFields());
    }

    private void clearDrinkFields() {
        txtTenDoUong.setText("");
        txtDonGiaDoUong.setText("");
        if (cboTrangThaiDoUong != null) {
            cboTrangThaiDoUong.setSelectedIndex(0);
        }
        if (chkBestSellerDoUong != null) {
            chkBestSellerDoUong.setSelected(false);
        }
        tableDoUong.clearSelection();
        selectedDoUongID = null;
    }

    private void handleCardButtons(final JButton btnThem, final JButton btnSua, final JButton btnXoa, final JButton btnHuy) {
        // Add Button
        btnThem.addActionListener(e -> {
            try {
                String loaiThe = txtLoaiThe.getText().trim();
                if (loaiThe.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Vui lòng nhập loại thẻ", "Lỗi", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                int menhGia = Integer.parseInt(txtMenhGia.getText().trim());

                dichVuController.themTheCao(loaiThe, menhGia);
                JOptionPane.showMessageDialog(this, "Thêm thẻ cào thành công", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                showCardData();
                clearCardFields();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Mệnh giá không hợp lệ", "Lỗi", JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Lỗi: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        });

        // Edit Button
        btnSua.addActionListener(e -> {
            try {
                int selectedRow = tableTheCao.getSelectedRow();
                if (selectedRow == -1) {
                    JOptionPane.showMessageDialog(this, "Vui lòng chọn thẻ cào cần sửa", "Lỗi", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Lấy mã dịch vụ trực tiếp từ bảng
                String maDV = tableTheCao.getValueAt(selectedRow, 0).toString();

                String loaiThe = txtLoaiThe.getText().trim();
                if (loaiThe.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Vui lòng nhập loại thẻ", "Lỗi", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                int menhGia;
                try {
                    menhGia = Integer.parseInt(txtMenhGia.getText().trim());
                    if (menhGia <= 0) {
                        JOptionPane.showMessageDialog(this, "Mệnh giá phải lớn hơn 0", "Lỗi", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(this, "Mệnh giá không hợp lệ", "Lỗi", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Update in database
                dichVuController.suaTheCao(maDV, loaiThe, menhGia);

                JOptionPane.showMessageDialog(this, "Sửa thẻ cào thành công", "Thông báo", JOptionPane.INFORMATION_MESSAGE);

                // Refresh data
                showCardData();

                // Tìm dòng có chứa mã dịch vụ vừa sửa và chọn lại
                for (int i = 0; i < tableTheCao.getRowCount(); i++) {
                    if (tableTheCao.getValueAt(i, 0).toString().equals(maDV)) {
                        tableTheCao.setRowSelectionInterval(i, i);
                        break;
                    }
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Lỗi: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
            }
        });

        // Delete Button
        btnXoa.addActionListener(e -> {
            try {
                int selectedRow = tableTheCao.getSelectedRow();
                if (selectedRow == -1) {
                    JOptionPane.showMessageDialog(this, "Vui lòng chọn thẻ cào cần xóa", "Lỗi", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Lấy mã dịch vụ trực tiếp từ bảng
                String maDV = tableTheCao.getValueAt(selectedRow, 0).toString();
                String loaiThe = tableTheCao.getValueAt(selectedRow, 1).toString();

                int confirm = JOptionPane.showConfirmDialog(this,
                        "Bạn có chắc chắn muốn xóa thẻ cào " + loaiThe + "?", "Xác nhận", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    dichVuController.xoaDichVu(maDV);
                    JOptionPane.showMessageDialog(this, "Xóa thẻ cào thành công", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                    showCardData();
                    clearCardFields();
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Lỗi: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        });

        // Cancel Button
        btnHuy.addActionListener(e -> clearCardFields());
    }

    private void clearCardFields() {
        txtLoaiThe.setText("");
        txtMenhGia.setText("");
        tableTheCao.clearSelection();
        selectedTheCaoID = null;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new DichVuForm().setVisible(true);
        });
    }
}
