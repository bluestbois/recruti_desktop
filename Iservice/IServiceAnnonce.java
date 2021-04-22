/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Iservice;

import Entite.Annonce;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author ines
 */
public interface IServiceAnnonce {

    public ArrayList<Annonce> DisplayAll() throws SQLException;
 public ArrayList<Annonce> DisplayNEW() throws SQLException;
    public void ajouter(Annonce A) throws SQLException;

    public void modifier(Annonce A) throws SQLException;

    public boolean delete(int id) throws SQLException;

    public Annonce details(int id) throws SQLException;

    public int count();

    public int count_non_active();

    public int count_active();

    public ArrayList<Annonce> triParTitre() throws SQLException;

    public ArrayList<Annonce> triParDate() throws SQLException;
}
