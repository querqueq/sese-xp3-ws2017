package at.ac.tuwien.student.sese2017.xp.hotelmanagement.domain.test;

import at.ac.tuwien.student.sese2017.xp.hotelmanagement.config.AppProperties;
import at.ac.tuwien.student.sese2017.xp.hotelmanagement.domain.data.AddressEntity;
import at.ac.tuwien.student.sese2017.xp.hotelmanagement.domain.data.CustomerEntity;
import at.ac.tuwien.student.sese2017.xp.hotelmanagement.domain.data.JobTitle;
import at.ac.tuwien.student.sese2017.xp.hotelmanagement.domain.data.PriceType;
import at.ac.tuwien.student.sese2017.xp.hotelmanagement.domain.data.ReceiptEntity;
import at.ac.tuwien.student.sese2017.xp.hotelmanagement.domain.data.RoomEntity;
import at.ac.tuwien.student.sese2017.xp.hotelmanagement.domain.data.Sex;
import at.ac.tuwien.student.sese2017.xp.hotelmanagement.domain.data.StaffEntity;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

/**
 * This Class handles the injection of all test data for the test cases.
 *
 * @author akraschitzer
 * @author lkerck
 */
@Slf4j
@Service

public class TestDataInjector {

  /**
   * Transaction manager to initialize and commit a transaction.
   */
  private final PlatformTransactionManager txManager;
  /**
   * Entity manager.
   */
  private final EntityManager em;
  /**
   * App properties for the injectTestData property
   * is true if injectTestData flag is set true.
   * If no value is set at all it defaults to false.
   */
  private final AppProperties appProperties;


  /**
   * Initialize the testData injector.
   *
   * @param txManager Current transaction manager
   * @param em Current EM
   * @param appProperties injectTestData flag. Is set via command line --injecttestdata or
   *                       via properties file injecttestdata=true. Defaults to false if not set.
   */
  @Autowired
  public TestDataInjector(
      // Autowired, Specify correct transaction manager (multiple beans of this type can occur)
      @Qualifier("transactionManager") PlatformTransactionManager txManager,
      // Autowired above
      EntityManager em,
      // Injected by spring from properties
      AppProperties appProperties) {
    this.txManager = txManager;
    this.em = em;
    this.appProperties = appProperties;
  }

  /**
   * Inject the test data into the database.
   *
   * @author lkerck
   */
  @PostConstruct
  @Transactional
  public void inject() {
    // If injectData property is set to false or not set at all return
    if (!appProperties.isInjectTestdata()) {
      return;
    }

    /* Check if "@SpringBootTest" annotation is on classpath to identify
     * test runs and warn if testdata is used in normal operation
     */
    try {
      Class.forName("org.springframework.boot.test.context.SpringBootTest");
    } catch (ClassNotFoundException e) {
      log.warn("\n\n!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!\nTEST DATA INJECTED IN DATABASE. "
          + "USE WITH CARE\n!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!\n");
    }

    // Initialize a new transacton
    TransactionTemplate tmpl = new TransactionTemplate(txManager);
    tmpl.execute(new TransactionCallbackWithoutResult() {
      @Override
      protected void doInTransactionWithoutResult(@Nullable TransactionStatus status) {
        // Now we're in a thread with a valid transaction, injecting the test datasets now.
        log.info("Injecting test data");
        em.persist(ROOM_1);
        em.persist(ROOM_2);
        em.persist(ROOM_3);
        em.persist(ROOM_4);
        em.persist(ROOM_5);
        em.persist(ROOM_6);
        em.persist(CUSTOMER_1);
        em.persist(CUSTOMER_2);
        em.persist(CUSTOMER_3);
        em.persist(CUSTOMER_7);
        em.persist(ADDRESS_HOTEL);
        em.persist(ADDRESS_1);
        em.persist(ADDRESS_2);
        em.persist(ADDRESS_3);
        em.persist(ADDRESS_4);
        em.persist(ADDRESS_5);
        em.persist(ADDRESS_6);
        em.persist(RECEIPT_1);
        em.persist(RECEIPT_2);
        em.persist(RECEIPT_3);
        em.persist(RECEIPT_4);
        em.persist(RECEIPT_5);
        em.persist(RECEIPT_6);
        em.persist(STAFF_1);
        em.flush();
        log.info("Finished injecting test data");
      }
    });

  }

