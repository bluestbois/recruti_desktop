/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package esprit.recrutini.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.ProtocolException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Scanner;
import java.util.function.Predicate;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import static javafx.collections.FXCollections.observableList;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.SortEvent;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.DragEvent;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.web.HTMLEditor;
import javafx.stage.Stage;
import javax.swing.JOptionPane;
import esprit.recrutini.entities.Forum;
import esprit.recrutini.entities.Post;
import esprit.recrutini.entities.Recruiter;
import static esprit.recrutini.services.CandidateSession.currentCandidate;
import esprit.recrutini.services.CurseFilterService;
import esprit.recrutini.services.ForumCRUD;
import esprit.recrutini.services.PostCRUD;
import esprit.recrutini.services.ServiceRecruiter;
import esprit.recrutini.services.Session;
import esprit.recrutini.tools.DataSource;
import java.io.FileOutputStream;
import static java.sql.Types.NULL;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import javafx.collections.transformation.SortedList;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Node;

import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;
import org.controlsfx.control.textfield.AutoCompletionBinding;
import org.controlsfx.control.textfield.TextFields;

/**
 * FXML Controller class
 *
 * @author ASUS
 */
public class ForumController implements Initializable {

    @FXML
    private TextField tfTitleForum;
    @FXML
    private HTMLEditor tfDescriptionForum;
    @FXML
    private TableView<Forum> tableFourm;
    @FXML
    private TableColumn<Forum, String> col_title;
    // WebEngine webE ; 
    private TableColumn<Forum, String> col_dec;
    @FXML
    private Button btnDeleteForum;
    @FXML
    private Label controleTitle;
    @FXML
    private Label controleDescription;
    @FXML
    private Button btnDetails;
    @FXML
    private Label tfIdForum;
    private static int idf;
    ObservableList<Forum> oblistdisc = FXCollections.observableArrayList();
    ForumCRUD fc = new ForumCRUD();
    PostCRUD pc = new PostCRUD();

    @FXML
    private TextField cherche;
    Stage stage;
    @FXML
    private Button profile;
    @FXML
    private ImageView btnprofile;
    @FXML
    private Label userlabel;
    @FXML
    private ImageView logout;
    @FXML
    private Label titleF;
    private FlowPane fl;
    @FXML
    private Button btnClear;

    Set<String> possibleWordSet = new HashSet<>();

    private AutoCompletionBinding<String> autoCompletionBinding;

    String[] words;
    @FXML
    public WebView viewDescription;

    Session S = Session.getInstance();
    private String mail = "";
    @FXML
    private Button btnUpdateForum;
    @FXML
    private Label user;
    @FXML
    private Label userForum;
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
        // TODO        
        initTable();
        tfIdForum.setVisible(false);
//        affForm();
        //search_formation();
        words = fc.getForumsTitle().toArray(new String[0]);

        Collections.addAll(possibleWordSet, words);
        autoCompletionBinding = TextFields.bindAutoCompletion(cherche, possibleWordSet);

