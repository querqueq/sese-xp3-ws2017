package at.ac.tuwien.student.sese2017.xp.hotelmanagement.service;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.core.IsNot.not;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import at.ac.tuwien.student.sese2017.xp.hotelmanagement.HotelManagementApplicationTests;
import at.ac.tuwien.student.sese2017.xp.hotelmanagement.domain.data.*;
import at.ac.tuwien.student.sese2017.xp.hotelmanagement.domain.dto.ReservationError;
import at.ac.tuwien.student.sese2017.xp.hotelmanagement.domain.dto.ReservationError.ReservationOverlapError;
import at.ac.tuwien.student.sese2017.xp.hotelmanagement.domain.dto.ReservationExplanation;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;

import at.ac.tuwien.student.sese2017.xp.hotelmanagement.domain.test.*;
import lombok.extern.slf4j.Slf4j;

import org.hamcrest.Matchers;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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

    // Execute service function
    ReservationExplanation explanation = reservationService.checkReservation(
        expectedResult.getCustomers(),
        expectedResult.getRooms(),
        expectedResult.getStartTime(),
        expectedResult.getEndTime()
    );

    // Check result
    assertNotNull("Null returned by service (ReservationExplanation)", explanation);
    assertTrue("returned ReservationEntity is null", explanation.getReservation().isPresent());
    assertEquals("Result size (errors) not correct", explanation.getErrors().size(), 0);
    assertEquals("Result size (warnings) not correct", explanation.getWarnings().size(), 0);
    equalReservationEntity(explanation.getReservation().get(), expectedResult);
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
        expectedResult.getEndTime()
    );

    // Check result
    assertNotNull("Null returned by service (ReservationExplanation)", explanation);
    assertTrue("returned ReservationEntity is null", explanation.getReservation().isPresent());
    assertEquals("Result size (errors) not correct", explanation.getErrors().size(), 0);
    assertEquals("Result size (warnings) not correct", explanation.getWarnings().size(), 0);
    equalReservationEntity(explanation.getReservation().get(), expectedResult);
  }

  /**
   * Check a reservation for multiple rooms.
   */
  @Test
  public void checkReservation_validMultipleRooms() throws Exception {
    // Define expected result
    ReservationEntity expectedResult = createDummyReservation();
    expectedResult.getRooms().add(tD.ROOM_2);

    // Execute service function
    ReservationExplanation explanation = reservationService.checkReservation(
        expectedResult.getCustomers(),
        expectedResult.getRooms(),
        expectedResult.getStartTime(),
        expectedResult.getEndTime()
    );

    // Check result
    assertNotNull("Null returned by service (ReservationExplanation)", explanation);
    assertTrue("returned ReservationEntity is null", explanation.getReservation().isPresent());
    assertEquals("Result size (errors) not correct", explanation.getErrors().size(), 0);
    assertEquals("Result size (warnings) not correct", explanation.getWarnings().size(), 0);
    equalReservationEntity(explanation.getReservation().get(), expectedResult);
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
        tD.RESERVATION_1.getEndTime()
    );

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
        tD.RESERVATION_1.getEndTime()
    );
  }

  @Test(expected = IllegalArgumentException.class)
  public void checkReservation_emptyRooms() throws Exception {
    reservationService.checkReservation(
        tD.RESERVATION_1.getCustomers(),
        new ArrayList<>(),
        tD.RESERVATION_1.getStartTime(),
        tD.RESERVATION_1.getEndTime()
    );
  }

  @Test(expected = IllegalArgumentException.class)
  public void checkReservation_nullCustomers() throws Exception {
    reservationService.checkReservation(
        null,
        tD.RESERVATION_1.getRooms(),
        tD.RESERVATION_1.getStartTime(),
        tD.RESERVATION_1.getEndTime()
    );
  }

  @Test(expected = IllegalArgumentException.class)
  public void checkReservation_emptyCustomers() throws Exception {
    reservationService.checkReservation(
        new ArrayList<>(),
        tD.RESERVATION_1.getRooms(),
        tD.RESERVATION_1.getStartTime(),
        tD.RESERVATION_1.getEndTime()
    );
  }

  @Test(expected = IllegalArgumentException.class)
  public void checkReservation_nullStartDate() throws Exception {
    reservationService.checkReservation(
        tD.RESERVATION_1.getCustomers(),
        tD.RESERVATION_1.getRooms(),
        null,
        tD.RESERVATION_1.getEndTime()
    );
  }

  @Test(expected = IllegalArgumentException.class)
  public void checkReservation_nullEndDate() throws Exception {
    reservationService.checkReservation(
        tD.RESERVATION_1.getCustomers(),
        tD.RESERVATION_1.getRooms(),
        tD.RESERVATION_1.getStartTime(),
        null
    );
  }

  @Test(expected = IllegalArgumentException.class)
  public void checkReservation_endBeforeStartDate() throws Exception {
    reservationService.checkReservation(
        tD.RESERVATION_1.getCustomers(),
        tD.RESERVATION_1.getRooms(),
        tD.RESERVATION_1.getEndTime(),
        tD.RESERVATION_1.getStartTime()
    );
  }

  private ReservationEntity createDummyReservation() {
    ReservationEntity reservationEntity = new ReservationEntity();
    List<CustomerEntity> customers = new ArrayList<>();
    customers.add(tD.CUSTOMER_1);
    reservationEntity.setCustomers(customers);
    List<RoomEntity> rooms = new ArrayList<>();
    rooms.add(tD.ROOM_6);
    reservationEntity.setRooms(rooms);
    reservationEntity.setStartTime(LocalDateTime.of(2017, 10, 17, 1, 1));
    reservationEntity.setEndTime(LocalDateTime.of(2017, 10, 22, 1, 1));
    reservationEntity.setDiscount(BigDecimal.valueOf(0.0));
    reservationEntity.setPrice(200.0);
    return reservationEntity;
  }

  private void equalReservationEntity(ReservationEntity resA, ReservationEntity resB) {
    assertThat("Customers list did not match", resA.getCustomers(),
        containsInAnyOrder(resB.getCustomers()));
    assertThat("Rooms list did not match", resA.getRooms(), containsInAnyOrder(resB.getRooms()));
    assertEquals("StartTime did not match", resA.getStartTime(), resB.getStartTime());
    assertEquals("EndTime did not match", resA.getEndTime(), resB.getEndTime());
    assertEquals("Discount did not match", resA.getDiscount(), resB.getDiscount());
    assertEquals("Price did not match", resA.getPrice(), resB.getPrice());
  }
}