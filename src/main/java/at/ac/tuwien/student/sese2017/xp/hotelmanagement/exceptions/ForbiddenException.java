package at.ac.tuwien.student.sese2017.xp.hotelmanagement.exceptions;

import static java.lang.String.format;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import at.ac.tuwien.student.sese2017.xp.hotelmanagement.domain.data.Role;

/**
 * Exception for operations that could not be performed because a user does not have the authority.
 * @author johannes
 */
@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ForbiddenException extends RuntimeException {
  public ForbiddenException(String msg) {
    super(msg);
  }
  
  public ForbiddenException(Role missingRole, Long deniedUser, String deniedAction) {
    this(format("User %s cannot perform '%s' because they are missing role %s"
        , deniedUser, deniedAction, missingRole));
  }
}
