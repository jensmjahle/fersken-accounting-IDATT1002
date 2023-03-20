package edu.ntnu.idatt1002.registers;

import edu.ntnu.idatt1002.Expense;

public class BudgetRegister extends Register<Expense> {
  private static final String FILE_NAME = "budgets.txt";

  public BudgetRegister() {
    super(FILE_NAME);
  }

}