package at.ac.tuwien.student.sese2017.xp.hotelmanagement.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
public class MainController {

  @RequestMapping("/")
  public String mainPage() {
    return "redirect:/index"; //redirect to the index page
  }

  @RequestMapping("/index")
  public String index() {
    log.info("index - Page called");
    return "index"; //path to the template to call
  }
}
