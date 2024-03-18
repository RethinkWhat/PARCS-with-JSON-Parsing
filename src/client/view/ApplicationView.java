package client.view;

import client.view.application_pages.ReservationPageView;
import client.view.application_pages.TimerPageView;
import client.view.application_pages.UserProfileView;
import utilities.Resources;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 * Template for object ApplicationView.
 * The ApplicationView is the layout that holds all the different pages of the client application:
 * 1. The Reservation Page as the Home Page
 * 2. Ticket Page
 * 3. Account Page
 */
public class ApplicationView extends JFrame {
    /**
     * The panel that holds different component panels.
     */
    private JPanel pnlCards;
    /**
     * The button that expands the navigation bar.
     */
    private JButton btnNavMenu;
    /**
     * The home button in the navigation bar.
     */
    private JButton btnNavHome;
    /**
     * The ticket button in the navigation.
     */
    private JButton btnNavTicket;
    /**
     * The account button in the navigation.
     */
    private JButton btnNavAccount;
    /**
     * The logout button in the navigation.
     */
    private JButton btnNavLogout;
    /**
     * Location of the current user.
     */
    private JLabel lblLocation;
    /**
     * The CardLayout that controls the components of the MainPanel (pnlMain).
     */
    private CardLayout mainCardLayout = new CardLayout();
    /**
     * The stylesheet.
     */
    private Resources res = new Resources();
    /**
     * Panel of reservation page view.
     */
    private ReservationPageView reservationPageView;
    /**
     * Panel of timer view.
     */
    private TimerPageView timerView;
    /**
     * Panel of user profile view.
     */
    private UserProfileView userProfileView;

    /**
     * Constructs a ReservationPage frame.
     */
    public ApplicationView() {
        super("PARCS");

        // body panel acting as a container to hold all UI components
        Container contentArea = new JPanel(new BorderLayout());

        // header
        HeaderPanel pnlHeader = new HeaderPanel();
        contentArea.add(pnlHeader, BorderLayout.NORTH);

        // navigation
        NavbarPanel pnlNavbar = new NavbarPanel();
        contentArea.add(pnlNavbar, BorderLayout.WEST);

        // body
        pnlCards = new JPanel(mainCardLayout);
        pnlCards.setPreferredSize(new Dimension(1100,700));
        contentArea.add(pnlCards, BorderLayout.CENTER);

        // Home Page
        pnlCards.add(reservationPageView = new ReservationPageView(), "home");

        // Ticket Page
        pnlCards.add(timerView = new TimerPageView(), "ticket");

        // Account Page
        pnlCards.add(userProfileView = new UserProfileView(), "account");

        this.setIconImage(res.logoParcs.getImage());
        this.setContentPane(contentArea);
        this.setLocationRelativeTo(null);
        this.pack();
        this.setSize(1300,800);
        this.setResizable(false);
        this.setVisible(true);
    }

    /**
     * The panel that contains useful information and the searchbar.
     */
    class HeaderPanel extends JPanel {
        /**
         * Constructs a panel of HeaderPanel.
         */
        public HeaderPanel() {
            setBackground(res.celadon);
            setLayout(new FlowLayout(FlowLayout.LEFT));

            btnNavMenu = res.createBtnIconOnly(res.logoParcs, 40,30);
            add(btnNavMenu);

            lblLocation = res.createLblH3("Home", res.white);
            add(lblLocation);

            this.setPreferredSize(new Dimension(1300, 50));
        }
    }

    /**
     * The navigation bar contains the buttons for navigation.
     */
    class NavbarPanel extends JPanel {
        /**
         * Constructs a panel of NavbarPanel.
         */
        public NavbarPanel() {
            setBackground(res.feldgrau);

            btnNavHome = res.createBtnIconOnly(res.iconHome,30,30);
            btnNavHome.setHorizontalAlignment(SwingConstants.LEFT);
            add(btnNavHome);

            btnNavTicket = res.createBtnIconOnly(res.iconTicket, 30,30);
            btnNavTicket.setHorizontalAlignment(SwingConstants.LEFT);
            add(btnNavTicket);

            btnNavAccount = res.createBtnIconOnly(res.iconAccount,30,30);
            btnNavAccount.setHorizontalAlignment(SwingConstants.LEFT);
            add(btnNavAccount);

            btnNavLogout = res.createBtnIconOnly(res.iconLogout,30,30);
            btnNavLogout.setHorizontalAlignment(SwingConstants.LEFT);
            add(btnNavLogout);

            this.setPreferredSize(new Dimension(60, 800));
        }
    }

    /**
     * Retrieves the current panel of ReservationPageView
     * @return The current panel of ReservationPageView.
     */
    public ReservationPageView getReservationPageView() {
        return reservationPageView;
    }

    /**
     * Retrieves the current panel of UserProfileView.
     * @return The current panel of UserProfileView.
     */
    public UserProfileView getUserProfileView() {
        return userProfileView;
    }

    /**
     * Retrieves the current panel of TimerView.
     * @return The current panel of TimerView.
     */
    public TimerPageView getTimerView() {
        return timerView;
    }


    /**
     * Sets a specified action listener for btnNavHome.
     * @param actionListener The specified action listener.
     */
    public void setNavHomeListener(ActionListener actionListener) {
        btnNavHome.addActionListener(actionListener);
    }

    /**
     * Sets a specified action listener for btnNavTicket.
     * @param actionListener The specified action listener.
     */
    public void setNavTicketListener(ActionListener actionListener) {
        btnNavTicket.addActionListener(actionListener);
    }

    /**
     * Sets a specified action listener for btnNavAccount.
     * @param actionListener The specified action listener.
     */
    public void setNavAccountListener(ActionListener actionListener) {
        btnNavAccount.addActionListener(actionListener);
    }

    /**
     * Sets a specified action listener for btnNavLogout.
     * @param actionListener The specified action listener.
     */
    public void setNavLogoutListener(ActionListener actionListener) {
        btnNavLogout.addActionListener(actionListener);
    }

    /**
     * Retrieves the current card layout panel containing the different pages.
     * @return The current card layout panel.
     */
    public JPanel getPnlCards() {
        return pnlCards;
    }

    /**
     * Retrieves the current JButton of btnNavMenu in the navigation bar.
     * @return The current btnNavMenu.
     */
    public JButton getBtnNavMenu() {
        return btnNavMenu;
    }

    /**
     * Retrieves the current JButton of btnNavHome in the navigation bar.
     * @return The current btnNavHome.
     */
    public JButton getBtnNavHome() {
        return btnNavHome;
    }

    /**
     * Retrieves the current JButton of btnNavTicket in the navigation bar.
     * @return The current btnNavTicket.
     */
    public JButton getBtnNavTicket() {
        return btnNavTicket;
    }

    /**
     * Retrieves the current JButton of btnNavAccount in the navigation bar.
     * @return The current btnNavAccount.
     */
    public JButton getBtnNavAccount() {
        return btnNavAccount;
    }

    /**
     * Retrieves the current JButton of btnNavLogout in the navigation bar.
     * @return The current btnNavLogout.
     */
    public JButton getBtnNavLogout() {
        return btnNavLogout;
    }

    /**
     * Retrieves the current JLabel of getLblLocation in the HeaderPanel.
     * @return The current getLblLocation.
     */
    public JLabel getLblLocation() {
        return lblLocation;
    }

    /**
     * Retrieves the current card layout panel that contains the different views.
     * @return The current mainCardLayout.
     */
    public CardLayout getMainCardLayout() {
        return mainCardLayout;
    }
}
