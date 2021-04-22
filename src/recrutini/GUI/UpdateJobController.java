/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package recrutini.GUI;

import Entite.Job;
import Entite.Recruiter;
import Service.ServiceJob;
import Service.ServiceRecruiter;
import Service.Session;
import Service.SessionJob;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
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
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javax.swing.JOptionPane;

/**
 * FXML Controller class
 *
 * @author asus
 */
public class UpdateJobController implements Initializable {

    @FXML
    private TextField title;
    @FXML
    private TextArea description;
    @FXML
    private DatePicker date;
    @FXML
    private Label btnfo;
    @FXML
    private Button btnin;
    private int id_job = 0;
    SessionJob SJ = SessionJob.getInstance();
    private String mail = "";
    Session S = Session.getInstance();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        id_job = SJ.getId();
          try {
            displayAll();

        } catch (SQLException ex) {
            Logger.getLogger(UpdateJobController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void displayAll() throws SQLException {

        ServiceJob sp = new ServiceJob();

        Job J = sp.details(id_job);

        title.setText(J.getTitle());

        description.setText(J.getDescription());

        LocalDate DateA = J.getDate().toLocalDate();
        date.setValue(DateA);

    }

    @FXML
    private void jobs(MouseEvent event) throws  IOException  {
                       Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        stage.close();

        Scene scene = new Scene(FXMLLoader.load(getClass().getResource("JobsRe.fxml")));
        stage.setScene(scene);
        stage.show(); 
    }

    @FXML
    private void update(ActionEvent event) throws SQLException, IOException {

   
        ServiceJob sA = new ServiceJob();
  

        java.sql.Date D = java.sql.Date.valueOf(date.getValue().atTime(11, 10).toLocalDate());


        sA.modifier(new Job(id_job, title.getText(), description.getText(), D,0));

        JOptionPane.showMessageDialog(null, "modification  effectué");
        
                           Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        stage.close();

        Scene scene = new Scene(FXMLLoader.load(getClass().getResource("JobsRe.fxml")));
        stage.setScene(scene);
        stage.show(); 
    }

}
