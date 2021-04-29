/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package esprit.recrutini.controller;

import esprit.recrutini.entities.Recruiter;
import static esprit.recrutini.services.CandidateSession.currentCandidate;
import esprit.recrutini.services.ServiceRecruiter;
import esprit.recrutini.services.Session;
import java.net.URL;
import java.sql.SQLException;
import static java.sql.Types.NULL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.Initializable;

/**
 * FXML Controller class
 *
 * @author ASUS
 */
public class TestController implements Initializable {

    Session S = Session.getInstance();
    private String mail = "";

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            // TODO
            if (currentCandidate.getId() != NULL) {
                System.out.println("idCandidate" + currentCandidate.getId());
            } else {
                mail = S.getEmail();
                ServiceRecruiter R = new ServiceRecruiter();
                Recruiter Re;

                Re = R.getRecruiter(mail);
                int id = Re.getId();
                System.out.println("idRecruteur" + id);
            }
        } catch (SQLException ex) {
            Logger.getLogger(TestController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
