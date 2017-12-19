package at.ac.tuwien.student.sese2017.xp.hotelmanagement.domain.repository;

import at.ac.tuwien.student.sese2017.xp.hotelmanagement.domain.data.ReceiptEntity;
import at.ac.tuwien.student.sese2017.xp.hotelmanagement.domain.repository.custom.CustomSearchRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.history.RevisionRepository;

/**
 * ReceiptRepository with basic CRUD.
 */
public interface ReceiptRepository extends CrudRepository<ReceiptEntity, Long>,
    CustomSearchRepository<ReceiptEntity>, RevisionRepository<ReceiptEntity, Long, Integer> {
}
