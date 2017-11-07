package at.ac.tuwien.student.sese2017.xp.hotelmanagement.domain.dao;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Customer {

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
