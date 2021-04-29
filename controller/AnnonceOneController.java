/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package esprit.recrutini.controller;

import esprit.recrutini.entities.Annonce;
import esprit.recrutini.entities.Projet;
import static esprit.recrutini.services.CandidateSession.currentCandidate;
import esprit.recrutini.services.ServiceAnnonce;
import esprit.recrutini.services.ServiceProjet;
import java.net.URL;
import java.sql.SQLException;
import static java.sql.Types.NULL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
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
    Annonce An;

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
            An=A;
        Titre.setText(A.getTitle());
        description.setText(A.getDescription());
        salaire.setText(A.getSalary());
        projet.setText(P.getTitle());
        descriptionProjet.setText(P.getDescription());
        if (P.getImage() != null) {
            image.setImage(new Image(getClass().getResource("/esprit/recrutini/img/" + P.getImage()).toExternalForm()));
        }
    }

    @FXML
    private void Apply(MouseEvent event) {
        try {
            ServiceProjet sp = new ServiceProjet();
            ServiceAnnonce sA = new ServiceAnnonce();
            Annonce A = sA.details(An.getId());

            long millis = System.currentTimeMillis();
            java.sql.Date date = new java.sql.Date(millis);
            int id = currentCandidate.getId();
            int id2 = A.getId();

            if (sA.checkapply(id, id2) == 0) {
                try {
                    sA.apply(currentCandidate.getId(), NULL , A.getId(), date, 0);
                } catch (SQLException ex) {
                    Logger.getLogger(JobsController.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error DIALOG");
                alert.setHeaderText(null);
                alert.setContentText("You already applied to this freelance offer");
                alert.showAndWait();
            }

        } catch (SQLException ex) {
            Logger.getLogger(JobsController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
