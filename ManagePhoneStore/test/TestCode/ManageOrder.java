/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TestCode;

import DAO.InformationCustomerDAO;
import DAO.OrderDAO;
import DAO.OrderDetailDAO;
import DAO.InformationPhoneDAO;
import DAO.StatusDAO;
import Entity.InformationCustomer;
import Entity.InformationPhone;
import Entity.Order;
import Entity.OrderDetail;
import Entity.Status;
import Libraries.CurrentOrder;
import Libraries.Dialog;
import Libraries.Login;
import Libraries.XDate;
import Libraries.XImage;
import Libraries.XMoney;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Dell
 */
public class ManageOrder extends javax.swing.JDialog {

    /**
     * Creates new form ManageOrder
     */
    StatusDAO stdao = new StatusDAO();
    public int rowDetail;
    public int rowBasic;
//    public static ArrayList<OrderDetail> CurrentOrder.Order = new ArrayList<OrderDetail>();
//    public static ArrayList<OrderDetail> CurrentOrder.Order = null;
    public ArrayList<Order> lstO = new ArrayList<Order>();
    public InformationCustomerDAO Cusdao = new InformationCustomerDAO();
    public OrderDetailDAO ODdao = new OrderDetailDAO();
    public OrderDAO Odao = new OrderDAO();
    public InformationPhoneDAO daoPhone = new InformationPhoneDAO();
    public StatusDAO daoStatus = new StatusDAO();

    public ManageOrder(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        init();
    }

    public void init() {
        setLocationRelativeTo(null);
        filltoTableOrderDetail();
        filltoTableOrder();
        fillCbCustomer();
        filltoTableSearch();
        this.rowDetail = -1;
        this.rowBasic = -1;
        this.UpdateStatus();
        SumPrice();
    }

    public void fillCbCustomer() {
        ArrayList<InformationCustomer> infor = Cusdao.SelectAll();
        for (InformationCustomer in : infor) {
            cboCustomer.addItem(String.valueOf(in.getCodeCustomer()));
        }
    }

    public void filltoTableOrderDetail() {
        DefaultTableModel model = (DefaultTableModel) tblDetail.getModel();
        model.setRowCount(0);
        ArrayList<InformationPhone> lt = new ArrayList<InformationPhone>();
        try {
            if (CurrentOrder.Order == null || CurrentOrder.Order.size() == 0) {
                txtCodeOrderDetail.setText("Không có đơn hàng");
            } else {
                if (CurrentOrder.Order.get(0).getCodeOrder() == 0) {
                    for (OrderDetail orderDetail : CurrentOrder.Order) {
                        Object[] rowDetail = {"Chưa tạo đơn hàng", daoPhone.SelectID(orderDetail.getCodePhone()).getNamePhone(), XMoney.toString(orderDetail.getPrice(), "VN"), orderDetail.getQuantity(), orderDetail.getNote()};
                        model.addRow(rowDetail);
                    }
                } else {
                    for (OrderDetail orderDetail : CurrentOrder.Order) {
                        Object[] rowDetail = {orderDetail.getCodePhone(), daoPhone.SelectID(orderDetail.getCodePhone()).getNamePhone(), XMoney.toString(orderDetail.getPrice(), "VN"), orderDetail.getQuantity(), orderDetail.getNote()};
                        model.addRow(rowDetail);
                    }
                }
            }
            SumPrice();
        } catch (Exception e) {
            Dialog.Message(this, "Không thể truy vấn dữ liệu !");
        }
    }

    public void viewAll() {
        DefaultTableModel model = (DefaultTableModel) tblDetail.getModel();
        model.setRowCount(0);
        ArrayList<InformationPhone> lt = new ArrayList<InformationPhone>();
        try {
            txtCodeOrderDetail.setText("");
            ArrayList<OrderDetail> dts = ODdao.SelectAll();
            for (OrderDetail orderDetail : dts) {
                Object[] rowDetail = {orderDetail.getCodePhone(), daoPhone.SelectID(orderDetail.getCodePhone()).getNamePhone(), XMoney.toString(orderDetail.getPrice(), "VN"), orderDetail.getQuantity(), orderDetail.getNote()};
                model.addRow(rowDetail);
            }
        } catch (Exception e) {
            Dialog.Message(this, "Không thể truy vấn dữ liệu !");
        }
    }

    public void filltoTableOrder() {
        DefaultTableModel model = (DefaultTableModel) tblOrder.getModel();
        model.setRowCount(0);
        try {
            ArrayList<Order> od = new ArrayList<Order>();
            od = Odao.SelectAll();
            for (Order order : od) {
                Object[] rowDetail = {order.getCodeOrder(), Cusdao.SelectID(String.valueOf(order.getCodeCustomer())).getNameCustomer(), order.getAddressOrder(), XDate.toString(order.getOrderDate(), "dd/MM/yyyy"), order.isStatus() ? "Hoàn thành" : "Chưa hoàn thành", order.getCodeEmployye()};
                model.addRow(rowDetail);
            }
            tblOrder.setDefaultEditor(Object.class, null);
        } catch (Exception e) {
            Dialog.Message(this, "Không thể truy vấn dữ liệu ! " + e);
        }

    }

    public void filltoTableSearch() {
        DefaultTableModel model = (DefaultTableModel) tblSearchPhone.getModel();
        model.setRowCount(0);
        try {
            ArrayList<InformationPhone> od = new ArrayList<InformationPhone>();
            od = daoPhone.SelectAll();
            for (InformationPhone phone : od) {
                Object[] rowDetail = {phone.getCodePhone(), phone.getNamePhone(), phone.getQuantity(), XMoney.toString(phone.getPrice(), "VN")};
                model.addRow(rowDetail);
            }
            tblOrder.setDefaultEditor(Object.class, null);
        } catch (Exception e) {
            Dialog.Message(this, "Không thể truy vấn dữ liệu ! " + e);
        }

    }

    public void filltoTableOrder(Order order) {
        DefaultTableModel model = (DefaultTableModel) tblOrder.getModel();
        model.setRowCount(0);
        try {
            Object[] rowDetail = {order.getCodeOrder(), Cusdao.SelectID(String.valueOf(order.getCodeCustomer())).getNameCustomer(), order.getAddressOrder(), XDate.toString(order.getOrderDate(), "dd/MM/yyyy"), order.isStatus() ? "Hoàn thành" : "Chưa hoàn thành", order.getCodeEmployye()};
            model.addRow(rowDetail);
            tblOrder.setDefaultEditor(Object.class, null);
        } catch (Exception e) {
            Dialog.Message(this, "Không thể truy vấn dữ liệu ! " + e);
        }

    }