  public static final RoomEntity ROOM_1 = new RoomEntity().setName("presidentialSuite")
      .setMaxOccupants(4);

  static {
    ROOM_1.getPriceMap().put(PriceType.SINGLE, 400.21);
    ROOM_1.getPriceMap().put(PriceType.DOUBLE, 600.38);
    ROOM_1.getPriceMap().put(PriceType.TRIPLE, 721.41);
    ROOM_1.getPriceMap().put(PriceType.SINGLE_WITH_CHILD, 532.89);
    ROOM_1.getPriceMap().put(PriceType.SINGLE_WITH_TWO_CHILDREN, 610.88);
    ROOM_1.getPriceMap().put(PriceType.DOUBLE_WITH_CHILD, 690.54);
  }

  public static final RoomEntity ROOM_2 = new RoomEntity().setName("2BedRoom")
      .setMaxOccupants(2);

  static {
    ROOM_2.getPriceMap().put(PriceType.SINGLE, 70.38);
    ROOM_2.getPriceMap().put(PriceType.DOUBLE, 110.36);
    ROOM_2.getPriceMap().put(PriceType.SINGLE_WITH_CHILD, 97.89);
  }

  public static final RoomEntity ROOM_3 = new RoomEntity().setName("4BedRoom")
      .setMaxOccupants(4);

  static {
    ROOM_3.getPriceMap().put(PriceType.SINGLE, 130.21);
    ROOM_3.getPriceMap().put(PriceType.DOUBLE, 220.91);
    ROOM_3.getPriceMap().put(PriceType.TRIPLE, 300.28);
    ROOM_3.getPriceMap().put(PriceType.SINGLE_WITH_CHILD, 200.60);
    ROOM_3.getPriceMap().put(PriceType.SINGLE_WITH_TWO_CHILDREN, 240.73);
    ROOM_3.getPriceMap().put(PriceType.DOUBLE_WITH_CHILD, 260.17);
  }

  public static final RoomEntity ROOM_4 = new RoomEntity().setName("HoneyMoonSuite")
      .setMaxOccupants(2);

  static {
    ROOM_4.getPriceMap().put(PriceType.SINGLE, 240.11);
    ROOM_4.getPriceMap().put(PriceType.DOUBLE, 420.00);
    ROOM_4.getPriceMap().put(PriceType.SINGLE_WITH_CHILD, 330.90);
  }

  public static final RoomEntity ROOM_5 = new RoomEntity().setName("SingleRoom")
      .setMaxOccupants(1);

  static {
    ROOM_5.getPriceMap().put(PriceType.SINGLE, 57.66);
  }

  public static final RoomEntity ROOM_6 = new RoomEntity().setName("DoubleBedRoom")
      .setMaxOccupants(2);

  static {
    ROOM_6.getPriceMap().put(PriceType.SINGLE, 98.33);
    ROOM_6.getPriceMap().put(PriceType.DOUBLE, 170.16);
    ROOM_6.getPriceMap().put(PriceType.SINGLE_WITH_CHILD, 130.22);
    ROOM_6.getPriceMap().put(PriceType.SINGLE_WITH_TWO_CHILDREN, 175.32);
  }
  
  public static final AddressEntity ADDRESS_HOTEL = new AddressEntity()
      .setName("Hotel zum schoenen Urblaub")
      .setStreetAddress1("Am Buchtaler Jockl 1")
      .setZipCode("3024")
      .setCity("Lungau nahe dem Pongau")
      .setState("Austria")
      ;

