package at.ac.tuwien.student.sese2017.xp.hotelmanagement.service;

import at.ac.tuwien.student.sese2017.xp.hotelmanagement.domain.data.CustomerEntity;
import at.ac.tuwien.student.sese2017.xp.hotelmanagement.domain.repository.CustomerRepository;
import at.ac.tuwien.student.sese2017.xp.hotelmanagement.domain.repository.CustomerSearch;
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
  private static final Pattern PHONE_PATTERN = Pattern.compile("^\\d{0,50}$");
  private CustomerRepository customerRepository;
  private CustomerSearch customerSearch;

  @Autowired
  public CustomerService(CustomerRepository customerRepository, CustomerSearch customerSearch) {
    this.customerRepository = customerRepository;
    this.customerSearch = customerSearch;
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

  public List<CustomerEntity> search(String searchText) {
    return customerSearch.search(searchText);
  }

  private void checkPhoneNumber(String phoneNumber) {
    if (phoneNumber != null && !PHONE_PATTERN.asPredicate().test(phoneNumber)) {
      throw new ValidationException(String.format("%s invalid phone number", phoneNumber));
    }
  }
}
