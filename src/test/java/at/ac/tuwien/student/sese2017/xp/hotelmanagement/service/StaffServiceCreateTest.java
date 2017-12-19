package at.ac.tuwien.student.sese2017.xp.hotelmanagement.service;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.isEmptyOrNullString;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import at.ac.tuwien.student.sese2017.xp.hotelmanagement.MapUtil;
import at.ac.tuwien.student.sese2017.xp.hotelmanagement.auth.PasswordManager;
import at.ac.tuwien.student.sese2017.xp.hotelmanagement.auth.SettableAuthenticationFacade;
import at.ac.tuwien.student.sese2017.xp.hotelmanagement.domain.data.JobTitle;
import at.ac.tuwien.student.sese2017.xp.hotelmanagement.domain.data.Role;
import at.ac.tuwien.student.sese2017.xp.hotelmanagement.domain.data.Sex;
import at.ac.tuwien.student.sese2017.xp.hotelmanagement.domain.data.StaffEntity;
import at.ac.tuwien.student.sese2017.xp.hotelmanagement.domain.data.UserEntity;
import at.ac.tuwien.student.sese2017.xp.hotelmanagement.domain.dto.StaffEmployment;
import at.ac.tuwien.student.sese2017.xp.hotelmanagement.domain.repository.StaffRepository;
import at.ac.tuwien.student.sese2017.xp.hotelmanagement.domain.repository.UserRepository;
import at.ac.tuwien.student.sese2017.xp.hotelmanagement.exceptions.ForbiddenException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

public class StaffServiceCreateTest {

  @Rule
  public final ExpectedException exception = ExpectedException.none();
 
  private StaffService staffService;
  private UserRepository userRepository;
  private StaffRepository staffRepository;
  private SettableAuthenticationFacade authenticationFacade = new SettableAuthenticationFacade();
  private StaffEntity nonManager;
  private StaffEntity manager;
  private StaffEntity newStaff;
  
  @Before
  public void setup() {
    authenticationFacade.setAuthentication(null);
    userRepository = mock(UserRepository.class);
    staffRepository = mock(StaffRepository.class);
    staffService = new StaffService(null, userRepository, staffRepository, authenticationFacade,
        new PasswordManager());
    newStaff = (StaffEntity) new StaffEntity()
        .setBirthday(LocalDate.of(1990, 5, 16))
        .setEmail("new.guy@hotel.com")
        .setJobTitle(JobTitle.MAINTENANCE)
        .setName("Noobie")
        .setSex(Sex.MALE)
        .setYearlyVacationDays(MapUtil.getMap(Arrays.asList(2017), Arrays.asList(15)));
    nonManager = (StaffEntity) new StaffEntity()
        .setBirthday(LocalDate.of(1990, 5, 16))
        .setEmail("n.flynn@hotel.com")
        .setJobTitle(JobTitle.CLEANER)
        .setName("Neil Flynn")
        .setSex(Sex.MALE)
        .setYearlyVacationDays(MapUtil.getMap(Arrays.asList(2010), Arrays.asList(22)))
        .setRoles(JobTitle.CLEANER.getRoles())
        .setUsername("janitor")
        .setId(1L);
    manager = (StaffEntity) new StaffEntity()
        .setBirthday(LocalDate.of(1990, 5, 10))
        .setEmail("manager@hotel.com")
        .setJobTitle(JobTitle.MANAGER)
        .setName("Michael Scott")
        .setSex(Sex.MALE)
        .setYearlyVacationDays(MapUtil.getMap(Arrays.asList(2015), Arrays.asList(30)))
        .setRoles(JobTitle.MANAGER.getRoles())
        .setUsername("manager")
        .setId(2L);
    when(userRepository.findByUsername(any())).thenReturn(Collections.emptyList());
    when(staffRepository.findById(any())).thenReturn(Optional.empty());
  }
  
  private Long ids = 2000L;
  private Long uniqueId() {
    return ids++;
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
      }
    });
  }
  
  @Test
  public void testCreateStaffer() {
    using(manager);
    setRequestingUser(manager);
    List<Role> newStaffsRoles = newStaff.getJobTitle().getRoles();
    Long newStaffId = uniqueId();
    when(staffRepository.save(newStaff)).thenAnswer(new Answer<StaffEntity>() {
      @Override
      public StaffEntity answer(InvocationOnMock inv) throws Throwable {
        StaffEntity staffer = (StaffEntity) inv.getArgument(0);
        staffer.setId(newStaffId);
        return staffer;
      }      
    });
    when(staffRepository.findById(newStaffId)).thenReturn(Optional.of(newStaff));
    StaffEmployment staffEmployment = staffService.create(newStaff);
    verify(staffRepository, times(1)).save(any());
    assertThat(staffEmployment.getClearTextPassword(), not(isEmptyOrNullString()));
    assertThat(newStaff.getPassword(), not(isEmptyOrNullString()));
    assertThat(newStaff.getPassword(), not(is(staffEmployment.getClearTextPassword())));
    assertThat(newStaff.getRoles(), is(newStaffsRoles));
    assertTrue(newStaff.getYearlyVacationDays().size() > 0);
    assertThat(newStaff.getUsername(), is(newStaff.getEmail()));
  }
  
  @Test
  public void testCreateStafferAsNonManager() {
    using(nonManager);
    setRequestingUser(nonManager);
    exception.expect(ForbiddenException.class);
    staffService.create(newStaff);
  }
  
  @Test
  public void testCreateNullStaffer() {
    using(manager);
    setRequestingUser(manager);
    exception.expect(IllegalArgumentException.class);
    staffService.create(null);
  }
  
  @Test
  public void testCreateStafferUsingInvalidUsername() {
    setRequestingUser(manager);
    exception.expect(IllegalStateException.class);
    staffService.create(newStaff);
  }
}
