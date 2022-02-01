package controllers;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.sun.corba.se.impl.protocol.giopmsgheaders.Message;

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
import javafx.scene.layout.Background;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeLineJoin;
import javafx.stage.Stage;
import model.Line;
import model.StationTime;

public class AddTimeController implements Initializable {
	@FXML
	GridPane map;
	private String id;
	private Scene previousScene;
	private Map<Line,TextArea>textFields=new HashMap<>();
	public AddTimeController() {
		
	}
	public AddTimeController(Scene previousScene,String id) {
		this.id=id;
		this.previousScene=previousScene;
	}
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		addTextFields();
	}

	private void addTextFields() {
		double height=map.getPrefHeight();
		double width=map.getPrefWidth();
		
		Line[] lines=RestMethods.method("GET", id);
		double cellHeight=height/lines.length;
		double cellWidth=width/map.getColumnConstraints().size();
		
		for(int i=0;i<4;i++) {
			for(int j=0;j<lines.length;j++) { 
        BorderPane bp=new BorderPane();
        bp.setPrefHeight(cellHeight);
        bp.setPrefWidth(cellWidth);
        bp.setStyle("-fx-background-color: #906b2c; -fx-border-width: 2; -fx-border-color: #f4f4f4");
        TextArea ta=new TextArea();
        ta.setStyle("-fx-background-color: #906b2c; -fx-border-width: 2; -fx-border-color: #f4f4f4");
        if(j<3) {
        	if(i==0) {
        	ta.setText(lines[j].getSign());
        	ta.setEditable(false);
        	bp.setCenter(ta);
        	}
        	else if(i==1) {
        	ta.setText(id);
        	ta.setEditable(false);
        	bp.setCenter(ta);
        	}
        	else if(i==2) {
        		List<StationTime> listOfStationTime=lines[j].getStations().stream().filter(elem->elem.getStation().equals(id)).collect(Collectors.toList());
        		listOfStationTime.stream().map(elem->elem.getTime()).forEach(elem->ta.setText(elem));
        		ta.setEditable(false);
        		bp.setCenter(ta);
        	}
        	else if(i==3) {
        		List<StationTime> listOfStationTime=lines[j].getStations().stream().filter(elem->elem.getStation().equals(id)).collect(Collectors.toList());
        		listOfStationTime.stream().map(elem->elem.getActualTimeOfPassing()).forEach(elem->ta.setText(elem));
        		textFields.put(lines[j],ta);
        		bp.setCenter(ta);
        	}
        }
		map.add(bp,i,j);
			}
		}
	}
	@FXML
	public void save(ActionEvent e) {
		Line l=null;
		for(Map.Entry<Line,TextArea> pair :textFields.entrySet()) {
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
		          alert.setContentText("Sačuvano!");
			      alert.showAndWait();
		      }
		    });
	}
	@FXML
	public void goBack(ActionEvent e) {
		Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        stage.setScene(previousScene);
        stage.show();
	}


}
