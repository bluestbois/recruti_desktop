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
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
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
public class AddJobController implements Initializable {

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
    private String mail = "";
    Session S = Session.getInstance();
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
   
    }    

    @FXML
    private void jobs(MouseEvent event) throws IOException {
                  Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        stage.close();

        Scene scene = new Scene(FXMLLoader.load(getClass().getResource("JobsRe.fxml")));
        stage.setScene(scene);
        stage.show(); 
    }

    @FXML
    private void add(ActionEvent event) throws SQLException {
             mail = S.getEmail();
              ServiceRecruiter R = new ServiceRecruiter();
        Recruiter Re;
           Re = R.getRecruiter(mail);
          ServiceJob sA = new ServiceJob();
             Re = R.getRecruiter(mail);
       
            java.sql.Date D = java.sql.Date.valueOf(date.getValue().atTime(11, 10).toLocalDate());
    
           sA.ajouter(new Job(0, title.getText(), description.getText(),D,Re.getId()));

        JOptionPane.showMessageDialog(null, "Ajout effectué");
        title.clear();
   
        description.clear();
        
    }
    
}
