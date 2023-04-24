package edu.ntnu.idatt1002.registers;

import edu.ntnu.idatt1002.storageitems.Expense;

/**
 * This class represents an expense register that stores object of type "Expense".
 * The class extends from class Register.
 */
public class ExpenseRegister extends Register<Expense> {

  /**
   * Constructor that creates an instance of the class.
   *
   * @param fileName file name of the file to store data.
   */
  public ExpenseRegister(String fileName) {
    super(fileName);
  }

}
