package at.ac.tuwien.student.sese2017.xp.hotelmanagement.service;

import at.ac.tuwien.student.sese2017.xp.hotelmanagement.HotelManagementApplicationTests;
import at.ac.tuwien.student.sese2017.xp.hotelmanagement.domain.data.AddressEntity;
import at.ac.tuwien.student.sese2017.xp.hotelmanagement.domain.data.CustomerEntity;
import at.ac.tuwien.student.sese2017.xp.hotelmanagement.domain.data.Sex;
import at.ac.tuwien.student.sese2017.xp.hotelmanagement.domain.repository.CustomerRepository;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URI;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import static org.junit.Assert.assertNotNull;

@Transactional
public class CustomerServiceTest extends HotelManagementApplicationTests.Default {
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
      + "kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet. \n" + "\n"
      + "Duis autem vel eum iriure dolor in hendrerit in vulputate velit esse molestie "
      + "consequat, vel illum dolore eu feugiat nulla facilisis at";

  @Autowired
  private CustomerService customerService;
  @Autowired
  CustomerRepository repo;

  @Test
  public void testCreateEntityWithAllFields() throws MalformedURLException {
    CustomerEntity entity = createCustomerEntity();
    Long id = customerService.create(entity);
    assertNotNull(id);
  }

  @Test
  public void testCreateEntityWithCompulsoryFields() throws MalformedURLException {
    CustomerEntity entity = createCustomerEntity();
    entity.setCompanyName(null);
    entity.setFaxNumber(null);
    entity.setNote(null);
    entity.setWebAddress(null);
    Long id = customerService.create(entity);
    assertNotNull(id);
  }

  @Test(expected = ConstraintViolationException.class)
  public void testCreateEntityWithOneInvalidField() throws MalformedURLException {
    CustomerEntity entity = createCustomerEntity();
    entity.setPhoneNumber("This is not a phone number");
    customerService.create(entity);
  }

  @Test(expected = ConstraintViolationException.class)
  public void testCreateEntityWithMultipleInvalidFields() throws MalformedURLException {
    CustomerEntity entity = createCustomerEntity();
    entity.setBirthday(LocalDate.now().plus(1, ChronoUnit.DAYS));
    entity.setEmail("invalid email");
    customerService.create(entity);
  }

  @Test(expected = ConstraintViolationException.class)
  public void testCreateEntityWithCompulsoryNullField() throws MalformedURLException {
    CustomerEntity entity = createCustomerEntity();
    entity.setName(null);
    customerService.create(entity);
  }

  @Test(expected = ConstraintViolationException.class)
  public void testCreateEntityWithInvalidDiscount() throws MalformedURLException {
    CustomerEntity entity = createCustomerEntity();
    entity.setDiscount(BigDecimal.valueOf(101.0D));
    customerService.create(entity);
  }

  @Test(expected = ValidationException.class)
  public void testCreateEntityWithInvalidBirthday() throws MalformedURLException {
    CustomerEntity entity = createCustomerEntity();
    entity.setBirthday(LocalDate.now().plus(1, ChronoUnit.DAYS));
    customerService.create(entity);
  }

  private CustomerEntity createCustomerEntity() throws MalformedURLException {
    CustomerEntity entity = new CustomerEntity();
    entity.setBillingAddress(createAddressEntity());
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
  
  private AddressEntity createAddressEntity() {
    return new AddressEntity()
        .setName("Abbey Fields")
        .setStreetAddress1("Karlsplatz 1")
        .setZipCode("1040")
        .setCity("Wien")
        .setState("Austria");
  }
}
