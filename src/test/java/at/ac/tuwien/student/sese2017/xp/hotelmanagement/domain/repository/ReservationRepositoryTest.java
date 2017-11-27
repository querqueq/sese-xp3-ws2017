package at.ac.tuwien.student.sese2017.xp.hotelmanagement.domain.repository;

import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.core.IsNot.not;
import static org.hamcrest.Matchers.hasItems;
import static org.junit.Assert.*;

import at.ac.tuwien.student.sese2017.xp.hotelmanagement.HotelManagementApplicationTests;
import at.ac.tuwien.student.sese2017.xp.hotelmanagement.domain.data.ReservationEntity;
import at.ac.tuwien.student.sese2017.xp.hotelmanagement.domain.test.TestDataDirectory;
import java.time.LocalDateTime;
import java.util.List;
import javax.transaction.Transactional;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

@Transactional
public class ReservationRepositoryTest extends HotelManagementApplicationTests{

  @Autowired
  TestDataDirectory tD;

  @Autowired
  ReservationRepository reservationRepository;

  @Test
  public void checkForReservations() throws Exception {
    List<ReservationEntity> reservationEntities = reservationRepository
        .checkForOverlapping(tD.ROOM_1,
            LocalDateTime.of(2017, 10, 5, 17, 20),
            LocalDateTime.of(2017, 10, 9, 9, 20));
    assertReservation(reservationEntities);

    reservationEntities = reservationRepository
        .checkForOverlapping(tD.ROOM_1,
            LocalDateTime.of(2017, 9, 22, 17, 20),
            LocalDateTime.of(2017, 10, 2, 9, 20));
    assertReservation(reservationEntities);

    reservationEntities = reservationRepository
        .checkForOverlapping(tD.ROOM_1,
            LocalDateTime.of(2017, 9, 22, 17, 20),
            LocalDateTime.of(2017, 10, 9, 9, 20));
    assertReservation(reservationEntities);

    reservationEntities = reservationRepository
        .checkForOverlapping(tD.ROOM_1,
            LocalDateTime.of(2017, 10, 2, 17, 20),
            LocalDateTime.of(2017, 10, 4, 9, 20));
    assertReservation(reservationEntities);

    reservationEntities = reservationRepository
        .checkForOverlapping(tD.ROOM_1,
            LocalDateTime.of(2017, 10, 22, 17, 20),
            LocalDateTime.of(2017, 11, 2, 9, 20));
    assertNoReservation(reservationEntities);




    reservationEntities = reservationRepository
        .checkForOverlapping(tD.ROOM_6,
            LocalDateTime.of(2017, 10, 5, 17, 20),
            LocalDateTime.of(2017, 10, 9, 9, 20));
    assertReservation(reservationEntities);

    reservationEntities = reservationRepository
        .checkForOverlapping(tD.ROOM_6,
            LocalDateTime.of(2017, 9, 22, 17, 20),
            LocalDateTime.of(2017, 10, 2, 9, 20));
    assertReservation(reservationEntities);

    reservationEntities = reservationRepository
        .checkForOverlapping(tD.ROOM_6,
            LocalDateTime.of(2017, 9, 22, 17, 20),
            LocalDateTime.of(2017, 10, 9, 9, 20));
    assertReservation(reservationEntities);

    reservationEntities = reservationRepository
        .checkForOverlapping(tD.ROOM_6,
            LocalDateTime.of(2017, 10, 2, 17, 20),
            LocalDateTime.of(2017, 10, 4, 9, 20));
    assertReservation(reservationEntities);

    reservationEntities = reservationRepository
        .checkForOverlapping(tD.ROOM_6,
            LocalDateTime.of(2017, 10, 22, 17, 20),
            LocalDateTime.of(2017, 11, 2, 9, 20));
    assertNoReservation(reservationEntities);





    reservationEntities = reservationRepository
        .checkForOverlapping(tD.ROOM_2,
            LocalDateTime.of(2017, 10, 5, 17, 20),
            LocalDateTime.of(2017, 10, 9, 9, 20));
    assertNoReservation(reservationEntities);

    reservationEntities = reservationRepository
        .checkForOverlapping(tD.ROOM_2,
            LocalDateTime.of(2017, 9, 22, 17, 20),
            LocalDateTime.of(2017, 10, 2, 9, 20));
    assertNoReservation(reservationEntities);

    reservationEntities = reservationRepository
        .checkForOverlapping(tD.ROOM_2,
            LocalDateTime.of(2017, 9, 22, 17, 20),
            LocalDateTime.of(2017, 10, 9, 9, 20));
    assertNoReservation(reservationEntities);

    reservationEntities = reservationRepository
        .checkForOverlapping(tD.ROOM_2,
            LocalDateTime.of(2017, 10, 2, 17, 20),
            LocalDateTime.of(2017, 10, 4, 9, 20));
    assertNoReservation(reservationEntities);

    reservationEntities = reservationRepository
        .checkForOverlapping(tD.ROOM_2,
            LocalDateTime.of(2017, 10, 22, 17, 20),
            LocalDateTime.of(2017, 11, 2, 9, 20));
    assertNoReservation(reservationEntities);
  }


  private void assertReservation(List<ReservationEntity> reservationEntities) {
    assertThat("Should have returned a reservation", reservationEntities,
        hasItems(Matchers.hasProperty("reservationId", equalTo(tD.RESERVATION_1.getReservationId()))));
  }
  private void assertNoReservation(List<ReservationEntity> reservationEntities) {
    assertThat("Should not returned a reservation", reservationEntities, not(
        hasItems(Matchers.hasProperty("reservationId", equalTo(tD.RESERVATION_1.getReservationId())))));
  }

}