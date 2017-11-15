package at.ac.tuwien.student.sese2017.xp.hotelmanagement.web;

import at.ac.tuwien.student.sese2017.xp.hotelmanagement.domain.web.form.RoomDirectorySearchCriteria;
import at.ac.tuwien.student.sese2017.xp.hotelmanagement.service.RoomService;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;


/**
 * This is the controller for all general room related actions on the Webpage.
 *
 * @author akraschitzer
 * @author lkerck
 */
@Slf4j
@Controller
public class RoomController {

  private final RoomService roomService;

  @Autowired
  public RoomController(RoomService roomService) {
    this.roomService = roomService;
  }

  /**
   * Handles calls to the suburl "/staff/roomDirectory" on the web representation.
   *
   * @return String representing the path to the template that is to be shown.
   */
  @RequestMapping("/staff/roomDirectory")
  public String roomDirectory(Model model, RoomDirectorySearchCriteria searchCriteria) {
    log.info("staff roomDirectory - Page called");

    // If max price is not set we have to ignore priceType as well, because it is always set.
    if (searchCriteria.getMaxPrice() == null) {
      searchCriteria.setPriceType(null);
    }

    model.addAttribute("roomList", roomService.getAllRoomsByCriteria(
        searchCriteria.getName(),
        searchCriteria.getMinOccupants(),
        searchCriteria.getMaxOccupants(),
        searchCriteria.getPriceType(),
        searchCriteria.getMaxPrice()
    ));

    return "staff/roomDirectory"; //path to the template to call
  }
}
