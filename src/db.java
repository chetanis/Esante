
import java.sql.Connection;
import java.sql.DriverManager;
import javax.swing.JOptionPane;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author anischetouane
 */
public class db {
    Connection conn=null;
    public static Connection java_db(){
        
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            String connectionString = "jdbc:mysql://localhost/ESante";
            String password="ANis2004";
            String user="root";


            
            Connection conn =DriverManager.getConnection(connectionString,user,password);

//            JOptionPane.showMessageDialog(null, "connecter");
            return conn;
           
            
        }catch (Exception e){
            JOptionPane.showMessageDialog(null, e);
            return null;
        }
        
    }
}
