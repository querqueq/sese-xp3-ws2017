package at.ac.tuwien.student.sese2017.xp.hotelmanagement.domain.data;

import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
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
  @GeneratedValue
  private Long id;

  @Column(nullable = false)
  @DateTimeFormat(iso = ISO.DATE)
  private LocalDate fromDate;

  @Column(nullable = false)
  @DateTimeFormat(iso = ISO.DATE)
  private LocalDate toDate;

  @Column(nullable = false)
  private Integer vacationDays;
  
  @ManyToOne(optional = false, fetch = FetchType.EAGER)
  private StaffEntity staffer;
  
  @ManyToOne
  @Nullable
  private StaffEntity manager;
  
  @Column(nullable = true)
  @Enumerated
  private VacationStatus resolution = VacationStatus.PENDING;
  
  @Column(nullable = true)
  private String reason;
}
