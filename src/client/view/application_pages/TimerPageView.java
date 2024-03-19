package client.view.application_pages;

import utilities.Resources;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;



/**
 * The TimerPageView displays the current booking of the user.
 */
public class TimerPageView extends JPanel {

    /**
     * The current time of the timer.
     */
    double current;

    /**
     * The panel that holds different components.
     */
    private JPanel container;
    /**
     * The panel for the timer.
     */
    private final TimerPanel pnlTimer;
    /**
     * The panel for the timer graphics (stopwatch and arc).
     */
    private TimerGraphics pnlTimerGraphics;
    /**
     * The panel for information.
     */
    private final TicketInfoPanel pnlTicketInfo;
    /**
     * The dialog for dlgEndTimer.
     */
    private EndTimerDialog dlgEndTimer;
    /**
     * The stylesheet.
     */
    private final Resources res = new Resources();

    /**
     * Constructs a panel of TimerViewPatch.
     */
    public TimerPageView() {
        setLayout(new BorderLayout());
        setBorder(new EmptyBorder(20, 20, 20, 20));
        setBackground(res.lightGray);

        dlgEndTimer = new EndTimerDialog();
        //dlgEndTimer.setVisible(false);

        container = res.createPnlRounded(1100, 700, res.white, res.white);
        container.setPreferredSize(new Dimension(1100, 700));
        container.setBorder(new EmptyBorder(20, 20, 20, 20));
        container.setLayout(new BorderLayout());
        add(container, BorderLayout.CENTER);

        JLabel lblTitle = res.createLblH1("Current Ticket", res.eerieBlack);
        lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
        container.add(lblTitle, BorderLayout.NORTH);

        pnlTimer = new TimerPanel();
        container.add(pnlTimer, BorderLayout.WEST);

        pnlTicketInfo = new TicketInfoPanel();
        container.add(pnlTicketInfo, BorderLayout.EAST);

        this.setPreferredSize(new Dimension(1100, 700));
    }

    public void setCurrent(double i) {
        this.current = i;
    }

    /**
     * The panel to hold the timer.
     */
    public class TimerPanel extends JPanel {


        /**
         * Constructs a panel of TimerPanel.
         */
        public TimerPanel() {
            setLayout(new BorderLayout());
            setBorder(new EmptyBorder(20, 20, 20, 20));
            setBackground(res.white);

            pnlTimerGraphics = new TimerGraphics();
            add(pnlTimerGraphics, BorderLayout.CENTER);

            JPanel pnlButtons = new JPanel(new FlowLayout());
            pnlButtons.setBackground(res.white);
            add(pnlButtons, BorderLayout.SOUTH);


            this.setPreferredSize(new Dimension(550, 650));
        }

    }

    /**
     * The panel of TimerGraphics.
     */
    public class TimerGraphics extends JPanel {
        /**
         * The timer to delay UI components.
         */
        private Timer swingTimer;

        /**
         * The hour of the timer object.
         */
        private int hour;
        /**
         * The minute of the timer object.
         */
        private int minute;
        /**
         * The second of the timer object.
         */
        private int second;
        /**
         * The radius of the timer, representing a countdown.
         */
        private static final int CIRCLE_RADIUS = 152;
        /**
         * The starting angle of the countdown timer.
         */
        private static final int ARC_START_ANGLE = 90;
        /**
         * The extent of the arc.
         */
        private int arcExtent;
        /**
         * The components inside the timer.
         */
        double init;

        /**
         * Constructs a panel of TimerGraphics.
         */
        public TimerGraphics() {
            setBackground(res.white);

            arcExtent = 360; // arc of the timer
            hour = 1; // starting hour by default
            minute = 0; // default minute
            second = 0; // default second
            init = hour * 3600 + minute * 60 + second; // instantiation of the timer.
            current = init; // instantiation of the current timer

            swingTimer = new Timer(1000, new ActionListener() {
                /**
                 * Resets the timer every second.
                 * @param e the event to be processed
                 */
                @Override
                public void actionPerformed(ActionEvent e) {
                    updateTime();
                    repaint();
                }
            });

            this.setPreferredSize(new Dimension(150,100));
        }

        /**
         * Retrieves the current swing timer.
         * @return The current swing timer.
         */
        public Timer getSwingTimer() {
            return swingTimer;
        }

        /**
         * Retrieves the current hour of the timer.
         * @return The current hour.
         */
        public int getHour() {
            return hour;
        }

        /**
         * Mutates the new hour of the timer.
         * @param hour The new hour (integer).
         */
        public void setHour(int hour) {
            this.hour = hour;
        }

        /**
         * Mutates the new minute of the timer.
         * @param minute The new minute (integer).
         */
        public void setMinute(int minute) {
            this.minute = minute;
        }

