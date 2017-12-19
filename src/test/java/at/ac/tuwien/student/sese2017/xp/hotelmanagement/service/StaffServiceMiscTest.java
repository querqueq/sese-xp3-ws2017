package at.ac.tuwien.student.sese2017.xp.hotelmanagement.service;

import static org.junit.Assert.assertFalse;

import at.ac.tuwien.student.sese2017.xp.hotelmanagement.HotelManagementApplicationTests;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Tests the rest.
 * @author Michael
 *
 */
public class StaffServiceMiscTest extends HotelManagementApplicationTests {
  
  @Autowired
  private StaffService staffService;
  
  /**
   * Tests if a staff entity can be found using an ID.
   */
  @Test
  public void testFindById() {
    assertFalse(staffService.findById(1L).isPresent());
  }
}
