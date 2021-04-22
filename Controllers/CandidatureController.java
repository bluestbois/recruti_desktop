/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import com.sun.prism.impl.Disposer;
import Entity.Candidature;
import Entity.Job;
import Entity.Candidate;
import Entity.Freelance;
import Services.ServiceCandidature;
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
import Services.CandidateSession;
import static Services.CandidateSession.currentCandidate;


/**
 * FXML Controller class
 *
 * @author yessine darmoul
 */

public class CandidatureController implements Initializable {

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
    @FXML
    private Button affecter;
     @FXML
    private JFXComboBox<String> combocandidate;
    @FXML
    private JFXComboBox<String> combojob;
    @FXML
    private JFXTextField updateidcandidate;
    @FXML
    private JFXTextField updateidjob;
    @FXML
    private JFXTextField updateidcandidature;
    @FXML
    private JFXComboBox<String> combofreelance;
    @FXML
    private JFXTextField updateidfreelance;
    @FXML
    private JFXTextField updatescore;
    @FXML
    private Button update;

    ServiceCandidature serv=new ServiceCandidature();
    
        ObservableList<Candidature> oblist = FXCollections.observableArrayList();
   
   

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        combocandidate.setPromptText("candidate");
        combojob.setPromptText("job");
        combofreelance.setPromptText("freelance");
       List<Integer> list=new ArrayList<>();
        try {
            for( Candidature d : serv.readAll())
            {
                 combocandidate.getItems().addAll(String.valueOf(d.getCandidate_id()));
            }
              for( Candidature d : serv.readAll())
            {
                 combojob.getItems().addAll(String.valueOf(d.getJob_id()));
            }
              for( Candidature d : serv.readAll())
            {
                 combofreelance.getItems().addAll(String.valueOf(d.getFreelance_id()));
            }
        } catch (SQLException ex) {
            System.out.print("error");
        }
        
        
       
        
        
        try {
            // TODO
            
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
            
            
        } catch (SQLException ex) {
           System.out.print("error");
        }
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
    private void modifiercoursetudiant(MouseEvent event) {
         Candidature c = tableview.getSelectionModel().getSelectedItem();
        updateidcandidature.setText("" + c.getId());
        updateidcandidate.setText("" + c.getCandidate_id());
        updateidjob.setText("" + c.getJob_id());
        updateidfreelance.setText("" + c.getFreelance_id());
        updatescore.setText("" + c.getScore());
        tableview.getColumns().get(0).setVisible(false);
        tableview.getColumns().get(0).setVisible(true);
        tableview.refresh();
    }

    @FXML
    private void affectercoursetudiant(ActionEvent event) throws SQLException {
        
      
        Candidature c=new Candidature();
        c.setCandidate_id(Integer.parseInt(combocandidate.getValue()) );
        c.setJob_id(Integer.parseInt(combojob.getValue()));
        c.setFreelance_id(Integer.parseInt(combofreelance.getValue()));
        
        serv.ajouter(c);
        tableview.getColumns().get(0).setVisible(false);
        tableview.getColumns().get(0).setVisible(true);
        tableview.refresh();
        
        
    }


    @FXML
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
            tableview.refresh();
            serv.update(idcandidature,idcandidate,idjob,idfreelance,score);
            tableview.getColumns().get(0).setVisible(false);
        tableview.getColumns().get(0).setVisible(true);
        tableview.refresh();
            FXMLLoader.load(getClass().getResource("/GUI/Candidature.fxml"));

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
    private void retour(ActionEvent event) throws IOException {
          FXMLLoader.load(getClass().getResource("/GUI/UserInterface.fxml"));

    }
    
}
