package at.ac.tuwien.student.sese2017.xp.hotelmanagement.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import at.ac.tuwien.student.sese2017.xp.hotelmanagement.domain.data.CustomerEntity;
import at.ac.tuwien.student.sese2017.xp.hotelmanagement.service.CustomerService;

/**
 * This controller handles basic requests to the staff only space of the web page.
 * For example the "/staff/index" page.
 *
 * @author akraschitzer
 */
@Slf4j
@Controller
public class StaffController {

  private CustomerService service;

  @Autowired
  public StaffController(CustomerService service) {
    this.service = service;
  }

  @GetMapping("/staff/customer/create")
  public String createCustomer(Model model) {
    model.addAttribute("customer", new CustomerEntity());
    return "staff/customerCreate";
  }
  
  @PostMapping("/staff/customer/create")
  public String postCustomer(@ModelAttribute CustomerEntity entity) {
    service.create(entity);
    return "staff/customerCreate";
  }

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
