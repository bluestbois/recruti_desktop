/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package esprit.recrutini.entities;

/**
 *
 * @author asus
 */
public class Recruiter {

    private int id;
    private String name;
    private String username;
    private String email;
    private String password;
    private String adress;
    private String description;
    private String phone;
    private String image;
    private String field;
    private int randoom;

    public Recruiter(int id, String field, String name, String username, String email, String password, String adress, String description, String phone, String image, int randoom) {
        this.id = id;
        this.name = name;
        this.username = username;
        this.email = email;
        this.password = password;
        this.adress = adress;
        this.description = description;
        this.phone = phone;
        this.image = image;
        this.field = field;
        this.randoom = randoom;
    }

    public Recruiter() {
    }

    public Recruiter(String string, String string0, String string1, String string2, String string3, String string4, String string5) {
        this.name = string0;

        this.email = string1;
        this.password = string3;
        this.adress = string4;
        this.description = string2;
        this.phone = string5;
        this.field = string;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public int getRandoom() {
        return randoom;
    }

    public void setRandoom(int randoom) {
        this.randoom = randoom;
    }

    @Override
    public String toString() {
        return "Recruiter{" + "id=" + id + ", name=" + name + ", username=" + username + ", email=" + email + ", password=" + password + ", adress=" + adress + ", description=" + description + ", phone=" + phone + ", image=" + image + ", field=" + field + ", randoom=" + randoom + '}';
    }

}
