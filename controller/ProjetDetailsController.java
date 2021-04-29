/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package esprit.recrutini.controller;


import esprit.recrutini.entities.Projet;
import esprit.recrutini.services.ServiceProjet;
import esprit.recrutini.services.SessionProjet;
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
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author ines
 */
public class ProjetDetailsController implements Initializable {

    @FXML
    private Pane pnlOverview;
    @FXML
    private Label nombre;
    @FXML
    private Label name;
    @FXML
    private ImageView image;
    @FXML
    private Label date;

    private int id_projet = 0;
    SessionProjet SP = SessionProjet.getInstance();
    @FXML
    private Text description;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
     id_projet = SP.getId_Projet();
      try {
            displayAll();

            // TODO
        } catch (SQLException ex) {
            Logger.getLogger(ProjetDetailsController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }    

    public void displayAll() throws SQLException {

     ServiceProjet sp = new ServiceProjet();

        Projet P = sp.details(id_projet);
  
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String Date = dateFormat.format(P.getDate());
 
        date.setText(Date);
        System.out.println(P.getImage());
       if(P.getImage()!=null)
        {
        image.setImage(new Image(getClass().getResource("/esprit/recrutini/img/" + P.getImage()).toExternalForm()));
        }
        name.setText(P.getTitle());
        description.setText(P.getDescription());
       
      

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
