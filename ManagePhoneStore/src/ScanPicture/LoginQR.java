package ScanPicture;

import Entity.InformationUsers;
import Libraries.Dialog;
import Libraries.Login;
import Libraries.XImage;
import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamDiscoveryService;
import com.github.sarxos.webcam.WebcamPanel;
import com.github.sarxos.webcam.WebcamResolution;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.LuminanceSource;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.NotFoundException;
import com.google.zxing.Result;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;
import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.log4j.BasicConfigurator;

public class LoginQR extends javax.swing.JDialog {

    public static boolean isLoginQR = false;
    private WebcamPanel panel = null;
    private Webcam webcam = null;
    private static final long serialVersionUID = 6441489157408381878L;
    WebcamDiscoveryService discovery = Webcam.getDiscoveryService();

    public LoginQR(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        initWebcam();
        Init();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        result_field = new javax.swing.JTextField();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        this.setTitle("Quét mã QR");
        this.setIconImage(XImage.getIcon("qrcode.png"));
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        result_field.setBorder(null);
        jPanel1.add(result_field, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 340, 470, 20));

        jSeparator1.setForeground(new java.awt.Color(126, 167, 206));
        jPanel1.add(jSeparator1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 360, 470, 10));

        jLabel1.setForeground(new java.awt.Color(105, 105, 105));
        jLabel1.setText("Resultado");
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 320, -1, -1));

        jPanel2.setBackground(new java.awt.Color(250, 250, 250));
        jPanel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(230, 230, 230)));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 470, 300));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 500, 380));
        this.addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                destroy();
                dispose();
            }
        });
        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Windows".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(LoginQR.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                LoginQR dialog = new LoginQR(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        BasicConfigurator.resetConfiguration();
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });

    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTextField result_field;
    // End of variables declaration//GEN-END:variables

    private void initWebcam() {
        BasicConfigurator.configure();
        Dimension size = WebcamResolution.QVGA.getSize();
        webcam = Webcam.getWebcams().get(0); //0 is default webcam
        webcam.setViewSize(size);
        this.setLocationRelativeTo(null);
        panel = new WebcamPanel(webcam);
        panel.setPreferredSize(size);
        panel.setFPSDisplayed(true);
        jPanel2.add(panel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 470, 300));
    }

    public void Init() {
        new Thread() {
            @Override
            public void run() {
                do {
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    Result result = null;
                    BufferedImage image = null;

                    if (webcam.isOpen()) {
                        if ((image = webcam.getImage()) == null) {
                            continue;
                        }
                    }
                    try {
                        LuminanceSource source = new BufferedImageLuminanceSource(image);
                        BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));
                        result = new MultiFormatReader().decode(bitmap);
                    } catch (NotFoundException e) {
                        //No result...
                    } catch (Exception e) {

                    }
                    if (result != null) {
//                result_field.setText(result.getText());
                        int index = result.getText().indexOf("-");
                        InformationUsers u = new InformationUsers();
                        String username = "";
                        String password = "";
                        if (index != -1) {
                            username = result.getText().substring(0, index).trim();
                            password = result.getText().substring(index + 1, result.getText().length()).trim();
                            u.setUserName(username);
                            u.setPassWords(password);
                            u.setRoles(false);
                            if (u.checkLoginUser()) {
                                try {
                                    result_field.setText("Đăng nhập thành công với mã : " + username);
                                    Login.user = u;
//                        isLoginQR = true;
                                    Thread.sleep(3000);
                                    destroy();

                                    dispose();
                                } catch (InterruptedException ex) {
                                    Logger.getLogger(LoginQR.class.getName()).log(Level.SEVERE, null, ex);
                                }
                            } else {
                                try {
                                    result_field.setText("Không có tài khoản ");
                                    Thread.sleep(2000);
//                        isLoginQR = false;
                                    if (Dialog.Confirm(null, "Bạn có muốn thoát hay không")) {
                                        destroy();
                                        dispose();
                                    } else {
                                        result_field.setText("");
                                    }
                                } catch (InterruptedException ex) {
                                    Logger.getLogger(LoginQR.class.getName()).log(Level.SEVERE, null, ex);
                                }
                            }
                        } else {
                            try {
                                result_field.setText("QR Code không đúng định dạng");
                                Thread.sleep(2000);
//                        isLoginQR = false;
                                if (Dialog.Confirm(null, "Bạn có muốn thoát hay không")) {
                                    destroy();
                                    dispose();
                                } else {
                                    result_field.setText("");
                                }
                            } catch (InterruptedException ex) {
                                Logger.getLogger(LoginQR.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                    }
                } while (true);
            }
        }.start();
    }

    public void destroy() {
        BasicConfigurator.resetConfiguration();
        webcam.close();
        discovery.stop();

    }

}
