/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package esprit.recrutini.entities;

import java.sql.Date;
/**
 *
 * @author yessine darmoul
 */
public class Candidature {
    private int id;
    private int candidate_id;
    private int job_id;
    private int freelance_id;
    private Date date;
    private int score;

     public Candidature(int id, int aInt0, int aInt1, int aInt2, Date date, int aInt3) {
         this.id = id;
        this.candidate_id = aInt0;
        this.job_id = aInt1;
        this.freelance_id = aInt2;
        this.date=date;
        this.score=aInt3;
    }
    
    
    
    public Candidature() {
        
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCandidate_id() {
        return candidate_id;
    }

    public void setCandidate_id(int candidate_id) {
        this.candidate_id = candidate_id;
    }

    public int getJob_id() {
        return job_id;
    }

    public void setJob_id(int job_id) {
        this.job_id = job_id;
    }

    public int getFreelance_id() {
        return freelance_id;
    }

    public void setFreelance_id(int freelance_id) {
        this.freelance_id = freelance_id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    @Override
    public String toString() {
        return "Candidature{" + "id=" + id + ", candidate_id=" + candidate_id + ", job_id=" + job_id + ", freelance_id=" + freelance_id + ", date=" + date + ", score=" + score + '}';
    }
    
    
    
}
