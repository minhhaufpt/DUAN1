/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Libraries.JDBCHelper;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author nongn
 */
public class StatisticalDAO {

    public List<Object[]> getListOfArray(String sql, String[] cols, Object... args) {
        try {
            List<Object[]> list = new ArrayList<>();
            ResultSet rs = JDBCHelper.Query(sql, args);
            while (rs.next()) {
                Object[] vals = new Object[cols.length];
                for (int i = 0; i < cols.length; i++) {
                    vals[i] = rs.getObject(cols[i]);
                }
                list.add(vals);
            }
            rs.getStatement().getConnection().close();
            return list;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    ;
     public List<Object[]> getsl(Date datefrom, Date dateto) {
        String sql = "{CALL sl_dienthoai_donhang_date(?,?)}";
        String[] cols = {"code", "slhd", "tongsodt", "tong"};
        return this.getListOfArray(sql, cols, datefrom, dateto);
    }

    ;
    public List<Object[]> getDebt(Date datefrom, Date dateto) {
        String sql = "{CALL sl_dienthoai_chuathanhtoan_date(?,?)}";
        String[] cols = {"makh", "tongdienthoai", "tonggiatri", "soluongchuathanhtoan"};
        return this.getListOfArray(sql, cols, datefrom, dateto);
    }

    public List<Object[]> getTotalnumberofphones(Date datefrom, Date dateto) {
        String sql = "{CALL sl_dienthoai_date(?,?)}";
        String[] cols = {"code", "namep", "tongsodt", "tong"};
        return this.getListOfArray(sql, cols, datefrom, dateto);
    }

}
