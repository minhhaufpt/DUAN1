/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package TestCode;

import DAO.InformationCustomerDAO;
import Entity.InformationCustomer;
import Libraries.Dialog;
import java.util.List;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author PC
 */
public class ManageCustomer extends javax.swing.JDialog {

    /**
     * Creates new form ManageCustomer
     */
    InformationCustomerDAO dao = new InformationCustomerDAO();
    public int row;

    public ManageCustomer(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        init();
    }

    public void init() {
        setLocationRelativeTo(null);
        this.row = -1;
        this.fillTable();
        this.updateStatus();
    }

    boolean checkValidate() {
        if (txtCodeCustomer.getText().equals("")) {
            txtCodeCustomer.requestFocus();
            Dialog.Message(this, "Please enter full information");
            return false;
        } else if (txtNameCustomer.getText().equals("")) {
            txtNameCustomer.requestFocus();
            Dialog.Message(this, "Please enter full information");
            return false;
        } else if (txtPhoneNumber.getText().equals("")) {
            txtPhoneNumber.requestFocus();
            Dialog.Message(this, "Please enter full information");
            return false;
        } else if (txtEmail.getText().equals("")) {
            txtEmail.requestFocus();
            Dialog.Message(this, "Please enter full information");
            return false;
        } else if (txtAddress.getText().equals("")) {
            txtAddress.requestFocus();
            Dialog.Message(this, "Please enter full information");
            return false;
        }
        return true;
    }
    //Cái này xài list hay sao ko biet de day nhe :))

    void fillTable() {
        DefaultTableModel model = (DefaultTableModel) tblnformation.getModel();
        model.setRowCount(0);
        try {
            List<InformationCustomer> lst = dao.SelectAll();
            for (InformationCustomer cd : lst) {
                Object[] row = {cd.getCodeCustomer(), cd.getNameCustomer(), cd.getPhoneNumber(), cd.getEmail(), cd.getAddress(), cd.getNote()};
                model.addRow(row);
            }
            tblnformation.setDefaultEditor(Object.class, null);
        } catch (Exception e) {
            Dialog.Message(this, "Không thể truy vấn dữ liệu !");
        }
    }

    void showDetail() {
        if (row != -1) {
            String code = String.valueOf(tblnformation.getValueAt(row, 0));
            InformationCustomer info = dao.SelectID(code);
            this.setForm(info);
            txtCodeCustomer.setEnabled(false);
        }
    }

    void setForm(InformationCustomer cd) {
        txtAddress.setText(cd.getAddress());
        txtEmail.setText(cd.getEmail());
        txtNameCustomer.setText(cd.getNameCustomer());
        txtPhoneNumber.setText(cd.getPhoneNumber());
        txtCodeCustomer.setText(String.valueOf(cd.getCodeCustomer()));
        txtNote.setText(cd.getNote());
    }

    void clearForm() {
        txtCodeCustomer.setText("");
        txtNameCustomer.setText("");
        txtPhoneNumber.setText("");
        txtEmail.setText("");
        txtAddress.setText("");
        txtNote.setText("");
        txtCodeCustomer.setEnabled(true);
        this.fillTable();
        this.row = -1;
        this.updateStatus();

    }

    InformationCustomer getForm() {
        InformationCustomer entity = new InformationCustomer();
        entity.setCodeCustomer(Integer.parseInt(txtCodeCustomer.getText().trim()));
        entity.setNameCustomer(txtNameCustomer.getText());
        entity.setPhoneNumber(txtPhoneNumber.getText());
        entity.setEmail(txtEmail.getText());
        entity.setAddress(txtAddress.getText());
        entity.setNote(txtNote.getText());
        return entity;
    }

    void Insert() {
        if (checkValidate()) {
            InformationCustomer entity = getForm();
            try {
                dao.Insert(entity);
                this.fillTable();
                this.clearForm();
                Dialog.Message(this, "Successfully added new!");
            } catch (Exception ex) {
                Dialog.Message(this, "New add failed!!!");
            }
        }
    }

