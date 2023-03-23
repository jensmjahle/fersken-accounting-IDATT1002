package edu.ntnu.idatt1002.fxml;

import edu.ntnu.idatt1002.PathUtility;
import edu.ntnu.idatt1002.RegisterManager;
import edu.ntnu.idatt1002.Sale;
import edu.ntnu.idatt1002.registers.SaleRegister;
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

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ListOfAllSalesController implements Initializable {

  @FXML
  private TableView salesTableView;
  @FXML
  private TableColumn customerTableColumn;
  @FXML
  private TableColumn dateTableColumn;
  @FXML
  private TableColumn accountTableColumn;
  @FXML
  private TableColumn amountTableColumn;
  @FXML
  private TableColumn productTableColumn;

  public void switchToMainMenuScene(MouseEvent event) throws IOException {
    Parent root = FXMLLoader.load(PathUtility.getResourcePath("MainMenu"));
    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    Scene scene = new Scene(root);
    stage.setScene(scene);
    stage.show();
  }

  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {
    SaleRegister saleRegister = RegisterManager.getInstance().getSaleRegister();

    customerTableColumn.setCellValueFactory(
            new PropertyValueFactory<>("customer"));

    dateTableColumn.setCellValueFactory
            (new PropertyValueFactory<>("date"));

    accountTableColumn.setCellValueFactory(
            new PropertyValueFactory<>("receiverAccount"));

    amountTableColumn.setCellValueFactory(
            new PropertyValueFactory<>("amount"));

    productTableColumn.setCellValueFactory(
            new PropertyValueFactory<>("product"));

    for (Sale sale : saleRegister.getObjects()) {
      salesTableView.getItems().add(sale);
    }
  }
}