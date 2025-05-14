package com.example.view;

import com.example.controller.DichVuController;
import com.example.model.DvDoAn;
import com.example.model.DvDoUong;
import com.example.model.DvTheCao;

import javax.swing.*;
import javax.swing.border.AbstractBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.plaf.basic.BasicButtonUI;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.List;
import javax.imageio.ImageIO;

public class DichVuForm extends JFrame {
    private DichVuController controller;

    // Main panels
    private JPanel mainPanel;
    private JPanel foodPanel, drinkPanel, phoneCardPanel;

    // Food components
    private JTable foodTable;
    private DefaultTableModel foodModel;
    private JTextField txtFoodSearch, txtFoodName, txtFoodPrice;
    private JCheckBox chkFoodBestSeller;
    private JComboBox<String> cboFoodStatus;
    private JButton btnAddFood, btnEditFood, btnDeleteFood, btnCancelFood, btnFoodImage, btnSearchFood;
    private JLabel lblFoodStatus, picFood;
    private Image foodImage;

    // Drink components
    private JTable drinkTable;
    private DefaultTableModel drinkModel;
    private JTextField txtDrinkSearch, txtDrinkName, txtDrinkPrice;
    private JCheckBox chkDrinkBestSeller;
    private JComboBox<String> cboDrinkStatus;
    private JButton btnAddDrink, btnEditDrink, btnDeleteDrink, btnCancelDrink, btnDrinkImage, btnSearchDrink;
    private JLabel lblDrinkStatus, picDrink;
    private Image drinkImage;

    // Phone Card components
    private JTable cardTable;
    private DefaultTableModel cardModel;
    private JTextField txtCardSearch, txtCardType, txtCardValue;
    private JButton btnAddCard, btnEditCard, btnDeleteCard, btnCancelCard, btnCardImage, btnSearchCard;
    private JLabel picCard;
    private Image cardImage;
    private JComboBox<String> cboCardStatus;

    // Colors
    private final Color HEADER_COLOR = new Color(0, 191, 165); // Turquoise
    private final Color BUTTON_COLOR = new Color(123, 104, 238); // Medium slate blue
    private final Color BUTTON_HOVER_COLOR = new Color(137, 116, 255); // Lighter purple
    private final Color BUTTON_TEXT_COLOR = Color.WHITE;
    private final Color BACKGROUND_COLOR = new Color(37, 42, 64); // Dark blue background
    private final Color PANEL_BACKGROUND = Color.WHITE;
    private final Color ACCENT_COLOR = new Color(255, 152, 0); // Orange
    private final Color BORDER_COLOR = new Color(230, 230, 230);
    private final Color TEXT_COLOR = new Color(33, 33, 33);
    private final Color PLACEHOLDER_COLOR = new Color(180, 180, 180);
    private final Color TABLE_BORDER_COLOR = new Color(0, 191, 165); // Turquoise
    private final Color TABLE_HEADER_COLOR = Color.WHITE;
    private final Color TABLE_HEADER_TEXT_COLOR = TEXT_COLOR;

    public DichVuForm() {
        controller = new DichVuController();
        initComponents();
        loadFoodData();
        loadDrinkData();
        loadCardData();
    }

    // Method to get content for embedding in MenuForm
    public Container getContent() {
        return getContentPane();
    }

