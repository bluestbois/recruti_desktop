/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Services;

import Config.MaConnexion;
import Entities.Project;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author NOUSSA
 */
public class GestionProjet {
   
    Connection cnx;
    PreparedStatement ste;
    
    public GestionProjet(){
         cnx = MaConnexion.getinstance().getCnx();
    }
    
    public void ajouterProjet(Project c){
         try {
        String sql = "insert into project(title,description)"+"values(?,?)";
        ste = cnx.prepareStatement(sql);
        ste.setString(1, c.getTitle());
        ste.setString(2, c.getDescription());
        ste.executeUpdate();
             System.out.println("projet Ajouté");
         }catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
    public void modifierProjet(Project c){
        String requete = "UPDATE project  SET title = ?, description = ? WHERE id = ?";
        try {
            PreparedStatement pst = cnx.prepareStatement(requete);
            pst.setString(1, c.getTitle());
            pst.setString(2, c.getDescription());
            pst.setInt(3, c.getId());
            pst.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        System.out.println("Projet modifé!");
    }
    
    public void supprimerProjet(Project c){
        String requete = "DELETE FROM project WHERE id = ?";
        try {
            PreparedStatement pst = cnx.prepareStatement(requete);
            pst.setInt(1, c.getId());
            pst.executeUpdate();
        } catch (SQLException ex) {
             System.out.println(ex.getMessage());
        }
        System.out.println("Projet supprimé!");
    }
    
     public List<Project> getProjects(){
        List<Project> list = new ArrayList<>();
        String requete = "SELECT * FROM project ";
        try {
            PreparedStatement pst = cnx.prepareStatement(requete);
            ResultSet rs = pst.executeQuery();
            while(rs.next()){
                Project c = new Project(rs.getInt(1), rs.getString(3), rs.getString(4));                
                list.add(c);
            }
            System.out.println("List des categories à été crée!");
        } catch (SQLException ex) {
             System.out.println(ex.getMessage());
        }
        return list;
    }
    
   
}
