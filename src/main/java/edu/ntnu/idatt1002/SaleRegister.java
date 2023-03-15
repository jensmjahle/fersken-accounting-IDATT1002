package edu.ntnu.idatt1002;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * Represents a sale register that stores and changes all data stored about sales.
 */
public class SaleRegister {

  /**
   * Adds a new sale to the sale record by writing it to the file that stores all sales.
   *
   * @param customer The contact object representing the customer of the sale.
   * @param date     The date of the sale.
   * @param product  The text description of the product that was sold in the sale.
   * @param receiver The account that will receive the money for the sale.
   * @param amount   The cost of the product.
   */
  public void addSale(Contact customer, Date date, String product, String receiver, double amount) {

    Sale sale = new Sale(customer, date, product, receiver, amount);

    try (FileWriter writer = new FileWriter("Sales.txt", true)) {
      String saleInfo = customer.getName() + ","
          + sale.getDate() + ","
          + sale.getProduct() + ","
          + sale.getReceiverAccount() + ","
          + sale.getAmount() + "\n";
      writer.write(saleInfo);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * Retrieves a list of strings of all the sales stored in Sales.txt.
   *
   * @return A list of all sales as Strings
   */
  public List<String[]> getSales() {
    List<String[]> sales = new ArrayList<>();
    File file = new File("Sales.txt");

    try (BufferedReader reader = Files.newBufferedReader(file.toPath())) {
      String line;
      while ((line = reader.readLine()) != null) {
        String[] values = line.split(",");
        sales.add(values);
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return sales;
  }

  /**
   * Removes a specified line from the file that stores all sales.
   *
   * @param lineNumberToRemove The index of the line that will be removed.
   * @throws NullPointerException If either the writer or reader is null.
   * @throws IOException If an IO error occurs.
   */
  public void removeSale(int lineNumberToRemove) throws NullPointerException, IOException {
    File oldFile = new File("Sales.txt");
    File newFile = new File("tempSales.txt");

    try (BufferedReader reader = new BufferedReader(new FileReader(oldFile));
         BufferedWriter writer = new BufferedWriter(new FileWriter(newFile))
    ) {

      int lineCounter = 0;
      String currentLine;
      while ((currentLine = reader.readLine()) != null) {
        if (lineCounter == lineNumberToRemove) {
          lineCounter++;
          continue;
        }
        writer.write(currentLine + "\n");
        lineCounter++;
      }

    } catch (IOException e) {
      e.printStackTrace();
    }
    Files.move(newFile.toPath(), oldFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
  }


}