        cherche.setOnKeyPressed((KeyEvent e)
                -> {
            switch (e.getCode()) {
                case ENTER:
                    learnWord(cherche.getText());
                    break;
                default:
                    break;
            }
        });

    }

    private void learnWord(String text) {
        possibleWordSet.add(text);
        if (autoCompletionBinding != null) {
            autoCompletionBinding.dispose();
        }
        autoCompletionBinding = TextFields.bindAutoCompletion(cherche, possibleWordSet);
    }

    public void setTfTitleForum(String tfTitleForum) {
        this.tfTitleForum.setText(tfTitleForum);
    }

    public void setTfDescriptionForum(String tfDescriptionForum) {
        this.tfDescriptionForum.setHtmlText(tfDescriptionForum);
    }

    public void setTfIdForum(String tfIdForum) {
        this.tfIdForum.setText(tfIdForum);
    }

    public String getTfIdForum() {
        return tfIdForum.getText();
    }

    public void setTitleF(String titleF) {
        this.titleF.setText(titleF);
    }

    private void clearAll() {
        tfTitleForum.setText("");
        tfDescriptionForum.setHtmlText("");
        tfIdForum.setText("");

    }

    private void initTable() {

        try {
            oblistdisc = (ObservableList<Forum>) fc.readAlldiscc();
            col_title.setCellValueFactory(new PropertyValueFactory<>("title"));
            // col_dec.setCellValueFactory(new PropertyValueFactory<>("description"));
            tableFourm.setItems(oblistdisc);
            FilteredList<Forum> filteredData = new FilteredList<>(oblistdisc, b -> true);

            cherche.textProperty().addListener((observable, oldValue, newValue) -> {
                filteredData.setPredicate(Forum -> {
                    // If filter text is empty, display all persons.

                    if (newValue == null || newValue.isEmpty()) {
                        return true;
                    }

                    // Compare first name and last name of every person with filter text.
                    String lowerCaseFilter = newValue.toLowerCase();

                    if (Forum.getTitle().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                        return true; // Filter matches first name.
                    } else if (Forum.getDescription().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                        return true; // Filter matches last name.
                    } else {
                        return false; // Does not match.
                    }
                });
            });
            // 3. Wrap the FilteredList in a SortedList. 
            SortedList<Forum> sortedData = new SortedList<>(filteredData);

            // 4. Bind the SortedList comparator to the TableView comparator.
            // 	  Otherwise, sorting the TableView would have no effect.
            sortedData.comparatorProperty().bind(tableFourm.comparatorProperty());

            // 5. Add sorted (and filtered) data to the table.
            tableFourm.setItems(sortedData);

        } catch (SQLException ex) {
            Logger.getLogger(ForumController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void ajouter(ActionEvent event) throws FileNotFoundException {
        if (tfTitleForum.getText().isEmpty() || tfDescriptionForum.getHtmlText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "please fill all the textfields");
        } else {
            ForumCRUD pc = new ForumCRUD();
            Forum f = new Forum();
            controleDescription.setText("");
            controleTitle.setText("");
            String tTitle = CurseFilterService.cleanText(tfTitleForum.getText());
            String tDescription = CurseFilterService.cleanText(tfDescriptionForum.getHtmlText());
            f.setTitle(tTitle);
            f.setDescription(tDescription);
            if (currentCandidate.getId() != NULL) {
                System.out.println("idCandidate" + currentCandidate.getId());
                int idCandidate = currentCandidate.getId();
                f.setIdC(idCandidate);
                pc.addForumCandidate(f);
                Image img = new Image("/esprit/recrutini/img/ok.png");
                Notifications notifAdd = Notifications.create()
                        .title("add complet")
                        .text("Forum added by " + currentCandidate.getFirstname() + " " + currentCandidate.getLastname())
                        .graphic(new ImageView(img))
                        .hideAfter(Duration.seconds(5))
                        .position(Pos.TOP_RIGHT);
                notifAdd.show();

            } else {
                try {
                    mail = S.getEmail();
                    ServiceRecruiter R = new ServiceRecruiter();
                    Recruiter Re;

                    Re = R.getRecruiter(mail);

                    int idRecruteur = Re.getId();
                    f.setIdR(idRecruteur);

                    pc.addForumRecruiter(f);
                    Image img = new Image("/esprit/recrutini/img/ok.png");
                    Notifications notifAdd = Notifications.create()
                            .title("add complet")
                            .text("Forum added by " + Re.getUsername())
                            .graphic(new ImageView(img))
                            .hideAfter(Duration.seconds(5))
                            .position(Pos.TOP_RIGHT);
                    notifAdd.show();

                } catch (SQLException ex) {
                    Logger.getLogger(ForumController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            clearAll();
            initTable();
        }

    }

    @FXML
    private void delete(ActionEvent event) throws SQLException {

        Forum dis = tableFourm.getSelectionModel().getSelectedItem();

        Forum FC = fc.findByIdC(dis.getId());

        if (currentCandidate.getId() == FC.getIdC()) {
            int retour = JOptionPane.showConfirmDialog(null,
                    "OK - Annuler",
                    "titre",
                    JOptionPane.OK_CANCEL_OPTION);
            if (retour == 0) {
                if (!dis.equals(null)) {
                    fc.delete(dis.getId());
                    initTable();
                    Image img = new Image("/esprit/recrutini/img/remove.png");
                    Notifications notifAdd = Notifications.create()
                            .title("Remove")
                            .text("delete sucees")
                            .graphic(new ImageView(img))
                            .hideAfter(Duration.seconds(5))
                            .position(Pos.TOP_RIGHT);
                    notifAdd.show();
                    clearAll();

                }
            }

        } else if (currentCandidate.getId() == NULL) {

            mail = S.getEmail();
            ServiceRecruiter R = new ServiceRecruiter();
            Recruiter Re;

            Re = R.getRecruiter(mail);
            int idRecruteur = Re.getId();
            Forum FR = fc.findByIdR(dis.getId());
            System.out.println("idRecruteur :" + idRecruteur);
            System.out.println("fr.getIdR" + FR.getIdR());

            if (idRecruteur == FR.getIdR()) {
                int retour = JOptionPane.showConfirmDialog(null,
                        "OK - Annuler",
                        "titre",
                        JOptionPane.OK_CANCEL_OPTION);
                System.out.println("retour :" + retour);
                if (retour == 0) {
                    if (!dis.equals(null)) {
                        fc.delete(dis.getId());
                        initTable();
                        Image img = new Image("/esprit/recrutini/img/remove.png");
                        Notifications notifAdd = Notifications.create()
                                .title("Remove")
                                .text("delete sucees")
                                .graphic(new ImageView(img))
                                .hideAfter(Duration.seconds(5))
                                .position(Pos.TOP_RIGHT);
                        notifAdd.show();
                        clearAll();

                    }
                }
            } else {
                JOptionPane.showMessageDialog(null, "you can't delete the forum");
            }

        } else {

            JOptionPane.showMessageDialog(null, "you can't delete the forum");
        }
    }

    @FXML
    private void update(ActionEvent event) throws SQLException {

        Forum F = tableFourm.getSelectionModel().getSelectedItem();

        Forum FC = fc.findByIdC(F.getId());
        Forum FR = fc.findByIdR(F.getId());

        if (currentCandidate.getId() == FC.getIdC()) {
            if (F == null) {
                JOptionPane.showMessageDialog(null, "There is nothing selected !");
            } else {

                if (tfTitleForum.getText().isEmpty() || tfDescriptionForum.getHtmlText().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "please fill all the textfields ");
                } else {
                    controleDescription.setText("");
                    controleTitle.setText("");
                    fc.update(F.getId(), CurseFilterService.cleanText(tfTitleForum.getText()), CurseFilterService.cleanText(tfDescriptionForum.getHtmlText()));
                    Image img = new Image("/esprit/recrutini/img/update.png");
                    Notifications notifAdd = Notifications.create()
                            .title("Update")
                            .text("Update sucees")
                            .graphic(new ImageView(img))
                            .hideAfter(Duration.seconds(5))
                            .position(Pos.TOP_RIGHT);
                    notifAdd.show();
                    WebEngine WebE = viewDescription.getEngine();
                    WebE.loadContent(" ");
                    initTable();
                    clearAll();

                }

            }
        } else if (currentCandidate.getId() == NULL) {
            mail = S.getEmail();
            ServiceRecruiter R = new ServiceRecruiter();
            Recruiter Re;
            Re = R.getRecruiter(mail);

            if (FR.getIdR() == Re.getId()) {
                if (F == null) {
                    JOptionPane.showMessageDialog(null, "There is nothing selected !");
                } else {

                    if (tfTitleForum.getText().isEmpty() || tfDescriptionForum.getHtmlText().isEmpty()) {
                        JOptionPane.showMessageDialog(null, "please fill all the textfields ");
                    } else {
                        controleDescription.setText("");
                        controleTitle.setText("");
                        fc.update(F.getId(), CurseFilterService.cleanText(tfTitleForum.getText()), CurseFilterService.cleanText(tfDescriptionForum.getHtmlText()));
                        Image img = new Image("/esprit/recrutini/img/update.png");
                        Notifications notifAdd = Notifications.create()
                                .title("Update")
                                .text("Update sucees")
                                .graphic(new ImageView(img))
                                .hideAfter(Duration.seconds(5))
                                .position(Pos.TOP_RIGHT);
                        notifAdd.show();
                        WebEngine WebE = viewDescription.getEngine();
                        WebE.loadContent(" ");
                        initTable();
                        clearAll();

                    }
                }
            } else {
                JOptionPane.showMessageDialog(null, "you can't delete the forum");
            }
        } else {

            JOptionPane.showMessageDialog(null, "you can't Update the forum");

        }
    }

    @FXML
    private void handleAction(MouseEvent event
    ) {
        Forum F = tableFourm.getSelectionModel().getSelectedItem();
        if (F == null) {
            JOptionPane.showMessageDialog(null, "There is nothing selected !");
        } else {
            tfTitleForum.setText(F.getTitle());
            tfDescriptionForum.setHtmlText(F.getDescription());
            String id = Integer.toString(F.getId());
            titleF.setText(F.getTitle());
            tfIdForum.setText(id);
            WebEngine WebE = viewDescription.getEngine();
            WebE.loadContent(tfDescriptionForum.getHtmlText());
        }

    }

    @FXML
    private void detailsForum(ActionEvent event
    ) {
        try {

            FXMLLoader loader
                    = new FXMLLoader(getClass().getResource("/esprit/recrutini/view/DetailForum.fxml"));
            Parent root = loader.load();
            String test = tfTitleForum.getText();
            if ("".equals(test)) {
                JOptionPane.showMessageDialog(null, "There is nothing selected !");
            } else {
                DetailForumController dc = loader.getController();
                dc.setRestDescriptionFourm(tfDescriptionForum.getHtmlText());
                dc.setRestTitleForum(tfTitleForum.getText());
                dc.setRestIdFourm(tfIdForum.getText());
                dc.setTforum(tfTitleForum.getText());

                WebEngine WebE = dc.DescriptionFourm.getEngine();
                WebE.loadContent(tfDescriptionForum.getHtmlText());

                try {

                    dc.initTable((ObservableList<Post>) pc.readAllpost2(Integer.parseInt(tfIdForum.getText())));

                } catch (SQLException ex) {
                    Logger.getLogger(ForumController.class
                            .getName()).log(Level.SEVERE, null, ex);
                }
                tfTitleForum.getScene().setRoot(root);
            }
        } catch (IOException ex) {
            Logger.getLogger(ForumController.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void exit(MouseEvent event
    ) {

        stage = (Stage) ((ImageView) event.getSource()).getScene().getWindow();
        stage.close();
    }

    @FXML
    private void Clear(ActionEvent event
    ) {
        tfTitleForum.setText("");
        tfDescriptionForum.setHtmlText("");
        tfIdForum.setText("");
        WebEngine WebE = viewDescription.getEngine();
        WebE.loadContent("");
    }

    @FXML
    private void Profile(ActionEvent event
    ) {
        if (currentCandidate.getId() != NULL) {
            try {
                Node node = (Node) event.getSource();
                Stage stage = (Stage) node.getScene().getWindow();
                stage.close();

                Scene scene = new Scene(FXMLLoader.load(getClass().getResource("/esprit/recrutini/view/ProfilCandidate.fxml")));
                stage.setScene(scene);
                stage.show();
            } catch (IOException ex) {
                Logger.getLogger(AnnonceFrontController.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            try {
                Node node = (Node) event.getSource();
                Stage stage = (Stage) node.getScene().getWindow();
                stage.close();

                Scene scene = new Scene(FXMLLoader.load(getClass().getResource("/esprit/recrutini/view/Profil.fxml")));
                stage.setScene(scene);
                stage.show();
            } catch (IOException ex) {
                Logger.getLogger(AnnonceFrontController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @FXML
    private void Freelance(ActionEvent event
    ) {
        try {
            Node node = (Node) event.getSource();
            Stage stage = (Stage) node.getScene().getWindow();
            stage.close();

            Scene scene = new Scene(FXMLLoader.load(getClass().getResource("/esprit/recrutini/view/AnnonceFront.fxml")));
            stage.setScene(scene);
            stage.show();

        } catch (IOException ex) {
            Logger.getLogger(ProfilController.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void Job(ActionEvent event
    ) {
        try {
            Node node = (Node) event.getSource();
            Stage stage = (Stage) node.getScene().getWindow();
            stage.close();

            Scene scene = new Scene(FXMLLoader.load(getClass().getResource("/esprit/recrutini/view/Jobs.fxml")));
            stage.setScene(scene);
            stage.show();

        } catch (IOException ex) {
            Logger.getLogger(ProfilController.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }

}
