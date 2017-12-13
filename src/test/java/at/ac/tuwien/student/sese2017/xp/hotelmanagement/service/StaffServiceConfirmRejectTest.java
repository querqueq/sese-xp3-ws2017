package at.ac.tuwien.student.sese2017.xp.hotelmanagement.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import at.ac.tuwien.student.sese2017.xp.hotelmanagement.HotelManagementApplicationTests;
import at.ac.tuwien.student.sese2017.xp.hotelmanagement.auth.AuthenticationFacade;
import at.ac.tuwien.student.sese2017.xp.hotelmanagement.auth.SettableAuthenticationFacade;
import at.ac.tuwien.student.sese2017.xp.hotelmanagement.domain.data.JobTitle;
import at.ac.tuwien.student.sese2017.xp.hotelmanagement.domain.data.Sex;
import at.ac.tuwien.student.sese2017.xp.hotelmanagement.domain.data.StaffEntity;
import at.ac.tuwien.student.sese2017.xp.hotelmanagement.domain.data.UserEntity;
import at.ac.tuwien.student.sese2017.xp.hotelmanagement.domain.data.VacationEntity;
import at.ac.tuwien.student.sese2017.xp.hotelmanagement.domain.data.VacationStatus;
import at.ac.tuwien.student.sese2017.xp.hotelmanagement.domain.repository.StaffRepository;
import at.ac.tuwien.student.sese2017.xp.hotelmanagement.domain.repository.UserRepository;
import at.ac.tuwien.student.sese2017.xp.hotelmanagement.domain.repository.VacationRepository;
import at.ac.tuwien.student.sese2017.xp.hotelmanagement.domain.test.TestDataInjector;
import at.ac.tuwien.student.sese2017.xp.hotelmanagement.exceptions.ForbiddenException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.transaction.Transactional;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import static org.mockito.Mockito.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.TestingAuthenticationProvider;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

public class StaffServiceConfirmRejectTest {

  @Rule
  public final ExpectedException exception = ExpectedException.none();
 
  private StaffService staffService;
  private VacationRepository vacationRepository;
  private UserRepository userRepository;
  private StaffRepository staffRepository;
  private SettableAuthenticationFacade authenticationFacade = new SettableAuthenticationFacade();
  private StaffEntity requester;
  private VacationEntity pendingVacation;  
  private StaffEntity manager;
  private VacationEntity acceptedVacation;
  
  @Before
  public void setup() {
    authenticationFacade.setAuthentication(null);
    vacationRepository = mock(VacationRepository.class);
    userRepository = mock(UserRepository.class);
    staffRepository = mock(StaffRepository.class);
    staffService = new StaffService(vacationRepository, userRepository, staffRepository, authenticationFacade);
    requester = (StaffEntity) new StaffEntity()
        .setBirthday(LocalDate.of(1990, 5, 16))
        .setEmail("n.flynn@hotel.com")
        .setJobTitle(JobTitle.CLEANER)
        .setName("Neil Flynn")
        .setSex(Sex.MALE)
        .setYearlyVacationDays(new HashMap<Integer, Integer>() {{put(2010, 22);}})
        .setRoles(JobTitle.CLEANER.getRoles())
        .setUsername("janitor")
        .setId(1L);
    manager = (StaffEntity) new StaffEntity()
        .setBirthday(LocalDate.of(1990, 5, 10))
        .setEmail("manager@hotel.com")
        .setJobTitle(JobTitle.MANAGER)
        .setName("Michael Scott")
        .setSex(Sex.MALE)
        .setYearlyVacationDays(new HashMap<Integer, Integer>() {{put(2015, 30);}})
        .setRoles(JobTitle.MANAGER.getRoles())
        .setUsername("manager")
        .setId(2L);
    pendingVacation = new VacationEntity()
        .setFromDate(LocalDate.of(2020, 2, 10))
        .setToDate(LocalDate.of(2020, 2, 20))
        .setResolution(VacationStatus.PENDING)
        .setStaffer(requester)
        .setVacationDays(7)
        .setId(42L);
    acceptedVacation = new VacationEntity()
        .setManager(manager)
        .setFromDate(LocalDate.of(2019, 2, 10))
        .setToDate(LocalDate.of(2019, 2, 20))
        .setResolution(VacationStatus.ACCEPTED)
        .setStaffer(requester)
        .setVacationDays(7)
        .setId(100L);
    when(userRepository.findByUsername(any())).thenReturn(Collections.emptyList());
  }
  
  private void setRequestingUser(UserEntity userEntity) {
    List<GrantedAuthority> authorities = userEntity.getRoles().stream()
        .map(Enum::name)
        .map(SimpleGrantedAuthority::new)
        .collect(Collectors.toList());
    User user = new User(userEntity.getUsername(), "password", authorities);    
    authenticationFacade.setAuthentication(new TestingAuthenticationToken(user, null, authorities));
  }
  
