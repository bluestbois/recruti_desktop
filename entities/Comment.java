/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package esprit.recrutini.entities;

import java.util.Date;

/**
 *
 * @author ASUS
 */
public class Comment {
    private int id ; 
    private String content ; 
    private Date date ;
    private int rating ;
    private int idP ; 
    private int idC;
    private int idR;

    public Comment() {
    }

    public Comment(int id, String content, int rating) {
        this.id = id;
        this.content = content;
        this.rating = rating;
    }

    public Comment(int id, String content, Date date, int rating, int idP) {
        this.id = id;
        this.content = content;
        this.date = date;
        this.rating = rating;
        this.idP = idP;
    }

    public Comment(int id, String content, int rating, Date date) {
        this.id = id;
        this.content = content;
        this.date = date;
        this.rating = rating;
    }

    public Comment(int id, String content, Date date, int rating, int idP, int idC, int idR) {
        this.id = id;
        this.content = content;
        this.date = date;
        this.rating = rating;
        this.idP = idP;
        this.idC = idC;
        this.idR = idR;
    }

    public Comment(int id , int idC,String content, Date date, int rating, int idP) {
        this.id = id;
        this.content = content;
        this.date = date;
        this.rating = rating;
        this.idP = idP;
        this.idC = idC;
    }

    public Comment(int id, String content, Date date, int rating, int idP, int idR) {
        this.id = id;
        this.content = content;
        this.date = date;
        this.rating = rating;
        this.idP = idP;
        this.idR = idR;
    }

    public int getIdC() {
        return idC;
    }

    public void setIdC(int idC) {
        this.idC = idC;
    }

    public int getIdR() {
        return idR;
    }

    public void setIdR(int idR) {
        this.idR = idR;
    }

 

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public int getIdP() {
        return idP;
    }

    public void setIdP(int idP) {
        this.idP = idP;
    }

    @Override
    public String toString() {
        return "Comment{" + "id=" + id + ", content=" + content + ", date=" + date + ", rating=" + rating + ", idP=" + idP + ", idC=" + idC + ", idR=" + idR + '}';
    }

   
    
    
}
