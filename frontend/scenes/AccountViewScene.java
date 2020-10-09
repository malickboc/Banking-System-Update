package scenes;


import java.io.FileNotFoundException;
import java.util.List;

import accounts.Account;
import accounts.AccountController;
import accounts.table.AccountTableDAOImpl;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import transactions.TransactionDAOImpl;
import user.User;

public class AccountViewScene {

	public static Scene getScene(User user) {

		TableView tableView = new TableView();
		
		TableColumn<String , User> Firstcolumn = new TableColumn<>("AccountType");
		Firstcolumn.setCellValueFactory(new PropertyValueFactory<>("AccountType"));
		
		TableColumn<String , User> Secondcolumn = new TableColumn<>("Balance");
		Secondcolumn.setCellValueFactory(new PropertyValueFactory<>("Balance"));
		
		tableView.getColumns().add(Firstcolumn);
		tableView.getColumns().add(Secondcolumn);
		
		List<Account> accounts =null;
		try {
			AccountController accountController = new AccountController(new AccountTableDAOImpl(new TransactionDAOImpl()));
	          accounts = accountController.getAccounts(user.getDriversLicense());
	      for (Account account :accounts) {
	    	  
	    	  tableView.getItems().add(account);
	      }
		}
			catch (FileNotFoundException e) {
				
				Alert alert = new Alert(AlertType.ERROR,"Error loading accounts." ,ButtonType.OK);
				alert .showAndWait();
			}
			
			tableView.setPlaceholder(new Label("No row to display"));
			
			
			
			tableView.setOnMouseClicked ((MouseEvent e)->{
	  if (e.getButton().equals(MouseButton.PRIMARY)&& e.getClickCount()==2) {
		  SceneController.changeScene(TransactionScene.getScene((Account)tableView.getSelectionModel().getSelectedItem()));
	  }
			
			});
		
			
			Button Accountbutton =new Button("Add New Account");
			HBox box = new HBox(10);
			box.setAlignment(Pos.BOTTOM_CENTER);
			box.getChildren().add(Accountbutton);
			
			
		VBox vbox = new VBox(tableView,box);
		Accountbutton.setOnAction(new EventHandler<ActionEvent>(){
			
			@Override
			
			public void handle(ActionEvent ev) {
				SceneController.changeScene(AddAccountScene.getScene(user));
			}
		});

		Scene scene = new Scene(vbox);

		return scene;
		}

	}