    public void showSearchPhone(InformationPhone t) {
        txtCodePhone.setText(t.getCodePhone());
        txtNamePhone.setText(t.getNamePhone());
        txtQuantityInShop.setText(String.valueOf(t.getQuantity()));
        txtPrice.setText(XMoney.toString(t.getPrice(), "VN"));
        lblImage.setText("");
        if (t.getImage() != null && !t.getImage().equalsIgnoreCase("No Image")) {
            lblImage.setIcon(XImage.setResizeable(XImage.ReadImgPro(t.getImage()), 170, 170, 0));
        } else {
            lblImage.setIcon(null);
        }
    }

    public void setFormDeatail(OrderDetail od) {
        txtCodeOrderDetail.setText(String.valueOf(od.getCodeOrder()));
    }

    public void setFormOrder(Order od) {
        txtCodeOrder.setText(String.valueOf(od.getCodeOrder()));
        txtAddressOrder.setText(od.getAddressOrder());
        cboCustomer.setSelectedItem(String.valueOf(od.getCodeCustomer()));
        cbFormality.setSelectedItem(od.getFormality());
        DateOrder.setDate(od.getOrderDate());
        if (od.isStatus() == true) {
            chkstatus.setSelected(true);
            DatePay.setDate(od.getDateOfPayment());
        } else {
            chkstatus.setSelected(false);
            DatePay.setDate(null);
        }
    }

    public ArrayList<OrderDetail> getOrderDetail(String codeorder) {
        ArrayList<OrderDetail> detail = ODdao.SelectByIDOrder(codeorder);
        return detail;
    }

    public Order getFormOrder() {
        Order od = new Order();
        if (txtCodeOrder.getText().equalsIgnoreCase("")) {
            od.setCodeOrder(0);
        } else {
            od.setCodeOrder(Integer.parseInt(txtCodeOrder.getText()));
        }
//        od.setCodeOrder(0);
        od.setAddressOrder(txtAddressOrder.getText());
//        od.setCodeEmployye(Login.user.getUserName());
        od.setCodeEmployye("nhanvienkithuat");
        od.setOrderDate(DateOrder.getDate());
        od.setFormality(String.valueOf(cbFormality.getSelectedItem()));
        od.setDateOfPayment(DatePay.getDate());
        od.setStatus(chkstatus.isSelected() ? true : false);
        od.setCodeCustomer(Integer.parseInt(String.valueOf(cboCustomer.getSelectedItem())));
        return od;
    }

    void Insert() {
        Order entity = getFormOrder();
        if (entity != null)
        try {
            if (CurrentOrder.Order != null || CurrentOrder.Order.size() == 0) {
                entity.setOrderDate(new Date());
                int kq = Odao.Insert(entity, true);
                if (kq != 0) {
                    for (OrderDetail detail : CurrentOrder.Order) {
                        detail.setCodeOrder(kq);
                        ODdao.Insert(detail);
                    }
                }
                this.filltoTableOrder();
//                this.clearForm();
                Dialog.MessageWarning(this, "Successfully added new!");
            } else {
                Dialog.MessageWarning(this, "Không tìm thấy sản phẩm trong đơn hàng");
            }
        } catch (Exception ex) {
            Dialog.MessageError(this, "Phone Code already exists\nNew add failed! === " + ex);
        }

    }

    ArrayList<OrderDetail> getUpdateDetail() {
        ArrayList<OrderDetail> lst = new ArrayList<OrderDetail>();
        if (CurrentOrder.Order != null || CurrentOrder.Order.size() == 0) {
            for (int i = 0; i < tblDetail.getRowCount(); i++) {
                OrderDetail od = new OrderDetail();
                od.setCodeOrder(Integer.parseInt(txtCodeOrderDetail.getText().trim()));
                od.setCodePhone(String.valueOf(tblDetail.getValueAt(i, 0)));
                od.setQuantity(Integer.parseInt(String.valueOf(tblDetail.getValueAt(i, 3))));
                od.setNote(String.valueOf(tblDetail.getValueAt(i, 4)).equals("") ? "No Note" : String.valueOf(tblDetail.getValueAt(i, 4)));
                od.setPrice(daoPhone.SelectID(od.getCodePhone()).getPrice());
                lst.add(od);
            }
        } else {
            Dialog.MessageWarning(this, "Không tìm thấy sản phẩm cần update");
            return null;
        }
        return lst.size() == 0 ? null : lst;
    }

    void UpdateDetail() {
        try {
            ArrayList<OrderDetail> lt = this.getUpdateDetail();
            if (lt != null) {
                ODdao.UpdateALL(lt);
                this.filltoTableOrder();
//                this.clearForm();
                Dialog.MessageWarning(this, "Successfully added new!");
            } else {
                Dialog.MessageWarning(this, "Không tìm thấy sản phẩm trong đơn hàng");
            }
        } catch (Exception ex) {
            Dialog.MessageError(this, "Phone Code already exists\nNew add failed! === " + ex);
        }
    }

    void Delete() {
        String codeorder = txtCodeOrder.getText();
        if (Dialog.Confirm(this, "Do you want to delete this product?")) {
            try {
                Odao.Detele(codeorder);
                this.filltoTableOrder();
//                this.clearForm();
                Dialog.MessageInformation(this, "Delete successfully");
            } catch (Exception e) {
                Dialog.MessageError(this, "Error occurred, delete failed!");
            }
        }
    }

    void Update() {
        Order entity = getFormOrder();
        if (entity != null) {
            try {
                Odao.Update(entity);
                this.filltoTableOrder();
                Dialog.Message(this, "Update succefully!");
            } catch (Exception e) {
                Dialog.Message(this, "Update failed!");
            }
        }
    }

    void UpdateStatus() {
//        Dialog.Message(null, this.rowBasic);
        if (this.rowBasic < 0) {
            btnDetailOrder.setEnabled(false);
            btnInsertOrder.setEnabled(true);
            btnUpdatestatus.setEnabled(false);
            txtCodeOrder.setEnabled(true);
            btnDeleteOrder.setEnabled(false);
        } else {
            btnDetailOrder.setEnabled(true);
            btnInsertOrder.setEnabled(false);
            btnUpdatestatus.setEnabled(true);
            txtCodeOrder.setEnabled(false);
            btnDeleteOrder.setEnabled(true);
        }
        if (CurrentOrder.Order == null || CurrentOrder.Order.size() == 0) {
            btnCreat.setEnabled(false);
            btnUpdateDetail.setEnabled(false);
            btnCancel1.setEnabled(false);
        } else if (CurrentOrder.Order.get(0).getCodeOrder() != 0) {
            btnCreat.setEnabled(false);
            btnUpdateDetail.setEnabled(true);
            btnCancel1.setEnabled(true);
        } else {
            btnCreat.setEnabled(true);
            btnUpdateDetail.setEnabled(false);
            btnCancel1.setEnabled(false);
        }
    }

