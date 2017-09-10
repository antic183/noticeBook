package ch.teko.ntb.business.util;

import org.mindrot.jbcrypt.BCrypt;

/**
 * Created by antic-software-ing on 09.09.2017.
 */
public class SecureHash {

  public static boolean checkPassword(String plainTextPassword, String hashedPassword) {
    boolean passwordIsMatched = false;
    if (BCrypt.checkpw(plainTextPassword, hashedPassword)) {
      passwordIsMatched = true;
    }

    return passwordIsMatched;
  }

  public static String hashPassword(String plainTextPassword) {
    String hashed = BCrypt.hashpw(plainTextPassword, BCrypt.gensalt());
    return hashed;
  }

}
