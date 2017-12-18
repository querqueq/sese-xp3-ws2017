package at.ac.tuwien.student.sese2017.xp.hotelmanagement.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import at.ac.tuwien.student.sese2017.xp.hotelmanagement.MapUtil;
import at.ac.tuwien.student.sese2017.xp.hotelmanagement.auth.PasswordManager;
import at.ac.tuwien.student.sese2017.xp.hotelmanagement.domain.data.JobTitle;
import at.ac.tuwien.student.sese2017.xp.hotelmanagement.domain.data.Sex;
import at.ac.tuwien.student.sese2017.xp.hotelmanagement.domain.data.StaffEntity;
import at.ac.tuwien.student.sese2017.xp.hotelmanagement.domain.data.VacationEntity;
import at.ac.tuwien.student.sese2017.xp.hotelmanagement.domain.repository.StaffRepository;
import at.ac.tuwien.student.sese2017.xp.hotelmanagement.domain.repository.VacationRepository;
import at.ac.tuwien.student.sese2017.xp.hotelmanagement.exceptions.NotEnoughVacationDaysException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

public class StaffServiceRequestVacationTest {
  @Rule
  public final ExpectedException exception = ExpectedException.none();
  
  private StaffService staffService;
  private StaffRepository staffRepository;
  private VacationRepository vacationRepository;
  private StaffEntity staff;

  @Before
  public void setup() {
    staffRepository = mock(StaffRepository.class);
    vacationRepository = mock(VacationRepository.class);
    staffService = new StaffService(vacationRepository,
        null,
        staffRepository,
        null,
        new PasswordManager());
    staff = (StaffEntity) new StaffEntity()
        .setBirthday(LocalDate.of(1990, 5, 16))
        .setEmail("new.guy@hotel.com")
        .setJobTitle(JobTitle.MAINTENANCE)
        .setName("Noobie")
        .setSex(Sex.MALE)
        .setYearlyVacationDays(MapUtil.getMap(Arrays.asList(2017), Arrays.asList(15)));
    when(staffRepository.findById(any())).thenReturn(Optional.empty());
    when(vacationRepository.save(any())).thenAnswer(new Answer<VacationEntity>() {
      @Override
      public VacationEntity answer(InvocationOnMock inv) throws Throwable {
        VacationEntity vacation = (VacationEntity) inv.getArgument(0);
        if(vacation.getId() == null) {
          vacation.setId(uniqueId());
        }
        return vacation;
      }
    });
  }

  @After
  public void teardown() {
    staff.getVacations().clear();
  }

  private void using(Object... entities) {
    Arrays.stream(entities).forEach(entity -> {
      if(entity.getClass().equals(VacationEntity.class)) {
        VacationEntity vacation = (VacationEntity) entity;
        vacation.getStaffer().getVacations().add(vacation);
        when(vacationRepository.findById(vacation.getId())).thenReturn(Optional.of(vacation));
      }
    });
  }

  private Long ids = 3000L;

  private Long requestVacation;
  private Long uniqueId() {
    return ids++;
  }

  /**
   * Tests if a valid vacation can be requested.
   * @throws NotEnoughVacationDaysException Should not be thrown since there are enough vacation days.
   */
  @Test
  public void testRequestValidVacation() throws NotEnoughVacationDaysException {
    Long id = uniqueId();
    VacationEntity vacation = getVacationEntity();
    when(vacationRepository.save(vacation)).thenAnswer(new Answer<VacationEntity>() {
      @Override
      public VacationEntity answer(InvocationOnMock invocation) throws Throwable {
        VacationEntity entity = (VacationEntity)invocation.getArgument(0);
        entity.setId(id);
        return entity;
      }
    });
    assertEquals(id, staffService.requestVacation(vacation));
  }

  @Test
  public void testRequestMultipleVacations() throws NotEnoughVacationDaysException {
    using(getVacationEntity().setId(uniqueId()));
    assertNotNull(staffService.requestVacation(getVacationEntity()
        .setVacationDays(10)
        .setFromDate(LocalDate.of(2017, 10, 7))
        .setToDate(LocalDate.of(2017, 12, 31))));
  }

  @Test(expected=NotEnoughVacationDaysException.class)
  public void testRequestMultipleVacationsNotEnoughFreeDays() throws NotEnoughVacationDaysException {
    using(getVacationEntity().setId(uniqueId()));
    staffService.requestVacation(getVacationEntity()
        .setVacationDays(11)
        .setFromDate(LocalDate.of(2017, 10, 7))
        .setToDate(LocalDate.of(2017, 12, 31)));
  }

