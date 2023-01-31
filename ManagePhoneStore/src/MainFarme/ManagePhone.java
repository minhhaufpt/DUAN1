/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package MainFarme;

import javax.swing.ImageIcon;
import DAO.InformationPhoneDAO;
import Entity.InformationPhone;
import Libraries.Dialog;
import Libraries.XImage;
import Libraries.XMoney;
import ScanPicture.VideoCapture;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import org.opencv.core.Core;

/**
 *
 * @author PC
 */
public class ManagePhone extends javax.swing.JDialog {

    InformationPhoneDAO dao = new InformationPhoneDAO();
    public int row;
    int search = 1;

    /**
     * Creates new form InformationPhone
     */
    public ManagePhone(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        init();
    }

    public ManagePhone(java.awt.Dialog parent, boolean modal) {
        super(parent, modal);
        initComponents();
        init();
    }

    public void init() {
        setLocationRelativeTo(null);
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        this.fillTable();
        this.row = -1;
        this.updateStatus();
    }

    public boolean check() {
        try {
            double p = Double.parseDouble(txtSellingPrice.getText().trim().replace(",", "").replace(".", ""));
        } catch (NumberFormatException e) {
            Dialog.MessageWarning(this, "Please enter information number!");
            txtSellingPrice.requestFocus();
            return false;
        }
        try {
            double p = Double.parseDouble(txtQuantity.getText());
        } catch (NumberFormatException e) {
            Dialog.MessageWarning(this, "Please enter information number!");
            txtQuantity.requestFocus();
            return false;

        }
        return true;
    }

    InformationPhone getForm() {
        InformationPhone entity = new InformationPhone();
        if (check() && checkValidate()) {
            entity.setCodePhone(txtPhoneCode.getText().trim());
            entity.setNamePhone(txtPhoneName.getText());
            entity.setPrice(Double.parseDouble(txtSellingPrice.getText().trim().replace(",", "").replace(".", "")));
            entity.setQuantity(Integer.parseInt(txtQuantity.getText()));
            entity.setOperatingSystem(String.valueOf(cbooperatingSystem.getSelectedItem()));
            entity.setBrand(String.valueOf(cboBrand.getSelectedItem()));
            entity.setCPU(txtCPU.getText());
            entity.setColor(String.valueOf(cbColor.getSelectedItem()));
            entity.setSize(String.valueOf(cboSize.getSelectedItem()));
            entity.setROM(Integer.parseInt(String.valueOf(cboRom.getSelectedItem())));
            entity.setRAM(Integer.parseInt(String.valueOf(cboRam.getSelectedItem())));
            entity.setSceenResolution(String.valueOf(cboScreen.getSelectedItem()));
            entity.setCamera(String.valueOf(cboCamera.getSelectedItem()));
            entity.setBattery(String.valueOf(cboBattery.getSelectedItem()));
            entity.setYearOfManufacture(Integer.parseInt(String.valueOf(cboYear.getSelectedItem())));
            entity.setMaterial(String.valueOf(cboMateria.getSelectedItem()));
            entity.setOrigin(String.valueOf(cboOrigin.getSelectedItem()));
            entity.setImage(lblImage.getToolTipText() == null ? "No Image" : lblImage.getToolTipText());
            return entity;
        } else {
            return null;
        }
    }

    void Insert() {
        if (checkValidate()) {
            InformationPhone entity = getForm();
            if (entity != null)
            try {
                dao.Insert(entity);
                this.fillTable();
                this.clearForm();
                Dialog.MessageInformation(this, "Successfully added new!");
            } catch (Exception ex) {
                Dialog.MessageError(this, "Phone Code already exists\nNew add failed!");
            }
        }
    }

    void Delete() {
        String PhoneCode = txtPhoneCode.getText();
        if (Dialog.Confirm(this, "Do you want to delete this product?")) {
            try {
                dao.Detele(PhoneCode);
                this.fillTable();
                this.clearForm();
                Dialog.MessageInformation(this, "Delete successfully");
            } catch (Exception e) {
                Dialog.MessageError(this, "Error occurred, delete failed!");
            }
        }
    }

    void Update() {

        InformationPhone entity = getForm();
        if (entity != null) {
            try {
                dao.Update(entity);
                this.fillTable();
                Dialog.MessageInformation(this, "Update succefully!");
            } catch (Exception e) {
                Dialog.MessageError(this, "Update failed!");
            }
        }
    }

    boolean checkValidate() {
        if (txtPhoneCode.getText().equals("")) {
            txtPhoneCode.requestFocus();
            Dialog.MessageWarning(this, "Please enter full information");
            return false;
        } else if (txtPhoneName.getText().equals("")) {
            txtPhoneName.requestFocus();
            Dialog.MessageWarning(this, "Please enter full information");
            return false;
        } else if (txtSellingPrice.getText().equals("")) {
            txtSellingPrice.requestFocus();
            Dialog.MessageWarning(this, "Please enter full information");
            return false;
        } else if (txtQuantity.getText().equals("")) {
            txtQuantity.requestFocus();
            Dialog.MessageWarning(this, "Please enter full information");
            return false;
        } else if (txtCPU.getText().equals("")) {
            txtCPU.requestFocus();
            Dialog.MessageWarning(this, "Please enter full information");
            return false;
        } else if (cbColor.getSelectedItem() == null) {
            Dialog.MessageWarning(this, "Please enter full information");
            return false;
        } else if (cboBattery.getSelectedItem() == null) {
            Dialog.MessageWarning(this, "Please enter full information");
            return false;
        } else if (cboBrand.getSelectedItem() == null) {
            Dialog.MessageWarning(this, "Please enter full information");
            return false;
        } else if (cboCamera.getSelectedItem() == null) {
            Dialog.MessageWarning(this, "Please enter full information");
            return false;
        } else if (cboMateria.getSelectedItem() == null) {
            Dialog.MessageWarning(this, "Please enter full information");
            return false;
        } else if (cboOrigin.getSelectedItem() == null) {
            Dialog.MessageWarning(this, "Please enter full information");
            return false;
        } else if (cboRam.getSelectedItem() == null) {
            Dialog.MessageWarning(this, "Please enter full information");
            return false;
        } else if (cboRom.getSelectedItem() == null) {
            Dialog.MessageWarning(this, "Please enter full information");
            return false;
        } else if (cboScreen.getSelectedItem() == null) {
            Dialog.MessageWarning(this, "Please enter full information");
            return false;
        } else if (cboSize.getSelectedItem() == null) {
            Dialog.MessageWarning(this, "Please enter full information");
            return false;
        } else if (cboYear.getSelectedItem() == null) {
            Dialog.MessageWarning(this, "Please enter full information");
            return false;
        } else if (cbooperatingSystem.getSelectedItem() == null) {
            Dialog.MessageWarning(this, "Please enter full information");
            return false;
        }
        return true;
    }

