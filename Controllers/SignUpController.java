package Controllers;

import CONNECTION.DataSource;

import Entity.Candidate;
import Services.SendingMail;
import Services.Service_Candidate;
import static Services.Service_Candidate.currentCandidate;
import com.jfoenix.controls.JFXButton;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import static java.lang.Integer.parseInt;
import java.net.MalformedURLException;
import java.net.URL;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Optional;

import java.util.ResourceBundle;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.effect.BoxBlur;

import javafx.scene.effect.Effect;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;

import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javax.mail.MessagingException;
import org.mindrot.jbcrypt.BCrypt;
import org.controlsfx.control.textfield.AutoCompletionBinding;
import org.controlsfx.control.textfield.TextFields;

/**
 * FXML Controller class
 *
 * @author yessine darmoul
 */
public class SignUpController implements Initializable {

   
    @FXML
    private JFXTextField email;
    @FXML
    private JFXPasswordField password;
    @FXML
    private DatePicker birthday;
    @FXML
    private JFXTextField tfphonenumber;
    @FXML
    private JFXTextField tfaddress;
    @FXML
    private JFXTextField tffirstname;
    @FXML
    private JFXTextField tfLastname;
    @FXML
    private JFXTextField tfgender;
    @FXML
    private JFXTextField tfnationality;
    @FXML
    private JFXTextField tfloe;
    @FXML
    private JFXTextField tfexperience;
    @FXML
    private TextField filename;
    @FXML
    private Label passwordStrength;
    private Label Error_output;
//    private static final Effect frostEffect
//            = new BoxBlur(10, 10, 3);
    private Circle Avatar;
    @FXML
    private AnchorPane root;
    @FXML
    private JFXButton btnRegister;
    @FXML
    private JFXButton btnback;
    @FXML
    private JFXTextField tfidU;
    private JFXTextField tfname;
    private DataSource conn;
    private PreparedStatement pst = null;
    // private Connection conn;
    private String sql;
    ResultSet rs;
    @FXML
    private ImageView aaa;
    private FileInputStream fis;
    @FXML
    private Label ImagePath;
    private ComboBox<String> cbAS;
   
