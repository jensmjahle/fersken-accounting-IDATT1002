package edu.ntnu.idatt1002.registers;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * This class is a super class for all register classes.
 * It stores data of objects that must be defined in subclasses.
 *
 * @param <T> Object type of witch the register stores. Must be defined in subclasses.
 */
public class Register <T>{
  private final String fileName;
  private final List<T> objectRegister;

  /**
   * Constructor for the super class that is used to create instances of subclasses of Register.
   * It reads data stored in resources from the given file name if the file exists.
   * This data is then added to the register.
   *
   * @param fileName The file name of where to store the register. Used as filepath
   */
  protected Register(String fileName){
    Objects.requireNonNull(fileName, "File name cannot be null");
    this.fileName = "src/main/resources/registers/" + Objects.requireNonNull(fileName, "File name cannot be null.") + ".txt";
      objectRegister = new ArrayList<>(readFiles());
  }

  /**
   * This method saves all objects stored in the register to a .txt file in resources.
   * It uses fileName as the filepath.
   */
  private void writeToFile(){
    try (FileOutputStream output = new FileOutputStream(fileName)){

      ObjectOutputStream objectOutput = new ObjectOutputStream(output);


      objectOutput.writeObject(objectRegister);

      objectOutput.close();


    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * Retrieves a list of objects from a .txt file stored in resources.
   * It uses fileName as the filepath.
   * If the file does not exist it wil return an empty ArrayList.
   *
   * @return A list of objects from a .txt file if the file exists or an empty ArrayList if not
   */
  private List<T> readFiles() {
    ArrayList<T> fileData = new ArrayList<>();


     File file = new File(fileName);
    if (file.exists()) {

       try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fileName))) {

         fileData = (ArrayList<T>) ois.readObject();

       } catch (java.io.IOException | ClassNotFoundException e) {
         e.printStackTrace();

       }

     }
    return fileData;
  }

  /**
   * This method removes an object of type T from the objectRegister list.
   * It returns true if the removal is successful and false if the removal is unsuccessful.
   *
   * @param objectToRemove Object to be removed from objectRegister. Object of type T
   *
   * @return True if the removal is successful and false if the removal is unsuccessful
   */
  public boolean removeObject(T objectToRemove){
    Objects.requireNonNull(objectToRemove, "Cannot remove null object from register");
    for (T object : objectRegister){
      if (object.equals(objectToRemove)){
        objectRegister.remove(objectToRemove);
        writeToFile();
        return true;
      }
    }
    return false;
  }

  /**
   * Retrieves a list of all T objects stored in the objectRegister.
   *
   * @return A list of all T objects in objectRegister
   */
  public List<T> getObjects() {
    return objectRegister;
  }

  /**
   * Adds an object to the objectRegister.
   *
   * @param object to be added
   */
  public void addObject(T object) {
    Objects.requireNonNull(object, "Cannot add null object to register");
    objectRegister.add(object);
    writeToFile();
  }
}
