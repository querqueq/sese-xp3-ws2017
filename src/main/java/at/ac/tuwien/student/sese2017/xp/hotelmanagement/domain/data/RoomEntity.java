package at.ac.tuwien.student.sese2017.xp.hotelmanagement.domain.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import javax.persistence.OneToMany;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
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

  @ElementCollection
  private Map<PriceType, Double> priceMap = new HashMap<>();

}
