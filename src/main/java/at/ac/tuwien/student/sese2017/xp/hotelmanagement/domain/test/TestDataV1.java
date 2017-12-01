package at.ac.tuwien.student.sese2017.xp.hotelmanagement.domain.test;

import at.ac.tuwien.student.sese2017.xp.hotelmanagement.domain.data.AddressEntity;
import at.ac.tuwien.student.sese2017.xp.hotelmanagement.domain.data.CustomerEntity;
import at.ac.tuwien.student.sese2017.xp.hotelmanagement.domain.data.PriceType;
import at.ac.tuwien.student.sese2017.xp.hotelmanagement.domain.data.ReceiptEntity;
import at.ac.tuwien.student.sese2017.xp.hotelmanagement.domain.data.RoomEntity;
import at.ac.tuwien.student.sese2017.xp.hotelmanagement.domain.data.Sex;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Version 1 of the test data.
 * Object wrapper for test data instances
 */
public class TestDataV1 implements InjectableDataDirectory {

  /*
    ALL fields with Entity objects get injected into the database by TestDataInjectorService

    The order in which the entities get injected can be specified with the @Order annotation
   */


  //CHECKSTYLE.OFF: MemberName - Keep upper case for sudo static fields (static in spring context)
  //CHECKSTYLE.OFF: AbbreviationAsWordInName
  public final RoomEntity ROOM_1;
  public final RoomEntity ROOM_2;
  public final RoomEntity ROOM_3;
  public final RoomEntity ROOM_4;
  public final RoomEntity ROOM_5;
  public final RoomEntity ROOM_6;
  public final AddressEntity ADDRESS_HOTEL;
  public final AddressEntity ADDRESS_1;
  public final AddressEntity ADDRESS_2;
  public final AddressEntity ADDRESS_3;
  public final AddressEntity ADDRESS_4;
  public final AddressEntity ADDRESS_5;
  public final AddressEntity ADDRESS_6;
  public final CustomerEntity CUSTOMER_1;
  public final CustomerEntity CUSTOMER_2;
  public final CustomerEntity CUSTOMER_3;
  public final CustomerEntity CUSTOMER_4;
  public final CustomerEntity CUSTOMER_5;
  public final CustomerEntity CUSTOMER_6;
  public final CustomerEntity CUSTOMER_7;
  public final ReceiptEntity RECEIPT_1;
  public final ReceiptEntity RECEIPT_2;
  public final ReceiptEntity RECEIPT_3;
  public final ReceiptEntity RECEIPT_4;
  public final ReceiptEntity RECEIPT_5;
  public final ReceiptEntity RECEIPT_6;
  //CHECKSTYLE.ON: AbbreviationAsWordInName
  //CHECKSTYLE.ON: MemberName

