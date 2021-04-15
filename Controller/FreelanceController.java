/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Config.MaConnexion;
import Entities.Freelance;
import Services.GestionFreelance;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javax.swing.JOptionPane;

/**
 *
 * @author NOUSSA
 */
public class FreelanceController {
      
    Connection cnx;
    public PreparedStatement st;
    public ResultSet result;
    
    @FXML
    private TextField txt_searchID;
    @FXML
    private TextField txt_Title;
    @FXML
    private TextField txt_Description;
    @FXML
    private TextField txt_Salary;
    @FXML
    private TableView<Freelance> table_freelance;
    @FXML
    private TableColumn<Freelance, String> freelance_title;
    @FXML
    private TableColumn<Freelance, String> freelance_description;
    @FXML
    private TableColumn<Freelance, String> freelance_salary;
    @FXML
    private Button btn_add;
    @FXML
    private Button btn_edit;
    @FXML
    private Button btn_delete;
public ObservableList<Freelance> data = FXCollections.observableArrayList();
    /**
     * Initializes the controller class.
     */
    

    @FXML
    private void add_freelance() {
        cnx = MaConnexion.getinstance().getCnx();
        String title =txt_Title.getText();
        String description =txt_Description.getText();
        
        String sql="insert into freelance (title,description)VALUES (?,?)";
        if(!title.equals("")&&!description.equals("")){
            try{
                st=cnx.prepareStatement(sql);
                st.setString(1,title);
                st.setString(2,description);           
                st.execute();
                txt_Title.setText("");
                txt_Description.setText("");
                Alert alert = new Alert(AlertType.CONFIRMATION,"freelance opportunity added !",javafx.scene.control.ButtonType.OK);
                alert.showAndWait();
          show_freelance();
            }catch (SQLException e) {
                e.printStackTrace();
            }
        }else {
            Alert alert = new Alert(AlertType.WARNING,"veuillez remplir toute les champs",javafx.scene.control.ButtonType.OK);
            alert.showAndWait();
        }
    }

    @FXML
    private void edit_freelance() {
       cnx = MaConnexion.getinstance().getCnx();
        String title =txt_Title.getText();
        String description =txt_Description.getText();     
        String sql="update freelance set title=?,description=? where title = '"+txt_searchID.getText()+"'";
        if(!title.equals("")&&!description.equals("")){
            try{
                st=cnx.prepareStatement(sql);
                st.setString(1,title);
                st.setString(2,description);
                st.executeUpdate();
                txt_Title.setText("");
                txt_Description.setText("");
                Alert alert = new Alert(AlertType.CONFIRMATION,"modifie avec succes !",javafx.scene.control.ButtonType.OK);
                alert.showAndWait();
          show_freelance();
            }catch (SQLException e) {
                e.printStackTrace();
            }
        }else {
            Alert alert = new Alert(AlertType.WARNING,"veuillez remplir toute les champs",javafx.scene.control.ButtonType.OK);
            alert.showAndWait();
        }
        
    }

    @FXML
    private void delete_freelance() {
        cnx = MaConnexion.getinstance().getCnx();
        String sql="delete from freelance where title ='"+txt_searchID.getText()+"'";
        try{
            st=cnx.prepareStatement(sql);
            st.executeUpdate();
                txt_Title.setText("");
                txt_Description.setText("");
                Alert alert = new Alert(AlertType.CONFIRMATION,"supprimé avec succes !",javafx.scene.control.ButtonType.OK);
                alert.showAndWait();
          show_freelance();
            }catch (SQLException e) {
                e.printStackTrace();
            
        }
    }

    @FXML
    private void search_freelance()  {
        cnx = MaConnexion.getinstance().getCnx();
     String sql="select * from freelance where title ='"+txt_searchID.getText()+"'";
     int m=0;
     try {
     st=cnx.prepareStatement(sql);
     result=st.executeQuery();
     if(result.next()){
          txt_Title.setText(result.getString("title"));
          txt_Description.setText(result.getString("description"));
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
    public void show_freelance (){
        cnx = MaConnexion.getinstance().getCnx();
        table_freelance.getItems().clear();
        String sql="select * from freelance";
        try {
            st=cnx.prepareStatement (sql);
            result=st.executeQuery();
            while (result.next()){
                data.add(new Freelance (result.getString("title"),result.getString("description")));
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        freelance_title.setCellValueFactory(new PropertyValueFactory<Freelance, String>("title"));
        freelance_description.setCellValueFactory(new PropertyValueFactory<Freelance, String>("description"));
        table_freelance.setItems(data);
    }
    
   
    public void initialize (URL arg0, ResourceBundle arg1){
      cnx = MaConnexion.getinstance().getCnx();
       show_freelance();
    
}
}