    void Delete() {
        String CodeCustomer = txtCodeCustomer.getText();
        if (Dialog.Confirm(this, "Do you want to delete this customer?")) {
            try {
                dao.Detele(CodeCustomer);
                this.fillTable();
                this.clearForm();
                Dialog.Message(this, "Delete successfully");
            } catch (Exception e) {
                Dialog.Message(this, "Error occurred, delete failed");
            }
        }
    }

    void Update() {
        if (checkValidate()) {
            InformationCustomer entity = getForm();
            try {
                dao.Update(entity);
                this.fillTable();
                Dialog.Message(this, "Update succefully!");
            } catch (Exception e) {
                Dialog.Message(this, "Update failed!");
            }
        }
    }

    void updateStatus() {
        boolean showDetail = (this.row >= 0);
        boolean leftend = (this.row == 0);
        boolean rightend = (this.row == tblnformation.getRowCount() - 1);
        //trang thai from
        btnInsert.setEnabled(!showDetail);
        btnUpdate.setEnabled(showDetail);
        btnDelete.setEnabled(showDetail);
        //nut chuyen tiep
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        PanelNen = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        FindCustomer = new javax.swing.JTextField();
        btnSearch = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtNote = new javax.swing.JTextArea();
        txtAddress = new javax.swing.JTextField();
        txtEmail = new javax.swing.JTextField();
        txtCodeCustomer = new javax.swing.JTextField();
        txtNameCustomer = new javax.swing.JTextField();
        txtPhoneNumber = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblnformation = new javax.swing.JTable();
        btnHistoryOrder = new javax.swing.JButton();
        btnInsert = new javax.swing.JButton();
        btnDelete = new javax.swing.JButton();
        btnUpdate = new javax.swing.JButton();
        btnReset = new javax.swing.JButton();
        btnCancel = new javax.swing.JButton();
        jLabel8 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        PanelNen.setBackground(new java.awt.Color(204, 204, 255));

        jLabel1.setFont(new java.awt.Font("Arial Narrow", 0, 18)); // NOI18N
        jLabel1.setText("Code Customer:");

        jLabel2.setFont(new java.awt.Font("Arial Narrow", 0, 18)); // NOI18N
        jLabel2.setText("Name Customer:");

        jLabel3.setFont(new java.awt.Font("Arial Narrow", 0, 18)); // NOI18N
        jLabel3.setText("Phone Number:");

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel4.setText("Email:");

        jLabel5.setFont(new java.awt.Font("Arial Narrow", 0, 18)); // NOI18N
        jLabel5.setText("Address:");

        jLabel6.setFont(new java.awt.Font("Arial Narrow", 0, 18)); // NOI18N
        jLabel6.setText("Note:");

        jLabel7.setFont(new java.awt.Font("Arial Narrow", 0, 18)); // NOI18N
        jLabel7.setText("Find Customer:");

        FindCustomer.setFont(new java.awt.Font("Arial Narrow", 0, 18)); // NOI18N
        FindCustomer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                FindCustomerActionPerformed(evt);
            }
        });

        btnSearch.setFont(new java.awt.Font("Arial Narrow", 0, 18)); // NOI18N
        btnSearch.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/icon/search.png"))); // NOI18N
        btnSearch.setText("Search");
        btnSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSearchActionPerformed(evt);
            }
        });

        txtNote.setColumns(20);
        txtNote.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        txtNote.setRows(5);
        jScrollPane1.setViewportView(txtNote);

        txtAddress.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N

        txtEmail.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N

        txtCodeCustomer.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        txtCodeCustomer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodeCustomerActionPerformed(evt);
            }
        });

        txtNameCustomer.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        txtNameCustomer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNameCustomerActionPerformed(evt);
            }
        });

        txtPhoneNumber.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        txtPhoneNumber.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtPhoneNumberActionPerformed(evt);
            }
        });

        tblnformation.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        tblnformation.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Code Customer", "Name Customer", "Phone Number", "Email", "Address", "Note"
            }
        ));
        tblnformation.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                tblnformationMouseReleased(evt);
            }
        });
        jScrollPane2.setViewportView(tblnformation);

        btnHistoryOrder.setFont(new java.awt.Font("Arial Narrow", 0, 18)); // NOI18N
        btnHistoryOrder.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/icon/HistoryOrder.png"))); // NOI18N
        btnHistoryOrder.setText("History Order");
        btnHistoryOrder.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHistoryOrderActionPerformed(evt);
            }
        });

        btnInsert.setFont(new java.awt.Font("Arial Narrow", 0, 18)); // NOI18N
        btnInsert.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/icon/insert.png"))); // NOI18N
        btnInsert.setText("Insert");
        btnInsert.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnInsertActionPerformed(evt);
            }
        });

        btnDelete.setFont(new java.awt.Font("Arial Narrow", 0, 18)); // NOI18N
        btnDelete.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/icon/delete.png"))); // NOI18N
        btnDelete.setText("Delete");
        btnDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteActionPerformed(evt);
            }
        });

        btnUpdate.setFont(new java.awt.Font("Arial Narrow", 0, 18)); // NOI18N
        btnUpdate.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/icon/Update.png"))); // NOI18N
        btnUpdate.setText("Update");
        btnUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdateActionPerformed(evt);
            }
        });

        btnReset.setFont(new java.awt.Font("Arial Narrow", 0, 18)); // NOI18N
        btnReset.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/icon/Reset.png"))); // NOI18N
        btnReset.setText("Reset");
        btnReset.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnResetActionPerformed(evt);
            }
        });

        btnCancel.setFont(new java.awt.Font("Arial Narrow", 0, 18)); // NOI18N
        btnCancel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/icon/Cancel_1.png"))); // NOI18N
        btnCancel.setText("Canel");
        btnCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelActionPerformed(evt);
            }
        });

        jLabel8.setFont(new java.awt.Font("Arial Narrow", 1, 24)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 153, 153));
        jLabel8.setText("Information Customer");

        javax.swing.GroupLayout PanelNenLayout = new javax.swing.GroupLayout(PanelNen);
        PanelNen.setLayout(PanelNenLayout);
        PanelNenLayout.setHorizontalGroup(
            PanelNenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PanelNenLayout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(PanelNenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3)
                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(38, 38, 38)
                .addGroup(PanelNenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(PanelNenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(PanelNenLayout.createSequentialGroup()
                            .addComponent(txtPhoneNumber, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(65, 65, 65)
                            .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(PanelNenLayout.createSequentialGroup()
                            .addComponent(txtNameCustomer, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(64, 64, 64)
                            .addComponent(jLabel5))
                        .addGroup(PanelNenLayout.createSequentialGroup()
                            .addComponent(txtCodeCustomer, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(65, 65, 65)
                            .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(PanelNenLayout.createSequentialGroup()
                        .addComponent(FindCustomer, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnSearch)))
                .addGap(26, 26, 26)
                .addGroup(PanelNenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(PanelNenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(txtAddress, javax.swing.GroupLayout.DEFAULT_SIZE, 367, Short.MAX_VALUE)
                        .addComponent(txtEmail))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 367, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(PanelNenLayout.createSequentialGroup()
                .addGroup(PanelNenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(PanelNenLayout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(btnHistoryOrder)
                        .addGap(50, 50, 50)
                        .addComponent(btnInsert)
                        .addGap(50, 50, 50)
                        .addComponent(btnDelete)
                        .addGap(50, 50, 50)
                        .addComponent(btnUpdate)
                        .addGap(50, 50, 50)
                        .addComponent(btnReset)
                        .addGap(50, 50, 50)
                        .addComponent(btnCancel))
                    .addGroup(PanelNenLayout.createSequentialGroup()
                        .addGap(79, 79, 79)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 815, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(25, Short.MAX_VALUE))
            .addGroup(PanelNenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(PanelNenLayout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jLabel8)
                    .addContainerGap(756, Short.MAX_VALUE)))
        );
        PanelNenLayout.setVerticalGroup(
            PanelNenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PanelNenLayout.createSequentialGroup()
                .addContainerGap(62, Short.MAX_VALUE)
                .addGroup(PanelNenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtCodeCustomer, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel4)
                    .addComponent(txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(30, 30, 30)
                .addGroup(PanelNenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtNameCustomer, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2)
                    .addComponent(txtAddress, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5))
                .addGap(23, 23, 23)
                .addGroup(PanelNenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(PanelNenLayout.createSequentialGroup()
                        .addGroup(PanelNenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtPhoneNumber, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3)
                            .addComponent(jLabel6))
                        .addGap(23, 23, 23)
                        .addGroup(PanelNenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(FindCustomer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel7)
                            .addComponent(btnSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(27, 27, 27)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 192, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(PanelNenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(PanelNenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnReset, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnCancel, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnHistoryOrder, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(PanelNenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(PanelNenLayout.createSequentialGroup()
                            .addGap(1, 1, 1)
                            .addGroup(PanelNenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(btnDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(btnInsert, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PanelNenLayout.createSequentialGroup()
                            .addComponent(btnUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(1, 1, 1))))
                .addGap(21, 21, 21))
            .addGroup(PanelNenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(PanelNenLayout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jLabel8)
                    .addContainerGap(524, Short.MAX_VALUE)))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(PanelNen, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(PanelNen, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtPhoneNumberActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPhoneNumberActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtPhoneNumberActionPerformed

    private void FindCustomerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_FindCustomerActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_FindCustomerActionPerformed

    private void btnCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelActionPerformed
        // TODO add your handling code here:
       this.dispose();
    }//GEN-LAST:event_btnCancelActionPerformed

    private void btnHistoryOrderActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHistoryOrderActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnHistoryOrderActionPerformed

    private void btnInsertActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnInsertActionPerformed
        // TODO add your handling code here:
        Insert();
    }//GEN-LAST:event_btnInsertActionPerformed

    private void btnResetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnResetActionPerformed
        // TODO add your handling code here:
        clearForm();
    }//GEN-LAST:event_btnResetActionPerformed

    private void txtNameCustomerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNameCustomerActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNameCustomerActionPerformed

    private void txtCodeCustomerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodeCustomerActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCodeCustomerActionPerformed

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        // TODO add your handling code here:
        Delete();
    }//GEN-LAST:event_btnDeleteActionPerformed

    private void btnUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateActionPerformed
        // TODO add your handling code here:
        Update();
    }//GEN-LAST:event_btnUpdateActionPerformed

    private void btnSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSearchActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnSearchActionPerformed

    private void tblnformationMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblnformationMouseReleased
        // TODO add your handling code here:
        if (evt.getClickCount() == 2) {
            this.row = tblnformation.getSelectedRow();
            showDetail();
            this.updateStatus();
        }
    }//GEN-LAST:event_tblnformationMouseReleased

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
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(ManageCustomer.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ManageCustomer.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ManageCustomer.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ManageCustomer.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
         java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                ManageCustomer dialog = new ManageCustomer(new javax.swing.JFrame(), true);
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

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField FindCustomer;
    private javax.swing.JPanel PanelNen;
    private javax.swing.JButton btnCancel;
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnHistoryOrder;
    private javax.swing.JButton btnInsert;
    private javax.swing.JButton btnReset;
    private javax.swing.JButton btnSearch;
    private javax.swing.JButton btnUpdate;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable tblnformation;
    private javax.swing.JTextField txtAddress;
    private javax.swing.JTextField txtCodeCustomer;
    private javax.swing.JTextField txtEmail;
    private javax.swing.JTextField txtNameCustomer;
    private javax.swing.JTextArea txtNote;
    private javax.swing.JTextField txtPhoneNumber;
    // End of variables declaration//GEN-END:variables
}