        /**
         * Mutates the new second of the timer.
         * @param second The new second (integer).
         */
        public void setSecond(int second) {
            this.second = second;
        }

        /**
         * Updates the time left.
         */
        private void updateTime() {
            if (hour == 0 && minute == 0 && second == 0) {
                swingTimer.stop();
            } else if (minute == 0 && second == 0) {
                hour--;
                minute = 59;
                second = 59;
            } else if (second == 0) {
                minute--;
                second = 59;
            } else {
                second--;
            }
        }

        /**
         * Paints the stopwatch.
         *
         * @param g the <code>Graphics</code> context in which to paint
         */
        public void paint(Graphics g) {
            super.paint(g);
            drawArc(g);
            drawStopwatch(g);
        }

        /**
         * Draws the arc of the circle.
         *
         * @param g The specified graphics component.
         */
        private void drawArc(Graphics g) {
            if (arcExtent >= 0) {
                g.setColor(Color.GREEN);
                g.fillArc(100, 100, 2 * CIRCLE_RADIUS, 2 * CIRCLE_RADIUS, ARC_START_ANGLE, arcExtent);
                g.setColor(res.white);
                g.fillArc(112, 112, 280, 280, 0, 360);
            }
            current--;
            arcExtent = (int) ((current / init) * 360.0);
        }

        /**
         * Draws the stopwatch labels inside the timer.
         *
         * @param g The specified graphics components.
         */
        private void drawStopwatch(Graphics g) {
            g.setColor(res.eerieBlack);
            g.setFont(new Font("Arial", Font.PLAIN, 24));
            String timeString = String.format("%02d:%02d:%02d", hour, minute, second);

            FontMetrics fm = g.getFontMetrics();
            int x = 210;
            int y = (getHeight() - fm.getHeight()) / 2 + fm.getAscent();
            g.drawString(timeString, x, y);
        }
    }

    /**
     * The panel to hold the ticket information of the booking.
     */
    public class TicketInfoPanel extends JPanel {


        /**
         * The label of the parking type.
         */
        private JLabel lblParkingType;
        /**
         * The label of the parking spot.
         */
        private JLabel lblParkingSpot;
        /**
         * The label of booked parking.
         */
        private JLabel lblDate;
        /**
         * The label of the parking duration in hours.
         */
        private JLabel lblDuration;
        /**
         * The label for the time-in and time-out of the booking.
         */
        private JLabel lblTime;

        /**
         * Constructs a panel of TicketInfoPanel.
         */
        public TicketInfoPanel() {
            setLayout(new GridBagLayout());
            setBackground(res.white);

            GridBagConstraints gbc = new GridBagConstraints();
            gbc.insets = new Insets(10, 10, 10, 10);

            gbc.gridwidth = 1;
            gbc.ipadx = 50;
            gbc.fill = GridBagConstraints.BOTH;
            gbc.anchor = GridBagConstraints.WEST;

            // left column
            gbc.gridx = 0;
            gbc.gridy = 0;
            JLabel lblParkAreaLabel = res.createLblP("Parking Area:", res.eerieBlack);
            add(lblParkAreaLabel, gbc);

            gbc.gridy = 1;
            JLabel lblParkingTypeLabel = res.createLblP("Parking Type:", res.eerieBlack);
            add(lblParkingTypeLabel, gbc);

            gbc.gridy = 2;
            JLabel lblParkingSpotLabel = res.createLblP("Parking Spot:", res.eerieBlack);
            add(lblParkingSpotLabel, gbc);

            gbc.gridy = 3;
            JLabel lblDateLabel = res.createLblP("Date:", res.eerieBlack);
            add(lblDateLabel, gbc);

            gbc.gridy = 4;
            JLabel lblDurationLabel = res.createLblP("Duration:", res.eerieBlack);
            add(lblDurationLabel, gbc);

            gbc.gridy = 5;
            JLabel lblTimeLabel = res.createLblP("Time:", res.eerieBlack);
            add(lblTimeLabel, gbc);

            // right column
            gbc.gridwidth = 1;

            gbc.gridx = 1;
            gbc.gridy = 0;
            JLabel lblParkingArea = res.createLblP("SLU Maryheights Campus", res.eerieBlack);
            add(lblParkingArea, gbc);

            gbc.gridy = 1;
            lblParkingType = res.createLblP("Info", res.eerieBlack);
            add(lblParkingType, gbc);

            gbc.gridy = 2;
            lblParkingSpot = res.createLblP("Info", res.eerieBlack);
            add(lblParkingSpot, gbc);

            gbc.gridy = 3;
            lblDate = res.createLblP("Info", res.eerieBlack);
            add(lblDate, gbc);

            gbc.gridy = 4;
            lblDuration = res.createLblP("Info", res.eerieBlack);
            add(lblDuration, gbc);

            gbc.gridy = 5;
            lblTime = res.createLblP("Info", res.eerieBlack);
            add(lblTime, gbc);

            this.setPreferredSize(new Dimension(550, 650));
        }


