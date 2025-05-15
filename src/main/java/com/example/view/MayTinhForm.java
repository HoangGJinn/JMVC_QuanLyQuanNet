package com.example.view;

import com.example.dao.MayTinhDAO;
import com.example.dao.LoaiMayDAO;
import com.example.model.MayTinh;
import com.example.model.LoaiMay;
import com.example.util.UIStyler;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class MayTinhForm extends JFrame {
    JTable tableMay, tableLoaiMay;
    JTextField tfSearchMay, tfSearchLoai, tfTenLoai, tfSoTien;
    JComboBox<String> cbLoaiMay, cbTrangThai;
    JButton btnThemMay, btnCapNhatMay, btnHuyMay;
    JButton btnThemLoai, btnCapNhatLoai, btnXoaLoai, btnLinhKien, btnHuyLoai;
    JFormattedTextField tfNgayLapDat;
    JLabel statusBar;

    // DAO objects for database access
    private MayTinhDAO mayTinhDAO;
    private LoaiMayDAO loaiMayDAO;

    // Table models
    private DefaultTableModel modelMay;
    private DefaultTableModel modelLoaiMay;

    // Main content panel
    private JPanel mainContentPanel;
    private boolean isEmbedded = false;

    public MayTinhForm() {
        this(false);
    }

    public MayTinhForm(boolean isEmbedded) {
        this.isEmbedded = isEmbedded;
        mayTinhDAO = MayTinhDAO.getInstance();
        loaiMayDAO = LoaiMayDAO.getInstance();

        setTitle("Quản lý máy");
        if (!isEmbedded) {
            setDefaultCloseOperation(EXIT_ON_CLOSE);
            setSize(1300, 700);
        }

        // Create main content panel
        mainContentPanel = new JPanel(new BorderLayout());
        mainContentPanel.setBackground(UIStyler.SECONDARY_DARK);

        initUI();

        if (!isEmbedded) {
            setContentPane(mainContentPanel);
            setLocationRelativeTo(null);
            setVisible(true);
        }
    }

    public Container getContent() {
        return mainContentPanel;
    }

    private void initUI() {
        // Panel chính chứa hai bảng
        JPanel mainPanel = new JPanel(new GridLayout(1, 2, 10, 0));
        mainPanel.setBackground(UIStyler.SECONDARY_DARK);

        // Panel bên trái - Danh sách máy tính
        JPanel panelMay = new JPanel(new BorderLayout(0, 10));
        panelMay.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createEmptyBorder(10, 10, 10, 10), 
                "Danh sách máy tính", 
                javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, 
                javax.swing.border.TitledBorder.DEFAULT_POSITION,
                new Font("Sans-serif", Font.BOLD, 18),
                Color.BLACK));
        panelMay.setBackground(UIStyler.SECONDARY_DARK);

        // Thanh tìm kiếm
        JPanel searchPanel = new JPanel(new BorderLayout());
        searchPanel.setBackground(UIStyler.SECONDARY_DARK);
        tfSearchMay = new JTextField("");
        // Apply consistent styling to text field
        UIStyler.styleTextField(tfSearchMay);
        // Add placeholder text
        UIStyler.setPlaceholder(tfSearchMay, "Nhập số máy để tìm kiếm...");

        tfSearchMay.addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyPressed(java.awt.event.KeyEvent evt) {
                if (evt.getKeyCode() == java.awt.event.KeyEvent.VK_ENTER) {
                    loadMayTinh();
                }
            }
        });

        JButton searchButton = new JButton("🔍 Tìm");
        // Apply consistent styling to button
        UIStyler.styleButton(searchButton);
        searchButton.setPreferredSize(new Dimension(80, 40));
        searchButton.addActionListener(e -> loadMayTinh());

        searchPanel.add(tfSearchMay, BorderLayout.CENTER);
        searchPanel.add(searchButton, BorderLayout.EAST);

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(UIStyler.SECONDARY_DARK);
        topPanel.add(searchPanel, BorderLayout.CENTER);

        JButton collapseButton = new JButton("^");
        // Apply consistent styling to button
        UIStyler.styleButton(collapseButton);
        collapseButton.setPreferredSize(new Dimension(50, 40));
        topPanel.add(collapseButton, BorderLayout.EAST);

        panelMay.add(topPanel, BorderLayout.NORTH);

        String[] cols1 = {"Số Máy", "Trạng Thái", "Ngày Lắp Đặt", "Thời Gian SD", "Mã Loại"};
        modelMay = new DefaultTableModel(cols1, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tableMay = new JTable(modelMay);
        // Apply consistent styling to table
        UIStyler.styleTable(tableMay);
        tableMay.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int selectedRow = tableMay.getSelectedRow();
                if (selectedRow >= 0) {
                    String soMay = (String) modelMay.getValueAt(selectedRow, 0);
                    String trangThai = (String) modelMay.getValueAt(selectedRow, 1);
                    String ngayLapDatStr = (String) modelMay.getValueAt(selectedRow, 2);
                    int maLoaiMay = Integer.parseInt(modelMay.getValueAt(selectedRow, 4).toString());

                    // Set values to form fields
                    try {
                        Date ngayLapDat = new SimpleDateFormat("dd/MM/yyyy").parse(ngayLapDatStr);
                        tfNgayLapDat.setValue(ngayLapDat);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }

                    cbTrangThai.setSelectedItem(trangThai);

                    // Find the index for the loaiMay
                    for (int i = 0; i < cbLoaiMay.getItemCount(); i++) {
                        if (Integer.parseInt(cbLoaiMay.getItemAt(i).toString().split(" - ")[0]) == maLoaiMay) {
                            cbLoaiMay.setSelectedIndex(i);
                            break;
                        }
                    }

                    // Show update and cancel buttons
                    btnCapNhatMay.setVisible(true);
                    btnHuyMay.setVisible(true);
                }
            }
        });

        JScrollPane scrollPane1 = new JScrollPane(tableMay);
        // Apply consistent styling to scroll pane
        UIStyler.styleScrollPane(scrollPane1);
        panelMay.add(scrollPane1, BorderLayout.CENTER);

        // Form dưới cùng
        JPanel panelFormContainer = new JPanel(new BorderLayout(0, 10));
        panelFormContainer.setBackground(UIStyler.SECONDARY_DARK);

        JPanel panelForm = new JPanel(new GridLayout(3, 2, 10, 10));
        panelForm.setBackground(UIStyler.SECONDARY_DARK);

        JLabel lblNgayLapDat = new JLabel("Ngày lắp đặt:");
        // Apply consistent styling to label
        UIStyler.styleLabel(lblNgayLapDat);

        tfNgayLapDat = new JFormattedTextField(new SimpleDateFormat("dd/MM/yyyy"));
        tfNgayLapDat.setValue(new Date());
        // Apply styling similar to text fields
        tfNgayLapDat.setFont(UIStyler.LABEL_FONT);
        tfNgayLapDat.setPreferredSize(UIStyler.TEXT_FIELD_SIZE);
        tfNgayLapDat.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200)),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)));

        JLabel lblLoaiMay = new JLabel("Loại máy:");
        // Apply consistent styling to label
        UIStyler.styleLabel(lblLoaiMay);

        cbLoaiMay = new JComboBox<>();
        // Apply consistent styling to combo box
        UIStyler.styleComboBox(cbLoaiMay);

        JLabel lblTrangThai = new JLabel("Trạng thái:");
        // Apply consistent styling to label
        UIStyler.styleLabel(lblTrangThai);

        cbTrangThai = new JComboBox<>(new String[]{"Không hoạt động", "Đang hoạt động", "Hư hỏng"});
        // Apply consistent styling to combo box
        UIStyler.styleComboBox(cbTrangThai);

        panelForm.add(lblNgayLapDat);
        panelForm.add(tfNgayLapDat);
        panelForm.add(lblLoaiMay);
        panelForm.add(cbLoaiMay);
        panelForm.add(lblTrangThai);
        panelForm.add(cbTrangThai);

        JPanel panelBtnMay = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        panelBtnMay.setBackground(UIStyler.SECONDARY_DARK);

        btnThemMay = new JButton("Thêm");
        // Apply consistent styling to button
        UIStyler.styleButton(btnThemMay);
        btnThemMay.addActionListener(e -> themMayTinh());

        btnCapNhatMay = new JButton("Cập Nhật");
        // Apply consistent styling to warning button
        UIStyler.styleWarningButton(btnCapNhatMay);
        btnCapNhatMay.addActionListener(e -> capNhatMayTinh());
        btnCapNhatMay.setVisible(false);

        btnHuyMay = new JButton("Hủy");
        // Apply consistent styling to button
        UIStyler.styleButton(btnHuyMay);
        btnHuyMay.addActionListener(e -> huyMayTinh());
        btnHuyMay.setVisible(false);

        panelBtnMay.add(btnThemMay);
        panelBtnMay.add(btnCapNhatMay);
        panelBtnMay.add(btnHuyMay);

        panelFormContainer.add(panelForm, BorderLayout.CENTER);
        panelFormContainer.add(panelBtnMay, BorderLayout.SOUTH);

        panelMay.add(panelFormContainer, BorderLayout.SOUTH);

        // Panel bên phải - Danh sách loại máy
        JPanel panelLoai = new JPanel(new BorderLayout(0, 10));
        panelLoai.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createEmptyBorder(10, 10, 10, 10), 
                "Danh sách loại máy", 
                javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, 
                javax.swing.border.TitledBorder.DEFAULT_POSITION,
                new Font("Sans-serif", Font.BOLD, 18),
                Color.BLACK));
        panelLoai.setBackground(UIStyler.SECONDARY_DARK);

        // Thanh tìm kiếm loại máy
        JPanel searchPanelLoai = new JPanel(new BorderLayout());
        searchPanelLoai.setBackground(UIStyler.SECONDARY_DARK);
        tfSearchLoai = new JTextField("");
        // Apply consistent styling to text field
        UIStyler.styleTextField(tfSearchLoai);
        // Add placeholder text
        UIStyler.setPlaceholder(tfSearchLoai, "Nhập tên loại máy để tìm kiếm...");


        // Thêm phím Enter để tìm kiếm loại máy
        tfSearchLoai.addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyPressed(java.awt.event.KeyEvent evt) {
                if (evt.getKeyCode() == java.awt.event.KeyEvent.VK_ENTER) {
                    loadLoaiMay();
                }
            }
        });

        JButton searchButtonLoai = new JButton("🔍 Tìm");
        // Apply consistent styling to button
        UIStyler.styleButton(searchButtonLoai);
        searchButtonLoai.setPreferredSize(new Dimension(80, 40));
        searchButtonLoai.addActionListener(e -> loadLoaiMay());

        searchPanelLoai.add(tfSearchLoai, BorderLayout.CENTER);
        searchPanelLoai.add(searchButtonLoai, BorderLayout.EAST);

        JPanel topPanelLoai = new JPanel(new BorderLayout());
        topPanelLoai.setBackground(UIStyler.SECONDARY_DARK);
        topPanelLoai.add(searchPanelLoai, BorderLayout.CENTER);

        btnThemLoai = new JButton("Thêm");
        // Apply consistent styling to button
        UIStyler.styleButton(btnThemLoai);
        btnThemLoai.addActionListener(e -> themLoaiMay());

        JPanel addBtnPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        addBtnPanel.setBackground(UIStyler.SECONDARY_DARK);
        addBtnPanel.add(btnThemLoai);

        topPanelLoai.add(addBtnPanel, BorderLayout.WEST);

        JButton collapseButtonLoai = new JButton("^");
        // Apply consistent styling to button
        UIStyler.styleButton(collapseButtonLoai);
        collapseButtonLoai.setPreferredSize(new Dimension(50, 40));
        topPanelLoai.add(collapseButtonLoai, BorderLayout.EAST);

        panelLoai.add(topPanelLoai, BorderLayout.NORTH);

        // Bảng loại máy
        String[] cols2 = {"Mã Loại", "Tên Loại", "Số Tiền 1 Giờ"};
        modelLoaiMay = new DefaultTableModel(cols2, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tableLoaiMay = new JTable(modelLoaiMay);
        // Apply consistent styling to table
        UIStyler.styleTable(tableLoaiMay);
        tableLoaiMay.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int selectedRow = tableLoaiMay.getSelectedRow();
                if (selectedRow >= 0) {
                    String maLoai = tableLoaiMay.getValueAt(selectedRow, 0).toString();
                    String tenLoai = tableLoaiMay.getValueAt(selectedRow, 1).toString();
                    String soTien = tableLoaiMay.getValueAt(selectedRow, 2).toString().replace(" VND", "").replace(",", "");

                    tfTenLoai.setText(tenLoai);
                    tfSoTien.setText(soTien);

                    btnCapNhatLoai.setVisible(true);
                    btnXoaLoai.setVisible(true);
                    btnLinhKien.setVisible(true);
                    btnHuyLoai.setVisible(true);
                }
            }
        });

        JScrollPane scrollPane2 = new JScrollPane(tableLoaiMay);
        // Apply consistent styling to scroll pane
        UIStyler.styleScrollPane(scrollPane2);
        panelLoai.add(scrollPane2, BorderLayout.CENTER);

        // Form loại máy
        JPanel panelLoaiFormContainer = new JPanel(new BorderLayout(0, 10));
        panelLoaiFormContainer.setBackground(UIStyler.SECONDARY_DARK);

        JPanel panelLoaiForm = new JPanel(new GridLayout(2, 2, 10, 10));
        panelLoaiForm.setBackground(UIStyler.SECONDARY_DARK);

        JLabel lblTenLoai = new JLabel("Tên loại:");
        // Apply consistent styling to label
        UIStyler.styleLabel(lblTenLoai);

        tfTenLoai = new JTextField("");
        // Apply consistent styling to text field
        UIStyler.styleTextField(tfTenLoai);

        JLabel lblSoTien = new JLabel("Số tiền 1 giờ:");
        // Apply consistent styling to label
        UIStyler.styleLabel(lblSoTien);

        tfSoTien = new JTextField("");
        // Apply consistent styling to text field
        UIStyler.styleTextField(tfSoTien);

        panelLoaiForm.add(lblTenLoai);
        panelLoaiForm.add(tfTenLoai);
        panelLoaiForm.add(lblSoTien);
        panelLoaiForm.add(tfSoTien);

        JPanel panelBtnLoai = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        panelBtnLoai.setBackground(UIStyler.SECONDARY_DARK);

        btnCapNhatLoai = new JButton("Cập Nhật");
        // Apply consistent styling to warning button
        UIStyler.styleWarningButton(btnCapNhatLoai);
        btnCapNhatLoai.addActionListener(e -> capNhatLoaiMay());
        btnCapNhatLoai.setVisible(false);

        btnXoaLoai = new JButton("Xóa");
        // Apply consistent styling to danger button
        UIStyler.styleDangerButton(btnXoaLoai);
        btnXoaLoai.addActionListener(e -> xoaLoaiMay());
        btnXoaLoai.setVisible(false);

        btnLinhKien = new JButton("Linh Kiện");
        // Apply consistent styling to button
        UIStyler.styleButton(btnLinhKien);
        btnLinhKien.addActionListener(e -> xemLinhKien());
        btnLinhKien.setVisible(false);

        btnHuyLoai = new JButton("Hủy");
        // Apply consistent styling to button
        UIStyler.styleButton(btnHuyLoai);
        btnHuyLoai.addActionListener(e -> huyLoaiMay());
        btnHuyLoai.setVisible(false);

        panelBtnLoai.add(btnCapNhatLoai);
        panelBtnLoai.add(btnXoaLoai);
        panelBtnLoai.add(btnLinhKien);
        panelBtnLoai.add(btnHuyLoai);

        panelLoaiFormContainer.add(panelLoaiForm, BorderLayout.CENTER);
        panelLoaiFormContainer.add(panelBtnLoai, BorderLayout.SOUTH);

        panelLoai.add(panelLoaiFormContainer, BorderLayout.SOUTH);

        // Thêm vào frame chính
        mainPanel.add(panelMay);
        mainPanel.add(panelLoai);

        // Thêm thanh trạng thái ở dưới cùng
        statusBar = new JLabel("Sẵn sàng");
        // Apply consistent styling to status bar
        UIStyler.styleStatusBar(statusBar);
        JPanel statusPanel = new JPanel(new BorderLayout());
        statusPanel.setBackground(UIStyler.STATUS_BAR_COLOR);
        statusPanel.add(statusBar, BorderLayout.WEST);

        // Thêm các panel vào frame
        mainContentPanel.add(mainPanel, BorderLayout.CENTER);
        mainContentPanel.add(statusPanel, BorderLayout.SOUTH);

        // Load data
        loadLoaiMay();
        updateLoaiMayComboBox();
        loadMayTinh();
    }

    //Load danh sách máy tính từ database và hiển thị lên bảng

    private void loadMayTinh() {
        String searchText = tfSearchMay.getText().trim();
        List<MayTinh> dsMayTinh;

        // Bỏ qua placeholder khi tìm kiếm
        if (searchText.equals("Nhập số máy để tìm kiếm...")) {
            searchText = "";
        }

        // Xóa dữ liệu cũ trong bảng
        modelMay.setRowCount(0);

        try {
            if (searchText.isEmpty()) {
                dsMayTinh = mayTinhDAO.layDsMayTinh();
            } else {
                dsMayTinh = mayTinhDAO.timKiemMayTinh(searchText);
            }

            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

            for (MayTinh mt : dsMayTinh) {
                Object[] row = {
                    mt.getSoMay(),
                    mt.getTrangThai(),
                    dateFormat.format(mt.getNgayLapDat()),
                    mt.getThoiGianSD(),
                    mt.getMaLoaiMay()
                };
                modelMay.addRow(row);
            }

            // Hiển thị thông báo kết quả tìm kiếm nếu có nhập từ khóa tìm kiếm
            if (!searchText.isEmpty()) {
                if (dsMayTinh.isEmpty()) {
                    JOptionPane.showMessageDialog(this, 
                        "Không tìm thấy máy tính nào phù hợp với từ khóa: " + searchText,
                        "Kết quả tìm kiếm", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    statusBar.setText("Tìm thấy " + dsMayTinh.size() + " máy tính phù hợp với từ khóa: " + searchText);
                }
            } else {
                statusBar.setText("Tổng số máy tính: " + dsMayTinh.size());
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi khi tải dữ liệu máy tính: " + e.getMessage(),
                    "Lỗi", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    private void loadLoaiMay() {
        String searchText = tfSearchLoai.getText().trim();
        List<LoaiMay> dsLoaiMay;

        // Bỏ qua placeholder khi tìm kiếm
        if (searchText.equals("Nhập tên loại máy để tìm kiếm...")) {
            searchText = "";
        }

        // Xóa dữ liệu cũ trong bảng
        modelLoaiMay.setRowCount(0);

        try {
            if (searchText.isEmpty()) {
                dsLoaiMay = loaiMayDAO.layDsLoaiMay();
            } else {
                dsLoaiMay = loaiMayDAO.timKiemLoaiMay(searchText);
            }

            for (LoaiMay lm : dsLoaiMay) {
                Object[] row = {
                    lm.getMaLoaiMay(),
                    lm.getTenLoaiMay(),
                    formatPrice(lm.getSoTienMotGio())
                };
                modelLoaiMay.addRow(row);
            }

            // Hiển thị thông báo kết quả tìm kiếm nếu có nhập từ khóa tìm kiếm
            if (!searchText.isEmpty()) {
                if (dsLoaiMay.isEmpty()) {
                    JOptionPane.showMessageDialog(this, 
                        "Không tìm thấy loại máy nào phù hợp với từ khóa: " + searchText,
                        "Kết quả tìm kiếm", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    statusBar.setText("Tìm thấy " + dsLoaiMay.size() + " loại máy phù hợp với từ khóa: " + searchText);
                }
            } else {
                statusBar.setText("Tổng số loại máy: " + dsLoaiMay.size());
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi khi tải dữ liệu loại máy: " + e.getMessage(),
                    "Lỗi", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }


    //Cập nhật danh sách loại máy vào ComboBox

    private void updateLoaiMayComboBox() {
        cbLoaiMay.removeAllItems();

        try {
            List<LoaiMay> dsLoaiMay = loaiMayDAO.layDsLoaiMay();
            for (LoaiMay lm : dsLoaiMay) {
                cbLoaiMay.addItem(lm.getMaLoaiMay() + " - " + lm.getTenLoaiMay());
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi khi tải danh sách loại máy: " + e.getMessage(),
                    "Lỗi", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }



    private String formatPrice(int price) {
        return String.format("%,d VND", price);
    }

    //Xử lý sự kiện thêm máy tính mới

    private void themMayTinh() {
        try {
            int result = JOptionPane.showConfirmDialog(this,
                    "Bạn có chắc chắn muốn thêm mới máy tính không?",
                    "Xác nhận", JOptionPane.YES_NO_OPTION);
            if (result == JOptionPane.YES_OPTION) {
                Date ngayLapDat = (Date) tfNgayLapDat.getValue();

                java.sql.Date sqlDate = new java.sql.Date(ngayLapDat.getTime());

                // Lấy mã loại máy từ ComboBox
                String selectedLoaiMay = cbLoaiMay.getSelectedItem().toString();
                int maLoaiMay = Integer.parseInt(selectedLoaiMay.split(" - ")[0]);

                mayTinhDAO.themMayTinh(sqlDate, maLoaiMay);
                loadMayTinh();
                JOptionPane.showMessageDialog(this, "Thêm máy tính thành công");
                huyMayTinh();
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi khi thêm máy tính: " + e.getMessage(),
                    "Lỗi", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }


    //Xử lý sự kiện cập nhật thông tin máy tính

    private void capNhatMayTinh() {
        try {
            int selectedRow = tableMay.getSelectedRow();
            if (selectedRow >= 0) {
                // Tạo hộp thoại xác nhận với icon
                UIManager.put("OptionPane.background", new Color(240, 240, 240));
                UIManager.put("Panel.background", new Color(240, 240, 240));

                int result = JOptionPane.showConfirmDialog(this,
                        "Bạn có chắc chắn muốn sửa thông tin máy tính này không?",
                        "Xác nhận cập nhật", JOptionPane.YES_NO_OPTION, 
                        JOptionPane.QUESTION_MESSAGE);

                if (result == JOptionPane.YES_OPTION) {
                    String soMay = modelMay.getValueAt(selectedRow, 0).toString();
                    String trangThai = cbTrangThai.getSelectedItem().toString();

                    // Lấy mã loại máy từ ComboBox
                    String selectedLoaiMay = cbLoaiMay.getSelectedItem().toString();
                    int maLoaiMay = Integer.parseInt(selectedLoaiMay.split(" - ")[0]);

                    // Hiển thị thông báo đang cập nhật
                    statusBar.setText("Đang cập nhật máy tính " + soMay + "...");

                    mayTinhDAO.suaMayTinh(soMay, trangThai, maLoaiMay);
                    loadMayTinh();

                    // Hiển thị thông báo thành công với icon
                    JOptionPane.showMessageDialog(this, 
                            "Cập nhật máy tính thành công!",
                            "Thành công", JOptionPane.INFORMATION_MESSAGE);

                    // Chọn lại dòng vừa cập nhật
                    for (int i = 0; i < modelMay.getRowCount(); i++) {
                        if (modelMay.getValueAt(i, 0).toString().equals(soMay)) {
                            tableMay.setRowSelectionInterval(i, i);
                            break;
                        }
                    }

                    // Cập nhật trạng thái
                    statusBar.setText("Đã cập nhật máy tính " + soMay);
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi khi cập nhật máy tính: " + e.getMessage(),
                    "Lỗi", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }


    //Xử lý sự kiện hủy thao tác với máy tính

    private void huyMayTinh() {
        tfNgayLapDat.setValue(new Date());
        cbTrangThai.setSelectedIndex(0);
        if (cbLoaiMay.getItemCount() > 0) {
            cbLoaiMay.setSelectedIndex(0);
        }
        tableMay.clearSelection();
        btnCapNhatMay.setVisible(false);
        btnHuyMay.setVisible(false);
    }


    //Xử lý sự kiện thêm loại máy mới

    private void themLoaiMay() {
        try {
            String tenLoai = JOptionPane.showInputDialog(this, "Nhập tên loại máy:", "Thêm loại máy", JOptionPane.QUESTION_MESSAGE);
            if (tenLoai != null && !tenLoai.trim().isEmpty()) {
                String soTienStr = JOptionPane.showInputDialog(this, "Nhập số tiền 1 giờ:", "Thêm loại máy", JOptionPane.QUESTION_MESSAGE);
                if (soTienStr != null && !soTienStr.trim().isEmpty()) {
                    int soTien = Integer.parseInt(soTienStr.trim());

                    loaiMayDAO.themLoaiMay(tenLoai, soTien);
                    loadLoaiMay();
                    updateLoaiMayComboBox();
                    JOptionPane.showMessageDialog(this, "Thêm loại máy thành công");
                    huyLoaiMay();
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi khi thêm loại máy: " + e.getMessage(),
                    "Lỗi", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }


    //Xử lý sự kiện cập nhật thông tin loại máy
    private void capNhatLoaiMay() {
        try {
            int selectedRow = tableLoaiMay.getSelectedRow();
            if (selectedRow >= 0) {
                // Tạo hộp thoại xác nhận với icon
                UIManager.put("OptionPane.background", new Color(240, 240, 240));
                UIManager.put("Panel.background", new Color(240, 240, 240));

                int result = JOptionPane.showConfirmDialog(this,
                        "Bạn có chắc chắn muốn sửa thông tin loại máy này không?",
                        "Xác nhận cập nhật", JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE);

                if (result == JOptionPane.YES_OPTION) {
                    int maLoaiMay = Integer.parseInt(modelLoaiMay.getValueAt(selectedRow, 0).toString());
                    String tenLoaiMay = tfTenLoai.getText().trim();

                    // Kiểm tra tên loại máy không được rỗng
                    if (tenLoaiMay.isEmpty()) {
                        JOptionPane.showMessageDialog(this,
                                "Tên loại máy không được để trống!",
                                "Lỗi nhập liệu", JOptionPane.WARNING_MESSAGE);
                        return;
                    }

                    // Kiểm tra và xử lý giá tiền
                    String soTienStr = tfSoTien.getText().trim().replace(".", "").replace(",", "");
                    if (soTienStr.isEmpty()) {
                        JOptionPane.showMessageDialog(this,
                                "Số tiền một giờ không được để trống!",
                                "Lỗi nhập liệu", JOptionPane.WARNING_MESSAGE);
                        return;
                    }

                    int soTienMotGio;
                    try {
                        soTienMotGio = Integer.parseInt(soTienStr);
                    } catch (NumberFormatException e) {
                        JOptionPane.showMessageDialog(this,
                                "Số tiền phải là số nguyên!",
                                "Lỗi nhập liệu", JOptionPane.WARNING_MESSAGE);
                        return;
                    }

                    if (soTienMotGio <= 0) {
                        JOptionPane.showMessageDialog(this,
                                "Số tiền phải lớn hơn 0!",
                                "Lỗi nhập liệu", JOptionPane.WARNING_MESSAGE);
                        return;
                    }

                    // Hiển thị thông báo đang cập nhật
                    statusBar.setText("Đang cập nhật loại máy " + tenLoaiMay + "...");

                    loaiMayDAO.suaLoaiMay(maLoaiMay, tenLoaiMay, soTienMotGio);
                    loadLoaiMay();
                    updateLoaiMayComboBox();

                    // Hiển thị thông báo thành công với icon
                    JOptionPane.showMessageDialog(this, 
                            "Cập nhật loại máy thành công!",
                            "Thành công", JOptionPane.INFORMATION_MESSAGE);

                    // Chọn lại dòng vừa cập nhật
                    for (int i = 0; i < modelLoaiMay.getRowCount(); i++) {
                        if (Integer.parseInt(modelLoaiMay.getValueAt(i, 0).toString()) == maLoaiMay) {
                            tableLoaiMay.setRowSelectionInterval(i, i);
                            break;
                        }
                    }

                    // Cập nhật trạng thái
                    statusBar.setText("Đã cập nhật loại máy " + tenLoaiMay);
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi khi cập nhật loại máy: " + e.getMessage(),
                    "Lỗi", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    //Xử lý sự kiện xóa loại máy
    private void xoaLoaiMay() {
        try {
            int selectedRow = tableLoaiMay.getSelectedRow();
            if (selectedRow >= 0) {
                int result = JOptionPane.showConfirmDialog(this,
                        "Bạn có chắc chắn muốn xóa loại máy này không?",
                        "Xác nhận", JOptionPane.YES_NO_OPTION);
                if (result == JOptionPane.YES_OPTION) {
                    int maLoaiMay = Integer.parseInt(modelLoaiMay.getValueAt(selectedRow, 0).toString());

                    loaiMayDAO.xoaLoaiMay(maLoaiMay);
                    loadLoaiMay();
                    updateLoaiMayComboBox();
                    JOptionPane.showMessageDialog(this, "Xóa loại máy thành công");
                    huyLoaiMay();
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi khi xóa loại máy: " + e.getMessage(),
                    "Lỗi", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }


    //Xử lý sự kiện hủy thao tác với loại máy

    private void huyLoaiMay() {
        tfTenLoai.setText("");
        tfSoTien.setText("");
        tableLoaiMay.clearSelection();
        btnCapNhatLoai.setVisible(false);
        btnXoaLoai.setVisible(false);
        btnLinhKien.setVisible(false);
        btnHuyLoai.setVisible(false);
    }

    //Xử lý sự kiện xem linh kiện của loại máy

    private void xemLinhKien() {
        int selectedRow = tableLoaiMay.getSelectedRow();
        if (selectedRow >= 0) {
            int maLoaiMay = Integer.parseInt(modelLoaiMay.getValueAt(selectedRow, 0).toString());
            String tenLoaiMay = modelLoaiMay.getValueAt(selectedRow, 1).toString();

            // Hiển thị thông báo đang tải
            statusBar.setText("Đang mở form quản lý linh kiện cho " + tenLoaiMay + "...");

            // Mở form linh kiện
            LinhKienForm linhKienForm = new LinhKienForm(this, maLoaiMay);
            linhKienForm.setVisible(true);

            // Cập nhật trạng thái
            statusBar.setText("Sẵn sàng");
        } else {
            JOptionPane.showMessageDialog(this, 
                "Vui lòng chọn một loại máy để xem linh kiện", 
                "Thông báo", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    public static void main(String[] args) {
        // Apply global UI styling
        UIStyler.applyGlobalStyles();

        SwingUtilities.invokeLater(MayTinhForm::new);
    }
}
