package at.ac.tuwien.student.sese2017.xp.hotelmanagement.auth;

import java.util.Collection;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

public class UserWithId extends User {
  private static final long serialVersionUID = 1L;
  private final Long id;

  public UserWithId(Long id, String username, String password,
      Collection<? extends GrantedAuthority> authorities) {
    super(username, password, authorities);
    this.id = id;
  }

  public Long getId() {
    return id;
  }
}
