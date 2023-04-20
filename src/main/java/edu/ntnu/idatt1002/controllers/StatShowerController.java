package edu.ntnu.idatt1002.controllers;

import edu.ntnu.idatt1002.RegisterManager;
import edu.ntnu.idatt1002.Statistics;
import edu.ntnu.idatt1002.viewmanagement.View;
import edu.ntnu.idatt1002.viewmanagement.ViewManager;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.Year;
import java.time.YearMonth;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart.Data;
import javafx.scene.chart.XYChart.Series;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

/**
 * Class that represents a controller for the Stats.fxml file. Enables a graph containing expenses,
 * sales, and the difference between them to be shown and changed according to the user's wishes.
 */
public class StatShowerController implements Initializable {

  public static final String LINE_CHART = "Line Chart";
  public static final String BAR_CHART = "Bar Chart";
  private static final Logger LOGGER = Logger.getLogger(StatShowerController.class.getName());
  @FXML
  private Text differenceInPeriodText;
  @FXML
  private Text saleInPeriodText;
  @FXML
  private Text expenseInPeriodText;
  @FXML
  private Pane chartPane;
  @FXML
  private ChoiceBox<String> graphChooser;
  @FXML
  private ChoiceBox<String> dataSpacingChooser;
  @FXML
  private DatePicker datePicker1;
  @FXML
  private DatePicker datePicker2;
  @FXML
  private CheckBox showExpenses;
  @FXML
  private CheckBox showSales;
  @FXML
  private CheckBox showDifference;

  private Date firstSelectedDate;
  private Date secondSelectedDate;
  private String selectedChartType;
  private String selectedSpacing;

  private Series<String, Number> incomeInPeriod;
  private Series<String, Number> expenseInPeriod;
  private Series<String, Number> differenceInPeriod;


  /**
   * Initializes an instance of this controller class. Sets default information
   *
   * @param url            The url of the fxml file.
   * @param resourceBundle The ResourceBundle for the FXML file.
   */
  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {

    incomeInPeriod = new Series<>();
    expenseInPeriod = new Series<>();
    differenceInPeriod = new Series<>();
    setNamesForSeries();

    setDefaultDates();
    setChoiceBoxOptions();
    updateChart();
    setPeriodInfo();

  }

  /**
   * Sets the name for the sales, expenses and differences series.
   */
  private void setNamesForSeries() {
    incomeInPeriod.setName("Sales");
    expenseInPeriod.setName("Expenses");
    differenceInPeriod.setName("Difference");

  }

  /**
   * Sets the text information about sales, expenses and their difference in the currently specified
   * time period.
   */
  private void setPeriodInfo() {
    Statistics statistics = new Statistics(RegisterManager.getInstance());
    expenseInPeriodText.setText(
        "Expense total: " + statistics.getExpenseTotalForTimePeriod(firstSelectedDate,
            secondSelectedDate));
    saleInPeriodText.setText(
        "Sale total: " + statistics.getSaleTotalForTimePeriod(firstSelectedDate,
            secondSelectedDate));
    differenceInPeriodText.setText(
        "Difference: " + statistics.getDifferenceForTimePeriod(firstSelectedDate,
            secondSelectedDate));

  }

  /**
   * Fills the choice boxes with options and sets them to their default values.
   */
  private void setChoiceBoxOptions() {
    graphChooser.getItems().addAll(LINE_CHART, BAR_CHART);
    dataSpacingChooser.getItems().addAll("Days", "Months", "Years");
    graphChooser.setValue(LINE_CHART);
    dataSpacingChooser.setValue("Days");
  }

  /**
   * Sets the default dates to the first and second date of the current month.
   */
  private void setDefaultDates() {

    datePicker1.setValue(LocalDate.now().withDayOfMonth(1));
    datePicker2.setValue(LocalDate.now().withDayOfMonth(LocalDate.now().lengthOfMonth()));
    handleDatePicker1();
    handleDatePicker2();
  }

  /**
   * Switches the application back to the main menu.
   *
   * @param event Event to trigger the action.
   * @throws IOException If the location of the main menu scene is invalid.
   */
  public void switchToMainMenuScene(MouseEvent event) throws IOException {
    ViewManager.switchToScene(event, View.MAIN_MENU);
  }

  /**
   * Handles the graph chooser so that the chosen graph type is retrieved.
   */
  public void handleGraphChooser() {
    selectedChartType = graphChooser.getValue();

    try {
      updateChart();

    } catch (Exception e) {
      Alert alert = new Alert(AlertType.WARNING, "Error updating chart");
      alert.showAndWait();
    }
  }

