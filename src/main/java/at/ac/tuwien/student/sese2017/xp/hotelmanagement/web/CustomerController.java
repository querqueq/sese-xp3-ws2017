package at.ac.tuwien.student.sese2017.xp.hotelmanagement.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import at.ac.tuwien.student.sese2017.xp.hotelmanagement.domain.data.CustomerEntity;
import at.ac.tuwien.student.sese2017.xp.hotelmanagement.service.CustomerService;

/**
 * This controller handles basic requests to the customer only space of the web page.
 * For example the "/customer/index" page.
 *
 * @author akraschitzer
 */
@Slf4j
@Controller
public class CustomerController {

  private CustomerService service;

  @Autowired
  public CustomerController(CustomerService service) {
    this.service = service;
  }
  
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
  
  @GetMapping("/customer/create")
  public String createCustomer() {
    return "customer/create";
  }
  
  @PostMapping("/customer/create")
  public String postCustomer(CustomerEntity entity) {
    return "customer/create";
  }
}
