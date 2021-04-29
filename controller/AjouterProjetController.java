/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package esprit.recrutini.controller;

import esprit.recrutini.entities.Projet;
import esprit.recrutini.services.ServiceProjet;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javax.swing.JOptionPane;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import esprit.recrutini.entities.Recruiter;
import esprit.recrutini.services.ServiceRecruiter;
import esprit.recrutini.services.Servicebcrypt;
import esprit.recrutini.services.Session;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * FXML Controller class
 *
 * @author ines
 */
public class AjouterProjetController implements Initializable {
    List<String> type;
    String imgG = "";
    @FXML
    private Pane pnlOverview;
    @FXML
    private Label nombre;
    @FXML
    private ImageView imageview;
    @FXML
    private Button ajout;
    @FXML
    private TextField titre;
    @FXML
    private TextArea description;
    @FXML
    private Button imagechose;
    @FXML
    private DatePicker date;
            Session S = Session.getInstance();
    @FXML
    private Label userlabel;
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
        type = new ArrayList<>();
        type.add("*.jpg");
        type.add("*.png");

        
         date.setValue(LocalDate.now());
        date.setDayCellFactory(picker -> new DateCell() {
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                LocalDate today = LocalDate.now();

                setDisable(empty || date.compareTo(today) < 0);
            }
        });
        // controll de saisie  l formulaire lezem tkoun m3ebya 
        titre.textProperty().addListener((ov, oldValue, newValue) -> {
            if ( description.getText().length() == 0 || titre.getText().length() == 0 ) {
                ajout.setDisable(true);
            } else {
                ajout.setDisable(false);
            }

        });
          description.textProperty().addListener((ov, oldValue, newValue) -> {
            if (description.getText().length() == 0 || titre.getText().length() == 0 ) {
                ajout.setDisable(true);
            } else {
                ajout.setDisable(false);
            }

        });
    }    

    @FXML
    private void ajouter_projet(ActionEvent event) throws SQLException {
        ServiceProjet sP = new ServiceProjet();
       
       
            java.sql.Date date_Projet = java.sql.Date.valueOf(date.getValue().atTime(11, 10).toLocalDate());
        if (imgG.length() == 0) {
            sP.ajouter(new Projet(0,10, titre.getText(), description.getText(),date_Projet,"" ));
        }
           sP.ajouter(new Projet(0,10, titre.getText(), description.getText(),date_Projet,imgG ));

        JOptionPane.showMessageDialog(null, "Ajout effectué");
        titre.clear();
        description.clear();
        imageview.setImage(null);

    }

    @FXML
  private void import_image(ActionEvent event) throws IOException {
        FileChooser f = new FileChooser();

        f.getExtensionFilters().add(new FileChooser.ExtensionFilter("jpeg,png files", type));
        File fc = f.showOpenDialog(null);
        if (f != null) {
            
            imgG = fc.getName().toString();

            Path temp = Files.copy(fc.toPath(), Paths.get("A:\\esprit\\sinda\\2emeS\\PI\\java\\recrutini\\src\\esprit\\recrutini\\img\\" + imgG), StandardCopyOption.REPLACE_EXISTING);

            Image i = new Image(fc.getAbsoluteFile().toURI().toString());
            imageview.setImage(i);
        }
        fc.exists();
    }

     @FXML
    private void Projets(ActionEvent event) throws IOException {
                Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        stage.close();

        Scene scene = new Scene(FXMLLoader.load(getClass().getResource("/esprit/recrutini/view/Projets.fxml")));
        stage.setScene(scene);
        stage.show();
    }
    
    private void Anonnces(ActionEvent event) throws IOException {
                Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        stage.close();

        Scene scene = new Scene(FXMLLoader.load(getClass().getResource("/esprit/recrutini/view/Annonce.fxml")));
        stage.setScene(scene);
        stage.show();
    }

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
         try {
            Node node = (Node) event.getSource();
            Stage stage = (Stage) node.getScene().getWindow();
            stage.close();
            
            Scene scene = new Scene(FXMLLoader.load(getClass().getResource("/esprit/recrutini/view/Candidature.fxml")));
            stage.setScene(scene);  
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(ProfilController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
