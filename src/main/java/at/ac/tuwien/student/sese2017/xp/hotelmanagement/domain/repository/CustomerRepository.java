package at.ac.tuwien.student.sese2017.xp.hotelmanagement.domain.repository;

import org.springframework.data.repository.CrudRepository;
import at.ac.tuwien.student.sese2017.xp.hotelmanagement.domain.data.CustomerEntity;

public interface CustomerRepository extends CrudRepository<CustomerEntity, Long> {

}
