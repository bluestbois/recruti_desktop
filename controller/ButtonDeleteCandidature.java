/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package esprit.recrutini.controller;

import com.sun.prism.impl.Disposer;
import esprit.recrutini.entities.Candidature;
import esprit.recrutini.services.ServiceCandidature;
import esprit.recrutini.services.CandidateSession;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import esprit.recrutini.services.CandidateSession;
import static esprit.recrutini.services.CandidateSession.currentCandidate;

/**
 *
 * @author yessine darmoul
 */
public class ButtonDeleteCandidature extends TableCell<Disposer.Record, Boolean> {
   final Button cellButton = new Button("Delete");
          ServiceCandidature serv =new ServiceCandidature(); 
        ButtonDeleteCandidature(){
        
        	//Action when the button is pressed
            cellButton.setOnAction((ActionEvent t) -> {
                try {
                    // get Selected Item
                    Candidature idcourant = (Candidature) ButtonDeleteCandidature.this.getTableView().getItems().get(ButtonDeleteCandidature.this.getIndex());
                    //remove selected item from the table list
                    ObservableList<Candidature> list= FXCollections.observableArrayList();
                    try {
                        for (Candidature p:serv.readAll())
                        {
                            list.add(p);
                            
                        }
                    } catch (SQLException ex) {
                        Logger.getLogger(ButtonDeleteCandidature.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    System.out.println(idcourant);
                    list.remove(idcourant);
                    try {
                        serv.delete(idcourant.getId());
                    } catch (SQLException ex) {
                        Logger.getLogger(ButtonDeleteCandidature.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    System.out.println(list);
                    FXMLLoader.load(getClass().getResource("/esprit/recrutini/view/UserInterface.fxml"));
                } catch (IOException ex) {
                    Logger.getLogger(ButtonDeleteCandidature.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
        }
        

        //Display button if the row is not empty
        @Override
        protected void updateItem(Boolean t, boolean empty) {
            super.updateItem(t, empty);
            if(!empty){
               // setGraphic(cellButton2);
                setGraphic(cellButton);
            }
        }  
       
        
        
        
        
}
