package Libraries;

import java.awt.Component;
import javax.swing.JOptionPane;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 *
 * @author NguyenMinhHau_PS24488
 */
public class Dialog {

    public static void Message(Component parent, Object mess) {
        JOptionPane.showMessageDialog(parent, mess, "Cửa hàng JRPhone", JOptionPane.PLAIN_MESSAGE);
    }

    public static void MessageInformation(Component parent, Object mess) {
        JOptionPane.showMessageDialog(parent, mess, "Cửa hàng JRPhone", JOptionPane.INFORMATION_MESSAGE);
    }

    public static void MessageError(Component parent, Object mess) {
        JOptionPane.showMessageDialog(parent, mess, "Cửa hàng JRPhone", JOptionPane.ERROR_MESSAGE);
    }

    public static void MessageWarning(Component parent, Object mess) {
        JOptionPane.showMessageDialog(parent, mess, "Cửa hàng JRPhone", JOptionPane.WARNING_MESSAGE);
    }

    public static void MessageQuestion(Component parent, Object mess) {
        JOptionPane.showMessageDialog(parent, mess, "Cửa hàng JRPhone", JOptionPane.QUESTION_MESSAGE);
    }


    public static boolean Confirm(Component parent, Object mess) {
        int result = JOptionPane.showConfirmDialog(parent, mess, "Cửa hàng JRPhone", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        return result == JOptionPane.YES_OPTION;
    }

    public static String Input(Component parent, Object mess) {
        return JOptionPane.showInputDialog(parent, mess, "Cửa hàng JRPhone", JOptionPane.QUESTION_MESSAGE);
    }
}
