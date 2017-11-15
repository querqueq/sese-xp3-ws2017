package at.ac.tuwien.student.sese2017.xp.hotelmanagement.service;

import java.time.LocalDate;
import javax.validation.Valid;
import javax.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import at.ac.tuwien.student.sese2017.xp.hotelmanagement.domain.data.CustomerEntity;
import at.ac.tuwien.student.sese2017.xp.hotelmanagement.domain.repository.CustomerRepository;

/**
 * @author Michael
 * @author Johannes
 *
 */
@Validated
@Service
public class CustomerService {
  private CustomerRepository customerRepository;

  @Autowired
  public CustomerService(CustomerRepository customerRepository) {
    this.customerRepository = customerRepository;
  }

  /**
   * Validates and saves the given entity into the underlying data store
   * @param entity The customer entity to create
   * @return The id of the created entity
   */
  public Long create(@Valid CustomerEntity entity) {
    //TODO deny double entries (match for name and billing address)
    if(entity.getBirthday().isAfter(LocalDate.now())) {
      throw new ValidationException("Cannot have been born in the future!");
    }
    return customerRepository.save(entity).getId();
  }
}
