package scenes;
import java.io.FileNotFoundException;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
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
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import user.AddressDAO;
import user.AddressDAOImpl;
import user.User;
import user.UserController;
import user.UserDAO;
import user.UserDAOImpl;

public class DefaultScene {

	/**
	 * Generates the Default scene for the Banking System, which is the home page.
	 * 
	 * @return The constructed default scene for the Banking System.
	 */
	public static Scene getScene() {

		GridPane grid = new GridPane();
		grid.setAlignment(Pos.CENTER);
		grid.setStyle("-fx-background-color: white;");
		grid.setMaxWidth(300);
		grid.setMaxHeight(300);
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(25, 25, 25, 25));

		Image img = new Image("logo.png");
		grid.add(new ImageView(img), 0, 0);

		Text instructions = new Text("Enter a drivers license number");
		grid.add(instructions, 0, 1);

		TextField driversLicenseField = new TextField();
		grid.add(driversLicenseField, 0, 2);

		Button submitButton = new Button("Submit");
		HBox hbBtn = new HBox(10);
		hbBtn.setAlignment(Pos.BOTTOM_CENTER);
		hbBtn.getChildren().add(submitButton);
		grid.add(hbBtn, 0, 3);

		submitButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent e) {
				AddressDAO addressDAOConnection = new AddressDAOImpl();
				UserDAO userDAOConnection = new UserDAOImpl(addressDAOConnection);
				UserController userController = new UserController(userDAOConnection);
				try {
					User user = userController.getUser(driversLicenseField.getText());
					SceneController.changeScene(AccountViewScene.getScene(user));
				} catch (FileNotFoundException | IllegalArgumentException ex) {
					Alert alert = new Alert(AlertType.ERROR);
					alert.setTitle("Error");
					alert.setHeaderText("Not so fast there...");
					alert.setContentText(ex.getMessage());
					alert.showAndWait();
					SceneController.changeScene(DefaultScene.getScene());
				}
			}
		});

		Button addNewCustomerButton = new Button("Add New User");
		hbBtn = new HBox(10);
		hbBtn.setAlignment(Pos.BOTTOM_CENTER);
		hbBtn.getChildren().add(addNewCustomerButton);
		grid.add(hbBtn, 0, 4);

		addNewCustomerButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent e) {
				SceneController.changeScene(UserScene.getScene());
			}
		});

		Text notFederallyInsuredText = new Text("Not Federally insured by NCUA.");
		grid.add(notFederallyInsuredText, 0, 5);

		BorderPane bp = new BorderPane();
		HBox hb = new HBox();
		hb.setAlignment(Pos.CENTER);
		hb.getChildren().addAll(notFederallyInsuredText);
		bp.setCenter(grid);
		bp.setBottom(hb);
		grid.setBorder(new Border(
				new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(2))));

		Scene scene = new Scene(bp, 475, 550);
		scene.getStylesheets().add(LoginScene.class.getResource("Login.css").toExternalForm());

		return scene;
	}

}