    void fillTable() {
        DefaultTableModel model = (DefaultTableModel) tblPhone.getModel();
        model.setRowCount(0);
        try {
            try {
                List<InformationPhone> lst = dao.SelectAll();
                for (InformationPhone cd : lst) {
                    Object[] row = {cd.getCodePhone(), cd.getNamePhone(), XMoney.toString(cd.getPrice(), "EN"), cd.getQuantity(), cd.getRAM() + "GB/" + cd.getROM() + "GB", cd.getCPU(), cd.getBrand()};
                    model.addRow(row);
                }
                tblPhone.setDefaultEditor(Object.class, null);
            } catch (Exception e) {
                Dialog.MessageError(this, "Unable to query data! \n" + e);
            }

        } catch (Exception e) {
            Dialog.MessageError(this, "Unable to query data ! \n" + e);
        }
    }

    void fillTableToList(List<InformationPhone> lst) {
        DefaultTableModel model = (DefaultTableModel) tblPhone.getModel();
        model.setRowCount(0);
        try {
            try {
                for (InformationPhone cd : lst) {
                    Object[] row = {cd.getCodePhone(), cd.getNamePhone(), XMoney.toString(cd.getPrice(), "EN"), cd.getQuantity(), cd.getRAM() + "GB/" + cd.getROM() + "GB", cd.getCPU(), cd.getBrand()};
                    model.addRow(row);
                }
                tblPhone.setDefaultEditor(Object.class, null);
            } catch (Exception e) {
                Dialog.MessageError(this, "Unable to query data! \n" + e);
            }

        } catch (Exception e) {
            Dialog.MessageError(this, "Unable to query data ! \n" + e);
        }
    }

    void getImage() {
        JFileChooser filechoose = new JFileChooser();
        if (txtPhoneCode.getText().trim().equals("")) {
            Dialog.MessageError(this, "Please enter CodeProduct to choose image");
        } else {
            if (filechoose.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
                File file = filechoose.getSelectedFile();
                XImage.SaveImgPro(file, txtPhoneCode.getText().trim());
                ImageIcon icon = XImage.ReadImgPro(txtPhoneCode.getText().trim() + ".jpg");
                lblImage.setIcon(XImage.setResizeable(icon, 150, 200, 0));
                lblImage.setToolTipText(txtPhoneCode.getText().trim() + ".jpg");
            }
        }
    }

    void setForm(InformationPhone infor) {
//        txtSearch.setText("");
        cboBattery.setSelectedItem(infor.getBattery());
        txtCPU.setText(infor.getCPU());
        cboCamera.setSelectedItem(infor.getCamera());
        cbColor.setSelectedItem(infor.getColor());
        cboMateria.setSelectedItem(infor.getMaterial());
        cboOrigin.setSelectedItem(infor.getOrigin());
        txtPhoneCode.setText(infor.getCodePhone());
        txtPhoneName.setText(infor.getNamePhone());
        txtQuantity.setText(String.valueOf(infor.getQuantity()));
        cboScreen.setSelectedItem(infor.getSceenResolution());
        txtSellingPrice.setText(XMoney.toString(infor.getPrice(), "VN"));
        cboSize.setSelectedItem(infor.getSize());
        cboBrand.setSelectedItem(infor.getBrand());
        cboRam.setSelectedItem(String.valueOf(infor.getRAM()).equals("0") ? null : String.valueOf(infor.getRAM()));
        cboRom.setSelectedItem(String.valueOf(infor.getROM()).equals("0") ? null : String.valueOf(infor.getROM()));
        cboYear.setSelectedItem(String.valueOf(infor.getYearOfManufacture()).equals("0") ? null : String.valueOf(infor.getYearOfManufacture()));
        cbooperatingSystem.setSelectedItem(infor.getOperatingSystem());
        lblImage.setText("");
        if (infor.getImage() != null && !infor.getImage().equalsIgnoreCase("No Image")) {
            lblImage.setIcon(XImage.setResizeable(XImage.ReadImgPro(infor.getImage()), 170, 170, 0));
        } else {
            lblImage.setIcon(null);
        }
    }

