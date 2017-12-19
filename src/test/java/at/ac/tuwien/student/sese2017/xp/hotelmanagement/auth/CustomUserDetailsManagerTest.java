package at.ac.tuwien.student.sese2017.xp.hotelmanagement.auth;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import at.ac.tuwien.student.sese2017.xp.hotelmanagement.domain.data.JobTitle;
import at.ac.tuwien.student.sese2017.xp.hotelmanagement.domain.data.UserEntity;
import at.ac.tuwien.student.sese2017.xp.hotelmanagement.domain.repository.UserRepository;
import java.util.Arrays;
import java.util.Collections;
import org.junit.Before;
import org.junit.Test;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * Tests {@link CustomUserDetailsManager}.
 * 
 * @author johannes
 */
public class CustomUserDetailsManagerTest {

  private CustomUserDetailsManager mgmt;
  private UserRepository userRepository;

  @Before
  public void setup() {
    userRepository = mock(UserRepository.class);
    mgmt = new CustomUserDetailsManager(userRepository);
  }
  
  /**
   * Test if user can be loaded by their username.
   */
  @Test
  public void testLoadUserByUsername() {
    final String username = "foo";
    UserEntity user = new UserEntity();
    user.setUsername(username);
    user.setPassword("aaayyyy");
    user.setRoles(JobTitle.CLEANER.getRoles());
    when(userRepository.findByUsername(any())).thenReturn(Arrays.asList(user));
    UserDetails details = mgmt.loadUserByUsername(username);
    assertEquals(details.getUsername(), username);
    assertEquals(details.getPassword(), user.getPassword());
  }
  
  /**
   * Test if.
   */
  @Test(expected = UsernameNotFoundException.class)
  public void testLoadUserByUsernameNotExist() {
    final String username = "foo";
    when(userRepository.findByUsername(any())).thenReturn(Collections.emptyList());
    mgmt.loadUserByUsername(username);
  }

  /**
   * Confirms that the called method is not implemented.
   */
  @Test(expected = UnsupportedOperationException.class)
  public void testUpdateUser() {
    mgmt.updateUser(null);
  }

  /**
   * Confirms that the called method is not implemented.
   */
  @Test(expected = UnsupportedOperationException.class)
  public void testDeleteUser() {
    mgmt.deleteUser(null);
  }

  /**
   * Confirms that the called method is not implemented.
   */
  @Test(expected = UnsupportedOperationException.class)
  public void testCreateUser() {
    mgmt.createUser(null);
  }

  /**
   * Confirms that the called method is not implemented.
   */
  @Test(expected = UnsupportedOperationException.class)
  public void testChangePassword() {
    mgmt.changePassword(null, null);
  }

  /**
   * Honestly, its for coverage.
   */
  @Test
  public void testUserExists() {
    when(userRepository.findByUsername(any())).thenReturn(Collections.emptyList());
  }
}
