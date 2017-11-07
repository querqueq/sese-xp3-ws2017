package at.ac.tuwien.student.sese2017.xp.hotelmanagement.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http
          .authorizeRequests()
          .antMatchers("/css/**", "/index", "/register", "/error").permitAll()
          .antMatchers("/customer/**").hasRole("CUSTOMER")
        .antMatchers("/staff/**").hasRole("STAFF")
          .and()
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
          .inMemoryAuthentication()
              .withUser("customer").password("password").roles("CUSTOMER")
              .and()
              .withUser("staff").password("password").roles("STAFF");
    //TODO check database if customer is present:
  }
}
