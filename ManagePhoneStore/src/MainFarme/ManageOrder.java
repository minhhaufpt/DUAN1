/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MainFarme;

import javax.swing.ImageIcon;
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
import java.awt.Color;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
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
    public int rowHistory;
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
        fillCbCustomer();
        filltoTableHistory();
        filltoTableOrderDetail();
        DateOrder.setDate(new Date());
        this.rowDetail = -1;
        this.UpdateStatus();
        SumPrice();
    }

    public void fillCbCustomer() {
        ArrayList<InformationCustomer> infor = Cusdao.SelectAll();
        DefaultComboBoxModel model = (DefaultComboBoxModel) cboCustomer.getModel();
        model.removeAllElements();
        for (InformationCustomer in : infor) {
            model.addElement(in);
        }
    }

    public void filltoTableOrderDetail() {
        DefaultTableModel model = (DefaultTableModel) tblDetail.getModel();
        model.setRowCount(0);
        ArrayList<InformationPhone> lt = new ArrayList<InformationPhone>();
        try {
            if (CurrentOrder.Order == null) {
                txtCodeOrder.setText("No orders");
            } else {
//                txtCodeOrder.setText(String.valueOf(CurrentOrder.Order.get(0).getCodeOrder()));
                for (OrderDetail orderDetail : CurrentOrder.Order) {
                    if (orderDetail.getCodeOrder() == 0) {
                        Object[] rowDetail = {"Not created", daoPhone.SelectID(orderDetail.getCodePhone()).getNamePhone(), XMoney.toString(orderDetail.getPrice(), "VN"), orderDetail.getQuantity(), orderDetail.getNote(), orderDetail.getCodePhone()};
                        model.addRow(rowDetail);
                    } else {
                        Object[] rowDetail = {orderDetail.getCodeOrder(), daoPhone.SelectID(orderDetail.getCodePhone()).getNamePhone(), XMoney.toString(orderDetail.getPrice(), "VN"), orderDetail.getQuantity(), orderDetail.getNote(), orderDetail.getCodePhone()};
                        model.addRow(rowDetail);
                    }
                }
            }
            SumPrice();
        } catch (Exception e) {
            Dialog.Message(this, "Unable to query data !");
        }
    }

    public void filltoTableHistory() {
        DefaultTableModel model = (DefaultTableModel) tblHistory.getModel();
        model.setRowCount(0);
        try {
            ArrayList<Order> od = Odao.SelectAll();
            for (Order order : od) {
                Object[] rowDetail = {order.getCodeOrder(), Cusdao.SelectID(String.valueOf(order.getCodeCustomer())).getNameCustomer(), XDate.toString(order.getOrderDate(), "dd/MM/yyyy"), order.getAddressOrder(), order.isStatus(), order.getCodeEmployye()};
                model.addRow(rowDetail);
            }
        } catch (Exception e) {
            Dialog.MessageError(this, "Unable to query data !");
        }
    }

    public void filltoTableHistory(ArrayList<Order> od) {
        DefaultTableModel model = (DefaultTableModel) tblHistory.getModel();
        model.setRowCount(0);
        try {
            for (Order order : od) {
                Object[] rowDetail = {order.getCodeOrder(), order.getCodeCustomer(), XDate.toString(order.getOrderDate(), "dd/MM/yyyy"), order.getAddressOrder(), order.isStatus(), order.getCodeEmployye()};
                model.addRow(rowDetail);
            }
        } catch (Exception e) {
            Dialog.MessageError(this, "Unable to query data !");
        }
    }

    public void setFormOrder(Order od) {
        txtCodeOrder.setText(String.valueOf(od.getCodeOrder()));
        DefaultComboBoxModel model = (DefaultComboBoxModel) cboCustomer.getModel();
        model.setSelectedItem(Cusdao.SelectID(String.valueOf(od.getCodeCustomer())));
        txtAddressOrder.setText(od.getAddressOrder());
        DateOrder.setDate(od.getOrderDate());
        if (od.isStatus() == true) {
            chkstatus.setSelected(true);
            DatePay.setDate(od.getDateOfPayment());
        } else {
            chkstatus.setSelected(false);
            DatePay.setDate(null);
        }
    }

    public void setFormDetail() {
        if (this.rowDetail != -1) {
            txtQuantity.setText(String.valueOf(tblDetail.getValueAt(this.rowDetail, 3)));
            txtNote.setText(String.valueOf(tblDetail.getValueAt(this.rowDetail, 4)));
        }
    }

    ArrayList<OrderDetail> getFormDetail() {
        ArrayList<OrderDetail> lst = null;
        if (CurrentOrder.Order != null) {
            for (int i = 0; i < tblDetail.getRowCount(); i++) {
                OrderDetail od = new OrderDetail();
                od.setCodeOrder(Integer.parseInt(txtCodeOrder.getText().trim()));
                od.setCodePhone(String.valueOf(tblDetail.getValueAt(i, 0)));
                od.setQuantity(Integer.parseInt(String.valueOf(tblDetail.getValueAt(i, 3))));
                od.setNote(String.valueOf(tblDetail.getValueAt(i, 4)).equals("") ? "No Note" : String.valueOf(tblDetail.getValueAt(i, 4)));
                od.setPrice(daoPhone.SelectID(od.getCodePhone()).getPrice());
                lst.add(od);
            }
        } else {
            Dialog.MessageWarning(this, "No products found");
            return null;
        }
        return lst;
    }

    public Order getFormOrder() {
        Order od = new Order();
        if (txtCodeOrder.getText().equalsIgnoreCase("")) {
            od.setCodeOrder(0);
        } else {
            try {
                int k = Integer.parseInt(txtCodeOrder.getText());
                od.setCodeOrder(k);
            } catch (NumberFormatException ex) {
                od.setCodeOrder(0);
            }
        }
//        od.setCodeOrder(0);
        od.setAddressOrder(txtAddressOrder.getText());
        try {
            od.setCodeEmployye(Login.user.getUserName());
        } catch (Exception e) {
            Dialog.MessageError(this, "Not logged in yet");
        }
        //       od.setCodeEmployye("nhanvienkithuat");
        od.setOrderDate(DateOrder.getDate());
        od.setFormality(String.valueOf(cbFormality.getSelectedItem()));
        od.setDateOfPayment(DatePay.getDate());
        od.setStatus(chkstatus.isSelected() ? true : false);
        od.setCodeCustomer(Integer.parseInt(String.valueOf(cboCustomer.getSelectedItem())));
        return od;
    }

    //    public ArrayList<OrderDetail> getOrderDetail(String codeorder) {
