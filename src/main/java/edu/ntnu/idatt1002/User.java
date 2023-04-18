package edu.ntnu.idatt1002;

import java.io.Serializable;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Arrays;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

public class User implements Serializable {

  private final String userName;

  private byte[] salt;
  private byte[] hash;

  public User(String userName, String password) throws NoSuchAlgorithmException, InvalidKeySpecException {
    this.userName = userName;
    setSalt();
    hash = createHash(password);
  }

  public String getUserName() {
    return userName;
  }




  private byte[] createHash(String password) throws NoSuchAlgorithmException, InvalidKeySpecException {

    KeySpec keySpec = new PBEKeySpec(password.toCharArray(), getSalt(), 65536, 128);
    SecretKeyFactory secretKeyFactory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");

    return secretKeyFactory.generateSecret(keySpec).getEncoded();
  }

  private void setSalt(){
    SecureRandom random = new SecureRandom();
    byte[] salt = new byte[16];
    random.nextBytes(salt);
    this.salt = salt;
  }

  public byte[] getSalt(){
    return salt;
  }

  public byte[] getHash(){
    return hash;
  }

  public void setNewPassword(String newPassword)
      throws IllegalArgumentException, NoSuchAlgorithmException, InvalidKeySpecException {
    setSalt();
    byte[]newHash = createHash(newPassword);
    if (Arrays.equals(newHash, getHash())){
      throw new IllegalArgumentException("Password cannot be the same as previous password");
    }

    this.hash = newHash;
  }
}

