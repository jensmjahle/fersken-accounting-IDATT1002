package edu.ntnu.idatt1002.utility;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Public class for getting resolving resource paths for fxml files.
 */
public class PathUtility {

  /**
   * Private constructor that prohibits the creation of a PathUtility object.
   */
  private PathUtility() {
    throw new UnsupportedOperationException("Cannot create path utility object");
  }

  /**
   * Gets the resource path of the specified location.
   *
   * @param location Name of the fxml file located in the pages folder in resources.
   * @return The resource path of the specified file.
   * @throws MalformedURLException If the resource path is invalid
   * @throws NullPointerException  If a file cannot be created from the path because of an error.
   */
  public static URL getResourcePath(String location) throws MalformedURLException {
    String partOfPath = "src/main/resources/pages/" + location + ".fxml";
    return new File(partOfPath).toURI().toURL();
  }

}
