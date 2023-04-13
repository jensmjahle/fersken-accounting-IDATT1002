package edu.ntnu.idatt1002.viewmanagement;

import edu.ntnu.idatt1002.PathUtility;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class ViewManager {

  private static Scene scene;

  private static Stage stage;

  public static void setScene(Scene scene) {ViewManager.scene = scene;}

  public static void switchToScene(MouseEvent event, View view) throws IOException {
    Parent root = FXMLLoader.load(PathUtility.getResourcePath(view.getFileName()));
    stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    scene = new Scene(root);
    stage.setScene(scene);
    stage.show();
  }
}
