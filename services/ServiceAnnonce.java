/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package esprit.recrutini.services;

import esprit.recrutini.entities.Annonce;
import Utils.DataBase;
import esprit.recrutini.tools.DataSource;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import static java.sql.Types.NULL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author ines
 */
public class ServiceAnnonce implements esprit.recrutini.services.IServiceAnnonce {

    private Connection cnx;
    private Statement ste;
    private PreparedStatement pre;

    public ServiceAnnonce() {
        cnx = DataBase.getInstance().getConnection();
    }

    @Override
    public ArrayList<Annonce> DisplayNEW() throws SQLException {
        ArrayList<Annonce> TabA = new ArrayList<>();
        String req = "SELECT * FROM freelance ";
        PreparedStatement p;
        p = cnx.prepareStatement(req);
        ResultSet rs = p.executeQuery();
        while (rs.next()) {

            LocalDate today = LocalDate.now();

            if ((rs.getDate(6).toLocalDate().plusDays(10)).compareTo(today) > 0) {
                java.sql.Date sqlDate = java.sql.Date.valueOf(rs.getDate(6).toLocalDate());

                TabA.add(new Annonce(rs.getInt(1), rs.getInt(2), rs.getString(3), rs.getString(4), rs.getString(5), sqlDate, rs.getString(7)));
            } else {
                pre = cnx.prepareStatement("UPDATE freelance SET  etat = 'non active '  WHERE id = ?");
                pre.setInt(1, rs.getInt(1));

                pre.executeUpdate();
            }
        }
        return TabA;
    }

    @Override
    public ArrayList<Annonce> DisplayAll() throws SQLException {
        ArrayList<Annonce> TabA = new ArrayList<>();
        String req = "SELECT * FROM freelance";
        PreparedStatement p;
        p = cnx.prepareStatement(req);
        ResultSet rs = p.executeQuery();
        while (rs.next()) {

            {
                java.sql.Date sqlDate = java.sql.Date.valueOf(rs.getDate(6).toLocalDate());

                TabA.add(new Annonce(rs.getInt(1), rs.getInt(2), rs.getString(3), rs.getString(4), rs.getString(5), sqlDate, rs.getString(7)));

            }
        }
        return TabA;
    }

    @Override
    public void ajouter(Annonce A) throws SQLException {
        java.sql.Date sqldate = java.sql.Date.valueOf(A.getDate().toLocalDate());

        pre = cnx.prepareStatement("INSERT INTO freelance ( `project_id` ,`title`, `description`, `salary`, `date` , `etat`) VALUES ( ?, ?, ?, ?,?,?);");
        pre.setInt(1, A.getProject_id());
        pre.setString(2, A.getTitle());
        pre.setString(3, A.getDescription());
        pre.setString(4, A.getSalary());
        pre.setDate(5, sqldate);
        pre.setString(6, A.getEtat());

        pre.executeUpdate();
    }

    @Override
    public void modifier(Annonce A) throws SQLException {
        java.sql.Date sqldate = java.sql.Date.valueOf(A.getDate().toLocalDate());

        pre = cnx.prepareStatement("UPDATE freelance SET project_id=? ,title=?, description = ? ,salary=? , date = ? WHERE id = ?");
        pre.setInt(1, A.getProject_id());
        pre.setString(2, A.getTitle());
        pre.setString(3, A.getDescription());
        pre.setString(4, A.getSalary());

        pre.setDate(5, sqldate);

        pre.setInt(6, A.getId());
        pre.executeUpdate();
    }

    @Override
    public boolean delete(int id) throws SQLException {
        pre = cnx.prepareStatement("delete from  freelance where id = '" + id + "'");
        pre.execute();
        return true;
    }

    @Override
    public Annonce details(int id) throws SQLException {
        String req = "select * from freelance where id ='" + id + "'";

        Annonce A = null;

        try {
            ste = cnx.createStatement();
            ResultSet rs = ste.executeQuery(req);
            while (rs.next()) {
                java.sql.Date sqlDate = java.sql.Date.valueOf(rs.getDate(6).toLocalDate());

                A = new Annonce(rs.getInt(1), rs.getInt(2), rs.getString(3), rs.getString(4), rs.getString(5), sqlDate, rs.getString(7));
            }
        } catch (SQLException ex) {
            Logger.getLogger(ServiceProjet.class.getName()).log(Level.SEVERE, null, ex);
        }

        return A;
    }

    @Override
    public int count() {
        try {
            String req = "select count(*) AS total from freelance ";
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

    @Override
    public int count_active() {
        try {
            String req = "select count(*) AS total from freelance where etat='active' ";
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

    @Override
    public int count_non_active() {
        try {
            String req = "select count(*) AS total from freelance where etat='non active' ";
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

    @Override
    public ArrayList<Annonce> triParTitre() throws SQLException {
        ArrayList<Annonce> TabA = new ArrayList<>();
        String req = "SELECT * FROM freelance order by title  ";
        PreparedStatement p;
        p = cnx.prepareStatement(req);
        ResultSet rs = p.executeQuery();
        while (rs.next()) {

            java.sql.Date sqlDate = java.sql.Date.valueOf(rs.getDate(6).toLocalDate());

            TabA.add(new Annonce(rs.getInt(1), rs.getInt(2), rs.getString(3), rs.getString(4), rs.getString(5), sqlDate, rs.getString(7)));
        }
        return TabA;
    }

    @Override
    public ArrayList<Annonce> triParDate() throws SQLException {
        ArrayList<Annonce> TabA = new ArrayList<>();
        String req = "SELECT * FROM freelance order by date ";
        PreparedStatement p;
        p = cnx.prepareStatement(req);
        ResultSet rs = p.executeQuery();
        while (rs.next()) {

            java.sql.Date sqlDate = java.sql.Date.valueOf(rs.getDate(6).toLocalDate());

            TabA.add(new Annonce(rs.getInt(1), rs.getInt(2), rs.getString(3), rs.getString(4), rs.getString(5), sqlDate, rs.getString(7)));
        }
        return TabA;
    }

    public void apply(int id, int job, int freelance, Date date, int score) throws SQLException {
        String query = "INSERT INTO Recrutini.`Candidature` (Candidate_id,`job_id`,`freelance_id`,`date`,`score`) VALUES (?,?,?,?,?)";
        PreparedStatement pst = null;
        DataSource con = DataSource.getInstance();

        pst = con.getCnx().prepareStatement(query);
        pst.setInt(1, id);
        pst.setNull(2, NULL);
        pst.setInt(3, freelance);
        pst.setDate(4, date);
        pst.setInt(5, score);
        pst.executeUpdate();

        JOptionPane.showMessageDialog(null, "job/Freelance affected successfully");
    }

    public int checkapply(int id, int id1) throws SQLException {
        ResultSet rs;
        PreparedStatement pst;
        DataSource con = DataSource.getInstance();
        String requete = "SELECT * FROM Candidature Where candidate_id =? and freelance_id = ?  ";
        pst = con.getCnx().prepareStatement(requete);
        pst.setInt(1, id);
        pst.setInt(2, id1);
        rs = pst.executeQuery();
        if (rs.next()) {
            return 1;
        } else {
            return 0;
        }

    }
}