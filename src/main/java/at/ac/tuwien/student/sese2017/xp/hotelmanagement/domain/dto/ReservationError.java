package at.ac.tuwien.student.sese2017.xp.hotelmanagement.domain.dto;

import lombok.Builder;
import lombok.Data;

public abstract class ReservationError {
  @Data
  @Builder
  public static class ReservationOverlapError extends ReservationError {
    private Long overlappingRoom;
  }
}