        /**
         * Retrieves the current JLabel of lblParkingType.
         *
         * @return The current lblParkingType.
         */
        public JLabel getLblParkingType() {
            return lblParkingType;
        }

        public void setLblParkingType(String label) {
            lblParkingType.setText(label);
        }

        /**
         * Retrieves the current JLabel of lblParkingSpot.
         *
         * @return The current lblParkingSpot.
         */
        public JLabel getLblParkingSpot() {
            return lblParkingSpot;
        }

        public void setLblParkingSpot(String label) {
            lblParkingSpot.setText(label);
        }

        /**
         * Retrieves the current JLabel of lblDate.
         *
         * @return The current lblDate.
         */
        public JLabel getLblDate() {
            return lblDate;
        }

        public void setLblParkingDate(String label) {
            lblDate.setText(label);
        }

        /**
         * Retrieves the current JLabel of lblDuration.
         *
         * @return The current lblDuration.
         */
        public JLabel getLblDuration() {
            return lblDuration;
        }

        public void setLblParkingDuration(String label) {
            lblDuration.setText(label);
        }

        /**
         * Retrieves the current JLabel of lblTime.
         *
         * @return The current lblTime.
         */
        public JLabel getLblTime() {
            return lblTime;
        }

        public void setLblParkingTime(String label) {
            lblTime.setText(label);
        }
    }

    /**
     * Displays a warning message when the btnEndTimer is clicked.
     */
    public class EndTimerDialog extends JDialog {
        /**
         * The button to confirm.
         */
        private JButton btnConfirm;
        /**
         * The button to cancel.
         */
        private JButton btnCancel;

        /**
         * Constructs a dialog of EndTimerDialog.
         */
        public EndTimerDialog() {
            setBorder(new EmptyBorder(20,20,20,20));

            Container contentArea = new JPanel(new BorderLayout());
            contentArea.setBackground(res.white);

            JLabel lblTitle = res.createLblH1("Ticket Cancellation", res.eerieBlack);
            contentArea.add(lblTitle, BorderLayout.NORTH);

            JLabel lblMessage = res.createLblP("<html>Are you sure you want to prematurely end your <br>" +
                    "reservation? Your ticket will be rendered as completed. <br>" +
                    "You cannot undo this action.", res.eerieBlack);
            contentArea.add(lblMessage, BorderLayout.CENTER);

            JPanel pnlButtons = new JPanel(new FlowLayout());
            pnlButtons.setBackground(res.white);
            contentArea.add(pnlButtons, BorderLayout.SOUTH);

            btnConfirm = res.createBtnRounded("Yes", res.red, res.eerieBlack, 10);
            pnlButtons.add(btnConfirm);

            btnCancel = res.createBtnRounded("No", res.lightGray, res.eerieBlack, 10);
            pnlButtons.add(btnCancel);

            this.setContentPane(contentArea);
            this.setModal(true);
            this.pack();
            this.setSize(400, 200);
            this.setResizable(false);
            this.setVisible(false);
        }

        /**
         * Sets a specified action listener to btnConfirm.
         * @param actionListener The specified action listener.
         */
        public void setConfirmListener(ActionListener actionListener) {
            btnConfirm.addActionListener(actionListener);
        }

        /**
         * Retrieves the current JButton of btnConfirm.
         * @return The current btnConfirm.
         */
        public JButton getBtnConfirm() {
            return btnConfirm;
        }

        /**
         * Retrieves the current JButton of btnCancel.
         * @return The current btnCancel.
         */
        public JButton getBtnCancel() {
            return btnCancel;
        }

        /**
         * Sets a specified action listener to btnCancel.
         * @param actionListener The specified btnCancel.
         */
        public void setCancelListener(ActionListener actionListener) {
            btnCancel.addActionListener(actionListener);
        }
    }

    /**
     * Retrieves the current JPanel of pnlTimer.
     *
     * @return The current pnlTimer.
     */
    public TimerPanel getPnlTimer() {
        return pnlTimer;
    }

    /**
     * Retrieves the current JPanel of pnlTicketInfo.
     *
     * @return The current pnlTicketInfo.
     */
    public TicketInfoPanel getPnlTicketInfo() {
        return pnlTicketInfo;
    }

    /**
     * Retrieves the current JPanel of pnlTimerGraphics.
     * @return The current pnlTimerGraphics.
     */
    public TimerGraphics getPnlTimerGraphics() {
        return pnlTimerGraphics;
    }

    /**
     * Retrieves the current JDialog of dlgEndTimer.
     * @return The current dlgEndTimer.
     */
    public EndTimerDialog getDlgEndTimer() {
        return dlgEndTimer;
    }
}
