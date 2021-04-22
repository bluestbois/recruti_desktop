/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package recrutini.GUI;

import Service.ServiceRecruiter;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Properties;
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
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.swing.JOptionPane;

/**
 * FXML Controller class
 *
 * @author asus
 */
public class ForgotPasswordController implements Initializable {

    @FXML
    private ImageView imageview;
    @FXML
    private TextField mail;
    @FXML
    private Button btnin;
    @FXML
    private Label btnfo;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private boolean send(ActionEvent event) throws SQLException, IOException {
        ServiceRecruiter R = new ServiceRecruiter();
        int token = R.forgotpass(this.mail.getText());
        if (token != 0 && !this.mail.getText().isEmpty()) {
            try {
                String host = "smtp.gmail.com";
                String user = "recruritini@gmail.com";
                String pass = "recrutinirecrutini";
                String to = this.mail.getText();
                String from = "Recruitini";
                String subject = "Verification code";
                String messageText = "This is your verification code valid for 60 seconds :  " + token;
                boolean sessionDebug = false;

                Properties props = System.getProperties();

                props.put("mail.smtp.starttls.enable", "true");
                props.put("mail.smtp.host", host);
                props.put("mail.smtp.port", "587");
                props.put("mail.smtp.auth", "true");
                props.put("mail.smtp.starttls.required", "true");

                java.security.Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());
                Session mailSession = Session.getDefaultInstance(props, null);
                mailSession.setDebug(sessionDebug);
                Message msg = new MimeMessage(mailSession);
                msg.setFrom(new InternetAddress(from));
                InternetAddress[] address = {new InternetAddress(to)};
                msg.setRecipients(Message.RecipientType.TO, address);
                msg.setSubject(subject);
                msg.setSentDate(new java.util.Date());
                msg.setText(messageText);

                Transport transport = mailSession.getTransport("smtp");
                transport.connect(host, user, pass);

                transport.sendMessage(msg, msg.getAllRecipients());
                System.out.println("code was sent  successfully");
                
                   Service.Session.getInstace(this.mail.getText());
                       JOptionPane.showMessageDialog(null,"a verification code was sent to your email");
                              Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        stage.close();

        Scene scene = new Scene(FXMLLoader.load(getClass().getResource("ResetPassword.fxml")));
        stage.setScene(scene);
        stage.show();

                //    transport.close();
                return transport.isConnected();

            } catch (Exception ex) {
                System.out.println(ex);
            }
        } else {
            JOptionPane.showMessageDialog(null, this.mail.getText() + " doesn't existing, PLEASE SIGN UP");
            Node node = (Node) event.getSource();
            Stage stage = (Stage) node.getScene().getWindow();
            stage.close();

            Scene scene = new Scene(FXMLLoader.load(getClass().getResource("Signup.fxml")));
            stage.setScene(scene);
            stage.show();
        }
        return false;
    }

    private void Signup(ActionEvent event) throws IOException {
        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        stage.close();

        Scene scene = new Scene(FXMLLoader.load(getClass().getResource("Signup.fxml")));
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void Signup(MouseEvent event) {
    }

}
