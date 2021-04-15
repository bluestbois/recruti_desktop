/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Services;

import Config.MaConnexion;
import Entities.Freelance;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author NOUSSA
 */
public class GestionFreelance {
    
    Connection cnx;
    PreparedStatement ste;
    
    public GestionFreelance(){
         cnx = MaConnexion.getinstance().getCnx();
    }
    
    public void ajouterFreelance(Freelance c){
         try {
        String sql = "insert into freelance(title,description,salary)"+"values(?,?)";
        ste = cnx.prepareStatement(sql);
        ste.setString(1, c.getTitle());
        ste.setString(2, c.getDescription());
        ste.setInt(3, c.getSalary());
        ste.executeUpdate();
             System.out.println("freelance opportunity added");
         }catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
    public void modifierFreelance(Freelance c){
        String requete = "UPDATE freelance  SET title = ?, description = ?, salary = ? WHERE id = ?";
        try {
            PreparedStatement pst = cnx.prepareStatement(requete);
            pst.setString(1, c.getTitle());
            pst.setString(2, c.getDescription());
            pst.setInt(3, c.getId());
            pst.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        System.out.println("freelance modifé!");
    }
    
    public void supprimerFreelance(Freelance c){
        String requete = "DELETE FROM freelance WHERE id = ?";
        try {
            PreparedStatement pst = cnx.prepareStatement(requete);
            pst.setInt(1, c.getId());
            pst.executeUpdate();
        } catch (SQLException ex) {
             System.out.println(ex.getMessage());
        }
        System.out.println("freelance opportunity deleted!");
    }
    
     public List<Freelance> getFreelances(){
        List<Freelance> list = new ArrayList<>();
        String requete = "SELECT * FROM freelance ";
        try {
            PreparedStatement pst = cnx.prepareStatement(requete);
            ResultSet rs = pst.executeQuery();
            while(rs.next()){
                Freelance c = new Freelance(rs.getInt(1), rs.getString(3), rs.getString(4), rs.getInt(5));                
                list.add(c);
            }
            System.out.println("List des freelance à été crée!");
        } catch (SQLException ex) {
             System.out.println(ex.getMessage());
        }
        return list;
    }
    
   
}
