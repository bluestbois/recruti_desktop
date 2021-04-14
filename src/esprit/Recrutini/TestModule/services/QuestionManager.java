package esprit.Recrutini.TestModule.services;

import esprit.Recrutini.TestModule.entities.Question;
import esprit.Recrutini.tools.MyConnection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author amine
 */
public class QuestionManager {

    private QuestionManager(){}
    
    public static void add(Question question){
        String query = "INSERT INTO `question` "
            + "(`id`, `test_id`, "
            + "`statement`, `answer_a`, `answer_b`, `answer_c`, "
            + "`right_answer`, `points`) "
            + "VALUES (NULL, ?, ?, ?, ?, ?, ?, ?)";
        
        try {
            PreparedStatement insert = MyConnection
            .getInstance()
            .getConnection()
            .prepareStatement(query);
            
            insert.setInt(1, question.getTest().getId());
            insert.setString(2, question.getStatement());
            insert.setString(3, question.getAnswerA());
            insert.setString(4, question.getAnswerB());
            insert.setString(5, question.getAnswerC());
            insert.setInt(6, question.getRightAnswer());
            insert.setInt(7, question.getPoints());
            insert.executeUpdate();
            
            System.out.println(question.toString() + " -> Added!");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
    public static List<Question> findAll() {
        List<Question> list = new ArrayList();
        ResultSet results;
        
        try {
            String query = "SELECT * FROM question";
            
            results = MyConnection
                    .getInstance()
                    .getConnection()
                    .createStatement()
                    .executeQuery(query);
        
            while(results.next()){
                Question question = new Question(
                    results.getInt(1),
                    TestManager.find(results.getInt(2)),
                    results.getString(3),
                    results.getString(4),
                    results.getString(5),
                    results.getString(6),
                    results.getInt(7),
                    results.getInt(8)
                );

                list.add(question);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        
        return list;
    }
    
    public static Question find(int id) {
        Question question = new Question();
        question.setId(id);
        ResultSet results;
        
        try {
            String query = "SELECT * FROM question WHERE id = ?";
            
            PreparedStatement select = MyConnection
                .getInstance()
                .getConnection()
                .prepareStatement(query);
            
            select.setString(1, String.valueOf(id));
            
            results = select.executeQuery();
        
            while(results.next()){
                question.setId(id);
                question.setTest(TestManager.find(results.getInt(2)));
                question.setStatement(results.getString(3));
                question.setAnswerA(results.getString(4));
                question.setAnswerB(results.getString(5));
                question.setAnswerC(results.getString(6));
                question.setRightAnswer(results.getInt(7));
                question.setPoints(results.getInt(8));
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        
        return question;
    }
    
    public static void update(Question question){
        String query = "UPDATE `question` "
                + "SET "
                    + "`test_id` = ? , "
                    + "`statement` = ? , "
                    + "`answer_a` = ? , " 
                    + "`answer_b` = ? , "
                    + "`answer_c` = ? , "
                    + "`right_answer` = ? , "
                    + "`points` = ? "
                + "WHERE "
                    + "`question`.`id` = ?";
        
        try {
            PreparedStatement update = MyConnection
            .getInstance()
            .getConnection()
            .prepareStatement(query);
            
            update.setInt(1, question.getTest().getId());
            update.setString(2, question.getStatement());
            update.setString(3, question.getAnswerA());
            update.setString(4, question.getAnswerB());
            update.setString(5, question.getAnswerC());
            update.setInt(6, question.getRightAnswer());
            update.setInt(7, question.getPoints());
            update.setInt(8, question.getId());
            update.executeUpdate(); 
            
            System.out.println(question.toString() + " -> Updated!");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
    public static void delete(Question question){
        String query = "DELETE FROM `question` "
            + "WHERE `question`.`id` = ? ";
        
        try {
            PreparedStatement delete = MyConnection
            .getInstance()
            .getConnection()
            .prepareStatement(query);
            
            delete.setString(1, String.valueOf(question.getId()));
            delete.executeUpdate(); 
            
            System.out.println(question.toString() + " -> Deleted!");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
    public static void delete(int id){
        String query = "DELETE FROM `question` "
            + "WHERE `question`.`id` = ? ";
        
        Question question = QuestionManager.find(id);
        
        try {
            PreparedStatement delete = MyConnection
            .getInstance()
            .getConnection()
            .prepareStatement(query);
            
            delete.setInt(1, id);
            delete.executeUpdate(); 
            
            System.out.println(question.toString() + " -> Deleted!");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
    public static List<Question> randomize(List<Question> questions){
        Collections.shuffle(questions);
        return questions;
    }
}