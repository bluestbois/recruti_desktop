/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package esprit.recrutini.controller;

import esprit.recrutini.entities.Candidate;
import esprit.recrutini.tools.DataSource;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import java.sql.Date;
import java.time.LocalDate;
import java.time.ZoneId;
import javafx.scene.control.DatePicker;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;

/**
 * FXML Controller class
 *
 * @author yessine darmoul
 */
public class CandidateCrudController implements Initializable {
   DataSource cnx;
    public PreparedStatement st;
    public ResultSet result;
    public ObservableList<Candidate> data = FXCollections.observableArrayList();

    @FXML
    private TextField txt_searchID;
    @FXML
    private TableView<Candidate> table_candidate;
    @FXML
    private TableColumn<Candidate, String> tv_firstname;
    @FXML
    private TableColumn<Candidate, String> tv_lastname;
    @FXML
    private TableColumn<Candidate, String> tv_email;
    @FXML
    private TableColumn<Candidate, String> tv_password;
    @FXML
    private TableColumn<Candidate, Date> tv_birthday;
    @FXML
    private TableColumn<Candidate, String> tv_gender;
    @FXML
    private TableColumn<Candidate, String> tv_nationality;
    @FXML
    private TableColumn<Candidate, String> tv_phonenumber;
    @FXML
    private TableColumn<Candidate, String> tv_address;
    @FXML
    private TableColumn<Candidate, String> tv_image;
    @FXML
    private TableColumn<Candidate, String> tv_loe;
    @FXML
    private TableColumn<Candidate, Integer> tv_experience;
    @FXML
    private Button btn_add;
    @FXML
    private Button btn_edit;
    @FXML
    private Button btn_delete;
    @FXML
    private TextField txt_firstname;
    @FXML
    private TextField txt_lastname;
    @FXML
    private TextField txt_email;
    @FXML
    private PasswordField txt_password;
    @FXML
    private TextField txt_gender;
    @FXML
    private TextField txt_nationality;
    @FXML
    private TextField txt_phonenumber;
    @FXML
    private TextField txt_address;
    @FXML
    private TextField txt_image;
    @FXML
    private TextField txt_loe;
    @FXML
    private TextField txt_experience;
    @FXML
    private DatePicker dp_birthday;
    

    /**
     * Initializes the controller class.
     */
   
    @FXML
    private void add_Candidate() {
        String first_name =txt_firstname.getText();
        String last_name =txt_lastname.getText();
        String email =txt_email.getText();
        String password =txt_password.getText();
        
        String gender =txt_gender.getText();
        String nationality =txt_nationality.getText();
        String address =txt_address.getText();
        String phone_number =txt_phonenumber.getText();
        String image =txt_image.getText();
        String loe =txt_loe.getText();
        String experience =txt_experience.getText();
        
        
        String sql="insert into candidate (first_name,last_name,email,password,birthday,gender,nationality,address,phone_number,image,loe,experience)VALUES (?,?,?,?,?,?,?,?,?,?,?,?)";
        if(!first_name.equals("")&&!last_name.equals("")&&!nationality.equals("")&&!phone_number.equals("")&&!image.equals("")&&!email.equals("")&&!password.equals("")){
            try{
                st=cnx.getCnx().prepareStatement(sql);
                st.setString(1,first_name);
                st.setString(2,last_name);
                st.setString(3,email);
                st.setString(4,password);
                //java.util.Date date=java.util.Date.from(dp_birthday.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
                //Date sqlDate=new Date(date.getTime());
                st.setDate(5,null);
                st.setString(6,gender);
                st.setString(7,nationality);
                st.setString(9,address);
                st.setString(8,phone_number);
                st.setString(10,image);
                st.setString(11,loe);
                st.setString(12,experience);
                st.execute();
                txt_firstname.setText("");
                txt_lastname.setText("");
                txt_email.setText("");
                txt_password.setText("");
                txt_gender.setText("");
                txt_nationality.setText("");
                txt_address.setText("");
                txt_phonenumber.setText("");
                txt_image.setText("");
                txt_loe.setText("");
                txt_experience.setText("");
                //dp_birthday.setValue(null);
                
                Alert alert = new Alert(AlertType.CONFIRMATION,"Candidate added !",javafx.scene.control.ButtonType.OK);
                alert.showAndWait();
                show_candidate();
            }catch (SQLException e) {
                e.printStackTrace();
            }
        }else {
            Alert alert = new Alert(AlertType.WARNING,"veuillez remplir les champs",javafx.scene.control.ButtonType.OK);
            alert.showAndWait();
        }
    }

