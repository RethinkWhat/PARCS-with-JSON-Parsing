package utilities;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.font.ImageGraphicAttribute;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;

/**
 * The stylesheet of the different view classes.
 */
public class Resources {
    /**
     * Main brand color
     */
    public final Color celadon = new Color(128, 207, 168);
    /**
     * Secondary brand color
     */
    public final Color feldgrau = new Color(76, 102, 99);
    /**
     * Solid color
     */
    public final Color eerieBlack = new Color(17, 29, 19);
    /**
     * Solid color
     */
    public final Color white = new Color(255, 255, 255);
    /**
     * Background color for I/O components.
     */
    public final Color lightGray = new Color(239, 239, 239);
    /**
     * Foreground color for I/O components.
     */
    public final Color gray = new Color(133, 133, 133);
    /**
     * Error color.
     */
    public final Color red = new Color(230, 92, 92);
    /**
     * The icon for the PARCS logo.
     */
    public ImageIcon logoParcs = new ImageIcon("res/drawable/parcs-logo.png");
    /**
     * The icon for the home button in the navigation bar.
     */
    public ImageIcon iconHome = new ImageIcon("res/drawable/icons/home-white-outline.png");
    /**
     * The icon for the ticket button in the navigation bar.
     */
    public ImageIcon iconTicket = new ImageIcon("res/drawable/icons/ticket-white-outline.png");
    /**
     * The icon for the account button in the navigation bar.
     */
    public ImageIcon iconAccount = new ImageIcon("res/drawable/icons/user-white-outline.png");
    /**
     * The icon fot the logout button in the navigation bar.
     */
    public ImageIcon iconLogout = new ImageIcon("res/drawable/icons/exit-white-outline.png");
    /**
     * The icon for the available car slots panel.
     */
    public ImageIcon iconSolidCar = new ImageIcon("res/drawable/icons/car-black-outline.png");
    /**
     * The icon for the available motorcycle slots panel.
     */
    public ImageIcon iconSolidMotor = new ImageIcon("res/drawable/icons/motorcycle-black-solid.png");
    /**
     * The colored icon for the available car parking slots.
     */
    public ImageIcon iconAvailableCar = new ImageIcon("res/drawable/icons/car-celadon-solid.png");
    /**
     * The colored icon for the available motorcycle parking slots.
     */
    public ImageIcon iconAvailableMotor = new ImageIcon("res/drawable/icons/motorcycle-celadon-solid.png");
    /**
     * The colored icon for the taken car parking slots.
     */
    public ImageIcon iconTakenCar = new ImageIcon("res/drawable/icons/car-red-solid.png");
    /**
     * The colored icon for the taken motorcycle parking slots.
     */
    public ImageIcon iconTakenMotor = new ImageIcon("res/drawable/icons/motorcycle-red-solid.png");
    /**
     * The icon for the total bookings of the user panel.
     */
    public ImageIcon iconSolidTicket = new ImageIcon("res/drawable/icons/ticket-black-solid.png");
    /**
     * The icon for navigation (forward).
     */
    public ImageIcon iconRightArrow = new ImageIcon("res/drawable/icons/arrow-right.png");
    /**
     * The icon for navigation (back).
     */
    public ImageIcon iconLeftArrow = new ImageIcon("res/drawable/icons/arrow-left.png");
    /**
     * The icon for closing a certain panel.
     */
    public ImageIcon iconClose = new ImageIcon("res/drawable/icons/close-black-solid.png");
    /**
     * The icon for history in the admin page.
     */
    public ImageIcon iconHistory = new ImageIcon("res/drawable/icons/history-white-outline.png");
    /**
     * The icon for switch in the admin page.
     */
    public ImageIcon iconSwitch = new ImageIcon("res/drawable/icons/switch-white-outline.png");
    /**

     * The icon for the successful reservation prompt.
     */
    public ImageIcon iconSuccess = new ImageIcon("res/drawable/icons/check-celadon-solid.png");
    /**
     * The icon for when the server is closed/offline.
     */
    public ImageIcon iconOffline = new ImageIcon("res/drawable/icons/offline-red-outline.png");
    /**
     * The icon for errors.
     */
    public ImageIcon iconError = new ImageIcon("res/drawable/icons/error-red-solid.png");
    /**
     * The icon for edit.
     */
    public ImageIcon iconEdit = new ImageIcon("res/drawable/icons/edit.png");
    /**
     * The icon for refresh.
     */
    public ImageIcon iconRefresh = new ImageIcon("res/drawable/icons/refresh-black-solid.png");
    /**
     * The icon for delete.
     */
    public ImageIcon iconDelete = new ImageIcon("res/drawable/icons/delete-gray-solid.png");
    /**
     * Creates a new JLabel with a specified text and color.
     * The JLabel is a heading (h1).
     *
     * @param text  The specified text.
     * @param color The specified color.
     * @return JLabel with specified text and color in an H1 format.
     */
    public JLabel createLblH1(String text, Color color) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Arial", Font.BOLD, 26));
        label.setForeground(color);
        return label;
    }

    /**
     * Creates a new JLabel with a specified text and color.
     * The JLabel is a heading (h2).
     *
     * @param text  The specified text.
     * @param color The specified color.
     * @return JLabel with specified text and color in an H2 format.
     */
    public JLabel createLblH2(String text, Color color) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Arial", Font.BOLD, 22));
        label.setForeground(color);
        return label;
    }

    /**
     * Creates a new JLabel with a specified text and color.
     * The JLabel is a heading (h3).
     *
     * @param text  The specified text.
     * @param color The specified color.
     * @return JLabel with specified text and color in an H3 format.
     */
    public JLabel createLblH3(String text, Color color) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Arial", Font.BOLD, 18));
        label.setForeground(color);
        return label;
    }

    /**
     * Creates a new JLabel with a specified text and color.
     * The JLabel is a heading (h4).
     *
     * @param text  The specified text.
     * @param color The specified color.
     * @return JLabel with specified text and color in an H3 format.
     */
    public JLabel createLblH4(String text, Color color) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Arial", Font.BOLD, 12));
        label.setForeground(color);
        return label;
    }


    /**
     * Creates a new JLabel with a specified text and color.
     * The JLabel is a paragraph.
     *
     * @param text  The specified text.
     * @param color The specified color.
     * @return JLabel with specified text and color in a paragraph format.
     */
    public JLabel createLblP(String text, Color color) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Arial", Font.PLAIN, 16));
        label.setForeground(color);
        return label;
    }

    /**
     * Creates a new JLabel with a specified text and color.
     * The JLabel is a paragraph.
     *
     * @param text  The specified text.
     * @param color The specified color.
     * @return JLabel with specified text and color in a paragraph format.
     */
    public JLabel createLblP2(String text, Color color) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Arial", Font.PLAIN, 20));
        label.setForeground(color);
        return label;
    }

    /**
     * Creates a new JButton with a specified text and color.
     * The JButton will only contain text with no background color and no icon.
     *
     * @param text  The specified text.
     * @param color The specified foreground color.
     * @return The JButton with specified text and foreground color.
     */
    public JButton createBtnTxtOnly(String text, Color color) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 15));
        button.setForeground(color);
        button.setOpaque(false);
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);
        button.setFocusable(false);
        return button;
    }

    /**
     * Creates a new JButton with a specified icon and size.
     *
     * @param icon   The specified icon URL.
     * @param width  The desired width of the icon.
     * @param height The desired height of the icon.
     * @return The JButton with a specified icon and size.
     */
    public JButton createBtnIconOnly(ImageIcon icon, int width, int height) {
        Image img = icon.getImage();
        Image scaledImg = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);

        JButton button = new JButton();
        button.setIcon(new ImageIcon(scaledImg));
        button.setOpaque(false);
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);
        button.setFocusable(false);
        return button;
    }

    /**
     * Creates a new JButton with a specified text, background color, foreground color, and radius.
     *
     * @param text       The specified text.
     * @param background The specified background.
     * @param foreground The specified foreground.
     * @param radius     The specified radius.
     * @return The specified button.
     */
    public JButton createBtnRounded(String text, Color background, Color foreground, int radius) {
        JButton button = new JButton(text);
        button.setBackground(background);
        button.setForeground(foreground);
        button.setFont(new Font("Arial", Font.PLAIN, 15));
        button.setBorder(new RoundedBorder(radius));
        button.setVerticalTextPosition(SwingConstants.CENTER);
        button.setHorizontalAlignment(SwingConstants.CENTER);
        button.setBorderPainted(true);
        return button;
    }

    /**
     * Creates a new JComboBox with a specified background color, foreground color, and radius.
     *
     * @param background The specified background.
     * @param foreground The specified foreground.
     * @param radius     The specified radius.
     * @return The specified combo box.
     */
    public JComboBox<?> createCmbRounded(Color background, Color foreground, int radius) {
        JComboBox<?> comboBox = new JComboBox<>();
        comboBox.setFont(new Font("Arial", Font.PLAIN, 14));
        comboBox.setBackground(background);
        comboBox.setForeground(foreground);
        comboBox.setLightWeightPopupEnabled(true);
        comboBox.setBorder(new RoundedBorder(radius));
        return comboBox;
    }

    /**
     * Template for RoundedBorder object.
     */
    public static class RoundedBorder implements Border {
        /**
         * The radius of the rounded border.
         */
        private int radius;

        /**
         * Constructs a rounded border with a specified radius.
         *
         * @param radius The specified radius.
         */
        public RoundedBorder(int radius) {
            this.radius = radius;
        }

        /**
         * Retrieves the current insets of the rounded border.
         *
         * @param c the component for which this border insets value applies
         * @return The current insets of the rounded border.
         */
        public Insets getBorderInsets(Component c) {
            return new Insets(this.radius + 2, this.radius + 2, this.radius + 3, this.radius);
        }

        /**
         * Returns the current state of opacity of the rounded border.
         * Returns true if the border is opaque, false if otherwise.
         *
         * @return The current opacity of the rounded border.
         */
        public boolean isBorderOpaque() {
            return true;
        }

        /**
         * Paints the Border.
         *
         * @param c      the component for which this border is being painted
         * @param g      the paint graphics
         * @param x      the x position of the painted border
         * @param y      the y position of the painted border
         * @param width  the width of the painted border
         * @param height the height of the painted border
         */
        public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
            g.drawRoundRect(x, y, width - 1, height - 1, radius, radius);
        }
    }

    /**
     * Creates a JTextField with rounded corners with a specified text, background color, foreground color, and columns.
     *
     * @param text       The specified text.
     * @param background The specified background color.
     * @param foreground The specified foreground color.
     * @param columns    The specified columns.
     * @return The new JTextField with rounded borders.
     */
    public JTextField createTxtRounded(String text, Color background, Color foreground, int columns) {
        JTextField textField = new RoundJTextField(columns);
        textField.setText(text);
        textField.setBackground(background);
        textField.setForeground(foreground);
        textField.setFont(new Font("Arial", Font.PLAIN, 16));
        return textField;
    }

    /**
     * Creates a new JPanel with a specified width, height, foreground and background color.
     * The JPanel to be created will have rounded edges.
     *
     * @param width    The specified width.
     * @param height   The specified height.
     * @param pnlColor The specified foreground color.
     * @param bgColor  The specified background color. This color must be the same with another component's background
     *                 color for the foreground to have its rounded edges.
     * @return The JPanel with rounded edges with specified width, height, foreground and background color.
     */
    public JPanel createPnlRounded(int width, int height, Color pnlColor, Color bgColor) {
        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Dimension arcs = new Dimension(30, 30);
                Graphics2D graphics = (Graphics2D) g;
                graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);


                //Draws the rounded panel with borders.
                graphics.setColor(pnlColor);
                graphics.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, arcs.width, arcs.height);
                graphics.setColor(bgColor);
            }
        };

        panel.setPreferredSize(new Dimension(width, height));
        return panel;
    }

    /**
     * Template for RoundedJTextField object.
     */
    static class RoundJTextField extends JTextField {
        /**
         * The shape of the RoundJTextField.
         */
        private Shape shape;

        /**
         * Constructs a RoundJTextField with a specified size.
         *
         * @param size The specified size.
         */
        public RoundJTextField(int size) {
            super(size);
            setOpaque(false); // As suggested by @AVD in comment.
        }

        /**
         * Paints the background of the RoundJTextField.
         *
         * @param g the <code>Graphics</code> object to protect
         */
        protected void paintComponent(Graphics g) {
            g.setColor(getBackground());
            g.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 15, 15);
            super.paintComponent(g);
        }

        /**
         * Paints the borders of the RoundJTextField.
         *
         * @param g the <code>Graphics</code> context in which to paint
         */
        protected void paintBorder(Graphics g) {
            g.setColor(getForeground());
            g.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 15, 15);
        }

        /**
         * Checks whether the shape is equal to the given bound coordinates.
         *
         * @param x the <i>x</i> coordinate of the point
         * @param y the <i>y</i> coordinate of the point
         * @return true / false
         */
        public boolean contains(int x, int y) {
            if (shape == null || !shape.getBounds().equals(getBounds())) {
                shape = new RoundRectangle2D.Float(0, 0, getWidth() - 1, getHeight() - 1, 15, 15);
            }
            return shape.contains(x, y);
        }
    }

    /**
     * Creates a RoundJPasswordField with a specified background color, foreground color, and columns.
     *
     * @param background The specified background color.
     * @param foreground The specified foreground color.
     * @param columns    The specified columns.
     * @return The new RoundJPasswordField.
     */
    public JPasswordField createPwdRounded(Color background, Color foreground, int columns) {
        JPasswordField passwordField = new RoundJPasswordField(columns);
        passwordField.setBackground(background);
        passwordField.setForeground(foreground);
        passwordField.setFont(new Font("Arial", Font.PLAIN, 16));
        return passwordField;
    }

    /**
     * Template for RoundJPasswordField object.
     */
    static class RoundJPasswordField extends JPasswordField {
        /**
         * The shape of the RoundJPasswordField.
         */
        private Shape shape;

        /**
         * Constructs a RoundJPasswordField with a specified size.
         *
         * @param size The specified size.
         */
        public RoundJPasswordField(int size) {
            super(size);
            setOpaque(false);
        }

        /**
         * Paints the background of the RoundJTextField.
         *
         * @param g the <code>Graphics</code> object to protect
         */
        protected void paintComponent(Graphics g) {
            g.setColor(getBackground());
            g.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 15, 15);
            super.paintComponent(g);
        }

        /**
         * Paints the borders of the RoundJTextField.
         *
         * @param g the <code>Graphics</code> context in which to paint
         */
        protected void paintBorder(Graphics g) {
            g.setColor(getForeground());
            g.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 15, 15);
        }

        /**
         * Checks whether the shape is equal to the given bound coordinates.
         *
         * @param x the <i>x</i> coordinate of the point
         * @param y the <i>y</i> coordinate of the point
         * @return TODO
         */
        public boolean contains(int x, int y) {
            if (shape == null || !shape.getBounds().equals(getBounds())) {
                shape = new RoundRectangle2D.Float(0, 0, getWidth() - 1, getHeight() - 1, 15, 15);
            }
            return shape.contains(x, y);
        }
    }

    /**
     * Changes the Cursor when hovered to a specific UI component.
     */
    public static class CursorChanger extends MouseAdapter {
        /**
         * The specified button.
         */
        private JButton button;

        /**
         * Constructs an object of CursorChanger with a specified JButton.
         *
         * @param button The specified button.
         */
        public CursorChanger(JButton button) {
            this.button = button;
        }

        /**
         * When the mouse hovers inside the vicinity of the UI component.
         *
         * @param e the event to be processed
         */
        @Override
        public void mouseEntered(MouseEvent e) {
            button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        }

        /**
         * When the mouse hovers outside the UI component.
         *
         * @param e the event to be processed
         */
        @Override
        public void mouseExited(MouseEvent e) {
            button.setCursor(Cursor.getDefaultCursor());
        }
    }

    /**
     * Clears the text in a specified JTextField when it is focused, and inserts a specified placeholder
     * text when unfocused.
     */
    public static class TextFieldFocus implements FocusListener {
        /**
         * The specified text field.
         */
        private JTextField textField;
        /**
         * The specified placeholder text.
         */
        private String placeholder;

        /**
         * Constructs an object of TextFieldFocus with a specified text field and placeholder text.
         *
         * @param textField   The specified text field.
         * @param placeholder The specified placeholder text.
         */
        public TextFieldFocus(JTextField textField, String placeholder) {
            this.textField = textField;
            this.placeholder = placeholder;
        }

        /**
         * Processes the event when focused. The text field contents are cleared to accommodate user input.
         *
         * @param e the event to be processed
         */
        @Override
        public void focusGained(FocusEvent e) {
            if (textField.getText().equals(placeholder)) {
                textField.setText("");
            }
        }

        /**
         * Processes the event when unfocused. A placeholder text is inserted in the text field.
         *
         * @param e the event to be processed
         */
        @Override
        public void focusLost(FocusEvent e) {
            if (textField.getText().isEmpty()) {
                textField.setText(placeholder);
            }
        }
    }

    /**
     * Clears the text in a specified JPasswordField when it is focused, and inserts a specified placeholder
     * text when unfocused.
     */
    public static class PasswordFocus implements FocusListener {
        /**
         * The specified password field.
         */
        private JPasswordField passwordField;
        /**
         * The specified placeholder text.
         */
        private String placeholder;

        /**
         * Constructs an object of PasswordFocus with a specified password field, show password text box and
         * placeholder text.
         *
         * @param passwordField The specified password field.
         * @param placeholder   The specified placeholder text.
         */
        public PasswordFocus(JPasswordField passwordField, String placeholder) {
            this.passwordField = passwordField;
            this.placeholder = placeholder;
            this.passwordField.setText(placeholder);
        }

        /**
         * Processes the event when focused. Clears the password field of its placeholder text.
         *
         * @param e the event to be processed
         */
        @Override
        public void focusGained(FocusEvent e) {
            passwordField.setEchoChar('●');
            if (String.valueOf(passwordField.getPassword()).equals(placeholder)) {
                passwordField.setText("");
            }
        }

        /**
         * Processes the event when focused. The checkbox is overridden and displays the password in plain text, and
         * adds a placeholder text.
         *
         * @param e the event to be processed
         */
        @Override
        public void focusLost(FocusEvent e) {
            if (String.valueOf(passwordField.getPassword()).isEmpty()) {
                passwordField.setText(placeholder);
                passwordField.setEchoChar((char) 0);
            } else {
                passwordField.setEchoChar('●');
            }
        }
    }
}
