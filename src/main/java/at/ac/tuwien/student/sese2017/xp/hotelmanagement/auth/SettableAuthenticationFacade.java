package at.ac.tuwien.student.sese2017.xp.hotelmanagement.auth;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

/**
 * Provides an implementation for AuthenticationFacade where the Authentication can be set.
 * @author Michael
 *
 */
@Component
public class SettableAuthenticationFacade implements AuthenticationFacade {

  private Authentication authentication;
  
  @Override
  public Authentication getAuthentication() {
    return authentication != null ? authentication : SecurityContextHolder.getContext().getAuthentication();
  }

  /**
   * Sets the Authentication to be used when getAuthentication is called.
   * If a null value is given the default SecurityContextHolder will be used to fetch the Authentication
   * @param authentication The Authentication to use.
   */
  public void setAuthentication(Authentication authentication) {
    this.authentication = authentication;
  }
}
