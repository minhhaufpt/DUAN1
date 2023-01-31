/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entity;

import java.util.Date;

/**
 *
 * @author Dell
 */
public class ImportCoupon {

    int CodeCoupon;
    String CodePhone;
    String CodeEmployee;
    int Quantity;
    Date DateImport;
    String Note;

    public ImportCoupon() {
    }

    public ImportCoupon(int CodeCoupon, String CodePhone, String CodeEmployee, int Quantity, Date DateImport, String Note) {
        this.CodeCoupon = CodeCoupon;
        this.CodePhone = CodePhone;
        this.CodeEmployee = CodeEmployee;
        this.Quantity = Quantity;
        this.DateImport = DateImport;
        this.Note = Note;
    }

    public int getCodeCoupon() {
        return CodeCoupon;
    }

    public void setCodeCoupon(int CodeCoupon) {
        this.CodeCoupon = CodeCoupon;
    }

    public String getCodePhone() {
        return CodePhone;
    }

    public void setCodePhone(String CodePhone) {
        this.CodePhone = CodePhone;
    }

    public String getCodeEmployee() {
        return CodeEmployee;
    }

    public void setCodeEmployee(String CodeEmployee) {
        this.CodeEmployee = CodeEmployee;
    }

    public int getQuantity() {
        return Quantity;
    }

    public void setQuantity(int Quantity) {
        this.Quantity = Quantity;
    }

    public Date getDateImport() {
        return DateImport;
    }

    public void setDateImport(Date DateImport) {
        this.DateImport = DateImport;
    }

    public String getNote() {
        return Note;
    }

    public void setNote(String Note) {
        this.Note = Note;
    }

    @Override
    public String toString() {
        return this.CodePhone;
    }

}
