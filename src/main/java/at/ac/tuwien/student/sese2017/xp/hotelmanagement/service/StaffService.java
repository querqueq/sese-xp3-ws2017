package at.ac.tuwien.student.sese2017.xp.hotelmanagement.service;

import at.ac.tuwien.student.sese2017.xp.hotelmanagement.auth.AuthenticationFacade;
import at.ac.tuwien.student.sese2017.xp.hotelmanagement.domain.data.Role;
import at.ac.tuwien.student.sese2017.xp.hotelmanagement.domain.data.StaffEntity;
import at.ac.tuwien.student.sese2017.xp.hotelmanagement.domain.data.VacationEntity;
import at.ac.tuwien.student.sese2017.xp.hotelmanagement.domain.data.VacationStatus;
import at.ac.tuwien.student.sese2017.xp.hotelmanagement.domain.dto.StaffEmployment;
import at.ac.tuwien.student.sese2017.xp.hotelmanagement.domain.repository.StaffRepository;
import at.ac.tuwien.student.sese2017.xp.hotelmanagement.domain.repository.UserRepository;
import at.ac.tuwien.student.sese2017.xp.hotelmanagement.domain.repository.VacationRepository;
import at.ac.tuwien.student.sese2017.xp.hotelmanagement.exceptions.NotEnoughJohannesException;
import java.util.Optional;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Validated
@Service
public class StaffService {

  private final VacationRepository vacationRepository;
  private final UserRepository userRepository;
  private final StaffRepository staffRepository;
  private final AuthenticationFacade authFacade;

  @Autowired
  public StaffService(VacationRepository vacationRepository,
      UserRepository userRepository,
      StaffRepository staffRepository,
      AuthenticationFacade authFacade) {
    this.vacationRepository = vacationRepository;
    this.userRepository = userRepository;
    this.staffRepository = staffRepository;
    this.authFacade = authFacade;
  }

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
    Optional<VacationEntity> vacationOpt = vacationRepository.findById(vacationId);
    if(!vacationOpt.isPresent()) {
      throw new IllegalArgumentException("Vacation with id " + vacationId + " does not exist!");
    }
    VacationEntity vacation = vacationOpt.get();
    if(!vacation.getResolution().equals(VacationStatus.PENDING)) {
      throw new IllegalStateException("Cannot confirm a vacation that is already in state " + vacation.getResolution() + "!");
    }
    vacation.setManager(tryGetCurrentUserAsManager());
    vacation.setResolution(VacationStatus.ACCEPTED);
    vacationRepository.save(vacation);
  }

  public void rejectVacation(Long vacationId, String reason) {
    if(reason == null || reason.equals("")) {
      throw new IllegalArgumentException("Reason cannot be empty!");
    }
    Optional<VacationEntity> vacationOpt = vacationRepository.findById(vacationId);
    if(!vacationOpt.isPresent()) {
      throw new IllegalArgumentException("Vacation with id " + vacationId + " does not exist!");
    }
    VacationEntity vacation = vacationOpt.get();
    if(!vacation.getResolution().equals(VacationStatus.PENDING)) {
      throw new IllegalStateException("Cannot reject a vacation that is already in state " + vacation.getResolution() + "!");
    }
    vacation.setManager(tryGetCurrentUserAsManager());
    vacation.setResolution(VacationStatus.REJECTED);
    vacation.setReason(reason);
    vacationRepository.save(vacation);
  }

  private StaffEntity tryGetCurrentUserAsManager() {
    Authentication authentication = authFacade.getAuthentication();
    StaffEntity manager;
    if (!(authentication instanceof AnonymousAuthenticationToken)) {
      manager = Optional.ofNullable(
          userRepository.findByUsername(authentication.getName()))
          .map(us -> {
            if(us.isEmpty()) {
              return null;
            } else {
              return us.get(0).getId();
            }
          })
          .flatMap(u -> staffRepository.findById(u))
          .orElseThrow(() -> new IllegalStateException("Could not find manager by currently logged in user name!"));
    } else {
      throw new IllegalStateException("Anonymous users cannot confirm vacations!");
    }

    if(!manager.getRoles().contains(Role.MANAGER)) {
      throw new IllegalStateException("Current user is not a manager!");
    }
    return manager;
  }
}
