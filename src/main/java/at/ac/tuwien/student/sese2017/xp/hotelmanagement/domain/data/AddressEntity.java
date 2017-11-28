package at.ac.tuwien.student.sese2017.xp.hotelmanagement.domain.data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import lombok.Data;
import org.hibernate.search.annotations.Analyzer;
import org.hibernate.search.annotations.ClassBridge;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.Store;

@Data
@Indexed
@Entity
//@ClassBridge(impl = AddressBridge.class)
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
