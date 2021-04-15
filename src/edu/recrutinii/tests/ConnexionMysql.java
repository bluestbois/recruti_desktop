/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.recrutinii.tests;

import java.sql.*;

/**
 *
 * @author asus
 */
public class ConnexionMysql {
    public Connection cn=null;
    public static Connection connexionDB (){
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection cn= DriverManager.getConnection("jdbc:mysql://localhost:3306/recrutini","root","");
            System.out.println("Connexion reussite");
            return cn;
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("Connexion echoué");
            e.printStackTrace();
        }return null;
    }
    
    
}




