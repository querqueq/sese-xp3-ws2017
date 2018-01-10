package at.ac.tuwien.student.sese2017.xp.hotelmanagement.auth;

import org.springframework.security.core.Authentication;

/**
 * Exposes the Authentication through an interface to make it accessible everywhere.
 * @author Michael
 *
 */
public interface AuthenticationFacade {
  /**
   * Gets the Authentication of the currently logged in user.
   * @return The current logged in user or an anonymous Authentication.
   */
  Authentication getAuthentication();
}
