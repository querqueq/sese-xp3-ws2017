package at.ac.tuwien.student.sese2017.xp.hotelmanagement.service;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import at.ac.tuwien.student.sese2017.xp.hotelmanagement.HotelManagementApplicationTests;
import at.ac.tuwien.student.sese2017.xp.hotelmanagement.domain.data.ReceiptEntity;
import at.ac.tuwien.student.sese2017.xp.hotelmanagement.domain.repository.ReceiptRepository;
import at.ac.tuwien.student.sese2017.xp.hotelmanagement.domain.repository.RoomRepository;
import at.ac.tuwien.student.sese2017.xp.hotelmanagement.domain.test.TestDataInjector;
import java.util.List;
import javax.transaction.Transactional;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

@Transactional
public class ReceiptServiceTest extends HotelManagementApplicationTests {

  @Autowired
  ReceiptService receiptService;

  @Autowired
  private ReceiptRepository receiptRepository;
  
  @Autowired
  private RoomRepository roomRepository;

  /**
   * Find all receipt on empty db
   */
  @Test
  public void getAllReceipts_emptyDB() throws Exception {
    // Clear all receipts to enable room removal
    receiptRepository.deleteAll();
    // Define expected result
    ReceiptEntity[] expectedResult = new ReceiptEntity[]{};
    // Execute service function
    List<ReceiptEntity> allReceiptsByCriteria = receiptService.search("Non-existant");

    // Check result
    assertNotNull("Null returned by service", allReceiptsByCriteria);
    assertEquals("Result size not correct", expectedResult.length, allReceiptsByCriteria.size());
    assertThat("Not the right elements returned", allReceiptsByCriteria, containsInAnyOrder(expectedResult));
  }

  /**
   * Find only receipts between the 7th and the 18th of October 2017 (inclusive).
   *
   * <p>
   *   Should return only receipts 1, 2 and 4
   * </p>
   */
  @Test
  public void getAllReceiptsByReceiptDate() throws Exception {
    // Define expected result
    ReceiptEntity[] expectedResult = new ReceiptEntity[]{
        TestDataInjector.RECEIPT_1, TestDataInjector.RECEIPT_2, TestDataInjector.RECEIPT_4
    };
    // Execute service function
    List<ReceiptEntity> allReceiptsByCriteria = receiptService.search("2017");

    // Check result
    assertNotNull("Null returned by service", allReceiptsByCriteria);
    assertEquals("Result size not correct", expectedResult.length, allReceiptsByCriteria.size());
    assertThat("Not the right elements returned", allReceiptsByCriteria, containsInAnyOrder(expectedResult));
  }

