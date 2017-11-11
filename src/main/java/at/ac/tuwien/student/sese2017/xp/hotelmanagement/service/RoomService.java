package at.ac.tuwien.student.sese2017.xp.hotelmanagement.service;

import at.ac.tuwien.student.sese2017.xp.hotelmanagement.domain.data.PriceType;
import at.ac.tuwien.student.sese2017.xp.hotelmanagement.domain.data.RoomEntity;

import java.util.List;

import lombok.extern.slf4j.Slf4j;


/**
 * Service class for all room related functions.
 *
 * @author akraschitzer
 * @author lkerck
 */
@Slf4j
public class RoomService {

  /**
   * Search for rooms by given criteria.
   *
   * <p>Any value given will be used. If null is passed, that criteria will be ignored</p>
   *
   * @param name part of the room name or number
   * @param minOccupants minimal number of available slots
   * @param maxOccupants maximal number of available slots
   * @param priceType price type (type of occupancy. Only used for maxPrice)
   * @param maxPrice maximal price. Needs a defined priceType
   * @return Filtered result list
   */
  public List<RoomEntity> getAllRoomsByCriteria(String name, Integer minOccupants,
                                                Integer maxOccupants, PriceType priceType,
                                                Double maxPrice) {
    return null;
  }


}
