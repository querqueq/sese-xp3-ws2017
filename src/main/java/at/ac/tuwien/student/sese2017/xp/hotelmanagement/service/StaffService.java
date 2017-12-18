package at.ac.tuwien.student.sese2017.xp.hotelmanagement.service;

import at.ac.tuwien.student.sese2017.xp.hotelmanagement.auth.AuthenticationFacade;
import at.ac.tuwien.student.sese2017.xp.hotelmanagement.auth.PasswordManager;
import at.ac.tuwien.student.sese2017.xp.hotelmanagement.auth.UserWithId;
import at.ac.tuwien.student.sese2017.xp.hotelmanagement.domain.data.Role;
import at.ac.tuwien.student.sese2017.xp.hotelmanagement.domain.data.StaffEntity;
import at.ac.tuwien.student.sese2017.xp.hotelmanagement.domain.data.UserEntity;
import at.ac.tuwien.student.sese2017.xp.hotelmanagement.domain.data.VacationEntity;
import at.ac.tuwien.student.sese2017.xp.hotelmanagement.domain.data.VacationStatus;
import at.ac.tuwien.student.sese2017.xp.hotelmanagement.domain.dto.StaffEmployment;
import at.ac.tuwien.student.sese2017.xp.hotelmanagement.domain.repository.StaffRepository;
import at.ac.tuwien.student.sese2017.xp.hotelmanagement.domain.repository.UserRepository;
import at.ac.tuwien.student.sese2017.xp.hotelmanagement.domain.repository.VacationRepository;
import at.ac.tuwien.student.sese2017.xp.hotelmanagement.exceptions.ForbiddenException;
import at.ac.tuwien.student.sese2017.xp.hotelmanagement.exceptions.NotEnoughVacationDaysException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.AbstractMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NavigableMap;
import java.util.Optional;
import java.util.TreeMap;
import java.util.stream.Collectors;
import javax.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

/**
 * Service class for all staff related functions carried out by staff.
 * Calling staffer is injected via security context.
 * 
 * @author johannes, michael
 */
@Validated
@Service
@Slf4j
public class StaffService {

  /**
   * Initial value for yearly vacation days for new staffers.
   */
  private static final int DEFAULT_YEARLY_VACATION_DAYS = 20;
  private final VacationRepository vacationRepository;
  private final UserRepository userRepository;
  private final StaffRepository staffRepository;
  private final AuthenticationFacade authFacade;
  private PasswordManager passwordManager;

  /**
   * Constructs a StaffService.
   * @param vacationRepository vacation repository
   * @param userRepository user repository
   * @param staffRepository staff repository
   * @param authFacade authentication facade
   * @param passwordManager password manager
   */
  @Autowired
  public StaffService(VacationRepository vacationRepository,
      UserRepository userRepository,
      StaffRepository staffRepository,
      AuthenticationFacade authFacade,
      PasswordManager passwordManager) {
    this.vacationRepository = vacationRepository;
    this.userRepository = userRepository;
    this.staffRepository = staffRepository;
    this.authFacade = authFacade;
    this.passwordManager = passwordManager;
  }

  /**
   * Creates a new staffer.
   * Only managers can do this.
   * 
   * @param entity The StaffEntity to create
   * @return An object containing the ID and clear text password of the staff.
   */
  public StaffEmployment create(@Valid StaffEntity entity) {
    if (entity == null) {
      throw new IllegalArgumentException("StaffEntity cannot be null!");
    }

    //Get current user
    List<UserEntity> manager = userRepository
        .findByUsername(authFacade.getAuthentication().getName());
    if (manager.size() != 1) {
      throw new IllegalStateException("Did not find exactly 1 manager with username "
          + authFacade.getAuthentication().getName() + "!");
    }

    UserEntity managerEntity = manager.get(0);
    //Check if user is manager
    if (!managerEntity.getRoles().contains(Role.MANAGER)) {
      throw new ForbiddenException(Role.MANAGER, managerEntity.getId(), "creating staffer");
    }
    StaffEmployment employment = new StaffEmployment();
    String clearTextPassword = passwordManager.generatePassword();

    //Return clear text password to caller so it can be displayed once
    employment.setClearTextPassword(clearTextPassword);
    entity.setUsername(entity.getEmail());

    //salt and hash password
    entity.setPassword(passwordManager.encodePassword(clearTextPassword));
    entity.setRoles(entity.getJobTitle().getRoles());

    //set default yearly vacation if not set by caller
    if (entity.getYearlyVacationDays() == null || entity.getYearlyVacationDays().isEmpty()) {
      //FIXME make default yearly vacation days configurable if customer wants them to be
      Map<Integer, Integer> yearlyVacationDays = new HashMap<>();
      yearlyVacationDays.put(LocalDate.now().getYear(), DEFAULT_YEARLY_VACATION_DAYS);
      entity.setYearlyVacationDays(yearlyVacationDays);
    }
    Long id = staffRepository.save(entity).getId();
    employment.setId(id);
    return employment;
  }

