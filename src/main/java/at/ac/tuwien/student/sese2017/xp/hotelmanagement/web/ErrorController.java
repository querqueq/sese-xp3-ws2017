package at.ac.tuwien.student.sese2017.xp.hotelmanagement.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * This Class handles all requests to the /error sub url.
 * All errors of missing privilages, page not found, or others redirect to /error.
 *
 * @author akraschitzer
 * @author lkerck
 */
@Slf4j
public class ErrorController {

  /**
   * This method handles all requests to the /error suburl. It displays the occured error and
   * logs it.
   * @param throwable The throwable caused by the error.
   * @param model A model object handed to the tamplate.
   * @return The path to the template to call next.
   */
  @ExceptionHandler(Throwable.class)
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  public String exception(final Throwable throwable, final Model model) {
    log.error("Exception during execution of SpringSecurity application", throwable);
    String errorMessage = (throwable != null ? throwable.getMessage() : "Unknown error");
    model.addAttribute("errorMessage", errorMessage);
    return "error";
  }
}
