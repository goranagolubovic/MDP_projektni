package main;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class FXMain extends Application {
    @FXML
    public static void main(String[] args)  {
        launch(args);
    }

    
    @Override
    public void start(Stage primaryStage) throws IOException {
        Parent root = null;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/czsmdp.fxml"));
        try {
            root = loader.load();
            
        } catch (IOException e) {
        	e.printStackTrace();
            //Logger.getLogger(FXMain.class.getName()).addHandler(MainPageController.handler);
            //Logger.getLogger(FXMain.class.getName()).log(Level.WARNING, e.fillInStackTrace().toString());
        }
        Scene scene1 = new Scene(root);
        primaryStage.setScene(scene1);
        primaryStage.setResizable(false);
        primaryStage.setTitle("CZSMDP");
        primaryStage.show();
    }

}
