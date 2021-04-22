/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Services;

import CONNECTION.DataSource;
import Entity.Candidate;


import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.util.Duration;
import tray.animations.AnimationType;
import tray.notification.NotificationType;
import tray.notification.TrayNotification;



/**
 *
 * @author yessine darmoul
 */
public class Service_Candidate implements CRUD<Candidate>{
    
    private PreparedStatement pst = null;
    private PreparedStatement pst2 = null;
    private final DataSource conn;
    private String sql;
    ResultSet rs;
      public static Candidate currentCandidate = new Candidate();
    public Service_Candidate() throws SQLException{
        conn = DataSource.getInstance();
    }
    @Override
    public int insert(Candidate i) {
          String md5 = getMd5(i.getPassword());
       
       String requete = "insert into Candidate (id,first_name,last_name,email,password,birthday,gender,nationality,address,phone_number,image,loe,experience,token) values (?,?,?,?,?,?,?,?,?,?)";
        try {
            pst =conn.getCnx().prepareStatement(requete);
            pst.setString(1, i.getFirstname());
            pst.setString(3, i.getEmail());
            pst.setString(4, md5);
            pst.setInt(4, i.getId());
            pst.setString(9, i.getPhonenumber());
             pst.setDate(5, (java.sql.Date) i.getBirthday());
            
            pst.setString(8, i.getAddress());
            
            pst.setInt(8, 1);
            pst.setInt(9, 0);
            pst.setFloat(10, 0);
 
            pst.executeUpdate();
            return 1;
        } catch (SQLException ex) {
            Logger.getLogger(Service_Candidate.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        }
    }


    @Override
    public int SingIn(Candidate si1) {
               try {
            pst = conn.getCnx().prepareStatement("SELECT * FROM Candidate WHERE  email = ? and password = ?");
            pst.setString(1, si1.getEmail());
            pst.setString(2, si1.getPassword());
            rs = pst.executeQuery();
     if (rs != null) {
                while (rs.next()) {
                    System.out.println(rs.getString(3));
                    System.out.println(rs.getString(2));
                 
                    currentCandidate.setId(1);
                    currentCandidate.setFirstname(rs.getString(2));
                currentCandidate.setEmail(rs.getString(4));
                
                    TrayNotification tray = new TrayNotification();
            AnimationType type = AnimationType.POPUP;
        
            tray.setAnimationType(type);
            tray.setTitle("LOGGED IN");
            tray.setMessage("WELCOME  "+""+currentCandidate.getFirstname());
            tray.setNotificationType(NotificationType.SUCCESS);
            tray.dismiss();
            tray.showAndDismiss(Duration.millis(3000));
                 
                   
                    return 1;
                }
            }

        } catch (SQLException ex) {
            Logger.getLogger(Service_Candidate.class.getName()).log(Level.SEVERE, null, ex);
        }
      return 0;
    }

    @Override
    public int VerifierCompte(Candidate verif, String code) {
          try {
            String requete = "SELECT * FROM Candidate Where password =? and email = ?  ";
            pst = conn.getCnx().prepareStatement(requete);
            pst.setString(1, verif.getPassword());
            pst.setString(3, verif.getEmail());
          

            rs = pst.executeQuery();

            boolean v = rs.next();

            if (v == true) {
                if ((rs.getString(10).equals(code)) == false) {
                   Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error DIALOG");
            alert.setHeaderText(null);
            alert.setContentText("Account not verified "
                    + "\n code is not correct");
            alert.showAndWait();
                    return 0;
                } else {
                    int idU = rs.getInt("id");
                    System.out.println(idU);
                    String req = "UPDATE Candidate set enabled=? where id=? ";
                    try {
                        pst2 = conn.getCnx().prepareStatement(req);
                        pst2.setInt(1, 1);
                        pst2.setInt(2, idU);
                        pst2.executeUpdate();
                          Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("INFORMATION DIALOG");
            alert.setHeaderText(null);
            alert.setContentText("Account has been verified");
            alert.showAndWait();
                        return 1;
                    } catch (SQLException ex) {
                        Logger.getLogger(Service_Candidate.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }

            }
        } catch (SQLException ex) {
            Logger.getLogger(Service_Candidate.class.getName()).log(Level.SEVERE, null, ex);
        }
          return 0;
    }

   

    @Override
    public String getMd5(String input) {
      //  throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
       try { 
  
            // Static getInstance method is called with hashing MD5 
            MessageDigest md = MessageDigest.getInstance("MD5"); 
  
            // digest() method is called to calculate message digest 
            //  of an input digest() return array of byte 
            byte[] messageDigest = md.digest(input.getBytes()); 
  
            // Convert byte array into signum representation 
            BigInteger no = new BigInteger(1, messageDigest); 
  
            // Convert message digest into hex value 
            String hashtext = no.toString(16); 
            while (hashtext.length() < 32) { 
                hashtext = "0" + hashtext; 
            } 
            return hashtext; 
        }  
  
        // For specifying wrong message digest algorithms 
        catch (NoSuchAlgorithmException e) { 
            throw new RuntimeException(e); 
        } 
    
    }

    /**
     *
     * @return
     */
   
    public ArrayList<Candidate> getshow() {
             Statement ste = null;  
        ArrayList<Candidate> A = new ArrayList<>();
        String req = "SELECT * FROM `Candidate`";
        System.out.println(req);
        try {
            rs = ste.executeQuery(req);
            System.out.println(rs);
            if (rs != null) {
                while (rs.next()) {
                    Candidate c = new Candidate();
                    c.setFirstname(rs.getString(1));
                    c.setEmail(rs.getString(3));
                    
                    c.setPhonenumber(rs.getString(8));

                    
                    A.add(c);

                }
            }

        } catch (SQLException ex) {
            Logger.getLogger(Candidate.class.getName()).log(Level.SEVERE, null, ex);
        }

        return A;
    }
      public static Candidate getCurrentCandidate() {
        return currentCandidate;
    }

    public static void setCurrentCandidate(Candidate currentCandidate) {
        Service_Candidate.currentCandidate = currentCandidate;
    }
    public int getmails(String email) throws SQLException {
        int i = 0;
        //pst = cnx.createStatement();
        pst = conn.getCnx().prepareStatement("SELECT * FROM candidate WHERE email='" + email + "' ;");
        ResultSet rs = pst.executeQuery("SELECT * FROM candidate WHERE email='" + email + "' ;");
        while (rs.next()) {
            i++;
        }
        return i;
    }
        public boolean auth(String email, String pwd) throws SQLException {
        String req = "select * from candidate where ((email ='" + email + "') AND password='" + pwd + "') ";

        Boolean i = false;

        try {
            pst = conn.getCnx().prepareStatement(req);
            ResultSet rs = pst.executeQuery(req);
            while (rs.next()) {
                i = true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(Service_Candidate.class.getName()).log(Level.SEVERE, null, ex);
        }

        return i;
    }
}
