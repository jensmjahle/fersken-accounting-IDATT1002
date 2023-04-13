package edu.ntnu.idatt1002.controllers;

import edu.ntnu.idatt1002.Contact;
import edu.ntnu.idatt1002.PathUtility;
import edu.ntnu.idatt1002.RegisterManager;
import edu.ntnu.idatt1002.registers.ContactRegister;
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
 * Controller for the list of all customers fxml file. Shows a table of suppliers to the user.
 * Enables the user to delete and edit existing suppliers
 */
public class ListOfAllCustomersController implements Initializable {

  @FXML
  private Button deleteButton;
  @FXML
  private Button editButton;
  @FXML
  private ImageView infoIcon;
  @FXML
  private TableView<Contact> customerTable;
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
  private ContactRegister customerRegister;

  /**
   * Switches the application back to the main menu scene.
   *
   * @param event The event that triggers the switch back to the main menu.
   * @throws IOException If the resource cannot be found
   */
  @FXML
  private void switchToMainMenuScene(MouseEvent event) throws IOException {
    ViewManager.switchToScene(event, View.MAINMENU);
  }

  /**
   * Initializes the controller with necessary functions and updates the column that shows the
   * customers.
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
    customerTable.getItems().removeAll(customerTable.getItems());
    customerRegister = RegisterManager.getInstance().getCustomerRegister();

    nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

    emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));

    phoneNumberColumn.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));

    organizationNumberColumn.setCellValueFactory(new PropertyValueFactory<>("organizationNumber"));

    accountNumberColumn.setCellValueFactory(new PropertyValueFactory<>("accountNumber"));

    postCodeColumn.setCellValueFactory(new PropertyValueFactory<>("postCode"));

    streetColumn.setCellValueFactory(new PropertyValueFactory<>("street"));

    streetNumberColumn.setCellValueFactory(new PropertyValueFactory<>("streetNumber"));

    for (Contact customer : customerRegister.getObjects()) {
      customerTable.getItems().add(customer);
    }
  }

  /**
   * Deletes the currently highlighted objects in the tableview from the register.
   */
  @FXML
  private void deleteButtonClicked() {
    Alert alert = new Alert(AlertType.CONFIRMATION);
    List<Contact> selectedCustomers = customerTable.getSelectionModel().getSelectedItems();
    alert.setContentText(
        "Are you sure you want to delete: " + selectedCustomers.size() + " customer(s)?");
    alert.showAndWait();
    if (alert.getResult() == ButtonType.OK) {

      for (Contact customer : selectedCustomers) {
        customerRegister.removeObject(customer);
      }

    }
    updateTable();
  }

  @FXML
  private void editButtonClicked() {

    List<Contact> selectedCustomers = customerTable.getSelectionModel().getSelectedItems();
    if (selectedCustomers.size() != 1) {
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
    return customerTable.getSelectionModel().selectedItemProperty().isNotNull();
  }

  /**
   * Creates a boolean binding that checks if only one item is selected.
   *
   * @return a boolean binding that represents information about if only one item is selected in the
   *        table.
   */
  private BooleanBinding onlyOneSelectedItemBinding() {
    return Bindings.createBooleanBinding(
        () -> customerTable.getSelectionModel().getSelectedItems().size() == 1,
        customerTable.getSelectionModel().getSelectedItems());
  }

  /**
   * Installs a tooltip to the info icon to show the user how to use the controls for deleting and
   * editing objects.
   */
  public void installToolTip() {
    Tooltip tooltip = new Tooltip("""
        Highlight customer : left click
        Unhighlight customer : press left click on a marked customer
        Mark multiple customer : hold ctrl while highlighting
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
    customerTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
  }
}



