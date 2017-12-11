package at.ac.tuwien.student.sese2017.xp.hotelmanagement.service;

import static org.junit.Assert.assertNotNull;
import at.ac.tuwien.student.sese2017.xp.hotelmanagement.HotelManagementApplicationTests;
import at.ac.tuwien.student.sese2017.xp.hotelmanagement.domain.data.VacationEntity;
import at.ac.tuwien.student.sese2017.xp.hotelmanagement.domain.test.TestDataInjector;
import at.ac.tuwien.student.sese2017.xp.hotelmanagement.exceptions.NotEnoughVacationDaysException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import javax.transaction.Transactional;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

@Transactional
public class StaffServiceTest extends HotelManagementApplicationTests {

  @Autowired
  private StaffService staffService;

  /**
   * Tests if a valid vacation can be requested.
   * @throws NotEnoughVacationDaysException Should not be thrown since there are enough vacation days.
   */
  @Test
  public void testRequestValidVacation() throws NotEnoughVacationDaysException {
    assertNotNull(staffService.requestVacation(getVacationEntity()));
  }

  /**
   * Tests if the to date can be before the from date.
   * @throws NotEnoughVacationDaysException Should not be thrown since there are enough vacation days.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testVacationRequestWithToDateBeforeFromDate() throws NotEnoughVacationDaysException {
    VacationEntity vacationEntity = getVacationEntity()
        .setFrom(LocalDate.of(2017, 10, 6))
        .setTo(LocalDate.of(2017, 10, 2));
    staffService.requestVacation(vacationEntity);
  }

  /**
   * Tests if 0 is an acceptable value for vacation days.
   * @throws NotEnoughVacationDaysException Should not be thrown since there are enough vacation days.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testZeroVacationDays() throws NotEnoughVacationDaysException {
    VacationEntity vacationEntity = getVacationEntity()
        .setFrom(LocalDate.of(2017, 10, 2))
        .setTo(LocalDate.of(2017, 10, 2))
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
        .setFrom(LocalDate.of(2017, 10, 2))
        .setTo(LocalDate.of(2017, 10, 2))
        .setVacationDays(-1);
    staffService.requestVacation(vacationEntity);
  }

  /**
   * Tests if a vacation request for a staffer where the yearly vacation days is unknown.
   * @throws NotEnoughVacationDaysException Should not be thrown since there are enough vacation days.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testRequestVacationWithUnknownDays() throws NotEnoughVacationDaysException {
    VacationEntity vacationEntity = getVacationEntity()
        .setFrom(LocalDate.of(2016, 2, 4))
        .setTo(LocalDate.of(2016, 2, 7));
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
  @Test(expected = IllegalArgumentException.class)
  public void testExceedVacationDaysLimit() throws NotEnoughVacationDaysException {
    VacationEntity vacationEntity = getVacationEntity()
        .setFrom(LocalDate.of(2017, 10, 2))
        .setTo(LocalDate.of(2017, 10, 29))
        .setVacationDays(21);
    staffService.requestVacation(vacationEntity);
  }

  private VacationEntity getVacationEntity() {
    Map<Integer, Integer> vacationDays = new HashMap<>();
    vacationDays.put(2017, 20);
    return new VacationEntity()
        .setVacationDays(5)
        .setFrom(LocalDate.of(2017, 10, 2))
        .setTo(LocalDate.of(2017, 10, 6))
        .setStaffer(TestDataInjector.STAFF_1);
  }
}
