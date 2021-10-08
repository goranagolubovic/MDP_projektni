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
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeLineJoin;
import model.Line;
import model.StationTime;

public class AddTimeController implements Initializable {
	@FXML
	GridPane map;
	private String id;
	private Scene previousScene;
	private Map<Line,CheckBox>cboxs=new HashMap<>();
	private Map<Line,TextField>textFields=new HashMap<>();
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
		
		/*RowConstraints rowConstraint = new RowConstraints();
		rowConstraint.setPercentHeight(height / lines.length);
		
		
		for(int i=0;i<lines.length;i++) {
			map.getRowConstraints().add(rowConstraint);
		}*/
		double cellHeight=height/lines.length;
		
		
		
		double cellWidth=width/map.getColumnConstraints().size();
		System.out.println(cellHeight);
		for(int i=0;i<5;i++) {
			for(int j=0;j<lines.length;j++) {
		/*Rectangle rectangle=new Rectangle(cellHeight,cellWidth);
		rectangle.setFill(Color.rgb(144, 107, 44));
		rectangle.setStroke(Color.rgb(244, 244, 244));
        rectangle.setStrokeWidth(2.0);
        rectangle.setStrokeLineJoin(StrokeLineJoin.MITER);*/
        
        BorderPane bp=new BorderPane();
        bp.setPrefHeight(cellHeight);
        bp.setPrefWidth(cellWidth);
        bp.setStyle("-fx-background-color: #906b2c; -fx-border-width: 2; -fx-border-color: #f4f4f4");
        TextArea ta=new TextArea();
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
        		TextField tf=new TextField();
        		tf.setPrefHeight(cellHeight);
        		tf.setPrefWidth(cellWidth);
        		textFields.put(lines[j],tf);
        		bp.setCenter(tf);
        	}
        	else {
        		CheckBox cb=new CheckBox();
        		cboxs.put(lines[j],cb);
        		bp.setTop(cb);
        	}
        }
       
       // bp.setBorder(Color.rgb(244, 244, 244));
		map.add(bp,i,j);
			}
		}
	}
	@FXML
	public void goBack(ActionEvent e) {
		
	}
	@FXML
	public void save(ActionEvent e) {
		Collection<TextField>valuesOfTextFileds=textFields.values();
		Collection<CheckBox>valuesOfCheckBoxes=cboxs.values();
		Line l=null;
		for(Map.Entry<Line,TextField> pair :textFields.entrySet()) {
			if(!"".equals(pair.getValue().getText())) {
			l=pair.getKey();
			List<StationTime>stationTimes=l.getStations().stream().filter(elem->elem.getStation().equals(id)).collect(Collectors.toList());
			StationTime st=stationTimes.get(0);
			st.setActualTimeOfPassing(pair.getValue().getText());
			st.setIsTrainArrived(1);
			List<StationTime>newListOfStation=l.getStations().stream().filter(elem->!elem.getStation().equals(id)).collect(Collectors.toList());
			newListOfStation.add(st);
			Line line=new Line(l.getSign(),newListOfStation);
			final Line li=line;
			 Platform.runLater(new Runnable() {
			      public void run() {
			          Alert alert = new Alert(Alert.AlertType.INFORMATION);
			          String s="";
			          Line []lines=RestMethods.method("GET", id);
			          for(Line line:lines) {
			        	  s+=line.toString()+" ; ";
			          }
			          alert.setContentText(s);
			          alert.showAndWait();
			      }
			    });
			RestMethods.methodPut("PUT",id,line);
			
			}
		}
			
	}


}
