package at.ac.tuwien.student.sese2017.xp.hotelmanagement.service.test;

import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.stringContainsInOrder;
import static org.junit.Assert.*;
import static org.mockito.Mockito.inOrder;

import at.ac.tuwien.student.sese2017.xp.hotelmanagement.config.AppProperties;
import at.ac.tuwien.student.sese2017.xp.hotelmanagement.domain.data.CustomerEntity;
import at.ac.tuwien.student.sese2017.xp.hotelmanagement.domain.data.RoomEntity;
import at.ac.tuwien.student.sese2017.xp.hotelmanagement.domain.test.InjectableDataDirectory;
import at.ac.tuwien.student.sese2017.xp.hotelmanagement.domain.test.TestDataDirectory;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.junit.Test;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.springframework.core.annotation.Order;
import org.springframework.lang.Nullable;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionException;
import org.springframework.transaction.TransactionStatus;

public class TestDataInjectorServiceTest {


  @Test
  public void testInjectOrder() throws Exception {
    TestOrderedClass repo = new TestOrderedClass();
    Object[] expectedResult = new Object[]{repo.y, repo.z, repo.x};
    TestDataInjectorService injectorService = new TestDataInjectorService(
        null, null, null, repo);
    List<Object> objects = injectorService.prepareTestData();
    assertThat("Not the right elements returned", objects, contains(expectedResult));
  }


  @Test
  public void testHasJPAAnnotation() throws Exception {
    assertFalse(TestDataInjectorService.hasJPAAnnotation(TestDataDirectory.class));
    assertTrue(TestDataInjectorService.hasJPAAnnotation(CustomerEntity.class));
  }


  @Test
  public void testGetOrder() throws Exception {
    assertEquals(2,TestDataInjectorService.getOrder(TestOrderedClass.class.getDeclaredField("x")));
    assertEquals(-4,TestDataInjectorService.getOrder(TestOrderedClass.class.getDeclaredField("y")));
    assertEquals(0,TestDataInjectorService.getOrder(TestOrderedClass.class.getDeclaredField("z")));

  }

  private class TestOrderedClass implements InjectableDataDirectory {
    @Order(2)
    public final StringEntity x = new StringEntity(1L, "4");
    @Order(-4)
    public final StringEntity y = new StringEntity(2L, "Hello");
    public final StringEntity z = new StringEntity(3L, "World");
    @Order(-9) // Private fields should be ignored
    private final StringEntity a = new StringEntity(3L, "World");
  }

  @Entity
  @Data
  @AllArgsConstructor
  private class StringEntity {
    @Id
    @GeneratedValue
    private Long id;
    private String value;
  }
}