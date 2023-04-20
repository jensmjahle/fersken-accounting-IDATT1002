package edu.ntnu.idatt1002;

import edu.ntnu.idatt1002.registers.BudgetRegister;
import edu.ntnu.idatt1002.registers.ContactRegister;
import edu.ntnu.idatt1002.registers.ExpenseRegister;
import edu.ntnu.idatt1002.registers.SaleRegister;
import edu.ntnu.idatt1002.registers.UserRegister;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

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
  private final UserRegister userRegister;
  private String userName;

  /**
   * Private singleton constructor for the Register manager. Tries to create a directory for storing
   * users if it does not already exist. Instantiates the user register.
   */
  private RegisterManager() {
    try {
      Files.createDirectories(Path.of("src/main/resources/registers/users"));
    } catch (Exception e) {
      e.printStackTrace();
    }
    this.userRegister = new UserRegister("users");
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

    Files.createDirectories(Path.of("src/main/resources/registers/" + userName));

    this.budgetRegister = new BudgetRegister(userName + "/budgets");
    this.saleRegister = new SaleRegister(userName + "/sales");
    this.expenseRegister = new ExpenseRegister(userName + "/expenses");
    this.customerRegister = new ContactRegister(userName + "/customers");
    this.supplierRegister = new ContactRegister(userName + "/suppliers");
  }

  /**
   * Returns the username of the current user.
   *
   * @return The username of the current user.
   */
  public String getUserName() {
    return userName;
  }

}
