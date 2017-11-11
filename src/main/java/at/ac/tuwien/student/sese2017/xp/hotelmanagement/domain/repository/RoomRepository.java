package at.ac.tuwien.student.sese2017.xp.hotelmanagement.domain.repository;

import at.ac.tuwien.student.sese2017.xp.hotelmanagement.domain.data.RoomEntity;
import org.springframework.data.repository.CrudRepository;

/**
 * Repository for RoomEntity.
 *
 * <p>Provides all necessary functionality for basic crud operations concerning the RoomEntity.</p>
 */
public interface RoomRepository extends CrudRepository<RoomEntity, Long> {
}
