package at.ac.tuwien.student.sese2017.xp.hotelmanagement.domain.test;

import at.ac.tuwien.student.sese2017.xp.hotelmanagement.domain.data.CustomerEntity;
import at.ac.tuwien.student.sese2017.xp.hotelmanagement.domain.data.PriceType;
import at.ac.tuwien.student.sese2017.xp.hotelmanagement.domain.data.RoomEntity;
import at.ac.tuwien.student.sese2017.xp.hotelmanagement.domain.data.Sex;
import java.math.BigDecimal;
import java.time.LocalDate;
import org.springframework.stereotype.Component;

/**
 * Object wrapper for test data instances
 */
@Component
public class TestDataDirectory {
  public final RoomEntity ROOM_1;
  public final RoomEntity ROOM_2;
  public final RoomEntity ROOM_3;
  public final RoomEntity ROOM_4;
  public final RoomEntity ROOM_5;
  public final RoomEntity ROOM_6;
  public final CustomerEntity CUSTOMER_1;
  public final CustomerEntity CUSTOMER_2;
  public final CustomerEntity CUSTOMER_3;

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
  }
}
