/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package esprit.recrutini.services;

/**
 *
 * @author asus
 */
public class Session {
    
    private static Session instance;
    private String email;
    
    
    public Session(String mail) {
        this.email = mail;
    }
    public static Session getInstance() {
        return instance;
        
    }
    public static void setInstance(Session instance) {
        Session.instance = instance; 
    }
     public static Session getInstace(String mail) {
        if(instance == null) {
         instance = new Session(mail);
        }
        return instance;
    }

   

    @Override
    public String toString() {
        return "Session{" + "mail=" + email + '}';
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }



      public void cleanSession() {
       
        email = null;
      instance = null;

    } 
    
}
