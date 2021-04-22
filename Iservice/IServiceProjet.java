/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Iservice;

import Entite.Projet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author ines
 */
public interface IServiceProjet {

    public ArrayList<Projet> DisplayAll() throws SQLException;

    public void ajouter(Projet P) throws SQLException;

    public void modifier(Projet P) throws SQLException;

    public boolean delete(int id) throws SQLException;

    public Projet details(int id) throws SQLException;

    public int count();

    public ArrayList DetailsRecruteur();

    public ArrayList<Projet> triParTitre() throws SQLException;

    public ArrayList<Projet> triParDate() throws SQLException ;

}
