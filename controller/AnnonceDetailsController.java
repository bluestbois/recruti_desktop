/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package esprit.recrutini.controller;


import esprit.recrutini.entities.Annonce;
import esprit.recrutini.entities.Projet;
import esprit.recrutini.services.ServiceAnnonce;
import esprit.recrutini.services.ServiceProjet;
import esprit.recrutini.services.SessionAnnonce;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author ines
 */
public class AnnonceDetailsController implements Initializable {

    @FXML
    private Pane pnlOverview;
  
    @FXML
    private Label name;
    @FXML
    private Label date;
    @FXML
    private Text description;
    @FXML
    private Label salaire;
    @FXML
    private Label projet;
 private int id_annonce = 0;
    SessionAnnonce SP = SessionAnnonce.getInstance();
    @FXML
    private Label nombre;
    @FXML
    private Text descriptionProjet;
    @FXML
    private Label etat;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        id_annonce = SP.getId_Projet();
         try {
            displayAll();

            // TODO
        } catch (SQLException ex) {
            Logger.getLogger(ProjetDetailsController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }    

   public void displayAll() throws SQLException {

     ServiceProjet sp = new ServiceProjet();
    ServiceAnnonce sA = new ServiceAnnonce();
        Annonce A = sA.details(id_annonce);
        Projet P = sp.details(A.getProject_id());
      
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String Date = dateFormat.format(A.getDate());
 
        date.setText(Date);
    
        name.setText(A.getTitle());
        description.setText(A.getDescription());
        salaire.setText(A.getSalary());
        projet.setText(P.getTitle());
        descriptionProjet.setText(P.getDescription());
        etat.setText(A.getEtat());
  

    }

      @FXML
    private void Projets(ActionEvent event) throws IOException {
           SP.cleanSession();
                Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        stage.close();

        Scene scene = new Scene(FXMLLoader.load(getClass().getResource("/esprit/recrutini/view/Projets.fxml")));
        stage.setScene(scene);
        stage.show();
    }
    
       @FXML
    private void Anonnces(ActionEvent event) throws IOException {
        SP.cleanSession();
                Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        stage.close();

        Scene scene = new Scene(FXMLLoader.load(getClass().getResource("/esprit/recrutini/view/Annonce.fxml")));
        stage.setScene(scene);
        stage.show();
    }
    
}
