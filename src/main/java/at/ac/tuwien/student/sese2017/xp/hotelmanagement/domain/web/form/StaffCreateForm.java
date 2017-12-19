package at.ac.tuwien.student.sese2017.xp.hotelmanagement.domain.web.form;

import at.ac.tuwien.student.sese2017.xp.hotelmanagement.domain.data.StaffEntity;
import lombok.Data;

@Data
public class StaffCreateForm {
  private StaffEntity entity = new StaffEntity();
  private Integer yearlyVacationDays = 20;
  private Integer initialVacationDays = yearlyVacationDays;
}
