/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package esprit.recrutini.services;

import esprit.recrutini.entities.Candidate;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javax.swing.JOptionPane;
import esprit.recrutini.entities.Forum;
import esprit.recrutini.entities.Post;
import esprit.recrutini.entities.Recruiter;
import esprit.recrutini.tools.DataSource;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ASUS
 */
public class ForumCRUD {

    public void addForumRecruiter(Forum f) {
        try {
            String requete = "INSERT INTO forum (recruiter_id,title,description)"
                    + "VALUES (?,?,?)";
            PreparedStatement pst = DataSource.getInstance().getCnx().prepareStatement(requete);
            pst.setInt(1, f.getIdR());
            pst.setString(2, f.getTitle());
            pst.setString(3, f.getDescription());

            pst.executeUpdate();
            System.out.println("Forum added!");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

    }

    public void addForumCandidate(Forum f) {
        try {
            String requete = "INSERT INTO forum (title,description,candidate_id)"
                    + "VALUES (?,?,?)";
            PreparedStatement pst = DataSource.getInstance().getCnx().prepareStatement(requete);

            pst.setString(1, f.getTitle());
            pst.setString(2, f.getDescription());
            pst.setInt(3, f.getIdC());
            pst.executeUpdate();
            System.out.println("Forum added!");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

    }

    public ObservableList<Forum> readAlldiscc() throws SQLException {
        ObservableList oblistdisc = FXCollections.observableArrayList();

        Statement st = DataSource.getInstance().getCnx().createStatement();
        ResultSet rs = st.executeQuery("select * from forum ;");
        while (rs.next()) {
            int id = rs.getInt("id");
            String title = rs.getString("title");
            String description = rs.getString("description");

            Forum d = new Forum(id, title, description);
            oblistdisc.add(d);

        }
        return oblistdisc;
    }

    public List<Forum> getForums() {
        List<Forum> myList = new ArrayList();
        try {
            String requete = "SELECT * FROM forum";
            Statement st = DataSource.getInstance().getCnx().createStatement();
            ResultSet rs = st.executeQuery(requete);
            while (rs.next()) {
                Forum f = new Forum();
                f.setId(rs.getInt(1));
                f.setTitle(rs.getString("title"));
                f.setDescription(rs.getString("description"));
                myList.add(f);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return myList;
    }

    public boolean delete(int dd) throws SQLException {

        PreparedStatement pre = DataSource.getInstance().getCnx().prepareStatement("DELETE FROM forum WHERE id ='" + dd + "' ;");
        pre.executeUpdate();

        return true;
    }

    public boolean update(int id, String title, String description) throws SQLException {
        PreparedStatement pre = DataSource.getInstance().getCnx().prepareStatement("UPDATE forum SET title= '" + title + "' , description='" + description + "' WHERE id='" + id + "' ;");
        JOptionPane.showMessageDialog(null, "Forum update avec succées");
        pre.executeUpdate();
        return true;
    }

    public ObservableList<Forum> saerchTitle(String titlee) throws SQLException {
        ObservableList<Forum> arr = FXCollections.observableArrayList();
        Statement st = DataSource.getInstance().getCnx().createStatement();
        ResultSet rs = st.executeQuery("select * from forum where title='" + titlee + "';");
        while (rs.next()) {
            int id = rs.getInt(1);

            String title = rs.getString("title");
            String description = rs.getString("description");

            Forum f = new Forum(id, title, description);
            arr.add(f);

        }
        return arr;

    }

    public Forum findById(int id) {

        try {
            String request = "SELECT * FROM forum where id = " + id;
            PreparedStatement pst = DataSource.getInstance().getCnx().prepareStatement(request);
            ResultSet rs = pst.executeQuery(request);
            Forum forum = new Forum();
            while (rs.next()) {

                forum.setId(rs.getInt("id"));
                forum.setTitle(rs.getString("title"));
                forum.setDescription(rs.getString("description"));

            }
            return forum;

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return null;
    }

    public List<String> getForumsTitle() {
        List<String> myList = new ArrayList();
        try {

            ResultSet rs = DataSource.getInstance().getCnx().createStatement().executeQuery("select * from  forum ;");
            while (rs.next()) {
                String title = rs.getString("title");
                myList.add(title);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return myList;
    }

    public Forum findByIdC(int idF) {

        try {
            String request = "SELECT * FROM forum where id = '" + idF + "';";
            PreparedStatement pst = DataSource.getInstance().getCnx().prepareStatement(request);
            ResultSet rs = pst.executeQuery(request);
            Forum forum = new Forum();
            while (rs.next()) {

                forum.setId(rs.getInt("id"));
                forum.setTitle(rs.getString("title"));
                forum.setDescription(rs.getString("description"));
                forum.setIdC(rs.getInt("candidate_id"));

            }
            return forum;

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return null;
    }

    public Forum findByIdR(int idF) {

        try {
            String request = "SELECT * FROM forum where id = '" + idF + "';";
            PreparedStatement pst = DataSource.getInstance().getCnx().prepareStatement(request);
            ResultSet rs = pst.executeQuery(request);
            Forum forum = new Forum();
            while (rs.next()) {

                forum.setId(rs.getInt("id"));
                forum.setIdR(rs.getInt("recruiter_id"));
               // System.out.println("idR requette:"+forum.getIdR());
            }
            return forum;

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return null;
    }

    public Candidate findByIDC(int idC) {
        try {
            String request = "SELECT * FROM candidate where id = '" + idC + "';";
            PreparedStatement pst = DataSource.getInstance().getCnx().prepareStatement(request);
            ResultSet rs = pst.executeQuery(request);
            Candidate candidate = new Candidate();
            while (rs.next()) {

                candidate.setId(rs.getInt("id"));
                candidate.setFirstname(rs.getString("first_name"));
                candidate.setLastname(rs.getString("last_name"));

            }
            return candidate;
        } catch (SQLException ex) {
            Logger.getLogger(ForumCRUD.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public Recruiter finfByIDR(int idR) {
        try {
            String request = "SELECT * FROM recruiter where id = '" + idR + "';";
            PreparedStatement pst = DataSource.getInstance().getCnx().prepareStatement(request);
            ResultSet rs = pst.executeQuery(request);
            Recruiter recruiter = new Recruiter();
            while (rs.next()) {

                recruiter.setId(rs.getInt("id"));
                recruiter.setName(rs.getString("name"));

            }
            return recruiter;
        } catch (SQLException ex) {
            Logger.getLogger(ForumCRUD.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
