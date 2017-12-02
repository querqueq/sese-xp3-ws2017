package at.ac.tuwien.student.sese2017.xp.hotelmanagement.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception for entities that could not be found.
 * @author Michael
 *
 */
@ResponseStatus(value=HttpStatus.NOT_FOUND)
public class NotFoundException extends RuntimeException {
  private static final long serialVersionUID = 1L;

  /**
   * Constructs an exception with a default not found message for an entity.
   * @param id the id of the entity not found
   * @param entityClass the class of the entity that was not found
   */
  public NotFoundException(Long id, Class<?> entityClass) {
    super(String.format("Entity %s with id %d not found!", entityClass.getSimpleName(), id));
  }
}
