package at.ac.tuwien.student.sese2017.xp.hotelmanagement.domain.data;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

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

    public ReservationRoomBooking(RoomEntity roomEntity, PriceType priceType){
        this.roomEntity = roomEntity;
        this.priceType = priceType;
    }

}
