/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Entity.InformationPhone;
import Libraries.JDBCHelper;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Admin
 */
public class InformationPhoneDAO extends MainDAO<InformationPhone, String> {

    String insert_sql = "INSERT INTO InformationPhone(CodePhone,NamePhone,Price,Quantity,Color,OperatingSystem,Brand,CPU,ROM,RAM,ScreenResolution,Camera,Battery,YearOfManufacture,Material,Size,Origin,Image)  VALUES (?, ?, ?, ?, ?,?, ?, ?, ?, ?, ?,?, ?, ?, ?, ?, ?,?)";
    String update_sql = "UPDATE InformationPhone SET NamePhone=?, Price=?, Quantity=?, Color=?, OperatingSystem=? , Brand=?, CPU=?, ROM=?, RAM=?,ScreenResolution=?,Camera=?,Battery=?,YearOfManufacture=?,Material=?,Size=?,Origin=?,Image=? WHERE CodePhone like ?";
    String remove_sql = "Delete from InformationPhone where CodePhone = ?";
    String select_sql = "Select * from InformationPhone";
    String select_id_sql = "Select * from InformationPhone where CodePhone = ?";

    @Override
    public void Update(InformationPhone entity) {
        JDBCHelper.Update(update_sql, entity.getNamePhone(), entity.getPrice(), entity.getQuantity(), entity.getColor(), entity.getOperatingSystem(), entity.getBrand(), entity.getCPU(), entity.getROM(), entity.getRAM(), entity.getSceenResolution(), entity.getCamera(), entity.getBattery(), entity.getYearOfManufacture(), entity.getMaterial(), entity.getSize(), entity.getOrigin(), entity.getImage(), entity.getCodePhone());
    }

    @Override
    public void Insert(InformationPhone entity) {

        JDBCHelper.Update(insert_sql, entity.getCodePhone(), entity.getNamePhone(), entity.getPrice(), entity.getQuantity(), entity.getColor(), entity.getOperatingSystem(), entity.getBrand(), entity.getCPU(), entity.getROM(), entity.getRAM(), entity.getSceenResolution(), entity.getCamera(), entity.getBattery(), entity.getYearOfManufacture(), entity.getMaterial(), entity.getSize(), entity.getOrigin(), entity.getImage());

    }

    @Override
    public void Detele(String keytype) {
        JDBCHelper.Update(remove_sql, keytype);
    }

    @Override
    public InformationPhone SelectID(String id) {

        List<InformationPhone> list = this.SelectBySql(select_id_sql, id);
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    @Override
    public ArrayList<InformationPhone> SelectAll() {
        if (this.SelectBySql(select_sql).isEmpty()) {
            return null;
        }
        return this.SelectBySql(select_sql);
    }

    @Override
    protected ArrayList<InformationPhone> SelectBySql(String sql, Object... args) {
        ArrayList<InformationPhone> list = new ArrayList<InformationPhone>();
        try {
            ResultSet rs = JDBCHelper.Query(sql, args);
            while (rs.next()) {
                InformationPhone entity = new InformationPhone(rs.getString("CodePhone"),
                        rs.getString("NamePhone"), rs.getDouble("Price"), rs.getInt("Quantity"),
                        rs.getString("Color"), rs.getString("OperatingSystem"), rs.getString("Brand"),
                        rs.getString("CPU"), rs.getInt("ROM"), rs.getInt("RAM"), rs.getString("ScreenResolution"),
                        rs.getString("Camera"), rs.getString("Battery"),
                        rs.getInt("YearOfManufacture"), rs.getString("Material"), rs.getString("Size"),
                        rs.getString("Origin"), rs.getString("Image"));
                list.add(entity);
            }
            rs.getStatement().getConnection().close();
            return list;
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    public ArrayList<InformationPhone> SelectByName(String name) {
        name = name.trim();
        try {
            double price = Double.parseDouble(name);
            double max = price + 1000000;
            double min = price - 1000000;
            String select = "Select * from InformationPhone where Price < ? and Price > ?";
            return this.SelectBySql(select, max, min);
        } catch (Exception e) {
            String select = "Select * from InformationPhone where NamePhone like ?";
            return this.SelectBySql(select, "%" + name + "%");
        }
    }

    public void UpdateSL(String codephone, int quatity) {
        String update_sl = "UPDATE InformationPhone SET Quantity = Quantity + ? WHERE CodePhone like ?";
        JDBCHelper.Update(update_sl, quatity, codephone);
    }
}
