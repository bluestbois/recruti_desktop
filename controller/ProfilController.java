/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package esprit.recrutini.controller;

import esprit.recrutini.entities.Fields;
import esprit.recrutini.entities.Recruiter;
import esprit.recrutini.services.ServiceRecruiter;
import esprit.recrutini.services.Servicebcrypt;
import esprit.recrutini.services.Session;
import Utils.staticvar;
import com.github.sarxos.webcam.Webcam;
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

/**
 * FXML Controller class
 *
 * @author asus
 */
public class ProfilController implements Initializable {

    List<String> type;
    String imgG = "";
    @FXML
    private Label logout;
    @FXML
    private TextField tel;
    @FXML
    private TextField adress;
    @FXML
    private TextField nom;
    @FXML
    private Label labtel;
    @FXML
    private ComboBox<Fields> field;
    @FXML
    private TextArea description;
    @FXML
    private ImageView imageview;
    @FXML
    private Button btnupp;
    @FXML
    private Button cam;
    @FXML
    private ImageView oldimage;
    @FXML
    private Label username;
    @FXML
    private Label email;
    @FXML
    private TextField password;
    @FXML
    private Button btnin;
    @FXML
    private Label ads;
    private String mail = "";
    Session S = Session.getInstance();
    @FXML
    private Button btnupp1;
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

        type = new ArrayList<>();
        type.add("*.jpg");
        type.add("*.png");
      
        field.setItems(FXCollections.observableArrayList(Fields.values()));
        mail = S.getEmail();
              ServiceRecruiter R = new ServiceRecruiter();
        Recruiter Re;
        try {
            Re = R.getRecruiter(mail);
            
               imgG=Re.getImage();
        } catch (SQLException ex) {
            Logger.getLogger(ProfilController.class.getName()).log(Level.SEVERE, null, ex);
        }
     
        try {
            Informations();

            // TODO
        } catch (SQLException ex) {
            Logger.getLogger(ProfilController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void Informations() throws SQLException {
        ServiceRecruiter R = new ServiceRecruiter();
        Recruiter Re = R.getRecruiter(mail);
        String OldImage = Re.getImage();
        email.setText(Re.getEmail());
        username.setText(Re.getUsername());
        nom.setText(Re.getName());
        tel.setText(Re.getPhone());
        description.setText(Re.getDescription());
        adress.setText(Re.getAdress());
        field.getSelectionModel().selectFirst();
        oldimage.setImage(new Image(getClass().getResource("/esprit/recrutini/img/" +OldImage).toExternalForm()));

    }

    @FXML
    private void webcam(ActionEvent event) {
        Webcam webcam = Webcam.getDefault();
        webcam.open();
        Random rand = new Random();
        int alea = rand.nextInt(200000 - 10 + 1) + 200000;
        // get image
        BufferedImage image = webcam.getImage();

        try {
            // save image to PNG file

            File f = new File("A:\\esprit\\sinda\\2emeS\\PI\\java\\recrutini\\src\\esprit\\recrutini\\img" + alea + ".png");

            imgG = alea + ".png";
 
            ImageIO.write(image, "PNG", f);

            webcam.close();
            imageview.setImage(new javafx.scene.image.Image(f.toURI().toString()));

        } catch (IOException ex) {
            Logger.getLogger(SignupController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void send(ActionEvent event) throws SQLException {
        ServiceRecruiter R = new ServiceRecruiter();
        String crypted = Servicebcrypt.hashpw(password.getText(), staticvar.SALT);
        R.updatepassword(this.email.getText(), crypted);
        JOptionPane.showMessageDialog(null, "password has been changed successfully ");

    }

    @FXML
    private void modifier(ActionEvent event) throws SQLException {

        ServiceRecruiter R = new ServiceRecruiter();
    Recruiter Re = R.getRecruiter(mail);
        String masque = "^[a-zA-Z]+[a-zA-Z0-9\\._-]*[a-zA-Z0-9]@[a-zA-Z]+"
                + "[a-zA-Z0-9\\._-]*[a-zA-Z0-9]+\\.[a-zA-Z]{2,4}$";
        Pattern pattern = Pattern.compile(masque);

        String tell = tel.getText();
        String nomm = nom.getText();

        String descr = description.getText();
        String address = adress.getText();
        String mobile = tel.getText();
        String fieldd = field.getValue().toString();

        if (tell.isEmpty() || nomm.isEmpty() || address.isEmpty() || fieldd.isEmpty()) {
            JOptionPane.showMessageDialog(null, "please fill all the textfields and choose a picture");
        } else {

      

                if (tell.matches("^[0-9]+$") && tell.length() == 8) {

  
                        Recruiter re = new Recruiter(Re.getId(),fieldd, nomm, "", "", "", address, descr, mobile, imgG,  0);
                        R.update(re);
                          try {
            Informations();

            // TODO
        } catch (SQLException ex) {
            Logger.getLogger(ProfilController.class.getName()).log(Level.SEVERE, null, ex);
        }
                              JOptionPane.showMessageDialog(null, "Information updated ");

                 

                } else {
                    labtel.setText("the formatof " + tell + " is not correct");
                }

         
        }
    }

    @FXML
    private void logout(MouseEvent event) throws IOException {
        S.cleanSession();
                Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        stage.close();

        Scene scene = new Scene(FXMLLoader.load(getClass().getResource("/esprit/recrutini/view/Login.fxml")));
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void jobs(MouseEvent event) throws IOException {
                Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        Scene scene = new Scene(FXMLLoader.load(getClass().getResource("/esprit/recrutini/view/JobsRe.fxml")));
        stage.setScene(scene);
        stage.show();  
    }





    @FXML
    private void deleteAccount(ActionEvent event) throws SQLException, IOException {
        
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                            alert.setTitle("suppression");
                            alert.setHeaderText("Are you sure you want to delete your account , all your ads will be deleted");
                            Optional<ButtonType> option = alert.showAndWait();

                            if (option.get() == ButtonType.OK) {
                                
                                  ServiceRecruiter R = new ServiceRecruiter();
                                  
        Recruiter Re = R.getRecruiter(mail);
                                  if(R.delete(Re.getId()))
                                  {
                                        JOptionPane.showMessageDialog(null, "we are sorry that you left ");
                                         S.cleanSession();
                Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        stage.close();

        Scene scene = new Scene(FXMLLoader.load(getClass().getResource("/esprit/recrutini/view/Login.fxml")));
        stage.setScene(scene);
        stage.show();
                                        
                                  }
                            }
    }


    @FXML
    private void deleteAccount(MouseEvent event) {
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
            Logger.getLogger(ProfilController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void Freelance(ActionEvent event) {
        try {
            Node node = (Node) event.getSource();
            Stage stage = (Stage) node.getScene().getWindow();
            stage.close();
            
            Scene scene = new Scene(FXMLLoader.load(getClass().getResource("/esprit/recrutini/view/AnnonceFront.fxml")));
            stage.setScene(scene);  
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(ProfilController.class.getName()).log(Level.SEVERE, null, ex);
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
