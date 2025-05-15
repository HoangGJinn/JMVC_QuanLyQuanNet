package com.example.util;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.FocusListener;
import java.awt.event.MouseListener;
import java.awt.geom.RoundRectangle2D;
import java.awt.geom.Area;

/**
 * Utility class for consistent UI styling across the application
 */
public class UIStyler {
    // Color scheme
    public static final Color PRIMARY_DARK = new Color(30, 34, 53);
    public static final Color SECONDARY_DARK = new Color(40, 44, 68);
    public static final Color HEADER_DARK = new Color(25, 29, 45);
    public static final Color ACCENT_COLOR = new Color(116, 103, 239);
    public static final Color ACCENT_COLOR_HOVER = new Color(126, 113, 249);
    public static final Color ACCENT_COLOR_SECONDARY = new Color(0, 204, 204);
    public static final Color WARNING_COLOR = new Color(255, 152, 0);
    public static final Color DANGER_COLOR = new Color(255, 75, 75);
    public static final Color SUCCESS_COLOR = new Color(46, 204, 113);
    public static final Color TEXT_COLOR = new Color(255, 255, 255);
    public static final Color TEXT_COLOR_SECONDARY = new Color(200, 200, 220);
    public static final Color HOVER_COLOR = new Color(46, 51, 77);
    public static final Color PANEL_BACKGROUND = new Color(245, 246, 250);
    public static final Color STATUS_BAR_COLOR = new Color(60, 64, 88);

    // Fonts
    public static final Font TITLE_FONT = new Font("Arial", Font.BOLD, 24);
    public static final Font SUBTITLE_FONT = new Font("Arial", Font.BOLD, 18);
    public static final Font BUTTON_FONT = new Font("Arial", Font.BOLD, 14);
    public static final Font LABEL_FONT = new Font("Arial", Font.PLAIN, 14);
    public static final Font TABLE_HEADER_FONT = new Font("Arial", Font.BOLD, 14);
    public static final Font TABLE_CELL_FONT = new Font("Arial", Font.PLAIN, 14);
    public static final Font STATUS_FONT = new Font("Arial", Font.PLAIN, 14);

    // Dimensions
    public static final Dimension BUTTON_SIZE = new Dimension(120, 40);
    public static final Dimension LARGE_BUTTON_SIZE = new Dimension(130, 45);
    public static final Dimension TEXT_FIELD_SIZE = new Dimension(200, 40);

    // Borders
    public static final int PANEL_RADIUS = 10;
    public static final int BUTTON_RADIUS = 8;
    public static final int FIELD_RADIUS = 5;

