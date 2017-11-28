package at.ac.tuwien.student.sese2017.xp.hotelmanagement.service;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItems;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import at.ac.tuwien.student.sese2017.xp.hotelmanagement.HotelManagementApplicationTests;
import at.ac.tuwien.student.sese2017.xp.hotelmanagement.domain.data.CustomerEntity;
import at.ac.tuwien.student.sese2017.xp.hotelmanagement.domain.data.PriceType;
import at.ac.tuwien.student.sese2017.xp.hotelmanagement.domain.data.ReservationEntity;
import at.ac.tuwien.student.sese2017.xp.hotelmanagement.domain.data.ReservationRoomBooking;
import at.ac.tuwien.student.sese2017.xp.hotelmanagement.domain.dto.ReservationError;
import at.ac.tuwien.student.sese2017.xp.hotelmanagement.domain.dto.ReservationError.ReservationOverlapError;
import at.ac.tuwien.student.sese2017.xp.hotelmanagement.domain.dto.ReservationExplanation;
import at.ac.tuwien.student.sese2017.xp.hotelmanagement.domain.test.TestDataDirectory;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.hamcrest.Matchers;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

@Slf4j
@Transactional
public class ReservationServiceTest extends HotelManagementApplicationTests {


  private ReservationExplanation RESERVATION_EXPLANATION_ROOMALREADYBOOKED;


  @Autowired
  ReservationService reservationService;

  @Autowired
  TestDataDirectory tD;

  @PostConstruct
  public void init() {
    List<ReservationError> errors = new ArrayList<>();
    errors.add(ReservationError.ReservationOverlapError.builder().
        overlappingRoom(tD.ROOM_1.getRoomId()).build());
    errors.add(ReservationError.ReservationOverlapError.builder().
        overlappingRoom(tD.ROOM_6.getRoomId()).build());
    RESERVATION_EXPLANATION_ROOMALREADYBOOKED =
        ReservationExplanation.builder().errors(errors).build();
  }

  /**
   * Create a reservation
   */
  @Test
  @Ignore // Currently not implemented
  public void confirmReservation() {
    // Execute service function
    Long id = reservationService.confirmReservation(createDummyReservation());

    // Check result
    assertNotNull("Null returned by service (ReservationExplanation)", id);
  }

  /**
   * Check a reservation with getStartTime() = getEndTime().
   */
  @Test
  public void checkReservation_validStartDateEqualsEndDate() {
    // Define expected result
    ReservationEntity expectedResult = createDummyReservation();
    expectedResult.setStartTime(expectedResult.getEndTime());
    expectedResult.setPrice(0.0);

    // Execute service function
    ReservationExplanation explanation = reservationService.checkReservation(
        expectedResult.getCustomers(),
        expectedResult.getRooms(),
        expectedResult.getStartTime(),
        expectedResult.getEndTime(),
        expectedResult.getDiscount());

    // Check result
    assertNotNull("Null returned by service (ReservationExplanation)", explanation);
    assertTrue("returned ReservationEntity is null", explanation.getReservation().isPresent());
    assertEquals("Result size (errors) not correct", explanation.getErrors().size(), 0);
    assertEquals("Result size (warnings) not correct", explanation.getWarnings().size(), 0);
    equalReservationEntity(expectedResult, explanation.getReservation().get());
  }

  /**
   * Check a reservation for multiple customers.
   */
  @Test
  public void checkReservation_validMultipleCustomers() {
    // Define expected result
    ReservationEntity expectedResult = createDummyReservation();
    expectedResult.getCustomers().add(tD.CUSTOMER_2);

    // Execute service function
    ReservationExplanation explanation = reservationService.checkReservation(
        expectedResult.getCustomers(),
        expectedResult.getRooms(),
        expectedResult.getStartTime(),
        expectedResult.getEndTime(),
        expectedResult.getDiscount());

    // Check result
    assertNotNull("Null returned by service (ReservationExplanation)", explanation);
    assertTrue("returned ReservationEntity is null", explanation.getReservation().isPresent());
    assertEquals("Result size (errors) not correct", explanation.getErrors().size(), 0);
    assertEquals("Result size (warnings) not correct", explanation.getWarnings().size(), 0);
    equalReservationEntity(expectedResult, explanation.getReservation().get());
  }

  /**
   * Check a reservation with discount.
   */
  @Test
  public void checkReservation_validDiscount() {
    // Define expected result
    ReservationEntity expectedResult = createDummyReservation();
    expectedResult.setDiscount(BigDecimal.valueOf(20));
    expectedResult.setPrice(expectedResult.getPrice()*0.8);

    // Execute service function
    ReservationExplanation explanation = reservationService.checkReservation(
            expectedResult.getCustomers(),
            expectedResult.getRooms(),
            expectedResult.getStartTime(),
            expectedResult.getEndTime(),
            expectedResult.getDiscount());

    // Check result
    assertNotNull("Null returned by service (ReservationExplanation)", explanation);
    assertTrue("returned ReservationEntity is null", explanation.getReservation().isPresent());
    assertEquals("Result size (errors) not correct", explanation.getErrors().size(), 0);
    assertEquals("Result size (warnings) not correct", explanation.getWarnings().size(), 0);
    equalReservationEntity(expectedResult, explanation.getReservation().get());
  }


