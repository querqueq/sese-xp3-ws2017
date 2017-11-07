package at.ac.tuwien.student.sese2017.xp.hotelmanagement.web;

import at.ac.tuwien.student.sese2017.xp.hotelmanagement.domain.dao.Customer;

import javax.validation.Valid;

import lombok.extern.java.Log;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Log
@Controller
public class RegistrationController {

  @RequestMapping("/register")
  public String registration(Customer customer) {
    log.info("registration - Page called");
    return "registration";
  }

  /**
   * Checks if the submitted information is valid.
   * @param customer the object containing submitted information
   * @param bindingResult results of the validation process
   * @param model the model shared with the template
   * @return the next site to navigate to
   */
  @RequestMapping(value = "/register", method = RequestMethod.POST)
  public String checkRegistrationInfo(@Valid Customer customer, BindingResult bindingResult,
                                      Model model) {
    if (bindingResult.hasErrors()) {
      model.addAttribute("registrationError", true);
      log.info("registration - failed ERRORS:(" + bindingResult.getAllErrors().toString() + ")");
      return "registration";
    }

    //TODO create Customer in Database


    return "login";
  }
}
