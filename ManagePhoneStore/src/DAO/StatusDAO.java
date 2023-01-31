/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Entity.OrderDetail;
import Entity.Status;
import Libraries.JDBCHelper;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author NguyenMinhHau_PS24488
 */
public class StatusDAO extends MainDAO<Status, String> {

    String insert_sql = "Insert into Status(CodeStatus,DateOfPayment,Formality,Status) Values(?,?,?,?)";
    String update_sql = "Update Status set DateOfPayment=?,Formality=?,Status=? where CodeStatus = ? ";
    String remove_sql = "Delete from Status where CodeStatus = ?";
    String select_sql = "Select * from Status";
    String select_id_sql = "Select * from Status where CodeStatus = ?";

    @Override
    public void Update(Status entity) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void Insert(Status entity) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void Detele(String keytype) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Status SelectID(String id) {
        int i = Integer.parseInt(id);
        List<Status> list = this.SelectBySql(select_id_sql, i);
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    @Override
    public ArrayList<Status> SelectAll() {
        return this.SelectBySql(select_sql);
    }

    @Override
    protected ArrayList<Status> SelectBySql(String sql, Object... args) {
        ArrayList<Status> list = new ArrayList<Status>();
        try {
            ResultSet rs = JDBCHelper.Query(sql, args);
            while (rs.next()) {
                Status entity = new Status(rs.getInt("CodeStatus"), rs.getDate("DateOfPayment"), rs.getString("Formality"), rs.getBoolean("Status"));
                list.add(entity);
            }
            rs.getStatement().getConnection().close();
            return list;
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    public Status SelectByCodeOrder(String id) {
        String select_id = "Select * from Status where CodeStatus = ?";
        List<Status> list = this.SelectBySql(select_id, id);
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }
}
