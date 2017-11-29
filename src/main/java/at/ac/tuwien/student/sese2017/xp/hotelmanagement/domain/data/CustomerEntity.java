package at.ac.tuwien.student.sese2017.xp.hotelmanagement.domain.data;

import java.math.BigDecimal;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import lombok.Data;
import lombok.ToString;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.search.annotations.ContainedIn;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.IndexedEmbedded;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

/**
 * This is a representation of the customer used by CustomerService and the CustomerController as
 * DTO.
 *
 * @author Michael
 * @author Johannes
 */
@Data
@ToString(exclude = "receipts")
@Indexed
@Entity
public class CustomerEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

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

  @IndexedEmbedded
  @ManyToOne(cascade = {CascadeType.PERSIST})
  @JoinColumn
  private AddressEntity billingAddress;

  @Column
  private String companyName;

  @Column
  @Lob
  private String note;

  /**
   * Store user discount as double.
   *
   * <p>
   * Percent value, Min value is 0 max value is 100. Default is 0
   * </p>
   */
  @Column
  @NotNull
  @ColumnDefault("0")
  @Digits(fraction = 2, integer = 3)
  @Min(value = 0)
  @Max(value = 100)
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

  @ContainedIn
  @ManyToMany(cascade = {CascadeType.PERSIST})
  @JoinTable(name = "Customer_Receipt",
      joinColumns = {@JoinColumn(name = "customerEntity_id", referencedColumnName = "id")},
      inverseJoinColumns = {
          @JoinColumn(name = "receiptEntity_id", referencedColumnName = "receiptId")})
  private List<ReceiptEntity> receipts = new ArrayList<>();
}