  /**
   * Check a reservation for multiple rooms.
   */
  @Test
  public void checkReservation_validMultipleRooms() throws Exception {
    // Define expected result
    ReservationEntity expectedResult = createDummyReservation();
    expectedResult.getRooms().add(new ReservationRoomBooking(tD.ROOM_2, PriceType.SINGLE));
    expectedResult.setPrice(expectedResult.getPrice() + 351.9);

    // Execute service function
    ReservationExplanation explanation = reservationService.checkReservation(
        expectedResult.getCustomers(),
        expectedResult.getRooms(),
        expectedResult.getStartTime(),
        expectedResult.getEndTime(),
        expectedResult.getDiscount());

    // Check result
    assertNotNull("Null returned by service (ReservationExplanation)", explanation);
    assertTrue("returned ReservationEntity is null", explanation.getReservation().isPresent());
    assertEquals("Result size (errors) not correct", explanation.getErrors().size(), 0);
    assertEquals("Result size (warnings) not correct", explanation.getWarnings().size(), 0);
    equalReservationEntity(expectedResult, explanation.getReservation().get());
  }

  /**
   * Check Reservation with invalid room booking.
   */
  @Test
  public void checkReservation_invalidBooking() throws Exception {
    // Define expected result
    ReservationEntity expectedResult = createDummyReservation();
    expectedResult.getRooms().add(new ReservationRoomBooking(tD.ROOM_2, PriceType.TRIPLE));
    expectedResult.setPrice(expectedResult.getPrice() + 351.9);

    // Execute service function
    try {
      ReservationExplanation explanation = reservationService.checkReservation(
              expectedResult.getCustomers(),
              expectedResult.getRooms(),
              expectedResult.getStartTime(),
              expectedResult.getEndTime(),
              expectedResult.getDiscount());
      fail();
    } catch(IllegalArgumentException e) {
      // Success
    }

  }

  @Test
  public void x() {
    List<ReservationOverlapError> build = new ArrayList<>();
    build.add(ReservationOverlapError.builder().
        overlappingRoom(tD.ROOM_1.getRoomId()).build());
    List<ReservationOverlapError> build2 = new ArrayList<>();
    build2.add(ReservationOverlapError.builder().
        overlappingRoom(tD.ROOM_1.getRoomId()).build());

    assertThat("Not the right elements returned", build,
        hasItems(Matchers.hasProperty("overlappingRoom", equalTo(tD.ROOM_1.getRoomId()))));

  }

  /**
   * A reservation can not be created for a room that is already reserved.
   *
   * <p>Should return the RESERVATION_EXPLANATION_ROOMALREADYBOOKED</p>
   */
  @Test
  public void checkReservation_roomAlreadyBooked() throws Exception {
    // Define expected result
    ReservationExplanation expectedResult = RESERVATION_EXPLANATION_ROOMALREADYBOOKED;

    // Execute service function
    ReservationExplanation explanation = reservationService.checkReservation(
        tD.RESERVATION_1.getCustomers(),
        tD.RESERVATION_1.getRooms(),
        tD.RESERVATION_1.getStartTime(),
        tD.RESERVATION_1.getEndTime(),
        tD.RESERVATION_1.getDiscount());

    // Check result
    assertNotNull("Null returned by service", explanation);
    assertNotNull("Returned Errors is null", explanation.getErrors());
    assertNotNull("Returned Warnings is null", explanation.getWarnings());
    assertEquals("Result size not correct", expectedResult.getErrors().size(),
        explanation.getErrors().size());
    assertEquals("Result size not correct", 0, explanation.getWarnings().size());
    assertThat("Not the right elements returned", explanation.getErrors(),
        hasItems(Matchers.hasProperty("overlappingRoom", equalTo(tD.ROOM_1.getRoomId()))));
    assertThat("Not the right elements returned", explanation.getErrors(),
        hasItems(Matchers.hasProperty("overlappingRoom", equalTo(tD.ROOM_6.getRoomId()))));
  }

  @Test(expected = IllegalArgumentException.class)
  public void checkReservation_nullRooms() throws Exception {
    reservationService.checkReservation(
        null,
        tD.RESERVATION_1.getRooms(),
        tD.RESERVATION_1.getStartTime(),
        tD.RESERVATION_1.getEndTime(),
        tD.RESERVATION_1.getDiscount());
  }

  @Test(expected = IllegalArgumentException.class)
  public void checkReservation_emptyRooms() throws Exception {
    reservationService.checkReservation(
        tD.RESERVATION_1.getCustomers(),
        new ArrayList<>(),
        tD.RESERVATION_1.getStartTime(),
        tD.RESERVATION_1.getEndTime(),
        tD.RESERVATION_1.getDiscount());
  }

