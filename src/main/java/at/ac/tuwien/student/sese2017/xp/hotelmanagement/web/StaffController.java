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
import org.springframework.beans.factory.annotation.Required;
import org.springframework.data.querydsl.SimpleEntityPathResolver;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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

  private static final String STAFF_SEARCH_VIEW = "staff/search";
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
  public String getSearch(Model model,
      @RequestParam(value = "keywords", required = false) String keywords,
      @RequestParam(value = "domain", required = false) SearchOption searchOption) {
    log.info("staff index - Page called");
    StaffSearchCriteria staffSearchCriteria = new StaffSearchCriteria();
    staffSearchCriteria
        .setSearchOption(Optional.ofNullable(searchOption).orElse(SearchOption.CUSTOMERS));
    staffSearchCriteria.setSearchText(keywords);
    model.addAttribute(SEARCH_CRITERIA, staffSearchCriteria);
    Optional.ofNullable(staffSearchCriteria).map(StaffSearchCriteria::getSearchOption)
        .ifPresent(option -> {
          switch (option) {
            case CUSTOMERS:
              model.addAttribute("customers", customerService.search(keywords));
              break;
            case RECEIPTS:
              model.addAttribute(RECEIPTS, receiptService.search(keywords));
              break;
            default:
              break;
          }
        });
    return STAFF_SEARCH_VIEW;
  }

  /**
   * A view with an evaluated search.
   * 
   * @param model with search criteria and receipts
   * @param criteria specified search criteria
   * @return view path
   */
  @PostMapping("/staff/search")
  public String doSearch(Model model,
      @ModelAttribute(SEARCH_CRITERIA) StaffSearchCriteria criteria) {
    log.info("search customer - Page called");
    return redirectToSearch(criteria);
  }

  /**
   * A view with all receipts of a specific customer.
   * 
   * @param model with search criteria and receipts
   * @param customerId specific customer
   * @return view path
   */
  @GetMapping("/staff/customers/{customerId}/receipts")
  public String getReceipts(Model model, @PathVariable("customerId") Long customerId) {
    StaffSearchCriteria criteria = new StaffSearchCriteria();
    criteria.setSearchText(null);
    criteria.setSearchOption(SearchOption.RECEIPTS);
    model.addAttribute(SEARCH_CRITERIA, criteria);
    model.addAttribute(RECEIPTS, receiptService.getReceiptsForCustomer(customerId));
    return STAFF_SEARCH_VIEW;
  }

  /**
   * Views all details of a specific receipt.
   * 
   * @param model with search criteria and receipts
   * @param receiptId id of the receipt which is to be laid eyes upon
   * @param searchKeywords ye previously inputedly termy
   * @param cancel whether the receipt shall be prompted for cancelation or not
   * @return view path
   */
  @GetMapping("/staff/receipts/{receiptId}")
  public String getReceipt(Model model, @PathVariable("receiptId") Long receiptId,
      @RequestParam("keywords") String searchKeywords,
      @RequestParam(value = "cancel", defaultValue = "false") Boolean cancel) {
    model.addAttribute("cancel", cancel);
    model.addAttribute("keywords", searchKeywords);
    model.addAttribute("receipt", receiptService.getReceipt(receiptId));
    return "staff/receipt";
  }

  /**
   * Cancel a specific receipt.
   * 
   * @param model with search criteria and receipts
   * @param searchKeywords ye previously inputedly termy
   * @param receiptId id of the receipt which is to be laid eyes upon
   * @return view path
   */
  @PostMapping("/staff/receipts/{receiptId}")
  public String cancelReceipt(Model model,
      @RequestParam(value = "keywords", required = false) String searchKeywords,
      @PathVariable("receiptId") Long receiptId) {
    StaffSearchCriteria criteria = new StaffSearchCriteria();
    criteria.setSearchText(searchKeywords);
    criteria.setSearchOption(SearchOption.RECEIPTS);
    try {
      receiptService.cancelReceipt(receiptId);
      model.addAttribute("success", "Rechnung storniert!");
      // TODO add success alert to view
    } catch (RuntimeException e) {
      model.addAttribute("danger", "Rechnung konnte nicht storniert werden!");
      // TODO view danger alert in view
    }
    return redirectToSearch(SearchOption.RECEIPTS, searchKeywords);
  }

  private String redirectToSearch(StaffSearchCriteria criteria) {
    return redirectToSearch(criteria.getSearchOption(), criteria.getSearchText());
  }

  private String redirectToSearch(SearchOption searchOption, String searchText) {
    return String.format("redirect:/staff/search?keywords=%s&domain=%s", searchText, searchOption);
  }
}