  public static final AddressEntity ADDRESS_1 = new AddressEntity()
      .setName("Abbey Fields")
      .setStreetAddress1("Karlsplatz 1")
      .setZipCode("1040")
      .setCity("Wien")
      .setState("Austria")
      ;

  public static final AddressEntity ADDRESS_2 = new AddressEntity()
      .setName("Simon Holt")
      .setStreetAddress1("AbbeyRoad 287")
      .setZipCode("EB5 K2H")
      .setCity("London")
      .setState("United Kingdom")
      ;

  public static final AddressEntity ADDRESS_3 = new AddressEntity()
      .setName("Dieter Decker")
      .setStreetAddress1("Am Rotbahnplatz 59")
      .setZipCode("11957")
      .setCity("Berlin")
      .setState("Deutschland");

  public static final AddressEntity ADDRESS_4 = new AddressEntity()
      .setName("Ira T. Adkins")
      .setStreetAddress1("215 Aenean Ave")
      .setStreetAddress2("")
      .setZipCode("41990-087")
      .setCity("Sint-Denijs-Westrem")
      .setState("Oost-Vlaanderen");

  public static final AddressEntity ADDRESS_5 = new AddressEntity()
      .setName("Cheryl A. Nielsen")
      .setStreetAddress1("6126 Eu Ave")
      .setStreetAddress2("")
      .setZipCode("41687-555")
      .setCity("Te Puke")
      .setState("NI")
      ;

  public static final AddressEntity ADDRESS_6 = new AddressEntity()
      .setName("Cassandra V. Noble")
      .setStreetAddress1("1059 Augue St.")
      .setStreetAddress2("Ap #542")
      .setZipCode("6453")
      .setCity("Sommariva Perno")
      .setState("Piemonte")
      ;

  public static final CustomerEntity CUSTOMER_1 = new CustomerEntity()
      .setBillingAddress(ADDRESS_1)
      .setBirthday(LocalDate.of(1982, 7, 7))
      .setDiscount(BigDecimal.ZERO)
      .setEmail("hr.mueller@example.org")
      .setName("Abbey Fields")
      .setSex(Sex.MALE)
      .setPhoneNumber("01234567")
      ;

  public static final CustomerEntity CUSTOMER_2 = new CustomerEntity()
      .setBillingAddress(ADDRESS_2)
      .setBirthday(LocalDate.of(1969, 10, 26))
      .setDiscount(BigDecimal.TEN)
      .setEmail("simon.holt@gmail.com")
      .setName("Simon Holt")
      .setSex(Sex.MALE)
      .setPhoneNumber("01234568")
      ;

  public static final CustomerEntity CUSTOMER_3 = new CustomerEntity()
      .setBillingAddress(ADDRESS_3)
      .setBirthday(LocalDate.of(1999, 1, 23))
      .setDiscount(BigDecimal.ZERO)
      .setEmail("foo@gmail.com")
      .setName("Dieter Decker")
      .setSex(Sex.FEMALE)
      .setPhoneNumber("01234569")
      ;

  public static final CustomerEntity CUSTOMER_4 = new CustomerEntity()
      .setBillingAddress(ADDRESS_4)
      .setBirthday(LocalDate.of(1999, 1, 24))
      .setDiscount(BigDecimal.ZERO)
      .setEmail("josef87@gmx.at")
      .setName("Josef Gold")
      .setSex(Sex.MALE)
      .setPhoneNumber("0")
      ;

  public static final CustomerEntity CUSTOMER_5 = new CustomerEntity()
      .setBillingAddress(ADDRESS_5)
      .setBirthday(LocalDate.of(1999, 1, 25))
      .setDiscount(BigDecimal.ZERO)
      .setEmail("andrè.maier@student.tuwien.ac.at")
      .setName("Andrè Volker Maier")
      .setSex(Sex.MALE)
      .setPhoneNumber("0")
      ;
  
