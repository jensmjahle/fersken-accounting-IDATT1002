package edu.ntnu.idatt1002.fxml;

import edu.ntnu.idatt1002.PathUtility;
import edu.ntnu.idatt1002.RegisterManager;
import edu.ntnu.idatt1002.Sale;
import edu.ntnu.idatt1002.registers.SaleRegister;
import java.io.File;
import java.io.IOException;
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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 * Controller for the list of all sales fxml file. Shows a table of suppliers to the user. Enables
 * the user to delete and edit existing suppliers
 */
public class ListOfAllSalesController implements Initializable {

  @FXML
  public Button editButton;
  @FXML
  public Button deleteButton;
  @FXML
  public ImageView infoIcon;
  @FXML
  private TableView<Sale> salesTableView;
  @FXML
  private TableColumn<Sale, String> customerTableColumn;
  @FXML
  private TableColumn<Sale, String> dateTableColumn;
  @FXML
  private TableColumn<Sale, String> accountTableColumn;
  @FXML
  private TableColumn<Sale, String> amountTableColumn;
  @FXML
  private TableColumn<Sale, String> productTableColumn;
  private SaleRegister saleRegister;

  /**
   * Switches the application back to the main menu scene.
   *
   * @param event The event that triggers the switch back to the main menu.
   * @throws IOException If the resource cannot be found
   */
  @FXML
  private void switchToMainMenuScene(MouseEvent event) throws IOException {
    Parent root = FXMLLoader.load(PathUtility.getResourcePath("MainMenu"));
    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    Scene scene = new Scene(root);
    stage.setScene(scene);
    stage.show();
  }

  /**
   * Initializes the controller with necessary functions and updates the column that shows the
   * sales.
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
    enableInformationIcon();
    installToolTip();


  }

  /**
   * Enables multi-selection in the tableview.
   */
  private void enableMultiSelection() {
    salesTableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
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
   * Updates the tableview with updated values for the displayed objects.
   */
  private void updateTable() {
    salesTableView.getItems().removeAll(salesTableView.getItems());
    saleRegister = RegisterManager.getInstance().getSaleRegister();

    customerTableColumn.setCellValueFactory(new PropertyValueFactory<>("customer"));

    dateTableColumn.setCellValueFactory(new PropertyValueFactory<>("date"));

    accountTableColumn.setCellValueFactory(new PropertyValueFactory<>("receiverAccount"));

    amountTableColumn.setCellValueFactory(new PropertyValueFactory<>("amount"));

    productTableColumn.setCellValueFactory(new PropertyValueFactory<>("product"));

    for (Sale sale : saleRegister.getObjects()) {
      salesTableView.getItems().add(sale);
    }
  }

  /**
   * Deletes the currently highlighted objects in the tableview from the register.
   */
  @FXML
  private void deleteButtonClicked() {
    Alert alert = new Alert(AlertType.CONFIRMATION);
    List<Sale> selectedSales = salesTableView.getSelectionModel().getSelectedItems();
    alert.setContentText("Are you sure you want to delete: " + selectedSales.size() + " sale(s)?");
    alert.showAndWait();
    if (alert.getResult() == ButtonType.OK) {
      for (Sale sale : selectedSales) {
        saleRegister.removeObject(sale);
      }
      updateTable();
    }

  }

  @FXML
  private void editButtonClicked() {

    List<Sale> selectedSales = salesTableView.getSelectionModel().getSelectedItems();
    if (selectedSales.size() != 1) {
      Alert alert = new Alert(AlertType.WARNING, "Can only edit 1 item at once");
      alert.showAndWait();
      return;
    }
    //TODO add editing button functionality

    updateTable();

  }

  /**
   * Enables the disabling of buttons when their actions are not possible to perform.
   */
  private void disableButtonsWhileInvalid() {
    deleteButton.disableProperty().bind(selectedItemsNotNullBinding().not());
    editButton.disableProperty().bind(onlyOneSelectedItemBinding().not());
  }

  /**
   * Boolean binding that checks if there are any selected items.
   *
   * @return a BooleanBinding that represents if there are any selected items or not
   */
  private BooleanBinding selectedItemsNotNullBinding() {
    return salesTableView.getSelectionModel().selectedItemProperty().isNotNull();
  }

  /**
   * Creates a boolean binding that checks if only one item is selected.
   *
   * @return a boolean binding that represents information about if only one item is selected in the
   *        table.
   */
  private BooleanBinding onlyOneSelectedItemBinding() {
    return Bindings.createBooleanBinding(
        () -> salesTableView.getSelectionModel().getSelectedItems().size() == 1,
        salesTableView.getSelectionModel().getSelectedItems());
  }

  /**
   * Installs a tooltip to the info icon to show the user how to use the controls for deleting and
   * editing objects.
   */
  private void installToolTip() {
    Tooltip tooltip = new Tooltip("""
        Highlight sale : left click
        Unhighlight sale : press left click on a marked sale
        Mark multiple sales : hold ctrl while highlighting
        """);
    Tooltip.install(infoIcon, tooltip);
  }
}