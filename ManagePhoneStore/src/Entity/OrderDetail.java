/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entity;

/**
 *
 * @author Dell
 */
public class OrderDetail {
    int CodeOrder;
    String CodePhone,Note;
    int Quantity;
    double Price;
    
    public OrderDetail() {
    }

    public OrderDetail(int CodeOrder, String CodePhone, double Price, int Quantity,String Note) {
        this.CodeOrder = CodeOrder;
        this.CodePhone = CodePhone;
        this.Note = Note;
        this.Price = Price;
        this.Quantity = Quantity;
    }

    public int getCodeOrder() {
        return CodeOrder;
    }

    public void setCodeOrder(int CodeOrder) {
        this.CodeOrder = CodeOrder;
    }


    public String getCodePhone() {
        return CodePhone;
    }

    public void setCodePhone(String CodePhone) {
        this.CodePhone = CodePhone;
    }

    public String getNote() {
        return Note;
    }

    public void setNote(String Note) {
        this.Note = Note;
    }


    public double getPrice() {
        return Price;
    }

    public void setPrice(double Price) {
        this.Price = Price;
    }

    public int getQuantity() {
        return Quantity;
    }

    public void setQuantity(int Quantity) {
        this.Quantity = Quantity;
    }

   
}
