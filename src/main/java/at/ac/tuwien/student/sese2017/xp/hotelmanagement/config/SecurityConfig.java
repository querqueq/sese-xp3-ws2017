package at.ac.tuwien.student.sese2017.xp.hotelmanagement.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import at.ac.tuwien.student.sese2017.xp.hotelmanagement.domain.data.Role;

/**
 * This class specifies security aspects of the application.
 *
 * @author akraschitzer
 */
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

  /**
   * Specifies the security scheme for the whole web presentation.
   *
   * @param http The HttpSecurity object the scheme is defined on.
   * @throws Exception can be thrown
   */
  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http
          .authorizeRequests()
          // permitAll gives public access to the matched sub urls
          .antMatchers("/css/**", "/index", "/register", "/error").permitAll()
          // hasRole checks for a role on the loggedIn user
          .antMatchers("/customer/**").hasRole("CUSTOMER")
          .antMatchers("/staff/staffers/create").hasRole(Role.MANAGER.name())
          .antMatchers("/staff/**").hasRole("STAFF")          
          .and()
          // This defines the login page where the user can authenticate themselves
          .formLogin()
          .loginPage("/login").failureUrl("/login-error");
  }

  /**
   * Handles all authentication attempts for customers and employees.
   *
   * @param auth The AuthenticationManagerBuilder contains the submitted login information.
   * @throws Exception can be thrown
   */
  @Autowired
  public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
    auth
          .inMemoryAuthentication() //defines the users to check against as in memory, see below
              // following are valid login credentials and their defined role
              // the role which is checked on certain sub urls
              .withUser("customer").password("password").roles("CUSTOMER")
              .and()
              .withUser("staff").password("password").roles(Role.STAFF.name())
              .and()
              .withUser("manager").password("password").roles(Role.MANAGER.name(), Role.STAFF.name()
              );

          /* TODO check database if customer is present:
           * The DataBase could be done with Method 2 of following StackOverflow Post
           * https://stackoverflow.com/questions/41489383/how-can-i-validate-the-credentials-in-my-database-using-spring-security-and-res
           */
  }
}
