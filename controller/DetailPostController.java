/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package esprit.recrutini.controller;

import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.SQLException;
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
import javafx.scene.control.Spinner;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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
import esprit.recrutini.services.PostCRUD;
import esprit.recrutini.services.ServiceRecruiter;
import esprit.recrutini.services.Session;
import esprit.recrutini.tools.DataSource;
import static java.sql.Types.NULL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.WritableDoubleValue;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.TableCell;
import javafx.scene.web.HTMLEditor;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.util.Callback;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;
import org.controlsfx.control.Rating;

/**
 * FXML Controller class
 *
 * @author ASUS
 */
public class DetailPostController implements Initializable {

    @FXML
    private Label TitleP;
    @FXML
    private Label DescriptionP;
    @FXML
    private TableView<Comment> tableComment;
    @FXML
    private TableColumn<Comment, String> col_content;
    @FXML
    private TableColumn<Comment, Integer> col_rating;
    @FXML
    private TableColumn<Comment, String> col_date;

    private ComboBox<Post> ComboP;
    PostCRUD pc = new PostCRUD();
    CommentCRUD cc = new CommentCRUD();
    ObservableList<Comment> oblistcomment = FXCollections.observableArrayList();

    @FXML
    private TextArea contentC;

    @FXML
    private Button btnajouter;
    @FXML
    private Button btnupdate;
    @FXML
    private Button btnDelete;
    @FXML
    private Label idC;
    Stage stage;

