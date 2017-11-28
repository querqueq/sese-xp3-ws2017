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
@ClassBridge(impl = AddressBridge.class)
public class AddressEntity {

  @Id
  @GeneratedValue
  private long addressId;

  @Field(store = Store.YES, analyzer = @Analyzer(definition = "customanalyzer"))
  private String name;

  @Field(store = Store.YES, analyzer = @Analyzer(definition = "customanalyzer"))
  private String streetAddress1;

  @Field(store = Store.YES, analyzer = @Analyzer(definition = "customanalyzer"))
  private String streetAddress2;

  @Field(store = Store.YES, analyzer = @Analyzer(definition = "customanalyzer"))
  private String city;

  @Field(store = Store.YES, analyzer = @Analyzer(definition = "customanalyzer"))
  private String state;

  @Field(store = Store.YES, analyzer = @Analyzer(definition = "customanalyzer"))
  private String zipCode;
}
