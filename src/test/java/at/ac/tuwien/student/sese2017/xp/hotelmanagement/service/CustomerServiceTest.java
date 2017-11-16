package at.ac.tuwien.student.sese2017.xp.hotelmanagement.service;

import static org.junit.Assert.assertNotNull;

import at.ac.tuwien.student.sese2017.xp.hotelmanagement.HotelManagementApplicationTests;
import at.ac.tuwien.student.sese2017.xp.hotelmanagement.domain.data.CustomerEntity;
import at.ac.tuwien.student.sese2017.xp.hotelmanagement.domain.data.Sex;
import at.ac.tuwien.student.sese2017.xp.hotelmanagement.domain.repository.CustomerRepository;
import at.ac.tuwien.student.sese2017.xp.hotelmanagement.domain.repository.CustomerSearch;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URI;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import javax.transaction.Transactional;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;
import org.hibernate.Transaction;
import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.Search;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit4.SpringRunner;

@Transactional
public class CustomerServiceTest extends HotelManagementApplicationTests{
  private static final String LOREM_IPSUM = "Lorem ipsum dolor sit amet, consetetur sadipscing "
      + "elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, "
      + "sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita "
      + "kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet. Lorem ipsum "
      + "dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut "
      + "labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et "
      + "justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est "
      + "Lorem ipsum dolor sit amet. Lorem ipsum dolor sit amet, consetetur sadipscing elitr, "
      + "sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed "
      + "diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita "
      + "kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet. \n"
      + "\n"
      + "Duis autem vel eum iriure dolor in hendrerit in vulputate velit esse molestie "
      + "consequat, vel illum dolore eu feugiat nulla facilisis at";

  @Autowired
  private CustomerService customerService;
  @Autowired
  private CustomerSearch customerSearch;
  @PersistenceContext(type = PersistenceContextType.EXTENDED)
  EntityManager entityManager;
  @Autowired
  CustomerRepository repo;

  @Test
  public void testCreateEntityWithAllFields() throws MalformedURLException {
    CustomerEntity entity = createEntity();
    Long id = customerService.create(entity);
    assertNotNull(id);
  }

  @Test
  public void testCreateEntityWithCompulsoryFields() throws MalformedURLException {
    CustomerEntity entity = createEntity();
    entity.setCompanyName(null);
    entity.setFaxNumber(null);
    entity.setNote(null);
    entity.setWebAddress(null);
    Long id = customerService.create(entity);
    assertNotNull(id);
  }

  @Test(expected = ConstraintViolationException.class)
  public void testCreateEntityWithOneInvalidField() throws MalformedURLException {
    CustomerEntity entity = createEntity();
    entity.setPhoneNumber("This is not a phone number");
    customerService.create(entity);
  }

  @Test(expected = ConstraintViolationException.class)
  public void testCreateEntityWithMultipleInvalidFields() throws MalformedURLException {
    CustomerEntity entity = createEntity();
    entity.setBirthday(LocalDate.now().plus(1, ChronoUnit.DAYS));
    entity.setEmail("invalid email");
    customerService.create(entity);
  }

  @Test(expected = ConstraintViolationException.class)
  public void testCreateEntityWithCompulsoryNullField() throws MalformedURLException {
    CustomerEntity entity = createEntity();
    entity.setName(null);
    customerService.create(entity);
  }

  @Test(expected = ConstraintViolationException.class)
  public void testCreateEntityWithInvalidDiscount() throws MalformedURLException {
    CustomerEntity entity = createEntity();
    entity.setDiscount(BigDecimal.valueOf(101.0D));
    customerService.create(entity);
  }

  @Test(expected = ValidationException.class)
  public void testCreateEntityWithInvalidBirthday() throws MalformedURLException {
    CustomerEntity entity = createEntity();
    entity.setBirthday(LocalDate.now().plus(1, ChronoUnit.DAYS));
    customerService.create(entity);
  }

  @Test
  public void test() throws MalformedURLException, InterruptedException {
//    FullTextEntityManager fullTextEntityManager = Search.getFullTextEntityManager(entityManager);
//    fullTextEntityManager.createIndexer(CustomerEntity.class).startAndWait();
    repo.findAll().forEach(c -> System.out.println(c));
    List<CustomerEntity> customers = customerSearch.search("Müller");
    System.out.println(customers.size());
    System.out.println(customers.get(0));
  }
  
  private CustomerEntity createEntity() throws MalformedURLException {
    CustomerEntity entity = new CustomerEntity();
    entity.setBillingAddress("Valid address");
    entity.setBirthday(LocalDate.now().minus(2, ChronoUnit.DECADES));
    entity.setCompanyName("Company");
    entity.setDiscount(BigDecimal.ZERO);
    entity.setEmail("test@email.com");
    entity.setFaxNumber("010101101010110");
    entity.setName("First Last");
    entity.setNote(LOREM_IPSUM);
    entity.setPhoneNumber("0123456789");
    entity.setSex(Sex.FEMALE);
    entity.setWebAddress(URI.create("http://localhost").toURL());
    return entity;
  }
}