    void clearForm() {
        txtPhoneCode.setText("");
        txtPhoneName.setText("");
        txtSellingPrice.setText("");
        txtQuantity.setText("");
        cbColor.setSelectedItem(null);
        cbooperatingSystem.setSelectedItem(null);
        cboBrand.setSelectedItem(null);
        txtCPU.setText("");
        cboRom.setSelectedItem(null);
        cboRam.setSelectedItem(null);
        cboScreen.setSelectedItem(null);
        cboCamera.setSelectedItem(null);
        cboBattery.setSelectedItem(null);
        cboYear.setSelectedItem(null);
        cboMateria.setSelectedItem(null);
        cboSize.setSelectedItem(null);
        cboOrigin.setSelectedItem(null);
        lblImage.setText("");
        lblImage.setIcon(null);
        lblImage.setToolTipText(null);
        this.row = -1;
        this.updateStatus();
    }

    void showDetail() {
        if (row != -1) {
            String code = String.valueOf(tblPhone.getValueAt(row, 0));
            InformationPhone info = dao.SelectID(code);
            this.setForm(info);
//            txtPhoneCode.enable(false);
            this.updateStatus();
        }
    }

//    void updateStatus() {
//        boolean showDetail = (this.row >= 0);
//        //trang thai from
//        btnInsert.setEnabled(!showDetail);
//        btnUpdate.setEnabled(showDetail);
//        btnDelete.setEnabled(showDetail);
//        //nut chuyen tiep
//    }
    public void setTab(int index) {
        this.TabPanel.setSelectedIndex(index);
    }

