/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Libraries;

import Entity.InformationUsers;

/**
 *
 * @author NguyenMinhHau_PS24488
 */
public class Login {
    public static  InformationUsers user = null;
    public static void clear(){
        Login.user = null;
    }
    public static boolean isCheckLogining(){
        return Login.user != null;
    }
    public static boolean isManager(){
        return Login.isCheckLogining()&& user.isRoles();
    }
}
