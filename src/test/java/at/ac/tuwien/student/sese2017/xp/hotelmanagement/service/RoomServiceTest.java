package at.ac.tuwien.student.sese2017.xp.hotelmanagement.service;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

import at.ac.tuwien.student.sese2017.xp.hotelmanagement.HotelManagementApplicationTests;
import at.ac.tuwien.student.sese2017.xp.hotelmanagement.domain.data.PriceType;
import at.ac.tuwien.student.sese2017.xp.hotelmanagement.domain.data.RoomEntity;
import at.ac.tuwien.student.sese2017.xp.hotelmanagement.domain.test.TestDataInjector;
import at.ac.tuwien.student.sese2017.xp.hotelmanagement.domain.repository.RoomRepository;
import java.util.List;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

//@TestExecutionListeners({TransactionalTestExecutionListener.class})
@Slf4j
@Transactional
public class RoomServiceTest extends HotelManagementApplicationTests {

  @Autowired
  RoomService roomService;

  @Autowired
  private EntityManager entityManager;

  @Autowired
  private RoomRepository roomRepository;

  /**
   * Find all rooms
   */
  @Test
  public void getAllRooms_fullDB() throws Exception {
    // Define expected result
    RoomEntity[] expectedResult = new RoomEntity[]{
        TestDataInjector.ROOM_1, TestDataInjector.ROOM_2, TestDataInjector.ROOM_3,
        TestDataInjector.ROOM_4, TestDataInjector.ROOM_5, TestDataInjector.ROOM_6
    };
    // Execute service function
    List<RoomEntity> allRoomsByCriteria = roomService.getAllRooms();

    // Check result
    assertNotNull("Null returned by service", allRoomsByCriteria);
    assertEquals("Result size not correct", expectedResult.length, allRoomsByCriteria.size());
    assertThat("Not the right elements returned", allRoomsByCriteria, containsInAnyOrder(expectedResult));
  }

  /**
   * Find all rooms on empty db
   */
  @Test
  public void getAllRooms_emptyDB() throws Exception {
    // Clear rooms from database
    roomRepository.deleteAll();
    // Define expected result
    RoomEntity[] expectedResult = new RoomEntity[]{};
    // Execute service function
    List<RoomEntity> allRoomsByCriteria = roomService.getAllRooms();

    // Check result
    assertNotNull("Null returned by service", allRoomsByCriteria);
    assertEquals("Result size not correct", expectedResult.length, allRoomsByCriteria.size());
    assertThat("Not the right elements returned", allRoomsByCriteria, containsInAnyOrder(expectedResult));
  }

  /**
   * Find only rooms with BedRoom in the name.
   *
   * <p>
   *   Should return only room 2, 3 and 6
   * </p>
   */
  @Test
  public void getAllRoomsByCriteria_partialNameOnly() throws Exception {
    // Define expected result
    RoomEntity[] expectedResult = new RoomEntity[]{
        TestDataInjector.ROOM_2, TestDataInjector.ROOM_3, TestDataInjector.ROOM_6
    };
    // Execute service function
    List<RoomEntity> allRoomsByCriteria = roomService
        .getAllRoomsByCriteria("BedRoom", null, null, null, null);

    // Check result
    assertNotNull("Null returned by service", allRoomsByCriteria);
    assertEquals("Result size not correct", expectedResult.length, allRoomsByCriteria.size());
    assertThat("Not the right elements returned", allRoomsByCriteria, containsInAnyOrder(expectedResult));
  }

  /**
   * Find only rooms with 2BedRoom in the name. (Full match)
   *
   * <p>
   *   Should return only room 2
   * </p>
   */
  @Test
  public void getAllRoomsByCriteria_fullNameOnly() throws Exception {
    // Define expected result
    RoomEntity[] expectedResult = new RoomEntity[]{TestDataInjector.ROOM_2};

    // Execute service function
    List<RoomEntity> allRoomsByCriteria = roomService
        .getAllRoomsByCriteria("2BedRoom", null, null, null, null);

    // Check result
    assertNotNull("Null returned by service", allRoomsByCriteria);
    assertEquals("Result size not correct", expectedResult.length, allRoomsByCriteria.size());
    assertThat("Not the right elements returned", allRoomsByCriteria, containsInAnyOrder(expectedResult));
  }

