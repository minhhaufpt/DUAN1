/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Libraries;

import java.awt.Image;
import java.io.File;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

/**
 *
 * @author NguyenMinhHau_PS24488
 */
public class XImage {

    public static Image getIcon() {
        URL url = XImage.class.getResource("/Image/icon/...");
        return new ImageIcon(url).getImage();
    }
    //OverLoading

    public static Image getIcon(String u) {
        URL url = null;
        try {
            url = XImage.class.getResource("/Image/icon/" + u);
        } catch (Exception e) {
            Dialog.MessageError(null, "Không tìm thấy hình ảnh");
        }
        return new ImageIcon(url).getImage();
    }

    public ImageIcon setIconToSize(String nameImg, int w, int h, int i) {
        Image im = null;
        try {
            ImageIcon img = new ImageIcon(getClass().getResource("/Image/Icon/" + nameImg));
            im = img.getImage();
        } catch (Exception e) {
            Dialog.MessageError(null, "Không tìm thấy hình ảnh");
        }
        return new ImageIcon(im.getScaledInstance(w, h, i));
    }

    public static ImageIcon setResizeable(ImageIcon img, int w, int h, int i) {
        Image im = null;
        try {
            im = img.getImage();
        } catch (Exception e) {
            Dialog.MessageError(null, "Không thể định dạng hình ảnh");
        }
        return new ImageIcon(im.getScaledInstance(w, h, i));
    }

    public static void SaveImgFace(File src, String nameImg) {
        File link = new File("Images/RecognitionFace", src.getName());
        if (!link.getParentFile().exists()) {
            link.getParentFile().mkdirs();
        }
        try {
            Path from = Paths.get(src.getAbsolutePath());
            Path to = Paths.get(link.getAbsolutePath());
            Path lammoi = Paths.get(new File("Images/RecognitionFace").getAbsolutePath() + "\\" + nameImg + ".jpg");
            if (nameImg.equalsIgnoreCase("") || nameImg == null) {
                Files.copy(from, to, StandardCopyOption.REPLACE_EXISTING);
            } else {
                Files.copy(from, to, StandardCopyOption.REPLACE_EXISTING);
                try {
                   // Files.deleteIfExists(lammoi);
                    Files.move(to, to.resolveSibling(nameImg + ".jpg"));
                } catch (Exception e) {
                    Dialog.MessageError(null, "Không thể xử lí hình ảnh");
                }
            }
        } catch (Exception ex) {
             Dialog.MessageError(null, "Không thể lưu hình ảnh");
        }
    }

    public static ImageIcon ReadImgFace(String filename) {
        File path = new File("Images/RecognitionFace", filename);
        return new ImageIcon(path.getAbsolutePath());
    }

    public static void SaveImgPro(File src, String nameImg) {
        File link = new File("Images/Products", src.getName());
        if (!link.getParentFile().exists()) {
            link.getParentFile().mkdirs();
        }
        try {
            Path from = Paths.get(src.getAbsolutePath());
            Path to = Paths.get(link.getAbsolutePath());
            Path lammoi = Paths.get(new File("Images/Products").getAbsolutePath() + "\\" + nameImg + ".jpg");
            if (nameImg.equalsIgnoreCase("") || nameImg == null) {
                Files.copy(from, to, StandardCopyOption.REPLACE_EXISTING);
            } else {
                Files.copy(from, to, StandardCopyOption.REPLACE_EXISTING);
                try {
                   // Files.deleteIfExists(lammoi);
                    Files.move(to, to.resolveSibling(nameImg + ".jpg"));
                } catch (Exception e) {
                    Dialog.MessageError(null, "Không thể xử lí hình ảnh");
                }
            }
        } catch (Exception ex) {
             Dialog.MessageError(null, "Không thể lưu hình ảnh");
        }
    }

    public static ImageIcon ReadImgPro(String filename) {
        File path = new File("Images/Products", filename);
        return new ImageIcon(path.getAbsolutePath());
    }
}
