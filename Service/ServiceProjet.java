/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Service;

import Utils.DataBase;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import Entite.Projet;
import Entite.Annonce;

/**
 *
 * @author ines
 */
public class ServiceProjet implements Iservice.IServiceProjet {

    private Connection cnx;
    private Statement ste;
    private PreparedStatement pre;

    public ServiceProjet() {
        cnx = DataBase.getInstance().getConnection();
    }

    /**
     *
     * @return @throws SQLException
     */
    @Override
    public ArrayList<Projet> DisplayAll() throws SQLException {
        ArrayList<Projet> TabP = new ArrayList<>();
        String req = "SELECT * FROM project";
        PreparedStatement p;
        p = cnx.prepareStatement(req);
        ResultSet rs = p.executeQuery();
        while (rs.next()) {

            java.sql.Date sqlDate = java.sql.Date.valueOf(rs.getDate(5).toLocalDate());
 

            TabP.add(new Projet(rs.getInt(1), rs.getInt(2), rs.getString(3), rs.getString(4), sqlDate,rs.getString(6)));
        }
        return TabP;
    }

    @Override
    public void ajouter(Projet P) throws SQLException {

        java.sql.Date sqldate = java.sql.Date.valueOf(P.getDate().toLocalDate());
     
        pre = cnx.prepareStatement("INSERT INTO project ( `recruiter_id` ,`title`, `description`,  `date`,`image`) VALUES ( ?, ?, ?, ?,?);");
         pre.setInt(1, P.getRecruiter_id());
        pre.setString(2, P.getTitle());
        pre.setString(3, P.getDescription());
        pre.setDate(4, sqldate);
         pre.setString(5, P.getImage());
        pre.executeUpdate();

    }

    @Override
    public void modifier(Projet P) throws SQLException {
        java.sql.Date sqldate = java.sql.Date.valueOf(P.getDate().toLocalDate());
     
        pre = cnx.prepareStatement("UPDATE project SET title=?, description = ? , date = ? , image = ? WHERE id = ?");
        pre.setString(1, P.getTitle());
        pre.setString(2, P.getDescription());
   

        pre.setDate(3, sqldate);
        pre.setString(4, P.getImage());
        pre.setInt(5, P.getId());
        pre.executeUpdate();

    }

    @Override
    public boolean delete(int id) throws SQLException {

        pre = cnx.prepareStatement("delete from  freelance where project_id = '" + id + "'");
       pre.execute(); 
       pre = cnx.prepareStatement("delete from  project where id = '" + id + "'");
       pre.execute();
       
        return true;
    }

    @Override
    public Projet details(int id) throws SQLException {

        String req = "select * from project where id ='" + id + "'";

        Projet P = null;

        try {
            ste = cnx.createStatement();
            ResultSet rs = ste.executeQuery(req);
            while (rs.next()) {
                java.sql.Date sqlDate = java.sql.Date.valueOf(rs.getDate(5).toLocalDate());
     
               
           P= new Projet(rs.getInt(1), rs.getInt(2), rs.getString(3), rs.getString(4), sqlDate , rs.getString(6));
            }
        } catch (SQLException ex) {
            Logger.getLogger(ServiceProjet.class.getName()).log(Level.SEVERE, null, ex);
        }

        return P;
    }

    @Override
    public int count() {
        try {
            String req = "select count(*) AS total from project ";
            ste = cnx.createStatement();
            ResultSet rs = ste.executeQuery(req);
            rs.next();
            int count = rs.getInt(1);
            return count;
        } catch (SQLException ex) {
            Logger.getLogger(ServiceProjet.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }

    /**
     *
     * @return
     * @throws SQLException
     */
    @Override
    public ArrayList<Projet> triParTitre() throws SQLException {
        ArrayList<Projet> TabP = new ArrayList<>();
        String req = "SELECT * FROM project order by title ";
        PreparedStatement p;
        p = cnx.prepareStatement(req);
        ResultSet rs = p.executeQuery();
        while (rs.next()) {
            java.sql.Date sqlDate = java.sql.Date.valueOf(rs.getDate(5).toLocalDate());
         
            TabP.add(new Projet(rs.getInt(1), rs.getInt(2), rs.getString(3), rs.getString(4), sqlDate , rs.getString(6)));
        }
        return TabP;
    }

      @Override
    public ArrayList<Projet> triParDate() throws SQLException  {
            ArrayList<Projet> TabP = new ArrayList<>();
        String req = "SELECT * FROM project order by date ";
        PreparedStatement p;
        p = cnx.prepareStatement(req);
        ResultSet rs = p.executeQuery();
        while (rs.next()) {
            java.sql.Date sqlDate = java.sql.Date.valueOf(rs.getDate(5).toLocalDate());
         
            TabP.add(new Projet(rs.getInt(1), rs.getInt(2), rs.getString(3), rs.getString(4), sqlDate,rs.getString(6)));
        }
        return TabP;
    }
    
    @Override
    public ArrayList DetailsRecruteur() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

  
}
