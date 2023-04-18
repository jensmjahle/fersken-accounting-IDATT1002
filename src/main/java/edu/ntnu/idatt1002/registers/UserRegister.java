package edu.ntnu.idatt1002.registers;

import edu.ntnu.idatt1002.User;
import edu.ntnu.idatt1002.registers.Register;
import java.util.Objects;

/**
 * Class that represents a register for all users.
 */
public class UserRegister extends Register<User> {

  /**
   * Constructor for a user register. Creates a user register at the location specified by a
   * fileName
   *
   * @param fileName The name that specifies where in the resources/register directory the file will
   *                 be stored
   */
  public UserRegister(String fileName) {
    super(fileName);
  }

  public User findUserByName(String userName){
    return getObjects().stream()
        .filter(user -> user.getUserName().equals(userName))
        .findFirst().orElse(null);
  }

  public boolean userNameExists(String userName){
    return getObjects().stream()
        .anyMatch(user -> user.getUserName().equals(userName));
  }

  @Override
  public void addObject(User user){
    Objects.requireNonNull(user, "Cannot add null object to register");
    if (getObjects().stream()
        .anyMatch(aUser -> aUser.getUserName().equals(user.getUserName()))){
      throw new IllegalArgumentException("Username is already in use");
    }
    getObjects().add(user);
    writeToFile();
  }
}
