/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package esprit.recrutini.Iservices;

import esprit.recrutini.entities.Recruiter;
import java.sql.SQLException;

/**
 *
 * @author asus
 */
public interface IServiceRecruiter {

    void ajouter(Recruiter R) throws SQLException;

    boolean delete(int id) throws SQLException;

    boolean update(Recruiter R) throws SQLException;

    public int getID(String nom) throws SQLException;

    public Recruiter getRecruiter(String email) throws SQLException;

    public boolean auth(String mail, String pwd) throws SQLException;

    public int forgotpass(String mail) throws SQLException;

    public int getToken(int token) throws SQLException;

    public void UpdateToken(String mail) throws SQLException;

    public int getmails(String mail) throws SQLException;

    public int getUsername(String username) throws SQLException;

    public int getTel(String tel) throws SQLException;

    public void updatepassword(String mail, String pass) throws SQLException;
}
