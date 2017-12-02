package at.ac.tuwien.student.sese2017.xp.hotelmanagement.domain.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import lombok.Data;
import lombok.ToString;
import org.hibernate.envers.Audited;
import org.hibernate.envers.RelationTargetAuditMode;
import org.hibernate.search.annotations.ContainedIn;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Indexed;

/**
 * Representation of a hotel room.
 *
 * @author akaschitzer
 * @author lkerck
 */
@Data
@ToString(exclude = "receipts")
@Indexed
@Audited
//(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
@Entity
public class RoomEntity {

  /**
   * Unique id of a room.
   */
  @Id
  @GeneratedValue
  private Long roomId;

  /**
   * Room name or room number.
   */
  @Field
  @Column(nullable = false, unique = true)
  private String name;

  /**
   * The maximum number of people who can occupy the room at any time.
   */
  @Column(nullable = false)
  private Integer maxOccupants;

  @ElementCollection
  private Map<PriceType, Double> priceMap = new HashMap<>();

  @ContainedIn
  @ManyToMany(cascade = {CascadeType.PERSIST})
  @JoinTable(name = "Room_Receipt",
      joinColumns = {@JoinColumn(name = "roomEntity_id", referencedColumnName = "roomId")},
      inverseJoinColumns = {
          @JoinColumn(name = "receiptEntity_id", referencedColumnName = "receiptId")})
  private List<ReceiptEntity> receipts = new ArrayList<>();

  public RoomEntity addReceipt(ReceiptEntity receipt) {
    receipts.add(receipt);
    return this;
  }
}
