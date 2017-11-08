package at.ac.tuwien.student.sese2017.xp.hotelmanagement.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Slf4j
@Controller
public class LoginController {

  @RequestMapping("/login")
  public String login() {
    log.info("login - page called");
    return "login"; //path to the template to call
  }

  @RequestMapping(value = "/login", method = RequestMethod.POST)
  public String postLogin() {
    log.info("login - failed");
    return "redirect:login-error"; //redirect to the template to call
  }

  @RequestMapping("/login-error")
  public String errorLogin(Model model) {
    model.addAttribute("loginError", true);
    return "login"; //path to the template to call
  }
}
