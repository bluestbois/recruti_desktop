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
import java.util.Timer;
import java.util.TimerTask;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
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
public class ResetPasswordController implements Initializable {

    @FXML
    private ImageView imageview;
    @FXML
    private TextField password;
    @FXML
    private Button btnin;
    @FXML
    private TextField code;
    @FXML
    private Label btnfo;
    private String email = "";
    Session S = Session.getInstance();
   int timer = 60 ;
    @FXML
    private Label alert;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Timer chrono = new Timer();
        chrono.schedule(new TimerTask() {
         
            @Override
            public void run() {
          
             System.out.println(timer);
                if ( timer ==5)
                {
                    
                        System.out.println("attention !");
                }
                if (timer ==0)
                {
                    cancel();
                }
                   timer --;
              
            }
        }, 1000,1000);
       email = S.getEmail();
    }    

    @FXML
    private void send(ActionEvent event) throws SQLException, IOException {
          ServiceRecruiter R = new ServiceRecruiter();
          int token =Integer.parseInt(code.getText());
          //int token =0;
          if(timer>0)
          {
          if(R.getToken(token)!=0)
          {
                   String crypted = Servicebcrypt.hashpw( password.getText(), staticvar.SALT);
          R.updatepassword(email,crypted);
          R.UpdateToken(email);
          timer=0;
           JOptionPane.showMessageDialog(null,"password has been changed successfully "); 
                   R.UpdateToken(email);
                             Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        stage.close();

        Scene scene = new Scene(FXMLLoader.load(getClass().getResource("Login.fxml")));
        stage.setScene(scene);
        stage.show();  
          } 
          else {
              
               JOptionPane.showMessageDialog(null,"invalid code"); 
                   R.UpdateToken(email);
                             Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        stage.close();

        Scene scene = new Scene(FXMLLoader.load(getClass().getResource("ForgotPassword.fxml")));
        stage.setScene(scene);
        stage.show();  
                   
        }
          }
          else 
          {
              JOptionPane.showMessageDialog(null,"expired  code"); 
                   R.UpdateToken(email);
                             Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        stage.close();

        Scene scene = new Scene(FXMLLoader.load(getClass().getResource("ForgotPassword.fxml")));
        stage.setScene(scene);
        stage.show();   
          }
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


    
}
