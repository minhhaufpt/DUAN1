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
public class Order {

    int CodeOrder, CodeCustomer;
    Date OrderDate;
    String AddressOrder, CodeEmployye;
    Date DateOfPayment;
    String Formality;
    boolean Status;
    
    public Order() {
    }

    public int getCodeOrder() {
        return CodeOrder;
    }

    public void setCodeOrder(int CodeOrder) {
        this.CodeOrder = CodeOrder;
    }

    public int getCodeCustomer() {
        return CodeCustomer;
    }

    public void setCodeCustomer(int CodeCustomer) {
        this.CodeCustomer = CodeCustomer;
    }

    public Date getOrderDate() {
        return OrderDate;
    }

    public void setOrderDate(Date OrderDate) {
        this.OrderDate = OrderDate;
    }

    public String getAddressOrder() {
        return AddressOrder;
    }

    public void setAddressOrder(String AddressOrder) {
        this.AddressOrder = AddressOrder;
    }

    public String getCodeEmployye() {
        return CodeEmployye;
    }

    public void setCodeEmployye(String CodeEmployye) {
        this.CodeEmployye = CodeEmployye;
    }

    public Date getDateOfPayment() {
        return DateOfPayment;
    }

    public void setDateOfPayment(Date DateOfPayment) {
        this.DateOfPayment = DateOfPayment;
    }

    public String getFormality() {
        return Formality;
    }

    public void setFormality(String Formality) {
        this.Formality = Formality;
    }

    public boolean isStatus() {
        return Status;
    }

    public void setStatus(boolean Status) {
        this.Status = Status;
    }

    public Order(int CodeOrder, int CodeCustomer, Date OrderDate, String AddressOrder, String CodeEmployye, Date DateOfPayment, String Formality, boolean Status) {
        this.CodeOrder = CodeOrder;
        this.CodeCustomer = CodeCustomer;
        this.OrderDate = OrderDate;
        this.AddressOrder = AddressOrder;
        this.CodeEmployye = CodeEmployye;
        this.DateOfPayment = DateOfPayment;
        this.Formality = Formality;
        this.Status = Status;
    }


}
