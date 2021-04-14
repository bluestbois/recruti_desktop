/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package esprit.Recrutini.tools;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * 
 * @author amine
 */
public class MyConnection {
   private final String username = "root";
   private final String password = "";
   private final String url = "jdbc:mysql://localhost:3306/recrutini";
   private Connection connection;
   private static MyConnection instance;
   
    private MyConnection() {
       try {
           connection = DriverManager.getConnection(url, username, password);
       } catch (SQLException ex) {
           System.out.println(ex.getMessage());
       }
    }
    
    
    public Connection getConnection(){
        return connection;
    }
    
    public static MyConnection getInstance(){
        if(instance == null)
            instance = new MyConnection();
        return instance;
    }
}