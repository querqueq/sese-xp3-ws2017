package at.ac.tuwien.student.sese2017.xp.hotelmanagement.domain.repository;

import at.ac.tuwien.student.sese2017.xp.hotelmanagement.domain.data.RoomEntity;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import at.ac.tuwien.student.sese2017.xp.hotelmanagement.domain.data.ReservationEntity;
import org.springframework.data.repository.query.Param;

public interface ReservationRepository extends CrudRepository<ReservationEntity, Long> {

  @Query("select r from ReservationEntity r join r.rooms p where "
      + "?1 = p.roomEntity and "
      + "(r.startTime between ?2 AND ?3 "
      + "OR r.endTime between ?2 AND ?3 "
      + "OR (r.startTime < ?2 AND r.endTime > ?2))")
  List<ReservationEntity> checkForOverlapping
      (RoomEntity room, LocalDateTime startTime, LocalDateTime endTime);

}
