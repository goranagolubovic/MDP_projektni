package controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.List;
import java.util.ResourceBundle;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import server.AZSMDPInterface;
import server.Report;

public class DownloadReportController implements Initializable{
	@FXML
	GridPane gridPaneReport;
	private Scene previousScene;
	private String previousTitle;
	private List<String> fileNames;
	public DownloadReportController(Scene scene, String title, List<String> fileNames) {
		previousScene=scene;
		previousTitle=title;
		this.fileNames=fileNames;
	}
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
	/*	String PATH = "resources";
		String DOWNLOAD_PATH="downlaoding";
			System.setProperty("java.security.policy", PATH + File.separator + "client_policyfile.txt");
			if (System.getSecurityManager() == null) {
				System.setSecurityManager(new SecurityManager());
			}
			AZSMDPInterface azsmdpServer;
			try {
				azsmdpServer = (AZSMDPInterface) Naming.lookup("rmi://127.0.0.1:1099/AZSMDPServer");
				List<String> fileNames = azsmdpServer.getReportNames();	
				initializeGridPaneReport(fileNames);
			} catch (MalformedURLException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			} catch (RemoteException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			} catch (NotBoundException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}*/
		initializeGridPaneReport(fileNames);
	}
	private void initializeGridPaneReport(List<String> fileNames) {
		  gridPaneReport.getRowConstraints().clear();
		  for(int i=0;i<fileNames.size();i++) {
			  gridPaneReport.getRowConstraints().add(new RowConstraints(50));
		  }
		  for(int i=0;i<fileNames.size();i++) {
			  Text text=new Text();
			  text.setText(fileNames.get(i));
			  gridPaneReport.add(text, 0, i);
		  }
		  for(int i=0;i<fileNames.size();i++) {
			  ImageView imageView;
			try {
				imageView = new ImageView(new Image(new FileInputStream("C:\\Users\\goran\\Desktop\\MDP-projektni\\ZSMDP\\images\\download.png")));
				  imageView.setOnMouseClicked(downloadFile(i));
				  imageView.setFitHeight(30);
				  imageView.setFitWidth(30);
				  gridPaneReport.add(imageView, 1, i);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		  }
		  
	}
	@FXML
	private EventHandler<? super MouseEvent> downloadFile(int i) {
		String PATH = "resources";
		String DOWNLOAD_PATH="downloading";
			System.setProperty("java.security.policy", PATH + File.separator + "client_policyfile.txt");
			if (System.getSecurityManager() == null) {
				System.setSecurityManager(new SecurityManager());
			}
			AZSMDPInterface azsmdpServer;
			try {
				azsmdpServer = (AZSMDPInterface) Naming.lookup("rmi://127.0.0.1:1099/AZSMDPServer");
				List<String> fileNames = azsmdpServer.getReportNames();
				Report report=azsmdpServer.downloadReport(fileNames.get(i));
				File downloadedFile=new File(DOWNLOAD_PATH+File.separator+report.getFileName());
				downloadedFile.createNewFile();
			} catch (MalformedURLException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			} catch (RemoteException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			} catch (NotBoundException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		return null;
	}
	@FXML
	private void goBack(MouseEvent e) {
		Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        stage.setScene(previousScene);
        stage.setTitle(previousTitle);
        stage.show();
	}
	
}
