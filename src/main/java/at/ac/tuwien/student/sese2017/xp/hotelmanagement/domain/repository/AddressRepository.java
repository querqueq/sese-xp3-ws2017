package at.ac.tuwien.student.sese2017.xp.hotelmanagement.domain.repository;

import at.ac.tuwien.student.sese2017.xp.hotelmanagement.domain.data.AddressEntity;
import java.util.List;
import org.springframework.data.repository.CrudRepository;

/**
 * AddressRepository with basic CRUD.
 */
public interface AddressRepository extends CrudRepository<AddressEntity, Long> {
  List<AddressEntity> findAllByNameContainingIgnoreCase(String name);
}
