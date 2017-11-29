package at.ac.tuwien.student.sese2017.xp.hotelmanagement.web;

import at.ac.tuwien.student.sese2017.xp.hotelmanagement.domain.data.AddressEntity;
import at.ac.tuwien.student.sese2017.xp.hotelmanagement.domain.data.CustomerEntity;
import at.ac.tuwien.student.sese2017.xp.hotelmanagement.domain.data.ReceiptEntity;
import at.ac.tuwien.student.sese2017.xp.hotelmanagement.domain.web.form.StaffSearchCriteria;
import at.ac.tuwien.student.sese2017.xp.hotelmanagement.domain.web.form.StaffSearchCriteria.SearchOption;
import at.ac.tuwien.student.sese2017.xp.hotelmanagement.service.CustomerService;
import at.ac.tuwien.student.sese2017.xp.hotelmanagement.service.ReceiptService;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import javax.validation.ValidationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.querydsl.SimpleEntityPathResolver;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * This controller handles basic requests to the staff only space of the web page. For example the
 * "/staff/index" page.
 *
 * @author Johannes
 * @author Michael
 * 
 */
@Slf4j
@Controller
public class StaffController {

  private static final String RECEIPTS = "receipts";
  private static final String SEARCH_CRITERIA = "searchCriteria";
  private static final String CUSTOMER_ATTRIBUTE_NAME = "customer";
  private CustomerService customerService;
  private ReceiptService receiptService;

  /**
   * Controller for staff use cases.
   * 
   * @param customerService The CustomerService to search and create CustomerEntity objects.
   */
  @Autowired
  public StaffController(CustomerService customerService, ReceiptService receiptService) {
    this.customerService = customerService;
    this.receiptService = receiptService;
  }

  /**
   * Blank form for creating a new customer.
   * 
   * @param model model for view
   * @return path to template
   */
  @GetMapping("/staff/customer/create")
  public String createCustomer(Model model) {
    log.info("create customer - Page called");
    CustomerEntity newCustomer = new CustomerEntity();
    newCustomer.setDiscount(BigDecimal.ZERO);
    newCustomer.setBillingAddress(new AddressEntity());
    model.addAttribute(CUSTOMER_ATTRIBUTE_NAME, newCustomer);
    return "staff/customerCreate";
  }

  /**
   * A blank form with a note about the customer creation. If there are no errors. Otherwise a
   * partially filled form with error messages.
   * 
   * @param model model for view
   * @param entity customer entity filled out by form
   * @return path to template
   */
  @PostMapping("/staff/customer/create")
  public String postCustomer(Model model, @ModelAttribute CustomerEntity entity) {
    log.info("post customer - Page called");
    try {
      Long customerId = customerService.create(entity);
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
   * 
   * @param model model for view
   * @return path to template
   */
  @GetMapping("/staff/customer/search")
  public String getSearchCustomer(Model model) {
    log.info("getSearchCustomer - Page called");
    model.addAttribute(SEARCH_CRITERIA, new StaffSearchCriteria());
    return "staff/customerSearch";
  }

  /**
   * Does a full text search with the given search criteria and puts the result in the model.
   * 
   * @param model model for view
   * @param criteria the search criteria model
   * @return path to template
   */
  @PostMapping("/staff/customer/search")
  public String searchCustomer(Model model, @ModelAttribute StaffSearchCriteria criteria) {
    log.info("search customer - Page called");
    model.addAttribute("customerList", customerService.search(criteria.getSearchText()));
    return "staff/customerSearch";
  }

  /**
   * Handles calls to the suburl "/staff/index" on the web representation.
   *
   * @return String representing the path to the template that is to be shown.
   */
  @RequestMapping({"/staff/search", "/staff", "/staff/index"})
  public String search(Model model) {
    log.info("staff index - Page called");
    StaffSearchCriteria staffSearchCriteria = new StaffSearchCriteria();
    staffSearchCriteria.setSearchOption(SearchOption.CUSTOMERS);
    model.addAttribute(SEARCH_CRITERIA, staffSearchCriteria);
    return "staff/search";
  }

  @PostMapping("/staff/search")
  public String postSearch(Model model,
      @ModelAttribute(SEARCH_CRITERIA) StaffSearchCriteria criteria) {
    log.info("search customer - Page called");
    Optional.ofNullable(criteria).map(StaffSearchCriteria::getSearchOption)
        .ifPresent(searchOption -> {
          switch (searchOption) {
            case CUSTOMERS:
              model.addAttribute("customers", customerService.search(criteria.getSearchText()));
              break;
            case RECEIPTS:
              model.addAttribute(RECEIPTS, receiptService.search(criteria.getSearchText()));
              break;
            default:
              break;
          }
        });
    criteria.setSearchText(null);
    model.addAttribute(SEARCH_CRITERIA, criteria);
    return "staff/search";
  }
  
  @GetMapping("/staff/customers/{customerId}/receipts")
  public String getReceipts(Model model, @PathVariable("customerId") Long customerId) {
    List<ReceiptEntity> receiptsForCustomer = receiptService.getReceiptsForCustomer(customerId);
    StaffSearchCriteria criteria = new StaffSearchCriteria();
    criteria.setSearchText(null);
    criteria.setSearchOption(SearchOption.RECEIPTS);
    model.addAttribute(SEARCH_CRITERIA, criteria);
    model.addAttribute(RECEIPTS, receiptsForCustomer);
    return "staff/search";
  }
}
