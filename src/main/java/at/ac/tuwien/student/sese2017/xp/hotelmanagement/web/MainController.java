package at.ac.tuwien.student.sese2017.xp.hotelmanagement.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * This Class handles all requests to public index URLs and processes or redirects them on.
 */
@Slf4j
@Controller
public class MainController {

  /**
   * Handles calls to web representation.
   *
   * @return String representing the path to the template that is to be shown.
   */
  @RequestMapping("/")
  public String mainPage() {
    return "redirect:/index"; //redirect to the index page
  }


  /**
   * Handles calls to the suburl "/index" on the web representation.
   *
   * @return String representing the path to the template that is to be shown.
   */
  @RequestMapping("/index")
  public String index() {
    log.info("index - Page called");
    return "index"; //path to the template to call
  }
}
