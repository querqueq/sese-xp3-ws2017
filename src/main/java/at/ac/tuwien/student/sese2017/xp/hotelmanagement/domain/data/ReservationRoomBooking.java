package at.ac.tuwien.student.sese2017.xp.hotelmanagement.domain.data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Entity
@NoArgsConstructor
public class ReservationRoomBooking {
  @Id
  @GeneratedValue
  private Long rrbId;

  @ManyToOne
  @JoinColumn
  private ReservationEntity reservationEntity;

  @ManyToOne
  @JoinColumn
  private RoomEntity roomEntity;

  private PriceType priceType;

  public ReservationRoomBooking(RoomEntity roomEntity, PriceType priceType) {
    this.roomEntity = roomEntity;
    this.priceType = priceType;
  }
}
