package at.ac.tuwien.student.sese2017.xp.hotelmanagement.domain.test;

import at.ac.tuwien.student.sese2017.xp.hotelmanagement.domain.data.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * Object wrapper for test data instances
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
  public final CustomerEntity CUSTOMER_1;
  public final CustomerEntity CUSTOMER_2;
  public final CustomerEntity CUSTOMER_3;
  public final ReservationEntity RESERVATION_1;

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

    List<CustomerEntity> customers = new ArrayList<>();
    customers.add(CUSTOMER_1);
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
  }
}
