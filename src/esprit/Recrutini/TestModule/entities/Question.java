/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package esprit.Recrutini.TestModule.entities;

/**
 *
 * @author amine
 */
public class Question {
    private int id;
    private Test test;
    private String statement;
    private String answerA;
    private String answerB;
    private String answerC;
    private int rightAnswer;
    private int points;

    public Question() {
    }

    public Question(int id, Test test, String statement, String answerA, String answerB, String answerC, int rightAnswer, int points) {
        this.id = id;
        this.test = test;
        this.statement = statement;
        this.answerA = answerA;
        this.answerB = answerB;
        this.answerC = answerC;
        this.rightAnswer = rightAnswer;
        this.points = points;
    }

    public Question(Test test, String statement, String answerA, String answerB, String answerC, int rightAnswer, int points) {
        this.test = test;
        this.statement = statement;
        this.answerA = answerA;
        this.answerB = answerB;
        this.answerC = answerC;
        this.rightAnswer = rightAnswer;
        this.points = points;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStatement() {
        return statement;
    }

    public void setStatement(String statement) {
        this.statement = statement;
    }

    public String getAnswerA() {
        return answerA;
    }

    public void setAnswerA(String answerA) {
        this.answerA = answerA;
    }

    public String getAnswerB() {
        return answerB;
    }

    public void setAnswerB(String answerB) {
        this.answerB = answerB;
    }

    public String getAnswerC() {
        return answerC;
    }

    public void setAnswerC(String answerC) {
        this.answerC = answerC;
    }

    public int getRightAnswer() {
        return rightAnswer;
    }

    public void setRightAnswer(int rightAnswer) {
        this.rightAnswer = rightAnswer;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }
    
    //Test relation (OneToMany)
    
    public Test getTest() {
        return test;
    }

    public void setTest(Test test) {
        this.test = test;
    }

    @Override
    public String toString() {
        return "Question{ \n" 
                + "    id=" + id 
                + ",\n    statement=" + statement 
                + ",\n    answerA=" + answerA 
                + ",\n    answerB=" + answerB 
                + ",\n    answerC=" + answerC 
                + ",\n    rightAnswer=" + rightAnswer 
                + ",\n    points=" + points 
            + "\n}";
    }    
}
