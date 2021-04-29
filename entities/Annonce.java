/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package esprit.recrutini.entities;

import java.sql.Date;

/**
 *
 * @author ines
 */
public class Annonce {
    private int id;
    private int project_id;
    private String title;
    private String description;
    private String salary ;
     private String etat ;
    private java.sql.Date date;

    public Annonce(int id, int project_id, String title, String description, String salary, Date date , String etat) {
        this.id = id;
        this.project_id = project_id;
        this.title = title;
        this.description = description;
        this.salary = salary;
        this.date = date;
        this.etat = etat;
    }

    
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getProject_id() {
        return project_id;
    }

    public void setProject_id(int project_id) {
        this.project_id = project_id;
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

    public String getSalary() {
        return salary;
    }

    public void setSalary(String salary) {
        this.salary = salary;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getEtat() {
        return etat;
    }

    public void setEtat(String etat) {
        this.etat = etat;
    }
    
    
    
}
