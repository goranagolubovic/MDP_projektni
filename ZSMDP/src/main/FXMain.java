package main;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

import controllers.LoginController;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import properties.ConfigProperties;
import service.LoginService;

public class FXMain extends Application {
	private static Logger log = Logger.getLogger(FXMain.class.getName());
    @FXML
    public static void main(String[] args)  {
        launch(args);
    }

    private static ConfigProperties configProperties=new ConfigProperties();
    @Override
    public void start(Stage primaryStage) throws IOException {
    
    	//Logger.getLogger(FXMain.class.getName()).addHandler(handler);
        Parent root = null;
        FXMLLoader loader = new FXMLLoader(getClass().getResource(configProperties.getProperties().getProperty("LOGIN")));
        LoginController loginController=new LoginController();
        loader.setController(loginController);
        try {
            root = loader.load();
            
        } catch (IOException e) {
        	e.printStackTrace();
        	Logger.getLogger(FXMain.class.getName()).log(Level.WARNING, e.fillInStackTrace().toString());
        }
        Scene scene1 = new Scene(root);
        primaryStage.setScene(scene1);
        primaryStage.setResizable(false);
        primaryStage.setTitle("Prijava korisnika");
        primaryStage.show();
    }

}
