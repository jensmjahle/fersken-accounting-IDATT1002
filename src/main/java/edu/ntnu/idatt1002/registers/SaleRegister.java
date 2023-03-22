package edu.ntnu.idatt1002.registers;

import edu.ntnu.idatt1002.Sale;

/**
 * This class represents a sale register that stores object of type "Sale".
 * The class extends from class Register.
 */
public class SaleRegister extends Register<Sale> {

  /**
   * Constructor that creates an instance of the class.
   *
   * @param fileName file name of the file to store data.
   */
  public SaleRegister(String fileName) {
    super(fileName);
  }
}