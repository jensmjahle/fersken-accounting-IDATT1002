package edu.ntnu.idatt1002.controllers;

import edu.ntnu.idatt1002.registers.BudgetRegister;
import edu.ntnu.idatt1002.registers.RegisterManager;
import edu.ntnu.idatt1002.storageitems.Budget;
import edu.ntnu.idatt1002.utility.PathUtility;
import edu.ntnu.idatt1002.viewmanagement.View;
import edu.ntnu.idatt1002.viewmanagement.ViewManager;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.InputEvent;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * Controller for the list of all budgets fxml file. Shows a table of budgets to the user. Enables
 * the user to open, delete and edit existing budgets.
 */
public class ListOfAllBudgetsController implements Initializable {

  @FXML
  private Pane informationPane;
  @FXML
  private Button deleteButton;
  @FXML
  private Button editButton;
  @FXML
  private Button openButton;
  @FXML
  private TableView<Budget> budgetTable;
  @FXML
  private TableColumn<Budget, String> budgetNameColumn;
  @FXML
  private TableColumn<Budget, Double> budgetExpensesColumn;
  @FXML
  private TableColumn<Budget, Double> budgetIncomesColumn;
  @FXML
  private TableColumn<Budget, String> budgetDifferenceColumn;
  private BudgetRegister budgetRegister;


  /**
   * Switches the application back to the main menu scene.
   *
   * @param event The event that triggers the switch back to the main menu.
   */
  @FXML
  private void switchToMainMenuScene(InputEvent event) {
    ViewManager.switchToScene(event, View.MAIN_MENU);
  }

  /**
   * Initializes the controller with necessary functions and updates the column that shows the
   * budgets.
   *
   * @param url            The location used to resolve relative paths for the root object, or null
   *                       if the location is not known.
   * @param resourceBundle The resources used to localize the root object, or null if the root
   *                       object was not localized.
   */
  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {
    updateTable();
    disableButtonsWhileInvalid();
    enableMultiSelection();
    installToolTip();
  }

  /**
   * Updates the tableview with updated values for the displayed objects.
   */
  private void updateTable() {
    budgetTable.getItems().clear();

    budgetRegister = RegisterManager.getInstance().getBudgetRegister();
    budgetNameColumn.setCellValueFactory(new PropertyValueFactory<>("projectName"));
    budgetExpensesColumn.setCellValueFactory(new PropertyValueFactory<>("sumOfExpenses"));
    budgetIncomesColumn.setCellValueFactory(new PropertyValueFactory<>("sumOfSales"));
    budgetDifferenceColumn.setCellValueFactory(new PropertyValueFactory<>("difference"));

    for (Budget budget : budgetRegister.getObjects()) {
      budgetTable.getItems().add(budget);
    }
  }

  /**
   * Deletes the currently highlighted objects in the tableview from the register.
   */
  @FXML
  private void deleteButtonClicked() {
    Alert alert = new Alert(AlertType.CONFIRMATION);
    List<Budget> selectedBudgets = budgetTable.getSelectionModel().getSelectedItems();
    alert.setContentText(
        "Are you sure you want to delete: " + selectedBudgets.size() + " budget(s)?");
    alert.showAndWait();
    if (alert.getResult() == ButtonType.OK) {

      for (Budget budget : selectedBudgets) {
        budgetRegister.removeObject(budget);
      }

    }
    updateTable();
  }

  /**
   * Switches to open budget page. Opens the currently highlighted budget.
   *
   * @param event Event that triggers switch to open budget page.
   */
  @FXML
  private void switchToOpenBudgetScene(MouseEvent event) {
    try {

      FXMLLoader loader = new FXMLLoader(
          PathUtility.getResourcePath(View.OPEN_BUDGET.getFileName()));
      Parent root = loader.load();
      OpenBudgetController controller = loader.getController();
      controller.budgetToBeShown(budgetTable.getSelectionModel().getSelectedItems().get(0));

      Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
      Scene scene = new Scene(root);
      stage.setScene(scene);
      stage.show();
    } catch (Exception e) {
      Alert alert = new Alert(AlertType.ERROR,
          "Cannot switch to selected page\nPlease contact customer service");
      alert.showAndWait();
    }
  }

  /**
   * Switches to edit budget page. Displays the currently highlighted budget and allows for edits.
   *
   * @param event Event that triggers switch to edit budget page.
   */
  @FXML
  private void switchToEditBudgetScene(InputEvent event) {
    try {

      FXMLLoader loader = new FXMLLoader(
          PathUtility.getResourcePath(View.EDIT_BUDGET.getFileName()));
      Parent root = loader.load();
      EditBudgetController controller = loader.getController();
      controller.setBudgetToRemove(budgetTable.getSelectionModel().getSelectedItems().get(0));

      Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
      Scene scene = new Scene(root);
      stage.setScene(scene);
      stage.show();
    } catch (Exception e) {
      Alert alert = new Alert(AlertType.ERROR,
          "Cannot switch to selected page\nPlease contact customer service");
      alert.showAndWait();
    }

  }

  /**
   * Enables the disabling of buttons when their actions are not possible to perform.
   */
  private void disableButtonsWhileInvalid() {
    deleteButton.disableProperty().bind(selectedItemsNotNullBinding().not());
    editButton.disableProperty().bind(onlyOneSelectedItemBinding().not());
    openButton.disableProperty().bind(onlyOneSelectedItemBinding().not());
  }

  /**
   * Boolean binding that checks if there are any selected items.
   *
   * @return a BooleanBinding that represents if there are any selected items or not
   */
  private BooleanBinding selectedItemsNotNullBinding() {
    return budgetTable.getSelectionModel().selectedItemProperty().isNotNull();
  }

  /**
   * Creates a boolean binding that checks if only one item is selected.
   *
   * @return a boolean binding that represents information about if only one item is selected in the
   *        table.
   */
  private BooleanBinding onlyOneSelectedItemBinding() {
    return Bindings.createBooleanBinding(
        () -> budgetTable.getSelectionModel().getSelectedItems().size() == 1,
        budgetTable.getSelectionModel().getSelectedItems());
  }

  /**
   * Installs a tooltip to the info icon to show the user how to use the controls for deleting and
   * editing objects.
   */
  private void installToolTip() {
    Tooltip tooltip = new Tooltip("""
        Highlight budget : left click
        Unhighlight budget : press left click on a marked budget
        Mark multiple budgets : hold ctrl while highlighting
        """);
    tooltip.setFont(Font.font(20));
    tooltip.setShowDelay(Duration.ZERO);
    Tooltip.install(informationPane, tooltip);
  }


  /**
   * Enables multi-selection in the tableview.
   */
  private void enableMultiSelection() {
    budgetTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
  }

  /**
   * Handles key shortcuts, executing the shortcut linked to each KeyCode.
   *
   * @param keyEvent Event that triggers the shortcut.
   */
  @FXML
  private void handleKeyPressed(KeyEvent keyEvent) {
    if (keyEvent.getCode().equals(KeyCode.ENTER)) {
      switchToEditBudgetScene(keyEvent);
    } else if (keyEvent.getCode().equals(KeyCode.ESCAPE)) {
      switchToMainMenuScene(keyEvent);
    }
  }


}




