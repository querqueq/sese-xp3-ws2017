package at.ac.tuwien.student.sese2017.xp.hotelmanagement.domain.data;

import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertThat;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import javax.transaction.Transactional;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import at.ac.tuwien.student.sese2017.xp.hotelmanagement.HotelManagementApplicationTests;
import at.ac.tuwien.student.sese2017.xp.hotelmanagement.domain.repository.CustomerSearch;
import at.ac.tuwien.student.sese2017.xp.hotelmanagement.domain.test.TestDataInjector;

@Transactional
public class CustomerSearchTest extends HotelManagementApplicationTests {

  @Autowired
  private CustomerSearch customerSearch;

  /**
   * Find 2 matching customers whose name or address contains "Bäcker".
   * Should find Müller and Bäcker. 
   */
  @Test
  public void testSearchPartialMatch() {
    assertThat(
        customerSearch.search("Bäcker").stream().map(c -> c.getName()).collect(Collectors.toList()),
        Matchers.<Collection<String>>allOf(hasSize(2),
            hasItem(TestDataInjector.CUSTOMER_1.getName()),
            hasItem(TestDataInjector.CUSTOMER_2.getName())));
  }

  /**
   * Find exact matches for one given keyword.
   * Should find Müller.
   */
  @Test
  public void testSearchExactMatches() {
    assertThat(
        customerSearch.search("Müller").stream().map(c -> c.getName()).collect(Collectors.toList()),
        Matchers.<Collection<String>>allOf(hasSize(1),
            hasItem(TestDataInjector.CUSTOMER_1.getName())));
  }
  
  /**
   * Find all customes where either their name contains Abbey or their Address.
   * Should find Bäcker and Abbey.
   */
  @Test
  public void testSearchExactMatchDifferentFields() {
    assertThat(
        customerSearch.search("Abbey").stream().map(c -> c.getName()).collect(Collectors.toList()),
        Matchers.<Collection<String>>allOf(hasSize(2),
            hasItem(TestDataInjector.CUSTOMER_2.getName()),
            hasItem(TestDataInjector.CUSTOMER_3.getName())));
  }
  
  /**
   * Find all customers whose address contain the keyword "Wien".
   * Should find Müller and Fields.
   */
  @Test
  public void testSearchExactMatchAddress() {
    assertThat(
        customerSearch.search("Wien").stream().map(c -> c.getName()).collect(Collectors.toList()),
        Matchers.<Collection<String>>allOf(hasSize(2),
            hasItem(TestDataInjector.CUSTOMER_1.getName()),
            hasItem(TestDataInjector.CUSTOMER_3.getName())));
  }
  
  /**
   * Find all customer who match for either "Abbey" or "Wien" only once.
   * Should return all customers (Müller, Bäcker and Fields).
   */
  @Test
  public void testSearchMultipleExactMatches() {
    assertThat(
        customerSearch.search("Abbey Wien").stream().map(c -> c.getName()).collect(Collectors.toList()),
        Matchers.<Collection<String>>allOf(hasSize(3),
            hasItem(TestDataInjector.CUSTOMER_1.getName()),
            hasItem(TestDataInjector.CUSTOMER_2.getName()),
            hasItem(TestDataInjector.CUSTOMER_3.getName())));
  }

  /**
   * Find all customers who match for a keyword with 1 spelling error.
   * Keyword is "Abbay" instead of "Abbey".
   * Should find Bäcker and Fields.
   */
  @Test
  public void testSearchSpellingError() {
    assertThat(
        customerSearch.search("Abbay").stream().map(c -> c.getName()).collect(Collectors.toList()),
        Matchers.<Collection<String>>allOf(hasSize(2),
            hasItem(TestDataInjector.CUSTOMER_2.getName()),
            hasItem(TestDataInjector.CUSTOMER_3.getName())));
  }

  /**
   * Find no customers for an keyword nobody matches with.
   * Should find no customers.
   */
  @Test
  public void testSearchUnfindable() {
    assertThat(customerSearch.search("Xyczzte").stream().map(c -> c.getName())
        .collect(Collectors.toList()), hasSize(0));
  }

  /**
   * Find no customers since 1 character is too little input.
   */
  @Test
  public void testSearchOneCharacter() {
    assertThat(
        customerSearch.search("b").stream().map(c -> c.getName()).collect(Collectors.toList()),
        hasSize(0));
  }

  /**
   * Find no customers since 2 characters is too little input.
   */
  @Test
  public void testSearchTwoCharacter() {
    assertThat(
        customerSearch.search("bb").stream().map(c -> c.getName()).collect(Collectors.toList()),
        hasSize(0));
  }

  /**
   * Find all customers who match for "bbe" since 3 characters are the least amount input required.
   * Should find Abbey and Fields.
   */
  @Test
  public void testSearchThreeCharacter() {
    assertThat(
        customerSearch.search("bbe").stream().map(c -> c.getName()).collect(Collectors.toList()),
        Matchers.<Collection<String>>allOf(hasSize(2),
            hasItem(TestDataInjector.CUSTOMER_2.getName()),
            hasItem(TestDataInjector.CUSTOMER_3.getName())));
  }

  /**
   * Find no customers since an empty input is not enough to find anything. 
   */
  @Test
  public void testSearchEmpty() {
    assertThat(
        customerSearch.search("").stream().map(c -> c.getName()).collect(Collectors.toList()),
        hasSize(0));
  }

  /**
   * Find no customers since no input is not enough to find anything. 
   */
  @Test
  public void testSearchNull() {
    assertThat(
        customerSearch.search(null).stream().map(c -> c.getName()).collect(Collectors.toList()),
        hasSize(0));
  }
}