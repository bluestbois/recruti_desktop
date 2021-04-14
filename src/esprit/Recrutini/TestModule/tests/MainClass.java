/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package esprit.Recrutini.TestModule.tests;

import esprit.Recrutini.TestModule.entities.Categorie;
import esprit.Recrutini.TestModule.entities.Question;
import esprit.Recrutini.TestModule.entities.Test;
import esprit.Recrutini.TestModule.services.CategorieManager;
import esprit.Recrutini.TestModule.services.QuestionManager;
import esprit.Recrutini.TestModule.services.TestManager;
import java.util.List;

/**
 *
 * @author amine
 */
public class MainClass {
    public static void main(String[] args) {
        Test test = TestManager.find(32);
        System.out.println(test);
        
        test.setQuestions(QuestionManager.randomize(test.getQuestions()));
        System.out.println(test);
    }
}