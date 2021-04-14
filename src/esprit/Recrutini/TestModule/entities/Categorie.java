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
public class Categorie {
    private int id;
    private String name;
    private List<Test> tests;

    public Categorie() {
        tests = new ArrayList();
    }

    public Categorie(int id, String name) {
        this.id = id;
        this.name = name;
        tests = new ArrayList();
    }
    
    public Categorie(int id, String name, List<Test> tests) {
        this.id = id;
        this.name = name;
        this.tests = tests;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    //Tests relation (ManyTomany)
    
    public List<Test> getTests() {
        return tests;
    }

    public void setTests(List<Test> tests) {
        this.tests = tests;
    }
    
    public void addTest(Test test){
        if(!tests.contains(test)){
            tests.add(test);
            test.addCategorie(this);
        }
    }
    
    public void removeTest(Test test){
        if(tests.remove(test))
            test.removeCategorie(this);
    }

    @Override
    public String toString() {
        return "Categorie {\n" 
            + "     id=" + id 
            + ",\n   name=" + name 
        + "\n}";
    }
}
