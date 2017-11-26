package at.ac.tuwien.student.sese2017.xp.hotelmanagement.domain.data;

import lombok.Data;

@Data
public class AddressEntity {
  private String name;
  private String streetAddress1;
  private String streetAddress2;
  private String city;
  private String state;
  private String zipCode;
}
