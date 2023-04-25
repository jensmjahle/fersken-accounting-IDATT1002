package edu.ntnu.idatt1002.registers;

import edu.ntnu.idatt1002.storageitems.User;
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

  /**
   * Finds a user with a matching username.
   *
   * @param userName The username used to find matches.
   * @return The user with a corresponding username. If no user exists by the specified name, null
   *        will be returned.
   */
  public User findUserByName(String userName) {
    return getObjects().stream().filter(user -> user.getUserName().equalsIgnoreCase(userName))
        .findFirst().orElse(null);
  }

  /**
   * Checks if a username is already in use.
   *
   * @param userName The username to be searched for.
   * @return true if the username already is in use, false if not.
   */
  public boolean userNameAlreadyExists(String userName) {
    return getObjects().stream().noneMatch(user -> user.getUserName().equalsIgnoreCase(userName));
  }

  /**
   * Adds a user to the user register.
   *
   * @param user The user that will be added to the register.
   */
  @Override
  public void addObject(User user) {
    Objects.requireNonNull(user, "Cannot add null object to register");
    if (getObjects().stream().anyMatch(user2 -> user2.getUserName().equals(user.getUserName()))) {
      throw new IllegalArgumentException("Username is already in use");
    }
    getObjects().add(user);
    writeToFile();
  }
}
