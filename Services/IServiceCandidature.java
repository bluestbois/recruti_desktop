/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Services;

import Entity.Candidature;
import java.sql.SQLException;
import java.util.List;
import javafx.collections.ObservableList;

/**
 *
 * @author yessine darmoul
 */
public interface IServiceCandidature <T>{
    void ajouter(T t) throws SQLException;
    boolean delete(int id) throws SQLException;
    boolean update(int id,int idcours,int idetudiant) throws SQLException;
    List<Candidature> readAll() throws SQLException;
}
