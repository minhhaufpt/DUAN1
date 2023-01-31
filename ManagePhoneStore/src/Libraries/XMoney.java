/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Libraries;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;

/**
 *
 * @author NguyenMinhHau_PS24488
 */
public class XMoney {

    public static String toString(double money, String patten) {
        Locale localeVN = new Locale("vi", "VN");
        NumberFormat vn = NumberFormat.getInstance(localeVN);
        Locale localeEN = new Locale("en", "EN");
        NumberFormat en = NumberFormat.getInstance(localeEN);

        String str = "";
        if (patten.equalsIgnoreCase("vi") || patten.equalsIgnoreCase("VN")) {
            str = vn.format(money);
        } else if (patten.equalsIgnoreCase("en")) {
            str = en.format(money);
        } else {
            str = vn.format(money);
        }
        return str;
    }

    public static double toMoney(String money) {
        String moneyFormat = "";
        double moneyDouble = 0;
        try {
            moneyFormat = money.replace(",", "");
            moneyFormat = moneyFormat.replace(".", "");
            moneyDouble = Double.parseDouble(moneyFormat);
        } catch (Exception e) {
            Dialog.MessageError(null, "Không được chứa kí tự khác . và ,");
        }
        return moneyDouble;
    }

}
