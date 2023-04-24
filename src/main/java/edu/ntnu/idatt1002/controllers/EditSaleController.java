package edu.ntnu.idatt1002.controllers;

import edu.ntnu.idatt1002.registers.RegisterManager;
import edu.ntnu.idatt1002.storageitems.Sale;
import edu.ntnu.idatt1002.viewmanagement.View;
import edu.ntnu.idatt1002.viewmanagement.ViewManager;
import java.time.ZoneId;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.input.InputEvent;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

/**
 * Controller for the Edit Sale fxml file.
 */
public class EditSaleController extends CreateSaleController {

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
  private Sale sale;

  /**
   * Sets the sale and updates the fields with current info.
   *
   * @param sale Sale that will be edited and displayed
   */
  public void setSale(Sale sale) {
    this.sale = sale;
    fillFieldsWithCurrentInfo();

  }

  /**
   * Fills the customer choice box with all customers during initialization.
   */
  @Override
  public void initialize() {
    fillContactBox();
  }

  /**
   * Tries to create a new sale. If the sale is created the old sale will be deleted.
   */
  @FXML
  private void replaceSale(InputEvent event) {

    try {

      createSale();
      RegisterManager.getInstance().getSaleRegister().removeObject(sale);
      switchToListOfSales(event);
    } catch (NumberFormatException e) {
      Alert alert = new Alert(AlertType.WARNING, e.getMessage() + " is not a valid number");
      alert.showAndWait();

    } catch (IllegalArgumentException e) {
      Alert alert = new Alert(AlertType.WARNING, "Cannot save sale because: " + e.getMessage());
      alert.showAndWait();
    }

  }

  /**
   * Loads the list of sales fxml file.
   *
   * @param mouseEvent the event that triggers the switch back to list of sales.
   */
  @FXML
  public void switchToListOfSales(InputEvent mouseEvent) {
    ViewManager.switchToScene(mouseEvent, View.LIST_OF_ALL_SALES);
  }

  /**
   * Fills the text input fields with the current info about the sale that will be edited.
   */
  @FXML
  public void fillFieldsWithCurrentInfo() {
    try {
      customerComboBox.setValue(sale.getCustomerName());

      datePicker.setValue(sale.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
      accountTextField.setText(sale.getReceiverAccount());
      productTextField.setText(sale.getProduct());
      amountTextField.setText(String.valueOf(sale.getAmount()));
    } catch (Exception e) {
      e.printStackTrace();
      Alert alert = new Alert(AlertType.ERROR, "Cannot present all fields for selected expense");
      alert.showAndWait();
    }
  }

  /**
   * Handles key shortcuts, executing the shortcut linked to each KeyCode.
   *
   * @param keyEvent Event that triggers the shortcut.
   */
  @FXML
  private void handleKeyPressed(KeyEvent keyEvent) {
    if (keyEvent.getCode().equals(KeyCode.ENTER)) {
      replaceSale(keyEvent);
    } else if (keyEvent.getCode().equals(KeyCode.ESCAPE)) {
      switchToListOfSales(keyEvent);
    }
  }

}



