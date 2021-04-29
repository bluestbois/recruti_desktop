/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package esprit.recrutini.services;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javax.swing.JOptionPane;
import esprit.recrutini.controller.DetailForumController;
import esprit.recrutini.entities.Forum;
import esprit.recrutini.entities.Post;
import esprit.recrutini.tools.DataSource;
import java.sql.Date;

/**
 *
 * @author ASUS
 */
public class PostCRUD {

    public ObservableList<Post> readAllpost2(int idd) throws SQLException {

        ObservableList oblistpost = FXCollections.observableArrayList();
        Statement st = DataSource.getInstance().getCnx().createStatement();
        ResultSet rs = st.executeQuery("SELECT * FROM `post` WHERE forum_id = '" + idd + "';");

        //System.out.println(idd);
        while (rs.next()) {

            int id = rs.getInt("id");
            String title = rs.getString("title");
            String description = rs.getString("description");
            int views = rs.getInt("views");
            int noc = rs.getInt("noc");
            Date date = rs.getDate("creat_at");
            //System.out.println("date " + creat_at);
            Post d = new Post(id, title, description, views, noc, date);
            oblistpost.add(d);

        }
        return oblistpost;
    }

    public void addPostC(Post p, int i) {

        try {
            String requete = "INSERT INTO post (candidate_id,forum_id,title,description,views,noc,creat_at)"
                    + "VALUES (?,?,?,?,?,?,sysdate())";
            PreparedStatement pst = DataSource.getInstance().getCnx().prepareStatement(requete);
            pst.setInt(1, p.getIdC());
            pst.setInt(2, i);
            pst.setString(3, p.getTitle());
            pst.setString(4, p.getDescription());
            pst.setInt(5, 0);
            pst.setInt(6, 0);
            //pst.setDate(7, p.getDate());

            pst.executeUpdate();
            System.out.println("Post added!");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

    }

    public void addPostR(Post p, int i) {

        try {
            String requete = "INSERT INTO post (recruiter_id,forum_id,title,description,views,noc,creat_at)"
                    + "VALUES (?,?,?,?,?,?,sysdate())";
            PreparedStatement pst = DataSource.getInstance().getCnx().prepareStatement(requete);
            pst.setInt(1, p.getIdR());
            pst.setInt(2, i);
            pst.setString(3, p.getTitle());
            pst.setString(4, p.getDescription());
            pst.setInt(5, 0);
            pst.setInt(6, 0);
           // pst.setDate(7, p.getDate());

            pst.executeUpdate();
            System.out.println("Post added!");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

    }

    public boolean delete(int dd) throws SQLException {

        PreparedStatement pre = DataSource.getInstance().getCnx().prepareStatement("DELETE FROM post WHERE id ='" + dd + "' ;");
        pre.executeUpdate();
        JOptionPane.showMessageDialog(null, "post supprimé avec succées");

        return true;
    }

    public boolean updatePostview(int id) throws SQLException {
        PreparedStatement pre = DataSource.getInstance().getCnx().prepareStatement("UPDATE post SET views= views+1   WHERE id='" + id + "' ;");
        // JOptionPane.showMessageDialog(null, "Post update avec succées");
        pre.executeUpdate();
        return true;
    }

    public boolean update(int id, String title, String description) throws SQLException {
        PreparedStatement pre = DataSource.getInstance().getCnx().prepareStatement("UPDATE post SET title= '" + title + "' , description='" + description + "' WHERE id='" + id + "' ;");
        JOptionPane.showMessageDialog(null, "Post update avec succées");
        pre.executeUpdate();
        return true;
    }

 
    public Post findById(int id) {

        try {
            String request = "SELECT * FROM post where id = " + id;
            PreparedStatement pst = DataSource.getInstance().getCnx().prepareStatement(request);
            ResultSet rs = pst.executeQuery(request);
            Post post = new Post();
            while (rs.next()) {

                post.setId(rs.getInt("id"));
                post.setTitle(rs.getString("title"));
                post.setDescription(rs.getString("description"));
                post.setViews(rs.getInt("views"));
                post.setNoc(rs.getInt("noc"));
                post.setDate(rs.getDate("creat_at"));

            }
            return post;

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return null;
    }
     public Post findByIdC(int idF) {

        try {
            String request = "SELECT * FROM post where id = '" + idF + "';";
            PreparedStatement pst = DataSource.getInstance().getCnx().prepareStatement(request);
            ResultSet rs = pst.executeQuery(request);
            Post post = new Post();
            while (rs.next()) {

                post.setId(rs.getInt("id"));
                post.setTitle(rs.getString("title"));
                post.setDescription(rs.getString("description"));
               post.setIdC(rs.getInt("candidate_id"));

            }
            return post;

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return null;
    }

    public Post findByIdR(int idF) {

        try {
            String request = "SELECT * FROM post where id = '" + idF + "';";
            PreparedStatement pst = DataSource.getInstance().getCnx().prepareStatement(request);
            ResultSet rs = pst.executeQuery(request);
            Post post = new Post();
            while (rs.next()) {

                post.setId(rs.getInt("id"));
                post.setTitle(rs.getString("title"));
                post.setDescription(rs.getString("description"));
                post.setIdR(rs.getInt("recruiter_id"));

            }
            return post;

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return null;
    }
     public List<String> getPostsTitle(int idF) {
        List<String> myList = new ArrayList();
        try {

            ResultSet rs = DataSource.getInstance().getCnx().createStatement().executeQuery("select * from  post where forum_id ='"+idF+"';");
            while (rs.next()) {
                String title = rs.getString("title");
                myList.add(title);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return myList;
    }
}
