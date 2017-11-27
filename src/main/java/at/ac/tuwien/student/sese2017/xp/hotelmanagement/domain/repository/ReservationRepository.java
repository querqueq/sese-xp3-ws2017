package at.ac.tuwien.student.sese2017.xp.hotelmanagement.domain.repository;

import org.springframework.data.repository.CrudRepository;
import at.ac.tuwien.student.sese2017.xp.hotelmanagement.domain.data.ReservationEntity;

public interface ReservationRepository extends CrudRepository<Long, ReservationEntity> {

}