    @FXML
    private void edit_candidate() {
        String first_name =txt_firstname.getText();
        String last_name =txt_lastname.getText();
        String email =txt_email.getText();
        String password =txt_password.getText();
        //Date birthday =dp_birthday.getDate();
        String gender =txt_gender.getText();
        String nationality =txt_nationality.getText();
        String address =txt_address.getText();
        String phone_number =txt_phonenumber.getText();
        String image =txt_image.getText();
        String loe =txt_loe.getText();
        String experience =txt_experience.getText();
        
        String sql="update candidate set first_name=?,last_name=?,email=?,password=?,birthday=?,gender=?,nationality=?,phone_number=?,address=?,image=?,loe=?,experience=? where first_name = '"+txt_searchID.getText()+"'";
        if(!first_name.equals("")&&!last_name.equals("")&&!nationality.equals("")&&!phone_number.equals("")&&!email.equals("")&&!password.equals("")){
            try{
                st=cnx.getCnx().prepareStatement(sql);
                st.setString(1,first_name);
                st.setString(2,last_name);
                st.setString(3,email);
                st.setString(4,password);
                //java.util.Date date=java.util.Date.from(dp_birthday.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
                //Date sqlDate=new Date(date.getTime());
                st.setDate(5,null);
                st.setString(6,gender);
                st.setString(7,nationality);
                st.setString(8,phone_number);
                st.setString(9,address);
                st.setString(10,image);
                st.setString(11,loe);
                st.setString(12,experience);
                st.executeUpdate();
                txt_firstname.setText("");
                txt_lastname.setText("");
                txt_email.setText("");
                txt_password.setText("");
                //dp_birthday.setValue(null);
                txt_gender.setText("");
                txt_nationality.setText("");
                txt_address.setText("");
                txt_phonenumber.setText("");
                txt_image.setText("");
                txt_loe.setText("");
                txt_experience.setText("");
                Alert alert = new Alert(AlertType.CONFIRMATION,"modifie avec succes !",javafx.scene.control.ButtonType.OK);
                alert.showAndWait();
          show_candidate();
            }catch (SQLException e) {
                e.printStackTrace();
            }
        }else {
            Alert alert = new Alert(AlertType.WARNING,"veuillez remplir toute les champs",javafx.scene.control.ButtonType.OK);
            alert.showAndWait();
        }
        
    }

    @FXML
    private void delete_candidate() {
        String sql="delete from candidate where email ='"+txt_searchID.getText()+"'";
        try{
            st=cnx.getCnx().prepareStatement(sql);
            st.executeUpdate();
            txt_firstname.setText("");
                txt_lastname.setText("");
                txt_email.setText("");
                txt_password.setText("");
                //dp_birthday.setValue(null);
                txt_gender.setText("");
                txt_nationality.setText("");
                txt_address.setText("");
                txt_phonenumber.setText("");
                txt_image.setText("");
                txt_loe.setText("");
                txt_experience.setText("");
                Alert alert = new Alert(AlertType.CONFIRMATION,"supprimé avec succes !",javafx.scene.control.ButtonType.OK);
                alert.showAndWait();
          show_candidate();
            }catch (SQLException e) {
                e.printStackTrace();
            
        }
    }

    @FXML
    private void search_candidate()  {
     String sql="select * from candidate where email ='"+txt_searchID.getText()+"'";
     int m=0;
     try {
     st=cnx.getCnx().prepareStatement(sql);
     result=st.executeQuery();
     if(result.next()){
          txt_firstname.setText(result.getString("first_name"));
          txt_lastname.setText(result.getString("last_name"));
          txt_email.setText(result.getString("email"));
          txt_password.setText(result.getString("password"));
         // dp_birthday.setDate(result.getValue("birthday"));
          txt_gender.setText(result.getString("gender"));
          txt_nationality.setText(result.getString("nationality"));
          txt_address.setText(result.getString("address"));
          txt_phonenumber.setText(result.getString("phone_number"));
          txt_image.setText(result.getString("image"));
          txt_loe.setText(result.getString("loe"));
          txt_experience.setText(result.getString("experience"));
          m=1;
     }
    }catch (SQLException e){
    e.printStackTrace();
}
     if (m==0){
         Alert alert = new Alert(AlertType.ERROR,"No Candidate with the email="+txt_searchID.getText()+"", javafx.scene.control.ButtonType.OK);
         alert.showAndWait();
     }
    }
    public void show_candidate (){
        table_candidate.getItems().clear();
        String sql="select * from candidate";
        try {
            st=cnx.getCnx().prepareStatement (sql);
            result=st.executeQuery();
            while (result.next()){
                data.add(new Candidate (result.getString("first_name"),result.getString("last_name"),result.getString("email"),result.getString("password"),result.getDate("birthday"),result.getString("gender"),result.getString("nationality"),result.getString("phone_number"),result.getString("address"),result.getString("image"),result.getString("loe"),result.getString("experience")));
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
            tv_firstname.setCellValueFactory(new PropertyValueFactory<Candidate, String>("firstname"));
            tv_lastname.setCellValueFactory(new PropertyValueFactory<Candidate, String>("lastname"));
            tv_email.setCellValueFactory(new PropertyValueFactory<Candidate, String>("email"));
            tv_password.setCellValueFactory(new PropertyValueFactory<Candidate, String>("password"));
            tv_birthday.setCellValueFactory(new PropertyValueFactory<Candidate, Date>("birthday"));
             tv_gender.setCellValueFactory(new PropertyValueFactory<Candidate, String>("gender"));
             tv_nationality.setCellValueFactory(new PropertyValueFactory<Candidate, String>("nationality"));
             tv_phonenumber.setCellValueFactory(new PropertyValueFactory<Candidate, String>("phonenumber"));
             tv_address.setCellValueFactory(new PropertyValueFactory<Candidate, String>("address"));
             tv_image.setCellValueFactory(new PropertyValueFactory<Candidate, String>("image"));
             tv_loe.setCellValueFactory(new PropertyValueFactory<Candidate, String>("loe"));
             tv_experience.setCellValueFactory(new PropertyValueFactory<Candidate, Integer>("experience"));
             table_candidate.setItems(data);
    }
    
    @Override
    public void initialize (URL arg0, ResourceBundle arg1){
       cnx=DataSource.getInstance();
       show_candidate();
    
}
}

    