    private AutoCompletionBinding<String> autoCompletionBinding;
    private String[] _possibleSuggestions = {"Tunis", "Ariana", "Ben Arous", "Mannouba", "Bizerte", "Nabeul", "Béja", "Jendouba", "Zaghouan", "Siliana", "Le Kef", "Sousse", "Monastir", "Mahdia", "Kasserine", "Sidi Bouzid", "Kairouan", "Gafsa", "Sfax", "Gabès", "Médenine", "Tozeur", "Kebili","Ttataouine"};
    private Set<String> possibleSuggestions = new HashSet<>(Arrays.asList(_possibleSuggestions));
    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        conn = DataSource.getInstance();
        password.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            if (!"".equals(newValue)) {
                updatePasswordStrength(newValue);
            } else {
                passwordStrength.setText("");
            }
        });
        filename.setEditable(false);
        birthday.setEditable(false);
        TextFields.bindAutoCompletion(tfaddress,"Tunis", "Ariana", "Ben Arous", "Mannouba", "Bizerte", "Nabeul", "Béja", "Jendouba", "Zaghouan", "Siliana", "Le Kef", "Sousse", "Monastir", "Mahdia", "Kasserine", "Sidi Bouzid", "Kairouan", "Gafsa", "Sfax", "Gabès", "Médenine", "Tozeur", "Kebili","Ttataouine");
        TextFields.bindAutoCompletion(tfnationality,"Afghan","Albanian","Algerian","American","Andorran","Angolan","Anguillan","Citizen of Antigua and Barbuda","Argentine","Armenian","Australian","Austrian","Azerbaijani","Bahamian","Bahraini","Bangladeshi","Barbadian","Belarusian","Belgian","Belizean","Beninese","Bermudian","Bhutanese","Bolivian","Citizen of Bosnia and Herzegovina","Botswanan","Brazilian","British","British Virgin Islander","Bruneian","Bulgarian","Burkinan","Burmese","Burundian","Cambodian","Cameroonian","Canadian","Cape Verdean","Cayman Islander","Central African","Chadian","Chilean","Chinese","Colombian","Comoran","Congolese (Congo)","Congolese (DRC)","Cook Islander","Costa Rican","Croatian","Cuban","Cymraes","Cymro","Cypriot","CzechDanish","Djiboutian","Dominican","Citizen of the Dominican Republic","DutchEast","Timorese","Ecuadorean","Egyptian","Emirati","English","Equatorial Guinean","Eritrean","Estonian","EthiopianFaroese","Fijian","Filipino","Finnish","FrenchGabonese","Gambian","Georgian","German","Ghanaian","Gibraltarian","Greek","Greenlandic","Grenadian","Guamanian","Guatemalan","Citizen of Guinea-Bissau","Guinean","Guyanese","Haitian","Honduran","Hong Konger","HungarianIcelandic","Indian","Indonesian","Iranian","Iraqi","Irish","Israeli","Italian","Ivorian","Jamaican","Japanese","Jordanian","Kazakh","Kenyan","Kittitian","Citizen of Kiribati","Kosovan","Kuwaiti","Kyrgyz","Lao","Latvian","Lebanese","Liberian","Libyan","Liechtenstein citizen","Lithuanian","Luxembourger","Macanese","Macedonian","Malagasy","Malawian","Malaysian","Maldivian","Malian","Maltese","Marshallese","Martiniquais","Mauritanian","Mauritian","Mexican","Micronesian","Moldovan","Monegasque","Mongolian","Montenegrin","Montserratian","Moroccan","Mosotho","Mozambican","Namibian","Nauruan","Nepalese","New Zealander","Nicaraguan","Nigerian","Nigerien","Niuean","North Korean","Northern Irish","Norwegian","OmaniPakistani","Palauan","Palestinian","Panamanian","Papua New Guinean","Paraguayan","Peruvian","Pitcairn Islander","Polish","Portuguese","Prydeinig","Puerto Rican","Qatari","Romanian","Russian","Rwandan","Salvadorean","Sammarinese","Samoan","Sao Tomean","Saudi Arabian","Scottish","Senegalese","Serbian","Citizen of Seychelles","Sierra Leonean","Singaporean","Slovak","Slovenian","Solomon Islander","Somali","South African","South Korean","South Sudanese","Spanish","Sri Lankan","St Helenian","St Lucian","	Stateless","Sudanese","Surinamese","Swazi","Swedish","Swiss","Syrian","Taiwanese","Tajik","Tanzanian","Thai","Togolese","Tongan","Trinidadian","Tristanian","Tunisian","Turkish","Turkmen","Turks and Caicos Islander","TuvaluanUgandan","Ukrainian","Uruguayan","UzbekVatican citizen","Citizen of Vanuatu","Venezuelan","Vietnamese","Vincentian","Wallisian","Welsh","Yemeni","Zambian","Zimbabwean");

        try {
            autoOrderNOP();

            //  tfidU.setEditable(false);
        } catch (SQLException ex) {
            Logger.getLogger(SignUpController.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            tfidU.setText("" + autoOrderNOP());
        } catch (SQLException ex) {
            Logger.getLogger(SignUpController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private static boolean copyFileUsingStream(File source, File dest) throws Exception {
        InputStream is = null;
        OutputStream os = null;
        try {
            is = new FileInputStream(source);
            os = new FileOutputStream(dest);
            byte[] buffer = new byte[1024];
            int length;
            while ((length = is.read(buffer)) > 0) {
                os.write(buffer, 0, length);
            }
        } finally {
            is.close();
            os.close();
        }
        return true;
    }

    @FXML
    private void chooseProfilePicture(ActionEvent event) throws Exception {
        FileChooser chooser = new FileChooser();
        chooser.setTitle("Open File");
        chooser.setInitialDirectory(new File(System.getProperty("user.home")));
        chooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Image Files", "*.bmp", "*.png", "*.jpg", "*.gif"));
        File file = chooser.showOpenDialog(new Stage());
        if (file != null) {
            String imagepath = file.toURI().toURL().toString();
            System.out.println("file:" + imagepath);
            Image image = new Image(imagepath);
            System.out.println("height:" + image.getHeight() + "\nWidth:" + image.getWidth());
            aaa.setImage(image);
            
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information Dialog");
            alert.setHeaderText("Please Select a File");
          
            alert.showAndWait();
        }

    }
    private static final String[] VALID_EXTENSIONS = new String[]{".png", ".jpg", "jpeg", "gif", "bmp"};

    final String newStyle = "-fx-background-radius: 50em;\n"
            + "    -fx-min-width: 50px;\n"
            + "    -fx-min-height: 50px; \n"
            + "    -fx-max-width: 50px;\n"
            + "    -fx-max-height: 50px;\n"
            + "    -fx-background-color: #6fb52c;\n"
            + "    -fx-text-fill: #ffffff;\n"
            + "    -fx-border-color: #ffffff;\n"
            + "    -fx-border-width: 2px; \n"
            + "    -fx-background-insets:0;"
            + "    -fx-border-radius: 50em;";

    

    private boolean isPhoneNumberValid() {
        
         Pattern p = Pattern.compile("^[0-9]{8}$");
         Matcher m = p.matcher(tfphonenumber.getText());
         if (m.find() && m.group().equals(tfphonenumber.getText())) {
            return true;
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Validate Phone number");
            alert.setHeaderText(null);
            alert.setContentText("Please Enter Valid Phone number");
            alert.showAndWait();

            return false;
        }
        
    }

    private boolean isFormFilled() {
        if (     tffirstname.getText().equals("") || tfLastname.getText().equals("") || password.getText().equals("")
                || email.getText().equals("") || tfphonenumber.getText().equals("") || birthday.getValue() == null
                || tfaddress.getText().equals("") || tfgender.getText().equals("")|| tfloe.getText().equals("")  || tfexperience.getText().equals("")) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Fields missing");
            alert.setHeaderText(null);
            alert.setContentText("Please fill all the fields");
            alert.showAndWait();
            return false;
        }
        return true;
    }

    private int calculatePasswordStrength(String password) {

        //total score of password
        int iPasswordScore = 0;

        if (password.length() < 8) {
            return 0;
        } else if (password.length() >= 10) {
            iPasswordScore += 2;
        } else {
            iPasswordScore += 1;
        }

        //if it contains one digit, add 2 to total score
        if (password.matches("(?=.*[0-9]).*")) {
            iPasswordScore += 2;
        }

        //if it contains one lower case letter, add 2 to total score
        if (password.matches("(?=.*[a-z]).*")) {
            iPasswordScore += 2;
        }

        //if it contains one upper case letter, add 2 to total score
        if (password.matches("(?=.*[A-Z]).*")) {
            iPasswordScore += 2;
        }

        //if it contains one special character, add 2 to total score
        if (password.matches("(?=.*[~!@#$%^&*()_-]).*")) {
            iPasswordScore += 2;
        }

        return iPasswordScore;

    }



    private void updatePasswordStrength(String value) {
        if (calculatePasswordStrength(value) < 5) {
            passwordStrength.setText("( weak )");
            passwordStrength.setTextFill(Color.web("#ff0505"));
        } else if (calculatePasswordStrength(value) == 5) {
            passwordStrength.setText("( average )");
            passwordStrength.setTextFill(Color.web("#ed701b"));
        } else if (calculatePasswordStrength(value) >= 8) {
            passwordStrength.setText("( strong )");
            passwordStrength.setTextFill(Color.web("#6fb52c"));
        }
    }

    public boolean validateNomP() {
        Pattern p = Pattern.compile("[a-zA-Z]+");
        Matcher m = p.matcher(tffirstname.getText());
        if (m.find() && m.group().equals(tffirstname.getText())) {
            return true;
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Validate First Name");
            alert.setHeaderText(null);
            alert.setContentText("Please Enter Valid First Name");
            alert.showAndWait();

            return false;
        }
    }

    public boolean validateEmaillP() {
        Pattern p = Pattern.compile("[a-zA-Z0-9][a-zA-Z0-9._]*@[a-zA-Z0-9]+([.][a-zA-Z]+)+");
        Matcher m = p.matcher(email.getText());
        if (m.find() && m.group().equals(email.getText())) {
            return true;
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Validate Email");
            alert.setHeaderText(null);
            alert.setContentText("Please Enter Valid Email");
            alert.showAndWait();

            return false;
        }
    }

    public boolean validatePasswordP() {
        // Pattern p = Pattern.compile("(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{6,}");
        // Matcher m = p.matcher(pwdC.getText());
        String pattern = "(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{6,}";
        if (password.getText().matches(pattern)) {

            if (password.getText().equals(password.getText())) {
                return true;
            } else {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Validate Password");
                alert.setHeaderText(null);
                alert.setContentText("Check your password confirmation");
                alert.showAndWait();
                return false;
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Validate Password");
            alert.setHeaderText(null);
            alert.setContentText("Password must contain at least one(Digit, Lowercase, UpperCase and Special Character) and length must be between 6 -15");
            alert.showAndWait();

            return false;
        }
    }

    public boolean validateDateP() {
        if (birthday.getValue() == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Validate Date");
            alert.setHeaderText(null);
            alert.setContentText("Enter valid date");
            alert.showAndWait();
            return false;
        }

        Date date = java.sql.Date.valueOf(birthday.getValue());
        DateFormat dateFormat = new SimpleDateFormat("yyyy-dd-MM");
        Calendar c = Calendar.getInstance();
        if (c.getTime().after(date) == true && c.getTime().equals(date) == false) {
            return true;

        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Validate Date");
            alert.setHeaderText(null);
            alert.setContentText("Enter valid date");
            alert.showAndWait();
            return false;

        }
    }

    public boolean validatePasswordC() {
        // Pattern p = Pattern.compile("(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{6,}");
        // Matcher m = p.matcher(pwdC.getText());
        String pattern = "(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{6,}";
        String pattern1 = "(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{6,}";
        if (password.getText().matches(pattern) && password.getText().equals(password.getText())) {
            return true;
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Validate Password");
            alert.setHeaderText(null);
            alert.setContentText("Password must contain at least one(Digit, Lowercase, UpperCase and Special Character) and length must be between 6 -15");
            alert.showAndWait();

            return false;
        }

    }
    

    @FXML
    private void CreateAccount(ActionEvent event) throws SQLException, FileNotFoundException, MalformedURLException, IOException, MessagingException {
        SendingMail sm = new SendingMail();
        conn = DataSource.getInstance();
        Services.Service_Candidate cc = new Service_Candidate();
        String img = ImagePath.getText();
        img = img.replace("//", "////");
        String aa = "not yet";
        String sql = "insert into Candidate (id,first_name,last_name,email,password,birthday,gender,nationality,phone_number,address,image,loe,experience) values (?,?,?,?,?,?,?,?,?,?,?,?,?)";
        //  if(isFormFilled()){
        if (isFormFilled() && validateEmaillP() && validateDateP() == true && isPhoneNumberValid() && validateNomP() /*&& validatePasswordC()*/) {
            
            Integer.valueOf(tfidU.getText());
            String firstname = tffirstname.getText();
            String lastname = tfLastname.getText();
            String mail = email.getText();
            String pswd = password.getText();
            pswd = BCrypt.hashpw(pswd, BCrypt.gensalt(13));
            pswd= pswd.replaceFirst("a", "y");
            Date.valueOf(birthday.getValue());
            String gender = tfgender.getText();
            String nationality = tfnationality.getText();
            String phonenumber = tfphonenumber.getText();
            String address = tfaddress.getText();
            String loe = tfloe.getText();
            Integer experience = parseInt(tfexperience.getText());

            // String birthDate = this.birthDate.getText();
            
            
            if(cc.getmails(mail)!=0)
            {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Email already used !");
            alert.setHeaderText(null);
            alert.setContentText("Enter unused email");
            alert.showAndWait();
            }else{
            try {
                pst = conn.getCnx().prepareStatement(sql);
                pst.setInt(1, Integer.valueOf(tfidU.getText()));
                pst.setString(2, firstname);
                pst.setString(3, lastname);
                pst.setString(4, mail);
                pst.setString(5, pswd);
                pst.setString(7, gender);                
                pst.setString(8, nationality);
                pst.setString(9, phonenumber);
                pst.setDate(6, Date.valueOf(birthday.getValue()));
                pst.setString(10, address);
                pst.setString(11,"");
                pst.setString(12, loe);
                pst.setInt(13, experience);
                //pst.setString(10, img);
                // pst.setString(10,String.valueOf(cbAS.getSelectionModel().getSelectedItem()) );
              

                int i = pst.executeUpdate();
                if (i == 1) {

                    sm.SendEmail(mail);
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Registration confirmation");
                    alert.setHeaderText("Successful registration");
                    alert.setContentText("A verification email has been sent, please verify your account before logging in");
                    alert.showAndWait();

                    Services.Service_Candidate c = new Service_Candidate();
                    
                    Candidate c1 = new Candidate();
                    Services.SendingMail ssm = new SendingMail();
                    String MAIL = email.getText();
                    String pswd1 = password.getText();
                   
                    c1.setEmail(MAIL);
                    c1.setPassword(pswd);
                    
                    int verif = c.SingIn(c1);
                   
                    TextInputDialog dialog = new TextInputDialog("Votre code");

                    dialog.setTitle("Verification code");
                    dialog.setHeaderText("Please insert the code  \n that was sent to your mailbox ");
                    dialog.setContentText("Please enter your code:");
                    Optional<String> result = dialog.showAndWait();

                    if (result.isPresent()) {
                        System.out.println("Your code: " + result.get());
                       if (c.VerifierCompte(c1, result.get()) == 1) {
                            Alert A1 = new Alert(Alert.AlertType.CONFIRMATION, "Activate" + currentCandidate.getFirstname() + "?");
                            A1.setTitle("Account information");
                            A1.setHeaderText("Activation account");
                            A1.setContentText("Please enter your details again to log in");
                            A1.showAndWait();
                            dialog.close();
                        } 

                    }
                } else {
                    Services.Service_Candidate c = new Service_Candidate();
                    
                    Candidate c1 = new Candidate();
                    int verif1 = c.SingIn(c1);
                    
                    if ( verif1 == 1 ) {
                        String mm = email.getText();
                        String pass = password.getText();
                        String requete = "SELECT * FROM Candidate Where password =?  and email = ?  ";
                        pst = conn.getCnx().prepareStatement(requete);

                        pst.setString(1, pass);
                        pst.setString(3, mm);
                        rs = pst.executeQuery();
                        while (rs.next()) {
                            rs.getString(1);
                            rs.getInt(8);
                        }

                        if (rs.getInt(6) == 1) {
                            Alert A2 = new Alert(Alert.AlertType.INFORMATION);
                            A2.setTitle("Account Information");
                            A2.setHeaderText("Account already activated");
                            A2.setContentText("connect directly");
                            A2.showAndWait();
                        } else {
                            System.out.println("hhhhhh");
                        }

            rs = pst.executeQuery();
                    }

                }
            } catch (SQLException ex) {
                System.out.println(ex);

            } finally {
                pst.close();
                BackAutomaticly(event);
                //}
            }

        }
    }}

    private void clearFields() {
        tfidU.setText(null);
        tffirstname.setText(null);
        tfLastname.setText(null);
        email.setText(null);
        tfgender.setText(null);
        tfnationality.setText(null);
        tfloe.setText(null);
        tfexperience.setText(null);
        password.setText(null);
        birthday.setValue(null);
        tfaddress.setText(null);
        tfphonenumber.setText(null);

    }

    private int autoOrderNOP() throws SQLException {
        int nop = 0;
        try {
            conn = DataSource.getInstance();

            String sql2 = "select max(id) from Candidate";
            pst = conn.getCnx().prepareStatement(sql2);
            rs = pst.executeQuery();
            if (rs.next()) {
                nop = rs.getInt(1);

            }

            nop++;
            pst.close();
            rs.close();

        } catch (SQLException e) {
        }
        return nop;

    }

    private void BackAutomaticly(ActionEvent event) throws IOException {
        try {
            ((Node) event.getSource()).getScene().getWindow().hide();
            Stage primaryStage = new Stage();
            FXMLLoader loader = new FXMLLoader();
            Pane root = loader.load(getClass().getResource("/GUI/Login.fxml"));
            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.show();

        } catch (IOException ex2) {
            //TODO:handle exception 
            System.out.println("Error :" + ex2.getMessage());
        }
    }

    @FXML
    private void back(ActionEvent event) {
        try {
            ((Node) event.getSource()).getScene().getWindow().hide();
            Stage primaryStage = new Stage();
            FXMLLoader loader = new FXMLLoader();
            Pane root = loader.load(getClass().getResource("/GUI/Login.fxml"));
            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.show();

        } catch (IOException ex2) {
            //TODO:handle exception 
            System.out.println("Error :" + ex2.getMessage());
        }
    }


}
