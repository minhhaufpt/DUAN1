/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Entity.Order;
import Entity.OrderDetail;
import Libraries.JDBCHelper;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author NguyenMinhHau_PS24488
 */
public class OrderDAO extends MainDAO<Order, String> {

    String insert_sql = "Insert into OrderBasic(CodeCustomer,CodeEmpoyye,OrderDate,AddressOrder,DateOfPayment,Formality,Status) Values(?,?,?,?,?,?,?)";
    String update_sql = "Update OrderBasic set CodeCustomer=?,CodeEmpoyye=?,OrderDate=?,AddressOrder = ?,DateOfPayment=?,Formality=?,Status=? where CodeOrder = ? ";
    String remove_sql = "Delete from OrderBasic where CodeOrder = ?";
    String select_sql = "Select * from OrderBasic";
    String select_id_sql = "Select * from OrderBasic where CodeOrder = ?";
    String insert_Detail = "Insert into OrderDetail(CodeOrder,CodePhone,Price,Quantity,Note) Values(?,?,?,?,?)";

    @Override
    public void Update(Order entity) {
        JDBCHelper.Update(update_sql, entity.getCodeCustomer(), entity.getCodeEmployye(), entity.getOrderDate(), entity.getAddressOrder(), entity.getDateOfPayment(), entity.getFormality(), entity.isStatus(), entity.getCodeOrder());
    }

    @Override
    public void Insert(Order entity) {
        JDBCHelper.Update(insert_sql, entity.getCodeCustomer(), entity.getCodeEmployye(), entity.getOrderDate(), entity.getAddressOrder(), entity.getDateOfPayment(), entity.getFormality(), entity.isStatus());

    }

    @Override
    public void Detele(String keytype) {
        JDBCHelper.Update(remove_sql, keytype);
    }

    @Override
    public Order SelectID(String id) {
        int k = Integer.parseInt(id);
        List<Order> list = this.SelectBySql(select_id_sql, k);
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    @Override
    public ArrayList<Order> SelectAll() {
        return this.SelectBySql(select_sql);
    }

    @Override
    protected ArrayList<Order> SelectBySql(String sql, Object... args) {
        ArrayList<Order> list = new ArrayList<Order>();
        try {
            ResultSet rs = JDBCHelper.Query(sql, args);
            while (rs.next()) {
                Order entity = new Order(rs.getInt("CodeOrder"), rs.getInt("CodeCustomer"), rs.getDate("OrderDate"), rs.getString("AddressOrder"), rs.getString("CodeEmpoyye"), rs.getDate("DateOfPayment"), rs.getString("Formality"), rs.getBoolean("Status"));
                list.add(entity);
            }
            rs.getStatement().getConnection().close();
            return list;
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }

    }

    public int Insert(Order entity, boolean ck) throws SQLException {
        if (ck == true) {
            JDBCHelper.Update(insert_sql, entity.getCodeCustomer(), entity.getCodeEmployye(), entity.getOrderDate(), entity.getAddressOrder(), entity.getDateOfPayment(), entity.getFormality(), entity.isStatus());
            String timcode = "select CodeOrder from OrderDetail where Price = 0 ";
            ResultSet rs = JDBCHelper.Query(timcode);
            while (rs.next()) {
                return rs.getInt("CodeOrder");
            }
            return 0;
        }
        return 0;
    }

    public ArrayList<Order> SelectFindCode(String id) {
        int k = Integer.parseInt(id);
        String select_codeorder = "Select * from OrderBasic where CodeOrder = ?";
        String select_codecustomer = "Select * from OrderBasic where CodeCustomer = ?";
        ArrayList<Order> list1 = this.SelectBySql(select_codeorder, k);
        ArrayList<Order> list2 = this.SelectBySql(select_codecustomer, k);
        if (list1.isEmpty() && list2.isEmpty()) {
            return null;
        } else if (!list1.isEmpty()) {
            return list1;
        } else if (!list2.isEmpty()) {
            return list2;
        }
        return null;
    }

    public ArrayList<Order> SelectFindDate(Date day) {
        String select_day = "Select * from OrderBasic where CONVERT(DATE,OrderDate) = ?";
        ArrayList<Order> list = this.SelectBySql(select_day, day);
        if (list.isEmpty()) {
            return null;
        }
        return list;
    }

    public List<Integer> selectYears() {
        String sql = "SELECT DISTINCT year(OrderDate) Year FROM OrderBasic ORDER BY Year DESC";
        List<Integer> list = new ArrayList<>();
        try {
            ResultSet rs = JDBCHelper.Query(sql);
            while (rs.next()) {
                list.add(rs.getInt(1));
            };
            rs.getStatement().getConnection().close();
            return list;
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }
    
    public List<String> selectMonthYears(){
        String sql = "  SELECT DISTINCT year(OrderDate) Year, MONTH(OrderDate) month FROM OrderBasic ORDER BY Year DESC";
        List<String> list = new ArrayList<>();
        try {
            ResultSet rs = JDBCHelper.Query(sql);
            while(rs.next()){
                String m_Y= String.valueOf(rs.getInt(2)) +"-"+  String.valueOf(rs.getInt(1));
                list.add(m_Y);
                 
            };
           
            rs.getStatement().getConnection().close();
            return list;
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

}
