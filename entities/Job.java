/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package esprit.recrutini.entities;

import java.sql.Date;

/**
 *
 * @author asus
 */
public class Job {

    private int id;
    private String title;
    private String description;
    private java.sql.Date date;
    private int recruiter_id;

    public Job(int id, String title, String description, Date date, int recruiter_id) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.date = date;
        this.recruiter_id = recruiter_id;
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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getRecruiter_id() {
        return recruiter_id;
    }

    public void setRecruiter_id(int recruiter_id) {
        this.recruiter_id = recruiter_id;
    }

    @Override
    public String toString() {
        return "Job{" + "id=" + id + ", title=" + title + ", description=" + description + ", date=" + date + ", recruiter_id=" + recruiter_id + '}';
    }
    
    
    

}
