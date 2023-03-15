package edu.ntnu.idatt1002.registers;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

public class Register <T>{
  private final String fileName;
  private final ArrayList<T> objectRegister;

  protected Register(String fileName){
    this.fileName = fileName;
    objectRegister = new ArrayList<>();
  }


  public void writeToFile(){
    try (FileOutputStream output = new FileOutputStream(fileName, false)){

      ObjectOutputStream objectOutput = new ObjectOutputStream(output);


      objectOutput.writeObject(objectRegister);

      objectOutput.close();


    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public List<T> readFiles() {
    ArrayList<T> fileData = new ArrayList<>();
    try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fileName))) {



      fileData = (ArrayList<T>) ois.readObject();

    } catch (java.io.IOException | ClassNotFoundException e) {
      e.printStackTrace();
    }
    return fileData;
  }

  public boolean removeObject(T objectToRemove){
    for (T object : objectRegister){
      if (object.equals(objectToRemove)){
        objectRegister.remove(objectToRemove);
        return true;
      }
    }
    return false;
  }

  public List<T> getObjects() {
    return objectRegister;
  }

  /**
   * Adds an object to the objectRegister.
   *
   * @param object to be added.
   */
  public void addObject(T object) {
    objectRegister.add(object);
  }

}
