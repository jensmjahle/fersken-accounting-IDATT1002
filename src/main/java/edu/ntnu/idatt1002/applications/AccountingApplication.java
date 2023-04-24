package edu.ntnu.idatt1002.applications;

import edu.ntnu.idatt1002.registers.RegisterManager;
import edu.ntnu.idatt1002.storageitems.User;
import edu.ntnu.idatt1002.registers.UserRegister;
import java.io.File;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;


public class AccountingApplication extends Application {

  private static final Logger LOGGER = Logger.getLogger(AccountingApplication.class.getName());

  public static void main(String[] args) {

    launch(args);
  }

  @Override
  @SuppressWarnings("SpellCheckingInspection")
  public void start(Stage primaryStage) {
    try {

      FXMLLoader loader = new FXMLLoader();
      //addAdminUserManually();
      URL url = new File("src/main/resources/pages/Login.fxml").toURI().toURL();
      loader.setLocation(url);
      Pane pane = loader.load();

      Scene scene = new Scene(pane);

      primaryStage.setTitle("Fersken Regnskap");
      primaryStage.setScene(scene);
      primaryStage.show();
      primaryStage.setResizable(false);
      setIcon(primaryStage);
    } catch (Exception e) {
      Alert alert = new Alert(AlertType.ERROR,
          "Cannot start application, please contact customer support");
      alert.showAndWait();
      LOGGER.log(Level.SEVERE, "Unknown Error in main class" + e);
    }
  }

  private void setIcon(Stage primaryStage) {
    try {
      URL url2 = new File("src/main/resources/Icons/peach.png").toURI().toURL();
      primaryStage.getIcons().add(new Image(url2.toString()));
    } catch (Exception e) {
      LOGGER.log(Level.FINE, "Could not add icon to application because: " + e);
    }

  }

  /**
   * Adds an admin user, and sets the current user to the admin.
   */
  private void addAdminUserManually() {
    UserRegister userRegister = RegisterManager.getInstance().getUserRegister();
    try {
      if (userRegister.userNameAlreadyExists("Admin")) {
        userRegister.addObject(new User("Admin", "Admin123"));
      }
      RegisterManager.getInstance().setUserName("Admin");
    } catch (Exception e) {
      Alert alert = new Alert(AlertType.ERROR,
          "Critical error while reading user files \n please contact customer support" + e);
      alert.showAndWait();
    }
  }
}