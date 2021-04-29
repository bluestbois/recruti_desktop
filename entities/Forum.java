/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package esprit.recrutini.entities;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ASUS
 */
public class Forum {
   private int id ;
   private String title;
   private String description ; 
   private int idR;
   private int idC;
   
   private List<Post> posts;

    public Forum(int id, String title, String description, int idR, int idC) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.idR = idR;
        this.idC = idC;
    }

    public Forum(int id, String title, String description, int idC) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.idC = idC;
    }

    public Forum(int id, String title, int idR, String description) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.idR = idR;
    }

   

    public Forum() {
        posts = new ArrayList<>();
    }

    public Forum(int id, String title, String description) {
         posts = new ArrayList<>();
        this.id = id;
        this.title = title;
        this.description = description;
    }

    public Forum(String title, String description) {
         posts = new ArrayList<>();
        this.title = title;
        this.description = description;
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

    public int getIdR() {
        return idR;
    }

    public void setIdR(int idR) {
        this.idR = idR;
    }

    public int getIdC() {
        return idC;
    }

    public void setIdC(int idC) {
        this.idC = idC;
    }

    @Override
    public String toString() {
        return "Forum{" + "id=" + id + ", title=" + title + ", description=" + description + ", idR=" + idR + ", idC=" + idC + ", posts=" + posts + '}';
    }

   

   
   
   
}
