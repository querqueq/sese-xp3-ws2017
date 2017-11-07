package at.ac.tuwien.student.sese2017.xp.hotelmanagement.web;

import lombok.extern.java.Log;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Log
@Controller
public class MainController {

  @RequestMapping("/")
  public String mainPage() {
    return "redirect:/index";
  }

  @RequestMapping("index")
  public String index() {
    log.info("index - Page called");
    return "index";
  }
}
