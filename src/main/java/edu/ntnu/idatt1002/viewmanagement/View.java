package edu.ntnu.idatt1002.viewmanagement;

/**
 * Enum for the file name of the different fxml files in the application.
 */
public enum View {
  CREATE_BUDGET("CreateBudget"),
  CREATE_CUSTOMER("CreateCustomer"),
  CREATE_EXPENSE("CreateExpense"),
  CREATE_SALE("CreateSale"),
  CREATE_SUPPLIER("CreateSupplier"),
  CREATE_USER("CreateUser"),
  EDIT_BUDGET("EditBudget"),
  EDIT_CUSTOMER("EditCustomer"),
  EDIT_EXPENSE("EditExpense"),
  EDIT_SALE("EditSale"),
  EDIT_SUPPLIER("EditSupplier"),
  LIST_OF_ALL_BUDGETS("ListOfAllBudgets"),
  LIST_OF_ALL_CUSTOMERS("ListOfAllCustomers"),
  LIST_OF_ALL_EXPENSES("ListOfAllExpenses"),
  LIST_OF_ALL_SALES("ListOfAllSales"),
  LIST_OF_ALL_SUPPLIERS("ListOfAllSuppliers"),
  LOGIN("Login"),
  MAIN_MENU("MainMenu"),
  OPEN_BUDGET("OpenBudget"),
  STATS("Stats");

  private final String fileName;

  /**
   * Constructs a new View enum with the given file name.
   *
   * @param fileName the file name connected to the enum constant
   */
  View(String fileName) {
    this.fileName = fileName;
  }

  /**
   * Returns the filename of the enum.
   *
   * @return The filename corresponding to the enum.
   */
  public String getFileName() {
    return fileName;
  }
}
