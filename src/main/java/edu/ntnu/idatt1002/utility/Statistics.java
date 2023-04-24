package edu.ntnu.idatt1002.utility;


import edu.ntnu.idatt1002.registers.ExpenseRegister;
import edu.ntnu.idatt1002.registers.RegisterManager;
import edu.ntnu.idatt1002.registers.SaleRegister;
import edu.ntnu.idatt1002.storageitems.Expense;
import edu.ntnu.idatt1002.storageitems.Sale;
import java.text.SimpleDateFormat;
import java.time.Year;
import java.time.YearMonth;
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
   * Returns the sum for all sale prices summed up for a specified month.
   *
   * @param month The month used to find the total sale price.
   * @return The total sale price for the specified month
   */
  @SuppressWarnings({"deprecation", "MagicConstant"})
  public double getSaleTotalForMonth(YearMonth month) {
    return saleRegister.getObjects().stream().filter(sale -> sale.getDate() != null).filter(
            sale -> sale.getDate().getMonth() == month.getMonthValue() - 1
                && sale.getDate().getYear() + 1900 == month.getYear()).mapToDouble(Sale::getAmount)
        .sum();
  }

  /**
   * Returns the sum for all expense costs summed up for a specified month.
   *
   * @param month The month used to find the total expense cost.
   * @return The total expense cost for the specified month
   */
  @SuppressWarnings({"deprecation", "MagicConstant"})
  public double getExpenseTotalForMonth(YearMonth month) {
    return expenseRegister.getObjects().stream().filter(expense -> expense.getDate() != null
            && expense.getDate().getMonth() == month.getMonthValue() - 1
            && expense.getDate().getYear() + 1900 == month.getYear())
        .mapToDouble(Expense::getAmount)
        .sum();
  }

  /**
   * Finds out the difference between the sales sum and expense sum for a specified month.
   *
   * @param month The month that will be used to find the difference between sales and expenses.
   * @return The difference between the sales sum and expense sum for a specified month.
   */
  public double getDifferenceTotalForMonth(YearMonth month) {
    return getSaleTotalForMonth(month) - getExpenseTotalForMonth(month);
  }

  /**
   * Finds out the difference between the sales sum and expense sum for a specified day.
   *
   * @param date Date that will be used to find the difference between sales and expenses.
   * @return The difference between the sales sum and expense sum for a specified day.
   */
  public double getDifferenceForDay(Date date) {
    return getSaleTotalForDay(date) - getExpenseTotalForDay(date);
  }

  /**
   * Gets the sale total for all sales between two specified dates.
   *
   * @param startDate The start time of the period to calculate.
   * @param endDate   The end time of the period to calculate.
   * @return The sale total for all sales that occur between the specified dates
   */
  public double getSaleTotalForTimePeriod(Date startDate, Date endDate) {
    return saleRegister.getObjects().stream().filter(sale -> sale.getDate() != null)
        .filter(sale -> !sale.getDate().before(startDate) && !sale.getDate().after(endDate))
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
    return expenseRegister.getObjects().stream().filter(expense -> expense.getDate() != null)
        .filter(
            expense -> !expense.getDate().before(startDate) && !expense.getDate().after(endDate))
        .mapToDouble(Expense::getAmount).sum();
  }

  /**
   * Finds out the difference between sales sum and expense sum for a specified time period.
   *
   * @param startDate The start date of the time period.
   * @param endDate   The end date of the time period.
   * @return the difference between sales sum and expense sum for a specified time period.
   */
  public double getDifferenceForTimePeriod(Date startDate, Date endDate) {
    return getSaleTotalForTimePeriod(startDate, endDate) - getExpenseTotalForTimePeriod(startDate,
        endDate);
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
   * Returns the sum for all sale prices summed up for a specified year.
   *
   * @param year The year used to find the total sale price.
   * @return The total sale price for the specified year
   */
  @SuppressWarnings("deprecation")
  public double getSaleTotalForYear(Year year) {
    return saleRegister.getObjects().stream().filter(sale -> sale.getDate() != null)
        .filter(sale -> sale.getDate().getYear() + 1900 == year.getValue())
        .mapToDouble(Sale::getAmount).sum();
  }

  /**
   * Returns the sum for all expense costs summed up for a specified year.
   *
   * @param year The year used to find the total sale price.
   * @return The total expense costs for the specified year
   */
  @SuppressWarnings("deprecation")
  public double getExpenseTotalForYear(Year year) {
    return expenseRegister.getObjects().stream().filter(expense -> expense.getDate() != null)
        .filter(expense -> expense.getDate().getYear() + 1900 == year.getValue())
        .mapToDouble(Expense::getAmount).sum();
  }

  /**
   * Finds the total difference between the sale sum and expense sum for a specified year.
   *
   * @param year The year used to calculate the total difference for.
   * @return the total difference between the sale sum and expense sum for a specified year.
   */
  public double getDifferenceTotalForYear(Year year) {
    return getSaleTotalForYear(year) - getExpenseTotalForYear(year);
  }

  /**
   * Finds out the total expense amount for a specified date.
   *
   * @param day The day that will be used to find the total expense amount
   * @return The total sales amount for the specified date.
   */
  public double getExpenseTotalForDay(Date day) {
    return expenseRegister.getObjects().stream().filter(expense -> expense.getDate() != null)
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
