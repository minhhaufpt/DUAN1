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
public class Status {
    int CodeStatus;
    Date DateOfPayment;
    String Formality;
    boolean Status;

    public Status() {
    }

    public Status(int CodeStatus, Date DateOfPayment, String Formality, boolean Status) {
        this.CodeStatus = CodeStatus;
        this.DateOfPayment = DateOfPayment;
        this.Formality = Formality;
        this.Status = Status;
    }

    public int getCodeStatus() {
        return CodeStatus;
    }

    public void setCodeStatus(int CodeStatus) {
        this.CodeStatus = CodeStatus;
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
    
}
