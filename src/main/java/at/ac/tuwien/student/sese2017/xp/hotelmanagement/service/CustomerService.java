package at.ac.tuwien.student.sese2017.xp.hotelmanagement.service;

import at.ac.tuwien.student.sese2017.xp.hotelmanagement.auth.PasswordManager;
import at.ac.tuwien.student.sese2017.xp.hotelmanagement.domain.data.CustomerEntity;
import at.ac.tuwien.student.sese2017.xp.hotelmanagement.domain.data.Role;
import at.ac.tuwien.student.sese2017.xp.hotelmanagement.domain.repository.CustomerRepository;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;
import javax.validation.Valid;
import javax.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

/**
 * Service class for all customer related functions carried out by staff.
 * 
 * @author Michael
 * @author Johannes
 */
@Validated
@Service
public class CustomerService {
  //FIXME Replace this password with a randomly generated password
  // as soon as email sending to customers is required
  private static final String DEFAULT_DEV_PASS = "password";
  // Pattern to validate phone and fax numbers
  // Currently phone numbers are considered validate if the have a maximum
  // of 50 digits(we had to pick something without limiting really long
  // phone numbers)
  private static final Pattern PHONE_PATTERN = Pattern.compile("^[+]??[0-9 ]{0,50}$");
  private final CustomerRepository customerRepository;
  private PasswordManager passwordManager;

  /**
   * Creates an instance of CustomerService.
   * @param customerRepository Repository to save CustomerEntity objects through this service.
   */
  @Autowired
  public CustomerService(CustomerRepository customerRepository,
      PasswordManager passwordManager) {
    this.customerRepository = customerRepository;
    this.passwordManager = passwordManager;
  }

  /**
   * Looks for a customer in the existing database.
   *
   * <p>
   * Returns null if no customer with the given ID could be found
   * </p>
   *
   * @param id long id of the customer to look for
   * @return CustomerEntity Object associated with the given id
   */
  public CustomerEntity getCustomer(Long id) {
    Optional<CustomerEntity> byId = customerRepository.findById(id);
    return byId.orElse(null);
  }

  /**
   * Validates and saves the given entity in the underlying data store.
   * 
   * @param entity The customer entity to save
   * @return The id of the saved entity
   */
  public Long save(@Valid CustomerEntity entity) {
    // TODO deny double entries (match for name and billing address)
    // TODO generate random password and send to the customers email
    entity.setUsername(entity.getEmail());
    entity.setPassword(passwordManager.encodePassword(DEFAULT_DEV_PASS));
    entity.setRoles(Arrays.asList(Role.CUSTOMER));
    checkPhoneNumber(entity.getFaxNumber());
    checkPhoneNumber(entity.getPhoneNumber());
    if (entity.getBirthday().isAfter(LocalDate.now())) {
      throw new ValidationException("Das Geburtsdatum muss in der Vergangenheit liegen.");
    }
    return customerRepository.save(entity).getId();
  }

  /**
   * Full text search for customers.
   * @param searchText multiple keywords seperated by whitespaces
   * @return List of matching customers
   */
  public List<CustomerEntity> search(String searchText) {
    return customerRepository.search(searchText);
  }

  private void checkPhoneNumber(String phoneNumber) {
    if (phoneNumber != null && !PHONE_PATTERN.asPredicate().test(phoneNumber)) {
      throw new ValidationException(String.format("Telefonnummer: %s ungültig!", phoneNumber));
    }
  }
}
