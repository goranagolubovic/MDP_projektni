package main;


	import javafx.application.Application;
	import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
	import javafx.scene.layout.VBox;
	import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;

	public class Main extends Application{

	    public static void main(String[] args) {
	        launch(args);
	    }

	    @Override
	    public void start(Stage primaryStage) throws Exception {
	       
	        Parent root = null;
	        FXMLLoader loader = new FXMLLoader(getClass().getResource(File.separator+"resources"+File.separator+"fxmls"+File.separator+"LoginWindow.fxml"));
	        try {
	            root = loader.load();
	        } catch (IOException e) {
	           
	        }
	       
	        VBox vbox = loader.<VBox>load();

	        Scene scene = new Scene(vbox);
	        primaryStage.setScene(scene);
	        primaryStage.show();
	    }
	}
