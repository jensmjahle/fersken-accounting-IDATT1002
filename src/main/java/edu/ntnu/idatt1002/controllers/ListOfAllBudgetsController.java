package edu.ntnu.idatt1002.controllers;

import edu.ntnu.idatt1002.Budget;
import edu.ntnu.idatt1002.PathUtility;
import edu.ntnu.idatt1002.RegisterManager;
import edu.ntnu.idatt1002.registers.BudgetRegister;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import edu.ntnu.idatt1002.viewmanagement.View;
import edu.ntnu.idatt1002.viewmanagement.ViewManager;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 * Controller for the list of all budgets fxml file. Shows a table of suppliers to the user.
 * Enables the user to delete and edit existing suppliers
 */
public class ListOfAllBudgetsController implements Initializable {

  @FXML
  private Button deleteButton;
  @FXML
  private Button editButton;
  @FXML
  private Button openButton;
  @FXML
  private ImageView infoIcon;
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
   * @throws IOException If the resource cannot be found
   */
  @FXML
  private void switchToMainMenuScene(MouseEvent event) throws IOException {
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
    enableInformationIcon();
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
  @FXML
  private void switchToOpenBudgetScene(MouseEvent event) throws  IOException{

    FXMLLoader loader = new FXMLLoader(PathUtility.getResourcePath(View.OPEN_BUDGET.getFileName()));
    Parent root = loader.load();
    OpenBudgetController controller = loader.getController();
    controller.budgetToBeShown(budgetTable.getSelectionModel().getSelectedItems().get(0));

    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    Scene scene = new Scene(root);
    stage.setScene(scene);
    stage.show();
  }

  @FXML
  private void switchToEditBudgetScene(MouseEvent event) throws IOException{

    FXMLLoader loader = new FXMLLoader(PathUtility.getResourcePath(View.EDIT_BUDGET.getFileName()));
    Parent root = loader.load();
    EditBudgetController controller = loader.getController();
    controller.setBudgetToRemove(budgetTable.getSelectionModel().getSelectedItems().get(0));

    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    Scene scene = new Scene(root);
    stage.setScene(scene);
    stage.show();


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
    Tooltip.install(infoIcon, tooltip);
  }

  /**
   * Adds an information icon to the current scene.
   */
  private void enableInformationIcon() {
    try {
      File imageFile = new File("src/main/resources/Icons/icon_information.png");
      Image image = new Image(imageFile.toURI().toString());
      infoIcon.setImage(image);
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
  }

  /**
   * Enables multi-selection in the tableview.
   */
  private void enableMultiSelection() {
    budgetTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
  }


}




