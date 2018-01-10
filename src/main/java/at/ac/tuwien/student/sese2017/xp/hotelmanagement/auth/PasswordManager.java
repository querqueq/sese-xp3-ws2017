package at.ac.tuwien.student.sese2017.xp.hotelmanagement.auth;

import java.security.SecureRandom;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * Generates and encodes passwords.
 * @author Michael
 *
 */
@Component
public class PasswordManager {
  private static final int PASSWORD_LENGTH = 10;
  private final PasswordEncoder encoder;
  private final SecureRandom random = new SecureRandom();

  public PasswordManager() {
    encoder = new BCryptPasswordEncoder();
  }

  /**
   * Generates a random 10 character password String that does not contain ambiguous characters.
   * @return A 10 character String password.
   */
  public String generatePassword() {
    String letters = "abcdefghjkmnpqrstuvwxyzABCDEFGHJKMNPQRSTUVWXYZ23456789+@";

    String pw = "";
    for (int i = 0; i < PASSWORD_LENGTH; i++) {
      int index = (int)(random.nextDouble() * letters.length());
      pw += letters.substring(index, index + 1);
    }
    return pw;
  }
  
  /**
   * Encodes a clear text password using the default {@link PasswordEncoder}.
   * @param clearTextPassword The clear text password to encode.
   * @return An encoded password String.
   */
  public String encodePassword(String clearTextPassword) {
    return encoder.encode(clearTextPassword);
  }
}
