/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Service;

import Entite.Job;
import Iservice.IServiceJob;
import Utils.DataBase;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author asus
 */
public class ServiceJob implements IServiceJob {

    private Connection cnx;
    private Statement ste;
    private PreparedStatement pre;

    public ServiceJob() {
        cnx = DataBase.getInstance().getConnection();
    }

    @Override
    public ArrayList<Job> DisplayAll(int from, int to) throws SQLException {
        ArrayList<Job> TabA = new ArrayList<>();
        String req = "SELECT * FROM job limit " + from + ", " + to + "  ";
        PreparedStatement p;
        p = cnx.prepareStatement(req);
        ResultSet rs = p.executeQuery();
        while (rs.next()) {
            java.sql.Date sqlDate = java.sql.Date.valueOf(rs.getDate(4).toLocalDate());

            TabA.add(new Job(rs.getInt(1), rs.getString(3), rs.getString(5), sqlDate, rs.getInt(2)));

        }
        return TabA;
    }

    @Override
    public List<Job> Display_OF_RECRUITER(int id , int from, int to) throws SQLException {
        ArrayList<Job> TabA = new ArrayList<>();
        String req = "SELECT * FROM job where recruiter_id= '" + id + "'  limit " + from + ", " + to + " ";
        PreparedStatement p;
        p = cnx.prepareStatement(req);
        ResultSet rs = p.executeQuery();
        while (rs.next()) {
            java.sql.Date sqlDate = java.sql.Date.valueOf(rs.getDate(4).toLocalDate());

            TabA.add(new Job(rs.getInt(1), rs.getString(3), rs.getString(5), sqlDate, rs.getInt(2)));

        }
        return TabA;
    }

    @Override
    public void ajouter(Job J) throws SQLException {
        java.sql.Date sqldate = java.sql.Date.valueOf(J.getDate().toLocalDate());

        pre = cnx.prepareStatement("INSERT INTO job ( `id` ,`recruiter_id`, `title`, `description`, `date` ) VALUES ( ?, ?, ?, ?,?);");
        pre.setInt(1, J.getId());
        pre.setInt(2, J.getRecruiter_id());
        pre.setString(3, J.getTitle());
        pre.setString(4, J.getDescription());

        pre.setDate(5, sqldate);

        pre.executeUpdate();
    }

    @Override
    public void modifier(Job J) throws SQLException {
        java.sql.Date sqldate = java.sql.Date.valueOf(J.getDate().toLocalDate());

        pre = cnx.prepareStatement("UPDATE job SET  title=?, description = ? , date = ? WHERE id = ?");

        pre.setString(1, J.getTitle());
        pre.setString(2, J.getDescription());

        pre.setDate(3, sqldate);
   pre.setInt(4, J.getId());
        pre.executeUpdate();
    }

    @Override
    public boolean delete(int id) throws SQLException {
        pre = cnx.prepareStatement("delete from  job where id = '" + id + "'");
        pre.execute();
        return true;
    }

    @Override
    public Job details(int id) throws SQLException {
        Job J = null;

        String req = "SELECT * FROM job where id= '" + id + "'";
        
        PreparedStatement p;
        p = cnx.prepareStatement(req);
        ResultSet rs = p.executeQuery();
        while (rs.next()) {
            java.sql.Date sqlDate = java.sql.Date.valueOf(rs.getDate(4).toLocalDate());
            J = new Job(rs.getInt(1), rs.getString(3), rs.getString(5), sqlDate, rs.getInt(2));
     
        }
        return J;
    }

    @Override
    public int count(int id ) {
         try {
            String req = "select count(*) AS total from job  where recruiter_id= '" + id + "' ";
            ste = cnx.createStatement();
            ResultSet rs = ste.executeQuery(req);
            rs.next();
            int count = rs.getInt(1);
            return count;
        } catch (SQLException ex) {
            Logger.getLogger(ServiceJob.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }
    @Override
    public int count2() {
         try {
            String req = "select count(*) AS total from job   ";
            ste = cnx.createStatement();
            ResultSet rs = ste.executeQuery(req);
            rs.next();
            int count = rs.getInt(1);
            return count;
        } catch (SQLException ex) {
            Logger.getLogger(ServiceJob.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }
}
