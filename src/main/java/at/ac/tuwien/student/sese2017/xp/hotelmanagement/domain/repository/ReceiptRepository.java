package at.ac.tuwien.student.sese2017.xp.hotelmanagement.domain.repository;

import at.ac.tuwien.student.sese2017.xp.hotelmanagement.domain.data.ReceiptEntity;
import org.springframework.data.repository.CrudRepository;

/**
 * ReceiptRepository with basic CRUD.
 */
public interface ReceiptRepository extends CrudRepository<ReceiptEntity, Long>{
}
