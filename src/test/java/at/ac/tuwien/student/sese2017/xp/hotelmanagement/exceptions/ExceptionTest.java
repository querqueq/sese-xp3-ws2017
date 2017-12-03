package at.ac.tuwien.student.sese2017.xp.hotelmanagement.exceptions;

import static org.junit.Assert.assertEquals;
import at.ac.tuwien.student.sese2017.xp.hotelmanagement.domain.data.CustomerEntity;
import org.junit.Test;

/**
 * Tests that exceptions are constructed correctly.
 * @author Michael
 *
 */
public class ExceptionTest {
  
  /**
   * Tests if the NotFoundException Long, Class<?> constructor builds a correct message.
   */
  @Test
  public void testNotFoundExceptionMessage() {
    NotFoundException ex = new NotFoundException(1L, CustomerEntity.class);
    assertEquals("Entity CustomerEntity with id 1 not found!", ex.getMessage());
  }
}
