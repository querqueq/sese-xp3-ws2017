package at.ac.tuwien.student.sese2017.xp.hotelmanagement.domain.data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Data;

/**
 * This is a representation of the customer used by RegistrationController and registration.html.
 * It works as an example how to apply an objects information to a web page and vice versa.
 *
 * <p>
 * This class should be extended for use as DAO for database accesses.
 * </p>
 *
 * @author akraschitzer
 */
@Data
public class CustomerEntity {

  @NotNull
  @Size(min = 2, max = 30)
  private String name;

  @NotNull
  @Min(18)
  private Integer age;

  @NotNull
  @Size(min = 6, max = 30)
  private String username;

  @NotNull
  @Size(min = 6, max = 30)
  private String password;
}
