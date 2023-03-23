package edu.ntnu.idatt1002;

import edu.ntnu.idatt1002.registers.BudgetRegister;
import edu.ntnu.idatt1002.registers.ContactRegister;
import edu.ntnu.idatt1002.registers.ExpenseRegister;
import edu.ntnu.idatt1002.registers.SaleRegister;

/**
 * Singleton class that contains all different registers used to store information for the user.
 */
public class RegisterManager {

  private static RegisterManager instance;
  private final BudgetRegister budgetRegister;
  private final ContactRegister customerRegister;
  private final ContactRegister supplierRegister;
  private final SaleRegister saleRegister;
  private final ExpenseRegister expenseRegister;

  private RegisterManager() {
    this.budgetRegister = new BudgetRegister("budgets");
    this.saleRegister = new SaleRegister("sales");
    this.expenseRegister = new ExpenseRegister("expenses");
    this.customerRegister = new ContactRegister("customers");
    this.supplierRegister = new ContactRegister("suppliers");
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


}
