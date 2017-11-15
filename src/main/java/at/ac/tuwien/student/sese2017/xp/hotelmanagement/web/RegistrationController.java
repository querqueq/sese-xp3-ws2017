package at.ac.tuwien.student.sese2017.xp.hotelmanagement.web;

import at.ac.tuwien.student.sese2017.xp.hotelmanagement.domain.data.CustomerEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import javax.validation.Valid;
import lombok.extern.slf4j.Slf4j;

/**
 * This Controller handles all calls to the suburl "/register".
 *
 * @author akraschitzer
 */
@Slf4j
@Controller
public class RegistrationController {


  /**
   * Handles calls to the suburl "/reigster" on the web representation.
   *
   * @param customerEntity This parameter is used to represent the values shown on the web page
   * @return String representing the path to the template that is to be shown.
   */
  @RequestMapping("/register")
  public String registration(CustomerEntity customerEntity) {
    log.info("registration - Page called");
    return "registration"; //path to the template to call
  }

  /**
   * Checks if the submitted information is valid.
   * @param customerEntity the object containing submitted information
   * @param bindingResult results of the validation process
   * @param model the model shared with the template to pass on information
   * @return String representing the path to the template that is to be shown.
   */
  @RequestMapping(value = "/register", method = RequestMethod.POST)
  public String checkRegistrationInfo(@Valid CustomerEntity customerEntity,
                                      BindingResult bindingResult, Model model) {
    if (bindingResult.hasErrors()) {
      model.addAttribute("registrationError", true);
      log.info("registration - failed ERRORS:({})", bindingResult.getAllErrors().toString());
      return "registration"; //path to the template to call
    }

    //TODO create UserEntity in Database


    return "login"; //path to the template to call
  }
}
