package com.example.view;

import com.example.controller.TaiKhoanController;
import com.example.model.TaiKhoan;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.List;

public class TaiKhoanForm extends JFrame {

    private TaiKhoanController controller;
    private JTable userTable;
    private DefaultTableModel tableModel;
    private JPanel formPanel;
    private JTextField searchField, usernameField, balanceField;
    private JPasswordField passwordField;
    private JCheckBox changePasswordCheckbox;
    private JSplitPane splitPane;
    private JButton lockButton;
    
    // Main content panel and embedded flag
    private JPanel mainContentPanel;
    private boolean isEmbedded = false;

    public TaiKhoanForm() {
        this(false);
    }
    
    public TaiKhoanForm(boolean isEmbedded) {
        this.isEmbedded = isEmbedded;
        controller = new TaiKhoanController(); // Controller đã có

        setTitle("Quản Lý Tài Khoản");
        if (!isEmbedded) {
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setMinimumSize(new Dimension(1000, 600));
            setLocationRelativeTo(null);
        }
        
        // Create main content panel
        mainContentPanel = new JPanel(new BorderLayout(10, 10));
        mainContentPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        mainContentPanel.setBackground(new Color(34, 34, 51)); // Nền tối

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setOpaque(false);

        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 5, 0));
        searchPanel.setOpaque(false);
        searchField = new JTextField(20);
        searchField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        searchField.putClientProperty("JTextField.placeholderText", "Tìm kiếm");

        JButton searchButton = new JButton("🔍");
        searchButton.setPreferredSize(new Dimension(40, 30));
        searchButton.setBackground(Color.WHITE);
        searchButton.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1, true));

        searchButton.addActionListener(e -> {
            loadData();
        });

        searchPanel.add(searchField);
        searchPanel.add(searchButton);
        topPanel.add(searchPanel, BorderLayout.EAST);

        mainContentPanel.add(topPanel, BorderLayout.NORTH);

        splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, createTablePanel(), createFormPanel());
        splitPane.setDividerLocation(600);
        splitPane.setResizeWeight(0.6);
        splitPane.setBorder(null);
        mainContentPanel.add(splitPane, BorderLayout.CENTER);
        
        if (!isEmbedded) {
            add(mainContentPanel);
            setVisible(true);
        }

        loadData();
        formPanel.setVisible(false); // Ẩn formPanel ngay từ đầu
    }
    
    public Container getContent() {
        return mainContentPanel;
    }

    private JPanel createTablePanel() {
        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.setOpaque(false);

        String[] headers = {"Tên Đăng Nhập", "Số Dư", "Trạng Thái", "Ngày Tạo"};
        tableModel = new DefaultTableModel(null, headers) {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        userTable = new JTable(tableModel);
        userTable.setRowHeight(30);
        userTable.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        userTable.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
        userTable.getTableHeader().setBackground(new Color(100, 240, 210));
        userTable.getTableHeader().setForeground(Color.BLACK);
        userTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        userTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && userTable.getSelectedRow() != -1) {
                displaySelectedUserInfo(userTable.getSelectedRow());
            }
        });

        JScrollPane scrollPane = new JScrollPane(userTable);
        scrollPane.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        tablePanel.add(scrollPane, BorderLayout.CENTER);


        return tablePanel;
    }

    private JPanel createFormPanel() {
        formPanel = new JPanel();
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
        formPanel.setBackground(Color.WHITE);
        formPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1, true),
                new EmptyBorder(15, 20, 15, 20)
        ));

        JLabel title = new JLabel("Thông Tin Tài Khoản");
        title.setFont(new Font("Segoe UI", Font.BOLD, 16));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        formPanel.add(title);
        formPanel.add(Box.createVerticalStrut(10));

        JPanel fieldPanel = new JPanel(new GridBagLayout());
        fieldPanel.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 5, 8, 5);
        gbc.anchor = GridBagConstraints.WEST;

        gbc.gridx = 0; gbc.gridy = 0;
        fieldPanel.add(new JLabel("Tên đăng nhập:"), gbc);
        gbc.gridx = 1;
        usernameField = createStyledTextField(true);
        fieldPanel.add(usernameField, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        fieldPanel.add(new JLabel("Số dư:"), gbc);
        gbc.gridx = 1;
        balanceField = createStyledTextField(true);
        fieldPanel.add(balanceField, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        fieldPanel.add(new JLabel("Mật khẩu:"), gbc);
        gbc.gridx = 1;
        JPanel passwordPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        passwordPanel.setOpaque(false);
        changePasswordCheckbox = new JCheckBox("Đổi");
        changePasswordCheckbox.setBackground(Color.WHITE);
        passwordField = new JPasswordField(12);
        passwordField.setEnabled(false);
        passwordField.setBorder(BorderFactory.createLineBorder(new Color(120, 80, 255), 1, true));
        passwordPanel.add(changePasswordCheckbox);
        passwordPanel.add(passwordField);
        fieldPanel.add(passwordPanel, gbc);

        changePasswordCheckbox.addActionListener(e -> passwordField.setEnabled(changePasswordCheckbox.isSelected()));

        formPanel.add(fieldPanel);

        // ==== BUTTON PANEL ====
        //hgap va vgap la khoang cach cac button
        JPanel buttonPanel = new JPanel(new GridLayout(3, 2, 10, 10)); // 3 hàng, 2 cột, hgap=10, vgap=10
        buttonPanel.setOpaque(false);

        JPanel outerPanel = new JPanel();
        outerPanel.setOpaque(false);
        outerPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        outerPanel.add(buttonPanel);

        formPanel.add(Box.createVerticalStrut(10));
        formPanel.add(outerPanel);

        JButton addButton = createStyledButton("Thêm NV");
        JButton reloadButton = createStyledButton("Làm Mới");
        JButton updateButton = createStyledButton("Cập Nhật");
        JButton cancelButton = createStyledButton("Hủy");
        lockButton = createStyledButton("Khóa");
        //JButton depositButton = createStyledButton("Nạp Tiền");

        addButton.addActionListener(e -> {
            try {
                int result = JOptionPane.showConfirmDialog(
                        null,
                        "Bạn có chắc chắn muốn tạo tài khoản này không?",
                        "Xác nhận",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE
                );

                if (result == JOptionPane.NO_OPTION) {
                    return;
                }

                String tenTk = usernameField.getText();
                //String soDuText = balanceField.getText();
                int soDu = Integer.parseInt(balanceField.getText());

                controller.themTaiKhoan(tenTk, soDu);

                JOptionPane.showMessageDialog(null, "Thêm tài khoản thành công.");
                loadData();

                formPanel.setVisible(false); // set formPanel to invisible
                splitPane.setDividerLocation(1.0);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Số dư không hợp lệ.", "Lỗi", JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        });

        reloadButton.addActionListener(e -> loadData());
        updateButton.addActionListener(e -> {
            String username = usernameField.getText();
            String soDuText = balanceField.getText();
            String password = new String(passwordField.getPassword());

            if (username.isEmpty() || soDuText.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Vui lòng điền đầy đủ thông tin.", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                int soDu = Integer.parseInt(soDuText);
                controller.suaTaiKhoan(username, password, soDu);

                JOptionPane.showMessageDialog(this, "Cập nhật thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                loadData();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Số dư không hợp lệ.", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        });

        cancelButton.addActionListener(e -> {
            usernameField.setText("");
            balanceField.setText("");
            passwordField.setText("");
//            passwordField.setEnabled(false);
//            changePasswordCheckbox.setSelected(false);
            formPanel.setVisible(false); // set formPanel to invisible
            splitPane.setDividerLocation(1.0); // thu gọn lại formPanel về cuối

        });

        lockButton.addActionListener(e -> {
            try {
                String username = usernameField.getText();  // Lấy tên tài khoản từ ô nhập liệu
                String buttonText = lockButton.getText();  // Lấy văn bản trên nút (Khóa/Mở khóa)

                if (username.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Vui lòng chọn tài khoản để khóa.", "Lỗi", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Hiển thị hộp thoại xác nhận
                int result = JOptionPane.showConfirmDialog(this,
                        "Bạn có chắc chắn muốn " + buttonText.toLowerCase() + " tài khoản này không?",
                        "Xác nhận", JOptionPane.YES_NO_OPTION);

                if (result == JOptionPane.YES_OPTION) {
                    if (buttonText.equals("Khóa")) {
                        // Gọi phương thức khóa tài khoản
                        controller.khoaTaiKhoan(username);
                        JOptionPane.showMessageDialog(this, "Khóa tài khoản thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                        lockButton.setText("Mở khóa");  // Cập nhật văn bản của nút
                    } else {
                        // Gọi phương thức mở khóa tài khoản
                        controller.moKhoaTaiKhoan(username);
                        JOptionPane.showMessageDialog(this, "Mở khóa tài khoản thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                        lockButton.setText("Khóa");  // Cập nhật văn bản của nút
                    }

                    // Cập nhật lại danh sách tài khoản
                    loadData();
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        });


        buttonPanel.add(addButton);
        buttonPanel.add(reloadButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(cancelButton);
        buttonPanel.add(lockButton);
        //buttonPanel.add(depositButton);
        formPanel.add(Box.createVerticalStrut(10));
        formPanel.add(buttonPanel);

        return formPanel;
    }

    private void loadData() {
        // Xóa dữ liệu trong các Field nhập
        usernameField.setText("");
        balanceField.setText("");
        passwordField.setText("");

        try {
            String searchText = searchField.getText().trim();
            List<TaiKhoan> danhSach;

            if (searchText.isEmpty()) {
                danhSach = controller.getDanhSachTaiKhoan();
            } else {
                danhSach = controller.timTaiKhoan(searchText);
            }

            tableModel.setRowCount(0); // Xóa bảng

            for (TaiKhoan tk : danhSach) {
                Object[] row = {
                        tk.getTenDangNhap(),
                        formatCurrency(tk.getSoDu()),                 // định dạng tiền tệ
                        tk.getTrangThai(),
                        tk.getNgayTao() != null
                                ? new SimpleDateFormat("dd/MM/yyyy h:mm:ss a").format(tk.getNgayTao())
                                : ""
                };
                tableModel.addRow(row);
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Lỗi khi tải dữ liệu: " + e.getMessage(),
                    "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private String formatCurrency(double amount) {
        DecimalFormat formatter = new DecimalFormat("#,###");
        return formatter.format(amount) + " VNĐ";
    }


    // === HIỂN THỊ DỮ LIỆU LÊN FORM ===
    private void displaySelectedUserInfo(int row) {
        String username = tableModel.getValueAt(row, 0).toString();
        List<TaiKhoan> list = controller.timTaiKhoan(username);

        if (!list.isEmpty()) {
            TaiKhoan tk = list.get(0);
            usernameField.setText(tk.getTenDangNhap());
            balanceField.setText(String.valueOf(tk.getSoDu()));
            passwordField.setText(tk.getMatKhau());
            passwordField.setEnabled(false);
            changePasswordCheckbox.setSelected(false);
            formPanel.setVisible(true);
            splitPane.setDividerLocation(600); // cập nhật lại kích thước để hiện panel
            splitPane.revalidate(); // đảm bảo layout được cập nhật
            if(tk.getTrangThai().equals("Bị khóa")) {
                lockButton.setText("Mở khóa");
            } else {
                lockButton.setText("Khóa");
            }
        }
    }

    private JTextField createStyledTextField(boolean editable) {
        JTextField field = new JTextField(15);
        field.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        field.setBorder(BorderFactory.createLineBorder(new Color(120, 80, 255), 1, true));
        field.setEditable(editable);
        return field;
    }

    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setPreferredSize(new Dimension(100, 35));
        button.setBackground(new Color(120, 80, 255));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setFont(new Font("Segoe UI", Font.BOLD, 13));
        button.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));

        return button;
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                //UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");

            } catch (Exception ignored) {}
            new TaiKhoanForm();
        });
    }
}
