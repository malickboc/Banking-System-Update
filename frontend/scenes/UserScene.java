package scenes;

import java.io.FileNotFoundException;
import java.time.format.DateTimeParseException;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import user.AddressDAO;
import user.AddressDAOImpl;
import user.State;
import user.UserController;
import user.UserDAO;
import user.UserDAOImpl;

/**
 * Used to represent the add user scene.
 * 
 * @author ripke1tj
 *
 */
public class UserScene {

	/**
	 * Generates the User scene for the Banking System.
	 * 
	 * @return The constructed user scene for the Banking System.
	 */
	public static Scene getScene() {

		BorderPane outer = new BorderPane();
		BorderPane inner = new BorderPane();
		outer.setCenter(inner);

		StackPane stackPane = new StackPane();
		Image img = new Image("logo.png");
		stackPane.getChildren().add(new ImageView(img));
		inner.setTop(stackPane);

		GridPane grid = new GridPane();
		grid.setAlignment(Pos.CENTER);
		grid.setStyle("-fx-background-color: white;");
		grid.setBorder(new Border(
				new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(2))));
		grid.setMinHeight(400);
		grid.setMinWidth(300);
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(25, 25, 25, 25));

		inner.setCenter(grid);

		Text driversLicenseText = new Text("Driver's License:");
		grid.add(driversLicenseText, 0, 0);
		TextField driversLicenseTextField = new TextField();
		grid.add(driversLicenseTextField, 1, 0);

		Text birthDateText = new Text("Birth Date (MM/DD/YYYY)");
		grid.add(birthDateText, 0, 1);
		TextField birthDateTextField = new TextField();
		grid.add(birthDateTextField, 1, 1);

		Text firstNameText = new Text("First name:");
		grid.add(firstNameText, 0, 2);
		TextField firstNameTextField = new TextField();
		grid.add(firstNameTextField, 1, 2);

		Text middleNameText = new Text("Middle name:");
		grid.add(middleNameText, 0, 3);
		TextField middleNameTextField = new TextField();
		grid.add(middleNameTextField, 1, 3);

		Text lastNameText = new Text("Last name:");
		grid.add(lastNameText, 0, 4);
		TextField lastNameTextField = new TextField();
		grid.add(lastNameTextField, 1, 4);

		Text addressText = new Text("Street One:");
		grid.add(addressText, 0, 5);
		TextField addressTextField = new TextField();
		grid.add(addressTextField, 1, 5);

		Text addressText2 = new Text("Street Two:");
		grid.add(addressText2, 0, 6);
		TextField addressTextField2 = new TextField();
		grid.add(addressTextField2, 1, 6);

		Text cityText = new Text("City:");
		grid.add(cityText, 0, 7);
		TextField cityTextField = new TextField();
		grid.add(cityTextField, 1, 7);

		Text stateText = new Text("State:");
		grid.add(stateText, 0, 8);
		TextField stateTextField = new TextField();
		grid.add(stateTextField, 1, 8);

		Text zipText = new Text("Zip:");
		grid.add(zipText, 0, 9);
		TextField zipTextField = new TextField();
		grid.add(zipTextField, 1, 9);

		HBox leftBox = new HBox();
		HBox.setHgrow(leftBox, Priority.ALWAYS);
		leftBox.setAlignment(Pos.CENTER_LEFT);
		Button cancelButton = new Button("Cancel");
		cancelButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				SceneController.changeScene(DefaultScene.getScene());
			}
		});
		leftBox.getChildren().add(cancelButton);

		HBox rightBox = new HBox();
		HBox.setHgrow(rightBox, Priority.ALWAYS);
		rightBox.setAlignment(Pos.CENTER_RIGHT);
		Button submitButton = new Button("Submit");
		submitButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {

				AddressDAO addressDAOConnection = new AddressDAOImpl();
				UserDAO userDAOConnection = new UserDAOImpl(addressDAOConnection);
				UserController userController = new UserController(userDAOConnection);
				try {
					userController.addUser(driversLicenseTextField.getText(), firstNameTextField.getText(),
							middleNameTextField.getText(), lastNameTextField.getText(), birthDateTextField.getText(),
							addressTextField.getText(), addressTextField2.getText(), cityTextField.getText(), stateTextField.getText(), zipTextField.getText());
				} catch (FileNotFoundException | IllegalStateException | DateTimeParseException ex) {
					Alert alert = new Alert(AlertType.ERROR);
					alert.setTitle("Error");
					alert.setHeaderText("Not so fast there...");
					alert.setContentText(ex.getMessage());
					alert.showAndWait();
				}

				SceneController.changeScene(DefaultScene.getScene());

			}
		});
		rightBox.getChildren().add(submitButton);

		HBox bottom = new HBox(leftBox, rightBox);
		bottom.setPadding(new Insets(10));

		outer.setBottom(bottom);

		Scene scene = new Scene(outer);
		scene.getStylesheets().add(LoginScene.class.getResource("Login.css").toExternalForm());
		return scene;

	}
}