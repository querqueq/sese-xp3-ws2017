package at.ac.tuwien.student.sese2017.xp.hotelmanagement.domain.data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import lombok.Data;
import org.hibernate.envers.Audited;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.IndexedEmbedded;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

/**
 * Representation of a receipt.
 * 
 * @author akaschitzer
 */
@Data
@Indexed
@Audited
@Entity
public class ReceiptEntity {
  @Id
  @GeneratedValue
  private Long receiptId;

  @ManyToOne(cascade = {CascadeType.PERSIST})
  @JoinColumn
  @IndexedEmbedded
  private AddressEntity hotelAddress;

  @ManyToMany(cascade = {CascadeType.PERSIST})
  @JoinTable(name = "Customer_Receipt",
      joinColumns = {@JoinColumn(name = "receiptEntity_id", referencedColumnName = "receiptId")},
      inverseJoinColumns = {@JoinColumn(name = "customerEntity_id", referencedColumnName = "id")})
  @IndexedEmbedded
  private List<CustomerEntity> customers = new ArrayList<>();

  @ManyToMany(cascade = {CascadeType.PERSIST})
  @JoinTable(name = "Room_Receipt",
      joinColumns = {@JoinColumn(name = "receiptEntity_id", referencedColumnName = "receiptId")},
      inverseJoinColumns = {@JoinColumn(name = "roomEntity_id", referencedColumnName = "roomId")})
  @IndexedEmbedded
  private List<RoomEntity> rooms = new ArrayList<>();

  private Double discount;
  private Double price;
  private Integer durationOfStay;

  @DateTimeFormat(iso = ISO.DATE_TIME)
  private LocalDateTime receiptDate;

  public ReceiptEntity addCustomer(CustomerEntity customer) {
    customers.add(customer);
    return this;
  }

  public ReceiptEntity addRoom(RoomEntity room) {
    rooms.add(room);
    return this;
  }
}
