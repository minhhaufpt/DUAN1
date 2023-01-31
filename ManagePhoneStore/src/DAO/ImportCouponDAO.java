/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Entity.ImportCoupon;
import Libraries.JDBCHelper;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author nongn
 */
public class ImportCouponDAO extends MainDAO<ImportCoupon, Integer> {

    String INSERT_SQL = "INSERT INTO ImportCoupon(CodePhone,CodeEmployee,Quantity,DateImport,Note)VALUES(?,?,?,?,?)";
    String UPDATE_SQL = "UPDATE ImportCoupon SET CodePhone=?,CodeEmployee=?,Quantity=?,DateImport=?,Note=? WHERE CodeCoupon=? ";
    String DELETE_SQL = "DELETE FROM ImportCoupon WHERE CodeCoupon = ? ";
    String SELECT_ALL_SQL = "SELECT * FROM ImportCoupon";
    String SELECT_BY_ID_SQL = "SELECT * FROM ImportCoupon WHERE CodeCoupon=?";

    @Override
    public void Update(ImportCoupon entity) {
        JDBCHelper.Update(UPDATE_SQL,entity.getCodePhone(), entity.getCodeEmployee(), entity.getQuantity(), entity.getDateImport(), entity.getNote(), entity.getCodeCoupon());
    }

    @Override
    public void Insert(ImportCoupon entity) {
        JDBCHelper.Update(INSERT_SQL, entity.getCodePhone(), entity.getCodeEmployee(), entity.getQuantity(), entity.getDateImport(), entity.getNote());

    }

    @Override
    public void Detele(Integer keytype) {
        JDBCHelper.Update(DELETE_SQL, keytype);
    }

    @Override
    public ImportCoupon SelectID(Integer id) {
        List<ImportCoupon> list = this.SelectBySql(SELECT_BY_ID_SQL, id);
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    @Override
    public ArrayList<ImportCoupon> SelectAll() {
        return this.SelectBySql(SELECT_ALL_SQL);
    }

    @Override
    protected ArrayList<ImportCoupon> SelectBySql(String sql, Object... args) {
        ArrayList<ImportCoupon> list = new ArrayList<ImportCoupon>();
        try {
            ResultSet rs = JDBCHelper.Query(sql, args);
            while (rs.next()) {
                ImportCoupon entity = new ImportCoupon();
                entity.setCodeCoupon(rs.getInt("CodeCoupon"));
                entity.setCodePhone(rs.getString("CodePhone"));
                entity.setCodeEmployee(rs.getString("CodeEmployee"));
                entity.setQuantity(rs.getInt("Quantity"));
                entity.setDateImport(rs.getDate("DateImport"));
                entity.setNote(rs.getString("Note"));
                list.add(entity);
            }
            rs.getStatement().getConnection().close();
            return list;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public ArrayList<ImportCoupon> selectByInformationPhone(String cd) {
        String sql = "SELECT * FROM ImportCoupon WHERE CodePhone like ?";
        return this.SelectBySql(sql, cd);
    }
}
