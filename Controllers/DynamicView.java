/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;


import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;
import javafx.util.Duration;
import tray.animations.AnimationType;
import tray.notification.NotificationType;
import tray.notification.TrayNotification;

/**
 *
 * @author yessine darmoul
 */
public class DynamicView {
     private DynamicView() {
    }
    public static void Change_content(BorderPane borderpane,String resource) throws IOException
    {  Parent views=FXMLLoader.load(new DynamicView().getClass().getResource("/GUI/"+resource+".fxml"));
        borderpane.setCenter(views);
    }
    public static void Show_notif_success(String title,String msg)
    {
        TrayNotification notif=new TrayNotification();
        notif.setAnimationType(AnimationType.POPUP);
        notif.setTitle(title);
        notif.setMessage(msg);
        notif.setNotificationType(NotificationType.SUCCESS);
        notif.showAndDismiss(Duration.seconds(4));   
    }
    public static void Show_notif_error(String title,String msg)
    {
        TrayNotification notif=new TrayNotification();
        notif.setAnimationType(AnimationType.POPUP);
        notif.setTitle(title);
        notif.setMessage(msg);
        notif.setNotificationType(NotificationType.ERROR);
        notif.showAndDismiss(Duration.seconds(4));   
    }
    public static void Show_notif_warning(String title,String msg)
    {
        TrayNotification notif=new TrayNotification();
        notif.setAnimationType(AnimationType.POPUP);
        notif.setTitle(title);
        notif.setMessage(msg);
        notif.setNotificationType(NotificationType.WARNING);
        notif.showAndDismiss(Duration.seconds(4));   
    }
    public static void Show_notif_notice(String title,String msg)
    {
        TrayNotification notif=new TrayNotification();
        notif.setAnimationType(AnimationType.POPUP);
        notif.setTitle(title);
        notif.setMessage(msg);
        notif.setNotificationType(NotificationType.NOTICE);
        notif.showAndDismiss(Duration.seconds(4));   
    }
  
}
