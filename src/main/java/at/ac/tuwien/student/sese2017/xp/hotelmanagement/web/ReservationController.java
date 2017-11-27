package at.ac.tuwien.student.sese2017.xp.hotelmanagement.web;

import at.ac.tuwien.student.sese2017.xp.hotelmanagement.domain.data.RoomEntity;
import at.ac.tuwien.student.sese2017.xp.hotelmanagement.domain.test.TestDataDirectory;
import at.ac.tuwien.student.sese2017.xp.hotelmanagement.domain.web.form.ReservationCreator;
import at.ac.tuwien.student.sese2017.xp.hotelmanagement.service.ReservationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

/**
 * This is the controller for all general room related actions on the Webpage.
 *
 * @author akraschitzer
 * @author lkerck
 */
@Slf4j
@Controller
public class ReservationController {

  ReservationService reservationService;

  TestDataDirectory tD;

  public ReservationController (ReservationService reservationService, TestDataDirectory testDataDirectory) {
    this.reservationService = reservationService;
    this.tD = testDataDirectory;
  }

  @RequestMapping("/staff/reservation/create")
  public String createReservation(Model model, ReservationCreator reservationCreator) {
    List<RoomEntity> selectedRooms = new ArrayList<>();
    selectedRooms.add(tD.ROOM_2);
    model.addAttribute("selectedRooms", selectedRooms);
    return "staff/reservationCreate";
  }

}
