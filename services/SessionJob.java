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
public class SessionJob {
   
    private static SessionJob instance;
    private int id;
    
    
    private SessionJob(int id) {
          this.id = id;
    }
    public static SessionJob getInstance() {
        return instance;
        
    }

    public static void setInstance(SessionJob instance) {
        SessionJob.instance = instance;
    }

    
    
     public static SessionJob getInstace(int id) {
        if(instance == null) {
         instance = new SessionJob(id);
        }
        return instance;
    }

  

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "SessionJob{" + "id=" + id + '}';
    }




      public void cleanSession() {
       
        id = 0;
      instance = null;

    } 
    
}
