package scenes;

import java.io.FileNotFoundException;
import java.io.IOException;

import accounts.AccountController;
import accounts.daos.impls.BusinessCheckingAccountDAOImpl;
import accounts.daos.impls.BusinessSavingsAccountDAOImpl;
import accounts.daos.impls.PersonalCheckingAccountDAOImpl;
import accounts.daos.impls.PersonalSavingsAccountDAOImpl;
import accounts.daos.impls.StudentCheckingAccountDAOImpl;
import accounts.daos.impls.StudentSavingsAccountDAOImpl;
import accounts.table.AccountTableDAO;
import accounts.table.AccountTableDAOImpl;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
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
import transactions.TransactionDAO;
import transactions.TransactionDAOImpl;
import user.AddressDAO;
import user.AddressDAOImpl;
import user.User;
import user.UserController;
import user.UserDAO;
import user.UserDAOImpl;

public class AddAccountScene {

	/**
	 * Generates the add account scene for the Banking System.
	 * 
	 * @return The constructed add account scene for the Banking System.
	 */
	public static Scene getScene(User user) {

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

		Text instructions = new Text("Select an account type below:");
		grid.add(instructions, 0, 1);
		
		String accountTypes[]= {"Student Checking Account","Personal Checking Account", "Business Checking Account","Student Savings Account", 
				"Personal Savings Account", "Business Savings Account"};
		
		ComboBox combo_box = new ComboBox (FXCollections.observableArrayList(accountTypes));
		grid.add(combo_box,0,2);

		Button submitButton = new Button("Submit");
		HBox hbBtn = new HBox(10);
		hbBtn.setAlignment(Pos.BOTTOM_CENTER);
		hbBtn.getChildren().add(submitButton);
		grid.add(hbBtn, 0, 3);

		submitButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent e) {
				AccountController accountController;
				TransactionDAO transactionDAO = new TransactionDAOImpl();
				AccountTableDAO accountTableDAO = new AccountTableDAOImpl(transactionDAO);
				switch ((String) combo_box.getValue()) {
				case "Student Checking Account":
					accountController = new AccountController(
							new StudentCheckingAccountDAOImpl(accountTableDAO, transactionDAO), accountTableDAO);
					try {
						accountController.addAccount(user);
					} catch (IllegalStateException | IllegalArgumentException | IOException ex) {
						Alert alert = new Alert(AlertType.ERROR, ex.getMessage(), ButtonType.OK);
						alert.showAndWait();
					}
					break;
				case "Personal Checking Account":
					accountController = new AccountController(
							new PersonalCheckingAccountDAOImpl(accountTableDAO, transactionDAO), accountTableDAO);

					try {
						accountController.addAccount(user);
					} catch (IllegalStateException | IllegalArgumentException | IOException ex) {
						Alert alert = new Alert(AlertType.ERROR, ex.getMessage(), ButtonType.OK);
						alert.showAndWait();
					}
					break;
				case "Business Checking Account":
					accountController = new AccountController(
							new BusinessCheckingAccountDAOImpl(accountTableDAO, transactionDAO), accountTableDAO);

					try {
						accountController.addAccount(user);
					} catch (IllegalStateException | IllegalArgumentException | IOException ex) {
						Alert alert = new Alert(AlertType.ERROR, ex.getMessage(), ButtonType.OK);
						alert.showAndWait();
					}
					break;

				case "Student Savings Account":
					accountController = new AccountController(
							new StudentSavingsAccountDAOImpl(accountTableDAO, transactionDAO), accountTableDAO);

					try {
						accountController.addAccount(user);
					} catch (IllegalStateException | IllegalArgumentException | IOException ex) {
						Alert alert = new Alert(AlertType.ERROR, ex.getMessage(), ButtonType.OK);
						alert.showAndWait();
					}
					break;
				case "Personal Savings Account":
					accountController = new AccountController(
							new PersonalSavingsAccountDAOImpl(accountTableDAO, transactionDAO), accountTableDAO);

					try {
						accountController.addAccount(user);
					} catch (IllegalStateException | IllegalArgumentException | IOException ex) {
						Alert alert = new Alert(AlertType.ERROR, ex.getMessage(), ButtonType.OK);
						alert.showAndWait();
					}
					break;
				case "Business Savings Account":
					accountController = new AccountController(
							new BusinessSavingsAccountDAOImpl(accountTableDAO, transactionDAO), accountTableDAO);

					try {
						accountController.addAccount(user);
					} catch (IllegalStateException | IllegalArgumentException | IOException ex) {
						Alert alert = new Alert(AlertType.ERROR, ex.getMessage(), ButtonType.OK);
						alert.showAndWait();
					}
					break;
				default:
					Alert alert = new Alert(AlertType.ERROR, "Invalid selection.", ButtonType.OK);
					alert.showAndWait();
				}
				SceneController.changeScene(DefaultScene.getScene());

			}
		});

		Button cancelButton = new Button("Cancel");
		hbBtn = new HBox(10);
		hbBtn.setAlignment(Pos.BOTTOM_CENTER);
		hbBtn.getChildren().add(cancelButton);
		grid.add(hbBtn, 0, 4);

		cancelButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent e) {
				SceneController.changeScene(DefaultScene.getScene());
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
