package main;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

import controller.CZSMDPController;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import properties.ConfigProperties;

public class FXMain extends Application {
	private static ConfigProperties configProperties=new ConfigProperties();
    @FXML
    public static void main(String[] args)  {
        launch(args);
    }
 
    @Override
    public void start(Stage primaryStage) throws IOException {
    	
        Parent root = null;
        FXMLLoader loader = new FXMLLoader(getClass().getResource(configProperties.getProperties().getProperty("CZSMDP")));
        try {
            root = loader.load();
            
        } catch (IOException e) {
        	Logger.getLogger(FXMain.class.getName()).log(Level.WARNING, e.fillInStackTrace().toString());
        }
        Scene scene1 = new Scene(root);
        primaryStage.setScene(scene1);
        primaryStage.setResizable(false);
        primaryStage.setTitle("CZSMDP");
        primaryStage.show();
    }

}
