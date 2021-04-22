/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package recrutini.GUI;

import Entite.Fields;
import Entite.Recruiter;
import Service.ServiceRecruiter;
import Service.Servicebcrypt;
import Utils.staticvar;
import com.github.sarxos.webcam.Webcam;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.ProtocolException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
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
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.Window;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import rest.file.uploader.tn.FileUploader;

/**
 * FXML Controller class
 *
 * @author asus
 */
public class SignupController implements Initializable {

    List<String> type;
    String imgG = "";
    @FXML
    private TextField mail;
    @FXML
    private Button btnupp;
    @FXML
    private PasswordField pwd1;
    @FXML
    private TextField tel;
    @FXML
    private TextField adress;
    @FXML
    private TextField username;
    @FXML
    private TextField nom;
    @FXML
    private TextField prenom;
    @FXML
    private Label labtel;
    @FXML
    private Label labmail;

    
    @FXML
    private Label labpwd;
    @FXML
    private Label labusername;
    @FXML
    private Label labname1;
    @FXML
    private Label labname;
    @FXML
    private Button cam;
    @FXML
    private ComboBox<Fields> field;
    @FXML
    private TextArea description;
    @FXML
    private Button back;
    @FXML
    private ImageView imageview;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        type = new ArrayList<>();
        type.add("*.jpg");
        type.add("*.png");
        field.setItems(FXCollections.observableArrayList(Fields.values()));
    }
  @FXML
    private void ajouter(ActionEvent event) throws SQLException {
        ServiceRecruiter R = new ServiceRecruiter();
        String mai = mail.getText();
        String masque = "^[a-zA-Z]+[a-zA-Z0-9\\._-]*[a-zA-Z0-9]@[a-zA-Z]+"
                + "[a-zA-Z0-9\\._-]*[a-zA-Z0-9]+\\.[a-zA-Z]{2,4}$";
        Pattern pattern = Pattern.compile(masque);
        Matcher controler = pattern.matcher(mai);
        String tell = tel.getText();
        String usernam = username.getText();
        String nomm = nom.getText();
        String prenomm = prenom.getText();
        String pwd = pwd1.getText();
        String descr = description.getText();
        String address = adress.getText();
        String mobile = tel.getText();
        String fieldd = field.getValue().toString();
   
        try {
            if (tell.isEmpty() || usernam.isEmpty() || nomm.isEmpty() || prenomm.isEmpty() || pwd.isEmpty() || address.isEmpty() || fieldd.isEmpty()) {
                JOptionPane.showMessageDialog(null, "please fill all the textfields and choose a picture");
            } else {
                if (controler.matches()) {
                    if (nomm.matches("^[a-zA-Z]+$")) {
                        if (prenomm.matches("^[a-zA-Z]+$")) {
                            if (pwd.length() >= 8) {

                                if (usernam.matches("^[a-zA-Z]+[a-zA-Z0-9]+$") && usernam.length() > 3) {
                                    if (tell.matches("^[0-9]+$") && tell.length() == 8) {
                                        if (R.getUsername(usernam) == 0) {
                                            if (R.getmails(mai) == 0) {
                                                if (R.getTel(tell) == 0) {

                                                    String crypted = Servicebcrypt.hashpw(pwd, staticvar.SALT);

                                                    Recruiter re = new Recruiter(0,fieldd, prenomm + " " + nomm, usernam, mai, crypted, address, descr, mobile, imgG,  0);
                                                    R.ajouter(re);
                                                         Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        stage.close();

        Scene scene = new Scene(FXMLLoader.load(getClass().getResource("Login.fxml")));
        stage.setScene(scene);
        stage.show();

                                                } else {
                                                    JOptionPane.showMessageDialog(null, "Number phone: " + tell + " exist !");
                                                }
                                            } else {
                                                JOptionPane.showMessageDialog(null, "E-mail: " + mai + " exist !");
                                            }
                                        } else {
                                            JOptionPane.showMessageDialog(null, "Username: " + usernam + " exist !");
                                        }
                                    } else {
                                        labtel.setText("the format of " + tell + " is not correct");
                                    }

                                } else {
                                    labusername.setText("the format of " + usernam + " is not correct");
                                }

                            } else {
                                labpwd.setText("your password must exceed 8 characters ");
                            }
                        } else {
                            labname1.setText("the format of " + prenomm + " is not correct");
                        }
                    } else {
                        labname.setText("the format of " + nomm + " is not correct ");
                    }
                } else {
                    labmail.setText("the format of " + mai + " is not correct");
                }
            }

        } catch (ProtocolException ex) {
            Logger.getLogger(SignupController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(SignupController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
      @FXML
    private void back(MouseEvent event) throws IOException {
           Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        stage.close();

        Scene scene = new Scene(FXMLLoader.load(getClass().getResource("Login.fxml")));
        stage.setScene(scene);
        stage.show();
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

            File f = new File("C:\\Users\\asus\\Desktop\\wael\\Recrutini\\src\\recrutini\\GUI\\images\\" + alea + ".png");
         
            imgG =alea+".png";
            
            ImageIO.write(image, "PNG", f);

            webcam.close();
                imageview.setImage(new javafx.scene.image.Image(f.toURI().toString()));

        } catch (IOException ex) {
            Logger.getLogger(SignupController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

  


 

 

}
