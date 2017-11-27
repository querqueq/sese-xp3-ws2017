package at.ac.tuwien.student.sese2017.xp.hotelmanagement.domain.data;

import java.util.List;
import java.util.Optional;
import lombok.Builder;
import lombok.Data;

/**
 * Explains why a reservation was not possible or warns for instance about defects of a room.
 * @author johannes
 */
@Data
@Builder
public class ReservationExplanation {
  private ReservationEntity reservation;
  private List<ReservationError> errors;
  private List<ReservationWarning> warnings;
  
  public Optional<ReservationEntity> getReservation() {
    return Optional.ofNullable(this.reservation);
  }
}
