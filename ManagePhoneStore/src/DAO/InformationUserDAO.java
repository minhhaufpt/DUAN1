/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Entity.InformationUsers;
import Libraries.JDBCHelper;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author NguyenMinhHau_PS24488
 */
public class InformationUserDAO extends MainDAO<InformationUsers, String> {

    String insert_sql = "Insert into InformationUsers(UserNames,PassWords,Roles,Name,BirthYear,PhoneNumber,Email,Address,Image) Values(?,?,?,?,?,?,?,?,?)";
    String update_sql = "Update InformationUsers set PassWords=?,Roles=?,Name=?,BirthYear=?,PhoneNumber=?,Email=?,Address=?,Image=? where UserNames = ? ";
    String remove_sql = "Delete from InformationUsers where UserNames = ?";
    String select_sql = "Select * from InformationUsers";
    String select_id_sql = "Select * from InformationUsers where UserNames = ?";

    @Override
    public void Update(InformationUsers entity) {
        JDBCHelper.Update(update_sql, entity.getPassWords(), entity.isRoles(), entity.getName(), entity.getBirthYear(), entity.getPhoneNumber(), entity.getEmail(), entity.getAddress(), entity.getImage(), entity.getUserName());

    }

    @Override
    public void Insert(InformationUsers entity) {
        JDBCHelper.Update(insert_sql, entity.getUserName(), entity.getPassWords(), entity.isRoles(), entity.getName(), entity.getBirthYear(), entity.getPhoneNumber(), entity.getEmail(), entity.getAddress(), entity.getImage());

    }

    @Override
    public void Detele(String keytype) {
        JDBCHelper.Update(remove_sql, keytype);
    }

    public int UpdateReturn(InformationUsers entity) {
        return JDBCHelper.Update(update_sql, entity.getPassWords(), entity.isRoles(), entity.getName(), entity.getBirthYear(), entity.getPhoneNumber(), entity.getEmail(), entity.getAddress(), entity.getImage(), entity.getUserName());

    }

    public int InsertReturn(InformationUsers entity) {
        return JDBCHelper.Update(insert_sql, entity.getUserName(), entity.getPassWords(), entity.isRoles(), entity.getName(), entity.getBirthYear(), entity.getPhoneNumber(), entity.getEmail(), entity.getAddress(), entity.getImage());

    }

    public int DeteleReturn(String keytype) {
        return JDBCHelper.Update(remove_sql, keytype);
    }

    @Override
    public InformationUsers SelectID(String id) {
        List<InformationUsers> list = this.SelectBySql(select_id_sql, id);
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    @Override
    public ArrayList<InformationUsers> SelectAll() {
        return this.SelectBySql(select_sql);
    }

    @Override
    protected ArrayList<InformationUsers> SelectBySql(String sql, Object... args) {
        ArrayList<InformationUsers> list = new ArrayList<InformationUsers>();
        try {
            ResultSet rs = JDBCHelper.Query(sql, args);
            while (rs.next()) {
                InformationUsers entity = new InformationUsers(rs.getString("UserNames"), rs.getString("PassWords"), rs.getBoolean("Roles"), rs.getString("Name"), rs.getDate("BirthYear"), rs.getString("PhoneNumber"), rs.getString("Email"), rs.getString("Address"), rs.getString("Image"));
                list.add(entity);
            }
            rs.getStatement().getConnection().close();
            return list;
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    public void UpdatePassword(InformationUsers entity) {
        String updatep_sql = "Update InformationUsers set PassWords=? where UserNames = ? ";
        JDBCHelper.Update(updatep_sql, entity.getPassWords(), entity.getUserName());

    }

    public List<InformationUsers> SelectByKeyword(String key) {
        String select = "Select * from InformationUsers where Name like ?";
        return this.SelectBySql(select, "%" + key + "%");
    }

    public List<InformationUsers> selectNotEmployee() {
        String sql = "select * from InformationUsers where Roles = false";
        return this.SelectBySql(sql);
    }

    public boolean selectNotDelete(String key) {
        key = key.trim();
        String sql = "select * from ImportCoupon where CodeEmployee like ?";
        String sql1 = "select * from OrderBasic where CodeEmployee like ?";
        try {
            ResultSet rs = JDBCHelper.Query(sql, "%" + key + "%");
            ResultSet rs1 = JDBCHelper.Query(sql1, "%" + key + "%");
            int k = 0;
            while (rs.next()) {
                k++;
            }
            while (rs1.next()) {
                k++;
            }
            rs.getStatement().getConnection().close();
            return k == 0 ? true : false;
        } catch (Exception ex) {
            return false;
        }

    }

}
