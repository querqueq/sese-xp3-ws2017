package at.ac.tuwien.student.sese2017.xp.hotelmanagement.domain.repository;

import at.ac.tuwien.student.sese2017.xp.hotelmanagement.domain.data.CustomerEntity;
import at.ac.tuwien.student.sese2017.xp.hotelmanagement.domain.repository.custom.CustomSearchRepository;
import org.springframework.data.repository.CrudRepository;

/**
 * CustomerRepository with basic CRUD and Lucene Search functionality.
 */
public interface CustomerRepository extends CrudRepository<CustomerEntity, Long>,
    CustomSearchRepository<CustomerEntity> {
}
