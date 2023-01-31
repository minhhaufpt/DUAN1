/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Entity.InformationCustomer;
import Libraries.JDBCHelper;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Admin
 */
public class InformationCustomerDAO extends MainDAO<InformationCustomer, String> {

    String insert_sql = "Insert into InformationCustomer(NameCustomer,PhoneNumber,Email,Address,Note) Values(?,?,?,?,?)";
    String update_sql = "Update InformationCustomer set NameCustomer=?,PhoneNumber=?,Email=?,Address=?,Note=? where CodeCustomer = ? ";
    String remove_sql = "Delete from InformationCustomer where CodeCustomer = ?";
    String select_sql = "Select * from InformationCustomer";
    String select_id_sql = "Select * from InformationCustomer where CodeCustomer = ?";

    @Override
    public void Update(InformationCustomer entity) {
        JDBCHelper.Update(update_sql, entity.getNameCustomer(), entity.getPhoneNumber(), entity.getEmail(), entity.getAddress(), entity.getNote(), entity.getCodeCustomer());
    }

    @Override
    public void Insert(InformationCustomer entity) {
        JDBCHelper.Update(insert_sql, entity.getNameCustomer(), entity.getPhoneNumber(), entity.getEmail(), entity.getAddress(), entity.getNote());
    }

    @Override
    public void Detele(String keytype) {
        JDBCHelper.Update(remove_sql, keytype);
    }

    @Override
    public InformationCustomer SelectID(String id) {
    int i = Integer.parseInt(id);
        List<InformationCustomer> list = this.SelectBySql(select_id_sql, i);
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);

    }

    @Override
    public ArrayList SelectAll() {
        return this.SelectBySql(select_sql);
    }

    @Override
    protected ArrayList SelectBySql(String sql, Object... args) {

        ArrayList<InformationCustomer> list = new ArrayList<InformationCustomer>();
        try {
            ResultSet rs = JDBCHelper.Query(sql, args);
            while (rs.next()) {
                InformationCustomer entity = new InformationCustomer(rs.getInt("CodeCustomer"), rs.getString("NameCustomer"),
                        rs.getString("PhoneNumber"), rs.getString("Email"), rs.getString("Address"), rs.getString("Note"));
                list.add(entity);
            }
            rs.getStatement().getConnection().close();
            return list;
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    public ArrayList<InformationCustomer> SelectByName(String name) {
        String select = "Select * from InformationCustomer where NameCustomer  like ?";
        return this.SelectBySql(select, "%" + name + "%");
    }
}