  /**
   * Find all rooms by providing no criteria
   */
  @Test
  public void getAllRoomsByCriteria_noCriteria() throws Exception {
    // Define expected result
    RoomEntity[] expectedResult = new RoomEntity[]{
        TestDataInjector.ROOM_1, TestDataInjector.ROOM_2, TestDataInjector.ROOM_3,
        TestDataInjector.ROOM_4, TestDataInjector.ROOM_5, TestDataInjector.ROOM_6
    };
    // Execute service function
    List<RoomEntity> allRoomsByCriteria = roomService
        .getAllRoomsByCriteria(null, null, null, null, null);

    // Check result
    assertNotNull("Null returned by service", allRoomsByCriteria);
    assertEquals("Result size not correct", expectedResult.length, allRoomsByCriteria.size());
    assertThat("Not the right elements returned", allRoomsByCriteria, containsInAnyOrder(expectedResult));
  }

  /**
   * Find all rooms with occupants grater or equal 2
   *
   * <p>
   *   Should return all rooms except room 5
   * </p>
   */
  @Test
  public void getAllRoomsByCriteria_minOccupants() throws Exception {
    // Define expected result
    RoomEntity[] expectedResult = new RoomEntity[]{
        TestDataInjector.ROOM_1, TestDataInjector.ROOM_2, TestDataInjector.ROOM_3,
        TestDataInjector.ROOM_4, TestDataInjector.ROOM_6
    };
    // Execute service function
    List<RoomEntity> allRoomsByCriteria = roomService
        .getAllRoomsByCriteria(null, 2, null, null, null);

    // Check result
    assertNotNull("Null returned by service", allRoomsByCriteria);
    assertEquals("Result size not correct", expectedResult.length, allRoomsByCriteria.size());
    assertThat("Not the right elements returned", allRoomsByCriteria, containsInAnyOrder(expectedResult));
  }

  /**
   * Find all rooms with occupants smaller or equal 2
   *
   * <p>
   *   Should return rooms 2,4,5 and 6
   * </p>
   */
  @Test
  public void getAllRoomsByCriteria_maxOccupants() throws Exception {
    // Define expected result
    RoomEntity[] expectedResult = new RoomEntity[]{
        TestDataInjector.ROOM_2, TestDataInjector.ROOM_4, TestDataInjector.ROOM_5,
        TestDataInjector.ROOM_6
    };
    // Execute service function
    List<RoomEntity> allRoomsByCriteria = roomService
        .getAllRoomsByCriteria(null, null, 2, null, null);

    // Check result
    assertNotNull("Null returned by service", allRoomsByCriteria);
    assertEquals("Result size not correct", expectedResult.length, allRoomsByCriteria.size());
    assertThat("Not the right elements returned", allRoomsByCriteria, containsInAnyOrder(expectedResult));
  }

  /**
   * Find all rooms with occupants equal 2
   *
   * <p>
   *   Should return rooms 2,4 and 6
   * </p>
   */
  @Test
  public void getAllRoomsByCriteria_minAndMaxOccupants() throws Exception {
    // Define expected result
    RoomEntity[] expectedResult = new RoomEntity[]{
        TestDataInjector.ROOM_2, TestDataInjector.ROOM_4, TestDataInjector.ROOM_6
    };
    // Execute service function
    List<RoomEntity> allRoomsByCriteria = roomService
        .getAllRoomsByCriteria(null, 2, 2, null, null);

    // Check result
    assertNotNull("Null returned by service", allRoomsByCriteria);
    assertEquals("Result size not correct", expectedResult.length, allRoomsByCriteria.size());
    assertThat("Not the right elements returned", allRoomsByCriteria, containsInAnyOrder(expectedResult));
  }

  /**
   * Find all rooms with occupants equal 2 and name containing "Room"
   *
   * <p>
   *   Should return rooms 2 and 6
   * </p>
   */
  @Test
  public void getAllRoomsByCriteria_minAndMaxAndName() throws Exception {
    // Define expected result
    RoomEntity[] expectedResult = new RoomEntity[]{
        TestDataInjector.ROOM_2, TestDataInjector.ROOM_6
    };
    // Execute service function
    List<RoomEntity> allRoomsByCriteria = roomService
        .getAllRoomsByCriteria("Room", 2, 2, null, null);

    // Check result
    assertNotNull("Null returned by service", allRoomsByCriteria);
    assertEquals("Result size not correct", expectedResult.length, allRoomsByCriteria.size());
    assertThat("Not the right elements returned", allRoomsByCriteria, containsInAnyOrder(expectedResult));
  }
  /**
   * Find all rooms with SinglePrice below 100
   *
   * <p>
   *   Should return rooms 2,5 and 6
   * </p>
   */
  @Test
  public void getAllRoomsByCriteria_singlePrice() throws Exception {
    // Define expected result
    RoomEntity[] expectedResult = new RoomEntity[]{
        TestDataInjector.ROOM_2, TestDataInjector.ROOM_5, TestDataInjector.ROOM_6
    };
    // Execute service function
    List<RoomEntity> allRoomsByCriteria = roomService
        .getAllRoomsByCriteria(null, null, null, PriceType.SINGLE, 100.0);

    // Check result
    assertNotNull("Null returned by service", allRoomsByCriteria);
    assertEquals("Result size not correct", expectedResult.length, allRoomsByCriteria.size());
    assertThat("Not the right elements returned", allRoomsByCriteria, containsInAnyOrder(expectedResult));
  }

