/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package esprit.recrutini.controller;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javax.swing.JOptionPane;
import esprit.recrutini.entities.Comment;
import esprit.recrutini.entities.Forum;
import esprit.recrutini.entities.Post;
import esprit.recrutini.entities.Recruiter;
import static esprit.recrutini.services.CandidateSession.currentCandidate;
import esprit.recrutini.services.CommentCRUD;
import esprit.recrutini.services.CurseFilterService;
import esprit.recrutini.services.ForumCRUD;
import esprit.recrutini.services.PostCRUD;
import esprit.recrutini.services.ServiceRecruiter;
import esprit.recrutini.services.Session;
import esprit.recrutini.tools.DataSource;
import java.sql.Connection;
import static java.sql.Types.NULL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.web.HTMLEditor;
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
public class DetailForumController implements Initializable {

    @FXML
    private Label restTitleForum;
    @FXML
    private Label restDescriptionFourm;
    @FXML
    public TableView<Post> tablePost;
    @FXML
    private TableColumn<Post, String> col_title;

    ObservableList<Post> oblistpost = FXCollections.observableArrayList();
    ForumCRUD pf = new ForumCRUD();
    PostCRUD pc = new PostCRUD();
    CommentCRUD cc = new CommentCRUD();
    @FXML
    private Label idF;

    @FXML
    private TextField tfTitlePost;
    @FXML
    private HTMLEditor tfDescriptionPost;
    @FXML
    private Button btnAdd;
    @FXML
    private Button btnDelete;
    @FXML
    private Button btnmodif;
    @FXML
    private Button btnDetailsPost;
    @FXML
    private Label idP;
    Stage stage;
    @FXML
    private Button btnR;
    Forum forumTest;
    @FXML
    private Button profile;
    @FXML
    private ImageView btnprofile;
    @FXML
    private Label userlabel;
    @FXML
    private ImageView logout;
    @FXML
    private Label Tforum;
    @FXML
    private Label tPost;
    private PieChart statPost;
    ObservableList<PieChart.Data> statPostdata;
    private Connection con;
    @FXML
    private Button btnClear;
    @FXML
    public WebView DescriptionFourm;
    @FXML
    public WebView DescriptionPost;
    @FXML
    private Label noc;
    @FXML
    private Label views;
    @FXML
    private Label date;

    Session S = Session.getInstance();
    private String mail = "";
    @FXML
    private ImageView returnimage;
    @FXML
    private Button btnForum;
    @FXML
    private Button btnFreelance;
    @FXML
    private Button btnJob;

    /**
     * Initializes the controller class.
     */
    public DetailForumController() {
        con = DataSource.getInstance().getCnx();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        idF.setVisible(false);
        idP.setVisible(false);
        restDescriptionFourm.setVisible(false);

    }

    private void clearAll() {
        idP.setText("");
        tfTitlePost.setText("");
        tfDescriptionPost.setHtmlText("");

    }

    public void setDescriptionPost(WebView DescriptionPost) {
        this.DescriptionPost = DescriptionPost;
    }

    public void setTfTitlePost(String tfTitlePost) {
        this.tfTitlePost.setText(tfTitlePost);
    }

    public void setTfDescriptionPost(String tfDescriptionPost) {
        this.tfDescriptionPost.setHtmlText(tfDescriptionPost);
    }

    public void setIdP(String idP) {
        this.idP.setText(idP);
    }

    public void setRestDescriptionFourm(String restDescriptionFourm) {
        this.restDescriptionFourm.setText(restDescriptionFourm);
    }

    public void setTforum(String Tforum) {
        this.Tforum.setText(Tforum);
    }

    public void settPost(String tPost) {
        this.tPost.setText(tPost);
    }

    public void setRestTitleForum(String restTitleForum) {
        this.restTitleForum.setText(restTitleForum);
    }

    public void setRestIdFourm(String idF) {
        this.idF.setText(idF);
    }

    public void setViews(String views) {
        this.views.setText(views);
    }

    public void setNoc(String noc) {
        this.noc.setText(noc);
    }

    public void setDate(String date) {
        this.date.setText(date);
    }

