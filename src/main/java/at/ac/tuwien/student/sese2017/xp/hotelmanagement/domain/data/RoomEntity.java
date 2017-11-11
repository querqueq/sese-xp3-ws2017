package at.ac.tuwien.student.sese2017.xp.hotelmanagement.domain.data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * Representation of a hotel room.
 *
 * @author akaschitzer
 * @author lkerck
 */
@Data
@Entity
@Slf4j
public class RoomEntity {

  /**
   * Unique id of a room.
   */
  @Id
  @GeneratedValue
  private Long roomId;

  /**
   * Room name or room number.
   */
  @Column(nullable = false, unique = true)
  private String name;

  /**
   * The maximum number of people who can occupy the room at any time.
   */
  @Column(nullable = false)
  private Integer maxOccupants;

  /**
   * Price for one adult per night.
   */
  @Column
  private Double priceSingle = 0.0;  // initialize with default value

  /**
   * Price for two adults per night.
   */
  @Column
  private Double priceDouble = 0.0;  // initialize with default value

  /**
   * Price for three adults per night.
   */
  @Column
  private Double priceTriple = 0.0;  // initialize with default value

  /**
   * Price for one adult and one child per night.
   */
  @Column
  private Double priceSingleWithChild = 0.0;  // initialize with default value

  /**
   * Price for one adult and two children per night.
   */
  @Column
  private Double priceSingleWith2Children = 0.0;  // initialize with default value

  /**
   * Price for two adults and one child per night.
   */
  @Column
  private Double priceDoubleWithChild = 0.0;  // initialize with default value
}