  /**
   * Find all rooms with Triple below 300.28
   *
   * <p>
   *   Should only return room 3
   * </p>
   */
  @Test
  public void getAllRoomsByCriteria_triplePrice() throws Exception {
    // Define expected result
    RoomEntity[] expectedResult = new RoomEntity[]{
        TestDataInjector.ROOM_3
    };
    // Execute service function
    List<RoomEntity> allRoomsByCriteria = roomService
        .getAllRoomsByCriteria(null, null, null, PriceType.TRIPLE, 300.28);

    // Check result
    assertNotNull("Null returned by service", allRoomsByCriteria);
    assertEquals("Result size not correct", expectedResult.length, allRoomsByCriteria.size());
    assertThat("Not the right elements returned", allRoomsByCriteria, containsInAnyOrder(expectedResult));
  }

  /**
   * Find all rooms with name containing Room
   * with min and max 2 and 4
   * and Single price below 98.33
   *
   * <p>
   *   Should only return room 3
   * </p>
   */
  @Test
  public void getAllRoomsByCriteria_allCriterias() throws Exception {
    // Define expected result
    RoomEntity[] expectedResult = new RoomEntity[]{
        TestDataInjector.ROOM_2, TestDataInjector.ROOM_6
    };
    // Execute service function
    List<RoomEntity> allRoomsByCriteria = roomService
        .getAllRoomsByCriteria("Room", 2, 4, PriceType.SINGLE, 98.33);

    // Check result
    assertNotNull("Null returned by service", allRoomsByCriteria);
    assertEquals("Result size not correct", expectedResult.length, allRoomsByCriteria.size());
    assertThat("Not the right elements returned", allRoomsByCriteria, containsInAnyOrder(expectedResult));
  }

  /*
    ======= ERROR CASES ======
   */

  /**
   * Check integer validation with min = 4 and max = 2.
   *
   * <p>
   *   Should throw InvalidArgumentException
   * </p>
   */
  @Test(expected = IllegalArgumentException.class)
  public void getAllRoomsByCriteria_minAndMaxOccupantsInvalid() throws Exception {
    roomService.getAllRoomsByCriteria(null, 4, 2, null, null);
  }

  /**
   * Check integer validation with min negative
   *
   * <p>
   *   Should throw InvalidArgumentException
   * </p>
   */
  @Test(expected = IllegalArgumentException.class)
  public void getAllRoomsByCriteria_minOccupantsInvalid() throws Exception {
    roomService.getAllRoomsByCriteria(null, -1, null, null, null);
  }

  /**
   * Check integer validation with max negative
   *
   * <p>
   *   Should throw InvalidArgumentException
   * </p>
   */
  @Test(expected = IllegalArgumentException.class)
  public void getAllRoomsByCriteria_maxOccupantsInvalid() throws Exception {
    roomService.getAllRoomsByCriteria(null, null, -1, null, null);
  }

  /**
   * Check only PriceType given without maxPrice
   *
   * <p>
   *   Should throw InvalidArgumentException
   * </p>
   */
  @Test(expected = IllegalArgumentException.class)
  public void getAllRoomsByCriteria_onlyPriceTypeGiven() throws Exception {
    roomService.getAllRoomsByCriteria(null, null, null, PriceType.SINGLE, null);
  }

  /**
   * Check only maxPrice given without PriceType
   *
   * <p>
   *   Should throw InvalidArgumentException
   * </p>
   */
  @Test(expected = IllegalArgumentException.class)
  public void getAllRoomsByCriteria_onlyMaxPriceGiven() throws Exception {
    roomService.getAllRoomsByCriteria(null, null, null, null, 2.2);
  }

  /**
   * Check integer validation with maxPrice invalid.
   *
   * <p>
   *   Should throw InvalidArgumentException
   * </p>
   */
  @Test(expected = IllegalArgumentException.class)
  public void getAllRoomsByCriteria_maxPriceInvalid() throws Exception {
    roomService.getAllRoomsByCriteria(null, null, null, null, -1.0);
  }
}