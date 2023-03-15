package edu.ntnu.idatt1002.registers;

import edu.ntnu.idatt1002.Sale;

public class SaleRegister extends Register<Sale> {
  private static final String FILE_NAME = "sales.txt";

  public SaleRegister() {
    super(FILE_NAME);
  }
}