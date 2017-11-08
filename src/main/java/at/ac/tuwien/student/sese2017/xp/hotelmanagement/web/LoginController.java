package at.ac.tuwien.student.sese2017.xp.hotelmanagement.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * This controller handles all requests concerning login actions.
 * The credential check is done by the Security Config
 * @see at.ac.tuwien.student.sese2017.xp.hotelmanagement.config.SecurityConfig#configureGlobal(AuthenticationManagerBuilder)
 *
 * @author akraschitzer
 */
@Slf4j
@Controller
public class LoginController {

  /**
   * Handles calls to the suburl "/login" on the web representation.
   *
   * @return String representing the path to the template that is to be shown.
   */
  @RequestMapping("/login")
  public String login() {
    log.info("login - page called");
    return "login"; //path to the template to call
  }

  /**
   * Handles calls to the suburl "/login" with the POST request method.
   * This happens when the login according to the SecurityConfig fails.
   * Redirects to the "/login-error" page
   *
   * @return a redirection to another suburl
   */
  @RequestMapping(value = "/login", method = RequestMethod.POST)
  public String postLogin() {
    log.info("login - failed");
    return "redirect:/login-error"; //redirect to another suburl
  }

  /**
   * Handles calls to the suburl "/login-error" on the web representation.
   * Sets an attribute which can be accessed on the web page. to activate an error message.
   *
   * @return String representing the path to the template that is to be shown.
   */
  @RequestMapping("/login-error")
  public String errorLogin(Model model) {
    model.addAttribute("loginError", true);
    return "login"; //path to the template to call
  }
}
