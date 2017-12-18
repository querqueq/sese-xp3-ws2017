package at.ac.tuwien.student.sese2017.xp.hotelmanagement.exceptions;

import static java.lang.String.format;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Thrown if a vacation cannot be requested due to too few vacation days.
 * 
 * @author johannes
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class NotEnoughVacationDaysException extends Exception {
  private static final long serialVersionUID = -5272380406565453037L;
  private Long staffId;
  private Integer leftVacationDays;
  private Integer forYear;

  /**
   * Constructs a {@linkplain NotEnoughVacationDaysException} with a error message containing the
   * parameters.
   * 
   * @param staffId id of staffer who does not have enough vacation days
   * @param leftVacationDays vacation days left in forYear for staffer with staffId
   * @param forYear target vacation year
   */
  public NotEnoughVacationDaysException(Long staffId, Integer leftVacationDays, Integer forYear) {
    super(format("Only "));
    this.staffId = staffId;
    this.leftVacationDays = leftVacationDays;
    this.forYear = forYear;
  }
}
