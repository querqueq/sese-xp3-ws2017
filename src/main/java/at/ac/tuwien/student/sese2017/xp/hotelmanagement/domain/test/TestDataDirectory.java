package at.ac.tuwien.student.sese2017.xp.hotelmanagement.domain.test;

import at.ac.tuwien.student.sese2017.xp.hotelmanagement.domain.data.AddressEntity;
import at.ac.tuwien.student.sese2017.xp.hotelmanagement.domain.data.CustomerEntity;
import at.ac.tuwien.student.sese2017.xp.hotelmanagement.domain.data.JobTitle;
import at.ac.tuwien.student.sese2017.xp.hotelmanagement.domain.data.PriceType;
import at.ac.tuwien.student.sese2017.xp.hotelmanagement.domain.data.ReceiptEntity;
import at.ac.tuwien.student.sese2017.xp.hotelmanagement.domain.data.ReservationEntity;
import at.ac.tuwien.student.sese2017.xp.hotelmanagement.domain.data.ReservationRoomBooking;
import at.ac.tuwien.student.sese2017.xp.hotelmanagement.domain.data.Role;
import at.ac.tuwien.student.sese2017.xp.hotelmanagement.domain.data.RoomEntity;
import at.ac.tuwien.student.sese2017.xp.hotelmanagement.domain.data.Sex;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import at.ac.tuwien.student.sese2017.xp.hotelmanagement.domain.data.StaffEntity;
import at.ac.tuwien.student.sese2017.xp.hotelmanagement.domain.data.VacationEntity;
import at.ac.tuwien.student.sese2017.xp.hotelmanagement.domain.data.VacationStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * Object wrapper for test data instances.
 */
@Component
public class TestDataDirectory implements InjectableDataDirectory {

  /*
    ALL fields with Entity objects get injected into the database by TestDataInjectorService

    The order in which the entities get injected can be specified with the @Order annotation
   */

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
  public final CustomerEntity DEFAULT_CUSTOMER;
  public final CustomerEntity CUSTOMER_1;
  public final CustomerEntity CUSTOMER_2;
  public final CustomerEntity CUSTOMER_3;
  public final CustomerEntity CUSTOMER_4;
  public final CustomerEntity CUSTOMER_5;
  public final CustomerEntity CUSTOMER_6;
  public final CustomerEntity CUSTOMER_7;
  public final ReservationEntity RESERVATION_1;
  public final ReceiptEntity RECEIPT_1;
  public final ReceiptEntity RECEIPT_2;
  public final ReceiptEntity RECEIPT_3;
  public final ReceiptEntity RECEIPT_4;
  public final ReceiptEntity RECEIPT_5;
  public final ReceiptEntity RECEIPT_6;
  public final StaffEntity DEFAULT_STAFF;
  public final StaffEntity STAFF_1;
  public final StaffEntity STAFF_2;
  public final StaffEntity MANAGER_1;
  public final VacationEntity VACATION_PENDING_1;
  public final VacationEntity VACATION_PENDING_2;
  public final VacationEntity VACATION_ACCEPTED_1;
  public final VacationEntity VACATION_REJECTED_1;

