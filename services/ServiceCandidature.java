/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package esprit.recrutini.services;

import esprit.recrutini.entities.Candidature;
import esprit.recrutini.tools.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import java.sql.Date;


/**
 *
 * @author yessine darmoul
 */
public class ServiceCandidature implements IServiceCandidature<Candidature>{
   private DataSource con;
    private Statement pst;
    
    public ServiceCandidature()
    {
    con = DataSource.getInstance();
    }


   
    
    
    public boolean update(int id ,int idcandidate,int idjob,int idfreelance,int score) throws SQLException {
PreparedStatement pst;
String req =("update Candidature SET Candidate_id= '" +idcandidate+"' , job_id= '" +idjob+"', freelance_id= '" +idfreelance+"', score= '" +score+"'  WHERE id='"+id+"' ;");
pst =con.getCnx().prepareStatement(req);
JOptionPane.showMessageDialog(null,"Candidature modified successfully");            
pst.executeUpdate();
       
        return true;    }

   @Override
    public List<Candidature> readAll() throws SQLException {
List<Candidature> list=new ArrayList<>();
   PreparedStatement pst;
   String req =("select * from Candidature");
   pst =con.getCnx().prepareStatement(req);
    ResultSet rs=pst.executeQuery(req);
     while (rs.next())
     {                
               int id=rs.getInt(1);
               int idcandidate=rs.getInt(2);
               int idjob=rs.getInt(3);
               int idfreelance=rs.getInt(4);
               Date date=rs.getDate(5);
               int score=rs.getInt(6);

               Candidature c=new Candidature();
                     c.setId(id);            
                     c.setCandidate_id(idcandidate);
                     c.setJob_id(idjob);
                     c.setFreelance_id(idfreelance);
                     c.setDate(date);
                     c.setScore(score);
                     list.add(c);
                     }
    return list; 
    
    
    }

  @Override
    public boolean delete(int id) throws SQLException {

PreparedStatement pst;
String req =("DELETE FROM candidature WHERE id='"+id+"' ;");
JOptionPane.showMessageDialog(null,"Candidature deleted");   
pst =con.getCnx().prepareStatement(req);
pst.executeUpdate();
        return true;    
    }

    public void ajouter(Candidature c) throws SQLException {

        
       
      
        
                    String query = "INSERT INTO `Recrutini`.`Candidature` (`Candidate_id`,`job_id`,`freelance_id`,`date`,`score`) VALUES (?,?,?,?,?)";
            PreparedStatement pst;
            

            con = DataSource.getInstance();
            pst=con.getCnx().prepareStatement(query);
            pst.setInt(1, c.getCandidate_id());
            pst.setInt(2, c.getJob_id());
            pst.setInt(3, c.getFreelance_id());
            pst.setDate(4,(java.sql.Date) c.getDate());
            pst.setInt(5, c.getJob_id());
            pst.executeUpdate();
        
 JOptionPane.showMessageDialog(null,"job/Freelance affected successfully");
        
        
    }

    @Override
    public boolean update(int id, int idcan, int idjob) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    

    
}
