package at.ac.tuwien.student.sese2017.xp.hotelmanagement.domain.data;

import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.lang.Nullable;

@Data
@Entity
public class VacationEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @NotNull
  @DateTimeFormat(iso = ISO.DATE)
  private LocalDate from;

  @NotNull
  @DateTimeFormat(iso = ISO.DATE)
  private LocalDate to;

  private Integer vacationDays;
  
  @ManyToOne
  private StaffEntity staffer;
  
  @ManyToOne
  @Nullable
  private StaffEntity manager;
  
  @Column
  @Enumerated
  private VacationStatus resolution = VacationStatus.PENDING;
  
  @Column
  @Nullable
  private String reason;
}