  @Test(expected = IllegalArgumentException.class)
  public void checkReservation_nullCustomers() throws Exception {
    reservationService.checkReservation(
        null,
        tD.RESERVATION_1.getRooms(),
        tD.RESERVATION_1.getStartTime(),
        tD.RESERVATION_1.getEndTime(),
        tD.RESERVATION_1.getDiscount());
  }

  @Test(expected = IllegalArgumentException.class)
  public void checkReservation_emptyCustomers() throws Exception {
    reservationService.checkReservation(
        new ArrayList<>(),
        tD.RESERVATION_1.getRooms(),
        tD.RESERVATION_1.getStartTime(),
        tD.RESERVATION_1.getEndTime(),
        tD.RESERVATION_1.getDiscount());
  }

  @Test(expected = IllegalArgumentException.class)
  public void checkReservation_nullStartDate() throws Exception {
    reservationService.checkReservation(
        tD.RESERVATION_1.getCustomers(),
        tD.RESERVATION_1.getRooms(),
        null,
        tD.RESERVATION_1.getEndTime(),
        tD.RESERVATION_1.getDiscount());
  }

  @Test(expected = IllegalArgumentException.class)
  public void checkReservation_nullEndDate() throws Exception {
    reservationService.checkReservation(
        tD.RESERVATION_1.getCustomers(),
        tD.RESERVATION_1.getRooms(),
        tD.RESERVATION_1.getStartTime(),
        null,
        tD.RESERVATION_1.getDiscount());
  }

  @Test(expected = IllegalArgumentException.class)
  public void checkReservation_nullDiscount() throws Exception {
    reservationService.checkReservation(
            tD.RESERVATION_1.getCustomers(),
            tD.RESERVATION_1.getRooms(),
            tD.RESERVATION_1.getStartTime(),
            tD.RESERVATION_1.getEndTime(),
            null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void checkReservation_negativeDiscount() throws Exception {
    reservationService.checkReservation(
            tD.RESERVATION_1.getCustomers(),
            tD.RESERVATION_1.getRooms(),
            tD.RESERVATION_1.getStartTime(),
            tD.RESERVATION_1.getEndTime(),
            BigDecimal.valueOf(-2.0));
  }

  @Test(expected = IllegalArgumentException.class)
  public void checkReservation_discountToBig() throws Exception {
    reservationService.checkReservation(
            tD.RESERVATION_1.getCustomers(),
            tD.RESERVATION_1.getRooms(),
            tD.RESERVATION_1.getStartTime(),
            tD.RESERVATION_1.getEndTime(),
            BigDecimal.valueOf(101.0));
  }

  @Test(expected = IllegalArgumentException.class)
  public void checkReservation_endBeforeStartDate() throws Exception {
    reservationService.checkReservation(
        tD.RESERVATION_1.getCustomers(),
        tD.RESERVATION_1.getRooms(),
        tD.RESERVATION_1.getEndTime(),
        tD.RESERVATION_1.getStartTime(),
        tD.RESERVATION_1.getDiscount());
  }

  private ReservationEntity createDummyReservation() {
    ReservationEntity reservationEntity = new ReservationEntity();
    List<CustomerEntity> customers = new ArrayList<>();
    customers.add(tD.CUSTOMER_1);
    reservationEntity.setCustomers(customers);
    List<ReservationRoomBooking> rooms = new ArrayList<>();
    rooms.add(new ReservationRoomBooking(tD.ROOM_6, PriceType.DOUBLE));
    reservationEntity.setRooms(rooms);
    reservationEntity.setStartTime(LocalDateTime.of(2017, 10, 17, 1, 1));
    reservationEntity.setEndTime(LocalDateTime.of(2017, 10, 22, 1, 1));
    reservationEntity.setDiscount(BigDecimal.valueOf(0.0));
    reservationEntity.setPrice(850.8);
    return reservationEntity;
  }

  private void equalReservationEntity(ReservationEntity expected, ReservationEntity actual) {
    List<CustomerEntity> customers1 = expected.getCustomers();
    List<CustomerEntity> customers2 = actual.getCustomers();
    assertEquals(customers1.get(0), customers2.get(0));
    assertThat("Customers list did not match", actual.getCustomers(),
        containsInAnyOrder(expected.getCustomers().toArray()));
    assertThat("Rooms list did not match", actual.getRooms(), containsInAnyOrder(expected.getRooms().toArray()));
    assertEquals("StartTime did not match", expected.getStartTime(), actual.getStartTime());
    assertEquals("EndTime did not match", expected.getEndTime(), actual.getEndTime());
    assertEquals("Discount did not match", expected.getDiscount(), actual.getDiscount());
    assertEquals("Price did not match", expected.getPrice(), actual.getPrice());
  }
}