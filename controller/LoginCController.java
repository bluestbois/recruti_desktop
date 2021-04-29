/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package esprit.recrutini.controller;

import esprit.recrutini.tools.DataSource;

import esprit.recrutini.entities.Candidate;

import esprit.recrutini.services.CandidateSession;
import static esprit.recrutini.services.CandidateSession.currentCandidate;
import esprit.recrutini.services.SendingMail;
import esprit.recrutini.services.Service_Candidate;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Properties;

import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.prefs.Preferences;
import javafx.animation.Animation;
import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.ScaleTransition;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;

import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import javafx.scene.control.CheckBox;

import javafx.scene.control.Label;
import javafx.scene.control.TextInputDialog;

import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import javafx.scene.layout.AnchorPane;

import javafx.stage.Stage;
import javafx.util.Duration;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import org.mindrot.jbcrypt.BCrypt;
import tray.animations.AnimationType;
import tray.notification.NotificationType;
import tray.notification.TrayNotification;

/**
 * FXML Controller class
 *
 * @author yessine darmoul
 */
public class LoginCController implements Initializable {

    @FXML
    private AnchorPane root;
    @FXML
    private JFXTextField email;
    @FXML
    private JFXPasswordField password;
    private JFXButton btnStarted;
//    Stage stage;
//
//   Scene scene;
    private ImageView ImageHome;
    private int minute;
    private int hour;
    private int second;
    private String am_pm;

    private boolean Timer;
    private Integer tseconds;
    private Calendar cal;
//    private DataBaseConnection dc;
    private PreparedStatement pst = null;
    private DataSource conn;
    private String sql;
    ResultSet rs;
    @FXML
    private JFXButton btnLogin;
    @FXML
    private JFXButton btnlSignUp;
    Preferences preferences;

    @FXML
    private JFXCheckBox rme;
    PreparedStatement ps;
    ResultSet res;
    @FXML
    private CheckBox cbpassword;
    CandidateSession US;
    private ImageView img1;
    @FXML
    private Label lblTime;
    private Label lblDate;
    Stage stage;
    @FXML
    private Label lblErrorMail;
    @FXML
    private JFXButton btnVerif;
    @FXML
    private AnchorPane anchorSignIN;
    @FXML
    private AnchorPane anchorSup;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        ScaleTransition st = new ScaleTransition(Duration.seconds(2), btnlSignUp);
        st.setFromX(1);
        st.setFromY(1.02);
        st.setToX(1.04);
        st.setToY(1.06);

