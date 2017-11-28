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
import org.hibernate.search.annotations.Analyze;
import org.hibernate.search.annotations.Analyzer;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.FieldBridge;
import org.hibernate.search.annotations.Index;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.IndexedEmbedded;
import org.hibernate.search.annotations.Store;
import org.hibernate.search.bridge.builtin.DefaultStringBridge;
import org.hibernate.search.bridge.builtin.impl.BuiltinIterableBridge;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

@Data
@Indexed
@Entity
public class ReceiptEntity {
  @Id
  @GeneratedValue
  private Long receiptId;

  @Field(index = Index.YES, store = Store.YES, analyzer = @Analyzer(definition = "customanalyzer"))
  @ManyToOne(cascade = {CascadeType.PERSIST})
  @JoinColumn
  @IndexedEmbedded
  @FieldBridge(impl = DefaultStringBridge.class)
  private AddressEntity hotelAddress;
  
  @Field(index = Index.YES, store = Store.YES, analyzer = @Analyzer(definition = "customanalyzer"))
  @ManyToMany(cascade = {CascadeType.PERSIST})
  @JoinTable(name = "Customer_Receipt",
  joinColumns = { @JoinColumn(name = "receiptEntity_id", referencedColumnName = "receiptId") }, 
  inverseJoinColumns = { @JoinColumn(name = "customerEntity_id", referencedColumnName = "id") })
  @IndexedEmbedded
  @FieldBridge(impl = BuiltinIterableBridge.class)
  private List<CustomerEntity> customers = new ArrayList<>();

  @Field(index = Index.YES, store = Store.YES, analyzer = @Analyzer(definition = "customanalyzer"))
  @ManyToMany(cascade = {CascadeType.PERSIST})
  @JoinTable(name = "Room_Receipt",
  joinColumns = { @JoinColumn(name = "receiptEntity_id", referencedColumnName = "receiptId") }, 
  inverseJoinColumns = { @JoinColumn(name = "roomEntity_id", referencedColumnName = "roomId") })
  @IndexedEmbedded
  @FieldBridge(impl = BuiltinIterableBridge.class)
  private List<RoomEntity> rooms = new ArrayList<>();

  private Double discount;
  private Double price;
  private Integer durationOfStay;

  @Field(index = Index.YES, store = Store.YES, analyze = Analyze.NO)
  //  @DateBridge(resolution = Resolution.HOUR, encoding = EncodingType.STRING)
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