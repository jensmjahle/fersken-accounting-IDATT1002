package edu.ntnu.idatt1002.fxml;

import edu.ntnu.idatt1002.Budget;
import edu.ntnu.idatt1002.PathUtility;
import edu.ntnu.idatt1002.RegisterManager;
import edu.ntnu.idatt1002.registers.BudgetRegister;
import javafx.fxml.FXML;
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

import java.awt.event.ActionEvent;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ListOfAllBudgetsController implements Initializable {

  private Stage stage;
  private Scene scene;
  private Parent root;

  @FXML
  private TableView budgetTable;
  @FXML
  private TableColumn budgetNameColumn;
  @FXML
  private TableColumn budgetExpensesColumn;
  @FXML
  private TableColumn budgetIncomesColumn;
  @FXML
  private TableColumn budgetDifferenceColumn;

  public void switchToMainMenuScene(MouseEvent event) throws IOException {
    Parent root = FXMLLoader.load(PathUtility.getResourcePath("MainMenu"));
    stage = (Stage)((Node)event.getSource()).getScene().getWindow();
    scene = new Scene(root);
    stage.setScene(scene);
    stage.show();
  }

  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {
   // BudgetRegister budgetRegister = new BudgetRegister("budgets");
    BudgetRegister budgetRegister = RegisterManager.getInstance().getBudgetRegister();

    budgetNameColumn.setCellValueFactory(
        new PropertyValueFactory<>("projectName"));

    budgetExpensesColumn.setCellValueFactory(
        new PropertyValueFactory<>("sumOfExpenses"));

    budgetIncomesColumn.setCellValueFactory(
        new PropertyValueFactory<>("sumOfSales"));

    budgetDifferenceColumn.setCellValueFactory(
        new PropertyValueFactory<>("difference"));

    for(Budget budget: budgetRegister.getObjects()){
      budgetTable.getItems().add(budget);
    }
  }
}