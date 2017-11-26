package at.ac.tuwien.student.sese2017.xp.hotelmanagement.domain.data;


import java.util.Date;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.Data;

@Data
@Entity
public class ReceiptEntity {
  @Id
  @GeneratedValue
  private Long receiptId;
  @ManyToOne(cascade = {CascadeType.PERSIST})
  @JoinColumn
  private AddressEntity hotelAddress;
  @ManyToOne(cascade = {CascadeType.PERSIST})
  @JoinColumn
  private AddressEntity customerAddress;
  @ManyToOne
  @JoinColumn
  private RoomEntity room;
  private Double discount;
  private Double price;
  private Integer durationOfStay;
  private Date receiptDate;
}
