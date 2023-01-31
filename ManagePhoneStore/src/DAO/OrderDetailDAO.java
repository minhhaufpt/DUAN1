/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Entity.OrderDetail;
import Libraries.JDBCHelper;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author NguyenMinhHau_PS24488
 */
public class OrderDetailDAO extends MainDAO<OrderDetail, String> {

    String insert_sql = "Insert into OrderDetail(CodeOrder,CodePhone,Price,Quantity,Note) Values(?,?,?,?,?)";
    String update_sql = "Update OrderDetail set Price=?,Quantity=?,Note=? where CodeOrder = ? and CodePhone=?";
    String remove_sql = "Delete from OrderDetail where CodeOrder = ?";
    String select_sql = "Select * from OrderDetail";
    String select_id_sql = "Select * from OrderDetail where CodeOrder = ?";

    @Override
    public void Update(OrderDetail entity) {
//        JDBCHelper.Update(update_sql, entity.getPrice(), entity.getQuantity(), entity.getNote(), entity.getCodeOrder(), entity.getCodePhone());
        this.Detele(String.valueOf(entity.getCodeOrder()));
        this.Insert(entity);
    }

    public void UpdateALL(ArrayList<OrderDetail> entity) {
//        JDBCHelper.Update(update_sql, entity.getPrice(), entity.getQuantity(), entity.getNote(), entity.getCodeOrder(), entity.getCodePhone());
        this.Detele(String.valueOf(entity.get(0).getCodeOrder()));
        for (OrderDetail detail : entity) {
            this.Insert(detail);
        }
    }

    @Override
    public void Insert(OrderDetail entity) {
        JDBCHelper.Update(insert_sql, entity.getCodeOrder(), entity.getCodePhone(), entity.getPrice(), entity.getQuantity(), entity.getNote());
    }

    @Override
    public void Detele(String keytype) {
        JDBCHelper.Update(remove_sql, keytype);
    }

    @Override
    public OrderDetail SelectID(String id) {
        List<OrderDetail> list = this.SelectBySql(select_id_sql, id);
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    @Override
    public ArrayList<OrderDetail> SelectAll() {
        return this.SelectBySql(select_sql);
    }

    @Override
    protected ArrayList<OrderDetail> SelectBySql(String sql, Object... args) {
        ArrayList<OrderDetail> list = new ArrayList<OrderDetail>();
        try {
            ResultSet rs = JDBCHelper.Query(sql, args);
            while (rs.next()) {
                OrderDetail entity = new OrderDetail(rs.getInt("CodeOrder"), rs.getString("CodePhone"), rs.getDouble("Price"), rs.getInt("Quantity"), rs.getString("Note"));
                list.add(entity);
            }
            rs.getStatement().getConnection().close();
            return list;
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }

    }

    public ArrayList<OrderDetail> SelectByIDOrder(String id) {
        ArrayList<OrderDetail> list = this.SelectBySql(select_id_sql, Integer.parseInt(id));
        if (list.isEmpty()) {
            return null;
        }
        return list;
    }

    public OrderDetail SelectByIDOrder(String x, String y) {
        String select_khoa_sql = "Select * from OrderDetailwhere CodeOrder = ? and CodePhone=?";
        ArrayList<OrderDetail> list = this.SelectBySql(select_khoa_sql, x, y);
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }
}
