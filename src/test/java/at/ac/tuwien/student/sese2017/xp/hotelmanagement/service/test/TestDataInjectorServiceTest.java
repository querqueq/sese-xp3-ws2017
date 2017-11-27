package at.ac.tuwien.student.sese2017.xp.hotelmanagement.service.test;

import static org.junit.Assert.*;

import at.ac.tuwien.student.sese2017.xp.hotelmanagement.domain.data.CustomerEntity;
import at.ac.tuwien.student.sese2017.xp.hotelmanagement.domain.test.TestDataDirectory;
import javax.persistence.Entity;
import org.junit.Test;
import org.springframework.core.annotation.Order;

public class TestDataInjectorServiceTest {

  @Test
  public void hasJPAAnnotation() throws Exception {
    assertFalse(TestDataInjectorService.hasJPAAnnotation(TestDataDirectory.class));
    assertTrue(TestDataInjectorService.hasJPAAnnotation(CustomerEntity.class));
  }


  @Test
  public void getOrder() throws Exception {
    assertEquals(2,TestDataInjectorService.getOrder(TestOrderedClass.class.getDeclaredField("x")));
    assertEquals(-4,TestDataInjectorService.getOrder(TestOrderedClass.class.getDeclaredField("y")));
    assertEquals(0,TestDataInjectorService.getOrder(TestOrderedClass.class.getDeclaredField("z")));

  }




  private class TestOrderedClass {
    @Order(2)
    public String x;
    @Order(-4)
    public String y;

    public String z;
  }
}