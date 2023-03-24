package edu.ntnu.idatt1002.fxml;

import edu.ntnu.idatt1002.Expense;
import edu.ntnu.idatt1002.RegisterManager;
import edu.ntnu.idatt1002.registers.ExpenseRegister;
import javafx.fxml.FXML;
import edu.ntnu.idatt1002.PathUtility;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ListOfAllExpensesController implements Initializable {

  private Stage stage;
  private Scene scene;
  private Parent root;
  @FXML
  private TableView expenseTableView;
  @FXML
  private TableColumn amountTableColumn;
  @FXML
  private TableColumn dateTableColumn;
  @FXML
  private TableColumn productTableColumn;

  public void switchToMainMenuScene(MouseEvent event) throws IOException {
    Parent root = FXMLLoader.load(PathUtility.getResourcePath("MainMenu"));
    stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    scene = new Scene(root);
    stage.setScene(scene);
    stage.show();
  }

  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {
    ExpenseRegister expenseRegister = RegisterManager.getInstance().getExpenseRegister();

    amountTableColumn.setCellValueFactory(
            new PropertyValueFactory<>("amount"));

    dateTableColumn.setCellValueFactory(
            new PropertyValueFactory<>("date"));

    productTableColumn.setCellValueFactory(
            new PropertyValueFactory<>("product"));
    for (Expense expense : expenseRegister.getObjects()) {
      expenseTableView.getItems().add(expense);
    }
  }
}
