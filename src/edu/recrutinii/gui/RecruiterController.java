/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.recrutinii.gui;

import edu.recrutinii.entities.Recruiter;
import edu.recrutinii.tests.ConnexionMysql;
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

/**
 * FXML Controller class
 *
 * @author asus
 */
public class RecruiterController implements Initializable {
Connection cnx;
    public PreparedStatement st;
    public ResultSet result;
    
    @FXML
    private TextField txt_searchID;
    @FXML
    private TextField txt_Name;
    @FXML
    private TextField txt_address;
    @FXML
    private TextField txt_description;
    @FXML
    private TextField txt_phone_number;
    @FXML
    private TextField txt_image;
    @FXML
    private TextField txt_field_id;
    @FXML
    private TextField txt_email;
    @FXML
    private PasswordField txt_password;
    @FXML
    private TableView<Recruiter> table_recruiter;
    @FXML
    private TableColumn<Recruiter, String> recruiter_name;
    @FXML
    private TableColumn<Recruiter, String> recruiter_address;
    @FXML
    private TableColumn<Recruiter, String> recruiter_description;
    @FXML
    private TableColumn<Recruiter, String> recruiter_phone_number;
    @FXML
    private TableColumn<Recruiter, String> recruiter_field_id;
    @FXML
    private TableColumn<Recruiter, String> recruiter_email;
    @FXML
    private Button btn_add;
    @FXML
    private Button btn_edit;
    @FXML
    private Button btn_delete;
public ObservableList<Recruiter> data = FXCollections.observableArrayList();
    /**
     * Initializes the controller class.
     */
    

    @FXML
    private void add_recruiter() {
        String name =txt_Name.getText();
        String address =txt_address.getText();
        String description =txt_description.getText();
        String phone_number =txt_phone_number.getText();
        String image =txt_image.getText();
        String field_id =txt_field_id.getText();
        String email =txt_email.getText();
        String password =txt_password.getText();
        
        String sql="insert into recruiter (name,address,description,phone_number,image,field_id,email,password)VALUES (?,?,?,?,?,?,?,?)";
        if(!name.equals("")&&!address.equals("")&&!description.equals("")&&!phone_number.equals("")&&!image.equals("")&&!field_id.equals("")&&!email.equals("")&&!password.equals("")){
            try{
                st=cnx.prepareStatement(sql);
                st.setString(1,name);
                st.setString(2,address);
                st.setString(3,description);
                st.setString(4,phone_number);
                st.setString(5,image);
                st.setString(6,field_id);
                st.setString(7,email);
                st.setString(8,password);
                st.execute();
                txt_Name.setText("");
                txt_address.setText("");
                txt_description.setText("");
                txt_phone_number.setText("");
                txt_image.setText("");
                txt_field_id.setText("");
                txt_email.setText("");
                txt_password.setText("");
                Alert alert = new Alert(AlertType.CONFIRMATION,"recruiter added !",javafx.scene.control.ButtonType.OK);
                alert.showAndWait();
          show_recruiter();
            }catch (SQLException e) {
                e.printStackTrace();
            }
        }else {
            Alert alert = new Alert(AlertType.WARNING,"veuillez remplir toute les champs",javafx.scene.control.ButtonType.OK);
            alert.showAndWait();
        }
    }

    @FXML
    private void edit_recruiter() {
        String name =txt_Name.getText();
        String address =txt_address.getText();
        String description =txt_description.getText();
        String phone_number =txt_phone_number.getText();
        String image =txt_image.getText();
        String field_id =txt_field_id.getText();
        String email =txt_email.getText();
        String password =txt_password.getText();
        
        String sql="update recruiter set name=?,address=?,description=?,phone_number=?,image=?,field_id=?,email=?,password=? where name = '"+txt_searchID.getText()+"'";
        if(!name.equals("")&&!address.equals("")&&!description.equals("")&&!phone_number.equals("")&&!image.equals("")&&!field_id.equals("")&&!email.equals("")&&!password.equals("")){
            try{
                st=cnx.prepareStatement(sql);
                st.setString(1,name);
                st.setString(2,address);
                st.setString(3,description);
                st.setString(4,phone_number);
                st.setString(5,image);
                st.setString(6,field_id);
                st.setString(7,email);
                st.setString(8,password);
                st.executeUpdate();
                txt_Name.setText("");
                txt_address.setText("");
                txt_description.setText("");
                txt_phone_number.setText("");
                txt_image.setText("");
                txt_field_id.setText("");
                txt_email.setText("");
                txt_password.setText("");
                Alert alert = new Alert(AlertType.CONFIRMATION,"modifie avec succes !",javafx.scene.control.ButtonType.OK);
                alert.showAndWait();
          show_recruiter();
            }catch (SQLException e) {
                e.printStackTrace();
            }
        }else {
            Alert alert = new Alert(AlertType.WARNING,"veuillez remplir toute les champs",javafx.scene.control.ButtonType.OK);
            alert.showAndWait();
        }
        
    }

