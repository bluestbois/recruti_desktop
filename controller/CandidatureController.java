/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package esprit.recrutini.controller;

import esprit.recrutini.tools.DataSource;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import com.sun.prism.impl.Disposer;
import esprit.recrutini.entities.Candidature;
import esprit.recrutini.entities.Job;
import esprit.recrutini.entities.Candidate;
import esprit.recrutini.entities.Freelance;
import esprit.recrutini.services.ServiceCandidature;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.util.Callback;
import javax.swing.JOptionPane;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import java.sql.Date;
import esprit.recrutini.services.CandidateSession;
import static esprit.recrutini.services.CandidateSession.currentCandidate;
import esprit.recrutini.services.Service_Candidate;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;


/**
 * FXML Controller class
 *
 * @author yessine darmoul
 */

public class CandidatureController implements Initializable {
    DataSource cnx;
    public PreparedStatement pst;
    public ResultSet result;
    @FXML
    private Pane pnlCustomer;
    @FXML
    private Pane pnlOrders;
    @FXML
    private Pane pnlMenus;
    @FXML
    private Pane pnlOverview;
    @FXML
    private TableView<Candidature> tableview;
    @FXML
    private TableColumn<Candidature, Integer> t_id;
    @FXML
    private TableColumn<Candidature, Integer> t_idcandidate;
    @FXML
    private TableColumn<Candidature, Integer> t_idjob;
    @FXML
    private TableColumn<Candidature, Integer> t_idfreelance;
    @FXML
    private TableColumn<Candidature, Date> t_iddate;
    @FXML
    private TableColumn<Candidature, Integer> t_score;
    private JFXComboBox<String> combocandidate;
    private JFXComboBox<String> combojob;
    private JFXTextField updateidcandidate;
    private JFXTextField updateidjob;
    private JFXTextField updateidcandidature;
    private JFXComboBox<String> combofreelance;
    private JFXTextField updateidfreelance;
    private JFXTextField updatescore;

    ServiceCandidature serv=new ServiceCandidature();
    
        ObservableList<Candidature> oblist = FXCollections.observableArrayList();
    @FXML
    private Label updatecours;
    @FXML
    private Button profile;
    @FXML
    private ImageView btnprofile;
    @FXML
    private Button btnforum;
    @FXML
    private Button btnFreelance;
    @FXML
    private Button btnProjects;
    @FXML
    private Button btnJobs;
    @FXML
    private Button btnCandidature;

    
   
   

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
 
