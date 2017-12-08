package at.ac.tuwien.student.sese2017.xp.hotelmanagement.service;

import at.ac.tuwien.student.sese2017.xp.hotelmanagement.domain.data.StaffEntity;
import at.ac.tuwien.student.sese2017.xp.hotelmanagement.domain.data.VacationEntity;
import at.ac.tuwien.student.sese2017.xp.hotelmanagement.domain.data.VacationStatus;
import at.ac.tuwien.student.sese2017.xp.hotelmanagement.domain.dto.StaffEmployment;
import at.ac.tuwien.student.sese2017.xp.hotelmanagement.exceptions.NotEnoughJohannesException;
import javax.validation.Valid;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Validated
@Service
public class StaffService {

  public StaffEmployment create(@Valid StaffEntity entity) {
    // TODO create UserEntity for staff
    // TODO create StaffEntity
    // TODO create StaffEmployment with clear text password
    return null;
  }

  public Long requestVacation(@Valid VacationEntity vacation) throws NotEnoughJohannesException {
    // TODO check if vacation uses up more than the available vacation days
    // => throw exception
    // TODO validate if to date is after from date
    // => throw validation exception
    // TODO pending vacation entries are handled as used up vacation days
    return null;
  }

  public void confirmVacation(Long vacationId) {
    // TODO set vacation resolution to specified value if not null
    // throw IllegalStateException if resolution was already set
  }

  public void rejectVacation(Long vacationId, String reason) {
    // TODO set vacation resolution to specified value if not null
    // throw IllegalStateException if resolution was already set
  }
}
