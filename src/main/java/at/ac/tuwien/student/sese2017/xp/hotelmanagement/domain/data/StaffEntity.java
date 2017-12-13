package at.ac.tuwien.student.sese2017.xp.hotelmanagement.domain.data;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.search.annotations.Field;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
public class StaffEntity extends UserEntity {  
  
  @Column
  @Field
  @NotNull
  private String name;

  @Column
  @NotNull
  @DateTimeFormat(iso = ISO.DATE)
  private LocalDate birthday;
  
  @Column
  @NotNull
  private Sex sex;
  
  @Column(unique = true)
  @Email
  @NotNull
  private String email;
  
  @Column
  @Enumerated
  private JobTitle jobTitle;

  /**
   * Maps how many vacation days per year an employee has
   * The map key describes the year since the employee gets
   * the value number of vacation days
   * There are only entries if the vacation days change
   * so a map with entries [2017=20, 2020=22] would mean that
   * from 2017 to 2019 the employee would have 20 vacation days
   * per year and starting from 2020 they would have 22 days
   */
  @Column
  @ElementCollection
  private Map<Integer, Integer> yearlyVacationDays = new HashMap<>();
  
  @OneToMany(mappedBy="staffer", targetEntity = VacationEntity.class
      , fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  private List<VacationEntity> vacations = new ArrayList<>();
}
