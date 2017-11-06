package at.ac.tuwien.student.sese2017.xp.hotelmanagement.domain.repository;

import at.ac.tuwien.student.sese2017.xp.hotelmanagement.domain.data.ExampleEntity;
import java.util.List;
import org.springframework.data.repository.CrudRepository;

/**
 * Simple Crud Repository. Is automatically implemented by
 * Spring and available via "@Autowired ExampleRepository exampleRepository;"
 *
 * <p>Provides all necessary functionality for basic crud operations and can be
 * extended with custom queries like the example below.</p>
 */
public interface ExampleRepository extends CrudRepository<ExampleEntity, Long> {
  List<ExampleEntity> findBySecondFieldOrderByDateFieldDesc(String secondField);
}
