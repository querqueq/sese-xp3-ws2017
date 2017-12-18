package at.ac.tuwien.student.sese2017.xp.hotelmanagement.config;

import at.ac.tuwien.student.sese2017.xp.hotelmanagement.auth.CustomUserDetailsManager;
import at.ac.tuwien.student.sese2017.xp.hotelmanagement.domain.data.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

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
          .antMatchers("/customer/**").hasRole(Role.CUSTOMER.name())
          .antMatchers("/staff/staffers/create").hasRole(Role.MANAGER.name())
          .antMatchers("/staff/**").hasRole(Role.STAFF.name())          
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
  public void configureGlobal(AuthenticationManagerBuilder auth,
      CustomUserDetailsManager userManager) throws Exception {
    auth.userDetailsService(userManager).passwordEncoder(new BCryptPasswordEncoder());
  }
}