  /**
   * Sets the selected spacing to the spacing selected by the user.
   */
  public void handleSpacingChooser() {
    selectedSpacing = dataSpacingChooser.getValue();
  }

  /**
   * Handles the first datepicker so that the chosen date is retrieved.
   */
  public void handleDatePicker1() {
    firstSelectedDate = Date.from(
        datePicker1.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
  }

  /**
   * Handles the second datepicker so that the chosen date is retrieved.
   */
  public void handleDatePicker2() {
    secondSelectedDate = Date.from(
        datePicker2.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
  }

  /**
   * Updates the chart to include current data.
   */
  public void updateChart() {
    clearCurrentData();

    switchDatesIfWrongOrder();

    try {
      switch (selectedSpacing) {
        case "Months" -> updateDataSetsMonths(firstSelectedDate, secondSelectedDate);
        case "Years" -> updateDataSetsYears(firstSelectedDate, secondSelectedDate);
        default -> updateDataSetDays(firstSelectedDate, secondSelectedDate);
      }
    } catch (Exception e) {
      LOGGER.info("Error in switch" + e);
    }
    List<Series<String, Number>> dataSetsToAdd = filterSelectedLines();

    if (selectedChartType.equals(BAR_CHART)) {
      createBarChart(dataSetsToAdd);
    }
    if (selectedChartType.equals(LINE_CHART)) {
      createLineChart(dataSetsToAdd);
    }
    setPeriodInfo();


  }

  /**
   * Switches the first and selected dates if the first is not before the second.
   */
  private void switchDatesIfWrongOrder() {
    if (!firstSelectedDate.before(secondSelectedDate)) {
      Date tempDate = firstSelectedDate;
      firstSelectedDate = secondSelectedDate;
      secondSelectedDate = tempDate;
    }
  }

  /**
   * Filters the list of data series depending on which sets the user wants to see.
   *
   * @return A list of data series that contain only the data series that the user wants to have
   *        shown in the chart.
   */
  private List<Series<String, Number>> filterSelectedLines() {
    List<Series<String, Number>> dataSetsToAdd = new ArrayList<>();

    if (showExpenses.isSelected()) {
      dataSetsToAdd.add(expenseInPeriod);
    }
    if (showSales.isSelected()) {
      dataSetsToAdd.add(incomeInPeriod);
    }
    if (showDifference.isSelected()) {
      dataSetsToAdd.add(differenceInPeriod);
    }
    return dataSetsToAdd;
  }

  /**
   * Creates a line chart with a list given data series.
   *
   * @param listOfDataSeries The list of data series that will be used to create the chart.
   */
  private void createLineChart(List<Series<String, Number>> listOfDataSeries) {

    final CategoryAxis xAxis = new CategoryAxis();
    final NumberAxis yAxis = new NumberAxis();
    xAxis.setLabel(selectedSpacing);
    yAxis.setLabel("Kr");
    LineChart<String, Number> lineChart = new LineChart<>(xAxis, yAxis);

    lineChart.getData().removeAll(lineChart.getData());
    lineChart.getData().addAll(listOfDataSeries);
    lineChart.setAnimated(false);
    lineChart.setCreateSymbols(false);

    lineChart.setPadding(new Insets(0, 0, 0, 0));
    lineChart.getXAxis().setAutoRanging(true);
    lineChart.getYAxis().setAutoRanging(true);
    chartPane.getChildren().removeAll(chartPane.getChildren());
    chartPane.getChildren().add(lineChart);
    setSeriesColours();

    lineChart.setLegendVisible(false);
  }

  private void setSeriesColours() {
    incomeInPeriod.getNode().setStyle("-fx-stroke: #384c6b; -fx-stroke-width: 5; ");
    differenceInPeriod.getNode().setStyle(
        "-fx-stroke: #e28a2b; -fx-stroke-width: 5; -fx-border-color: #000000; -fx-border-width: 5");
    expenseInPeriod.getNode().setStyle("-fx-stroke: #859bba; -fx-stroke-width: 5; ");


  }

  private void createBarChart(List<Series<String, Number>> listOfDataSeries) {

    final CategoryAxis xAxis = new CategoryAxis();
    final NumberAxis yAxis = new NumberAxis();

    xAxis.setLabel(selectedSpacing);

    yAxis.setLabel("Kr");
    BarChart<String, Number> barChart = new BarChart<>(xAxis, yAxis);

    barChart.getData().removeAll(barChart.getData());

    barChart.getData().addAll(listOfDataSeries);

    barChart.setBarGap(0);
    barChart.setCategoryGap(3);
    barChart.setAnimated(false);
    barChart.setPadding(new Insets(0, 0, 0, 0));
    barChart.getXAxis().setAutoRanging(true);
    barChart.getYAxis().setAutoRanging(true);
    chartPane.getChildren().removeAll(chartPane.getChildren());
    chartPane.getChildren().add(barChart);
    barChart.setLegendVisible(false);
    setSeriesColours();
  }

  /**
   * Updates the chart with data points for all different days between the start day and end day.
   *
   * @param startDate The date that will be used to create the first data point
   * @param endDate   The date that will be the last data point in the data series.
   */
  @SuppressWarnings("deprecation")
  private void updateDataSetDays(Date startDate, Date endDate) {
    Statistics statistics = new Statistics(RegisterManager.getInstance());

    Date current = startDate;

    while (!current.after(endDate)) {
      String dateFormatted =
          current.getDate() + "/" + current.getMonth() + "/" + (current.getYear() + 1900);

      incomeInPeriod.getData()
          .add(new Data<>(dateFormatted, statistics.getSaleTotalForDay(current)));

      expenseInPeriod.getData()
          .add(new Data<>(dateFormatted, -statistics.getExpenseTotalForDay(current)));

      differenceInPeriod.getData()
          .add(new Data<>(dateFormatted, statistics.getDifferenceForDay(current)));

      Calendar calendar = Calendar.getInstance();
      calendar.setTime(current);
      calendar.add(Calendar.DATE, 1);
      current = calendar.getTime();
    }

  }

  /**
   * Updates the chart with data points for all different months between the start month and end
   * month.
   *
   * @param startDate The date that will be used to find the first month to create points from.
   * @param endDate   The date that will be used to find the first month to create points from.
   */
  @SuppressWarnings("deprecation")
  private void updateDataSetsMonths(Date startDate, Date endDate) {
    Statistics statistics = new Statistics(RegisterManager.getInstance());

    YearMonth firstMonth = YearMonth.of(startDate.getYear() + 1900, startDate.getMonth() + 1);
    YearMonth endMonth = YearMonth.of(endDate.getYear() + 1900, endDate.getMonth() + 1);

    YearMonth currentMonth = firstMonth;

    while (!currentMonth.isAfter(endMonth)) {
      String monthFormatted = currentMonth.getMonthValue() + "/" + currentMonth.getYear();
      incomeInPeriod.getData()
          .add(new Data<>(monthFormatted, statistics.getSaleTotalForMonth(currentMonth)));
      expenseInPeriod.getData()
          .add(new Data<>(monthFormatted, -statistics.getExpenseTotalForMonth(currentMonth)));
      differenceInPeriod.getData()
          .add(new Data<>(monthFormatted, statistics.getDifferenceTotalForMonth(currentMonth)));
      currentMonth = currentMonth.plusMonths(1);
    }
  }

  /**
   * Updates the chart with data points for all different years between the start year and end
   * year.
   *
   * @param startDate The date that will be used to find the first year to create points from.
   * @param endDate   The date that will be used to find the last year to create points from.
   */
  @SuppressWarnings("deprecation")
  private void updateDataSetsYears(Date startDate, Date endDate) {
    Statistics statistics = new Statistics(RegisterManager.getInstance());

    Year firstYear = Year.of(startDate.getYear() + 1900);
    Year endYear = Year.of(endDate.getYear() + 1900);

    Year currentYear = firstYear;
    while (!currentYear.isAfter(endYear)) {
      String yearFormatted = String.valueOf(currentYear.getValue());

      incomeInPeriod.getData()
          .add(new Data<>(yearFormatted, statistics.getSaleTotalForYear(currentYear)));
      expenseInPeriod.getData()
          .add(new Data<>(yearFormatted, -statistics.getExpenseTotalForYear(currentYear)));
      differenceInPeriod.getData()
          .add(new Data<>(yearFormatted, statistics.getDifferenceTotalForYear(currentYear)));
      currentYear = currentYear.plusYears(1);
    }
  }

  /**
   * Removes all current date from the chart.
   */
  private void clearCurrentData() {
    incomeInPeriod.getData().removeAll(incomeInPeriod.getData());
    expenseInPeriod.getData().removeAll(expenseInPeriod.getData());
    differenceInPeriod.getData().removeAll(differenceInPeriod.getData());
  }

  /**
   * Updates chart when the show expense checkbox is ticked.
   */
  public void handleShowExpenseToggle() {
    updateChart();
  }

  /**
   * Updates chart when the show sales checkbox is ticked.
   */
  public void handleShowSalesToggle() {
    updateChart();
  }

  /**
   * Updates chart when the show difference checkbox is ticked.
   */
  public void handleShowDifferenceToggle() {
    updateChart();
  }
}
