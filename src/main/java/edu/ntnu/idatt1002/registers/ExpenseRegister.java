package edu.ntnu.idatt1002.registers;

import edu.ntnu.idatt1002.Expense;

public class ExpenseRegister extends Register<Expense> {
  private static final String FILE_NAME = "expenses.txt";

  public ExpenseRegister() {
    super(FILE_NAME);
  }

}
