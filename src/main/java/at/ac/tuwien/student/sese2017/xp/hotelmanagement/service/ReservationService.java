package at.ac.tuwien.student.sese2017.xp.hotelmanagement.service;

import java.time.LocalDateTime;
import java.util.List;
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

  @Autowired
  public ReservationService(ReservationRepository reservationRepository) {
    this.reservationRepository = reservationRepository;
  }
  
  public ReservationExplanation checkReservation(List<Long> customers
      , List<Long> rooms
      , LocalDateTime start
      , LocalDateTime end) {
    ReservationExplanationBuilder builder = ReservationExplanation.builder();
    //TODO check for überschneidungen
    //TODO check for mängel
   return builder.build();
  }
  
  public Long confirmReservation(ReservationEntity reservation) {
    //TODO recheck reserveration
    return null;
  }
}
