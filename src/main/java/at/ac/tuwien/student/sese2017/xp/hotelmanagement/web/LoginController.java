package at.ac.tuwien.student.sese2017.xp.hotelmanagement.web;

import lombok.extern.java.Log;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Log
@Controller
public class LoginController {

  @RequestMapping("/login")
  public String login() {
    log.info("login - page called");
    return "login";
  }

  @RequestMapping(value = "/login", method = RequestMethod.POST)
  public String postLogin() {
    log.info("registration - failed");
    return "redirect:login-error";
  }

  @RequestMapping("/login-error")
  public String errorLogin(Model model) {
    model.addAttribute("loginError", true);
    return "login";
  }
}