  public TestDataDirectory() {
    // ROOMS
    this.ROOM_1 = new RoomEntity()
        .setName("presidentialSuite")
        .setMaxOccupants(4);

    this.ROOM_1.getPriceMap().put(PriceType.SINGLE, 400.21);
    this.ROOM_1.getPriceMap().put(PriceType.DOUBLE, 600.38);
    this.ROOM_1.getPriceMap().put(PriceType.TRIPLE, 721.41);
    this.ROOM_1.getPriceMap().put(PriceType.SINGLE_WITH_CHILD, 532.89);
    this.ROOM_1.getPriceMap().put(PriceType.SINGLE_WITH_TWO_CHILDREN, 610.88);
    this.ROOM_1.getPriceMap().put(PriceType.DOUBLE_WITH_CHILD, 690.54);


    this.ROOM_2 = new RoomEntity()
        .setName("2BedRoom")
        .setMaxOccupants(2);

    this.ROOM_2.getPriceMap().put(PriceType.SINGLE, 70.38);
    this.ROOM_2.getPriceMap().put(PriceType.DOUBLE, 110.36);
    this.ROOM_2.getPriceMap().put(PriceType.SINGLE_WITH_CHILD, 97.89);


    this.ROOM_3 = new RoomEntity()
        .setName("4BedRoom")
        .setMaxOccupants(4);

    this.ROOM_3.getPriceMap().put(PriceType.SINGLE, 130.21);
    this.ROOM_3.getPriceMap().put(PriceType.DOUBLE, 220.91);
    this.ROOM_3.getPriceMap().put(PriceType.TRIPLE, 300.28);
    this.ROOM_3.getPriceMap().put(PriceType.SINGLE_WITH_CHILD, 200.60);
    this.ROOM_3.getPriceMap().put(PriceType.SINGLE_WITH_TWO_CHILDREN, 240.73);
    this.ROOM_3.getPriceMap().put(PriceType.DOUBLE_WITH_CHILD, 260.17);


    this.ROOM_4 = new RoomEntity()
        .setName("HoneyMoonSuite")
        .setMaxOccupants(2);

    this.ROOM_4.getPriceMap().put(PriceType.SINGLE, 240.11);
    this.ROOM_4.getPriceMap().put(PriceType.DOUBLE, 420.00);
    this.ROOM_4.getPriceMap().put(PriceType.SINGLE_WITH_CHILD, 330.90);


    this.ROOM_5 = new RoomEntity()
        .setName("SingleRoom")
        .setMaxOccupants(1);

    this.ROOM_5.getPriceMap().put(PriceType.SINGLE, 57.66);


    this.ROOM_6 = new RoomEntity()
        .setName("DoubleBedRoom")
        .setMaxOccupants(2);

    this.ROOM_6.getPriceMap().put(PriceType.SINGLE, 98.33);
    this.ROOM_6.getPriceMap().put(PriceType.DOUBLE, 170.16);
    this.ROOM_6.getPriceMap().put(PriceType.SINGLE_WITH_CHILD, 130.22);
    this.ROOM_6.getPriceMap().put(PriceType.SINGLE_WITH_TWO_CHILDREN, 175.32);

    // CUSTOMERS

    this.ADDRESS_HOTEL = new AddressEntity()
            .setName("Hotel zum schoenen Urblaub")
            .setStreetAddress1("Am Buchtaler Jockl 1")
            .setZipCode("3024")
            .setCity("Lungau nahe dem Pongau")
            .setState("Austria")
            ;

    this.ADDRESS_1 = new AddressEntity()
            .setName("Abbey Fields")
            .setStreetAddress1("Karlsplatz 1")
            .setZipCode("1040")
            .setCity("Wien")
            .setState("Austria")
            ;

    this.ADDRESS_2 = new AddressEntity()
            .setName("Simon Holt")
            .setStreetAddress1("AbbeyRoad 287")
            .setZipCode("EB5 K2H")
            .setCity("London")
            .setState("United Kingdom")
            ;

    this.ADDRESS_3 = new AddressEntity()
            .setName("Dieter Decker")
            .setStreetAddress1("Am Rotbahnplatz 59")
            .setZipCode("11957")
            .setCity("Berlin")
            .setState("Deutschland");

    this.ADDRESS_4 = new AddressEntity()
            .setName("Ira T. Adkins")
            .setStreetAddress1("215 Aenean Ave")
            .setStreetAddress2("")
            .setZipCode("41990-087")
            .setCity("Sint-Denijs-Westrem")
            .setState("Oost-Vlaanderen");

    this.ADDRESS_5 = new AddressEntity()
            .setName("Cheryl A. Nielsen")
            .setStreetAddress1("6126 Eu Ave")
            .setStreetAddress2("")
            .setZipCode("41687-555")
            .setCity("Te Puke")
            .setState("NI")
            ;

    this.ADDRESS_6 = new AddressEntity()
            .setName("Cassandra V. Noble")
            .setStreetAddress1("1059 Augue St.")
            .setStreetAddress2("Ap #542")
            .setZipCode("6453")
            .setCity("Sommariva Perno")
            .setState("Piemonte")
            ;

    this.DEFAULT_CUSTOMER = (CustomerEntity)new CustomerEntity()
            .setBillingAddress(ADDRESS_1)
            .setBirthday(LocalDate.of(1986, 5, 15))
            .setDiscount(BigDecimal.ZERO)
            .setEmail("customer@example.org")
            .setName("Custom customer")
            .setSex(Sex.MALE)
            .setPhoneNumber("13371337")
            .setUsername("customer")
            .setPassword(new BCryptPasswordEncoder().encode("password"))
            .setRoles(Arrays.asList(Role.CUSTOMER))
            ;

    this.CUSTOMER_1 = new CustomerEntity()
            .setBillingAddress(ADDRESS_1)
            .setBirthday(LocalDate.of(1982, 7, 7))
            .setDiscount(BigDecimal.ZERO)
            .setEmail("hr.mueller@example.org")
            .setName("Abbey Fields")
            .setSex(Sex.MALE)
            .setPhoneNumber("01234567")
            ;

    this.CUSTOMER_2 = new CustomerEntity()
            .setBillingAddress(ADDRESS_2)
            .setBirthday(LocalDate.of(1969, 10, 26))
            .setDiscount(BigDecimal.TEN)
            .setEmail("simon.holt@gmail.com")
            .setName("Simon Holt")
            .setSex(Sex.MALE)
            .setPhoneNumber("01234568")
            ;

    this.CUSTOMER_3 = new CustomerEntity()
            .setBillingAddress(ADDRESS_3)
            .setBirthday(LocalDate.of(1999, 1, 23))
            .setDiscount(BigDecimal.ZERO)
            .setEmail("foo@gmail.com")
            .setName("Dieter Decker")
            .setSex(Sex.FEMALE)
            .setPhoneNumber("01234569")
            ;

    this.CUSTOMER_4 = new CustomerEntity()
            .setBillingAddress(ADDRESS_4)
            .setBirthday(LocalDate.of(1999, 1, 24))
            .setDiscount(BigDecimal.ZERO)
            .setEmail("josef87@gmx.at")
            .setName("Josef Gold")
            .setSex(Sex.MALE)
            .setPhoneNumber("0")
            ;

    this.CUSTOMER_5 = new CustomerEntity()
            .setBillingAddress(ADDRESS_5)
            .setBirthday(LocalDate.of(1999, 1, 25))
            .setDiscount(BigDecimal.ZERO)
            .setEmail("andrè.maier@student.tuwien.ac.at")
            .setName("Andrè Volker Maier")
            .setSex(Sex.MALE)
            .setPhoneNumber("0")
            ;

    this.CUSTOMER_6 = new CustomerEntity()
            .setBillingAddress(ADDRESS_6)
            .setBirthday(LocalDate.of(1999, 1, 26))
            .setDiscount(BigDecimal.ZERO)
            .setEmail("sascha402@gmail.com")
            .setName("Sascha Völker")
            .setSex(Sex.MALE)
            .setPhoneNumber("0")
            ;

    this.CUSTOMER_7 = new CustomerEntity()
            .setBillingAddress(ADDRESS_6)
            .setBirthday(LocalDate.of(1999, 1, 26))
            .setDiscount(BigDecimal.ZERO)
            .setEmail("goldstein@gmail.com")
            .setName("Hermann Goldstein")
            .setSex(Sex.MALE)
            .setPhoneNumber("0")
            ;
    /*
    this.CUSTOMER_1 = new CustomerEntity()
        .setBillingAddress("Bäckerstraße 7, Wien 1010")
        .setBirthday(LocalDate.of(1982, 7, 7))
        .setDiscount(BigDecimal.ZERO)
        .setEmail("hr.mueller@example.org")
        .setName("Gerhard Müller")
        .setSex(Sex.MALE)
        .setPhoneNumber("01234567");

    this.CUSTOMER_2 = new CustomerEntity()
        .setBillingAddress("Abbey Road, London")
        .setBirthday(LocalDate.of(1969, 10, 26))
        .setDiscount(BigDecimal.TEN)
        .setEmail("dieter.baecker@gmail.com")
        .setName("Dieter Bäcker")
        .setSex(Sex.MALE)
        .setPhoneNumber("01234568");

    this.CUSTOMER_3 = new CustomerEntity()
        .setBillingAddress("Karlsplatz 1, 1040 Wien")
        .setBirthday(LocalDate.of(1999, 1, 23))
        .setDiscount(BigDecimal.ZERO)
        .setEmail("foo@gmail.com")
        .setName("Abbey Fields")
        .setSex(Sex.FEMALE)
        .setPhoneNumber("01234569");
    */

  List<CustomerEntity> customers = new ArrayList<>();
    customers.add(CUSTOMER_1);
    customers.add(CUSTOMER_2);
    customers.add(CUSTOMER_3);
    customers.add(CUSTOMER_4);
    customers.add(CUSTOMER_5);
    customers.add(CUSTOMER_6);
    customers.add(CUSTOMER_7);
    List<ReservationRoomBooking> rooms = new ArrayList<>();
    rooms.add(new ReservationRoomBooking().setRoomEntity(ROOM_1).setPriceType(PriceType.DOUBLE));
    rooms.add(new ReservationRoomBooking().setRoomEntity(ROOM_6).setPriceType(PriceType.SINGLE));
    this.RESERVATION_1 = new ReservationEntity()
        .setCustomers(customers)
        .setRooms(rooms)
        .setStartTime(LocalDateTime.of(2017, 10, 1, 17,20))
        .setEndTime(LocalDateTime.of(2017, 10, 6, 9,20))
        .setDiscount(BigDecimal.valueOf(0.0))
        .setPrice(700.0);

    this.RECEIPT_1 = new ReceiptEntity()
            .addCustomer(CUSTOMER_1)
            .setHotelAddress(ADDRESS_HOTEL)
            .setDurationOfStay(10)
            .addRoom(ROOM_1)
            .setPrice(6853.95)
            .setDiscount(0.05)
            .setReceiptDate(LocalDateTime.of(2017, 10, 7, 21, 6))
    ;

    this.RECEIPT_2 = new ReceiptEntity()
            .addCustomer(CUSTOMER_2)
            .setHotelAddress(ADDRESS_HOTEL)
            .setDurationOfStay(3)
            .addRoom(ROOM_3)
            .setPrice(601.8)
            .setDiscount(0.0)
            .setReceiptDate(LocalDateTime.of(2017, 10, 17, 8, 20))
    ;

    this.RECEIPT_3 = new ReceiptEntity()
            .addCustomer(CUSTOMER_3)
            .setHotelAddress(ADDRESS_HOTEL)
            .setDurationOfStay(1)
            .addRoom(ROOM_5)
            .setPrice(57.66)
            .setDiscount(0.0)
            .setReceiptDate(LocalDateTime.of(2017, 10, 19, 17, 18))
    ;

    this.RECEIPT_4 = new ReceiptEntity()
            .addCustomer(CUSTOMER_4)
            .setHotelAddress(ADDRESS_HOTEL)
            .setDurationOfStay(12)
            .addRoom(ROOM_2)
            .setPrice(820.95)
            .setDiscount(0.02)
            .setReceiptDate(LocalDateTime.of(2017, 10, 7, 6, 41))
    ;

    this.RECEIPT_5 = new ReceiptEntity()
            .addCustomer(CUSTOMER_5)
            .setHotelAddress(ADDRESS_HOTEL)
            .setDurationOfStay(3)
            .addRoom(ROOM_4)
            .setPrice(1134.0)
            .setDiscount(0.1)
            .setReceiptDate(LocalDateTime.of(2017, 10, 6, 0, 41))
    ;

    this.RECEIPT_6 = new ReceiptEntity()
            .addCustomer(CUSTOMER_6)
            .setHotelAddress(ADDRESS_HOTEL)
            .setDurationOfStay(2)
            .addRoom(ROOM_6)
            .setPrice(350.64)
            .setDiscount(0.0)
            .setReceiptDate(LocalDateTime.of(2017, 10, 4, 8, 20))
    ;

    this.DEFAULT_STAFF = (StaffEntity)new StaffEntity()
            .setBirthday(LocalDate.of(1986, 6, 22))
            .setEmail("staff@hotel.com")
            .setJobTitle(JobTitle.RECEPTIONIST)
            .setName("Staff member")
            .setSex(Sex.FEMALE)
            .setYearlyVacationDays(new HashMap<Integer, Integer>() {{put(2017, 20);}})
            .setRoles(JobTitle.RECEPTIONIST.getRoles())
            .setUsername("staff")
            .setPassword(new BCryptPasswordEncoder().encode("password"));

    this.STAFF_1 = (StaffEntity)new StaffEntity()
            .setBirthday(LocalDate.of(1998, 5, 16))
            .setEmail("receptionist@hotel.com")
            .setJobTitle(JobTitle.RECEPTIONIST)
            .setName("Stefanie Stafferson")
            .setSex(Sex.FEMALE)
            .setYearlyVacationDays(new HashMap<Integer, Integer>() {{put(2017, 20);}} )
            .setRoles(JobTitle.RECEPTIONIST.getRoles())
            .setUsername("receptionist")
            .setPassword(new BCryptPasswordEncoder().encode("password"));

    this.MANAGER_1 = (StaffEntity) new StaffEntity()
            .setBirthday(LocalDate.of(1990, 5, 10))
            .setEmail("manager@hotel.com")
            .setJobTitle(JobTitle.MANAGER)
            .setName("Michael Scott")
            .setSex(Sex.MALE)
            .setYearlyVacationDays(new HashMap<Integer, Integer>() {{put(2017, 5);}})
            .setRoles(JobTitle.MANAGER.getRoles())
            .setUsername("manager")
            .setPassword(new BCryptPasswordEncoder().encode("password"))
    ;

    this.STAFF_2 = (StaffEntity) new StaffEntity()
            .setBirthday(LocalDate.of(1990, 5, 16))
            .setEmail("n.flynn@hotel.com")
            .setJobTitle(JobTitle.CLEANER)
            .setName("Neil Flynn")
            .setSex(Sex.MALE)
            .setYearlyVacationDays(new HashMap<Integer, Integer>() {{put(2010, 22);}})
            .setRoles(JobTitle.CLEANER.getRoles())
            .setUsername("janitor")
            .setPassword(new BCryptPasswordEncoder().encode("password"))
    ;

    this.VACATION_PENDING_1 = new VacationEntity()
            .setFromDate(LocalDate.of(2020, 2, 10))
            .setToDate(LocalDate.of(2020, 2, 20))
            .setResolution(VacationStatus.PENDING)
            .setStaffer(STAFF_2)
            .setVacationDays(7)
    ;

    this.VACATION_ACCEPTED_1 = new VacationEntity()
            .setFromDate(LocalDate.of(2018, 2, 10))
            .setToDate(LocalDate.of(2018, 2, 20))
            .setResolution(VacationStatus.ACCEPTED)
            .setStaffer(STAFF_1)
            .setManager(MANAGER_1)
            .setVacationDays(7)
    ;

    this.VACATION_REJECTED_1 = new VacationEntity()
            .setFromDate(LocalDate.of(2018, 3, 10))
            .setToDate(LocalDate.of(2018, 3, 18))
            .setResolution(VacationStatus.REJECTED)
            .setStaffer(STAFF_2)
            .setManager(MANAGER_1)
            .setReason("Akt Gottes")
            .setVacationDays(7)
    ;

    this.VACATION_PENDING_2 = new VacationEntity()
            .setFromDate(LocalDate.of(2018, 1, 12))
            .setToDate(LocalDate.of(2018, 1, 19))
            .setResolution(VacationStatus.PENDING)
            .setStaffer(STAFF_2)
            .setVacationDays(5)
    ;
  }
}