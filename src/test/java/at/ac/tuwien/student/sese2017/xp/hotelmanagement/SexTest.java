package at.ac.tuwien.student.sese2017.xp.hotelmanagement;

import static org.junit.Assert.assertEquals;

import at.ac.tuwien.student.sese2017.xp.hotelmanagement.domain.data.Sex;
import org.junit.Test;

public class SexTest {
  
  @Test
  public void testMaleSex() {
    assertEquals(Sex.MALE.getDisplay(), "M\u00e4nnlich"); // Maenlich
  }
}
