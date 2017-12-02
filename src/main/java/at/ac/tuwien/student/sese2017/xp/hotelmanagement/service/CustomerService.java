package at.ac.tuwien.student.sese2017.xp.hotelmanagement.service;

import at.ac.tuwien.student.sese2017.xp.hotelmanagement.domain.data.CustomerEntity;
import at.ac.tuwien.student.sese2017.xp.hotelmanagement.domain.repository.CustomerRepository;
import java.time.LocalDate;
import java.util.List;
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
  // Pattern to validate phone and fax numbers
  // Currently phone numbers are considered validate if the have a maximum
  // of 50 digits(we had to pick something without limiting really long
  // phone numbers)
  private static final Pattern PHONE_PATTERN = Pattern.compile("^\\d{0,50}$");
  private final CustomerRepository customerRepository;

  /**
   * Creates an instance of CustomerService.
   * @param customerRepository Repository to save CustomerEntity objects through this service.
   */
  @Autowired
  public CustomerService(CustomerRepository customerRepository) {
    this.customerRepository = customerRepository;
  }

  /**
   * Validates and saves the given entity into the underlying data store.
   * 
   * @param entity The customer entity to create
   * @return The id of the created entity
   */
  public Long create(@Valid CustomerEntity entity) {
    // TODO deny double entries (match for name and billing address)
    checkPhoneNumber(entity.getFaxNumber());
    checkPhoneNumber(entity.getPhoneNumber());
    if (entity.getBirthday().isAfter(LocalDate.now())) {
      throw new ValidationException("Cannot have been born in the future!");
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
      throw new ValidationException(String.format("%s invalid phone number", phoneNumber));
    }
  }
}