    @FXML
    private Label idPc;
    @FXML
    private Label idForum;
    @FXML
    private Label titleForum;
    @FXML
    private Label descriptionFourm;
    @FXML
    private Button profile;
    @FXML
    private ImageView btnprofile;
    @FXML
    private Label userlabel;
    @FXML
    private ImageView logout;
    @FXML
    private Label TF;
    @FXML
    private Label TP;
    @FXML
    private Button btnClear;
    @FXML
    private Rating RatingComment;
    @FXML
    private Rating RatingPost;
    @FXML
    public WebView DescriptionForum;
    @FXML
    public WebView DescriptionPost;
    Session S = Session.getInstance();
    private String mail = "";
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
        idC.setVisible(false);
        idPc.setVisible(false);
        idForum.setVisible(false);
        descriptionFourm.setVisible(false);
        DescriptionP.setVisible(false);
        // initTable();
    }

    private void clearAll() {
        idC.setText("");
        contentC.setText("");
        RatingComment.setRating(0);

    }

    /**
     *********Rating*********
     */
    public void setRatingPost(float RatingPost) {
        this.RatingPost.setRating(RatingPost);
    }

  
    public class RatingItem {

        private final DoubleProperty rating;

        public RatingItem(int rating) {
            this.rating = new SimpleDoubleProperty(rating);
        }

        public final double getRating() {
            return this.rating.get();
        }

        public final void setRating(double value) {
            this.rating.set(value);
        }

        public final DoubleProperty ratingProperty() {
            return this.rating;
        }

    }

    /**
     * ******forum***********
     */
    public void setIdForum(String idForum) {
        this.idForum.setText(idForum);
    }

    public void setTitleForum(String titleForum) {
        this.titleForum.setText(titleForum);
    }

    public void setDescriptionFourm(String descriptionFourm) {
        this.descriptionFourm.setText(descriptionFourm);
    }

    public void setTF(String TF) {
        this.TF.setText(TF);
    }

    /**
     * ******post***********
     */
    public void setDescriptionP(String DescriptionP) {
        this.DescriptionP.setText(DescriptionP);
    }

    public void setTitleP(String TitleP) {
        this.TitleP.setText(TitleP);
    }

    public void setIdPc(String idPc) {
        this.idPc.setText(idPc);
    }

    public void setTP(String TP) {
        this.TP.setText(TP);
    }

    public void initTable(ObservableList<Comment> comments) {
        col_content.setCellValueFactory(new PropertyValueFactory<>("content"));
        col_rating.setCellValueFactory(new PropertyValueFactory<>("rating"));
        col_date.setCellValueFactory(new PropertyValueFactory<>("date"));
        tableComment.setItems(comments);

    }

    public void initTable(int i) {
        try {
            oblistcomment = (ObservableList<Comment>) cc.readAllcomment2(i);
            col_content.setCellValueFactory(new PropertyValueFactory<>("content"));
            col_rating.setCellValueFactory(new PropertyValueFactory<>("rating"));
            col_date.setCellValueFactory(new PropertyValueFactory<>("date"));
            tableComment.setItems(oblistcomment);

        } catch (SQLException ex) {
            Logger.getLogger(DetailPostController.class
                    .getName()).log(Level.SEVERE, null, ex);
        }

    }

    @FXML
    private void ajouter(ActionEvent event) throws SQLException {
        CommentCRUD cc = new CommentCRUD();
        Comment c = new Comment();
        String content = CurseFilterService.cleanText(contentC.getText());
        c.setContent(content);
        c.setRating((int) RatingComment.getRating());
        Integer i = Integer.valueOf(idPc.getText());
        c.setIdP(i);
        cc.updatePost(i);
        if (currentCandidate.getId() != NULL) {
            System.out.println("idCandidate" + currentCandidate.getId());
            int idCandidate = currentCandidate.getId();
            c.setIdC(idCandidate);
            cc.addCommentC(c, i);
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
                c.setIdR(idRecruteur);
                cc.addCommentR(c, i);
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
        rating();

    }

    private void rating() {
        try {
            int count = cc.count(Integer.parseInt(idPc.getText()));
            System.out.println("count  :  " + count);
            int sum = cc.sum(Integer.parseInt(idPc.getText()));
            System.out.println("sum  :  " + sum);
            float moy = (float) sum / count;
            System.out.println("rating:  " + moy);
            RatingPost.setRating(moy);

        } catch (SQLException ex) {
            Logger.getLogger(DetailPostController.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void select(MouseEvent event) {
        Comment C = tableComment.getSelectionModel().getSelectedItem();
        contentC.setText(C.getContent());
        Integer r = C.getRating();

        Integer id = C.getId();
        RatingComment.setRating(r);

        idC.setText(id.toString());

    }

    @FXML
    private void update(ActionEvent event) throws SQLException {
        Comment C = tableComment.getSelectionModel().getSelectedItem();
        Comment CC = cc.findByIdC(C.getId());
        Comment CR = cc.findByIdR(C.getId());
        if (currentCandidate.getId() == CC.getIdC()) {
            if (C == null) {
                JOptionPane.showMessageDialog(null, "There is nothing selected !");
            } else {
                Integer i = Integer.valueOf(idPc.getText());

                cc.update(C.getId(), CurseFilterService.cleanText(contentC.getText()), (int) RatingComment.getRating());
                initTable(i);
                Image img = new Image("/esprit/recrutini/img/update.png");
                Notifications notifAdd = Notifications.create()
                        .title("Update")
                        .text("Update sucees")
                        .graphic(new ImageView(img))
                        .hideAfter(Duration.seconds(5))
                        .position(Pos.TOP_RIGHT);
                notifAdd.show();
                clearAll();
                rating();

            }
        } else {

            JOptionPane.showMessageDialog(null, "you can't Update the Comment");
        }
    }

    @FXML
    private void delete(ActionEvent event) throws SQLException {
        Comment dis = tableComment.getSelectionModel().getSelectedItem();

        Comment CC = cc.findByIdC(dis.getId());
        Comment CR = cc.findByIdR(dis.getId());

        if (currentCandidate.getId() == CC.getIdC()) {
            int retour = JOptionPane.showConfirmDialog(null, "OK - Annuler",
                    "titre",
                    JOptionPane.OK_CANCEL_OPTION);
            System.out.println("retour :" + retour);
            if (retour == 0) {
                if (!dis.equals(null)) {
                    cc.delete(dis.getId());
                    Integer i = Integer.valueOf(idPc.getText());
                    cc.updatePostnocDelete(i);
                    initTable(i);
                    Image img = new Image("/esprit/recrutini/img/remove.png");
                    Notifications notifAdd = Notifications.create()
                            .title("Remove")
                            .text("delete sucees")
                            .graphic(new ImageView(img))
                            .hideAfter(Duration.seconds(5))
                            .position(Pos.TOP_RIGHT);
                    notifAdd.show();
                    clearAll();
                    rating();
                }
            }
        } else {

            JOptionPane.showMessageDialog(null, "you can't delete the Comment");
        }
    }

    @FXML
    private void Return(MouseEvent event) {
        
        try {
            FXMLLoader loader
                    = new FXMLLoader(getClass().getResource("/esprit/recrutini/view/DetailForum.fxml"));
            Parent root = loader.load();

            DetailForumController dc = loader.getController();
            //ForumController fc = loader.getController();
            dc.setTfDescriptionPost(DescriptionP.getText());
            dc.setTfTitlePost(TitleP.getText());
            dc.setIdP(idPc.getText());
            dc.setRestIdFourm(idForum.getText());
            dc.setRestTitleForum(titleForum.getText());
            dc.setRestDescriptionFourm(descriptionFourm.getText());
            dc.setTforum(titleForum.getText());
            dc.settPost(">" + TitleP.getText());

            dc.setViews(Integer.toString((pc.findById(Integer.parseInt(idPc.getText()))).getViews()));
            dc.setNoc(Integer.toString((pc.findById(Integer.parseInt(idPc.getText()))).getNoc()));

            DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
            dc.setDate(df.format((pc.findById(Integer.parseInt(idPc.getText()))).getDate()));

            WebEngine WebP = dc.DescriptionPost.getEngine();
            WebP.loadContent(DescriptionP.getText());

            WebEngine WebF = dc.DescriptionFourm.getEngine();
            WebF.loadContent(descriptionFourm.getText());

            try {
                dc.initTable((ObservableList<Post>) pc.readAllpost2(Integer.parseInt(idForum.getText())));
                // dc.initTable((ObservableList<Post>) pc.readAllpost2(Integer.parseInt(idPc.getText())));

            } catch (SQLException ex) {
                Logger.getLogger(ForumController.class
                        .getName()).log(Level.SEVERE, null, ex);
            }
            TitleP.getScene().setRoot(root);

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
        idC.setText("");
        contentC.setText("");
        RatingComment.setRating(0);
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
