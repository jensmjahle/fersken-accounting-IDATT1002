package edu.ntnu.idatt1002.controllers;

import edu.ntnu.idatt1002.PathUtility;
import edu.ntnu.idatt1002.RegisterManager;
import edu.ntnu.idatt1002.Sale;
import edu.ntnu.idatt1002.viewmanagement.View;
import java.io.IOException;
import java.time.ZoneId;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

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
  private void replaceSale(MouseEvent event) throws IOException {

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
   * @throws IOException if the resource path is invalid.
   */
  @FXML
  public void switchToListOfSales(MouseEvent mouseEvent) throws IOException {
    Parent root = FXMLLoader.load(
        PathUtility.getResourcePath(View.LIST_OF_ALL_SALES.getFileName()));
    Stage stage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
    Scene scene = new Scene(root);
    stage.setScene(scene);
    stage.show();
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

}



