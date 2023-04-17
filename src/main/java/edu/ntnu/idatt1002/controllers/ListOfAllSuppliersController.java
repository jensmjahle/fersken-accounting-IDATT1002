package edu.ntnu.idatt1002.controllers;

import edu.ntnu.idatt1002.Contact;
import edu.ntnu.idatt1002.PathUtility;
import edu.ntnu.idatt1002.RegisterManager;
import edu.ntnu.idatt1002.registers.ContactRegister;
import edu.ntnu.idatt1002.viewmanagement.View;
import edu.ntnu.idatt1002.viewmanagement.ViewManager;
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
 * Controller for the list of all suppliers fxml file. Shows a table of suppliers to the user.
 * Enables the user to delete and edit existing suppliers
 */
public class ListOfAllSuppliersController implements Initializable {

  @FXML
  private ImageView infoIcon;
  @FXML
  private Button deleteButton;
  @FXML
  private Button editButton;
  @FXML
  private TableView<Contact> supplierTable;
  @FXML
  private TableColumn<Contact, String> nameColumn;
  @FXML
  private TableColumn<Contact, String> emailColumn;
  @FXML
  private TableColumn<Contact, String> phoneNumberColumn;
  @FXML
  private TableColumn<Contact, String> organizationNumberColumn;
  @FXML
  private TableColumn<Contact, String> accountNumberColumn;
  @FXML
  private TableColumn<Contact, String> postCodeColumn;
  @FXML
  private TableColumn<Contact, String> streetColumn;
  @FXML
  private TableColumn<Contact, String> streetNumberColumn;
  private ContactRegister supplierRegister;

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


  @FXML
  private void switchToEditScene(MouseEvent event) throws IOException {
    FXMLLoader loader = new FXMLLoader(PathUtility.getResourcePath(View.EDIT_SUPPLIER.getFileName()));
    Parent root = loader.load();
    EditSupplierController controller = loader.getController();
    controller.setSupplier(supplierTable.getSelectionModel().getSelectedItems().get(0));

    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    Scene scene = new Scene(root);
    stage.setScene(scene);
    stage.show();
  }

  /**
   * Initializes the controller with necessary functions and updates the column that shows the
   * suppliers.
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
   * Updates the tableview with updated values for the displayed objects.
   */
  private void updateTable() {
    supplierTable.getItems().removeAll(supplierTable.getItems());
    supplierRegister = RegisterManager.getInstance().getSupplierRegister();

    nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

    emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));

    phoneNumberColumn.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));

    organizationNumberColumn.setCellValueFactory(new PropertyValueFactory<>("organizationNumber"));

    accountNumberColumn.setCellValueFactory(new PropertyValueFactory<>("accountNumber"));

    postCodeColumn.setCellValueFactory(new PropertyValueFactory<>("postCode"));

    streetColumn.setCellValueFactory(new PropertyValueFactory<>("street"));

    streetNumberColumn.setCellValueFactory(new PropertyValueFactory<>("streetNumber"));

    for (Contact contact : supplierRegister.getObjects()) {
      supplierTable.getItems().add(contact);
    }
  }

  /**
   * Deletes the currently highlighted objects in the tableview from the register.
   */
  @FXML
  private void deleteButtonClicked() {
    Alert alert = new Alert(AlertType.CONFIRMATION);
    List<Contact> selectedSuppliers = supplierTable.getSelectionModel().getSelectedItems();
    alert.setContentText(
        "Are you sure you want to delete: " + selectedSuppliers.size() + " sale(s)?");
    alert.showAndWait();
    if (alert.getResult() == ButtonType.OK) {

      for (Contact supplier : selectedSuppliers) {
        supplierRegister.removeObject(supplier);
      }
      updateTable();
    }

  }

  @FXML
  private void editButtonClicked() {

    List<Contact> selectedSuppliers = supplierTable.getSelectionModel().getSelectedItems();
    if (selectedSuppliers.size() != 1) {
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
    return supplierTable.getSelectionModel().selectedItemProperty().isNotNull();
  }

  /**
   * Creates a boolean binding that checks if only one item is selected.
   *
   * @return a boolean binding that represents information about if only one item is selected in the
   * table.
   */
  private BooleanBinding onlyOneSelectedItemBinding() {
    return Bindings.createBooleanBinding(
        () -> supplierTable.getSelectionModel().getSelectedItems().size() == 1,
        supplierTable.getSelectionModel().getSelectedItems());
  }

  /**
   * Installs a tooltip to the info icon to show the user how to use the controls for deleting and
   * editing objects.
   */
  public void installToolTip() {
    Tooltip tooltip = new Tooltip("""
        Highlight supplier : left click
        Unhighlight supplier : press left click on a marked supplier
        Mark multiple supplier : hold ctrl while highlighting
        """);
    Tooltip.install(infoIcon, tooltip);
  }

  /**
   * Enables multi-selection in the tableview.
   */
  private void enableMultiSelection() {
    supplierTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
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
}
