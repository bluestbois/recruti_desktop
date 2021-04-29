/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package esprit.recrutini.controller;


import esprit.recrutini.entities.Projet;
import esprit.recrutini.services.ServiceProjet;
import esprit.recrutini.services.SessionProjet;
import java.io.File;
import java.io.IOException;
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
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
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

/**
 * FXML Controller class
 *
 * @author ines
 */
public class UpdateProjetController implements Initializable {
   List<String> type;
    String imgG = "";
    @FXML
    private Pane pnlOverview;
    @FXML
    private Label nombre;
    @FXML
    private ImageView imageview;
    @FXML
    private TextField titre;
    @FXML
    private TextArea description;
    @FXML
    private Button imagechose;
    @FXML
    private Label message;
    @FXML
    private DatePicker date;
    @FXML
    private Label label;
    private int id_projet = 0;
    SessionProjet SP = SessionProjet.getInstance();
    @FXML
    private Button modifier;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       
        id_projet = SP.getId_Projet();
           
  type = new ArrayList<>();
        type.add("*.jpg");
        type.add("*.png");
        try {
            displayAll();

        } catch (SQLException ex) {
            Logger.getLogger(UpdateProjetController.class.getName()).log(Level.SEVERE, null, ex);
        }

        // controll de saisie  l formulaire lezem tkoun m3ebya 

        description.textProperty().addListener((ov, oldValue, newValue) -> {
            if ( description.getText().length() == 0 || titre.getText().length() == 0 ) {
                modifier.setDisable(true);
            } else {
                modifier.setDisable(false);
            }

        });
        titre.textProperty().addListener((ov, oldValue, newValue) -> {
            if ( description.getText().length() == 0 || titre.getText().length() == 0 ) {
                modifier.setDisable(true);
            } else {
                modifier.setDisable(false);
            }

        });
    }    

     public void displayAll() throws SQLException {

        ServiceProjet sp = new ServiceProjet();

        Projet P = sp.details(id_projet);
 
        label.setText(P.getTitle());
        titre.setText(P.getTitle());
        description.setText(P.getDescription());
          LocalDate DateP= P.getDate().toLocalDate();
        date.setValue(DateP);
         if(P.getImage()!=null)
        {
         
        imageview.setImage(new Image(getClass().getResource("/esprit/recrutini/img/" + P.getImage()).toExternalForm()));
        }

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
    private void modifier(ActionEvent event) throws SQLException {
        ServiceProjet sp = new ServiceProjet();
        Projet P = sp.details(id_projet);
        String img = "";
        int id_format;
        if (imgG.length() == 0) {
            img = P.getImage();
        } else {
            img = imgG;
        }

  java.sql.Date date_Projet = java.sql.Date.valueOf(date.getValue().atTime(11, 10).toLocalDate());

        sp.modifier(new Projet(id_projet,0, titre.getText(), description.getText(),date_Projet,img));

        JOptionPane.showMessageDialog(null, "modification effectué");

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