  private void using(Object... entities) {
    Arrays.stream(entities).forEach(entity -> {
      if(entity.getClass().equals(StaffEntity.class)) {
        StaffEntity staffer = (StaffEntity) entity;
        when(staffRepository.findById(staffer.getId())).thenReturn(Optional.of(staffer));
        when(userRepository.findByUsername(staffer.getUsername())).thenReturn(Arrays.asList(staffer));
      } else if(entity.getClass().equals(VacationEntity.class)) {
        VacationEntity vac = (VacationEntity) entity;
        when(vacationRepository.findById(vac.getId())).thenReturn(Optional.of(vac));
      }
    });
  }

  /**
   * Test if a manager can confirm a legitimate vacation request.
   */
  @Test
  public void testConfirmVacation() {
    using(manager, requester, pendingVacation);
    setRequestingUser(manager);
    staffService.confirmVacation(pendingVacation.getId());
    assertEquals(VacationStatus.ACCEPTED, pendingVacation.getResolution());
    assertEquals(pendingVacation.getManager().getId(), manager.getId());
    verify(vacationRepository, atLeast(1)).findById(pendingVacation.getId());
  }
  
  /**
   * Test if a manager can reject a legitimate vacation request.
   */
  @Test
  public void testRejectVacation() {
    using(manager, requester, pendingVacation);
    final String reason = "I don't like you!";
    setRequestingUser(manager);
    staffService.rejectVacation(pendingVacation.getId(), reason);
    assertEquals(VacationStatus.REJECTED, pendingVacation.getResolution());
    assertEquals(reason, pendingVacation.getReason());
    assertEquals(pendingVacation.getManager().getId(), manager.getId());
    verify(vacationRepository, atLeast(1)).findById(pendingVacation.getId());
  }
  
  @Test
  public void testConfirmWithNullManager() {
    using(requester, pendingVacation);
    //Reason for failure: missing null check in StaffService:94
    //if (!(authentication instanceof AnonymousAuthenticationToken)) {
    exception.expect(IllegalStateException.class);
    staffService.confirmVacation(pendingVacation.getId());
  }
  
  @Test
  public void testRejectWithNullManager() {
    using(requester, pendingVacation);
    exception.expect(IllegalStateException.class);
    staffService.rejectVacation(pendingVacation.getId(), "I don't like you!");
  }
  
  @Test
  public void testConfirmWithNonExistantManager() {
    using(requester, pendingVacation);
    setRequestingUser(manager);
    exception.expect(IllegalStateException.class);
    staffService.confirmVacation(pendingVacation.getId());
  }
  
  @Test
  public void testRejectWithNonExistantManager() {
    using(requester, pendingVacation);
    setRequestingUser(manager);
    exception.expect(IllegalStateException.class);
    staffService.rejectVacation(pendingVacation.getId(), "I don't like you!");
  }
  
  @Test
  public void testConfirmAsNonManager() {
    using(requester, pendingVacation);
    setRequestingUser(requester);
    exception.expect(ForbiddenException.class); 
    staffService.confirmVacation(pendingVacation.getId());
  }
  
  @Test
  public void testRejectAsNonManager() {
    using(requester, pendingVacation);
    setRequestingUser(requester);
    exception.expect(ForbiddenException.class); 
    staffService.rejectVacation(pendingVacation.getId(), "I don't like you!");
  }
  
  @Test
  public void testConfirmNull() {
    using(manager);
    setRequestingUser(manager);
    exception.expect(IllegalArgumentException.class); 
    staffService.confirmVacation(null);
  }
  
  @Test
  public void testConfirmNonExistant() {
    using(manager, requester);
    setRequestingUser(manager);
    exception.expect(IllegalArgumentException.class); 
    staffService.confirmVacation(pendingVacation.getId());
  }
  
  @Test
  public void testRejectNull() {
    using(manager);
    setRequestingUser(manager);
    exception.expect(IllegalArgumentException.class); 
    staffService.rejectVacation(null, "Wahoo");
  }
  
  @Test
  public void testRejectNonExistant() {
    using(manager, requester);
    setRequestingUser(manager);
    exception.expect(IllegalArgumentException.class); 
    staffService.rejectVacation(pendingVacation.getId(), "rejected"); 
  }
  
  @Test
  public void testRejectWithNullReason() {
    using(manager, requester, pendingVacation);
    setRequestingUser(manager);
    exception.expect(IllegalArgumentException.class);
    staffService.rejectVacation(pendingVacation.getId(), null);
  }
  
  @Test
  public void testRejectWithEmptyReason() {
    using(manager, requester, pendingVacation);
    setRequestingUser(manager);
    exception.expect(IllegalArgumentException.class);
    staffService.rejectVacation(pendingVacation.getId(), "");
  }
  
  @Test
  public void testRejectResolvedVacation() {
    using(manager, requester, acceptedVacation);
    setRequestingUser(manager);
    exception.expect(IllegalStateException.class);
    staffService.rejectVacation(acceptedVacation.getId(), "some reason");
  }
  
  @Test
  public void testConfirmResolvedVacation() {
    using(manager, requester, acceptedVacation);
    setRequestingUser(manager);
    exception.expect(IllegalStateException.class);
    staffService.confirmVacation(acceptedVacation.getId());    
  }
}
