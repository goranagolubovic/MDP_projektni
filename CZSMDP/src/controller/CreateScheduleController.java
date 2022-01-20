package controller;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

//import com.sun.corba.se.impl.protocol.giopmsgheaders.Message;

import control.RestMethods;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.Background;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeLineJoin;
import javafx.stage.Stage;
import model.Line;
import model.StationTime;

public class CreateScheduleController implements Initializable {
	@FXML
	GridPane map;
	private String previousTitle;
	private Scene previousScene;
	private List<TextField>textFields=new ArrayList<>();
	public CreateScheduleController() {
		
	}
	public CreateScheduleController(Scene scene,String title) {
		previousTitle=title;
		previousScene=scene;
	}
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		addTextFields();
	}

	private void addTextFields() {
		double height=map.getPrefHeight();
		double width=map.getPrefWidth();
		
		//Line[] lines=RestMethods.method("GET", id);
		double cellHeight=height/10;
		double cellWidth=width/map.getColumnConstraints().size();
	        map.getColumnConstraints().clear();
	        map.getRowConstraints().clear();
	        for (int j = 0; j < 3; j++) {
	        	map.getColumnConstraints().add(new ColumnConstraints(cellWidth));
	        }
	        for (int i = 0; i < 10; i++) {
	            map.getRowConstraints().add(new RowConstraints(cellHeight));
	        }
		
		for(int i=0;i<10;i++) {
			for(int j=0;j<3;j++) { 
        /*BorderPane bp=new BorderPane();
        bp.setPrefHeight(cellHeight);
        bp.setPrefWidth(cellWidth);
        bp.setStyle("-fx-background-color: #906b2c; -fx-border-width: 2; -fx-border-color: #f4f4f4");
        */
        TextField tf=new TextField();
        tf.setMinHeight(cellHeight);
        tf.setPrefWidth(cellWidth);
        textFields.add(tf);
        //ta.setStyle("-fx-background-color: #906b2c; -fx-border-width: 2; -fx-border-color: #f4f4f4");
        //bp.setCenter(tf);
		map.add(tf,j,i);
			}
		}
	}
	@FXML
	public void save(ActionEvent e) {
		List<Line>lines=new ArrayList<>();
		for (int i = 0; i < textFields.size(); i+=3) {
			if (isSignOfLinePresent(i)) {
					String signOfLine=textFields.get(i).getText();
					StationTime st=new StationTime(textFields.get(i+1).getText(),textFields.get(i+2).getText(),"",0);
					Line line=new Line(signOfLine,findStationTimesForLine(signOfLine));
					lines.add(line);
			}
			}
		for(Line line:lines) {
			if(RestMethods.createSchedule("POST",line)) {
				Platform.runLater(()->{
					Alert alert=new Alert(AlertType.INFORMATION);
					alert.setContentText("Uspješno sačuvano.");
					alert.showAndWait();
				});
			}
			else {
				Platform.runLater(()->{
					Alert alert=new Alert(AlertType.INFORMATION);
					alert.setContentText("Neuspješno sačuvano.");
					alert.showAndWait();
				});
			}
		}
	}
		/*for(Map.Entry<Line,TextArea> pair :textFields.entrySet()) {
			l=pair.getKey();
			List<StationTime>stationTimes=l.getStations().stream().filter(elem->elem.getStation().equals(id)).collect(Collectors.toList());
			StationTime st=stationTimes.get(0);
			st.setActualTimeOfPassing(pair.getValue().getText());
			if(!"".equals(pair.getValue().getText())) {
			st.setIsTrainArrived(1);
			}
			else {
				st.setIsTrainArrived(1);
			}
			List<StationTime>newListOfStation=l.getStations().stream().filter(elem->!elem.getStation().equals(id)).collect(Collectors.toList());
			newListOfStation.add(st);
			Line line=new Line(l.getSign(),newListOfStation);
			RestMethods.methodPut("PUT",id,line);
			}
		 Platform.runLater(new Runnable() {
		      public void run() {
		          Alert alert = new Alert(Alert.AlertType.INFORMATION);
		          alert.setTitle("");
		          VBox a = new VBox();
		          	a.setStyle("-fx-background-color:  #906b2c");
		            a.setPrefHeight(100);
		            a.setPrefWidth(100);
		             Label label = new Label("Sačuvano!");
		             label.setStyle("-fx-text-fill:  #ffffff");
		             a.getChildren().add(label);
		             alert.getDialogPane().setContent(a);
			         alert.showAndWait();
		      }
		    });
	}*/
	private List<StationTime> findStationTimesForLine(String signOfLine) {
		List<StationTime> stationsTime=new ArrayList<>();
		for(int i=0;i<textFields.size()-2;i++) {
			if(signOfLine.equals(textFields.get(i).getText())) {
				StationTime stationTime = new StationTime(textFields.get(i+1).getText(),textFields.get(i+2).getText(),"",0);
				stationsTime.add(stationTime);
			}
		}
		return stationsTime;
	}
	private boolean isSignOfLinePresent(int i) {
		Optional<TextField>tf=textFields.stream().skip(i).findFirst();
		if(tf.isPresent()) {
			if(tf.get().getText()!="") {
				return true;
			}
		}
		return false;
	}
	@FXML
	public void goBack(MouseEvent e) {
		Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        stage.setScene(previousScene);
        stage.setTitle(previousTitle);
        stage.show();
	}


}
