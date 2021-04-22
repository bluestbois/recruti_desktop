/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import CONNECTION.DataSource;
import Entity.Candidate;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.sql.PreparedStatement;
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
import javafx.fxml.Initializable;
import javafx.print.PageLayout;
import javafx.print.PageOrientation;
import javafx.print.Paper;
import javafx.print.Printer;
import javafx.print.PrinterAttributes;
import javafx.print.PrinterJob;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.transform.Scale;
import javafx.stage.Stage;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;

/**
 * FXML Controller class
 *
 * @author yessine darmoul
 */
public class UserInterfaceController implements Initializable {

    @FXML
    private TableView<Candidate> table_candidate;
    @FXML
    private TableColumn<Candidate, Integer> id;
    @FXML
    private TableColumn<Candidate, String> firstname;
     @FXML
    private TableColumn<Candidate, String> lastname;
    @FXML
     private TableColumn<Candidate, String> email;
    @FXML
    private TableColumn<Candidate, String> gender;
    @FXML
    private TableColumn<Candidate, String> nationality;
    @FXML
    private TableColumn<Candidate, String> phonenumber;
    @FXML
    private TableColumn<Candidate, String> address;
    @FXML
    private TableColumn<Candidate, String> loe;
    @FXML
    private TableColumn<Candidate, Integer> experience;

 private PreparedStatement pst;
    private Stage stage;
    private Scene scene;
    private ResultSet rs;
    private DataSource conn;
        private ObservableList<Candidate> data;
    @FXML
    private Button printbtn;
    @FXML
    private Button excel;
  

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            InitUser();
        } catch (SQLException ex) {
            Logger.getLogger(UserInterfaceController.class.getName()).log(Level.SEVERE, null, ex);
        }
        show();
    }    
   
    public void InitUser() throws SQLException {
        conn = DataSource.getInstance();     
        try {
            data = FXCollections.observableArrayList();
            
            String sql = "SELECT id,first_name,last_name,email,gender,nationality,phone_number,address,loe,experience FROM Candidate";
            rs = conn.getCnx().createStatement().executeQuery(sql);

            while (rs.next()) {
                conn = DataSource.getInstance();
               
                data.add(new Candidate(rs.getInt("id"),rs.getString("first_name"),rs.getString("last_name"),rs.getString("email"),rs.getString("gender"),rs.getString("nationality"),rs.getString("phone_number"),rs.getString("address"),rs.getString("loe"),rs.getString("experience")));
               
       
            }
                                           
                                           
        } catch (SQLException ex) {
            System.err.println("Error" + ex);
            System.out.println("erreur!!!!");
        }
        
        
       
    }
