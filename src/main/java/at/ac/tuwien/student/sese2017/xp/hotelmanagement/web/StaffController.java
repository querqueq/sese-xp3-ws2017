package at.ac.tuwien.student.sese2017.xp.hotelmanagement.web;

import at.ac.tuwien.student.sese2017.xp.hotelmanagement.auth.UserWithId;
import at.ac.tuwien.student.sese2017.xp.hotelmanagement.domain.data.AddressEntity;
import at.ac.tuwien.student.sese2017.xp.hotelmanagement.domain.data.CustomerEntity;
import at.ac.tuwien.student.sese2017.xp.hotelmanagement.domain.data.StaffEntity;
import at.ac.tuwien.student.sese2017.xp.hotelmanagement.domain.data.VacationEntity;
import at.ac.tuwien.student.sese2017.xp.hotelmanagement.domain.dto.StaffEmployment;
import at.ac.tuwien.student.sese2017.xp.hotelmanagement.domain.web.form.StaffCreateForm;
import at.ac.tuwien.student.sese2017.xp.hotelmanagement.domain.web.form.StaffSearchCriteria;
import at.ac.tuwien.student.sese2017.xp.hotelmanagement.domain.web.form.StaffSearchCriteria.SearchOption;
import at.ac.tuwien.student.sese2017.xp.hotelmanagement.exceptions.NotEnoughVacationDaysException;
import at.ac.tuwien.student.sese2017.xp.hotelmanagement.service.CustomerService;
import at.ac.tuwien.student.sese2017.xp.hotelmanagement.service.ReceiptService;
import at.ac.tuwien.student.sese2017.xp.hotelmanagement.service.StaffService;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import javax.validation.ValidationException;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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

  private static final String SUCCESS_ATTRIBUTE_NAME = "success";
  private static final String DANGER_ATTRIBUTE_NAME = "danger";
  private static final String STAFF_SEARCH_VIEW = "staff/search";
  private static final String STAFF_CUSTOMER_CREATE_VIEW = "staff/customerCreate";
  private static final String RECEIPTS = "receipts";
  private static final String SEARCH_CRITERIA = "searchCriteria";
  private static final String CUSTOMER_ATTRIBUTE_NAME = "customer";
  private static final String STAFFER_ATTRIBUTE_NAME = "staffer";
  private static final String VACATIONS_ATTRIBUTE_NAME = "vacations";
  private CustomerService customerService;
  private ReceiptService receiptService;
  private StaffService staffService;

  /**
   * Controller for staff use cases.
   * 
   * @param customerService The CustomerService to search and create CustomerEntity objects.
   */
  @Autowired
  public StaffController(CustomerService customerService,
      ReceiptService receiptService,
      StaffService staffService) {
    this.customerService = customerService;
    this.receiptService = receiptService;
    this.staffService = staffService;
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
   * This method is also called after editing a customer.
   *
   * @param model model for view
   * @param entity customer entity filled out by form
   * @return path to template
   */
  @PostMapping("/staff/customer/create")
  public String postCustomer(Model model, @ModelAttribute CustomerEntity entity,
                             RedirectAttributes redir) {
    log.info("post customer - Page called");
    try {
      Long customerId = customerService.save(entity);

      if (entity.getId() != null) {
        log.info("Existing entity (id {}) changed", entity.getId());
        redir.addFlashAttribute("success", "Kundendaten erfolgreich geändert!");
        return "redirect:/staff/search?keywords="
            + URLEncoder.encode(entity.getName(), "UTF-8")
            + "&domain=CUSTOMERS";
      } else {
        log.info("created customer {}", customerId);
        model.addAttribute("note",
            String.format("Kunde %s erfasst! (%d)", entity.getName(), customerId));
        model.addAttribute(CUSTOMER_ATTRIBUTE_NAME, new CustomerEntity());
      }
    } catch (ValidationException e) {
      model.addAttribute("note", "Fehler - " + e.getMessage());
      model.addAttribute(CUSTOMER_ATTRIBUTE_NAME, entity);
      log.warn("Customer save validation failed", e);
    } catch (UnsupportedEncodingException e) {
      log.warn("Failed to encode customer name for redirect to search", e);
      return "redirect:/staff/search&domain=CUSTOMERS";
    }
    return STAFF_CUSTOMER_CREATE_VIEW;
  }

  /**
   * Fills the customerCreate.html form with the information of the entity with the passed id.
   *
   * <p>If the given id is not found in the database, an empty form is shown.</p>
   *
   * @param model model for view
   * @param customerId id of the customer to edit
   * @return path to template
   */
  @GetMapping("/staff/customer/edit/{customerId}")
  public String editCustomer(Model model, @PathVariable("customerId") Long customerId) {
    log.info("edit customer - Page called");
    CustomerEntity customer = customerService.getCustomer(customerId);
    if (customer == null) {
      customer = new CustomerEntity();
      customer.setBillingAddress(new AddressEntity());
    }
    model.addAttribute(CUSTOMER_ATTRIBUTE_NAME, customer);
    return STAFF_CUSTOMER_CREATE_VIEW;
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
    log.info("customer's receipts - Page called");
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
    log.info("receipt details (cancel: {}) - Page called", cancel);
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
      @PathVariable("receiptId") Long receiptId,
      RedirectAttributes redir) {
    log.info("cancel receipt - Page called");
    StaffSearchCriteria criteria = new StaffSearchCriteria();
    criteria.setSearchText(searchKeywords);
    criteria.setSearchOption(SearchOption.RECEIPTS);
    try {
      receiptService.cancelReceipt(receiptId);
      redir.addFlashAttribute(SUCCESS_ATTRIBUTE_NAME, "Rechnung " + receiptId + " storniert.");
    } catch (RuntimeException e) {
      log.error("Error while canceling receipt {}", receiptId, e);
      redir.addFlashAttribute(DANGER_ATTRIBUTE_NAME, "Rechnung konnte nicht storniert werden.");
    }
    return redirectToSearch(SearchOption.RECEIPTS, searchKeywords);
  }

  /**
   * Return form for creating new staffer.
   *
   * @param model empty model
   * @return view path
   */
  @GetMapping("/staff/staffers/create")
  public String getCreateStaff(Model model) {
    log.info("create staff - Page called");
    StaffCreateForm dto = new StaffCreateForm();
    model.addAttribute(STAFFER_ATTRIBUTE_NAME, dto);
    return "staff/staffCreate";
  }

  /**
   * Creates a new staffer.
   *
   * @param model empty model
   * @param dto new staff form dto
   * @return view path
   */
  @PostMapping("/staff/staffers/create")
  public String doCreateStaff(Model model, @ModelAttribute StaffCreateForm dto) {
    log.info("post staff - Page called");
    StaffEntity entity = dto.getEntity();
    try {
      Map<Integer, Integer> initVacDays = new HashMap<>();
      Integer currentYear = LocalDate.now().getYear();
      initVacDays.put(currentYear, dto.getInitialVacationDays());
      initVacDays.put(currentYear + 1, dto.getYearlyVacationDays());
      dto.getEntity().setYearlyVacationDays(initVacDays);
      StaffEmployment employment = staffService.create(dto.getEntity());
      log.info("created staffer {}", employment.getId());
      model.addAttribute("note",
          String.format("Mitarbeiter %s (%d) erfasst! Das Passwort ist '%s' "
              + "(Passwort wird nicht nochmal angezeigt!)",
              entity.getName(), employment.getId(), employment.getClearTextPassword()));
      return getCreateStaff(model);
    } catch (ValidationException e) {
      model.addAttribute("note", "Fehler");
      model.addAttribute(STAFFER_ATTRIBUTE_NAME, dto);
      return "staff/staffCreate";
    }

  }

  /**
   * Returns for your creating a vacation request.
   *
   * @param model empty model
   * @param authentication for getting current user
   * @return view path
   */
  @GetMapping("/staff/staffers/vacations/create")
  public String getVacationInput(Model model, Authentication authentication) {
    Long stafferId = ((UserWithId)authentication.getPrincipal()).getId();
    model.addAttribute("stafferId", stafferId);
    model.addAttribute("vacation", new VacationEntity());
    return "/staff/requestVacation";
  }

  /**
   * Creates a new vacation request.
   *
   * @param model empty model
   * @param vacation vacation to be accepted
   * @param authentication for getting current user
   * @return view path
   */
  @PostMapping("/staff/staffers/vacations/create")
  public String doVacationInput(Model model,
      @ModelAttribute("vacation") VacationEntity vacation,
      Authentication authentication) {
    Long stafferId = ((UserWithId)authentication.getPrincipal()).getId();
    Optional<StaffEntity> staff = staffService.findById(stafferId);
    if (staff.isPresent()) {
      vacation.setStaffer(staff.get());
      try {
        Long vacationId = staffService.requestVacation(vacation);
        StringBuilder successMessage = new StringBuilder()
            .append(String.format("Urlaub (%d) im Umfang von %d ",
                vacationId,
                vacation.getVacationDays()));
        if (vacation.getVacationDays() == 1) {
          successMessage.append("Tag");
        } else {
          successMessage.append("Tage");
        }
        successMessage.append(" erfasst!");
        model.addAttribute("note", successMessage.toString());
      } catch (NotEnoughVacationDaysException e) {
        model.addAttribute("note", "Nicht genügend freie Urlaubstage!");
      } catch (IllegalArgumentException e) {
        model.addAttribute("note", e.getMessage());
      }
    } else {
      model.addAttribute("note", "Mitarbeiter mit ID " + stafferId + " nicht gefunden!");
    }
    return "/staff/requestVacation";
  }

  /**
   * Get all pending, running and future vacation requests.
   *
   * @param model empty model
   * @return view path
   */
  @GetMapping("/staff/vacations")
  public String getVacations(Model model) {
    log.info("get vacations - Page called");
    model.addAttribute(VACATIONS_ATTRIBUTE_NAME, staffService.getCurrentVactionRequests());
    return "staff/vacationMgmt";
  }

  /**
   * Accept a vacation.
   *
   * @param model empty model
   * @param vacationId vacation to be accepted
   * @param redir for success/failure messages after redirect
   * @return redirect path
   */
  @PostMapping(value = "/staff/vacations/{vacationId}/resolve", params = "action=accept")
  public String acceptVacation(Model model, @PathVariable("vacationId") Long vacationId,
      RedirectAttributes redir) {
    log.info("accept vacation {} - Page called", vacationId);
    try {
      staffService.confirmVacation(vacationId);
      redir.addFlashAttribute(SUCCESS_ATTRIBUTE_NAME, "Urlaubsantrag bewilligt!");
    } catch (IllegalStateException | IllegalArgumentException e) {
      redir.addFlashAttribute(DANGER_ATTRIBUTE_NAME, e.getMessage());
    }
    return redirectToVacationOverview();
  }

  /**
   * Reject a vacation with a non-optional reason.
   *
   * @param model empty model
   * @param vacationId vacation to be accepted
   * @param reason non optional reason for vacation denial
   * @param redir for success/failure messages after redirect
   * @return redirect path
   */
  @PostMapping(value = "/staff/vacations/{vacationId}/resolve", params = "action=reject")
  public String rejectVacation(Model model,
      @PathVariable("vacationId") Long vacationId,
      @ModelAttribute("reason") String reason,
      RedirectAttributes redir) {
    log.info("reject vacation {} - Page called", vacationId);
    try {
      staffService.rejectVacation(vacationId, reason);
      redir.addFlashAttribute(SUCCESS_ATTRIBUTE_NAME, "Urlaubsantrag abgelehnt!");
    } catch (IllegalStateException | IllegalArgumentException e) {
      redir.addFlashAttribute(DANGER_ATTRIBUTE_NAME, e.getMessage());
    }
    return redirectToVacationOverview();
  }

  private String redirectToSearch(StaffSearchCriteria criteria) {
    return redirectToSearch(criteria.getSearchOption(), criteria.getSearchText());
  }

  private String redirectToSearch(SearchOption searchOption, String searchText) {
    return String.format("redirect:/staff/search?keywords=%s&domain=%s", searchText, searchOption);
  }

  private String redirectToVacationOverview() {
    return "redirect:/staff/vacations";
  }
}