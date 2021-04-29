/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package esprit.recrutini.Iservices;

import esprit.recrutini.entities.Job;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author asus
 */
public interface IServiceJob {

    public ArrayList<Job> DisplayAll(int from, int to) throws SQLException;

    public List<Job> Display_OF_RECRUITER(int id , int from, int to) throws SQLException;

    public void ajouter(Job J) throws SQLException;

    public void modifier(Job J) throws SQLException;

    public boolean delete(int id) throws SQLException;

    public Job details(int id) throws SQLException;

    public int count( int id);
     public int count2( );
}
