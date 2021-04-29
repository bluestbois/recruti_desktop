/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package esprit.recrutini.entities;

import java.sql.Date;

/**
 *
 * @author ASUS
 */
public class Post {

    private int id;
    private String title;
    private String description;
    private int views;
    private int noc;
    private Date date;
    private int idF;
    private Forum forum;
    private int idC;
    private int idR;

    public Post() {
    }

    public Post(int id, String title, String description, int views, int noc, Date date, int idF) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.views = views;
        this.noc = noc;
        this.date = date;
        this.idF = idF;
    }

    public Post(int id, String title, String description, int views, int noc, Date date, int idF, int idC, int idR) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.views = views;
        this.noc = noc;
        this.date = date;
        this.idF = idF;
        this.idC = idC;
        this.idR = idR;
    }

    public Post(int id, String title, int idC, String description, int views, int noc, Date date, int idF) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.views = views;
        this.noc = noc;
        this.date = date;
        this.idF = idF;
        this.idC = idC;
    }

    public Post(int id, String title, String description, int views, int noc, Date date, int idF, int idR) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.views = views;
        this.noc = noc;
        this.date = date;
        this.idF = idF;
        this.idR = idR;
    }

    public Post(int id, String title, String description) {
        this.id = id;
        this.title = title;
        this.description = description;
    }

    public Post(int id, String title, String description, int noc) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.noc = noc;
    }

    public Post(int id, String title, String description, int views, int noc) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.views = views;
        this.noc = noc;
    }

    public Post(int id, String title, String description, int views, int noc, Date date) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.views = views;
        this.noc = noc;
        this.date = date;
    }

    public Post(String title, String description, int views, int noc, Date date) {
        this.title = title;
        this.description = description;
        this.views = views;
        this.noc = noc;
        this.date = date;
    }

    public int getIdF() {
        return idF;
    }

    public void setIdF(int idF) {
        this.idF = idF;
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

    public int getViews() {
        return views;
    }

    public void setViews(int views) {
        this.views = views;
    }

    public int getNoc() {
        return noc;
    }

    public void setNoc(int noc) {
        this.noc = noc;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Forum getForum() {
        return forum;
    }

    public void setForum(Forum forum) {
        this.forum = forum;
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

    @Override
    public String toString() {
        return "Post{" + "id=" + id + ", title=" + title + ", description=" + description + ", views=" + views + ", noc=" + noc + ", date=" + date + ", idF=" + idF + ", forum=" + forum + ", idC=" + idC + ", idR=" + idR + '}';
    }

  
    

   

}
