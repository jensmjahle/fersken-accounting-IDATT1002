package edu.ntnu.idatt1002;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Budget class with variables that make up a budget.
 */
public class Budget implements Serializable {
  private String projectName;
  private final ArrayList<Expense> listOfExpenses;
  private final ArrayList<Sale> listOfSales;
  private double sumOfExpenses;
  private double sumOfSales;
  private double difference;

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

  public double sumOfExpenses() {
    double sum = 0;
    for(Expense expense: listOfExpenses) {
      sum += expense.getAmount();
    }

    return sum;
  }

  public double sumOfSales() {
    double sum = 0;
    for(Sale sale: listOfSales) {
      sum += sale.getAmount();
    }

    return sum;
  }

  public double getSumOfExpenses() {
    return sumOfExpenses();
  }

  public double getSumOfSales() {
    return sumOfSales();
  }

  public double getDifference() {
    return sumOfSales() - sumOfExpenses();
  }
  /**
   * Creates a string that holds budget info.
   *
   * @return A string containing budget info.
   */
  @Override
  public String toString() {
    return "Budget{" +
        "project='" + projectName + '\'' +
        ", listOfExpenses=" + listOfExpenses +
        ", listOfSales=" + listOfSales +
        '}';
  }
}