public void show(){
    
        id.setCellValueFactory(new PropertyValueFactory<Candidate,Integer>("id"));
        firstname.setCellValueFactory(new PropertyValueFactory<Candidate,String>("firstname"));
        lastname.setCellValueFactory(new PropertyValueFactory<Candidate,String>("lastname"));
        email.setCellValueFactory(new PropertyValueFactory<Candidate,String>("email"));
        gender.setCellValueFactory(new PropertyValueFactory<Candidate,String>("gender"));
        nationality.setCellValueFactory(new PropertyValueFactory<Candidate,String>("nationality"));
        phonenumber.setCellValueFactory(new PropertyValueFactory<Candidate,String>("phonenumber"));
        address.setCellValueFactory(new PropertyValueFactory<Candidate,String>("address"));
        loe.setCellValueFactory(new PropertyValueFactory<Candidate,String>("loe"));
        experience.setCellValueFactory(new PropertyValueFactory<Candidate,Integer>("experience"));

    //   Table_stock.setItems(null);
       table_candidate.setItems(data); 
     
    
    
}

    @FXML
  private void Imprimer(ActionEvent event) throws NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
         printNode(table_candidate);
    }
    public static void printNode(final Node node) throws NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
    Printer printer = Printer.getDefaultPrinter();
    PageLayout pageLayout
        = printer.createPageLayout(Paper.A4, PageOrientation.LANDSCAPE, Printer.MarginType.DEFAULT);
    PrinterAttributes attr = printer.getPrinterAttributes();
    PrinterJob job = PrinterJob.createPrinterJob();
    double scaleX= pageLayout.getPrintableWidth() / node.getBoundsInParent().getWidth();
    double scaleY= pageLayout.getPrintableHeight() / node.getBoundsInParent().getHeight();
    Scale scale = new Scale(scaleX, scaleY);
    node.getTransforms().add(scale);

    if (job != null && job.showPrintDialog(node.getScene().getWindow())) {
      boolean success = job.printPage(pageLayout, node);
      if (success) {
        job.endJob();

      }
    }
    node.getTransforms().remove(scale);
  }

    @FXML
  

 private void ExportExcel(ActionEvent event) throws FileNotFoundException, IOException {
         try {
           
            conn = DataSource.getInstance();
            Statement stmt1 = conn.getCnx().createStatement();
            //Variable counter for keeping track of number of rows inserted.  
            int counter = 1;
            FileOutputStream fileOut = null;
           
            String sql = "Select * from Candidate";

            //Creation of New Work Book in Excel and sheet.  
            HSSFWorkbook hwb = new HSSFWorkbook();
            HSSFSheet sheet = hwb.createSheet("new sheet");
            //Creating Headings in Excel sheet.  
            HSSFRow rowhead = sheet.createRow((short) 0);
            rowhead.createCell((short) 1).setCellValue("id");//Row Name1  
            rowhead.createCell((short) 2).setCellValue("firstname");//Row Name2  
            rowhead.createCell((short) 3).setCellValue("lastname");//Row Name3  
            rowhead.createCell((short) 5).setCellValue("email");//Row Name4
            rowhead.createCell((short) 8).setCellValue("gender");//Row Name5 
            rowhead.createCell((short) 9).setCellValue("nationality");//Row Name5
            rowhead.createCell((short) 10).setCellValue("phonenumber");
            rowhead.createCell((short) 11).setCellValue("address");
            rowhead.createCell((short) 12).setCellValue("loe");
            rowhead.createCell((short) 13).setCellValue("experience");
            

            ResultSet rs = stmt1.executeQuery(sql);
            while (rs.next()) {
                //Insertion in corresponding row  
                HSSFRow row = sheet.createRow((int) counter);
                /* Activity, Username, TIME_OF_EVENT are row names  
          * corresponding to table  
          * in related Database. */
                CellStyle dateCellStyle = hwb.createCellStyle();
                CellStyle dateCellStyle1 = hwb.createCellStyle();
                CreationHelper createHelper = hwb.getCreationHelper();
                //Cell dateOfBirthCell = row.createCell(2);
//            dateOfBirthCell.setCellValue(employee.getDateOfBirth());
//            dateOfBirthCell.setCellStyle(dateCellStyle);
//                dateCellStyle.setDataFormat(createHelper.createDataFormat().getFormat("dd-MM-yyyy"));
//                dateCellStyle1.setDataFormat(createHelper.createDataFormat().getFormat("dd-MM-yyyy"));

                row.createCell((short) 1).setCellValue(rs.getInt("id"));
                row.createCell((short) 2).setCellValue(rs.getString("first_name"));
                row.createCell((short) 3).setCellValue(rs.getString("last_name"));
                row.createCell((short) 5).setCellValue(rs.getString("email"));
                row.createCell((short) 8).setCellValue(rs.getString("gender"));
                row.createCell((short) 9).setCellValue(rs.getString("nationality"));
                row.createCell((short) 10).setCellValue(rs.getString("phone_number"));
                row.createCell((short) 11).setCellValue(rs.getString("address"));
                row.createCell((short) 12).setCellValue(rs.getString("loe"));
                row.createCell((short) 13).setCellValue(rs.getInt("experience"));
                
                
                

//                row.createCell((short) 3).setCellStyle(dateCellStyle);
//                row.createCell((short) 3).setCellValue(rs.getDate("date"));
            
//                Cell dateS = row.createCell((short) 4);
//                dateS.setCellValue(rs.getDate("dates"));
//                dateS.setCellStyle(dateCellStyle);
//
//
//                Cell dateE = row.createCell((short) 5);
//                dateE.setCellValue(rs.getDate("datee"));
//                dateE.setCellStyle(dateCellStyle1);

                sheet.autoSizeColumn(1);
                sheet.autoSizeColumn(2);
                sheet.setColumnWidth(3, 256 * 25);

                sheet.setZoom(150);
                sheet.autoSizeColumn(1);
                sheet.autoSizeColumn(2);
                sheet.setColumnWidth(3, 256 * 25);

                sheet.setZoom(150);

                counter++;
                try {
                    //For performing write to Excel file  
                    fileOut = new FileOutputStream("Candidates.xls");
                    hwb.write(fileOut);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            //Close all the parameters once writing to excel is compelte.  
            fileOut.close();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("INFORMATION DIALOG");
            alert.setHeaderText(null);
            alert.setContentText("All courses Has Been Exported in Excel Sheet");
            alert.showAndWait();
            rs.close();
            stmt1.close();

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
    }
}
   