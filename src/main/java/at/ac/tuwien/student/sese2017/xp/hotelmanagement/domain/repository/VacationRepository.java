package at.ac.tuwien.student.sese2017.xp.hotelmanagement.domain.repository;

import at.ac.tuwien.student.sese2017.xp.hotelmanagement.domain.data.VacationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VacationRepository extends JpaRepository<VacationEntity, Long> {
}
