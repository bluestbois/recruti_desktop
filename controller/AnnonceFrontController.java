/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package esprit.recrutini.controller;

import esprit.recrutini.entities.Annonce;
import static esprit.recrutini.services.CandidateSession.currentCandidate;
import esprit.recrutini.services.ServiceAnnonce;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import static java.sql.Types.NULL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author ines
 */
public class AnnonceFrontController implements Initializable {

    @FXML
    private Label text;
    @FXML
    private VBox liste_annonce;
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
        try {
            DisplayOne();
        } catch (SQLException ex) {
            Logger.getLogger(AnnonceFrontController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(AnnonceFrontController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void DisplayOne() throws SQLException, IOException {
        ServiceAnnonce ServA = new ServiceAnnonce();
        ArrayList<Annonce> TabT = ServA.DisplayNEW();

        for (Annonce c : TabT) {

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/esprit/recrutini/view/AnnonceOne.fxml"));
            Parent n = (Parent) loader.load();
            esprit.recrutini.controller.AnnonceOneController form = loader.getController();
            form.InitAnnonce(c);
            liste_annonce.getChildren().add(n);
        }
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
