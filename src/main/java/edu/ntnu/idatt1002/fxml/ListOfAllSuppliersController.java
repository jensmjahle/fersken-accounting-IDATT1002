package edu.ntnu.idatt1002.fxml;

import edu.ntnu.idatt1002.Contact;
import edu.ntnu.idatt1002.PathUtility;
import edu.ntnu.idatt1002.registers.ContactRegister;
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

public class ListOfAllSuppliersController implements Initializable {

  private Stage stage;
  private Scene scene;
  private Parent root;

  @FXML
  private TableView supplierTable;
  @FXML
  private TableColumn nameColumn;
  @FXML
  private TableColumn emailColumn;
  @FXML
  private TableColumn phoneNumberColumn;
  @FXML
  private TableColumn organizationNumberColumn;
  @FXML
  private TableColumn accountNumberColumn;
  @FXML
  private TableColumn postCodeColumn;
  @FXML
  private TableColumn streetColumn;
  @FXML
  private TableColumn streetNumberColumn;

  public void switchToMainMenuScene(MouseEvent event) throws IOException {
    Parent root = FXMLLoader.load(PathUtility.getResourcePath("MainMenu"));
    stage = (Stage)((Node)event.getSource()).getScene().getWindow();
    scene = new Scene(root);
    stage.setScene(scene);
    stage.show();
  }

  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {
    ContactRegister contactRegister = new ContactRegister("suppliers");

    nameColumn.setCellValueFactory(
        new PropertyValueFactory<>("name"));

    emailColumn.setCellValueFactory(
        new PropertyValueFactory<>("email"));

    phoneNumberColumn.setCellValueFactory(
        new PropertyValueFactory<>("phoneNumber"));

    organizationNumberColumn.setCellValueFactory(
        new PropertyValueFactory<>("organizationNumber"));

    accountNumberColumn.setCellValueFactory(
        new PropertyValueFactory<>("accountNumber"));

    postCodeColumn.setCellValueFactory(
        new PropertyValueFactory<>("postCode"));

    streetColumn.setCellValueFactory(
        new PropertyValueFactory<>("street"));

    streetNumberColumn.setCellValueFactory(
        new PropertyValueFactory<>("streetNumber"));

    for(Contact contact: contactRegister.getObjects()){
      supplierTable.getItems().add(contact);
    }
  }
}