    private void initTable(int id) {
        try {
            /*String idfs = idtest.getText();
            Integer idf = Integer.valueOf(idfs);*/

            oblistpost = (ObservableList<Post>) pc.readAllpost2(id);
            col_title.setCellValueFactory(new PropertyValueFactory<>("title"));
            // col_description.setCellValueFactory(new PropertyValueFactory<>("description"));
            //col_noc.setCellValueFactory(new PropertyValueFactory<>("noc"));
            // col_views.setCellValueFactory(new PropertyValueFactory<>("views"));

            tablePost.setItems(oblistpost);
        } catch (SQLException ex) {
            Logger.getLogger(DetailForumController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void initTable(ObservableList<Post> posts) {
        // String idfs = idtest.getText();
        // Integer idf = Integer.valueOf(idfs);
        //oblistpost = (ObservableList<Post>) pc.readAllpost2(idf);
        col_title.setCellValueFactory(new PropertyValueFactory<>("title"));
        //  col_description.setCellValueFactory(new PropertyValueFactory<>("description"));
        //  col_noc.setCellValueFactory(new PropertyValueFactory<>("noc"));

        //  col_views.setCellValueFactory(new PropertyValueFactory<>("views"));
        tablePost.setItems(posts);

    }

    @FXML
    private void ajouter(ActionEvent event) {
        if (tfTitlePost.getText().isEmpty() || tfDescriptionPost.getHtmlText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "please fill all the textfields");
        } else {
            PostCRUD pc = new PostCRUD();
            Post p = new Post();
            String tTitle = CurseFilterService.cleanText(tfTitlePost.getText());
            String tDescription = CurseFilterService.cleanText(tfDescriptionPost.getHtmlText());
            p.setTitle(tTitle);
            p.setDescription(tDescription);
            Integer i = Integer.valueOf(idF.getText());
            p.setIdF(i);
            if (currentCandidate.getId() != NULL) {
                System.out.println("idCandidate" + currentCandidate.getId());
                int idCandidate = currentCandidate.getId();
                p.setIdC(idCandidate);
                pc.addPostC(p, i);
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
                    p.setIdR(idRecruteur);
                    pc.addPostR(p, i);
                    Image img = new Image("/esprit/recrutini/img/ok.png");
                    Notifications notifAdd = Notifications.create()
                            .title("add complet")
                            .text("Forum added by " + Re.getUsername())
                            .graphic(new ImageView(img))
                            .hideAfter(Duration.seconds(5))
                            .position(Pos.TOP_RIGHT);
                    notifAdd.show();
                } catch (SQLException ex) {
                    Logger.getLogger(DetailForumController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            clearAll();
            initTable(i);
            System.out.println(p.getDate());

        }
    }

    @FXML
    private void select(MouseEvent event
    ) {
        Post P = tablePost.getSelectionModel().getSelectedItem();
        tfTitlePost.setText(P.getTitle());
        tfDescriptionPost.setHtmlText(P.getDescription());
        Integer idPost = P.getId();
        idP.setText(idPost.toString());
        WebEngine WebE = DescriptionPost.getEngine();
        WebE.loadContent(tfDescriptionPost.getHtmlText());
        noc.setText(Integer.toString(P.getNoc()));
        views.setText(Integer.toString(P.getViews()));

        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        date.setText(df.format(P.getDate()));

        String id = Integer.toString(P.getId());
        String tpost = tfTitlePost.getText();
        tPost.setText(">" + tpost);
    }

    @FXML
    private void delete(ActionEvent event) throws SQLException {
        Integer i = Integer.valueOf(idF.getText());
        Post dis = tablePost.getSelectionModel().getSelectedItem();

        Post PC = pc.findByIdC(dis.getId());
        Post PR = pc.findByIdR(dis.getId());

        if (currentCandidate.getId() == PC.getIdC()) {
            int retour = JOptionPane.showConfirmDialog(null, "OK - Annuler",
                    "titre",
                    JOptionPane.OK_CANCEL_OPTION);
            System.out.println("retour :" + retour);
            if (retour == 0) {
                if (!dis.equals(null)) {
                    pc.delete(dis.getId());
                    Image img = new Image("/esprit/recrutini/img/remove.png");
                    Notifications notifAdd = Notifications.create()
                            .title("Remove")
                            .text("delete sucees")
                            .graphic(new ImageView(img))
                            .hideAfter(Duration.seconds(5))
                            .position(Pos.TOP_RIGHT);
                    notifAdd.show();
                    initTable(i);
                    clearAll();
                }
            }
        } else if (currentCandidate.getId() == NULL) {
            mail = S.getEmail();
            ServiceRecruiter R = new ServiceRecruiter();
            Recruiter Re;

            Re = R.getRecruiter(mail);

            int idRecruteur = Re.getId();
            if (idRecruteur == PR.getIdR()) {
                int retour = JOptionPane.showConfirmDialog(null, "OK - Annuler",
                        "titre",
                        JOptionPane.OK_CANCEL_OPTION);
                System.out.println("retour :" + retour);
                if (retour == 0) {
                    if (!dis.equals(null)) {
                        pc.delete(dis.getId());
                        Image img = new Image("/esprit/recrutini/img/remove.png");
                        Notifications notifAdd = Notifications.create()
                                .title("Remove")
                                .text("delete sucees")
                                .graphic(new ImageView(img))
                                .hideAfter(Duration.seconds(5))
                                .position(Pos.TOP_RIGHT);
                        notifAdd.show();
                        initTable(i);
                        clearAll();
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "you can't delete the Post");
                }
            }
        } else {
            JOptionPane.showMessageDialog(null, "you can't delete the Post");
        }
    }

    @FXML
    private void update(ActionEvent event) throws SQLException {
        Integer i = Integer.valueOf(idF.getText());
        Post P = tablePost.getSelectionModel().getSelectedItem();
        Post PC = pc.findByIdC(P.getId());
        Post PR = pc.findByIdR(P.getId());

        if (currentCandidate.getId() == PC.getIdC()) {
            if (P == null) {
                JOptionPane.showMessageDialog(null, "There is nothing selected !");
            } else {
                if (tfTitlePost.getText().isEmpty() || tfDescriptionPost.getHtmlText().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "please fill all the textfields ");
                } else {
                    pc.update(P.getId(), CurseFilterService.cleanText(tfTitlePost.getText()), CurseFilterService.cleanText(tfDescriptionPost.getHtmlText()));
                    Image img = new Image("/esprit/recrutini/img/update.png");
                    Notifications notifAdd = Notifications.create()
                            .title("Update")
                            .text("Update sucees")
                            .graphic(new ImageView(img))
                            .hideAfter(Duration.seconds(5))
                            .position(Pos.TOP_RIGHT);
                    notifAdd.show();
                    initTable(i);
                    clearAll();

                }
            }
        } else if (currentCandidate.getId() == NULL) {
            ServiceRecruiter R = new ServiceRecruiter();
            Recruiter Re;
            Re = R.getRecruiter(mail);
            if (PR.getIdR() == Re.getId()) {
                if (P == null) {
                    JOptionPane.showMessageDialog(null, "There is nothing selected !");
                } else {
                    if (tfTitlePost.getText().isEmpty() || tfDescriptionPost.getHtmlText().isEmpty()) {
                        JOptionPane.showMessageDialog(null, "please fill all the textfields ");
                    } else {
                        pc.update(P.getId(), CurseFilterService.cleanText(tfTitlePost.getText()), CurseFilterService.cleanText(tfDescriptionPost.getHtmlText()));
                        Image img = new Image("/esprit/recrutini/img/update.png");
                        Notifications notifAdd = Notifications.create()
                                .title("Update")
                                .text("Update sucees")
                                .graphic(new ImageView(img))
                                .hideAfter(Duration.seconds(5))
                                .position(Pos.TOP_RIGHT);
                        notifAdd.show();
                        initTable(i);
                        clearAll();

                    }
                }

            } else {

                JOptionPane.showMessageDialog(null, "you can't Update the Post");

            }

        } else {

            JOptionPane.showMessageDialog(null, "you can't Update the Post");
        }
    }

    @FXML
    private void DetailsPost(ActionEvent event) throws SQLException {
        try {
            FXMLLoader loader
                    = new FXMLLoader(getClass().getResource("/esprit/recrutini/view/DetailPost.fxml"));
            Parent root = loader.load();
            String test = tfTitlePost.getText();
            if ("".equals(test)) {
                JOptionPane.showMessageDialog(null, "There is nothing selected !");
            } else {
                String v = idP.getText();
                int idPclicked = Integer.valueOf(v);
                DetailPostController dp = loader.getController();

                dp.setDescriptionP(tfDescriptionPost.getHtmlText());
                dp.setTitleP(tfTitlePost.getText());
                dp.setIdPc(idP.getText());
                dp.setIdForum(idF.getText());
                dp.setTitleForum(restTitleForum.getText());
                dp.setDescriptionFourm(restDescriptionFourm.getText());
                dp.setTF(restTitleForum.getText());
                dp.setTP(">" + tfTitlePost.getText());
                pc.updatePostview(idPclicked);
                initTable(idPclicked);

                WebEngine WebP = dp.DescriptionPost.getEngine();
                WebP.loadContent(tfDescriptionPost.getHtmlText());

                WebEngine WebF = dp.DescriptionForum.getEngine();
                WebF.loadContent(restDescriptionFourm.getText());

                try {
                    dp.initTable((ObservableList<Comment>) cc.readAllcomment2(Integer.parseInt(idP.getText())));
                    // image(dp);
                    int count = cc.count(Integer.parseInt(idP.getText()));
                    System.out.println("count  :  " + count);

                    if (count != 0) {
                        int sum = cc.sum(Integer.parseInt(idP.getText()));
                        System.out.println("sum  :  " + sum);
                        float moy = (float) sum / count;
                        System.out.println("rating:  " + moy);
                        dp.setRatingPost(moy);
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(ForumController.class.getName()).log(Level.SEVERE, null, ex);
                }
                tfTitlePost.getScene().setRoot(root);
            }
        } catch (IOException ex) {
            Logger.getLogger(DetailPostController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void Return(ActionEvent event
    ) {
        try {
            FXMLLoader loader
                    = new FXMLLoader(getClass().getResource("/esprit/recrutini/view/Forum.fxml"));
            Parent root = loader.load();

            ForumController df = loader.getController();
            df.setTfDescriptionForum(restDescriptionFourm.getText());
            df.setTfTitleForum(restTitleForum.getText());
            df.setTfIdForum(idF.getText());
            df.setTitleF(restTitleForum.getText());
            WebEngine WebE = df.viewDescription.getEngine();
            WebE.loadContent(restDescriptionFourm.getText());
            tfTitlePost.getScene().setRoot(root);

        } catch (IOException ex) {
            Logger.getLogger(ForumController.class.getName()).log(Level.SEVERE, null, ex);
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
        idP.setText("");
        tfTitlePost.setText("");
        tfDescriptionPost.setHtmlText("");
        WebEngine WebE = DescriptionPost.getEngine();
        WebE.loadContent("");
        noc.setText("");
        views.setText("");
        date.setText("");
    }

    @FXML
    private void Return(MouseEvent event) {

        try {
            FXMLLoader loader
                    = new FXMLLoader(getClass().getResource("/esprit/recrutini/view/Forum.fxml"));
            Parent root = loader.load();

            ForumController df = loader.getController();
            df.setTfDescriptionForum(restDescriptionFourm.getText());
            df.setTfTitleForum(restTitleForum.getText());
            df.setTfIdForum(idF.getText());
            df.setTitleF(restTitleForum.getText());
            WebEngine WebE = df.viewDescription.getEngine();
            WebE.loadContent(restDescriptionFourm.getText());
            tfTitlePost.getScene().setRoot(root);

        } catch (IOException ex) {
            Logger.getLogger(ForumController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void Profile(ActionEvent event) {
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
    private void Freelance(ActionEvent event) {
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
    private void Job(ActionEvent event) {
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
