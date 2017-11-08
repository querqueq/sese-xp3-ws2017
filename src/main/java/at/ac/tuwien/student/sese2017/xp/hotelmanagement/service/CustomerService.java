package at.ac.tuwien.student.sese2017.xp.hotelmanagement.service;

import javax.validation.Valid;
import org.springframework.validation.annotation.Validated;
import at.ac.tuwien.student.sese2017.xp.hotelmanagement.domain.data.CustomerEntity;

/**
 * @author Michael
 * @author Johannes
 *
 */
@Validated
public interface CustomerService {
  /**
   * Validates and saves the given entity into the underlying data store
   * @param entity The customer entity to create
   * @return The id of the created entity
   */
  Long create(@Valid CustomerEntity entity);
}
