package edu.ntnu.idatt1002.viewmanagement;

public enum View {
  MAIN_MENU("MainMenu"),
  CREATE_BUDGET("CreateBudget"),
  CREATE_CUSTOMER("CreateCustomer"),
  CREATE_SUPPLIER("CreateSupplier"),
  EDIT_CUSTOMER("EditCustomer"),
  EDIT_EXPENSE("EditExpense"),
  EDIT_SALE("EditSale"),
  EDIT_SUPPLIER("EditSupplier"),
  EDIT_BUDGET("EditBudget"),
  LIST_OF_ALL_BUDGETS("ListOfAllBudgets"),
  LIST_OF_ALL_CUSTOMERS("ListOfAllCustomers"),
  LIST_OF_ALL_EXPENSES("ListOfAllExpenses"),
  LIST_OF_ALL_SALES("ListOfAllSales"),
  LIST_OF_ALL_SUPPLIERS("ListOfAllSuppliers"),
  REGISTER_EXPENSE("CreateExpense"),
  REGISTER_SALE("CreateSale"),
  STATS("Stats");

  private final String fileName;

  View(String fileName) {
    this.fileName = fileName;
  }

public String getFileName() {
    return fileName;
  }
}