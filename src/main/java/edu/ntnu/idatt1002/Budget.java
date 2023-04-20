package edu.ntnu.idatt1002;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * The Budget class represents a budget with lists of expenses and sales, used to keep track of the
 * different projects of the user.
 */
public class Budget implements Serializable {

  private String projectName;
  private final ArrayList<Expense> listOfExpenses;
  private final ArrayList<Sale> listOfSales;


  /**
   * Constructor that initializes a budget. throws exception is project name is empty or blank
   *
   * @param project name of project budget is connected to
   */
  public Budget(String project) {
    if (project.isEmpty()) {
      throw new IllegalArgumentException("Project cannot be empty");
    }
    setProject(project);
    this.listOfExpenses = new ArrayList<>();
    this.listOfSales = new ArrayList<>();
  }

  /**
   * Get-method for the name of project budget is connected to.
   *
   * @return name of the project
   */
  public String getProjectName() {
    return projectName;
  }

  /**
   * Set-method for new name for project.
   *
   * @param project new name for project
   */

  public void setProject(String project) {
    if (project.isEmpty() || project.isBlank()) {
      throw new IllegalArgumentException("Budget has to be connected to a specific project");
    }
    this.projectName = project;
  }

  /**
   * Get-method for list of expenses.
   *
   * @return list of expenses in the budget
   */

  public List<Expense> getListOfExpenses() {
    return listOfExpenses;
  }

  /**
   * Method for adding a list of expenses to the budget.
   *
   * @param expenses list of expenses
   */
  public void addListOfExpenses(List<Expense> expenses) {
    listOfExpenses.addAll(expenses);
  }

  /**
   * Method for adding a single expense to the budget.
   *
   * @param newExpense expense to be added to budget
   */
  public void addExpense(Expense newExpense) {
    listOfExpenses.add(newExpense);
  }

  /**
   * Get-method for list of sales.
   *
   * @return list of sales
   */
  public List<Sale> getListOfSales() {
    return listOfSales;
  }

  /**
   * Method for adding a list of sales to the budget.
   *
   * @param sales list of sales
   */
  public void addListOfSales(List<Sale> sales) {
    listOfSales.addAll(sales);
  }

  /**
   * Method for adding a single sale to the budget.
   *
   * @param newSale sale to be added to budget
   */
  public void addSale(Sale newSale) {
    listOfSales.add(newSale);
  }

  /**
   * Gets the total expense sum for the expense costs in the budget.
   *
   * @return The total expense sum for the budget.
   */
  public double getSumOfExpenses() {
    return listOfExpenses.stream().mapToDouble(Expense::getAmount).sum();
  }

  /**
   * Gets the total sum of all sale amounts in the budget.
   *
   * @return The total sale sum for the budget.
   */
  public double getSumOfSales() {
    return listOfSales.stream().mapToDouble(Sale::getAmount).sum();
  }

  /**
   * Finds out the difference between sale sum and expense sum for the project.
   *
   * @return The difference between sale sum and expense sum for the project.
   */
  public double getDifference() {
    return getSumOfSales() - getSumOfExpenses();
  }

  /**
   * Creates a string that holds budget info.
   *
   * @return A string containing budget info.
   */
  @Override
  public String toString() {
    return "Budget{" + "project='" + projectName + '\'' + ", listOfExpenses=" + listOfExpenses
        + ", listOfSales=" + listOfSales + '}';
  }
}
