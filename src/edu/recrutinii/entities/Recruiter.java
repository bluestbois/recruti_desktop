/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.recrutinii.entities;



/**
 *
 * @author asus
 */
public class Recruiter {
    private int id ;
    private String field_id ;
    private String name ;
    private String email ;
    private String description;
    private String password ;
    private String address ;
    private String phone_number;
    private String image ;

    public Recruiter() {
        super();
    }
    

    public Recruiter(String field_id, String name, String email, String description, String password,String address, String phone_number, String image) {
        
        this.field_id = field_id;
        this.name = name;
        this.email = email;
        this.description = description;
        this.password = password;
         this.address = address;
        this.phone_number = phone_number;
        this.image = image;
    }

   

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getField_id() {
        return field_id;
    }

    public void setField_id(String field_id) {
        this.field_id = field_id;
    }
 public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
     public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "Recruiter{" + "id=" + id + ", field_id=" + field_id + ", email=" + email + ", name=" + name + ", description=" + description + ", password=" + password + ", address=" + address +", phone_number=" + phone_number + ", image=" + image + '}';
    }

   
    

   


}
