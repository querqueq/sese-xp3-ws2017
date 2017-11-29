package at.ac.tuwien.student.sese2017.xp.hotelmanagement.domain.data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import lombok.Data;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Indexed;

@Data
@Indexed
@Entity
public class AddressEntity {

  @Id
  @GeneratedValue
  private long addressId;

  @Field
  private String name;

  @Field
  private String streetAddress1;

  @Field
  private String streetAddress2;

  @Field
  private String city;

  @Field
  private String state;

  @Field
  private String zipCode;
}