//        ArrayList<OrderDetail> detail = ODdao.SelectByIDOrder(codeorder);
//        return detail;
//    }
//    public void viewAll() {
//        DefaultTableModel model = (DefaultTableModel) tblDetail.getModel();
//        model.setRowCount(0);
//        ArrayList<InformationPhone> lt = new ArrayList<InformationPhone>();
//        try {
//            txtCodeOrderDetail.setText("");
//            ArrayList<OrderDetail> dts = ODdao.SelectAll();
//            for (OrderDetail orderDetail : dts) {
//                Object[] rowDetail = {orderDetail.getCodePhone(), daoPhone.SelectID(orderDetail.getCodePhone()).getNamePhone(), XMoney.toString(orderDetail.getPrice(), "VN"), orderDetail.getQuantity(), orderDetail.getNote()};
//                model.addRow(rowDetail);
//            }
//        } catch (Exception e) {
//            Dialog.Message(this, "Unable to query data !");
//        }
//    }
//    public void filltoTableSearch() {
//        DefaultTableModel model = (DefaultTableModel) tblSearchPhone.getModel();
//        model.setRowCount(0);
//        try {
//            ArrayList<InformationPhone> od = new ArrayList<InformationPhone>();
//            od = daoPhone.SelectAll();
//            for (InformationPhone phone : od) {
//                Object[] rowDetail = {phone.getCodePhone(), phone.getNamePhone(), phone.getQuantity(), XMoney.toString(phone.getPrice(), "VN")};
//                model.addRow(rowDetail);
//            }
//            tblOrder.setDefaultEditor(Object.class, null);
//        } catch (Exception e) {
//            Dialog.Message(this, "Unable to query data ! " + e);
//        }
//
//    }
//    public void filltoTableOrder(Order order) {
//        DefaultTableModel model = (DefaultTableModel) tblOrder.getModel();
//        model.setRowCount(0);
//        try {
//            Object[] rowDetail = {order.getCodeOrder(), Cusdao.SelectID(String.valueOf(order.getCodeCustomer())).getNameCustomer(), order.getAddressOrder(), XDate.toString(order.getOrderDate(), "dd/MM/yyyy"), order.isStatus() ? "Hoàn thành" : "Chưa hoàn thành", order.getCodeEmployye()};
//            model.addRow(rowDetail);
//            tblOrder.setDefaultEditor(Object.class, null);
//        } catch (Exception e) {
//            Dialog.Message(this, "Unable to query data ! " + e);
//        }
//
//    }
//    public void showSearchPhone(InformationPhone t) {
//        txtCodePhone.setText(t.getCodePhone());
//        txtNamePhone.setText(t.getNamePhone());
//        txtQuantityInShop.setText(String.valueOf(t.getQuantity()));
//        txtPrice.setText(XMoney.toString(t.getPrice(), "VN"));
//        lblImage.setText("");
//        if (t.getImage() != null && !t.getImage().equalsIgnoreCase("No Image")) {
//            lblImage.setIcon(XImage.setResizeable(XImage.ReadImgPro(t.getImage()), 170, 170, 0));
//        } else {
//            lblImage.setIcon(null);
//        }
//    }
//    public void setFormDeatail(OrderDetail od) {
//        txtCodeOrderDetail.setText(String.valueOf(od.getCodeOrder()));
//    }
//    void UpdateDetail() {
//        try {
//            ArrayList<OrderDetail> lt = this.getUpdateDetail();
//            if (lt != null) {
//                ODdao.UpdateALL(lt);
//                this.filltoTableOrder();
////                this.clearForm();
//                Dialog.MessageWarning(this, "Successfully added new!");
//            } else {
//                Dialog.MessageWarning(this, "Không tìm thấy sản phẩm trong đơn hàng");
//            }
//        } catch (Exception ex) {
//            Dialog.MessageError(this, "Phone Code already exists\nNew add failed! === " + ex);
//        }
//    }
    void Insert() {
        Order entity = getFormOrder();
        if (entity != null)
        try {
            if (CurrentOrder.Order != null) {
                entity.setOrderDate(new Date());
                int kq = Odao.Insert(entity, true);
                if (kq != 0) {
                    ODdao.Detele(String.valueOf(kq));
                    for (OrderDetail detail : CurrentOrder.Order) {
                        detail.setCodeOrder(kq);
                        if (detail.getQuantity() == 0) {
                            continue;
                        }
                        ODdao.Insert(detail);
                    }
                }
                CurrentOrder.ClearnOrder();
                Dialog.MessageInformation(this, "Successful order creation");
                this.UpdateStatus();
                filltoTableHistory();
                this.rowHistory = tblHistory.getRowCount() - 1;
                tblHistory.setRowSelectionInterval(this.rowHistory, this.rowHistory);
                tab.setSelectedIndex(1);
            } else {
                Dialog.MessageWarning(this, "No products found");
            }
        } catch (Exception ex) {
         //   Dialog.MessageError(this, "Phone Code already exists\n" + ex);
        }

    }

    void Delete() {
        String codeorder = txtCodeOrder.getText();
        if (Dialog.Confirm(this, "Do you want to delete this product?")) {
            try {
                Odao.Detele(codeorder);
                CurrentOrder.ClearnOrder();
                this.filltoTableOrderDetail();
                this.filltoTableHistory();
                this.ResetOrder();
//                this.clearForm();
                this.UpdateStatus();
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
                ODdao.Detele(String.valueOf(entity.getCodeOrder()));
                for (OrderDetail detail : CurrentOrder.Order) {
                    detail.setCodeOrder(entity.getCodeOrder());
                    ODdao.Insert(detail);
                }
                this.filltoTableOrderDetail();
                this.filltoTableHistory();
                this.clearUpdate();
                this.UpdateStatus();
                Dialog.MessageInformation(this, "Update succefully!");
            } catch (Exception e) {
                Dialog.MessageError(this, "Update failed!");
            }
        }
    }

    void UpdateStatus() {
//        Dialog.Message(null, this.rowBasic);
        boolean status = CurrentOrder.Order == null ? false : true;
        if (CurrentOrder.Order == null) {
            btnCreatOrder.setEnabled(status);
            btnUpdateOrder.setEnabled(status);
            btnDeleteOrder.setEnabled(status);
            btnRemove.setEnabled(status);
//            if (this.rowHistory >= 0) {
//                btnUpdatestatus.setEnabled(!status);
//                btnDeleteOrder.setEnabled(!status);
//            }
        } else if (CurrentOrder.Order.get(0).getCodeOrder() == 0) {
            btnCreatOrder.setEnabled(status);
            btnUpdateOrder.setEnabled(!status);
            btnDeleteOrder.setEnabled(!status);
            if (rowDetail >= 0) {
                btnRemove.setEnabled(true);
            } else {
                btnRemove.setEnabled(false);
            }
        } else {
            btnCreatOrder.setEnabled(!status);
            btnUpdateOrder.setEnabled(status);
            btnDeleteOrder.setEnabled(status);
            btnRemove.setEnabled(status);
            if (rowDetail >= 0) {
                btnRemove.setEnabled(true);
            } else {
                btnRemove.setEnabled(false);
            }
        }
    }

    void ResetOrder() {
        this.rowDetail = -1;
        filltoTableOrderDetail();
        txtCodeOrder.setText("");
        txtAddressOrder.setText("");
        cboCustomer.setSelectedItem(null);
        cbFormality.setSelectedIndex(0);
        DateOrder.setDate(null);
        chkstatus.setSelected(false);
        DatePay.setDate(null);
        chkstatus.setSelected(false);
        txtQuantity.setText("");
        txtNote.setText("");
        txtNote.setEditable(false);
        txtQuantity.setEditable(false);
        UpdateStatus();
    }

    void clearUpdate() {
        this.rowDetail = -1;
        txtQuantity.setText("");
        txtNote.setText("");
        txtNote.setEditable(false);
        txtQuantity.setEditable(false);
    }