    public void viewPhoneDetail(String codephone) {
        btnDelete.setEnabled(false);
        btnDelete.setVisible(false);
        btnUpdate.setEnabled(false);
        btnUpdate.setVisible(false);
        btnInsert.setEnabled(false);
        btnInsert.setVisible(false);
        btnReset.setEnabled(false);
        btnReset.setVisible(false);
        setTab(1);
        try {
            this.setForm(dao.SelectID(codephone));
        } catch (Exception e) {
            Dialog.MessageError(this, "No products found");
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panelnen = new javax.swing.JPanel();
        TabPanel = new javax.swing.JTabbedPane();
        tabTable = new javax.swing.JPanel();
        truot1 = new javax.swing.JScrollPane();
        tblPhone = new javax.swing.JTable();
        lbFind = new javax.swing.JLabel();
        txtSearch = new javax.swing.JTextField();
        btnSearch = new javax.swing.JButton();
        tabDetail = new javax.swing.JPanel();
        TabGeneral = new javax.swing.JPanel();
        btnScanProduct = new javax.swing.JButton();
        btnUploadImage = new javax.swing.JButton();
        lblImage = new javax.swing.JLabel();
        txtPhoneCode = new javax.swing.JTextField();
        txtPhoneName = new javax.swing.JTextField();
        txtSellingPrice = new javax.swing.JTextField();
        txtQuantity = new javax.swing.JTextField();
        lblPhoneCode = new javax.swing.JLabel();
        lblPhoneName = new javax.swing.JLabel();
        lblSellingPrice = new javax.swing.JLabel();
        lblQuantity = new javax.swing.JLabel();
        lblImage1 = new javax.swing.JLabel();
        tabSpecification = new javax.swing.JPanel();
        lblCPU = new javax.swing.JLabel();
        lblSystem = new javax.swing.JLabel();
        lblscreen = new javax.swing.JLabel();
        lblbrand = new javax.swing.JLabel();
        lblCamera = new javax.swing.JLabel();
        lblBattery = new javax.swing.JLabel();
        txtCPU = new javax.swing.JTextField();
        lblcolor = new javax.swing.JLabel();
        lblsize = new javax.swing.JLabel();
        lblRam = new javax.swing.JLabel();
        lblrom = new javax.swing.JLabel();
        lblyear = new javax.swing.JLabel();
        lblMateria = new javax.swing.JLabel();
        lblorigin = new javax.swing.JLabel();
        cboRam = new javax.swing.JComboBox<>();
        cboRom = new javax.swing.JComboBox<>();
        cbooperatingSystem = new javax.swing.JComboBox<>();
        cboBrand = new javax.swing.JComboBox<>();
        cboYear = new javax.swing.JComboBox<>();
        cbColor = new javax.swing.JComboBox<>();
        cboMateria = new javax.swing.JComboBox<>();
        cboScreen = new javax.swing.JComboBox<>();
        cboSize = new javax.swing.JComboBox<>();
        cboCamera = new javax.swing.JComboBox<>();
        cboBattery = new javax.swing.JComboBox<>();
        cboOrigin = new javax.swing.JComboBox<>();
        btnInsert = new javax.swing.JButton();
        btnUpdate = new javax.swing.JButton();
        btnDelete = new javax.swing.JButton();
        btnReset = new javax.swing.JButton();
        btnCancel = new javax.swing.JButton();
        btnLeftEnd = new javax.swing.JButton();
        btnLeft = new javax.swing.JButton();
        btnRight = new javax.swing.JButton();
        btnRightEnd = new javax.swing.JButton();
        lblInformationPhone = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Phone");
        setIconImage(new ImageIcon(getClass().getResource("/Image/Icon/Logo.jpg")).getImage());

        panelnen.setBackground(new java.awt.Color(204, 204, 255));

        TabPanel.setBackground(new java.awt.Color(255, 204, 204));
        TabPanel.setFont(new java.awt.Font("Arial Narrow", 0, 20)); // NOI18N

        tabTable.setBackground(new java.awt.Color(204, 255, 204));

        tblPhone.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        tblPhone.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "CodePhone", "NamePhone", "Price(VND)", "Quantity", "Memory(RAM/ROM)", "CPU", "Brand"
            }
        ));
        tblPhone.setRowHeight(20);
        tblPhone.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                tblPhoneMouseReleased(evt);
            }
        });
        truot1.setViewportView(tblPhone);

        lbFind.setFont(new java.awt.Font("Arial Narrow", 0, 18)); // NOI18N
        lbFind.setText("Find Phone:");

        txtSearch.setFont(new java.awt.Font("Arial Narrow", 0, 18)); // NOI18N
        txtSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtSearchActionPerformed(evt);
            }
        });

        btnSearch.setFont(new java.awt.Font("Arial Narrow", 0, 18)); // NOI18N
        btnSearch.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Icon/Search_1.png"))); // NOI18N
        btnSearch.setText("Search");
        btnSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSearchActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout tabTableLayout = new javax.swing.GroupLayout(tabTable);
        tabTable.setLayout(tabTableLayout);
        tabTableLayout.setHorizontalGroup(
            tabTableLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(tabTableLayout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addComponent(truot1, javax.swing.GroupLayout.PREFERRED_SIZE, 1026, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(19, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, tabTableLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lbFind)
                .addGap(29, 29, 29)
                .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 560, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(94, 94, 94)
                .addComponent(btnSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(84, 84, 84))
        );
        tabTableLayout.setVerticalGroup(
            tabTableLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(tabTableLayout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addGroup(tabTableLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbFind)
                    .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(truot1, javax.swing.GroupLayout.PREFERRED_SIZE, 512, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(38, Short.MAX_VALUE))
        );

        TabPanel.addTab("Table", tabTable);

        tabDetail.setBackground(new java.awt.Color(255, 255, 204));
        tabDetail.setForeground(new java.awt.Color(255, 255, 204));

        TabGeneral.setBackground(new java.awt.Color(204, 255, 255));
        TabGeneral.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "General Information", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Arial Narrow", 0, 18))); // NOI18N

        btnScanProduct.setFont(new java.awt.Font("Arial Narrow", 0, 18)); // NOI18N
        btnScanProduct.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Icon/scan_1.png"))); // NOI18N
        btnScanProduct.setText("Scan Product");
        btnScanProduct.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnScanProductActionPerformed(evt);
            }
        });

        btnUploadImage.setFont(new java.awt.Font("Arial Narrow", 0, 18)); // NOI18N
        btnUploadImage.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Icon/Upload Image.png"))); // NOI18N
        btnUploadImage.setText("Upload Image");
        btnUploadImage.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUploadImageActionPerformed(evt);
            }
        });

        lblImage.setFont(new java.awt.Font("Tahoma", 3, 14)); // NOI18N
        lblImage.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblImage.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        lblImage.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblImageMouseClicked(evt);
            }
        });

        txtPhoneCode.setFont(new java.awt.Font("Arial Narrow", 0, 18)); // NOI18N

        txtPhoneName.setFont(new java.awt.Font("Arial Narrow", 0, 18)); // NOI18N
        txtPhoneName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtPhoneNameActionPerformed(evt);
            }
        });

        txtSellingPrice.setFont(new java.awt.Font("Arial Narrow", 0, 18)); // NOI18N
        txtSellingPrice.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtSellingPriceMouseClicked(evt);
            }
        });
        txtSellingPrice.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtSellingPriceActionPerformed(evt);
            }
        });

        txtQuantity.setFont(new java.awt.Font("Arial Narrow", 0, 18)); // NOI18N

        lblPhoneCode.setFont(new java.awt.Font("Arial Narrow", 0, 18)); // NOI18N
        lblPhoneCode.setText("Phone Code:");

        lblPhoneName.setFont(new java.awt.Font("Arial Narrow", 0, 18)); // NOI18N
        lblPhoneName.setText("Phone Name:");

        lblSellingPrice.setFont(new java.awt.Font("Arial Narrow", 0, 18)); // NOI18N
        lblSellingPrice.setText("Selling Price (VND):");

        lblQuantity.setFont(new java.awt.Font("Arial Narrow", 0, 18)); // NOI18N
        lblQuantity.setText("Quantity:");

        lblImage1.setFont(new java.awt.Font("Arial Narrow", 0, 18)); // NOI18N
        lblImage1.setText("Image");

        javax.swing.GroupLayout TabGeneralLayout = new javax.swing.GroupLayout(TabGeneral);
        TabGeneral.setLayout(TabGeneralLayout);
        TabGeneralLayout.setHorizontalGroup(
            TabGeneralLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(TabGeneralLayout.createSequentialGroup()
                .addGroup(TabGeneralLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(TabGeneralLayout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lblImage1)
                        .addGap(179, 179, 179))
                    .addGroup(TabGeneralLayout.createSequentialGroup()
                        .addGap(40, 40, 40)
                        .addGroup(TabGeneralLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(btnUploadImage, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnScanProduct, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lblImage, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(124, 124, 124)))
                .addGroup(TabGeneralLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(TabGeneralLayout.createSequentialGroup()
                        .addGroup(TabGeneralLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(lblPhoneCode, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblPhoneName, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(20, 20, 20)
                        .addGroup(TabGeneralLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtPhoneName, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtPhoneCode, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(TabGeneralLayout.createSequentialGroup()
                        .addGroup(TabGeneralLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(lblSellingPrice, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblQuantity, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(TabGeneralLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtQuantity, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtSellingPrice, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(68, 68, 68))
        );
        TabGeneralLayout.setVerticalGroup(
            TabGeneralLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(TabGeneralLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(TabGeneralLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(TabGeneralLayout.createSequentialGroup()
                        .addComponent(lblImage1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(TabGeneralLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblImage, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, TabGeneralLayout.createSequentialGroup()
                                .addComponent(btnScanProduct, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(31, 31, 31)
                                .addComponent(btnUploadImage, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(26, 26, 26))))
                    .addGroup(TabGeneralLayout.createSequentialGroup()
                        .addGroup(TabGeneralLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtPhoneCode, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblPhoneCode))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(TabGeneralLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtPhoneName, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblPhoneName))
                        .addGap(18, 18, 18)
                        .addGroup(TabGeneralLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtSellingPrice, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblSellingPrice))
                        .addGap(18, 18, 18)
                        .addGroup(TabGeneralLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtQuantity, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblQuantity))))
                .addGap(18, 18, 18))
        );

        tabSpecification.setBackground(new java.awt.Color(204, 255, 204));
        tabSpecification.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Specifications", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Arial Narrow", 0, 18))); // NOI18N
        tabSpecification.setName(""); // NOI18N

        lblCPU.setFont(new java.awt.Font("Arial Narrow", 0, 18)); // NOI18N
        lblCPU.setText("CPU:");

        lblSystem.setFont(new java.awt.Font("Arial Narrow", 0, 18)); // NOI18N
        lblSystem.setText("Operating system:");

        lblscreen.setFont(new java.awt.Font("Arial Narrow", 0, 18)); // NOI18N
        lblscreen.setText("Screen:");

        lblbrand.setFont(new java.awt.Font("Arial Narrow", 0, 18)); // NOI18N
        lblbrand.setText("Brand:");

        lblCamera.setFont(new java.awt.Font("Arial Narrow", 0, 18)); // NOI18N
        lblCamera.setText("Camera:");

        lblBattery.setFont(new java.awt.Font("Arial Narrow", 0, 18)); // NOI18N
        lblBattery.setText("Battery");

        txtCPU.setFont(new java.awt.Font("Arial Narrow", 0, 18)); // NOI18N
        txtCPU.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCPUActionPerformed(evt);
            }
        });

        lblcolor.setFont(new java.awt.Font("Arial Narrow", 0, 18)); // NOI18N
        lblcolor.setText("Color:");

        lblsize.setFont(new java.awt.Font("Arial Narrow", 0, 18)); // NOI18N
        lblsize.setText("Size:");

        lblRam.setFont(new java.awt.Font("Arial Narrow", 0, 18)); // NOI18N
        lblRam.setText("Ram(GB):");

        lblrom.setFont(new java.awt.Font("Arial Narrow", 0, 18)); // NOI18N
        lblrom.setText("Rom(GB):");

        lblyear.setFont(new java.awt.Font("Arial Narrow", 0, 18)); // NOI18N
        lblyear.setText("Debut Year:");

        lblMateria.setFont(new java.awt.Font("Arial Narrow", 0, 18)); // NOI18N
        lblMateria.setText("Materia:");

        lblorigin.setFont(new java.awt.Font("Arial Narrow", 0, 18)); // NOI18N
        lblorigin.setText("Origin:");

        cboRam.setFont(new java.awt.Font("Arial Narrow", 0, 18)); // NOI18N
        cboRam.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "1", "2", "3", "4", "6", "8", "12", " " }));
        cboRam.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboRamActionPerformed(evt);
            }
        });

        cboRom.setFont(new java.awt.Font("Arial Narrow", 0, 18)); // NOI18N
        cboRom.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "12", "32", "64", "128", "240", "560", "1000" }));
        cboRom.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboRomActionPerformed(evt);
            }
        });

        cbooperatingSystem.setFont(new java.awt.Font("Arial Narrow", 0, 18)); // NOI18N
        cbooperatingSystem.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Android", "IOS", "Windows Phone", "Symbian", "BlackBerry OS", "Bada" }));
        cbooperatingSystem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbooperatingSystemActionPerformed(evt);
            }
        });

        cboBrand.setFont(new java.awt.Font("Arial Narrow", 0, 18)); // NOI18N
        cboBrand.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Apple", "SamSung", "Redmi", "Realme", "Xaoimi", "Oppo", "Vivo", "Huawei", "Nokia", "Tecno", "Itel", " " }));

        cboYear.setFont(new java.awt.Font("Arial Narrow", 0, 18)); // NOI18N
        cboYear.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "2019", "2020", "2021", "2022", "2023", "2024", "2025" }));

        cbColor.setFont(new java.awt.Font("Arial Narrow", 0, 18)); // NOI18N
        cbColor.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Red", "Violet", "Orange", "Blue", "Brown", "Yellow", "Green", "Black", "White", "Pink", "Gray", " " }));

        cboMateria.setFont(new java.awt.Font("Arial", 0, 16)); // NOI18N
        cboMateria.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Platic", "Anuminium", "Gold", "Silver", "Diamond" }));

        cboScreen.setFont(new java.awt.Font("Arial", 0, 16)); // NOI18N
        cboScreen.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "1024 x 768 pixels", "1280 x 720 pixels", "1280 x 720 pixels", "1440 x 720 pixels", "1920 x 1080 pixels", "2160 x 1080 pixels", "2280 x 1080 pixels", "2340 x 1080 pixels", "2560 x 1440 pixels", "3200 x 1800 pixels", "2960 x 1440 pixels", "3120 x 1440 pixels" }));
        cboScreen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboScreenActionPerformed(evt);
            }
        });

        cboSize.setFont(new java.awt.Font("Arial", 0, 16)); // NOI18N
        cboSize.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "7.1 inch", "6.9 inch", "6.7 inch", "6.5 inch", "6.3 inch", "6.1 inch", "5.9 inch", " " }));

        cboCamera.setFont(new java.awt.Font("Arial Narrow", 0, 18)); // NOI18N
        cboCamera.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "1MP", "2MP", "4MP", "8MP", "16MP", "20MP", "32MP", "50MP", "64MP", "108MP" }));

        cboBattery.setFont(new java.awt.Font("Arial Narrow", 0, 18)); // NOI18N
        cboBattery.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "2000mAh", "2500mAh", "3000mAh", "3200mAh", "3400mAh", "3600mAh", "3800mAh", "4000mAh", "4100mAh", "4200mAh", "4300mAh", "4400mAh", "4500mAh", "4600mAh", "4700mAh", "4800mAh", "4900mAh", "5000mAh", "5100mAh", "5200mAh", "5300mAh", "5400mAh", "5500mAh" }));

        cboOrigin.setFont(new java.awt.Font("Arial Narrow", 0, 18)); // NOI18N
        cboOrigin.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "VietNam", "China", "Korea", "Japan", "India", "Brazil", "America", "France", "Germany", "England", "Canada", "ThaiLan" }));

        javax.swing.GroupLayout tabSpecificationLayout = new javax.swing.GroupLayout(tabSpecification);
        tabSpecification.setLayout(tabSpecificationLayout);
        tabSpecificationLayout.setHorizontalGroup(
            tabSpecificationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(tabSpecificationLayout.createSequentialGroup()
                .addGap(45, 45, 45)
                .addGroup(tabSpecificationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(tabSpecificationLayout.createSequentialGroup()
                        .addGroup(tabSpecificationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblcolor, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(tabSpecificationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(lblCamera, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(lblBattery, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(lblCPU, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(26, 26, 26)
                        .addGroup(tabSpecificationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtCPU)
                            .addComponent(cboCamera, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(tabSpecificationLayout.createSequentialGroup()
                                .addComponent(cbColor, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(lblsize)
                                .addGap(30, 30, 30)
                                .addComponent(cboSize, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(cboBattery, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(tabSpecificationLayout.createSequentialGroup()
                        .addGroup(tabSpecificationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(tabSpecificationLayout.createSequentialGroup()
                                .addGap(82, 82, 82)
                                .addComponent(cboYear, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(17, 17, 17)
                                .addComponent(lblMateria))
                            .addComponent(lblyear))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(cboMateria, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addGap(142, 142, 142)
                .addGroup(tabSpecificationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblSystem, javax.swing.GroupLayout.DEFAULT_SIZE, 128, Short.MAX_VALUE)
                    .addComponent(lblbrand, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblscreen, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(tabSpecificationLayout.createSequentialGroup()
                        .addGroup(tabSpecificationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblRam, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblorigin, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addGap(26, 26, 26)
                .addGroup(tabSpecificationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(cbooperatingSystem, javax.swing.GroupLayout.Alignment.TRAILING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(cboScreen, javax.swing.GroupLayout.Alignment.TRAILING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(cboBrand, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(tabSpecificationLayout.createSequentialGroup()
                        .addComponent(cboRam, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 33, Short.MAX_VALUE)
                        .addComponent(lblrom, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(cboRom, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(cboOrigin, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(61, 61, 61))
        );
        tabSpecificationLayout.setVerticalGroup(
            tabSpecificationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(tabSpecificationLayout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(tabSpecificationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(tabSpecificationLayout.createSequentialGroup()
                        .addGroup(tabSpecificationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtCPU, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblCPU, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(tabSpecificationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(lblCamera, javax.swing.GroupLayout.DEFAULT_SIZE, 33, Short.MAX_VALUE)
                            .addComponent(cboCamera))
                        .addGap(18, 18, 18)
                        .addGroup(tabSpecificationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(lblBattery, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
                            .addComponent(cboBattery))
                        .addGap(18, 18, 18)
                        .addGroup(tabSpecificationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(lblcolor, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(cbColor, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 32, Short.MAX_VALUE)
                            .addGroup(tabSpecificationLayout.createSequentialGroup()
                                .addGap(1, 1, 1)
                                .addComponent(cboSize))
                            .addComponent(lblsize, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(tabSpecificationLayout.createSequentialGroup()
                        .addGroup(tabSpecificationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cbooperatingSystem, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblSystem, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(tabSpecificationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cboScreen, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblbrand))
                        .addGap(18, 18, 18)
                        .addGroup(tabSpecificationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cboBrand, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblscreen, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addGroup(tabSpecificationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblrom, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cboRam, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cboRom, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblRam, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(18, 18, 18)
                .addGroup(tabSpecificationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(tabSpecificationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(lblyear, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(cboYear, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(lblMateria, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(cboMateria, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(tabSpecificationLayout.createSequentialGroup()
                        .addGap(3, 3, 3)
                        .addGroup(tabSpecificationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(cboOrigin, javax.swing.GroupLayout.DEFAULT_SIZE, 33, Short.MAX_VALUE)
                            .addComponent(lblorigin, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap())
        );

        btnInsert.setFont(new java.awt.Font("Arial Narrow", 0, 18)); // NOI18N
        btnInsert.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Icon/insert.png"))); // NOI18N
        btnInsert.setText("Insert");
        btnInsert.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnInsertActionPerformed(evt);
            }
        });

        btnUpdate.setFont(new java.awt.Font("Arial Narrow", 0, 18)); // NOI18N
        btnUpdate.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Icon/Update.png"))); // NOI18N
        btnUpdate.setText("Update");
        btnUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdateActionPerformed(evt);
            }
        });

        btnDelete.setFont(new java.awt.Font("Arial Narrow", 0, 18)); // NOI18N
        btnDelete.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Icon/delete.png"))); // NOI18N
        btnDelete.setText("Delete");
        btnDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteActionPerformed(evt);
            }
        });

        btnReset.setFont(new java.awt.Font("Arial Narrow", 0, 18)); // NOI18N
        btnReset.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Icon/Reset.png"))); // NOI18N
        btnReset.setText("Reset");
        btnReset.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnResetActionPerformed(evt);
            }
        });

        btnCancel.setFont(new java.awt.Font("Arial Narrow", 0, 18)); // NOI18N
        btnCancel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Icon/Cancel_1.png"))); // NOI18N
        btnCancel.setText("Cancel");
        btnCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelActionPerformed(evt);
            }
        });

        btnLeftEnd.setText("|<");
        btnLeftEnd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLeftEndActionPerformed(evt);
            }
        });

        btnLeft.setText("<<");
        btnLeft.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLeftActionPerformed(evt);
            }
        });

        btnRight.setText(">>");
        btnRight.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRightActionPerformed(evt);
            }
        });

        btnRightEnd.setText(">|");
        btnRightEnd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRightEndActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout tabDetailLayout = new javax.swing.GroupLayout(tabDetail);
        tabDetail.setLayout(tabDetailLayout);
        tabDetailLayout.setHorizontalGroup(
            tabDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, tabDetailLayout.createSequentialGroup()
                .addGroup(tabDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(tabDetailLayout.createSequentialGroup()
                        .addGap(30, 31, Short.MAX_VALUE)
                        .addGroup(tabDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(TabGeneral, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(tabSpecification, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(tabDetailLayout.createSequentialGroup()
                        .addGap(41, 41, 41)
                        .addComponent(btnLeftEnd, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnLeft, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnRight, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnRightEnd, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnInsert)
                        .addGap(15, 15, 15)
                        .addComponent(btnUpdate)
                        .addGap(41, 41, 41)
                        .addComponent(btnDelete)
                        .addGap(15, 15, 15)
                        .addComponent(btnReset)
                        .addGap(15, 15, 15)
                        .addComponent(btnCancel)))
                .addGap(30, 30, 30))
        );
        tabDetailLayout.setVerticalGroup(
            tabDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(tabDetailLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(TabGeneral, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tabSpecification, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 36, Short.MAX_VALUE)
                .addGroup(tabDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnReset, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCancel, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnInsert, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnLeftEnd, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnLeft, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnRight, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnRightEnd, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(22, 22, 22))
        );

        TabPanel.addTab(" Detail ", tabDetail);

        lblInformationPhone.setFont(new java.awt.Font("Arial Narrow", 1, 24)); // NOI18N
        lblInformationPhone.setForeground(new java.awt.Color(255, 102, 102));
        lblInformationPhone.setText("Information Phone");

        javax.swing.GroupLayout panelnenLayout = new javax.swing.GroupLayout(panelnen);
        panelnen.setLayout(panelnenLayout);
        panelnenLayout.setHorizontalGroup(
            panelnenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelnenLayout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(lblInformationPhone)
                .addContainerGap(910, Short.MAX_VALUE))
            .addGroup(panelnenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(panelnenLayout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(TabPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
        panelnenLayout.setVerticalGroup(
            panelnenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelnenLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblInformationPhone)
                .addContainerGap(695, Short.MAX_VALUE))
            .addGroup(panelnenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelnenLayout.createSequentialGroup()
                    .addContainerGap(52, Short.MAX_VALUE)
                    .addComponent(TabPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 672, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap()))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(panelnen, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(panelnen, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void lblImageMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblImageMouseClicked
        // TODO add your handling code here:
        try {
            getImage();
        } catch (Exception e) {
            Dialog.MessageError(this, "Can't select photo");
        }
    }//GEN-LAST:event_lblImageMouseClicked

    private void btnCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelActionPerformed
        // TODO add your handling code here:   
        this.dispose();
    }//GEN-LAST:event_btnCancelActionPerformed

    private void btnScanProductActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnScanProductActionPerformed
        // TODO add your handling code here:
        if (txtPhoneCode.getText().trim().equals("")) {
            Dialog.MessageWarning(this, "Please enter CodePhone to save you");
        } else {
            try {
                VideoCapture vd = new VideoCapture(null, true, txtPhoneCode.getText().trim(), "Products");
                vd.setVisible(true);
            } catch (Exception e) {
                Dialog.MessageError(this, "Can't open camera");
            }
        }
    }//GEN-LAST:event_btnScanProductActionPerformed

    private void txtPhoneNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPhoneNameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtPhoneNameActionPerformed

    private void btnUploadImageActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUploadImageActionPerformed
        // TODO add your handling code here:
        try {
            getImage();
        } catch (Exception e) {
            Dialog.MessageError(this, "Can't select photo");
        }
    }//GEN-LAST:event_btnUploadImageActionPerformed

    private void btnInsertActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnInsertActionPerformed
        // TODO add your handling code here:
        Insert();
    }//GEN-LAST:event_btnInsertActionPerformed

    private void txtSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtSearchActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtSearchActionPerformed

    private void btnUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateActionPerformed
        // TODO add your handling code here:
        Update();
    }//GEN-LAST:event_btnUpdateActionPerformed

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        // TODO add your handling code here:
        Delete();
    }//GEN-LAST:event_btnDeleteActionPerformed

    private void btnResetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnResetActionPerformed
        // TODO add your handling code here:
        clearForm();
        
    }//GEN-LAST:event_btnResetActionPerformed

    private void btnSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSearchActionPerformed
        // TODO add your handling code here:
        String x = txtSearch.getText().trim();
        try {
            search = 1;
            ArrayList<InformationPhone> t = dao.SelectByName(x);
            if (t == null) {
                Dialog.MessageInformation(this, "No products");
            } else if (t.size() > 0) {
                fillTableToList(t);
//                btnSearch.setEnabled(false);
            } else {
            }
        } catch (Exception e) {
            Dialog.MessageError(this, e);
        }
        this.updateStatus();
    }//GEN-LAST:event_btnSearchActionPerformed

    private void cbooperatingSystemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbooperatingSystemActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbooperatingSystemActionPerformed

    private void cboRomActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboRomActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cboRomActionPerformed

    private void cboRamActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboRamActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cboRamActionPerformed

    private void txtCPUActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCPUActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCPUActionPerformed

    private void tblPhoneMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblPhoneMouseReleased
        // TODO add your handling code here:
        if (evt.getClickCount() == 2) {
            this.row = tblPhone.getSelectedRow();
            showDetail();
            TabPanel.setSelectedIndex(1);
            this.updateStatus();
        }
    }//GEN-LAST:event_tblPhoneMouseReleased

    private void txtSellingPriceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtSellingPriceActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtSellingPriceActionPerformed

    private void txtSellingPriceMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtSellingPriceMouseClicked
        // TODO add your handling code here:
        txtSellingPrice.setText(txtSellingPrice.getText().replace(".", "").replace(",", ""));
    }//GEN-LAST:event_txtSellingPriceMouseClicked

    private void cboScreenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboScreenActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cboScreenActionPerformed

    private void btnLeftEndActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLeftEndActionPerformed
        // TODO add your handling code here:
        leftend();
    }//GEN-LAST:event_btnLeftEndActionPerformed

    private void btnLeftActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLeftActionPerformed
        // TODO add your handling code here:
        left();
    }//GEN-LAST:event_btnLeftActionPerformed

    private void btnRightActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRightActionPerformed
        // TODO add your handling code here:
        right();
    }//GEN-LAST:event_btnRightActionPerformed

    private void btnRightEndActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRightEndActionPerformed
        // TODO add your handling code here:
        rightend();
    }//GEN-LAST:event_btnRightEndActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(ManagePhone.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ManagePhone.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ManagePhone.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ManagePhone.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                ManagePhone dialog = new ManagePhone(new javax.swing.JFrame(), true);
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
    private javax.swing.JPanel TabGeneral;
    private javax.swing.JTabbedPane TabPanel;
    private javax.swing.JButton btnCancel;
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnInsert;
    private javax.swing.JButton btnLeft;
    private javax.swing.JButton btnLeftEnd;
    private javax.swing.JButton btnReset;
    private javax.swing.JButton btnRight;
    private javax.swing.JButton btnRightEnd;
    private javax.swing.JButton btnScanProduct;
    private javax.swing.JButton btnSearch;
    private javax.swing.JButton btnUpdate;
    private javax.swing.JButton btnUploadImage;
    private javax.swing.JComboBox<String> cbColor;
    private javax.swing.JComboBox<String> cboBattery;
    private javax.swing.JComboBox<String> cboBrand;
    private javax.swing.JComboBox<String> cboCamera;
    private javax.swing.JComboBox<String> cboMateria;
    private javax.swing.JComboBox<String> cboOrigin;
    private javax.swing.JComboBox<String> cboRam;
    private javax.swing.JComboBox<String> cboRom;
    private javax.swing.JComboBox<String> cboScreen;
    private javax.swing.JComboBox<String> cboSize;
    private javax.swing.JComboBox<String> cboYear;
    private javax.swing.JComboBox<String> cbooperatingSystem;
    private javax.swing.JLabel lbFind;
    private javax.swing.JLabel lblBattery;
    private javax.swing.JLabel lblCPU;
    private javax.swing.JLabel lblCamera;
    private javax.swing.JLabel lblImage;
    private javax.swing.JLabel lblImage1;
    private javax.swing.JLabel lblInformationPhone;
    private javax.swing.JLabel lblMateria;
    private javax.swing.JLabel lblPhoneCode;
    private javax.swing.JLabel lblPhoneName;
    private javax.swing.JLabel lblQuantity;
    private javax.swing.JLabel lblRam;
    private javax.swing.JLabel lblSellingPrice;
    private javax.swing.JLabel lblSystem;
    private javax.swing.JLabel lblbrand;
    private javax.swing.JLabel lblcolor;
    private javax.swing.JLabel lblorigin;
    private javax.swing.JLabel lblrom;
    private javax.swing.JLabel lblscreen;
    private javax.swing.JLabel lblsize;
    private javax.swing.JLabel lblyear;
    private javax.swing.JPanel panelnen;
    private javax.swing.JPanel tabDetail;
    private javax.swing.JPanel tabSpecification;
    private javax.swing.JPanel tabTable;
    private javax.swing.JTable tblPhone;
    private javax.swing.JScrollPane truot1;
    private javax.swing.JTextField txtCPU;
    private javax.swing.JTextField txtPhoneCode;
    private javax.swing.JTextField txtPhoneName;
    private javax.swing.JTextField txtQuantity;
    private javax.swing.JTextField txtSearch;
    private javax.swing.JTextField txtSellingPrice;
    // End of variables declaration//GEN-END:variables
void rightend() {
        this.row = tblPhone.getRowCount() - 1;
        this.showDetail();
    }

    void leftend() {
        this.row = 0;
        this.showDetail();
    }

    void right() {
        if (this.row < tblPhone.getRowCount() - 1) {
            this.row++;
            this.showDetail();
        }
    }

    void left() {
        if (this.row > 0) {
            this.row--;
            this.showDetail();
        }
    }

    void updateStatus() {
        boolean showDetail = (this.row >= 0);
        boolean leftend = (this.row == 0);
        boolean rightend = (this.row == tblPhone.getRowCount() - 1);
        //trang thai from
        btnInsert.setEnabled(!showDetail);
        btnUpdate.setEnabled(showDetail);
        btnDelete.setEnabled(showDetail);
        //nut chuyen tiep
        btnLeft.setEnabled(showDetail && !leftend);
        btnLeftEnd.setEnabled(showDetail && !leftend);
        btnRight.setEnabled(showDetail && !rightend);
        btnRightEnd.setEnabled(showDetail && !rightend);
    }
}