        ScaleTransition st2 = new ScaleTransition(Duration.seconds(2), btnLogin.getClip());
        st2.setFromX(1.0);
        st2.setFromY(1.02);
        st2.setToX(1.04);
        st2.setToY(1.06);
        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                st.play();
                st.setOnFinished((ActionEvent event) -> {
                    stop();
                    st2.play();
                    st2.setOnFinished((ActionEvent event1) -> {
                        st.play();
                    });
                });
            }
        };
        timer.start();

        startClock();
        conn = DataSource.getInstance();

        try {
            ps = conn.getCnx().prepareStatement("select email from Candidate");
        } catch (SQLException ex) {
            Logger.getLogger(LoginCController.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
            res = ps.executeQuery();
        } catch (SQLException ex) {
            Logger.getLogger(LoginCController.class.getName()).log(Level.SEVERE, null, ex);
        }

        List list = new ArrayList();
        try {
            while (res.next()) {
                list.add(res.getString("email"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(LoginCController.class.getName()).log(Level.SEVERE, null, ex);
        }
        cbpassword.setOnAction((event) -> {
            if (cbpassword.isSelected()) {

                password.setPromptText(password.getText());
                password.setText("");
                password.setDisable(true);

            } else {
                password.setText(password.getPromptText());
                password.setPromptText("");
                password.setDisable(false);
            }

        });

    }

    // TODO
    private void startClock() {
        Timeline clock = new Timeline(new KeyFrame(Duration.millis(Calendar.getInstance().get(Calendar.MILLISECOND)), (ActionEvent event) -> {
            cal = Calendar.getInstance();
            second = cal.get(Calendar.SECOND);
            minute = cal.get(Calendar.MINUTE);
            hour = cal.get(Calendar.HOUR);
            am_pm = (cal.get(Calendar.AM_PM) == 0) ? "AM" : "PM";
            lblTime.setText(String.format("   %02d : %02d : %02d %s", hour, minute, second, am_pm));
            if (Timer) {
                if (tseconds == 0) {
                    Timer = false;
                    //timer.setText("Time Out");
                } else {
                    //timer.setText(tseconds.toString());
                    tseconds--;
                }
            }
        }), new KeyFrame(Duration.millis(Calendar.getInstance().get(Calendar.MILLISECOND))));
        clock.setCycleCount(Animation.INDEFINITE);
        clock.play();
    }

    private boolean isEmailValid(String email) {
        String pattern = "\\b[\\w.%-]+@[-.\\w]+\\.[A-Za-z]{2,4}\\b";
        return email.matches(pattern);
    }

    @FXML
    private void Login(ActionEvent event) throws SQLException, IOException {
        esprit.recrutini.services.Service_Candidate cc = new Service_Candidate();
        Candidate candidate = new Candidate();
        CandidateSession daoU = new CandidateSession();
        String mail = email.getText();
        if (cc.getmails(mail) != 0) {
            Candidate result = daoU.login(email.getText());
            currentCandidate = daoU.login(email.getText());

            if (BCrypt.checkpw(password.getText(), result.getPassword().replaceFirst("y", "a"))) 
            {
                //CandidateSession.getInstace(email.getText());

                try {
                    root = FXMLLoader.load(getClass().getResource("/esprit/recrutini/view/ProfilCandidate.fxml"));
                    Stage myWindow = (Stage) email.getScene().getWindow();
                    Scene sc = new Scene(root);
                    myWindow.setScene(sc);
                    myWindow.setTitle("page name");
                    //myWindow.setFullScreen(true);
                    myWindow.show();
                } catch (IOException ex) {
                    System.out.println("erreeeuuur");
                    Logger.getLogger(LoginCController.class.getName()).log(Level.SEVERE, null, ex);
                }

                System.out.println(currentCandidate.getLastname());

            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Wrong Password", ButtonType.OK);
                alert.showAndWait();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Wrong Email Or Password");
            alert.setHeaderText(null);
            alert.setContentText("Check Email/Password");
            alert.showAndWait();
        }
    }

    @FXML
    private void SignUp(ActionEvent event) throws IOException {
        try {
            stage = (Stage) btnlSignUp.getScene().getWindow();
            //Stage Stage=new Stage();
            //FXMLLoader loader=new FXMLLoader();
            anchorSup = FXMLLoader.load(getClass().getResource("/esprit/recrutini/view/SignUpC.fxml"));
            root.getChildren().add(anchorSup);
            Scene scene = btnlSignUp.getScene();
            scene.setRoot(root);

            stage.setScene(scene);
            stage.centerOnScreen();
            stage.show();

        } catch (IOException ex1) {
            //TODO:handle exception
            System.out.println("Error " + ex1.getMessage());
        }
        // LoginController.showInscription();
    }

    @FXML
    private void verify(ActionEvent event) throws SQLException {
        esprit.recrutini.services.Service_Candidate Candidate = new Service_Candidate();
        Candidate c1 = new Candidate();
        esprit.recrutini.services.SendingMail ssm = new SendingMail();
        String MAIL = email.getText();
        String pswd = password.getText();

        c1.setEmail(MAIL);
        c1.setPassword(pswd);
        int verif = Candidate.SingIn(c1);
        if (verif == 1) {
            ssm.SendEmail(MAIL);
            TextInputDialog dialog = new TextInputDialog("Votre code");

            dialog.setTitle("Verification code");
            dialog.setHeaderText("Please insert the code  \n that was sent to your mailbox ");
            dialog.setContentText("Please enter your code:");
            Optional<String> result = dialog.showAndWait();

            if (result.isPresent()) {
                System.out.println("Your code: " + result.get());
                if (Candidate.VerifierCompte(c1, result.get()) == 1) {
                    System.out.println("********" + Candidate.VerifierCompte(c1, result.get()));
                    Alert A1 = new Alert(Alert.AlertType.CONFIRMATION, "Activate" + currentCandidate.getFirstname() + "?");
                    A1.setTitle("Account information");
                    A1.setHeaderText("Activation account");
                    A1.setContentText("Please enter your details again to log in");
                    A1.showAndWait();
                    dialog.close();
                }

            }
        } else {
            if (verif == 1) {
                String mm = email.getText();
                String pass = password.getText();
                String requete = "SELECT * FROM Candidate Where password =?  and email = ?  ";
                pst = conn.getCnx().prepareStatement(requete);

                pst.setString(1, pass);
                pst.setString(2, mm);
                rs = pst.executeQuery();
                while (rs.next()) {
                    rs.getString(4);
                    rs.getInt(11);
                }

                if (rs.getInt(11) == 1) {
                    Alert A2 = new Alert(Alert.AlertType.INFORMATION);
                    A2.setTitle("Account Information");
                    A2.setHeaderText("Account already activated");
                    A2.setContentText("connect directly");
                    A2.showAndWait();
                } else {
                    System.out.println("hhhhhhhhhh");
                }

                // rs = pst.executeQuery();
            }
        }
    }

}
