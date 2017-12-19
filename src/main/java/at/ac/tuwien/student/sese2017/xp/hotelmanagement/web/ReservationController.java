package at.ac.tuwien.student.sese2017.xp.hotelmanagement.web;

import at.ac.tuwien.student.sese2017.xp.hotelmanagement.domain.data.RoomEntity;
import at.ac.tuwien.student.sese2017.xp.hotelmanagement.domain.repository.RoomRepository;
import at.ac.tuwien.student.sese2017.xp.hotelmanagement.domain.test.TestDataDirectory;
import at.ac.tuwien.student.sese2017.xp.hotelmanagement.domain.web.form.ReservationCreator;
import at.ac.tuwien.student.sese2017.xp.hotelmanagement.domain.web.form.ReservationRoomFind;
import at.ac.tuwien.student.sese2017.xp.hotelmanagement.domain.web.form.StaffSearchCriteria;
import at.ac.tuwien.student.sese2017.xp.hotelmanagement.service.ReservationService;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * This is the controller for all general room related actions on the Webpage.
 *
 * @author akraschitzer
 * @author lkerck
 */
@Slf4j
@Controller
public class ReservationController {

  private static final String SEARCH_RESULT_KEY = "searchResult";
  private static final String SELECTED_ROOMS_KEY = "selectedRooms";
  private static final String RESERVATION_CREATE_VIEW = "reservation/roomSearch.html";
  private static final String SEARCH_CRITERIA = "searchCriteria";
  private final RoomRepository roomRepository;
  private final ReservationService reservationService;
  private final TestDataDirectory tD;

  public ReservationController(ReservationService reservationService,
                               TestDataDirectory testDataDirectory,
                               RoomRepository roomRepository) {
    this.reservationService = reservationService;
    this.tD = testDataDirectory;
    this.roomRepository = roomRepository;
  }

//  @RequestMapping("/staff/reservation/create")
  public String createReservation(Model model, ReservationCreator reservationCreator) {
    List<RoomEntity> selectedRooms = new ArrayList<>();
    selectedRooms.add(tD.ROOM_2);
    model.addAttribute("selectedRooms", selectedRooms);
    return "staff/reservationCreate";
  }

  /**
   * Handles calls to the suburl "/staff/index" on the web representation.
   *
   * @return String representing the path to the template that is to be shown.
   */
  DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

  @RequestMapping({"/staff/reservation/create"})
  public String getSearch(Model model,
      @RequestParam(value = "searchText", required = false) String searchText,
      @RequestParam(value = "from", required = false) String from,
      @RequestParam(value = "to", required = false) String to) {
    log.info("reservation create search page called");
    Collection<RoomEntity> foundRooms = new ArrayList<>();
    if(searchText != null) {
      foundRooms = roomRepository.findAllByNameContainingIgnoringCase(searchText);
    }
    ReservationRoomFind staffSearchCriteria = new ReservationRoomFind();
    if(from != null) {
      staffSearchCriteria.setFrom(from);
    }
    if(to != null) {
      staffSearchCriteria.setTo(to);
    }
    model.addAttribute(SEARCH_CRITERIA, staffSearchCriteria);
    model.addAttribute(SEARCH_RESULT_KEY, foundRooms);
    model.addAttribute(SELECTED_ROOMS_KEY, new ArrayList<>());

    return RESERVATION_CREATE_VIEW;
  }

  @PostMapping("/staff/reservation/create")
  public String doSearch(Model model,
      @ModelAttribute(SEARCH_CRITERIA) ReservationRoomFind criteria) {
    return String.format("redirect:/staff/reservation/create?searchText=%s&from=%s&to=%s", criteria.getSearchText(), criteria.getFrom(), criteria.getTo());
  }
  @PostMapping("/staff/reservation/addRoom")
  public String doSearch(Model model,
      @ModelAttribute(SEARCH_CRITERIA) StaffSearchCriteria criteria) {
    return String.format("redirect:/staff/reservation/create?searchText=%s", criteria.getSearchText());
  }
}
