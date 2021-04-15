/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entities;

/**
 *
 * @author NOUSSA
 */
public class Freelance {
     private int id;
    private String title;
    private String description;
     private int salary;
    
    public Freelance() {
        
}
public Freelance(String title, String description) {       
        this.title = title;
        this.description = description;
     
    }

     public Freelance(int id, String title, String description,int salary) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.salary = salary;
    }
     public Freelance(String title, String description,int salary) {       
        this.title = title;
        this.description = description;
        this.salary = salary;
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
    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    @Override
    public String toString() {
        return "Freelance{" + "id=" + id + ", title=" + title + ", description=" + description + ", salary=" + salary + '}';
    }

    
}
