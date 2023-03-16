package edu.ntnu.idatt1002.registers;

import edu.ntnu.idatt1002.Expense;

public class ContactRegister extends Register<Expense> {
  private static final String FILE_NAME = "expenses.txt";

  public ContactRegister() {
    super(FILE_NAME);
  }

}