package at.ac.tuwien.student.sese2017.xp.hotelmanagement.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import lombok.extern.slf4j.Slf4j;

/**
 * This controller handles basic requests to the customer only space of the web page.
 * For example the "/customer/index" page.
 *
 * @author akraschitzer
 */
@Slf4j
@Controller
public class CustomerController {

  /**
   * Handles calls to the suburl "/customer/index" on the web representation.
   *
   * @return String representing the path to the template that is to be shown.
   */
  @RequestMapping("/customer/index")
  public String userIndex() {
    log.info("customer index - Page called");
    return "customer/index"; //path to the template to call
  }
}
