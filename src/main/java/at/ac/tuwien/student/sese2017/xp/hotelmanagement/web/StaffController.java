package at.ac.tuwien.student.sese2017.xp.hotelmanagement.web;

import at.ac.tuwien.student.sese2017.xp.hotelmanagement.domain.data.CustomerEntity;
import at.ac.tuwien.student.sese2017.xp.hotelmanagement.domain.web.form.CustomerSearchCriteria;
import at.ac.tuwien.student.sese2017.xp.hotelmanagement.service.CustomerService;
import java.math.BigDecimal;
import javax.validation.ValidationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * This controller handles basic requests to the staff only space of the web page. 
 * For example the "/staff/index" page.
 *
 */
@Slf4j
@Controller
public class StaffController {

  private static final String CUSTOMER_SEARCH_CRITERIA_ATTRIBUTE_NAME = "customerSearchCriteria";
  private static final String CUSTOMER_ATTRIBUTE_NAME = "customer";
  private CustomerService service;

  @Autowired
  public StaffController(CustomerService service) {
    this.service = service;
  }

  /**
   * Blank form for creating a new customer.
   * @param  model model for view
   * @return  path to template 
   */
  @GetMapping("/staff/customer/create")
  public String createCustomer(Model model) {
    log.info("create customer - Page called");
    CustomerEntity newCustomer = new CustomerEntity();
    newCustomer.setDiscount(BigDecimal.ZERO);
    model.addAttribute(CUSTOMER_ATTRIBUTE_NAME, newCustomer);
    return "staff/customerCreate";
  }

  /**
   * A blank form with a note about the customer creation.
   * If there are no errors. Otherwise a partially filled
   * form with error messages.
   * @param model  model for view
   * @param entity  customer entity filled out by form  
   * @return path to template 
   */
  @PostMapping("/staff/customer/create")
  public String postCustomer(Model model, @ModelAttribute CustomerEntity entity) {
    log.info("post customer - Page called");
    try {
      Long customerId = service.create(entity);
      log.info("created customer {}", customerId);
      model.addAttribute("note",
          String.format("Kunde %s erfasst! (%d)", entity.getName(), customerId));
      model.addAttribute(CUSTOMER_ATTRIBUTE_NAME, new CustomerEntity());
    } catch (ValidationException e) {
      model.addAttribute("note", "Fehler");
      model.addAttribute(CUSTOMER_ATTRIBUTE_NAME, entity);
    }
    return "staff/customerCreate";

  }
  
  /**
   * Gets the view for the customer search.
   * @param model model for view
   * @return path to template  
   */
  @GetMapping("/staff/customer/search")
  public String getSearchCustomer(Model model) {
    log.info("getSearchCustomer - Page called");
    model.addAttribute(CUSTOMER_SEARCH_CRITERIA_ATTRIBUTE_NAME, new CustomerSearchCriteria());
    return "staff/customerSearch";
  }
  
  /**
   * Does a full text search with the given search criteria and puts the result in the model.
   * @param model model for view
   * @param criteria the search criteria model
   * @return path to template 
   */
  @PostMapping("/staff/customer/search")
  public String searchCustomer(Model model, @ModelAttribute CustomerSearchCriteria criteria) {
    log.info("search customer - Page called");
    model.addAttribute("customerList", service.search(criteria.getSearchText()));
    return "staff/customerSearch";
  }

  /**
   * Handles calls to the suburl "/staff/index" on the web representation.
   *
   * @return String representing the path to the template that is to be shown.
   */
  @RequestMapping("/staff/index")
  public String userIndex() {
    log.info("staff index - Page called");
    return "staff/index"; // path to the template to call
  }
}
