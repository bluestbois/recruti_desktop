package esprit.Recrutini.TestModule.services;

import esprit.Recrutini.TestModule.entities.Categorie;
import esprit.Recrutini.TestModule.entities.Question;
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
public class TestManager {
    
    private TestManager(){}
    
    public static void add(Test test){
        String query = "INSERT INTO `test` (`id`, `name`) VALUES "
            + "(NULL, ?, ?)";
        
        try {
            PreparedStatement insert = MyConnection
            .getInstance()
            .getConnection()
            .prepareStatement(query);
            
            insert.setString(1, test.getTitle());
            insert.setString(2, test.getDescription());
            insert.executeUpdate();
            
            //Adding categories
            
            for(Categorie categorie : test.getCategories()){
                String name = CategorieManager.find(categorie.getId()).getName();
                if(!categorie.getName().equals(name))
                    System.out.println("This categorie doesn't exist!");
                else {
                    PreparedStatement addRelation = MyConnection
                        .getInstance()
                        .getConnection()
                        .prepareStatement("INSERT INTO `test_test_categorie` "
                                + "(`test_id`, `test_categorie_id`) "
                                + "VALUES (?, ?); ");
                    
                    addRelation.setInt(1, test.getId());
                    addRelation.setInt(2, categorie.getId());

                    addRelation.executeQuery();
                }
            }
            
            //Adding questions
            
            //TODO: Figure out if I need to create new question rows here.
            
//            for(Question question : test.getQuestions()){
//                String name = CategorieManager.find(categorie.getId()).getName();
//                if(!categorie.getName().equals(name))
//                    System.out.println("This categorie doesn't exist!");
//                else {
//                    PreparedStatement addRelation = MyConnection
//                        .getInstance()
//                        .getConnection()
//                        .prepareStatement("INSERT INTO `test_test_categorie` "
//                                + "(`test_id`, `test_categorie_id`) "
//                                + "VALUES (?, ?); ");
//                    
//                    addRelation.setInt(1, test.getId());
//                    addRelation.setInt(2, categorie.getId());
//
//                    addRelation.executeQuery();
//                }
//            }
            
            System.out.println(test.toString() + " -> Added!");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
    public static List<Test> findAll() {
        List<Test> list = new ArrayList();
        ResultSet testsResults;
        PreparedStatement subQuery;
        
        try {
            String query = "SELECT * FROM test";
            
            testsResults = MyConnection
                    .getInstance()
                    .getConnection()
                    .createStatement()
                    .executeQuery(query);
        
            while(testsResults.next()){
                Test test = new Test(
                    testsResults.getInt(1),
                    testsResults.getString(2),
                    testsResults.getString(3)
                );

                //Test Categories fetching
                
                String categoriesQuery = "SELECT c.id, c.name "
                    + "FROM `test_categorie` AS c ,"
                        + "`test_test_categorie` AS tc, "
                        + "`test` AS t "
                    + "WHERE t.id = tc.test_id "
                    + "AND tc.test_categorie_id = c.id "
                    + "AND t.id = ?";
                
                subQuery = MyConnection.
                    getInstance().
                    getConnection().
                    prepareStatement(categoriesQuery);
                
                subQuery.setString(1, String.valueOf(test.getId()));
                ResultSet categorieResults = subQuery.executeQuery();
                
                while(categorieResults.next()){
                    test.addCategorie(new Categorie(
                        categorieResults.getInt(1),
                        categorieResults.getString(2)
                    ));
                }
                
                //Test Questions fetching
                
                String questionsQuery = "SELECT * FROM `question`"
                    + "WHERE `question`.`test_id` = ?";
                
                subQuery = MyConnection.
                    getInstance().
                    getConnection().
                    prepareStatement(questionsQuery);
                
                subQuery.setString(1, String.valueOf(test.getId()));
                ResultSet questionsResults = subQuery.executeQuery();
                
                while(questionsResults.next()){
                    test.addQuestion(new Question(
                        questionsResults.getInt(1),
                        test,
                        questionsResults.getString(3),
                        questionsResults.getString(4),
                        questionsResults.getString(5),
                        questionsResults.getString(6),
                        questionsResults.getInt(7),
                        questionsResults.getInt(8)
                    ));
                }
                
                list.add(test);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        
        return list;
    }
    
    public static Test find(int id) {
        Test test = new Test();
        test.setId(id);
        ResultSet testsResults;
        PreparedStatement subQuery;
        
        try {
            String query = "SELECT * FROM test WHERE id = ?";
            
            PreparedStatement select = MyConnection
                .getInstance()
                .getConnection()
                .prepareStatement(query);
            
            select.setString(1, String.valueOf(id));
            
            testsResults = select.executeQuery();
        
            while(testsResults.next()){
                test.setId(id);
                test.setTitle(testsResults.getString(2));
                test.setDescription(testsResults.getString(3));
                
                //Test Categories fetching
                
                String categoriesQuery = "SELECT c.id, c.name "
                    + "FROM `test_categorie` AS c ,"
                        + "`test_test_categorie` AS tc, "
                        + "`test` AS t "
                    + "WHERE t.id = tc.test_id "
                    + "AND tc.test_categorie_id = c.id "
                    + "AND t.id = ?";
                
                subQuery = MyConnection.
                    getInstance().
                    getConnection().
                    prepareStatement(categoriesQuery);
                
                subQuery.setString(1, String.valueOf(id));
                ResultSet categorieResults = subQuery.executeQuery();
                
                while(categorieResults.next()){
                    test.addCategorie(new Categorie(
                        categorieResults.getInt(1),
                        categorieResults.getString(2)
                    ));
                }
                
                //Test Questions fetching
                
                String questionsQuery = "SELECT * FROM `question`"
                    + "WHERE `question`.`test_id` = ?";
                
                subQuery = MyConnection.
                    getInstance().
                    getConnection().
                    prepareStatement(questionsQuery);
                
                subQuery.setString(1, String.valueOf(id));
                ResultSet questionsResults = subQuery.executeQuery();
                
                while(questionsResults.next()){
                    test.addQuestion(new Question(
                        questionsResults.getInt(1),
                        test,
                        questionsResults.getString(3),
                        questionsResults.getString(4),
                        questionsResults.getString(5),
                        questionsResults.getString(6),
                        questionsResults.getInt(7),
                        questionsResults.getInt(8)
                    ));
                }
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        
        return test;
    }
    
    public static void update(Test test){
        String query = "UPDATE `test` "
            + "SET `title` = '?', "
            + "`description` = '?' "
            + "WHERE `test`.`id` = ?";
        
        try {
            PreparedStatement update = MyConnection
            .getInstance()
            .getConnection()
            .prepareStatement(query);
            
            update.setString(1, test.getTitle());
            update.setString(2, test.getDescription());
            update.setString(2, String.valueOf(test.getId()));
            update.executeUpdate(); 
            
            MyConnection
                .getInstance()
                .getConnection()
                .createStatement()
                .executeQuery("DELETE FROM `test_test_categorie` "
                    + "WHERE test_id = " + test.getId()
                );
            
            for(Categorie categorie : test.getCategories()){
                PreparedStatement addRelation = MyConnection
                .getInstance()
                .getConnection()
                .prepareStatement("INSERT INTO `test_test_categorie` "
                    + "(`test_id`, `test_categorie_id`) "
                    + "VALUES (?, ?); ");
                
                addRelation.setInt(1, test.getId());
                addRelation.setInt(2, categorie.getId());
                
                addRelation.executeQuery();
            }
            
            System.out.println(test.toString() + " -> Updated!");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
    public static void delete(Test test){
        try {
            PreparedStatement delete;
            
            for(Question question : test.getQuestions()){
                String statement = QuestionManager
                    .find(question.getId())
                    .getStatement();
                
                if(question.getStatement().equals(statement)){
                    delete = MyConnection
                        .getInstance()
                        .getConnection()
                        .prepareStatement("DELETE FROM `question` "
                        + "WHERE `id` = ? ");

                    delete.setInt(1, question.getId());
                    delete.executeUpdate();
                }
            }
            
            delete = MyConnection
                .getInstance()
                .getConnection()
                .prepareStatement("DELETE FROM `test` "
                + "WHERE `test`.`id` = ? ");
            
            delete.setString(1, String.valueOf(test.getId()));
            delete.executeUpdate(); 
            
            delete = MyConnection
                .getInstance()
                .getConnection()
                .prepareStatement("DELETE FROM `test_test_categorie` "
                + "WHERE `test_id` = ? ");
            
            delete.setString(1, String.valueOf(test.getId()));
            delete.executeUpdate();
            
            System.out.println(test.toString() + " -> Deleted!");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
    /**
     *
     * @param test
     * @param answers
     * @return
     */
    public static double passTest(Test test, List<Integer> answers){
        List<Question> questions = test.getQuestions();
        
        if(questions.size() != answers.size()){
            System.out.println("No / Missing answers!");
            return -1;
        }
        
        int sum = 0;
        for(int i = 0; i < questions.size(); i++){
            sum += questions.get(i).getRightAnswer() == answers.get(i) 
                ? questions.get(i).getPoints()
                : 0;
        }
        
        return sum / questions.size();
    }
}