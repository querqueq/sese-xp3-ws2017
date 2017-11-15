package at.ac.tuwien.student.sese2017.xp.hotelmanagement.domain.data;

import org.hibernate.annotations.ColumnDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import java.math.BigDecimal;
import java.net.URL;
import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import lombok.Data;

/**
 * This is a representation of the customer used by CustomerService and the CustomerController as DTO
 *
 * @author Michael
 * @author Johannes
 */
@Data
@Entity
public class CustomerEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @Column
  @NotNull
  private String name;

  // specification does not include minimum age validation
  // TODO validate; birthday can only be in the past
  @Column
  @NotNull
  @DateTimeFormat(iso = ISO.DATE)
  private LocalDate birthday;

  @Column
  @NotNull
  private Sex sex;

  @Column
  @NotNull
  private String billingAddress;

  @Column
  private String companyName;

  @Column
  @Lob
  private String note;

  @Column @NotNull
  @ColumnDefault("0")
  @Digits(fraction = 2, integer = 3)
  @Min(value = 0) @Max(value = 100)
  private BigDecimal discount;

  @Column
  @NotNull
  @Digits(fraction = 0, integer = 50)
  private String phoneNumber;

  @Column
  @Email
  @NotNull
  private String email;

  @Column
  private URL webAddress;

  @Column
  private String faxNumber;
}
