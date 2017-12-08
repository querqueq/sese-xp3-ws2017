package at.ac.tuwien.student.sese2017.xp.hotelmanagement.auth;

import at.ac.tuwien.student.sese2017.xp.hotelmanagement.domain.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.provisioning.UserDetailsManager;

public class CustomUserDetailsManager implements UserDetailsManager {

  private UserRepository userRepository;

  @Autowired
  public CustomUserDetailsManager(UserRepository userRepository) {
    this.userRepository = userRepository;
  }
  
  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public void createUser(UserDetails user) {
    // TODO Auto-generated method stub

  }

  @Override
  public void updateUser(UserDetails user) {
    // TODO Auto-generated method stub

  }

  @Override
  public void deleteUser(String username) {
    // TODO Auto-generated method stub

  }

  @Override
  public void changePassword(String oldPassword, String newPassword) {
    // TODO Auto-generated method stub

  }

  @Override
  public boolean userExists(String username) {
    // TODO Auto-generated method stub
    return false;
  }

}
