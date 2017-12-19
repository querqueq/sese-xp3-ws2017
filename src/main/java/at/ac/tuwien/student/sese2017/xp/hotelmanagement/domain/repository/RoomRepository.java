package at.ac.tuwien.student.sese2017.xp.hotelmanagement.domain.repository;

import at.ac.tuwien.student.sese2017.xp.hotelmanagement.domain.data.PriceType;
import at.ac.tuwien.student.sese2017.xp.hotelmanagement.domain.data.RoomEntity;
import java.util.Collection;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

/**
 * Repository for RoomEntity.
 *
 * <p>Provides all necessary functionality for basic crud operations concerning the RoomEntity.</p>
 */
public interface RoomRepository extends CrudRepository<RoomEntity, Long> {

  /**
   * Find all rooms by the 3 values.
   *
   * @param name -
   * @param minOccupants -
   * @param maxOccupants -
   * @return List of rooms
   */
  Collection<RoomEntity> findAllByNameContainingIgnoringCaseAndMaxOccupantsBetween(
      String name, Integer minOccupants, Integer maxOccupants);

  Collection<RoomEntity> findAllByNameContainingIgnoringCase(String name);

  /**
   * Find all rooms by the 3 values and having a priceType entry with value lower than maxPrice.
   *
   * @param name -
   * @param minOccupants -
   * @param maxOccupants -
   * @param priceType -
   * @param maxPrice -
   * @return List of rooms
   */
  @Query("select r from RoomEntity r join r.priceMap p where (KEY(p) = ?4 and p <= ?5) "
      + "and r.maxOccupants >= ?2 and r.maxOccupants <= ?3 and "
      + "LOWER(r.name) LIKE LOWER(concat('%', ?1, '%'))")
  Collection<RoomEntity> findAll(String name, Integer minOccupants, Integer maxOccupants,
      PriceType priceType, Double maxPrice);
}
