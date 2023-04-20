package edu.ntnu.idatt1002;

import java.io.Serializable;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Arrays;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

/**
 * Represents a user with a username, password stored as a hash, and a salt used to create the
 * hash.
 */
public class User implements Serializable {

  private final String userName;

  private byte[] salt;
  private byte[] hash;

  /**
   * Constructor for the user class.
   *
   * @param userName The username of the user
   * @param password The input password of a user
   * @throws NoSuchAlgorithmException If the algorithm for creating the hash is not found
   * @throws InvalidKeySpecException  if the given key specification is inappropriate for the
   *                                  secret-key factory to produce a secret key.
   */
  public User(String userName, String password)
      throws NoSuchAlgorithmException, InvalidKeySpecException {
    if (password.length() > 64 || password.length() < 8) {
      throw new IllegalArgumentException("Password has to be between 8 and 64 characters long");
    }
    if (!password.matches(".*[0-9].*")){
      throw new IllegalArgumentException("Password needs minimum 1 number");
    }
    this.userName = userName;
    setSalt();
    hash = createHash(password);
  }

  /**
   * Returns the username of the user.
   *
   * @return the username of the user.
   */
  public String getUserName() {
    return userName;
  }

  /**
   * Creates a hash from the input password and the salt.
   *
   * @param password The password used to create the hash.
   * @return The created hash.
   * @throws NoSuchAlgorithmException If the algorithm for creating the hash is not found.
   * @throws InvalidKeySpecException  If the given key specification is inappropriate for the
   *                                  secret-key factory to produce a secret key.
   */
  private byte[] createHash(String password)
      throws NoSuchAlgorithmException, InvalidKeySpecException {

    KeySpec keySpec = new PBEKeySpec(password.toCharArray(), getSalt(), 65536, 128);
    SecretKeyFactory secretKeyFactory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");

    return secretKeyFactory.generateSecret(keySpec).getEncoded();
  }

  /**
   * Sets a new random salt of length 16.
   */
  private void setSalt() {
    SecureRandom random = new SecureRandom();
    byte[] salt = new byte[16];
    random.nextBytes(salt);
    this.salt = salt;
  }

  /**
   * Returns the salt for the password.
   *
   * @return The salt for the password.
   */
  public byte[] getSalt() {
    return salt;
  }

  /**
   * Returns the hashed password.
   *
   * @return The hashed password.
   */
  public byte[] getHash() {
    return hash;
  }

  /**
   * Sets a new password, updates the salt and hash to new values.
   *
   * @param newPassword The new password that will be set.
   * @throws IllegalArgumentException If the password is the same as the previous password
   * @throws NoSuchAlgorithmException If the algorithm for creating the hash is not found.
   * @throws InvalidKeySpecException  If the given key specification is inappropriate for the
   *                                  secret-key factory to produce a secret key
   */
  public void setNewPassword(String newPassword)
      throws IllegalArgumentException, NoSuchAlgorithmException, InvalidKeySpecException {
    setSalt();
    byte[] newHash = createHash(newPassword);
    if (Arrays.equals(newHash, getHash())) {
      throw new IllegalArgumentException("Password cannot be the same as previous password");
    }

    this.hash = newHash;
  }
}