  /**
   * Gets all pending vacation requests and all running and future vacation requests.
   * @return pending, running and future vacation requests
   */
  public List<VacationEntity> getCurrentVactionRequests() {
    final LocalDate now = LocalDate.now();
    return vacationRepository.findAll(Sort.by("fromDate")).stream()
        .filter(vac -> vac.getResolution().equals(VacationStatus.PENDING)
            || vac.getToDate().isAfter(now))
        .collect(Collectors.toList());
  }

  /**
   * Requests a vacation for the staffer inside of given vacation. 
   * 
   * @param vacation vacation to be requested
   * @return id of vacation
   * @throws IllegalArgumentException if a sanity check for vacation fails
   * @throws IllegalStateException if a prerequisite for vacation request is missing
   * @throws NotEnoughVacationDaysException if there are not enough vacation days left for given vacation
   * @throws ForbiddenException if this method is called by a non-manager
   */
  public Long requestVacation(@Valid VacationEntity vacation)
      throws NotEnoughVacationDaysException {
    StaffEntity requester = vacation.getStaffer();

    if (vacation.getResolution() == null) {
      vacation.setResolution(VacationStatus.PENDING);
    } else if(!vacation.getResolution().equals(VacationStatus.PENDING)) {
      throw new IllegalArgumentException("Vacation request already resolved");
    }

    if (requester == null) {
      throw new IllegalArgumentException("Vacation not requested by anyone");
    }

    if (vacation.getVacationDays() < 1) {
      throw new IllegalArgumentException("Urlaubsanstrag mit " + vacation.getVacationDays() + " Tagen unzulässig!");
    }

    //Calculate available vacation days up until and including this year
    //Future reductions in vacation days could lead to illegal vacations 
    Integer targetYear = vacation.getToDate().getYear();
    //Sort yearlyVacationDays by year
    NavigableMap<Integer, Integer> yearlyVacationDays = new TreeMap<>(requester.getYearlyVacationDays());

    if (yearlyVacationDays.isEmpty()) {
      throw new IllegalStateException("Staffer " + requester.getId() + " does not have any vacation days set");
    } else if (yearlyVacationDays.firstKey() > targetYear) {
      throw new IllegalStateException("Keine verfügbaren Urlaubstage für "
          + yearlyVacationDays.firstKey());
    }

    if (vacation.getToDate().isBefore(vacation.getFromDate())) {
      throw new IllegalArgumentException("Das Urlaubsende muss nach dem Urlaubsanfang sein!");
    }

    int maxDays = (int)ChronoUnit.DAYS.between(vacation.getFromDate(), vacation.getToDate()) + 1;
    if (vacation.getVacationDays() > maxDays) {
      throw new IllegalArgumentException("Zu viele Urlaubstage beantragt, es stehen "
          + maxDays + " Tage zur Verfügung!");
    }

    Boolean isOverlapping = requester.getVacations().stream()
        //Only check overlaps for pending and accepted vacations
        .filter(vac -> !VacationStatus.REJECTED.equals(vac.getResolution()))
        .map(vac -> !vac.getFromDate().isAfter(vacation.getToDate())
            && !vac.getToDate().isBefore(vacation.getFromDate()))    
        .reduce(false, (l,r) -> l || r);
    if (isOverlapping) {
      throw new IllegalArgumentException("Urlaubsüberlappung");
    }


    log.info("Staffer {} request {} days of vacation from {} till {}",
        requester.getId(), vacation.getVacationDays(), vacation.getFromDate(),
        vacation.getToDate());

    //Calculate total days of accepted and pending vacation requests 
    Integer totalUsedVacationDays = requester.getVacations()
        .stream().filter(v -> v.getResolution().equals(VacationStatus.ACCEPTED) 
            || v.getResolution().equals(VacationStatus.PENDING))
        .reduce(0, (totalDays, v) -> totalDays + v.getVacationDays(), (l, r) -> l + r);

    //Calculate total number of vacation days possible up until and including the target year
    Integer totalPossibleVacationDays = yearlyVacationDays.descendingMap().entrySet().stream()
        .filter(year -> year.getKey() < targetYear)
        .reduce(new AbstractMap.SimpleEntry<Integer,Integer>(targetYear, 0),
            (prevYear, currentYear) -> new AbstractMap.SimpleEntry<Integer,Integer>(currentYear.getKey(),
                prevYear.getValue() + currentYear.getValue() * (prevYear.getKey()
                    - currentYear.getKey())
                )
            ).getValue();
    Integer sameYearDays = yearlyVacationDays.get(targetYear);
    if (sameYearDays != null) {
      totalPossibleVacationDays += sameYearDays;
    }

    Integer leftVacationDays = totalPossibleVacationDays - totalUsedVacationDays;

    //Check if vacation uses up more than the available vacation days
    if (leftVacationDays - vacation.getVacationDays() < 0) {
      throw new NotEnoughVacationDaysException(requester.getId(), leftVacationDays, targetYear);
    }

    vacationRepository.save(vacation);

    return vacation.getId();
  }

