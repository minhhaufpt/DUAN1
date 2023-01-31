/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entity;

import ConnectSQLSever.ConnectSQLSever;
import Libraries.Dialog;
import java.awt.Desktop;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.sql.ResultSet;
import java.util.Date;
import javax.swing.JOptionPane;

/**
 *
 * @author Dell
 */
public class InformationUsers {

    String UserName, PassWords, Name;
    Date BirthYear;
    String PhoneNumber, Email, Address, Image;
    boolean Roles;

    public InformationUsers() {
    }

    public InformationUsers(String UserName, String PassWords, boolean Roles, String Name, Date BirthYear, String PhoneNumber, String Email, String Address, String Image) {
        this.UserName = UserName;
        this.PassWords = PassWords;
        this.Name = Name;
        this.BirthYear = BirthYear;
        this.PhoneNumber = PhoneNumber;
        this.Email = Email;
        this.Address = Address;
        this.Roles = Roles;
        this.Image = Image;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String UserName) {
        this.UserName = UserName;
    }

    public String getPassWords() {
        return PassWords;
    }

    public void setPassWords(String PassWords) {
        this.PassWords = PassWords;
    }

    public String getName() {
        return Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public Date getBirthYear() {
        return BirthYear;
    }

    public void setBirthYear(Date BirthYear) {
        this.BirthYear = BirthYear;
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

    public boolean isRoles() {
        return Roles;
    }

    public void setRoles(boolean Roles) {
        this.Roles = Roles;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String Image) {
        this.Image = Image;
    }
    
    public boolean checkLoginUser() {
        try {
            ConnectSQLSever con = new ConnectSQLSever();
            ResultSet rs = con.getTable("InformationUsers");
            while (rs.next()) {
                if (this.UserName.equals(rs.getString("UserNames")) && this.PassWords.equals(rs.getString("PassWords"))) {
                    return true;
                }
            }
        } catch (Exception ex) {
            Dialog.MessageWarning(null, ex);
        }

        return false;
    }
    private boolean checkAdmin = false;

    public boolean checkLoginAdmin() {
        try {
            ConnectSQLSever con = new ConnectSQLSever();
            ResultSet rs = con.getTable("InformationUsers");
            File file = new File("D:\\IDE\\Git\\DuAn1\\ManagePhoneStore\\src\\FaceRecognitionByPython");
            while (rs.next()) {
                if (this.UserName.equals(rs.getString("UserNames")) && this.PassWords.equals(rs.getString("PassWords")) && rs.getBoolean("Roles") == true) {
                    JOptionPane.showMessageDialog(null, "Tiến hành xác nhận khuôn mặt", "Login", JOptionPane.INFORMATION_MESSAGE);
                    if (!Desktop.isDesktopSupported())//check if Desktop is supported by Platform or not  
                    {
                        JOptionPane.showMessageDialog(null, "Not Supported", "Login", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        Desktop desktop = Desktop.getDesktop();
                        if (file.exists()) //checks file exists or not  
                        {
                            try {
                                desktop.open(new File("D:\\IDE\\Git\\DuAn1\\ManagePhoneStore\\src\\FaceRecognitionByPython\\Recoginition_Face.py"));
                                Thread.sleep(15000);
                                while (true) {
                                    FileInputStream fi = null;
                                    try {
                                        fi = new FileInputStream("D:\\IDE\\Git\\DuAn1\\ManagePhoneStore\\src\\FaceRecognitionByPython\\Record.txt");
                                        BufferedReader br = new BufferedReader(new InputStreamReader(fi));
                                        String line = br.readLine();
                                        String xacnhan = "";
                                        while (line != null) {
                                            xacnhan = line + " " + (br.readLine() != null ? br.readLine() : "");
                                            line = br.readLine();
                                        }
                                        xacnhan = xacnhan.trim();
                                        if (xacnhan.equalsIgnoreCase("")) {
//                                          JOptionPane.showMessageDialog(null, "Không nhận diện được ", "Login", JOptionPane.INFORMATION_MESSAGE);
                                        } else if (xacnhan.equalsIgnoreCase("Unknown")) {
                                            Dialog.MessageWarning(null, "Không có thông tin");
                                            FileWriter fw = new FileWriter("D:\\IDE\\Git\\DuAn1\\ManagePhoneStore\\src\\FaceRecognitionByPython\\Record.txt");
                                            fw.write("");
                                            break;
                                        } else {
                                            this.UserName = this.UserName.trim();
                                            if (xacnhan.equalsIgnoreCase(this.UserName)) {
                                                Dialog.MessageInformation(null, "Nhận diện thành công");
                                                FileWriter fw = new FileWriter("D:\\IDE\\Git\\DuAn1\\ManagePhoneStore\\src\\FaceRecognitionByPython\\Record.txt");
                                                fw.write("");
                                                checkAdmin = true;
                                                break;
                                            } else {
                                                Dialog.MessageWarning(null, "Tài khoản và khuôn mặt không trùng khớp");
                                                FileWriter fw = new FileWriter("D:\\IDE\\Git\\DuAn1\\ManagePhoneStore\\src\\FaceRecognitionByPython\\Record.txt");
                                                fw.write("");
                                                break;
                                            }
                                        }
                                    } catch (Exception ex) {
                                        Dialog.MessageWarning(null, ex);
                                    } finally {
                                        fi.close();
                                    }
                                    Thread.sleep(800);
                                }
                            } catch (Exception ex) {
                                Dialog.MessageWarning(null, ex);
                            }
                            return checkAdmin;
                        } else {
                            Dialog.MessageWarning(null, "File not exists !");
                        }
                    }
                }
            }
        } catch (Exception e) {
            Dialog.MessageWarning(null, e);
        }
        return false;
    }
}
