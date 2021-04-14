package esprit.Recrutini.TestModule.services;

import esprit.Recrutini.TestModule.entities.Categorie;
import esprit.Recrutini.TestModule.entities.Test;
import esprit.Recrutini.tools.MyConnection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author amine
 */
public class CategorieManager {

    private CategorieManager(){}
    
    public static void add(Categorie categorie){
        String query = "INSERT INTO `test_categorie` (`id`, `name`) VALUES "
            + "(NULL, ?)";
        
        try {
            PreparedStatement insert = MyConnection
            .getInstance()
            .getConnection()
            .prepareStatement(query);
            
            insert.setString(1, categorie.getName());
            insert.executeUpdate();
            
            System.out.println(categorie.toString() + " -> Added!");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
    public static List<Categorie> findAll() {
        List<Categorie> list = new ArrayList();
        ResultSet results;
        
        try {
            String query = "SELECT * FROM test_categorie";
            
            results = MyConnection
                    .getInstance()
                    .getConnection()
                    .createStatement()
                    .executeQuery(query);
        
            while(results.next()){
                Categorie categorie = new Categorie(
                    results.getInt(1),
                    results.getString(2)
                );

                String testsQuery = "SELECT t.id "
                    + "FROM "
                        + "test as t, "
                        + "test_categorie as tc, "
                        + "test_test_categorie as ttc "
                    + "WHERE "
                        + "t.id = ttc.test_id "
                        + "and tc.id = ttc.test_categorie_id "
                        + "and tc.id = ?";
                
                PreparedStatement subQuery = MyConnection.
                    getInstance().
                    getConnection().
                    prepareStatement(testsQuery);
                
                subQuery.setInt(1, categorie.getId());
                ResultSet testsResults = subQuery.executeQuery();
                
                while(testsResults.next()){
                    categorie.addTest(TestManager.find(testsResults.getInt(1)));
                }
                
                list.add(categorie);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        
        return list;
    }
    
    public static Categorie find(int id) {
        Categorie categorie = new Categorie();
        categorie.setId(id);
        ResultSet results;
        
        try {
            String query = "SELECT * FROM test_categorie WHERE id = ?";
            
            PreparedStatement select = MyConnection
                .getInstance()
                .getConnection()
                .prepareStatement(query);
            
            select.setString(1, String.valueOf(id));
            
            results = select.executeQuery();
        
            while(results.next()){
                categorie.setId(id);
                categorie.setName(results.getString(2));
                
                String testsQuery = "SELECT t.id "
                    + "FROM "
                        + "test as t, "
                        + "test_categorie as tc, "
                        + "test_test_categorie as ttc "
                    + "WHERE "
                        + "t.id = ttc.test_id "
                        + "and tc.id = ttc.test_categorie_id "
                        + "and tc.id = ?";
                
                PreparedStatement subQuery = MyConnection.
                    getInstance().
                    getConnection().
                    prepareStatement(testsQuery);
                
                subQuery.setInt(1, categorie.getId());
                ResultSet testsResults = subQuery.executeQuery();
                
                while(testsResults.next()){
                    categorie.addTest(TestManager.find(testsResults.getInt(1)));
                }
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        
        return categorie;
    }
    
    public static void update(Categorie categorie){
        String query = "UPDATE `test_categorie` "
            + "SET `name` = ? "
            + "WHERE `test_categorie`.`id` = ?;";
        
        try {
            PreparedStatement update = MyConnection
            .getInstance()
            .getConnection()
            .prepareStatement(query);
            
            update.setString(1, categorie.getName());
            update.setString(2, String.valueOf(categorie.getId()));
            update.executeUpdate(); 
            
//            MyConnection
//                .getInstance()
//                .getConnection()
//                .createStatement()
//                .executeQuery("DELETE FROM `test_test_categorie` "
//                    + "WHERE test_categorie_id = " + categorie.getId()
//                );
//            
//            for(Test test : categorie.getTests()){
//                
//                PreparedStatement addRelation = MyConnection
//                .getInstance()
//                .getConnection()
//                .prepareStatement("INSERT INTO `test_test_categorie` "
//                    + "(`test_id`, `test_categorie_id`) "
//                    + "VALUES (?, ?); ");
//                
//                addRelation.setInt(1, test.getId());
//                addRelation.setInt(2, categorie.getId());
//                
//                addRelation.executeQuery();
//            }
            
            System.out.println(categorie.toString() + " -> Updated!");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
    public static void delete(Categorie categorie){
        try {
            PreparedStatement delete = MyConnection
            .getInstance()
            .getConnection()
            .prepareStatement("DELETE FROM `test_categorie` "
            + "WHERE `test_categorie`.`id` = ? ");
            
            delete.setString(1, String.valueOf(categorie.getId()));
            delete.executeUpdate(); 
            
            delete = MyConnection
            .getInstance()
            .getConnection()
            .prepareStatement("DELETE FROM `test_test_categorie` "
            + "WHERE `test_categorie_id` = ? ");
            
            delete.setString(1, String.valueOf(categorie.getId()));
            delete.executeUpdate();
            
            System.out.println(categorie.toString() + " -> Deleted!");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
}