package edu.ntnu.idatt1002;

import java.io.File;
import java.net.URL;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;


public class Main extends Application {

  public static void main(String[] args) {
    launch(args);
  }

  @Override
  public void start(Stage primaryStage) throws Exception {
    FXMLLoader loader = new FXMLLoader();

    URL url = new File("src/main/resources/pages/Login.fxml").toURI().toURL();

    loader.setLocation(url);

    BorderPane borderPane = loader.load();

    Scene scene = new Scene(borderPane);
    primaryStage.setTitle("Fersken Regnskap");
    primaryStage.setScene(scene);
    primaryStage.show();
    primaryStage.setResizable(false);
  }
}