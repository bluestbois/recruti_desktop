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
public class SessionAnnonce {
     
    private static SessionAnnonce instance;
    private int id_annonce;
    
    
    public SessionAnnonce(int id) {
        this.id_annonce = id;
    }
    public static SessionAnnonce getInstance() {
        return instance;
        
    }
    public static void setInstance(SessionAnnonce instance) {
        SessionAnnonce.instance = instance; 
    }
     public static SessionAnnonce getInstace(int id) {
        if(instance == null) {
         instance = new SessionAnnonce(id);
        }
        return instance;
    }

    public int getId_Projet() {
        return id_annonce;
    }

    public void setId_Projet(int id_Projet) {
        this.id_annonce = id_annonce;
    }

    @Override
    public String toString() {
        return "SessionAnnonce{" + "id_Annonce=" + id_annonce + '}';
    }

 

      public void cleanSession() {
       
        id_annonce = 0;
      instance = null;

    } 
}
