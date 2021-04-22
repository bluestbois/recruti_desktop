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
import java.net.URL;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

/**
 * FXML Controller class
 *
 * @author ines
 */
public class AnnonceOneController implements Initializable {

    @FXML
    private Label Titre;
    @FXML
    private Label salaire;
    @FXML
    private Text description;
    @FXML
    private Label date;
    @FXML
    private Label projet;
    @FXML
    private Text descriptionProjet;
    @FXML
    private ImageView image;
    @FXML
    private Label salaire1;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
        public void InitAnnonce(Annonce ann) throws SQLException {

       ServiceProjet sp = new ServiceProjet();
       ServiceAnnonce sA = new ServiceAnnonce();
        Annonce A = sA.details(ann.getId());
        Projet P = sp.details(A.getProject_id());
      
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String Date = dateFormat.format(A.getDate());
 
        date.setText(Date);
    
        Titre.setText(A.getTitle());
        description.setText(A.getDescription());
        salaire.setText(A.getSalary());
        projet.setText(P.getTitle());
        descriptionProjet.setText(P.getDescription());
           if(P.getImage()!=null)
        {
        image.setImage(new Image(getClass().getResource("images/" + P.getImage()).toExternalForm()));
        }
    }
    
}