  /**
   * Initialize the testdata fields.
   */
  public TestDataV1() {

    ROOM_1 = new RoomEntity().setName("presidentialSuite")
            .setMaxOccupants(4);


    ROOM_1.getPriceMap().put(PriceType.SINGLE, 400.21);
    ROOM_1.getPriceMap().put(PriceType.DOUBLE, 600.38);
    ROOM_1.getPriceMap().put(PriceType.TRIPLE, 721.41);
    ROOM_1.getPriceMap().put(PriceType.SINGLE_WITH_CHILD, 532.89);
    ROOM_1.getPriceMap().put(PriceType.SINGLE_WITH_TWO_CHILDREN, 610.88);
    ROOM_1.getPriceMap().put(PriceType.DOUBLE_WITH_CHILD, 690.54);

    ROOM_2 = new RoomEntity().setName("2BedRoom")
            .setMaxOccupants(2);


    ROOM_2.getPriceMap().put(PriceType.SINGLE, 70.38);
    ROOM_2.getPriceMap().put(PriceType.DOUBLE, 110.36);
    ROOM_2.getPriceMap().put(PriceType.SINGLE_WITH_CHILD, 97.89);

    ROOM_3 = new RoomEntity().setName("4BedRoom")
            .setMaxOccupants(4);


    ROOM_3.getPriceMap().put(PriceType.SINGLE, 130.21);
    ROOM_3.getPriceMap().put(PriceType.DOUBLE, 220.91);
    ROOM_3.getPriceMap().put(PriceType.TRIPLE, 300.28);
    ROOM_3.getPriceMap().put(PriceType.SINGLE_WITH_CHILD, 200.60);
    ROOM_3.getPriceMap().put(PriceType.SINGLE_WITH_TWO_CHILDREN, 240.73);
    ROOM_3.getPriceMap().put(PriceType.DOUBLE_WITH_CHILD, 260.17);

    ROOM_4 = new RoomEntity().setName("HoneyMoonSuite")
            .setMaxOccupants(2);


    ROOM_4.getPriceMap().put(PriceType.SINGLE, 240.11);
    ROOM_4.getPriceMap().put(PriceType.DOUBLE, 420.00);
    ROOM_4.getPriceMap().put(PriceType.SINGLE_WITH_CHILD, 330.90);

    ROOM_5 = new RoomEntity().setName("SingleRoom")
            .setMaxOccupants(1);


    ROOM_5.getPriceMap().put(PriceType.SINGLE, 57.66);

    ROOM_6 = new RoomEntity().setName("DoubleBedRoom")
            .setMaxOccupants(2);


    ROOM_6.getPriceMap().put(PriceType.SINGLE, 98.33);
    ROOM_6.getPriceMap().put(PriceType.DOUBLE, 170.16);
    ROOM_6.getPriceMap().put(PriceType.SINGLE_WITH_CHILD, 130.22);
    ROOM_6.getPriceMap().put(PriceType.SINGLE_WITH_TWO_CHILDREN, 175.32);

    ADDRESS_HOTEL = new AddressEntity()
            .setName("Hotel zum schoenen Urblaub")
            .setStreetAddress1("Am Buchtaler Jockl 1")
            .setZipCode("3024")
            .setCity("Lungau nahe dem Pongau")
            .setState("Austria")
    ;

    ADDRESS_1 = new AddressEntity()
            .setName("Abbey Fields")
            .setStreetAddress1("Karlsplatz 1")
            .setZipCode("1040")
            .setCity("Wien")
            .setState("Austria")
    ;

    ADDRESS_2 = new AddressEntity()
            .setName("Simon Holt")
            .setStreetAddress1("AbbeyRoad 287")
            .setZipCode("EB5 K2H")
            .setCity("London")
            .setState("United Kingdom")
    ;

    ADDRESS_3 = new AddressEntity()
            .setName("Dieter Decker")
            .setStreetAddress1("Am Rotbahnplatz 59")
            .setZipCode("11957")
            .setCity("Berlin")
            .setState("Deutschland");

    ADDRESS_4 = new AddressEntity()
            .setName("Ira T. Adkins")
            .setStreetAddress1("215 Aenean Ave")
            .setStreetAddress2("")
            .setZipCode("41990-087")
            .setCity("Sint-Denijs-Westrem")
            .setState("Oost-Vlaanderen");

    ADDRESS_5 = new AddressEntity()
            .setName("Cheryl A. Nielsen")
            .setStreetAddress1("6126 Eu Ave")
            .setStreetAddress2("")
            .setZipCode("41687-555")
            .setCity("Te Puke")
            .setState("NI")
    ;

    ADDRESS_6 = new AddressEntity()
            .setName("Cassandra V. Noble")
            .setStreetAddress1("1059 Augue St.")
            .setStreetAddress2("Ap #542")
            .setZipCode("6453")
            .setCity("Sommariva Perno")
            .setState("Piemonte")
    ;

    CUSTOMER_1 = new CustomerEntity()
            .setBillingAddress(ADDRESS_1)
            .setBirthday(LocalDate.of(1982, 7, 7))
            .setDiscount(BigDecimal.ZERO)
            .setEmail("hr.mueller@example.org")
            .setName("Abbey Fields")
            .setSex(Sex.MALE)
            .setPhoneNumber("01234567")
    ;

    CUSTOMER_2 = new CustomerEntity()
            .setBillingAddress(ADDRESS_2)
            .setBirthday(LocalDate.of(1969, 10, 26))
            .setDiscount(BigDecimal.TEN)
            .setEmail("simon.holt@gmail.com")
            .setName("Simon Holt")
            .setSex(Sex.MALE)
            .setPhoneNumber("01234568")
    ;

    CUSTOMER_3 = new CustomerEntity()
            .setBillingAddress(ADDRESS_3)
            .setBirthday(LocalDate.of(1999, 1, 23))
            .setDiscount(BigDecimal.ZERO)
            .setEmail("foo@gmail.com")
            .setName("Dieter Decker")
            .setSex(Sex.FEMALE)
            .setPhoneNumber("01234569")
    ;

    CUSTOMER_4 = new CustomerEntity()
            .setBillingAddress(ADDRESS_4)
            .setBirthday(LocalDate.of(1999, 1, 24))
            .setDiscount(BigDecimal.ZERO)
            .setEmail("josef87@gmx.at")
            .setName("Josef Gold")
            .setSex(Sex.MALE)
            .setPhoneNumber("0")
    ;

    CUSTOMER_5 = new CustomerEntity()
            .setBillingAddress(ADDRESS_5)
            .setBirthday(LocalDate.of(1999, 1, 25))
            .setDiscount(BigDecimal.ZERO)
            .setEmail("andrè.maier@student.tuwien.ac.at")
            .setName("Andrè Volker Maier")
            .setSex(Sex.MALE)
            .setPhoneNumber("0")
    ;

    CUSTOMER_6 = new CustomerEntity()
            .setBillingAddress(ADDRESS_6)
            .setBirthday(LocalDate.of(1999, 1, 26))
            .setDiscount(BigDecimal.ZERO)
            .setEmail("sascha402@gmail.com")
            .setName("Sascha Völker")
            .setSex(Sex.MALE)
            .setPhoneNumber("0")
    ;

    CUSTOMER_7 = new CustomerEntity()
            .setBillingAddress(ADDRESS_6)
            .setBirthday(LocalDate.of(1999, 1, 26))
            .setDiscount(BigDecimal.ZERO)
            .setEmail("goldstein@gmail.com")
            .setName("Hermann Goldstein")
            .setSex(Sex.MALE)
            .setPhoneNumber("0")
    ;

    RECEIPT_1 = new ReceiptEntity()
            .addCustomer(CUSTOMER_1)
            .setHotelAddress(ADDRESS_HOTEL)
            .setDurationOfStay(10)
            .addRoom(ROOM_1)
            .setPrice(6853.95)
            .setDiscount(0.05)
            .setReceiptDate(LocalDateTime.of(2017, 10, 7, 21, 6))
    ;

    RECEIPT_2 = new ReceiptEntity()
            .addCustomer(CUSTOMER_2)
            .setHotelAddress(ADDRESS_HOTEL)
            .setDurationOfStay(3)
            .addRoom(ROOM_3)
            .setPrice(601.8)
            .setDiscount(0.0)
            .setReceiptDate(LocalDateTime.of(2017, 10, 17, 8, 20))
    ;

    RECEIPT_3 = new ReceiptEntity()
            .addCustomer(CUSTOMER_3)
            .setHotelAddress(ADDRESS_HOTEL)
            .setDurationOfStay(1)
            .addRoom(ROOM_5)
            .setPrice(57.66)
            .setDiscount(0.0)
            .setReceiptDate(LocalDateTime.of(2017, 10, 19, 17, 18))
    ;

    RECEIPT_4 = new ReceiptEntity()
            .addCustomer(CUSTOMER_4)
            .setHotelAddress(ADDRESS_HOTEL)
            .setDurationOfStay(12)
            .addRoom(ROOM_2)
            .setPrice(820.95)
            .setDiscount(0.02)
            .setReceiptDate(LocalDateTime.of(2017, 10, 7, 6, 41))
    ;

    RECEIPT_5 = new ReceiptEntity()
            .addCustomer(CUSTOMER_5)
            .setHotelAddress(ADDRESS_HOTEL)
            .setDurationOfStay(3)
            .addRoom(ROOM_4)
            .setPrice(1134.0)
            .setDiscount(0.1)
            .setReceiptDate(LocalDateTime.of(2017, 10, 6, 0, 41))
    ;

    RECEIPT_6 = new ReceiptEntity()
            .addCustomer(CUSTOMER_6)
            .setHotelAddress(ADDRESS_HOTEL)
            .setDurationOfStay(2)
            .addRoom(ROOM_6)
            .setPrice(350.64)
            .setDiscount(0.0)
            .setReceiptDate(LocalDateTime.of(2017, 10, 4, 8, 20))
    ;

  }

  @Override
  public int getTestdataVersion() {
    return 1;
  }
}
