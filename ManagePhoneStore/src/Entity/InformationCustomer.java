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
public class InformationCustomer {

    int CodeCustomer;
    String NameCustomer, PhoneNumber, Email, Address, Note;

    public InformationCustomer() {
    }

    public InformationCustomer(int CodeCustomer, String NameCustomer, String PhoneNumber, String Email, String Address, String Note) {
        this.CodeCustomer = CodeCustomer;
        this.NameCustomer = NameCustomer;
        this.PhoneNumber = PhoneNumber;
        this.Email = Email;
        this.Address = Address;
        this.Note = Note;
    }

    public int getCodeCustomer() {
        return CodeCustomer;
    }

    public void setCodeCustomer(int CodeCustomer) {
        this.CodeCustomer = CodeCustomer;
    }

    public String getNameCustomer() {
        return NameCustomer;
    }

    public void setNameCustomer(String NameCustomer) {
        this.NameCustomer = NameCustomer;
    }

    public String getPhoneNumber() {
        return PhoneNumber;
    }

    public void setPhoneNumber(String PhoneNumber) {
        this.PhoneNumber = PhoneNumber;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String Email) {
        this.Email = Email;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String Address) {
        this.Address = Address;
    }

    public String getNote() {
        return Note;
    }

    public void setNote(String Note) {
        this.Note = Note;
    }

    @Override
    public String toString() {
        return String.valueOf(this.CodeCustomer);
    }
}
