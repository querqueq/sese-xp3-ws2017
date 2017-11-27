package at.ac.tuwien.student.sese2017.xp.hotelmanagement.service.test;

import static org.junit.Assert.*;

import at.ac.tuwien.student.sese2017.xp.hotelmanagement.domain.data.CustomerEntity;
import at.ac.tuwien.student.sese2017.xp.hotelmanagement.domain.test.TestDataDirectory;
import javax.persistence.Entity;
import org.junit.Test;

public class TestDataInjectorServiceTest {

  @Test
  public void hasJPAAnnotation() throws Exception {
    assertFalse(TestDataInjectorService.hasJPAAnnotation(TestDataDirectory.class));
    assertTrue(TestDataInjectorService.hasJPAAnnotation(CustomerEntity.class));
  }
}