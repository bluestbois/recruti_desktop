/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package recrutini.GUI;

import Service.ServiceRecruiter;
import Service.Servicebcrypt;
import Service.Session;
import Utils.staticvar;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javax.swing.JOptionPane;

/**
 * FXML Controller class
 *
 * @author asus
 */
public class LoginController implements Initializable {

    @FXML
    private ImageView imageview;
    @FXML
    private TextField mail;
    @FXML
    private PasswordField pwd;
    @FXML
    private Button btnin;
    @FXML
    private Label btnfo;
    @FXML
    private Label btnfo1;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void signin(ActionEvent event) throws SQLException, IOException {
        String email = mail.getText();
            String pwd1 = pwd.getText();
            String crypted = Servicebcrypt.hashpw(pwd1, staticvar.SALT);
            ServiceRecruiter R = new ServiceRecruiter();
            if(R.auth(email, crypted))
            {
                Session.getInstace(email);
            
               Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        stage.close();

        Scene scene = new Scene(FXMLLoader.load(getClass().getResource("Profil.fxml")));
        stage.setScene(scene);
        stage.show();
            }
            else 
            {
                 JOptionPane.showMessageDialog(null, "Username / email : " + email + " n'exist pas !");
            }
    }

    private void Singup(ActionEvent event) throws IOException {
        
                Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        stage.close();

        Scene scene = new Scene(FXMLLoader.load(getClass().getResource("Singup.fxml")));
        stage.setScene(scene);
        stage.show();  
    }

    @FXML
    private void forgot(MouseEvent event) throws IOException {
                Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        stage.close();

        Scene scene = new Scene(FXMLLoader.load(getClass().getResource("ForgotPassword.fxml")));
        stage.setScene(scene);
        stage.show();  
    }

    @FXML
    private void Singup(MouseEvent event) throws IOException {
                     Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        stage.close();

        Scene scene = new Scene(FXMLLoader.load(getClass().getResource("Signup.fxml")));
        stage.setScene(scene);
        stage.show();  
    }
    
}
