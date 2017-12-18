package at.ac.tuwien.student.sese2017.xp.hotelmanagement.auth;

import at.ac.tuwien.student.sese2017.xp.hotelmanagement.domain.data.UserEntity;
import at.ac.tuwien.student.sese2017.xp.hotelmanagement.domain.repository.UserRepository;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Component;

@Component
public class CustomUserDetailsManager implements UserDetailsManager {

  private static final String ROLE_PREFIX = "ROLE_";
  private UserRepository userRepository;

  @Autowired
  public CustomUserDetailsManager(UserRepository userRepository) {
    this.userRepository = userRepository;
  }
  
  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    List<UserEntity> users = userRepository.findByUsername(username);
    if(users.isEmpty()) {
      throw new UsernameNotFoundException("User with name " + username + " does not exist.");
    } else if(users.size() > 1) {
      throw new IllegalStateException("Multiple users with name " + username + " found!");
    }
    UserEntity user = users.get(0);
    
    return new User(user.getUsername(),
        user.getPassword(),
        user.getRoles().stream()
        // spring requires this role prefix or else the roles in class SecurityConfig won't match
        .map(r -> ROLE_PREFIX + r.name())
        .map(SimpleGrantedAuthority::new)
        .collect(Collectors.toList()));
  }

  @Override
  public void createUser(UserDetails user) {
    throw new UnsupportedOperationException("Creating a user is not supported via CustomUserDetailsManager.");
  }

  @Override
  public void updateUser(UserDetails user) {
    throw new UnsupportedOperationException("Updating a user is not supported via CustomUserDetailsManager.");
  }

  @Override
  public void deleteUser(String username) {
    throw new UnsupportedOperationException("Deleting a user is not supported via CustomUserDetailsManager.");
  }

  @Override
  public void changePassword(String oldPassword, String newPassword) {
    throw new UnsupportedOperationException("Changing a user password is not supported via CustomUserDetailsManager.");
  }

  @Override
  public boolean userExists(String username) {
    return !userRepository.findByUsername(username).isEmpty();
  }

}
