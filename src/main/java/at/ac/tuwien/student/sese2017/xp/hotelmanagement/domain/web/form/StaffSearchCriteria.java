package at.ac.tuwien.student.sese2017.xp.hotelmanagement.domain.web.form;

import lombok.Data;

/**
 * This is a data object representing the available criteria to search for customers.
 * 
 * @author Michael
 *
 */
@Data
public class StaffSearchCriteria {
  /**
   * Search String for full text search.
   */
  private String searchText;
  
  private SearchOption searchOption;
  
  public static enum SearchOption {
    CUSTOMERS,
    RECEIPTS,
    RESERVATIONS
  }
}