    void ResetOrder() {
        this.rowBasic = -1;
        this.rowDetail = -1;
        filltoTableOrder();
        filltoTableOrderDetail();
        txtCodeOrder.setText("");
        txtAddressOrder.setText("");
        cboCustomer.setSelectedIndex(0);
        cbFormality.setSelectedIndex(0);
        DateOrder.setDate(null);
        chkstatus.setSelected(false);
        DatePay.setDate(null);
        UpdateStatus();

    }

    void ResetOrderDetail() {
        this.rowDetail = -1;
//        CurrentOrder.Order.clear();
        CurrentOrder.Order = null;
        txtChooseProduct.setText("");
        filltoTableOrderDetail();
        UpdateStatus();

    }

    void SumPrice() {
        double Sum = 0;
        if (tblDetail.getRowCount() != 0) {
            for (int i = 0; i < tblDetail.getRowCount(); i++) {
                double price = Double.parseDouble(String.valueOf(tblDetail.getValueAt(i, 2)).replace(",", "").replace(".", ""));
                int count = Integer.parseInt(String.valueOf(tblDetail.getValueAt(i, 3)));
                Sum += price * count;
            }
        }
        txtSum.setText("  " + XMoney.toString(Sum, "VN") + "  ");
    }

    public boolean checkDetailOrder(String codephone) {
        if (CurrentOrder.Order == null) {
            return true;
        }
        for (OrderDetail ob : CurrentOrder.Order) {
            if (ob.getCodePhone().equalsIgnoreCase(codephone)) {
                return false;
            }
        }
        return true;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        PanelNen = new javax.swing.JPanel();
        tab = new javax.swing.JTabbedPane();
        tabDetail = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblDetail = new javax.swing.JTable();
        lblOrder = new javax.swing.JLabel();
        lblChooseProduct = new javax.swing.JLabel();
        txtChooseProduct = new javax.swing.JTextField();
        btnEdit = new javax.swing.JButton();
        btnRemove = new javax.swing.JButton();
        btnSearch1 = new javax.swing.JButton();
        txtCodeOrderDetail = new javax.swing.JLabel();
        lblSum = new javax.swing.JLabel();
        txtSum = new javax.swing.JLabel();
        btnUpdateDetail = new javax.swing.JButton();
        btnReset = new javax.swing.JButton();
        btnCancel1 = new javax.swing.JButton();
        btneditNote = new javax.swing.JButton();
        btnCreat = new javax.swing.JButton();
        btnView = new javax.swing.JButton();
        tabOrder = new javax.swing.JPanel();
        lblCodeOrder = new javax.swing.JLabel();
        lblAddressOrder = new javax.swing.JLabel();
        txtCodeOrder = new javax.swing.JTextField();
        txtAddressOrder = new javax.swing.JTextField();
        lblCodeEmployee = new javax.swing.JLabel();
        lblDateOrder = new javax.swing.JLabel();
        lblSearchCode = new javax.swing.JLabel();
        txtSearchCode = new javax.swing.JTextField();
        btnSearchOrder = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblOrder = new javax.swing.JTable();
        btnInsertOrder = new javax.swing.JButton();
        btnDetailOrder = new javax.swing.JButton();
        btnDeleteOrder = new javax.swing.JButton();
        btnCancelOrder = new javax.swing.JButton();
        lblDateOrder1 = new javax.swing.JLabel();
        btnUpdatestatus = new javax.swing.JButton();
        chkstatus = new javax.swing.JCheckBox();
        DateOrder = new com.toedter.calendar.JDateChooser();
        lblDatePay = new javax.swing.JLabel();
        DatePay = new com.toedter.calendar.JDateChooser();
        lblAddressOrder1 = new javax.swing.JLabel();
        cboCustomer = new javax.swing.JComboBox<>();
        cbFormality = new javax.swing.JComboBox<>();
        btnNewCustomer = new javax.swing.JButton();
        btnResetOrder = new javax.swing.JButton();
        tabSearch = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblSearchPhone = new javax.swing.JTable();
        lblChooseProduct1 = new javax.swing.JLabel();
        txtChooseProduct1 = new javax.swing.JTextField();
        btnSearch2 = new javax.swing.JButton();
        lblCodePhone = new javax.swing.JLabel();
        lblNamePhone = new javax.swing.JLabel();
        lblQuantityInShop = new javax.swing.JLabel();
        lblPrice = new javax.swing.JLabel();
        txtCodePhone = new javax.swing.JLabel();
        txtNamePhone = new javax.swing.JLabel();
        txtQuantityInShop = new javax.swing.JLabel();
        txtPrice = new javax.swing.JLabel();
        btnChoose = new javax.swing.JButton();
        btnCancel2 = new javax.swing.JButton();
        lblImage = new javax.swing.JLabel();
        lblManageOrder = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Order");

        PanelNen.setBackground(new java.awt.Color(204, 255, 204));

        tab.setFont(new java.awt.Font("Arial Narrow", 0, 18)); // NOI18N

        tabDetail.setBackground(new java.awt.Color(255, 204, 153));

        tblDetail.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        tblDetail.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Code Phone", "Name Phone", "Price", "Quantity Of Buy", "Note"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, true, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblDetail.setRowHeight(20);
        tblDetail.addHierarchyListener(new java.awt.event.HierarchyListener() {
            public void hierarchyChanged(java.awt.event.HierarchyEvent evt) {
                tblDetailHierarchyChanged(evt);
            }
        });
        tblDetail.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblDetailMouseClicked(evt);
            }
        });
        tblDetail.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                tblDetailPropertyChange(evt);
            }
        });
        tblDetail.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tblDetailKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tblDetailKeyReleased(evt);
            }
        });
        tblDetail.addVetoableChangeListener(new java.beans.VetoableChangeListener() {
            public void vetoableChange(java.beans.PropertyChangeEvent evt)throws java.beans.PropertyVetoException {
                tblDetailVetoableChange(evt);
            }
        });
        jScrollPane2.setViewportView(tblDetail);

        lblOrder.setFont(new java.awt.Font("Arial Narrow", 0, 18)); // NOI18N
        lblOrder.setText("Code Order:");

        lblChooseProduct.setFont(new java.awt.Font("Arial Narrow", 0, 18)); // NOI18N
        lblChooseProduct.setText("Choose Product");

        txtChooseProduct.setFont(new java.awt.Font("Arial Narrow", 0, 18)); // NOI18N
        txtChooseProduct.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtChooseProductActionPerformed(evt);
            }
        });

        btnEdit.setFont(new java.awt.Font("Arial Narrow", 0, 18)); // NOI18N
        btnEdit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/icon/edit quantity.png"))); // NOI18N
        btnEdit.setText("Edit Quantity");

        btnRemove.setFont(new java.awt.Font("Arial Narrow", 0, 18)); // NOI18N
        btnRemove.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/icon/remove product.png"))); // NOI18N
        btnRemove.setText("Remove Product");
        btnRemove.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRemoveActionPerformed(evt);
            }
        });

        btnSearch1.setFont(new java.awt.Font("Arial Narrow", 0, 18)); // NOI18N
        btnSearch1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/icon/Search_1.png"))); // NOI18N
        btnSearch1.setText("Search");
        btnSearch1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSearch1ActionPerformed(evt);
            }
        });

        txtCodeOrderDetail.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N

        lblSum.setFont(new java.awt.Font("Arial Narrow", 0, 18)); // NOI18N
        lblSum.setText("Sum ( VNĐ ) : ");

        txtSum.setFont(new java.awt.Font("Arial Narrow", 0, 18)); // NOI18N

        btnUpdateDetail.setFont(new java.awt.Font("Arial Narrow", 0, 18)); // NOI18N
        btnUpdateDetail.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/icon/Update.png"))); // NOI18N
        btnUpdateDetail.setText("Update Detail");
        btnUpdateDetail.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdateDetailActionPerformed(evt);
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

        btnCancel1.setFont(new java.awt.Font("Arial Narrow", 0, 18)); // NOI18N
        btnCancel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/icon/back01.png"))); // NOI18N
        btnCancel1.setText("Back");
        btnCancel1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancel1ActionPerformed(evt);
            }
        });

        btneditNote.setFont(new java.awt.Font("Arial Narrow", 0, 18)); // NOI18N
        btneditNote.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/icon/edit note.png"))); // NOI18N
        btneditNote.setText("Edit Note");

        btnCreat.setFont(new java.awt.Font("Arial Narrow", 0, 18)); // NOI18N
        btnCreat.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/icon/CreateOrder.png"))); // NOI18N
        btnCreat.setText("Create Order");
        btnCreat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCreatActionPerformed(evt);
            }
        });

        btnView.setFont(new java.awt.Font("Arial Narrow", 0, 18)); // NOI18N
        btnView.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/icon/view all.png"))); // NOI18N
        btnView.setText("View All");
        btnView.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnViewActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout tabDetailLayout = new javax.swing.GroupLayout(tabDetail);
        tabDetail.setLayout(tabDetailLayout);
        tabDetailLayout.setHorizontalGroup(
            tabDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(tabDetailLayout.createSequentialGroup()
                .addGroup(tabDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(tabDetailLayout.createSequentialGroup()
                        .addGap(25, 25, 25)
                        .addGroup(tabDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(tabDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addGroup(tabDetailLayout.createSequentialGroup()
                                    .addComponent(btnView)
                                    .addGap(65, 65, 65)
                                    .addComponent(btnEdit)
                                    .addGap(65, 65, 65)
                                    .addComponent(btneditNote)
                                    .addGap(65, 65, 65)
                                    .addComponent(btnRemove))
                                .addGroup(tabDetailLayout.createSequentialGroup()
                                    .addComponent(lblOrder, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(18, 18, 18)
                                    .addComponent(txtCodeOrderDetail, javax.swing.GroupLayout.PREFERRED_SIZE, 173, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(44, 44, 44)
                                    .addComponent(lblChooseProduct)
                                    .addGap(28, 28, 28)
                                    .addComponent(txtChooseProduct, javax.swing.GroupLayout.PREFERRED_SIZE, 162, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(18, 18, 18)
                                    .addComponent(btnSearch1)))
                            .addGroup(tabDetailLayout.createSequentialGroup()
                                .addComponent(btnCreat)
                                .addGap(90, 90, 90)
                                .addComponent(btnUpdateDetail)
                                .addGap(90, 90, 90)
                                .addComponent(btnReset, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(58, 58, 58)
                                .addComponent(btnCancel1))
                            .addGroup(tabDetailLayout.createSequentialGroup()
                                .addComponent(lblSum)
                                .addGap(18, 18, 18)
                                .addComponent(txtSum, javax.swing.GroupLayout.PREFERRED_SIZE, 538, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(tabDetailLayout.createSequentialGroup()
                        .addGap(40, 40, 40)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 723, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(30, 30, 30))
        );
        tabDetailLayout.setVerticalGroup(
            tabDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(tabDetailLayout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addGroup(tabDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, tabDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(lblChooseProduct, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtChooseProduct, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnSearch1, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(txtCodeOrderDetail, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblOrder, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(tabDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnEdit, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnRemove, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btneditNote, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnView, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 256, Short.MAX_VALUE)
                .addGap(2, 2, 2)
                .addGroup(tabDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtSum, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblSum, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(27, 27, 27)
                .addGroup(tabDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnUpdateDetail, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCancel1, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnReset, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCreat, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(21, 21, 21))
        );

        tab.addTab("  Detail  ", tabDetail);

        tabOrder.setBackground(new java.awt.Color(255, 255, 204));

        lblCodeOrder.setFont(new java.awt.Font("Arial Narrow", 0, 18)); // NOI18N
        lblCodeOrder.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblCodeOrder.setText("Code Order:");

        lblAddressOrder.setFont(new java.awt.Font("Arial Narrow", 0, 18)); // NOI18N
        lblAddressOrder.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblAddressOrder.setText("Address Order:");

        txtCodeOrder.setFont(new java.awt.Font("Arial", 0, 16)); // NOI18N
        txtCodeOrder.setHorizontalAlignment(javax.swing.JTextField.LEFT);

        txtAddressOrder.setFont(new java.awt.Font("Arial", 0, 16)); // NOI18N
        txtAddressOrder.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        txtAddressOrder.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtAddressOrderActionPerformed(evt);
            }
        });

        lblCodeEmployee.setFont(new java.awt.Font("Arial Narrow", 0, 18)); // NOI18N
        lblCodeEmployee.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblCodeEmployee.setText("Code Customer:");

        lblDateOrder.setFont(new java.awt.Font("Arial Narrow", 0, 18)); // NOI18N
        lblDateOrder.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblDateOrder.setText("Date Order:");

        lblSearchCode.setFont(new java.awt.Font("Arial Narrow", 0, 18)); // NOI18N
        lblSearchCode.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblSearchCode.setText("Find");

        txtSearchCode.setFont(new java.awt.Font("Arial", 0, 16)); // NOI18N
        txtSearchCode.setHorizontalAlignment(javax.swing.JTextField.LEFT);

        btnSearchOrder.setFont(new java.awt.Font("Arial Narrow", 0, 18)); // NOI18N
        btnSearchOrder.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/icon/search.png"))); // NOI18N
        btnSearchOrder.setText("Search");
        btnSearchOrder.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSearchOrderActionPerformed(evt);
            }
        });

        tblOrder.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        tblOrder.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Code Order", "Name Customer", "Address Order", "Date Order", "Status", "Code Employee"
            }
        ));
        tblOrder.setRowHeight(20);
        tblOrder.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                tblOrderMouseReleased(evt);
            }
        });
        jScrollPane1.setViewportView(tblOrder);

        btnInsertOrder.setFont(new java.awt.Font("Arial Narrow", 0, 18)); // NOI18N
        btnInsertOrder.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/icon/insert.png"))); // NOI18N
        btnInsertOrder.setText("Insert");
        btnInsertOrder.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnInsertOrderActionPerformed(evt);
            }
        });

        btnDetailOrder.setFont(new java.awt.Font("Arial Narrow", 0, 18)); // NOI18N
        btnDetailOrder.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/icon/Detail.png"))); // NOI18N
        btnDetailOrder.setText("Detail");
        btnDetailOrder.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDetailOrderActionPerformed(evt);
            }
        });

        btnDeleteOrder.setFont(new java.awt.Font("Arial Narrow", 0, 18)); // NOI18N
        btnDeleteOrder.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/icon/delete.png"))); // NOI18N
        btnDeleteOrder.setText("Delete");
        btnDeleteOrder.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteOrderActionPerformed(evt);
            }
        });

        btnCancelOrder.setFont(new java.awt.Font("Arial Narrow", 0, 18)); // NOI18N
        btnCancelOrder.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/icon/Cancel_1.png"))); // NOI18N
        btnCancelOrder.setText("Cancel");
        btnCancelOrder.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelOrderActionPerformed(evt);
            }
        });

        lblDateOrder1.setFont(new java.awt.Font("Arial Narrow", 0, 18)); // NOI18N
        lblDateOrder1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblDateOrder1.setText("Status:");

        btnUpdatestatus.setFont(new java.awt.Font("Arial Narrow", 0, 18)); // NOI18N
        btnUpdatestatus.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/icon/Update.png"))); // NOI18N
        btnUpdatestatus.setText("Update Status");
        btnUpdatestatus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdatestatusActionPerformed(evt);
            }
        });

        chkstatus.setFont(new java.awt.Font("Arial Narrow", 0, 18)); // NOI18N
        chkstatus.setText("Sussec");

        DateOrder.setDateFormatString("dd/MM/yyyy");
        DateOrder.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N

        lblDatePay.setFont(new java.awt.Font("Arial Narrow", 0, 18)); // NOI18N
        lblDatePay.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblDatePay.setText("Date of Payment:");

        DatePay.setDateFormatString("dd/MM/yyyy");
        DatePay.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N

        lblAddressOrder1.setFont(new java.awt.Font("Arial Narrow", 0, 18)); // NOI18N
        lblAddressOrder1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblAddressOrder1.setText("Formality:");

        cboCustomer.setFont(new java.awt.Font("Arial", 0, 16)); // NOI18N

        cbFormality.setFont(new java.awt.Font("Arial", 0, 16)); // NOI18N
        cbFormality.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Vận chuyển", "Tại cửa hàng" }));

        btnNewCustomer.setFont(new java.awt.Font("Arial Narrow", 0, 18)); // NOI18N
        btnNewCustomer.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/icon/New.png"))); // NOI18N
        btnNewCustomer.setText("New");
        btnNewCustomer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNewCustomerActionPerformed(evt);
            }
        });

        btnResetOrder.setFont(new java.awt.Font("Arial Narrow", 0, 18)); // NOI18N
        btnResetOrder.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/icon/Reset.png"))); // NOI18N
        btnResetOrder.setText("Reset");
        btnResetOrder.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnResetOrderActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout tabOrderLayout = new javax.swing.GroupLayout(tabOrder);
        tabOrder.setLayout(tabOrderLayout);
        tabOrderLayout.setHorizontalGroup(
            tabOrderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, tabOrderLayout.createSequentialGroup()
                .addGap(20, 27, Short.MAX_VALUE)
                .addGroup(tabOrderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(tabOrderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, tabOrderLayout.createSequentialGroup()
                            .addGroup(tabOrderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addGroup(tabOrderLayout.createSequentialGroup()
                                    .addComponent(lblAddressOrder, javax.swing.GroupLayout.DEFAULT_SIZE, 101, Short.MAX_VALUE)
                                    .addGap(31, 31, 31)
                                    .addComponent(txtAddressOrder, javax.swing.GroupLayout.PREFERRED_SIZE, 240, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(24, 24, 24)
                                    .addComponent(lblDateOrder, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(tabOrderLayout.createSequentialGroup()
                                    .addGroup(tabOrderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, tabOrderLayout.createSequentialGroup()
                                            .addGroup(tabOrderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                .addComponent(lblCodeOrder, javax.swing.GroupLayout.DEFAULT_SIZE, 122, Short.MAX_VALUE)
                                                .addComponent(lblSearchCode, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED))
                                        .addGroup(tabOrderLayout.createSequentialGroup()
                                            .addComponent(lblAddressOrder1, javax.swing.GroupLayout.DEFAULT_SIZE, 101, Short.MAX_VALUE)
                                            .addGap(31, 31, 31)))
                                    .addGroup(tabOrderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addGroup(tabOrderLayout.createSequentialGroup()
                                            .addComponent(txtCodeOrder, javax.swing.GroupLayout.PREFERRED_SIZE, 240, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGap(24, 24, 24)
                                            .addComponent(lblCodeEmployee, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, tabOrderLayout.createSequentialGroup()
                                            .addGroup(tabOrderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                .addComponent(txtSearchCode, javax.swing.GroupLayout.DEFAULT_SIZE, 240, Short.MAX_VALUE)
                                                .addComponent(cbFormality, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addGroup(tabOrderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addComponent(lblDatePay, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addComponent(btnSearchOrder))))))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addGroup(tabOrderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addGroup(tabOrderLayout.createSequentialGroup()
                                    .addComponent(lblDateOrder1, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addComponent(chkstatus, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addComponent(DateOrder, javax.swing.GroupLayout.DEFAULT_SIZE, 240, Short.MAX_VALUE)
                                .addComponent(DatePay, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(tabOrderLayout.createSequentialGroup()
                                    .addComponent(cboCustomer, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addComponent(btnNewCustomer, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                    .addGroup(tabOrderLayout.createSequentialGroup()
                        .addComponent(btnDetailOrder)
                        .addGap(18, 18, 18)
                        .addComponent(btnInsertOrder)
                        .addGap(18, 18, 18)
                        .addComponent(btnUpdatestatus)
                        .addGap(18, 18, 18)
                        .addComponent(btnDeleteOrder)
                        .addGap(18, 18, 18)
                        .addComponent(btnResetOrder)
                        .addGap(18, 18, 18)
                        .addComponent(btnCancelOrder)))
                .addGap(17, 17, 17))
        );
        tabOrderLayout.setVerticalGroup(
            tabOrderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(tabOrderLayout.createSequentialGroup()
                .addGap(8, 8, 8)
                .addGroup(tabOrderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(tabOrderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(lblCodeOrder, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtCodeOrder, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(tabOrderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(cboCustomer, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnNewCustomer, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(lblCodeEmployee, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(23, 23, 23)
                .addGroup(tabOrderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblAddressOrder)
                    .addComponent(txtAddressOrder, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblDateOrder)
                    .addComponent(DateOrder, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(tabOrderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(DatePay, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblAddressOrder1, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(tabOrderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(lblDatePay, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
                        .addComponent(cbFormality, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(11, 11, 11)
                .addGroup(tabOrderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtSearchCode, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSearchOrder, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblSearchCode, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblDateOrder1, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(chkstatus, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 218, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addGroup(tabOrderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnInsertOrder, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnDetailOrder, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnDeleteOrder, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCancelOrder, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnUpdatestatus, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnResetOrder, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(22, 22, 22))
        );

        tab.addTab("  Order  ", tabOrder);

        tabSearch.setBackground(new java.awt.Color(204, 255, 255));

        tblSearchPhone.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        tblSearchPhone.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Code Phone", "Name Phone", "Quantity In Shop", "Price"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblSearchPhone.setRowHeight(20);
        tblSearchPhone.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                tblSearchPhoneMouseReleased(evt);
            }
        });
        jScrollPane3.setViewportView(tblSearchPhone);

        lblChooseProduct1.setFont(new java.awt.Font("Arial Narrow", 0, 18)); // NOI18N
        lblChooseProduct1.setText("Choose Product");

        txtChooseProduct1.setFont(new java.awt.Font("Arial Narrow", 0, 18)); // NOI18N

        btnSearch2.setFont(new java.awt.Font("Arial Narrow", 0, 18)); // NOI18N
        btnSearch2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/icon/Search_1.png"))); // NOI18N
        btnSearch2.setText("Search");
        btnSearch2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSearch2ActionPerformed(evt);
            }
        });

        lblCodePhone.setFont(new java.awt.Font("Arial Narrow", 0, 18)); // NOI18N
        lblCodePhone.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblCodePhone.setText("Code Phone");

        lblNamePhone.setFont(new java.awt.Font("Arial Narrow", 0, 18)); // NOI18N
        lblNamePhone.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblNamePhone.setText("Name Phone");

        lblQuantityInShop.setFont(new java.awt.Font("Arial Narrow", 0, 18)); // NOI18N
        lblQuantityInShop.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblQuantityInShop.setText("Quantity In Shop");

        lblPrice.setFont(new java.awt.Font("Arial Narrow", 0, 18)); // NOI18N
        lblPrice.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblPrice.setText("Price");

        txtCodePhone.setFont(new java.awt.Font("Arial Narrow", 0, 18)); // NOI18N

        txtNamePhone.setFont(new java.awt.Font("Arial Narrow", 0, 18)); // NOI18N

        txtQuantityInShop.setFont(new java.awt.Font("Arial Narrow", 0, 18)); // NOI18N

        txtPrice.setFont(new java.awt.Font("Arial Narrow", 0, 18)); // NOI18N

        btnChoose.setFont(new java.awt.Font("Arial Narrow", 0, 18)); // NOI18N
        btnChoose.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/icon/Choose.png"))); // NOI18N
        btnChoose.setText("Choose");
        btnChoose.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnChooseActionPerformed(evt);
            }
        });

        btnCancel2.setFont(new java.awt.Font("Arial Narrow", 0, 18)); // NOI18N
        btnCancel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/icon/back01.png"))); // NOI18N
        btnCancel2.setText("Back");

        lblImage.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        javax.swing.GroupLayout tabSearchLayout = new javax.swing.GroupLayout(tabSearch);
        tabSearch.setLayout(tabSearchLayout);
        tabSearchLayout.setHorizontalGroup(
            tabSearchLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(tabSearchLayout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addGroup(tabSearchLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(tabSearchLayout.createSequentialGroup()
                        .addGap(58, 58, 58)
                        .addComponent(lblImage, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(93, 93, 93)
                        .addGroup(tabSearchLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(tabSearchLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(lblNamePhone, javax.swing.GroupLayout.DEFAULT_SIZE, 142, Short.MAX_VALUE)
                                .addComponent(lblCodePhone, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(lblQuantityInShop, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addComponent(lblPrice, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblChooseProduct1))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(tabSearchLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(tabSearchLayout.createSequentialGroup()
                                .addGroup(tabSearchLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtPrice, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(txtQuantityInShop, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(txtNamePhone, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(txtCodePhone, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(40, 40, 40))
                            .addGroup(tabSearchLayout.createSequentialGroup()
                                .addComponent(txtChooseProduct1, javax.swing.GroupLayout.PREFERRED_SIZE, 198, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btnSearch2)
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                    .addGroup(tabSearchLayout.createSequentialGroup()
                        .addGroup(tabSearchLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(tabSearchLayout.createSequentialGroup()
                                .addComponent(btnChoose)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btnCancel2))
                            .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 761, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap(31, Short.MAX_VALUE))))
        );
        tabSearchLayout.setVerticalGroup(
            tabSearchLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(tabSearchLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(tabSearchLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(tabSearchLayout.createSequentialGroup()
                        .addGroup(tabSearchLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblChooseProduct1)
                            .addComponent(txtChooseProduct1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnSearch2, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(tabSearchLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblCodePhone, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtCodePhone, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(tabSearchLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(tabSearchLayout.createSequentialGroup()
                                .addComponent(lblNamePhone, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(lblQuantityInShop, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18))
                            .addGroup(tabSearchLayout.createSequentialGroup()
                                .addComponent(txtNamePhone, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, Short.MAX_VALUE)
                                .addComponent(txtQuantityInShop, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(20, 20, 20)))
                        .addGroup(tabSearchLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtPrice, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblPrice, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, tabSearchLayout.createSequentialGroup()
                        .addComponent(lblImage, javax.swing.GroupLayout.PREFERRED_SIZE, 179, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(9, 9, 9)))
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 223, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(tabSearchLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnCancel2, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnChoose, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        tab.addTab("  SearchPhone  ", tabSearch);

        lblManageOrder.setFont(new java.awt.Font("Arial Narrow", 1, 24)); // NOI18N
        lblManageOrder.setForeground(new java.awt.Color(255, 102, 102));
        lblManageOrder.setText("Manage Order");

        javax.swing.GroupLayout PanelNenLayout = new javax.swing.GroupLayout(PanelNen);
        PanelNen.setLayout(PanelNenLayout);
        PanelNenLayout.setHorizontalGroup(
            PanelNenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelNenLayout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(lblManageOrder)
                .addContainerGap(695, Short.MAX_VALUE))
            .addGroup(PanelNenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(PanelNenLayout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(tab, javax.swing.GroupLayout.PREFERRED_SIZE, 831, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
        PanelNenLayout.setVerticalGroup(
            PanelNenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelNenLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblManageOrder)
                .addContainerGap(570, Short.MAX_VALUE))
            .addGroup(PanelNenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(PanelNenLayout.createSequentialGroup()
                    .addGap(49, 49, 49)
                    .addComponent(tab, javax.swing.GroupLayout.PREFERRED_SIZE, 550, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(PanelNen, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(PanelNen, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnResetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnResetActionPerformed
        // TODO add your handling code here:
        try {
            ResetOrderDetail();
        } catch (Exception e) {
            Dialog.MessageError(this, "Xảy ra lỗi khi làm mới chi tiết đơn hàng");
        }

    }//GEN-LAST:event_btnResetActionPerformed

    private void btnChooseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnChooseActionPerformed
        // TODO add your handling code here:
        if (txtCodePhone.getText().trim().equals("")) {
            Dialog.MessageWarning(this, "Vui lòng chọn sản phẩm");
        } else {
            try {
                if (this.rowBasic < 0) {
                    if (checkDetailOrder(txtCodePhone.getText().trim())) {
//                                    Dialog.Message(null, "Chưa có lỗi");
                        CurrentOrder.Order = new ArrayList<>();
                        CurrentOrder.Order.add(new OrderDetail(0, txtCodePhone.getText().trim(), daoPhone.SelectID(txtCodePhone.getText().trim()).getPrice(), 1, ""));
                        Dialog.MessageInformation(this, "Đã thêm sản phẩm vào giỏ hàng");
                    } else {
                        Dialog.MessageWarning(this, "Đã có sản phẩm này");
                    }
                } else {
                    if (checkDetailOrder(txtCodePhone.getText().trim())) {
//                                    Dialog.Message(null, "Chưa có lỗi");
                        CurrentOrder.Order = new ArrayList<>();
                        OrderDetail od = new OrderDetail(Integer.parseInt(String.valueOf(tblOrder.getValueAt(this.rowBasic, 0))), txtCodePhone.getText().trim(), daoPhone.SelectID(txtCodePhone.getText().trim()).getPrice(), 1, "");
                        CurrentOrder.Order.add(od);
                        setFormDeatail(od);
                        Dialog.MessageInformation(this, "Đã thêm sản phẩm vào giỏ hàng");
                    } else {
                        Dialog.MessageWarning(this, "Đã có sản phẩm này");
                    }
                }
                filltoTableOrderDetail();
                UpdateStatus();
            } catch (Exception e) {
                Dialog.MessageError(this, "Lỗi khi thêm sản phẩm + " + e);
            }
        }
    }//GEN-LAST:event_btnChooseActionPerformed

    private void btnSearch2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSearch2ActionPerformed
        // TODO add your handling code here:

        String x = txtChooseProduct1.getText().trim();
        try {
            InformationPhone t = daoPhone.SelectID(x);
            if (t == null) {
                Dialog.MessageInformation(this, "No products");
                txtCodePhone.setText("");
                txtNamePhone.setText("");
                txtQuantityInShop.setText("");
                txtPrice.setText("");
                lblImage.setText("");
                lblImage.setIcon(null);
            } else {
                showSearchPhone(t);
//                btnSearch.setEnabled(false);
            }
            filltoTableSearch();
        } catch (Exception e) {
            Dialog.MessageError(null, e);
        }
    }//GEN-LAST:event_btnSearch2ActionPerformed

    private void btnSearch1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSearch1ActionPerformed
        // TODO add your handling code here:
        String x = txtChooseProduct.getText().trim();
        try {
            InformationPhone t = daoPhone.SelectID(x);
            if (t == null) {
                Dialog.MessageInformation(this, "No products");
            } else {
                showSearchPhone(t);
                tab.setSelectedIndex(2);
//                btnSearch.setEnabled(false);
            }
        } catch (Exception e) {
            Dialog.MessageError(null, e);
        }
    }//GEN-LAST:event_btnSearch1ActionPerformed

    private void tblOrderMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblOrderMouseReleased
        // TODO add your handling code here:
        if (evt.getClickCount() == 2) {
            this.rowBasic = tblOrder.getSelectedRow();
            Order order = Odao.SelectID(String.valueOf(tblOrder.getValueAt(rowBasic, 0)));
//            Dialog.Message(null, order.getCodeCustomer());
            setFormOrder(order);
            CurrentOrder.Order = getOrderDetail(String.valueOf(tblOrder.getValueAt(rowBasic, 0)));
            if (CurrentOrder.Order != null) {
                setFormDeatail(CurrentOrder.Order.get(0));
            }
            filltoTableOrderDetail();
            UpdateStatus();
//            tab.setSelectedIndex(0);
        }
    }//GEN-LAST:event_tblOrderMouseReleased

    private void btnInsertOrderActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnInsertOrderActionPerformed
        // TODO add your handling code here:
        try {
            Insert();
        } catch (Exception e) {
            Dialog.MessageError(null, e);
        }

    }//GEN-LAST:event_btnInsertOrderActionPerformed

    private void btnUpdatestatusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdatestatusActionPerformed
        // TODO add your handling code here:
        try {
            Update();
        } catch (Exception e) {
            Dialog.MessageError(null, e);
        }
    }//GEN-LAST:event_btnUpdatestatusActionPerformed

    private void btnDeleteOrderActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteOrderActionPerformed
        // TODO add your handling code here:
        try {
            Delete();
        } catch (Exception e) {
            Dialog.MessageError(null, e);
        }
    }//GEN-LAST:event_btnDeleteOrderActionPerformed

    private void btnNewCustomerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNewCustomerActionPerformed
        // TODO add your handling code here:
        try {
            new ManageCustomer(null, true).setVisible(true);
            fillCbCustomer();
        } catch (Exception e) {
            Dialog.MessageError(null, e);
        }
    }//GEN-LAST:event_btnNewCustomerActionPerformed

    private void btnSearchOrderActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSearchOrderActionPerformed
        // TODO add your handling code here:
        try {
            String sc = "0";
            if (!txtSearchCode.getText().equals("")) {
                sc = txtSearchCode.getText();
            }
            Order od = Odao.SelectID(sc);
            if (od != null) {
                filltoTableOrder(od);
            } else {
                Dialog.MessageInformation(this, "Không có đơn hàng này");
            }
        } catch (Exception e) {
            Dialog.MessageError(this, e);
        }
    }//GEN-LAST:event_btnSearchOrderActionPerformed

    private void btnViewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnViewActionPerformed
        // TODO add your handling code here:
        viewAll();
    }//GEN-LAST:event_btnViewActionPerformed

    private void btnDetailOrderActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDetailOrderActionPerformed
        // TODO add your handling code here:
        this.rowBasic = tblOrder.getSelectedRow();
        CurrentOrder.Order = getOrderDetail(String.valueOf(tblOrder.getValueAt(rowBasic, 0)));
        if (CurrentOrder.Order != null) {
            setFormDeatail(CurrentOrder.Order.get(0));
        }
        filltoTableOrderDetail();
        tab.setSelectedIndex(0);
    }//GEN-LAST:event_btnDetailOrderActionPerformed

    private void btnCancelOrderActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelOrderActionPerformed
        // TODO add your handling code here:
        this.dispose();
    }//GEN-LAST:event_btnCancelOrderActionPerformed

    private void btnCancel1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancel1ActionPerformed
        // TODO add your handling code here:
        this.tab.setSelectedIndex(1);
    }//GEN-LAST:event_btnCancel1ActionPerformed

    private void btnResetOrderActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnResetOrderActionPerformed
        // TODO add your handling code here:
        try {
            ResetOrder();
        } catch (Exception e) {
            Dialog.MessageError(null, "Lỗi khi làm mới");
        }
    }//GEN-LAST:event_btnResetOrderActionPerformed

    private void btnCreatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCreatActionPerformed
        // TODO add your handling code here:
        try {
            tab.setSelectedIndex(1);
        } catch (Exception e) {
            Dialog.MessageError(this, "Không thể tạo đơn hàng");
        }
    }//GEN-LAST:event_btnCreatActionPerformed

    private void btnUpdateDetailActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateDetailActionPerformed
        // TODO add your handling code here:
        try {
            UpdateDetail();
        } catch (Exception e) {
            Dialog.MessageError(null, "Lỗi khi update chi tiết đơn hàng");
        }
    }//GEN-LAST:event_btnUpdateDetailActionPerformed

    private void btnRemoveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRemoveActionPerformed
//        Dialog.Message(null, rowDetail);
        if (this.rowDetail >= 0) {
            DefaultTableModel model = (DefaultTableModel) tblDetail.getModel();
            model.removeRow(this.rowDetail);
        }
    }//GEN-LAST:event_btnRemoveActionPerformed

    private void tblDetailMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblDetailMouseClicked
        // TODO add your handling code here:
        this.rowDetail = tblDetail.getSelectedRow();
    }//GEN-LAST:event_tblDetailMouseClicked

    private void tblDetailPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_tblDetailPropertyChange
        // TODO add your handling code here:
        SumPrice();
    }//GEN-LAST:event_tblDetailPropertyChange

    private void tblDetailVetoableChange(java.beans.PropertyChangeEvent evt)throws java.beans.PropertyVetoException {//GEN-FIRST:event_tblDetailVetoableChange
        // TODO add your handling code here:
        SumPrice();
    }//GEN-LAST:event_tblDetailVetoableChange

    private void tblDetailHierarchyChanged(java.awt.event.HierarchyEvent evt) {//GEN-FIRST:event_tblDetailHierarchyChanged
        // TODO add your handling code here:
        SumPrice();
    }//GEN-LAST:event_tblDetailHierarchyChanged

    private void tblDetailKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tblDetailKeyReleased
        // TODO add your handling code here:
        SumPrice();
    }//GEN-LAST:event_tblDetailKeyReleased

    private void tblDetailKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tblDetailKeyPressed
        // TODO add your handling code here:
        SumPrice();
    }//GEN-LAST:event_tblDetailKeyPressed

    private void txtChooseProductActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtChooseProductActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtChooseProductActionPerformed

    private void tblSearchPhoneMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblSearchPhoneMouseReleased
        // TODO add your handling code here:
        if (evt.getClickCount() == 2) {
            String codephone = (String) tblSearchPhone.getValueAt(tblSearchPhone.getSelectedRow(), 0);
            showSearchPhone(daoPhone.SelectID(codephone));
//            tab.setSelectedIndex(0);
        }
    }//GEN-LAST:event_tblSearchPhoneMouseReleased

    private void txtAddressOrderActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtAddressOrderActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtAddressOrderActionPerformed

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
            java.util.logging.Logger.getLogger(ManageOrder.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ManageOrder.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ManageOrder.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ManageOrder.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                ManageOrder dialog = new ManageOrder(new javax.swing.JFrame(), true);
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
    private com.toedter.calendar.JDateChooser DateOrder;
    private com.toedter.calendar.JDateChooser DatePay;
    private javax.swing.JPanel PanelNen;
    private javax.swing.JButton btnCancel1;
    private javax.swing.JButton btnCancel2;
    private javax.swing.JButton btnCancelOrder;
    private javax.swing.JButton btnChoose;
    private javax.swing.JButton btnCreat;
    private javax.swing.JButton btnDeleteOrder;
    private javax.swing.JButton btnDetailOrder;
    private javax.swing.JButton btnEdit;
    private javax.swing.JButton btnInsertOrder;
    private javax.swing.JButton btnNewCustomer;
    private javax.swing.JButton btnRemove;
    private javax.swing.JButton btnReset;
    private javax.swing.JButton btnResetOrder;
    private javax.swing.JButton btnSearch1;
    private javax.swing.JButton btnSearch2;
    private javax.swing.JButton btnSearchOrder;
    private javax.swing.JButton btnUpdateDetail;
    private javax.swing.JButton btnUpdatestatus;
    private javax.swing.JButton btnView;
    private javax.swing.JButton btneditNote;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JComboBox<String> cbFormality;
    private javax.swing.JComboBox<String> cboCustomer;
    private javax.swing.JCheckBox chkstatus;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JLabel lblAddressOrder;
    private javax.swing.JLabel lblAddressOrder1;
    private javax.swing.JLabel lblChooseProduct;
    private javax.swing.JLabel lblChooseProduct1;
    private javax.swing.JLabel lblCodeEmployee;
    private javax.swing.JLabel lblCodeOrder;
    private javax.swing.JLabel lblCodePhone;
    private javax.swing.JLabel lblDateOrder;
    private javax.swing.JLabel lblDateOrder1;
    private javax.swing.JLabel lblDatePay;
    private javax.swing.JLabel lblImage;
    private javax.swing.JLabel lblManageOrder;
    private javax.swing.JLabel lblNamePhone;
    private javax.swing.JLabel lblOrder;
    private javax.swing.JLabel lblPrice;
    private javax.swing.JLabel lblQuantityInShop;
    private javax.swing.JLabel lblSearchCode;
    private javax.swing.JLabel lblSum;
    private javax.swing.JTabbedPane tab;
    private javax.swing.JPanel tabDetail;
    private javax.swing.JPanel tabOrder;
    private javax.swing.JPanel tabSearch;
    private javax.swing.JTable tblDetail;
    private javax.swing.JTable tblOrder;
    private javax.swing.JTable tblSearchPhone;
    private javax.swing.JTextField txtAddressOrder;
    private javax.swing.JTextField txtChooseProduct;
    private javax.swing.JTextField txtChooseProduct1;
    private javax.swing.JTextField txtCodeOrder;
    private javax.swing.JLabel txtCodeOrderDetail;
    private javax.swing.JLabel txtCodePhone;
    private javax.swing.JLabel txtNamePhone;
    private javax.swing.JLabel txtPrice;
    private javax.swing.JLabel txtQuantityInShop;
    private javax.swing.JTextField txtSearchCode;
    private javax.swing.JLabel txtSum;
    // End of variables declaration//GEN-END:variables
}
