package edu.ntnu.idatt1002.controllers;

import edu.ntnu.idatt1002.registers.ContactRegister;
import edu.ntnu.idatt1002.registers.RegisterManager;
import edu.ntnu.idatt1002.storageitems.Contact;
import edu.ntnu.idatt1002.storageitems.Sale;
import edu.ntnu.idatt1002.viewmanagement.View;
import edu.ntnu.idatt1002.viewmanagement.ViewManager;
import java.time.ZoneId;
import java.util.Date;
import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.input.InputEvent;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.util.Duration;

/**
 * Controller class for the register sale page.
 */
public class CreateSaleController {

  @FXML
  private ComboBox<String> customerComboBox;
  @FXML
  private DatePicker datePicker;
  @FXML
  private TextField accountTextField;
  @FXML
  private TextField productTextField;
  @FXML
  private TextField amountTextField;
  private View pageToReturnTo;

  /**
   * Method for switching back to main menu. If a different page is specified by the pageToReturnTo
   * parameter, the method will try to go to that page instead.
   *
   * @param event Type of event that brings you back to main menu.
   */
  public void switchToSelectedScene(InputEvent event) {
    if (pageToReturnTo != null) {
      ViewManager.switchToScene(event, pageToReturnTo);
    }
    ViewManager.switchToScene(event, View.MAIN_MENU);

  }

  /**
   * Initializes nodes on create sale page.
   */
  @FXML
  public void initialize() {
    fillContactBox();
  }

  /**
   * Tries to create a sale and add it to the register, if the sale cannot be created because of
   * invalid values, the method will throw exceptions.
   *
   * @throws IllegalArgumentException if Any of the input fields are invalid values for a sale
   * @throws NullPointerException     if any of the input fields are null
   */
  public void createSale() throws IllegalArgumentException, NullPointerException {
    if (customerComboBox.getValue() == null || customerComboBox.getValue().isBlank()) {
      throw new IllegalArgumentException("customer has to be chosen");
    }
    if (datePicker.getValue() == null) {
      throw new IllegalArgumentException("Date has to be chosen");
    }
    if (amountTextField.getText().isEmpty()) {
      throw new IllegalArgumentException("Amount has to be chosen");
    }
    if (accountTextField.getText().isEmpty()) {
      throw new IllegalArgumentException("Account has to be chosen");
    }
    if (productTextField.getText().isEmpty()) {
      throw new IllegalArgumentException("Product has to be chosen");
    }

    String customer = customerComboBox.getValue();
    Contact contact = RegisterManager.getInstance().getCustomerRegister()
        .findContactFromName(customer);
    Date date = Date.from(datePicker.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
    String receiverAccount = accountTextField.getText();
    String product = productTextField.getText();
    double amount = (Double.parseDouble(amountTextField.getText()));

    Sale newSale;
    if (contact != null) {
      newSale = new Sale(contact, date, product, receiverAccount, amount);
    } else {
      newSale = new Sale(customer, date, product, receiverAccount, amount);
    }
    RegisterManager.getInstance().getSaleRegister().addObject(newSale);

    clearAllFields();
    confirmSaleIsCreated();
  }

  /**
   * Tries to create a sale, if the sale is not created, the method will show an alert informing the
   * user of the problem.
   */
  public void onCreateSaleClicked() {
    try {
      createSale();
      confirmSaleIsCreated();
    } catch (NumberFormatException e) {
      Alert alert = new Alert(AlertType.WARNING, e.getMessage() + " is not a valid number");
      alert.showAndWait();

    } catch (IllegalArgumentException e) {
      Alert alert = new Alert(AlertType.WARNING, "Cannot save expense because: " + e.getMessage());
      alert.showAndWait();
    }
  }

  /**
   * Shows an alert that a sale has been created for 1 second.
   */
  public void confirmSaleIsCreated() {
    Alert saleHasBeenCreated = new Alert(AlertType.CONFIRMATION, "Sale has been saved");
    saleHasBeenCreated.show();
    PauseTransition delay = new PauseTransition(Duration.seconds(1));
    delay.setOnFinished(event -> saleHasBeenCreated.close());
    delay.play();
  }

  /**
   * Method for clearing all fields of input.
   */
  @FXML
  public void clearAllFields() {
    customerComboBox.setValue(null);
    datePicker.getEditor().clear();
    accountTextField.clear();
    productTextField.clear();
    amountTextField.clear();
  }

  /**
   * Fills the contact box with existing contacts.
   */
  public void fillContactBox() {
    ContactRegister contactRegister = RegisterManager.getInstance().getCustomerRegister();
    customerComboBox.setEditable(true);
    for (Contact contact : contactRegister.getObjects()) {
      customerComboBox.getItems().add(contact.getName());
    }
  }

  /**
   * Sets a new page to return on return button pressed or when sale has been created.
   *
   * @param view The location of the page that should be loaded
   */
  public void setPageToReturnTo(View view) {
    this.pageToReturnTo = view;
  }

  /**
   * Handles key shortcuts, executing the shortcut linked to each KeyCode.
   *
   * @param keyEvent Event that triggers the shortcut.
   */
  @FXML
  private void handleKeyPressed(KeyEvent keyEvent) {
    if (keyEvent.getCode().equals(KeyCode.ENTER)) {
      onCreateSaleClicked();
    } else if (keyEvent.getCode().equals(KeyCode.ESCAPE)) {
      switchToSelectedScene(keyEvent);
    }
  }
}