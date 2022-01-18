package controller;

import java.util.Optional;

import control.RestMethods;
import control.XMLSerializer;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class DeleteLineController {
	private Scene previousScene;
	private String previousTitle;
	@FXML
	TextField lineSignTextField;
	public DeleteLineController(Scene scene, String title) {
		previousScene=scene;
		previousTitle=title;
	}
	@FXML
	private void deleteLine(MouseEvent e) {
		if(!isFieldEmpty()) {
			Platform.runLater(()->{
				Alert alert=new Alert(AlertType.CONFIRMATION);
				alert.setContentText("Da li želite obrisati liniju "+lineSignTextField.getText()+"?");
				final Optional<ButtonType> result = alert.showAndWait();

				if (result.get() == ButtonType.OK) {
					if(!RestMethods.methodDelete("DELETE",lineSignTextField.getText())) {
							alert=new Alert(AlertType.INFORMATION);
							alert.setContentText("Unijeli ste nepostojeću oznaku linije.Pokušajte ponovo.");
							alert.showAndWait();
					}
					else {
						alert=new Alert(AlertType.INFORMATION);
						alert.setContentText("Uspješno ste obrisali liniju "+lineSignTextField.getText()+".");
						alert.showAndWait();
						
						Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
				        stage.setScene(previousScene);
				        stage.setTitle(previousTitle);
				        stage.show();
					}
				}
			});
				
		}
		else {
			Platform.runLater(()->{
				Alert alert=new Alert(AlertType.WARNING);
				alert.setContentText("Popunite polje pa pokušajte ponovo brisanje.");
				alert.showAndWait();
			});
		}
	}
	private boolean isFieldEmpty() {
		if(lineSignTextField.getText().isEmpty()) {
			return true;
		}
		return false;
	}

}
