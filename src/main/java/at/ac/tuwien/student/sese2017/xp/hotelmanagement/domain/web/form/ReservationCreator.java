package at.ac.tuwien.student.sese2017.xp.hotelmanagement.domain.web.form;

import at.ac.tuwien.student.sese2017.xp.hotelmanagement.domain.data.CustomerEntity;
import at.ac.tuwien.student.sese2017.xp.hotelmanagement.domain.data.RoomEntity;

import java.time.LocalDateTime;
import java.util.List;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@Data
public class ReservationCreator {
  private List<CustomerEntity> selectedCustomers;
  private List<RoomEntity> selectedRooms;
  private LocalDateTime startDate;
  private LocalDateTime endDate;
}
