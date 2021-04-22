/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Services;

import CONNECTION.DataSource;
import Entity.Candidate;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 *
 * @author yessine darmoul
 */
public class CandidateSession {

    private LocalDate last_login;
    public static Candidate currentCandidate = new Candidate();
    Connection cnx;
    private DataSource conn;
    Statement ste;
    ResultSet rs;
    PreparedStatement pst;

    public Candidate login(String email){
        Candidate candidate = new Candidate();

        try {
            String requete = "select * from Candidate where email='"+email+"'";
            Statement st = cnx.createStatement();
            ResultSet rs = st.executeQuery(requete);
            int count = 0;
            while(rs.next()){
                count ++;
                candidate.setId(rs.getInt(1));
                candidate.setFirstname(rs.getString(2));
                candidate.setPassword(rs.getString(5));
                candidate.setEmail(rs.getString(4));
                candidate.setPhonenumber(rs.getString(9));
                candidate.setAddress(rs.getString(10));
            
            
            
            }
            
            System.out.println(count);
            if(count == 0){
                 Candidate a = new Candidate();
                 a.setPassword("");
                return a;
            }else{
                return candidate;
            }
        } catch (SQLException ex) {
            Logger.getLogger(CandidateSession.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
        
    }
    
    public CandidateSession() {
        this.cnx = DataSource.getInstance().getCnx();
        try {
            ste = cnx.createStatement();
        } catch (SQLException ex) {
            Logger.getLogger(CandidateSession.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public LocalDate getLast_login() {
        return last_login;
    }

    public void setLast_login(LocalDate last_login) {
        this.last_login = last_login;
    }

    public CandidateSession CandidateSessionLogOut() {
        currentCandidate.setFirstname("");
        currentCandidate.setEmail("");
        currentCandidate.setId(0);
        return null;
    }
//verifier login lors de la connexion
    public static boolean isLogedIn(Candidate candidate) {
        if ((candidate.getEmail() == "" && candidate.getFirstname() == "" && (candidate.getBirthday() == null ) && candidate.getAddress() == null)) {
            return false;
        } else {
            return true;
        }
    }
  

    public boolean isValidCandidate(String Email) {
        String req = "SELECT * FROM `Candidate` WHERE email =\'" + Email + "\'";
        try {
            rs = ste.executeQuery(req);
            System.out.println(rs);
            if (rs != null) {
                while (rs.next()) {
                    System.out.println(rs.toString());
                    return true;
                }
            }

        } catch (SQLException ex) {
            Logger.getLogger(Candidate.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }  
            
  
}