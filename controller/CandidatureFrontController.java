/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package esprit.recrutini.controller;

import esprit.recrutini.entities.Candidate;
import esprit.recrutini.entities.Candidature;
import esprit.recrutini.services.CandidateSession;
import static esprit.recrutini.services.CandidateSession.currentCandidate;
import esprit.recrutini.tools.DataSource;
import java.io.IOException;
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
import static java.sql.Types.NULL;
import java.time.LocalDate;
import java.time.ZoneId;
import javafx.scene.control.DatePicker;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author yessine darmoul
 */
public class CandidatureFrontController implements Initializable {

    DataSource cnx;
    public PreparedStatement st;
    public ResultSet result;
    public ObservableList<Candidature> data = FXCollections.observableArrayList();
    @FXML
    private TextField txt_searchID;
    @FXML
    private TableView<Candidature> table_candidature;
    
    @FXML
    private Button btn_delete;
    @FXML
    private TableColumn<Candidature,Integer> tv_id;
    @FXML
    private TableColumn<Candidature,Integer> tv_job;
    @FXML
    private TableColumn<Candidature,Integer> tv_freelance;
    @FXML
    private TableColumn<Candidature,Date> tv_date;
    @FXML
    private TableColumn<Candidature,Integer> tv_score;
    @FXML
    private TextField txt_job;
    @FXML
    private TextField txt_id;
    @FXML
    private TextField txt_freelance;
    @FXML
    private DatePicker date;
    @FXML
    private TextField txt_score;
    
    Candidature x ;
    @FXML
    private Button profile;
    @FXML
    private ImageView btnprofile;
    @FXML
    private Button btnForum;
    @FXML
    private Button btnFreelance;
    @FXML
    private Button btnJob;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
         cnx=DataSource.getInstance();
       show_candidature();
    }    

    public void show_candidature (){
        table_candidature.getItems().clear();
        String sql="select * from candidature where candidate_id ='" + currentCandidate.getId() + "'";
        try {
            st=cnx.getCnx().prepareStatement (sql);
            result=st.executeQuery();
            while (result.next()){
                data.add(new Candidature (result.getInt("id"),result.getInt("candidate_id"),result.getInt("Job_id"),result.getInt("Freelance_id"),result.getDate("date"),result.getInt("score")));
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
            tv_id.setCellValueFactory(new PropertyValueFactory<Candidature, Integer>("id"));
            tv_job.setCellValueFactory(new PropertyValueFactory<Candidature, Integer>("job_id"));
            tv_freelance.setCellValueFactory(new PropertyValueFactory<Candidature, Integer>("freelance_id"));
            
            tv_date.setCellValueFactory(new PropertyValueFactory<Candidature, Date>("date"));
             tv_score.setCellValueFactory(new PropertyValueFactory<Candidature, Integer>("score"));
             
             table_candidature.setItems(data);
             
    }
    
    
    
    @FXML
    private void search_candidature(MouseEvent event) {
     String sql="select * from candidature where id ='"+txt_searchID.getText()+"'";
     int m=0;
     try {
     st=cnx.getCnx().prepareStatement(sql);
     result=st.executeQuery();
     if(result.next()){
          txt_id.setText(result.getString("id"));
          txt_job.setText(result.getString("job_id"));
          txt_freelance.setText(result.getString("freelance_id"));
          //date.setValue(result.getDate("date"));
          txt_score.setText(result.getString("score"));
          
          m=1;
     }
    }catch (SQLException e){
    e.printStackTrace();
}
     if (m==0){
         Alert alert = new Alert(AlertType.ERROR,"No Candidature with an id="+txt_searchID.getText()+"", javafx.scene.control.ButtonType.OK);
         alert.showAndWait();
     }
    }


    @FXML
    private void delete_candidature() {
        Candidature c = table_candidature.getSelectionModel().getSelectedItem();
        txt_id.setText("" + c.getId());
        txt_job.setText("" + c.getJob_id());
        txt_freelance.setText("" + c.getFreelance_id());
        //date.setDate((c.getDate()));
        txt_score.setText("" + c.getScore());
        
        String sql="delete from candidature where id ='"+txt_id.getText()+"'";
        try{
                st=cnx.getCnx().prepareStatement(sql);
                st.executeUpdate();
                txt_id.setText("");
                txt_job.setText("");
                txt_freelance.setText("");
                date.setValue(null);
                txt_score.setText("");
                
                Alert alert = new Alert(AlertType.CONFIRMATION,"Deleted",javafx.scene.control.ButtonType.OK);
                alert.showAndWait();
            show_candidature();
            }catch (SQLException e) {
                e.printStackTrace();
            
        }
    }

    @FXML
    private void Profile(ActionEvent event) {
          if (currentCandidate.getId() != NULL) {
            try {
                Node node = (Node) event.getSource();
                Stage stage = (Stage) node.getScene().getWindow();
                stage.close();

                Scene scene = new Scene(FXMLLoader.load(getClass().getResource("/esprit/recrutini/view/ProfilCandidate.fxml")));
                stage.setScene(scene);
                stage.show();
            } catch (IOException ex) {
                Logger.getLogger(AnnonceFrontController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        else 
        {
            try {
                Node node = (Node) event.getSource();
                Stage stage = (Stage) node.getScene().getWindow();
                stage.close();

                Scene scene = new Scene(FXMLLoader.load(getClass().getResource("/esprit/recrutini/view/Profil.fxml")));
                stage.setScene(scene);
                stage.show();
            } catch (IOException ex) {
                Logger.getLogger(AnnonceFrontController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @FXML
    private void Forum(ActionEvent event) {
        try {
            Node node = (Node) event.getSource();
            Stage stage = (Stage) node.getScene().getWindow();
            stage.close();

            Scene scene = new Scene(FXMLLoader.load(getClass().getResource("/esprit/recrutini/view/Forum.fxml")));
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(AnnonceFrontController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void Freelance(ActionEvent event) {
         try {
            Node node = (Node) event.getSource();
            Stage stage = (Stage) node.getScene().getWindow();
            stage.close();

            Scene scene = new Scene(FXMLLoader.load(getClass().getResource("/esprit/recrutini/view/AnnonceFront.fxml")));
            stage.setScene(scene);
            stage.show();

        } catch (IOException ex) {
            Logger.getLogger(ProfilController.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void Job(ActionEvent event) {
         try {
            Node node = (Node) event.getSource();
            Stage stage = (Stage) node.getScene().getWindow();
            stage.close();

            Scene scene = new Scene(FXMLLoader.load(getClass().getResource("/esprit/recrutini/view/Jobs.fxml")));
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(ProfilController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
