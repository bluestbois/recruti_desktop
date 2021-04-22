/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package recrutini.GUI;


import Entite.Annonce;
import Entite.Projet;
import Service.ServiceAnnonce;
import Service.ServiceProjet;
import Service.SessionAnnonce;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Callback;
import javax.swing.JOptionPane;

/**
 * FXML Controller class
 *
 * @author ines
 */
public class UpdateAnnonceController implements Initializable {

    @FXML
    private Pane pnlOverview;
    @FXML
    private Label nombre;
    @FXML
    private Button modifier;
    @FXML
    private TextField titre;
    @FXML
    private TextArea description;
    @FXML
    private TextField salaire;
    @FXML
    private ComboBox<Projet> id_projet;
    @FXML
    private Label message;
    @FXML
    private DatePicker date;
    @FXML
    private Label label;
     private int id_annonce = 0;
    SessionAnnonce SP = SessionAnnonce.getInstance();
    @FXML
    private Label projet;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
           id_annonce = SP.getId_Projet();
         Callback<ListView<Projet>, ListCell<Projet>> cellFactory = new Callback<ListView<Projet>, ListCell<Projet>>() {

            @Override
            public ListCell<Projet> call(ListView<Projet> l) {
                return new ListCell<Projet>() {

                    @Override
                    protected void updateItem(Projet item, boolean empty) {
                        super.updateItem(item, empty);
                        if (item == null || empty) {
                            setGraphic(null);
                        } else {
                            setText(item.getTitle());
                        }
                    }
                };
            }
        };

        id_projet.setButtonCell(cellFactory.call(null));
        id_projet.setCellFactory(cellFactory);
        //remplissage du combobox
        ServiceProjet sp = new ServiceProjet();
        List<Projet> arr = new ArrayList<>();
        try {
            arr = sp.DisplayAll();
        } catch (SQLException ex) {
            Logger.getLogger(AjouterAnnonceController.class.getName()).log(Level.SEVERE, null, ex);
        }
        for (Projet p : arr) {
            String titre = p.getTitle();
     
            id_projet.getItems().add(p);
        }
        
   try {
            displayAll();

        } catch (SQLException ex) {
            Logger.getLogger(UpdateProjetController.class.getName()).log(Level.SEVERE, null, ex);
        }

        // controll de saisie  l formulaire lezem tkoun m3ebya 

           id_projet.valueProperty().addListener((ov, oldValue, newValue) -> {
            if (newValue == null || description.getText().length() == 0 || titre.getText().length() == 0 || salaire.getText().length()==0) {
                modifier.setDisable(true);
            } else {
                modifier.setDisable(false);
            }

        });
        titre.textProperty().addListener((ov, oldValue, newValue) -> {
            if (id_projet.getValue() == null || description.getText().length() == 0 || titre.getText().length() == 0 || salaire.getText().length()==0 ) {
                modifier.setDisable(true);
            } else {
                modifier.setDisable(false);
            }

        });
          description.textProperty().addListener((ov, oldValue, newValue) -> {
            if (id_projet.getValue() == null || description.getText().length() == 0 || titre.getText().length() == 0 || salaire.getText().length()==0 ) {
                modifier.setDisable(true);
            } else {
                modifier.setDisable(false);
            }

        }); 
               salaire.textProperty().addListener((ov, oldValue, newValue) -> {
            if (id_projet.getValue() == null || description.getText().length() == 0 || titre.getText().length() == 0 || salaire.getText().length()==0 ) {
                modifier.setDisable(true);
            } else {
                modifier.setDisable(false);
            }

        }); 
    }    

     public void displayAll() throws SQLException {

       ServiceProjet sp = new ServiceProjet();
    ServiceAnnonce sA = new ServiceAnnonce();
        Annonce A = sA.details(id_annonce);

        Projet P = sp.details(A.getProject_id());
 
        label.setText(A.getTitle());
        titre.setText(A.getTitle());
           salaire.setText(A.getSalary());
        description.setText(A.getDescription());
        projet.setText(P.getTitle());
          LocalDate DateA= A.getDate().toLocalDate();
        date.setValue(DateA);
        
        
     

    } 

    @FXML
    private void modifier(ActionEvent event) throws SQLException {
            ServiceAnnonce sA = new ServiceAnnonce();

       
            java.sql.Date date_annonce = java.sql.Date.valueOf(date.getValue().atTime(11, 10).toLocalDate());
    
           sA.modifier(new Annonce(id_annonce,id_projet.getValue().getId(), titre.getText(), description.getText(),salaire.getText(),date_annonce,"" ));

        JOptionPane.showMessageDialog(null, "modification  effectué");
    
    }


    @FXML
    private void Projets(ActionEvent event) throws IOException {
           SP.cleanSession();
                     Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        stage.close();

        Scene scene = new Scene(FXMLLoader.load(getClass().getResource("Projets.fxml")));
        stage.setScene(scene);
        stage.show();
    }

  
    
       @FXML
    private void Anonnces(ActionEvent event) throws IOException {
           SP.cleanSession();
                Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        stage.close();

        Scene scene = new Scene(FXMLLoader.load(getClass().getResource("Annonce.fxml")));
        stage.setScene(scene);
        stage.show();
    }

    
}
