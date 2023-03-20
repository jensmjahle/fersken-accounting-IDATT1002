package edu.ntnu.idatt1002;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Budget class with variables that make up a budget.
 */
public class Budget implements Serializable {
  private String project;
  private final ArrayList<Expense> listOfExpenses;
  private final ArrayList<Sale> listOfSales;

  /**
   * Constructor that initializes a budget.
   * throws exception is project name is empty or blank
   *
   * @param project name of project budget is connected to
   */
  public Budget(String project) {

    setProject(project);
    this.listOfExpenses = new ArrayList<>();
    this.listOfSales = new ArrayList<>();
  }

  /**
   * Get-method for the name of project budget is connected to.
   *
   * @return name of the project
   */
  public String getProject() {
    return project;
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
    this.project = project;
  }

  /**
   * Get-method for list of expenses.
   *
   * @return list of expenses in the budget
   */

  public ArrayList<Expense> getListOfExpenses() {
    return listOfExpenses;
  }

  /**
   * Method for adding a list of expenses to the budget.
   *
   * @param expenses list of expenses
   */
  public void setListOfExpenses(ArrayList<Expense> expenses) {
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
  public ArrayList<Sale> getListOfSales() {
    return listOfSales;
  }

  /**
   * Method for adding a list of sales to the budget.
   *
   * @param sales list of sales
   */
  public void setListOfSales(ArrayList<Sale> sales) {
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
}
