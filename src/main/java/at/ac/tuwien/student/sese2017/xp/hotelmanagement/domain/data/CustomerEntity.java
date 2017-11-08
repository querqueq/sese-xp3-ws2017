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

  /**
   * The full name of the customer.
   */
  @NotNull
  @Size(min = 2, max = 30)
  private String name;

  /**
   * The age of the customer, has to be at least 18.
   */
  @NotNull
  @Min(18)
  private Integer age;

  /**
   * The username of the customer.
   * This username will checked against when logging in.
   */
  @NotNull
  @Size(min = 6, max = 30)
  private String username;


  /**
   * The password of the customer.
   * This password will checked against when logging in.
   */
  @NotNull
  @Size(min = 6, max = 30)
  private String password;
}
