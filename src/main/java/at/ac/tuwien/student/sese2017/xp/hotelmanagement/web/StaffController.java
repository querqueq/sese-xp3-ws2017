package at.ac.tuwien.student.sese2017.xp.hotelmanagement.web;

import lombok.extern.java.Log;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Log
@Controller
public class StaffController {

  @RequestMapping("/staff/index")
  public String userIndex() {
    return "staff/index";
  }
}
