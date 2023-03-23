package edu.ntnu.idatt1002.fxml;

import edu.ntnu.idatt1002.*;
import edu.ntnu.idatt1002.registers.BudgetRegister;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.awt.event.ActionEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CreateBudgetController {

  private Stage stage;
  private Scene scene;
  private Parent root;

  @FXML
  private TextField projectNameField;
  @FXML
  private TextField expenseNameField;
  @FXML
  private TextField expenseAmountField;
  @FXML
  private TextField incomeNameField;
  @FXML
  private TextField incomeAmountField;
  @FXML
  private TableView expenseTable;
  @FXML
  private TableColumn expenseNameColumn;
  @FXML
  private TableColumn expenseAmountColumn;
  @FXML
  private TableView incomeTable;
  @FXML
  private TableColumn incomeNameColumn;
  @FXML
  private TableColumn incomeAmountColumn;

  Budget newBudget;
  ArrayList<Expense> newListOfExpenses = new ArrayList<>();
  ArrayList<Sale> newListOfIncomes = new ArrayList<>();
  public void switchToMainMenuScene(MouseEvent event) throws IOException {
    Parent root = FXMLLoader.load(PathUtility.getResourcePath("MainMenu"));
    stage = (Stage)((Node)event.getSource()).getScene().getWindow();
    scene = new Scene(root);
    stage.setScene(scene);
    stage.show();
  }

  public void addExpense() {
    String expenseName = expenseNameField.getText();
    String expenseAmount = expenseAmountField.getText();

    Expense newExpense = new Expense(Double.parseDouble(expenseAmount), expenseName);
    newListOfExpenses.add(newExpense);

    expenseNameField.clear();
    expenseAmountField.clear();

    //jens fikser
    RegisterManager.getInstance().getExpenseRegister().addObject(newExpense);

    updateTables();


  }

  public void addIncome() {
    String incomeName = incomeNameField.getText();
    String incomeAmount = incomeAmountField.getText();

    Sale newIncome = new Sale(incomeName, Double.parseDouble(incomeAmount));
    newListOfIncomes.add(newIncome);

    incomeNameField.clear();
    incomeAmountField.clear();

    //jens fikser
    RegisterManager.getInstance().getSaleRegister().addObject(newIncome);

    updateTables();


  }

  public void updateTables() {
    expenseNameColumn.setCellValueFactory(
        new PropertyValueFactory<>("product"));

    expenseAmountColumn.setCellValueFactory(
        new PropertyValueFactory<>("amount"));

    incomeNameColumn.setCellValueFactory(
        new PropertyValueFactory<>("product"));

    incomeAmountColumn.setCellValueFactory(
        new PropertyValueFactory<>("amount"));

    expenseTable.getItems().clear();
    incomeTable.getItems().clear();

    for(Expense expense: newListOfExpenses){
      expenseTable.getItems().add(expense);
    }

    for(Sale sale: newListOfIncomes){
      incomeTable.getItems().add(sale);
    }
  }

  public void createBudget() {

    String projectName = projectNameField.getText();
    newBudget = new Budget(projectName);
    newBudget.addListOfExpenses(newListOfExpenses);
    newBudget.addListOfSales(newListOfIncomes);

    RegisterManager.getInstance().getBudgetRegister().addObject(newBudget);

    clearAllFields();
  }

  public void clearAllFields() {
    projectNameField.clear();
    expenseTable.getItems().clear();
    incomeTable.getItems().clear();
    incomeNameField.clear();
    incomeAmountField.clear();
    expenseNameField.clear();
    expenseAmountField.clear();
  }
}
