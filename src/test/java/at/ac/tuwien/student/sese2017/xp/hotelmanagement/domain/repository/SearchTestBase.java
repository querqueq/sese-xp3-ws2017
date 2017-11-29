package at.ac.tuwien.student.sese2017.xp.hotelmanagement.domain.repository;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertThat;

import at.ac.tuwien.student.sese2017.xp.hotelmanagement.HotelManagementApplicationTests;
import at.ac.tuwien.student.sese2017.xp.hotelmanagement.domain.repository.custom.CustomSearchRepository;
import java.util.stream.Collectors;
import javax.transaction.Transactional;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.springframework.beans.factory.annotation.Autowired;

@Transactional
public abstract class SearchTestBase extends HotelManagementApplicationTests {

  protected abstract CustomSearchRepository<?> getSearch();

  /**
   * Find no customers for an keyword nobody matches with.
   * Should find no customers.
   */
  @Test
  public void testSearchUnfindable() {
    assertThat(getSearch().search("Xyczzte"), hasSize(0));
  }

  /**
   * Find no customers since 1 character is too little input.
   */
  @Test
  public void testSearchOneCharacter() {
    assertThat(getSearch().search("b"), hasSize(0));
  }

  /**
   * Find no customers since 2 characters is too little input.
   */
  @Test
  public void testSearchTwoCharacter() {
    assertThat(getSearch().search("bb"), hasSize(0));
  }

  /**
   * Find no customers since an empty input is not enough to find anything. 
   */
  @Test
  public void testSearchEmpty() {
    assertThat(getSearch().search(""), hasSize(0));
  }

  /**
   * Find no customers since no input is not enough to find anything. 
   */
  @Test
  public void testSearchNull() {
    assertThat(getSearch().search(null), hasSize(0));
  }
}