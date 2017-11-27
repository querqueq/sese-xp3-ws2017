package at.ac.tuwien.student.sese2017.xp.hotelmanagement.domain.data;

import java.time.LocalDateTime;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.Size;
import lombok.Data;

@Data
@Entity
public class ReservationEntity {

  /**
   * Unique id of a reservation.
   */
  @Id
  @GeneratedValue
  private Long reservationId;
  
  @OneToMany
  @Size(min=1)
  private List<RoomEntity> rooms;
  
  @OneToMany
  @Size(min=1)
  private List<CustomerEntity> customers;
  
  private LocalDateTime startTime;
  
  private LocalDateTime endTime;
  
  private Double discount;
  
  private Double price;
}
