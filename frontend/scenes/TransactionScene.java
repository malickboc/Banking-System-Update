package scenes;

import java.awt.Label;
import java.io.FileNotFoundException;
import java.io.IOException;

import accounts.Account;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
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

public class TransactionScene {

	/**
	 * Generates the Default scene for the Banking System, which is the home page.
	 * 
	 * @return The constructed default scene for the Banking System.
	 */
	public static Scene getScene(Account account) {

		GridPane grid = new GridPane();
		grid.setAlignment(Pos.CENTER);
		grid.setStyle("-fx-background-color: white;");
		grid.setMaxWidth(400);
		grid.setMaxHeight(400);
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(25, 25, 25, 25));
		
		

		Image img = new Image("logo.png");
		grid.add(new ImageView(img), 0, 0);
		
		Text accountBalance = new Text("Account Balance");
		grid.add(accountBalance, 0, 1);
		
		Text accountview = new Text("$"+account.getBalance().toPlainString());
		grid.add(accountview, 0, 2);
		
		Text  amount = new Text("Enter an amount");
		grid.add(amount, 0, 3);

		
		TextField  amountview = new TextField("10.00");
		grid.add(amountview, 0, 4);

		
		Button depositButton = new  Button ("Deposit");
		grid.add(depositButton, 1, 1);


		depositButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				try {
					
					account.deposit(amountview.getText());

					accountview.setText("$"+account.getBalance().toPlainString());
					
				}
				catch(IOException | IllegalArgumentException e) {
					
					Alert alert = new Alert(AlertType.ERROR, e.getMessage(),ButtonType.OK) ;
					alert.showAndWait();
					
				}
			}
			
			
			
		});

		
		Button withdrawButton = new  Button ("withdraw");
		grid.add(withdrawButton,1,2);
		
		withdrawButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				try {
					
					account.withdraw(amountview.getText());

					account.withdraw(amountview.getText());
					accountview.setText("$"+account.getBalance().toPlainString());
					
				}
				catch(IOException | IllegalArgumentException e) {
					
					Alert alert = new Alert(AlertType.ERROR, e.getMessage(),ButtonType.OK) ;
					alert.showAndWait();
					
				}
			}
			
			
			
		});
		
		Button cancelButton = new  Button ("cancel");
		grid.add(cancelButton,1,3);
		
		cancelButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				SceneController.changeScene(DefaultScene.getScene());
			}
			
			
			
		});
		
		Text notFederallyInsuredText = new Text ("Not Federally insured by NCUA");
		
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
