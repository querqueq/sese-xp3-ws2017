package at.ac.tuwien.student.sese2017.xp.hotelmanagement.exceptions;

import static java.lang.String.format;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class NotEnoughVacationDaysException extends Exception {  
  private static final long serialVersionUID = -5272380406565453037L;
  private Long staffId;  
  private Integer leftVacationDays;  
  private Integer forYear;

  public NotEnoughVacationDaysException(Long staffId, Integer leftVacationDays, Integer forYear) {
    super(format("Only %d vacation days left for staffer %d!", leftVacationDays, staffId));
    this.staffId = staffId;
    this.leftVacationDays = leftVacationDays;
    this.forYear = forYear;
  }  
}
