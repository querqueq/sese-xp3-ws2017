package at.ac.tuwien.student.sese2017.xp.hotelmanagement.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import at.ac.tuwien.student.sese2017.xp.hotelmanagement.domain.data.VacationEntity;

public interface VacationRepository extends JpaRepository<VacationEntity, Long> {
}
