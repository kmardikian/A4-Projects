/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ptktdisplay;

import com.a4.utils.ConnectAs400;
//import java.awt.Image;
import java.util.Map;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import ptktData.CustomException;
import ptktData.AppParms;

/**
 *
 * @author Khatchik
 */
public class Main extends Application {
    private Logger logger;

    @Override
    public void start(Stage stage) throws Exception {
        //System.out.println(System.getProperty("user.home"));

        Map<String, String> namedParms;
        namedParms = getParameters().getNamed();
        ConnectAs400.setJtopen();
        ConnectAs400.setConParms(namedParms.get("systemName"), namedParms.get("user"),
                namedParms.get("password"), namedParms.get("lib"));
        try {
            ConnectAs400.conAs400();
        } catch (Exception ex) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Error Connecting to AS400\n" + ex.getLocalizedMessage());
            alert.setTitle("Error Connecting to AS400");
            alert.showAndWait();
            ex.printStackTrace();
            return;
        }
        ConnectAs400.As400JobInfo as400Info =  ConnectAs400.getAs400JobInfo();
               
        System.out.println("AS400Job started:" + as400Info.getJobnNum() 
                + "/" +as400Info.getJobUser()
                + "/" + as400Info.getJobName() + "/" );
        try {
            AppParms pstatList = new AppParms(namedParms.get("systemName"),
                    namedParms.get("user"), namedParms.get("password"), namedParms.get("lib"),
                    namedParms.get("wrh"));
        } catch (CustomException ex) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Error retrieving Pick ticket Status list\n" + ex.getLocalizedMessage());
            alert.setTitle("Error retrirving Warehouse list");
            alert.showAndWait();

        }

        Parent root = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));
         

        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.setTitle("Pick Ticket Dashboard Display");
        stage.getIcons().add(new Image(Main.class.getResourceAsStream("/res/wrh2.png")));
        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