  @Test
  public void testRequestMultipleVacationsWithDifferentYearlyDays()
      throws NotEnoughVacationDaysException {
    StaffEntity oldStaff = new StaffEntity()
        .setBirthday(LocalDate.of(1970, 5, 16))
        .setEmail("old.guy@hotel.com")
        .setJobTitle(JobTitle.RECEPTIONIST)
        .setName("Old guy")
        .setSex(Sex.MALE)
        .setYearlyVacationDays(MapUtil.getMap(Arrays.asList(2010, 2012, 2015), Arrays.asList(15, 17, 20)));
    VacationEntity vacation = getVacationEntity()
        .setStaffer(oldStaff)
        .setFromDate(LocalDate.of(2010, 10, 24))
        .setToDate(LocalDate.of(2010, 11, 10))
        .setVacationDays(14);
    assertNotNull(staffService.requestVacation(vacation));
    using(vacation);
    vacation = getVacationEntity()
        .setStaffer(oldStaff)
        .setFromDate(LocalDate.of(2011, 11, 24))
        .setToDate(LocalDate.of(2012, 02, 27))
        .setVacationDays(33);
    assertNotNull(staffService.requestVacation(vacation));
    using(vacation);
    vacation = getVacationEntity()
        .setStaffer(oldStaff)
        .setFromDate(LocalDate.of(2014, 11, 24))
        .setToDate(LocalDate.of(2015, 03, 27))
        .setVacationDays(55);
    exception.expect(NotEnoughVacationDaysException.class);
    staffService.requestVacation(vacation);
  }
  
  @Test
  public void requestOverlappingVacation() throws NotEnoughVacationDaysException {
    VacationEntity vacation = getVacationEntity();
    staffService.requestVacation(vacation);
    using(vacation);
    exception.expect(IllegalArgumentException.class);
    staffService.requestVacation(getVacationEntity());
  }

  /**
   * Tests if the to date can be before the from date.
   * @throws NotEnoughVacationDaysException Should not be thrown since there are enough vacation days.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testVacationRequestWithToDateBeforeFromDate() throws NotEnoughVacationDaysException {
    staffService.requestVacation(getVacationEntity()
        .setFromDate(LocalDate.of(2017, 10, 6))
        .setToDate(LocalDate.of(2017, 10, 2)));
  }

  /**
   * Tests if 0 is an acceptable value for vacation days.
   * @throws NotEnoughVacationDaysException Should not be thrown since there are enough vacation days.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testZeroVacationDays() throws NotEnoughVacationDaysException {
    VacationEntity vacationEntity = getVacationEntity()
        .setFromDate(LocalDate.of(2017, 10, 2))
        .setToDate(LocalDate.of(2017, 10, 2))
        .setVacationDays(0);
    staffService.requestVacation(vacationEntity);
  }

  /**
   * Tests if a negative value is an acceptable value for vacation days.
   * @throws NotEnoughVacationDaysException Should not be thrown since there are enough vacation days.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testNegativeVacationDays() throws NotEnoughVacationDaysException {
    VacationEntity vacationEntity = getVacationEntity()
        .setFromDate(LocalDate.of(2017, 10, 2))
        .setToDate(LocalDate.of(2017, 10, 2))
        .setVacationDays(-1);
    staffService.requestVacation(vacationEntity);
  }

  /**
   * Tests if a vacation request for a staffer where the yearly vacation days is unknown.
   * @throws NotEnoughVacationDaysException Should not be thrown since there are enough vacation days.
   */
  @Test(expected = IllegalStateException.class)
  public void testRequestVacationWithUnknownDays() throws NotEnoughVacationDaysException {
    VacationEntity vacationEntity = getVacationEntity()
        .setFromDate(LocalDate.of(2016, 2, 4))
        .setToDate(LocalDate.of(2016, 2, 7));
    staffService.requestVacation(vacationEntity);
  }

  /**
   * Tests if a vacation request can incorporate more vacation days than the given
   * from and to date span.
   * E.g. if the request contains the dates from: 2017-10-02 and to: 2017-10-06
   * then the number of vacation days cannot be greater than 5
   * @throws NotEnoughVacationDaysException Should not be thrown since there are enough vacation days.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testUnmatchingVacationDaysAndPeriod() throws NotEnoughVacationDaysException {
    VacationEntity vacationEntity = getVacationEntity()
        .setVacationDays(6);
    staffService.requestVacation(vacationEntity);
  }

  /**
   * Tests if a vacation request can incorporate more vacation days than the given
   * staffer has available.
   * @throws NotEnoughVacationDaysException Should be thrown because of too many vacation days
   */
  @Test(expected = NotEnoughVacationDaysException.class)
  public void testExceedVacationDaysLimit() throws NotEnoughVacationDaysException {
    VacationEntity vacationEntity = getVacationEntity()
        .setFromDate(LocalDate.of(2017, 10, 2))
        .setToDate(LocalDate.of(2017, 10, 29))
        .setVacationDays(21);
    staffService.requestVacation(vacationEntity);
  }

  private VacationEntity getVacationEntity() {
    Map<Integer, Integer> vacationDays = new HashMap<>();
    vacationDays.put(2017, 20);
    return new VacationEntity()
        .setVacationDays(5)
        .setFromDate(LocalDate.of(2017, 10, 2))
        .setToDate(LocalDate.of(2017, 10, 6))
        .setStaffer(staff);
  }
}