    /**
     * Style a JButton with the primary accent color
     */
    public static void styleButton(JButton button) {
        button.setFont(BUTTON_FONT);
        button.setBackground(ACCENT_COLOR);
        button.setForeground(TEXT_COLOR);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setPreferredSize(BUTTON_SIZE);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Add hover effect
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(ACCENT_COLOR_HOVER);
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(ACCENT_COLOR);
            }
        });
    }

    /**
     * Style a JButton with a warning color (for update operations)
     */
    public static void styleWarningButton(JButton button) {
        styleButton(button);
        button.setBackground(WARNING_COLOR);
        button.setFont(new Font("Arial", Font.BOLD, 16));
        button.setPreferredSize(LARGE_BUTTON_SIZE);

        // Add hover effect
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(255, 172, 20));
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(WARNING_COLOR);
            }
        });
    }

    /**
     * Style a JButton with a danger color (for delete operations)
     */
    public static void styleDangerButton(JButton button) {
        styleButton(button);
        button.setBackground(DANGER_COLOR);

        // Add hover effect
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(255, 95, 95));
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(DANGER_COLOR);
            }
        });
    }

    /**
     * Style a JButton with a success color (for confirm operations)
     */
    public static void styleSuccessButton(JButton button) {
        styleButton(button);
        button.setBackground(SUCCESS_COLOR);

        // Add hover effect
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(66, 224, 133));
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(SUCCESS_COLOR);
            }
        });
    }

    /**
     * Style a JTextField with a modern, beautiful look
     */
    public static void styleTextField(JTextField textField) {
        textField.setFont(LABEL_FONT);
        textField.setPreferredSize(TEXT_FIELD_SIZE);

        // Create a rounded border with subtle shadow effect
        Border roundedBorder = new Border() {
            @Override
            public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                // Draw subtle shadow
                g2.setColor(new Color(0, 0, 0, 20));
                g2.fillRoundRect(x + 1, y + 1, width - 2, height - 1, FIELD_RADIUS * 2, FIELD_RADIUS * 2);

                // Draw border
                g2.setColor(new Color(200, 200, 200));
                g2.drawRoundRect(x, y, width - 1, height - 2, FIELD_RADIUS * 2, FIELD_RADIUS * 2);

                g2.dispose();
            }

            @Override
            public Insets getBorderInsets(Component c) {
                return new Insets(6, 10, 6, 10);
            }

            @Override
            public boolean isBorderOpaque() {
                return false;
            }
        };

        textField.setBorder(roundedBorder);
        textField.setBackground(Color.WHITE);

        // Add focus and hover effects
        textField.addFocusListener(new java.awt.event.FocusAdapter() {
            @Override
            public void focusGained(java.awt.event.FocusEvent evt) {
                textField.setBorder(new Border() {
                    @Override
                    public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
                        Graphics2D g2 = (Graphics2D) g.create();
                        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                        // Draw subtle shadow
                        g2.setColor(new Color(0, 0, 0, 20));
                        g2.fillRoundRect(x + 1, y + 1, width - 2, height - 1, FIELD_RADIUS * 2, FIELD_RADIUS * 2);

                        // Draw border with accent color
                        g2.setColor(ACCENT_COLOR);
                        g2.setStroke(new BasicStroke(1.5f));
                        g2.drawRoundRect(x, y, width - 1, height - 2, FIELD_RADIUS * 2, FIELD_RADIUS * 2);

                        g2.dispose();
                    }

                    @Override
                    public Insets getBorderInsets(Component c) {
                        return new Insets(6, 10, 6, 10);
                    }

                    @Override
                    public boolean isBorderOpaque() {
                        return false;
                    }
                });
            }

            @Override
            public void focusLost(java.awt.event.FocusEvent evt) {
                textField.setBorder(roundedBorder);
            }
        });

        // Add hover effect
        textField.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                if (!textField.hasFocus()) {
                    textField.setBorder(new Border() {
                        @Override
                        public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
                            Graphics2D g2 = (Graphics2D) g.create();
                            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                            // Draw subtle shadow
                            g2.setColor(new Color(0, 0, 0, 20));
                            g2.fillRoundRect(x + 1, y + 1, width - 2, height - 1, FIELD_RADIUS * 2, FIELD_RADIUS * 2);

                            // Draw border with darker color
                            g2.setColor(new Color(150, 150, 150));
                            g2.drawRoundRect(x, y, width - 1, height - 2, FIELD_RADIUS * 2, FIELD_RADIUS * 2);

                            g2.dispose();
                        }

                        @Override
                        public Insets getBorderInsets(Component c) {
                            return new Insets(6, 10, 6, 10);
                        }

                        @Override
                        public boolean isBorderOpaque() {
                            return false;
                        }
                    });
                }
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                if (!textField.hasFocus()) {
                    textField.setBorder(roundedBorder);
                }
            }
        });
    }

    /**
     * Style a JComboBox with a modern, beautiful look
     */
    public static void styleComboBox(JComboBox<?> comboBox) {
        comboBox.setFont(LABEL_FONT);
        comboBox.setPreferredSize(TEXT_FIELD_SIZE);

        // Create a rounded border with subtle shadow effect
        Border roundedBorder = new Border() {
            @Override
            public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                // Draw subtle shadow
                g2.setColor(new Color(0, 0, 0, 20));
                g2.fillRoundRect(x + 1, y + 1, width - 2, height - 1, FIELD_RADIUS * 2, FIELD_RADIUS * 2);

                // Draw border
                g2.setColor(new Color(200, 200, 200));
                g2.drawRoundRect(x, y, width - 1, height - 2, FIELD_RADIUS * 2, FIELD_RADIUS * 2);

                g2.dispose();
            }

            @Override
            public Insets getBorderInsets(Component c) {
                return new Insets(6, 10, 6, 10);
            }

            @Override
            public boolean isBorderOpaque() {
                return false;
            }
        };

        comboBox.setBorder(roundedBorder);
        comboBox.setBackground(Color.WHITE);

        // Custom renderer for items with hover effect
        comboBox.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, 
                                                         boolean isSelected, boolean cellHasFocus) {
                JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);

                // Add padding
                label.setBorder(BorderFactory.createEmptyBorder(8, 12, 8, 12));

                // Set colors based on selection state
                if (isSelected) {
                    label.setBackground(ACCENT_COLOR);
                    label.setForeground(TEXT_COLOR);
                } else {
                    label.setBackground(Color.WHITE);
                    label.setForeground(Color.DARK_GRAY);
                }

                return label;
            }
        });

        // Style the arrow button
        for (Component comp : comboBox.getComponents()) {
            if (comp instanceof AbstractButton) {
                ((AbstractButton) comp).setBorderPainted(false);
                ((AbstractButton) comp).setBackground(Color.WHITE);
                ((AbstractButton) comp).setFocusPainted(false);
            }
        }

        // Add hover effect to the combobox
        comboBox.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                comboBox.setBorder(BorderFactory.createCompoundBorder(
                    roundedBorder,
                    BorderFactory.createEmptyBorder(0, 0, 0, 0)
                ));
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                comboBox.setBorder(roundedBorder);
            }
        });
    }

    /**
     * Style a JTable with consistent look
     */
    public static void styleTable(JTable table) {
        table.setRowHeight(30);
        table.setFont(TABLE_CELL_FONT);
        table.setSelectionBackground(new Color(232, 232, 232));
        table.setGridColor(new Color(230, 230, 230));
        table.setShowGrid(true);
        table.setIntercellSpacing(new Dimension(5, 5));

        // Style the table header
        JTableHeader header = table.getTableHeader();
        header.setFont(TABLE_HEADER_FONT);
        header.setBackground(ACCENT_COLOR_SECONDARY);
        header.setForeground(TEXT_COLOR);
        header.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, ACCENT_COLOR_SECONDARY));
    }

    /**
     * Style a JScrollPane containing a table
     */
    public static void styleScrollPane(JScrollPane scrollPane) {
        scrollPane.getViewport().setBackground(Color.WHITE);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
    }

    /**
     * Style a JPanel with rounded corners
     */
    public static JPanel createRoundedPanel(int radius) {
        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(getBackground());
                g2.fill(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), radius, radius));
                g2.dispose();
            }
        };
        panel.setOpaque(false);
        return panel;
    }

    /**
     * Style a JPanel as a card with rounded corners and shadow effect
     */
    public static JPanel createCardPanel(int radius) {
        JPanel panel = createRoundedPanel(radius);
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        return panel;
    }

    /**
     * Style a JLabel as a title
     */
    public static void styleTitleLabel(JLabel label) {
        label.setFont(TITLE_FONT);
        label.setForeground(TEXT_COLOR);
    }

    /**
     * Style a JLabel as a subtitle
     */
    public static void styleSubtitleLabel(JLabel label) {
        label.setFont(SUBTITLE_FONT);
        label.setForeground(TEXT_COLOR);
    }

    /**
     * Style a JLabel as a regular label
     */
    public static void styleLabel(JLabel label) {
        label.setFont(LABEL_FONT);
        label.setForeground(TEXT_COLOR);
    }

    /**
     * Style a status bar
     */
    public static void styleStatusBar(JLabel statusBar) {
        statusBar.setFont(STATUS_FONT);
        statusBar.setForeground(TEXT_COLOR);
        statusBar.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
    }

    /**
     * Apply placeholder text to a text field with animation
     */
    public static void setPlaceholder(JTextField textField, String placeholder) {
        textField.setText(placeholder);
        textField.setForeground(new Color(180, 180, 180));

        // Create a standard rounded border that will be used by both listeners
        Border standardBorder = new Border() {
            @Override
            public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                // Draw subtle shadow
                g2.setColor(new Color(0, 0, 0, 20));
                g2.fillRoundRect(x + 1, y + 1, width - 2, height - 1, FIELD_RADIUS * 2, FIELD_RADIUS * 2);

                // Draw border
                g2.setColor(new Color(200, 200, 200));
                g2.drawRoundRect(x, y, width - 1, height - 2, FIELD_RADIUS * 2, FIELD_RADIUS * 2);

                g2.dispose();
            }

            @Override
            public Insets getBorderInsets(Component c) {
                return new Insets(6, 10, 6, 10);
            }

            @Override
            public boolean isBorderOpaque() {
                return false;
            }
        };

        // Create a focus border
        Border focusBorder = new Border() {
            @Override
            public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                // Draw subtle shadow
                g2.setColor(new Color(0, 0, 0, 20));
                g2.fillRoundRect(x + 1, y + 1, width - 2, height - 1, FIELD_RADIUS * 2, FIELD_RADIUS * 2);

                // Draw border with accent color
                g2.setColor(ACCENT_COLOR);
                g2.setStroke(new BasicStroke(1.5f));
                g2.drawRoundRect(x, y, width - 1, height - 2, FIELD_RADIUS * 2, FIELD_RADIUS * 2);

                g2.dispose();
            }

            @Override
            public Insets getBorderInsets(Component c) {
                return new Insets(6, 10, 6, 10);
            }

            @Override
            public boolean isBorderOpaque() {
                return false;
            }
        };

        // Create a hover border
        Border hoverBorder = new Border() {
            @Override
            public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                // Draw subtle shadow
                g2.setColor(new Color(0, 0, 0, 20));
                g2.fillRoundRect(x + 1, y + 1, width - 2, height - 1, FIELD_RADIUS * 2, FIELD_RADIUS * 2);

                // Draw border with darker color
                g2.setColor(new Color(150, 150, 150));
                g2.drawRoundRect(x, y, width - 1, height - 2, FIELD_RADIUS * 2, FIELD_RADIUS * 2);

                g2.dispose();
            }

            @Override
            public Insets getBorderInsets(Component c) {
                return new Insets(6, 10, 6, 10);
            }

            @Override
            public boolean isBorderOpaque() {
                return false;
            }
        };

        // Apply the standard border initially
        textField.setBorder(standardBorder);

        // Remove existing focus listeners to avoid conflicts
        for (FocusListener listener : textField.getFocusListeners()) {
            if (listener.getClass().getName().contains("UIStyler")) {
                textField.removeFocusListener(listener);
            }
        }

        // Add new focus listener that handles both placeholder and styling
        textField.addFocusListener(new java.awt.event.FocusAdapter() {
            @Override
            public void focusGained(java.awt.event.FocusEvent evt) {
                // Handle placeholder text
                if (textField.getText().equals(placeholder)) {
                    textField.setText("");
                    textField.setForeground(Color.BLACK);
                }

                // Apply focus styling
                textField.setBorder(focusBorder);
            }

            @Override
            public void focusLost(java.awt.event.FocusEvent evt) {
                // Handle placeholder text
                if (textField.getText().isEmpty()) {
                    textField.setForeground(new Color(180, 180, 180));
                    textField.setText(placeholder);
                }

                // Apply standard styling
                textField.setBorder(standardBorder);
            }
        });

        // Remove existing mouse listeners to avoid conflicts
        for (MouseListener listener : textField.getMouseListeners()) {
            if (listener.getClass().getName().contains("UIStyler")) {
                textField.removeMouseListener(listener);
            }
        }

        // Add hover effect
        textField.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                if (!textField.hasFocus()) {
                    textField.setBorder(hoverBorder);
                }
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                if (!textField.hasFocus()) {
                    textField.setBorder(standardBorder);
                }
            }
        });
    }

    /**
     * Apply global UI settings
     */
    public static void applyGlobalStyles() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            UIManager.put("Button.background", ACCENT_COLOR);
            UIManager.put("Button.foreground", TEXT_COLOR);
            UIManager.put("Button.focus", ACCENT_COLOR);
            UIManager.put("Panel.background", SECONDARY_DARK);
            UIManager.put("OptionPane.background", new Color(240, 240, 240));
            UIManager.put("OptionPane.messageFont", LABEL_FONT);
            UIManager.put("OptionPane.buttonFont", BUTTON_FONT);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