    private void initComponents() {
        setTitle("Quản lý Dịch vụ");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(1300, 743);
        setLocationRelativeTo(null);
        getContentPane().setBackground(BACKGROUND_COLOR);
        
        mainPanel = new JPanel(new GridLayout(1, 3, 8, 0));
        mainPanel.setBackground(BACKGROUND_COLOR);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));
        
        // Create the three panels
        foodPanel = createFoodPanel();
        drinkPanel = createDrinkPanel();
        phoneCardPanel = createPhoneCardPanel();
        
        // Add panels to main panel
        mainPanel.add(foodPanel);
        mainPanel.add(drinkPanel);
        mainPanel.add(phoneCardPanel);
        
        // Add main panel to frame
        getContentPane().add(mainPanel);
    }

    private JPanel createFoodPanel() {
        JPanel panel = createCustomPanel();
        panel.setLayout(new BorderLayout(0, 0));
        
        // Top section with title
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setOpaque(false);
        
        // Title
        JLabel titleLabel = new JLabel("Dịch vụ đồ ăn");
        titleLabel.setFont(new Font("Be Vietnam Pro", Font.BOLD, 22));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(8, 12, 3, 12));
        
        // Search panel
        JPanel searchPanel = new JPanel(new BorderLayout());
        searchPanel.setOpaque(false);
        searchPanel.setBorder(BorderFactory.createEmptyBorder(8, 0, 3, 12));
        
        txtFoodSearch = createRoundTextField("Tìm kiếm", 15);
        btnSearchFood = createSearchButton();
        btnSearchFood.addActionListener(e -> searchFood());
        
        searchPanel.add(txtFoodSearch, BorderLayout.CENTER);
        searchPanel.add(btnSearchFood, BorderLayout.EAST);
        
        // Create a container panel for proper layout
        JPanel headerPanel = new JPanel(new GridLayout(2, 1, 0, 0));
        headerPanel.setOpaque(false);
        headerPanel.add(titleLabel);
        
        JPanel searchContainer = new JPanel(new BorderLayout());
        searchContainer.setOpaque(false);
        searchContainer.add(searchPanel, BorderLayout.EAST);
        headerPanel.add(searchContainer);
        
        topPanel.add(headerPanel, BorderLayout.CENTER);
        
        // Table with padding
        JPanel tableContainer = new JPanel(new BorderLayout());
        tableContainer.setOpaque(false);
        tableContainer.setBorder(BorderFactory.createEmptyBorder(3, 0, 0, 0));
        
        foodModel = new DefaultTableModel(
            new String[]{"MaDV", "Tên Đồ Ăn", "Đơn Giá", "Trạng Thái", "BS"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        foodTable = createStyledTable(foodModel);
        foodTable.setRowHeight(25);
        foodTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                displayFoodDetails(foodTable.getSelectedRow());
            }
        });
        
        JScrollPane tableScrollPane = new JScrollPane(foodTable);
        tableScrollPane.setBorder(BorderFactory.createEmptyBorder());
        tableScrollPane.getViewport().setBackground(PANEL_BACKGROUND);
        
        tableContainer.add(tableScrollPane, BorderLayout.CENTER);
        
        // Form panel - using BorderLayout for main content and right side buttons
        JPanel formPanel = new JPanel(new BorderLayout(8, 0));
        formPanel.setOpaque(false);
        formPanel.setBorder(BorderFactory.createEmptyBorder(8, 12, 8, 12));
        
        // Left panel for form fields
        JPanel fieldsPanel = new JPanel();
        fieldsPanel.setLayout(new BoxLayout(fieldsPanel, BoxLayout.Y_AXIS));
        fieldsPanel.setOpaque(false);
        
        // Food name row
        JPanel nameRow = new JPanel(new BorderLayout(8, 0));
        nameRow.setOpaque(false);
        
        JLabel lblFoodName = new JLabel("Tên đồ ăn:");
        lblFoodName.setFont(new Font("HarmonyOS Sans", Font.PLAIN, 13));
        lblFoodName.setPreferredSize(new Dimension(75, 25));
        lblFoodName.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        txtFoodName = createRoundTextField("Nhập tên đồ ăn", 25);
        
        nameRow.add(lblFoodName, BorderLayout.WEST);
        nameRow.add(txtFoodName, BorderLayout.CENTER);
        
        // Price and best seller row
        JPanel priceRow = new JPanel(new BorderLayout(8, 0));
        priceRow.setOpaque(false);
        
        JLabel lblFoodPrice = new JLabel("Đơn giá:");
        lblFoodPrice.setFont(new Font("HarmonyOS Sans", Font.PLAIN, 13));
        lblFoodPrice.setPreferredSize(new Dimension(75, 25));
        
        JPanel priceFieldPanel = new JPanel(new BorderLayout(5, 0));
        priceFieldPanel.setOpaque(false);
        
        txtFoodPrice = createRoundTextField("Nhập đơn giá", 15);
        
        chkFoodBestSeller = new JCheckBox("Best seller");
        chkFoodBestSeller.setOpaque(false);
        chkFoodBestSeller.setFont(new Font("HarmonyOS Sans", Font.PLAIN, 13));
        
        priceFieldPanel.add(txtFoodPrice, BorderLayout.CENTER);
        priceFieldPanel.add(chkFoodBestSeller, BorderLayout.EAST);
        
        priceRow.add(lblFoodPrice, BorderLayout.WEST);
        priceRow.add(priceFieldPanel, BorderLayout.CENTER);
        
        // Image row
        JPanel imageRow = new JPanel(new BorderLayout(8, 0));
        imageRow.setOpaque(false);
        
        JLabel lblImage = new JLabel("Hình:");
        lblImage.setFont(new Font("HarmonyOS Sans", Font.PLAIN, 13));
        lblImage.setPreferredSize(new Dimension(80, 25));
        
        JPanel imagePanel = new JPanel(new BorderLayout(8, 0));
        imagePanel.setOpaque(false);
        
        picFood = new JLabel();
        picFood.setPreferredSize(new Dimension(45, 45));
        picFood.setBorder(BorderFactory.createLineBorder(BORDER_COLOR));
        
        btnFoodImage = createRoundButton("Chọn", BUTTON_COLOR, BUTTON_TEXT_COLOR);
        btnFoodImage.addActionListener(e -> selectFoodImage());
        
        imagePanel.add(picFood, BorderLayout.WEST);
        imagePanel.add(btnFoodImage, BorderLayout.CENTER);
        
        imageRow.add(lblImage, BorderLayout.WEST);
        imageRow.add(imagePanel, BorderLayout.CENTER);
        
        // Status row (shown always)
        JPanel statusRow = new JPanel(new BorderLayout(8, 0));
        statusRow.setOpaque(false);
        
        lblFoodStatus = new JLabel("Trạng thái:");
        lblFoodStatus.setFont(new Font("HarmonyOS Sans", Font.PLAIN, 13));
        lblFoodStatus.setPreferredSize(new Dimension(80, 25));
        
        cboFoodStatus = new JComboBox<>(new String[]{"Đang phục vụ", "Tạm ngưng phục vụ"});
        cboFoodStatus.setFont(new Font("HarmonyOS Sans", Font.PLAIN, 13));
        cboFoodStatus.setPreferredSize(new Dimension(100, 30));
        
        statusRow.add(lblFoodStatus, BorderLayout.WEST);
        statusRow.add(cboFoodStatus, BorderLayout.CENTER);
        
        // Add spacing between elements
        fieldsPanel.add(nameRow);
        fieldsPanel.add(Box.createRigidArea(new Dimension(0, 8)));
        fieldsPanel.add(priceRow);
        fieldsPanel.add(Box.createRigidArea(new Dimension(0, 8)));
        fieldsPanel.add(imageRow);
        fieldsPanel.add(Box.createRigidArea(new Dimension(0, 8)));
        fieldsPanel.add(statusRow);
        
        // Right panel for buttons in vertical layout
        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new BoxLayout(buttonsPanel, BoxLayout.Y_AXIS));
        buttonsPanel.setOpaque(false);
        buttonsPanel.setBorder(BorderFactory.createEmptyBorder(0, 8, 0, 0));
        
        btnAddFood = createRoundButton("Thêm", BUTTON_COLOR, BUTTON_TEXT_COLOR);
        btnEditFood = createRoundButton("Cập Nhật", BUTTON_COLOR, BUTTON_TEXT_COLOR);
        btnDeleteFood = createRoundButton("Xóa", BUTTON_COLOR, BUTTON_TEXT_COLOR);
        btnCancelFood = createRoundButton("Huỷ", BUTTON_COLOR, BUTTON_TEXT_COLOR);
        
        // Set fixed width to buttons
        Dimension buttonSize = new Dimension(100, 35);
        btnAddFood.setPreferredSize(buttonSize);
        btnAddFood.setMinimumSize(buttonSize);
        btnAddFood.setMaximumSize(buttonSize);
        
        btnEditFood.setPreferredSize(buttonSize);
        btnEditFood.setMinimumSize(buttonSize);
        btnEditFood.setMaximumSize(buttonSize);
        
        btnDeleteFood.setPreferredSize(buttonSize);
        btnDeleteFood.setMinimumSize(buttonSize);
        btnDeleteFood.setMaximumSize(buttonSize);
        
        btnCancelFood.setPreferredSize(buttonSize);
        btnCancelFood.setMinimumSize(buttonSize);
        btnCancelFood.setMaximumSize(buttonSize);
        
        btnAddFood.addActionListener(e -> addFood());
        btnEditFood.addActionListener(e -> updateFood());
        btnDeleteFood.addActionListener(e -> deleteFood());
        btnCancelFood.addActionListener(e -> cancelFoodEdit());
        
        btnEditFood.setVisible(false);
        btnDeleteFood.setVisible(false);
        btnCancelFood.setVisible(false);
        
        // Add buttons to panel with alignment and spacing
        btnAddFood.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnEditFood.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnDeleteFood.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnCancelFood.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        buttonsPanel.add(btnAddFood);
        buttonsPanel.add(Box.createRigidArea(new Dimension(0, 8)));
        buttonsPanel.add(btnEditFood);
        buttonsPanel.add(Box.createRigidArea(new Dimension(0, 8)));
        buttonsPanel.add(btnDeleteFood);
        buttonsPanel.add(Box.createRigidArea(new Dimension(0, 8)));
        buttonsPanel.add(btnCancelFood);
        
        // Add all components to the form panel
        formPanel.add(fieldsPanel, BorderLayout.CENTER);
        formPanel.add(buttonsPanel, BorderLayout.EAST);
        
        // Add all panels to main panel
        panel.add(topPanel, BorderLayout.NORTH);
        panel.add(tableContainer, BorderLayout.CENTER);
        panel.add(formPanel, BorderLayout.SOUTH);
        
        return panel;
    }

    private JPanel createDrinkPanel() {
        JPanel panel = createCustomPanel();
        panel.setLayout(new BorderLayout(0, 0));
        
        // Top section with title
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setOpaque(false);
        
        // Title
        JLabel titleLabel = new JLabel("Dịch vụ đồ uống");
        titleLabel.setFont(new Font("Be Vietnam Pro", Font.BOLD, 22));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(8, 12, 3, 12));
        
        // Search panel
        JPanel searchPanel = new JPanel(new BorderLayout());
        searchPanel.setOpaque(false);
        searchPanel.setBorder(BorderFactory.createEmptyBorder(8, 0, 3, 12));
        
        txtDrinkSearch = createRoundTextField("Tìm kiếm", 15);
        btnSearchDrink = createSearchButton();
        btnSearchDrink.addActionListener(e -> searchDrink());
        
        searchPanel.add(txtDrinkSearch, BorderLayout.CENTER);
        searchPanel.add(btnSearchDrink, BorderLayout.EAST);
        
        // Create a container panel for proper layout
        JPanel headerPanel = new JPanel(new GridLayout(2, 1, 0, 0));
        headerPanel.setOpaque(false);
        headerPanel.add(titleLabel);
        
        JPanel searchContainer = new JPanel(new BorderLayout());
        searchContainer.setOpaque(false);
        searchContainer.add(searchPanel, BorderLayout.EAST);
        headerPanel.add(searchContainer);
        
        topPanel.add(headerPanel, BorderLayout.CENTER);
        
        // Table with padding
        JPanel tableContainer = new JPanel(new BorderLayout());
        tableContainer.setOpaque(false);
        tableContainer.setBorder(BorderFactory.createEmptyBorder(3, 0, 0, 0));
        
        drinkModel = new DefaultTableModel(
            new String[]{"MaDV", "Tên Đồ Uống", "Đơn Giá", "Trạng Thái", "BS"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        drinkTable = createStyledTable(drinkModel);
        drinkTable.setRowHeight(25);
        drinkTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                displayDrinkDetails(drinkTable.getSelectedRow());
            }
        });
        
        JScrollPane tableScrollPane = new JScrollPane(drinkTable);
        tableScrollPane.setBorder(BorderFactory.createEmptyBorder());
        tableScrollPane.getViewport().setBackground(PANEL_BACKGROUND);
        
        tableContainer.add(tableScrollPane, BorderLayout.CENTER);
        
        // Form panel - using BorderLayout for main content and right side buttons
        JPanel formPanel = new JPanel(new BorderLayout(8, 0));
        formPanel.setOpaque(false);
        formPanel.setBorder(BorderFactory.createEmptyBorder(8, 12, 8, 12));
        
        // Left panel for form fields
        JPanel fieldsPanel = new JPanel();
        fieldsPanel.setLayout(new BoxLayout(fieldsPanel, BoxLayout.Y_AXIS));
        fieldsPanel.setOpaque(false);
        
        // Drink name row
        JPanel nameRow = new JPanel(new BorderLayout(8, 0));
        nameRow.setOpaque(false);
        
        JLabel lblDrinkName = new JLabel("Tên đồ uống:");
        lblDrinkName.setFont(new Font("HarmonyOS Sans", Font.PLAIN, 13));
        lblDrinkName.setPreferredSize(new Dimension(80, 25));
        lblDrinkName.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        txtDrinkName = createRoundTextField("Nhập tên đồ uống", 25);
        
        nameRow.add(lblDrinkName, BorderLayout.WEST);
        nameRow.add(txtDrinkName, BorderLayout.CENTER);
        
        // Price and best seller row
        JPanel priceRow = new JPanel(new BorderLayout(8, 0));
        priceRow.setOpaque(false);
        
        JLabel lblDrinkPrice = new JLabel("Đơn giá:");
        lblDrinkPrice.setFont(new Font("HarmonyOS Sans", Font.PLAIN, 13));
        lblDrinkPrice.setPreferredSize(new Dimension(80, 25));
        
        JPanel priceFieldPanel = new JPanel(new BorderLayout(5, 0));
        priceFieldPanel.setOpaque(false);
        
        txtDrinkPrice = createRoundTextField("Nhập đơn giá", 15);
        
        chkDrinkBestSeller = new JCheckBox("Best seller");
        chkDrinkBestSeller.setOpaque(false);
        chkDrinkBestSeller.setFont(new Font("HarmonyOS Sans", Font.PLAIN, 13));
        
        priceFieldPanel.add(txtDrinkPrice, BorderLayout.CENTER);
        priceFieldPanel.add(chkDrinkBestSeller, BorderLayout.EAST);
        
        priceRow.add(lblDrinkPrice, BorderLayout.WEST);
        priceRow.add(priceFieldPanel, BorderLayout.CENTER);
        
        // Image row
        JPanel imageRow = new JPanel(new BorderLayout(8, 0));
        imageRow.setOpaque(false);
        
        JLabel lblImage = new JLabel("Hình:");
        lblImage.setFont(new Font("HarmonyOS Sans", Font.PLAIN, 13));
        lblImage.setPreferredSize(new Dimension(80, 25));
        
        JPanel imagePanel = new JPanel(new BorderLayout(8, 0));
        imagePanel.setOpaque(false);
        
        picDrink = new JLabel();
        picDrink.setPreferredSize(new Dimension(45, 45));
        picDrink.setBorder(BorderFactory.createLineBorder(BORDER_COLOR));
        
        btnDrinkImage = createRoundButton("Chọn", BUTTON_COLOR, BUTTON_TEXT_COLOR);
        btnDrinkImage.addActionListener(e -> selectDrinkImage());
        
        imagePanel.add(picDrink, BorderLayout.WEST);
        imagePanel.add(btnDrinkImage, BorderLayout.CENTER);
        
        imageRow.add(lblImage, BorderLayout.WEST);
        imageRow.add(imagePanel, BorderLayout.CENTER);
        
        // Status row (shown always)
        JPanel statusRow = new JPanel(new BorderLayout(8, 0));
        statusRow.setOpaque(false);
        
        lblDrinkStatus = new JLabel("Trạng thái:");
        lblDrinkStatus.setFont(new Font("HarmonyOS Sans", Font.PLAIN, 13));
        lblDrinkStatus.setPreferredSize(new Dimension(80, 25));
        
        cboDrinkStatus = new JComboBox<>(new String[]{"Đang phục vụ", "Tạm ngưng phục vụ"});
        cboDrinkStatus.setFont(new Font("HarmonyOS Sans", Font.PLAIN, 13));
        cboDrinkStatus.setPreferredSize(new Dimension(100, 30));
        
        statusRow.add(lblDrinkStatus, BorderLayout.WEST);
        statusRow.add(cboDrinkStatus, BorderLayout.CENTER);
        
        // Add spacing between elements
        fieldsPanel.add(nameRow);
        fieldsPanel.add(Box.createRigidArea(new Dimension(0, 8)));
        fieldsPanel.add(priceRow);
        fieldsPanel.add(Box.createRigidArea(new Dimension(0, 8)));
        fieldsPanel.add(imageRow);
        fieldsPanel.add(Box.createRigidArea(new Dimension(0, 8)));
        fieldsPanel.add(statusRow);
        
        // Right panel for buttons in vertical layout
        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new BoxLayout(buttonsPanel, BoxLayout.Y_AXIS));
        buttonsPanel.setOpaque(false);
        buttonsPanel.setBorder(BorderFactory.createEmptyBorder(0, 8, 0, 0));
        
        btnAddDrink = createRoundButton("Thêm", BUTTON_COLOR, BUTTON_TEXT_COLOR);
        btnEditDrink = createRoundButton("Cập Nhật", BUTTON_COLOR, BUTTON_TEXT_COLOR);
        btnDeleteDrink = createRoundButton("Xóa", BUTTON_COLOR, BUTTON_TEXT_COLOR);
        btnCancelDrink = createRoundButton("Huỷ", BUTTON_COLOR, BUTTON_TEXT_COLOR);
        
        // Set fixed width to buttons
        Dimension buttonSize = new Dimension(100, 35);
        btnAddDrink.setPreferredSize(buttonSize);
        btnAddDrink.setMinimumSize(buttonSize);
        btnAddDrink.setMaximumSize(buttonSize);
        
        btnEditDrink.setPreferredSize(buttonSize);
        btnEditDrink.setMinimumSize(buttonSize);
        btnEditDrink.setMaximumSize(buttonSize);
        
        btnDeleteDrink.setPreferredSize(buttonSize);
        btnDeleteDrink.setMinimumSize(buttonSize);
        btnDeleteDrink.setMaximumSize(buttonSize);
        
        btnCancelDrink.setPreferredSize(buttonSize);
        btnCancelDrink.setMinimumSize(buttonSize);
        btnCancelDrink.setMaximumSize(buttonSize);
        
        btnAddDrink.addActionListener(e -> addDrink());
        btnEditDrink.addActionListener(e -> updateDrink());
        btnDeleteDrink.addActionListener(e -> deleteDrink());
        btnCancelDrink.addActionListener(e -> cancelDrinkEdit());
        
        btnEditDrink.setVisible(false);
        btnDeleteDrink.setVisible(false);
        btnCancelDrink.setVisible(false);
        
        // Add buttons to panel with alignment and spacing
        btnAddDrink.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnEditDrink.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnDeleteDrink.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnCancelDrink.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        buttonsPanel.add(btnAddDrink);
        buttonsPanel.add(Box.createRigidArea(new Dimension(0, 8)));
        buttonsPanel.add(btnEditDrink);
        buttonsPanel.add(Box.createRigidArea(new Dimension(0, 8)));
        buttonsPanel.add(btnDeleteDrink);
        buttonsPanel.add(Box.createRigidArea(new Dimension(0, 8)));
        buttonsPanel.add(btnCancelDrink);
        
        // Add all components to the form panel
        formPanel.add(fieldsPanel, BorderLayout.CENTER);
        formPanel.add(buttonsPanel, BorderLayout.EAST);
        
        // Add all panels to main panel
        panel.add(topPanel, BorderLayout.NORTH);
        panel.add(tableContainer, BorderLayout.CENTER);
        panel.add(formPanel, BorderLayout.SOUTH);
        
        return panel;
    }

    private JPanel createPhoneCardPanel() {
        JPanel panel = createCustomPanel();
        panel.setLayout(new BorderLayout(0, 0));
        
        // Top section with title
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setOpaque(false);
        
        // Title
        JLabel titleLabel = new JLabel("Dịch vụ thẻ cào");
        titleLabel.setFont(new Font("Be Vietnam Pro", Font.BOLD, 22));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(8, 12, 3, 12));
        
        // Search panel
        JPanel searchPanel = new JPanel(new BorderLayout());
        searchPanel.setOpaque(false);
        searchPanel.setBorder(BorderFactory.createEmptyBorder(8, 0, 3, 12));
        
        txtCardSearch = createRoundTextField("Tìm kiếm", 15);
        btnSearchCard = createSearchButton();
        btnSearchCard.addActionListener(e -> searchCard());
        
        searchPanel.add(txtCardSearch, BorderLayout.CENTER);
        searchPanel.add(btnSearchCard, BorderLayout.EAST);
        
        // Create a container panel for proper layout
        JPanel headerPanel = new JPanel(new GridLayout(2, 1, 0, 0));
        headerPanel.setOpaque(false);
        headerPanel.add(titleLabel);
        
        JPanel searchContainer = new JPanel(new BorderLayout());
        searchContainer.setOpaque(false);
        searchContainer.add(searchPanel, BorderLayout.EAST);
        headerPanel.add(searchContainer);
        
        topPanel.add(headerPanel, BorderLayout.CENTER);
        
        // Table with padding
        JPanel tableContainer = new JPanel(new BorderLayout());
        tableContainer.setOpaque(false);
        tableContainer.setBorder(BorderFactory.createEmptyBorder(3, 0, 0, 0));
        
        cardModel = new DefaultTableModel(
            new String[]{"MaDV", "Loại Thẻ", "Mệnh Giá"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        cardTable = createStyledTable(cardModel);
        cardTable.setRowHeight(25);
        cardTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                displayCardDetails(cardTable.getSelectedRow());
            }
        });
        
        JScrollPane tableScrollPane = new JScrollPane(cardTable);
        tableScrollPane.setBorder(BorderFactory.createEmptyBorder());
        tableScrollPane.getViewport().setBackground(PANEL_BACKGROUND);
        
        tableContainer.add(tableScrollPane, BorderLayout.CENTER);
        
        // Form panel - using BorderLayout for main content and right side buttons
        JPanel formPanel = new JPanel(new BorderLayout(8, 0));
        formPanel.setOpaque(false);
        formPanel.setBorder(BorderFactory.createEmptyBorder(8, 12, 8, 12));
        
        // Left panel for form fields
        JPanel fieldsPanel = new JPanel();
        fieldsPanel.setLayout(new BoxLayout(fieldsPanel, BoxLayout.Y_AXIS));
        fieldsPanel.setOpaque(false);
        
        // Card type row
        JPanel typeRow = new JPanel(new BorderLayout(8, 0));
        typeRow.setOpaque(false);
        
        JLabel lblCardType = new JLabel("Loại thẻ:");
        lblCardType.setFont(new Font("HarmonyOS Sans", Font.PLAIN, 13));
        lblCardType.setPreferredSize(new Dimension(80, 25));
        
        txtCardType = createRoundTextField("Nhập loại thẻ", 25);
        
        typeRow.add(lblCardType, BorderLayout.WEST);
        typeRow.add(txtCardType, BorderLayout.CENTER);
        
        // Value row
        JPanel valueRow = new JPanel(new BorderLayout(8, 0));
        valueRow.setOpaque(false);
        
        JLabel lblCardValue = new JLabel("Mệnh giá:");
        lblCardValue.setFont(new Font("HarmonyOS Sans", Font.PLAIN, 13));
        lblCardValue.setPreferredSize(new Dimension(80, 25));
        
        txtCardValue = createRoundTextField("Nhập mệnh giá", 20);
        
        valueRow.add(lblCardValue, BorderLayout.WEST);
        valueRow.add(txtCardValue, BorderLayout.CENTER);
        
        // Image row
        JPanel imageRow = new JPanel(new BorderLayout(8, 0));
        imageRow.setOpaque(false);
        
        JLabel lblImage = new JLabel("Hình:");
        lblImage.setFont(new Font("HarmonyOS Sans", Font.PLAIN, 13));
        lblImage.setPreferredSize(new Dimension(80, 25));
        
        JPanel imagePanel = new JPanel(new BorderLayout(8, 0));
        imagePanel.setOpaque(false);
        
        picCard = new JLabel();
        picCard.setPreferredSize(new Dimension(45, 45));
        picCard.setBorder(BorderFactory.createLineBorder(BORDER_COLOR));
        
        btnCardImage = createRoundButton("Chọn", BUTTON_COLOR, BUTTON_TEXT_COLOR);
        btnCardImage.addActionListener(e -> selectCardImage());
        
        imagePanel.add(picCard, BorderLayout.WEST);
        imagePanel.add(btnCardImage, BorderLayout.CENTER);
        
        imageRow.add(lblImage, BorderLayout.WEST);
        imageRow.add(imagePanel, BorderLayout.CENTER);
        
        // Add spacing between elements
        fieldsPanel.add(typeRow);
        fieldsPanel.add(Box.createRigidArea(new Dimension(0, 8)));
        fieldsPanel.add(valueRow);
        fieldsPanel.add(Box.createRigidArea(new Dimension(0, 8)));
        fieldsPanel.add(imageRow);

        // Right panel for buttons in vertical layout
        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new BoxLayout(buttonsPanel, BoxLayout.Y_AXIS));
        buttonsPanel.setOpaque(false);
        buttonsPanel.setBorder(BorderFactory.createEmptyBorder(0, 8, 0, 0));
        
        btnAddCard = createRoundButton("Thêm", BUTTON_COLOR, BUTTON_TEXT_COLOR);
        btnEditCard = createRoundButton("Cập Nhật", BUTTON_COLOR, BUTTON_TEXT_COLOR);
        btnDeleteCard = createRoundButton("Xóa", BUTTON_COLOR, BUTTON_TEXT_COLOR);
        btnCancelCard = createRoundButton("Huỷ", BUTTON_COLOR, BUTTON_TEXT_COLOR);
        
        // Set fixed width to buttons
        Dimension buttonSize = new Dimension(100, 35);
        btnAddCard.setPreferredSize(buttonSize);
        btnAddCard.setMinimumSize(buttonSize);
        btnAddCard.setMaximumSize(buttonSize);
        
        btnEditCard.setPreferredSize(buttonSize);
        btnEditCard.setMinimumSize(buttonSize);
        btnEditCard.setMaximumSize(buttonSize);
        
        btnDeleteCard.setPreferredSize(buttonSize);
        btnDeleteCard.setMinimumSize(buttonSize);
        btnDeleteCard.setMaximumSize(buttonSize);
        
        btnCancelCard.setPreferredSize(buttonSize);
        btnCancelCard.setMinimumSize(buttonSize);
        btnCancelCard.setMaximumSize(buttonSize);
        
        btnAddCard.addActionListener(e -> addCard());
        btnEditCard.addActionListener(e -> updateCard());
        btnDeleteCard.addActionListener(e -> deleteCard());
        btnCancelCard.addActionListener(e -> cancelCardEdit());
        
        btnEditCard.setVisible(false);
        btnDeleteCard.setVisible(false);
        btnCancelCard.setVisible(false);
        
        // Add buttons to panel with alignment and spacing
        btnAddCard.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnEditCard.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnDeleteCard.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnCancelCard.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        buttonsPanel.add(btnAddCard);
        buttonsPanel.add(Box.createRigidArea(new Dimension(0, 8)));
        buttonsPanel.add(btnEditCard);
        buttonsPanel.add(Box.createRigidArea(new Dimension(0, 8)));
        buttonsPanel.add(btnDeleteCard);
        buttonsPanel.add(Box.createRigidArea(new Dimension(0, 8)));
        buttonsPanel.add(btnCancelCard);
        
        // Add all components to the form panel
        formPanel.add(fieldsPanel, BorderLayout.CENTER);
        formPanel.add(buttonsPanel, BorderLayout.EAST);
        
        // Add all panels to main panel
        panel.add(topPanel, BorderLayout.NORTH);
        panel.add(tableContainer, BorderLayout.CENTER);
        panel.add(formPanel, BorderLayout.SOUTH);
        
        return panel;
    }

    private JPanel createCustomPanel() {
        JPanel panel = new JPanel();
        panel.setBackground(PANEL_BACKGROUND);
        panel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        return panel;
    }

    private JTextField createRoundTextField(String placeholder, int columns) {
        JTextField textField = new JTextField(columns);
        textField.setBorder(BorderFactory.createCompoundBorder(
            new RoundedBorder(BUTTON_COLOR, 1, 15),
            BorderFactory.createEmptyBorder(8, 12, 8, 12)
        ));
        textField.setFont(new Font("HarmonyOS Sans", Font.PLAIN, 14));

        // Add placeholder effect
        textField.setText(placeholder);
        textField.setForeground(PLACEHOLDER_COLOR);

        textField.addFocusListener(new java.awt.event.FocusAdapter() {
            @Override
            public void focusGained(java.awt.event.FocusEvent evt) {
                if (textField.getText().equals(placeholder)) {
                    textField.setText("");
                    textField.setForeground(TEXT_COLOR);
                }
            }

            @Override
            public void focusLost(java.awt.event.FocusEvent evt) {
                if (textField.getText().isEmpty()) {
                    textField.setText(placeholder);
                    textField.setForeground(PLACEHOLDER_COLOR);
                }
            }
        });

        return textField;
    }

    private JButton createSearchButton() {
        JButton button = new JButton("🔍");
        button.setPreferredSize(new Dimension(40, 40));
        button.setBackground(BUTTON_COLOR);
        button.setForeground(Color.WHITE);
        button.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        button.setFocusPainted(false);
        button.setFont(new Font("Segoe UI", Font.BOLD, 14));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        // Add hover effect
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(BUTTON_HOVER_COLOR);
            }
            
            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(BUTTON_COLOR);
            }
        });
        
        return button;
    }

    private JButton createRoundButton(String text, Color bgColor, Color fgColor) {
        JButton button = new JButton(text);
        button.setBackground(bgColor);
        button.setForeground(fgColor);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setFont(new Font("HarmonyOS Sans", Font.BOLD, 14));
        button.setBorder(BorderFactory.createEmptyBorder(8, 20, 8, 20));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setUI(new RoundedButtonUI(20));

        // Add hover effect
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(BUTTON_HOVER_COLOR);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(bgColor);
            }
        });

        return button;
    }

    // Inner class for rounded button UI
    private class RoundedButtonUI extends BasicButtonUI {
        private int radius;

        public RoundedButtonUI(int radius) {
            this.radius = radius;
        }

        @Override
        public void paint(Graphics g, JComponent c) {
            AbstractButton b = (AbstractButton) c;
            ButtonModel model = b.getModel();
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            
            if (model.isPressed()) {
                g2.setColor(b.getBackground().darker());
            } else if (model.isRollover()) {
                g2.setColor(BUTTON_HOVER_COLOR);
            } else {
                g2.setColor(b.getBackground());
            }
            
            g2.fillRoundRect(0, 0, b.getWidth(), b.getHeight(), radius, radius);
            super.paint(g, c);
        }
    }

    // Inner class for rounded border
    private class RoundedBorder extends AbstractBorder {
        private Color color;
        private int thickness;
        private int radius;
        private Insets insets;

        public RoundedBorder(Color color, int thickness, int radius) {
            this.color = color;
            this.thickness = thickness;
            this.radius = radius;
            this.insets = new Insets(thickness, thickness, thickness, thickness);
        }

        @Override
        public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(color);
            g2.drawRoundRect(x, y, width - 1, height - 1, radius, radius);
            g2.dispose();
        }

        @Override
        public Insets getBorderInsets(Component c) {
            return insets;
        }

        @Override
        public Insets getBorderInsets(Component c, Insets insets) {
            return getBorderInsets(c);
        }
    }

    private JTextField createSimpleTextField(String placeholder) {
        // Replace this with createRoundTextField since we want all text fields to be rounded
        return createRoundTextField(placeholder, 20);
    }

    private JTable createStyledTable(DefaultTableModel model) {
        JTable table = new JTable(model);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setRowHeight(30);
        table.setFont(new Font("HarmonyOS Sans", Font.PLAIN, 14));
        table.setShowGrid(true);
        table.setGridColor(TABLE_BORDER_COLOR);
        table.setBorder(BorderFactory.createLineBorder(TABLE_BORDER_COLOR));
        
        // Style header
        JTableHeader header = table.getTableHeader();
        header.setBackground(TABLE_HEADER_COLOR);
        header.setForeground(TABLE_HEADER_TEXT_COLOR);
        header.setFont(new Font("HarmonyOS Sans", Font.BOLD, 14));
        header.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, TABLE_BORDER_COLOR));
        
        // Cell renderer for center alignment
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        
        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }
        
        return table;
    }

    private void loadFoodData() {
        foodModel.setRowCount(0);

        List<DvDoAn> foodList;
        String searchText = txtFoodSearch.getText().trim();

        // Skip search if the text is the placeholder
        if (searchText.equals("Tìm kiếm")) {
            searchText = "";
        }

        if (searchText.isEmpty()) {
            foodList = controller.layDanhSachDoAn();
        } else {
            foodList = controller.timKiemDoAn(searchText);
        }

        for (DvDoAn food : foodList) {
            foodModel.addRow(new Object[]{
                    food.getMaDV(),
                    food.getTenDoAn(),
                    formatPrice(food.getDonGia()),
                    food.getTrangThai(),
                    food.getBestSeller() ? "True" : "False"
            });
        }
    }

    private void loadDrinkData() {
        drinkModel.setRowCount(0);

        List<DvDoUong> drinkList;
        String searchText = txtDrinkSearch.getText().trim();

        // Skip search if the text is the placeholder
        if (searchText.equals("Tìm kiếm")) {
            searchText = "";
        }

        if (searchText.isEmpty()) {
            drinkList = controller.layDanhSachDoUong();
        } else {
            drinkList = controller.timKiemDoUong(searchText);
        }

        for (DvDoUong drink : drinkList) {
            drinkModel.addRow(new Object[]{
                drink.getMaDV(),
                drink.getTenDoUong(),
                formatPrice(drink.getDonGia()),
                drink.getTrangThai(),
                drink.getBestSeller() ? "True" : "False"
            });
        }
    }

    private void loadCardData() {
        cardModel.setRowCount(0);

        List<DvTheCao> cardList;
        String searchText = txtCardSearch.getText().trim();

        // Skip search if the text is the placeholder
        if (searchText.equals("Tìm kiếm")) {
            searchText = "";
        }

        if (searchText.isEmpty()) {
            cardList = controller.layDanhSachTheCao();
        } else {
            cardList = controller.timKiemTheCao(searchText);
        }

        for (DvTheCao card : cardList) {
            cardModel.addRow(new Object[]{
                card.getMaDV(),
                card.getLoaiThe(),
                formatPrice(card.getMenhGia())
            });
        }
    }

    private void displayFoodDetails(int row) {
        if (row < 0) return;

        String maDV = foodModel.getValueAt(row, 0).toString();
        String tenDoAn = foodModel.getValueAt(row, 1).toString();
        String donGiaStr = foodModel.getValueAt(row, 2).toString().replace(" VNĐ", "").replace(",", "");
        int donGia = Integer.parseInt(donGiaStr);
        String trangThai = foodModel.getValueAt(row, 3).toString();
        boolean bestSeller = !foodModel.getValueAt(row, 4).toString().isEmpty();

        // Display values in form fields
        txtFoodName.setText(tenDoAn);
        txtFoodName.setForeground(TEXT_COLOR);

        txtFoodPrice.setText(String.valueOf(donGia));
        txtFoodPrice.setForeground(TEXT_COLOR);

        chkFoodBestSeller.setSelected(bestSeller);
        cboFoodStatus.setSelectedItem(trangThai);

        // Load image if available
        String baseImageName = tenDoAn;
        String[] imageExtensions = {".png", ".jpg", ".jpeg"};
        File imageFile = null;

        for (String ext : imageExtensions) {
            File tempFile = new File("Images/" + baseImageName + ext);
            if (tempFile.exists()) {
                imageFile = tempFile;
                break;
            }
        }

        if (imageFile != null) {
            try {
                foodImage = ImageIO.read(imageFile);
                Image scaledImage = foodImage.getScaledInstance(45, 45, Image.SCALE_SMOOTH);
                picFood.setIcon(new ImageIcon(scaledImage));
            } catch (IOException e) {
                JOptionPane.showMessageDialog(this, "Lỗi khi tải hình ảnh: " + e.getMessage(),
                        "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            picFood.setIcon(null);
        }

        // Show edit controls, hide add controls
        btnAddFood.setVisible(false);
        btnEditFood.setVisible(true);
        btnDeleteFood.setVisible(true);
        btnCancelFood.setVisible(true);
        lblFoodStatus.setVisible(true);
        cboFoodStatus.setVisible(true);
    }

    private void selectFoodImage() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter(
                "Image Files", "jpg", "jpeg", "png", "bmp"));
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        int result = fileChooser.showOpenDialog(this);

        if (result == JFileChooser.APPROVE_OPTION) {
            try {
                File selectedFile = fileChooser.getSelectedFile();
                foodImage = ImageIO.read(selectedFile);
                Image scaledImage = foodImage.getScaledInstance(45, 45, Image.SCALE_SMOOTH);
                picFood.setIcon(new ImageIcon(scaledImage));
            } catch (IOException e) {
                JOptionPane.showMessageDialog(this, "Lỗi khi chọn hình ảnh: " + e.getMessage(),
                        "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void addFood() {
        try {
            int confirm = JOptionPane.showConfirmDialog(
                this,
                "Bạn có chắc chắn muốn thêm dịch vụ đồ ăn này không?",
                "Xác nhận",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE
            );

            if (confirm != JOptionPane.YES_OPTION) {
                return;
            }

            String tenDoAn = txtFoodName.getText().trim();
            if (tenDoAn.equals("Nhập tên đồ ăn")) {
                tenDoAn = "";
            }

            String priceText = txtFoodPrice.getText().trim();
            if (priceText.equals("Nhập đơn giá")) {
                priceText = "";
            }

            if (tenDoAn.isEmpty() || priceText.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin!",
                        "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }

            int donGia = Integer.parseInt(priceText);
            boolean bestSeller = chkFoodBestSeller.isSelected();

            if (donGia <= 0) {
                JOptionPane.showMessageDialog(this, "Đơn giá phải lớn hơn 0!",
                        "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }

            controller.themDoAn(tenDoAn, donGia, bestSeller);

            // Save image if one was selected
            if (picFood.getIcon() != null && foodImage != null) {
                saveImageToFolder(foodImage, tenDoAn + ".png");
            }

            JOptionPane.showMessageDialog(this, "Thêm dịch vụ đồ ăn thành công");
            cancelFoodEdit();
            loadFoodData();
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Đơn giá phải là số!",
                    "Lỗi", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage(),
                    "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void updateFood() {
        try {
            int selectedRow = foodTable.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn một món ăn để sửa!",
                        "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }

            int confirm = JOptionPane.showConfirmDialog(
                this,
                "Bạn có chắc chắn muốn sửa thông tin dịch vụ đồ ăn này không?",
                "Xác nhận",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE
            );

            if (confirm != JOptionPane.YES_OPTION) {
                return;
            }

            String maDV = foodModel.getValueAt(selectedRow, 0).toString();
            String tenDoAn = txtFoodName.getText().trim();
            if (tenDoAn.equals("Nhập tên đồ ăn")) {
                tenDoAn = "";
            }

            String priceText = txtFoodPrice.getText().trim();
            if (priceText.equals("Nhập đơn giá")) {
                priceText = "";
            }

            if (tenDoAn.isEmpty() || priceText.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin!",
                        "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }

            int donGia = Integer.parseInt(priceText);
            boolean bestSeller = chkFoodBestSeller.isSelected();
            String trangThai = cboFoodStatus.getSelectedItem().toString();

            if (donGia <= 0) {
                JOptionPane.showMessageDialog(this, "Đơn giá phải lớn hơn 0!",
                        "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }

            controller.suaDoAn(maDV, tenDoAn, donGia, bestSeller, trangThai);

            // Save image if one was selected
            if (picFood.getIcon() != null && foodImage != null) {
                saveImageToFolder(foodImage, tenDoAn + ".png");
            }

            JOptionPane.showMessageDialog(this, "Sửa dịch vụ đồ ăn thành công");
            loadFoodData();

            // Reselect the row
            for (int i = 0; i < foodTable.getRowCount(); i++) {
                if (foodTable.getValueAt(i, 0).equals(maDV)) {
                    foodTable.setRowSelectionInterval(i, i);
                    break;
                }
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Đơn giá phải là số!",
                    "Lỗi", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage(),
                    "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void deleteFood() {
        try {
            int selectedRow = foodTable.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn một món ăn để xóa!",
                        "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }

            int confirm = JOptionPane.showConfirmDialog(
                this,
                "Bạn có chắc chắn muốn xóa dịch vụ đồ ăn này không?",
                "Xác nhận",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE
            );

            if (confirm != JOptionPane.YES_OPTION) {
                return;
            }

            String maDV = foodModel.getValueAt(selectedRow, 0).toString();
            String tenDoAn = foodModel.getValueAt(selectedRow, 1).toString();

            controller.xoaDichVu(maDV);

            // Delete image if exists
            String imgName = tenDoAn + ".png";
            File imgFile = new File("Images/" + imgName);
            if (imgFile.exists()) {
                imgFile.delete();
            }

            JOptionPane.showMessageDialog(this, "Xóa dịch vụ đồ ăn thành công");
            loadFoodData();
            cancelFoodEdit();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage(),
                    "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void cancelFoodEdit() {
        // Reset text fields with placeholders
        txtFoodName.setText("Nhập tên đồ ăn");
        txtFoodName.setForeground(PLACEHOLDER_COLOR);

        txtFoodPrice.setText("Nhập đơn giá");
        txtFoodPrice.setForeground(PLACEHOLDER_COLOR);

        chkFoodBestSeller.setSelected(false);
        cboFoodStatus.setSelectedIndex(0);
        picFood.setIcon(null);
        foodTable.clearSelection();

        // Show/hide appropriate buttons
        btnAddFood.setVisible(true);
        btnEditFood.setVisible(false);
        btnDeleteFood.setVisible(false);
        btnCancelFood.setVisible(false);
        lblFoodStatus.setVisible(false);
        cboFoodStatus.setVisible(false);
    }

    private void saveImageToFolder(Image imageToSave, String imageName) {
        File imagesFolder = new File("Images");
        if (!imagesFolder.exists()) {
            imagesFolder.mkdir();
        }

        File outputFile = new File(imagesFolder, imageName);
        try {
            BufferedImage bufferedImage = new BufferedImage(
                imageToSave.getWidth(null),
                imageToSave.getHeight(null),
                BufferedImage.TYPE_INT_RGB);

            Graphics2D g2d = bufferedImage.createGraphics();
            g2d.drawImage(imageToSave, 0, 0, null);
            g2d.dispose();

            ImageIO.write(bufferedImage, "png", outputFile);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Lỗi khi lưu ảnh: " + e.getMessage(),
                    "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private String formatPrice(int price) {
        DecimalFormat df = new DecimalFormat("#,###");
        return df.format(price) + " VNĐ";
    }

    // Drink service operations
    private void searchDrink() {
        loadDrinkData();
    }

    private void displayDrinkDetails(int row) {
        if (row < 0) return;

        String maDV = drinkModel.getValueAt(row, 0).toString();
        String tenDoUong = drinkModel.getValueAt(row, 1).toString();
        String donGiaStr = drinkModel.getValueAt(row, 2).toString().replace(" VNĐ", "").replace(",", "");
        int donGia = Integer.parseInt(donGiaStr);
        String trangThai = drinkModel.getValueAt(row, 3).toString();
        boolean bestSeller = !drinkModel.getValueAt(row, 4).toString().isEmpty();

        // Display values in form fields
        txtDrinkName.setText(tenDoUong);
        txtDrinkName.setForeground(TEXT_COLOR);

        txtDrinkPrice.setText(String.valueOf(donGia));
        txtDrinkPrice.setForeground(TEXT_COLOR);

        chkDrinkBestSeller.setSelected(bestSeller);
        cboDrinkStatus.setSelectedItem(trangThai);

        // Load image if available
        String baseImageName = tenDoUong;
        String[] imageExtensions = {".png", ".jpg", ".jpeg"};
        File imageFile = null;

        for (String ext : imageExtensions) {
            File tempFile = new File("Images/" + baseImageName + ext);
            if (tempFile.exists()) {
                imageFile = tempFile;
                break;
            }
        }

        if (imageFile != null) {
            try {
                drinkImage = ImageIO.read(imageFile);
                Image scaledImage = drinkImage.getScaledInstance(45, 45, Image.SCALE_SMOOTH);
                picDrink.setIcon(new ImageIcon(scaledImage));
            } catch (IOException e) {
                JOptionPane.showMessageDialog(this, "Lỗi khi tải hình ảnh: " + e.getMessage(),
                        "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            picDrink.setIcon(null);
        }

        // Show edit controls, hide add controls
        btnAddDrink.setVisible(false);
        btnEditDrink.setVisible(true);
        btnDeleteDrink.setVisible(true);
        btnCancelDrink.setVisible(true);
        lblDrinkStatus.setVisible(true);
        cboDrinkStatus.setVisible(true);
    }

    private void selectDrinkImage() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter(
                "Image Files", "jpg", "jpeg", "png", "bmp"));
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        int result = fileChooser.showOpenDialog(this);

        if (result == JFileChooser.APPROVE_OPTION) {
            try {
                File selectedFile = fileChooser.getSelectedFile();
                drinkImage = ImageIO.read(selectedFile);
                Image scaledImage = drinkImage.getScaledInstance(45, 45, Image.SCALE_SMOOTH);
                picDrink.setIcon(new ImageIcon(scaledImage));
            } catch (IOException e) {
                JOptionPane.showMessageDialog(this, "Lỗi khi chọn hình ảnh: " + e.getMessage(),
                        "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void addDrink() {
        try {
            int confirm = JOptionPane.showConfirmDialog(
                this,
                "Bạn có chắc chắn muốn thêm dịch vụ đồ uống này không?",
                "Xác nhận",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE
            );

            if (confirm != JOptionPane.YES_OPTION) {
                return;
            }

            String tenDoUong = txtDrinkName.getText().trim();
            if (tenDoUong.equals("Nhập tên đồ uống")) {
                tenDoUong = "";
            }

            String priceText = txtDrinkPrice.getText().trim();
            if (priceText.equals("Nhập đơn giá")) {
                priceText = "";
            }

            if (tenDoUong.isEmpty() || priceText.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin!",
                        "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }

            int donGia = Integer.parseInt(priceText);
            boolean bestSeller = chkDrinkBestSeller.isSelected();

            if (donGia <= 0) {
                JOptionPane.showMessageDialog(this, "Đơn giá phải lớn hơn 0!",
                        "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }

            controller.themDoUong(tenDoUong, donGia, bestSeller);

            // Save image if one was selected
            if (picDrink.getIcon() != null && drinkImage != null) {
                saveImageToFolder(drinkImage, tenDoUong + ".png");
            }

            JOptionPane.showMessageDialog(this, "Thêm dịch vụ đồ uống thành công");
            cancelDrinkEdit();
            loadDrinkData();
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Đơn giá phải là số!",
                    "Lỗi", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage(),
                    "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void updateDrink() {
        try {
            int selectedRow = drinkTable.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn một đồ uống để sửa!",
                        "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }

            int confirm = JOptionPane.showConfirmDialog(
                this,
                "Bạn có chắc chắn muốn sửa thông tin dịch vụ đồ uống này không?",
                "Xác nhận",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE
            );

            if (confirm != JOptionPane.YES_OPTION) {
                return;
            }

            String maDV = drinkModel.getValueAt(selectedRow, 0).toString();
            String tenDoUong = txtDrinkName.getText().trim();
            if (tenDoUong.equals("Nhập tên đồ uống")) {
                tenDoUong = "";
            }

            String priceText = txtDrinkPrice.getText().trim();
            if (priceText.equals("Nhập đơn giá")) {
                priceText = "";
            }

            if (tenDoUong.isEmpty() || priceText.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin!",
                        "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }

            int donGia = Integer.parseInt(priceText);
            boolean bestSeller = chkDrinkBestSeller.isSelected();
            String trangThai = cboDrinkStatus.getSelectedItem().toString();

            if (donGia <= 0) {
                JOptionPane.showMessageDialog(this, "Đơn giá phải lớn hơn 0!",
                        "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }

            controller.suaDoUong(maDV, tenDoUong, donGia, bestSeller, trangThai);

            // Save image if one was selected
            if (picDrink.getIcon() != null && drinkImage != null) {
                saveImageToFolder(drinkImage, tenDoUong + ".png");
            }

            JOptionPane.showMessageDialog(this, "Sửa dịch vụ đồ uống thành công");
            loadDrinkData();

            // Reselect the row
            for (int i = 0; i < drinkTable.getRowCount(); i++) {
                if (drinkTable.getValueAt(i, 0).equals(maDV)) {
                    drinkTable.setRowSelectionInterval(i, i);
                    break;
                }
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Đơn giá phải là số!",
                    "Lỗi", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage(),
                    "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void deleteDrink() {
        try {
            int selectedRow = drinkTable.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn một đồ uống để xóa!",
                        "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }

            int confirm = JOptionPane.showConfirmDialog(
                this,
                "Bạn có chắc chắn muốn xóa dịch vụ đồ uống này không?",
                "Xác nhận",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE
            );

            if (confirm != JOptionPane.YES_OPTION) {
                return;
            }

            String maDV = drinkModel.getValueAt(selectedRow, 0).toString();
            String tenDoUong = drinkModel.getValueAt(selectedRow, 1).toString();

            controller.xoaDichVu(maDV);

            // Delete image if exists
            String imgName = tenDoUong + ".png";
            File imgFile = new File("Images/" + imgName);
            if (imgFile.exists()) {
                imgFile.delete();
            }

            JOptionPane.showMessageDialog(this, "Xóa dịch vụ đồ uống thành công");
            loadDrinkData();
            cancelDrinkEdit();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage(),
                    "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void cancelDrinkEdit() {
        // Reset text fields with placeholders
        txtDrinkName.setText("Nhập tên đồ uống");
        txtDrinkName.setForeground(PLACEHOLDER_COLOR);

        txtDrinkPrice.setText("Nhập đơn giá");
        txtDrinkPrice.setForeground(PLACEHOLDER_COLOR);

        chkDrinkBestSeller.setSelected(false);
        cboDrinkStatus.setSelectedIndex(0);
        picDrink.setIcon(null);
        drinkTable.clearSelection();

        // Show/hide appropriate buttons
        btnAddDrink.setVisible(true);
        btnEditDrink.setVisible(false);
        btnDeleteDrink.setVisible(false);
        btnCancelDrink.setVisible(false);
        lblDrinkStatus.setVisible(false);
        cboDrinkStatus.setVisible(false);
    }

    // Phone Card service operations
    private void searchCard() {
        loadCardData();
    }

    private void displayCardDetails(int row) {
        if (row < 0) return;

        String maDV = cardModel.getValueAt(row, 0).toString();
        String loaiThe = cardModel.getValueAt(row, 1).toString();
        String menhGiaStr = cardModel.getValueAt(row, 2).toString().replace(" VNĐ", "").replace(",", "");
        int menhGia = Integer.parseInt(menhGiaStr);

        // Display values in form fields
        txtCardType.setText(loaiThe);
        txtCardType.setForeground(TEXT_COLOR);

        txtCardValue.setText(String.valueOf(menhGia));
        txtCardValue.setForeground(TEXT_COLOR);

        // Load image if available
        String baseImageName = loaiThe;
        String[] imageExtensions = {".png", ".jpg", ".jpeg"};
        File imageFile = null;

        for (String ext : imageExtensions) {
            File tempFile = new File("Images/" + baseImageName + ext);
            if (tempFile.exists()) {
                imageFile = tempFile;
                break;
            }
        }

        if (imageFile != null) {
            try {
                cardImage = ImageIO.read(imageFile);
                Image scaledImage = cardImage.getScaledInstance(45, 45, Image.SCALE_SMOOTH);
                picCard.setIcon(new ImageIcon(scaledImage));
            } catch (IOException e) {
                JOptionPane.showMessageDialog(this, "Lỗi khi tải hình ảnh: " + e.getMessage(),
                        "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            picCard.setIcon(null);
        }

        // Show edit controls, hide add controls
        btnAddCard.setVisible(false);
        btnEditCard.setVisible(true);
        btnDeleteCard.setVisible(true);
        btnCancelCard.setVisible(true);
    }

    private void selectCardImage() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter(
                "Image Files", "jpg", "jpeg", "png", "bmp"));
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        int result = fileChooser.showOpenDialog(this);

        if (result == JFileChooser.APPROVE_OPTION) {
            try {
                File selectedFile = fileChooser.getSelectedFile();
                cardImage = ImageIO.read(selectedFile);
                Image scaledImage = cardImage.getScaledInstance(45, 45, Image.SCALE_SMOOTH);
                picCard.setIcon(new ImageIcon(scaledImage));
            } catch (IOException e) {
                JOptionPane.showMessageDialog(this, "Lỗi khi chọn hình ảnh: " + e.getMessage(),
                        "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void addCard() {
        try {
            int confirm = JOptionPane.showConfirmDialog(
                this,
                "Bạn có chắc chắn muốn thêm dịch vụ thẻ cào này không?",
                "Xác nhận",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE
            );

            if (confirm != JOptionPane.YES_OPTION) {
                return;
            }

            String loaiThe = txtCardType.getText().trim();
            if (loaiThe.equals("Nhập loại thẻ")) {
                loaiThe = "";
            }

            String valueText = txtCardValue.getText().trim();
            if (valueText.equals("Nhập mệnh giá")) {
                valueText = "";
            }

            if (loaiThe.isEmpty() || valueText.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin!",
                        "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }

            int menhGia = Integer.parseInt(valueText);

            if (menhGia <= 0) {
                JOptionPane.showMessageDialog(this, "Mệnh giá phải lớn hơn 0!",
                        "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }

            controller.themTheCao(loaiThe, menhGia);

            // Save image if one was selected
            if (picCard.getIcon() != null && cardImage != null) {
                saveImageToFolder(cardImage, loaiThe + ".png");
            }

            JOptionPane.showMessageDialog(this, "Thêm dịch vụ thẻ cào thành công");
            cancelCardEdit();
            loadCardData();
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Mệnh giá phải là số!",
                    "Lỗi", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage(),
                    "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void updateCard() {
        try {
            int selectedRow = cardTable.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn một thẻ cào để sửa!",
                        "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }

            int confirm = JOptionPane.showConfirmDialog(
                this,
                "Bạn có chắc chắn muốn sửa thông tin dịch vụ thẻ cào này không?",
                "Xác nhận",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE
            );

            if (confirm != JOptionPane.YES_OPTION) {
                return;
            }

            String maDV = cardModel.getValueAt(selectedRow, 0).toString();
            String loaiThe = txtCardType.getText().trim();
            if (loaiThe.equals("Nhập loại thẻ")) {
                loaiThe = "";
            }

            String valueText = txtCardValue.getText().trim();
            if (valueText.equals("Nhập mệnh giá")) {
                valueText = "";
            }

            if (loaiThe.isEmpty() || valueText.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin!",
                        "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }

            int menhGia = Integer.parseInt(valueText);

            if (menhGia <= 0) {
                JOptionPane.showMessageDialog(this, "Mệnh giá phải lớn hơn 0!",
                        "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }

            controller.suaTheCao(maDV, loaiThe, menhGia);

            // Save image if one was selected
            if (picCard.getIcon() != null && cardImage != null) {
                saveImageToFolder(cardImage, loaiThe + ".png");
            }

            JOptionPane.showMessageDialog(this, "Sửa dịch vụ thẻ cào thành công");
            loadCardData();

            // Reselect the row
            for (int i = 0; i < cardTable.getRowCount(); i++) {
                if (cardTable.getValueAt(i, 0).equals(maDV)) {
                    cardTable.setRowSelectionInterval(i, i);
                    break;
                }
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Mệnh giá phải là số!",
                    "Lỗi", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage(),
                    "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void deleteCard() {
        try {
            int selectedRow = cardTable.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn một thẻ cào để xóa!",
                        "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }

            int confirm = JOptionPane.showConfirmDialog(
                this,
                "Bạn có chắc chắn muốn xóa dịch vụ thẻ cào này không?",
                "Xác nhận",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE
            );

            if (confirm != JOptionPane.YES_OPTION) {
                return;
            }

            String maDV = cardModel.getValueAt(selectedRow, 0).toString();
            String loaiThe = cardModel.getValueAt(selectedRow, 1).toString();

            controller.xoaDichVu(maDV);

            // Delete image if exists
            String imgName = loaiThe + ".png";
            File imgFile = new File("Images/" + imgName);
            if (imgFile.exists()) {
                imgFile.delete();
            }

            JOptionPane.showMessageDialog(this, "Xóa dịch vụ thẻ cào thành công");
            loadCardData();
            cancelCardEdit();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage(),
                    "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void cancelCardEdit() {
        // Reset text fields with placeholders
        txtCardType.setText("Nhập loại thẻ");
        txtCardType.setForeground(PLACEHOLDER_COLOR);

        txtCardValue.setText("Nhập mệnh giá");
        txtCardValue.setForeground(PLACEHOLDER_COLOR);

        picCard.setIcon(null);
        cardTable.clearSelection();

        // Show/hide appropriate buttons
        btnAddCard.setVisible(true);
        btnEditCard.setVisible(false);
        btnDeleteCard.setVisible(false);
        btnCancelCard.setVisible(false);
    }

    private void searchFood() {
        loadFoodData();
    }

    // Main method
    public static void main(String[] args) {
        try {
            // Set system look and feel
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

            // Custom UI adjustments
            UIManager.put("Button.focus", new Color(0, 0, 0, 0));
            UIManager.put("CheckBox.focus", new Color(0, 0, 0, 0));
            UIManager.put("ComboBox.focus", new Color(0, 0, 0, 0));
            UIManager.put("Table.selectionBackground", new Color(210, 210, 255));
            UIManager.put("Table.selectionForeground", Color.BLACK);
        } catch (Exception e) {
            e.printStackTrace();
        }

        SwingUtilities.invokeLater(() -> {
            DichVuForm form = new DichVuForm();
            form.setVisible(true);
        });
    }
}
