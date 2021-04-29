/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package esprit.recrutini.controller;

import esprit.recrutini.tools.DataSource;
import esprit.recrutini.entities.Candidate;
import esprit.recrutini.services.Service_Candidate;
import esprit.recrutini.services.CandidateSession;
import static esprit.recrutini.services.CandidateSession.currentCandidate;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.ProtocolException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
import java.lang.Object;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javafx.scene.Cursor;
import org.mindrot.jbcrypt.BCrypt;

/**
 * FXML Controller class
 *
 * @author anest
 */
public class ProfilCandidateController implements Initializable {

    List<String> type;
    String imgG = "";
    @FXML
    private TextField tel;
    @FXML
    private TextField adress;
    @FXML
    private TextField firstname;
    @FXML
    private TextField lastname;
    @FXML
    private Label labtel;
    @FXML
    private Button btnupp;
    @FXML
    private Label username;
    @FXML
    private Label email;
    @FXML
    private TextField password;
    @FXML
    private Button btnin;
    private String mail = "";
    //Session S = Session.getInstance();
    CandidateSession S = new CandidateSession() ;//.getInstance();
    @FXML
    private Button btnupp1;
    @FXML
    private Button btnCand;
    @FXML
    private Button logout;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        try {
            System.out.println("test "+currentCandidate.getPhonenumber());
            
            
            //field.setItems(FXCollections.observableArrayList(Fields.values()));
            mail = S.getEmail();
            esprit.recrutini.services.Service_Candidate cc = new Service_Candidate();
            Candidate C;
           
            
            try {
                Informations();
                
                // TODO
            } catch (SQLException ex) {
               
                Logger.getLogger(ProfilCandidateController.class.getName()).log(Level.SEVERE, null, ex);
                
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(ProfilCandidateController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void Informations() throws SQLException {
        esprit.recrutini.services.Service_Candidate cc = new Service_Candidate();
        
        email.setText(currentCandidate.getEmail());
        username.setText(currentCandidate.getFirstname());
        firstname.setText(currentCandidate.getFirstname());
        lastname.setText(currentCandidate.getLastname());
        tel.setText(currentCandidate.getPhonenumber());
        adress.setText(currentCandidate.getAddress());
        

    }

    

    @FXML
    private void send(ActionEvent event) throws SQLException {
        esprit.recrutini.services.Service_Candidate cc = new Service_Candidate();
        String pswd = password.getText();
        pswd = BCrypt.hashpw(pswd, BCrypt.gensalt(13));
        pswd= pswd.replaceFirst("a", "y");
        
        cc.updatepassword(this.email.getText(), pswd);
        JOptionPane.showMessageDialog(null, "password has been changed successfully ");

    }

    @FXML
    private void modifier(ActionEvent event) throws SQLException {
        esprit.recrutini.services.Service_Candidate cc = new Service_Candidate();
    
        String masque = "^[a-zA-Z]+[a-zA-Z0-9\\._-]*[a-zA-Z0-9]@[a-zA-Z]+"
                + "[a-zA-Z0-9\\._-]*[a-zA-Z0-9]+\\.[a-zA-Z]{2,4}$";
        Pattern pattern = Pattern.compile(masque);

        String tell = tel.getText();
        String nomm = firstname.getText();
        String nommm = lastname.getText();

        
        String address = adress.getText();
        String phonenumber = tel.getText();
        

        if (phonenumber.isEmpty() || nomm.isEmpty() || address.isEmpty() || nomm.isEmpty()) {
            JOptionPane.showMessageDialog(null, "please fill all the textfields and choose a picture");
        } else {

      

                if (tell.matches("^[0-9]+$") && tell.length() == 8) {
                    DataSource conn;
                    conn = DataSource.getInstance();
                    PreparedStatement pre=conn.getCnx().prepareStatement("UPDATE candidate SET first_name='"+nomm+"',last_name='"+nommm+"',address='"+address+"',phone_number='"+phonenumber+"' WHERE id='"+currentCandidate.getId()+"' ;");
                    pre.executeUpdate();
                    currentCandidate.setId(1);
                    currentCandidate.setFirstname(nomm);
                    currentCandidate.setLastname(nommm);
                    currentCandidate.setPhonenumber(phonenumber);
                    currentCandidate.setAddress(address);
  
                        
                          try {
            Informations();

            // TODO
        } catch (SQLException ex) {
            Logger.getLogger(ProfilCandidateController.class.getName()).log(Level.SEVERE, null, ex);
        }
                              JOptionPane.showMessageDialog(null, "Information updated ");

                 

                } else {
                    labtel.setText("the format of " + tell + " is not correct");
                }

         
        }
    }

    private void logout(MouseEvent event) throws IOException {
        S.CandidateSessionLogOut();
                Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        stage.close();

        Scene scene = new Scene(FXMLLoader.load(getClass().getResource("/esprit/recrutini/view/LoginC.fxml")));
        stage.setScene(scene);
        stage.show();
    }

    private void jobs(MouseEvent event) throws IOException {
                Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        stage.close();

        Scene scene = new Scene(FXMLLoader.load(getClass().getResource("/esprit/recrutini/view/JobsRe.fxml")));
        stage.setScene(scene);
        stage.show();  
    }





    @FXML
    private void deleteAccount(ActionEvent event) throws SQLException, IOException {
        
        
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                            alert.setTitle("Delete Account");
                            alert.setHeaderText("Are you sure you want to delete your account ?");
                            Optional<ButtonType> option = alert.showAndWait();

                            if (option.get() == ButtonType.OK) {
                                    DataSource conn;
                                 conn = DataSource.getInstance();
//                                  
                                  PreparedStatement pr = conn.getCnx().prepareStatement("delete from  candidature where candidate_id = '" + currentCandidate.getId() + "'");
                                    pr.execute();
                                 PreparedStatement pre = conn.getCnx().prepareStatement("delete from  candidate where id = '" + currentCandidate.getId() + "'");
                                    pre.execute();
                                 
                                        JOptionPane.showMessageDialog(null, "we are sorry that you left ");
                                         S.CandidateSessionLogOut();
                Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        stage.close();

        Scene scene = new Scene(FXMLLoader.load(getClass().getResource("/esprit/recrutini/view/LoginC.fxml")));
        stage.setScene(scene);
        stage.show();
                                        
                                  }
                            
    }

    @FXML
    private void CandidatureFront(ActionEvent event) throws IOException {
        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        stage.close();

        Scene scene = new Scene(FXMLLoader.load(getClass().getResource("/esprit/recrutini/view/CandidatureFront.fxml")));
        stage.setScene(scene);
        stage.show();
       
    }

    @FXML
    private void logout(ActionEvent event) throws IOException {
        S.CandidateSessionLogOut();
         System.out.print("CD"+currentCandidate.getId());
                Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        stage.close();

        Scene scene = new Scene(FXMLLoader.load(getClass().getResource("/esprit/recrutini/view/LoginC.fxml")));
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void deleteAccount(MouseEvent event) {
    }


}
