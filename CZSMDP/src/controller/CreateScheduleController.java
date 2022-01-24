package controller;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import control.RestMethods;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
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
        TextField tf=new TextField();
        tf.setMinHeight(cellHeight);
        tf.setPrefWidth(cellWidth);
        textFields.add(tf);
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
