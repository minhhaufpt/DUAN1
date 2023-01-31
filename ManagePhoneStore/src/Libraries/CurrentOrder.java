/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Libraries;

import Entity.OrderDetail;
import java.util.ArrayList;

/**
 *
 * @author NguyenMinhHau_PS24488
 */
public class CurrentOrder {

    public static ArrayList<OrderDetail> Order = null;

    public static void ClearnOrder() {
        CurrentOrder.Order = null;
    }

    public static boolean checkOrderNull() {
        return CurrentOrder.Order == null ? true : false;
    }
}