  /**
   * Find only receipts for Rooms with "Suit" in the name.
   *
   * <p>
   *   Should return only receipt 1 and 5
   * </p>
   */
  @Test
  public void getAllReceiptsByCriteria_partialRoomNameOnly() throws Exception {
    // Define expected result
    ReceiptEntity[] expectedResult = new ReceiptEntity[]{
        TestDataInjector.RECEIPT_1, TestDataInjector.RECEIPT_5
    };

    // Execute service function
    List<ReceiptEntity> allReceiptsByCriteria = receiptService.search("presidentialSuite");
    
    receiptRepository.findAll()
    .forEach(r -> System.out.println(r));
    System.out.println();
    roomRepository.findAll()
    .forEach(r -> System.out.println(r));
    
    // Check result
    assertNotNull("Null returned by service", allReceiptsByCriteria);
    assertEquals("Result size not correct", expectedResult.length, allReceiptsByCriteria.size());
    assertThat("Not the right elements returned", allReceiptsByCriteria, containsInAnyOrder(expectedResult));
  }

//  /**
//   * Find only receipts for Rooms with 2BedRoom in the name. (Full match)
//   *
//   * <p>
//   *   Should return only receipt 4
//   * </p>
//   */
//  @Test
//  public void getAllReceiptsByCriteria_fullRoomNameOnly() throws Exception {
//    // Define expected result
//    ReceiptEntity[] expectedResult = new ReceiptEntity[]{TestDataInjector.RECEIPT_4};
//
//    // Execute service function
//    List<ReceiptEntity> allReceiptsByCriteria = receiptService
//        .getAllReceiptsByCriteria(null, null,
//            "2BedRoom",
//            null, null,
//            null, null,
//            null, null,
//            null);
//
//    // Check result
//    assertNotNull("Null returned by service", allReceiptsByCriteria);
//    assertEquals("Result size not correct", expectedResult.length, allReceiptsByCriteria.size());
//    assertThat("Not the right elements returned", allReceiptsByCriteria, containsInAnyOrder(expectedResult));
//  }
//
//  /**
//   * Find only receipts for Rooms with 2BedRoom in the name. (Full match)
//   *
//   * <p>
//   *   Should return only receipt 4
//   * </p>
//   */
//  @Test
//  public void getAllReceiptsByCriteria_fullRoomNameCaseInsensitive() throws Exception {
//    // Define expected result
//    ReceiptEntity[] expectedResult = new ReceiptEntity[]{TestDataInjector.RECEIPT_4};
//
//    // Execute service function
//    List<ReceiptEntity> allReceiptsByCriteria = receiptService
//        .getAllReceiptsByCriteria(null, null,
//            "2bedroom",
//            null, null,
//            null, null,
//            null, null,
//            null);
//
//    // Check result
//    assertNotNull("Null returned by service", allReceiptsByCriteria);
//    assertEquals("Result size not correct", expectedResult.length, allReceiptsByCriteria.size());
//    assertThat("Not the right elements returned", allReceiptsByCriteria, containsInAnyOrder(expectedResult));
//  }
//
//  /**
//   * Find only receipts with a price between 500 and 2000. (inclusive)
//   *
//   * <p>
//   *   Should return only receipts 2, 4 and 5
//   * </p>
//   */
//  @Test
//  public void getAllReceiptsByCriteria_PriceRange() throws Exception {
//    // Define expected result
//    ReceiptEntity[] expectedResult = new ReceiptEntity[]{
//        TestDataInjector.RECEIPT_2, TestDataInjector.RECEIPT_4, TestDataInjector.RECEIPT_5
//    };
//    // Execute service function
//    List<ReceiptEntity> allReceiptsByCriteria = receiptService
//        .getAllReceiptsByCriteria(null, null,
//            null,
//            500.0, 2000.0,
//            null, null,
//            null, null,
//            null);
//
//    // Check result
//    assertNotNull("Null returned by service", allReceiptsByCriteria);
//    assertEquals("Result size not correct", expectedResult.length, allReceiptsByCriteria.size());
//    assertThat("Not the right elements returned", allReceiptsByCriteria, containsInAnyOrder(expectedResult));
//  }
//
//  /**
//   * Find only receipts with prices at most 1000.
//   *
//   * <p>
//   *   Should return only receipts 2, 3, 4 and 6
//   * </p>
//   */
//  @Test
//  public void getAllReceiptsByCriteria_PriceRangeOpenDownwards() throws Exception {
//    // Define expected result
//    ReceiptEntity[] expectedResult = new ReceiptEntity[]{
//        TestDataInjector.RECEIPT_2, TestDataInjector.RECEIPT_3,
//        TestDataInjector.RECEIPT_4, TestDataInjector.RECEIPT_6
//    };
//    // Execute service function
//    List<ReceiptEntity> allReceiptsByCriteria = receiptService
//        .getAllReceiptsByCriteria(null, null,
//            null,
//            null, 1000.0,
//            null, null,
//            null, null,
//            null);
//
//    // Check result
//    assertNotNull("Null returned by service", allReceiptsByCriteria);
//    assertEquals("Result size not correct", expectedResult.length, allReceiptsByCriteria.size());
//    assertThat("Not the right elements returned", allReceiptsByCriteria, containsInAnyOrder(expectedResult));
//  }
//
//  /**
//   * Find only receipts with a price at least 1000.
//   *
//   * <p>
//   *   Should return only receipts 1, and 5
//   * </p>
//   */
//  @Test
//  public void getAllReceiptsByCriteria_PriceRangeOpenUpwards() throws Exception {
//    // Define expected result
//    ReceiptEntity[] expectedResult = new ReceiptEntity[]{
//        TestDataInjector.RECEIPT_1, TestDataInjector.RECEIPT_5
//    };
//    // Execute service function
//    List<ReceiptEntity> allReceiptsByCriteria = receiptService
//        .getAllReceiptsByCriteria(null, null,
//            null,
//            1000.0, null,
//            null, null,
//            null, null,
//            null);
//
//    // Check result
//    assertNotNull("Null returned by service", allReceiptsByCriteria);
//    assertEquals("Result size not correct", expectedResult.length, allReceiptsByCriteria.size());
//    assertThat("Not the right elements returned", allReceiptsByCriteria, containsInAnyOrder(expectedResult));
//  }
//
//  /**
//   * Find only receipts with a discount between 0.01 and 0.09. (inclusive)
//   *
//   * <p>
//   *   Should return only receipts 1 and 4
//   * </p>
//   */
//  @Test
//  public void getAllReceiptsByCriteria_DiscountRange() throws Exception {
//    // Define expected result
//    ReceiptEntity[] expectedResult = new ReceiptEntity[]{
//        TestDataInjector.RECEIPT_1, TestDataInjector.RECEIPT_4
//    };
//    // Execute service function
//    List<ReceiptEntity> allReceiptsByCriteria = receiptService
//        .getAllReceiptsByCriteria(null, null,
//            null,
//            null, null,
//            0.01, 0.09,
//            null, null,
//            null);
//
//    // Check result
//    assertNotNull("Null returned by service", allReceiptsByCriteria);
//    assertEquals("Result size not correct", expectedResult.length, allReceiptsByCriteria.size());
//    assertThat("Not the right elements returned", allReceiptsByCriteria, containsInAnyOrder(expectedResult));
//  }
//
//  /**
//   * Find only receipts with discounts lower than 0.02. (inclusive)
//   *
//   * <p>
//   *   Should return only receipts 2, 3, 4 and 6
//   * </p>
//   */
//  @Test
//  public void getAllReceiptsByCriteria_DiscountRangeOpenDownwards() throws Exception {
//    // Define expected result
//    ReceiptEntity[] expectedResult = new ReceiptEntity[]{
//        TestDataInjector.RECEIPT_2, TestDataInjector.RECEIPT_3,
//        TestDataInjector.RECEIPT_4, TestDataInjector.RECEIPT_6
//    };
//    // Execute service function
//    List<ReceiptEntity> allReceiptsByCriteria = receiptService
//        .getAllReceiptsByCriteria(null, null,
//            null,
//            null, null,
//            null, 0.02,
//            null, null,
//            null);
//
//    // Check result
//    assertNotNull("Null returned by service", allReceiptsByCriteria);
//    assertEquals("Result size not correct", expectedResult.length, allReceiptsByCriteria.size());
//    assertThat("Not the right elements returned", allReceiptsByCriteria, containsInAnyOrder(expectedResult));
//  }
//
//  /**
//   * Find only receipts with a discount at least 0.05.
//   *
//   * <p>
//   *   Should return only receipts 1, and 5
//   * </p>
//   */
//  @Test
//  public void getAllReceiptsByCriteria_DiscountRangeOpenUpwards() throws Exception {
//    // Define expected result
//    ReceiptEntity[] expectedResult = new ReceiptEntity[]{
//        TestDataInjector.RECEIPT_1, TestDataInjector.RECEIPT_5
//    };
//    // Execute service function
//    List<ReceiptEntity> allReceiptsByCriteria = receiptService
//        .getAllReceiptsByCriteria(null, null,
//            null,
//            null, null,
//            0.05, null,
//            null, null,
//            null);
//
//    // Check result
//    assertNotNull("Null returned by service", allReceiptsByCriteria);
//    assertEquals("Result size not correct", expectedResult.length, allReceiptsByCriteria.size());
//    assertThat("Not the right elements returned", allReceiptsByCriteria, containsInAnyOrder(expectedResult));
//  }
//
//  /**
//   * Find only receipts with length of stay between 3 and 10. (inclusive)
//   *
//   * <p>
//   *   Should return only receipts 1, 2, 4 and 5
//   * </p>
//   */
//  @Test
//  public void getAllReceiptsByCriteria_DaysOfStay() throws Exception {
//    // Define expected result
//    ReceiptEntity[] expectedResult = new ReceiptEntity[]{
//        TestDataInjector.RECEIPT_1, TestDataInjector.RECEIPT_2,
//        TestDataInjector.RECEIPT_4, TestDataInjector.RECEIPT_5
//    };
//    // Execute service function
//    List<ReceiptEntity> allReceiptsByCriteria = receiptService
//        .getAllReceiptsByCriteria(null, null,
//            null,
//            null, null,
//            null, null,
//            3, 10,
//            null);
//
//    // Check result
//    assertNotNull("Null returned by service", allReceiptsByCriteria);
//    assertEquals("Result size not correct", expectedResult.length, allReceiptsByCriteria.size());
//    assertThat("Not the right elements returned", allReceiptsByCriteria, containsInAnyOrder(expectedResult));
//  }
//
//  /**
//   * Find only receipts with length of stay smaller than or equal to 5.
//   *
//   * <p>
//   *   Should return only receipts 2, 3, 5 and 6
//   * </p>
//   */
//  @Test
//  public void getAllReceiptsByCriteria_DaysOfStayOpenDownwards() throws Exception {
//    // Define expected result
//    ReceiptEntity[] expectedResult = new ReceiptEntity[]{
//        TestDataInjector.RECEIPT_2, TestDataInjector.RECEIPT_3,
//        TestDataInjector.RECEIPT_5, TestDataInjector.RECEIPT_6
//    };
//    // Execute service function
//    List<ReceiptEntity> allReceiptsByCriteria = receiptService
//        .getAllReceiptsByCriteria(null, null,
//            null,
//            null, null,
//            null, null,
//            null, 3,
//            null);
//
//    // Check result
//    assertNotNull("Null returned by service", allReceiptsByCriteria);
//    assertEquals("Result size not correct", expectedResult.length, allReceiptsByCriteria.size());
//    assertThat("Not the right elements returned", allReceiptsByCriteria, containsInAnyOrder(expectedResult));
//  }
//
//  /**
//   * Find only receipts with length of stay larger than or equal to 5.
//   *
//   * <p>
//   *   Should return only receipts 1 and 4
//   * </p>
//   */
//  @Test
//  public void getAllReceiptsByCriteria_DaysOfStayOpenUpwards() throws Exception {
//    // Define expected result
//    ReceiptEntity[] expectedResult = new ReceiptEntity[]{
//        TestDataInjector.RECEIPT_1, TestDataInjector.RECEIPT_4
//    };
//    // Execute service function
//    List<ReceiptEntity> allReceiptsByCriteria = receiptService
//        .getAllReceiptsByCriteria(null, null,
//            null,
//            null, null,
//            null, null,
//            5, null,
//            null);
//
//    // Check result
//    assertNotNull("Null returned by service", allReceiptsByCriteria);
//    assertEquals("Result size not correct", expectedResult.length, allReceiptsByCriteria.size());
//    assertThat("Not the right elements returned", allReceiptsByCriteria, containsInAnyOrder(expectedResult));
//  }
//
//  /**
//   * Find only receipts for Customers with "platz" in the address.
//   *
//   * <p>
//   *   Should return only receipt 1 and 3
//   * </p>
//   */
//  @Test
//  public void getAllReceiptsByCriteria_partialCustomerAddressOnly() throws Exception {
//    // Define expected result
//    ReceiptEntity[] expectedResult = new ReceiptEntity[]{
//        TestDataInjector.RECEIPT_1, TestDataInjector.RECEIPT_3
//    };
//
//    // Execute service function
//    List<ReceiptEntity> allReceiptsByCriteria = receiptService
//        .getAllReceiptsByCriteria(null, null,
//            null,
//            null, null,
//            null, null,
//            null, null,
//            "platz");
//
//    // Check result
//    assertNotNull("Null returned by service", allReceiptsByCriteria);
//    assertEquals("Result size not correct", expectedResult.length, allReceiptsByCriteria.size());
//    assertThat("Not the right elements returned", allReceiptsByCriteria, containsInAnyOrder(expectedResult));
//  }
//
//  /**
//   * Find only receipts with a CustomerAddress having "Am Rotbahnplatz 59 11957" inside.
//   *
//   * <p>
//   *   Should return only receipt 3
//   * </p>
//   */
//  @Test
//  public void getAllReceiptsByCriteria_fullCustomerAddressOnly() throws Exception {
//    // Define expected result
//    ReceiptEntity[] expectedResult = new ReceiptEntity[]{TestDataInjector.RECEIPT_3};
//
//    // Execute service function
//    List<ReceiptEntity> allReceiptsByCriteria = receiptService
//        .getAllReceiptsByCriteria(null, null,
//            null,
//            null, null,
//            null, null,
//            null, null,
//             "Am Rotbahnplatz 59 11957");
//
//    // Check result
//    assertNotNull("Null returned by service", allReceiptsByCriteria);
//    assertEquals("Result size not correct", expectedResult.length, allReceiptsByCriteria.size());
//    assertThat("Not the right elements returned", allReceiptsByCriteria, containsInAnyOrder(expectedResult));
//  }
//
//  /**
//   * Find only receipts with an address containing "abbey" (partial match).
//   *
//   * <p>
//   *   Should return receipts 1 and 2
//   * </p>
//   */
//  @Test
//  public void getAllReceiptsByCriteria_fullCustomerAddressCaseInsensitive() throws Exception {
//    // Define expected result
//    ReceiptEntity[] expectedResult = new ReceiptEntity[]{
//        TestDataInjector.RECEIPT_1, TestDataInjector.RECEIPT_2
//    };
//
//    // Execute service function
//    List<ReceiptEntity> allReceiptsByCriteria = receiptService
//        .getAllReceiptsByCriteria(null, null,
//            null,
//            null, null,
//            null, null,
//            null, null,
//            "abbey");
//
//    // Check result
//    assertNotNull("Null returned by service", allReceiptsByCriteria);
//    assertEquals("Result size not correct", expectedResult.length, allReceiptsByCriteria.size());
//    assertThat("Not the right elements returned", allReceiptsByCriteria, containsInAnyOrder(expectedResult));
//  }
//
//  /**
//   * Find all receipts with a Room which name contains "Room"
//   * Dates before 18th of Octorber 2017
//   * the price higher than 100.0
//   * and a discount of max 0.0
//   *
//   * <p>
//   *   Should only return room 3
//   * </p>
//   */
//  @Test
//  public void getAllReceiptsByCriteria_allCriteria() throws Exception {
//    // Define expected result
//    ReceiptEntity[] expectedResult = new ReceiptEntity[]{TestDataInjector.RECEIPT_6};
//    // Execute service function
//    List<ReceiptEntity> allReceiptsByCriteria = receiptService
//        .getAllReceiptsByCriteria(null, new Date(1508277600000L),
//            "Receipt",
//            100.0, null,
//            null, 0.0,
//            null, null,
//            null);
//
//    // Check result
//    assertNotNull("Null returned by service", allReceiptsByCriteria);
//    assertEquals("Result size not correct", expectedResult.length, allReceiptsByCriteria.size());
//    assertThat("Not the right elements returned", allReceiptsByCriteria, containsInAnyOrder(expectedResult));
//  }
//
//  /**
//   * Find all receipts with a duration of stay of max 5
//   * address containing "platz"
//   *
//   * <p>
//   *   Should only return room 3
//   * </p>
//   */
//  @Test
//  public void getAllReceiptsByCriteria_allCriteriaCaseInsensitive() throws Exception {
//    // Define expected result
//    ReceiptEntity[] expectedResult = new ReceiptEntity[]{TestDataInjector.RECEIPT_3};
//    // Execute service function
//    List<ReceiptEntity> allReceiptsByCriteria = receiptService
//        .getAllReceiptsByCriteria(null, null,
//            null,
//            null, null,
//            null, 0.0,
//            null, 5,
//            "platz");
//
//    // Check result
//    assertNotNull("Null returned by service", allReceiptsByCriteria);
//    assertEquals("Result size not correct", expectedResult.length, allReceiptsByCriteria.size());
//    assertThat("Not the right elements returned", allReceiptsByCriteria, containsInAnyOrder(expectedResult));
//  }
//
//  /*
//    ======= ERROR CASES ======
//   */
//
//  /**
//   * Check price validation with min = 4 and max = 2.
//   *
//   * <p>
//   *   Should throw InvalidArgumentException
//   * </p>
//   */
//  @Test(expected = IllegalArgumentException.class)
//  public void getAllReceiptsByCriteria_PriceMaxSmallerThanMinInvalid() throws Exception {
//    List<ReceiptEntity> allReceiptsByCriteria = receiptService
//        .getAllReceiptsByCriteria(null, null,
//            null,
//            4.0, 2.0,
//            null, null,
//            null, null,
//            null);
//  }
//
//  /**
//   * Check price validation with min = -0.2.
//   *
//   * <p>
//   *   Should throw InvalidArgumentException
//   * </p>
//   */
//  @Test(expected = IllegalArgumentException.class)
//  public void getAllReceiptsByCriteria_PriceMinNegativeInvalid() throws Exception {
//    List<ReceiptEntity> allReceiptsByCriteria = receiptService
//        .getAllReceiptsByCriteria(null, null,
//            null,
//            -2.0, null,
//            null, null,
//            null, null,
//            null);
//  }
//
//  /**
//   * Check price validation with max = -0.2.
//   *
//   * <p>
//   *   Should throw InvalidArgumentException
//   * </p>
//   */
//  @Test(expected = IllegalArgumentException.class)
//  public void getAllReceiptsByCriteria_PriceMaxNegativeInvalid() throws Exception {
//    List<ReceiptEntity> allReceiptsByCriteria = receiptService
//        .getAllReceiptsByCriteria(null, null,
//            null,
//            null, -200.0,
//            null, null,
//            null, null,
//            null);
//  }
//
//  /**
//   * Check discount validation with min = 4 and max = 2.
//   *
//   * <p>
//   *   Should throw InvalidArgumentException
//   * </p>
//   */
//  @Test(expected = IllegalArgumentException.class)
//  public void getAllReceiptsByCriteria_DiscountMaxSmallerThanMinInvalid() throws Exception {
//    List<ReceiptEntity> allReceiptsByCriteria = receiptService
//        .getAllReceiptsByCriteria(null, null,
//            null,
//            null, null,
//            4.0, 2.0,
//            null, null,
//            null);
//  }
//
//  /**
//   * Check discount validation with min = -0.2.
//   *
//   * <p>
//   *   Should throw InvalidArgumentException
//   * </p>
//   */
//  @Test(expected = IllegalArgumentException.class)
//  public void getAllReceiptsByCriteria_DiscountMinNegativeInvalid() throws Exception {
//    List<ReceiptEntity> allReceiptsByCriteria = receiptService
//        .getAllReceiptsByCriteria(null, null,
//            null,
//            null, null,
//            -0.2, null,
//            null, null,
//            null);
//  }
//
//  /**
//   * Check discount validation with max = -0.4.
//   *
//   * <p>
//   *   Should throw InvalidArgumentException
//   * </p>
//   */
//  @Test(expected = IllegalArgumentException.class)
//  public void getAllReceiptsByCriteria_DiscountMaxNegativeInvalid() throws Exception {
//    List<ReceiptEntity> allReceiptsByCriteria = receiptService
//        .getAllReceiptsByCriteria(null, null,
//            null,
//            null, null,
//            null, -0.4,
//            null, null,
//            null);
//  }
//
//  /**
//   * Check discount validation with min <= 1 .
//   *
//   * <p>
//   *   Should throw InvalidArgumentException
//   * </p>
//   */
//  @Test(expected = IllegalArgumentException.class)
//  public void getAllReceiptsByCriteria_DiscountMinAtMostOneInvalid() throws Exception {
//    List<ReceiptEntity> allReceiptsByCriteria = receiptService
//        .getAllReceiptsByCriteria(null, null,
//            null,
//            null, null,
//            1.3, null,
//            null, null,
//            null);
//  }
//
//  /**
//   * Check discount validation with max <= 1 .
//   *
//   * <p>
//   *   Should throw InvalidArgumentException
//   * </p>
//   */
//  @Test(expected = IllegalArgumentException.class)
//  public void getAllReceiptsByCriteria_DiscountMaxAtMostOneInvalid() throws Exception {
//    List<ReceiptEntity> allReceiptsByCriteria = receiptService
//        .getAllReceiptsByCriteria(null, null,
//            null,
//            null, null,
//            null, 1.5,
//            null, null,
//            null);
//  }
//
//  /**
//   * Check daysOfStay validation with min = 4 and max = 2.
//   *
//   * <p>
//   *   Should throw InvalidArgumentException
//   * </p>
//   */
//  @Test(expected = IllegalArgumentException.class)
//  public void getAllReceiptsByCriteria_daysOfStayMaxSmallerThanMinInvalid() throws Exception {
//    List<ReceiptEntity> allReceiptsByCriteria = receiptService
//        .getAllReceiptsByCriteria(null, null,
//            null,
//            null, null,
//            null, null,
//            4, 2,
//            null);
//  }
//
//  /**
//   * Check daysOfStay validation with min = -1.
//   *
//   * <p>
//   *   Should throw InvalidArgumentException
//   * </p>
//   */
//  @Test(expected = IllegalArgumentException.class)
//  public void getAllReceiptsByCriteria_daysOfStayMinNegativeInvalid() throws Exception {
//    List<ReceiptEntity> allReceiptsByCriteria = receiptService
//        .getAllReceiptsByCriteria(null, null,
//            null,
//            null, null,
//            null, null,
//            -1, null,
//            null);
//  }
//
//  /**
//   * Check daysOfStay validation with -2.
//   *
//   * <p>
//   *   Should throw InvalidArgumentException
//   * </p>
//   */
//  @Test(expected = IllegalArgumentException.class)
//  public void getAllReceiptsByCriteria_daysOfStayMaxNegativeInvalid() throws Exception {
//    List<ReceiptEntity> allReceiptsByCriteria = receiptService
//        .getAllReceiptsByCriteria(null, null,
//            null,
//            null, null,
//            null, null,
//            null, -2,
//            null);
//  }
}