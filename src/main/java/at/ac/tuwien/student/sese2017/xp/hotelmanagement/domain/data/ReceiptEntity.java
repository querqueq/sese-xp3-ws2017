package at.ac.tuwien.student.sese2017.xp.hotelmanagement.domain.data;


import java.util.Date;
import lombok.Data;

@Data
public class ReceiptEntity {
  private AddressEntity hotelAddress;
  private AddressEntity customerAddress;
  private RoomEntity room;
  private Double discount;
  private Double price;
  private Integer durationOfStay;
  private Date receiptDate;
}
