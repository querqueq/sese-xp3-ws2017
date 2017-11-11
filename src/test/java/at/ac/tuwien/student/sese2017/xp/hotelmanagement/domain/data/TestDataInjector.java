package at.ac.tuwien.student.sese2017.xp.hotelmanagement.domain.data;

import javax.persistence.EntityManager;
import lombok.extern.slf4j.Slf4j;

/**
 * This Class handles the injection of all test data for the test cases.
 *
 * @author akraschitzer
 */
@Slf4j
public class TestDataInjector {

  public final static RoomEntity ROOM_1 = new RoomEntity().setName("presidentialSuite")
      .setMaxOccupants(4);
  static {
    ROOM_1.getPriceMap().put(PriceType.SINGLE, 400.21);
    ROOM_1.getPriceMap().put(PriceType.DOUBLE, 600.38);
    ROOM_1.getPriceMap().put(PriceType.TRIPLE, 721.41);
    ROOM_1.getPriceMap().put(PriceType.SINGLE_WITH_CHILD, 532.89);
    ROOM_1.getPriceMap().put(PriceType.SINGLE_WITH_TWO_CHILDREN, 610.88);
    ROOM_1.getPriceMap().put(PriceType.DOUBLE_WITH_CHILD, 690.54);
  }

  public final static RoomEntity ROOM_2 = new RoomEntity().setName("2BedRoom")
      .setMaxOccupants(2);
  static {
    ROOM_2.getPriceMap().put(PriceType.SINGLE, 70.38);
    ROOM_2.getPriceMap().put(PriceType.DOUBLE, 110.36);
    ROOM_2.getPriceMap().put(PriceType.SINGLE_WITH_CHILD, 97.89);
  }

  public final static RoomEntity ROOM_3 = new RoomEntity().setName("4BedRoom")
      .setMaxOccupants(4);
  static {
    ROOM_3.getPriceMap().put(PriceType.SINGLE, 130.21);
    ROOM_3.getPriceMap().put(PriceType.DOUBLE, 220.91);
    ROOM_3.getPriceMap().put(PriceType.TRIPLE, 300.28);
    ROOM_3.getPriceMap().put(PriceType.SINGLE_WITH_CHILD, 200.60);
    ROOM_3.getPriceMap().put(PriceType.SINGLE_WITH_TWO_CHILDREN, 240.73);
    ROOM_3.getPriceMap().put(PriceType.DOUBLE_WITH_CHILD, 260.17);
  }

  public final static RoomEntity ROOM_4 = new RoomEntity().setName("HoneyMoonSuite")
      .setMaxOccupants(2);
  static {
    ROOM_4.getPriceMap().put(PriceType.SINGLE, 240.11);
    ROOM_4.getPriceMap().put(PriceType.DOUBLE, 420.00);
    ROOM_4.getPriceMap().put(PriceType.SINGLE_WITH_CHILD, 330.90);
  }

  public final static RoomEntity ROOM_5 = new RoomEntity().setName("SingleRoom")
      .setMaxOccupants(1);
  static {
    ROOM_5.getPriceMap().put(PriceType.SINGLE, 57.66);
  }

  public final static RoomEntity ROOM_6 = new RoomEntity().setName("DoubleBedRoom")
      .setMaxOccupants(2);
  static {
    ROOM_6.getPriceMap().put(PriceType.SINGLE, 98.33);
    ROOM_6.getPriceMap().put(PriceType.DOUBLE, 170.16);
    ROOM_6.getPriceMap().put(PriceType.SINGLE_WITH_CHILD, 130.22);
    ROOM_6.getPriceMap().put(PriceType.SINGLE_WITH_TWO_CHILDREN, 175.32);
  }

  public static void injectRooms(EntityManager em) {
    log.info("Injecting rooms");
    em.persist(ROOM_1);
    em.persist(ROOM_2);
    em.persist(ROOM_3);
    em.persist(ROOM_4);
    em.persist(ROOM_5);
    em.persist(ROOM_6);
  }
  public static void clean() {
    ROOM_1.setRoomId(null);
    ROOM_2.setRoomId(null);
    ROOM_3.setRoomId(null);
    ROOM_4.setRoomId(null);
    ROOM_5.setRoomId(null);
    ROOM_6.setRoomId(null);
  }
}
