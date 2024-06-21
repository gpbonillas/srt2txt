package com.str2txt;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 *
 * @author DELL
 */
public class STR2Texto extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(STR2Texto.class.getClassLoader().getResource("FXMLDocument.fxml"));

        Scene scene = new Scene(root);        
        stage.setScene(scene);

        Image icon = new Image(getClass().getResource("/img/dory.jpg").toExternalForm());
        stage.getIcons().add(icon);
        stage.setTitle("Subtítulos a Texto");
        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
