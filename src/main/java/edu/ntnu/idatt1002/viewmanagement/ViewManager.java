package edu.ntnu.idatt1002.viewmanagement;

import edu.ntnu.idatt1002.utility.PathUtility;
import java.util.logging.Logger;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.InputEvent;
import javafx.stage.Stage;

public class ViewManager {

  private static Scene scene;
  private static final Logger LOGGER = Logger.getLogger(ViewManager.class.getName());

  public static void setScene(Scene scene) {
    ViewManager.scene = scene;
  }

  public static void switchToScene(InputEvent event, View view) {
    try {
      Parent root = FXMLLoader.load(PathUtility.getResourcePath(view.getFileName()));
      Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
      scene = new Scene(root);
      stage.setScene(scene);
      stage.show();
    } catch (Exception e) {
      LOGGER.warning("Error while switching scene because: " + e.getMessage());
      Alert alert = new Alert(AlertType.ERROR,
          "Cannot switch to selected page\nPlease contact customer service");
      alert.showAndWait();
    }
  }
}
