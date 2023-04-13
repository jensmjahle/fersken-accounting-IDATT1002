package edu.ntnu.idatt1002.viewmanagement;

public enum View {
  MAINMENU("MainMenu"),
  CREATEBUDGET("CreateBudget"),
  CREATECUSTOMER("CreateCustomer"),
  CREATESUPPLIER("CreateSupplier"),
  LISTOFALLBUDGETS("ListOfAllBudgets"),
  LISTOFALLCUSTOMERS("ListOfAllCustomers"),
  LISTOFALLEXPENSES("ListOfAllExpenses"),
  LISTOFALLSALES("ListOfAllSales"),
  LISTOFALLSUPPLIERS("ListOfAllSuppliers"),
  REGISTEREXPENSE("RegisterExpense"),
  REGISTERSALE("RegisterSale"),
  STATS("Stats");

  private String fileName;

  View(String fileName) {
    this.fileName = fileName;
  }

public String getFileName() {
    return fileName;
  }
}