//
//    void ResetOrderDetail() {
//        this.rowDetail = -1;
////        CurrentOrder.Order.clear();
//        CurrentOrder.Order = null;
//        txtChooseProduct.setText("");
//        filltoTableOrderDetail();
//        UpdateStatus();
//
//    }

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

    public void updateOrderCurrent() {
        CurrentOrder.Order.clear();
        CurrentOrder.Order = new ArrayList<>();
        for (int i = 0; i < tblDetail.getRowCount(); i++) {
            OrderDetail od = new OrderDetail();
            od.setCodeOrder(Integer.parseInt(String.valueOf(tblDetail.getValueAt(i, 0))));
            od.setCodePhone(String.valueOf(tblDetail.getValueAt(i, 5)));
            od.setNote(String.valueOf(tblDetail.getValueAt(i, 4)));
            od.setPrice(Double.parseDouble(String.valueOf(tblDetail.getValueAt(i, 2))));
            od.setQuantity(Integer.parseInt(String.valueOf(tblDetail.getValueAt(i, 3))));
        }
    }

    public void updateOrder() {
        try {
            this.rowDetail = this.tblDetail.getSelectedRow();
            if (this.rowDetail >= 0) {
                int sl = 0;
                try {
                    sl = Integer.parseInt(String.valueOf(tblDetail.getValueAt(this.rowDetail, 3)));
                } catch (Exception e) {
                    sl = 0;
                }
                String note = String.valueOf(tblDetail.getValueAt(this.rowDetail, 4));
                CurrentOrder.Order.get(this.rowDetail).setQuantity(sl);
                CurrentOrder.Order.get(this.rowDetail).setNote(note);
            }
            SumPrice();
        } catch (Exception e) {
        }
    }

    public void find() {
        String find = txtFind.getText().trim();
        try {
            int check = Integer.parseInt(find);
            ArrayList<Order> list = Odao.SelectFindCode(find);
            if (list == null) {
                Dialog.MessageWarning(this, "Không có bản ghi nào được lưu");
            } else {
                filltoTableHistory(list);
            }
        } catch (NumberFormatException ex) {
            try {
                Date day = XDate.toDate(find, "dd/MM/yyyy");
                ArrayList<Order> list = Odao.SelectFindDate(day);
                if (list == null) {
                    Dialog.MessageWarning(this, "Không có bản ghi nào được lưu");
                } else {
                    filltoTableHistory(list);
                }
            } catch (Exception e) {

            }
        }
    }
    void setTab(int i){
        try{
        tab.setSelectedIndex(i);
        }catch(Exception e){
            
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

        buttonGroup1 = new javax.swing.ButtonGroup();
        PanelNen = new javax.swing.JPanel();
        tab = new javax.swing.JTabbedPane();
        tabOrder = new javax.swing.JPanel();
        lblCodeOrder = new javax.swing.JLabel();
        lblAddressOrder = new javax.swing.JLabel();
        txtCodeOrder = new javax.swing.JTextField();
        txtAddressOrder = new javax.swing.JTextField();
        lblCodeEmployee = new javax.swing.JLabel();
        lblDateOrder = new javax.swing.JLabel();
        btnCreatOrder = new javax.swing.JButton();
        btnDeleteOrder = new javax.swing.JButton();
        btnCancelOrder = new javax.swing.JButton();
        btnUpdateOrder = new javax.swing.JButton();
        DateOrder = new com.toedter.calendar.JDateChooser();
        cboCustomer = new javax.swing.JComboBox<>();
        btnNewCustomer = new javax.swing.JButton();
        btnResetOrder = new javax.swing.JButton();
        truot1 = new javax.swing.JScrollPane();
        tblDetail = new javax.swing.JTable();
        btnRemove = new javax.swing.JButton();
        PanelStatus = new javax.swing.JPanel();
        lblDatePay = new javax.swing.JLabel();
        DatePay = new com.toedter.calendar.JDateChooser();
        cbFormality = new javax.swing.JComboBox<>();
        lblAddressOrder1 = new javax.swing.JLabel();
        lblDateOrder1 = new javax.swing.JLabel();
        chkstatus = new javax.swing.JCheckBox();
        txtQuantity = new javax.swing.JTextField();
        truot2 = new javax.swing.JScrollPane();
        txtNote = new javax.swing.JTextArea();
        txtSum = new javax.swing.JLabel();
        lblSum = new javax.swing.JLabel();
        btnAddProduct = new javax.swing.JButton();
        lbquantity = new javax.swing.JLabel();
        lbnote = new javax.swing.JLabel();
        tabHistory = new javax.swing.JPanel();
        truot3 = new javax.swing.JScrollPane();
        tblHistory = new javax.swing.JTable();
        txtFind = new javax.swing.JTextField();
        btntimkiem = new javax.swing.JButton();
        lblManageOrder = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Order");
        setIconImage(new ImageIcon(getClass().getResource("/Image/Icon/Logo.jpg")).getImage());

        PanelNen.setBackground(new java.awt.Color(204, 255, 204));

        tab.setFont(new java.awt.Font("Arial Narrow", 0, 18)); // NOI18N

        tabOrder.setBackground(new java.awt.Color(255, 255, 204));

        lblCodeOrder.setFont(new java.awt.Font("Arial Narrow", 0, 18)); // NOI18N
        lblCodeOrder.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblCodeOrder.setText("Code Order:");

        lblAddressOrder.setFont(new java.awt.Font("Arial Narrow", 0, 18)); // NOI18N
        lblAddressOrder.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblAddressOrder.setText("Address Order:");

        txtCodeOrder.setEditable(false);
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

        btnCreatOrder.setFont(new java.awt.Font("Arial Narrow", 0, 18)); // NOI18N
        btnCreatOrder.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Icon/CreateOrder.png"))); // NOI18N
        btnCreatOrder.setText("Creat Order");
        btnCreatOrder.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCreatOrderActionPerformed(evt);
            }
        });

        btnDeleteOrder.setFont(new java.awt.Font("Arial Narrow", 0, 18)); // NOI18N
        btnDeleteOrder.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Icon/delete.png"))); // NOI18N
        btnDeleteOrder.setText("Delete Order");
        btnDeleteOrder.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteOrderActionPerformed(evt);
            }
        });

        btnCancelOrder.setFont(new java.awt.Font("Arial Narrow", 0, 18)); // NOI18N
        btnCancelOrder.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Icon/Cancel_1.png"))); // NOI18N
        btnCancelOrder.setText("Cancel");
        btnCancelOrder.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelOrderActionPerformed(evt);
            }
        });

        btnUpdateOrder.setFont(new java.awt.Font("Arial Narrow", 0, 18)); // NOI18N
        btnUpdateOrder.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Icon/Update.png"))); // NOI18N
        btnUpdateOrder.setText("Update Order");
        btnUpdateOrder.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdateOrderActionPerformed(evt);
            }
        });

        DateOrder.setBackground(new java.awt.Color(255, 255, 204));
        DateOrder.setDateFormatString("dd/MM/yyyy");
        DateOrder.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N

        cboCustomer.setFont(new java.awt.Font("Arial", 0, 16)); // NOI18N
        cboCustomer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboCustomerActionPerformed(evt);
            }
        });

        btnNewCustomer.setFont(new java.awt.Font("Arial Narrow", 0, 18)); // NOI18N
        btnNewCustomer.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Icon/New.png"))); // NOI18N
        btnNewCustomer.setText("New");
        btnNewCustomer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNewCustomerActionPerformed(evt);
            }
        });

        btnResetOrder.setFont(new java.awt.Font("Arial Narrow", 0, 18)); // NOI18N
        btnResetOrder.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Icon/Reset.png"))); // NOI18N
        btnResetOrder.setText("Reset");
        btnResetOrder.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnResetOrderActionPerformed(evt);
            }
        });

        tblDetail.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        tblDetail.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Code Order", "Name Phone", "Price", "Quantity Of Buy", "Note", "Code Phone"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
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
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                tblDetailMouseEntered(evt);
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
        truot1.setViewportView(tblDetail);

        btnRemove.setFont(new java.awt.Font("Arial Narrow", 0, 18)); // NOI18N
        btnRemove.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Icon/Delete_1.png"))); // NOI18N
        btnRemove.setText("Remove Product");
        btnRemove.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRemoveActionPerformed(evt);
            }
        });

        PanelStatus.setBackground(new java.awt.Color(204, 204, 255));
        PanelStatus.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "  StatusOrder  ", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Arial Narrow", 1, 18))); // NOI18N

        lblDatePay.setFont(new java.awt.Font("Arial Narrow", 0, 18)); // NOI18N
        lblDatePay.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblDatePay.setText("Date of Payment:");

        DatePay.setBackground(new java.awt.Color(204, 204, 255));
        DatePay.setDateFormatString("dd/MM/yyyy");
        DatePay.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N

        cbFormality.setFont(new java.awt.Font("Arial", 0, 16)); // NOI18N
        cbFormality.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Vận chuyển", "Tại cửa hàng" }));

        lblAddressOrder1.setFont(new java.awt.Font("Arial Narrow", 0, 18)); // NOI18N
        lblAddressOrder1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblAddressOrder1.setText("Formality:");

        lblDateOrder1.setFont(new java.awt.Font("Arial Narrow", 0, 18)); // NOI18N
        lblDateOrder1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblDateOrder1.setText("Status:");

        chkstatus.setFont(new java.awt.Font("Arial Narrow", 0, 18)); // NOI18N
        chkstatus.setText("Sussec");
        chkstatus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkstatusActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout PanelStatusLayout = new javax.swing.GroupLayout(PanelStatus);
        PanelStatus.setLayout(PanelStatusLayout);
        PanelStatusLayout.setHorizontalGroup(
            PanelStatusLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 403, Short.MAX_VALUE)
            .addGroup(PanelStatusLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(PanelStatusLayout.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(PanelStatusLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addGroup(PanelStatusLayout.createSequentialGroup()
                            .addComponent(lblDatePay, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(DatePay, javax.swing.GroupLayout.DEFAULT_SIZE, 242, Short.MAX_VALUE))
                        .addGroup(PanelStatusLayout.createSequentialGroup()
                            .addGroup(PanelStatusLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(lblDateOrder1, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(lblAddressOrder1, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addGroup(PanelStatusLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(chkstatus, javax.swing.GroupLayout.DEFAULT_SIZE, 239, Short.MAX_VALUE)
                                .addComponent(cbFormality, 0, 239, Short.MAX_VALUE))))
                    .addContainerGap(22, Short.MAX_VALUE)))
        );
        PanelStatusLayout.setVerticalGroup(
            PanelStatusLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 147, Short.MAX_VALUE)
            .addGroup(PanelStatusLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(PanelStatusLayout.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(PanelStatusLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(DatePay, javax.swing.GroupLayout.DEFAULT_SIZE, 31, Short.MAX_VALUE)
                        .addComponent(lblDatePay, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGap(13, 13, 13)
                    .addGroup(PanelStatusLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(lblAddressOrder1, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(cbFormality, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                    .addGroup(PanelStatusLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(lblDateOrder1, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(chkstatus, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );

        txtQuantity.setEditable(false);
        txtQuantity.setFont(new java.awt.Font("Arial Narrow", 0, 18)); // NOI18N
        txtQuantity.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtQuantityActionPerformed(evt);
            }
        });
        txtQuantity.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtQuantityKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtQuantityKeyReleased(evt);
            }
        });

        txtNote.setEditable(false);
        txtNote.setColumns(20);
        txtNote.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        txtNote.setLineWrap(true);
        txtNote.setRows(5);
        txtNote.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtNoteKeyReleased(evt);
            }
        });
        truot2.setViewportView(txtNote);

        txtSum.setFont(new java.awt.Font("Arial Narrow", 0, 18)); // NOI18N

        lblSum.setFont(new java.awt.Font("Arial Narrow", 0, 18)); // NOI18N
        lblSum.setText("Sum ( VNĐ ) : ");

        btnAddProduct.setFont(new java.awt.Font("Arial Narrow", 0, 18)); // NOI18N
        btnAddProduct.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Icon/AddProduct.png"))); // NOI18N
        btnAddProduct.setText("Add Product");
        btnAddProduct.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddProductActionPerformed(evt);
            }
        });

        lbquantity.setFont(new java.awt.Font("Arial Narrow", 1, 18)); // NOI18N
        lbquantity.setText("Quantity");

        lbnote.setFont(new java.awt.Font("Arial Narrow", 1, 18)); // NOI18N
        lbnote.setText("Note");

        javax.swing.GroupLayout tabOrderLayout = new javax.swing.GroupLayout(tabOrder);
        tabOrder.setLayout(tabOrderLayout);
        tabOrderLayout.setHorizontalGroup(
            tabOrderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(tabOrderLayout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(tabOrderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(tabOrderLayout.createSequentialGroup()
                        .addGroup(tabOrderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(lblDateOrder, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblCodeOrder, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblCodeEmployee, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblAddressOrder, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(37, 37, 37)
                        .addGroup(tabOrderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(tabOrderLayout.createSequentialGroup()
                                .addComponent(cboCustomer, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGap(18, 18, 18)
                                .addComponent(btnNewCustomer, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(29, 29, 29))
                            .addGroup(tabOrderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(txtCodeOrder, javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(txtAddressOrder, javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(DateOrder, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 472, Short.MAX_VALUE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(PanelStatus, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(tabOrderLayout.createSequentialGroup()
                        .addGroup(tabOrderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(tabOrderLayout.createSequentialGroup()
                                .addComponent(btnUpdateOrder)
                                .addGap(35, 35, 35)
                                .addComponent(btnDeleteOrder, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGap(31, 31, 31)
                                .addComponent(btnResetOrder, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(40, 40, 40)
                                .addComponent(btnCancelOrder, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(37, 37, 37))
                            .addGroup(tabOrderLayout.createSequentialGroup()
                                .addComponent(lblSum)
                                .addGap(18, 18, 18)
                                .addComponent(txtSum, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addComponent(truot1, javax.swing.GroupLayout.PREFERRED_SIZE, 669, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(tabOrderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, tabOrderLayout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(tabOrderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(lbquantity, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(lbnote, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(tabOrderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtQuantity)
                                    .addComponent(truot2)))
                            .addGroup(tabOrderLayout.createSequentialGroup()
                                .addGap(10, 24, Short.MAX_VALUE)
                                .addGroup(tabOrderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(tabOrderLayout.createSequentialGroup()
                                        .addComponent(btnAddProduct, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(37, 37, 37)
                                        .addComponent(btnRemove, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(10, 10, 10))
                                    .addGroup(tabOrderLayout.createSequentialGroup()
                                        .addComponent(btnCreatOrder, javax.swing.GroupLayout.PREFERRED_SIZE, 258, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(49, 49, 49)))))))
                .addGap(30, 30, 30))
        );
        tabOrderLayout.setVerticalGroup(
            tabOrderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(tabOrderLayout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addGroup(tabOrderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(tabOrderLayout.createSequentialGroup()
                        .addGroup(tabOrderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblCodeOrder, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtCodeOrder, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(tabOrderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(tabOrderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(cboCustomer, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(btnNewCustomer, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(lblCodeEmployee, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(15, 15, 15)
                        .addGroup(tabOrderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblAddressOrder)
                            .addComponent(txtAddressOrder, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(tabOrderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(lblDateOrder, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(DateOrder, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(PanelStatus, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(tabOrderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(tabOrderLayout.createSequentialGroup()
                        .addGap(35, 35, 35)
                        .addGroup(tabOrderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtQuantity, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lbquantity))
                        .addGap(37, 37, 37)
                        .addGroup(tabOrderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(truot2, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lbnote, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(38, 38, 38)
                        .addGroup(tabOrderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnRemove, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnAddProduct, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(26, 26, 26))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, tabOrderLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(truot1, javax.swing.GroupLayout.PREFERRED_SIZE, 284, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)))
                .addGroup(tabOrderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnCreatOrder, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(tabOrderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(lblSum, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 38, Short.MAX_VALUE)
                        .addComponent(txtSum, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGap(18, 18, 18)
                .addGroup(tabOrderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(tabOrderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnUpdateOrder, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnDeleteOrder, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnResetOrder, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(btnCancelOrder, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        tab.addTab("  Order  ", tabOrder);

        tblHistory.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        tblHistory.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "Code Order", "Name Customer", "Order Date", "Address", "Status", "Code Employe"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Boolean.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblHistory.setToolTipText("");
        tblHistory.setRowHeight(20);
        tblHistory.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblHistoryMouseClicked(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                tblHistoryMouseReleased(evt);
            }
        });
        truot3.setViewportView(tblHistory);
        if (tblHistory.getColumnModel().getColumnCount() > 0) {
            tblHistory.getColumnModel().getColumn(4).setMinWidth(80);
            tblHistory.getColumnModel().getColumn(4).setPreferredWidth(80);
            tblHistory.getColumnModel().getColumn(4).setMaxWidth(80);
        }

        txtFind.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N

        btntimkiem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Icon/Search_1.png"))); // NOI18N
        btntimkiem.setText("Search");
        btntimkiem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btntimkiemActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout tabHistoryLayout = new javax.swing.GroupLayout(tabHistory);
        tabHistory.setLayout(tabHistoryLayout);
        tabHistoryLayout.setHorizontalGroup(
            tabHistoryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(tabHistoryLayout.createSequentialGroup()
                .addGroup(tabHistoryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(tabHistoryLayout.createSequentialGroup()
                        .addGap(277, 277, 277)
                        .addComponent(txtFind, javax.swing.GroupLayout.PREFERRED_SIZE, 482, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(38, 38, 38)
                        .addComponent(btntimkiem))
                    .addGroup(tabHistoryLayout.createSequentialGroup()
                        .addGap(44, 44, 44)
                        .addComponent(truot3, javax.swing.GroupLayout.PREFERRED_SIZE, 1020, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(92, Short.MAX_VALUE))
        );
        tabHistoryLayout.setVerticalGroup(
            tabHistoryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(tabHistoryLayout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addGroup(tabHistoryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtFind, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btntimkiem, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(truot3, javax.swing.GroupLayout.PREFERRED_SIZE, 548, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(61, Short.MAX_VALUE))
        );

        tab.addTab("  History  ", tabHistory);

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
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(PanelNenLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(tab)
                .addContainerGap())
        );
        PanelNenLayout.setVerticalGroup(
            PanelNenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelNenLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblManageOrder)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(tab)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(PanelNen, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(PanelNen, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void tblDetailVetoableChange(java.beans.PropertyChangeEvent evt)throws java.beans.PropertyVetoException {//GEN-FIRST:event_tblDetailVetoableChange
        // TODO add your handling code here:
        updateOrder();
    }//GEN-LAST:event_tblDetailVetoableChange

    private void tblDetailKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tblDetailKeyReleased
        // TODO add your handling code here:

    }//GEN-LAST:event_tblDetailKeyReleased

    private void tblDetailKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tblDetailKeyPressed
        // TODO add your handling code here:

    }//GEN-LAST:event_tblDetailKeyPressed

    private void tblDetailPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_tblDetailPropertyChange
        // TODO add your handling code here:

    }//GEN-LAST:event_tblDetailPropertyChange

    private void tblDetailMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblDetailMouseClicked
        // TODO add your handling code here:
        this.rowDetail = tblDetail.getSelectedRow();
        txtNote.setEditable(true);
        txtQuantity.setEditable(true);
        setFormDetail();
        this.UpdateStatus();

    }//GEN-LAST:event_tblDetailMouseClicked

    private void tblDetailHierarchyChanged(java.awt.event.HierarchyEvent evt) {//GEN-FIRST:event_tblDetailHierarchyChanged
        // TODO add your handling code here:
        updateOrder();
    }//GEN-LAST:event_tblDetailHierarchyChanged

    private void btnResetOrderActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnResetOrderActionPerformed
        // TODO add your handling code here:
        try {
//            ResetOrder();
            if (CurrentOrder.Order == null) {
                ResetOrder();
            } else {
                filltoTableOrderDetail();
                clearUpdate();
                this.UpdateStatus();
            }
        } catch (Exception e) {
            Dialog.MessageError(null, "Lỗi khi làm mới");
        }
    }//GEN-LAST:event_btnResetOrderActionPerformed

    private void btnNewCustomerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNewCustomerActionPerformed
        // TODO add your handling code here:
        try {
            new ManageCustomer(null, true).setVisible(true);
            fillCbCustomer();
        } catch (Exception e) {
            Dialog.MessageError(null, e);
        }
    }//GEN-LAST:event_btnNewCustomerActionPerformed

    private void btnUpdateOrderActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateOrderActionPerformed
        // TODO add your handling code here:
        try {
            Update();
        } catch (Exception e) {
            Dialog.MessageError(null, e);
        }
    }//GEN-LAST:event_btnUpdateOrderActionPerformed

    private void btnCancelOrderActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelOrderActionPerformed
        // TODO add your handling code here:
        CurrentOrder.ClearnOrder();
        this.rowDetail = -1;
        this.dispose();
    }//GEN-LAST:event_btnCancelOrderActionPerformed

    private void btnDeleteOrderActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteOrderActionPerformed
        // TODO add your handling code here:
        try {
            Delete();
        } catch (Exception e) {
            Dialog.MessageError(null, e);
        }
    }//GEN-LAST:event_btnDeleteOrderActionPerformed

    private void btnCreatOrderActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCreatOrderActionPerformed
        // TODO add your handling code here:
        try {
            Insert();
        } catch (Exception e) {
            Dialog.MessageError(null, e);
        }
    }//GEN-LAST:event_btnCreatOrderActionPerformed

    private void txtAddressOrderActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtAddressOrderActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtAddressOrderActionPerformed

    private void btnRemoveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRemoveActionPerformed
        //        Dialog.Message(null, rowDetail);
        if (this.rowDetail >= 0) {
//            DefaultTableModel model = (DefaultTableModel) tblDetail.getModel();
            if (CurrentOrder.Order != null) {
                int codeorder = 0;
                try {
                    codeorder = Integer.parseInt(String.valueOf(tblDetail.getValueAt(this.rowDetail, 0)));
                } catch (Exception e) {
                }
                String codephone = String.valueOf(tblDetail.getValueAt(this.rowDetail, 5));
                for (OrderDetail orderDetail : CurrentOrder.Order) {
                    if (orderDetail.getCodeOrder() == codeorder && orderDetail.getCodePhone().equalsIgnoreCase(codephone)) {
                        CurrentOrder.Order.remove(orderDetail);
                        break;
                    }
                }
            }
//            model.removeRow(this.rowDetail);
            this.filltoTableOrderDetail();
            txtQuantity.setText("");
            txtNote.setText("");
            this.rowDetail = -1;
            this.UpdateStatus();
        } else {
            Dialog.MessageWarning(this, "Haven't selected the product to delete");
        }
    }//GEN-LAST:event_btnRemoveActionPerformed

    private void btnAddProductActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddProductActionPerformed
        // TODO add your handling code here:
        try {
            StorePhone st = new StorePhone(this, true);
            st.NullCart();
            st.setVisible(true);
            filltoTableOrderDetail();
            this.rowDetail = -1;
            this.UpdateStatus();
            SumPrice();
        } catch (Exception e) {
            Dialog.MessageError(this, "Can't open shop");
        }
    }//GEN-LAST:event_btnAddProductActionPerformed

    private void cboCustomerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboCustomerActionPerformed
        // TODO add your handling code here:
//        DefaultComboBoxModel model = (DefaultComboBoxModel) cboCustomer.getModel();
        InformationCustomer cus = (InformationCustomer) cboCustomer.getSelectedItem();
        if (cus != null)
            txtAddressOrder.setText(cus.getAddress());
    }//GEN-LAST:event_cboCustomerActionPerformed

    private void txtQuantityKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtQuantityKeyPressed
        // TODO add your handling code here:

    }//GEN-LAST:event_txtQuantityKeyPressed

    private void txtQuantityKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtQuantityKeyReleased
        // TODO add your handling code here:
        if (this.rowDetail >= 0) {
            int sl = 0;
            if (txtQuantity.getText().equals("")) {
                sl = 0;
            } else {
                try {
                    sl = Integer.parseInt(txtQuantity.getText());
                    txtQuantity.setForeground(Color.black);
                } catch (Exception e) {
                    txtQuantity.setForeground(Color.red);
                    sl = 0;
                }
            }
            CurrentOrder.Order.get(this.rowDetail).setQuantity(sl);
//            filltoTableOrderDetail();
            tblDetail.setValueAt(sl, this.rowDetail, 3);
            SumPrice();
        }
    }//GEN-LAST:event_txtQuantityKeyReleased

    private void txtNoteKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNoteKeyReleased
        if (this.rowDetail >= 0) {
            String text = txtNote.getText();
            CurrentOrder.Order.get(this.rowDetail).setNote(text);
//            filltoTableOrderDetail();
            tblDetail.setValueAt(text, this.rowDetail, 4);
        }
    }//GEN-LAST:event_txtNoteKeyReleased

    private void chkstatusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkstatusActionPerformed
        // TODO add your handling code here:
        if (chkstatus.isSelected())
            if (Dialog.Confirm(this, "Xác nhận đã thanh toán không ?")) {
                if (DatePay.getDate() == null) {
                    DatePay.setDate(new Date());
                }
            } else {
                chkstatus.setSelected(false);
            }
    }//GEN-LAST:event_chkstatusActionPerformed

    private void txtQuantityActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtQuantityActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtQuantityActionPerformed

    private void tblHistoryMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblHistoryMouseClicked
        // TODO add your handling code here:


    }//GEN-LAST:event_tblHistoryMouseClicked

    private void tblHistoryMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblHistoryMouseReleased
        // TODO add your handling code here:
        if (evt.getClickCount() == 2) {
            this.ResetOrder();
            this.rowHistory = tblHistory.getSelectedRow();
            String codeorder = String.valueOf(tblHistory.getValueAt(this.rowHistory, 0));
            try {
                CurrentOrder.Order = ODdao.SelectByIDOrder(codeorder);
                setFormOrder(Odao.SelectID(codeorder));
                filltoTableOrderDetail();
                tab.setSelectedIndex(0);
                this.UpdateStatus();
            } catch (Exception e) {
                CurrentOrder.Order = null;
                Dialog.MessageError(this, "Không thể lấy danh sách");
            }
        }
    }//GEN-LAST:event_tblHistoryMouseReleased

    private void tblDetailMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblDetailMouseEntered

    }//GEN-LAST:event_tblDetailMouseEntered

    private void btntimkiemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btntimkiemActionPerformed
        // TODO add your handling code here:
        try {
            if (!txtFind.getText().equals("")) {
                find();
            } else {
                filltoTableHistory();
                txtFind.requestFocus();
            }
        } catch (Exception e) {
            Dialog.MessageError(this, e);
        }
    }//GEN-LAST:event_btntimkiemActionPerformed

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
    private javax.swing.JPanel PanelStatus;
    private javax.swing.JButton btnAddProduct;
    private javax.swing.JButton btnCancelOrder;
    private javax.swing.JButton btnCreatOrder;
    private javax.swing.JButton btnDeleteOrder;
    private javax.swing.JButton btnNewCustomer;
    private javax.swing.JButton btnRemove;
    private javax.swing.JButton btnResetOrder;
    private javax.swing.JButton btnUpdateOrder;
    private javax.swing.JButton btntimkiem;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JComboBox<String> cbFormality;
    private javax.swing.JComboBox<String> cboCustomer;
    private javax.swing.JCheckBox chkstatus;
    private javax.swing.JLabel lblAddressOrder;
    private javax.swing.JLabel lblAddressOrder1;
    private javax.swing.JLabel lblCodeEmployee;
    private javax.swing.JLabel lblCodeOrder;
    private javax.swing.JLabel lblDateOrder;
    private javax.swing.JLabel lblDateOrder1;
    private javax.swing.JLabel lblDatePay;
    private javax.swing.JLabel lblManageOrder;
    private javax.swing.JLabel lblSum;
    private javax.swing.JLabel lbnote;
    private javax.swing.JLabel lbquantity;
    private javax.swing.JTabbedPane tab;
    private javax.swing.JPanel tabHistory;
    private javax.swing.JPanel tabOrder;
    private javax.swing.JTable tblDetail;
    private javax.swing.JTable tblHistory;
    private javax.swing.JScrollPane truot1;
    private javax.swing.JScrollPane truot2;
    private javax.swing.JScrollPane truot3;
    private javax.swing.JTextField txtAddressOrder;
    private javax.swing.JTextField txtCodeOrder;
    private javax.swing.JTextField txtFind;
    private javax.swing.JTextArea txtNote;
    private javax.swing.JTextField txtQuantity;
    private javax.swing.JLabel txtSum;
    // End of variables declaration//GEN-END:variables
}
