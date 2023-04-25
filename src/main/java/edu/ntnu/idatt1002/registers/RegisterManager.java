package edu.ntnu.idatt1002.registers;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

/**
 * Singleton class that contains all different registers used to store information for the user.
 */
public class RegisterManager {

  private static RegisterManager instance;
  private BudgetRegister budgetRegister;
  private ContactRegister customerRegister;
  private ContactRegister supplierRegister;
  private SaleRegister saleRegister;
  private ExpenseRegister expenseRegister;
  private UserRegister userRegister;
  private String userName;

  /**
   * Private singleton constructor for the Register manager. Tries to create a directory for storing
   * users if it does not already exist. Instantiates the user register.
   */
  private RegisterManager() {

    try {
      Files.createDirectories(Path.of("src/main/java/registers"));
      userRegister = new UserRegister("src/main/java/registers" + "/users.txt");
    } catch (Exception e) {
      Alert alert = new Alert(AlertType.ERROR, "CANNOT CREATE DIRECTORY" + e);
      alert.showAndWait();
    }

  }

  /**
   * Creates a new RegisterManager if none exist already. If it already exists the method will
   * return the already created instance of the RegisterManager.
   *
   * @return The only instance of the register manager.
   */
  public static RegisterManager getInstance() {
    if (instance == null) {
      instance = new RegisterManager();
    }
    return instance;
  }

  /**
   * Get-method for the budget register.
   *
   * @return budget register
   */
  public BudgetRegister getBudgetRegister() {
    return budgetRegister;
  }

  /**
   * Get-method for the sale register.
   *
   * @return sale register
   */
  public SaleRegister getSaleRegister() {
    return saleRegister;
  }

  /**
   * Get-method for the expense register.
   *
   * @return expense register
   */
  public ExpenseRegister getExpenseRegister() {
    return expenseRegister;
  }

  /**
   * Get-method for the customer register.
   *
   * @return customer register
   */
  public ContactRegister getCustomerRegister() {
    return customerRegister;
  }

  /**
   * Get-method for the supplier register.
   *
   * @return supplier register
   */
  public ContactRegister getSupplierRegister() {
    return supplierRegister;
  }

  /**
   * Returns the user register.
   *
   * @return the user register.
   */
  public UserRegister getUserRegister() {
    return userRegister;
  }


  /**
   * Sets a new current user by providing a username, changing the registers to the registers of the
   * new user.
   *
   * @param userName The username of the new user.
   * @throws IOException If there are any files that are not found.
   */
  public void setUserName(String userName) throws IOException {
    this.userName = userName;
    createNewRegisters(userName);
  }

  /**
   * Creates a new directory for the new user if it does not exist. After, it creates a set of all
   * registers in the new directory
   *
   * @param userName The name of the selected user.
   * @throws IOException If it is impossible to create the new directory.
   */
  private void createNewRegisters(String userName) throws IOException {

    Files.createDirectories(Path.of("src/main/java/registers/" + userName));
    String startOfLocation = "src/main/java/registers/" + userName;
    this.budgetRegister = new BudgetRegister(startOfLocation + "/budgets.txt");
    this.saleRegister = new SaleRegister(startOfLocation + "/sales.txt");
    this.expenseRegister = new ExpenseRegister(startOfLocation + "/expenses.txt");
    this.customerRegister = new ContactRegister(startOfLocation + "/customers.txt");
    this.supplierRegister = new ContactRegister(startOfLocation + "/suppliers.txt");
  }

  /**
   * Returns the username of the current user.
   *
   * @return The username of the current user.
   */
  public String getUserName() {
    return userName;
  }

  /**
   * Sets the current user to null.
   */
  public void logOut() {
    this.userName = null;
  }
}