  /**
   * Confirm a pending vacation.
   * Only managers can do this.
   * 
   * @throws IllegalArgumentException if vacation does not exist
   * @throws IllegalStateException if vacation is not pending
   * @throws ForbiddenException if this method is called by a non-manager
   * @param vacationId id of vacation to be confirmed
   */
  public void confirmVacation(Long vacationId) {
    Optional<VacationEntity> vacationOpt = vacationRepository.findById(vacationId);
    if(!vacationOpt.isPresent()) {
      throw new IllegalArgumentException("Urlaub mit ID " + vacationId + " existiert nicht!");
    }
    VacationEntity vacation = vacationOpt.get();
    if(!vacation.getResolution().equals(VacationStatus.PENDING)) {
      throw new IllegalStateException("Cannot confirm a vacation that is already in state "
          + vacation.getResolution() + "!");
    }
    vacation.setManager(tryGetCurrentUserAsManager());
    vacation.setResolution(VacationStatus.ACCEPTED);
    vacationRepository.save(vacation);
  }

  /**
   * Reject a pending vacation.
   * Only managers can do this.
   * 
   * @throws IllegalArgumentException if vacation does not exist or the reason is empty
   * @throws IllegalStateException if vacation is not pending
   * @throws ForbiddenException if this method is called by a non-manager
   * @param vacationId id of vacation to be confirmed
   * @param reason reason for rejecting vacation request
   */
  public void rejectVacation(Long vacationId, String reason) {
    if (reason == null || reason.equals("")) {
      throw new IllegalArgumentException("Der Grund darf nicht leer sein!");
    }
    Optional<VacationEntity> vacationOpt = vacationRepository.findById(vacationId);
    if (!vacationOpt.isPresent()) {
      throw new IllegalArgumentException("Urlaub mit ID " + vacationId + " existiert nicht!");
    }
    VacationEntity vacation = vacationOpt.get();
    if (!vacation.getResolution().equals(VacationStatus.PENDING)) {
      throw new IllegalStateException("Cannot reject a vacation that is already in state "
          + vacation.getResolution() + "!");
    }
    vacation.setManager(tryGetCurrentUserAsManager());
    vacation.setResolution(VacationStatus.REJECTED);
    vacation.setReason(reason);
    vacationRepository.save(vacation);
  }

  /**
   * Get a staffer by it's id.
   * @param id staffers id
   * @return staffer for id
   */
  public Optional<StaffEntity> findById(Long id) {
    return staffRepository.findById(id);
  }

  private StaffEntity tryGetCurrentUserAsManager() {
    Authentication authentication = authFacade.getAuthentication();
    StaffEntity manager;
    if (!(authentication instanceof AnonymousAuthenticationToken) && authentication != null) {
      Long userId = ((UserWithId)authentication.getPrincipal()).getId();
      manager = staffRepository.findById(userId)
          .orElseThrow(() ->
          new IllegalStateException("User mit ID " + userId + " konnte nicht gefunden werden!"));
    } else {
      throw new IllegalStateException("Anonyme User können keine Urlaube bestätigen/ablehnen!");
    }

    if(!manager.getRoles().contains(Role.MANAGER)) {
      throw new ForbiddenException(Role.MANAGER, manager.getId(), "staff changes");
    }
    return manager;
  }
}
