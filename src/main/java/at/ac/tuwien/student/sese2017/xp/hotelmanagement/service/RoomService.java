package at.ac.tuwien.student.sese2017.xp.hotelmanagement.service;

import at.ac.tuwien.student.sese2017.xp.hotelmanagement.domain.data.PriceType;
import at.ac.tuwien.student.sese2017.xp.hotelmanagement.domain.data.RoomEntity;

import at.ac.tuwien.student.sese2017.xp.hotelmanagement.domain.repository.RoomRepository;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * Service class for all room related functions.
 *
 * @author akraschitzer
 * @author lkerck
 */
@Slf4j
@Service
public class RoomService {

  private final RoomRepository roomRepository;

  @Autowired
  public RoomService(RoomRepository roomRepository) {
    this.roomRepository = roomRepository;
  }

  /**
   * Get all rooms.
   */
  public List<RoomEntity> getAllRooms() {
    // Convert iterable to list
    Iterator<RoomEntity> it = roomRepository.findAll().iterator(); // Get iterator of result
    ArrayList<RoomEntity> roomEntities = new ArrayList<>(); // create return list
    it.forEachRemaining(roomEntities::add); //add all elements to arraylist

    return roomEntities;
  }

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
   * @throws IllegalArgumentException if Integer values are negative or contradict themself or if
   *     either price type or maxPrice is given without the other.
   */
  public List<RoomEntity> getAllRoomsByCriteria(String name, Integer minOccupants,
                                                Integer maxOccupants, PriceType priceType,
                                                Double maxPrice) throws IllegalArgumentException {

    // Change all null values to values incorporating the whole answer-space.
    // Before input checking to prevent null pointer exceptions.
    if (name == null) {
      name = "";
    }
    if (minOccupants == null) {
      minOccupants = 0;
    }
    if (maxOccupants == null) {
      maxOccupants = Integer.MAX_VALUE;
    }

    // Check for erroneous inputs. Null pointers shouldn't arrive here.
    if (priceType != null && maxPrice == null || priceType == null && maxPrice != null) {
      throw new IllegalArgumentException("PriceType and maxPrice can only be used together");
    }

    if (minOccupants < 0 || maxOccupants < 0 || minOccupants > maxOccupants) {
      throw new IllegalArgumentException("Occupants boundaries can't be negative");
    }

    if (maxPrice != null && maxPrice < 0) {
      throw new IllegalArgumentException("Price can't be negative");
    }

    /*
      Switch between the two cases price set or price not set. The other variables can simply be set
      to a generic value.
     */

    if (priceType == null) {
      // Call repository function with all values and wrap collection in a list
      return new ArrayList<>(
          roomRepository.findAllByNameContainingAndMaxOccupantsBetween(
              name, minOccupants, maxOccupants));
    } else {
      // Call repository function with all values and wrap collection in a list
      return new ArrayList<>(
          roomRepository.findAll(name, minOccupants, maxOccupants, priceType, maxPrice));
    }
  }


}
