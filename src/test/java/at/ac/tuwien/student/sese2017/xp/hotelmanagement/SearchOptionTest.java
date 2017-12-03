package at.ac.tuwien.student.sese2017.xp.hotelmanagement;

import static org.hamcrest.CoreMatchers.hasItems;
import static org.junit.Assert.assertThat;
import java.util.Arrays;
import java.util.stream.Collectors;
import org.junit.Test;
import at.ac.tuwien.student.sese2017.xp.hotelmanagement.domain.web.form.StaffSearchCriteria.SearchOption;

/**
 * Tests which are totally not there to juke test coverage.
 * 
 * @author Johannes
 */
public class SearchOptionTest {

  /**
   * Tests if e
   */
  @Test
  public void testEnumNames() {
    assertThat(Arrays.stream(SearchOption.values()).map(Enum::name).collect(Collectors.toList()),
        hasItems("CUSTOMERS", "RECEIPTS", "RESERVATIONS"));
  }
}