    @FXML
    private void delete_recruiter() {
        String sql="delete from recruiter where name ='"+txt_searchID.getText()+"'";
        try{
            st=cnx.prepareStatement(sql);
            st.executeUpdate();
            txt_Name.setText("");
                txt_address.setText("");
                txt_description.setText("");
                txt_phone_number.setText("");
                txt_image.setText("");
                txt_field_id.setText("");
                txt_email.setText("");
                txt_password.setText("");
                Alert alert = new Alert(AlertType.CONFIRMATION,"supprimé avec succes !",javafx.scene.control.ButtonType.OK);
                alert.showAndWait();
          show_recruiter();
            }catch (SQLException e) {
                e.printStackTrace();
            
        }
    }
    @FXML
    void tablerecruiter_event() {
        Recruiter recruiter=table_recruiter.getSelectionModel().getSelectedItem();
        String sql="select * from recruiter where name = ?";
        try {
            st=cnx.prepareStatement(sql);
            st.setString (1,recruiter.getName());
            result=st.executeQuery();
            if(result.next()){
                txt_Name.setText(result.getString("name"));
          txt_address.setText(result.getString("address"));
          txt_description.setText(result.getString("description"));
          txt_phone_number.setText(result.getString("phone_number"));
          txt_image.setText(result.getString("image"));
          txt_field_id.setText(result.getString("field_id"));
          txt_email.setText(result.getString("email"));
          txt_password.setText(result.getString("password"));
          txt_searchID.setText(result.getString("name"));
            }
        }catch (SQLException e){
            e.printStackTrace();
        }

    }

    @FXML
    private void search_recruiter()  {
     String sql="select * from recruiter where name ='"+txt_searchID.getText()+"'";
     int m=0;
     try {
     st=cnx.prepareStatement(sql);
     result=st.executeQuery();
     if(result.next()){
          txt_Name.setText(result.getString("name"));
          txt_address.setText(result.getString("address"));
          txt_description.setText(result.getString("description"));
          txt_phone_number.setText(result.getString("phone_number"));
          txt_image.setText(result.getString("image"));
          txt_field_id.setText(result.getString("field_id"));
          txt_email.setText(result.getString("email"));
          txt_password.setText(result.getString("password"));
          m=1;
     }
    }catch (SQLException e){
    e.printStackTrace();
}
     if (m==0){
         Alert alert = new Alert(AlertType.ERROR,"Aucun locataire avec nom="+txt_searchID.getText()+"", javafx.scene.control.ButtonType.OK);
         alert.showAndWait();
     }
    }
    public void show_recruiter (){
        table_recruiter.getItems().clear();
        String sql="select * from recruiter";
        try {
            st=cnx.prepareStatement (sql);
            result=st.executeQuery();
            while (result.next()){
                data.add(new Recruiter (result.getString("field_id"),result.getString("name"),result.getString("email"),result.getString("description"),result.getString("password"),result.getString("address"),result.getString("phone_number"),result.getString("image")));
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        recruiter_name.setCellValueFactory(new PropertyValueFactory<Recruiter, String>("name"));
         recruiter_address.setCellValueFactory(new PropertyValueFactory<Recruiter, String>("address"));
          recruiter_description.setCellValueFactory(new PropertyValueFactory<Recruiter, String>("description"));
           recruiter_phone_number.setCellValueFactory(new PropertyValueFactory<Recruiter, String>("phone_number"));
            recruiter_field_id.setCellValueFactory(new PropertyValueFactory<Recruiter, String>("field_id"));
             recruiter_email.setCellValueFactory(new PropertyValueFactory<Recruiter, String>("email"));
             table_recruiter.setItems(data);
    }
    
    @Override
    public void initialize (URL arg0, ResourceBundle arg1){
       cnx=ConnexionMysql.connexionDB();
       show_recruiter();
    
}
}

    