  public static final CustomerEntity CUSTOMER_6 = new CustomerEntity()
      .setBillingAddress(ADDRESS_6)
      .setBirthday(LocalDate.of(1999, 1, 26))
      .setDiscount(BigDecimal.ZERO)
      .setEmail("sascha402@gmail.com")
      .setName("Sascha Völker")
      .setSex(Sex.MALE)
      .setPhoneNumber("0")
      ;

  public static final CustomerEntity CUSTOMER_7 = new CustomerEntity()
      .setBillingAddress(ADDRESS_6)
      .setBirthday(LocalDate.of(1999, 1, 26))
      .setDiscount(BigDecimal.ZERO)
      .setEmail("goldstein@gmail.com")
      .setName("Hermann Goldstein")
      .setSex(Sex.MALE)
      .setPhoneNumber("0")
      ;

  public static final ReceiptEntity RECEIPT_1 = new ReceiptEntity()
      .addCustomer(CUSTOMER_1)
      .setHotelAddress(ADDRESS_HOTEL)
      .setDurationOfStay(10)
      .addRoom(ROOM_1)
      .setPrice(6853.95)
      .setDiscount(0.05)
      .setReceiptDate(LocalDateTime.of(2017, 10, 7, 21, 6))
      ;

  public static final ReceiptEntity RECEIPT_2 = new ReceiptEntity()
      .addCustomer(CUSTOMER_2)
      .setHotelAddress(ADDRESS_HOTEL)
      .setDurationOfStay(3)
      .addRoom(ROOM_3)
      .setPrice(601.8)
      .setDiscount(0.0)
      .setReceiptDate(LocalDateTime.of(2017, 10, 17, 8, 20))
      ;

  public static final ReceiptEntity RECEIPT_3 = new ReceiptEntity()
      .addCustomer(CUSTOMER_3)
      .setHotelAddress(ADDRESS_HOTEL)
      .setDurationOfStay(1)
      .addRoom(ROOM_5)
      .setPrice(57.66)
      .setDiscount(0.0)
      .setReceiptDate(LocalDateTime.of(2017, 10, 19, 17, 18))
      ;

  public static final ReceiptEntity RECEIPT_4 = new ReceiptEntity()
      .addCustomer(CUSTOMER_4)
      .setHotelAddress(ADDRESS_HOTEL)
      .setDurationOfStay(12)
      .addRoom(ROOM_2)
      .setPrice(820.95)
      .setDiscount(0.02)
      .setReceiptDate(LocalDateTime.of(2017, 10, 7, 6, 41))
      ;

  public static final ReceiptEntity RECEIPT_5 = new ReceiptEntity()
      .addCustomer(CUSTOMER_5)
      .setHotelAddress(ADDRESS_HOTEL)
      .setDurationOfStay(3)
      .addRoom(ROOM_4)
      .setPrice(1134.0)
      .setDiscount(0.1)
      .setReceiptDate(LocalDateTime.of(2017, 10, 6, 0, 41))
      ;

  public static final ReceiptEntity RECEIPT_6 = new ReceiptEntity()
      .addCustomer(CUSTOMER_6)
      .setHotelAddress(ADDRESS_HOTEL)
      .setDurationOfStay(2)
      .addRoom(ROOM_6)
      .setPrice(350.64)
      .setDiscount(0.0)
      .setReceiptDate(LocalDateTime.of(2017, 10, 4, 8, 20))
      ;
  
  @SuppressWarnings("serial")
  public static final StaffEntity STAFF_1 = new StaffEntity()
      .setBirthday(LocalDate.of(1998, 5, 16))
      .setEmail("staff@staff.com")
      .setJobTitle(JobTitle.RECEPTIONIST)
      .setName("Stefanie stafferson")
      .setSex(Sex.FEMALE)
      .setYearlyVacationDays(new HashMap<Integer, Integer>() {{put(2017, 20);}});
}
