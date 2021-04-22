/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entity;


import java.sql.ResultSet;
import CONNECTION.DataSource;
import java.sql.Statement;
import java.sql.SQLException;
import java.sql.Date;
import java.sql.PreparedStatement;

/**
 *
 * @author yessine darmoul
 */
public class Candidate {
   

    private int id;
    private String first_name;
    private String last_name;
    private String email;
    private String password;
    private Date birthday;
    private String gender;
    private String nationality;
    private String phone_number;
    private String address;
    private String image;
    private String loe;
    private String experience;
    

     public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstname() {
        return first_name;
    }

    public void setFirstname(String first_name) {
        this.first_name = first_name;
    }

    public String getLastname() {
        return last_name;
    }

    public void setLastname(String last_name) {
        this.last_name = last_name;
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

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getPhonenumber() {
        return phone_number;
    }

    public void setPhonenumber(String phone_number) {
        this.phone_number = phone_number;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getLoe() {
        return loe;
    }

    public void setLoe(String loe) {
        this.loe = loe;
    }

    public String getExperience() {
        return experience;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }
     @Override
    public String toString() {
        return "Candidate{" + "id=" + id + ", first_name=" + first_name+  ", last_name=" + last_name+ ", email=" + email + ", password=" + password +", birthday=" + birthday +", gender=" + gender +", nationality=" + nationality + ", phone_number=" + phone_number + ", address=" + address + ", image=" + image +  ", loe=" + loe + ", experience=" + experience +  '}';
    }

     public Candidate(String first_name, String last_name, String email,String password,Date birthday,String gender,String nationality, String phone_number,String address, String image,String loe,String experience) {
        
        this.first_name = first_name;
        this.last_name = last_name;
        this.email = email;
        this.password = password;
        this.birthday=birthday;
        this.gender=gender;
        this.nationality=nationality;
        this.phone_number = phone_number;
        this.address = address;
        this.image = image;
        this.loe=loe;
        this.experience=experience;
    
}
public Candidate(int id , String first_name, String last_name, String email, String gender, String nationality,String phone_number,String address,String loe,String experience)
{
   this.id=id;
   this.first_name=first_name;
   this.last_name=last_name;
   this.email=email;
   this.gender=gender;
   this.nationality=nationality;
   this.loe=loe;
   this.experience=experience;
   this.address=address;
   this.phone_number=phone_number;  
}

 public Candidate(int aInt, String string, String string0, String string1) {
    }

    public Candidate(Candidate Candidate) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }


 

    public Candidate(int id, String email, String password, String first_name, String phone_number, Date birthday, String city, String address, int status, int desactivate, int code) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.first_name = first_name;
        this.phone_number = phone_number;
        this.birthday = birthday;
        this.address = address;
    }

    

    public Candidate(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public Candidate(int id, String email) {
        this.email = email;
        this.id = id;
    }

    public Candidate(int idC, String first_name, String last_name) {
        this.id = idC;
        this.first_name = first_name;
        this.last_name = last_name;
          }

    public Candidate() {
        this.first_name = "";
        this.email = "";
    }
    
    @Override
    public int hashCode() {
        int hash = 3;
        hash = 59 * hash + this.id;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Candidate other = (Candidate) obj;
        return this.id == other.id;
    }



    public Candidate(int id, String email, String password, String first_name, String phone_number, Date birthday,  String address) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.first_name = first_name;
        this.phone_number = phone_number;
        this.birthday = birthday;
        this.address = address;
    }

    public Candidate(int id, String email ,
                        String first_name, String password,
                        String city, String address, String phone_number )
    {
        this.id=id;
        this.email=email;
        this.first_name=first_name;
        this.password=password;
        this.address=address;
        this.phone_number=phone_number;
      
    }

    public Candidate Session(Candidate si2) throws SQLException {
       
      // DataBaseConnection dc = null;  
     PreparedStatement pst = null;
     DataSource conn;
     String sql;
    ResultSet rs;
        conn= DataSource.getInstance();
          pst = conn.getCnx().prepareStatement("SELECT * FROM Candidate WHERE  email = ? and password = ? " );
            pst.setString(1, si2.getEmail());
            pst.setString(2, si2.getPassword());
            
            rs = pst.executeQuery();
            while (rs.next()) {
            
            Candidate c = new Candidate( rs.getInt("id"),rs.getString("first_name"));
            c.setId(rs.getInt("id"));
            c.setEmail(rs.getString("name"));
           
          
            return c;
        }
         
         
         
         
         return null;
         
     }
    

 

   

   
     
    
    
}
