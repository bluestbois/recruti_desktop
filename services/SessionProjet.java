/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package esprit.recrutini.services;

/**
 *
 * @author ines
 */
public class SessionProjet {
    
    private static SessionProjet instance;
    private int id_Projet;
    
    
    public SessionProjet(int id) {
        this.id_Projet = id;
    }
    public static SessionProjet getInstance() {
        return instance;
        
    }
    public static void setInstance(SessionProjet instance) {
        SessionProjet.instance = instance; 
    }
     public static SessionProjet getInstace(int id) {
        if(instance == null) {
         instance = new SessionProjet(id);
        }
        return instance;
    }

    public int getId_Projet() {
        return id_Projet;
    }

    public void setId_Projet(int id_Projet) {
        this.id_Projet = id_Projet;
    }

    @Override
    public String toString() {
        return "SessionProjet{" + "id_Projet=" + id_Projet + '}';
    }

 

      public void cleanSession() {
       
        id_Projet = 0;
      instance = null;

    } 
    
}
