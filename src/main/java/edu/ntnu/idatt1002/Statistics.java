package edu.ntnu.idatt1002;


import edu.ntnu.idatt1002.registers.ExpenseRegister;
import edu.ntnu.idatt1002.registers.SaleRegister;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * The Statistics class contains methods to calculate statistics of the different registers in the
 * application.
 */
public class Statistics {

  SaleRegister saleRegister;
  ExpenseRegister expenseRegister;

  /**
   * Constructor for the Statistics class.
   *
   * @param registerManager the RegisterManager object used to calculate statistics on.
   */
  public Statistics(RegisterManager registerManager) {
    this.expenseRegister = registerManager.getExpenseRegister();
    this.saleRegister = registerManager.getSaleRegister();
  }

  /**
   * Finds out the total sale sum for a specified month.
   *
   * @param month The month that will be used to calculate the total sum for that month.
   * @return The total sale sum for the specified month.
   */
  public double getSaleTotalForMonth(int month) {

    return saleRegister.getObjects().stream().filter(sale -> sale.getDate().getMonth() == month)
        .mapToDouble(Sale::getAmount).sum();
  }

  /**
   * Finds out the total expense sum for a specified month.
   *
   * @param month The month that will be used to calculate the total expenses for that month.
   * @return The total expense sum for the specified month.
   */
  public double getExpenseTotalForMonth(int month) {

    return expenseRegister.getObjects().stream()
        .filter(expense -> expense.getDate().getMonth() == month).mapToDouble(Expense::getAmount)
        .sum();
  }

  /**
   * Finds out the difference between sales and expenses for a given month.
   *
   * @param month The month that will be used to calculate the difference.
   * @return The total difference between sales and expenses for the given month.
   */
  public double getDifferenceForMonth(int month) {
    return getSaleTotalForMonth(month) - getExpenseTotalForMonth(month);
  }

  /**
   * Gets the sale total for all sales between two specified dates.
   *
   * @param startDate The start time of the period to calculate.
   * @param endDate   The end time of the period to calculate.
   * @return The sale total for all sales that occur between the specified dates
   */
  public double getSaleTotalForTimePeriod(Date startDate, Date endDate) {
    return saleRegister.getObjects().stream()
        .filter(sale -> sale.getDate().after(startDate) && sale.getDate().before(endDate))
        .mapToDouble(Sale::getAmount).sum();
  }

  /**
   * Gets the expense total for all expenses between two specified dates.
   *
   * @param startDate The start time of the period to calculate.
   * @param endDate   The end time of the period to calculate.
   * @return The expense total for all expenses that occur between the specified dates
   */
  public double getExpenseTotalForTimePeriod(Date startDate, Date endDate) {
    return expenseRegister.getObjects().stream()
        .filter(expense -> expense.getDate().after(startDate) && expense.getDate().before(endDate))
        .mapToDouble(Expense::getAmount).sum();
  }

  /**
   * Finds out the total sales amount for a specified date.
   *
   * @param day The day that will be used to find the total sales amount
   * @return The total sales amount for the specified date.
   */
  public double getSaleTotalForDay(Date day) {
    return saleRegister.getObjects().stream().filter(sale -> isSameDate(sale.getDate(), day))
        .mapToDouble(Sale::getAmount).sum();
  }

  /**
   * Finds out the total expense amount for a specified date.
   *
   * @param day The day that will be used to find the total expense amount
   * @return The total sales amount for the specified date.
   */
  public double getExpenseTotalForDay(Date day) {
    return expenseRegister.getObjects().stream()
        .filter(expense -> isSameDate(expense.getDate(), day)).mapToDouble(Expense::getAmount)
        .sum();
  }

  /**
   * Checks if two dates happen at the same calendar date, but not necessarily at the same time of
   * day.
   *
   * @param date          The first date to compare.
   * @param dateToCompare The date that the first date will be compared to
   * @return true if the two date represent the same calendar date, false otherwise.
   */
  private boolean isSameDate(Date date, Date dateToCompare) {
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
    return dateFormat.format(date).equals(dateFormat.format(dateToCompare));
  }

}
