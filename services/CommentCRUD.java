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
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javax.swing.JOptionPane;
import esprit.recrutini.entities.Comment;
import esprit.recrutini.entities.Forum;
import esprit.recrutini.entities.Post;
import esprit.recrutini.tools.DataSource;
import java.sql.Date;

/**
 *
 * @author ASUS
 */
public class CommentCRUD {

    public ObservableList<Comment> readAlldiscc() throws SQLException {
        ObservableList oblistdisc = FXCollections.observableArrayList();

        Statement st = DataSource.getInstance().getCnx().createStatement();
        ResultSet rs = st.executeQuery("select * from  comment;");
        while (rs.next()) {
            int id = rs.getInt("id");
            String title = rs.getString("title");
            String description = rs.getString("description");

            Forum d = new Forum(id, title, description);
            oblistdisc.add(d);

        }
        return oblistdisc;
    }

    public ObservableList<Comment> readAllcomment2(int idd) throws SQLException {

        ObservableList oblistpost = FXCollections.observableArrayList();
        Statement st = DataSource.getInstance().getCnx().createStatement();
        ResultSet rs = st.executeQuery("SELECT * FROM `comment` WHERE post_id = '" + idd + "';");

        // System.out.println(idd);
        while (rs.next()) {

            int id = rs.getInt("id");
            String content = rs.getString("content");
            int rating = rs.getInt("rating");
            Date date = rs.getDate("date");

            Comment d = new Comment(id, content, rating, date);
            oblistpost.add(d);

        }
        return oblistpost;
    }

    public void addCommentC(Comment c, int i) {
        try {
            String requete = "INSERT INTO comment (candidate_id,post_id,content,rating,date)"
                    + "VALUES (?,?,?,?,sysdate())";
            PreparedStatement pst = DataSource.getInstance().getCnx().prepareStatement(requete);
            pst.setInt(1, c.getIdC());
            pst.setInt(2, i);
            pst.setString(3, c.getContent());
            pst.setInt(4, c.getRating());

            pst.executeUpdate();
            System.out.println("Comment added!");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

    }

    public void addCommentR(Comment c, int i) {
        try {
            String requete = "INSERT INTO comment (recruiter_id,post_id,content,rating,date)"
                    + "VALUES (?,?,?,?,sysdate())";
            PreparedStatement pst = DataSource.getInstance().getCnx().prepareStatement(requete);
            pst.setInt(1, c.getIdR());
            pst.setInt(2, i);
            pst.setString(3, c.getContent());
            pst.setInt(4, c.getRating());

            pst.executeUpdate();
            System.out.println("Comment added!");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

    }

    public boolean delete(int dd) throws SQLException {

        PreparedStatement pre = DataSource.getInstance().getCnx().prepareStatement("DELETE FROM comment WHERE id ='" + dd + "' ;");
        pre.executeUpdate();
        JOptionPane.showMessageDialog(null, "comment supprimé avec succées");

        return true;
    }

    public boolean updatePost(int id) throws SQLException {
        PreparedStatement pre = DataSource.getInstance().getCnx().prepareStatement("UPDATE post SET noc= noc+1   WHERE id='" + id + "' ;");
        // JOptionPane.showMessageDialog(null, "Post update avec succées");
        pre.executeUpdate();
        return true;
    }

    public boolean updatePostnocDelete(int id) throws SQLException {
        PreparedStatement pre = DataSource.getInstance().getCnx().prepareStatement("UPDATE post SET noc= noc-1   WHERE id='" + id + "' ;");
        // JOptionPane.showMessageDialog(null, "Post update avec succées");
        pre.executeUpdate();
        return true;
    }

    public boolean update(int id, String content, int rating) throws SQLException {
        PreparedStatement pre = DataSource.getInstance().getCnx().prepareStatement("UPDATE comment SET content= '" + content + "' , rating='" + rating + "' WHERE id='" + id + "' ;");
        JOptionPane.showMessageDialog(null, "Comment update avec succées");
        pre.executeUpdate();
        return true;
    }

    public int count(int idP) throws SQLException {
        int i = 0;

        Statement st = DataSource.getInstance().getCnx().createStatement();
        ResultSet rs = st.executeQuery("select * from  comment where post_id ='" + idP + "';");
        while (rs.next()) {
            rs.getInt("id");

            rs.getString("content");
            rs.getInt("rating");
            i++;
        }
        return i;
    }

    public int sum(int idP) throws SQLException {
        int sum = 0;

        Statement st = DataSource.getInstance().getCnx().createStatement();
        ResultSet rs = st.executeQuery("select * from  comment where post_id ='" + idP + "';");
        while (rs.next()) {
            rs.getInt("id");

            rs.getString("content");
            int s = rs.getInt("rating");
            sum += s;

        }
        return sum;
    }

    public Comment findByIdC(int idF) {

        try {
            String request = "SELECT * FROM comment where id = '" + idF + "';";
            PreparedStatement pst = DataSource.getInstance().getCnx().prepareStatement(request);
            ResultSet rs = pst.executeQuery(request);
            Comment comment = new Comment();
            while (rs.next()) {

                comment.setId(rs.getInt("id"));
                comment.setContent(rs.getString("content"));
                comment.setRating(rs.getInt("rating"));
                comment.setIdC(rs.getInt("candidate_id"));

            }
            return comment;

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return null;
    }

    public Comment findByIdR(int idF) {

        try {
            String request = "SELECT * FROM comment where id = '" + idF + "';";
            PreparedStatement pst = DataSource.getInstance().getCnx().prepareStatement(request);
            ResultSet rs = pst.executeQuery(request);
            Comment comment = new Comment();
            while (rs.next()) {

                comment.setId(rs.getInt("id"));
                comment.setContent(rs.getString("content"));
                comment.setRating(rs.getInt("rating"));
                comment.setIdR(rs.getInt("recruiter_id"));

            }
            return comment;

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return null;
    }
}
