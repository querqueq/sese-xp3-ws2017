package at.ac.tuwien.student.sese2017.xp.hotelmanagement.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * This controller handles basic requests to the staff only space of the web page.
 * For example the "/staff/index" page.
 *
 * @author akraschitzer
 */
@Slf4j
@Controller
public class StaffController {

  /**
   * Handles calls to the suburl "/staff/index" on the web representation.
   *
   * @return String representing the path to the template that is to be shown.
   */
  @RequestMapping("/staff/index")
  public String userIndex() {
    log.info("staff index - Page called");
    return "staff/index"; //path to the template to call
  }
}
