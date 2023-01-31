/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ConnectSQLSever;

import java.sql.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;

/**
 *
 * @author NguyenMinhHau_PS24488
 */
public final class ConnectSQLSever {

    public ConnectSQLSever() throws Exception {
    }

    public Connection getConnection() throws Exception {
        String url = "jdbc:sqlserver://" + serverName + ":" + portNumber + "\\" + instance + ";databaseName=" + dbName + ";encrypt=true;trustServerCertificate=true;";
        if (instance == null || instance.trim().isEmpty()) {
            url = "jdbc:sqlserver://" + serverName + ":" + portNumber + ";databaseName=" + dbName + ";encrypt=true;trustServerCertificate=true;";
        }
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        return DriverManager.getConnection(url, userID, password);
    }
//    private final String serverName = "localhost";
//    private final String instance = "NGUYENMINHHAU";
    private final String serverName = "NGUYENMINHHAU\\NGUYENMINHHAU";
    private final String instance = "";
    private final String dbName = "ManagerPhoneStore";
    private final String portNumber = "1433";

    private final String userID = "sa";
    private final String password = "nmhau06022003";

    //Trả về kết quả bảng 
    public ResultSet getTable(String Name) throws Exception {
        String sql = "select * from " + Name;
        Connection con = getConnection();
//        PreparedStatement st = con.prepareStatement(sql);
//        ResultSet rs = st.executeQuery();
        Statement statement = con.createStatement();
        ResultSet rs = statement.executeQuery(sql);
        return rs;
    }

}
