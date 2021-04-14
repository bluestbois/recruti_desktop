/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package esprit.Recrutini.TestModule.entities;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author amine
 */
public class Test {
    private int id;
    private String title;
    private String description;
    private List<Question> questions;
    private List<Categorie> categories;

    public Test() {
        questions = new ArrayList();
        categories = new ArrayList();
    }

    public Test(int id, String title, String description) {
        this.id = id;
        this.title = title;
        this.description = description;
        questions = new ArrayList();
        categories = new ArrayList();
    }
    
    public Test(int id, String title, String description, List<Question> questions, List<Categorie> categories) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.questions = questions;
        this.categories = categories;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    
    //Questions relation
    
    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }
    
    public void removeQuestion(Question question) {
        if(questions.remove(question))
            question.setTest(null);
    }
    
    public void addQuestion(Question question) {
        if(!questions.contains(question)){
            questions.add(question);
            question.setTest(this);
        }
    }
    
    //Categories relation
    
    public List<Categorie> getCategories() {
        return categories;
    }

    public void setCategories(List<Categorie> categories) {
        this.categories = categories;
    }
    
    public void removeCategorie(Categorie categorie){
        if(categories.remove(categorie))
            categorie.removeTest(this);
    }
    
    public void addCategorie(Categorie categorie){
        if(!categories.contains(categorie)){
            categories.add(categorie);
            categorie.addTest(this);
        }
    }

    @Override
    public String toString() {
        return "Test {\n" 
            + "    id=" + id 
            + ",\n    title=" + title 
            + ",\n    description=" + description 
            + ",\n    questions=" + questions 
            + ",\n    categories=" + categories 
            + "\n}\n";
    }
    
    
}
