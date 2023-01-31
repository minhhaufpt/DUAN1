/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Libraries;

/**
 *
 * @author NguyenMinhHau_PS24488
 */
public class CheckValidate {

    public static boolean isCheckEmail(String email) {
        String chkEmail1 = "\\w+@\\w{3,8}+\\.+\\w{2,3}+\\.+\\w{2,3}";
        String chkEmail2 = "\\w+@\\w{3,8}+\\.+\\w{2,4}";
        if (email.matches(chkEmail1)) {
            return true;
        } else if (email.matches(chkEmail2)) {
            return true;
        }
        return false;
    }

    public static boolean isCheckPhone(String phone) {
        String chkphone = "[0][0-9]{8,10}$";
        if (phone.matches(chkphone)) {
            return true;
        }
        return false;
    }

    public static boolean isCheckNumber(String number) {
        try {
            double d = Double.parseDouble(number);
            return true;
        } catch (NumberFormatException ex) {
            return false;
        }
    }
     public static boolean isCheckName(String name) {
         String chkname = "\\D{1,50}";
        if (name.matches(chkname)) {
            return true;
        }
        return false;
    }
    

}
