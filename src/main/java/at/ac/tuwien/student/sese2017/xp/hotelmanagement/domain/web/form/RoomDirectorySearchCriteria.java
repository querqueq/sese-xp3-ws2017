package at.ac.tuwien.student.sese2017.xp.hotelmanagement.domain.web.form;

import at.ac.tuwien.student.sese2017.xp.hotelmanagement.domain.data.PriceType;
import lombok.Data;

/**
 * This is a data object representing the available criteria to define in the search mask of the
 * room directory.
 *
 * @author akraschitzer
 */
@Data
public class RoomDirectorySearchCriteria {

  /**
   * Search mask for the name attribute.
   */
  private String name;

  /**
   * Minimum occupants the searched room must have.
   */
  private Integer minOccupants;

  /**
   * Maximum occupants the searched room must have.
   */
  private Integer maxOccupants;

  /**
   * The type of price to search for.
   */
  private PriceType priceType;

  /**
   * The maximum price to search for.
   */
  private Double maxPrice;
}
