package at.ac.tuwien.student.sese2017.xp.hotelmanagement.domain.data;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import lombok.Data;

/**
 * Example database entry.
 */
@Data
@Entity
public class ExampleEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO) // Generic ID generator
  private Long id;

  @Column
  private String firstField;
  @Column
  private String secondField;
  @Column
  private Date dateField;

  @Lob // to automatically convert the string into a clob instead of a varchar
  private String longField;

}