        try {
            show_candidature();
        } catch (SQLException ex) {
            Logger.getLogger(CandidatureController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }    
        public void show_candidature () throws SQLException{
//        tableview.getItems().clear();
//        String sql="select * from candidate";
//        try {
//            pst=cnx.getCnx().prepareStatement (sql);
//            result=pst.executeQuery();
//            while (result.next()){
//                oblist.add(new Candidature (result.getInteger("id"),result.getString("Candidate_id"),result.getString("job_id"),result.getString("freelance_id"),result.getDate("date"),result.getString("score")));
//            }
//        }catch (SQLException e){
//            e.printStackTrace();
//        }
//            tv_firstname.setCellValueFactory(new PropertyValueFactory<Candidate, String>("firstname"));
//            tv_lastname.setCellValueFactory(new PropertyValueFactory<Candidate, String>("lastname"));
//            tv_email.setCellValueFactory(new PropertyValueFactory<Candidate, String>("email"));
//            tv_password.setCellValueFactory(new PropertyValueFactory<Candidate, String>("password"));
//            tv_birthday.setCellValueFactory(new PropertyValueFactory<Candidate, Date>("birthday"));
//             tv_gender.setCellValueFactory(new PropertyValueFactory<Candidate, String>("gender"));
//             tv_nationality.setCellValueFactory(new PropertyValueFactory<Candidate, String>("nationality"));
//             tv_phonenumber.setCellValueFactory(new PropertyValueFactory<Candidate, String>("phonenumber"));
//             tv_address.setCellValueFactory(new PropertyValueFactory<Candidate, String>("address"));
//             tv_image.setCellValueFactory(new PropertyValueFactory<Candidate, String>("image"));
//             tv_loe.setCellValueFactory(new PropertyValueFactory<Candidate, String>("loe"));
//             tv_experience.setCellValueFactory(new PropertyValueFactory<Candidate, Integer>("experience"));
//             table_candidate.setItems(data);
            tableview.getItems().clear();
            for (Candidature d : serv.readAll()) {
                oblist.add(d);
                
            }
            
            t_id.setCellValueFactory(new PropertyValueFactory<>("id"));
            t_idcandidate.setCellValueFactory(new PropertyValueFactory<>("candidate_id"));
            t_idjob.setCellValueFactory(new PropertyValueFactory<>("Job_id"));
            t_idfreelance.setCellValueFactory(new PropertyValueFactory<>("freelance_id"));
            t_iddate.setCellValueFactory(new PropertyValueFactory<>("date"));
            t_score.setCellValueFactory(new PropertyValueFactory<>("score"));
        
            tableview.setItems(oblist);
            TableColumn col_action = new TableColumn<>("supprimer");
            tableview.getColumns().add(col_action);
        col_action.setCellValueFactory(
                new Callback<TableColumn.CellDataFeatures<Disposer.Record, Boolean>, ObservableValue<Boolean>>() {
            
            @Override
            public ObservableValue<Boolean> call(TableColumn.CellDataFeatures<Disposer.Record, Boolean> p) {
                return new SimpleBooleanProperty(p.getValue() != null);
            }
        });

        //Adding the Button to the cell
        col_action.setCellFactory(
                new Callback<TableColumn<Disposer.Record, Boolean>, TableCell<Disposer.Record, Boolean>>() {
            
            @Override
            public TableCell<Disposer.Record, Boolean> call(TableColumn<Disposer.Record, Boolean> p) {
                return new ButtonDeleteCandidature();
            }
            
        });
    }
    

    @FXML
    private void modifiercoursetudiant(MouseEvent event) throws SQLException {
        Candidature c = tableview.getSelectionModel().getSelectedItem();
        updateidcandidature.setText("" + c.getId());
        updateidcandidate.setText("" + c.getCandidate_id());
        updateidjob.setText("" + c.getJob_id());
        updateidfreelance.setText("" + c.getFreelance_id());
        updatescore.setText("" + c.getScore());
       
        
        show_candidature();
        
    
    }

    private void affectercoursetudiant(ActionEvent event) throws SQLException {
        
      
        Candidature c=new Candidature();
        c.setCandidate_id(Integer.parseInt(combocandidate.getValue()) );
        c.setJob_id(Integer.parseInt(combojob.getValue()));
        c.setFreelance_id(Integer.parseInt(combofreelance.getValue()));
        
        serv.ajouter(c);
        
        show_candidature();
        
        
    }


    private void buttonmodifiercandidature(ActionEvent event) 
        
        
         throws SQLException, IOException {
        
        if (updateidcandidate.getText().isEmpty() || updateidjob.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "please fill all the textfields");
        } else {
            
            Candidature d = tableview.getSelectionModel().getSelectedItem();
            int idcandidature = Integer.parseInt(updateidcandidature.getText());
            int idcandidate = Integer.parseInt(updateidcandidate.getText());
            int idjob = Integer.parseInt(updateidjob.getText());
            int idfreelance = Integer.parseInt(updateidfreelance.getText());
            int score = Integer.parseInt(updatescore.getText());
            
            serv.update(idcandidature,idcandidate,idjob,idfreelance,score);
            show_candidature();
            FXMLLoader.load(getClass().getResource("/esprit/recrutini/view/Candidature.fxml"));

        }
    }

    
//     @FXML
//    private void savetoword(ActionEvent event) throws FileNotFoundException, IOException {
//         XWPFDocument document = new XWPFDocument();
//       FileOutputStream out = new FileOutputStream(new File("demo.docx"));
//       XWPFParagraph paragraph = document.createParagraph();
//       XWPFRun run = paragraph.createRun();
//        String value1 = updateidcandidature.getText();
//           
//            
//            String value2 = updateidcandidate.getText();
//            String value3 = updateidjob.getText();
//            String value4 = updateidfreelance.getText();
//            String value5 = updatescore.getText();
//           
//            String s1 = "";
//            s1= s1.concat("     ID :"     ).concat(value1).concat("     ID Candidate:     ").concat(value2).concat("      ID Job:    ").concat("      ID Freelance:    ").concat("      Score:    ");
//       run.setText(s1);
//       document.write(out);
//       out.close();
//       Alert alert = new Alert(Alert.AlertType.INFORMATION);
//                alert.setTitle("Information");
//                alert.setHeaderText(null);
//                alert.setContentText("DOCUMENT Saved");
//                
//                alert.showAndWait();
//    }


    @FXML
    private void Profils(ActionEvent event) {
         try {
            Node node = (Node) event.getSource();
            Stage stage = (Stage) node.getScene().getWindow();
            stage.close();
            
            Scene scene = new Scene(FXMLLoader.load(getClass().getResource("/esprit/recrutini/view/UserInterface.fxml")));
            stage.setScene(scene);  
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(ProfilController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void Forum(ActionEvent event) {
         try {
            Node node = (Node) event.getSource();
            Stage stage = (Stage) node.getScene().getWindow();
            stage.close();

            Scene scene = new Scene(FXMLLoader.load(getClass().getResource("/esprit/recrutini/view/BackForum.fxml")));
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
            
            Scene scene = new Scene(FXMLLoader.load(getClass().getResource("/esprit/recrutini/view/Annonce.fxml")));
            stage.setScene(scene);  
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(ProfilController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void Projects(ActionEvent event) {
         try {
            Node node = (Node) event.getSource();
            Stage stage = (Stage) node.getScene().getWindow();
            stage.close();
            
            Scene scene = new Scene(FXMLLoader.load(getClass().getResource("/esprit/recrutini/view/Projets.fxml")));
            stage.setScene(scene);  
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(ProfilController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void Jobs(ActionEvent event) {
          try {
            Node node = (Node) event.getSource();
            Stage stage = (Stage) node.getScene().getWindow();
            stage.close();
            
            Scene scene = new Scene(FXMLLoader.load(getClass().getResource("/esprit/recrutini/view/JobsBack.fxml")));
            stage.setScene(scene);  
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(ProfilController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void Candidature(ActionEvent event) {
    }
    
}
