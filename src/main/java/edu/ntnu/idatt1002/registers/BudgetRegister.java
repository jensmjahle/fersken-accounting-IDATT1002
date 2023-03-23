package edu.ntnu.idatt1002.registers;

import edu.ntnu.idatt1002.Budget;

/**
 * This class represents a budget register that stores object of type "Budget".
 * The class extends from class Register.
 */
public class BudgetRegister extends Register<Budget> {

  /**
   * Constructor that creates an instance of the class.
   *
   * @param fileName file name of the file to store data.
   */
  public BudgetRegister(String fileName) {
    super(fileName);
  }

}