package client.view.application_pages;

import java.awt.geom.RoundRectangle2D;

/**
 * To be implemented in Version 2.0.
 */
public class TicketCancelDialog extends javax.swing.JDialog {

    /**
     * Creates new form TicketCancelDialog
     */
    public TicketCancelDialog(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        setUndecorated(true);
        initComponents();
        setLocationRelativeTo(null);
        setShape(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), 30, 30));
        setVisible(true);
    }
    private void initComponents() {

        panelRt = new javax.swing.JPanel();
        ticketCancelTitle = new javax.swing.JLabel();
        messageLine1 = new javax.swing.JLabel();
        messageLine2 = new javax.swing.JLabel();
        messageLine3 = new javax.swing.JLabel();
        yesBtn = new javax.swing.JButton();
        noBtn = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        panelRt.setBackground(new java.awt.Color(255, 255, 255));
        panelRt.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        ticketCancelTitle.setFont(new java.awt.Font("Inter", 1, 48));
        ticketCancelTitle.setText("Ticket Cancellation");

        messageLine1.setFont(new java.awt.Font("Inter", 0, 24));
        messageLine1.setText("Are you sure you want to prematurely end your");

        messageLine2.setFont(new java.awt.Font("Inter", 0, 24));
        messageLine2.setText("reservation? Your ticket will be rendered void.");

        messageLine3.setFont(new java.awt.Font("Inter", 0, 24));
        messageLine3.setText("You cannot undo this action.");

        yesBtn.setBackground(new java.awt.Color(230, 92, 92));
        yesBtn.setText("Yes");
        yesBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                yesBtnActionPerformed(evt);
            }
        });
        noBtn.setBackground(new java.awt.Color(204, 204, 204));
        noBtn.setText("No");
        noBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                noBtnActionPerformed(evt);
            }
        });
        javax.swing.GroupLayout panelRtLayout = new javax.swing.GroupLayout(panelRt);
        panelRt.setLayout(panelRtLayout);
        panelRtLayout.setHorizontalGroup(
                panelRtLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelRtLayout.createSequentialGroup()
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(ticketCancelTitle)
                                .addGap(111, 111, 111))
                        .addGroup(panelRtLayout.createSequentialGroup()
                                .addGroup(panelRtLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(panelRtLayout.createSequentialGroup()
                                                .addGap(80, 80, 80)
                                                .addGroup(panelRtLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(messageLine2)
                                                        .addComponent(messageLine1)
                                                        .addComponent(messageLine3)))
                                        .addGroup(panelRtLayout.createSequentialGroup()
                                                .addGap(281, 281, 281)
                                                .addGroup(panelRtLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                        .addComponent(noBtn, javax.swing.GroupLayout.DEFAULT_SIZE, 125, Short.MAX_VALUE)
                                                        .addComponent(yesBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                                .addContainerGap(82, Short.MAX_VALUE))
        );
        panelRtLayout.setVerticalGroup(
                panelRtLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(panelRtLayout.createSequentialGroup()
                                .addGap(20, 20, 20)
                                .addComponent(ticketCancelTitle)
                                .addGap(18, 18, 18)
                                .addComponent(messageLine1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(messageLine2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(messageLine3)
                                .addGap(48, 48, 48)
                                .addComponent(yesBtn)
                                .addGap(18, 18, 18)
                                .addComponent(noBtn)
                                .addContainerGap(18, Short.MAX_VALUE))
        );

        getContentPane().add(panelRt, java.awt.BorderLayout.CENTER);

        pack();
    }
    private void yesBtnActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
    }
    private void noBtnActionPerformed(java.awt.event.ActionEvent evt) {
        dispose();
    }

    public static void main(String args[]) {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(TicketCancelDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(TicketCancelDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(TicketCancelDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TicketCancelDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }


        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                TicketCancelDialog dialog = new TicketCancelDialog(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify
    private javax.swing.JLabel messageLine1;
    private javax.swing.JLabel messageLine2;
    private javax.swing.JLabel messageLine3;
    private javax.swing.JButton noBtn;
    private javax.swing.JPanel panelRt;
    private javax.swing.JLabel ticketCancelTitle;
    private javax.swing.JButton yesBtn;
    // End of variables declaration
}
