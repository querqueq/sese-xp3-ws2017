package at.ac.tuwien.student.sese2017.xp.hotelmanagement.service;

import at.ac.tuwien.student.sese2017.xp.hotelmanagement.domain.dto.ReservationError;
import at.ac.tuwien.student.sese2017.xp.hotelmanagement.domain.dto.ReservationError.ReservationOverlapError;
import at.ac.tuwien.student.sese2017.xp.hotelmanagement.domain.dto.ReservationWarning;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import at.ac.tuwien.student.sese2017.xp.hotelmanagement.domain.data.CustomerEntity;
import at.ac.tuwien.student.sese2017.xp.hotelmanagement.domain.data.RoomEntity;
import at.ac.tuwien.student.sese2017.xp.hotelmanagement.domain.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import at.ac.tuwien.student.sese2017.xp.hotelmanagement.domain.data.ReservationEntity;
import at.ac.tuwien.student.sese2017.xp.hotelmanagement.domain.dto.ReservationExplanation;
import at.ac.tuwien.student.sese2017.xp.hotelmanagement.domain.dto.ReservationExplanation.ReservationExplanationBuilder;
import at.ac.tuwien.student.sese2017.xp.hotelmanagement.domain.repository.ReservationRepository;
import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @author johannes
 */
@Service
@Slf4j
public class ReservationService {

  private ReservationRepository reservationRepository;

  private RoomRepository roomRepository;

  @Autowired
  public ReservationService(ReservationRepository reservationRepository, RoomRepository roomRepository) {
    this.reservationRepository = reservationRepository;
    this.roomRepository = roomRepository;
  }
  
  public ReservationExplanation checkReservation(
      List<CustomerEntity> customers, List<RoomEntity> rooms,
      LocalDateTime start, LocalDateTime end)
      throws IllegalArgumentException {

    ReservationExplanationBuilder builder = ReservationExplanation.builder();

    if (customers == null || customers.size() == 0) {
      throw new IllegalArgumentException("customer list is invalid");
    }

    if (rooms == null || rooms.size() == 0) {
      throw new IllegalArgumentException("rooms list is invalid");
    }

    if (start == null) {
      throw new IllegalArgumentException("start date is invalid");
    }

    if (end == null) {
      throw new IllegalArgumentException("end date is invalid");
    }

    if (start.isAfter(end)) {
      throw new IllegalArgumentException("end date is before start date");
    }

    List<ReservationError> reservationErrors = new ArrayList<>();
    List<ReservationWarning> reservationWarnings = new ArrayList<>();
    // Check for overlapping bookings
    for (RoomEntity room : rooms) {
      // if reservation is ok continue
      if(reservationRepository.checkForOverlapping(room, start, end).isEmpty()) {
        continue;
      }
      // otherwise add the overlapping error to the reservation errors list
      reservationErrors.add(
          ReservationOverlapError.builder().overlappingRoom(room.getRoomId()).build());

      //TODO check for mängel
    }

    if(reservationErrors.isEmpty()) {
      ReservationEntity reservationEntity = new ReservationEntity()
          .setCustomers(customers)
          .setRooms(rooms)
          .setStartTime(start)
          .setEndTime(end)
//        .setDiscount(discount)
//        .setPrice(price)
          ;

      builder.reservation(reservationEntity);
    }
    builder.errors(reservationErrors);
    builder.warnings(reservationWarnings);
    return builder.build();
  }
  
  public Long confirmReservation(ReservationEntity reservation) throws IllegalArgumentException {
    //TODO recheck reserveration
    return null;
  }
}
