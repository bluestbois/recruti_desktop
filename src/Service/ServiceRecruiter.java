/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Service;

import Entite.Recruiter;
import Iservice.IServiceRecruiter;
import Utils.DataBase;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author asus
 */
public class ServiceRecruiter implements IServiceRecruiter {

    private Connection cnx;
    private Statement ste;
    private PreparedStatement pre;

    public ServiceRecruiter() {
        cnx = DataBase.getInstance().getConnection();
    }

    @Override
    public void ajouter(Recruiter R) throws SQLException {

        Random rand = new Random();
        int alea = rand.nextInt(200000 - 10 + 1) + 200000;

        pre = cnx.prepareStatement("INSERT INTO recruiter ( `field_id` ,`name`, `username`, `email`, `password` , `address`, `description`, `phone_number`, `image`, `randoom`) VALUES ( ?, ?, ?, ?,?,?,?,?,?,?);");
        pre.setString(1, R.getField());
        pre.setString(2, R.getName());
        pre.setString(3, R.getUsername());
        pre.setString(4, R.getEmail());
        pre.setString(5, R.getPassword());
        pre.setString(6, R.getAdress());
        pre.setString(7, R.getDescription());
        pre.setString(8, R.getPhone());
        pre.setString(9, R.getImage());
        pre.setInt(10, alea);
        pre.executeUpdate();
    }

    @Override
    public boolean delete(int id) throws SQLException {
         pre = cnx.prepareStatement("delete from  job where recruiter_id = '" + id + "'");
        pre.execute();
        pre = cnx.prepareStatement("delete from  recruiter where id = '" + id + "'");
        pre.execute();
        return true;
    }

    @Override
    public boolean update(Recruiter R) throws SQLException {
        pre = cnx.prepareStatement("UPDATE recruiter SET field_id=? ,name=?    , address = ? , description = ? , phone_number = ? , image = ?  WHERE id = ?");
        pre.setString(1, R.getField());
        pre.setString(2, R.getName());
        pre.setString(3, R.getAdress());
        pre.setString(4, R.getDescription());
        pre.setString(5, R.getPhone());
        pre.setString(6, R.getImage());
        pre.setInt(7, R.getId());
        pre.executeUpdate();
        return true;

    }
   @Override
    public Recruiter getRecruiter(String email) throws SQLException {
        String req = "select * from recruiter where email ='" + email + "' ";

        Recruiter R = null;

        try {
            ste = cnx.createStatement();
            ResultSet rs = ste.executeQuery(req);
            while (rs.next()) {

                R = new Recruiter(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8), rs.getString(9), rs.getString(10), rs.getInt(11));
            }
        } catch (SQLException ex) {
            Logger.getLogger(ServiceRecruiter.class.getName()).log(Level.SEVERE, null, ex);
        }

        return R;
    }

    
    @Override
    public int getID(String var) throws SQLException {
        String req = "select id from recruiter where (username ='" + var + "' || email ='" + var + "')";

        int id = 0 ;

        try {
            ste = cnx.createStatement();
            ResultSet rs = ste.executeQuery(req);
            while (rs.next()) {
            id = rs.getInt(1);
              
            }
        } catch (SQLException ex) {
            Logger.getLogger(ServiceRecruiter.class.getName()).log(Level.SEVERE, null, ex);
        }

        return id;
    }

    @Override
    public boolean auth(String var, String pwd) throws SQLException {
        String req = "select * from recruiter where ((username ='" + var + "' || email ='" + var + "') AND password='" + pwd + "') ";

        Boolean i = false;

        try {
            ste = cnx.createStatement();
            ResultSet rs = ste.executeQuery(req);
            while (rs.next()) {
                i = true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(ServiceRecruiter.class.getName()).log(Level.SEVERE, null, ex);
        }

        return i;
    }

    @Override
    public int forgotpass(String mail) throws SQLException {

        int a =0;
        ste = cnx.createStatement();
        ResultSet rs = ste.executeQuery("SELECT randoom FROM recruiter WHERE email='" + mail + "' ;");
        while (rs.next()) {
            a = rs.getInt("randoom");
        }
        return a;
    }

    @Override
    public int getToken(int token) throws SQLException {
        int i = 0;
     
        ste = cnx.createStatement();
        ResultSet rs = ste.executeQuery("SELECT * FROM recruiter WHERE randoom='" + token + "' ;");
        while (rs.next()) {
            i++;
        }
        return i;
    }

    @Override
    public void UpdateToken(String mail) throws SQLException {
            Random rand = new Random();
        int alea = rand.nextInt(200000 - 10 + 1) + 200000;
   PreparedStatement pre=cnx.prepareStatement("UPDATE recruiter SET randoom='"+alea+"' WHERE email='"+mail+"' ;");
pre.executeUpdate();
    }
    @Override
    public int getmails(String mail) throws SQLException {
        int i = 0;
        ste = cnx.createStatement();
        ResultSet rs = ste.executeQuery("SELECT * FROM recruiter WHERE email='" + mail + "' ;");
        while (rs.next()) {
            i++;
        }
        return i;
    }

    @Override
    public int getUsername(String username) throws SQLException {
       int i = 0;
        ste = cnx.createStatement();
        ResultSet rs = ste.executeQuery("SELECT * FROM recruiter WHERE username='" + username + "' ;");
        while (rs.next()) {
            i++;
        }
        return i;
    }

    @Override
    public int getTel(String tel) throws SQLException {
         int i  = 0;
        ste = cnx.createStatement();
        ResultSet rs = ste.executeQuery("SELECT * FROM recruiter WHERE phone_number='" + tel + "' ;");
        while (rs.next()) {
            i++;
        }
        return i;
    }
  @Override
    public void updatepassword(String mail,String pass) throws SQLException {
PreparedStatement pre=cnx.prepareStatement("UPDATE recruiter SET password='"+pass+"' WHERE email='"+mail+"' ;");
pre.executeUpdate();
    }
}
